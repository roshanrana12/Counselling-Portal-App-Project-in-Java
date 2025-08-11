package com.springboot.webmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.springboot.webmvc.dto.CounsellorDTO;
import com.springboot.webmvc.dto.DashboardResponseDTO;
import com.springboot.webmvc.service.CounsellorService;
import com.springboot.webmvc.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CounsellorController {

	@Autowired
	private CounsellorService counsellorService;

	@Autowired
	private EnquiryService enquiryService;

	@GetMapping("/")
	public String index(Model model) {
		return "index";

	}

	@GetMapping("/login-form")
	public String loadLoginPage(Model model) {

		model.addAttribute("counsellor", new CounsellorDTO());
		return "login";

	}

	@PostMapping("/login")
	public String handleLogin(HttpServletRequest request, @ModelAttribute("counsellor") CounsellorDTO counsellorDto,
			Model model) {

		CounsellorDTO counsellor = counsellorService.login(counsellorDto);
		if (counsellor == null) {
			model.addAttribute("errorMessage", "Invalid Credentials.");
			model.addAttribute("counsellor", new CounsellorDTO());
			return "login"; // Return to login page with an error
		} else {
			Integer counsellorId = counsellor.getCounsellorId();
			// store counsellorId into HttpServletRequest session obj
			HttpSession session = request.getSession(true);
			session.setAttribute("counsellorId", counsellorId);

			DashboardResponseDTO dashboardDto = enquiryService.getDashboardInfo(counsellorId);
			model.addAttribute("dashbordDto", dashboardDto);
			return "dashboard"; // Redirect to dashboard page
		}
	}

	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		DashboardResponseDTO dashboardDto = enquiryService.getDashboardInfo(counsellorId);
		model.addAttribute("dashbordDto", dashboardDto);
		return "dashboard"; // Redirect to dashboard page
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession(false);
		session.invalidate();
		model.addAttribute("counsellor", new CounsellorDTO());
		return "login";

	}

	@GetMapping("/register-form")
	public String loadRegisterPage(Model model) {
		model.addAttribute("counsellor", new CounsellorDTO());
		return "register";

	}

	@PostMapping("/register")
	public String registerPage(@Valid @ModelAttribute("counsellor") CounsellorDTO counsellorDTO,
			BindingResult bindingResult, Model model) {

		/// Validate the inputs first
		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "Invalid input format.");
			return "register"; // Return the login page with errors
		}

		boolean unique = counsellorService.uniqueEmailCheck(counsellorDTO.getEmail());
		if (unique) {
			boolean registered = counsellorService.register(counsellorDTO);
			if (registered) {
				model.addAttribute("smsg", "Registration Success");
				model.addAttribute("counsellor", new CounsellorDTO());
				
			} else {
				model.addAttribute("emsg", "Registration Failed");
			}
		} else {
			model.addAttribute("emsg", "Email already exists !");

		}
		return "register";

	}

}
