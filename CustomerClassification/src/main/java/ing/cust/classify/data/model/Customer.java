package ing.cust.classify.data.model;

import java.util.Date;

public class Customer {

	private int customerID;
	private Date tranDate;
	private Double tranAmt;
	private String tranDesc;
	
	
	public Customer(int customerID, Date tranDate, Double tranAmt, String tranDesc) {
		super();
		this.customerID = customerID;
		this.tranDate = tranDate;
		this.tranAmt = tranAmt;
		this.tranDesc = tranDesc;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public Date getTranDate() {
		return tranDate;
	}
	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}
	public Double getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(Double tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getTranDesc() {
		return tranDesc;
	}
	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}
	
	@Override
    public String toString() {
        return "Customer [id=" + customerID + ", tranDate=" + tranDate + ", tranAmt="
                + tranAmt + ", tranDesc=" + tranDesc + "]";
    }
}
