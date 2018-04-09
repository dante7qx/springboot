package org.dante.springboot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageVO {
	private String msgId;
	private String content;
}
