package com.cqmi.controller.login;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//import com.cqmi.controller.app.util.RedisUtil;
import com.cqmi.controller.login.bean.User;
import com.cqmi.db.dao.Loggerlog;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class LoginInterceptor  implements HandlerInterceptor {
//	   @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
	        //获取请求的RUi:去除http:localhost:8080这部分剩下的
	        String uri = request.getRequestURI();
	        //UTL:除了login.jsp是可以公开访问的，其他的URL都进行拦截控制
//	        if (uri.endsWith("js")>=0||uri.endsWith("css")) {
//	            return true;
//	        }
	      //	excel test	 
	      //  ExcelUilt.Excel("D:\\test.xlsx", 0);
	        if (uri.indexOf("/dispatch/insert.action") >= 0||uri.indexOf("/dispatch/insert") >= 0) {
	            return true;
	       }
	        if (uri.indexOf("/login/user.action") >= 0||uri.indexOf("/login/usercusid.action") >= 0) {
	            return true;
	        }
	        
	        if(uri.indexOf("/applogin/") >= 0||uri.indexOf("/applogin/") >= 0){
	        	return true;
	        }
	        
	        if (uri.indexOf("/cus/cuszc.action") >= 0||uri.indexOf("/cus/cuszc.action") >= 0) {
	            return true;
	        }
	        if (uri.indexOf("/cus/cuscodeYZ.action") >= 0||uri.indexOf("/cus/cuscodeYZ.action") >= 0) {
	            return true;
	        }
	        if (uri.indexOf("/cus/cussave.action") >= 0||uri.indexOf("/cus/cussave.action") >= 0) {
	            return true;
	        }
//	        /cusiduserapp
	        
	        //user.getCusid()+"_"+user.getUserid()+"_"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	       
	        
	        //获取session
	        HttpSession session = request.getSession();
	        User user = (User) session.getAttribute("user");
	        //判断session中是否有用户数据，如果有，则返回true，继续向下执行
	        if (user != null) {
	        	
	        	Loggerlog.log("info").warn("-客户-"+user.getCusid()+",用户-"+user.getUserid()+"---访问用户--"+user.getUsername()+"--:"+uri);
	        	
	            return true;
	        }
	        
	        
	        String token=request.getParameter("token");
//	        if(token!=null&&token.indexOf("_")>0){
//	        	 RedisUtil redisutil=new RedisUtil();
//	        	 Jedis jedis=redisutil.getJedis();
//	        	
//	        	try {
//	        		if(jedis.exists(token.split("_")[0]+"_"+token.split("_")[1])){
//		        		jedis.expire((token.split("_")[0]+"_"+token.split("_")[1]).getBytes(), 60*60*2);
//		        		return true;
//		        	}else{
//		        		response.setCharacterEncoding("GBK");
//		    			response.setContentType("text/html;charset=gbk");
//		    			Map<String, String> map=new HashMap<String, String>();
//		    			map.put("info", "9");
//		    			map.put("textinfo", "登录过期，请重新登录");   // 过去未操作
//		    			try {
//		    				response.getOutputStream().write(JSONObject.fromObject(map).toString().getBytes());
//		    				response.getOutputStream().flush();
//		    				response.getOutputStream().close();
//		    			} catch (IOException e) {
//		    				e.printStackTrace();
//		    			}
//		        		return false;
//		        	}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}finally{
//					jedis.close();
//				}
//	        	 
//	        }
	        
	        
	        String ip=request.getRemoteAddr();
	        
	        Loggerlog.log("error").error(new Date().toString()+"-访问拦截--ip:"+ip+",url:"+uri);
	        
	       // System.out.println("输出拦截器-------在这里");
	        
	        
	        //不符合条件的给出提示信息，并转发到登录页面
	        request.setAttribute("textinfo", "您还没有登录或登录过期，请先登录！");
	        request.getRequestDispatcher("/jsp/manageIntegral/login.jsp").forward(request, response);
	        
	        
//	        String contextPath=request.getContextPath();
//	        response.sendRedirect(contextPath + "/login/user.action");
	        
//	        request.getRequestDispatcher("/jsp/manageIntegral/login.jsp").forward(request, response);
	        return false;
	    }

//	    @Override
	    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	    }

//	    @Override
	    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	    }

		 
}
