package com.cqmi.db.dao;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cqmi.db.service.BeanService_Transaction;


public class BaseDao_Transaction<T> {
	
	private SQLProxy proxy;
	
	public BaseDao_Transaction() {
		super();
		try {
			proxy = getSQLProxy();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//	protected Connection getConnection()throws SQLException{
//      return ContextDataSource.getConnection( );
//    } 
//	protected SQLProxy getSQLProxy()throws SQLException{
//    	SQLProxy proxy=new SQLProxy();
//    	proxy.openConn(ContextDataSource.getConnection( ));
//    	return proxy;
//    }
	
 
	protected SQLProxy getSQLProxy()throws SQLException{
    	SQLProxy proxy=new SQLProxy();
    	proxy.openConn();
    	return proxy;
    }
	 
		public void OpenTransaction(){
			try {
				proxy.setAutoCommitFalse();
				System.out.println("-----------事物开启---------");
				//System.identityHashCode(con4)
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void commitExe(){
			try {
				proxy.commitExe();
				System.out.println("-----------事物提交成功---------");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void rollbackExe(){
			try {
				proxy.rollbackExe();
				System.out.println("-----------事物回滚---------");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void Close(){
			if(proxy!=null||proxy.conn!=null)proxy.closeConn();
		}
		
		public List<T> Select(String sql,Class<T> EntityClass) {
	    	List<T> resT=new ArrayList<T>();
			try {
				if(BeanService_Transaction.boo){
					System.out.println("==sql=="+sql);
					Loggerlog.log("sqlproxy").warn("==sql=="+sql);
				}
				proxy.execSQL(sql);
				Field fields[] = EntityClass.getDeclaredFields();
		            while (proxy.nextRow()) {
		            	T obj = EntityClass.newInstance();
		                for (int i = 0; i < fields.length; i++) {
		                    fields[i].setAccessible(true);
		                    String fieldStr=proxy.getFieldString(fields[i].getName());
		                    fields[i].set(obj, fieldStr==null?"":fieldStr);
		                }
		                resT.add(obj);
		            }
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	        return resT;

	    } 
		
		public List<T> Select(String sql, List<String> parameter,Class<T> EntityClass) {
	    	List<T> resT=new ArrayList<T>();
			try {
				if(BeanService_Transaction.boo){
					System.out.println(sysoLt(parameter)+"==sql=="+sql);
					Loggerlog.log("sqlproxy").warn(sysoLt(parameter)+"==sql=="+sql);
				}
				proxy.execSQL(sql,parameter);
				Field fields[] = EntityClass.getDeclaredFields();
		            while (proxy.nextRow()) {
		            	T obj = EntityClass.newInstance();
		                for (int i = 0; i < fields.length; i++) {
		                    fields[i].setAccessible(true);
		                    String fieldStr=proxy.getFieldString(fields[i].getName());
		                    fields[i].set(obj, fieldStr==null?"":fieldStr);
		                }
		                resT.add(obj);
		            }
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	        return resT;

	    }
		
		public List<Map<String, String>> SelectP(String sql,List<String> parameter) {
	    	List<Map<String, String>> resT=new ArrayList<Map<String, String>>();
			try {
				if(BeanService_Transaction.boo){
					System.out.println(sysoLt(parameter)+"==sql=="+sql);
					Loggerlog.log("sqlproxy").warn(sysoLt(parameter)+"==sql=="+sql);
				}
				
				proxy.execSQL(sql,parameter);
				String title[]=proxy.getColumnNames();
	            while (proxy.nextRow()) {
	            	Map<String, String> map=new HashMap<String, String>();
	                for (String value : title) {
	                	if(value!=null){
	                		sql=value;
	                		String fieldStr=proxy.getFieldString(value.trim().toUpperCase());
//	                		sql=value+":"+fieldStr;
	                		map.put(value.toLowerCase().trim(), fieldStr==null?"":fieldStr);
	                	}                 
					}
	                resT.add(map);
	            }
			} catch (Exception e) {
				System.out.println(sysoLt(parameter)+"错误列名:"+sql);
				Loggerlog.log("sqlproxy").error(sysoLt(parameter)+"错误列名:"+sql);
				e.printStackTrace();
				return null;
			}
	        return resT;
	    } 
	    
		public List<Map<String, String>> Select(String sql) {
	    	List<Map<String, String>> resT=new ArrayList<Map<String, String>>();
			try {
				if(BeanService_Transaction.boo){
					System.out.println("==sql=="+sql);
					Loggerlog.log("sqlproxy").warn("==sql=="+sql);
				}
				
				proxy.execSQL(sql);
				String title[]=proxy.getColumnNames();
	            while (proxy.nextRow()) {
	            	Map<String, String> map=new HashMap<String, String>();
	                for (String value : title) {
	                	if(value!=null){
	                		sql=value;
	                		String fieldStr=proxy.getFieldString(value.trim().toUpperCase());
//	                		sql=value+":"+fieldStr;
	                		map.put(value.toLowerCase().trim(), fieldStr==null?"":fieldStr);
	                	}                 
					}
	                resT.add(map);
	            }
			} catch (Exception e) {
				System.out.println("错误列名:"+sql);
				Loggerlog.log("sqlproxy").error("错误列名:"+sql);
				e.printStackTrace();
				return null;
			}
	        return resT;
	    }
		public List<JSONObject> JsonSelect(String sql) {
	    	List<JSONObject> resT=new ArrayList<JSONObject>();
			try {
				if(BeanService_Transaction.boo){
					System.out.println("==sql=="+sql);
					Loggerlog.log("sqlproxy").warn("==sql=="+sql);
				}
				
				proxy.execSQL(sql);
				String title[]=proxy.getColumnNames();
//				Gson gson=new Gson();
	            while (proxy.nextRow()) {
	            	JSONObject map=new JSONObject();
	                for (String value : title) {
	                	if(value!=null){
	                		sql=value;
	                		String fieldStr=proxy.getFieldString(value.trim().toUpperCase());
//	                		sql=value+":"+fieldStr;
	                		map.put(value.toLowerCase().trim(), fieldStr==null?"":fieldStr);
	                	}                 
					}
	                resT.add(map);
	            }
			} catch (Exception e) {
				System.out.println("错误列名:"+sql);
				Loggerlog.log("sqlproxy").error("错误列名:"+sql);
				e.printStackTrace();
				return null;
			}
	        return resT;
	    }
		public JSONObject[] JsonSelectArray(String sql) {
	    	JSONObject[] obj=new JSONObject[0];
			try {
				if(BeanService_Transaction.boo){ 
					System.out.println("==sql=="+sql);
					Loggerlog.log("sqlproxy").warn("==sql=="+sql);
				}
				
				proxy.execSQL(sql);
				String title[]=proxy.getColumnNames();
//				Gson gson=new Gson();
				List<JSONObject> resT=new ArrayList<JSONObject>();
	            while (proxy.nextRow()) {
	            	JSONObject map=new JSONObject();
	                for (String value : title) {
	                	if(value!=null){
	                		sql=value;
	                		String fieldStr=proxy.getFieldString(value.trim().toUpperCase());
//	                		sql=value+":"+fieldStr;
	                		map.put(value.toLowerCase().trim(), fieldStr==null?"":fieldStr);
	                	}                 
					}
	                resT.add(map);
	            }
	            obj=new JSONObject[resT.size()];
	            for (int i = 0; i < resT.size(); i++) {
	            	obj[i]=resT.get(i);
				}
			} catch (Exception e) {
				System.out.println("错误列名:"+sql);
				Loggerlog.log("sqlproxy").error("错误列名:"+sql);
				e.printStackTrace();
				return null;
			}
	        return obj;
	    }
		public List<JSONObject> JsonSelect(String sql,String []title) {
	    	List<JSONObject> resT=new ArrayList<JSONObject>();
			try {
				if(BeanService_Transaction.boo){ 
					System.out.println("==sql=="+sql);
					Loggerlog.log("sqlproxy").warn("==sql=="+sql);
				}
				proxy.execSQL(sql);
	            while (proxy.nextRow()) {
	            	JSONObject map=new JSONObject();
	                for (String value : title) {
	                	if(value!=null){
	                		sql=value;
	                		String fieldStr=proxy.getFieldString(value.trim().toUpperCase());
//	                		sql=value+":"+fieldStr;
	                		map.put(value.trim(), fieldStr==null?"":fieldStr);
	                		 
	                	}                 
					}
	                resT.add(map);
	            }
			} catch (Exception e) {
				System.out.println("错误列名:"+sql);
				Loggerlog.log("sqlproxy").error("错误列名:"+sql);
				e.printStackTrace();
				return null;
			}
	        return resT;

	    }
		
		public List<Map<String, String>> Select(String sql,List<String> parameter,String []title) {
	    	List<Map<String, String>> resT=new ArrayList<Map<String, String>>();
			try {
				if(BeanService_Transaction.boo){
					System.out.println(sysoLt(parameter)+"==sql=="+sql);
					Loggerlog.log("sqlproxy").warn(sysoLt(parameter)+"==sql=="+sql);
				}
				proxy.execSQL(sql,parameter);
	            while (proxy.nextRow()) {
	            	Map<String, String> map=new HashMap<String, String>();
	                for (String value : title) {
	                	if(value!=null){
	                		sql=value;
	                		String fieldStr=proxy.getFieldString(value.trim().toUpperCase());
//	                		sql=value+":"+fieldStr;
	                		map.put(value.trim(), fieldStr==null?"":fieldStr);
	                	}                 
					}
	                resT.add(map);
	            }
			} catch (Exception e) {
				System.out.println("错误列名:"+sql);
				Loggerlog.log("sqlproxy").error(sysoLt(parameter)+"错误列名:"+sql);
				e.printStackTrace();
				return null;
			}
	        return resT;

	    }
		
		public List<Map<String, String>> SelectT(String sql,String []title) {
	    	List<Map<String, String>> resT=new ArrayList<Map<String, String>>();
			try {
				if(BeanService_Transaction.boo){
					System.out.println("==sql=="+sql);
					Loggerlog.log("sqlproxy").warn("==sql=="+sql);
				}
				proxy.execSQL(sql);
	            while (proxy.nextRow()) {
	            	Map<String, String> map=new HashMap<String, String>();
	                for (String value : title) {
	                	if(value!=null){
	                		sql=value;
	                		String fieldStr=proxy.getFieldString(value.trim().toUpperCase());
//	                		sql=value+":"+fieldStr;
	                		map.put(value.trim(), fieldStr==null?"":fieldStr);
	                	}                 
					}
	                resT.add(map);
	            }
			} catch (Exception e) {
				System.out.println("错误列名:"+sql);
				 Loggerlog.log("sqlproxy").error("错误列名:"+sql);
				e.printStackTrace();
				return null;
			}
	        return resT;

	    }
		
		
		public  boolean execSQL(String sql) {
			try {
				 int row=proxy.execSQL(sql);
				 System.out.println("==sql=="+sql+"执行结果：成功;"+row+"行");
				 Loggerlog.log("sqlproxy").warn("==sql=="+sql+"执行结果：成功;"+row+"行");
			} catch (Exception e) {
				 System.out.println("==sql=="+sql+"执行结果：失败");
				 Loggerlog.log("sqlproxy").error("==sql=="+sql+"执行结果：失败");
				e.printStackTrace();
				return false;
			}
			return true;
		} 
	    public  boolean execSQL(String sql,List<String> parameter) {
			try {
				 int row=proxy.execSQL(sql,parameter);
				 Loggerlog.log("sqlproxy").warn(sysoLt(parameter)+"==sql=="+sql+"执行结果：成功;"+row+"行");
				 System.out.println(sysoLt(parameter)+"==sql=="+sql+"执行结果：成功;"+row+"行");
			} catch (Exception e) {
				e.printStackTrace();
				Loggerlog.log("sqlproxy").warn("==sql=="+sql+"执行结果：失败");
				 System.out.println("==sql=="+sql+"执行结果：失败");
				return false;
			}
			return true;
		}
		public  int execSQL2(String sql) {
			int row=0;
			try {
				 row=proxy.execSQL(sql);
				 System.out.println("==sql=="+sql+"执行结果：成功;"+row+"行");
				 Loggerlog.log("sqlproxy").warn("==sql=="+sql+"执行结果：成功;"+row+"行");
			} catch (Exception e) {
				e.printStackTrace();
				 System.out.println("==sql=="+sql+"执行结果：失败");
				 Loggerlog.log("sqlproxy").error("==sql=="+sql+"执行结果：失败");
				return row;
			}
			return row;
		}  
		
		public  int execSQL2(String sql,List<String> parameter) {
			int row=0;
			try {
				 Loggerlog.log("sqlproxy").warn(sysoLt(parameter)+"==sql=="+sql);
//				 System.out.println("==sql=="+sql);
				 row=proxy.execSQL(sql,parameter);
				 System.out.println(sysoLt(parameter)+"==sql=="+sql+"执行结果：成功;"+row+"行");
//				 Loggerlog.log("sqlproxy").warn(sysoLt(parameter)+"==sql=="+sql+"执行结果：成功;"+row+"行");
			} catch (Exception e) {
				e.printStackTrace();
				 System.out.println("==sql=="+sql+"执行结果：失败");
				 Loggerlog.log("sqlproxy").error(sysoLt(parameter)+"==sql=="+sql+"执行结果：失败");
				return row;
			}
			return row;
		}
		
		
		private String sysoLt(List<String> parameter){
			String lts="";
			for (String s : parameter) {
				lts+=("sql_:"+s+";");
			}
			return lts;
		}
}
