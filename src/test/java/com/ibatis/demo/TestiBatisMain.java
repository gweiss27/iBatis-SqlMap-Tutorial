package com.ibatis.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * Unit test for simple App.
 */
public class TestiBatisMain {
	
	// 1. Create variables to hold location of SQLMapConfig file and its properties file which will supply data to it
	
	public static final String SQL_PROPERTY_LOCATION = "META-INF/ibatis.properties";
	public static final String SQL_CONFIG_LOCATION = "META-INF/SqlMapConfig.xml";
	
	// 2. The SqlMapClient is the central class for working with SQL Maps.
	// This class will allow us to run mapped statements (select, insert, update, delete etc.),
	// Once we have an SqlMapClient instance, everything we need to work with SQL Maps is easily available.
		
	public static SqlMapClient sqlMapClient;
	
    @BeforeTest
    public static void configureiBatis() throws FileNotFoundException, IOException {
    	try {
    		
			// 3.1 Create reference variable of Configuration interface and assign implementing 
    		// class 'PropertiesConfiguration' object to it
			// Using this reference variable we can read 'ibatis.properties' file value
    		
    		Configuration sqlConfig = new PropertiesConfiguration(SQL_PROPERTY_LOCATION);
    		
			// 3.2 Properties to be used to provide values to dynamic property tokens present in the 
    		// sql-map-config.xml configuration file.
    		
    		Properties databaseProperty = new Properties();
    		databaseProperty.put("driver", sqlConfig.getString("driver").trim());
    		databaseProperty.put("jdbcURL", sqlConfig.getString("jdbcURL").trim());
    		databaseProperty.put("username", sqlConfig.getString("username").trim());
    		databaseProperty.put("password", sqlConfig.getString("password").trim());
    		
    		System.out.println( "Creating MySQL Database connection using"
    				+ "\n username :: " + databaseProperty.getProperty("username")
    				+ "\n password :: " + databaseProperty.getProperty("password")
    				+ "\n jdbcURL  :: " + databaseProperty.getProperty("jdbcURL")
    				+ "\n driver   :: " + databaseProperty.getProperty("driver"));
    		
    		// 3.3 Creating a Reader instance that reads an sql-map-config.xml file
    		Reader reader = Resources.getResourceAsReader(SQL_CONFIG_LOCATION);

    		// The SqlMapClientBuilder class is responsible for parsing configuration documents and building the SqlMapClient instance.
    		// Its current implementation works with XML configuration files
    		// Building an SqlMapClient using the specified reader and properties file.
    		
    		sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader, databaseProperty);
    		System.out.println("SqlMapClient setup successfully");
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		
    	}
    }
    
//    @Test(enabled=false)
    @Test
    public static void testSelectQueryUsingiBatis() throws FileNotFoundException, IOException, SQLException {
    	System.out.println(" testSelectQueryUsingiBatis executed");
    	if (sqlMapClient != null) {
    		try {
    			List<Employee> results = (List<Employee>) sqlMapClient.queryForList("common.getEmpDetails", null);
                Employee em = results.get(0);
    			System.out.println("emp_name from db : " + em.getEmpName());
    			System.out.println();
    			System.out.println("emp_salary from db : " + em.getEmpSalary());
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
    }
    
    
//    @Test(enabled=false)
    @Test
    public static void testInsertQueryUsingiBatis() throws FileNotFoundException, IOException, SQLException {
    	System.out.println(" testInsertQueryUsingiBatis executed");
    	Map<String, Object> searchparams = new HashMap<String, Object>();
    	searchparams.put("emp_id", 4);
    	searchparams.put("emp_name", "SpiceCabs");
    	searchparams.put("emp_salary", 999);
    	if (sqlMapClient != null) {
    		try {
    			sqlMapClient.insert("common.addEmpDetails", searchparams);
				System.out.println("|Record Inserted Successfully|");
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
//    @Test(enabled=false)
    @Test
    public static void testUpdateQueryUsingiBatis() throws FileNotFoundException, IOException, SQLException {
    	System.out.println(" testUpdateQueryUsingiBatis executed");
    	Map<String, Object> searchparams = new HashMap<String, Object>();
    	searchparams.put("id", 4);
    	searchparams.put("salary", 777);
    	if (sqlMapClient != null) {
    		try {
    			sqlMapClient.update("common.updateEmpDetails", searchparams);
				System.out.println("|Record updated Successfully|");
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
//    @Test(enabled=false)
    @Test
    public static void testDeleteQueryUsingiBatis() throws FileNotFoundException, IOException, SQLException {
    	System.out.println(" testDeleteQueryUsingiBatis executed");
    	Map<String, Object> searchparams = new HashMap<String, Object>();
    	searchparams.put("id", 1);
    	if (sqlMapClient != null) {
    		try {
    			sqlMapClient.delete("common.deleteEmpDetails", searchparams);
				System.out.println("|Record deleted Successfully|");
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }    
	
}
