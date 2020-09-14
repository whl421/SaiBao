//package com.cqmi.controller.login.util;
//
//import java.util.Map;
//
//
//
//public class JSONObject extends com.alibaba.fastjson.JSONObject{
//	public JSONObject(){
//		super();
//	}
//	public JSONObject(com.alibaba.fastjson.JSONObject jsonobject){
//		super(jsonobject);
//	}
//	
//	public JSONObject fromObject(String text){
//		return new JSONObject(com.alibaba.fastjson.JSONObject.parseObject(text)) ;
//	}
//	public JSONObject fromObject(Map map){
//		return new JSONObject(new com.alibaba.fastjson.JSONObject(map));
//	}
//	public String optString(String key){
//		if(super.containsKey(key)){
//			return null;
//		}
//		return super.getString(key);
//	}
//	
//	public String toString(){
//		return super.toJSONString();
//	}
//	
//}
