package org.dante.springboot.po;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class NotePO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Schema(description = "标题")
	private String title;

	@Schema(description = "n诶荣")
	private String body;

	@Hidden
	@ManyToMany
	private List<TagPO> tags;

}
