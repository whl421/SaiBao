package com.cqmi.controller.materialManage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/checkHistory")
public class MatCheckHistoryController extends BasicAction{
	
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
		System.out.println("--进入(checkHistory/list)--");
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by CHECKTIME desc";
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
		String where="where 1=1 and cusid = '"+cusid+"' and applystate in('2','3') ";
		
		String applytype = json.getString("applytype");
		String applycode = json.getString("applycode");
		String applyusername = json.getString("applyusername");
		String applytime = json.getString("applytime");
		String applystate = json.getString("applystate");
		String checkusername = json.getString("checkusername");
		String checktime = json.getString("checktime");
		String taskcode = json.getString("taskcode");
		String tasktitle = json.getString("tasktitle");
		
		where += applytype != null && !applytype.trim().equals("")?" and applytype = '"+applytype+"'":"";
		where += applycode != null && !applycode.trim().equals("")?" and t.applycode like '%"+applycode+"%'":"";
		where += applyusername != null && !applyusername.trim().equals("")?" and applyusername like '%"+applyusername+"%'":"";
		where += applytime != null && !applytime.trim().equals("")?" and applytime like '"+applytime+"%'":"";
		where += applystate != null && !applystate.trim().equals("")?" and applystate = '"+applystate+"'":"";
		where += checkusername != null && !checkusername.trim().equals("")?" and checkusername like '%"+checkusername+"%'":"";
		where += checktime != null && !checktime.trim().equals("")?" and checktime like '"+checktime+"%'":"";
		where += taskcode != null && !taskcode.trim().equals("")?" and t.taskcode like '%"+taskcode+"%'":"";
		where += tasktitle != null && !tasktitle.trim().equals("")?" and t.tasktitle like '%"+tasktitle+"%'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from ( " 
				+ " select u.USERNAME as applyusername,ua.USERNAME as checkusername,i.TASKCODE,i.TASKTITLE,t.* \n" 
				+ " from stor_clapply t\n" 
				+ " left join basic_user u on u.USERID = t.APPLYUSER\n" 
				+ " left join basic_user ua on ua.USERID = t.CHECKUSER\n" 
				+ " left join taskinfo i on i.TASKID = t.TASKID ) t "
				+ where +" "+order+" limit "+num+","+rows ;
		String title[] = {"clapplyid","cusid","applycode","applyuser","applytime","applyreason","checkuser",
				"checktime","applystate","jjreason","memo","applyusername","taskcode","tasktitle",
				"checkusername","taskid","applytype","pclapplyid"};
		/**
		 * 数据总条数
		 */
		String sqlt =" select count(*) total from ( select u.USERNAME as applyusername,ua.USERNAME as checkusername,i.TASKCODE,i.TASKTITLE,t.* \n" 
				+ " from stor_clapply t\n" 
				+ " left join basic_user u on u.USERID = t.APPLYUSER\n" 
				+ " left join basic_user ua on ua.USERID = t.CHECKUSER\n" 
				+ " left join taskinfo i on i.TASKID = t.TASKID ) t  "
				    +" "+where;
		
		/**
		 * map中为table的数据组装
		 */
		Map map=new HashMap();
		//total 总行数
		String titl[] = {"total"};
		map.put("total", tservice.getSelect(sqlt,titl).get(0).get("total"));
		
		//rows 格式为[{id:1,name:2},{id:1,name:2}...]
		map.put("rows", tservice.getSelect(sql,title)); 
		//关闭链接
		tservice.Close();
		
		
//		System.out.println(jsonObject.fromObject(map).toString());
	    return jsonObject.fromObject(map).toString();
	}
	
	
	/**
	 * 申请明细列表
	 * @param page
	 * @param rows
	 * @param sort
	 * @param sortOrder
	 * @param searchjson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/clApplyList",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String clApplyList(String page,String rows,String sort,String sortOrder,String searchjson) throws IOException{
		System.out.println("--进入(checkHistory/clApplyList)--");
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by cast(clapplylistid as signed) ";
		}
		
		
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
		String where="where 1=1 ";
		
		String clapplyid = json.getString("clapplyid");
		
		where += clapplyid != null && !clapplyid.trim().equals("")?" and t.clapplyid = '"+clapplyid+"'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from ( " 
				+ " select s.CLNAME,s.CLCODE,t.* from stor_clapplylist t "
				+ " left join stor_cl s on s.STORCLID = t.STORCLID ) t "
				+ where +" "+order+"" ;
		String title[] = {"clname","clcode","clapplylistid","clapplyid","storclid","applynum","memo"};
		/**
		 * 数据总条数
		 */
		String sqlt =" select count(*) total from ( select s.CLNAME,s.CLCODE,t.* from stor_clapplylist t "
				+ " left join stor_cl s on s.STORCLID = t.STORCLID ) t  "
				    +" "+where;
		
		/**
		 * map中为table的数据组装
		 */
		Map map=new HashMap();
		//total 总行数
		String titl[] = {"total"};
		map.put("total", tservice.getSelect(sqlt,titl).get(0).get("total"));
		
		//rows 格式为[{id:1,name:2},{id:1,name:2}...]
		map.put("rows", tservice.getSelect(sql,title)); 
		//关闭链接
		tservice.Close();
		
		
//		System.out.println(jsonObject.fromObject(map).toString());
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 查看表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/infoForm")
	@ResponseBody
	public ModelAndView infoForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {
			String sql = "select u.USERNAME as applyusername,ua.USERNAME as checkusername,i.TASKCODE,i.TASKTITLE,t.* \n" +
					"from stor_clapply t\n" +
					"left join basic_user u on u.USERID = t.APPLYUSER\n" +
					"left join basic_user ua on ua.USERID = t.CHECKUSER\n" +
					"left join taskinfo i on i.TASKID = t.TASKID\n" +
					"where t.CLAPPLYID = '"+tablejson+"'";
			String title[] = {"clapplyid","cusid","applycode","applyuser","applytime","applyreason","checkuser",
					"checktime","applystate","jjreason","memo","applyusername","taskcode","tasktitle","checkusername","applytype"};
			List<Map<String, String>> list = tservice.getSelect(sql, title);
			if(list != null && list.size() > 0) {
				modelAndView.addObject("map", list.get(0));
			}
			modelAndView.setViewName("materialManage/checkHistory/checkHistoryInfo");
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
		String code = "checkHistory";
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
			modelAndView.setViewName("materialManage/checkHistory/list");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;

	}
	
}
