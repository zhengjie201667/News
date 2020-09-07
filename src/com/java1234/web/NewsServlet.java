package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import com.java1234.dao.CommentDao;
import com.java1234.dao.NewsDao;
import com.java1234.dao.NewsTypeDao;
import com.java1234.model.Comment;
import com.java1234.model.News;
import com.java1234.model.PageBean;
import com.java1234.util.DbUtil;
import com.java1234.util.NavUtil;
import com.java1234.util.PageUtil;
import com.java1234.util.PropertiesUtil;
import com.java1234.util.StringUtil;

public class NewsServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	NewsDao newsDao = new NewsDao();
	NewsTypeDao newsTypeDao = new NewsTypeDao();
	CommentDao commentDao = new CommentDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resopnse) throws ServletException, IOException {
		this.doPost(request, resopnse);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resopnse) throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			String action = request.getParameter("action");
			if ("list".equals(action)) {
				this.newsList(request, resopnse);
			} else if ("show".equals(action)) {
				this.newsShow(request, resopnse);
			}
	}
	
	
	private void newsList(HttpServletRequest request, HttpServletResponse response) {
			String typeId=request.getParameter("typeId");
			String page = request.getParameter("page");
			if (StringUtil.isEmpty(page)) {
				page="1";
			}
			Connection con = null;
			News s_news = new News();
			if (StringUtil.isNotEmpty(typeId)) {
				s_news.setTypeId(Integer.parseInt(typeId));
			}
			try {
				con = dbUtil.getCon();
				int total = newsDao.newsCount(con, s_news);
				PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
				List<News> newestNewsListWithType = newsDao.newsList(con, s_news, pageBean);
								
				request.setAttribute("newestNewsListWithType", newestNewsListWithType);
				String typeName = newsTypeDao.getNewsTypeById(con, typeId).getTypeName();
				String navCode = NavUtil.getNewsListNavigation(typeName, typeId);
				request.setAttribute("navCode", navCode);
				request.setAttribute("pageCode", PageUtil.getUpAndDownPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")), typeId));
				request.setAttribute("mainPage", "news/newsList.jsp");
				request.getRequestDispatcher("foreground/newsTemp.jsp").forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					dbUtil.closeCon(con);
				} catch (Exception e2) {
				}
			}

		}
	
	private void newsShow(HttpServletRequest request, HttpServletResponse resopnse) {
		String newsId = request.getParameter("newsId");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			newsDao.newsClick(con, newsId);
			News news = newsDao.getNewsById(con, newsId);
			Comment s_comment = new Comment();
			s_comment.setNewsId(Integer.parseInt(newsId));
			List<Comment> commentList = commentDao.commentList(con, s_comment);
			request.setAttribute("commentList", commentList);
			for (Comment comment : commentList) {
				System.out.println(comment.getContent().toString());
			}
			request.setAttribute("news", news);
			String navCode = NavUtil.genNewsNavigation(news.getTypeName(), news.getTypeId()+"",news.getTitle());
			request.setAttribute("pageCode", this.getUpAndDownPageCode(newsDao.getUpAndDownPageId(con, newsId)));
			request.setAttribute("navCode", navCode);
			request.setAttribute("mainPage", "news/newsShow.jsp");
			request.getRequestDispatcher("foreground/newsTemp.jsp").forward(request, resopnse);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getUpAndDownPageCode(List<News> upAndDownPage) {
		News upNews = upAndDownPage.get(0);
		News dowNews = upAndDownPage.get(1);
		StringBuffer pageCode = new StringBuffer();
		if (upNews.getNewsId()==-1) {
			pageCode.append("<p>上一篇：没有了</p>");
		}else {
			pageCode.append("<p>上一篇：<a href='news?action=show&newsId="+upNews.getNewsId()+"'>"+upNews.getTitle()+"</p>");
		}
//		pageCode = new StringBuffer();
		if (dowNews.getNewsId()==-1) {
			pageCode.append("<p>下一篇：没有了</p>");
		}else {
			pageCode.append("<p>下一篇：<a href='news?action=show&newsId="+dowNews.getNewsId()+"'>"+dowNews.getTitle()+"</p>");
		}
		
		return pageCode.toString();
		
	}

}
