package com.cqmi.db.bean;

import java.io.Serializable;


@SuppressWarnings("serial")
public class TreeBean  implements Serializable{
 private String treeid;
 private String treename;
 private String parentid;
 private String departlevel;
// private Map<String, TreeBean> treemap=new HashMap<String, TreeBean>();
// 
//public Map<String, TreeBean> getTreemap() {
//	return treemap;
//}
//public void setTreemap(Map<String, TreeBean> treemap) {
//	this.treemap = treemap;
//}

 
public String getDepartlevel() {
		return departlevel;
}
public void setDepartlevel(String departlevel) {
	this.departlevel = departlevel;
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

public TreeBean(String treeid, String treename, String parentid,
		String departlevel) {
	super();
	this.treeid = treeid;
	this.treename = treename;
	this.parentid = parentid;
	this.departlevel = departlevel;
}
public TreeBean() {
	super();
}
 
}
