package cn.com.sina.bigevents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * �õ�һ��ʱ����ڵ����д��¼�
 * @author sina
 *
 */
public class BigeventsOfPeriod {
	
	public static final String DBDRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/bigevents?"
			+ "useUnicode=true&characterEncoding=UTF8";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	//���ݹ�������ȷ������ȡ�����ݿ��е���ؼ�¼
	public static final String SQL_EXACTTIME = "SELECT * FROM bigevents WHERE exacttime BETWEEN ? AND ?"
			+ "OR exacttime BETWEEN ? AND ?";
	//���ݲ���ȷ�����ڵõ����ݿ��е���ؼ�¼
	public static final String SQL_INEXACTTIME = "SELECT * FROM bigevents WHERE inexacttime = ?";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<String> date = new ArrayList<String>();
		List<String> eventName = new ArrayList<String>();
		date = setDate();
		eventName = getDataFromDB(date);
		printEventName(eventName);
		
	}
	/**
	 * ���г�����û����뿪ʼ�ͽ�ֹ���ڣ��������ڸ�ʽ0000-00-00
	 * @return ֻ���п�ʼ���ںͽ�ֹ���ڵļ���
	 */
	public static List<String> setDate(){
		List<String> date = new ArrayList<String>();
		String start = null;
		String end = null;		
		Scanner scan = new Scanner(System.in);
		System.out.print("��������ʼ����(��ʽ��0000-00-00):");
		start = scan.nextLine();
		System.out.print("�������ֹ����(��ʽ��9999-99-99):");
		end = scan.nextLine();
		date.add(start);
		date.add(end);
		scan.close();
		return date;
	}
	/**
	 * �õ�0000-00-00��ʽ���ڵ� �� �� ����
	 * @param date 0000-00-00��ʽ������
	 * @return ����������յ�����  0���� 1������
	 */
	public static String[] getYearAndMonthDay(String date){
		String[] temp = new String[2];
		temp[0] = date.substring(0,4);
		temp[1] = date.substring(5);
		return temp;
	}
	/**
	 * ������ũ������תΪ�������ڣ��õ�ũ�����պ͹������ڵ�Map
	 * @return ũ�����պ͹������ڵ�Map , keyΪũ������  ,  valueΪ��Ӧ��������
	 */
	public static Map<String, Date> lunarFestivalToSolarDate(){
		List<String> lunarFestival = new ArrayList<String>();
		Map<String, Date> solarDate = new HashMap<String, Date>();
		lunarFestival.add("���³�һ");
		lunarFestival.add("����ʮ��");
		lunarFestival.add("���³���");
		lunarFestival.add("���³���");
		lunarFestival.add("���³���");
		lunarFestival.add("����ʮ��");
		lunarFestival.add("����ʮ��");
		lunarFestival.add("���³���");
		lunarFestival.add("ʮ�³�һ");
		lunarFestival.add("ʮ��ʮ��");
		lunarFestival.add("���³���");
		lunarFestival.add("����إ��");
		lunarFestival.add("������ʮ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(String str : lunarFestival){
			String month = str.substring(0, 2);
			String day = str.substring(2);
			String temp = LunarCalendar.lunarToSolar(2015, month, day, false);
			try {
				solarDate.put(str,sdf.parse(temp));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}	
		return solarDate;
	}
	/**
	 * ĳ���µڼ������ڼ����˸�ʽ����תΪ�������ڣ��õ���Ӧ��Map
	 * @return  ĳ���µڼ������ڼ�����Ӧ�������ڵ�Map �� keyΪĳ���µڼ������ڼ� �� valueΪ��Ӧ�Ĺ�������
	 */
	public static Map<String,Date> someWeekSomeDay(){
		List<String> sourceDate = new ArrayList<String>();
		Map<String, Date> someDate = new HashMap<String, Date>();
		Calendar calendar = Calendar.getInstance();
		
		sourceDate.add("05�µ�2������7");
		sourceDate.add("06�µ�3������7");
		sourceDate.add("11�µ�4������4");
		sourceDate.add("05�µ�3������7");
		sourceDate.add("09�µ�3������2");
		sourceDate.add("09�µ�3������6");
		sourceDate.add("09�µ�4������7");
		sourceDate.add("10�µ�1������1");
		sourceDate.add("10�µ�2������3");
		sourceDate.add("10�µ�2������4");
		sourceDate.add("01�µ�1������6");
		sourceDate.add("05�µ�2������3");
		sourceDate.add("04�µ�2������7");
		sourceDate.add("11�µ�4������6");
		sourceDate.add("02�µ�1������4");
		sourceDate.add("08�µ�4������3");
		sourceDate.add("11�µ�2������6");
		sourceDate.add("05�µ�3������7");
		sourceDate.add("11�µ�2������1");
		sourceDate.add("11�µ�4������1");
		for(String str : sourceDate){
			if(str.substring(0, 1).equals("0")){
				calendar.set(Calendar.YEAR, 2015);
				calendar.set(Calendar.MONTH, Integer.parseInt(str.substring(1, 2)) - 1);
				calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, Integer.parseInt(str.substring(4, 5)));
				if(str.substring(8, 9).equals("7")){
					calendar.set(Calendar.DAY_OF_WEEK, 1);
				}else{
					calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(str.substring(8, 9)) + 1); 
				}
				someDate.put(str, calendar.getTime());
			}else{
				calendar.set(Calendar.YEAR, 2015);
				calendar.set(Calendar.MONTH, Integer.parseInt(str.substring(0, 2)) - 1);
				calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, Integer.parseInt(str.substring(4, 5)));
				if(str.substring(8, 9).equals("7")){
					calendar.set(Calendar.DAY_OF_WEEK, 1);
				}else{
					calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(str.substring(8, 9)) + 1);
				}
				someDate.put(str, calendar.getTime());
			}
		}
		
		return someDate;
	}
	/**
	 * �����ݿ���ȡ�����ڷ�Χ�ڵĴ��¼�
	 * @param time  Ҫ��ѯ�Ŀ�ʼ���ںͽ�ֹ���ڼ��� 0����ʼ���� 1����ֹ����
	 * @return  ���ڷ�Χ�ڵĴ��¼����Ƶļ���
	 */
	public static List<String> getDataFromDB(List<String> time) {
		Map<String, Date> solarDate = new HashMap<String, Date>();
		Map<String, Date> someDate = new HashMap<String, Date>();
		//����ũ��������������Ӧ�������ڵ�Map
		solarDate = lunarFestivalToSolarDate();
		//����ĳ�µڼ������ڼ�����Ӧ�������ڵ�Map
		someDate = someWeekSomeDay();
		List<String> eventName = new ArrayList<String>();
		//��ʼ����
		String startYMD = time.get(0);
		//��ֹ����
		String endYMD = time.get(1);
		String startYearAndMonthDay[] = getYearAndMonthDay(startYMD);
		String endYearAndMonthDay[] = getYearAndMonthDay(endYMD);
		//��ʼ���ڵ���
		String startYear = startYearAndMonthDay[0];
		//��ʼ���ڵ�����
		String startMonthDay = startYearAndMonthDay[1];
		//��ֹ���ڵ���
		String endYear = endYearAndMonthDay[0];
		//��ֹ���ڵ�����
		String endMonthDay = endYearAndMonthDay[1];
		Date startDate = null;
		Date endDate = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			startDate = sdf.parse(startYMD);
			endDate = sdf.parse(endYMD);
		} catch (ParseException e) {
			e.printStackTrace();
		}
				
		//������������
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		//ResultSetMetaData metaData = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt2 = conn.prepareStatement(SQL_INEXACTTIME);
			for(Map.Entry<String, Date> date : solarDate.entrySet()){
				if(date.getValue().after(startDate) && date.getValue().before(endDate)){
					pstmt2.setString(1, date.getKey());
					rs2 = pstmt2.executeQuery();
					while(rs2.next()){
						eventName.add(rs2.getString("eventname"));
					}
				}
			}
			for(Map.Entry<String, Date> date : someDate.entrySet()){
				if(date.getValue().after(startDate) && date.getValue().before(endDate)){
					pstmt2.setString(1, date.getKey());
					rs2 = pstmt2.executeQuery();
					while(rs2.next()){
						eventName.add(rs2.getString("eventname"));
					}
				}
			}
			pstmt1 = conn.prepareStatement(SQL_EXACTTIME);
			pstmt1.setString(1,startYMD);
			pstmt1.setString(2,endYMD);
			pstmt1.setString(3,startMonthDay);
			pstmt1.setString(4,endMonthDay);
			rs1 = pstmt1.executeQuery();
			while(rs1.next()){
				//System.out.println(rs.getString("eventname"));
				eventName.add(rs1.getString("eventname"));
			}
			/*
			metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			for(int i = 1;i<=columns;i++){
				System.out.print(metaData.getColumnName(i));
				System.out.print("\t");
			}
			System.out.println();
			while(rs.next()){
				for(int i = 1;i<=columns;i++){
					System.out.print(rs.getString(i));
					System.out.print("\t");
				}
				System.out.println();
			}
			*/
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eventName;
	}
	/**
	 * ��ӡʱ�䷶Χ�ڵĴ��¼�����
	 * @param eventName ���¼����Ƶļ���
	 */
	public static void printEventName(List<String> eventName){
		System.out.println("******��ʱ����ڵ��¼�������******");
		for(String event : eventName){
			System.out.println(event);
		}
	}

}
