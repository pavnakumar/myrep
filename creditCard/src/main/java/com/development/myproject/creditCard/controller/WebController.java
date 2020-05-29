package com.development.myproject.creditCard.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.development.myproject.creditCard.delegator.DelegateService;
import com.development.myproject.creditCard.dto.Account;
import com.development.myproject.creditCard.dto.Card;
import com.development.myproject.creditCard.dto.Registration;
import com.development.myproject.creditCard.repository.AccountRepository;
import com.development.myproject.creditCard.repository.CardRepository;
import com.development.myproject.creditCard.service.RegistrationService;

@Controller
public class WebController {
	
	@Resource
	private RegistrationService registrationService;
	
	@Resource
	private DelegateService delegateService;
	
	@Resource
	private CardRepository CardRepository; 
	
	@Resource
	private AccountRepository accountRepo;
	
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
		return "TransactionSucessFul"; 
	}
	
	@GetMapping(value = "/checkBalance")
	public String checkBalance() {
	
		return "checkBalance"; 
	}
	
	@GetMapping(value = "/getTransactionPage")
	public String getTransactionPage(@RequestParam String cardNo, Model model) {
		Card card =CardRepository.findCardByCardNo(cardNo);
		model.addAttribute("account", card.getAccount());
		return "registrationStatus"; 
	}
	
	
	

}
