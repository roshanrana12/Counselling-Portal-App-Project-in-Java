package com.springboot.webmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.webmvc.entity.CounsellorEntity;

public interface CounsellorRepo extends JpaRepository<CounsellorEntity, Integer> {
  public CounsellorEntity findByEmailAndPwd(String email,String pwd);
  public CounsellorEntity findByEmail(String email);
}
