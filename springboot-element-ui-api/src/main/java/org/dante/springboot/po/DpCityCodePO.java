package org.dante.springboot.po;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "dp_city_code")
@NoArgsConstructor
@AllArgsConstructor
public class DpCityCodePO implements Serializable {

	private static final long serialVersionUID = 674010993826076166L;
	
	@EmbeddedId
	private DpCityCodePK pk;

	private String extra;
	
}
