package com.cqmi.db.bean;

import java.lang.reflect.Method;

public class TestBean {
	private String id;
	private String invdate;
	private String name;
	private String amount;
	private String tax;
	private String total;
	private String note;
	
		    
	public String getJsonStr(String colnameorder){
		String colnames[]=colnameorder.split(",");
		String colname="[ ";
		for (int i = 0; i < colnames.length; i++) {
			if(i==0)
				colname+=("\""+attrname(colnames[i])+"\"");
			else
				colname+=(",\""+attrname(colnames[i])+"\"");
			
		}
		return "{\"id\":\""+attrname(colnames[0])+"\",\"cell\":"+colname+"]}";
	}
   private String attrname(String fieldName) {  
       try {  
//           Field field = this.getClass().getField(fieldName);  
//           //设置对象的访问权限，保证对private的属性的访问  
//           field.setAccessible(true);
//           return  (String)field.get(this); 
           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
           String getter = "get" + firstLetter + fieldName.substring(1);    
           Method method = this.getClass().getMethod(getter, new Class[] {});    
           Object value = method.invoke(this, new Object[] {});
           return (String)value;
       } catch (Exception e) {  
    	   e.printStackTrace();
           return null;  
       }   
   }
	public String tableName(){
		return "test";
	}
	public String idname(){
		return "id";
	}
	
	
	
	
	
	
	
	public TestBean(String id, String invdate, String name, String amount,
			String tax, String total, String note) {
		super();
		this.id = id;
		this.invdate = invdate;
		this.name = name;
		this.amount = amount;
		this.tax = tax;
		this.total = total;
		this.note = note;
	}
	public TestBean() {
	super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInvdate() {
		return invdate;
	}
	public void setInvdate(String invdate) {
		this.invdate = invdate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
   
}
