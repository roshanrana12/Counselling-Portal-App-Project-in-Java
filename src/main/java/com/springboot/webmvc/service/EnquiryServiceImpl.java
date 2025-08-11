package com.springboot.webmvc.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.springboot.webmvc.dto.DashboardResponseDTO;
import com.springboot.webmvc.dto.EnqFilterDTO;
import com.springboot.webmvc.dto.EnquiryDTO;
import com.springboot.webmvc.entity.CounsellorEntity;
import com.springboot.webmvc.entity.EnquiryEntity;
import com.springboot.webmvc.repository.CounsellorRepo;
import com.springboot.webmvc.repository.EnquiryRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private EnquiryRepo enquiryRepo;

	@Autowired
	private CounsellorRepo counsellorRepo;

	@Override
	public DashboardResponseDTO getDashboardInfo(Integer counsellorId) {

		DashboardResponseDTO dto = new DashboardResponseDTO();

		List<EnquiryEntity> enqList = enquiryRepo.findByCounsellorCounsellorId(counsellorId);

		int openCount = enqList.stream().filter(enq -> enq.getEnqStatus().equalsIgnoreCase("OPEN"))
				.collect(Collectors.toList()).size();

		int enrolledCount = enqList.stream().filter(enq -> enq.getEnqStatus().equalsIgnoreCase("ENROLLED"))
				.collect(Collectors.toList()).size();

		int lostCount = enqList.stream().filter(enq -> enq.getEnqStatus().equalsIgnoreCase("LOST"))
				.collect(Collectors.toList()).size();

		dto.setTotalEnqCount(enqList.size());
		dto.setOpenEnqCount(openCount);
		dto.setEnrolledEnqCount(enrolledCount);
		dto.setLostEnqCount(lostCount);

		return dto;
	}

	@Override
	public boolean addEnquiry(EnquiryDTO enqDTO, Integer counsellorId) {

		EnquiryEntity enqEntity = new EnquiryEntity();

		BeanUtils.copyProperties(enqDTO, enqEntity);

		Optional<CounsellorEntity> byId = counsellorRepo.findById(counsellorId);
		if (byId.isPresent()) {
			CounsellorEntity counsellor = byId.get();
			enqEntity.setCounsellor(counsellor);
		}

		EnquiryEntity savedEntity = enquiryRepo.save(enqEntity);
		return savedEntity.getEnqId() != null;
	}

	@Override
	public List<EnquiryDTO> getEnquiries(Integer counsellorId) {

		List<EnquiryDTO> enqsDtoList = new ArrayList<>();
		List<EnquiryEntity> enqList = enquiryRepo.findByCounsellorCounsellorId(counsellorId);

		//
		for (EnquiryEntity entity : enqList) {
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(entity, dto);

			enqsDtoList.add(dto);
		}

		return enqsDtoList;
	}

	@Override
	public List<EnquiryDTO> getEnquiries(EnqFilterDTO filterDTO, Integer counsellorId) {
		EnquiryEntity enqEntity = new EnquiryEntity();

		if (filterDTO.getClassMode() != null && !filterDTO.getClassMode().equals("")) {
			enqEntity.setClassMode(filterDTO.getClassMode());
		}

		if (filterDTO.getCourse() != null && !filterDTO.getCourse().equals("")) {
			enqEntity.setCourse(filterDTO.getCourse());
		}

		if (filterDTO.getEnqStatus() != null && !filterDTO.getEnqStatus().equals("")) {
			enqEntity.setEnqStatus(filterDTO.getEnqStatus());
		}

		CounsellorEntity counsellor = new CounsellorEntity();
		counsellor.setCounsellorId(counsellorId);
		enqEntity.setCounsellor(counsellor);

		Example<EnquiryEntity> of = Example.of(enqEntity);
		List<EnquiryEntity> enqList = enquiryRepo.findAll(of);

		List<EnquiryDTO> enqsDtoList = new ArrayList<>();
		for (EnquiryEntity enq : enqList) {
			EnquiryDTO enqDto = new EnquiryDTO();
			BeanUtils.copyProperties(enq, enqDto);
			enqsDtoList.add(enqDto);
		}
		return enqsDtoList;
	}

	@Override
	public EnquiryDTO getEnquiryById(Integer enqId) {
		Optional<EnquiryEntity> byId = enquiryRepo.findById(enqId);
		if (byId.isPresent()) {
			EnquiryEntity enquiryEntity = byId.get();
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(enquiryEntity, dto);

			return dto;
		}
		return null;

	}

	@Override
	public boolean updateEnquiry(Integer enqId, EnquiryDTO enquiryDTO, Integer counsellorId) {
		EnquiryDTO dto = getEnquiryById(enqId);
		dto.setEnqId(enquiryDTO.getEnqId());
		dto.setStuName(enquiryDTO.getStuName());
		dto.setStuPhno(enquiryDTO.getStuPhno());
		dto.setClassMode(enquiryDTO.getClassMode());
		dto.setCourse(enquiryDTO.getCourse());
		dto.setEnqStatus(enquiryDTO.getEnqStatus());

		EnquiryEntity entity = new EnquiryEntity();
		BeanUtils.copyProperties(dto, entity);
		Optional<CounsellorEntity> byId = counsellorRepo.findById(counsellorId);
		entity.setCounsellor(byId.get());
		EnquiryEntity saved = enquiryRepo.save(entity);
		return saved.getEnqId() != null;
	}

	@Override
	public boolean deleteEnqById(Integer id) {
		if (enquiryRepo.existsById(id)) {
			enquiryRepo.deleteById(id);
			return true;
		}
		return false;
	}

}
