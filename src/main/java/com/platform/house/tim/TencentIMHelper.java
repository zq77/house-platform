package com.platform.house.tim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.ServerException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.platform.house.controller.tim.TenCentIMController;
import com.platform.house.domain.User;
import com.tls.sigcheck.tls_sigcheck;

public class TencentIMHelper {
	private static final Logger log = LoggerFactory.getLogger(TencentIMHelper.class);
	
	// 腾讯云通讯Appid
	private static String SDKAppid = "1400114062";
	// 腾讯云通讯管理员帐号
	private static String AdminAcct = "benguo";
	private TencentIMConfig config;
	private RestTemplate rest;
	private ObjectMapper objectMapper;

	private Joiner.MapJoiner joiner = Joiner.on("&").withKeyValueSeparator("=");

	public TencentIMHelper(TencentIMConfig config, RestTemplate rest, ObjectMapper objectMapper) {
		this.config = config;
		this.rest = rest;
		this.objectMapper = objectMapper;
	}
	
	public static TencentIMHelper getTIMHelper() {
		TencentIMConfig config = new TencentIMConfig();
		config.setDefaultImAdminAccount(AdminAcct);
		config.setSdkAppid(SDKAppid);
		String path = Objects.requireNonNull(TenCentIMController.class.getClassLoader().getResource("")).getPath();
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			config.setJnisigcheckLibPath(path + "tencent_im/jnisigcheck.dll");
		} else {
			config.setJnisigcheckLibPath(path + "tencent_im/jnisigcheck.so");
		}

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("tencent_im/private_key");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder strBuilder = new StringBuilder();
		String s = "";
		try {
			while ((s = br.readLine()) != null) {
				strBuilder.append(s + '\n');
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String priKey = strBuilder.toString();
		config.setPrivateKey(priKey);
		RestTemplate rest = new RestTemplate();
		TencentIMHelper helper = new TencentIMHelper(config, rest, new ObjectMapper());
		return helper;
	}

	/**
	 * 生成usersig
	 *
	 * @param identifier
	 * @return
	 * @throws IOException
	 */
	public String genUsersig(String identifier) throws IOException {
		tls_sigcheck demo = new tls_sigcheck();
		// 使用前请修改动态库的加载路径
		demo.loadJniLib(config.getJnisigcheckLibPath());
		int ret = demo.tls_gen_signature_ex2(config.getSdkAppid(), identifier, config.getPrivateKey());
		if (0 != ret) {
			log.error("ret: {}, errMsg:{}", ret, demo.getErrMsg());
			throw new IOException(ResultCode.IM_ACCOUNT_LOGIN_FAILD);
		} else {
			String usersig = demo.getSig();
			System.out.println("sig:\n" + demo.getSig());
			log.debug("identifier '{}' take usersig is {}", identifier, usersig);
			return usersig;
		}
	}

	/**
	 * 获取默认设置的im admin 账号的usersig
	 *
	 * @return
	 * @throws IOException
	 */
	public String getIMAdminUsersig() throws IOException {
		if (StringUtils.isEmpty(config.getDefaultImAdminAccount())) {
			log.error("TencentIMConfig.defaultImAdminAccount不存在");
			throw new ServerException(ResultCode.IM_ACCOUNT_LOGIN_FAILD, new IOException());
		}
		return getIMAdminUsersig(config.getDefaultImAdminAccount());
	}

	/**
	 * 获取im admin 账号的usersig
	 *
	 * @param identifier
	 * @return
	 * @throws IOException
	 */
	public String getIMAdminUsersig(String identifier) throws IOException {
		String usersig = _getIMAdminUsersig(identifier);
		if (StringUtils.isEmpty(usersig)) {
			usersig = genUsersig(identifier);
			cacheIMAdminUsersig(identifier, usersig);
		}
		return usersig;
	}

	/**
	 * 导入账号
	 *
	 * @param user
	 * {"Identifier":"convertPassengerToVo","Nick":"convertPassengerToVo","FaceUrl":"http://www.qq.com"}
	 * @throws IOException
	 */
	public IMActionResponse accountImport(User user) throws IOException {
		String url = "https://console.tim.qq.com/v4/im_open_login_svc/account_import?";
		String queryString = joiner.join(getDefaultParams());
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("Identifier", user.getUsername());
		requestBody.put("Nick", user.getNickname());
		requestBody.put("FaceUrl", user.getAvatar());
		IMActionResponse res = request(url + queryString, requestBody, IMActionResponse.class);
		log.info(res.getActionStatus());
		return res;
	}

	/**
	 * 帐号批量导入
	 *
	 * @param accountIds
	 *            用户名，单个用户名长度不超过 32 字节，单次最多导入100个用户名
	 * @return 返回导入失败的帐号列表
	 * @throws IOException
	 */
	public List<String> multiAccountImport(String... accountIds) throws IOException {
		String url = "https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import?";
		String queryString = joiner.join(getDefaultParams());
		Map<String, Object> requestBody = ImmutableMap.of("Accounts", Arrays.asList(accountIds));

		IMMultiAccImportResponse res = request(url + queryString, requestBody, IMMultiAccImportResponse.class);
		if (!res.isSuccess()) {
			log.error("帐号批量导入失败:{}, response message is: {}", StringUtils.join(accountIds, ", "), toString(res));
			throw new IOException(ResultCode.IM_ACTION_FAILD);
		}
		return res.getFailAccounts();
	}

	/**
	 * 设置用户资料（IM）
	 *
	 * @param userDTO
	 * @throws IOException
	 */
	public void setAccountProfile(User userDTO) throws IOException {
		String url = "https://console.tim.qq.com/v4/profile/portrait_set?";
		String queryString = joiner.join(getDefaultParams());
		String nikename = userDTO.getUsername();
		int gder = 1;
		String gender = gder == 1 ? "Gender_Type_Male" : gder == 2 ? "Gender_Type_Female" : "Gender_Type_Unknown";
		Map<String, Object> requestBody = ImmutableMap.of("From_Account", userDTO.getUsername(), "ProfileItem",
				ImmutableList.of(ofProPortItem("Tag_Profile_IM_Nick", nikename),

						ofProPortItem("Tag_Profile_IM_Gender", gender),

						ofProPortItem("Tag_Profile_IM_Image", userDTO.getAvatar())));

		IMActionResult res = request(url + queryString, requestBody);
		if (!res.isSuccess()) {
			log.error("调用'{}'的账号信息到腾讯云IM失败, response message is: {}", userDTO.getUsername(), toString(res.getResult()));
			throw new IOException(ResultCode.IM_ACCOUNT_INFO_SET_FAILD);
		}
	}

	/**
	 * @param tag
	 * @param value
	 * @return
	 */
	private Map<String, Object> ofProPortItem(String tag, Object value) {
		return ImmutableMap.of("tag", tag, "value", value);
	}

	/**
	 * 默认值
	 *
	 * @param obj
	 * @param def
	 * @return
	 */
	private Object defNull(Object obj, Object def) {
		return obj == null ? def : obj;
	}

	/**
	 * 返回默认的参数
	 *
	 * @return
	 * @throws IOException
	 */
	private Map<String, String> getDefaultParams() throws IOException {
		Map<String, String> pathParams = Maps.newHashMap();
		pathParams.put("usersig", getIMAdminUsersig());
		pathParams.put("identifier", config.getDefaultImAdminAccount());
		pathParams.put("sdkappid", config.getSdkAppid());
		pathParams.put("random", UUID.randomUUID().toString());
		pathParams.put("contenttype", "json");
		return pathParams;
	}

	private void initPrivateKey() throws ServerException {
		if (StringUtils.isNotEmpty(config.getPrivateKey())) {
			return;
		}
		if (StringUtils.isEmpty(config.getPrivateKeyPath())) {
			throw new ServerException(ResultCode.OPERATION_FAILD, new IOException());
		}
		// todo
	}

	private <T> T request(String url, Map<String, Object> params, Class<T> cls) throws ServerException {
		return toBean(requestInvoke(url, params), cls);
	}

	private IMActionResult request(String url, Map<String, Object> params) throws ServerException {
		Map<String, Object> map = toBean(requestInvoke(url, params), new TypeReference<Map<String, Object>>() {
		});
		return new IMActionResult(map);

	}

	private String requestInvoke(String url, Map<String, Object> params) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		JSONObject jsonObject = new JSONObject(params);

		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
		String json = rest.postForObject(url, formEntity, String.class);
		try {
			log.info("request url {}, the params is: {}, and response: {}", url,
					objectMapper.writeValueAsString(params), json);
		} catch (IOException e) {

		}
		return json;
	}

	private <T> T toBean(String json, Class<T> cls) throws ServerException {
		try {
			return objectMapper.readValue(json, cls);
		} catch (IOException e) {
			log.error("json:{} 转换类型失败: {} ", json, cls);
			log.error(e.getMessage(), e);
			throw new ServerException(ResultCode.OPERATION_FAILD, e);
		}
	}

	private <T> T toBean(String json, TypeReference<T> type) throws ServerException {
		try {
			return objectMapper.readValue(json, type);
		} catch (IOException e) {
			log.error("json:{} 转换类型失败: {} ", json, type.getType());
			log.error(e.getMessage(), e);
			throw new ServerException(ResultCode.OPERATION_FAILD, e);
		}
	}

	private String _getIMAdminUsersig(String identifier) {
		// todo
		return null;
	}

	private void cacheIMAdminUsersig(String identifier, String usersig) {
		// todo
	}

	private String toString(Object obj) {
		if (obj == null) {
			return null;
		} else if ("".equals(obj)) {
			return "";
		}
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return obj.toString();
		}
	}

}
