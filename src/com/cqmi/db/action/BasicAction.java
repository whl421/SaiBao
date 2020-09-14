package com.cqmi.db.action;

import java.util.List;
import java.util.Map;

import com.cqmi.db.bean.LoginUser;
import com.cqmi.db.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BasicAction extends ActionSupport{
	private String jsonStr;
	private String act;
    private String actjson;
    private String oper;
    private String page;  //请求页面 从1开始
    private String total;  //总共多少页
    private String sidx; //排序的列名
    private String sord; //排序的方式
    private String record;
    private String rows; //每页多少行
    public String colnameorder; //字段排列顺序
    private String iframeid;
    private String htmlurl;
    private String returnmap;
    @SuppressWarnings("rawtypes")
	private List<Map> lmap;
    
    
    
	public String getReturnmap() {
		return returnmap;
	}
	public void setReturnmap(String returnmap) {
		this.returnmap = returnmap;
	}
	@SuppressWarnings("rawtypes")
	public List getLmap() {
		return lmap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setLmap(List lmap) {
		this.lmap = lmap;
	}
	public String getHtmlurl() {
		return htmlurl;
	}
	public void setHtmlurl(String htmlurl) {
		this.htmlurl = htmlurl;
	}
	public String getIframeid() {
		return iframeid;
	}
	public void setIframeid(String iframeid) {
		this.iframeid = iframeid;
	}
    private LoginUser user;
	 
    
    public LoginUser getUser() {
		return user;
	}
	public void setUser(LoginUser user) {
		this.user = user;
	}

	@SuppressWarnings("rawtypes")
	private Map remap;

	public String getColnameorder() {
		return colnameorder;
	}
	public void setColnameorder(String colnameorder) {
		this.colnameorder = colnameorder;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	@SuppressWarnings("rawtypes")
	public Map getRemap() {
		return remap;
	}
	@SuppressWarnings("rawtypes")
	public void setRemap(Map remap) {
		this.remap = remap;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public String getActjson() {
		return actjson;
	}
	public void setActjson(String actjson) {
		this.actjson = actjson;
	}
	
	public String maintest(){
		
		return SUCCESS;
	}
	
	/**
	 *测试json 
	 * @return
	 */
	public String getJson(){
		System.out.println(page); 
		System.out.println(total);
		System.out.println(record);
		System.out.println(rows);
		System.out.println(sidx);
		System.out.println(sord);
		System.out.println(actjson);
		System.out.println("oper===="+oper);
		
		
	    String res="{\"page\":\"1\",\"total\":2,\"records\":\"13\",\"rows\":[" +
	    		"{\"id\":\"13\",\"cell\":[\"13\",\"13\",\"2007-10-06\",\"Client 3\",\"1000.00\",\"0.00\",\"1000.00\",\"1\"]}," +
	    		"{\"id\":\"12\",\"cell\":[\"12\",\"12\",\"2007-10-06\",\"Client 2\",\"700.00\",\"140.00\",\"840.00\",\"2\"]}," +
	    		"{\"id\":\"11\",\"cell\":[\"11\",\"11\",\"2007-10-06\",\"Client 1\",\"600.00\",\"120.00\",\"720.00\",\"3\"]}," +
	    		"{\"id\":\"10\",\"cell\":[\"10\",\"10\",\"2007-10-06\",\"Client 2\",\"100.00\",\"20.00\",\"120.00\",null]}," +
	    		"{\"id\":\"9\",\"cell\":[\"9\",\"9\",\"2007-10-06\",\"Client 1\",\"200.00\",\"40.00\",\"240.00\",null]}," +
				"{\"id\":\"8\",\"cell\":[\"8\",\"8\",\"2007-10-06\",\"Client 3\",\"200.00\",\"0.00\",\"200.00\",null]}," +
				"{\"id\":\"7\",\"cell\":[\"7\",\"7\",\"2007-10-05\",\"Client 2\",\"120.00\",\"12.00\",\"134.00\",null]}," +
				"{\"id\":\"6\",\"cell\":[\"6\",\"6\",\"2007-10-05\",\"Client 1\",\"50.00\",\"10.00\",\"60.00\",\"\"]}," +
				"{\"id\":\"5\",\"cell\":[\"5\",\"5\",\"2007-10-05\",\"Client 3\",\"100.00\",\"0.00\",\"100.00\",\"no tax at all\"]}," +
				"{\"id\":\"4\",\"cell\":[\"4\",\"4\",\"2007-10-04\",\"Client 3\",\"150.00\",\"0.00\",\"150.00\",\"no tax\"]}," +
				"{\"id\":\"3\",\"cell\":[\"3\",\"3\",\"2007-10-04\",\"Client 33\",\"150.00\",\"0.00\",\"150.00\",\"no tax\"]}," +
				"{\"id\":\"2\",\"cell\":[\"2\",\"2\",\"2007-10-04\",\"Client 23\",\"150.00\",\"0.00\",\"150.00\",\"no tax\"]}," +
				"{\"id\":\"1\",\"cell\":[\"1\",\"1\",\"2007-10-04\",\"Client 13\",\"150.00\",\"0.00\",\"150.00\",\"no tax\"]}]}";
		setRemap(new JsonUtil().StringToMap(res));
	    
		return SUCCESS;
	}
	
	
	
	
	
}
