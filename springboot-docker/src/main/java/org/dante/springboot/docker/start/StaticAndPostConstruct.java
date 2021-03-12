package org.dante.springboot.docker.start;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class StaticAndPostConstruct {
	// 静态代码块会在依赖注入后自动执行,并优先执行
	static {
		System.out.println("---静态代码块会在依赖注入后自动执行,并优先执行--");
	}

	/**
	 * @Postcontruct’在依赖注入完成后自动调用
	 */
	@PostConstruct
	public static void haha() {
		System.out.println("---@Postcontruct’在依赖注入完成后自动调用---");
	}
}
