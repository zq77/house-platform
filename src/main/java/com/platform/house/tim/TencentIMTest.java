package com.platform.house.tim;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.house.services.AreaDivisionService;
import com.tls.sigcheck.tls_sigcheck;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class TencentIMTest {

	@Test
	public void testHelper() throws IOException {
		TencentIMConfig config = new TencentIMConfig();
		config.setDefaultImAdminAccount("benguo");
		config.setSdkAppid("1400114062");
		// config.setJnisigcheckLibPath("tencent_im/tls_sig_api-src/src/jnisigcheck.so");
		String path = Objects.requireNonNull(TencentIMTest.class.getClassLoader().getResource("")).getPath();
		config.setJnisigcheckLibPath(path + "tencent_im/jnisigcheck.dll");

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("tencent_im/private_key");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuilder strBuilder = new StringBuilder();
		String s = "";
		while ((s = br.readLine()) != null) {
			strBuilder.append(s + '\n');
		}
		br.close();
		String priKey = strBuilder.toString();

		config.setPrivateKey(priKey);
		// config.setPrivateKeyPath("");
		// RestTemplate rest = new RestTemplate();
		// rest.setMessageConverters(Arrays.asList(new
		// MappingJackson2HttpMessageConverter()));
		RestTemplate rest = jsonRestTemplate();
		TencentIMHelper helper = new TencentIMHelper(config, rest, new ObjectMapper());
		String adminUsersig = helper.getIMAdminUsersig();
		System.out.println("adminUsersig:\n" + adminUsersig);
		// helper.accountImport("test10");
		List<String> list = helper.multiAccountImport("test2jalsdkfjaoisdufo32jkleoflkjasdkfjasdiufowerquwlkj0",
				"test21", "test22", "test23");
		System.out.println(list);

	}

	public RestTemplate jsonRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		restTemplate.getMessageConverters().add(0, new FastJsonHttpMessageConverter());
		restTemplate.getInterceptors().add((request, body, execution) -> {
			System.out.println(new String(body));
			ClientHttpResponse response = execution.execute(request, body);
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			return response;
		});
		return restTemplate;
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(30000);
		factory.setConnectTimeout(30000);
		return factory;
	}
}
