package com.cqmi.db.dao;



import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;



/**
 * DataSource配置类
 * @author SUN
 */
public class MybatisSessionDataSource {
      /**
       * MyBatis下，全局唯一SqlSessionFactory，使用单例模式获取
       */
    //首先创建静态成员变量sqlSessionFactory，静态变量被所有的对象所共享。
    //  public static SqlSessionFactory sqlSessionFactory = null;
      private static SqlSessionFactory sqlSessionFactory = null;
      private  SqlSession sqlSession = null;
      
      //使用静态代码块保证线程安全问题
      static{
          
          String resource = "mybatis-config-dao.xml";
          
          try {
              
              InputStream inputStream = Resources.getResourceAsStream(resource);

              sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
              
          } catch (IOException e) {

              e.printStackTrace();
          }
          
      }
      // 创建session对象
	   public  void init(){
           sqlSession = sqlSessionFactory.openSession(true);
	   }
//	   public  void init(){
//		   try {
//			   InputStream inputStream = Resources.getResourceAsStream(resource);
//			   // 创建工厂
//			   sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//			   // 创建session对象
//	           sqlSession = sqlSessionFactory.openSession(true);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	   }
	   
	   public  SqlSession getSession() {
	        if (sqlSession == null) {
	            init();
	        }
	        return sqlSession;
	   }
	   
	   public Connection getConnection() throws SQLException{
//		   DataSource dataSource=sqlSession.getConfiguration().getEnvironment().getDataSource();
//		  
//		   System.out.println(dataSource);
//		   
//		   return dataSource.getConnection();
//		   
		   return sqlSession.getConnection();
		   

//		     return   sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
	   }
    
	   public  void close() {
	        if (sqlSession != null) {
	        	sqlSession.close();
	        	sqlSession = null;
	        }
	    }

	   
	   
	   
	   public  <T> T getMapper(Class<T> tClass) {
	        if (sqlSession == null) {
	            init();
	        }
	        return sqlSession.getMapper(tClass);
	    }

	    public  void commit() {
	        if (sqlSession != null) {
	        	sqlSession.commit();
	        }
	    }
	    
	    public  void rollback() {
	        if (sqlSession != null) {
	        	sqlSession.rollback();
	        }
	    }
	    
}
