package jl.project;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.exceptions.UnrecognizedOSException;

/***
 * 
 * @author 
 * Class that sets Selenium webdriver, Angular, and grid default configurations. 
 * The args that can be expected by the method main are:
 * - the path to the tmp folder
 * - the path the the StringExternalization.java file
 *
 */
public class ConfigurationUtility 
{
	static Logger logger = LoggerFactory.getLogger(ConfigurationUtility.class);
	static String pathToTmpFolderByArg = null;
	static String stringExternalizationFolderByArg = null;
	static String stringExternalizationFolder = "/src/test/java/jl/project/";
	static String stringExternalizationFileName = "StringExternalization.java";
	
	/***
	 * The arguments are used for scripting. 
	 * The first expected argument is the path to the tmp folder	 
	 */
	public static void main(String[] args) 
	{
		if (args.length!=0)
		{
			ifDebugEnabledog(logger, "Value for arg[0]: %s", args[0]);
			pathToTmpFolderByArg = args[0];
			ifDebugEnabledog(logger,"Value for arg[1]: %s", args[1]);
			stringExternalizationFolderByArg = args[1];
		}
		
		
		String tab = "\t";
		String webdriversValueWindows = tab +"public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver.exe\";"+ System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver.exe\"; "+ System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver.exe\"; "+ System.lineSeparator()
		+ tab + "// The value for WEBDRIVER_CHROME_ON_ANDROID_VALUE doesn't matter"+ System.lineSeparator()
		+ tab + "// as long as the webdriver is in the webdriver folder."+ System.lineSeparator()
		+ tab + "// Automatixc research of webdrrivers with "+ System.lineSeparator()
		+ tab + "// capabilities.setCapability[\"chromedriverExecutableDir\",\"../webdrivers/\"];"+ System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_CHROME_ON_ANDROID_VALUE = \"Chrome on Android\";" + System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_SAFARI_ON_IOS_VALUE = \"N.A.\";";
		String webdriversValueMacOS = tab+"public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver\"; "+ System.lineSeparator()
		+ tab +  "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver\"; "+ System.lineSeparator()
		+ tab +  "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver\"; "+ System.lineSeparator()
		+ tab + "// The value for WEBDRIVER_CHROME_ON_ANDROID_VALUE doesn't matter"+ System.lineSeparator()
		+ tab + "// as long as the webdriver is in the webdriver folder."+ System.lineSeparator()
		+ tab + "// Automatixc research of webdrrivers with "+ System.lineSeparator()
		+ tab + "// capabilities.setCapability[\"chromedriverExecutableDir\",\"../webdrivers/\"];"+ System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_CHROME_ON_ANDROID_VALUE = \"Chrome on Android\";" + System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_SAFARI_ON_IOS_VALUE = \"Safari on iOS\";" + System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_SAFARI_VALUE = \"Using XCUITest\" ; "
				+ "//\"Appium's primary support for automating iOS apps is via the XCUITest driver. \"";		
		String webdriversValueLinux = tab+"public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver\"; "+ System.lineSeparator()
		+ tab +  "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver\"; "+ System.lineSeparator()
		+ tab +  "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver\"; "+ System.lineSeparator()
		+ tab + "// The value for WEBDRIVER_CHROME_ON_ANDROID_VALUE doesn't matter"+ System.lineSeparator()
		+ tab + "// as long as the webdriver is in the webdriver folder."+ System.lineSeparator()
		+ tab + "// Automatixc research of webdrrivers with "+ System.lineSeparator()
		+ tab + "// capabilities.setCapability[\"chromedriverExecutableDir\",\"../webdrivers/\"];" + System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_CHROME_ON_ANDROID_VALUE = \"Chrome on Android\";" + System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_SAFARI_ON_IOS_VALUE = \"N.A.\";";
		
		String seleniumGridNotUsed = tab +  "public static final boolean GRID_NOT_USED = true;";
		
		String angularServer = tab +  "public static final String ANGULAR_SERVER_URL = \"http://localhost:4200\";";
		
		try 
		{
			replaceTagByData("webdrivers",stringExternalizationFolder, stringExternalizationFileName,webdriversValueWindows,webdriversValueMacOS,webdriversValueLinux);
		
			replaceTagByData("seleniumGrid4",stringExternalizationFolder, stringExternalizationFileName,seleniumGridNotUsed,seleniumGridNotUsed,seleniumGridNotUsed);
		
			replaceTagByData("angularServer",stringExternalizationFolder, stringExternalizationFileName,angularServer,angularServer,angularServer);
		
		} 
		catch (UnrecognizedOSException e) 
		{
			logger.debug(StringExternalization.EXCEPTION_IO);
			//e.printStackTrace //Commented to avoid a security hostpot
		}
	}
	
