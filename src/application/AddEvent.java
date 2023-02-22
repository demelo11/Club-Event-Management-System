package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AddEvent {

    @FXML
    private Button add;
    @FXML
    private Button cancel;
    @FXML
    private Label warning;

    @FXML
    private TextField eDate;

    @FXML
    private TextField eDescript;

    @FXML
    private TextField eTime;

    @FXML
    private TextField eTitle;
    
    @FXML
    private CheckBox cbox;

    public void addEvent(ActionEvent event) throws IOException {
        add();

  }
    
    private void send() {
    	Stack<String> recipient=new Stack<String>();
    	String body="TITLE:"+eTitle.getText().toString()+"\nDATE: "+eDate.getText().toString()+"\nTIME: "+eTime.getText().toString()+"\nDESCRIPTION: "+eDescript.getText().toString();
    	String title="COSC CLUB EVENT: "+eTitle.getText().toString();
    	
    	try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
            Statement stmt=con.createStatement();
            String sql="Select email from member";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()) {
            	recipient.push(rs.getString(1));
            }
            while(!recipient.isEmpty()) {
            	SendEmail.send(recipient.pop(),title,body);
            }
        }catch(Exception ex) {
        	System.out.println(ex);
        }
	}

	public void doneChecking(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Event.fxml");

    }
	
	public void easy(KeyEvent event) throws IOException {
    	if(event.getCode() == KeyCode.ENTER) {
    		add();
    	}
    }

	private void add() throws IOException {
		Main m = new Main();
        
        if(eTitle.getText().isEmpty() ||eDate.getText().isEmpty() || eTime.getText().isEmpty() || eDescript.getText().isEmpty()) {
        	warning.setText("Incomplete Information provided");
        }
        
        else {
        	String regex = "^\\d{4}-\\d{2}-\\d{2}$";
        	Pattern pattern = Pattern.compile(regex); 
        	Matcher matcher = pattern.matcher(eDate.getText());
        	if(!matcher.matches()) {
        		warning.setText("Date Format is Incorrect");
        	}
        	else {
        		regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
            	pattern = Pattern.compile(regex); 
            	matcher = pattern.matcher(eTime.getText());
            	if(!matcher.matches()) {
            		warning.setText("Time Format is Incorrect");
            	}
            	else {
            		if(cbox.isSelected()) {
            			send();
            		}
            		String d=eDescript.getText().toString().replaceAll("\"", "'");
			        //database connection
			        try {
			            Class.forName("com.mysql.jdbc.Driver");
			            Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
			            Statement stmt=con.createStatement();
			            String sql="Insert into eventInfo(title,date,time,description) values('"+eTitle.getText().toString()+"', '"+eDate.getText().toString()+"', '"+eTime.getText().toString()+"', \""+eDescript.getText().toString()+"\")";
			            stmt.execute(sql);
			        }catch(Exception e) {
			        	System.out.println(e);
			        }
			        
			        m.changeScene("Event.fxml");
            	}
        	}
        }
	}

}
