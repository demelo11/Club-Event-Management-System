package application;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {
	private static String user = "";

    public Controller() {

    }

    @FXML
    private Button button;
    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public static String getUser() {
    	return user;
    }

    public void userlogin(ActionEvent event) throws IOException {
        checkLogin();

    }

    private void checkLogin() throws IOException {
        Main m = new Main();
        //database connection
        try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
        Statement stmt=con.createStatement();
        String sql="Select * from login where username='"+username.getText().toString()+"' and password = '"+password.getText().toString()+"'";
        ResultSet rs=stmt.executeQuery(sql);
        if(rs.next()) {
            wrongLogIn.setText("Success!");
            
            //keep the username
            Finance.setUser(username.getText().toString());
            MainMenu.setUser(username.getText().toString());

            m.changeScene("afterLogin.fxml");
        }

        else if(username.getText().isEmpty() || password.getText().isEmpty()) {
            wrongLogIn.setText("Please enter your data.");
        }


        else {
            wrongLogIn.setText("Wrong username or password!");
        }
        }catch(Exception e) {
        	System.out.println(e);
        }
    }
    
    public void easyLog(KeyEvent event) throws IOException {
    	if(event.getCode() == KeyCode.ENTER) {
    		checkLogin();
    	}
    }

}