package com.mvc.web.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mvc.web.entity.user.Login;
import com.mvc.web.entity.user.User;
import com.mvc.web.service.UserDAO;

@WebServlet("/user/login")
public class LoginController extends HttpServlet {
	private static final Integer cookieExpire = 60 * 60 * 24 * 30; // 1 month

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pid = req.getParameter("id");
		String ppass = req.getParameter("pass");
		String remember = req.getParameter("remember");
		
		Login lg=new Login(pid, ppass);
		
		
		
		UserDAO ud = new UserDAO();

		
		User ur = (UserDAO.getInstance().loginCheck(lg));
		int result= ur.getNumber();
		System.out.println("result"+result);
		switch (result) {
		case -1:
			// login 창으로 다시 보낸다.
			req.setAttribute("ment", "존재하지 않는 ID 입니다.");
			doGet(req, resp);
			break;

		case 0:
			// login 창으로 다시 보낸다.
			req.setAttribute("ment", "PASSWORD 가 틀렸습니다.");
			doGet(req, resp);
			break;

		case 1:// 로그인 성공
				// 리스트 보낸다.
			
			String userId=ur.getId();
			String userNm=ur.getName();
			String userRank=ur.getRank();
			String userEmail=ur.getEmail();
			

			HttpSession session = req.getSession();
			session.setAttribute("userID",userId);
			session.setAttribute("UserNm",userNm);
			session.setAttribute("UserRank",userRank);
			session.setAttribute("UserEmail",userEmail);
			

			if (remember!=null){
				setCookie("sid", pid, resp);
			} else {
				setCookie("sid", "", resp);
			}

			String UserNm = (req.getSession().getAttribute("UserNm")).toString();

			System.out.println("세션 : " + (req.getSession().getAttribute("UserNm")).toString());
			System.out.println("세션 : " + (req.getSession().getAttribute("userID")).toString());
			System.out.println("세션 : " + (req.getSession().getAttribute("UserRank")).toString());
			System.out.println("세션 : " + (req.getSession().getAttribute("UserEmail")).toString());

			

			resp.sendRedirect("/board/content/list");
			break;
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userID = get_Cookie("sid", req);
		req.setAttribute("id", userID);

		req.getRequestDispatcher("/WEB-INF/board/user/login.jsp").forward(req, resp);
	}

	// 쿠키불러오기
	private String get_Cookie(String cid, HttpServletRequest req) {
		String ret = "";

		if (req == null) {
			return ret;
		}

		Cookie[] cookies = req.getCookies();
		if (cookies == null) {
			return ret;
		}

		for (Cookie ck : cookies) {
			if (ck.getName().equals(cid)) {
				System.out.println("ck.getName" + ck.getName());
				System.out.println("ck.getValue: " + ck.getValue());
				System.out.println("ret" + ret);
				ret = ck.getValue();
				ck.setMaxAge(cookieExpire);
				break;
			}
		}

		return null;
	}

	// 쿠키생성
	private void setCookie(String cid, String pid, HttpServletResponse resp) {
		Cookie ck = new Cookie(cid, pid); // sid,pid(접속한사람id)
		ck.setPath("/");
		ck.setMaxAge(cookieExpire);
		resp.addCookie(ck);

	}
}
