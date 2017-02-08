package cn.creat.zhxy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.creat.zhxy.po.User;
import cn.creat.zhxy.service.UserService;
import cn.creat.zhxy.utils.httputil.MyHttpClient;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static String codeUrl;
	private static String loginUrl;
	private static String logoutUrl;
	@Autowired
	private UserService userService;
	
	static{
		InputStream inStream = UserController.class.getClassLoader().getResourceAsStream("urls.properties");
		Properties properties = new Properties();
		try {
			properties.load(inStream);
			UserController.setCodeUrl(properties.getProperty("codeUrl"));
			UserController.setLoginUrl(properties.getProperty("loginUrl"));
			UserController.setLogoutUrl(properties.getProperty("logoutUrl"));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(inStream != null){
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//登录
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public void login(HttpSession session,HttpServletResponse httpResponse,@Validated User user,
			BindingResult result) throws Exception{
		httpResponse.setContentType("application/json;charset=utf-8");
		httpResponse.setCharacterEncoding("utf-8");
		PrintWriter printWriter =  httpResponse.getWriter();
		if(result.hasErrors()){
			Map<String, Object> errors = new HashMap<String, Object>();
			errors.put("Obj", 999);
			errors.put("IsSucceed", false);
			StringBuilder sb = new StringBuilder();
			for(ObjectError error : result.getAllErrors()){
				sb.append(error.getDefaultMessage());
				sb.append(";");
			}
			errors.put("Msg", sb.toString());
			printWriter.write(JSONObject.fromObject(errors).toString());
			printWriter.flush();
			printWriter.close();
			return;
		}
		user.setCilent(new MyHttpClient());
		String loginResult = userService.login(user,
				UserController.getLoginUrl(),UserController.getCodeUrl());
		if(loginResult.contains("true")){
			session.setAttribute("user", user);
		}
//System.out.println(loginResult);
		printWriter.write(loginResult);
		printWriter.flush();
		printWriter.close();
	}
	
	//退出
	@RequestMapping(value="/logout")
	public void logout(HttpSession session,HttpServletResponse httpResponse) throws Exception{
		httpResponse.setContentType("application/json;charset=utf-8");
		httpResponse.setCharacterEncoding("utf-8");
		PrintWriter printWriter =  httpResponse.getWriter();
		User user = (User) session.getAttribute("user");
		userService.logout(user, UserController.getLogoutUrl());
		session.invalidate();
		printWriter.write("{\"total\":-3,\"Msg\":\"退出成功\"}");
		printWriter.flush();
		printWriter.close();
	}
	
	public static String getCodeUrl() {
		return codeUrl;
	}
	public static void setCodeUrl(String codeUrl) {
		UserController.codeUrl = codeUrl;
	}
	public static String getLoginUrl() {
		return loginUrl;
	}
	public static void setLoginUrl(String loginUrl) {
		UserController.loginUrl = loginUrl;
	}
	public static String getLogoutUrl() {
		return logoutUrl;
	}
	public static void setLogoutUrl(String logoutUrl) {
		UserController.logoutUrl = logoutUrl;
	}
}
