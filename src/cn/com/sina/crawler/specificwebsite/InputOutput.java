package cn.com.sina.crawler.specificwebsite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * 输入输出操作类
 * @author sina
 *
 */
public class InputOutput {

	private String inUrls;  // 输入链接的pathname
	private String outUrls; // 输出url的pathname
	private String outText; // 输出新闻text的pathname
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void setIn(String inUrls){
		this.inUrls = inUrls;
	}
	
	public void setOutUrls(String outUrls){
		this.outUrls = outUrls;
	}
	
	public void setOutText(String outText){
		this.outText = outText;
	}
	/**
	 * 读入URL链接地址
	 * @return 网站频道url的集合
	 * @throws FileNotFoundException
	 */
	public List<String> inputURL() throws FileNotFoundException{
		List<String> listUrls = null;
		listUrls = new ArrayList<String>();
		File file = new File(this.inUrls);
		Scanner scan = new Scanner(file);
		while(scan.hasNextLine()){
			listUrls.add(scan.nextLine());
		}
		scan.close();
		return listUrls;
	}
	/**
	 * 输出新闻URL
	 * @param urls 新闻各频道url集合
	 * @throws IOException
	 */
	public void outputURL(List<List<String>> urls) throws IOException{
		 File file = new File(this.outUrls);
		 OutputStream output = new FileOutputStream(file);
		 for(List<String> list : urls){
			 for(String url : list){
				 output.write(url.getBytes());
				 output.write("\r\n".getBytes());
			 }
		 }
		 output.close();
	 }
	/**
	 * 输出时间和评论数
	 * @param list 时间和评论数集合
	 * @throws IOException
	 */
	public void outputTimeAndJoin(List<String> list) throws IOException{
		File file = new File(this.outText);
		OutputStream output = new FileOutputStream(file);
		for(String str : list){
			output.write(str.getBytes());
			output.write("\r\n".getBytes());
		}
		output.close();
	}
	/**
	 * 输出各频道新闻标题、时间等text内容
	 * @param listText 各频道新闻text集合
	 * @throws IOException
	 */
	public void outputText(List<List<String>> listText) throws IOException{
		 File file = new File(this.outText);
		 OutputStream output = new FileOutputStream(file);
		 int num = 1;
		 for(List<String> list : listText){
			 for(String text : list){
				 output.write(("[新闻 " + num + "]").getBytes());
				 output.write(text.getBytes());
				 output.write("\r\n".getBytes());
				 num++;
			 }
		 }
		 output.close();
	 }

}
