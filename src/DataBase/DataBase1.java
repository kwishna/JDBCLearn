package DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Put A Jar File For Sqlite In The classpath:
 * 
 * 
 * @author AB D
 *
 */
public class DataBase1 {

	public static void main(String[] args){

		ConnectionProvider cp = new ConnectionProvider();

		// Try-with Resources
		try( 
		Connection c = cp.getSqlConnection("world", "root", "Z29kbmFhcmE=");
		Statement st = c.createStatement();
		ResultSet reslt = st.executeQuery("SELECT * FROM country where name='india';")
			){
			
//			ResultSetMetaData meta = reslt.getMetaData();
			
			while(reslt.next()) {
			
					System.out.println(
									   reslt.getString("Name") + "\t" +
									   reslt.getString("Continent") + "\t" +
									   reslt.getString("Region") + "\t" +
									   reslt.getString("LifeExpectancy") + "\t" +
									   reslt.getString("GovernmentForm")							
									);
		
			}
			
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
	}
}
