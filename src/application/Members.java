package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
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

public class Members implements Initializable{

	@FXML
    private TableView<ModelTable>  table;
	@FXML
    private TableColumn<ModelTable, String> col_id;
	@FXML
    private TableColumn<ModelTable, String> col_name;
	@FXML
    private TableColumn<ModelTable, String> col_email;
	@FXML
    private TableColumn<ModelTable, String> col_date;
	
	@FXML
    private Button add;
	@FXML
    private Button confirm ;
	@FXML
    private Button ok;
	@FXML
    private Button delete;
	@FXML
	private TextField mName;
	@FXML
	private TextField mEmail;
	@FXML
	private TextField mDateJoined;
	@FXML
	private TextField mID;
	
	ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
	
	
	
	
	private int memberID;
	private String name;
	private String email;
	private Date dateJoined;
	
	public Members(){
	
	}
	
	public Members(int memberID,String name, String email, Date dateJoined){
		this.memberID=memberID;
		this.name=name;
		this.email=email;
		this.dateJoined=dateJoined;
	}

	public int getMemberID() {
		return memberID;
	}

	public String getName() {
		return name;
	}


	public String getEmail() {
		return email;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public String print() {
		return memberID+": \nName:"+name+"\nEmail: "+email+"\nDate Joined: "+dateJoined;
	}
	
	
	
	
	//javafx
	 public void addMember(ActionEvent event) throws IOException {
	        Main m = new Main();
	        m.changeScene("addMember.fxml");
	
	    }

	 
	 public void doneChecking(ActionEvent event) throws IOException {
	        Main m = new Main();
	        m.changeScene("afterLogin.fxml");
	
	    }
	 
	 @Override
		public void initialize(URL location, ResourceBundle resources) {
			
			
			try {
				 Class.forName("com.mysql.jdbc.Driver");
		           Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
		
		           ResultSet rs = con.createStatement().executeQuery("select * from member");
		           
		           while(rs.next()) {
		        	   oblist.add(new ModelTable(rs.getString("memberID"),rs.getString("name"),rs.getString("email"),rs.getString("dateJoined")));
		           }
		           
		       }catch(Exception e) {
		        System.out.println(e);
		       }
			
			col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
			col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
			col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
			table.setItems(oblist);
			
		} 
	 
	 public void delete(ActionEvent event) {
		 //Bring up an alert box
		  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		  alert.setTitle("Warning!");
		  alert.setContentText("Do you want to permanently delete this memeber?");
		  Optional<ButtonType> result = alert.showAndWait();
		  
		  
		  if(result.isEmpty()) {
			  return;
		  }else if(result.get() == ButtonType.OK) {
		 
			 int selectID = table.getSelectionModel().getSelectedIndex();
			 String memberID=col_id.getCellData(selectID);
			 
			 //delete entry from table
			 table.getItems().remove(selectID);
			 
			 //database connection
		        try {
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
		            Statement stmt=con.createStatement();
		            String sql="Delete from member where memberID = "+memberID;
		            stmt.execute(sql);
		        }catch(Exception e) {
		        	System.out.println(e);
		        }
		  }
	 }
		  


}
