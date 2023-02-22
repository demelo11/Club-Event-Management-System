package application;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class PrintCSV {
	public static void print() {
		StringBuilder s=new StringBuilder();
		String t="";
		
		s.append("TransactionID").append(",").append("Date").append(",").append("Type").append(",").append("Amount").append(",").append("ManagerID").append(",").append("Reason").append("\n");
		
		
		try {
	        Class.forName("com.mysql.jdbc.Driver");
	        Connection con= DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5482717","sql5482717","dFLcvrbMxR");
	        Statement stmt=con.createStatement();
	        String sql="Select * from finance";
	        ResultSet rs=stmt.executeQuery(sql);
	        while(rs.next()) {
	        	t=rs.getString("transactionID");
	        	s.append(t).append(",").append(rs.getString("date")).append(",").append(rs.getString("type")).append(",").append(rs.getString("amount")).append(",").append(rs.getString("managerID")).append(",").append(rs.getString("reason")).append("\n");
	        }
	        }catch(Exception e) {
	        	System.out.println(e);
	        }
		
		try {
			File f= new File("C:\\transactions");
			if(!f.exists()) {
				Path path=Paths.get("C:\\transactions");
				Files.createDirectory(path);
			}
			
			FileWriter w=new FileWriter("C:\\transactions\\Transaction_History_"+ t+".csv");
			w.write(s.toString());
			w.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}

