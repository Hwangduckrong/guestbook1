package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.javaex.vo.GuestVo;

public class GuestbookDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/guestbook_db";
	private String id = "guest";
	private String pw = "guest";

	// 생성자
	// 기본생성자 사용(그래서 생략)

	// 메소드 gs
	// 필드값을 외부에서 사용하면 안됨(그래서 생략)

	// 메소드 일반
	// DB연결 메소드
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원정리 메소드
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 게스트 정보 삭제하기
	public boolean deleteGuest(int no, String password) {

		boolean delete = false;

		int count = -1;
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *SQL문 준비
			String query = "";
			query += " delete from guest ";
			query += " where password = ? ";
			query += " and no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, password);
			pstmt.setInt(2, no);

			// 실행
			count = pstmt.executeUpdate();

			// 4. 결과처리
			if (count > 0) {
				delete = true;
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return delete;
	}

	// 게스트 정보 저장
	public int insertGuest(GuestVo guestVo) {

		int count = -1;
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *SQL문 준비
			String query = "";
			query += " insert into guest (name, password, content) ";
			query += " values (?, ?, ?) ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());

			// 실행
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}

	// 리스트 가져오기
	public List<GuestVo> getGuestList() {

		List<GuestVo> guestList = new ArrayList<GuestVo>();

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// *SQL문 준비
			String query = "";
			query += " select no, ";
			query += " 		  name, ";
			query += "		  password, ";
			query += " 		  content, ";
			query += " 		  reg_date ";
			query += " from guest ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			// 4. 결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String pw = rs.getString("password");
				String content = rs.getString("content");
				String date = rs.getString("reg_date");

				GuestVo guestVo = new GuestVo(no, name, pw, content, date);
				guestList.add(guestVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return guestList;

	}

}
