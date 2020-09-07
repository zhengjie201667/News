package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.News;
import com.java1234.model.PageBean;
import com.java1234.util.DateUtil;
import com.java1234.util.PropertiesUtil;

public class NewsDao {

	public List<News> newsList(Connection con,String sql)throws Exception{
		List<News> newsList=new ArrayList<News>();
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			News news=new News();
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeId(rs.getInt("typeId"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
			newsList.add(news);
		}
		return newsList;
	}
	
	public List<News> newsList(Connection con,News s_news,PageBean pageBean)throws Exception{
		List<News> newsList=new ArrayList<News>();
		StringBuffer sb=new StringBuffer("select * from t_news t1,t_newsType t2 where t1.typeId=t2.newsTypeId ");
		if(s_news.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_news.getTypeId());
		}
		sb.append(" order by t1.publishDate desc ");
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			News news=new News();
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeId(rs.getInt("typeId"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
			newsList.add(news);
		}
		return newsList;
	}
	
	public int newsCount(Connection con,News s_news) throws SQLException {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_news");
		if (s_news.getTypeId()!=-1) {
			sb.append(" and typeId="+s_news.getTypeId());
		}
		PreparedStatement pstmt;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
			rs = pstmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			return rs.getInt("total");
		}else {
			return 0;
		}
	}
	
	public News getNewsById(Connection con,String newsId) throws Exception {
		String sql = "select * from t_news t1,t_newsType t2 where t1.typeId=t2.newsTypeId and t1.newsId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		ResultSet rs = pstmt.executeQuery();
		News news = new News();
		if (rs.next()) {
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeName(rs.getString("typeName"));
			news.setTypeId(rs.getInt("typeId"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
		}
		return news;
	}
	
	public int newsClick(Connection con,String newsId) {
		String sql = "update t_news set click = click +1 where newsId =?";
		PreparedStatement pstmt;
		int num = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newsId);
			num = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return num;
	}
	
	public List<News> getUpAndDownPageId(Connection con,String newsId){
		List<News> upAndDownPage = new ArrayList<>();
		String sql = "select * from t_news where newsId<? order by newsId desc limit 1";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newsId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				upAndDownPage.add(new News(rs.getInt("newsId"), rs.getString("title")));
			}else {
				upAndDownPage.add(new News(-1, ""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		sql = "select * from t_news where newsId>? order by newsId asc limit 1";	
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newsId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				upAndDownPage.add(new News(rs.getInt("newsId"), rs.getString("title")));
			}else {
				upAndDownPage.add(new News(-1, ""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return upAndDownPage;
	}
}
