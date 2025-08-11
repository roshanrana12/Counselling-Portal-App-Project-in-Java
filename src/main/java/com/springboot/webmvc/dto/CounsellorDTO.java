package com.springboot.webmvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CounsellorDTO {

	private Integer counsellorId;

	@NotEmpty(message = "*Name is mandatory*")
	private String name;
	@NotEmpty(message = "*Email is mandatory*")
	@Email(message = "*Enter valid email id*")
	private String email;
	@NotEmpty(message = "*Gender is mandatory*")
	private String gender;
	@NotNull(message = "*Contact is mandatory*")
	private Long phno;
	@NotEmpty(message = "*Password is mandatory*")
	private String pwd;

}
