package com.java1234.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public class DbUtil {
	
	public Connection getCon() {
		Connection connection = null;
		try {
			Class.forName(PropertiesUtil.getValue("jdbcName"));
			connection = DriverManager.getConnection(PropertiesUtil.getValue("dbUrl"), PropertiesUtil.getValue("dbUserName"), PropertiesUtil.getValue("dbPassword"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeCon(Connection con) {
		if (con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		dbUtil.getCon();
		System.out.println("连接成功");
	}
}
