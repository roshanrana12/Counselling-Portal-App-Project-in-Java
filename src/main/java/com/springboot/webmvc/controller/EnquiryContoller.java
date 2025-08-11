package com.springboot.webmvc.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.webmvc.dto.EnqFilterDTO;
import com.springboot.webmvc.dto.EnquiryDTO;
import com.springboot.webmvc.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryContoller {

	@Autowired
	private EnquiryService enquiryService;

	@GetMapping("/enquiry-page")
	public String loadEnqPage(Model model) {

		model.addAttribute("enquiry", new EnquiryDTO());

		return "addEnquiry";
	}

	@PostMapping("/add-enquiry")
	public String addEnquiry(HttpServletRequest request, @ModelAttribute("enquiry") EnquiryDTO enquiryDTO,
			Model model) {
		// getting the counsellorId of loggedIn counsellor
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		boolean status = enquiryService.addEnquiry(enquiryDTO, counsellorId);
		if (status) {
			model.addAttribute("smsg", "Enquiry saved successfully");
		} else {
			model.addAttribute("emsg", "Failed to save the enquiry");
		}
		model.addAttribute("enquiry", new EnquiryDTO());
		return "addEnquiry";
	}

	@GetMapping("/edit-enquiry")
	public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model) {
		EnquiryDTO enquiryById = enquiryService.getEnquiryById(enqId);

		model.addAttribute("enquiry", enquiryById);

		return "updateEnquiry";
	}

	@PostMapping("/update-enquiry")
	public String updateEnq(@RequestParam("enqId") Integer enqId, @ModelAttribute("enquiry") EnquiryDTO enquiryDTO,
			HttpServletRequest request, Model model) {

		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		boolean updatedEnquiry = enquiryService.updateEnquiry(enqId, enquiryDTO, counsellorId);
		if (updatedEnquiry) {
			model.addAttribute("smsg", "Enquiry updated successfully");

		}
		model.addAttribute("enquiry", new EnquiryDTO());

		return "updateEnquiry";
	}

	@GetMapping("/delete-enquiry")
	public String deleteEnquiry(@RequestParam("enqId") Integer enqId, HttpServletRequest request, Model model) {
		EnquiryDTO enquiryById = enquiryService.getEnquiryById(enqId);
		model.addAttribute("enquiry", enquiryById);
		if (enquiryById != null) {

			boolean deleteEnqById = enquiryService.deleteEnqById(enqId);
			if (deleteEnqById) {
				model.addAttribute("deleted", "Enquiry deleted successfully");
			} else {
				model.addAttribute("notDeleted", "Failed to delete the enquiry");
			}

		} else {
			model.addAttribute("IdNotFound", "Enquiry does not exist");
		}

		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		List<EnquiryDTO> enquiriesLst = enquiryService.getEnquiries(counsellorId);
		model.addAttribute("enquiries", enquiriesLst);

		EnqFilterDTO filterDTO = new EnqFilterDTO();
		model.addAttribute("filterDTO", filterDTO);

		return "viewEnquiries";
	}

	@GetMapping("/view-enquiries")
	public String getEnquiries(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		List<EnquiryDTO> enquiriesLst = enquiryService.getEnquiries(counsellorId);
		model.addAttribute("enquiries", enquiriesLst);

		EnqFilterDTO filterDTO = new EnqFilterDTO();
		model.addAttribute("filterDTO", filterDTO);

		return "viewEnquiries";
	}

	@PostMapping("/filter-enquiries")
	public String filterEnquiries(HttpServletRequest request, @ModelAttribute("filterDTO") EnqFilterDTO filterDTO,
			Model model) {
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		List<EnquiryDTO> enquiriesLst = enquiryService.getEnquiries(filterDTO, counsellorId);
		model.addAttribute("enquiries", enquiriesLst);
		System.out.println(enquiriesLst);
		return "viewEnquiries";
		
		
		
	}
     
}
