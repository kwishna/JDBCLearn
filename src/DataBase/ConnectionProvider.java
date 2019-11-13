package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Objects;
import java.util.Properties;

import PropertyReader.PropReader;

/*
 * jdbc:sqlserver://localhost\\sqlexpress;user=sa;password=secret : SQL SERVER
 * jdbc:mysql://localhost:3306/test?user=root&password=secret
 * jdbc:oracle:thin:root/secret@localhost:1521:testdb
 * jdbc:postgresql://localhost:5432/testdb jdbc:sqlite:database_file_path
 * 
 * Class.forName("org.relique.jdbc.csv.CsvDriver");
 * jdbc:relique:csv:"+CSV_FILE_PATH_STRING
 * 
 * Z29kbmFhcmE=
 */
/**
 * <P>
 * Class For Getting Connections For Different Types For Databases. JDBC Jar
 * File Must Be Present In Classpath.
 * </P>
 * 
 * @author AB D
 * @since 	<code>7 July, 2019<code>
 *
 */
public class ConnectionProvider {

	private Connection connMySql;
	private Connection connOracle;
	private Connection connCsv;
	private Connection connSqlite;

	private Properties property = PropReader.getPropReader("database");

	/**
	 * 
	 * @param 	db  Database Name
	 * @param 	user  User-name For DB {@link Connection}
	 * @param 	password Password For User-name In Base64 Encrypted String
	 * @return 	{@link Connection} For Database Provided In Argument
	 * @see 	#getSqlConnection()
	 */
	public Connection getSqlConnection(String db, String user, String password) {

//		Class.forName("com.mysql.jdbc.Driver"); //Optional

		if (connMySql == null) {

			try {

				System.out.println("***** Connecting... *****");
				password = decodePassword(password);

				// 1st Way :-
				connMySql = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, user, password);
				System.out.println("***** Succesfully Connected! Go Ahead... *****\n");

				/**
				 * // 2nd Way :- Properties props = new Properties();
				 * props.put("user", "root");
				 * props.put("password", "root123"); Connection conn =
				 * DriverManager.getConnection(databaseURL, props);
				 * 
				 * // 3rd Way :- //
				 * DriverManager.getConnection(jdbc:mysql://localhost:3306/test?user=root&password=root);
				 * 
				 * // 4th Way :- Creating Connection In Better Way :-
				 * 
				 * MysqlDataSource ds = new MysqlDataSource();
				 * ds.setUser("root");
				 * ds.setPassword("hfghdfgh");
				 * ds.setDatabaseName("sakila");
				 * 
				 * Connection conn = ds.getConnection();
				 * 
				 */

// 				Java 7 Onwards :-
				/**
				 * try (Connection conn = DriverManager.getConnection(url, user, password)) {
				 * 
				 * if (conn != null) { System.out.println("Connected to the database"); }
				 * 
				 * }
				 * 
				 * catch (SQLException ex) { System.out.println("An error occurred. Maybe
				 * user/password is invalid"); ex.printStackTrace(); }
				 * 
				 */

			} catch (SQLException e) {

				System.err.println(":: Failed To Connect...");
				System.out.println(e.getMessage());
			}
		}

