package ing.cust.classify.processcustclassification.service;

import java.text.SimpleDateFormat;
import java.util.List;

import ing.cust.classify.data.model.Customer;
import ing.cust.classify.service.model.CustomerClassificationOutput;

public interface ProcessClassificationService {

	static SimpleDateFormat monthFormat= new SimpleDateFormat("MMMM");
	static final String Afternoon_Person= "Afternoon Person";
	static final String Big_Spender= "Big Spender";
	static final String Big_Ticket_Spender= "Big Ticket Spender";
	static final String Un_Classified= "Un Classified";
	static final String Fast_Spender= "Fast Spender";
	static final String Morning_Person= "Morning Person";
	static final String Potential_Saver= "Potential Saver";
	static final String Potential_Loan= "Potential Loan";
	static double eighty_percentage = 0.8;
	static double seventyfive_percentage = 0.75;
	static double twentyfive_percentage = 0.25;
	
	List<Customer> getDataFile();
	CustomerClassificationOutput findMyCustomerbyIdandMonth(List<Customer> customers, Integer id, String month);
	String classifytheCustomerAftermidDay(CustomerClassificationOutput response,String classification);
	String classifyCustomerspent80PerctDeposit(CustomerClassificationOutput response,String classification);
	String classifytheCustomerspentOverThousnad(CustomerClassificationOutput response,String classification);
	String classifytheCustomerspent75PrcntofDeposit7Days(CustomerClassificationOutput response,String classification);
	String classifytheCustomerBeforemidDay(CustomerClassificationOutput response,String classification);
	String classifyCustomerspent25PerctDeposit(CustomerClassificationOutput response,String classification);


	
	
}
