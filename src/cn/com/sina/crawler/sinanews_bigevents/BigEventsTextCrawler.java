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
 * ֻ���./crawler/��������0701-0810.xlsx ��sina���¼���������ĵ�ץȡ
 * @author sina
 *
 */
public class BigEventsTextCrawler {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// TODO Auto-generated method stub
		List<String> urls = null;
		// ���Ŵ��¼�url·��
		String pathname = "";
		urls = inputUrls(pathname);
		getText(urls);
		
	}
	/**
	 * ��ñ��������,ÿ�������½�һ��txt��д��
	 * @param listUrl �����¼�url����
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
		//ѭ���õ�ÿһ��ҳ��
		for(int i = 0;i<listUrl.size();i++){
			try {
				page = webClient.getPage(listUrl.get(i));
							
			} catch (FailingHttpStatusCodeException e) {
				// TODO: handle exception
				continue;
			}
			
			list1 = page.getByXPath("/html/body/div[@id='wrapOuter']/"
					+ "div[@class='wrap-inner']/div[@class='page-header']/h1");
			list2 = page.getByXPath("/html/body/div[@id='wrapOuter']/"
					+ "div[@class='wrap-inner']/div[@id='articleContent']/"
					+ "div[@class='left']/div[@id='artibody']/p");
			if((list1.size() != 0) && (list2.size() != 0)){
				HtmlElement headline = (HtmlElement)list1.get(0);
				List<HtmlElement> paragraphs = (List<HtmlElement>)list2; 
				StringBuffer sb = null;
				sb = new StringBuffer();
				//sb.append("[ ����   " + num +" ] ");
				//sb.append(listUrl.get(i));
				//sb.append("\r\n");
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
	 * ����url
	 * @param pathname ���url�ļ���·����ÿ��һ��url
	 * @return ���Ŵ��¼�url����
	 * @throws FileNotFoundException
	 */
	public static List<String> inputUrls(String pathname) throws FileNotFoundException{
		List<String> urls = new ArrayList<String>();
		// ���Ŵ��¼�url��·��
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
	 * ��������¼��ı�������ģ�ÿ�������½�һ��txt��д������
	 * @param text ���������
	 * @param num �ڼ������ţ���Ϊ�½���txt�ĵ���
	 * @throws IOException
	 */
	public static void outputText(String text,int num) throws IOException{
		// ����ĵ���·��
		String path = "";
		File file = new File(path + File.separator + num + ".txt");
		OutputStream outputStream = new FileOutputStream(file);
		outputStream.write(text.getBytes());
		outputStream.close();
	}
}
