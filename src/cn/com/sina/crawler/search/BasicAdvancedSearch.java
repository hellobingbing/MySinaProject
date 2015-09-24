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
 * 百度新闻高级搜索关键词，获得相关新闻的url集合
 * @author sina
 *
 */
class BasicAdvancedSearch {
	// 百度新闻高级搜索地址
	public static final String URL = "http://news.baidu.com/advanced_news.html";
	
	//private List<String> urlList;
	
	/**
	 * 取得与关键词相关的新闻url集合
	 * @param keywords 关键词
	 * @param siteUrl 新闻所在的网站，news.xinhuanet.com
	 * @param urlRegex 此网站新闻url的正则表达式
	 * @return 相关新闻url集合
	 */
	public static List<String> getUrlList(String keywords,String siteUrl,String urlRegex){
		List<String> urlList = new ArrayList<String>();
		WebClient webClient = new WebClient();
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		
		try {
			//高级搜索 百度新闻
			HtmlPage page = webClient.getPage(URL);
			
			HtmlForm form = page.getFormByName("f");
			
			//搜索按钮 "百度一下"
			HtmlSubmitInput button = form.getInputByValue("百度一下");
			
			//设置要搜索的关键词  包含全部关键词
			HtmlTextInput textInput = form.getInputByName("q1");
			textInput.setValueAttribute(keywords);
			
			// 限定要搜索的新闻的时间 radio button
			List<HtmlRadioButtonInput> radioButtonInputs = form.getRadioButtonsByName("s");
			// 全部时间
			radioButtonInputs.get(0).setChecked(true);
			// 时间段
			radioButtonInputs.get(1).setChecked(false);
			
			//设置关键词位置
			List<HtmlRadioButtonInput> radioButtons = form.getRadioButtonsByName("tn");
			radioButtons.get(0).setChecked(false);
			radioButtons.get(1).setChecked(true);
			
			//设置搜索结果每页显示条数
			HtmlSelect select = form.getSelectByName("rn");
			select.setDefaultValue("10");
			
			//限定要搜索的新闻源
			HtmlTextInput textInput_Url = form.getInputByName("q6");
			textInput_Url.setText(siteUrl);
			
			//获得搜索结果页面
			HtmlPage resultPage = button.click();
			String resultXml = resultPage.asXml();
			// 将搜索到的页面上的内容，与新闻url的正则相匹配
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
	 * 取得与关键词相关的新闻url集合
	 * @param keywords 关键词
	 * @param siteUrl 新闻所在的网站，news.xinhuanet.com
	 * @param startDate 开始日期
	 * @param endDate 截止日期
	 * @param urlRegex 此网站新闻url的正则表达式
	 * @return 相关新闻url集合
	 */
	public static List<String> getUrlList(String keywords,String siteUrl,String startDate,String endDate,String urlRegex){
		List<String> urlList = new ArrayList<String>();
		WebClient webClient = new WebClient();
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		
		try {
			//高级搜索 百度新闻
			HtmlPage page = webClient.getPage(URL);
			
			HtmlForm form = page.getFormByName("f");
			
			//搜索按钮 "百度一下"
			HtmlSubmitInput button = form.getInputByValue("百度一下");
			
			//设置要搜索的关键词  包含全部关键词
			HtmlTextInput textInput = form.getInputByName("q1");
			textInput.setValueAttribute(keywords);
			
			// 限定要搜索的新闻的时间 radio button
			List<HtmlRadioButtonInput> radioButtonInputs = form.getRadioButtonsByName("s");
			// 全部时间
			radioButtonInputs.get(0).setChecked(false);
			// 时间段
			radioButtonInputs.get(1).setChecked(true);
			
			//设置起止时间
			HtmlTextInput textInput_startDate = form.getInputByName("begin_date");
			textInput_startDate.setValueAttribute(startDate);
			HtmlTextInput textInput_endDate = form.getInputByName("end_date");
			textInput_endDate.setValueAttribute(endDate);
			
			//设置关键词位置
			List<HtmlRadioButtonInput> radioButtons = form.getRadioButtonsByName("tn");
			radioButtons.get(0).setChecked(false);
			radioButtons.get(1).setChecked(true);
			
			//设置搜索结果每页显示条数
			HtmlSelect select = form.getSelectByName("rn");
			select.setDefaultValue("10");
			
			//限定要搜索的新闻源
			HtmlTextInput textInput_Url = form.getInputByName("q6");
			textInput_Url.setText(siteUrl);
			
			//获得搜索结果页面
			HtmlPage resultPage = button.click();
			String resultXml = resultPage.asXml();
			// 将搜索到的页面上的内容，与新闻url的正则相匹配
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
