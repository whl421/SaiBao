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
@RequestMapping("/matCheck")
public class MatCheckController extends BasicAction{
	
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
		System.out.println("--进入(matCheck/list)--");
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by APPLYTIME ";
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
		
		String applycode = json.getString("applycode");
		String applyusername = json.getString("applyusername");
		String applytime = json.getString("applytime");
		String applystate = json.getString("applystate");
		String taskcode = json.getString("taskcode");
		String tasktitle = json.getString("tasktitle");
		String applytype = json.getString("applytype");
		
		where += applycode != null && !applycode.trim().equals("")?" and t.applycode like '%"+applycode+"%'":"";
		where += applyusername != null && !applyusername.trim().equals("")?" and t.applyusername like '%"+applyusername+"%'":"";
		where += applytime != null && !applytime.trim().equals("")?" and applytime like '"+applytime+"%'":"";
		where += applystate != null && !applystate.trim().equals("")?" and applystate = '"+applystate+"'":"";
		where += taskcode != null && !taskcode.trim().equals("")?" and t.taskcode like '%"+taskcode+"%'":"";
		where += tasktitle != null && !tasktitle.trim().equals("")?" and t.tasktitle like '%"+tasktitle+"%'":"";
		where += applytype != null && !applytype.trim().equals("")?" and t.applytype = '"+applytype+"'":"";
		
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
				"checktime","applystate","jjreason","memo","applyusername","taskcode","tasktitle","applytype"};
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
	 * 批量同意
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
			String cusid = user.getUserid();
			String nowTime = util.getNowTime();
			//开起事物
			tservice.OpenTransaction();
			String ids = tablejson.replace("\"", "'").replace("[", "").replace("]", "");
			String sql = "update STOR_CLAPPLY set APPLYSTATE = '2',CHECKUSER='"+userid+"',CHECKTIME='"+nowTime+"',JJREASON = '同意' " +
					" where CLAPPLYID in ("+ids+")";
			int r = tservice.DeleteSQL2(sql);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
				return new JSONObject().fromObject(map).toString();
			}
