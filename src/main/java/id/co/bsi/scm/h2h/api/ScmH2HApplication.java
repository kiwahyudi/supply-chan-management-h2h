package id.co.bsi.scm.h2h.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Value;

/**
 * Author   : kw
 * Date     : 3/1/22 03:03 PM
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ScmH2HApplication {
	
	@Value("${use.proxy}")
	private boolean useProxy;
	
	@Value("${proxy.host}")
	private String host;
	
	@Value("${proxy.port}")
	private Integer port;

	@Value("${server.ssl.key-store}")
	private String keyStore;
	
	@Value("${server.ssl.key-store-password}")
	private String keyStorePassword;
	
    public static void main(String[] args) {
        SpringApplication.run(ScmH2HApplication.class);
    }
    
    @Bean
    public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
//    	Path path = Paths.get(keyStore);
//    	
//    	SSLContext sslContext = new SSLContextBuilder()
//   		      .loadTrustMaterial(path.toFile(), keyStorePassword.toCharArray())
//   		      .build();
//   		    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
//   		    HttpClient httpClient = HttpClients.custom()
//   		      .setSSLSocketFactory(socketFactory)
//   		      .build();
//   		    HttpComponentsClientHttpRequestFactory factory = 
//   		      new HttpComponentsClientHttpRequestFactory(httpClient);
   		    
   		 HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
     	
     	if(useProxy) {
     		HttpHost httpHost = new HttpHost(host, port);
     		 
             HttpClientBuilder clientBuilder = HttpClientBuilder.create();
             clientBuilder.setProxy(httpHost);
      
             HttpClient httpClient = clientBuilder.build();
      
             factory = new HttpComponentsClientHttpRequestFactory(httpClient);
//  		    factory.setHttpClient(httpClient);
             factory.setConnectionRequestTimeout(1000);
             factory.setConnectTimeout(1000);
             factory.setReadTimeout(1000);
     	}
       	
 
       return new RestTemplate(factory);
    }
    
}
