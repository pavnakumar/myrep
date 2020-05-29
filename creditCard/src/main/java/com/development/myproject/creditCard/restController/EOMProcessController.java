package com.development.myproject.creditCard.restController;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.development.myproject.creditCard.delegator.DelegateService;


@RestController("/job")
public class EOMProcessController {
	
	@Resource
	DelegateService delegateService ;

	@GetMapping(value = "/runEOMJob")
	public String run() {
		
		delegateService.runEOMJOB();
		return "Success"; 
		
	}

}
