package ing.cust.classify.service.model;

public class CustomerClassificationInput {
	private String custid;
	private String transMonth;
	private String year;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getTransMonth() {
		return transMonth;
	}
	public void setTransMonth(String transMonth) {
		this.transMonth = transMonth;
	}
}
