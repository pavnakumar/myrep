package com.development.myproject.creditCard.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.development.myproject.creditCard.dto.Customer;
import com.development.myproject.creditCard.repository.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping("/getCust")
	public String getCustomer() {
		Customer customer = new Customer();
		customer.setName("pavan");
		customer.setAddress("test");
		customer.setPhone("9885131989");
		customerRepository.save(customer);
		
		return "pavan";
	}

}
