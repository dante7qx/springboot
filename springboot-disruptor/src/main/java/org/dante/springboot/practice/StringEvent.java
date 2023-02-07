package org.dante.springboot.practice;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 事件的定义
 * 
 * 
 * @author dante
 *
 */
@Data
@ToString
@NoArgsConstructor
public class StringEvent {
	private String value;
}
