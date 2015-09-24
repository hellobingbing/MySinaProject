package cn.com.sina.crawler.search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * �ٶ����Ÿ߼������ؼ��ʣ����������ŵ�url����
 * @author sina
 *
 */
class BasicAdvancedSearch {
	// �ٶ����Ÿ߼�������ַ
	public static final String URL = "http://news.baidu.com/advanced_news.html";
	
	//private List<String> urlList;
	
	/**
	 * ȡ����ؼ�����ص�����url����
	 * @param keywords �ؼ���
	 * @param siteUrl �������ڵ���վ��news.xinhuanet.com
	 * @param urlRegex ����վ����url��������ʽ
	 * @return �������url����
	 */
	public static List<String> getUrlList(String keywords,String siteUrl,String urlRegex){
		List<String> urlList = new ArrayList<String>();
		WebClient webClient = new WebClient();
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		
		try {
			//�߼����� �ٶ�����
			HtmlPage page = webClient.getPage(URL);
			
			HtmlForm form = page.getFormByName("f");
			
			//������ť "�ٶ�һ��"
			HtmlSubmitInput button = form.getInputByValue("�ٶ�һ��");
			
			//����Ҫ�����Ĺؼ���  ����ȫ���ؼ���
			HtmlTextInput textInput = form.getInputByName("q1");
			textInput.setValueAttribute(keywords);
			
			// �޶�Ҫ���������ŵ�ʱ�� radio button
			List<HtmlRadioButtonInput> radioButtonInputs = form.getRadioButtonsByName("s");
			// ȫ��ʱ��
			radioButtonInputs.get(0).setChecked(true);
			// ʱ���
			radioButtonInputs.get(1).setChecked(false);
			
			//���ùؼ���λ��
			List<HtmlRadioButtonInput> radioButtons = form.getRadioButtonsByName("tn");
			radioButtons.get(0).setChecked(false);
			radioButtons.get(1).setChecked(true);
			
			//�����������ÿҳ��ʾ����
			HtmlSelect select = form.getSelectByName("rn");
			select.setDefaultValue("10");
			
			//�޶�Ҫ����������Դ
			HtmlTextInput textInput_Url = form.getInputByName("q6");
			textInput_Url.setText(siteUrl);
			
			//����������ҳ��
			HtmlPage resultPage = button.click();
			String resultXml = resultPage.asXml();
			// ����������ҳ���ϵ����ݣ�������url��������ƥ��
			urlList = StringParser.getStringByRegex(resultXml, urlRegex);
				
			webClient.close();
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return urlList;
	}
	/**
	 * ȡ����ؼ�����ص�����url����
	 * @param keywords �ؼ���
	 * @param siteUrl �������ڵ���վ��news.xinhuanet.com
	 * @param startDate ��ʼ����
	 * @param endDate ��ֹ����
	 * @param urlRegex ����վ����url��������ʽ
	 * @return �������url����
	 */
	public static List<String> getUrlList(String keywords,String siteUrl,String startDate,String endDate,String urlRegex){
		List<String> urlList = new ArrayList<String>();
		WebClient webClient = new WebClient();
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		
		try {
			//�߼����� �ٶ�����
			HtmlPage page = webClient.getPage(URL);
			
			HtmlForm form = page.getFormByName("f");
			
			//������ť "�ٶ�һ��"
			HtmlSubmitInput button = form.getInputByValue("�ٶ�һ��");
			
			//����Ҫ�����Ĺؼ���  ����ȫ���ؼ���
			HtmlTextInput textInput = form.getInputByName("q1");
			textInput.setValueAttribute(keywords);
			
			// �޶�Ҫ���������ŵ�ʱ�� radio button
			List<HtmlRadioButtonInput> radioButtonInputs = form.getRadioButtonsByName("s");
			// ȫ��ʱ��
			radioButtonInputs.get(0).setChecked(false);
			// ʱ���
			radioButtonInputs.get(1).setChecked(true);
			
			//������ֹʱ��
			HtmlTextInput textInput_startDate = form.getInputByName("begin_date");
			textInput_startDate.setValueAttribute(startDate);
			HtmlTextInput textInput_endDate = form.getInputByName("end_date");
			textInput_endDate.setValueAttribute(endDate);
			
			//���ùؼ���λ��
			List<HtmlRadioButtonInput> radioButtons = form.getRadioButtonsByName("tn");
			radioButtons.get(0).setChecked(false);
			radioButtons.get(1).setChecked(true);
			
			//�����������ÿҳ��ʾ����
			HtmlSelect select = form.getSelectByName("rn");
			select.setDefaultValue("10");
			
			//�޶�Ҫ����������Դ
			HtmlTextInput textInput_Url = form.getInputByName("q6");
			textInput_Url.setText(siteUrl);
			
			//����������ҳ��
			HtmlPage resultPage = button.click();
			String resultXml = resultPage.asXml();
			// ����������ҳ���ϵ����ݣ�������url��������ƥ��
			urlList = StringParser.getStringByRegex(resultXml, urlRegex);
				
			webClient.close();
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return urlList;
	}

}
