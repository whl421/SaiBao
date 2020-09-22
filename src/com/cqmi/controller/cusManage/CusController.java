package com.cqmi.controller.cusManage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.cqmi.controller.login.LoginController;
import com.cqmi.controller.login.bean.User;
import com.cqmi.dao.util.Util;
import com.cqmi.db.action.BasicAction;
import com.cqmi.db.service.BeanService_Transaction;

@Controller
public class CusController extends BasicAction{
	
	
	/**
	 * 查询列表
	 * @param page
	 * @param rows
	 * @param sort
	 * @param sortOrder
	 * @param searchjson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="cus/cuslist",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String cuslist(String page,String rows,String sort,String sortOrder,String searchjson) throws IOException{
		System.out.println("--进入--");
//		System.out.println(page); 
//		System.out.println(rows); 
//		System.out.println(sort);
//		System.out.println(sortOrder); 
//		System.out.println(searchjson);
//		System.out.println("--结束--");
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&sort!=""){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by cuscode ";
		}
		/**
		 * 	page第几页	rows每页多少行    
		 */ 
		int num=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
		
		JSONObject jsonObject=new JSONObject();
		/**
		 * searchjson 查询条件
		 */
		JSONObject json=jsonObject.fromObject(searchjson);
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String userid = user.getUserid();
		String cusid = user.getCusid();
				
		/**
		 * 查询条件
		 */
		String where="where 1=1  ";
		String cusname=json.getString("cusname");
		where+=cusname!=null&&!cusname.trim().equals("")?" and cusname like '%"+cusname+"%'":"";

		/**
		 * 查询数据sql
		 */
		String sql=" select * from basic_customer "+where+" "+order+" limit "+num+","+rows;
		/**
		 * 数据总条数
		 */
		String sqlt="select count(*) total from basic_customer "+where;
		//开启数据库链接
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		/**
		 * map中为table的数据组装
		 */
		Map map=new HashMap();
		//total 总行数
		map.put("total", tservice.getSelect(sqlt).get(0).get("total"));
		
