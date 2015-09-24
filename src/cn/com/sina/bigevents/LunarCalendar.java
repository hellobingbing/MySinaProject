package cn.com.sina.bigevents;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 工具类，实现公农历互转
 * @author sina
 *
 */
public class LunarCalendar {
        
        /**
         * 支持转换的最小农历年份
         */
        public static final int MIN_YEAR = 1900;
        /**
         * 支持转换的最大农历年份
         */
        public static final int MAX_YEAR = 2099;

        /**
         * 公历每月前的天数
         */
        private static final int DAYS_BEFORE_MONTH[] = { 0, 31, 59, 90, 120, 151, 181,
                        212, 243, 273, 304, 334, 365 };
        
        /**
         * 用来表示1900年到2099年间农历年份的相关信息，共24位bit的16进制表示，其中：
         * 1. 前4位表示该年闰哪个月；
         * 2. 5-17位表示农历年份13个月的大小月分布，0表示小，1表示大；
         * 3. 最后7位表示农历年首（正月初一）对应的公历日期。
         * 
         * 以2014年的数据0x955ABF为例说明：
         *                 1001 0101 0101 1010 1011 1111
         *                 闰九月                                  农历正月初一对应公历1月31号        
         */
        private static final int LUNAR_INFO[] = {
                0x84B6BF,/*1900*/
              0x04AE53,0x0A5748,0x5526BD,0x0D2650,0x0D9544,0x46AAB9,0x056A4D,0x09AD42,0x24AEB6,0x04AE4A,/*1901-1910*/
            0x6A4DBE,0x0A4D52,0x0D2546,0x5D52BA,0x0B544E,0x0D6A43,0x296D37,0x095B4B,0x749BC1,0x049754,/*1911-1920*/
            0x0A4B48,0x5B25BC,0x06A550,0x06D445,0x4ADAB8,0x02B64D,0x095742,0x2497B7,0x04974A,0x664B3E,/*1921-1930*/
            0x0D4A51,0x0EA546,0x56D4BA,0x05AD4E,0x02B644,0x393738,0x092E4B,0x7C96BF,0x0C9553,0x0D4A48,/*1931-1940*/
            0x6DA53B,0x0B554F,0x056A45,0x4AADB9,0x025D4D,0x092D42,0x2C95B6,0x0A954A,0x7B4ABD,0x06CA51,/*1941-1950*/
            0x0B5546,0x555ABB,0x04DA4E,0x0A5B43,0x352BB8,0x052B4C,0x8A953F,0x0E9552,0x06AA48,0x6AD53C,/*1951-1960*/
            0x0AB54F,0x04B645,0x4A5739,0x0A574D,0x052642,0x3E9335,0x0D9549,0x75AABE,0x056A51,0x096D46,/*1961-1970*/
            0x54AEBB,0x04AD4F,0x0A4D43,0x4D26B7,0x0D254B,0x8D52BF,0x0B5452,0x0B6A47,0x696D3C,0x095B50,/*1971-1980*/
            0x049B45,0x4A4BB9,0x0A4B4D,0xAB25C2,0x06A554,0x06D449,0x6ADA3D,0x0AB651,0x095746,0x5497BB,/*1981-1990*/
            0x04974F,0x064B44,0x36A537,0x0EA54A,0x86B2BF,0x05AC53,0x0AB647,0x5936BC,0x092E50,0x0C9645,/*1991-2000*/
            0x4D4AB8,0x0D4A4C,0x0DA541,0x25AAB6,0x056A49,0x7AADBD,0x025D52,0x092D47,0x5C95BA,0x0A954E,/*2001-2010*/
            0x0B4A43,0x4B5537,0x0AD54A,0x955ABF,0x04BA53,0x0A5B48,0x652BBC,0x052B50,0x0A9345,0x474AB9,/*2011-2020*/
            0x06AA4C,0x0AD541,0x24DAB6,0x04B64A,0x6a573D,0x0A4E51,0x0D2646,0x5E933A,0x0D534D,0x05AA43,/*2021-2030*/
            0x36B537,0x096D4B,0xB4AEBF,0x04AD53,0x0A4D48,0x6D25BC,0x0D254F,0x0D5244,0x5DAA38,0x0B5A4C,/*2031-2040*/
            0x056D41,0x24ADB6,0x049B4A,0x7A4BBE,0x0A4B51,0x0AA546,0x5B52BA,0x06D24E,0x0ADA42,0x355B37,/*2041-2050*/
            0x09374B,0x8497C1,0x049753,0x064B48,0x66A53C,0x0EA54F,0x06AA44,0x4AB638,0x0AAE4C,0x092E42,/*2051-2060*/
            0x3C9735,0x0C9649,0x7D4ABD,0x0D4A51,0x0DA545,0x55AABA,0x056A4E,0x0A6D43,0x452EB7,0x052D4B,/*2061-2070*/
            0x8A95BF,0x0A9553,0x0B4A47,0x6B553B,0x0AD54F,0x055A45,0x4A5D38,0x0A5B4C,0x052B42,0x3A93B6,/*2071-2080*/
            0x069349,0x7729BD,0x06AA51,0x0AD546,0x54DABA,0x04B64E,0x0A5743,0x452738,0x0D264A,0x8E933E,/*2081-2090*/
            0x0D5252,0x0DAA47,0x66B53B,0x056D4F,0x04AE45,0x4A4EB9,0x0A4D4C,0x0D1541,0x2D92B5          /*2091-2099*/
    };
        
