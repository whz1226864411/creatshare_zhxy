package cn.creat.zhxy.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
	
}
