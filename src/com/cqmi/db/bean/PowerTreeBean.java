package com.cqmi.db.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PowerTreeBean  implements Serializable{
 private String treeid;
 private String treename;
 private String parentid;
 private String ischeck;
 private String htmlid; 
 private String type;
// private Map<String, TreeBean> treemap=new HashMap<String, TreeBean>();
// 
//public Map<String, TreeBean> getTreemap() {
//	return treemap;
//}
//public void setTreemap(Map<String, TreeBean> treemap) {
//	this.treemap = treemap;
//}

public String getIscheck() {
	return ischeck;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getHtmlid() {
	return htmlid;
}
public void setHtmlid(String htmlid) {
	this.htmlid = htmlid;
}
public void setIscheck(String ischeck) {
	this.ischeck = ischeck;
}
public String getTreeid() {
	return treeid;
}
public void setTreeid(String treeid) {
	this.treeid = treeid;
}
public String getTreename() {
	return treename;
}
public void setTreename(String treename) {
	this.treename = treename;
}
public String getParentid() {
	return parentid;
}
public void setParentid(String parentid) {
	this.parentid = parentid;
}

public PowerTreeBean() {
	super();
}
 
}