        /**
         * 将农历日期转换为公历日期
         * @param year               农历年份
         * @param monthString        农历月
         * @param monthDayString     农历日
         * @param isLeapMonth        该月是否是闰月
         * [url=home.php?mod=space&uid=7300]@return[/url] 返回农历日期对应的公历日期
         */
        public static final String lunarToSolar(int year, String monthString, String monthDayString,
                        boolean isLeapMonth) {
                int dayOffset;
                int leapMonth;
                int i;
                int month = 0;
                int monthDay = 0;
                
                switch (monthString) {
				case "正月" : month = 1;break;
				case "二月" : month = 2;break;
				case "三月" : month = 3;break;
				case "四月" : month = 4;break;
				case "五月" : month = 5;break;
				case "六月" : month = 6;break;
				case "七月" : month = 7;break;
				case "八月" : month = 8;break;
				case "九月" : month = 9;break;
				case "十月" : month = 10;break;
				case "十一月" : month = 11;break;
				case "腊月" : month = 12;break;
				default:break;
				}
                
                switch (monthDayString) {
                case "初一" : monthDay = 1;break;
        		case "初二" : monthDay = 2;break;
        		case "初三" : monthDay = 3;break;
        		case "初四" : monthDay = 4;break;
        		case "初五" : monthDay = 5;break;
        		case "初六" : monthDay = 6;break;
        		case "初七" : monthDay = 7;break;
        		case "初八" : monthDay = 8;break;
        		case "初九" : monthDay = 9;break;
        		case "初十" : monthDay = 10;break;
        		case "十一" : monthDay = 11;break;
        		case "十二" : monthDay = 12;break;
        		case "十三" : monthDay = 13;break;
        		case "十四" : monthDay = 14;break;
        		case "十五" : monthDay = 15;break;
        		case "十六" : monthDay = 16;break;
        		case "十七" : monthDay = 17;break;
        		case "十八" : monthDay = 18;break;
        		case "十九" : monthDay = 19;break;
        		case "二十" : monthDay = 20;break;
        		case "廿一" : monthDay = 21;break;
        		case "廿二" : monthDay = 22;break;
        		case "廿三" : monthDay = 23;break;
        		case "廿四" : monthDay = 24;break;
        		case "廿五" : monthDay = 25;break;
        		case "廿六" : monthDay = 26;break;
        		case "廿七" : monthDay = 27;break;
        		case "廿八" : monthDay = 28;break;
        		case "廿九" : monthDay = 29;break;
        		case "三十" : monthDay = 30;break;
				default:break;
				}
                
                if (year < MIN_YEAR || year > MAX_YEAR || month < 1 || month > 12
                                || monthDay < 1 || monthDay > 30) {
                        throw new IllegalArgumentException(
                                        "Illegal lunar date, must be like that:\n\t" +
                                        "year : 1900~2099\n\t" + 
                                        "month : 1~12\n\t" +
                                        "day : 1~30");
                }
                
                dayOffset = (LUNAR_INFO[year - MIN_YEAR] & 0x001F) - 1;

                if (((LUNAR_INFO[year - MIN_YEAR] & 0x0060) >> 5) == 2)
                        dayOffset += 31;

                for (i = 1; i < month; i++) {
                        if ((LUNAR_INFO[year - MIN_YEAR] & (0x80000 >> (i - 1))) == 0)
                                dayOffset += 29;
                        else
                                dayOffset += 30;
                }

                dayOffset += monthDay;
                leapMonth = (LUNAR_INFO[year - MIN_YEAR] & 0xf00000) >> 20;

                // 这一年有闰月
                if (leapMonth != 0) {
                        if (month > leapMonth || (month == leapMonth && isLeapMonth)) {
                                if ((LUNAR_INFO[year - MIN_YEAR] & (0x80000 >> (month - 1))) == 0)
                                        dayOffset += 29;
                                else
                                        dayOffset += 30;
                        }
                }

                if (dayOffset > 366 || (year % 4 != 0 && dayOffset > 365)) {
                        year += 1;
                        if (year % 4 == 1)
                                dayOffset -= 366;
                        else
                                dayOffset -= 365;
                }
                
                int[] solarInfo = new int[3];
                for (i = 1; i < 13; i++) {
                        int iPos = DAYS_BEFORE_MONTH[i];
                        if (year % 4 == 0 && i > 2) {
                                iPos += 1;
                        }

                        if (year % 4 == 0 && i == 2 && iPos + 1 == dayOffset) {
                                solarInfo[1] = i;
                                solarInfo[2] = dayOffset - 31;
                                break;
                        }

                        if (iPos >= dayOffset) {
                                solarInfo[1] = i;
                                iPos = DAYS_BEFORE_MONTH[i - 1];
                                if (year % 4 == 0 && i > 2) {
                                        iPos += 1;
                                }
                                if (dayOffset > iPos)
                                        solarInfo[2] = dayOffset - iPos;
                                else if (dayOffset == iPos) {
                                        if (year % 4 == 0 && i == 2)
                                                solarInfo[2] = DAYS_BEFORE_MONTH[i] - DAYS_BEFORE_MONTH[i - 1] + 1;
                                        else
                                                solarInfo[2] = DAYS_BEFORE_MONTH[i] - DAYS_BEFORE_MONTH[i - 1];

                                } else
                                        solarInfo[2] = dayOffset;
                                break;
                        }
                }
                solarInfo[0] = year;
                String solar = solarInfo[0] + "-" + solarInfo[1] + "-" + solarInfo[2];
                return solar;
        }
        
