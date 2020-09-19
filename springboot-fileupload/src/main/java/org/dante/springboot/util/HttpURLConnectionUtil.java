package org.dante.springboot.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import lombok.NonNull;

/**
 * 
 * @author dante
 *
 */
public class HttpURLConnectionUtil { 
	
	private static final String NEWLINE = "\r\n"; // 换行符 
	private static final String BOUNDARY_PREFIX = "--";  
	private static final String BOUNDARY = "========7d4a6d158c9";  // 数据分隔线  

	
	public static void requestUpload(String url, @NonNull List<String> filePaths, String fileArgName, @NonNull Map<String, String> params) throws IOException {
		HttpURLConnection conn = buildFileUploadConnection(url);
		OutputStream out = new DataOutputStream(conn.getOutputStream()); 
		out.write(buildFileUploadParam(filePaths, fileArgName, params));  
		
		 // 定义BufferedReader输入流来读取URL的响应  
        BufferedReader reader = new BufferedReader(new InputStreamReader(  
                conn.getInputStream()));  
        String line = null;  
        while ((line = reader.readLine()) != null) {  
            System.out.println(line);  
        }  
        IOUtils.close(conn);
	}
	
	/**
	 * 构造参数体
	 * 
	 * @param filePaths
	 * @param fileArgName
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static byte[] buildFileUploadParam(@NonNull List<String> filePaths, String fileArgName, @NonNull Map<String, String> params) throws IOException {
		StringBuilder sb = new StringBuilder(); 
		sb.append(buildFromDataParam(params));
		sb.append(buildFileData(filePaths, fileArgName));
		sb.append(buildEndData());
		return sb.toString().getBytes();
	}

	private static HttpURLConnection buildFileUploadConnection(String reqUrl) throws IOException {
		URL url = new URL(reqUrl);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("POST"); 
        // 设置为POST情  
        conn.setRequestMethod("POST");  
        // 发送POST请求必须设置如下两行  
        conn.setDoOutput(true);  
        conn.setDoInput(true);
        conn.setUseCaches(false);  
        // 设置请求头参数  
        conn.setRequestProperty("connection", "Keep-Alive");  
        conn.setRequestProperty("Charsert", "UTF-8");  
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
        return conn;
	}
	
	private static String buildFromDataParam(@NonNull Map<String, String> params) {
		StringBuilder sb = new StringBuilder(); 
    	for (String key : params.keySet()) {
    		sb.append(BOUNDARY_PREFIX);  
            sb.append(BOUNDARY);  
            sb.append(NEWLINE); 
            sb.append("Content-Disposition: form-data; name=\"");
            sb.append(key);
            sb.append("\"");
            sb.append(NEWLINE); 
            sb.append(NEWLINE); 
            sb.append(new String(Base64.getEncoder().encode(params.get(key).getBytes())));
            sb.append(NEWLINE); 
		}
		
		return sb.toString();
	}
	
	private static String buildFileData(@NonNull List<String> filePaths, String fileArgName) throws IOException {
		StringBuilder sb = new StringBuilder(); 
		for (String fileName : filePaths) {
			File file = new File(fileName);  
            sb.append(BOUNDARY_PREFIX);  
            sb.append(BOUNDARY);  
            sb.append(NEWLINE); 
            sb.append("Content-Disposition: form-data;name=\"").append(fileArgName).append("\";filename=\"" + fileName  
                    + "\"" + NEWLINE);  
            sb.append("Content-Type: application/octet-stream"); 
            // 参数头设置完以后需要两个换行，然后才是参数内容  
            sb.append(NEWLINE);  
            sb.append(NEWLINE);  
            
            sb.append(new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray(file))));
            sb.append(NEWLINE); 
		}
		return sb.toString();
	}
	
	private static String buildEndData() {
		return NEWLINE + BOUNDARY_PREFIX + BOUNDARY + BOUNDARY_PREFIX + NEWLINE;
	}
	
	public static void main(String[] args) throws IOException {
		String url = "http://localhost:8101/uploadEncode";
		String fileArgName = "photos";
		List<String> filePaths = Arrays.asList("/Users/dante/Documents/Project/java-world/springboot/springboot-fileupload/src/main/resources/static/1.jpg", 
				"/Users/dante/Documents/Project/java-world/springboot/springboot-fileupload/src/main/resources/static/2.jpg");
		Map<String, String> params = new HashMap<>();
		params.put("type", "1");
		params.put("businessId", "U9982");
		
		HttpURLConnectionUtil.requestUpload(url, filePaths, fileArgName, params);
	}
	
}
