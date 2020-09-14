package com.cqmi.db.service;

import java.util.List;
import java.util.Map;

import com.cqmi.db.dao.BaseDao;


/**
 * 自定义sql使用
 * @author yj
 *
 */
public class BeanService {
	@SuppressWarnings("rawtypes")
	private BaseDao dao;

	@SuppressWarnings("rawtypes")
	public BeanService() {
		dao = new BaseDao();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getSelect(String sql, Class EntityClass){
		return dao.Select(sql, EntityClass);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getSelect(String sql,String[] title){
		return dao.Select(sql, title);
	}
	
	public boolean InsertSQL(String sql){
		return dao.execSQL(sql);
	}
	public int InsertSQL2(String sql){
		return dao.execSQL2(sql);
	}
	
	public boolean UpdateSQL(String sql){
		return dao.execSQL(sql);
	}
	public int UpdateSQL2(String sql){
		return dao.execSQL2(sql);
	}
	public boolean DeleteSQL(String sql){
		return dao.execSQL(sql);
	}
	public int DeleteSQL2(String sql){
		return dao.execSQL2(sql);
	}
}
