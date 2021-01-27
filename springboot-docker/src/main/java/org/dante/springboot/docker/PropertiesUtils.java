package org.dante.springboot.docker;

import java.io.IOException;
import java.util.MissingResourceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PropertiesUtils {

	private static Environment env;

	@Autowired
	protected void set(Environment env) throws IOException {
		PropertiesUtils.env = env;
	}

	/**
     * 根据key获取值
     * 
     * @param key
     * @return
    */
	public static String getString(String key) {
		try {
			return env.getProperty(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * 根据key获取值, 如何key不存在, 返回null
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key, String defaultValue) {
		try {
			String value = env.getProperty(key);
			if (StringUtils.hasLength(value)) {
				return value;
			}
			return defaultValue;
		} catch (MissingResourceException e) {
			return defaultValue;
		}
	}

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		return Integer.parseInt(env.getProperty(key));
	}

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(String key, int defaultValue) {
		String value = env.getProperty(key);
		if (StringUtils.hasLength(value)) {
			return Integer.parseInt(value);
		}
		return defaultValue;
	}

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		String value = env.getProperty(key);
		if (StringUtils.hasLength(value)) {
			return new Boolean(value);
		}
		return defaultValue;
	}
	
}
