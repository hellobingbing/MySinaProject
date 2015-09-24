package cn.com.sina.crawler.search;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * 使用webDriver，抓取新华网上的新闻标题、时间、内容
 * @author sina
 *
 */
class XinhuaCrawlerWebdriver {

	private WebDriver webDriver;
	private String title;
	private String time;
	private String content;
	
	
	public XinhuaCrawlerWebdriver(WebDriver webDriver){
		this.webDriver = webDriver;
	}
	/**
	 * 取得新闻标题_时间_正文的list
	 * @param url 新闻url
	 * @return 一条新闻标题_时间_正文的list，0：标题1：时间2：正文
	 * @throws Exception
	 */
	public List<String> getTitle_Time_Content(String url) throws Exception{
		List<String> news = new ArrayList<String>();
		
		this.webDriver.get(url);
		// title
		List<WebElement> listTitle1 = this.webDriver.findElements(By.cssSelector("h1[id='title']"));
		List<WebElement> listTitle2 = this.webDriver.findElements(By.cssSelector("span[id='title']"));
		if(listTitle1.size() == 1){
			this.title = listTitle1.get(0).getText().trim();
		}else if(listTitle2.size() == 1){
			this.title = listTitle2.get(0).getText().trim();
		}else{
			this.title = this.webDriver.getTitle();
		}
		
		// time
		List<WebElement> listTime1 = this.webDriver.findElements(By.cssSelector("span[id='pubtime']"));
		List<WebElement> listTime2 = this.webDriver.findElements(By.cssSelector("span[class='time']"));
		List<WebElement> listTime3 = this.webDriver.findElements(By.cssSelector("div[id='pubtimeandfrom']"));
		List<WebElement> listTime4 = this.webDriver.findElements(By.cssSelector("td[class='gray fs12']"));
		if(listTime1.size() == 1){
			this.time = listTime1.get(0).getText().trim();
		}else if(listTime2.size() == 1){
			this.time = listTime2.get(0).getText().trim();
		}else if(listTime3.size() == 1){
			this.time = listTime3.get(0).getText().trim().substring(0, 19);
		}else if(listTime4.size() == 1){
			this.time = listTime4.get(0).getText().trim().substring(0, 20);
		}else{
			this.time = "null";
		}
		
		// content
		List<WebElement> list1 = this.webDriver.findElements(By.cssSelector("div[id='content']"));
		List<WebElement> list2 = this.webDriver.findElements(By.cssSelector("div[id='Content']"));
		List<WebElement> list3 = this.webDriver.findElements(By.cssSelector("div[id='contentblock']"));
		List<WebElement> list4 = this.webDriver.findElements(By.cssSelector("div[class='article']"));
		List<WebElement> list5 = this.webDriver.findElements(By.cssSelector("span[id='content']"));
		List<WebElement> list6 = this.webDriver.findElements(By.cssSelector("div[class='bai14']"));
		if(list1.size() == 1){
			this.content = list1.get(0).getText().trim();
		}else if(list2.size() == 1){
			this.content = list2.get(0).getText().trim();
		}else if(list3.size() == 1){
			this.content = list3.get(0).getText().trim();
		}else if(list4.size() == 1){
			this.content = list4.get(0).getText().trim();
		}else if(list5.size() == 1){
			this.content = list5.get(0).getText().trim();
		}else if(list6.size() == 1){
			this.content = list6.get(0).getText().trim();
		}else{
			this.content = "null";
		}
	
		news.add(this.title);
		news.add(this.time);
		news.add(this.content);
	
		return news;
	}
}
