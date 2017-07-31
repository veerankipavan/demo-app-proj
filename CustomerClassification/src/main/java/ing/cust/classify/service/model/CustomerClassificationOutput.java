package ing.cust.classify.service.model;

import java.util.ArrayList;
import java.util.List;

import ing.cust.classify.data.model.Customer;

public class CustomerClassificationOutput {
	
	private String customerClassification;
	private String balance;
	private List<Customer> custtransactions = new ArrayList<>();
	
	public String getCustomerClassification() {
		return customerClassification;
	}
	public void setCustomerClassification(String customerClassification) {
		this.customerClassification = customerClassification;
	}
	
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public List<Customer> getCusttransactions() {
		return custtransactions;
	}
	public void setCusttransactions(List<Customer> custtransactions) {
		this.custtransactions = custtransactions;
	};

	

	
}
