package org.dante.springboot.intro;

import lombok.Data;

@Data
public class Post {

	private String id;
	private String title;
	private String text;
	private String category;
	private String authorId;

}
