package cn.com.sina.crawler.sinanews_bigevents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
/**
 * 只针对./crawler/热门新闻0701-0810.xlsx 中sina新闻标题和正文的抓取
 * @author sina
 *
 */
public class SinaNewsTextCrawler {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// TODO Auto-generated method stub
		List<String> urls = null;
		// sina新闻url路径
		String pathname = "";
		urls = inputUrls(pathname);
		getText(urls);
		
		
	}
	/**
	 * 获得标题和正文，每条新闻新建一个txt并写入
	 * @param listUrl 新闻url集合
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void getText(List<String> listUrl) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		WebClient webClient = new WebClient();
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		List<?> list1 = null;
		List<?> list2 = null;
		HtmlPage page = null;
		//List<String> listContent = new ArrayList<String>();
		int num = 1;
		//循环得到每一个页面
		for(int i = 0;i<listUrl.size();i++){
			try {
				page = webClient.getPage(listUrl.get(i));
							
			} catch (FailingHttpStatusCodeException e) {
				// TODO: handle exception
				continue;
			}
			
			list1 = page.getByXPath("/html/body/div[@id='j_articleContent']/"
					+ "div[@class='article-module title']/h2");
			list2 = page.getByXPath("/html/body/div[@id='j_articleContent']/p");
			if((list1.size() != 0) && (list2.size() != 0)){
				HtmlElement headline = (HtmlElement)list1.get(0);
				List<HtmlElement> paragraphs = (List<HtmlElement>)list2; 
				StringBuffer sb = null;
				sb = new StringBuffer();
				//sb.append("[ 新闻   " + num +" ] ");
				sb.append(listUrl.get(i));
				sb.append("\r\n");
				sb.append(headline.getTextContent());
				sb.append("\r\n");
				for(HtmlElement element : paragraphs){
					sb.append(element.getTextContent() + "\r\n");
				}
				System.out.println(num);
				outputText(sb.toString(), num);
				num++;
			}
		}
		webClient.close();
		//return listContent;
	}
	/**
	 * 读入url
	 * @param pathname 存放url文件的路径，每行一条url
	 * @return 新闻url集合
	 * @throws FileNotFoundException
	 */
	public static List<String> inputUrls(String pathname) throws FileNotFoundException{
		List<String> urls = new ArrayList<String>();
		// 新闻url的路径
		File file = new File(pathname);
		InputStream input = new FileInputStream(file);
		Scanner scan = new Scanner(input);
		while(scan.hasNextLine()){
			urls.add(scan.nextLine());
		}
		scan.close();
		return urls;
	}
	/**
	 * 输出新闻事件的标题和正文，每条新闻新建一个txt并写入内容
	 * @param text 标题和正文
	 * @param num 第几条新闻，作为新建的txt文档名
	 * @throws IOException
	 */
	public static void outputText(String text,int num) throws IOException{
		// 输出文档的路径
		File file = new File("." + File.separator + "crawler" + File.separator +
				"news_sina" + File.separator + "news_text" + File.separator + num + ".txt");
		OutputStream outputStream = new FileOutputStream(file);
		outputStream.write(text.getBytes());
		outputStream.close();
	}
}
