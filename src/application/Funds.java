package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Funds implements Initializable{

    @FXML
    private Button ret;
    @FXML
    private Button download;

    @FXML
    private Label tAmount;

    @FXML
    void doneChecking(ActionEvent event) throws IOException {
    	 Main m = new Main();
	     m.changeScene("Finance.fxml");
    }
    
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Timeline time= new Timeline(new KeyFrame(Duration.seconds(0.1),e ->{
			try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
	            Statement stmt=con.createStatement();
	            String sql="Select currentAmount from tAmount where tID = (Select MAX(tID) from tAmount)";
	            ResultSet rs=stmt.executeQuery(sql);
	            if(rs.next()) {
	            	tAmount.setText(rs.getString(1));
	            }
	        }catch(Exception ex) {
	        	System.out.println(ex);
	        }
		}));
		
		time.setCycleCount(1);
		time.play();
	}
    
    public void save(ActionEvent event) {
    	PrintCSV.print();
    	//Bring up an alert box
		  Alert alert = new Alert(Alert.AlertType.INFORMATION);
		  alert.setTitle("Download Complete");
		  alert.setContentText(".csv file saved at 'C:\\transactions'");
		  alert.showAndWait();
    }

}

