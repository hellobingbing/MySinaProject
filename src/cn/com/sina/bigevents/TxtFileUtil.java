package cn.com.sina.bigevents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
/**
 * ��Excel�������ݿ���ȡ���ı����ݴ���txt�ĵ��У����ڼ����Ƶ������
 * @author sina
 *
 */
public class TxtFileUtil {
	
	public static final String DBDRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/bigevents?"
			+ "useUnicode=true&characterEncoding=UTF8";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String SQL = "SELECT * FROM bigevents WHERE two_level_class = ?";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Ҫ�����Excel�ļ�·��
		//String ExcelPath = "";
		// Ҫд���txt�ĵ�·��
		String txtPath = "";
		
		/*
		try {
			getTextFromExcel(pathname,txtPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		getTextFromDB(txtPath);
		
	}
	/**
	 * ��Excel��ȡ���ı����ݣ���д��ָ��·���µ�txt��
	 * @param excelPath Excel�ļ�·��
	 * @param txtPath Txt�ļ�·��
	 * @throws BiffException
	 * @throws IOException
	 */
	public static void getTextFromExcel(String excelPath,String txtPath) throws BiffException,IOException{
		Workbook workbook = null;
		Sheet sheet = null;
		Cell cell = null;
		int index = 1;
		File file = new File(excelPath);
		InputStream inputStream = new FileInputStream(file);
		workbook = Workbook.getWorkbook(inputStream);
		sheet = workbook.getSheet("��ȴ��¼�--չ��");
		for(int i = 1;i<sheet.getRows();i++){
			cell = sheet.getCell(7, i);
			File desfile = new File(txtPath + File.separator + index + ".txt");
			OutputStream outputStream = new FileOutputStream(desfile);
			outputStream.write(cell.getContents().getBytes());
			
			workbook.close();
			outputStream.close();
			index++;
		}
	}
	/**
	 * �����ݿ���ȡ���ı����ݣ���д��ָ��·���µ�txt��
	 * @param txtPath Txt�ļ�·��
	 */
	public static void getTextFromDB(String txtPath){
		int index = 1;
		//������������
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "�Ƽ�����");
			rs = pstmt.executeQuery();
			while(rs.next()){
				File file = new File(txtPath + File.separator + index + ".txt");
				OutputStream outputStream = new FileOutputStream(file);
				outputStream.write(rs.getString("description").getBytes());
				index++;
				outputStream.close();
			}
			conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
