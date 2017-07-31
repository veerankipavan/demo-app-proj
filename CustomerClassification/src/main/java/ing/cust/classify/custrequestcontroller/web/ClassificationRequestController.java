package ing.cust.classify.custrequestcontroller.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ing.cust.classify.data.model.Customer;
import ing.cust.classify.processcustclassification.service.ProcessClassificationService;
import ing.cust.classify.service.model.CustomerClassificationInput;
import ing.cust.classify.service.model.CustomerClassificationOutput;

@Controller
public class ClassificationRequestController {
	@Autowired private ProcessClassificationService processClassificationService;
	
	
	private Map<String,String> getMonths() {
		Map<String,String> months = new LinkedHashMap<>();
		months.put("January"  ,"January" );
		months.put("February" ,"February" );
		months.put("March"    ,"March" );
		months.put("April"    ,"April" );
		months.put("May"      ,"May" );
		months.put("June"     ,"June" );
		months.put("July"     ,"July" );
		months.put("August"   ,"August" );
		months.put("September","September" );
		months.put("October" , "October");
		months.put("November", "November");
		months.put("December", "December");
		return months;
	}
	
	private Map<Integer,String> getYears() {
		Map<Integer,String> years = new HashMap<>();
		years.put(1, "2016");
		return years;
	}
	
	@ExceptionHandler(Exception.class)
	  public ModelAndView handleError(HttpServletRequest req, Exception ex) {
	    System.out.println("Request: " + req.getRequestURL() + " raised " + ex);

	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("Index");
	    return mav;
	  }
	
	@RequestMapping(value="/inputCustomerInfo", method = RequestMethod.GET)
    public ModelAndView getinputCustomerInfo(ModelMap model){
		model.addAttribute("custid",new Integer(0));
		model.addAttribute("months", getMonths());
		model.addAttribute("year", getYears());

		ModelAndView modelView= new ModelAndView("inputCustomerInfo", "command", new CustomerClassificationInput());
		return modelView;
    }
	
	@RequestMapping(value="/displayClassification", method = RequestMethod.POST)
	public String getdisplayClassification(@ModelAttribute("SpringWeb")CustomerClassificationInput request, ModelMap model) {
		String custClassification =  null;
		List<Customer> customer=processClassificationService.getDataFile();
		CustomerClassificationOutput response = processClassificationService.findMyCustomerbyIdandMonth(customer, Integer.parseInt(request.getCustid()), request.getTransMonth());
		
		custClassification = processClassificationService.classifytheCustomerAftermidDay(response,custClassification);
		System.out.println("Customer classified as :"+custClassification);
		
		custClassification =processClassificationService.classifyCustomerspent80PerctDeposit(response,custClassification);
		
		System.out.println("Customer classified as :"+custClassification);
		
		custClassification =processClassificationService.classifytheCustomerspentOverThousnad(response,custClassification);

		System.out.println("Customer classified as :"+custClassification);

		custClassification =processClassificationService.classifytheCustomerspent75PrcntofDeposit7Days(response,custClassification);

		System.out.println("Customer classified as :"+custClassification);
		
		custClassification =processClassificationService.classifytheCustomerBeforemidDay(response,custClassification);

		System.out.println("Customer classified as :"+custClassification);
		
		custClassification =processClassificationService.classifyCustomerspent25PerctDeposit(response,custClassification);

		System.out.println("Customer classified as :"+custClassification);

		List<String> items = Arrays.asList(custClassification.split("\\s*,\\s*"));
		if(items.contains(processClassificationService.Big_Spender) && items.contains(processClassificationService.Fast_Spender) ) {
			Collections.replaceAll(items,processClassificationService.Big_Spender,processClassificationService.Potential_Loan);
			Collections.replaceAll(items,processClassificationService.Fast_Spender,processClassificationService.Potential_Loan);
		}
			
		 TreeSet set =new TreeSet(items);

		String  evaluateClassification = new String();


		for (Iterator<String> it = set.iterator() ; it.hasNext() ; ){
			evaluateClassification += it.next();
		  if (it.hasNext()) {
			  evaluateClassification += ", "; 
		  }
		}
		
		response.setCustomerClassification(evaluateClassification);
		
		model.addAttribute("custtransactions", response.getCusttransactions());
	    model.addAttribute("balance", response.getBalance());
	    model.addAttribute("customerClassification", response.getCustomerClassification());
	      
		return "displayClassification";
	}
	

	
	
}
