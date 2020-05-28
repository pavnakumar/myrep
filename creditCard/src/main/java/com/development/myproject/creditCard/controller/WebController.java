package com.development.myproject.creditCard.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.development.myproject.creditCard.delegator.DelegateService;
import com.development.myproject.creditCard.dto.Account;
import com.development.myproject.creditCard.dto.Registration;
import com.development.myproject.creditCard.service.RegistrationService;

@Controller
public class WebController {
	
	@Resource
	private RegistrationService registrationService;
	
	@Resource
	private DelegateService delegateService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)

	public String homePage() {
		 
		return "home"; 
	}
	
	@GetMapping(value = "/registration")
	public String registration(Model model) {
		model.addAttribute("registration", registrationService.getRegistrationData());
		return "registrationForm"; 
	}
	
	
	@PostMapping(value = "/doRegister")
	public String doRegistration(Registration registration,  Model model) {
		model.addAttribute("account", registrationService.register(registration));
		return "registrationStatus"; 
	}
	
	@PostMapping(value = "/doTransaction")
	public String doTransaction(Account account,  Model model) {
		model.addAttribute("account", delegateService.doTransaction(account));
		return "registrationStatus"; 
	}
	

}
