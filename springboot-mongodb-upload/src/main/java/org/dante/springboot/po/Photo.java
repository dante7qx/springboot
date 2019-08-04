package org.dante.springboot.po;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "photos")
public class Photo {
	@Id
	private String id;

	private String title;

	private Binary image;

	public Photo() {

	}

	public Photo(String title) {
		this.title = title;
	}
}
