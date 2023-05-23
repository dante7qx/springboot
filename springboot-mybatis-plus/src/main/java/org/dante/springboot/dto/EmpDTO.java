package org.dante.springboot.dto;

import lombok.Data;

@Data
public class EmpDTO {
	private Long id;
    private String name;
    private Integer age;
    private String email;

    private String city;
    private String address;
}
