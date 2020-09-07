package com.java1234.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.java1234.model.Link;


public class LinkDao {
	public List<Link> linkList(Connection con,String sql) {
		List<Link> linkList = new ArrayList<>();
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Link link = new Link();
				link.setLinkId(rs.getInt("linkId"));
				link.setLinkName(rs.getString("linkName"));
				link.setLinkUrl(rs.getString("linkUrl"));
				link.setLinkEmail(rs.getString("LinkEmail"));
				link.setOrderNum(rs.getInt("OrderNum"));
				linkList.add(link);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return linkList;
   }
	
	public int linkAdd(Connection con, Link link) throws Exception {
		String sql = "insert into t_link values(null,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, link.getLinkName());
		pstmt.setString(2, link.getLinkUrl());
		pstmt.setString(3, link.getLinkEmail());
		pstmt.setInt(4, link.getOrderNum());
		return pstmt.executeUpdate();
	}
}
