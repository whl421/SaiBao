//package com.cqmi.db.dao;
//
//
//
//import java.io.File;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//
////import com.mchange.v2.c3p0.ComboPooledDataSource;
//
////import com.mchange.v2.c3p0.ComboPooledDataSource;
//
//
///**
// * c3p0配置类
// * @author SUN
// */
//public class ContextDataSource {
//    /**
//     * c3p0数据源
//     */
//	//数据库连接池 使用C3P0数据库连接池 使用named-config模式 则通过有参构造函数初始化  带上name  
////	public static ComboPooledDataSource _ds = new ComboPooledDataSource("data1");  
//	//采用default-config模式  则使用无参构造函数即可  
//	
//    //从连接池中获取一个连接
////    public static Connection getConnection( ){
////    	synchronized(ContextDataSource._ds){
////    	      
////    		 Connection conn = null;    
////    		    try {    
////    		        conn = _ds.getConnection();    
////    		    } catch (Exception e) {    
////    		       e.printStackTrace();  
////    		    }  
////    		    
////    		    return conn; 
////    		
////    	}
////       
////    }
//
//	
//	/*
//	 * 使用jdbc数据源
//	 */
//    public static Map<String, Map<String, String>> _ds=new HashMap<String, Map<String,String>>();
//    
//    
//    	 
//	   private Map<String, String> getjdbcurl(String rootname){
//		   Map<String, String> jdbcurl=new HashMap<String, String>();
//		   String path=ContextDataSource.class.getClassLoader().getResource("").
//					toString().replace("WEB-INF/classes", "")
//					.replace("file:/", "")+"/jdbc.xml";
//				File file=new File(path);
//				if (file.isFile()) {
//					try {
//						Document document= new SAXReader().read(file);
//						@SuppressWarnings("unchecked")
//						List<Element> roots=document.getRootElement().elements();  
//						for (Element root : roots) {
//							if(root.element("jndi-name").getText().trim().equals(rootname)){
//								jdbcurl.put("driverUrl", root.element("driver-class").getText());
//								jdbcurl.put("url",root.element("connection-url").getText() );
//								jdbcurl.put("name", root.element("user-name").getText());
//								jdbcurl.put("password", root.element("password").getText());
//								break;
//							}
//						}
//					} catch (DocumentException e) {
//						System.out.println("错误>>>><<<<<<<"+path);
//					}
//				}else{
//					System.out.println("错误>>>><<<<<<<"+path);
//				}
//				return jdbcurl;
//	  }
//	 
//	   public static Connection getConnection(){
//		   synchronized(ContextDataSource._ds){
//			   Map<String, String> jdbc=null;
//			   String rootname="yhmes";
//			   if(_ds.get(rootname)==null){
//				    jdbc=new ContextDataSource().getjdbcurl(rootname);
//				   _ds.put(rootname, jdbc);
//			   }else{
//				    jdbc=_ds.get(rootname);
//			   }
//			  
//			   Connection conn =null;
//			   try {  
//		    	   Class.forName(jdbc.get("driverUrl"));  
//		           conn = DriverManager.getConnection(jdbc.get("url"), jdbc.get("name"), jdbc.get("password")); 
//		       } catch (ClassNotFoundException e) {  
//		           e.printStackTrace();  
//		       } catch (SQLException e) {  
//		           e.printStackTrace();  
//		       } 
//			   return conn;
//	    	}
//		   
//	   }
//     
//
//}
//
////public static Connection getConnection( ){
////synchronized(ContextDataSource._ds){
////   try {
////   	 Class.forName("oracle.jdbc.driver.OracleDriver");
////        String dbURL = "jdbc:oracle:thin:@192.168.148.128:1521:yhmes";
////        Connection conn = DriverManager.getConnection(dbURL, "yhmes", "yhmes"); 
////   	 return conn;
////	} catch (Exception e) {
////		e.printStackTrace();
////	}
////	return GetConnection("yhmes");
////}
////
////}
