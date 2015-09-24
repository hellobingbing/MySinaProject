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
 * 从Excel或者数据库中取得文本数据存入txt文档中，便于计算词频等特征
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
		
		// 要读入的Excel文件路径
		//String ExcelPath = "";
		// 要写入的txt文档路径
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
	 * 从Excel中取得文本数据，并写入指定路径下的txt中
	 * @param excelPath Excel文件路径
	 * @param txtPath Txt文件路径
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
		sheet = workbook.getSheet("年度大事件--展览");
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
	 * 从数据库中取得文本数据，并写入指定路径下的txt中
	 * @param txtPath Txt文件路径
	 */
	public static void getTextFromDB(String txtPath){
		int index = 1;
		//加载驱动程序
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
			pstmt.setString(1, "科技发明");
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
