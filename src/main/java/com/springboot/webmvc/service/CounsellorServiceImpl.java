package com.springboot.webmvc.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.webmvc.dto.CounsellorDTO;
import com.springboot.webmvc.entity.CounsellorEntity;
import com.springboot.webmvc.repository.CounsellorRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	@Autowired
	private CounsellorRepo counsellorRepo;

	@Override
	public CounsellorDTO login(CounsellorDTO counsellorDTO) {
		CounsellorEntity entity = counsellorRepo.findByEmailAndPwd(counsellorDTO.getEmail(), counsellorDTO.getPwd());
		if (entity != null) {

			// copy entity object data into dto object and return dto object
			CounsellorDTO dto = new CounsellorDTO();
			BeanUtils.copyProperties(entity, dto);

			return dto;
		}

		return null;
	}

	@Override
	public boolean uniqueEmailCheck(String email) {

		CounsellorEntity entity = counsellorRepo.findByEmail(email);

		return entity == null;
	}

	@Override
	public boolean register(CounsellorDTO counsellorDTO) {
		//copy the data from dto  to entity
		CounsellorEntity entity = new CounsellorEntity();
		BeanUtils.copyProperties(counsellorDTO, entity);

		CounsellorEntity savedEntity = counsellorRepo.save(entity);

		return savedEntity.getCounsellorId() != null;
	}

}
