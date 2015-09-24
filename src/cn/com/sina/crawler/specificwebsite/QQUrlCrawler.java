package cn.com.sina.crawler.specificwebsite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
/**
 * ��Ѷ�������Ƶ������URL
 * @author sina
 *
 */
public class QQUrlCrawler {
	// ��Ѷ����Ƶ����������
	public static final String IN_FILE = "." + File.separator + "crawler" + File.separator +
			"QQ" + File.separator + "sourceUrls.txt";
	// ��Ѷ����Ƶ������URL
	public static final String OUT_FILE = "." + File.separator + "crawler" + File.separator +
			"QQ" + File.separator + "QQUrls.txt";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		List<String> sourceUrls = null;
		List<List<String>> urls = null;
		sourceUrls = new ArrayList<String>();
		urls = new ArrayList<List<String>>();
		InputOutput inputOutput = new InputOutput();
		inputOutput.setIn(IN_FILE);
		sourceUrls = inputOutput.inputURL();
		urls = getQQUrls(sourceUrls);
		for(List<String> list : urls){
			for(String url : list){
				System.out.println(url);
			}
		}
		//inputOutput.setOutUrls(OUT_FILE);
		//inputOutput.outputURL(urls);
		
	}
	/**
	 * ȡ�����Ƶ������URL
	 * @param source ��Ѷ����Ƶ���������Ӽ���
	 * @return ���Ƶ������URL����
	 * @throws Exception
	 */
	public static List<List<String>> getQQUrls(List<String> source) throws Exception {
		
		List<List<String>> listQQUrls = null;
		listQQUrls = new ArrayList<List<String>>();		
		// Chrome �����
		System.setProperty("webdriver.chrome.driver", "." + File.separator + "Driver" + File.separator + "chromedriver.exe");
		WebDriver webDriver = new ChromeDriver();
		
		List<WebElement> divList = null;
		List<WebElement> aList = null;
		
		// ѭ��11��Ƶ����ҳ
		for(int i = 0;i<source.size();i++){
			List<String> listUrls = null;
			listUrls = new ArrayList<String>();
			webDriver.get(source.get(i));
			//System.out.println(webDriver.getTitle());
			divList = webDriver.findElements(By.cssSelector("ul"));
			if(divList.size() != 0){
				listUrls.addAll(getPartUrls(divList));
			}
			divList = webDriver.findElements(By.cssSelector("div[class='L5']"));
			if(divList.size() != 0){
				for(int j = 0;j<divList.size();j++){
					aList = divList.get(j).findElements(By.tagName("a"));
					for(int k = 0;k<aList.size();k++){
						listUrls.add(aList.get(k).getAttribute("href"));
					}
				}
			}
			listQQUrls.add(listUrls);
		}
		webDriver.quit();
		return listQQUrls;	
	}
	
	public static List<String> getPartUrls(List<WebElement> divList){
		List<String> listUrls = null;
		listUrls = new ArrayList<String>();
		List<WebElement> liList = null;
		List<WebElement> aList = null;
		for(int i = 0;i<divList.size();i++){
			liList = divList.get(i).findElements(By.tagName("li"));
			for(int j = 0;j<liList.size();j++){
				aList = liList.get(j).findElements(By.tagName("a"));
				for(int k = 0;k<aList.size();k++){
					listUrls.add(aList.get(k).getAttribute("href"));
				}
			}
		}
		return listUrls;
	}
	
}
