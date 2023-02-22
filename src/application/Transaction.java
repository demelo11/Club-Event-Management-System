package application;

import java.util.Date;

public class Transaction {

	
	private Date date;
	private String memberName;
	private String reason;
	private int amount;
	private int transactionID;
	
	public Transaction( ) {
	
		
	}
	
	public Transaction(Date date, String memberName, String reason, int amount, int transactionID ) {
		this.date= date;
		this.memberName = memberName;
		this.reason = reason;
		this.amount = amount;
		this.transactionID = transactionID;
		
	}
	
	
	public int getAmount() {
		return amount;
	}
	
	public int getID() {
		return transactionID;
	}
	
	public String getName() {
		return memberName;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String print() {
		return transactionID+": \nAmount:"+amount+"\nDate: "+date+"\nMember Name: "+memberName+"\nReason: "+reason;
	}
	
	public String getReason() {
		return reason;
	}
}
