package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.dao.LinkDao;
import com.java1234.util.DbUtil;
import com.java1234.util.NavUtil;
import com.java1234.util.StringUtil;

public class LinkServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil = new DbUtil();
	LinkDao linkDao = new LinkDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if ("preSave".equals(action)) {
			linkPreSave(request,response);
		} else if ("save".equals(action)) {
			linkSave(request,response);
		}
	}
	private void linkSave(HttpServletRequest request, HttpServletResponse response) {
		
		
	}
	private void linkPreSave(HttpServletRequest request, HttpServletResponse response) {
		String linkId = request.getParameter("linkId");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if (StringUtil.isNotEmpty(linkId)) {
				System.out.println("gengxincaozuo");
			}
			
			if (StringUtil.isNotEmpty(linkId)) {
				
			}else {
				request.setAttribute("navCode", NavUtil.genNewsManageNavigation("友情链接管理", "友情链接添加"));
				
			}
			request.setAttribute("mainPage", "/background/link/linkSave.jsp");
			request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeCon(con);
		}
	}
	
	
	
}
