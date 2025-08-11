package com.springboot.webmvc.service;

import java.util.List;



import com.springboot.webmvc.dto.DashboardResponseDTO;
import com.springboot.webmvc.dto.EnqFilterDTO;
import com.springboot.webmvc.dto.EnquiryDTO;

public interface EnquiryService {

	
	

	//show dashboard of logged in counsellor
	public DashboardResponseDTO getDashboardInfo(Integer counsellorId);

	//add enquiries
	public boolean addEnquiry(EnquiryDTO enqDTO, Integer counsellorId);
	
	//display all the enquiries of the logged in counsellor
	public List<EnquiryDTO> getEnquiries(Integer counsellorId);
	
	//display enquiries based on filters
	public List<EnquiryDTO> getEnquiries(EnqFilterDTO filterDTO,Integer counsellorId);
	
	//to edit the enquiry
	public EnquiryDTO getEnquiryById(Integer enqId);
	
	//to update the enquiry
	public boolean updateEnquiry(Integer enqId, EnquiryDTO enquiryDTO,Integer counsellorId); 
	
	//to delete enquiry
	public boolean deleteEnqById(Integer enqId);
}
