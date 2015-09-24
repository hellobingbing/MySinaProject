package cn.com.sina.crawler.specificwebsite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
/**
 * �����������Ƶ������URL
 * @author sina
 *
 */
public class NeteaseUrlCrawler {
	// ���׸���Ƶ����������
	public static final String IN_FILE = "." + File.separator + "crawler" + File.separator +
			"163" + File.separator + "sourceUrls.txt";
	// ���׸���Ƶ������URL
	public static final String OUT_FILE = "." + File.separator +"crawler" + File.separator +
			"163" + File.separator + "neteaseUrls.txt";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		List<String> urls = null;
		List<List<String>> listNeteaseUrls = null;
		InputOutput inputOutput = new InputOutput();
		inputOutput.setIn(IN_FILE);
		urls = inputOutput.inputURL();
		listNeteaseUrls = getNeteaseUrls(urls);
		for(List<String> listUrls : listNeteaseUrls){
			for(String url : listUrls){
				System.out.println(url);
			}
		}
		//inputOutput.setOutUrls(OUT_FILE);
		//inputOutput.outputURL(listNeteaseUrls);
		
	}
	/**
	 * ȡ�����Ƶ������URL
	 * @param source ���׸���Ƶ���������Ӽ���
	 * @return ���Ƶ������URL����
	 * @throws Exception
	 */
	public static List<List<String>> getNeteaseUrls(List<String> source) throws Exception {
		
		List<List<String>> listNeteaseUrls = null;
		listNeteaseUrls = new ArrayList<List<String>>();
		// IE �����
		//System.setProperty("webdriver.ie.driver", "." + File.separator + "Driver" + File.separator + "IEDriverServer.exe");
		//WebDriver webDriver = new InternetExplorerDriver();
		// Firefox �����
		//System.setPropery("webdriver.firefox.bin", "D:" + File.separator + "Program Files" + File.separator + "Mozilla Firefox" + File.separator + "firefox.exe");
		WebDriver webDriver = new FirefoxDriver();
		// Chrome �����
		//System.setProperty("webdriver.chrome.driver", "." + File.separator + "Driver" + File.separator + "chromedriver.exe");
		//WebDriver webDriver = new ChromeDriver();
		List<WebElement> divList = null;
		
		// ѭ��12��Ƶ����ҳ
		for(int i = 0;i<source.size();i++){
			List<String> listUrls = null;
			listUrls = new ArrayList<String>();
			webDriver.get(source.get(i));
			//System.out.println(webDriver.getTitle());
			divList = webDriver.findElements(By.cssSelector("div[class='content']"));
			if(divList.size() != 0){
				listUrls.addAll(getPartUrls(divList));
			}
			divList = webDriver.findElements(By.cssSelector("div[class='imgArea4']"));
			if(divList.size() != 0){
				listUrls.addAll(getPartUrls(divList));
			}
			listNeteaseUrls.add(listUrls);
		}
		webDriver.close();	
		return listNeteaseUrls;	
	}
	
	public static List<String> getPartUrls(List<WebElement> divList){
		List<String> listUrls = null;
		listUrls = new ArrayList<String>();
		List<WebElement> pList = null;
		List<WebElement> aList = null;
		for(int i = 0;i<divList.size();i++){
			pList = divList.get(i).findElements(By.tagName("p"));
			for(int j = 0;j<pList.size();j++){
				aList = pList.get(j).findElements(By.tagName("a"));
				for(int k = 0;k<aList.size();k++){
					listUrls.add(aList.get(k).getAttribute("href"));
				}
			}
		}
		return listUrls;
	}
	
}
