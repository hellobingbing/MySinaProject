package cn.com.sina.crawler.search;

import java.io.IOException;
import java.net.MalformedURLException;
//import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
/**
 * 此类用于直接运行
 * @author sina
 *
 */
public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//webDriverMethod();
		htmlunitMethod();
		
	}
	/**
	 * webDriver方法
	 */
	public static void webDriverMethod(){
		
		// 从数据库中得到id和事件名称
		String two_level_class = "传统节日";
		String siteUrl = "news.xinhuanet.com";
		String urlRegex = "(http://news\\.xinhuanet\\.com/.*htm)";
		Map<Integer, String> idAndName = DBOperation.dataFromDB(two_level_class);
				
		// 针对每一个id即事件名称高级搜索相关新闻链接
		List<String> urlList = null;
		List<String> idAndUrl = null;
		List<List<String>> idAndUrlList = new ArrayList<List<String>>();
		for(Map.Entry<Integer, String> me : idAndName.entrySet()){
			int id = me.getKey();
			String eventName = me.getValue();
			urlList = BasicAdvancedSearch.getUrlList(eventName, siteUrl, urlRegex);
			for(int i = 0;i<urlList.size();i++){
				idAndUrl = new ArrayList<String>();
				idAndUrl.add(Integer.toString(id));
				idAndUrl.add(urlList.get(i));
				idAndUrlList.add(idAndUrl);
			}
		}
		for(List<String> list : idAndUrlList){
			System.out.println(list.get(0) + "\t" + list.get(1));
		}
		
		// xinhuanet 对某url爬取title、time、content
		WebDriver webDriver = new FirefoxDriver();
		XinhuaCrawlerWebdriver xinhuaCrawlerWeb = new XinhuaCrawlerWebdriver(webDriver);
				
		List<List<String>> someNews = new ArrayList<List<String>>();
		List<String> news = null;
		List<String> Title_Time_Content = null;
		for(int i = 0;i<idAndUrlList.size();i++){
			news = new ArrayList<String>();
			Title_Time_Content = new ArrayList<String>();
			// id
			int id = Integer.parseInt(idAndUrlList.get(i).get(0));
			// url
			String url = idAndUrlList.get(i).get(1);
			
			try {
				Title_Time_Content = xinhuaCrawlerWeb.getTitle_Time_Content(url);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			// title
			String title = Title_Time_Content.get(0);
			// time
			String time = Title_Time_Content.get(1);
			// content
			String content = Title_Time_Content.get(2);
			
			news.add(Integer.toString(id));
			news.add(title);
			news.add(time);
			news.add(content);
			news.add(url);
			
			someNews.add(news);
		}
		webDriver.close();
		System.out.println(someNews.size());
		// 并写入数据库
		//DBOperation.dataToDB(someNews);
		
	}
	/**
	 * htmlunit方法
	 */
	public static void htmlunitMethod(){
		
		// 从数据库中得到id和事件名称
		String two_level_class = "传统节日";
		String siteUrl = "news.xinhuanet.com";
		String urlRegex = "(http://news\\.xinhuanet\\.com/.*htm)";
		Map<Integer, String> idAndName = DBOperation.dataFromDB(two_level_class);
				
		// 针对每一个id即事件名称高级搜索相关新闻链接
		List<String> urlList = null;
		List<String> idAndUrl = null;
		List<List<String>> idAndUrlList = new ArrayList<List<String>>();
		for(Map.Entry<Integer, String> me : idAndName.entrySet()){
			int id = me.getKey();
			String eventName = me.getValue();
			urlList = BasicAdvancedSearch.getUrlList(eventName, siteUrl, urlRegex);
			for(int i = 0;i<urlList.size();i++){
				idAndUrl = new ArrayList<String>();
				idAndUrl.add(Integer.toString(id));
				idAndUrl.add(urlList.get(i));
				idAndUrlList.add(idAndUrl);
			}
		}
		for(List<String> list : idAndUrlList){
			System.out.println(list.get(0) + "\t" + list.get(1));
		} 
				
		// xinhuanet 对某url爬取title、time、content
		WebClient webClient = new WebClient();
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		HtmlPage htmlPage = null;
				
		List<List<String>> someNews = new ArrayList<List<String>>();
		List<String> news = null;
		for(int i = 0;i<idAndUrlList.size();i++){
			news = new ArrayList<String>();
			// id
			int id = Integer.parseInt(idAndUrlList.get(i).get(0));
			// url
			String url = idAndUrlList.get(i).get(1);
			try {
				htmlPage = webClient.getPage(url);
						
			} catch (FailingHttpStatusCodeException e1) {
				continue;
			} catch (MalformedURLException e1) {
				continue;
			} catch (IOException e1) {
				continue;
			} catch (Exception e) {
				continue;
			}
			XinhuaCrawlerHtmlunit xinhuaCrawler = new XinhuaCrawlerHtmlunit(htmlPage);
			// title
			String title = null;
			title = xinhuaCrawler.getTitle();
			// time
			String time = null;
			time = xinhuaCrawler.getTime();
			// content
			String content = null;
			content = xinhuaCrawler.getContent();
				
			news.add(Integer.toString(id));
			news.add(title);
			news.add(time);
			news.add(content);
			news.add(url);
			someNews.add(news);
		}
		webClient.close();
		//for(List<String> list : someNews){
			//for(String str : list){
				//System.out.print(str + "\t");
			//}
			//System.out.println();
		//}
				
		// 并写入数据库
		//DBOperation.dataToDB(someNews);
	}

}
