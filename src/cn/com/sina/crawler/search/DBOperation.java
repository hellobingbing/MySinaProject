package cn.com.sina.crawler.search;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * �������ݿ�
 * @author sina
 *
 */
class DBOperation {
	
	public static final String DBDRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/bigevents?"
			+ "useUnicode=true&characterEncoding=UTF8";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	// �����ݿ�����ȡ�������
	public static final String SQL_FROM = "SELECT * FROM bigevents WHERE two_level_class = ?";
	// ��ץȡ��������д�����ݿ�
	public static final String SQL_TO = "INSERT INTO relatednews(id,ntitle,ntime,ncontent,nurl)"
			+ "VALUES(?,?,?,?,?)";
	
	
	/**
	 * ���ݶ�����������ȡ�ô��¼�id��eventname��Map
	 * @param two_level_class ���¼�������������
	 * @return ���¼�id��eventname��Map
	 */
	public static Map<Integer, String> dataFromDB(String two_level_class){
		Map<Integer, String> IdAndName = new HashMap<Integer, String>();
		
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_FROM);
			pstmt.setString(1, two_level_class);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				IdAndName.put(rs.getInt("id"), rs.getString("eventname"));
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return IdAndName;
	}
	/**
	 * ��ץȡ��������д�����ݿ�
	 * @param someNews ������ŵļ���
	 */
	public static void dataToDB(List<List<String>> someNews){
		List<Integer> rowCount = new ArrayList<Integer>();
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_TO);
			for(int i = 0;i<someNews.size();i++){
				pstmt.setInt(1, Integer.parseInt(someNews.get(i).get(0)));
				pstmt.setString(2, someNews.get(i).get(1));
				pstmt.setString(3, someNews.get(i).get(2));
				pstmt.setString(4, someNews.get(i).get(3));
				pstmt.setString(5, someNews.get(i).get(4));
				result = pstmt.executeUpdate();
				if(result == 1){
					rowCount.add(result);
				}
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(rowCount.size());
	}
	
}
