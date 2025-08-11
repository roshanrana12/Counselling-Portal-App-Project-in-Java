package com.springboot.webmvc.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "counsellor_tbl")
public class CounsellorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer counsellorId;
	private String name;
	private String email;
	private String gender;
	private String pwd;
	private Long phno;

	@OneToMany(mappedBy = "counsellor", cascade = CascadeType.ALL)
	private List<EnquiryEntity> enquiries;
}
