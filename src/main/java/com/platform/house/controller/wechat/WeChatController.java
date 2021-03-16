package com.platform.house.controller.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.platform.house.dto.WechatUserDto;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.xfire.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.platform.house.domain.User;
import com.platform.house.services.UserService;
import com.platform.house.tim.IMActionResponse;
import com.platform.house.tim.TencentIMHelper;
import com.platform.house.utils.ResponseUtil;

@RestController
@RequestMapping("/wechat")
public class WeChatController {
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = Logger.getLogger(WeChatController.class);

	private String WECHAT_AppID = "wxa105c3fdf0d33c27";
	private String WECHAT_Secret = "28b421bfd48e67eb8ec2b7c4d68f2299";


	@GetMapping(value = "/login")
	public ResponseEntity login(String code, String encryptedData, String iv) {
		String sessionKey = getSessionKey(code);
		String userInfoJsonStr = getUserInfo(sessionKey, encryptedData, iv);
		JSONObject userInfoJson = (JSONObject) JSON.parse(userInfoJsonStr);
		User user = userService.getUserByUnionid(userInfoJson.getString("unionId"));
		if(user == null) {
			user = userService.registerWeChatUser(userInfoJson);
		}
		if(user.isTimImport() == null) {
			// 导入TIM帐号
			importIMAccount(user);
		}
		WechatUserDto wechatUserDto = new WechatUserDto();
		wechatUserDto.setUserId(user.getId());
		wechatUserDto.setUsername(user.getUsername());
		return ResponseUtil.success(wechatUserDto);
	}

	private void importIMAccount(User user) {
		TencentIMHelper timHelper = TencentIMHelper.getTIMHelper();
		try {
			IMActionResponse res = timHelper.accountImport(user);
			// 设置导入TIM标记
			user.setTimImport(true);
			userService.saveAndFlush(user);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 微信小程序解密用户敏感数据获取用户信息
	 *
	 * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
	 * @param sessionKey 数据进行加密签名的密钥
	 * @param iv 加密算法的初始向量
	 * @return {"openId":"","nickName":"","gender":1,"language":"zh_CN","city":"","province":"","country":"","avatarUrl":"","unionId":
	 *         "", "watermark":{"timestamp":1534643823,"appid":""}}
	 */
	private String getUserInfo(String sessionKey, String encryptedData, String iv) {
		String result = "";
		logger.info(encryptedData);
		logger.info(sessionKey);
		logger.info(iv);
		// 被加密的数据
		byte[] dataByte = Base64.decode(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64.decode(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.decode(iv);
		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				result = new String(resultByte, "UTF-8");
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidParameterSpecException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidAlgorithmParameterException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchProviderException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("++++++ : " + result);
		return result;
	}
	
	/**
	 * 微信小程序获取用户sessionKey
	 * @param code 微信登陆后返回的生成码
	 * @return {"openId":"","nickName":"","gender":1,"language":"zh_CN","city":"","province":"","country":"","avatarUrl":"","unionId":
	 *         "", "watermark":{"timestamp":1534643823,"appid":""}}
	 */
	private String getSessionKey(String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WECHAT_AppID + "&secret=" + WECHAT_Secret
				+ "&js_code=" + code + "&grant_type=authorization_code'";
		RestTemplate rest = new RestTemplate();
		// {"session_key":"bHQfeStFJEn6VVoIiSLnTA==","openid":"oRK2o5Sqdm-tz47u5Uro4MluY0Ws"}
		String responeJson = rest.getForObject(url, String.class, Maps.newHashMap());
		JSONObject sessionKeyObj = (JSONObject) JSON.parse(responeJson);
		String sessioKey = (String) sessionKeyObj.get("session_key");
		return sessioKey;
	}

}
