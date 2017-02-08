package cn.creat.zhxy.service;

import cn.creat.zhxy.po.User;

public interface UserService {
	
	public String login(User user,String loginUrl,String codeUrl) throws Exception;
	
	public void logout(User user,String logoutUrl) throws Exception;
}
