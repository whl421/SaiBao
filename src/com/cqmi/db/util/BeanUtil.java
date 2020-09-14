package com.cqmi.db.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


import com.cqmi.db.service.BeanService;
import com.cqmi.db.service.BeanService_Transaction;
import com.google.gson.Gson;

public class BeanUtil {
	
	
	/**
	 *  json תΪ ����
	 * @param beanjson
	 * @param classs
	 * @return
	 */
//	public T getObj(String beanjson,Class<T> classs){
//		T t=new Gson().fromJson(beanjson, classs);
//		return t;
//	}
	/**
	 *  �Զ����ѯ
	 * @param colnameorder
	 * @return
	 */
	public String getJsonStr(String colnameorder,Map<String, String> map){
		String colnames[]=colnameorder.split(",");
		String colname="[ ";
		for (int i = 0; i < colnames.length; i++) {
			if(i==0)
				colname+=("\""+map.get(colnames[i])+"\"");
			else
				colname+=(",\""+map.get(colnames[i])+"\"");
			
		}
		return "{\"id\":\""+map.get(colnames[0])+"\",\"cell\":"+colname+"]}";
	}
	
	public Map getJsonStrs(String colnameorder,Map<String, String> map){
		String colnames[]=colnameorder.split(",");
		Map rmap=new HashMap();
		List<String> al=new ArrayList<String>();
		for (int i = 0; i < colnames.length; i++) {
			al.add(map.get(colnames[i]));
			
		}
		rmap.put("id", map.get(colnames[0]));
		rmap.put("cell", al);
		return rmap;
	}
	/**
	 * @param json
	 * @return Map
	 */
	@SuppressWarnings("rawtypes")
//	public Map StringToMap(String json){
//		try {
//			Map map=new Gson().fromJson(json,Map.class);
//			return map==null?null:map;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public Map StringToMap(String json){
	 if(json==null||json.trim().equals(""))return null;
		Map map=null;
		try {
			map=new Gson().fromJson(json,Map.class);
		} catch (Exception e) {
			System.out.println("�����Խ��������쳣");
			e.printStackTrace();
		}
		try {
			System.out.println("�����ֶ�������ʼ");
			if(map==null)return jsontoMap(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public Map jsontoMap(String jsons) throws Exception{
		JSONObject json=JSONObject.fromObject(jsons);
        Map<String,Object> map=new HashMap<String, Object>();
        Iterator it = json.keys();
        while (it.hasNext()) {  
           String key = (String) it.next();  
           Object value = json.get(key);  
           map.put(key, value);
        }
        return map;
	}
	
	/**
	 * 
	 * @param lbean
	 * @param colnameorder
	 * @param page
	 * @param totalrecords
	 * @param rows
	 * @return
	 */
	public Map BeanToTable(List<Map<String, String>> lbean,
			String colnameorder,String page, Map<String, String> totalrecords){
		List<Map> lmap=new ArrayList<Map>();
		 for (Map<String, String> map : lbean) {
			 lmap.add(getJsonStrs(colnameorder,map));
	  	 }
		 return getJsonJqGrids(page, totalrecords, lmap);
	}
	
	/** 
	 * �������:MapToString 
	 * �������:map 
	 * ����ֵ:String  
	*/  
	public String  MapToString(@SuppressWarnings("rawtypes") Map remap){  
		try {
			String restr=new Gson().toJson(remap);
			return restr==null?null:restr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ���� ��С ��ϲ�ѯ����
	 * @param obj
	 * @param map
	 * @return
	 */
	public String getObjSearchLike(Object obj,@SuppressWarnings("rawtypes") Map map){
		Field[] field = obj.getClass().getDeclaredFields();
		String where=" where 1=1";
		for(int j=0 ; j<field.length ; j++){ //������������
	        String name = field[j].getName(); //��ȡ���Ե�����
	        String mapname=(String) map.get(name);
	        if(mapname!=null&&!mapname.trim().equals("")){
	        	where+=(" and"+name+" like '%"+mapname+"%'");
	        }
//	        System.out.println(name+"-"+mapname);
		} 
		return where;
	}
	
	/**
	 * �ų��С ��ϲ�ѯ����
	 * @param obj
	 * @param map
	 * @param attrname �Ǵ�С��ѯ���ֶ�,��ѯʱ��ͬ����ѯ���ֶ�
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getObjSearchLike_nodate(Object obj,String jsonstr,String[] attrname) 
			throws UnsupportedEncodingException{
//		Field[] field = obj.getClass().getDeclaredFields();
		jsonstr = jsonstr == null ? null : jsonstr;
		jsonstr = jsonstrTojson(jsonstr);// jsonstr.replace("{", "{\"").replace("}", "\"}").replaceAll(",", "\",\"").replaceAll(":", "\":\"");
		System.out.println(jsonstr);
		@SuppressWarnings("rawtypes")
		Map map = StringToMap(jsonstr);	
		String where = " where 1=1";
		if(map == null) {
			return where;
		}
		@SuppressWarnings("unchecked")
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String value = (String) map.get(key);
			boolean boo = true;
	        for (String attr : attrname == null ? new String[0] : attrname) {
				if(attr.equals(key)) {
					boo = false;
				}
			}
	        if(value != null && !value.trim().equals("") && boo) {
	        	where += (" and t."+key+" like '%"+value+"%'");
	        }
	        if(value !=null && !value.trim().equals("") && !boo){
	        	where += (" and "+value+" ");
	        }
	        System.out.println(key+"=="+value);
		}
//		for(int j=0 ; j<field.length ; j++){ //������������
//	        String name = field[j].getName(); //��ȡ���Ե�����
//	        boolean boo = true;
//	        for (String attr : attrname == null ? new String[0] : attrname) {
//				if(attr.equals(name)) {
//					boo = false;
//				}
//			}
//	        String mapname = (String)(map==null?null:map.get(name));
//	        if(mapname != null && !mapname.trim().equals("") && boo) {
//	        	where += (" and t."+name+" like '%"+mapname+"%'");
//	        }
//	        if(mapname !=null && !mapname.trim().equals("") && !boo){
//	        	where += (" and "+mapname+" ");
//	        }
//	        System.out.println(name+"=="+mapname);
//		} 
		return where;
	}
	
	/**
	 * �ų��С ��ϲ�ѯ����
	 * @param obj
	 * @param map
	 * @param attrname �Ǵ�С��ѯ���ֶ�,��ѯʱ��ͬ����ѯ���ֶ�
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getObjSearchLike_nodate_a(Object obj,String jsonstr,String[] attrname, String[] query) 
			throws UnsupportedEncodingException{
//		Field[] field = obj.getClass().getDeclaredFields();
		jsonstr = jsonstr == null ? null : jsonstr;
		jsonstr = jsonstrTojson(jsonstr);// jsonstr.replace("{", "{\"").replace("}", "\"}").replaceAll(",", "\",\"").replaceAll(":", "\":\"");
		System.out.println(jsonstr);
		@SuppressWarnings("rawtypes")
		Map map = StringToMap(jsonstr);		
		String where = " where 1=1";
		if(map == null) {
			return where;
		}
		@SuppressWarnings("unchecked")
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String value = (String) map.get(key);
	        boolean boo = true;
	        for (String attr : attrname == null ? new String[0] : attrname) {
				if(attr.equals(key)) {
					boo = false;
				}
			}
	        boolean flag = true;
	        for (String qu : query == null ? new String[0] : query) {
				if(qu.equals(key)) {
					flag = false;
				}
			}
	        if(value != null && !value.trim().equals("") && boo) {
	        	if(flag == false) {
	        		where += (" and t."+key+" = '"+value+"'");
	        	} else {
	        		where += (" and t."+key+" like '%"+value+"%'");
	        	}
	        }
	        if(value !=null && !value.trim().equals("") && !boo){
	        	where += (" and "+value+" ");
	        }
	        System.out.println(key+"=="+value);
		} 
		return where;
	}
	
	public String jsonstrTojson(String jsonstr){
		return jsonstr;
//		if(jsonstr==null)return null;
//		jsonstr=jsonstr.replace("{", "").replace("}", "");
//		String [] jsonstrs=jsonstr.split(",");
//		String restr="{ ";
//		for (String js : jsonstrs) {
//			restr+="\""+js.replace(":", "\":\"")+"\",";
//		}
//		restr=restr.substring(0, restr.length()-1)+" }";
//		return restr;
	}
	/**
	 * ����ʹ�ã��������Ϊ���֣�������������
	 * @param ordertype ��������    number��date������ ParaUtil�Ĳ���
	 * @param sidx
	 * @param sord
	 * @return
	 */
	public String getOrderbysql(String sidx,String sord,int ordertype){
//		if(indexname.matches("[0-9^.]*")) 
//			indexname="to_number("+indexname+")";
		if(ordertype==ParaUtil.number)
			sidx="to_number("+sidx+")";
		return " order by "+sidx+" "+sord;
	}
	public String getOrderbysql_(String sidx,String sord,int ordertype){
		if(ordertype==ParaUtil.number)
			sidx="to_number("+sidx+")";
		return ","+sidx+" "+sord;
	}
	/**
	 *  ���� �� ��sql
	 * @param obj
	 * @param map
	 * @return
	 */
	public String insertSQL(Object obj,@SuppressWarnings("rawtypes") Map map,String table){
		Field[] field = obj.getClass().getDeclaredFields();
		String sql1="insert into ";
		String sql2=" (";
		String sql3=" (";
		for(int j=0 ; j<field.length ; j++){ //������������
	        String name = field[j].getName(); //��ȡ���Ե�����
	        sql2+=(name+",");
	        String mapname=(String) map.get(name);
//	        System.out.println("name:"+name+";mapname:"+mapname);
	        if(mapname==null){
	        	mapname="";
	        }
	        sql3+="'"+mapname+"',";
		} 
		String sql=sql1+table+sql2.substring(0, sql2.length()-1)+") values"+sql3.substring(0, sql3.length()-1)+")";
		return sql;
	}
	/**
	 * �޸�  �� ��sql
	 * @param obj
	 * @param map
	 * @param table
	 * @param idname ����id
	 * @return
	 */
	public String updateSQL(Object obj,@SuppressWarnings("rawtypes") Map map,String table,String idname){
		Field[] field = obj.getClass().getDeclaredFields();
		idname=idname.toLowerCase();
		String sql="update "+table+" t set ";
		for(int j=0 ; j<field.length ; j++){ //������������
	        String name = field[j].getName(); //��ȡ���Ե�����
	        String mapname=(String) map.get(name);
	        if(mapname!=null){
	        	sql+=" t."+name+"='"+mapname+"',";
	        }
		} 
		sql=sql.substring(0, sql.length()-1);
		sql=(sql+" where "+idname+"='"+map.get(idname)+"'");
		return sql;
	}
	/**
	 * �� ��ѯ sql
	 * @param table
	 * @param where
	 * @param order
	 * @param page
	 * @param rows
	 * @return
	 */
	public String selectSQL(String table,String where,String order,String page,String rows){
//		select a1.* from (select student.*,rownum rn from student) a1 where rn between 3 and 5
		int index=Integer.parseInt(page==null?"1":page);
		int row=Integer.parseInt(rows);
		int between1=row*index-row+1;
		int between2=row*index;
		String sql="select t.*,rownum rn from ("+table+") t "+where+" "+order;
		return row==-1?"select t.*,rownum rn from ("+table+") t "+where:"select a.* from ("+sql+") a where a.rn between "+between1+" and "+between2;
	}
	/**
	 *  �õ��ܵ�ҳ��
	 * @param table
	 * @param rows
	 * @param service
	 * @return
	 */
	public Map<String, String> getTotal(String table,String rows,String where,BeanService_Transaction service){
		int row=Integer.parseInt(rows);
		String sql="select count(*) total from ("+table+") t"+where;
		String[]title={"total"};
		List<Map<String, String>> lmap=service.getSelect(sql, title);
		int records=Integer.parseInt(lmap.get(0).get("total"));
		int total=(records/row)+(records%row==0?0:1);
		Map<String, String> map=new HashMap<String, String>();
		map.put("records", records+"");
		map.put("total", total+"");
		return map;
	}
	public Map<String, String> getTotal(String table,String rows,String where,BeanService service){
		int row=Integer.parseInt(rows);
		String sql="select count(*) total from ("+table+") t"+where;
		String[]title={"total"};
		List<Map<String, String>> lmap=service.getSelect(sql, title);
		int records=Integer.parseInt(lmap.get(0).get("total"));
		int total=(records/row)+(records%row==0?0:1);
		Map<String, String> map=new HashMap<String, String>();
		map.put("records", records+"");
		map.put("total", total+"");
		return map;
	}
	public Map<String, String> getTotal(String sql,String rows,BeanService service){
		int row=Integer.parseInt(rows);
		String[]title={"total"};
		List<Map<String, String>> lmap=service.getSelect(sql, title);
		int records=Integer.parseInt(lmap.get(0).get("total"));
		int total=(records/row)+(records%row==0?0:1);
		Map<String, String> map=new HashMap<String, String>();
		map.put("records", records+"");
		map.put("total", total+"");
		return map;
	}
	/**
	 * �õ� ����ID
	 * @param table
	 * @param id
	 * @param service
	 * @return
	 */
	public String getMaxID(String table,String idname,BeanService_Transaction service){
		String sql="select nvl(MAX(to_number(t."+idname+")),0)+1 id from ("+table+") t";
		String[]title={"id"};
		List<Map<String, String>> lmap=service.getSelect(sql, title);
		return lmap.get(0).get("id");
	}
	public String getMaxID(String table,String idname,BeanService service){
		String sql="select nvl(MAX(to_number(t."+idname+")),0)+1 id from ("+table+") t";
		String[]title={"id"};
		List<Map<String, String>> lmap=service.getSelect(sql, title);
		return lmap.get(0).get("id");
	}
	/**
	 * ɾ�����
	 * @param table
	 * @param idname
	 * @param id
	 * @return
	 */
	public String deletSql(String table,String idname,String id){
		return "delete from "+table+" t where t."+idname+" in ("+id.replace("[", "").replace("]", "").replaceAll("\"", "'")+")";
	}
	/**
	 * 
	 * @param page �ӵ�1ҳ��ʼ����ǰ���ص����Ϊ�ڼ�ҳ�����
	 * @param total ��ǰҳ������
	 * @param records �����������
	 * @param rows 
	 * @return
	 */
	public String getJsonJqGrid(String page,Map<String, String> totalrecords,String rows){
		String total=totalrecords.get("total");
		String records=totalrecords.get("records");
		return "{\"page\":\""+page+"\",\"total\":\""+total+"\",\"records\":\""+records+"\",\"rows\":["+rows+"]}";
	}
	public Map getJsonJqGrids(String page,Map<String, String> totalrecords,List<Map> rows){
//		String str="{\"page\":\""+page+"\",\"total\":"+total+",\"records\":\""+records+"\",\"rows\":["+rows+"]}";
//		return StringToMap(str);
		String total=totalrecords.get("total");
		String records=totalrecords.get("records");
		Map map=new HashMap();
		map.put("page",page );
		map.put("total", total);
		map.put("records",records );
		map.put("rows",rows );
		return map;
	}
	/**
	 * �ַ�ת��
	 * @param str
	 * @return
	 */
    public String UTF8ToGBK(String str) {
        if (str == null)
            return str;
        try {
//        	String utf8 = new String(str.getBytes("UTF-8"));  
//            String unicode = new String(utf8.getBytes(),"UTF-8");   
//            String gbk = new String(unicode.getBytes("GBK")); 
            return java.net.URLDecoder.decode(str,"utf-8");
        } catch (UnsupportedEncodingException usex) {
            return str;
        }
    }
	
}
