package com.springboot.webmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.webmvc.entity.EnquiryEntity;

public interface EnquiryRepo extends JpaRepository<EnquiryEntity, Integer> {
      public List<EnquiryEntity> findByCounsellorCounsellorId(Integer counsellorId);
}
