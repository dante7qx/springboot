package org.dante.springboot.vo;

import lombok.Data;

@Data
public class MemoryInfoDTO {
	private Long total; //内存总容量
    private Long used;  //内存使用大小
    private Long free;  //内存空闲大小
    private Long buffer;//缓冲内存大小
    private Long cache; //缓存内存大小
    private Double memoryUsage; //内存使用率
	
}
