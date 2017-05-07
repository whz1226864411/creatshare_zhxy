package cn.creat.zhxy.service.impl;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import cn.creat.zhxy.po.User;
import cn.creat.zhxy.service.UserService;
import cn.creat.zhxy.utils.httputil.MyHttpClient;
import cn.creat.zhxy.utils.parsecode.ParseCode;

public class UserServiceImpl implements UserService{

	public String login(User user,String loginUrl,String codeUrl) throws Exception {
		MyHttpClient client =user.getCilent();
		String body = "";
		do{
			CloseableHttpResponse response1 = client.sendGet(codeUrl);
			HttpEntity httpEntity1 = response1.getEntity();
			InputStream inputStream = httpEntity1.getContent();
			String code = ParseCode.getResultCode(inputStream);
			response1.close();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("UserName", user.getUserName());
			map.put("UserPassword", user.getUserPassword());
			map.put("ValiCode", code);
			map.put("json", true);
			CloseableHttpResponse response2 = client.sendPost(loginUrl, map, "utf-8",true);
			HttpEntity entity = response2.getEntity(); 
	        body = EntityUtils.toString(entity, "utf-8");
		}while(body.contains("验证码输入错误"));
		return body;
	}

	public void logout(User user, String logoutUrl) throws Exception {
		MyHttpClient client =user.getCilent();
		CloseableHttpResponse response = client.sendGet(logoutUrl);
		response.close();
	}

	public String getStuClass(User user,String stuClassUrl) throws Exception {
		MyHttpClient client =user.getCilent();
		String term_no = "";
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH)+1;
		int day = date.get(Calendar.DAY_OF_MONTH);
		if(month > 8 || (month == 8 && day >20)){
			term_no = year+"-"+(year+1)+"-1";
		}else{
			term_no = (year-1)+"-"+year+"-2";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("json", true);
		map.put("term_no", term_no);
		CloseableHttpResponse response = client.sendPost(stuClassUrl, map, "utf-8", false);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "utf-8");
		response.close();
		return json;
	}
	
}
