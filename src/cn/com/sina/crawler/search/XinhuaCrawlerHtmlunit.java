package cn.com.sina.crawler.search;

//import java.io.IOException;
//import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
//import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
//import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
/**
 * 使用htmlunit，抓取新华网上的新闻标题、时间、内容
 * @author sina
 *
 */
class XinhuaCrawlerHtmlunit {
	
	private HtmlPage htmlPage;
	private String title;
	private String time;
	private String content;
	private String url;
	
	public XinhuaCrawlerHtmlunit(HtmlPage htmlPage){
		this.htmlPage = htmlPage;
	}
	
	/**
	 * 取得新闻url
	 * @param url
	 * @return url
	 */
	public String getUrl(String url){
		this.url = url;
		return this.url;
	}
	/**
	 * 取得新闻标题
	 * @return title
	 */
	public String getTitle(){
		//WebClient webClient = new WebClient();
		//webClient.getOptions().setCssEnabled(false);
		//webClient.getOptions().setJavaScriptEnabled(false);
		//HtmlPage htmlPage = null;
		HtmlElement htmlTitle = null;
		try {
			//htmlPage = webClient.getPage(url);
			htmlTitle = this.htmlPage.getElementById("title", false);
			this.title = htmlTitle.getTextContent().trim();
		} catch (ElementNotFoundException e) {
			this.title = this.htmlPage.getTitleText().trim();
		} catch (Exception e) {
			this.title = "null";
		}
		//webClient.close();
		return this.title;
	}
	/**
	 * 取得新闻时间
	 * @return time
	 */
	public String getTime() {
		//WebClient webClient = new WebClient();
		//webClient.getOptions().setCssEnabled(false);
		//webClient.getOptions().setJavaScriptEnabled(false);
		//HtmlPage htmlPage = null;
		//HtmlElement htmlTime = null;
		
		//htmlPage = webClient.getPage(url);
		//htmlTime = htmlPage.getElementById("pubtime", false);
		//this.time = htmlTime.getTextContent().trim();
		
		List<?> list1 = this.htmlPage.getByXPath("//span[@id='pubtime']");
		List<?> list2 = this.htmlPage.getByXPath("//span[@class='time']");
		List<?> list3 = this.htmlPage.getByXPath("//div[@id='pubtimeandfrom']");
		List<?> list4 = this.htmlPage.getByXPath("//td[@class='gray fs12']");
		//List<?> list5 = this.htmlPage.getByXPath("//td[@height='22']");
		if(list1.size() == 1){
			HtmlElement element = (HtmlElement)list1.get(0);
			this.time = element.getTextContent().trim();
		}else if(list2.size() == 1){
			HtmlElement element = (HtmlElement)list2.get(0);
			this.time = element.getTextContent().trim();
		}else if(list3.size() == 1){
			HtmlElement element = (HtmlElement)list3.get(0);
			this.time = element.getTextContent().trim().substring(0, 19);
		}else if(list4.size() == 1){
			HtmlElement element = (HtmlElement)list4.get(0);
			this.time = element.getTextContent().trim().substring(0, 20);
		}else{
			this.time = "null";
		}
		//webClient.close();
		return this.time;
	}
	/**
	 * 取得新闻正文
	 * @return content
	 */
	public String getContent() {
		//WebClient webClient = new WebClient();
		//webClient.getOptions().setCssEnabled(false);
		//webClient.getOptions().setJavaScriptEnabled(false);
		//HtmlPage htmlPage = null;
		//HtmlElement htmlElement = null;
		
		//htmlPage = webClient.getPage(url);
		//htmlElement = htmlPage.getElementById("content", false);
		//this.content = htmlElement.getTextContent().trim();
		
		List<?> list1 = this.htmlPage.getByXPath("//div[@id='content']");
		List<?> list2 = this.htmlPage.getByXPath("//div[@id='Content']");
		List<?> list3 = this.htmlPage.getByXPath("//div[@id='contentblock']");
		List<?> list4 = this.htmlPage.getByXPath("//div[@class='article']");
		List<?> list5 = this.htmlPage.getByXPath("//span[@id='content']");
		List<?> list6 = this.htmlPage.getByXPath("//div[@class='bai14']");
		if(list1.size() == 1){
			HtmlElement element = (HtmlElement)list1.get(0);
			this.content = element.getTextContent().trim();
		}else if(list2.size() == 1){
			HtmlElement element = (HtmlElement)list2.get(0);
			this.content = element.getTextContent().trim();
		}else if(list3.size() == 1){
			HtmlElement element = (HtmlElement)list3.get(0);
			this.content = element.getTextContent().trim();
		}else if(list4.size() == 1){
			HtmlElement element = (HtmlElement)list4.get(0);
			this.content = element.getTextContent().trim();
		}else if(list5.size() == 1){
			HtmlElement element = (HtmlElement)list5.get(0);
			this.content = element.getTextContent().trim();
		}else if(list6.size() == 1){
			HtmlElement element = (HtmlElement)list6.get(0);
			this.content = element.getTextContent().trim();
		}else{
			this.content = "null";
		}
		//webClient.close();
		return this.content;
	}

}
