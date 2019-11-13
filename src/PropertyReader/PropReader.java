package PropertyReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * {@link Properties} File Reader
 * 
 * @author AB D
 *
 */
public class PropReader {

	private PropReader() {

	}
	
	/**
	 * 
	 * @param 	fileName 	File without <code>.properties</code> extension Present At Resources/propertyFile Folder
	 * @return 	{@link 	Properties}
	 * @throws 	RuntimeException 	If filename is null, blank or invalid extension.
	 */
	public static Properties getPropReader(String fileName) {
		
		Properties prop = null;
		
		if (fileName == null || fileName == "" || fileName.endsWith(".properties")) {
			
			System.err.println("Property File Must Be Valid");
			throw new RuntimeException("Property Filename Is : "+fileName);
			
		}
		else {
			
			String folderName = System.getProperty("user.dir") + "/Resources/propertyFile/";

			try (FileInputStream f = new FileInputStream(folderName + fileName + ".properties")) {
				
				prop = new Properties();
				prop.load(f);

			} catch (IOException e) {
				
				System.err.println("Provided Resource File Not Found - "+folderName+fileName);
				System.out.println(e.getMessage());
			}
		}

		return prop;
	}

}
//E:\Bhole_Nath\Eclipse_WorkSpace\WorkSpace_2\DataBases\Resources\propertyFile\database.properties