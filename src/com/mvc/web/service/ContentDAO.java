package com.mvc.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mvc.web.connection.ConnectionProvider;
import com.mvc.web.connection.jdbcUtil;
import com.mvc.web.entity.content.EtcList;
import com.mvc.web.entity.content.Notice;

public class ContentDAO {
	
	
	
	final private String url = "jdbc:mysql://localhost:3306/study1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	final private String id = "root";
	final private String pass = "1234";
	final private String driver = "com.mysql.jdbc.Driver";
	private int result;
	
	private static ContentDAO instance =new ContentDAO();
	
	
	public static ContentDAO getInstance() {
		return instance;
	}
	
	
//	public List<Notice> getList(){
//		return getList(1, "");
//	}
//	
//	public List<Notice> getList(int page){
//		return getList(page, "");
//	}

	/* 글 목록 */
	public EtcList getList(int page, String field, String qurry, String userrank) {
		Connection con=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
		int start = 1 + (page - 1) * 10;
		int end = page * 10;
		int count=0;

		String sql = "select * , (select count(id) as count "
				 + "             from tbl_board "
				 + "              where "+field+" like ? "
				 + "              and useFlag ='Y' "
				 + "               and boardid in (select boardID " 
				 + "   	        from user_auth "
				 + "               where rankcd= ?))as count "
				 + "	 from (select @rownum:=@rownum+1 as num ,n.*  "
				 + "	         from( select * "
				 + "	              from tbl_board "
				 + "		   where "+field+" like ? " 
				 + "			and useFlag ='Y' "
				 + "            and boardid in (select boardID "
				 + "			 from user_auth "
				 + "             where rankcd= ?) "
				 + "	       order by regdate desc)n "
				 + "	         where (@rownum:=0)=0)num "
				 + "	where num.num between ? and ? " ; // 조회 sql
		List<Notice> list = new ArrayList<>(); // list 배열 생성

		try {
			Class.forName(driver); // 필드 값으로 driver 명칭 선언
			con = DriverManager.getConnection(url, id, pass);
			psmt = con.prepareStatement(sql);
			psmt.setString(1, "%" + qurry + "%");
			psmt.setString(2, userrank);
			psmt.setString(3, "%" + qurry + "%");
			psmt.setString(4, userrank);
			psmt.setInt(5, start);
			psmt.setInt(6, end);
			System.out.println(psmt);
			rs = psmt.executeQuery();

			while (rs.next()) {
				int id1 = rs.getInt("id");
				String boardid = rs.getString("boardid");
				String title = rs.getString("title");
				String writeid = rs.getString("writeid");
				String content = rs.getString("content");
				Date regdate = rs.getTimestamp("regdate");
				int hit = rs.getInt("hit");
				count=rs.getInt("count");
				// 조회 된 값을 입력하여 초기화하는 생성자 생성
				Notice ns = new Notice(id1, title, writeid, content, regdate, hit);
				// list 에 조회된 값이 저장된 notice 객체 추가
				
				
				list.add(ns);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUtil.close(con);
			jdbcUtil.close(psmt);
			jdbcUtil.close(rs);
		}
		EtcList el=new EtcList(count, list);
		
		return el;
	}

	/* 자세히 보기 */
	public Notice getDetail(int no) {
		Notice ns = null;
		String sql = " SELECT tb.id, bm.board_name, tb.title, tb.writeid, tb.content, tb.regdate, tb.hit "
				+ "				      FROM tbl_board tb, "
				+ "					          board_master bm "
				+ "				     WHERE bm.board_id = tb.boardid "
				+ "				       AND tb.useFlag = 'Y' "
				+ "				       AND tb.id = ?  ";
		try {
			Class.forName(driver); // 필드 값으로 driver 명칭 선언
			Connection con = DriverManager.getConnection(url, id, pass);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, no);
			ResultSet rs = psmt.executeQuery();

			if (rs.next()) {
				String board =rs.getString("board_name");
				int id1 = rs.getInt("id");
				String title = rs.getString("title");
				String writeid = rs.getString("writeid");
				String content = rs.getString("content");
				Date regdate = rs.getTimestamp("regdate");
				int hit = rs.getInt("hit");

				ns = new Notice(id1, title, writeid, content, regdate, hit, board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ns;
	}

	// 조회된 글 카운
	public int getCount(String field, String qurry, String rank  ) {
		int count = 0;

		String sql = "select count(*) as count " + " from tbl_board " + " where useFlag='Y' " + " and " + field
				+ " like ? ";

		try {
			Class.forName(driver); // 필드 값으로 driver 명칭 선언
			Connection con = DriverManager.getConnection(url, id, pass);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, "%" + qurry + "%");
			System.out.println(psmt);
			ResultSet rs = psmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
				System.out.println("Servic:" + count);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}

	public int regeditNotice(String writeid, String title, String content) {
		String sql = "insert into tbl_board(title, writeid, content) values(?,?,?)";
			
		try {
			Class.forName(driver); // 필드 값으로 driver 명칭 선언
			Connection con = DriverManager.getConnection(url, id, pass);
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, writeid);
			psmt.setString(3, content);
			
			result = psmt.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//글수정
	public int updateContent(int pid, String userID, String title, String content) {
		int result=0;
		Connection con=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
		String sql ="update tbl_board set title = ? , content= ? , writeid= ? where id= ? ";
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setString(3, userID);
			psmt.setInt(4, pid);
			System.out.println(psmt);
			result= psmt.executeUpdate();

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUtil.close(con);
			jdbcUtil.close(psmt);
			
		}
		
		return result;
	}


	
		
	

	//id check
	

}
