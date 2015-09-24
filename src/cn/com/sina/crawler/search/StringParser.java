package cn.com.sina.crawler.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 正则表达式匹配字符串和替换、查找等处理
 * @author sina
 *
 */
public class StringParser {
	
	public static String find(String str, String regex, int pos) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		String result = null;
		int i = 0;
		while (matcher.find()) {
			result = matcher.group(1);
			i++;
			if (i == pos) {
				break;
			}
		}
		return result;
	}
	/**
	 * eg：将搜索到的页面上的内容，与新闻url的正则相匹配
	 * @param content 全部内容
	 * @param regex 正则表达式
	 * @return 匹配到的内容的集合
	 */
	public static List<String> getStringByRegex(String content,String regex){
		List<String> targetStrings = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		String result = null;
		while (matcher.find()) {
			result = matcher.group(1);
			targetStrings.add(result);
		}
		return targetStrings;
	}
	
	public static String stringFilter(String content){
		String str = content.replaceAll("<[.[^<]]*>", "");
		return str;
	}
	
}
