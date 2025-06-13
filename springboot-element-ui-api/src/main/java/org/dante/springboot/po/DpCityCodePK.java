package org.dante.springboot.po;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class DpCityCodePK implements Serializable {

	private static final long serialVersionUID = 606495016657188469L;

	private String code;

	private String ch;
	
	private String en;
}
