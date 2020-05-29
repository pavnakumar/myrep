package com.development.myproject.creditCard.enums.util;

import java.util.Date;
import java.util.Random;

public enum AccountNumberGenerator {
	
	GENERATE_CREDIT_CARD_DETAILS(){
		@Override
		public String generatorCardNo(Long id, int length) {
			
			return super.generatorCardNo(id, length);
		}
		
	};
	
	public String accountGenerator(Long id,int length) {
		 String accountStr = id.toString();
		 String creditCardPrifix =  new Date().getTime()+"";
		 StringBuilder accountNo = new StringBuilder();
		 for(int i =0;i<length-accountStr.length();i++) {
			 if(i<creditCardPrifix.length()) {
				 accountNo.append(creditCardPrifix.charAt(i));
			 }else{
				 accountNo.append("0");
			 }
		 }
		 accountNo.append(accountStr);
		 return accountNo.toString(); 
	}
	
	public String generatorCardNo(Long id,int length) {
		
		 return generatorCardNo(id, length); 
	}
	
	public String generatorCvvNo(int length) {
		Random random = new Random();
		StringBuilder builder =  new StringBuilder("0");
		for (int i = 0; i < length; i++) {
			builder.append("9");
		}
		int id = random.nextInt(Integer.parseInt(builder.toString()));
		 String accountStr = id+"";
		 StringBuilder accountNo = new StringBuilder();
		 for(int i =0;i<length-accountStr.length();i++) {
			 accountNo.append("0");
		 }
		 accountNo.append(accountStr);
		 return accountNo.toString(); 
	}

}
