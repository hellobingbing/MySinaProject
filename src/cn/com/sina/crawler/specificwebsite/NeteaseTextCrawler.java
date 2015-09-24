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
 * �����������Ƶ�����ŵı��⡢ʱ�䡢������
 * @author sina
 *
 */
public class NeteaseTextCrawler {
	// ������������URL
	public static final String IN_FILE = "." + File.separator + "crawler" + File.separator +
			"163" + File.separator + "neteaseUrls.txt";
	// ���ű��⡢ʱ�������
	public static final String OUT_FILE = "." + File.separator + "crawler" + File.separator +
			"163" + File.separator + "neteaseTimeAndJoin.txt";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		List<String> urls = null;
		List<String> listTimeAndJoin = null;
		InputOutput inputOutput = new InputOutput();
		inputOutput.setIn(IN_FILE);
		urls = inputOutput.inputURL();  // ��������URL����
		listTimeAndJoin = getTimeAndJoin(urls);  // ���ű��⡢ʱ������ۼ���
		System.out.println("��������" + listTimeAndJoin.size() + "��");
		for(String str : listTimeAndJoin){
			System.out.println(str);
		}
		//inputOutput.setOutText(OUT_FILE);
		//inputOutput.outputTimeAndJoin(listTimeAndJoin);
	}
	/**
	 * ȡ�����ű��⡢ʱ����������ļ���
	 * @param urls ��������URL����
	 * @return ���ű��⡢ʱ����������ļ���
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
				sb.append("[���� " + num + "]");				
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
