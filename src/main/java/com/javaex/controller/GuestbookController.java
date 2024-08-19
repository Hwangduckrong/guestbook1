package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println(action);

		if ("addList".equals(action)) {

			// 접수
			System.out.println("리스트 요청");

			// db데이터 가져오기
			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestVo> guestList = guestbookDao.getGuestList();

			// 화면그리기 --> 포워드
			// request 에 리스트주소 넣기
			request.setAttribute("guestList", guestList);

			// 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/addList.jsp");
			rd.forward(request, response);

		} else if ("insert".equals(action)) {

			System.out.println("등록 요청 데이터 3개 저장해줘");

			// 나머지 파라미터 꺼내서 PersonVo로 묶기
			String name = request.getParameter("name");
			String pw = request.getParameter("password");
			String content = request.getParameter("content");

			GuestVo guestVo = new GuestVo(name, pw, content);

			// Dao를 메모리에 올린다.
			GuestbookDao guestbookDao = new GuestbookDao();

			// insertPerson(personVo) 사용해서 db에 저장한다.
			guestbookDao.insertGuest(guestVo);

			// 리다이렉트
			response.sendRedirect("/guestbook/gbc?action=addList");

		} else if ("deleteForm".equals(action)) {

			System.out.println("삭제 폼 요청");

			// 파라미터는 기본이 문자형이기때문에 형변환 필요
			int no = Integer.parseInt(request.getParameter("no"));

			GuestVo guestVo = new GuestVo(no);

			// 화면+데이터 수정폼
			// 리퀘스트 어트리뷰트 영역에 guestVo 주소를 담는다
			request.setAttribute("guestVo", guestVo);

			// 포워드 deleteForm.jsp
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteForm.jsp");
			rd.forward(request, response);

		} else if ("delete".equals(action)) {

			// 파라미터는 기본이 문자형이기때문에 형변환 필요
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");

			// Dao를 메모리에 올린다
			GuestbookDao guestbookDao = new GuestbookDao();

			// GuestbookDao를 통해서 삭제(delete)를 시킨다
			boolean delete = guestbookDao.deleteGuest(no, password);

			if (delete) { // 삭제된 데이터가 있는 경우
				
				/*
				 리다이렉트 response.sendRedirect("/guestbook/gbc?action=deleteForm");은 오류 발생함
				 response.sendRedirect()는 새로운 URL을 클라이언트에게 지시하는 역할만 하므로, 
				 클라이언트는 새로운 요청을 만들 때 이전 요청의 파라미터를 포함하지 않는다.
				 */
				response.sendRedirect("/guestbook/gbc?action=addList");
				
			} else {
				System.out.println("입력하신 비밀번호가 일치하지 않습니다.");
				
				// 리퀘스트 어트리뷰트 영역에 guestVo 주소를 담는다
				request.setAttribute("no", no);
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/error.jsp");
				rd.forward(request, response);
			}

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
