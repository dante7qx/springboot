package org.dante.springboot.po;

import java.util.Date;
import java.util.List;

import com.mybatisflex.annotation.ColumnMask;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationOneToMany;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.mask.Masks;

import lombok.Data;

@Data
@Table("tb_account")
public class Account {

	@Id(keyType = KeyType.Auto)
	private Long id;
	@ColumnMask(Masks.CHINESE_NAME)
	private String userName;
	private Integer age;
	private Date birthday;
	
	@RelationOneToMany(selfField = "id", targetField = "accountId")
    private List<Book> books;
	
	//最大年龄
    private Integer maxAge;

    //平均年龄
    private Integer avgAge;

}
