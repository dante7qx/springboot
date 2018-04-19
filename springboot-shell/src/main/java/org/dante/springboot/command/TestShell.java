package org.dante.springboot.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestShell {
	public static void main(String[] args) throws IOException, InterruptedException {
		String[] cmds = {"/bin/sh","/Users/dante/Desktop/aa.sh","sss","12121"};  
        Process pro = Runtime.getRuntime().exec(cmds);  
        pro.waitFor();  
        InputStream in = pro.getInputStream();  
        BufferedReader read = new BufferedReader(new InputStreamReader(in));  
        String line = null;  
        while((line = read.readLine())!=null){  
            System.out.println(line);  
        }  
	}
}
