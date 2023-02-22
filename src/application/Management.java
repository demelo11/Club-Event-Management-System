package application;

import java.util.Scanner;

public class Management{
	private String username;
	private String password;
	private String name;
	private int memberID;
	private int managementID;
	
	public Management() {

	}
	
	public Management(String username, String password, String name, int memberID, int managementID) {
		super();
		this.username = username.toLowerCase();
		this.password = password;
		this.name = name;
		this.memberID = memberID;
		this.managementID = managementID;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public int getMemberID() {
		return memberID;
	}

	public int getManagementID() {
		return managementID;
	}

	public boolean login(String username, String password) {
		return (this.username.equals(username.toLowerCase())) && (this.password.equals(password));
	}
	
	public void logout() {
		//not sure what is supposed to be in this method
	}
	
	public void addMember(Members student) {
		//depending on if we can figure out the database the rest of the info will be added
		//to the database or to a linked list
	}
	
	public void removeMember(Members student) {
		//depending on if we can figure out the database the rest of the info will be added
		//to the database or to a linked list
	}
	
	public Members search(int memberID) {
		//depending on if we can figure out the database the rest of the info will be added
		//to the database or to a linked list
		
		//if database then query to search for Meber and return
		//or search linkedlist and return Member
		
		return null;
	}
	
	public void createEvent(Event newEvent) {
		//depending on if we can figure out the database the rest of the info will be added
		//to the database or to a linked list
		
		//will ask if they want event to be advertised
		Scanner in=new Scanner(System.in);
		System.out.print("Do you want to advertise this event?(Y/N) ");
		if(in.nextLine().toUpperCase().equals("Y")) {
			advertiseEvent(newEvent);
		}
	}

	private void advertiseEvent(Event event) {
		//this method advertises the event, still not sure how it works?
	}
	
	public void removeEvent(int eventID) {
		//use the event ID to search for the event and delete it
		
		//depending on if we can figure out the database the rest of the info will be added
		//to the database or to a linked list
	}
	
	public double checkFinance(Finance f) {
		return f.getCurrentAmount();
	}
	
	public void modifyFinance(Finance f) {
		//I can't remember why we have this?
		//Is it so the management can directly change the Finance amount?
		//If so then we'll probably end up making Finance an internal class for Management?
	}

}
