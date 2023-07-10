package org.dante.springboot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDKTests {

	/**
	 * Arrays.asList 返回的是 java.util.Arrays.ArrayList.ArrayList<T>(T[] array)，并不是 java.util.ArrayList。
	 * 
	 * 因此，其大小是固定不变的，无法添加新元素，add 会报 java.lang.UnsupportedOperationException
	 * 
	 * 可使用 List<String> list = new ArrayList<>(Arrays.asList(arr));
	 */
	@Test
	public void testArrays() {
		String[] arr = new String[] {"dante", "satan", "youna"};
		List<String> list = Arrays.asList(arr);
		list.add("Grovvy");
		log.info("{}", list);
	}
	
	@Test
	public void testArrays2() {
		String[] arr = new String[] {"dante", "satan", "youna"};
		List<String> list = new ArrayList<>(Arrays.asList(arr));
		list.add("Grovvy");
		log.info("{}", list);
	}
	
	@Test
	public void calculateOverflow() {
		int x1 = 2;
		int x2 = Integer.MAX_VALUE;
		
		Console.log(x1 * x2);
		Console.log(NumberUtil.mul(x1, x2));
	}
	
}
