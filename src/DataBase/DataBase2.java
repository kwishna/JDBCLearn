package DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * SQLITE DB READER
 * 
 * @author AB D
 *
 */
public class DataBase2 {
public static void main(String[] args){
	ConnectionProvider cp = new ConnectionProvider();
// 		Try-with Resources
		try(
				
//		Connection c = ConnectionProvider.getSqliteConnection("C:\\Users\\Krishna Singh\\Downloads\\Compressed"+"\\chinook.db");

		Connection c = cp.getSqliteConnection();
		Statement st = c.createStatement();
		ResultSet reslt = st.executeQuery("SELECT * FROM customers;")
			){
			
			while(reslt.next()) {
			
					System.out.println(							
									   reslt.getString("FirstName") + "\t" +
									   reslt.getString("LastName")  + "\t" +
									   reslt.getString("Company")   + "\t" +
									   reslt.getString("Address")   + "\t" +
									   reslt.getString("Country")
									);	
			}
			
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
	}
}
