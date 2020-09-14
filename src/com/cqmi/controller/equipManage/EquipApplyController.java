package com.cqmi.controller.equipManage;

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
@RequestMapping("/equipApply")
public class EquipApplyController extends BasicAction{
	
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
	public String list(String page,String rows,String sort,String sortOrder,String searchjson) throws IOException{
		System.out.println("--进入(equipApply/list)--");
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by APPLYTIME desc ";
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
		
		String equipapolycode = json.getString("equipapolycode");
		String equipcode = json.getString("equipcode");
		String equipname = json.getString("equipname");
		String applyusername = json.getString("applyusername");
		String applydate = json.getString("applydate");
		String usetime = json.getString("usetime");
		String applystate = json.getString("applystate");
		if(applydate != null && !"".equals(applydate)) {
			String applySdate = applydate.split("--")[0].trim()+" 00:00:00";
			String applyEdate = applydate.split("--")[1].trim()+" 23:59:59";
			where += " and APPLYTIME >= '"+applySdate+"' and APPLYTIME <= '"+applyEdate+"'";
		}
		if(usetime != null && !"".equals(usetime)) {
			String sdate = usetime.split("--")[0].trim();
			String edate = usetime.split("--")[1].trim();
			where += " and ((STARTTIME >= '"+sdate+"' and STARTTIME <= '"+edate+"') or (ENDTIME >= '"+sdate+"' and ENDTIME <= '"+edate+"'))";
//			where += " and ENDTIME >= '"+sdate+"' and ENDTIME <= '"+edate+"'";
		}
		
		where += equipapolycode != null && !equipapolycode.trim().equals("")?" and t.equipapolycode like '%"+equipapolycode+"%'":"";
		where += equipcode != null && !equipcode.trim().equals("")?" and t.equipcode like '%"+equipcode+"%'":"";
		where += equipname != null && !equipname.trim().equals("")?" and equipname like '%"+equipname+"%'":"";
		where += applyusername != null && !applyusername.trim().equals("")?" and APPLYUSERNAME like '%"+applyusername+"%'":"";
		where += applystate != null && !applystate.trim().equals("")?" and applystate = '"+applystate+"'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from (select e.EQUIPCODE,e.EQUIPNAME,i.TASKCODE,i.TASKTITLE,\n" +
				"u.USERNAME as APPLYUSERNAME,ua.USERNAME as checkname,t.* \n" +
				"from basic_equipapply t\n" +
				"left join basic_equip e on e.EQUIPID = t.EQUIPID\n" +
				"left join taskinfo i on i.TASKID = t.TASKID\n" +
				"left join basic_user u on u.USERID = t.APPLYUSER\n" +
				"left join basic_user ua on ua.USERID = t.CHECKUSER ) t "
				+ " "+where+" "+order+" limit "+num+","+rows ;
		/**
		 * 数据总条数
		 */
		String sqlt =" select count(*) total from (select e.EQUIPCODE,e.EQUIPNAME,i.TASKCODE,i.TASKTITLE,\n" +
				"u.USERNAME as APPLYUSERNAME,ua.USERNAME as checkname,t.* \n" +
				"from basic_equipapply t\n" +
				"left join basic_equip e on e.EQUIPID = t.EQUIPID\n" +
				"left join taskinfo i on i.TASKID = t.TASKID\n" +
				"left join basic_user u on u.USERID = t.APPLYUSER\n" +
				"left join basic_user ua on ua.USERID = t.CHECKUSER ) t  "
				    +" "+where;
		String title[] = {"equipcode","equipname","taskcode","tasktitle","applyusername","checkname","equipapplyid",
				"cusid","equipapolycode","equipid","taskid","starttime","endtime","applyuser","applytime",
				"checkuser","checktime","applystate","checkmemo","memo"};
		/**
		 * map中为table的数据组装
		 */
		Map map=new HashMap();
		//total 总行数
		String titl[] = {"total"};
		map.put("total", tservice.getSelect(sqlt, titl).get(0).get("total"));
		
		//rows 格式为[{id:1,name:2},{id:1,name:2}...]
		map.put("rows", tservice.getSelect(sql,title)); 
		//关闭链接
		tservice.Close();
		
		
//		System.out.println(jsonObject.fromObject(map).toString());
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 预约数据 
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/save",produces="application/json; charset=utf-8")
	@ResponseBody
	public String save(String tablejson) throws IOException{
		System.out.println("--进入(equipApply/save)--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		Map map = new HashMap();
		//开起事物
		tservice.OpenTransaction();
		//获取当前时间
		String datetime = util.getNowTime();
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
				
		//tablejson 转为json
		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();
		
		String cusid = user.getCusid();
		String equipapolycode = getCode();
		String equipid = json.optString("equipid");
		String taskid = json.optString("taskid");
		String starttime = json.optString("starttime");
		String endtime = json.optString("endtime");
		String applyuser = user.getUserid();
		String applytime = util.getNowTime();
		String applystate = "1";
		String memo = json.optString("memo");
		//查询该设备已经预约的时间段
		String sql1 = "select u.USERNAME,t.* from basic_equipapply t " +
				"left join basic_user u on u.USERID = t.APPLYUSER\n" +
				"where t.APPLYSTATE != '3'\n" +
				"and t.EQUIPID = '"+equipid+"'\n" +
				"and t.STARTTIME < '"+endtime+"'\n" +
				"and t.ENDTIME > '"+starttime+"'";
		List<Map<String, String>> list = tservice.getSelect(sql1);
		if(list != null && list.size() > 0) {
			String username = list.get(0).get("username");
			map.put("info", "0");
			map.put("text", "该时间段已经被"+username+"申请占用，请修改预约时间");
			return jsonObject.fromObject(map).toString();
		}
		/**
		 * 防注入使用
		 */
		String sql = " insert into BASIC_EQUIPAPPLY(CUSID,EQUIPAPOLYCODE,EQUIPID," +
				" TASKID,STARTTIME,ENDTIME," +
				" APPLYUSER,APPLYTIME,CHECKUSER," +
				" CHECKTIME,APPLYSTATE,CHECKMEMO," +
				" MEMO) " + 
				" value (?,?,?," +
				" ?,?,?," +
				" ?,?,?," +
				" ?,?,?," +
				" ?) ";

		//注意参数顺序
		lt.add(cusid);lt.add(equipapolycode);lt.add(equipid);
		lt.add(taskid);lt.add(starttime);lt.add(endtime);
		lt.add(applyuser);lt.add(applytime);lt.add("");
		lt.add("");lt.add(applystate);lt.add("");
		lt.add(memo);
		
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		int r;
		
		r = tservice.InsertSQL2(sql, lt);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			
			tservice.rollbackExe_close();
		}
		tservice.commitExe_close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 删除数据
	 * value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/delete",produces="application/json; charset=utf-8")
	@ResponseBody
	public String delete(String tablejson) throws UnsupportedEncodingException{
		System.out.println("删除数据id有"+tablejson);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice = new BeanService_Transaction();
		Map map = new HashMap();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String userid = user.getUserid();
			//开起事物
			tservice.OpenTransaction();
			
			//判断当前登陆用户和状态
			String sql1 = "select * from basic_equipapply t where t.EQUIPAPPLYID in("+tablejson.replace("\"", "'").replace("[", "").replace("]", "")+")";
			List<Map<String, String>> list = tservice.getSelect(sql1);
			if(list != null && list.size() > 0) {
				for(int i=0; i<list.size(); i++) {
					String applyuser = list.get(i).get("applyuser");
					String applystate = list.get(i).get("applystate");
					if(!userid.equals(applyuser)) {
						map.put("info", "0");
						map.put("textinfo", "只能删除自己提交的申请记录");
						return new JSONObject().fromObject(map).toString();
					}
					if(!"1".equals(applystate)) {
						map.put("info", "0");
						map.put("textinfo", "只能删除状态为待审核的申请记录");
						return new JSONObject().fromObject(map).toString();
					}
				}
			}
			String sql = "delete from basic_equipapply where EQUIPAPPLYID in ("+tablejson.replace("\"", "'").replace("[", "").replace("]", "")+")";
			int r = tservice.DeleteSQL2(sql);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
			
				tservice.rollbackExe_close();
			}else{
				tservice.commitExe_close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	    return new JSONObject().fromObject(map).toString();
		
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
			
			String sql = "select e.EQUIPCODE,e.EQUIPNAME,i.TASKCODE,i.TASKTITLE,\n" +
					"u.USERNAME as APPLYUSERNAME,ua.USERNAME as checkname,t.* \n" +
					"from basic_equipapply t\n" +
					"left join basic_equip e on e.EQUIPID = t.EQUIPID\n" +
					"left join taskinfo i on i.TASKID = t.TASKID\n" +
					"left join basic_user u on u.USERID = t.APPLYUSER\n" +
					"left join basic_user ua on ua.USERID = t.CHECKUSER\n" +
					"where t.EQUIPAPPLYID = '"+tablejson+"'";
			String title[] = {"equipcode","equipname","taskcode","tasktitle","applyusername","checkname","equipapplyid",
					"cusid","equipapolycode","equipid","taskid","starttime","endtime","applyuser","applytime",
					"checkuser","checktime","applystate","checkmemo","memo"};
			List<Map<String, String>> lmap = tservice.getSelect(sql,title);
			if(lmap != null && lmap.size() > 0) {
				modelAndView.addObject("map", lmap.get(0));
			}
			modelAndView.setViewName("equipApply/equipApplyInfo");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
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
			HashMap map = new HashMap();
			map.put("applyuser", user.getUserid());
			map.put("applyusername", user.getUsername());
			map.put("applytime", util.getNowTime());
			modelAndView.addObject("map", map);
			modelAndView.setViewName("equipApply/equipApplyAdd");
			
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
		String code = "equipApply";
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
			modelAndView.setViewName("equipApply/list");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;

	}
	
	/**
	 * 获取申请编号
	 * @return
	 */
	public String getCode() {
		String result = "";
		String nowDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()); 
		String sql = "";
		String code = nowDate+"01";
		BeanService_Transaction tservice = new BeanService_Transaction();
		try {
			sql = "select ifnull(max(t.EQUIPAPOLYCODE)+1,'"+code+"') CODE from BASIC_EQUIPAPPLY t where t.EQUIPAPOLYCODE like '"+nowDate+"%'";
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
	
	/**
	 * 设备信息
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/equipListForm")
	public ModelAndView equipListForm() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("common/selectEquipList");
		
		return modelAndView;

	}
	/**
	 * 任务信息
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/taskInfoForm")
	public ModelAndView taskInfoForm() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("common/selectTaskInfo");
		
		return modelAndView;

	}
	/**
	 * 设备预约审核
	 */
	/**
	 * 进入首页
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkListForm")
	public ModelAndView checkListForm() throws Exception {
		/**
		 * 权限判定
		 * lstaskfp  角色菜单中的菜单编号
		 */
		String code = "equipCheck";
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
			modelAndView.setViewName("equipApply/checklist");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;

	}
	
	/**
	 * 批量通过
	 * value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/batchpass",produces="application/json; charset=utf-8")
	@ResponseBody
	public String batchpass(String tablejson) throws UnsupportedEncodingException{
		System.out.println("批量通过数据id有"+tablejson);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice = new BeanService_Transaction();
		Map map = new HashMap();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String userid = user.getUserid();
			String nowTime = util.getNowTime();
			//开起事物
			tservice.OpenTransaction();
			
			String sql = "update basic_equipapply set APPLYSTATE = '2',CHECKUSER='"+userid+"',CHECKTIME='"+nowTime+"',CHECKMEMO='通过'" +
					" where EQUIPAPPLYID in ("+tablejson.replace("\"", "'").replace("[", "").replace("]", "")+")";
			int r = tservice.DeleteSQL2(sql);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
			
				tservice.rollbackExe_close();
			}else{
				tservice.commitExe_close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	    return new JSONObject().fromObject(map).toString();
		
	}
	
	/**
	 * 批量驳回
	 * value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/dismissal",produces="application/json; charset=utf-8")
	@ResponseBody
	public String dismissal(String tablejson) throws UnsupportedEncodingException{
		System.out.println("批量驳回数据id有"+tablejson);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice = new BeanService_Transaction();
		Map map = new HashMap();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String userid = user.getUserid();
			String nowTime = util.getNowTime();
			//开起事物
			tservice.OpenTransaction();
			
			String sql = "update basic_equipapply set APPLYSTATE = '3',CHECKUSER='"+userid+"',CHECKTIME='"+nowTime+"',CHECKMEMO='驳回' " +
					" where EQUIPAPPLYID in ("+tablejson.replace("\"", "'").replace("[", "").replace("]", "")+")";
			int r = tservice.DeleteSQL2(sql);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
			
				tservice.rollbackExe_close();
			}else{
				tservice.commitExe_close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	    return new JSONObject().fromObject(map).toString();
		
	}
	
	/**
	 * 审核
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/check",produces="application/json; charset=utf-8")
	@ResponseBody
	public String check(String tablejson) throws IOException{
		System.out.println("--进入(equipApply/check)--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		//获取当前时间
		String nowTime = util.getNowTime();
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		
		//tablejson 转为json
		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();
				
		/**
		 * 防注入使用
		 */
		String sql = "update BASIC_EQUIPAPPLY set APPLYSTATE = ?,CHECKUSER=?,CHECKTIME=?,CHECKMEMO=? where EQUIPAPPLYID = ?";

		String applystate = json.optString("applystate");
		String userid = user.getUserid();
		String checkmemo = json.optString("checkmemo");
		String equipapplyid = json.optString("equipapplyid");
		
		//注意参数顺序
		lt.add(applystate);lt.add(userid);lt.add(nowTime);
		lt.add(checkmemo);lt.add(equipapplyid);
		
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		int r;
		Map map = new HashMap();
		r = tservice.UpdateSQL2(sql, lt);
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
	 * 审核表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkForm")
	@ResponseBody
	public ModelAndView checkForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String sql = "select e.EQUIPCODE,e.EQUIPNAME,i.TASKCODE,i.TASKTITLE,\n" +
					"u.USERNAME as APPLYUSERNAME,ua.USERNAME as checkname,t.* \n" +
					"from basic_equipapply t\n" +
					"left join basic_equip e on e.EQUIPID = t.EQUIPID\n" +
					"left join taskinfo i on i.TASKID = t.TASKID\n" +
					"left join basic_user u on u.USERID = t.APPLYUSER\n" +
					"left join basic_user ua on ua.USERID = t.CHECKUSER\n" +
					"where t.EQUIPAPPLYID = '"+tablejson+"'";
			String title[] = {"equipcode","equipname","taskcode","tasktitle","applyusername","checkname","equipapplyid",
					"cusid","equipapolycode","equipid","taskid","starttime","endtime","applyuser","applytime",
					"checkuser","checktime","applystate","checkmemo","memo"};
			List<Map<String, String>> lmap = tservice.getSelect(sql,title);
			if(lmap != null && lmap.size() > 0) {
				lmap.get(0).put("checkname", user.getUsername());
				lmap.get(0).put("checktime", util.getNowTime());
				modelAndView.addObject("map", lmap.get(0));
			}
			modelAndView.setViewName("equipApply/equipApplyCheck");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
		
	}
}
