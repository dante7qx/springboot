package org.dante.springboot.cache.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheConsts {

	private static final String PREFIX = "spt:";


	public static final String USER = PREFIX + "user";
	public static final String ORDER = PREFIX + "order";
	public static final String PRODUCT = PREFIX + "product";

	public static List<String> names() {
		return List.of(USER, ORDER, PRODUCT);
	}
	
}
