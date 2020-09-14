package com.cqmi.controller.login.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User implements Serializable{
	private String userid;
	private String cusid;
	private String departid;
	private String departname;
	private String departcode;
	private String usercode;
	private String workcode;
	private String username;
	private String pname;
	private String hytype;	//客户类型
	private String dqdate;	//到期日期   验证是否到期
	private String state;	// 状态为0 该用户账号已被冻结，请联系管理员重新开通
	private String phone;
	private String emall;
	private String cusname;  //客户名称
	private String latitude;
	private String longitude;
	private	String radius;
	
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCusname() {
		return cusname;
	}
	public void setCusname(String cusname) {
		this.cusname = cusname;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmall() {
		return emall;
	}
	public void setEmall(String emall) {
		this.emall = emall;
	}
	public String getHytype() {
		return hytype;
	}
	public void setHytype(String hytype) {
		this.hytype = hytype;
	}
	public String getDqdate() {
		return dqdate;
	}
	public void setDqdate(String dqdate) {
		this.dqdate = dqdate;
	}
	private List<Map<String, String>> bpower;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCusid() {
		return cusid;
	}
	public void setCusid(String cusid) {
		this.cusid = cusid;
	}
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
	}
	public String getDepartname() {
		return departname;
	}
	public void setDepartname(String departname) {
		this.departname = departname;
	}
	public String getDepartcode() {
		return departcode;
	}
	public void setDepartcode(String departcode) {
		this.departcode = departcode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getWorkcode() {
		return workcode;
	}
	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	 
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public List<Map<String, String>> getBpower() {
		return bpower;
	}
	public void setBpower(List<Map<String, String>> bpower) {
		this.bpower = bpower;
	}
	public List<Map<String, String>> getBpower(String menu_htmlcode) {
		List<Map<String, String>> rl=new ArrayList<Map<String,String>>();
		
//		for (Map<String, String> map : bpower) {
//			if(map.get("htmlcode").equals(menu_htmlcode)){
//				rl.add(map);
//				String id=map.get("id");
//				for (Map<String, String> mp : bpower) {
//					if(mp.get("parentid").equals(id)){
//						rl.add(mp);
//					}
//				}
//				break;
//			}
//		}
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals(menu_htmlcode)){
				rl.add(map);
				String id=map.get("id");
				for (Map<String, String> mp : bpower) {
					if(mp.get("parentid").equals(id)){
						rl.add(mp);
						for (Map<String, String> mbp : bpower) {
							if(mbp.get("parentid").equals(mp.get("id"))){
								rl.add(mbp);
							}
						}
					}
				}
				break;
			}
		}
		
		return rl;
	}
	public User(List<Map<String, String>> bpower,UserBean userbean) {
		super();
		this.userid = userbean.userid;
		this.cusid = userbean.cusid;
		this.departid = userbean.departid;
		this.departname = userbean.departname;
		this.departcode = userbean.departcode;
		this.usercode = userbean.usercode;
		this.workcode = userbean.workcode;
		this.username = userbean.username;
		this.pname = userbean.pname;
		this.hytype = userbean.hytype;
		this.dqdate = userbean.dqdate;
		this.emall=userbean.emall;
		this.phone=userbean.phone;
		this.cusname=userbean.cusname;
		this.bpower=bpower;
		this.state=userbean.getState();
	}
	public User() {
		super();
	}
	
	
}
