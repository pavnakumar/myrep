package com.development.myproject.creditCard.delegator;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.development.myproject.creditCard.dto.Card;
import com.development.myproject.creditCard.repository.CardRepository;
import com.development.myproject.creditCard.service.UIResponseDto;
import com.development.myproject.creditCard.util.CommonUtil;

@Component
public class CardDelegator {
	
	@Resource
	private CardRepository cardRepository;
	
	public UIResponseDto getBalanceByCardNo(String cardNo){
		 UIResponseDto dto = null;
		Card card = cardRepository.findCardByCardNo(cardNo);
		if(CommonUtil.hasAvalue(card)) {
			Map<String,Object> detailsmap = new HashMap();
			detailsmap.put("cardNo", card.getCardNo());
			detailsmap.put("cardLimit", card.getCreditLimit());
			detailsmap.put("balance", card.getBalance());
			detailsmap.put("cashLimit", card.getCashLimit());
			detailsmap.put("cashBalance", card.getCashBalance());
			dto = new UIResponseDto(true, null);
			dto.setResponse(detailsmap);
		}else {
			dto = new UIResponseDto(false, "No card details Found for card no " + cardNo);
			
		}
		return dto;
		
	}

}