        /**
         * 将公历日期转换为农历日期，且标识是否是闰月
         * @param year
         * @param month
         * @param monthDay
         * @return 返回公历日期对应的农历日期
         */
        public static final String solarToLunar(int year, int month, int monthDay) {
                int[] lunarDate = new int[4];
                Date baseDate = new GregorianCalendar(1900, 0, 31).getTime();
                Date objDate = new GregorianCalendar(year, month - 1, monthDay).getTime();
                int offset = (int) ((objDate.getTime() - baseDate.getTime()) / 86400000L);
                
                // 用offset减去每农历年的天数计算当天是农历第几天
        // iYear最终结果是农历的年份, offset是当年的第几天
                int iYear, daysOfYear = 0;
                for (iYear = MIN_YEAR; iYear <= MAX_YEAR && offset > 0; iYear++) {
                        daysOfYear = daysInLunarYear(iYear);
                        offset -= daysOfYear;
                }
                if (offset < 0) {
                        offset += daysOfYear;
                        iYear--;
                }
                
                // 农历年份
                lunarDate[0] = iYear;
                
                int leapMonth = leapMonth(iYear); // 闰哪个月,1-12
                boolean isLeap = false;
                // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
                for (iMonth = 1; iMonth <= 13 && offset > 0; iMonth++) {
                        daysOfMonth = daysInLunarMonth(iYear, iMonth);
                        offset -= daysOfMonth;
                }
                // 当前月超过闰月，要校正
        if (leapMonth != 0 && iMonth > leapMonth) {
                --iMonth;
                
                if (iMonth == leapMonth) {
                        isLeap = true;
                }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
        }
        
        lunarDate[1] = iMonth;
        lunarDate[2] = offset + 1;
        lunarDate[3] = isLeap ? 1 : 0;
                
        String lunar = null;
        String years = null;
        String months = null;
        String days = null;
        years = lunarDate[0] + "年";
        switch (lunarDate[1]) {
		case 1 : months = "正月";break;
		case 2 : months = "二月";break;
		case 3 : months = "三月";break;
		case 4 : months = "四月";break;
		case 5 : months = "五月";break;
		case 6 : months = "六月";break;
		case 7 : months = "七月";break;
		case 8 : months = "八月";break;
		case 9 : months = "九月";break;
		case 10 : months = "十月";break;
		case 11 : months = "十一月";break;
		case 12 : months = "腊月";break;
		default:break;
		}
        switch (lunarDate[2]) {
    	case 1 : days = "初一";break;
		case 2 : days = "初二";break;
		case 3 : days = "初三";break;
		case 4 : days = "初四";break;
		case 5 : days = "初五";break;
		case 6 : days = "初六";break;
		case 7 : days = "初七";break;
		case 8 : days = "初八";break;
		case 9 : days = "初九";break;
		case 10 : days = "初十";break;
		case 11 : days = "十一";break;
		case 12 : days = "十二";break;
		case 13 : days = "十三";break;
		case 14 : days = "十四";break;
		case 15 : days = "十五";break;
		case 16 : days = "十六";break;
		case 17 : days = "十七";break;
		case 18 : days = "十八";break;
		case 19 : days = "十九";break;
		case 20 : days = "二十";break;
		case 21 : days = "廿一";break;
		case 22 : days = "廿二";break;
		case 23 : days = "廿三";break;
		case 24 : days = "廿四";break;
		case 25 : days = "廿五";break;
		case 26 : days = "廿六";break;
		case 27 : days = "廿七";break;
		case 28 : days = "廿八";break;
		case 29 : days = "廿九";break;
		case 30 : days = "三十";break;
		default:break;
		}
        
        lunar = years + months + days;
        return lunar;
        }
        
