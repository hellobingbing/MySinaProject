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
 * 凤凰体育相关频道新闻url和标题、时间、评论数
 * @author sina
 *
 */
public class IfengHtmlCrawler {
	
	public static final String IN_FILE = "." + File.separator + "crawler" + File.separator +
			"ifeng" + File.separator + "sourceUrls.txt";
	public static final String OUT_FILE = "." + File.separator + "crawler" + File.separator +
			"ifeng" + File.separator + "ifengText.txt";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// 相关频道的地址
		List<String> source = new ArrayList<String>();
		// 所有新闻集合
		List<List<String>> listText = new ArrayList<List<String>>();
		InputOutput inputOutput  = new InputOutput();
		inputOutput.setIn(IN_FILE);
		source = inputOutput.inputURL();

		listText = getIfengText(source);
		int num = 1;
		for(List<String> list : listText){
			for(String text : list){
				System.out.println("[新闻 " + num + "]" + text);
				num++;
			}
		}
		inputOutput.setOutText(OUT_FILE);
		inputOutput.outputText(listText);	
	}
	/**
	 * 凤凰相关频道的新闻标题、时间、评论数
	 * @param source 相关频道地址
	 * @return 凤凰网几个频道新闻的集合
	 */
	public static List<List<String>> getIfengText(List<String> source){
		List<List<String>> listIfengText = null;
		listIfengText = new ArrayList<List<String>>();
		String NBAUrl = "http://3g.ifeng.com/sports/nba/nbai?vt=5&mid=8Z5kpx";
		String zongheUrl = "http://3g.ifeng.com/sports/zonghetiyu/dir?vt=5&cid=148&mid=8Z5kpx";
		String pinglunUrl = "http://3g.ifeng.com/sports/titanpinglun/dir?vt=5&cid=150&mid=8Z5kpx";
		
		WebDriver webDriver = new FirefoxDriver();
		List<WebElement> ulList = null;
		List<WebElement> headline = null;
		List<WebElement> time = null;
		List<WebElement> comment = null;
		for(int i = 0;i<source.size();i++){
			List<String> listUrls = null;
			List<String> listText = null;
			listUrls = new ArrayList<String>();
			listText = new ArrayList<String>();
			
			if(source.get(i).equals(NBAUrl)){
				webDriver.get(source.get(i));
				ulList = webDriver.findElements(By.cssSelector("ul[class='slLis']"));
				listUrls.addAll(getPartUrls(ulList));
				
				for(String url : listUrls){
					StringBuffer sb = null;
					sb = new StringBuffer();
					webDriver.get(url);
					headline = webDriver.findElements(By.cssSelector("div[class='acTxtTit']>h1"));
					time = webDriver.findElements(By.cssSelector("div[class='acTxtTit']>div>div"));
					comment = webDriver.findElements(By.cssSelector("div[class='acCmtAll']"));
					if((headline.size() == 1) && (time.size() == 1) && (comment.size() == 1)){
						sb.append(headline.get(0).getText() + "\t").append(time.get(0).getText() + "\t").append(comment.get(0).getText());
						listText.add(sb.toString());
					}					
				}
				listIfengText.add(listText);
			}else if(source.get(i).equals(pinglunUrl)){
				webDriver.get(source.get(i));
				List<WebElement> divList = webDriver.findElements(By.cssSelector("div[class='nlist']"));
				for(int j = 0;j<divList.size();j++){
					List<WebElement> list = divList.get(j).findElements(By.tagName("ul")).get(0).findElements(By.tagName("li"));
					for(int k = 0;k<list.size();k++){
						listUrls.add(list.get(k).findElements(By.tagName("a")).get(0).getAttribute("href"));
					}
				}
				
				for(String url : listUrls){
					StringBuffer sb = null;
					sb = new StringBuffer();
					webDriver.get(url);
					headline = webDriver.findElements(By.cssSelector("div[class='titpics']>p[class='titin']"));
					time = webDriver.findElements(By.cssSelector("div[class='titpics']>p[class='titcc']"));
					comment = webDriver.findElements(By.cssSelector("form[name='plForm']>div>a[href]"));
					if((headline.size() == 1) && (time.size() == 1) && (comment.size() == 1)){
						sb.append(headline.get(0).getText() + "\t").append(time.get(0).getText() + "\t");
						comment.get(0).click();
						List<WebElement> elements = webDriver.findElements(By.cssSelector("div[class='pnpage']>span[class='font1']"));
						if(elements.size() == 1){
							sb.append(elements.get(0).getText());
						}
						listText.add(sb.toString());
					}		
				}
				listIfengText.add(listText);
			}else if(source.get(i).equals(zongheUrl)){
				webDriver.get(source.get(i));
				List<WebElement> divList = webDriver.findElements(By.cssSelector("div[class='nlist']"));
				for(int j = 0;j<divList.size();j++){
					List<WebElement> list = divList.get(j).findElements(By.tagName("ul")).get(0).findElements(By.tagName("li"));
					for(int k = 0;k<list.size();k++){
						listUrls.add(list.get(k).findElements(By.tagName("a")).get(0).getAttribute("href"));
					}
				}
				
				for(String url : listUrls){
					StringBuffer sb = null;
					sb = new StringBuffer();
					webDriver.get(url);
					headline = webDriver.findElements(By.cssSelector("div[class='titpics']>p[class='titin']"));
					time = webDriver.findElements(By.cssSelector("div[class='titpics']>p[class='titcc']"));
					comment = webDriver.findElements(By.cssSelector("form[name='plForm']>div>a[href]"));
					if((headline.size() == 1) && (time.size() == 1) && (comment.size() == 1)){
						sb.append(headline.get(0).getText() + "\t").append(time.get(0).getText() + "\t");
						comment.get(0).click();
						List<WebElement> elements = webDriver.findElements(By.cssSelector("div[class='pnpage']>span[class='font1']"));
						if(elements.size() == 1){
							sb.append(elements.get(0).getText());
						}
						listText.add(sb.toString());
					}
				}
				listIfengText.add(listText);
			}else{
				
				webDriver.get(source.get(i));
				ulList = webDriver.findElements(By.cssSelector("ul[class='report_alive']>li>div"));
				listText = getPartText(ulList);
				listIfengText.add(listText);				
			}		
		}
		webDriver.close();
		return listIfengText;
		
	}
	
	public static List<String> getPartUrls(List<WebElement> ulList){
		List<String> listUrls = null;
		listUrls = new ArrayList<String>();
		List<WebElement> liList = null;
		List<WebElement> aList = null;
		for(int i = 0;i<ulList.size();i++){
			liList = ulList.get(i).findElements(By.tagName("li"));
			for(int j = 0;j<liList.size();j++){
				aList = liList.get(j).findElements(By.tagName("a"));
				for(int k = 0;k<aList.size();k++){
					listUrls.add(aList.get(k).getAttribute("href"));
				}
			}
		}
		return listUrls;
	}
	
	public static List<String> getPartText(List<WebElement> ulList){
		List<String> listText = null;
		listText = new ArrayList<String>();
		WebDriver driver = new FirefoxDriver();
		List<WebElement> url = null;
		List<WebElement> pinglun = null;
		String pinglunshu = null;
		for(int j = 0;j<ulList.size();j++){
			StringBuffer sb = null;
			sb = new StringBuffer();
			url = ulList.get(j).findElements(By.tagName("a"));
			pinglun = ulList.get(j).findElements(By.tagName("p"));
					
			if((url.size() != 0) && (pinglun.size() != 0)){
				driver.get(url.get(0).getAttribute("href"));
				List<WebElement> headline = driver.findElements(By.cssSelector("div[class='title_text']>h3"));
				List<WebElement> time = driver.findElements(By.cssSelector("div[class='title_text']>p"));
				pinglunshu = pinglun.get(0).getText();
				if((headline.size() == 1) && (time.size() == 1)){
					sb.append(headline.get(0).getText() + "\t");
					sb.append(time.get(0).getText() + "\t");
					sb.append("评论" + pinglunshu);
					listText.add(sb.toString());
				}
			}	
		}
		driver.close();
		return listText;
	}
	
}
