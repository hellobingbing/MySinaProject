package cn.com.sina.crawler.specificwebsite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * ��Ѷ�������Ƶ�����ŵı��⡢ʱ�䡢��Դ 
 * @author sina
 *
 */
public class QQTextCrawler {
	// ��Ѷ��������URL
	public static final String IN_FILE = "." + File.separator + "crawler" + File.separator +
			"QQ" + File.separator + "QQUrls.txt";
	// ���ű��⡢ʱ�����Դ
	public static final String OUT_FILE = "." + File.separator + "crawler" + File.separator +
			"QQ" + File.separator + "QQTimeAndFrom.txt";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		List<String> urls = null;
		List<String> listTimeAndFrom = null;
		InputOutput inputOutput = new InputOutput();
		inputOutput.setIn(IN_FILE);
		urls = inputOutput.inputURL();
		listTimeAndFrom = getTimeAndFrom(urls);
		System.out.println("��������" + listTimeAndFrom.size() + "��");
		for(String str : listTimeAndFrom){
			System.out.println(str);
		}
		//inputOutput.setOutText(OUT_FILE);
		//inputOutput.outputTimeAndJoin(listTimeAndFrom);
		
	}
	/**
	 * ȡ�����ű��⡢ʱ�����Դ�ļ���
	 * @param urls ��������URL����
	 * @return ���ű��⡢ʱ�����Դ�ļ���
	 */
	public static List<String> getTimeAndFrom(List<String> urls){
		List<String> listTimeAndFrom = new ArrayList<String>();
		
		int num = 1;
		// Chrome �����
		System.setProperty("webdriver.chrome.driver", "." + File.separator + "Driver" + File.separator + "chromedriver.exe");
		WebDriver webDriver = new ChromeDriver();
		for(int i = 0;i<urls.size();i++){
			
			webDriver.get(urls.get(i));
			List<WebElement> title = webDriver.findElements(By.cssSelector("div[class='atc-title']"));
			List<WebElement> from = webDriver.findElements(By.cssSelector("div[class='atc-from']"));
			List<WebElement> time = webDriver.findElements(By.cssSelector("div[class='atc-date']"));
			
			StringBuffer sb = null;
			sb = new StringBuffer();
			
			if((from.size() == 1) && (title.size() == 1) && (time.size() == 1)){
				sb.append("[���� " + num + "]");				
				sb.append(title.get(0).getText() + "\t");
				sb.append(from.get(0).getText() + "\t");
				sb.append(time.get(0).getText());
				listTimeAndFrom.add(sb.toString());
				num++;
			}
		}
		webDriver.quit();
		return listTimeAndFrom;
	}
	
}
