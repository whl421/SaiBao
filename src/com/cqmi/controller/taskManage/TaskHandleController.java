package com.cqmi.controller.taskManage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cqmi.controller.login.LoginController;
import com.cqmi.controller.login.bean.User;
import com.cqmi.dao.util.Util;
import com.cqmi.db.service.BeanService_Transaction;


@Controller
@RequestMapping("/taskCl")
public class TaskHandleController {
	
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
		String userid = user.getUserid();
				
		/**
		 * 查询条件
		 */
		String where="where 1=1 ";
		String taskcode = json.getString("taskcode");
		String tasktitle = json.getString("tasktitle");
		where += taskcode != null && !taskcode.trim().equals("")?" and t.taskcode like '%"+taskcode+"%'":"";
		where += tasktitle != null && !tasktitle.trim().equals("")?" and t.tasktitle like '%"+tasktitle+"%'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from (select FIND_IN_SET('"+userid+"',t.EXECUTEUSER) EXEC,FIND_IN_SET('"+userid+"',t.XZUSERID) XZ, " +
				" i.TASKCODE,i.TASKTITLE,o.ORDERCODE,o.ORDERNAME,o.ORDERUSER,i.CREATETIME,u.username upexecusername,t.*  " +
				" from tasklist t " +
				" left join taskinfo i on i.TASKID = t.TASKID " +
				" left join orderform o on o.ORDERID = i.ORDERID " +
				" left join basic_user u on u.userid = t.upexecuser " +
				" where t.BJDSTATE in('1','2','5')  " +
				" and (t.EXECUTEUSER = '"+userid+"' or FIND_IN_SET('"+userid+"',t.XZUSERID)) ) t "
				  +" "+where+" "+order+" limit "+num+","+rows ;
		
