package com.development.myproject.creditCard.restController;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.development.myproject.creditCard.delegator.CardDelegator;
import com.development.myproject.creditCard.service.UIResponseDto;




@RestController
@RequestMapping("/card")
public class CardController {
	@Resource
	CardDelegator cardDelegator;
	
	
	@GetMapping("/balance/{cardNo}")
	public UIResponseDto getCustomer(@PathVariable String cardNo) {
		
		
		return cardDelegator.getBalanceByCardNo(cardNo);
	}

}
