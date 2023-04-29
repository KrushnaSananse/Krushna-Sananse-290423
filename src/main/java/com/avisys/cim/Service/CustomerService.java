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

	public Customer findByMobileNumber(String mobileNumber) {
		Customer finalCustomer = null;
		Customer customer = customerdao.findByMobileNumber(mobileNumber);
		if(customer != null ) {
			finalCustomer = customer;
		} else {
			customer = customerdao.findByMobileNumberContaining(mobileNumber);
			if(customer != null) {
				String mobileNumberList[] = customer.getMobileNumber().split(",");
				for(int count = 0;count<mobileNumberList.length ; count++) {
					if(mobileNumberList[count].equalsIgnoreCase(mobileNumber)) {
						finalCustomer = customer;
						break;
					}
				}
			}
		}
		return finalCustomer;
	}

	public boolean isMobileNumberPresent(String mobileNumber) {
		boolean isPresent = false;
		Customer customer = customerdao.findByMobileNumber(mobileNumber);
		if(customer != null ) {
			isPresent = true;
		} else {
			String mobileNumberList[] = mobileNumber.split(",");
			for(int count = 0;count<mobileNumberList.length ; count++) {
				customer = customerdao.findByMobileNumberContaining(mobileNumberList[count]);
				if(customer != null) {
					isPresent = true;
					break;
				}
			}
		}
		return isPresent;
	}

	public Customer addCustomer(Customer customer) {
		return customerdao.save(customer);
	}
}