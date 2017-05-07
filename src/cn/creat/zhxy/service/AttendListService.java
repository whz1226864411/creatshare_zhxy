package cn.creat.zhxy.service;

import cn.creat.zhxy.po.AppealParams;
import cn.creat.zhxy.po.AttendParameter;
import cn.creat.zhxy.po.User;

public interface AttendListService {
	public String getTodayAttend(String url, User user,AttendParameter attendParameter) throws Exception;
	public String getWeekAttend(String url, User user,AttendParameter attendParameter) throws Exception;
	public String getMonthAttend(String url, User user,AttendParameter attendParameter) throws Exception;
	public String getDiyAttend(String url, User user,AttendParameter attendParameter) throws Exception;
	public String appeal(String url, User user,AppealParams appealParams) throws Exception;
}
