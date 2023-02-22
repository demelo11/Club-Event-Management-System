package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AddMems {

    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private Label warning;

    @FXML
    private TextField mDateJoined;

    @FXML
    private TextField mEmail;

    @FXML
    private TextField mID;

    @FXML
    private TextField mName;

    public void insertMember(ActionEvent event) throws IOException {
        insert();
    }
    
    public void doneChecking(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Members.fxml");

    }
    
    public void easy(KeyEvent event) throws IOException {
    	if(event.getCode() == KeyCode.ENTER) {
    		insert();
    	}
    }

	private void insert() throws IOException {
		Main m = new Main();
        
        if(mID.getText().isEmpty() ||mName.getText().isEmpty() || mEmail.getText().isEmpty() || mDateJoined.getText().isEmpty()) {
        	warning.setText("Incomplete Information provided");
        }
        
        else {
        	String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        	Pattern pattern = Pattern.compile(regex); 
        	Matcher matcher = pattern.matcher(mEmail.getText());
        	if(!matcher.matches()) {
        		warning.setText("Email Format is Incorrect");
        	}
        	else {
        		regex = "^\\d{4}-\\d{2}-\\d{2}$";
            	pattern = Pattern.compile(regex); 
            	matcher = pattern.matcher(mDateJoined.getText());
            	if(!matcher.matches()) {
            		warning.setText("Date format is incorrect");
            	}
            	else {
            		regex = "[0-9]{5}";
                	pattern = Pattern.compile(regex); 
                	matcher = pattern.matcher(mID.getText());
                	if(!matcher.matches()) {
                		warning.setText("StudentID is 5-digit number only");
                	}
                	else {
		        		//database connection
				        try {
				            Class.forName("com.mysql.jdbc.Driver");
				            Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
				            Statement stmt=con.createStatement();
				            
				            String sql="Select memberID from member where memberId = "+mID.getText();
				            ResultSet rs=stmt.executeQuery(sql);
				            if(rs.next()) {
				            	warning.setText("This StudentID alredy exists");
				            	return;
				            }
				            else {
					            //insert stmt
					            String sql2="Insert into member values("+mID.getText().toString()+",'"+mName.getText().toString()+"', '"+mEmail.getText().toString()+"', '"+mDateJoined.getText().toString()+"')";
					            stmt.execute(sql2);
				            }
					        }catch(Exception e) {
					        	System.out.println(e);
					        }
					        m.changeScene("Members.fxml");
            	}
            	} 
		   }
        }
	}

}
