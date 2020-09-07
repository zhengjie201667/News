package com.java1234.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	public static String getValue(String key) {
//		Properties properties = new Properties();
//		File file = new File("src/news.properties");
//		try {
//			InputStream iStream = new FileInputStream(file);
//			properties.load(iStream);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		InputStream iStream = new PropertiesUtil().getClass().getResourceAsStream("/news.properties");
//		return properties.getProperty(key);
		Properties prop=new Properties();
		InputStream in=new PropertiesUtil().getClass().getResourceAsStream("/news.properties");
		try {
			prop.load(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
	public static void main(String[] args) {
		System.out.println(getValue("jdbcName"));
	}
}
