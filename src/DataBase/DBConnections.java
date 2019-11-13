package DataBase;
import java.sql.*;
import java.util.Base64;

import com.mysql.cj.jdbc.MysqlDataSource;
public class DBConnections {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
	/**
	 * DataSource Is Used Now.
	 * 
	 */
//		Class.forName("com.mysql.cj.jdbc.Driver"); Where com.mysql.jdbc.Driver Is Deprecated
		MysqlDataSource ds = new MysqlDataSource();
//		ds.setURL("jdbc:mysql://localhost:3306/sakila");
		ds.setUser("root");
		ds.setPassword(new String(Base64.getUrlDecoder().decode("Z29kbmFhcmE=")));
		ds.setDatabaseName("sakila");
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila","root","ghdfgh");
		Connection conn = ds.getConnection();
		Statement state = conn.createStatement();
		
//		PreparedStatement ps = conn.prepareStatement("UPDATE address WHERE empId=? and empName=?");
//		ps.setInt(1, 100);
//		ps.setString(2, "Krishna");
		
		ResultSet rs = state.executeQuery("SELECT * FROM actor;");
		
		while(rs.next()) {
			
			System.out.println(rs.getObject(1)+"   "+rs.getObject(2)+"   "+rs.getObject(3));
			
		}
		
	}

}
