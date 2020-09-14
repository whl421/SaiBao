package com.cqmi.controller.login.bean;

public class UserBean {
	public String userid;
	public String cusid;
	public String departid;
	public String departname;
	public String departcode;
	public String usercode;
	public String workcode;
	public String username;
	public String pname;
	public String state;
	public String phone;
	public String emall;
	public String cusname;
	
	
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String hytype;	//客户类型
	public String dqdate;	//到期日期
	
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
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
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
 
 
	
	
	public UserBean(String userid, String cusid, String departid,
			String departname, String departcode, String usercode,
			String workcode, String username, String pname) {
		super();
		this.userid = userid;
		this.cusid = cusid;
		this.departid = departid;
		this.departname = departname;
		this.departcode = departcode;
		this.usercode = usercode;
		this.workcode = workcode;
		this.username = username;
		this.pname = pname;
		this.state = state;
	}
	public UserBean() {
		super();
	}
	
}
