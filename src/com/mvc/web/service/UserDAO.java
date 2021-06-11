package com.mvc.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mvc.web.connection.ConnectionProvider;
import com.mvc.web.entity.content.Notice;
import com.mvc.web.entity.user.Login;
import com.mvc.web.entity.user.Register;
import com.mvc.web.entity.user.User;

public class UserDAO {

	private static UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}

	public User loginCheck(Login lg) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		User ur=new User();

		String sql = "select userid, userpass, username, useremail, userrank "
				+ "	from user "
				+ "  where flag='Y' "
				+ "   and userid=? "
				+ "   and userpass=SHA2(?,256) ";
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, lg.getId());
			psmt.setString(2, lg.getPass());
			rs = psmt.executeQuery();

			if (rs.next()) {	//아이디 패스워드 일치
					ur.setId(lg.getId());
					ur.setName(rs.getString("username"));
					ur.setRank(rs.getString("userrank"));
					ur.setEmail(rs.getString("useremail"));
					ur.setNumber(1);
			}else {	
				ur.setNumber(0);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ur;
	}

	public int idCheck(String pid) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		int result = 0;

		String sql = "select userID from user where userID=?";

		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pid);
			rs = psmt.executeQuery();

			if (rs.next()) {
				if (rs.next()){
					result=1;

				} else {
					result=0;
			
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 사용자 추가
	public int signUp(Register rt) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "insert into user (userID, userPass, userName, userEmail) "
				+ "values(?,SHA2(?,256),?,?) ";
		try {
			con = ConnectionProvider.getConnection();			
			psmt = con.prepareStatement(sql);
			psmt.setString(1, rt.getId());
			psmt.setString(2, rt.getPass());
			psmt.setString(3, rt.getName());
			psmt.setString(4, rt.getEmail());
			System.out.println("pstm aaaaaaaaa:" + psmt);
			result = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
