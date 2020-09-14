package com.cqmi.db.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDao<T> {
	public BaseDao() {
		super();
	}
//	protected Connection getConnection()throws SQLException{
//      return ContextDataSource.getConnection();
//    } 
	protected SQLProxy getSQLProxy()throws SQLException{
    	SQLProxy proxy=new SQLProxy();
    	proxy.openConn();
    	return proxy;
    }
	
	@SuppressWarnings("null")
	public List<T> Select(String sql,Class<T> EntityClass) {
    	List<T> resT=new ArrayList<T>();
    	SQLProxy proxy = null;
		try {
			proxy = getSQLProxy();
			System.out.println("==sql=="+sql);
			proxy.execSQL(sql);
			Field fields[] = EntityClass.getDeclaredFields();
	            while (proxy.nextRow()) {
	            	T obj = EntityClass.newInstance();
	                for (int i = 0; i < fields.length; i++) {
	                    fields[i].setAccessible(true);
	                    String fieldStr=proxy.getFieldString(fields[i].getName());
	                    fields[i].set(obj, fieldStr==null?"":fieldStr);
//	                    System.out.print(fields[i].getName()+":"+proxy.getFieldString(fields[i].getName())+";");
	                }
	                resT.add(obj);
	            }
		} catch (Exception e) {
			if(proxy!=null||proxy.conn!=null)proxy.closeConn();
			e.printStackTrace();
		} 
		if(proxy!=null||proxy.conn!=null)proxy.closeConn();
        return resT;

    }
    
	public List<Map<String, String>> Select(String sql,String []title) {
    	List<Map<String, String>> resT=new ArrayList<Map<String, String>>();
    	SQLProxy proxy =new SQLProxy();;
    	String colstr="";
		try {
			proxy.openConn();
			System.out.println("==sql=="+sql);
			proxy.execSQL(sql);
            while (proxy.nextRow()) {
            	Map<String, String> map=new HashMap<String, String>();
                for (String value : title) {
                	if(value!=null){
                		colstr=value;
                		String fieldStr=proxy.getFieldString(value.trim().toUpperCase());
                		map.put(value.trim(), fieldStr==null?"":fieldStr.trim());
                	}
				}
                resT.add(map);
            }
		} catch (Exception e) {
			System.out.println("����"+colstr);
			if(proxy!=null||proxy.conn!=null)proxy.closeConn();
			e.printStackTrace();
		}
		if(proxy!=null||proxy.conn!=null)proxy.closeConn();
        return resT;

    }
	
	@SuppressWarnings("null")
	public  boolean execSQL(String sql) {
		SQLProxy proxy=null;
		try {
			 proxy=getSQLProxy();
			 int row=proxy.execSQL(sql);
			 System.out.println("==sql=="+sql+"ִ�н��ɹ�"+row+"��");
		} catch (Exception e) {
			 System.out.println("==sql=="+sql+"ִ�н��ʧ��");
			 if(proxy!=null||proxy.conn!=null)proxy.closeConn();
			e.printStackTrace();
			return false;
		} 
		if(proxy!=null||proxy.conn!=null)proxy.closeConn();
		return true;
	} 
    
	@SuppressWarnings("null")
	public  int execSQL2(String sql) {
		SQLProxy proxy=null;
		int row=0;
		try {
			 proxy=getSQLProxy();
			 row=proxy.execSQL(sql);
			 System.out.println("==sql=="+sql+"ִ�н��ɹ�"+row+"��");
			
		} catch (Exception e) {
			 System.out.println("==sql=="+sql+"ִ�н��ʧ��");
			 if(proxy!=null||proxy.conn!=null)proxy.closeConn();
			e.printStackTrace();
			return row;
		} 
		if(proxy!=null||proxy.conn!=null)proxy.closeConn();
		return row;
	}  
}
