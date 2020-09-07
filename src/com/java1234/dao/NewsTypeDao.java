package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.NewsType;

public class NewsTypeDao {

	public List<NewsType> newsTypeList(Connection con)throws Exception{
		List<NewsType> newsTypeList=new ArrayList<NewsType>();
		String sql="select * from t_newsType";
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			NewsType newsType=new NewsType();
			newsType.setNewsTypeId(rs.getInt("newsTypeId"));
			newsType.setTypeName(rs.getString("typeName"));
			newsTypeList.add(newsType);
		}
		return newsTypeList;
	}
	
	public  NewsType getNewsTypeById(Connection con,String newsTypeId) {
		NewsType newsType = new NewsType();
		String sql = "select * from t_newsType where newsTypeId=?";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newsTypeId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				newsType.setNewsTypeId(rs.getInt("newsTypeId"));
				newsType.setTypeName(rs.getString("typeName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newsType;

	}
}
