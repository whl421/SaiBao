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
@RequestMapping("/matLy")
public class matLyController extends BasicAction{
	
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
		System.out.println("--进入(matLy/list)--");
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
		String where="where 1=1 and cusid = '"+cusid+"' ";
		
		String applytype = json.getString("applytype");
		String applycode = json.getString("applycode");
		String applyusername = json.getString("applyusername");
		String applytime = json.getString("applytime");
		String applystate = json.getString("applystate");
		String checkusername = json.getString("checkusername");
		String checktime = json.getString("checktime");
		String taskcode = json.getString("taskcode");
		String tasktitle = json.getString("tasktitle");
		String clcode = json.getString("clcode");
		String clname = json.getString("clname");
		
		where += applytype != null && !applytype.trim().equals("")?" and applytype = '"+applytype+"'":"";
		where += applycode != null && !applycode.trim().equals("")?" and t.applycode like '%"+applycode+"%'":"";
		where += applyusername != null && !applyusername.trim().equals("")?" and applyusername like '%"+applyusername+"%'":"";
		where += applytime != null && !applytime.trim().equals("")?" and applytime like '"+applytime+"%'":"";
		where += applystate != null && !applystate.trim().equals("")?" and applystate = '"+applystate+"'":"";
		where += checkusername != null && !checkusername.trim().equals("")?" and checkusername like '%"+checkusername+"%'":"";
		where += checktime != null && !checktime.trim().equals("")?" and checktime like '"+checktime+"%'":"";
		where += taskcode != null && !taskcode.trim().equals("")?" and t.taskcode like '%"+taskcode+"%'":"";
		where += tasktitle != null && !tasktitle.trim().equals("")?" and t.tasktitle like '%"+tasktitle+"%'":"";
		where += clcode != null && !clcode.trim().equals("")?" and t.clcode like '%"+clcode+"%'":"";
		where += clname != null && !clname.trim().equals("")?" and t.clname like '%"+clname+"%'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from ( " 
				+ "select c.CLCODE,c.CLNAME,a.*,i.TASKCODE,i.TASKTITLE, " 
				+ " t.CLAPPLYLISTID,t.STORCLID,t.APPLYNUM,t.MEMO as applymemo," 
				+ " u.USERNAME as applyusername,ua.USERNAME as checkusername "
				+ " from stor_clapplylist t "
				+ " left join stor_cl c on c.STORCLID = t.STORCLID "
				+ " left join stor_clapply a on a.CLAPPLYID = t.CLAPPLYID "
				+ " left join taskinfo i on i.TASKID = a.TASKID " 
				+ " left join basic_user u on u.USERID = a.APPLYUSER\n" 
				+ " left join basic_user ua on ua.USERID = a.CHECKUSER\n" 
				+ ") t "
				+ where +" "+order+" limit "+num+","+rows ;
		String title[] = {"clapplyid","cusid","applycode","applyuser","applytime","applyreason","checkuser",
				"checktime","applystate","jjreason","memo","applyusername","taskcode","tasktitle",
				"checkusername","taskid","applytype","pclapplyid","clcode","clname","applymemo",
				"clapplylistid","storclid","applynum"};
		/**
		 * 数据总条数
		 */
		String sqlt =" select count(*) total from ( select c.CLCODE,c.CLNAME,a.*,i.TASKCODE,i.TASKTITLE, " 
				+ " t.CLAPPLYLISTID,t.STORCLID,t.APPLYNUM,t.MEMO as applymemo," 
				+ " u.USERNAME as applyusername,ua.USERNAME as checkusername "
				+ " from stor_clapplylist t "
				+ " left join stor_cl c on c.STORCLID = t.STORCLID "
				+ " left join stor_clapply a on a.CLAPPLYID = t.CLAPPLYID "
				+ " left join taskinfo i on i.TASKID = a.TASKID " 
				+ " left join basic_user u on u.USERID = a.APPLYUSER\n" 
				+ " left join basic_user ua on ua.USERID = a.CHECKUSER\n ) t  "
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
		String code = "matLy";
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
			modelAndView.setViewName("materialManage/matLy/list");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;

	}
	
}