		return connMySql;

	}

	// Using Property Reader
	/**
	 * 
	 * @return 	{@link Connection} For The Provided MySQL Details In Property File.
	 * @see 	#getSqlConnection(String, String, String)
	 */
	public Connection getSqlConnection() {

		try {

			Class.forName(property.getProperty("mySqlDriver"));

		} catch (ClassNotFoundException e1) {
		}

		if (connMySql == null) {

			try {

				System.out.println("***** Connecting... *****");

				String url = property.getProperty("mySqlURL");
				String user = property.getProperty("mySqlUser");
				String password = decodePassword(property.getProperty("mySqlPassword"));
				String db = property.getProperty("mySqlDBName");
				String port = property.getProperty("mySqlPort");

				connMySql = DriverManager.getConnection(url + port + "/" + db, user, password);

				System.out.println("***** Succesfully Connected! Go Ahead... *****\n");

			} catch (SQLException e) {

				System.err.println(":: Failed To Connect...");
				System.out.println(e.getMessage());
			}
		}

		return connMySql;

	}

	/**
	 * 
	 * @param 	db  Database Name
	 * @param 	user User-name For DB Connection
	 * @param 	password Password For User-name In Base64 Encrypted String
	 * @return 	{@link Connection} For Database Provided In Argument
	 * @see 	#getOracleConnection()
	 */
	public Connection getOracleConnection(String db, String user, String password) {

//		Class.forName("oracle.jdbc.driver.OracleDriver"); 
//		jdbc:oracle:thin:@hostname:port Number:databaseName

		if (connOracle == null) {

			try {

				System.out.println("***** Connecting... *****");

				connOracle = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + db, user, password);

				System.out.println("***** Succesfully Connected! Go Ahead... *****\n");

			} catch (SQLException e) {

				System.out.println(e.getMessage());
			}
		}

		return connOracle;

	}

	// Using Property Reader
	/**
	 * 
	 * @return 	{@link Connection} For The Provided Oracle Details In Property File.
	 * @see 	#getOracleConnection(String, String, String)
	 */
	public Connection getOracleConnection() {

		try {

			Class.forName(property.getProperty("myOracleDriver"));

		} catch (ClassNotFoundException e1) {
		}

		if (connOracle == null) {

			try {

				System.out.println("***** Connecting... *****");

				String url = property.getProperty("myOracleURL");
				String user = property.getProperty("myOracleUser");
				String password = decodePassword(property.getProperty("myOraclePassword"));
				String db = property.getProperty("myOracleDBName");
				String port = property.getProperty("myOraclePort");

				connOracle = DriverManager.getConnection(url + port + ":" + db, user, password);

				System.out.println("***** Succesfully Connected! Go Ahead... *****\n");

			} catch (SQLException e) {

				System.err.println(":: Failed To Connect...");
				System.out.println(e.getMessage());
			}
		}

		return connOracle;

	}

	/**
	 * 
	 * @param 	directory Directory's Complete Path Where CSV File Is Present.
	 * @return 	{@link Connection} Object For The Provided CSV Directory
	 * @throws 	ClassNotFoundException If Driver Jar Not Present In Classpath
	 * @see 	#getCsvConnection()
	 */
	
	public Connection getCsvConnection(String directory) throws ClassNotFoundException {

		Class.forName("org.relique.jdbc.csv.CsvDriver");
		
		if (directory.endsWith(".csv")) {

			directory = directory.replace(".csv", "");
		}

		if (connCsv == null) {

			try {

				connCsv = DriverManager.getConnection("jdbc:relique:csv:" + directory);

			} catch (SQLException e) {

				System.out.println(e.getMessage());
			}
		}

		return connCsv;

	}

	/**
	 * 
	 * @return 	{@link Connection} Object For The CSV Directory Provided In Property File.
	 * @throws 	ClassNotFoundException If Driver Jar Not Present In Classpath Or Invalid Class Method In {@link Properties} File
	 * @see 	#getCsvConnection(String))
	 */
	
	public Connection getCsvConnection() throws ClassNotFoundException {

		Class.forName(property.getProperty("csvDriver"));

		if (connCsv == null) {

			try {
				
				String url = property.getProperty("csvURL");
				String csvFileLocation = property.getProperty("csvFileLocation");
				
				if (!csvFileLocation.endsWith("\\")) {

					csvFileLocation = csvFileLocation + "\\";
				}

				connCsv = DriverManager.getConnection(url + csvFileLocation);

			} catch (SQLException e) {

				System.out.println(e.getMessage());
			}
		}

		return connCsv;

	}

	/**
	 * 
	 * @param 	path Complete Sqlite File Path.
	 * @throws 	RuntimeException If File Extension Found Invalid.
	 * @return	{@link Connection} Object For Provided Sqlite DB
	 * @see 	ConnectionProvider#getSqliteConnection()
	 * 
	 */
	public Connection getSqliteConnection(String path) {

		if (!path.endsWith(".db")) {

			System.err.println("Sqlite DB File Seems Invalid. Must Ends With .db");
			throw new RuntimeException("Invalid DB File : " + path);
		}

		if (connSqlite == null) {

			try {

				connSqlite = DriverManager.getConnection("jdbc:sqlite:" + path);

			} catch (SQLException e) {

				System.out.println(e.getMessage());
			}
		}

		return connSqlite;

	}

	/**
	 * @throws 	RuntimeException If File Extension Found Invalid.
	 * @return 	{@link Connection} For Sqlite DB File Provided In Property File. It Must Be Present In Resources/sqliteDB.
	 * @see 	#getSqlConnection()
	 */
	public Connection getSqliteConnection() {

		if (connSqlite == null) {

			try {

				String url = property.getProperty("sqliteURL");
				String dbFileName = property.getProperty("sqliteDBFileName");
				String dbFile = System.getProperty("user.dir") + "/Resources/sqliteDB/" + dbFileName;

				if (!dbFileName.endsWith(".db")) {

					System.err.println("Sqlite DB File Seems Invalid. Must Ends With .db");
					throw new RuntimeException("Invalid DB File : " + dbFile);
				}

				connSqlite = DriverManager.getConnection(url + dbFile);

			} catch (SQLException e) {

				System.out.println(e.getMessage());
			}
		}

		return connSqlite;

	}

	private static String decodePassword(String encrypted) {

		return new String(Base64.getUrlDecoder().decode(encrypted));
	}

}
