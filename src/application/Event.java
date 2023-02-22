package application;

import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Event implements Initializable{

	@FXML
    private Button back;	
	@FXML
    private TableView<EventTable>  table;
	@FXML
    private TableColumn<EventTable, String> col_ID;
	@FXML
    private TableColumn<EventTable, String> col_title;
	@FXML
    private TableColumn<EventTable, String> col_Date;
	@FXML
    private TableColumn<EventTable, String> col_Time;
	@FXML
    private TableColumn<EventTable, String> col_Description;
	
	@FXML
    private Button ok;
	@FXML
    private Button delete;
	@FXML
    private Button create;
	@FXML
    private Button add;
	@FXML
	private TextField eDescript;
	@FXML
	private TextField eTitle;
	@FXML
	private TextField eDate;
	@FXML
	private TextField eTime;
	
	ObservableList<EventTable> oblist = FXCollections.observableArrayList();

	

	private int eventID;
	private String title;
	private String description;
	private Date date;
	private Time time;
	
	public Event() {
		
	
		
	}
	public Event(int eventID, String title, String description, Date date , Time time) {
		
		this.eventID = eventID;
		this.title = title;
		this.description = description;
		this.date = date;
		this.time = time;
		
	}
	
	public String getTitle() {
		return title;
		
	}
	
	public int getID() {
		return eventID;
		
	}
	
	public Date getDate() {
		return date;
		
	}
	
	public Time getTime() {
		return time;
		
	}
	
	public String print() {
		return eventID+": \nTitle:"+title+"\nDate: "+date+"\nTime: "+time+"\nDescription: "+description;
		
	}
	
	
	
	//javafx
	  public void doneChecking(ActionEvent event) throws IOException {
	        Main m = new Main();
	        m.changeScene("afterLogin.fxml");
	
	  }
	  
	  public void createEvent(ActionEvent event) throws IOException {
	        Main m = new Main();
	        m.changeScene("NewEvent.fxml");
	
	  }
	  
	  @Override
		public void initialize(URL location, ResourceBundle resources) {
			try {
				 Class.forName("com.mysql.jdbc.Driver");
		           Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
		
		           ResultSet rs = con.createStatement().executeQuery("select * from eventInfo");
		           
		           while(rs.next()) {
		        	   oblist.add(new EventTable(rs.getString("eventID"),rs.getString("title"),rs.getString("date"),rs.getString("time"),rs.getString("description")));
		           }
		           
		       }catch(Exception e) {
		        System.out.println(e);
		       }
			
			col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
			col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
			col_Time.setCellValueFactory(new PropertyValueFactory<>("time"));
			col_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
			table.setItems(oblist);
			
		}
	  
	  public void delete(ActionEvent event) {
		  
		  //Bring up an alert box
		  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		  alert.setTitle("Warning!");
		  alert.setContentText("Do you want to permanently delete this event?");
		  Optional<ButtonType> result = alert.showAndWait();
		  
		  
		  if(result.isEmpty()) {
			  return;
		  }else if(result.get() == ButtonType.OK) {
			 int selectID = table.getSelectionModel().getSelectedIndex();
			 String eventID=col_ID.getCellData(selectID);
			 
			 //delete entry from table
			 table.getItems().remove(selectID);
			 
			 //database connection
		        try {
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
		            Statement stmt=con.createStatement();
		            String sql="Delete from eventInfo where eventID = "+eventID;
		            stmt.execute(sql);
		        }catch(Exception e) {
		        	System.out.println(e);
		        }
		  }
		 }
	 
}
