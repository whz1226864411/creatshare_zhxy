package cn.creat.zhxy.po;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cn.creat.zhxy.utils.httputil.MyHttpClient;

public class User {
	
	@NotNull(message="{user.username.notnull}")
	@Size(min=8,max=8,message="{user.username.size}")
	private String userName;
	@NotNull(message="{user.password.notnull}")
	private String userPassword;
	private MyHttpClient cilent;
	
	public void setCilent(MyHttpClient cilent) {
		this.cilent = cilent;
	}
	public MyHttpClient getCilent() {
		return cilent;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
