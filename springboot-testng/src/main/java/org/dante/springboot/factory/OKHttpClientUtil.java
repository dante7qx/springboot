package org.dante.springboot.factory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpClientUtil {

	private OKHttpClientUtil() {
	}

	private static OkHttpClient okHttpClient;
	private static String serverUrl;
	private static Gson gson = new Gson();
	
	public static void buildServerUrl(String url) {
		serverUrl = url;
	}

	public static OkHttpClient getInstance() {
		if (okHttpClient == null) {
			okHttpClient = new OkHttpClient.Builder()
					// .sslSocketFactory(sslSocketFactory(), x509TrustManager())
					.retryOnConnectionFailure(false)
					.connectionPool(pool())
					.connectTimeout(30, TimeUnit.SECONDS)
					.readTimeout(30, TimeUnit.SECONDS)
					.writeTimeout(30, TimeUnit.SECONDS).build();
		}
		return okHttpClient;
	}

	private static ConnectionPool pool() {
		return new ConnectionPool(200, 5, TimeUnit.MINUTES);
	}
	
	/**
	 * 调用 Restful GET 接口
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Response getRestful(String url) throws IOException {
		getInstance();
		Request request = new Request.Builder()
                .url(serverUrl.concat(url))
                .build();
		return okHttpClient.newCall(request).execute();
	}
	
	/**
	 * 调用 Restful POST 接口
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static Response postRestful(String url, Map<String, Object> param) throws IOException {
		getInstance();
		RequestBody requestBody = RequestBody.create(gson.toJson(param), MediaType.parse("application/json; charset=utf-8"));
		Request request = new Request.Builder()
				.url(serverUrl.concat(url))
                .post(requestBody)
                .build();
		return okHttpClient.newCall(request).execute();
	}
	
	/**
	 * 调用 Restful DELETE 接口
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Response deleteRestful(String url) throws IOException {
		getInstance();
		Request request = new Request.Builder()
				.url(serverUrl.concat(url))
                .delete()
                .build();
		return okHttpClient.newCall(request).execute();
	}
	
}