        /**
         * 传回农历year年month月的总天数
         * @param year 要计算的年份
         * @param month        要计算的月
         * @return 传回天数
         */
        final public static int daysInMonth(int year, int month) {
                return daysInMonth(year, month, false);
        }
        
        /**
         * 传回农历year年month月的总天数
         * @param year 要计算的年份
         * @param month        要计算的月
         * @param leap 当月是否是闰月
         * @return 传回天数，如果闰月是错误的，返回0.
         */
        public static final int daysInMonth(int year, int month, boolean leap) {
                int leapMonth = leapMonth(year);
                int offset = 0;
                
                // 如果本年有闰月且month大于闰月时，需要校正
                if (leapMonth != 0 && month > leapMonth) {
                        offset = 1;
                }
                
                // 不考虑闰月
                if (!leap) {
                        return daysInLunarMonth(year, month + offset);
                } else {
                        // 传入的闰月是正确的月份
                        if (leapMonth != 0 && leapMonth == month) {
                                return daysInLunarMonth(year, month + 1);
                        }
                }
                
                return 0;
        }
        
        /**
     * 传回农历 year年的总天数
     *
     * @param year 将要计算的年份
     * @return 返回传入年份的总天数
     */
    private static int daysInLunarYear(int year) {
        int i, sum = 348;
        if (leapMonth(year) != 0) {
                sum = 377;
        }
        int monthInfo = LUNAR_INFO[year - MIN_YEAR] & 0x0FFF80;
        for (i = 0x80000; i > 0x7; i >>= 1) {
            if ((monthInfo & i) != 0)
                sum += 1;
        }
        return sum;
    }
    
    /**
     * 传回农历 year年month月的总天数，总共有13个月包括闰月
     *
     * @param year  将要计算的年份
     * @param month 将要计算的月份
     * @return 传回农历 year年month月的总天数
     */
    private static int daysInLunarMonth(int year, int month) {
        if ((LUNAR_INFO[year - MIN_YEAR] & (0x100000 >> month)) == 0)
            return 29;
        else
            return 30;
    }
        
        /**
         * 传回农历 year年闰哪个月 1-12 , 没闰传回 0
         * 
         * @param year 将要计算的年份
         * @return 传回农历 year年闰哪个月1-12, 没闰传回 0
         */
        private static int leapMonth(int year) {
                return (int) ((LUNAR_INFO[year - MIN_YEAR] & 0xF00000)) >> 20;
        }
        
        public static void main(String args[]){
        	String dateLunar = null;
        	String dateSolar = null;
        	dateLunar = LunarCalendar.solarToLunar(2015,8,31);
        	System.out.println(dateLunar);
        	
        	dateSolar = LunarCalendar.lunarToSolar(2015, "七月", "二十", false);
        	System.out.println(dateSolar);
        }
        
        
}

