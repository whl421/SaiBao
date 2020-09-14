package com.cqmi.db.dao;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Loggerlog {
	
//	private static Log logger = LogFactory.getLog(Loggerlog.class);
	private static Log  logger = LogFactory.getLog("info");
//	private static Log  logger = LogFactory.getLog("infoerror");
	private static Log  sqlproxy = LogFactory.getLog("sqlproxy");
	
	
//	private static Log  logger =  LogFactory.getLog(Loggerlog.class);
	
	
	
	/**
	 * 日志输出
	 * @param classs
	 * 
	 * info
	 * errors
	 * sqlproxy
	 * 
	 * @return
	 */
	public static Log  log(String classs){
		if(classs.equals("info"))return logger;
//		if(classs.equals("error"))return logger;
		if(classs.equals("sqlproxy"))return sqlproxy;
		
		return	logger;// (Logger) LogFactory.getLog(Loggerlog.class); 		//	LogManager.getLogger(classs);
	}
//	public static Log log(Class classs){
//		return	LogFactory.getLog(classs);
//	}
	
    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     * 
     * @param request
     * @return
     * @throws IOException
     */
    public final static String getIpAddress(HttpServletRequest request) throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");
        if (logger.isInfoEnabled()) {
            logger.error("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.error("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.error("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (logger.isInfoEnabled()) {
                    logger.error("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (logger.isInfoEnabled()) {
                    logger.error("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (logger.isInfoEnabled()) {
                    logger.error("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
	public void ss(){
		logger.warn("qwqwq");
	}
////	
	public static void main(String[] args) {
		new Loggerlog().ss();
		
		Logger.getLogger(Loggerlog.class).warn("qeqwe");
	}
	
	
	
}
