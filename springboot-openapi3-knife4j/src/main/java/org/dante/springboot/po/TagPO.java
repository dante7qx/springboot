package org.dante.springboot.po;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Schema(description = "标签对象")
public class TagPO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(description = "标签Id")
	private long id;

	@Schema(description = "标签名称")
	private String name;

	@Hidden // 不显示在文档中
	@ManyToMany(mappedBy = "tags")
	private List<NotePO> notes;

	public TagPO(long id, String name) {
		this.id = id;
		this.name = name;
	}
}
