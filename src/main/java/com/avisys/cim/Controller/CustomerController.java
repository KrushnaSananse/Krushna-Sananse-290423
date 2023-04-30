package com.avisys.cim.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.cim.Customer;
import com.avisys.cim.Service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	Object customerInfo = null;
	@Autowired
	private CustomerService customerService;

	@SuppressWarnings("unchecked")
	@GetMapping("/getCustomerInformation/{filter}")
	/**
	 * This is the rest api call which is giving us the information of the customers based on the filter provided
	 * 
	 * @param filter 	[all / firstName=value / lastName=value / mobileNumber=value]
	 * 	
	 * @return		Object of customer info
	 */
	public Object getCustomerInformation(@PathVariable String filter) {
		if(filter.contains("all")) {
			customerInfo = customerService.findAll();
		}
		else {
			if(filter.split("=").length == 1) {
				return "Please Specify the filter key=value properly";
			}
			String paramKey = filter.split("=")[0];
			String paramValue = filter.split("=")[1];

			if(paramKey.equalsIgnoreCase("firstName")) {
				customerInfo = customerService.findByFirstName(paramValue);
			}
			else if(paramKey.equals("lastName")){
				customerInfo = customerService.findByLastName(paramValue);
			}
			else if(paramKey.equals("mobileNumber")){
				customerInfo = customerService.findByMobileNumber(paramValue);
			}	
		}
		if(customerInfo instanceof Customer) {
			if(customerInfo==null) {
				return "No record found with given criteria, Filter should be in [all / firstName=value / lastName=value / mobileNumber=value]";
			}
		}else {
			if(customerInfo == null || ((List<Customer>) customerInfo).size()==0) {
				return "No record found with given criteria, Filter should be in [all / firstName=value / lastName=value / mobileNumber=value]";
			}
		}

		return customerInfo;
	}

	/**
	 * This is RestAPI to add the customer entry into the database
	 * 
	 * @param customer 		Customer Information
	 * 
	 * @return				Object of Response Entity
	 */
	@PostMapping("/addCustomer")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
		boolean isCustomerPresent = customerService.isMobileNumberPresent(customer.getMobileNumber());
		if(isCustomerPresent) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create Customer. Mobile number already present.");
		}else {
			customerService.addCustomer(customer);
			return ResponseEntity.ok(customer);
		}
	}
}
