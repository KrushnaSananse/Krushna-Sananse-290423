package com.avisys.cim.Dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avisys.cim.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long>{	
	List<Customer> findAll();
	List<Customer> findByFirstNameContainingIgnoreCase(String firstName);
	List<Customer> findByLastNameContainingIgnoreCase(String lastName);
	Customer findByMobileNumber(String mobileNumber);	
	Customer findByMobileNumberContaining(String mobileNumber);
}