package org.dante.springboot.demo;

import java.io.IOException;

import lombok.SneakyThrows;

public class SneakyThrowsDemo {
	
	@SneakyThrows
	public void a1() {
		b();
	}
	
	public void a2() throws IOException {
		b();
	}
	
	public void a3() {
		try {
			b();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void b() throws IOException {
		
	}
	
}
