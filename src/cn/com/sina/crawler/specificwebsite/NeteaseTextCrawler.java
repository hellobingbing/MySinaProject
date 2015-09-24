package cn.com.sina.crawler.specificwebsite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * 网易体育相关频道新闻的标题、时间、评论数
 * @author sina
 *
 */
public class NeteaseTextCrawler {
	// 网易所有新闻URL
	public static final String IN_FILE = "." + File.separator + "crawler" + File.separator +
			"163" + File.separator + "neteaseUrls.txt";
	// 新闻标题、时间和评论
	public static final String OUT_FILE = "." + File.separator + "crawler" + File.separator +
			"163" + File.separator + "neteaseTimeAndJoin.txt";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		List<String> urls = null;
		List<String> listTimeAndJoin = null;
		InputOutput inputOutput = new InputOutput();
		inputOutput.setIn(IN_FILE);
		urls = inputOutput.inputURL();  // 所有新闻URL集合
		listTimeAndJoin = getTimeAndJoin(urls);  // 新闻标题、时间和评论集合
		System.out.println("共有新闻" + listTimeAndJoin.size() + "条");
		for(String str : listTimeAndJoin){
			System.out.println(str);
		}
		//inputOutput.setOutText(OUT_FILE);
		//inputOutput.outputTimeAndJoin(listTimeAndJoin);
	}
	/**
	 * 取得新闻标题、时间和评论数的集合
	 * @param urls 所有新闻URL集合
	 * @return 新闻标题、时间和评论数的集合
	 */
	public static List<String> getTimeAndJoin(List<String> urls){
		List<String> listTimeAndJoin = new ArrayList<String>();
		
		int num = 1;
		//System.setProperty("webdriver.ie.driver", "." + File.separator + "Driver" + File.separator + "IEDriverServer.exe");
		//WebDriver driver = new InternetExplorerDriver();
		WebDriver driver = new FirefoxDriver();
		for(int i = 0;i<urls.size();i++){
			//System.setProperty("webdriver.ie.driver", "." + File.separator + "Driver" + File.separator + "IEDriverServer.exe");
			//WebDriver driver = new InternetExplorerDriver();
			driver.get(urls.get(i));
			List<WebElement> headline = driver.findElements(By.cssSelector("div[class='atitle tCenter']"));
			List<WebElement> timeAndJoin = driver.findElements(By.cssSelector("div[class='about']"));
			
			StringBuffer sb = null;
			sb = new StringBuffer();
			
			if((timeAndJoin.size() == 1) && (headline.size() == 1)){
				sb.append("[新闻 " + num + "]");				
				sb.append(headline.get(0).getText() + "\t");
				String[] temp = timeAndJoin.get(0).getText().split("\n");
				sb.append(temp[0] + "\t" + temp[1] + "\t" + temp[2]);
				//sb.append(timeAndJoin.get(0).getText().replaceAll("\\s+", ""));
				listTimeAndJoin.add(sb.toString());
				num++;
			}
			//driver.close();
		}
		driver.close();
		return listTimeAndJoin;
	}
	
}
