package com.springboot.webmvc.dto;

import lombok.Data;

@Data
public class DashboardResponseDTO {

	private Integer totalEnqCount;
	private Integer openEnqCount;
	private Integer enrolledEnqCount;
	private Integer lostEnqCount;

}
