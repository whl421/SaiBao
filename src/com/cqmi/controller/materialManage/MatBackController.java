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
@RequestMapping("/matBack")
public class MatBackController extends BasicAction{
	
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
		System.out.println("--进入(matBack/list)--");
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by APPLYTIME desc";
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
		String where="where 1=1 and cusid = '"+cusid+"' and APPLYUSER = '"+userid+"' and APPLYTYPE = '1' ";
		
		String applycode = json.getString("applycode");
		String applytime = json.getString("applytime");
		String applystate = json.getString("applystate");
		String taskcode = json.getString("taskcode");
		String tasktitle = json.getString("tasktitle");
		
		where += applycode != null && !applycode.trim().equals("")?" and t.applycode like '%"+applycode+"%'":"";
		where += applytime != null && !applytime.trim().equals("")?" and applytime like '"+applytime+"%'":"";
		where += applystate != null && !applystate.trim().equals("")?" and applystate = '"+applystate+"'":"";
		where += taskcode != null && !taskcode.trim().equals("")?" and t.taskcode like '%"+taskcode+"%'":"";
		where += tasktitle != null && !tasktitle.trim().equals("")?" and t.tasktitle like '%"+tasktitle+"%'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from ( " 
				+ " select u.USERNAME as applyusername,i.TASKCODE,i.TASKTITLE,t.* \n" 
				+ " from stor_clapply t\n" 
				+ " left join basic_user u on u.USERID = t.APPLYUSER\n" 
				+ " left join taskinfo i on i.TASKID = t.TASKID ) t "
				+ where +" "+order+" limit "+num+","+rows ;
		String title[] = {"clapplyid","cusid","applycode","applyuser","applytime","applyreason","checkuser",
				"checktime","applystate","jjreason","memo","applyusername","taskcode","tasktitle","thflag","pclapplyid"};
		/**
		 * 数据总条数
		 */
		String sqlt =" select count(*) total from ( select u.USERNAME as applyusername,i.TASKCODE,i.TASKTITLE,t.* \n" 
				+ " from stor_clapply t\n" 
				+ " left join basic_user u on u.USERID = t.APPLYUSER\n" 
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
	 * 材料退回
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value="/save",produces="application/json; charset=utf-8")
	@ResponseBody
	public String save(String tablejson, String sublist) throws IOException{
		
		System.out.println("--进入(matBack/save)--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		int r;
		Map map = new HashMap();
		//开起事物
		tservice.OpenTransaction();
		//获取当前时间
		String nowTime = util.getNowTime();
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String userid = user.getUserid();	
		//tablejson 转为json
		JSONObject json=jsonObject.fromObject(tablejson);
		
		String cusid = user.getCusid();
		String taskid = json.optString("taskid");
		String applyreason = json.optString("applyreason");
		String clapplyid = json.optString("clapplyid");
		String applycode = getCode();
		String applystate = "1";
		String applytype = "2";
		String thflag = "0";
		//新增主表信息
		String sql2 = "insert into STOR_CLAPPLY(CUSID,TASKID,APPLYCODE,APPLYUSER," +
				" APPLYTIME,APPLYREASON,CHECKUSER," +
				" CHECKTIME,APPLYSTATE,JJREASON," +
				" MEMO,APPLYTYPE,PCLAPPLYID,THFLAG) " + 
				" value (?,?,?,?," +
				" ?,?,?," +
				" ?,?,?," +
				" ?,?,?,?) ";
		List<String> para2 = new ArrayList<String>();
		para2.add(cusid);para2.add(taskid);para2.add(applycode);para2.add(userid);
		para2.add(nowTime);para2.add(applyreason);para2.add("");
		para2.add("");para2.add(applystate);para2.add("");
		para2.add("");para2.add(applytype);para2.add(clapplyid);para2.add(thflag);
		r = tservice.InsertSQL2(sql2, para2);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		//获取刚插入的ID
		String new_clapplyid = "";
		String title[] = {"id"};
		List<Map<String, String>> alist = tservice.getSelect("select LAST_INSERT_ID() as id",title);
		if(alist != null && alist.size()>0) {
			new_clapplyid = alist.get(0).get("id");
		} else {
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		//新增明细信息
		JsonArray array = new Gson().fromJson(sublist, JsonArray.class);
		//新增子表明细信息   循环明细信息
		for (JsonElement ele : array) {
			JsonObject tstr = ele.getAsJsonObject();
			System.out.println("子表数据"+tstr.toString());
			Map tmap = new Gson().fromJson(ele,Map.class);
			String storclid = (String)tmap.get("storclid");
			String applynum = (String)tmap.get("backnum");
			String memo = (String)tmap.get("memo");
			
			if(applynum != null && !"".equals(applynum) && !"0".equals(applynum) && !"null".equals(applynum)) {
				String lsql = "insert into STOR_CLAPPLYLIST(CLAPPLYID,STORCLID,APPLYNUM,MEMO) " + 
						" value (?,?,?,?) ";
				List<String> lpara = new ArrayList<String>();
				lpara.add(new_clapplyid);lpara.add(storclid);lpara.add(applynum);lpara.add(memo);
				r = tservice.InsertSQL2(lsql, lpara);
				if(r == 0){
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return jsonObject.fromObject(map).toString();
				}
			}
		}
		//更新申请单退回标记
		List<String> para3 = new ArrayList<String>();
		para3.add(clapplyid);
		r = tservice.UpdateSQL2("update STOR_CLAPPLY set THFLAG = '1' where CLAPPLYID = ?",para3);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		tservice.commitExe_close();
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
		System.out.println("--进入(matBack/clApplyList)--");
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
				+ " select '' as backnum,s.CLNAME,s.CLCODE,t.* from stor_clapplylist t "
				+ " left join stor_cl s on s.STORCLID = t.STORCLID ) t "
				+ where +" "+order+"" ;
		String title[] = {"clname","clcode","clapplylistid","clapplyid","storclid","applynum","memo","backnum"};
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
	 * 退回记录表单
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
			String clapplyid = "";
			//根据ID查询退回记录ID
			List<String> para = new ArrayList<String>();
			para.add(tablejson);
			List<Map<String, String>> alist = tservice.getSelect("select * from stor_clapply where PCLAPPLYID = ?", para);
			if(alist != null && alist.size() > 0 ) {
				clapplyid = alist.get(0).get("clapplyid");
			}
			String sql = "select u.USERNAME as applyusername,ua.USERNAME as checkusername,i.TASKCODE,i.TASKTITLE,t.* \n" +
					"from stor_clapply t\n" +
					"left join basic_user u on u.USERID = t.APPLYUSER\n" +
					"left join basic_user ua on ua.USERID = t.CHECKUSER\n" +
					"left join taskinfo i on i.TASKID = t.TASKID\n" +
					"where t.CLAPPLYID = '"+clapplyid+"'";
			String title[] = {"clapplyid","cusid","applycode","applyuser","applytime","applyreason","checkuser",
					"checktime","applystate","jjreason","memo","applyusername","taskcode","tasktitle","checkusername"};
			List<Map<String, String>> list = tservice.getSelect(sql, title);
			if(list != null && list.size() > 0) {
				modelAndView.addObject("map", list.get(0));
			}
			modelAndView.setViewName("materialManage/matBack/matBackInfo");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
		
	}
	
	/**
	 * 退回表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/backForm")
	@ResponseBody
	public ModelAndView backForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String sql = "select u.USERNAME as applyusername,ua.USERNAME as checkusername,i.TASKCODE,i.TASKTITLE,t.* \n" +
					"from stor_clapply t\n" +
					"left join basic_user u on u.USERID = t.APPLYUSER\n" +
					"left join basic_user ua on ua.USERID = t.CHECKUSER\n" +
					"left join taskinfo i on i.TASKID = t.TASKID\n" +
					"where t.CLAPPLYID = '"+tablejson+"'";
			String title[] = {"clapplyid","cusid","applycode","applyuser","applytime","applyreason","checkuser",
					"checktime","applystate","jjreason","memo","applyusername","taskcode","tasktitle","checkusername","taskid"};
			List<Map<String, String>> list = tservice.getSelect(sql, title);
			if(list != null && list.size() > 0) {
				list.get(0).put("backusername", user.getUsername());
				list.get(0).put("backtime", util.getNowTime());
				modelAndView.addObject("map", list.get(0));
			}
			modelAndView.setViewName("materialManage/matBack/matBackAdd");
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
		String code = "matBack";
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
			modelAndView.setViewName("materialManage/matBack/list");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;

	}
	
	/**
	 * 获取编号
	 * @return
	 */
	public String getCode() {
		String result = "";
		String nowDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()); 
		String sql = "";
		String code = nowDate+"001";
		BeanService_Transaction tservice = new BeanService_Transaction();
		try {
			sql = "select ifnull(max(t.APPLYCODE)+1,'"+code+"') CODE from STOR_CLAPPLY t where t.APPLYCODE like '"+nowDate+"%'";
			List<Map<String, String>> list = tservice.getSelect(sql);
			if(list != null && list.size() > 0) {
				result = list.get(0).get("code");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return result;
	}
}
