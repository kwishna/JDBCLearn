import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * External Jars Required : csvjdbc, dans-dbf-lib, opencsv, jackon-core, jackson-databind
 * JDK 8+ Advised.
 * 
 * @author AB D (Krishna Kr. Singh)
 *
 */

public class CsvJDBC {

	private static final String DIRECTORY = System.getProperty("user.dir"); // Put The Directory Where Your csv File Is Present
	
	public static List<Map<String, String>> dataAsListOfMapp(String query) throws ClassNotFoundException, SQLException{
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		// Loading driver.
	    Class.forName("org.relique.jdbc.csv.CsvDriver");
	    
	    // Creating Connection
	    Connection con = DriverManager.getConnection("jdbc:relique:csv:"+DIRECTORY);
	    
	    // Creating Statement
	    Statement s = con.createStatement();
	    
//	     s.executeQuery("SELECT * FROM data where season>= 2013 or toss_decision !=\'bat\' and city=\'Bangalore\'");
	    
	    //Executing Query
	    ResultSet rs = s.executeQuery(query);
	    
	    // Getting Metadata
	    ResultSetMetaData meta = rs.getMetaData();
	    
	    while(rs.next())
	    {
	        for (int i = 0; i < meta.getColumnCount(); i++)
	        { 
	        	map.put(meta.getColumnName(i + 1).trim(), rs.getString(i + 1).trim()); // Mapping Column Name With The Value For Each Row
	        	
	        }
	        list.add(map); // Adding Each Map To List
	      }
	    
	      con.close();
	      
		return list; 
	}
	
	
	public static String checkDataType(String data) { // Checking If String Data Can Be Converted In To Other Data Type
	 	   
	 	   String result="";
	 	   
	 	   if(data.toLowerCase() != data.toUpperCase()) {

	 		   result = "String";
	 	   }
	 	   
	 	   else{
	 		   
	 		   try {
	 			   
	 			   Integer.parseInt(data);
	 			   result = "Integer";
	 			   
				} catch (NumberFormatException e) {
					
					data = data.replace(".", "0");
					
	 		   		data = data.replaceAll("[^A-Za-z0-9]", " ");
	 		   		
	 		   		int flag = 0;
	 		   		
	 		   		for(int i=0; i<data.length(); i++) {
	    		   		
	 		   			if((int)data.charAt(i)==32) {
	 		   			flag++;

	 		   			}
	 		   			
	 		   			if(flag>1) {
	 		   				
	 		   				result = "date";
	 		   			}
	 		   			else result = "double";
	 		   		}
				   }
				}
	 	   
			return result;
	 	   
	    }
	
	/**
	 * Converting List Of Maps Into JSON. Each Map Consists Of Enteries Mapped To Column Name & Its Value From Query Execution Result Table
	 * 
	 * @param input
	 * @throws ParseException
	 * @throws IOException
	 * 
	 * Using "Jackson-databind" External Jar File To Convert Data To JSON
	 */
	public static String createJSON(List<Map<String, String>> input) throws ParseException, IOException {
		
		ObjectMapper objmap = new ObjectMapper();
		
		ArrayNode anode = objmap.createArrayNode(); // Array Of JSON Objects. Each JSON Object Contains Data Of A Complete Row From Result Table
		
		for(int i=0; i < input.size(); i++) {
			
			ObjectNode onode = objmap.createObjectNode(); // A JSON Object
			
			int valInt;
			double valDbl;
			
			Set<String> s = input.get(i).keySet(); // Set Of Keys
			
			Iterator<String> itr = s.iterator();
			
			while(itr.hasNext()) {
				
				String key = itr.next();
				
				String value = input.get(i).get(key); // Value WRT Key
				
				String datatype = checkDataType(value);
				
				if(datatype.equalsIgnoreCase("integer")) {
					valInt = Integer.parseInt(value); // Converting String To Integer If It's Column Name Represents An Integer
					onode.put(key, valInt); // Putting Each Entry Of Map In A JSON Object
					
				}
				
				else if(datatype.equalsIgnoreCase("double")) { // Converting String To Double If It's Column Name Represents An Integer
					valDbl = Double.parseDouble(value);
					onode.put(key, valDbl);
				}
			
				// Dates Are Not Converted To Date. They Are Still Date. Because, "onode.put(String, Date);" As Showing Invalid.
				else {
					
					onode.put(key, value);
					
				}

			}
			
			anode.add(onode); // JSON Object Added To Array Of Rows
		
		}
//		return anode.toString();
		return objmap.writerWithDefaultPrettyPrinter().writeValueAsString(anode); // For Pretty JSON
	}
	
	
	public static void saveToFile(String stringAsJSON, String filePath) throws IOException {
		
		Path p = Paths.get(filePath);
		
		if(!p.toFile().exists()) {
			Files.createFile(p);
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(p.toFile()));
		bw.write(stringAsJSON);
		bw.flush();
		bw.close();
	}
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException, IOException {
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		result = CsvJDBC.dataAsListOfMapp("SELECT * FROM data where season>= 2013 or toss_decision !=\'bat\' and city=\'Bangalore\'");
		
		String stringAsJSON = createJSON(result);
		
		String filePath = System.getProperty("user.dir")+"\\data1.json";
		
	//	saveToFile(stringAsJSON, filePath);
	}

}
