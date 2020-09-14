package com.cqmi.controller.orderManage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cqmi.controller.login.LoginController;
import com.cqmi.controller.login.bean.User;
import com.cqmi.dao.util.Util;
import com.cqmi.db.service.BeanService_Transaction;


@Controller
@RequestMapping("/order")
public class OrderFormController {
	
	Util util = new Util();
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
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@RequestMapping(value="/list",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String list(String page,String rows,String sort,String sortOrder,String searchjson,String actjson) throws IOException{
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by t.CREATETIME desc ";
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
		String cusid = user.getCusid();
				
		/**
		 * 查询条件
		 */
		String where="where 1=1 ";
		String ordercode = json.getString("ordercode");
		String ordername = json.getString("ordername");
		String khname = json.getString("khname");
		String orderdate = json.optString("orderdate");
		if(orderdate != null && !orderdate.trim().equals("")){
			String startdate = orderdate.split("--")[0].trim();
			String enddate = orderdate.split("--")[1].trim();
			where += " and ORDERTIME >= '"+startdate+"' and ORDERTIME <= '"+enddate+"'";
		}
		where += ordercode != null && !ordercode.trim().equals("")?" and t.ordercode like '%"+ordercode+"%'":"";
		where += ordername != null && !ordername.trim().equals("")?" and t.ordername like '%"+ordername+"%'":"";
		where += khname != null && !khname.trim().equals("")?" and t.khname like '%"+khname+"%'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from (select * from orderform t where t.OSTATE = '1') t "
				  +" "+where+" "+order+" limit "+num+","+rows ;
		String title[] = {"orderid","cusid","khname","ordercode","ordername","orderuser","ordertime","ostate",
				"orderprice","pricetime","qrmemo","tsusername","tsdate","departcode","departname","liststate",
				"confirmuser","confirmmemo","listdate","createtime"};
		/**
		 * 数据总条数
		 */
		String sqlt="select count(1) total from (select * from orderform t where t.OSTATE = '1') t "+where;
		String titl[] = {"total"};
		
		/**
		 * map中为table的数据组装
		 */
		Map map=new HashMap();
		//total 总行数
		map.put("total", tservice.getSelect(sqlt, titl).get(0).get("total"));
		
		//rows 格式为[{id:1,name:2},{id:1,name:2}...]
		map.put("rows", tservice.getSelect(sql,title)); 
		//关闭链接
		tservice.Close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 新增数据 
	 *  value="user/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@RequestMapping(value="/save",produces="application/json; charset=utf-8")
	@ResponseBody
	public String save(@RequestParam(value ="file",required = false)CommonsMultipartFile []file,
			String ordercode, String ordername, String ordertime, String orderuser, String khname) throws IOException{
		System.out.println("--进入(order/save)--");
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		//获取当前时间
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		
		/**
		 * tablejson 转为json
		 */
//		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();

		//=============================自动生成部门编号=============================
		
		String cusid = user.getCusid();
		String createuser = user.getUsername();
		String createtime = datetime;
		String ostate = "1";
		
		String sql="insert into orderform (CUSID,KHNAME,ORDERCODE," +
				" ORDERNAME,ORDERUSER,ORDERTIME," +
				" OSTATE,CREATETIME) " +
				" value (" +
				" ?,?,?," +
				" ?,?,?," +
				" ?,?" +
				" ) ";
		
		//注意参数顺序
		lt.add(cusid);lt.add(khname);lt.add(ordercode);
		lt.add(ordername);lt.add(orderuser);lt.add(ordertime);
		lt.add(ostate);lt.add(createtime);
		
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		int r=tservice.InsertSQL2(sql, lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		//获取刚插入的ID
		String newid = "";
		String title[] = {"id"};
		List<Map<String, String>> list = tservice.getSelect("select LAST_INSERT_ID() as id",title);
//		System.out.println("==="+list.get(0));
		if(list != null && list.size()>0) {
			newid = list.get(0).get("id");
		} else {
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
		}
		if(file.length > 0 && file[0].getSize() != 0) {
			//往附件表中增加数据
			String result = util.insertAttach(tservice, file, newid, user, "orderform", "orderid");
			if("0".equals(result)) {
				r = 0;
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
			}
		}
		map.put("info", "1");
		map.put("text", "操作成功");
		tservice.commitExe_close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 修改数据 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@RequestMapping(value="/updateinfo",produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateinfo(@RequestParam(value ="file",required = false)CommonsMultipartFile []file,
			String ordercode, String ordername, String ordertime, String orderuser, String khname,String orderid) throws IOException{
		System.out.println("--进入(order/updateinfo)--");
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		//获取当前时间
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		
		/**
		 * tablejson 转为json
		 */
//		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();

		//=============================自动生成部门编号=============================
		
		String cusid = user.getCusid();
		String createuser = user.getUsername();
		String createtime = datetime;
		String ostate = "1";
		
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
				
		String sql = " update orderform set khname=?,ordercode=?, " 
				   + " ordername=?,orderuser=?,ordertime=?"
				   + " where orderid = ? ";
		
		//注意参数顺序
		lt.add(khname);lt.add(ordercode);lt.add(ordername);
		lt.add(orderuser);lt.add(ordertime);lt.add(orderid);
		
		int r=tservice.InsertSQL2(sql, lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		
		/*
		 * //获取刚插入的ID
		String newid = "";
		String title[] = {"id"};
		List<Map<String, String>> list = tservice.getSelect("select LAST_INSERT_ID() as id",title);
//		System.out.println("==="+list.get(0));
		if(list != null && list.size()>0) {
			newid = list.get(0).get("id");
		} else {
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
		}
		*/
		//删除附件信息
		String delsql="delete from attach where fid=? and tableid=? and tablecolid=? ";
		
		lt.clear();
		lt.add(orderid);lt.add("orderform");lt.add("orderid");
		
		r=tservice.DeleteSQL2(delsql,lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
			//tservice.rollbackExe_close();
		}else{
			tservice.commitExe();
		}
		
		if(file.length > 0 && file[0].getSize() != 0) {
			//往附件表中增加数据
			String result = util.insertAttach(tservice, file, orderid, user, "orderform", "orderid");
			if("0".equals(result)) {
				r = 0;
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
			}
		}
		
		map.put("info", "1");
		map.put("text", "操作成功");
		tservice.commitExe_close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 进入列表
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listForm")
	public ModelAndView listForm() throws Exception {
		/**
		 * 权限判定
		 * lstaskfp  角色菜单中的菜单编号
		 */
		String code = "orderList";
		User user= ((User)LoginController.getSession().getAttribute("user"));
		List<Map<String, String>> bpower = user.getBpower(code);		//tservice.getSelect(G_b_sql);
		/**
		 * 1.确认当前模块权限，
		 * 2.获取按钮权限
		 */
		boolean boo=false;
		Map mb=new HashMap();
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals(code)){
				boo = true;
			} 
			if(!map.get("type").equals("2")){
				mb.put(map.get("htmlcode"), "1");
			}
		}
		ModelAndView modelAndView = new ModelAndView();
		
		//角色权限增加
		if(boo) {
			modelAndView.addObject("button", mb);
			modelAndView.setViewName("orderManage/orderForm/orderList");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;
	}
	
	/**
	 * 新增表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addForm")
	public ModelAndView addForm(String tablejson) throws Exception {
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			
			modelAndView.setViewName("orderManage/orderForm/orderAdd");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
		
	}
	
	
	/**
	 * 查看/修改表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/infoForm")
	public ModelAndView infotable(String tablejson,String type) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jsonObject = new JSONObject();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String userid = user.getUserid();
			
			String xssql = "select * from ORDERFORM t where t.ORDERID = '"+tablejson+"'";
			List<Map<String, String>> xsmap = tservice.getSelect(xssql);
			
			if(xsmap.size() > 0 && xsmap != null) {
				modelAndView.addObject("map", xsmap.get(0));
			} 
			//查询附件信息
			String sel_fjsql = "select * from attach t where t.CUSID = '"+cusid+"' and t.fid = '"+tablejson+"' " +
					" and t.tableid = 'orderform' and t.tablecolid = 'orderid'";
			List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql);
			
			modelAndView.addObject("cusid", cusid);
			modelAndView.addObject("username", user.getUserid()+"-"+user.getUsercode());
			modelAndView.addObject("fjmap",fjmap);
			
			if(type.equals("1")){		//查看
				modelAndView.setViewName("orderManage/orderForm/detailOrder");
			}else if(type.equals("2")){	//修改
				modelAndView.setViewName("orderManage/orderForm/orderUpdate");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
	}
	
	/**
	 * 下载文件
	 * @param request
	 * @param id
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downLoad")
	public ResponseEntity<byte[]> fileDownload(HttpServletRequest request,String id) throws Exception {
		Util util = new Util();
		return util.fileDownload(request, id, "orderform");
	}
	
	/**
	 *  确认
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/confirm",produces="application/json; charset=utf-8")
	@ResponseBody
	public String confirm(String tablejson) throws IOException{
		System.out.println("--进入(order/confirm)--"+tablejson);
		JSONObject jsonObject = new JSONObject();
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map<String, String> map = new HashMap<String, String>();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String userid = user.getUserid();
			String username = util.getUsername(userid, tservice);
			/**
			 * tablejson 数据
			 */
			JSONObject json = jsonObject.fromObject(tablejson);
			List<String> lt = new ArrayList<String>();
			String sql = "update orderform set OSTATE = ?,ORDERPRICE = ?,PRICETIME = ?,QRUSER = ?,QRMEMO = ? where ORDERID = ? ";
			String orderPrice = json.optString("orderPrice");
			String priceTime = new Util().getNowDate();
			String qruser = username;
			String qrmemo = json.optString("qrmemo");
			String orderId = json.optString("orderId");
			String ostate = json.optString("ostate");
			
			//注意参数顺序
			lt.add(ostate);
			lt.add(orderPrice);
			lt.add(priceTime);
			lt.add(qruser);
			lt.add(qrmemo);
			lt.add(orderId);
			
			int r=tservice.UpdateSQL2(sql, lt);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
			} else {
				tservice.commitExe_close();
			}
		} catch(Exception e) {
			e.printStackTrace();
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
		} finally {
			
		}
	    return jsonObject.fromObject(map).toString();
	}
	/**
	 * 确认表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/confirmForm")
	public ModelAndView confirmForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jsonObject = new JSONObject();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String userid = user.getUserid();
			
			String xssql = "select * from ORDERFORM t where t.ORDERID = '"+tablejson+"'";
			List<Map<String, String>> xsmap = tservice.getSelect(xssql);
			
			if(xsmap.size() > 0 && xsmap != null) {
				modelAndView.addObject("map", xsmap.get(0));
			} 
			//查询附件信息
			String sel_fjsql = "select * from attach t where t.CUSID = '"+cusid+"' and t.fid = '"+tablejson+"' " +
					" and t.tableid = 'orderform' and t.tablecolid = 'orderid'";
			List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql);
			
			modelAndView.addObject("cusid", cusid);
			modelAndView.addObject("username", user.getUserid()+"-"+user.getUsercode());
			modelAndView.addObject("fjmap",fjmap);
			modelAndView.setViewName("orderManage/orderForm/confirmOrder");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
	}
}
