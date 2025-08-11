package com.springboot.webmvc.service;

import com.springboot.webmvc.dto.CounsellorDTO;

public interface CounsellorService {
	//login
		public CounsellorDTO login(CounsellorDTO counsellorDTO);

		//check duplicate email
		public boolean uniqueEmailCheck(String email);

		//register counsellor
		public boolean register(CounsellorDTO counsellorDTO);
}
