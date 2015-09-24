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
 * �������������
 * @author sina
 *
 */
public class InputOutput {

	private String inUrls;  // �������ӵ�pathname
	private String outUrls; // ���url��pathname
	private String outText; // �������text��pathname
	
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
	 * ����URL���ӵ�ַ
	 * @return ��վƵ��url�ļ���
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
	 * �������URL
	 * @param urls ���Ÿ�Ƶ��url����
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
	 * ���ʱ���������
	 * @param list ʱ�������������
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
	 * �����Ƶ�����ű��⡢ʱ���text����
	 * @param listText ��Ƶ������text����
	 * @throws IOException
	 */
	public void outputText(List<List<String>> listText) throws IOException{
		 File file = new File(this.outText);
		 OutputStream output = new FileOutputStream(file);
		 int num = 1;
		 for(List<String> list : listText){
			 for(String text : list){
				 output.write(("[���� " + num + "]").getBytes());
				 output.write(text.getBytes());
				 output.write("\r\n".getBytes());
				 num++;
			 }
		 }
		 output.close();
	 }

}
