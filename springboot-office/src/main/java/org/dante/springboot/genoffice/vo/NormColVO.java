package org.dante.springboot.genoffice.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NormColVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title = "绩效指标";

	private String level1;

	private String level2;

	private String level3;

	private String value;
	


}
