package com.java1234.web;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.java1234.dao.NewsDao;
import com.java1234.dao.NewsTypeDao;
import com.java1234.model.News;
import com.java1234.model.NewsType;
import com.java1234.util.DbUtil;

public class InitServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	NewsDao newsDao=new NewsDao();
	NewsTypeDao newsTypeDao=new NewsTypeDao();

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext application=config.getServletContext();
		this.refreshSystem(application);
	}
	
	private void refreshSystem(ServletContext application){
		Connection con=null;
		try{
			con=dbUtil.getCon();
			
			List<NewsType> newsTypeList=newsTypeDao.newsTypeList(con);
			application.setAttribute("newsTypeList", newsTypeList);
			
			String sql="select * from t_news order by publishDate desc limit 0,8 ";
			List<News> newestNewsList=newsDao.newsList(con, sql);
			application.setAttribute("newestNewsList", newestNewsList);
			
			sql="select * from t_news order by click desc limit 0,8";
			List<News> hotNewsList=newsDao.newsList(con, sql);
			application.setAttribute("hotNewsList", hotNewsList);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
