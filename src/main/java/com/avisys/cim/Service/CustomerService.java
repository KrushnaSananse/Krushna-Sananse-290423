package com.avisys.cim.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisys.cim.Customer;
import com.avisys.cim.Dao.CustomerDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerDao customerdao;
	  
	public List<Customer> findAll() {
	    return customerdao.findAll();
	}
	
	public List<Customer> findByFirstName(String firstName) {
	    return customerdao.findByFirstNameContainingIgnoreCase(firstName);
	}
	
	public List<Customer> findByLastName(String lastName) {
	    return customerdao.findByLastNameContainingIgnoreCase(lastName);
	}
	
	public List<Customer> findByMobileNumber(String mobileNumber) {
	    return customerdao.findByMobileNumber(mobileNumber);
	}
	
	public Customer addCustomer(Customer customer) {
		return customerdao.save(customer);
	}
}