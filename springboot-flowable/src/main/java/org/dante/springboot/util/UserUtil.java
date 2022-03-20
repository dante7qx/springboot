package org.dante.springboot.util;

public class UserUtil {
	
	private static String currentUserId = "";
	
	public static String currentUser() {
		return currentUserId;
	}
	
	public static void setCurrentUser(String userId) {
		currentUserId = userId;
	}

}
