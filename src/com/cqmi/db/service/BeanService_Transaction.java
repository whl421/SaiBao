package com.cqmi.db.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.cqmi.db.dao.BaseDao_Transaction;


/**
 * �Զ���sqlʹ��
 * @author yj
 *
 */
public class BeanService_Transaction {
	public static boolean boo=true;

	private BaseDao_Transaction dao;
	 
	public BeanService_Transaction() {
		dao = new BaseDao_Transaction();
	}
	public void OpenTransaction(){
		dao.OpenTransaction();
	}
	public void commitExe(){
		dao.commitExe();
	}
	public void rollbackExe(){
		dao.rollbackExe();
	}
	public void commitExe_close(){
		dao.commitExe();
		dao.Close();
	}
	public void rollbackExe_close(){
		dao.rollbackExe();
		dao.Close();
	}
	public void Close(){
		dao.Close();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSelect(String sql,Class EntityClass){
		return dao.Select(sql, EntityClass);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSelect(String sql,List<String> parameter,Class EntityClass){
		return dao.Select(sql,parameter, EntityClass);
	}
	@SuppressWarnings("unchecked")
	public List<JSONObject> getJsonSelect(String sql,String[] title){
		return dao.JsonSelect(sql, title);
	}
	public List<Map<String, String>> getSelect(String sql,String[] title){
		return dao.SelectT(sql, title);
	}
	public List<Map<String, String>> getSelect(String sql,List<String> parameter,String[] title){
		return dao.Select(sql,parameter, title);
	}
	public List<Map<String, String>> getSelect(String sql,List<String> parameter){
		return dao.SelectP(sql,parameter);
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getSelect(String sql){
		return dao.Select(sql);
	}
	public List<JSONObject> getJsonSelect(String sql){
		return dao.JsonSelect(sql);
	}
	public boolean InsertSQL(String sql){
		return dao.execSQL(sql);
	}
	public int InsertSQL2(String sql){
		return dao.execSQL2(sql);
	}
	public int InsertSQL2(String sql,List<String> parameter){
		return dao.execSQL2(sql,parameter);
	}
	public boolean UpdateSQL(String sql){
		return dao.execSQL(sql);
	}
	public boolean UpdateSQL(String sql,List<String> parameter){
		return dao.execSQL(sql,parameter);
	}
	public int UpdateSQL2(String sql){
		return dao.execSQL2(sql);
	}
	public int UpdateSQL2(String sql,List<String> parameter){
		return dao.execSQL2(sql,parameter);
	}
	public boolean DeleteSQL(String sql){
		return dao.execSQL(sql);
	}
	public boolean DeleteSQL(String sql,List<String> parameter){
		return dao.execSQL(sql,parameter);
	}
	public int DeleteSQL2(String sql){
		return dao.execSQL2(sql);
	}
	public int DeleteSQL2(String sql,List<String> parameter){
		return dao.execSQL2(sql,parameter);
	}
}