	/***
	 * A method used to insert configuration data in a file. 
	 * An opening and closing tag in the file locates the position where the information should be inserted.
	 * 
	 * @param tagValue:			the tag used to locate the information.
	 * @param file_folder: 		the folder containing the file with the configuration information. 
	 * 							The path to the folder is relative to the root folder of the application.
	 * @param file_name: 		the name of the file with the configuration information. 
	 * @param value_Windows: 	the configuration value to insert if the operating system is Windows 
	 * @param value_MacOS: 		the configuration value to insert if the operating system is macOS.
	 * @param value_Linux: 		the configuration value to insert if the operating system is Linux.
	 * @throws UnrecognizedOSException 
	 */
	public static void replaceTagByData(String tagValue, String fileFolder, String fileName, String valueWindows, String valueMacOS, String valueLinux) throws UnrecognizedOSException
	{
		//temporary folder
		String pathToTmpFolder ="tmp";
		if (pathToTmpFolderByArg !=null) pathToTmpFolder = pathToTmpFolderByArg;		
				
		if (stringExternalizationFolderByArg !=null) stringExternalizationFolder = stringExternalizationFolderByArg;
		else stringExternalizationFolder = fileFolder;
		
		Path fileSwpPath = FileSystems.getDefault().getPath(pathToTmpFolder, fileName+".txt");
		
		Path filePath = FileSystems.getDefault().getPath( System.getProperty("user.dir")+stringExternalizationFolder, fileName);
		
		
		List<String> lines = null; 		
		String openingTag = "//<"+tagValue+">";
		String closingTag = "//</"+tagValue+">";
		boolean isATag = false;
		boolean betweenTags = false;
		String osName = System.getProperty("os.name");
				
		
		try 
		{
			Files.deleteIfExists(fileSwpPath);
			Files.createFile(fileSwpPath);
			lines = Files.readAllLines(filePath);
	
			
			for(String line:lines)
			{	
				
				if(line.contains(openingTag))
				{
					betweenTags = true;
					isATag = true;
					
					
				}
				else if(line.contains(closingTag))
				{
					isATag = true;
					betweenTags = false;
					
					String confData="";
					
					if(osName.contains("Windows"))
					{
						confData =valueWindows;
					}
					else if (osName.contains("Mac OS"))
					{
						confData = valueMacOS;
					}
					else if (osName.contains("Linux"))
					{
						confData= valueLinux;
					}
					else 
					{
						ifDebugEnabledog(logger, "Unrecognized operating system: %s",osName);
						throw new UnrecognizedOSException(String.format("Unrecognized operating system: %s",osName));						
					}
					//Adding the configuration information
					line = confData + System.lineSeparator()+ line; 
					
				}
				else
				{
					isATag = false;
				}
				
				if(!betweenTags ||  isATag) 
				{
					line = line + System.lineSeparator();
					Files.write(fileSwpPath, line.getBytes(),StandardOpenOption.APPEND);
					logger.debug(line);
				}
			}			
			
			//Replacing the previous version of the file
			Files.copy(fileSwpPath, filePath, StandardCopyOption.REPLACE_EXISTING);
		} 
		catch (IOException e) 
		{
			logger.debug(StringExternalization.EXCEPTION_IO);			
			//e.printStackTrace absent to avoid a security hotspot 
		}		
	}
	
	static void ifDebugEnabledog(Logger logger, String msg, String data) 
	{
		if (logger.isDebugEnabled()) logger.debug(String.format(msg, data));
	}
	
}
