package org.dante.springboot.service;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

/**
 * https://blog.csdn.net/mn960mn/article/details/48520199
 */
public class PoiService {

	public static void main(String[] args) {
		RestClient restClient = null;
		try {
//			SSLContext sslContext = new SSLContextBuilder()
//					.loadTrustMaterial(null, (X509Certificate[] chain, String authType) -> true).build(); 
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, 
					new TrustStrategy()  
            {  
                // 信任所有  
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException   
                {  
                    return true;  
                }  
            }).build(); 
					
			
			RestClientBuilder builder = RestClient.builder(new HttpHost("es.testos39.com", 443, "https"))
			        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
			        	@Override
			            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
			                return httpClientBuilder.setSSLContext(sslContext);
			            }
			        });
			restClient = builder.build();
			
			Header header =  new BasicHeader("Authorization", "Bearer PCzPZn-JCq1Peo4TwvqfTScnmzmaDQO2DfWtXl-xwNs");
			Response response = restClient.performRequest("GET", "/project.*/_search", header);
			RequestLine requestLine = response.getRequestLine();
			
			HttpHost host = response.getHost(); 
			int statusCode = response.getStatusLine().getStatusCode(); 
			Header[] headers = response.getHeaders(); 
			String responseBody = EntityUtils.toString(response.getEntity());
			
			System.out.println(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				restClient.close();
			} catch (IOException e) {
				System.out.println(2222);
				e.printStackTrace();
			}
		}
		
	}
}
