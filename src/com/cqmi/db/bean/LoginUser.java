package com.cqmi.db.bean;

import java.io.Serializable;
@SuppressWarnings("serial")
public class LoginUser  implements Serializable{
 private String username;
 private String usercode;
 private String userid;
 
 private String waitdayin;
 private String doshenhe;
 private String waitshenhe;
 
public LoginUser() {
	super();
}

public LoginUser(String username, String usercode, String userid) {
	super();
	this.username = username;
	this.usercode = usercode;
	this.userid = userid;
}

public String getDoshenhe() {
	return doshenhe;
}

public void setDoshenhe(String doshenhe) {
	this.doshenhe = doshenhe;
}

public String getWaitdayin() {
	return waitdayin;
}

public void setWaitdayin(String waitdayin) {
	this.waitdayin = waitdayin;
}

public String getWaitshenhe() {
	return waitshenhe;
}

public void setWaitshenhe(String waitshenhe) {
	this.waitshenhe = waitshenhe;
}

public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getUsercode() {
	return usercode;
}
public void setUsercode(String usercode) {
	this.usercode = usercode;
}
public String getUserid() {
	return userid;
}
public void setUserid(String userid) {
	this.userid = userid;
}
 
}