		String title[] = {"exec","xz","taskcode","tasktitle","ordercode","ordername","orderuser","createtime","tasklistid","taskid",
				"serialno","upexecuser","upmemo","upyjinfo","wcmemo","yjinfo","bjdstate","rejectreason","executeuser",
				"exectuetime","xzuserid","xzuser","xzplain","upexecusername"};
		/**
		 * 数据总条数
		 */
		String sqlt="select count(1) total from (select FIND_IN_SET('"+userid+"',t.EXECUTEUSER) EXEC,FIND_IN_SET('"+userid+"',t.XZUSERID) XZ, " +
				" i.TASKCODE,i.TASKTITLE,o.ORDERCODE,o.ORDERNAME,o.ORDERUSER,i.CREATETIME,t.*  " +
				" from tasklist t " +
				" left join taskinfo i on i.TASKID = t.TASKID " +
				" left join orderform o on o.ORDERID = i.ORDERID " +
				" left join basic_user u on u.userid = t.upexecuser " +
				" where t.BJDSTATE in('1','2','5')  " +
				" and (t.EXECUTEUSER = '"+userid+"' or FIND_IN_SET('"+userid+"',t.XZUSERID)) ) t "+where;
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
		String code = "taskHandle";
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
			modelAndView.setViewName("taskcl/list");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;
	}
	
	/**
	 *  接收
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/taskAccept",produces="application/json; charset=utf-8")
	@ResponseBody
	public String accept(String tablejson) throws IOException{
		System.out.println("--进入(taskmanage/accept)--"+tablejson);
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
			String nowTime = util.getNowTime();
			/**
			 * tablejson 数据
			 */
			JSONObject json = jsonObject.fromObject(tablejson);
			List<String> lt = new ArrayList<String>();
			String sql = "update tasklist t set t.BJDSTATE = ?, t.REJECTREASON = ?, EXECTUETIME = ? where t.TASKLISTID = ? ";
			String bjdstate = json.optString("bjdstate");
			String rejectmemo = json.optString("rejectmemo");
			String tasklistid = json.optString("tasklistid");
			String serialno = json.optString("serialno");
			String orderid = json.optString("orderid");
			String prevlistid = json.optString("prevlistid");
			
			//注意参数顺序
			lt.add(bjdstate);
			lt.add(rejectmemo);
			lt.add(nowTime);
			lt.add(tasklistid);
			
			int r=tservice.UpdateSQL2(sql, lt);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
				return jsonObject.fromObject(map).toString();
			} 
			String liststate = "";
			if("2".equals(bjdstate)) {
				liststate = "6";
			} else {
				liststate = "5";
			}
			if("1".equals(serialno)) {
				//更新委托单记录状态
				String sql1 = "update orderform t set t.LISTSTATE = ? where t.ORDERID = ?";
				
				List<String> para = new ArrayList<String>();
				para.add(liststate);para.add(orderid);
				r = tservice.UpdateSQL2(sql1, para);
				if(r == 0){
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return jsonObject.fromObject(map).toString();
				}
			} else {
				//更新上一阶段的状态
				String sql2 = "update tasklist t set t.BJDSTATE = ? where t.TASKLISTID = ?";
				List<String> para1 = new ArrayList<String>();
				para1.add(liststate);para1.add(prevlistid);
				r = tservice.UpdateSQL2(sql2, para1);
				if(r == 0){
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return jsonObject.fromObject(map).toString();
				}
			}
			tservice.commitExe_close();
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
	 * 接收表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/acceptForm")
	public ModelAndView acceptForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jsonObject = new JSONObject();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String userid = user.getUserid();
			String orderid = "";
			String xssql = "select FIND_IN_SET('"+userid+"',t.EXECUTEUSER) EXEC,FIND_IN_SET('"+userid+"',t.XZUSERID) XZ,\n" +
					"i.TASKCODE,i.TASKTITLE,i.taskmemo,i.orderid,o.ORDERCODE,o.ORDERNAME,o.ORDERUSER,i.CREATETIME,t.* \n" +
					"from tasklist t\n" +
					"left join taskinfo i on i.TASKID = t.TASKID\n" +
					"left join orderform o on o.ORDERID = i.ORDERID\n" +
					"where t.TASKLISTID = ? ";
			List<String> lt = new ArrayList<String>();
			lt.add(tablejson);
			String title[] = {"exec","xz","taskcode","tasktitle","taskmemo","orderid","ordercode","ordername","orderuser","createtime","tasklistid","taskid",
					"serialno","upexecuser","upmemo","upyjinfo","wcmemo","yjinfo","bjdstate","rejectreason","executeuser",
					"exectuetime","xzuserid","xzuser","xzplain","prevlistid"};
			List<Map<String, String>> xsmap = tservice.getSelect(xssql, lt, title);
			
			if(xsmap.size() > 0 && xsmap != null) {
				orderid = xsmap.get(0).get("orderid");
				modelAndView.addObject("map", xsmap.get(0));
			} 
			//查询附件信息
			String sel_fjsql = "select * from attach t where t.CUSID = '"+cusid+"' and t.fid = '"+orderid+"' " +
					" and t.tableid = 'orderform' and t.tablecolid = 'orderid'";
			List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql);
			if(fjmap != null && fjmap.size() > 0) {
				modelAndView.addObject("fjmap", fjmap);
			}
			modelAndView.setViewName("/taskcl/taskAccept");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
	}
	
	/**
	 * 任务处理表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/handleForm")
	public ModelAndView handleForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jsonObject = new JSONObject();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String userid = user.getUserid();
			String departCode = "";
			String orderid = "";
			String xssql = "select FIND_IN_SET('"+userid+"',t.EXECUTEUSER) EXEC,FIND_IN_SET('"+userid+"',t.XZUSERID) XZ,\n" +
					"i.TASKCODE,i.TASKTITLE,i.taskmemo,i.orderid,o.ORDERCODE,o.ORDERNAME,o.ORDERUSER,i.CREATETIME, " +
					"o.departcode,t.* \n" +
					"from tasklist t\n" +
					"left join taskinfo i on i.TASKID = t.TASKID\n" +
					"left join orderform o on o.ORDERID = i.ORDERID\n" +
					"where t.TASKLISTID = ? ";
			List<String> lt = new ArrayList<String>();
			lt.add(tablejson);
			String title[] = {"exec","xz","taskcode","tasktitle","taskmemo","orderid","ordercode","ordername","orderuser","createtime","tasklistid","taskid",
					"serialno","upexecuser","upmemo","upyjinfo","wcmemo","yjinfo","bjdstate","rejectreason","executeuser",
					"exectuetime","xzuserid","xzuser","xzplain","departcode"};
			List<Map<String, String>> xsmap = tservice.getSelect(xssql, lt, title);
			
			if(xsmap.size() > 0 && xsmap != null) {
				modelAndView.addObject("map", xsmap.get(0));
				departCode = xsmap.get(0).get("departcode");
				orderid = xsmap.get(0).get("orderid");
			} 
			//查询部门信息
			List<Map<String, String>> lmap = util.getUserList(tservice, cusid, departCode);
			modelAndView.addObject("userList", lmap);
			//查询附件信息
			String sel_fjsql = "select * from attach t where t.CUSID = '"+cusid+"' and t.fid = '"+orderid+"' " +
					" and t.tableid = 'orderform' and t.tablecolid = 'orderid'";
			List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql);
			if(fjmap != null && fjmap.size() > 0) {
				modelAndView.addObject("fjmap", fjmap);
			}
			modelAndView.setViewName("/taskcl/taskHandle");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
	}
	
	/**
	 *  任务处理
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/taskHandle",produces="application/json; charset=utf-8")
	@ResponseBody
	public String taskHandle(String tablejson) throws IOException{
		System.out.println("--进入(taskcl/taskHandle)--"+tablejson);
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
			String nowTime = util.getNowTime();
			/**
			 * tablejson 数据
			 */
			JSONObject json = jsonObject.fromObject(tablejson);
			
			String tasklistid = json.optString("tasklistid");
			String taskid = json.optString("taskid");
			String serialno = json.optString("serialno");
			String wcmemo = json.optString("wcmemo");
			String nextexecuser = json.optString("nextexecuser");
			String isclose = json.optString("isclose");
			String bjdstate = json.optString("bjdstate");
			String yjinfo = json.optString("yjinfo");
			String executeuser = json.optString("executeuser");
			serialno = Integer.parseInt(serialno)+1+"";
			//更新完成情况
			List<String> lt1 = new ArrayList<String>();
			String sql1 = "update tasklist t set t.WCMEMO = ?,t.YJINFO = ? where t.TASKLISTID = ?";
			lt1.add(wcmemo);lt1.add(yjinfo);lt1.add(tasklistid);
			int r = tservice.UpdateSQL2(sql1, lt1);
			if(r == 0) {
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
				return jsonObject.fromObject(map).toString();
			}
			//判断本阶段是否完成
			if("4".equals(bjdstate)) {
				//更新本阶段完成情况
				String sql3 = "update tasklist t set t.BJDSTATE = '4' where t.TASKLISTID = ?";
				List<String> lt3 = new ArrayList<String>();
				lt3.add(tasklistid);
				r = tservice.UpdateSQL2(sql3, lt3);
				if(r == 0) {
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return jsonObject.fromObject(map).toString();
				}
			}
			//判断任务是否完成
			if("1".equals(isclose)) {	//关闭
				//更新任务主表状态
				String sql2 = "update taskinfo t set t.TASKSTATE = '2' where t.TASKID = ?";
				List<String> lt2 = new ArrayList<String>();
				lt2.add(taskid);
				r = tservice.UpdateSQL2(sql2, lt2);
				if(r == 0) {
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return jsonObject.fromObject(map).toString();
				}
			} 
			if(!"".equals(nextexecuser) && nextexecuser != null) {
				//新增下一阶段处理过程
				String lsql = " insert into tasklist(TASKID,SERIALNO,PREVLISTID," +
						"UPEXECUSER,UPMEMO,UPYJINFO," +
						"WCMEMO,YJINFO,BJDSTATE," +
						"REJECTREASON,EXECUTEUSER,EXECTUETIME," +
						"XZUSERID,XZUSER,XZPLAIN) " +
						" VALUES(" +
						" ?,?,?, " +
						" ?,?,?, " +
						" ?,?,?, " +
						" ?,?,?, " +
						" ?,?,? )";
				List<String> l_lt = new ArrayList<String>();
				l_lt.add(taskid);l_lt.add(serialno);l_lt.add(tasklistid);
				l_lt.add(executeuser);l_lt.add(wcmemo);l_lt.add(yjinfo);
				l_lt.add("");l_lt.add("");l_lt.add("1");
				l_lt.add("");l_lt.add(nextexecuser);l_lt.add("");
				l_lt.add("");l_lt.add("");l_lt.add("");
				r = tservice.UpdateSQL2(lsql, l_lt);
				if(r == 0){
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return jsonObject.fromObject(map).toString();
				} 
			}
			tservice.commitExe_close();
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
	 * 添加协作表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addxzForm")
	public ModelAndView addxzForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jsonObject = new JSONObject();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String userid = user.getUserid();
			String departCode = "";
			String orderid = "";
			String xssql = "select FIND_IN_SET('"+userid+"',t.EXECUTEUSER) EXEC,FIND_IN_SET('"+userid+"',t.XZUSERID) XZ,\n" +
					"i.TASKCODE,i.TASKTITLE,i.taskmemo,i.orderid,o.ORDERCODE,o.ORDERNAME,o.ORDERUSER,i.CREATETIME, " +
					"o.departcode,t.* \n" +
					"from tasklist t\n" +
					"left join taskinfo i on i.TASKID = t.TASKID\n" +
					"left join orderform o on o.ORDERID = i.ORDERID\n" +
					"where t.TASKLISTID = ? ";
			List<String> lt = new ArrayList<String>();
			lt.add(tablejson);
			String title[] = {"exec","xz","taskcode","tasktitle","taskmemo","orderid","ordercode","ordername","orderuser","createtime","tasklistid","taskid",
					"serialno","upexecuser","upmemo","upyjinfo","wcmemo","yjinfo","bjdstate","rejectreason","executeuser",
					"exectuetime","xzuserid","xzuser","xzplain","departcode"};
			List<Map<String, String>> xsmap = tservice.getSelect(xssql, lt, title);
			
			if(xsmap.size() > 0 && xsmap != null) {
				modelAndView.addObject("map", xsmap.get(0));
				departCode = xsmap.get(0).get("departcode");
				orderid = xsmap.get(0).get("orderid");
			} 
			
			//获取按部门分组的用户
			 Map<String, List<Map>> userMap = util.getGroupUser(tservice, cusid);
			
			modelAndView.addObject("userMap", userMap);
			
			//查询附件信息
			String sel_fjsql = "select * from attach t where t.CUSID = '"+cusid+"' and t.fid = '"+orderid+"' " +
					" and t.tableid = 'orderform' and t.tablecolid = 'orderid'";
			List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql);
			if(fjmap != null && fjmap.size() > 0) {
				modelAndView.addObject("fjmap", fjmap);
			}
			
			modelAndView.setViewName("/taskcl/taskXz");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
	}
	
	/**
	 *  任务协作
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/taskXz",produces="application/json; charset=utf-8")
	@ResponseBody
	public String taskXz(String tablejson) throws IOException{
		System.out.println("--进入(taskcl/taskXz)--"+tablejson);
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
			String nowTime = util.getNowTime();
			/**
			 * tablejson 数据
			 */
			JSONObject json = jsonObject.fromObject(tablejson);
			
			String tasklistid = json.optString("tasklistid");
			String xzuserid = json.optString("xzuserid").replaceAll("\"", "").replace("[", "").replace("]", "");;
			String xzusername = json.optString("xzusername");
			String xzplain = json.optString("xzplain");
			//更新任务表
			String sql = "update tasklist t set t.xzuserid = CONCAT(xzuserid,?), t.xzuser = CONCAT(xzuser,?), t.xzplain = ? where t.TASKLISTID = ? ";
			List<String> lt1 = new ArrayList<String>();
			lt1.add(","+xzuserid);lt1.add(","+xzusername);lt1.add(" "+xzplain);lt1.add(tasklistid);
			int r = tservice.UpdateSQL2(sql, lt1);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
				return jsonObject.fromObject(map).toString();
			}
			for(int i=0; i<xzuserid.split(",").length; i++) {
				String userId = xzuserid.split(",")[i];
				String userName = xzusername.split(",")[i];
				//新增
				String lsql = " insert into taskxz(TASKLISTID,USERID,USERNAME," +
						" XZEXPLAIN,COMINFO,COMTIME," +
						" CREATEUSER,CREATETIME,MEMO) " +
						" VALUES(" +
						" ?,?,?, " +
						" ?,?,?, " +
						" ?,?,? )";
				List<String> lt = new ArrayList<String>();
				lt.add(tasklistid);lt.add(userId);lt.add(userName);
				lt.add(xzplain);lt.add("");lt.add("");
				lt.add(userid);lt.add(nowTime);lt.add("");
				r = tservice.InsertSQL2(lsql, lt);
				if(r == 0){
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return jsonObject.fromObject(map).toString();
				} 
			}
			tservice.commitExe_close();
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
	 * 协作处理表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/xzhandleForm")
	public ModelAndView xzhandleForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jsonObject = new JSONObject();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String userid = user.getUserid();
			String departCode = "";
			String orderid = "";
			String xssql = "select FIND_IN_SET('"+userid+"',t.EXECUTEUSER) EXEC,FIND_IN_SET('"+userid+"',t.XZUSERID) XZ,\n" +
					"i.TASKCODE,i.TASKTITLE,i.taskmemo,i.orderid,o.ORDERCODE,o.ORDERNAME,o.ORDERUSER,i.CREATETIME, " +
					"o.departcode,t.* \n" +
					"from tasklist t\n" +
					"left join taskinfo i on i.TASKID = t.TASKID\n" +
					"left join orderform o on o.ORDERID = i.ORDERID\n" +
					"where t.TASKLISTID = ? ";
			List<String> lt = new ArrayList<String>();
			lt.add(tablejson);
			String title[] = {"exec","xz","taskcode","tasktitle","taskmemo","orderid","ordercode","ordername","orderuser","createtime","tasklistid","taskid",
					"serialno","upexecuser","upmemo","upyjinfo","wcmemo","yjinfo","bjdstate","rejectreason","executeuser",
					"exectuetime","xzuserid","xzuser","xzplain","departcode"};
			List<Map<String, String>> xsmap = tservice.getSelect(xssql, lt, title);
			
			if(xsmap.size() > 0 && xsmap != null) {
				modelAndView.addObject("map", xsmap.get(0));
				departCode = xsmap.get(0).get("departcode");
				orderid = xsmap.get(0).get("orderid");
			} 
			
			//获取按部门分组的用户
			 Map<String, List<Map>> userMap = util.getGroupUser(tservice, cusid);
			
			modelAndView.addObject("userMap", userMap);
			
			//查询附件信息
			String sel_fjsql = "select * from attach t where t.CUSID = '"+cusid+"' and t.fid = '"+orderid+"' " +
					" and t.tableid = 'orderform' and t.tablecolid = 'orderid'";
			List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql);
			if(fjmap != null && fjmap.size() > 0) {
				modelAndView.addObject("fjmap", fjmap);
			}
			
			modelAndView.setViewName("/taskcl/taskXzHandle");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
	}
	
	/**
	 *  任务协作处理
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/taskXzHandle",produces="application/json; charset=utf-8")
	@ResponseBody
	public String taskXzhandle(String tablejson) throws IOException{
		System.out.println("--进入(taskcl/taskXzHandle)--"+tablejson);
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
			String nowTime = util.getNowTime();
			/**
			 * tablejson 数据
			 */
			JSONObject json = jsonObject.fromObject(tablejson);
			
			String tasklistid = json.optString("tasklistid");
			String cominfo = json.optString("cominfo");
			
			//新增
			String lsql = " update taskxz t set t.COMINFO = ?, COMTIME = ? where t.TASKLISTID = ? and USERID = ? ";
			List<String> lt = new ArrayList<String>();
			lt.add(cominfo);lt.add(nowTime);lt.add(tasklistid);lt.add(userid);
			int r = tservice.UpdateSQL2(lsql, lt);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
				return jsonObject.fromObject(map).toString();
			} 
			
			tservice.commitExe_close();
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
	 * 跟踪查询列表
	 * @param page
	 * @param rows
	 * @param sort
	 * @param sortOrder
	 * @param searchjson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@RequestMapping(value="/trackList",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String trackList(String page,String rows,String sort,String sortOrder,String searchjson,String actjson) throws IOException{
		
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
		String userid = user.getUserid();
				
		/**
		 * 查询条件
		 */
		String where="where 1=1 ";
		String taskcode = json.getString("taskcode");
		String tasktitle = json.getString("tasktitle");
		String ordercode = json.getString("ordercode");
		String ordername = json.getString("ordername");
		String orderdate = json.optString("orderdate");
		if(orderdate != null && !orderdate.trim().equals("")){
			String startdate = orderdate.split("--")[0].trim();
			String enddate = orderdate.split("--")[1].trim();
			where += " and ORDERTIME >= '"+startdate+"' and ORDERTIME <= '"+enddate+"'";
		}
		where += taskcode != null && !taskcode.trim().equals("")?" and t.taskcode like '%"+taskcode+"%'":"";
		where += tasktitle != null && !tasktitle.trim().equals("")?" and t.tasktitle like '%"+tasktitle+"%'":"";
		where += ordercode != null && !ordercode.trim().equals("")?" and t.ordercode like '%"+ordercode+"%'":"";
		where += ordername != null && !ordername.trim().equals("")?" and t.ordername like '%"+ordername+"%'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from (select o.ORDERCODE,o.ORDERNAME,o.ORDERTIME,t.* from taskinfo t " +
				" left join orderform o on o.ORDERID = t.ORDERID " +
				" ) t "+where+" "+order+" limit "+num+","+rows ;
		
		/**
		 * 数据总条数
		 */
		String sqlt="select count(1) total from (select o.ORDERCODE,o.ORDERNAME,o.ORDERTIME,t.* from taskinfo t " +
				" left join orderform o on o.ORDERID = t.ORDERID " +
				" ) t "+where;
		String titl[] = {"total"};
		
		/**
		 * map中为table的数据组装
		 */
		Map map=new HashMap();
		//total 总行数
		map.put("total", tservice.getSelect(sqlt, titl).get(0).get("total"));
		
		//rows 格式为[{id:1,name:2},{id:1,name:2}...]
		map.put("rows", tservice.getSelect(sql)); 
		//关闭链接
		tservice.Close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 进入列表
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trackListForm")
	public ModelAndView trackListForm() throws Exception {
		/**
		 * 权限判定
		 * lstaskfp  角色菜单中的菜单编号
		 */
		String code = "taskTrack";
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
			modelAndView.setViewName("taskcl/taskTracklist");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;
	}
	
	/**
	 * 获取任务编号
	 * @return
	 */
	public String getCode() {
		String result = "";
		String nowDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()); 
		String sql = "";
		String code = nowDate+"001";
		BeanService_Transaction tservice = new BeanService_Transaction();
		try {
			sql = "select ifnull(max(t.TASKCODE)+1,'"+code+"') TASKCODE from taskinfo t where t.TASKCODE like '"+nowDate+"%'";
			List<Map<String, String>> list = tservice.getSelect(sql);
			if(list != null && list.size() > 0) {
				result = list.get(0).get("taskcode");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return result;
	}
}
