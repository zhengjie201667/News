package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.Comment;
import com.java1234.util.DateUtil;

public class CommentDao {
	
	public List<Comment> commentList(Connection con,Comment s_comment){
		List<Comment> commentList = new ArrayList<Comment>();
		StringBuffer sb = new StringBuffer("select * from t_comment");
		if (s_comment.getNewsId()!=-1) {
			sb.append(" and newsId="+s_comment.getNewsId());
		}
		sb.append(" order by commentDate desc");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setCommentId(rs.getInt("commentId"));
				comment.setContent(rs.getString("content"));
				comment.setNewsId(rs.getInt("newsId"));
				comment.setUserIP(rs.getString("userIP"));
				comment.setCommentDate(DateUtil.formatString(rs.getString("commentDate"), "yyyy-MM-dd HH:mm:ss"));
				commentList.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentList;
	}
	
	public int commentAdd(Connection con,Comment comment) throws Exception {
		String sql = "insert into t_comment values (null,?,?,?,now())";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, comment.getNewsId());
			pstmt.setString(2, comment.getContent());
			pstmt.setString(3, comment.getUserIP());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt.executeUpdate();
	}
}
