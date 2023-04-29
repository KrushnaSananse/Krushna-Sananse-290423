package com.avisys.cim.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		if(customerInfo==null || ((List<Customer>) customerInfo).size()==0) {
			return "No record found with given criteria, Filter should be in [all / firstName=value / lastName=value / mobileNumber=value]";
		}
		return customerInfo;
	}
}
