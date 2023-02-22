package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Finance{

		@FXML
	    private Button mainmenu;
		@FXML
	    private Button funds;
		@FXML
	    private Button ok;
		@FXML
		private Button submit;
		@FXML
	    private Button deposit;
		@FXML
	    private Button withdraw;
		@FXML
	    private Button wSub;
		@FXML
	    private Button cancel;
		@FXML
		private TextField dAmount;
		@FXML
		private TextField dReason;
		@FXML
		private TextField wAmount;
		@FXML
		private TextField wReason;
		@FXML
		private Label tAmount;
		@FXML
		private Label warning;
		
		private double totalAmount;
		private Date currentDate;
		private static String user="";
		private  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		
		public Finance() {
		
		}
		
		public Finance(int totalAmount) {
			this.totalAmount = totalAmount;
			this.currentDate=new Date();
		}

		public double getCurrentAmount() {
			return totalAmount;
		}
		
		public Date getDate() {
			return currentDate;
		
			
		}
		
		public static void setUser(String us) {
			user=us;
		}
		
		public void deposit(Transaction deposit) {
			totalAmount += deposit.getAmount();
			
			//depending on if we can figure out the database the rest of the info will be added
			//to the database or to a linked list
		}
		public void withdraw(Transaction withdraw) {
			
			totalAmount -= withdraw.getAmount();
			
			//depending on if we can figure out the database the rest of the info will be added
			//to the database or to a linked list
		}
	

	    public void mainMenu(ActionEvent event) throws IOException {
	        Main m = new Main();
	        m.changeScene("afterLogin.fxml");
	
	    }
	        
	     public void checkFunds(ActionEvent event) throws IOException {
		        Main m = new Main();
		        m.changeScene("DisplayFunds.fxml"); 
		  }
	     
	        public void depositAmount(ActionEvent event) throws IOException {
		        Main m = new Main();
		        m.changeScene("Deposit.fxml");
		
		  }
	        public void withdrawAmount(ActionEvent event) throws IOException {
		        Main m = new Main();
		        m.changeScene("Withdraw.fxml");
		        
		
		  }
	        
	        public void doneChecking(ActionEvent event) throws IOException {
		        Main m = new Main();
		        m.changeScene("Finance.fxml");
		
		  }
	      
	      public void deposit(ActionEvent event) throws IOException{
		        dep();
		  }
	        public void withdraw(ActionEvent event) throws IOException {
		        wit();
		        
		  }

	        public void dEasy(KeyEvent event) throws IOException {
	        	if(event.getCode() == KeyCode.ENTER) {
	        		dep();
	        	}
	        }
	        
	        

			public void wEasy(KeyEvent event) throws IOException {
	        	if(event.getCode() == KeyCode.ENTER) {
	        		wit();
	        	}
	        }
	      
			private void wit() throws IOException{
				Main m = new Main();
			    
		        if(wAmount.getText().isEmpty() || wReason.getText().isEmpty()) {
		        	warning.setText("Incomplete Information provided");
		        }
		        
		        else {
		        	String regex = "[+]*[0-9]+\\.?[0-9]*";
		        	Pattern pattern = Pattern.compile(regex); 
		        	Matcher matcher = pattern.matcher(wAmount.getText());
		        	if(!matcher.matches()) {
		        		warning.setText("Positive Decimal Numbers Only");
		        	}
		        	else {
			        	double am=Double.parseDouble(wAmount.getText().toString());
			        	//database connection
				        try {
				            Class.forName("com.mysql.jdbc.Driver");
				            Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
				            Statement stmt=con.createStatement();
				            
				            //get current total amount
				            String sql="Select currentAmount from tAmount where tID = (Select MAX(tID) from tAmount)";
				            ResultSet rs=stmt.executeQuery(sql);
				            if(rs.next()) {
				            	totalAmount=rs.getDouble(1);
				            }
				            
				            
				            if(am<=totalAmount) {
				            	String sql2="Insert into finance(date, type, amount, managerID, reason) values('"+dtf.format(LocalDateTime.now())+"','withdrawal', "+wAmount.getText().toString()+", (SELECT managerID FROM management WHERE username='"+user+"'), '"+wReason.getText().toString()+"')";
				            	stmt.execute(sql2);
				            	
				            	//insert new record of total Amount
				            	String sql3="Insert into tAmount(currentAmount) values ("+(totalAmount-am)+")";
					            stmt.execute(sql3);
					            
					            m.changeScene("Finance.fxml");
				            }
				            else {
				            	warning.setText("Insufficient funds for transaction");
				            }
				        }catch(Exception e) {
				        	System.out.println(e);
				        }
		        	}
		        }
			}

			private void dep() throws IOException {
	        	Main m = new Main();
		        if(dAmount.getText().isEmpty() ||dReason.getText().isEmpty()) {
		        	warning.setText("Incomplete Information provided");
		        }
		        
		        else {
		        	String regex = "[+]*[0-9]+\\.?[0-9]*";
		        	Pattern pattern = Pattern.compile(regex); 
		        	Matcher matcher = pattern.matcher(dAmount.getText());
		        	if(!matcher.matches()) {
		        		warning.setText("Positive Decimal Numbers Only");
		        	}
		        	else {
			        	double am=Double.parseDouble(dAmount.getText().toString());
			        	//database connection
				        try {
				            Class.forName("com.mysql.jdbc.Driver");
				            Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
				            Statement stmt=con.createStatement();
				            String sql="Insert into finance(date, type, amount, managerID, reason) values('"+dtf.format(LocalDateTime.now())+"','deposit', "+dAmount.getText().toString()+", (SELECT managerID FROM management WHERE username='"+user+"'), '"+dReason.getText().toString()+"')";
				            stmt.execute(sql);
				            
				            //for get current tAmount
				            String sql2="Select currentAmount from tAmount where tID = (Select MAX(tID) from tAmount)";
				            ResultSet rs=stmt.executeQuery(sql2);
				            if(rs.next()) {
				            	totalAmount=rs.getDouble(1);
				            }
				            
				            //insert new tAmount record
				            String sql3="Insert into tAmount(currentAmount) values ("+(totalAmount+am)+")";
				            stmt.execute(sql3);
				        }catch(Exception e) {
				        	System.out.println(e);
				        }
				        
				        m.changeScene("Finance.fxml");
		        	}
		        }
			}
}
