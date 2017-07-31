package ing.cust.classify.processcustclassification.serviceimpl;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ing.cust.classify.data.model.Customer;
import ing.cust.classify.processcustclassification.service.ProcessClassificationService;
import ing.cust.classify.service.model.CustomerClassificationOutput;

@Service
public class ProcessClassificationServiceImpl implements ProcessClassificationService {
	final DateFormat opformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public List<Customer> getDataFile() {
		BufferedReader buffreader = null;

		List<Customer> custList = new ArrayList<Customer>();
		try {

			//InputStream ipstream = CustomerDataService.class.getResourceAsStream("/SampleCustCollection.txt");
			InputStream ipstream = ProcessClassificationService.class.getResourceAsStream("/TeechChallengeData.txt");
			System.out.println(ipstream != null);
			buffreader = new BufferedReader(new InputStreamReader(ipstream));

			Customer cust = null;
			String line = "";
			String[] arrs = null;
			DateFormat format = null;

			Date date = null;
			buffreader.readLine();
			while ((line = buffreader.readLine()) != null) {
				arrs = line.split(",");
				format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
				try {
					date = format.parse(arrs[1]);
				}
				catch(Exception e){
					System.out.println("Exception for : "+arrs[0]+"  "+arrs[1]+e.toString());
				}
				cust = new Customer(Integer.valueOf(arrs[0]), date, new Double(Double.parseDouble(arrs[2])), arrs[3]);
				custList.add(cust);
			}
			buffreader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return custList;
	}

	@Override
	public CustomerClassificationOutput findMyCustomerbyIdandMonth(List<Customer> customers, Integer id, String month) {

		CustomerClassificationOutput response = new CustomerClassificationOutput();
		Double balance = 0.00;
		
		List<Customer> customersbyIDMonth = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if (id.equals(customer.getCustomerID()) && month.equals(monthFormat.format(customer.getTranDate()))) {
				customersbyIDMonth.add(customer);
				balance+=customer.getTranAmt();
			}
		}
		
		response.setCusttransactions(customersbyIDMonth);
		for(Customer customer :customersbyIDMonth) {
			System.out.println("CustomerID :"+customer.getCustomerID()+" Date :"+customer.getTranDate()+" Amount: "+customer.getTranAmt());

		}
		
		response.setBalance(new DecimalFormat("#.##").format(balance));
		
		return response;

	
	}

	@Override
	public String classifytheCustomerAftermidDay(CustomerClassificationOutput response, String classification) {
		
		int totalnumberofTrns = response.getCusttransactions().size();
		int afternoonPersonCntr = 0;
		DateFormat outputformat = null;
		String output = null;
		
			for (Customer customer : response.getCusttransactions()) {
				outputformat = new SimpleDateFormat("HH");			
				output = outputformat.format(customer.getTranDate());
				if(customer.getTranAmt()<=0) {
					if (Integer.parseInt(output) >= 12)
						afternoonPersonCntr += 1;
				}
			}
			System.out.println("Total Trans :" + totalnumberofTrns + " 50%  of Trans After Midday: " + afternoonPersonCntr);
	
		if (afternoonPersonCntr > totalnumberofTrns / 2)
			return Afternoon_Person;
		else
			return Un_Classified;
	}
	
	@Override
	public String classifyCustomerspent80PerctDeposit(CustomerClassificationOutput response, String classification) {
		double totaldeposits= 0.00;
		double totalSpends= 0.00;
		
			for (Customer customer:response.getCusttransactions()) {
				if(customer.getTranAmt()>0)
				totaldeposits+=customer.getTranAmt();
				else
				totalSpends+=customer.getTranAmt();
		    }		
		System.out.println("Total deposits :"+totaldeposits+" 80%  of total deposits: "+eighty_percentage*totaldeposits+" Total Spends :"+totalSpends);
		
			if((eighty_percentage*totaldeposits)+totalSpends<0) {
				if(!classification.equalsIgnoreCase(Un_Classified))
						return classification+","+Big_Spender;

					else
						return Big_Spender;	
					
			}else {
				if(!classification.equalsIgnoreCase(Un_Classified))
					return classification;
				else
					return Un_Classified;
			}
	}

	@Override
	public String classifytheCustomerspentOverThousnad(CustomerClassificationOutput response, String classification) {
		int wdoverthousnad= 0;
	
			for (Customer customer:response.getCusttransactions()) {
				if(customer.getTranAmt()<-1000)
					wdoverthousnad+=1;
		    }
			
			System.out.println("Total withdrawals over Thousand : "+wdoverthousnad);
			
			if(wdoverthousnad>0) {
				if(!classification.equalsIgnoreCase(Un_Classified)) 
					return classification+","+Big_Ticket_Spender;
				else
				return Big_Ticket_Spender;
			}
			else {
				if(!classification.equalsIgnoreCase(Un_Classified))
					return classification;
				else
				return Un_Classified;	
			}
	
	}

