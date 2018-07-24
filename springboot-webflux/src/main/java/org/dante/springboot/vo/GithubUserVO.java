package org.dante.springboot.vo;

import java.util.Date;

import lombok.Data;

@Data
public class GithubUserVO {
	private String id;
	private String login;
	private String node_id;
	private String avatar_url;
	private String url;
	private Date created_at;
	private Date updated_at;
}
