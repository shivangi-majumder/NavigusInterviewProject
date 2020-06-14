package com.shivangi.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.shivangi.exception.NavigusException;


public class DBUtil {
	  private static Logger myLogger;
      private static Connection connection;
      static{

  		PropertyConfigurator.configure("resources/log4j.properties");
  		myLogger=Logger.getLogger("DBUtil.class");
  	  }
       
      private static Properties loadProp() 
    		  throws NavigusException {
    	  Properties myProp=new Properties();
  		try (FileInputStream fis=
  			new FileInputStream("resources/jdbc.properties");){  			
  			myProp.load(fis);
  			//myLogger.info("Property File loaded : ");	
  		} 
  		catch (Exception e){
  			//myLogger.error("Property File Not loaded");	
  			throw new NavigusException(e.getMessage());
  		}
  		return myProp;
	}
      public static Connection getConnection() 
    		  throws NavigusException {
    	  String url,username, password;
    	  try {
    		//creating properties and load the properties
    			Properties prop=DBUtil.loadProp();    		

    		 // driver = prop.getProperty("classname");
    		  url = prop.getProperty("url");
    		  username = prop.getProperty("username");
    		  password = prop.getProperty("password");
    		  
    		//  Class.forName(driver);
    		  
    		  //getConnection
    		  connection=DriverManager
    				  .getConnection(url, username, password);
    		  if(connection!=null){
    			 
    			  myLogger.info("connection Obtained! : "
    					  	+connection);				
    			  
    		  }
    		  else{
    			  throw new NavigusException(
    					  "Sorry!!! Something went wrong"
      			  		+ " while obtaining connection");
    		  }
    	  }catch(Exception e) {
    		  throw new NavigusException(e.getMessage());
    	  }
    	   return connection;  
      }
   
	public static Connection closeConnection() throws NavigusException {
    	  try {
    		  if(connection !=null) {
    			  connection.close();
    			  myLogger.info("Closing Connection");
    		  }
    		  else
    			  myLogger.error("Connection already closed");
    	  } catch (Exception e) {
    		  myLogger.error("Connection already closed");	
    		  throw new NavigusException(e.getMessage());
    	  }
		return connection;
      }
}
