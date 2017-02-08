package cn.creat.zhxy.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.creat.zhxy.po.User;

public class LoginHandleInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String url = request.getRequestURI();
		if(url.contains("/user/login.action")){
			return true;
		}
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			
			PrintWriter printWriter = response.getWriter();
			printWriter.write("{\"total\":-2,\"Msg\":\"您还未登录\"}");
			printWriter.flush();
			printWriter.close();
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
