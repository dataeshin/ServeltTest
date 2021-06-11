package com.mvc.web.controller.content;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.web.entity.content.EtcList;
import com.mvc.web.entity.content.Notice;
import com.mvc.web.service.ContentDAO;

@WebServlet("/board/content/list")
public class ContentListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String UserId= req.getSession().getAttribute("userID").toString();
		String UserNm = req.getSession().getAttribute("UserNm").toString();
		String UserRank=req.getSession().getAttribute("UserRank").toString();	
		
		System.out.println("content-userid : "+ UserId);
		System.out.println("content-usernm : "+ UserNm);
		System.out.println("content-userrank : "+ UserRank);
		
		int page=1;
		String page_=req.getParameter("p");
		String field_ =req.getParameter("f");
		String qurry_ = req.getParameter("q");
		String field = "title";
		String qurry = "";
		
				
				
		if(page_!=null && !page_.equals("")) {
			page=Integer.parseInt(page_);
		}
		if(qurry_ !=null && !qurry_.equals("")) {
				qurry = qurry_;
		}
		
		if(field_ !=null && !field_.equals("")) {
				field = field_;
			}
				
		//int count = ContentDAO.getInstance().getCount(field, qurry, userrank);
		
		
		
		
		EtcList el = ContentDAO.getInstance().getList(page, field,  qurry, UserRank);
		int count=el.getCount();
		
		List<Notice>list=el.getList();
		
	
		req.setAttribute("name", UserNm);
		req.setAttribute("count", count);
		req.setAttribute("list", list);
		String str =req.getParameter("cnt");

		req.getRequestDispatcher("/WEB-INF/board/content/noticeList.jsp").forward(req, resp);
	}
}
