package com.springboot.webmvc.dto;

import lombok.Data;

@Data
public class EnquiryDTO {
	
	private Integer enqId;
	private String stuName;
	private String stuPhno;
	private String classMode;
	private String course;
	private String enqStatus;
}
