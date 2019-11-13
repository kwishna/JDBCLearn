package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase3 {
	
	public static void getQuery(Connection c, Statement st, String qry) throws SQLException {

//		Statement st = c.createStatement();
//		String query = "SELECT * FROM sample";
		String query = qry;
		ResultSet reslt = st.executeQuery(query);
		while(reslt.next()) {
			
			System.out.println(							
					   reslt.getString("id") + "\t" +
					   reslt.getString("street")  + "\t\t" +
					   reslt.getString("city")   + "\t\t" +
					   reslt.getString("zip")   + "\t\t" +
					   reslt.getString("type")
					);	
		}
		
		st.close();		
	}

	public static void main(String[] args){

		ConnectionProvider cp = new ConnectionProvider();

		try(Connection c = cp.getCsvConnection(); Statement st = c.createStatement()){

			DataBase3.getQuery(c, st, "SELECT * FROM sample");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
	}

}
