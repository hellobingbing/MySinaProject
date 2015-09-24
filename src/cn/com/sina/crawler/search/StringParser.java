package cn.com.sina.crawler.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * ������ʽƥ���ַ������滻�����ҵȴ���
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
	 * eg������������ҳ���ϵ����ݣ�������url��������ƥ��
	 * @param content ȫ������
	 * @param regex ������ʽ
	 * @return ƥ�䵽�����ݵļ���
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
