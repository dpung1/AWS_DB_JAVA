package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnectionMgr;

public class Main {
	
	public static void main(String[] args) {
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		// 변수 선언
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// Java와 DB를 연결함.
			con = pool.getConnection();
			
			// 실행할 쿼리문 작성 
			String sql = "select * from user_tb";
			
			// 작성한 쿼리문 가공
			pstmt = con.prepareStatement(sql);
			
			// 가공된 쿼리문 실행 -> 결과를 ResultSet로 변환
			rs = pstmt.executeQuery();
			
			// 결과가 담긴 ResultSet을 반복작업을 통해 데이터 조회
			System.out.println("번호\t|\t아이디\t|\t비밀번호");
			while(rs.next()) { // rs = 리스트 형식으로 담겨져있음 (next = 행(row)을 가져옴)
				// getInt() -> 정수
				// getString() -> 문자열
				System.out.println(rs.getInt(1) + " \t|\t " + rs.getString(2) + " \t|\t " + rs.getString(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 생성된 rs, pststm, con 객체 소멸(DB 연결 해제)
			pool.freeConnection(con, pstmt, rs);
		}
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		try {
			// DB 연결
			con = pool.getConnection();
			
			// 쿼리문 작성
			String sql = "insert into user_tb values(0, ?, ?)";
			
			// 쿼리문 가공 준비
			pstmt = con.prepareStatement(sql);
			
			// 쿼리문 가공
			pstmt.setString(1, "ttt");
			pstmt.setString(2, "1234");
			
			// 쿼리문 실행 
			int successCount = pstmt.executeUpdate(); 
			System.out.println("insert 성공 횟수 : " + successCount);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt);
		}
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		try {
			// Java와 DB를 연결함.
			con = pool.getConnection();
			
			// 실행할 쿼리문 작성 
			String sql = "select * from user_tb";
			
			// 작성한 쿼리문 가공
			pstmt = con.prepareStatement(sql);
			
			// 가공된 쿼리문 실행 -> 결과를 ResultSet로 변환
			rs = pstmt.executeQuery();
			
			// 결과가 담긴 ResultSet을 반복작업을 통해 데이터 조회
			System.out.println("번호\t|\t아이디\t|\t비밀번호");
			while(rs.next()) { // rs = 리스트 형식으로 담겨져있음 (next = 행(row)을 가져옴)
				// getInt() -> 정수
				// getString() -> 문자열
				System.out.println(rs.getInt(1) + " \t|\t " + rs.getString(2) + " \t|\t " + rs.getString(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 생성된 rs, pststm, con 객체 소멸(DB 연결 해제)
			pool.freeConnection(con, pstmt, rs);
			
		}
	}
}
