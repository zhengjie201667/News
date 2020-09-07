package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java1234.dao.UserDao;
import com.java1234.model.User;
import com.java1234.util.DbUtil;

public class UserServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil = new DbUtil();
	UserDao userDao = new UserDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("login".equals(action)){
			login(request,response);
		} else if ("logout".equals(action)) {
			logout(request,response);
		}
		
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) {

		request.getSession().invalidate();
		try {
			response.sendRedirect(request.getContextPath()+"/background/login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			HttpSession session = request.getSession();
			User user = new User(userName, password);
			User currentUser = userDao.login(con, user);
			if (currentUser==null) {
				request.setAttribute("error", "用户名或密码错误");
				request.setAttribute("password", password);
				request.setAttribute("userName", userName);
				request.getRequestDispatcher("/background/login.jsp").forward(request, response);
			} else {
				session.setAttribute("currentUser", currentUser);
				request.setAttribute("mainPage", "/background/default.jsp");
				request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
