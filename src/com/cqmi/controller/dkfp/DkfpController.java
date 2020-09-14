package com.cqmi.controller.dkfp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cqmi.controller.login.LoginController;
import com.cqmi.controller.login.bean.User;
import com.cqmi.dao.util.Util;
import com.cqmi.db.action.BasicAction;
import com.cqmi.db.service.BeanService_Transaction;

@Controller
@RequestMapping("/dkfp")
public class DkfpController extends BasicAction{
	
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
	@RequestMapping(value="/list",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String list(String page,String rows,String sort,String sortOrder,String searchjson) throws IOException{
		System.out.println("--进入(dkfp/list)--");
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by ordercode ";
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
		String where="where 1=1 and cusid = '"+cusid+"' ";
		
		String ordercode = json.getString("ordercode");
		String ordername = json.getString("ordername");
		String dkstate = json.getString("dkstate");
		String fpstate = json.getString("fpstate");
		String dkdate = json.getString("dkdate");
		if(dkdate != null && !"".equals(dkdate)) {
			String sdate = dkdate.split("--")[0].trim();
			String edate = dkdate.split("--")[1].trim();
			where += " and dkdate >= '"+sdate+"' and dkdate <= '"+edate+"'";
		}
		where += ordercode != null && !ordercode.trim().equals("")?" and t.ordercode like '%"+ordercode+"%'":"";
		where += ordername != null && !ordername.trim().equals("")?" and ordername like '%"+ordername+"%'":"";
		where += dkstate != null && !dkstate.trim().equals("")?" and dkstate = '"+dkstate+"'":"";
		where += fpstate != null && !fpstate.trim().equals("")?" and fpstate = '"+fpstate+"'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from (select o.ORDERCODE,o.ORDERNAME,t.* from task_dkfp t " 
				  + " left join orderform o on o.ORDERID = t.TASKID) t "
				  + " "+where+" "+order+" limit "+num+","+rows ;
		/**
		 * 数据总条数
		 */
		String sqlt =" select count(*) total from (select o.ORDERCODE,o.ORDERNAME,t.* from task_dkfp t " 
				  + " left join orderform o on o.ORDERID = t.TASKID) t  "
				    +" "+where;
		
		/**
		 * map中为table的数据组装
		 */
		Map map=new HashMap();
		//total 总行数
		String titl[] = {"total"};
		map.put("total", tservice.getSelect(sqlt, titl).get(0).get("total"));
		
		//rows 格式为[{id:1,name:2},{id:1,name:2}...]
		map.put("rows", tservice.getSelect(sql)); 
		//关闭链接
		tservice.Close();
		
		
//		System.out.println(jsonObject.fromObject(map).toString());
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 发票维护 表数据
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/update",produces="application/json; charset=utf-8")
	@ResponseBody
	public String update(String tablejson) throws IOException{
		System.out.println("--进入(dkfp/update)--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		JSONObject json=jsonObject.fromObject(tablejson);
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		//获取参数值
		String dkfpid = json.optString("dkfpid");
		String fpstate = json.optString("fpstate");
		String kddate = json.optString("kddate");
		String kdname = json.optString("kdname");
		String kdno = json.optString("kdno");
		String kdusername = json.optString("kdusername");
		String kdtime = json.optString("kdtime");
		
		List<String> lt=new ArrayList<String>();
		String sql= " update task_dkfp set " +
				" fpstate=?,kddate=?,kdname=?," +
				" kdno=?,kdusername=?,kdtime=? " +
				" where dkfpid=? " ;
		
		//注意参数顺序
		lt.add(fpstate);lt.add(kddate);lt.add(kdname);
		lt.add(kdno);lt.add(kdusername);lt.add(kdtime);
		lt.add(dkfpid);
		
		Map map = new HashMap();
		int r = tservice.UpdateSQL2(sql, lt);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
		}else{
			tservice.commitExe_close();
		}
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 到款确认 表数据
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/dkconfirm",produces="application/json; charset=utf-8")
	@ResponseBody
	public String dkconfirm(String tablejson) throws IOException{
		System.out.println("--进入(dkfp/dkconfirm)--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		JSONObject json=jsonObject.fromObject(tablejson);
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		//获取参数值
		String dkfpid = json.optString("dkfpid");
		String dkstate = json.optString("dkstate");
		String dkdate = json.optString("dkdate");
		
		List<String> lt=new ArrayList<String>();
		String sql= " update task_dkfp set " +
				" dkstate=?,dkdate=? " +
				" where dkfpid=? " ;
		
		//注意参数顺序
		lt.add(dkstate);lt.add(dkdate);
		lt.add(dkfpid);
		
		Map map = new HashMap();
		int r = tservice.UpdateSQL2(sql, lt);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
		}else{
			tservice.commitExe_close();
		}
	    return jsonObject.fromObject(map).toString();
	}
		
	/**
	 * 到款确认表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dkconfirmForm")
	@ResponseBody
	public ModelAndView dkconfirmForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {
			
			String sql = " select o.ORDERCODE,o.ORDERNAME,t.* from task_dkfp t " +
					" left join orderform o on o.ORDERID = t.TASKID " +
					" where t.DKFPID = '"+tablejson+"'";
			List<Map<String, String>> lmap = tservice.getSelect(sql);
			if(lmap != null && lmap.size() > 0) {
				modelAndView.addObject("map", lmap.get(0));
			}
			modelAndView.setViewName("dkfp/dkConfirm");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
		
	}
	
	/**
	 * 发票维护表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateForm")
	@ResponseBody
	public ModelAndView updateForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String username = user.getUsername();
			String sql = " select o.ORDERCODE,o.ORDERNAME,t.* from task_dkfp t " +
					" left join orderform o on o.ORDERID = t.TASKID " +
					" where t.DKFPID = '"+tablejson+"'";
			List<Map<String, String>> lmap = tservice.getSelect(sql);
			if(lmap != null && lmap.size() > 0) {
				lmap.get(0).put("kdusername", username);
				lmap.get(0).put("kdtime", util.getNowTime());
				modelAndView.addObject("map", lmap.get(0));
			}
			modelAndView.setViewName("dkfp/dkfpUpdate");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
	}
	
	/**
	 * 进入首页
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
		String code = "dkfp";
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
			modelAndView.setViewName("dkfp/list");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;

	}
}
