package cn.com.sina.crawler.specificwebsite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
/**
 * 搜狐体育相关频道新闻URL
 * @author sina
 *
 */
public class SohuUrlCrawler {
	// 搜狐各个频道导航链接
	public static final String IN_FILE = "." + File.separator + "crawler" + File.separator +
			"sohu" + File.separator + "sourceUrls.txt";
	// 搜狐各个频道新闻URL
	public static final String OUT_FILE = "." + File.separator + "crawler" + File.separator +
			"sohu" + File.separator + "sohuUrls.txt";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		List<List<String>> listSohuUrls = new ArrayList<List<String>>();
		InputOutput inputOutput = new InputOutput();
		inputOutput.setIn(IN_FILE);
		List<String> source = inputOutput.inputURL();
		listSohuUrls = getSohuUrls(source);
		for(List<String> urls : listSohuUrls){
			System.out.println(urls.size());
			for(String url : urls){
				System.out.println(url);
			}
		}
		inputOutput.setOutUrls(OUT_FILE);
		inputOutput.outputURL(listSohuUrls);

	}
	/**
	 * 取得相关频道新闻URL
	 * @param source 搜狐各个频道导航链接集合
	 * @return 相关频道新闻URL集合
	 * @throws Exception
	 */
	public static List<List<String>> getSohuUrls(List<String> source) throws Exception {
		
		List<List<String>> listSohuUrls = null;
		listSohuUrls = new ArrayList<List<String>>();
		// IE 浏览器
		//System.setProperty("webdriver.ie.driver", "." + File.separator + "Driver" + File.separator + "IEDriverServer.exe");
		//WebDriver webDriver = new InternetExplorerDriver();
		// Firefox 浏览器
		//System.setPropery("webdriver.firefox.bin", "D:" + File.separator + "Program Files" + File.separator + "Mozilla Firefox" + File.separator + "firefox.exe");
		//WebDriver webDriver = new FirefoxDriver();
		// Chrome 浏览器
		System.setProperty("webdriver.chrome.driver", "." + File.separator + "Driver" + File.separator + "chromedriver.exe");
		WebDriver webDriver = new ChromeDriver();
		List<WebElement> divList = null;
		// 以下三个网页源码是特例
		String zhiboUrl = "http://zhibo.m.sohu.com/#program/5?_smuid=FKs54ZRMULAfR3xqZSSIhU&v=2";
		String golfUrl = "http://m.sohu.com/c/154/?_smuid=FKs54ZRMULAfR3xqZSSIhU&v=2";
		String zhongjiaUrl = "http://m.sohu.com/c/102/?_smuid=FKs54ZRMULAfR3xqZSSIhU&v=2";
		// 循环15个网页
		for(int i = 0;i<source.size();i++){
			List<String> listUrls = null;
			listUrls = new ArrayList<String>();
			//WebDriver webDriver = new FirefoxDriver();
			//WebDriver webDriver = new InternetExplorerDriver();
			if(source.get(i).equals(zhiboUrl)){
				webDriver.get(source.get(i));
				divList = webDriver.findElements(By.cssSelector("div[class='p it2']>a[href]"));
				for(int j = 0;j<divList.size();j++){
					listUrls.add(divList.get(j).getAttribute("href"));
				}
				listSohuUrls.add(listUrls);
			}else if(source.get(i).equals(golfUrl)){
				webDriver.get(source.get(i));
				divList = webDriver.findElements(By.cssSelector("html>body>div[class='ls']>div[class='ls']"));
				if(divList.size() != 0){
					listUrls.addAll(getPartUrls(divList));
				}
				divList = webDriver.findElements(By.cssSelector("html>body>div[class='ls']>div[class='w a3']"));
				if(divList.size() != 0){
					listUrls.addAll(getPartUrls(divList));
				}
				listSohuUrls.add(listUrls);
			}else if(source.get(i).equals(zhongjiaUrl)){
				webDriver.get(source.get(i));
				divList = webDriver.findElements(By.cssSelector("html>body>div[class='ls']"));
				if(divList.size() != 0){
					listUrls.addAll(getPartUrls(divList));
				}
				divList = webDriver.findElements(By.cssSelector("html>body>div[class='w a3']"));
				if(divList.size() != 0){
					listUrls.addAll(getPartUrls(divList));
				}
				divList = webDriver.findElements(By.cssSelector("html>body>p>a"));
				for(int j = 0;j<divList.size();j++){
					listUrls.add(divList.get(j).getAttribute("href"));
				}
				listSohuUrls.add(listUrls);
			}else{
				webDriver.get(source.get(i));
				divList = webDriver.findElements(By.cssSelector("div[class='ls']"));
				if(divList.size() != 0){
					listUrls.addAll(getPartUrls(divList));
				}
				divList = webDriver.findElements(By.cssSelector("div[class='ls pb1']"));
				if(divList.size() != 0){
					listUrls.addAll(getPartUrls(divList));
				}
				divList = webDriver.findElements(By.cssSelector("div[class='w1']"));
				if(divList.size() != 0){
					listUrls.addAll(getPartUrls(divList));
				}
				listSohuUrls.add(listUrls);
				}			
			//webDriver.quit();
		}
		webDriver.quit();
		
		return listSohuUrls;
		
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
