package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.dao.CommentDao;
import com.java1234.model.Comment;
import com.java1234.model.News;
import com.java1234.model.PageBean;
import com.java1234.util.DbUtil;
import com.java1234.util.NavUtil;
import com.java1234.util.PageUtil;
import com.java1234.util.PropertiesUtil;
import com.java1234.util.StringUtil;

public class CommentServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	DbUtil dbUtil=new DbUtil();
	CommentDao commentDao=new CommentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("save".equals(action)){
			commentSave(request,response);
		}
		
	}

	private void commentSave(HttpServletRequest request, HttpServletResponse response) {
		String newsId=request.getParameter("newsId");
		String content = request.getParameter("content");
		String userIP = request.getRemoteAddr();
		Comment comment = new Comment(Integer.parseInt(newsId), content, userIP);
		
		Connection con = null;
		
		try {
			con = dbUtil.getCon();
			commentDao.commentAdd(con, comment);
			request.getRequestDispatcher("news?action=show&newsId="+newsId).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e2) {
			}
		}

	}
	
}
