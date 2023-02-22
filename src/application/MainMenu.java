package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainMenu implements Initializable{

    @FXML
    private Button logout;
    @FXML
    private Button finance;
    @FXML
    private Button event;
    @FXML 
    private Label title;

    
    @FXML
    private Button member;
	
    private static String user;
	
	
	  public void checkMembers(ActionEvent event) throws IOException {
	        Main m = new Main();
	        m.changeScene("Members.fxml");

	    }

    public void userlogout(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Sample.fxml");

    }
    
    public void checkFinance(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Finance.fxml");

    }
    
    public void checkEvents(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Event.fxml");

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Timeline time= new Timeline(new KeyFrame(Duration.seconds(0.1),e ->{
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
		        Statement stmt=con.createStatement();
		        String sql="SELECT name FROM member WHERE memberID= (SELECT memberID FROM management WHERE username='"+user+"')";
		        ResultSet rs=stmt.executeQuery(sql);
	            if(rs.next()) {
	            	title.setText("Welcome, "+rs.getString(1));
	            }
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}));
        
		time.setCycleCount(1);
		time.play();
	}

	public static void setUser(String s) {
		user=s;
	}
}