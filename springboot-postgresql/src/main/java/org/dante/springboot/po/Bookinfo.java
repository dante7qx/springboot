package org.dante.springboot.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 书籍信息
 */
@Getter
@Setter
@TableName("t_bookinfo")
public class Bookinfo implements Serializable {

    private static final long serialVersionUID = 1L;
	
    @TableId(value = "id")
    private Integer id;
    
    private String bookNo;
    
    private String bookName;
	
}
