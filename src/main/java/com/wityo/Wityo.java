package com.wityo;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import com.wityo.common.WityoRestAppProperties;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import sun.net.www.http.HttpClient;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.SSLContextBuilder;

@SpringBootApplication
@EnableConfigurationProperties(WityoRestAppProperties.class)
public class Wityo {
	/*@Value("${server.ssl.trust-store}")
	private String trustStore;*/
	@Autowired
	private WityoRestAppProperties wityoRestAppProperties;

	public static void main(String[] args) {
		SpringApplication.run(Wityo.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RestTemplate getRestTemplate() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
		KeyStore  clientStore = KeyStore.getInstance("PKCS12");
		clientStore.load(new FileInputStream(wityoRestAppProperties.getWityoRestSslKeyStore()), "wityorest123".toCharArray());

		SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
		sslContextBuilder.loadKeyMaterial(clientStore, "wityorest123".toCharArray());
		sslContextBuilder.loadTrustMaterial(new TrustStrategy(){
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		});

		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build(),SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		CloseableHttpClient httpClient = HttpClients.custom()
			.setSSLSocketFactory(sslConnectionSocketFactory)
			.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setConnectTimeout(10000); // 10 seconds
		requestFactory.setReadTimeout(10000); // 10 seconds
		return new RestTemplate(requestFactory);
	}
	
	WebClient.Builder getWebClientBuilderBean(){
		return WebClient.builder();
	}
	
}
