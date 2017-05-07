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

import cn.creat.zhxy.po.AppealParams;
import cn.creat.zhxy.po.AttendParameter;
import cn.creat.zhxy.po.User;
import cn.creat.zhxy.service.AttendListService;
import cn.creat.zhxy.validgrop.AttendValidGropD;
import cn.creat.zhxy.validgrop.AttendValidGropN;


@Controller
@RequestMapping("/attend")
public class AttendListController {
	
	private static String attendListUrl;
	private static String appealUrl;
	
	@Autowired
	private AttendListService attendListService;
	
	static{
		InputStream inStream = AttendListController.class.getClassLoader().getResourceAsStream("urls.properties");
		Properties properties = new Properties();
		try {
			properties.load(inStream);
			AttendListController.setAttendListUrl(properties.getProperty("attendListUrl"));
			AttendListController.setAppealUrl(properties.getProperty("appealUrl"));
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
	
	@RequestMapping(value="/appeal",method=RequestMethod.POST)
	public void appeal(HttpSession session,HttpServletResponse httpResponse,@Validated
			AppealParams appealParams,BindingResult result) throws Exception{
		PrintWriter printWriter = getJsonPrintWriter(httpResponse, result,"IsSucceed",false);
		if(printWriter == null){
			return;
		}
		User user = (User) session.getAttribute("user");
		String json = attendListService.appeal(AttendListController.getAppealUrl(), user, appealParams);
		printWriter.write(json);
		printWriter.flush();
		printWriter.close();
	}
	
	@RequestMapping(value="/getTodayAttend",method=RequestMethod.POST)
	public void getTodayAttend(HttpSession session,HttpServletResponse httpResponse,@Validated(value=
		{AttendValidGropN.class}) AttendParameter attendParameter,BindingResult result) throws Exception{
		PrintWriter printWriter = getJsonPrintWriter(httpResponse, result,"total",-1);
		if(printWriter == null){
			return;
		}
		User user = (User) session.getAttribute("user");
		String json = attendListService.getTodayAttend(AttendListController.getAttendListUrl(), user, attendParameter);
		printWriter.write(json);
		printWriter.flush();
		printWriter.close();
	}
	
	@RequestMapping(value="/getWeekAttend",method=RequestMethod.POST)
	public void getWeekAttend(HttpSession session,HttpServletResponse httpResponse,@Validated(value=
		{AttendValidGropN.class}) AttendParameter attendParameter,BindingResult result) throws Exception{
		PrintWriter printWriter = getJsonPrintWriter(httpResponse, result,"total",-1);
		if(printWriter == null){
			return;
		}
		User user = (User) session.getAttribute("user");
		String json = attendListService.getWeekAttend(AttendListController.getAttendListUrl(), user, attendParameter);
		printWriter.write(json);
		printWriter.flush();
		printWriter.close();
	}
	//获得近一个月考情信息
	@RequestMapping(value="/getMonthAttend",method=RequestMethod.POST)
	public void getMonthAttend(HttpSession session,HttpServletResponse httpResponse,@Validated(value=
		{AttendValidGropN.class}) AttendParameter attendParameter,BindingResult result) throws Exception{
		PrintWriter printWriter = getJsonPrintWriter(httpResponse, result,"total",-1);
		if(printWriter == null){
			return;
		}
		User user = (User) session.getAttribute("user");
		String json = attendListService.getMonthAttend(AttendListController.getAttendListUrl(), user, attendParameter);
		printWriter.write(json);
		printWriter.flush();
		printWriter.close();
	}
	
	@RequestMapping(value="/getDiyAttend",method=RequestMethod.POST)
	public void getDiyAttend(HttpSession session,HttpServletResponse httpResponse,@Validated(value=
		{AttendValidGropD.class}) AttendParameter attendParameter,BindingResult result) throws Exception{
		PrintWriter printWriter = getJsonPrintWriter(httpResponse, result,"total",-1);
		if(printWriter == null){
			return;
		}
		User user = (User) session.getAttribute("user");
		String json = attendListService.getDiyAttend(AttendListController.getAttendListUrl(), user, attendParameter);
		printWriter.write(json);
		printWriter.flush();
		printWriter.close();
	}
	
	public static String getAttendListUrl() {
		return attendListUrl;
	}

	public static void setAttendListUrl(String attendListUrl) {
		AttendListController.attendListUrl = attendListUrl;
	}
	
	private String errorToString(BindingResult result,String type,Object value){
		Map<String, Object> errors = new HashMap<String, Object>();
		errors.put(type, value);
		StringBuilder sb = new StringBuilder();
		for(ObjectError error : result.getAllErrors()){
			sb.append(error.getDefaultMessage());
			sb.append(";");
		}
		errors.put("Msg", sb.toString());
		return JSONObject.fromObject(errors).toString();
	}
	
	private PrintWriter getJsonPrintWriter(HttpServletResponse response,BindingResult result,String type,Object value) throws IOException{
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter =  response.getWriter();
		if(result.hasErrors()){
			printWriter.write(errorToString(result,type,value));
			printWriter.flush();
			printWriter.close();
			return null;
		}
		return printWriter;
	}
	
	public static String getAppealUrl() {
		return appealUrl;
	}

	public static void setAppealUrl(String appealUrl) {
		AttendListController.appealUrl = appealUrl;
	}
}
