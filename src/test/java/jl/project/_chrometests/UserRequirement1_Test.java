	
package jl.project._chrometests;

import static org.assertj.core.api.Assertions.assertThat;


import java.awt.Robot;


import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;
import jl.project.__commontests.TestsUtilWithClicks;



/**
 * @author 
 *	Class testing the user requirement 1 of creating and deleting a category
 */
public class UserRequirement1_Test 
{
	Logger logger = LoggerFactory.getLogger(jl.project._chrometests.UserRequirement1_Test.class);
	WebDriver driver;	
	Robot robot;

	
	/**
	 * "The annotated method will be run before the first test method in the current class is invoked."  
	 * https://testng.org/doc/documentation-main.html
	 */
	@BeforeClass	
	public void setup() 
	{	
		
		robot = RobotFactory.getRobotInstance();
		
		driver = TestsUtilCommon.setup(logger,robot, StringExternalization.BROWSER_NAME_CHROME, driver, StringExternalization.WEBDRIVER_CHROME_KEY,StringExternalization.WEBDRIVER_CHROME_VALUE);
		
	}
	
	/**
	 * "The annotated method will be run before each test method"
	 * https://testng.org/doc/documentation-main.html
	 */
	@BeforeMethod	
	public void navigate() 
	{
		driver.get(StringExternalization.ANGULAR_SERVER_URL);		
	}
	
	/**
	 * Tests a successful creation of category
	 */
	@Test	
    public void createCategory_UsingClicks() 
	{
		boolean isCategoryFound = false;
		isCategoryFound = TestsUtilWithClicks.createCategory_UsingClicks(logger, driver, robot);
    	assertThat(isCategoryFound).isTrue();
    	
    }
	
	/**
	 * Tests a successful deletion of category	 
	 */
	@Test	
	public void deleteCategory_UsingClicks() 
	{
		boolean isCategoryFound = false;
		isCategoryFound = TestsUtilWithClicks.deleteCategory_UsingClicks(logger, driver, robot);
		assertThat(isCategoryFound).isFalse();		
	}	
	
	
	/**
	 * The annotated method will be run after all the test methods in the current class have been run.
	 * https://testng.org/doc/documentation-main.html 
	 */
	@AfterClass	
	public void releaseResources() 
	{
		TestsUtilCommon.release(driver);
	}

}
