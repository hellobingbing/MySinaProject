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
 * 得到一个时间段内的所有大事件
 * @author sina
 *
 */
public class BigeventsOfPeriod {
	
	public static final String DBDRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/bigevents?"
			+ "useUnicode=true&characterEncoding=UTF8";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	//根据公历即精确的日期取得数据库中的相关记录
	public static final String SQL_EXACTTIME = "SELECT * FROM bigevents WHERE exacttime BETWEEN ? AND ?"
			+ "OR exacttime BETWEEN ? AND ?";
	//根据不精确的日期得到数据库中的相关记录
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
	 * 运行程序后用户输入开始和截止日期，所有日期格式0000-00-00
	 * @return 只含有开始日期和截止日期的集合
	 */
	public static List<String> setDate(){
		List<String> date = new ArrayList<String>();
		String start = null;
		String end = null;		
		Scanner scan = new Scanner(System.in);
		System.out.print("请输入起始日期(格式：0000-00-00):");
		start = scan.nextLine();
		System.out.print("请输入截止日期(格式：9999-99-99):");
		end = scan.nextLine();
		date.add(start);
		date.add(end);
		scan.close();
		return date;
	}
	/**
	 * 得到0000-00-00格式日期的 年 和 月日
	 * @param date 0000-00-00格式的日期
	 * @return 含有年和月日的数组  0：年 1：月日
	 */
	public static String[] getYearAndMonthDay(String date){
		String[] temp = new String[2];
		temp[0] = date.substring(0,4);
		temp[1] = date.substring(5);
		return temp;
	}
	/**
	 * 将所有农历节日转为公历日期，得到农历节日和公历日期的Map
	 * @return 农历节日和公历日期的Map , key为农历日期  ,  value为相应公历日期
	 */
	public static Map<String, Date> lunarFestivalToSolarDate(){
		List<String> lunarFestival = new ArrayList<String>();
		Map<String, Date> solarDate = new HashMap<String, Date>();
		lunarFestival.add("正月初一");
		lunarFestival.add("正月十五");
		lunarFestival.add("三月初三");
		lunarFestival.add("五月初五");
		lunarFestival.add("七月初七");
		lunarFestival.add("七月十五");
		lunarFestival.add("八月十五");
		lunarFestival.add("九月初九");
		lunarFestival.add("十月初一");
		lunarFestival.add("十月十五");
		lunarFestival.add("腊月初八");
		lunarFestival.add("腊月廿三");
		lunarFestival.add("腊月三十");
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
	 * 某个月第几个星期几，此格式日期转为公历日期，得到相应的Map
	 * @return  某个月第几个星期几和相应公历日期的Map ， key为某个月第几个星期几 ， value为相应的公历日期
	 */
	public static Map<String,Date> someWeekSomeDay(){
		List<String> sourceDate = new ArrayList<String>();
		Map<String, Date> someDate = new HashMap<String, Date>();
		Calendar calendar = Calendar.getInstance();
		
		sourceDate.add("05月第2个星期7");
		sourceDate.add("06月第3个星期7");
		sourceDate.add("11月第4个星期4");
		sourceDate.add("05月第3个星期7");
		sourceDate.add("09月第3个星期2");
		sourceDate.add("09月第3个星期6");
		sourceDate.add("09月第4个星期7");
		sourceDate.add("10月第1个星期1");
		sourceDate.add("10月第2个星期3");
		sourceDate.add("10月第2个星期4");
		sourceDate.add("01月第1个星期6");
		sourceDate.add("05月第2个星期3");
		sourceDate.add("04月第2个星期7");
		sourceDate.add("11月第4个星期6");
		sourceDate.add("02月第1个星期4");
		sourceDate.add("08月第4个星期3");
		sourceDate.add("11月第2个星期6");
		sourceDate.add("05月第3个星期7");
		sourceDate.add("11月第2个星期1");
		sourceDate.add("11月第4个星期1");
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
	 * 从数据库中取得日期范围内的大事件
	 * @param time  要查询的开始日期和截止日期集合 0：开始日期 1：截止日期
	 * @return  日期范围内的大事件名称的集合
	 */
	public static List<String> getDataFromDB(List<String> time) {
		Map<String, Date> solarDate = new HashMap<String, Date>();
		Map<String, Date> someDate = new HashMap<String, Date>();
		//所有农历节日日期与相应公历日期的Map
		solarDate = lunarFestivalToSolarDate();
		//所有某月第几个星期几与相应公历日期的Map
		someDate = someWeekSomeDay();
		List<String> eventName = new ArrayList<String>();
		//开始日期
		String startYMD = time.get(0);
		//截止日期
		String endYMD = time.get(1);
		String startYearAndMonthDay[] = getYearAndMonthDay(startYMD);
		String endYearAndMonthDay[] = getYearAndMonthDay(endYMD);
		//开始日期的年
		String startYear = startYearAndMonthDay[0];
		//开始日期的月日
		String startMonthDay = startYearAndMonthDay[1];
		//截止日期的年
		String endYear = endYearAndMonthDay[0];
		//截止日期的月日
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
				
		//加载驱动程序
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
	 * 打印时间范围内的大事件名称
	 * @param eventName 大事件名称的集合
	 */
	public static void printEventName(List<String> eventName){
		System.out.println("******此时间段内的事件名称有******");
		for(String event : eventName){
			System.out.println(event);
		}
	}

}
