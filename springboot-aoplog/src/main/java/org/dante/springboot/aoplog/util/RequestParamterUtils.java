package org.dante.springboot.aoplog.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 请求参数公共类
 * 
 * @author dante
 *
 */
public class RequestParamterUtils {

	private RequestParamterUtils() {
		throw new IllegalAccessError("RequestParamterUtils 工具类，不能实例化！");
	}
	
	/**
	 * 获取 request 中请求的内容
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestQueryString(HttpServletRequest request) {
		String parameter = null;
		try {
			String submitMehtod = request.getMethod();
			if (submitMehtod.equals("GET")) {
				String queryString = request.getQueryString();
				if(!StringUtils.isEmpty(queryString)) {
					parameter = new String(queryString.getBytes("iso-8859-1"), "utf-8");
				}
			} else {
				parameter = getRequestPostStr(request);
			}
		} catch (IOException e) {
		}
		return parameter;
	}

	/**
	 * 获取 request 中 json 字符串的内容
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestJsonString(HttpServletRequest request) throws IOException {
		String submitMehtod = request.getMethod();
		// GET
		if (submitMehtod.equals("GET")) {
			return new String(request.getQueryString().getBytes("iso-8859-1"), "utf-8").replaceAll("%22", "\"");
			// POST
		} else {
			return getRequestPostStr(request);
		}
	}

	/**
	 * 获取 post 请求的 byte[] 数组
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

	/**
	 * 获取 post 请求内容
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestPostStr(HttpServletRequest request) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		return new String(buffer, charEncoding);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String,Object> getRequestParams(HttpServletRequest request) {
		
		String contentType = request.getContentType();//获取请求的content-type
        if(contentType!=null && contentType.contains("multipart/form-data")){//文件上传请求 *特殊请求
	    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	        MultipartHttpServletRequest multiReq = multipartResolver.resolveMultipart(request);
	        request = multiReq;//将转化后的reuqest赋值到过滤链中的参数 *重要
        }
            
        Map<String,Object> map = new HashMap<String,Object>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }
}