		//rows 格式为[{id:1,name:2},{id:1,name:2}...]
		map.put("rows", tservice.getSelect(sql)); 
		//关闭链接
		tservice.Close();
		
		
//		System.out.println(jsonObject.fromObject(map).toString());
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 新增数据 
	 */
	@RequestMapping(value="cus/cussaveZC",produces="application/json; charset=utf-8")
	@ResponseBody
	public String cussaveZC(String tablejson) throws IOException{
		System.out.println("--进入--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		//开起事物
		tservice.OpenTransaction();
				
		//获取当前时间
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		
		//tablejson 转为json
		JSONObject json=jsonObject.fromObject(tablejson);
		
		//获取页面参数值
		String cuscode = json.optString("cuscode");
		String cusname = json.optString("cusname");
		//String pwd = json.optString("pwd");
		String lxr = json.optString("lxr");
		String tel = json.optString("tel");
		String email = json.optString("email");
		String creater = user.getUserid();
		String creatername = user.getUsername();
		String creattime = datetime;
		String pwd = "-41149678231811287725040083248417372098";
		
		Map map=new HashMap();
		int r;
		//保存客户信息
		String sql = " insert into basic_customer (cuscode,cusname,sysusername,syspassword,lxr,tel,email,state,creater,creattime) "
				   + " value (?,?,?,?,?,?,?,?,?,?) ";
		
		List<String> lt = new ArrayList<String>();
		lt.add(cuscode);lt.add(cusname);lt.add(cuscode);lt.add(pwd);lt.add(lxr);
		lt.add(tel);lt.add(email);lt.add("0");lt.add(creater);lt.add(creattime);
		r = tservice.InsertSQL2(sql, lt);
		if(r == 0) {
			map.put("info", "0");
			map.put("text", "新增失败请联系管理员");
			tservice.rollbackExe_close();
		}
		
		
		
		tservice.commitExe_close();
		
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 新增数据 
	 */
	@RequestMapping(value="cus/cussave",produces="application/json; charset=utf-8")
	@ResponseBody
	public String cussave(String tablejson) throws IOException{
		System.out.println("--进入--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		//开起事物
		tservice.OpenTransaction();
				
		//获取当前时间
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		
		//tablejson 转为json
		JSONObject json=jsonObject.fromObject(tablejson);
		
		//获取页面参数值
		String cuscode = json.optString("cuscode");
		String cusname = json.optString("cusname");
		//String pwd = json.optString("pwd");
		String lxr = json.optString("lxr");
		String tel = json.optString("tel");
		String email = json.optString("email");
		//String creater = user.getUserid();
		//String creatername = user.getUsername();
		String creattime = datetime;
		String pwd = "-41149678231811287725040083248417372098";
		
		Map map=new HashMap();
		int r;
		//保存客户信息
		String sql = " insert into basic_customer (cuscode,cusname,sysusername,syspassword,lxr,tel,email,state,creater,creattime) "
				   + " value (?,?,?,?,?,?,?,?,?,?) ";
		
		List<String> lt = new ArrayList<String>();
		lt.add(cuscode);lt.add(cusname);lt.add(cuscode);lt.add(pwd);lt.add(lxr);
		lt.add(tel);lt.add(email);lt.add("0");lt.add(cusname);lt.add(creattime);
		r = tservice.InsertSQL2(sql, lt);
		if(r == 0) {
			map.put("info", "0");
			map.put("text", "新增失败请联系管理员");
			tservice.rollbackExe_close();
		}
		
		
		
		tservice.commitExe_close();
		
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 修改 表数据
	 *  value="user/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="cus/cusupdate",produces="application/json; charset=utf-8")
	@ResponseBody
	public String cusupdate(String tablejson) throws IOException{
		System.out.println("--进入--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		JSONObject json=jsonObject.fromObject(tablejson);
		
		Map map=new HashMap();
		//开起事物
		tservice.OpenTransaction();
		int r;
		
		//获取页面参数值
		String cusid = json.optString("cusid");
		String cuscode = json.optString("cuscode");
		String cusname = json.optString("cusname");
		String cusfullname = json.optString("cusfullname");
		String sysusername = json.optString("sysusername");
		String hytype = json.optString("hytype");
		String dwdz = json.optString("dwdz");
		String yb = json.optString("yb");
		String lxr = json.optString("lxr");
		String tel = json.optString("tel");
		String sbh = json.optString("sbh");
		String khh = json.optString("khh");
		String yhzh = json.optString("yhzh");
		String startdate = json.optString("startdate");
		String dqdate = json.optString("dqdate");
		String xfdate = json.optString("xfdate");
		String version = json.optString("version");
		String usernum = json.optString("usernum");
		String money = json.optString("money");
		String memo = json.optString("memo");
		
		//修改客户信息
		String sql="update basic_customer set cuscode=?,cusname=?,cusfullname=?,hytype=?,dwdz=?,yb=?,lxr=?,tel=?,sbh=?,khh=?,yhzh=?, "
				   + " startdate=?,dqdate=?,xfdate=?,version=?,usernum=?,money=?,memo=?,sysusername=? where cusid = ? ";
		
		List<String> lt=new ArrayList<String>();
		//注意参数顺序
		lt.add(cuscode);lt.add(cusname);lt.add(cusfullname);lt.add(hytype);lt.add(dwdz);lt.add(yb);
		lt.add(lxr);lt.add(tel);lt.add(sbh);lt.add(khh);lt.add(yhzh);
		lt.add(startdate);lt.add(dqdate);lt.add(xfdate);lt.add(version);lt.add(usernum);
		lt.add(money);lt.add(memo);lt.add(sysusername);lt.add(cusid);
		
		r=tservice.UpdateSQL2(sql, lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
			tservice.rollbackExe_close();
		}
		
		//修改管理员账号
		String sql2="update basic_user set usercode=?,phone=? where cusid = ? and issa = '1' ";
		
		List<String> lt2 = new ArrayList<String>();
		//注意参数顺序
		lt2.add(sysusername);lt2.add(sysusername);lt2.add(cusid);
		
		r=tservice.UpdateSQL2(sql2, lt2);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
			tservice.rollbackExe_close();
		}
		
		//修改顶级部门
		String sql3="update basic_depart set departname=? where cusid = ? and departcode = 'root' ";
		
		List<String> lt3 = new ArrayList<String>();
		//注意参数顺序
		lt3.add(cusname);lt3.add(cusid);
		
		r=tservice.UpdateSQL2(sql3, lt3);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
			tservice.rollbackExe_close();
		}
		
		tservice.commitExe_close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 删除数据
	 * value="user/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="cus/cusdelete",produces="application/json; charset=utf-8")
	@ResponseBody
	public String jkapplydelete(String tablejson) throws UnsupportedEncodingException{
		System.out.println("删除数据id有"+tablejson);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		String sql="delete from basic_customer where cusid = " + tablejson;
		int r=tservice.DeleteSQL2(sql);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
		
			tservice.rollbackExe_close();
		}else{
			tservice.commitExe_close();
		}
		
	    return new JSONObject().fromObject(map).toString();
		
	}
	
	/**
	 * 重置密码
	 */
	@RequestMapping(value="cus/cusupdatepwd",produces="application/json; charset=utf-8")
	@ResponseBody
	public String cusupdatepwd(String tablejson) throws UnsupportedEncodingException{
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		
		//查询用户id
		String uidsql = " select sysusername from basic_customer where cusid = ? ";
		
		List<String> lt2=new ArrayList<String>();
		lt2.add(tablejson);
		
		List<Map<String, String>> usercodemap = tservice.getSelect(uidsql,lt2);
		String usercode = usercodemap.get(0).get("sysusername");
		
		String pwd = "-41149678231811287725040083248417372098";
		
		//重置密码
		String sql="update basic_user set pwd = ? where usercode = ? ";
		
		List<String> lt=new ArrayList<String>();
		lt.add(pwd);lt.add(usercode);
		
		int r=tservice.UpdateSQL2(sql,lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "重置密码失败,请联系管理员");
		
			tservice.rollbackExe_close();
		}else{
			tservice.commitExe_close();
		}
		
	    return new JSONObject().fromObject(map).toString();
		
	}
	
	/**
	 * 验证客户编号
	 */
	@RequestMapping("cus/cuscodeYZ")
	@ResponseBody
	
	public String cuscodeYZ(String tablejson,String cusid) throws Exception {
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		JSONObject jsonObject=new JSONObject();
		
		String sql=" select * from basic_customer where cuscode = '"+tablejson+"'";
		if(cusid != "" && cusid != null){
			sql += " and cusid <> '"+cusid+"' ";
		}
		
		List<Map<String, String>> lmap = tservice.getSelect(sql);
		Map map=new HashMap();
		if(lmap.size() > 0){
			map.put("isok", "no");
		}else{
			map.put("isok", "yes");
		}
		//关闭链接
		tservice.Close();
		
		return jsonObject.fromObject(map).toString();
		
	}
	
	/**
	 * 验证管理员账号
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cus/sysusernameYZ")
	@ResponseBody
	
	public String sysusernameYZ(String tablejson) throws Exception {
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		JSONObject jsonObject=new JSONObject();
		String sql=" select * from basic_customer where sysusername = '"+tablejson+"'";
		List<Map<String, String>> lmap = tservice.getSelect(sql);
		Map map=new HashMap();
		if(lmap.size() > 0){
			map.put("isok", "no");
		}else{
			map.put("isok", "yes");
		}
		//关闭链接
		tservice.Close();
		
		return jsonObject.fromObject(map).toString();
		
	}
	
	/**
	 * 查看表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cus/cusload")
	@ResponseBody
	public ModelAndView cusload(String tablejson) throws Exception {
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		String sql=" select * from basic_customer where cusid='"+tablejson+"'";
		List<Map<String, String>> lmap=tservice.getSelect(sql);
		
		tservice.Close();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("map", lmap.get(0));
		modelAndView.setViewName("CusManage/cus_loadform");
		
		return modelAndView;
		
	}
	
	/**
	 * 查看客户版本信息
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cus/cuseditionload")
	@ResponseBody
	public ModelAndView cuseditionload() throws Exception {
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
		
		String sql=" select * from basic_customer where cusid='"+cusid+"'";
		List<Map<String, String>> lmap=tservice.getSelect(sql);
		
		tservice.Close();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("map", lmap.get(0));
		modelAndView.setViewName("CusManage/cus_editioninfo");
		
		return modelAndView;
		
	}
	
	/**
	 * 查看表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cus/fromuf8info")
	@ResponseBody
	public ModelAndView fromuf8info() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("utf8-jsp/index");
		
		return modelAndView;
		
	}
	
	
	/**
	 * 查看表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cus/setfrominfo")
	@ResponseBody
	public ModelAndView setfrominfo(String input,String select,String editorValue) throws Exception {
		if(input != null) {
			input =new String(input.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		if(select != null) {
			select =new String(select.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		if(editorValue != null) {
			editorValue =new String(editorValue.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		System.out.println(input);
		
		System.out.println(select);
		
		System.out.println(editorValue);
		
		ModelAndView modelAndView = new ModelAndView();
	//	modelAndView.addObject("boo", "1");
	//	modelAndView.setViewName("redirect:/user/fromuf8info.action");
	//content
		modelAndView.addObject("input", input);
		modelAndView.addObject("select", select);
		modelAndView.addObject("content", editorValue);
		modelAndView.setViewName("utf8-jsp/show");
		return modelAndView;
		
	}
 
	/**
	 * 新增表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cus/cusadd")
	public ModelAndView cusadd(String tablejson) throws Exception {
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		Util util = new Util();
		ModelAndView modelAndView = new ModelAndView();
		try {

			modelAndView.setViewName("CusManage/cus_addform");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;

	}
	
	/**
	 * 用户注册
	 */
	@RequestMapping("cus/cuszc")
	public ModelAndView cuszc(String tablejson) throws Exception {
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		Util util = new Util();
		ModelAndView modelAndView = new ModelAndView();
		try {

			modelAndView.setViewName("CusManage/cus_register");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;

	}
	
	/**
	 * 初始化权限
	 */
	@RequestMapping(value="cus/roleadd",produces="application/json; charset=utf-8")
	@ResponseBody
	public String roleadd(String tablejson) throws UnsupportedEncodingException{
		System.out.println("初始化数据id有"+tablejson);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		int r;
		Map map=new HashMap();
		
		String cusid = tablejson;
		
		//获取当前时间
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String creater = user.getUserid();
		String creatername = user.getUsername();
		String creattime = datetime;
		
		//查询客户信息
		String uidsql = " select * from basic_customer where cusid = " + cusid ;
		List<Map<String, String>> cusmap = tservice.getSelect(uidsql);
		String cusname = cusmap.get(0).get("cusname");
		String sysusername = cusmap.get(0).get("sysusername");
		String pwd = cusmap.get(0).get("syspassword");
		String email = cusmap.get(0).get("email");
				
		//新增超级管理员信息
		String sql2 = " insert into basic_user (cusid,phone,emall,pwd,issa,usercode,username, "
				    + " state,userstatus,createid,creater,creattime) "
				    + " value (?,?,?,?,?,?,?,?,?,?,?,?) ";
		
		List<String> lt2 = new ArrayList<String>();
		lt2.add(cusid);	lt2.add(sysusername);lt2.add(email);lt2.add(pwd);lt2.add("1");
		lt2.add(sysusername);lt2.add("系统管理员");lt2.add("1");lt2.add("1");lt2.add(creater);
		lt2.add(creatername);lt2.add(creattime);
		r = tservice.InsertSQL2(sql2, lt2);
		if(r == 0) {
			map.put("info", "0");
			map.put("text", "新增失败请联系管理员");
			tservice.rollbackExe_close();
		}
		
		//新增系统管理员权限
		String sql3 = " insert into roleuser (roleid,userid,cusid) value (?,(SELECT LAST_INSERT_ID()),? ) ";
		
		List<String> lt3 = new ArrayList<String>();
		lt3.add("2");lt3.add(cusid);
		r = tservice.InsertSQL2(sql3, lt3);
		if(r == 0) {
			map.put("info", "0");
			map.put("text", "新增失败请联系管理员");
			tservice.rollbackExe_close();
		}
		
		//新增部门信息
		String sql4 = " insert into basic_depart (cusid,departcode,departname,parentid,createuser,createtime,state) "
					+ " value (?,?,?,?,?,?,? ) ";
		
		List<String> lt4 = new ArrayList<String>();
		lt4.add(cusid);lt4.add("root");lt4.add(cusname);lt4.add("0");lt4.add(creater);lt4.add(creattime);lt4.add("1");
		r = tservice.InsertSQL2(sql4, lt4);
		if(r == 0) {
			map.put("info", "0");
			map.put("text", "新增失败请联系管理员");
			tservice.rollbackExe_close();
		}
		
		//更新客户状态为“已审核”
		String usql = " update basic_customer set state = 1 where cusid = '"+cusid+"' ";
		
		r = tservice.InsertSQL2(usql);
		if(r == 0) {
			map.put("info", "0");
			map.put("text", "新增失败请联系管理员");
			tservice.rollbackExe_close();
		}
		
//		//查询角色
//		String jssql = " select id,rolecode,rolename from role where cusid = '1' and id not in (1,2) ";
//		List<Map<String, String>> cusidmap = tservice.getSelect(jssql);
//		for(int i = 0; i < cusidmap.size(); i++){
//			String id = cusidmap.get(i).get("id");
//			String rolecode = cusidmap.get(i).get("rolecode");
//			String rolename = cusidmap.get(i).get("rolename");
//			
//			String sql5 = " insert into role (rolecode,rolename,cusid) value (?,?,?) ";
//		
//			List<String> lt5 = new ArrayList<String>();
//			lt5.add(rolecode);lt5.add(rolename);lt5.add(cusid);
//			r = tservice.InsertSQL2(sql5, lt5);
//			if(r == 0) {
//				map.put("info", "0");
//				map.put("text", "新增失败请联系管理员");
//				tservice.rollbackExe_close();
//			}
//			
//			//查询角色id
//			String ridsql = " select id from role where id = (SELECT LAST_INSERT_ID()) ";
//			List<Map<String, String>> ridmap = tservice.getSelect(ridsql);
//			String rid = ridmap.get(0).get("id");
//			
//			//查询角色权限
//			String mridsql = " select menuid from menurole where roleid = '"+id+"' ";
//			List<Map<String, String>> mridmap = tservice.getSelect(mridsql);
//			
//			if(mridmap.size() > 0 && mridmap != null){
//				for(int m = 0; m < mridmap.size(); m++){
//					String menuid = mridmap.get(m).get("menuid");
//					String sql3 = " insert into menurole (menuid,roleid,cusid) value (?,?,?) ";
//					
//					List<String> lt3 = new ArrayList<String>();
//					lt3.add(menuid);lt3.add(rid);lt3.add(cusid);
//					r = tservice.InsertSQL2(sql3, lt3);
//					if(r == 0) {
//						map.put("info", "0");
//						map.put("text", "新增失败请联系管理员");
//						tservice.rollbackExe_close();
//					}
//				}
//			}
//		}
		
		
		tservice.commitExe_close();		
		
	    return new JSONObject().fromObject(map).toString();
		
	}
	
	
	/**
	 * 进入首页
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cus/cusindex")
	public ModelAndView cusindex() throws Exception {
		/**
		 * 权限判定
		 * lstaskfp  角色菜单中的菜单编号
		 */
		User user= ((User)LoginController.getSession().getAttribute("user"));
		List<Map<String, String>> bpower=user.getBpower("cusmanage");		//tservice.getSelect(G_b_sql);
		/**
		 * 1.确认当前模块权限，
		 * 2.获取按钮权限
		 */
		boolean boo=false;
		Map mb=new HashMap();
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals("cusmanage"))boo=true;
			if(!map.get("type").equals("2"))mb.put(map.get("htmlcode"), "1");
			 
		}
		
		ModelAndView modelAndView = new ModelAndView();
		
		//角色权限增加
		if(boo){
			modelAndView.addObject("button", mb);
			modelAndView.setViewName("CusManage/cusmanage");
		}else
			modelAndView.setViewName("nopower");
		
		return modelAndView;

	}

}