//			
//			//更新锁定量
//			String sql1 = " select c.taskid,c.APPLYTYPE,t.* from STOR_CLAPPLYLIST t " +
//					" left join STOR_CLAPPLY c on c.CLAPPLYID = t.CLAPPLYID " +
//					" WHERE t.CLAPPLYID in ("+ids+")";
//			List<Map<String, String>> list = tservice.getSelect(sql1);
//			if(list != null && list.size() > 0) {
//				for(int i=0; i<list.size(); i++) {
//					String storclid = list.get(i).get("storclid");
//					String applynum = list.get(i).get("applynum");
//					String taskid = list.get(i).get("taskid");
//					String applytype = list.get(i).get("applytype");
//					if("1".equals(applytype)) {
//						//更新锁定量
//						String sql4 = "update STOR_CL set SDNUM = SDNUM-(?),STORNUM = STORNUM-(?) where storclid = ?";
//						List<String> para = new ArrayList<String>();
//						para.add(applynum);para.add(applynum);para.add(storclid);
//						r = tservice.UpdateSQL2(sql4,para);
//						if(r == 0){
//							map.put("info", "0");
//							map.put("text", "操作失败请联系管理员");
//							tservice.rollbackExe_close();
//							return new JSONObject().fromObject(map).toString();
//						}
//						//新增出库记录
//						String insertsql = " insert into STOR_BILL(CUSID,STORCLID,BILLTYPE," +
//								" BILLNUM,TASKID,INUSER," +
//								" OUTUSER,CREATETIME,MEMO) " + 
//								" value (?,?,?," +
//								" ?,?,?," +
//								" ?,?,?) ";
//						String billtype = "2";
//						List<String> lt = new ArrayList<String>();
//						lt.add(cusid);lt.add(storclid);lt.add(billtype);
//						lt.add(applynum);lt.add(taskid);lt.add("");
//						lt.add(userid);lt.add(nowTime);lt.add("");
//						r = tservice.InsertSQL2(insertsql, lt);
//						if(r == 0){
//							map.put("info", "0");
//							map.put("text", "操作失败请联系管理员");
//							tservice.rollbackExe_close();
//							return new JSONObject().fromObject(map).toString();
//						}
//					} else {
//						//退料单
//						//新增入库记录
//						String insertsql = " insert into STOR_BILL(CUSID,STORCLID,BILLTYPE," +
//								" BILLNUM,TASKID,INUSER," +
//								" OUTUSER,CREATETIME,MEMO) " + 
//								" value (?,?,?," +
//								" ?,?,?," +
//								" ?,?,?) ";
//						String billtype = "3";
//						List<String> lt1 = new ArrayList<String>();
//						lt1.add(cusid);lt1.add(storclid);lt1.add(billtype);
//						lt1.add(applynum);lt1.add(taskid);lt1.add(userid);
//						lt1.add("");lt1.add(nowTime);lt1.add("退库");
//						r = tservice.InsertSQL2(insertsql, lt1);
//						if(r == 0){
//							map.put("info", "0");
//							map.put("text", "操作失败请联系管理员");
//							tservice.rollbackExe_close();
//							return new JSONObject().fromObject(map).toString();
//						}
//						//更新库存和可用量
//						String sql4 = "update STOR_CL set STORNUM = STORNUM+(?),KYNUM = KYNUM+(?) where storclid = ?";
//						List<String> para = new ArrayList<String>();
//						para.add(applynum);para.add(applynum);para.add(storclid);
//						r = tservice.UpdateSQL2(sql4,para);
//						if(r == 0){
//							map.put("info", "0");
//							map.put("text", "操作失败请联系管理员");
//							tservice.rollbackExe_close();
//							return new JSONObject().fromObject(map).toString();
//						}
//					}
//				}
//			}
			tservice.commitExe_close();
		} catch(Exception e) {
			e.printStackTrace();
			tservice.rollbackExe_close();
		}
	    return new JSONObject().fromObject(map).toString();
		
	}
	
	/**
	 * 批量拒绝
	 * value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/batchrefuse",produces="application/json; charset=utf-8")
	@ResponseBody
	public String batchrefuse(String tablejson) throws UnsupportedEncodingException{
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
			String ids = tablejson.replace("\"", "'").replace("[", "").replace("]", "");
			String sql = "update STOR_CLAPPLY set APPLYSTATE = '3',CHECKUSER='"+userid+"',CHECKTIME='"+nowTime+"',JJREASON = '拒绝'  " +
					" where CLAPPLYID in ("+ids+")";
			int r = tservice.DeleteSQL2(sql);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
				return new JSONObject().fromObject(map).toString();
			}
			//更新锁定量
			String sql1 = " select c.APPLYTYPE,t.* from STOR_CLAPPLYLIST t " +
					" left join STOR_CLAPPLY c on c.CLAPPLYID = t.CLAPPLYID " +
					" WHERE t.CLAPPLYID in ("+ids+")";
			List<Map<String, String>> list = tservice.getSelect(sql1);
			if(list != null && list.size() > 0) {
				for(int i=0; i<list.size(); i++) {
					String storclid = list.get(i).get("storclid");
					String applynum = list.get(i).get("applynum");
					String applytype = list.get(i).get("applytype");
					if("1".equals(applytype)) {
						//更新锁定量
						String sql4 = "update STOR_CL set SDNUM = SDNUM-(?),KYNUM = KYNUM+(?) where storclid = ?";
						List<String> para = new ArrayList<String>();
						para.add(applynum);para.add(applynum);para.add(storclid);
						r = tservice.UpdateSQL2(sql4,para);
						if(r == 0){
							map.put("info", "0");
							map.put("text", "操作失败请联系管理员");
							tservice.rollbackExe_close();
							return new JSONObject().fromObject(map).toString();
						}
					}
				}
			}
			tservice.commitExe_close();
		} catch(Exception e) {
			e.printStackTrace();
			tservice.rollbackExe_close();
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
		System.out.println("--进入(matCheck/check)--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		//获取当前时间
		String nowTime = util.getNowTime();
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getUserid();
		//tablejson 转为json
		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();
				
		/**
		 * 防注入使用
		 */
		String sql = "update STOR_CLAPPLY set APPLYSTATE = ?,CHECKUSER=?,CHECKTIME=?,JJREASON=? where CLAPPLYID = ?";

		String applystate = json.optString("applystate");
		String userid = user.getUserid();
		String jjreason = json.optString("jjreason");
		String clapplyid = json.optString("clapplyid");
		String taskid = json.optString("taskid");
		
		//注意参数顺序
		lt.add(applystate);lt.add(userid);lt.add(nowTime);
		lt.add(jjreason);lt.add(clapplyid);
		
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
		//更新锁定量
		String sql1 = " select * from STOR_CLAPPLYLIST WHERE CLAPPLYID = ?";
		List<String> para1 = new ArrayList<String>();
		para1.add(clapplyid);
		List<Map<String, String>> list = tservice.getSelect(sql1, para1);
		if(list != null && list.size() > 0) {
			for(int i=0; i<list.size(); i++) {
				String storclid = list.get(i).get("storclid");
				String applynum = list.get(i).get("applynum");
				String sql4 = "";
				//更新锁定量
				if("3".equals(applystate)) {
					sql4 = "update STOR_CL set SDNUM = SDNUM-(?),KYNUM = KYNUM+(?) where storclid = ?";
					List<String> para = new ArrayList<String>();
					para.add(applynum);para.add(applynum);para.add(storclid);
					r = tservice.UpdateSQL2(sql4,para);
				} else if("2".equals(applystate)) {
//					sql4 = "update STOR_CL set SDNUM = SDNUM-(?),STORNUM = STORNUM-(?) where storclid = ?";
//					//新增出库记录
//					String insertsql = " insert into STOR_BILL(CUSID,STORCLID,BILLTYPE," +
//							" BILLNUM,TASKID,INUSER," +
//							" OUTUSER,CREATETIME,MEMO) " + 
//							" value (?,?,?," +
//							" ?,?,?," +
//							" ?,?,?) ";
//					String billtype = "2";
//					List<String> lt1 = new ArrayList<String>();
//					lt1.add(cusid);lt1.add(storclid);lt1.add(billtype);
//					lt1.add(applynum);lt1.add(taskid);lt1.add("");
//					lt1.add(userid);lt1.add(nowTime);lt1.add("");
//					r = tservice.InsertSQL2(insertsql, lt1);
//					if(r == 0){
//						map.put("info", "0");
//						map.put("text", "操作失败请联系管理员");
//						tservice.rollbackExe_close();
//						return new JSONObject().fromObject(map).toString();
//					}
				}
				
				if(r == 0){
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return new JSONObject().fromObject(map).toString();
				}
			}
		}
		tservice.commitExe_close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 退料单审核
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/backCheck",produces="application/json; charset=utf-8")
	@ResponseBody
	public String backCheck(String tablejson) throws IOException{
		System.out.println("--进入(matCheck/backCheck)--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		//获取当前时间
		String nowTime = util.getNowTime();
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getUserid();
		//tablejson 转为json
		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();
				
		/**
		 * 防注入使用
		 */
		String sql = "update STOR_CLAPPLY set APPLYSTATE = ?,CHECKUSER=?,CHECKTIME=?,JJREASON=? where CLAPPLYID = ?";

		String applystate = json.optString("applystate");
		String userid = user.getUserid();
		String jjreason = json.optString("jjreason");
		String clapplyid = json.optString("clapplyid");
		String taskid = json.optString("taskid");
		
		//注意参数顺序
		lt.add(applystate);lt.add(userid);lt.add(nowTime);
		lt.add(jjreason);lt.add(clapplyid);
		
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
		//新增入库记录
		String sql1 = " select * from STOR_CLAPPLYLIST WHERE CLAPPLYID = ?";
		List<String> para1 = new ArrayList<String>();
		para1.add(clapplyid);
		List<Map<String, String>> list = tservice.getSelect(sql1, para1);
		if(list != null && list.size() > 0) {
			for(int i=0; i<list.size(); i++) {
				String storclid = list.get(i).get("storclid");
				String applynum = list.get(i).get("applynum");
				if("3".equals(applystate)) {	//拒绝
					//不做操作
				} else if("2".equals(applystate)) {
					//新增入库记录
//					String insertsql = " insert into STOR_BILL(CUSID,STORCLID,BILLTYPE," +
//							" BILLNUM,TASKID,INUSER," +
//							" OUTUSER,CREATETIME,MEMO) " + 
//							" value (?,?,?," +
//							" ?,?,?," +
//							" ?,?,?) ";
//					String billtype = "3";
//					List<String> lt1 = new ArrayList<String>();
//					lt1.add(cusid);lt1.add(storclid);lt1.add(billtype);
//					lt1.add(applynum);lt1.add(taskid);lt1.add(userid);
//					lt1.add("");lt1.add(nowTime);lt1.add("退库");
//					r = tservice.InsertSQL2(insertsql, lt1);
//					if(r == 0){
//						map.put("info", "0");
//						map.put("text", "操作失败请联系管理员");
//						tservice.rollbackExe_close();
//						return new JSONObject().fromObject(map).toString();
//					}
//					//更新库存和可用量
//					String sql4 = "update STOR_CL set STORNUM = STORNUM+(?),KYNUM = KYNUM+(?) where storclid = ?";
//					List<String> para = new ArrayList<String>();
//					para.add(applynum);para.add(applynum);para.add(storclid);
//					r = tservice.UpdateSQL2(sql4,para);
//					if(r == 0){
//						map.put("info", "0");
//						map.put("text", "操作失败请联系管理员");
//						tservice.rollbackExe_close();
//						return new JSONObject().fromObject(map).toString();
//					}
				}
			}
		}
		tservice.commitExe_close();
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
			String applytype = "";
			if(list != null && list.size() > 0) {
				modelAndView.addObject("map", list.get(0));
				applytype = list.get(0).get("applytype");
			}
			if("2".equals(applytype)) {
				modelAndView.setViewName("materialManage/matCheck/matBackInfo");
			} else {
				modelAndView.setViewName("materialManage/matCheck/matCheckInfo");
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
			String applytype = "";
			String sql = "select u.USERNAME as applyusername,ua.USERNAME as checkusername,i.TASKCODE,i.TASKTITLE,t.* \n" +
					"from stor_clapply t\n" +
					"left join basic_user u on u.USERID = t.APPLYUSER\n" +
					"left join basic_user ua on ua.USERID = t.CHECKUSER\n" +
					"left join taskinfo i on i.TASKID = t.TASKID\n" +
					"where t.CLAPPLYID = '"+tablejson+"'";
			String title[] = {"clapplyid","cusid","taskid","applycode","applyuser","applytime","applyreason","checkuser",
					"checktime","applystate","jjreason","memo","applyusername","taskcode","tasktitle","checkusername","applytype"};
			List<Map<String, String>> list = tservice.getSelect(sql, title);
			if(list != null && list.size() > 0) {
				modelAndView.addObject("map", list.get(0));
				applytype = list.get(0).get("applytype");
			}
			if("2".equals(applytype)) {
				modelAndView.setViewName("materialManage/matCheck/matBackCheck");
			} else {
				modelAndView.setViewName("materialManage/matCheck/matCheck");
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
		String code = "matCheck";
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
			modelAndView.setViewName("materialManage/matCheck/list");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;

	}
	
}
