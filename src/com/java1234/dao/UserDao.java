package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.java1234.model.User;

public class UserDao {
	
	public User login(Connection con, User user) {
		User resultUser = null;
		String sql = "select * from t_user where userName=? and password=?";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				resultUser=new User();
				resultUser.setUserName(rs.getString("userName"));
				resultUser.setUserId(rs.getInt("userId"));
				resultUser.setUserName(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultUser;
		
	}
}
