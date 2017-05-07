package cn.creat.zhxy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import cn.creat.zhxy.po.AppealParams;
import cn.creat.zhxy.po.AttendParameter;
import cn.creat.zhxy.po.User;
import cn.creat.zhxy.service.AttendListService;
import cn.creat.zhxy.utils.dateparse.DateParse;
import cn.creat.zhxy.utils.httputil.MyHttpClient;
import cn.creat.zhxy.utils.params.ParamsToMap;

public class AttendListServiceImpl implements AttendListService{

	public String getTodayAttend(String url, User user,
			AttendParameter attendParameter) throws Exception {
		Date start = new Date();
		attendParameter.setStart(DateParse.dateToString(start, "yyyy-MM-dd"));
		attendParameter.setEnd(DateParse.dateToString(start, "yyyy-MM-dd"));
		return getAttend(url, user, attendParameter, 1);
	}

	public String getWeekAttend(String url, User user,
			AttendParameter attendParameter) throws Exception {
		Date end = new Date();
		Date start = new Date(end.getTime()-1000*60*60*24*7);
		attendParameter.setStart(DateParse.dateToString(start, "yyyy-MM-dd"));
		attendParameter.setEnd(DateParse.dateToString(end, "yyyy-MM-dd"));
		return getAttend(url, user, attendParameter, 2);
	}

	public String getMonthAttend(String url, User user,
			AttendParameter attendParameter) throws Exception {
		Date start = new Date();
		Date end = new Date(start.getTime()-1000*60*60*24*31);
		attendParameter.setStart(DateParse.dateToString(start, "yyyy-MM-dd"));
		attendParameter.setEnd(DateParse.dateToString(end, "yyyy-MM-dd"));
		return getAttend(url, user, attendParameter, 3);
	}

	public String getDiyAttend(String url, User user,
			AttendParameter attendParameter) throws Exception {
		return getAttend(url, user, attendParameter, 3);
	}

	private String getAttend(String url, User user,
			AttendParameter attendParameter,int status) throws Exception {
		String date=attendParameter.getStart()+"a"+attendParameter.getEnd();
		int[] flags = attendParameter.getFlag();
		StringBuilder sb = new StringBuilder();
		if(flags!= null && flags.length > 0){
			for(int flag :flags){
				if(sb.length() > 0){
					sb.append("a");
				}
				sb.append(String.valueOf(flag));
			}
		}
		MyHttpClient client = user.getCilent();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Flag", sb.toString());
		map.put("Status", status);
		map.put("WaterDate", date);
		map.put("page", attendParameter.getPage());
		map.put("rows", attendParameter.getRows());
		CloseableHttpResponse response = client.sendPost(url, map, "utf-8", false);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "utf-8");
	}

	@Override
	public String appeal(String url, User user, AppealParams appealParams)
			throws Exception {
		MyHttpClient client = user.getCilent();
		Map<String, Object> map = ParamsToMap.paramsToMapByUp(appealParams, AppealParams.class);
		CloseableHttpResponse response = client.sendPost(url, map, "utf-8", true);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "utf-8");
	}
}
