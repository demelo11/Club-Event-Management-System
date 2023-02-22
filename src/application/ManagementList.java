package application;



public class ManagementList<T> {



	private class Node {
		
		 String username;
		 String password;
		 String name;
		 int memberID;
		 int managementID;
		 Node next;
		
		
	Node( String username,String password,String name, int memberID,int managementID, Node next) {

			this.username = username;
			this.password = password;
			this.name = name;
			this.memberID = memberID;
			this.managementID = managementID;
			this.next = next;
		}

	}

	private Node head;

	public ManagementList() {

		head = null;
	}

	public boolean isEmpty() {

		return head == null;
	}

	//method to insert value in list
	public void insert(String username,String password,String name, int memberID,int managementID) {

		Node prev = null;
		Node curr = head;
		
		
		
		
		
		while (curr != null ) {
			prev = curr;
			curr = curr.next;

		}
		//insert node between nodes
		if (prev != null) {

			prev.next = new Node(username, password,name, memberID, managementID, curr);
		} 
		//insert done at head
		else {

			head = new Node(username, password,name, memberID, managementID, curr);

		}

	}

	public void deleteAll() {
		head = null;

	}

	public void print() {
		
		Node curr = head;
		
		//check if there any value in the list
		if (curr == null) System.out.print("There is no data in the list.");
		
		

		//print values 
		while (curr != null) {

			System.out.println( curr.username+ "  " + curr.password+ "  " + curr.name+ "  " +  curr.memberID+ "  " +  curr.managementID);
			curr = curr.next;

		}

	}


		//method to find size of list
	public int findSize() {
		
		return findSize(head);
	}

	private int findSize(Node h) {

		//base case: list is empty
		if (h == null)return 0;
		
		//recursively add size
		return 1 + findSize(h.next);
	}

}