	@Override
	public String classifytheCustomerspent75PrcntofDeposit7Days(CustomerClassificationOutput response, String classification) {
		
		
		List<Customer> cust7daysSorting =  response.getCusttransactions();
		double sevenDaysDeposit =0.00;
		double sevenDaysWithDrawals =0.00;
		Collections.sort(cust7daysSorting, new Comparator<Customer>() {
			public int compare(Customer m1, Customer m2) {
		        return m1.getTranDate().compareTo(m2.getTranDate());
		    }
		});
		
		System.out.println("After Sorting");
		Customer customerFirstDepositdate=null;
		for (Customer customer : cust7daysSorting) {
			if(customer.getTranAmt()>0) {
				customerFirstDepositdate=customer;
				System.out.println("Date :"+opformat.format(customer.getTranDate())+" Amount: "+customer.getTranAmt());
				break;
			}
			
		}
		
		Date plsSevenDate = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(customerFirstDepositdate.getTranDate()); 
		c.add(Calendar.DATE, 6);
		plsSevenDate = c.getTime();
		
		System.out.println("Date Incremented by 6:"+opformat.format(plsSevenDate));
		
		for(Customer cust: cust7daysSorting) {
			if(cust.getTranDate().before(plsSevenDate)) {
				if(cust.getTranAmt()>0)
					sevenDaysDeposit+=cust.getTranAmt();
				else
					sevenDaysWithDrawals+=cust.getTranAmt();

				System.out.println("Date :"+opformat.format(cust.getTranDate())+" Amount: "+cust.getTranAmt());

			}
		}
		
		
		System.out.println("Total deposits :"+sevenDaysDeposit+" 75%  of total deposits: "+seventyfive_percentage*sevenDaysDeposit+" Total witdrawls :"+sevenDaysWithDrawals);
		
		if((seventyfive_percentage*sevenDaysDeposit)+sevenDaysWithDrawals<0) {
			if(!classification.equalsIgnoreCase(Un_Classified))
				return classification+","+Fast_Spender;
			else
				return Fast_Spender;	
		}
		else {
			if(!classification.equalsIgnoreCase(Un_Classified))
				return classification;
			else
			return Un_Classified;

		}
	}

	@Override
	public String classifytheCustomerBeforemidDay(CustomerClassificationOutput response, String classification) {
		int totalnumberofTrns = response.getCusttransactions().size();
		int morningPersonCntr = 0;
		DateFormat outputformat = null;
		String output = null;
		
			for (Customer customer : response.getCusttransactions()) {
				outputformat = new SimpleDateFormat("HH");
				output = outputformat.format(customer.getTranDate());
				if(customer.getTranAmt() <=0) {
				if (Integer.parseInt(output) < 12)
					morningPersonCntr += 1;
				}
			}

		System.out.println("Total Trans :" + totalnumberofTrns + " 50%  of Trans Before Midday: " + morningPersonCntr);

			if (morningPersonCntr > totalnumberofTrns / 2) {
				if(!classification.equalsIgnoreCase(Un_Classified))
					return classification+","+Morning_Person;
				else
				return Morning_Person;
			}
			else {
				if(!classification.equalsIgnoreCase(Un_Classified))
					return classification;
				else
				return Un_Classified;
			}
	}

	@Override
	public String classifyCustomerspent25PerctDeposit(CustomerClassificationOutput response, String classification) {
		double totaldeposits= 0.00;
		double totalSpends= 0.00;
		
			for (Customer customer:response.getCusttransactions()) {
				if(customer.getTranAmt()>0)
				totaldeposits+=customer.getTranAmt();
				else
				totalSpends+=customer.getTranAmt();
		    }
		
		System.out.println("Total deposits :"+totaldeposits+" 25%  of total deposits: "+twentyfive_percentage*totaldeposits+" Total Spends :"+totalSpends);
		
			if((twentyfive_percentage*totaldeposits)+totalSpends>0) {
				if(!classification.equalsIgnoreCase(Un_Classified))
				return classification+","+Potential_Saver;	
				else
				return Potential_Saver;
			}
			else
				if(!classification.equalsIgnoreCase(Un_Classified))
					return classification;
				else
				return Un_Classified;
	}

}
