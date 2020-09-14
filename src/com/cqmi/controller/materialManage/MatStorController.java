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

@Controller
@RequestMapping("/matStor")
public class MatStorController extends BasicAction{
	
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
		System.out.println("--进入(matStor/list)--");
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by CLCODE,CLNAME ";
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
		
		String clcode = json.getString("clcode");
		String clname = json.getString("clname");
		
		where += clcode != null && !clcode.trim().equals("")?" and t.clcode like '%"+clcode+"%'":"";
		where += clname != null && !clname.trim().equals("")?" and clname like '%"+clname+"%'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from STOR_CL t "
				  +" "+where+" "+order+" limit "+num+","+rows ;
		/**
		 * 数据总条数
		 */
		String sqlt =" select count(*) total from STOR_CL t  "
				    +" "+where;
		
		/**
		 * map中为table的数据组装
		 */
		Map map=new HashMap();
		//total 总行数
		String titl[] = {"total"};
		map.put("total", tservice.getSelect(sqlt,titl).get(0).get("total"));
		
		//rows 格式为[{id:1,name:2},{id:1,name:2}...]
		map.put("rows", tservice.getSelect(sql)); 
		//关闭链接
		tservice.Close();
		
		
//		System.out.println(jsonObject.fromObject(map).toString());
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 入库保存
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
		
		System.out.println("--进入(matStor/save)--"+tablejson+"==sublist=="+sublist);
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
		String cusid = user.getCusid();
		//tablejson 转为json
		JSONObject json = jsonObject.fromObject(tablejson);
		List<String> lt = new ArrayList<String>();
		
		String billtype = json.optString("billtype");
		String applyid = json.optString("applyid");
		String taskid = json.optString("taskid");
		String billcode = getCode(cusid);
		String new_storclid = "";
		
		String billid = "";
		//新增主表信息
		String zbsql = "insert into STOR_BILL(CUSID,BILLCODE,BILLTYPE," +
				"TASKID,APPLYID,INUSER," +
				"CREATETIME,MEMO) " +
				"value(?,?,?," +
				"?,?,?," +
				"?,?)";
		lt.add(cusid);lt.add(billcode);lt.add(billtype);
		lt.add(taskid);lt.add(applyid);lt.add(userid);
		lt.add(nowTime);lt.add("");
		r = tservice.InsertSQL2(zbsql, lt);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		//获取刚插入的ID
		String title[] = {"id"};
		List<Map<String, String>> alist = tservice.getSelect("select LAST_INSERT_ID() as id",title);
		if(alist != null && alist.size()>0) {
			billid = alist.get(0).get("id");
		} else {
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		//新增明细信息    循环子表
		JsonArray array = new Gson().fromJson(sublist, JsonArray.class);
		boolean flag = true;
		//新增子表明细信息   循环明细信息
		for (JsonElement ele : array) {
			JsonObject tstr = ele.getAsJsonObject();
			System.out.println("子表数据"+tstr.toString());
			Map tmap = new Gson().fromJson(ele,Map.class);
			String storclid = (String)tmap.get("storclid");
			String billnum = (String)tmap.get("applynum");
			String clcode = (String)tmap.get("clcode");
			String clname = (String)tmap.get("clname");
			String fullname = clcode+" "+clname;
			String unitname = (String)tmap.get("unitname");
			String memo = (String)tmap.get("memo");
			String sqnum = (String)tmap.get("sqnum");
			String ycknum = (String)tmap.get("ycknum");
			String stornum = "";
			String surplus = "";
			if("3".equals(billtype)) {
				if(Float.parseFloat(sqnum) > (Float.parseFloat(billnum)+Float.parseFloat(ycknum))) {
					flag = false;
				}
			}
			
			if(billnum != null && !"".equals(billnum) && !"0".equals(billnum) && !"null".equals(billnum)) {
				if("".equals(storclid) || storclid == null) {
					//判断是否存在该物料信息
					String sql1 = "select * from STOR_CL where clcode = '"+clcode+"'";
					List<Map<String, String>> list = tservice.getSelect(sql1);
					if(list != null && list.size() > 0) {
						//更新库存记录信息
						new_storclid = list.get(0).get("storclid");
						stornum = list.get(0).get("stornum");
						surplus = Float.parseFloat(stornum)+Float.parseFloat(billnum)+"";
						String sql2 = "update STOR_CL set STORNUM = STORNUM+(?),KYNUM = KYNUM+(?),UNITNAME=? where storclid = ?";
						List<String> para2 = new ArrayList<String>();
						para2.add(billnum);para2.add(billnum);para2.add(unitname);
						para2.add(new_storclid);
						r = tservice.UpdateSQL2(sql2,para2);
						if(r == 0){
							map.put("info", "0");
							map.put("text", "操作失败请联系管理员");
							tservice.rollbackExe_close();
							return jsonObject.fromObject(map).toString();
						}
					} else {
						surplus = billnum;
						//新增库存记录信息
						String sql2 = "insert into STOR_CL(CUSID,CLCODE,CLNAME," +
								" FULLNAME,STORNUM,SDNUM," +
								" KYNUM,MEMO,UNITNAME) " + 
								" value (?,?,?," +
								" ?,?,?," +
								" ?,?,?) ";
						List<String> para2 = new ArrayList<String>();
						para2.add(cusid);para2.add(clcode);para2.add(clname);
						para2.add(fullname);para2.add(billnum);para2.add("0");
						para2.add(billnum);para2.add("");para2.add(unitname);
						r = tservice.InsertSQL2(sql2, para2);
						if(r == 0){
							map.put("info", "0");
							map.put("text", "操作失败请联系管理员");
							tservice.rollbackExe_close();
							return jsonObject.fromObject(map).toString();
						}
						//获取刚插入的ID
						String titl[] = {"id"};
						List<Map<String, String>> blist = tservice.getSelect("select LAST_INSERT_ID() as id",titl);
						if(blist != null && blist.size()>0) {
							new_storclid = blist.get(0).get("id");
						} else {
							map.put("info", "0");
							map.put("text", "操作失败请联系管理员");
							tservice.rollbackExe_close();
							return jsonObject.fromObject(map).toString();
						}
					}
				} else {	//退料入库
					String sql1 = "select * from STOR_CL where storclid = '"+storclid+"'";
					List<Map<String, String>> list = tservice.getSelect(sql1);
					if(list != null && list.size() > 0) {
						//更新库存记录信息
						new_storclid = list.get(0).get("storclid");
						stornum = list.get(0).get("stornum");
						surplus = Float.parseFloat(stornum)+Float.parseFloat(billnum)+"";
						String sql2 = "update STOR_CL set STORNUM = STORNUM+(?),KYNUM = KYNUM+(?),UNITNAME=? where storclid = ?";
						List<String> para2 = new ArrayList<String>();
						para2.add(billnum);para2.add(billnum);para2.add(unitname);
						para2.add(new_storclid);
						r = tservice.UpdateSQL2(sql2,para2);
						if(r == 0){
							map.put("info", "0");
							map.put("text", "操作失败请联系管理员");
							tservice.rollbackExe_close();
							return jsonObject.fromObject(map).toString();
						}
					}
				}
				//新增入库明细
				String sql = " insert into STOR_BILLLIST(BILLID,CUSID,STORCLID," +
						" BILLNUM,SURPLUS,MEMO) " + 
						" value (?,?,?," +
						" ?,?,?)";
				//注意参数顺序
				lt.clear();
				lt.add(billid);lt.add(cusid);lt.add(new_storclid);
				lt.add(billnum);lt.add(surplus);lt.add("");
				r = tservice.InsertSQL2(sql, lt);
				if(r == 0){
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return jsonObject.fromObject(map).toString();
				}
			}
		}
		if("3".equals(billtype)) {	//当入库类型为3 退库时，更新相应退库单的状态
			if(flag) {
				//更新申请单状态
				String sql5 = "update stor_clapply set state = '3' where clapplyid = '"+applyid+"' ";
				r = tservice.UpdateSQL2(sql5);
				if(r == 0){
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return new JSONObject().fromObject(map).toString();
				}
			} else {
				//更新申请单状态
				String sql5 = "update stor_clapply set state = '2' where clapplyid = '"+applyid+"' ";
				r = tservice.UpdateSQL2(sql5);
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
	 * 出库保存
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value="/outBound",produces="application/json; charset=utf-8")
	@ResponseBody
	public String outBound(String tablejson, String sublist) throws IOException{
		
		System.out.println("--进入(matStor/outBound)--"+tablejson+"==sublist=="+sublist);
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
		String cusid = user.getCusid();
		//tablejson 转为json
		JSONObject json = jsonObject.fromObject(tablejson);
		List<String> lt = new ArrayList<String>();
		
		String billtype = json.optString("billtype");
		String applyid = json.optString("applyid");
		String taskid = json.optString("taskid");
		String billcode = getCode(cusid);
		String billid = "";
		//新增主表信息
		String zbsql = "insert into STOR_BILL(CUSID,BILLCODE,BILLTYPE," +
				"TASKID,APPLYID,INUSER," +
				"CREATETIME,MEMO) " +
				"value(?,?,?," +
				"?,?,?," +
				"?,?)";
		lt.add(cusid);lt.add(billcode);lt.add(billtype);
		lt.add(taskid);lt.add(applyid);lt.add(userid);
		lt.add(nowTime);lt.add("");
		r = tservice.InsertSQL2(zbsql, lt);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		//获取刚插入的ID
		String title[] = {"id"};
		List<Map<String, String>> alist = tservice.getSelect("select LAST_INSERT_ID() as id",title);
		if(alist != null && alist.size()>0) {
			billid = alist.get(0).get("id");
		} else {
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		//新增明细信息    循环子表
		JsonArray array = new Gson().fromJson(sublist, JsonArray.class);
		boolean flag = true;
		//新增子表明细信息   循环明细信息
		for (JsonElement ele : array) {
			JsonObject tstr = ele.getAsJsonObject();
			System.out.println("子表数据"+tstr.toString());
			Map tmap = new Gson().fromJson(ele,Map.class);
			String storclid = (String)tmap.get("storclid");
			String billnum = (String)tmap.get("applynum");
			String clcode = (String)tmap.get("clcode");
			String clname = (String)tmap.get("clname");
			String sqnum = (String)tmap.get("sqnum");
			String ycknum = (String)tmap.get("ycknum");
//			String memo = (String)tmap.get("memo");
			String stornum = "";
			String surplus = "";
			if(Float.parseFloat(sqnum) > (Float.parseFloat(billnum)+Float.parseFloat(ycknum))) {
				flag = false;
			}
			if(billnum != null && !"".equals(billnum) && !"0".equals(billnum) && !"null".equals(billnum)) {
				
				String sql1 = "select * from STOR_CL where storclid = '"+storclid+"'";
				List<Map<String, String>> list = tservice.getSelect(sql1);
				if(list != null && list.size() > 0) {
					//更新库存记录信息
					stornum = list.get(0).get("stornum");
					surplus = Float.parseFloat(stornum)-Float.parseFloat(billnum)+"";
					//更新锁定量
					String sql4 = "update STOR_CL set SDNUM = SDNUM-(?),STORNUM = STORNUM-(?) where storclid = ?";
					List<String> para = new ArrayList<String>();
					para.add(billnum);para.add(billnum);para.add(storclid);
					r = tservice.UpdateSQL2(sql4,para);
					if(r == 0){
						map.put("info", "0");
						map.put("text", "操作失败请联系管理员");
						tservice.rollbackExe_close();
						return new JSONObject().fromObject(map).toString();
					}
				}
				//新增入库明细
				String sql = " insert into STOR_BILLLIST(BILLID,CUSID,STORCLID," +
						" BILLNUM,SURPLUS,MEMO) " + 
						" value (?,?,?," +
						" ?,?,?)";
				//注意参数顺序
				lt.clear();
				lt.add(billid);lt.add(cusid);lt.add(storclid);
				lt.add(billnum);lt.add(surplus);lt.add("");
				r = tservice.InsertSQL2(sql, lt);
				if(r == 0){
					map.put("info", "0");
					map.put("text", "操作失败请联系管理员");
					tservice.rollbackExe_close();
					return jsonObject.fromObject(map).toString();
				}
			}
		}
		if(flag) {
			//更新申请单状态
			String sql5 = "update stor_clapply set state = '3' where clapplyid = '"+applyid+"' ";
			r = tservice.UpdateSQL2(sql5);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
				return new JSONObject().fromObject(map).toString();
			}
		} else {
			//更新申请单状态
			String sql5 = "update stor_clapply set state = '2' where clapplyid = '"+applyid+"' ";
			r = tservice.UpdateSQL2(sql5);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
				return new JSONObject().fromObject(map).toString();
			}
		}
		
		tservice.commitExe_close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 出入库记录
	 * @param page
	 * @param rows
	 * @param sort
	 * @param sortOrder
	 * @param searchjson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/billList",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String billList(String page,String rows,String sort,String sortOrder,
			String searchjson, HttpServletRequest request) 
			throws IOException{
		
		System.out.println("--进入(matStor/billList)--");
		//定义
		JSONObject jsonObject = new JSONObject();
		String order = "";
		Map<String, Object> map = new HashMap<String, Object>();
		//开启数据库链接
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice = new BeanService_Transaction();
		try {
			if(searchjson != null) {
				searchjson = new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); 
			}
			//定义排序
			if(sort != null && !sort.trim().equals("")){
				order=(" order by "+sort+" "+sortOrder);
			}else{
				order = " order by CREATETIME desc ";
			}
			/**
			 * 	page第几页	rows每页多少行    
			 */ 
			int num = (Integer.parseInt(page)-1)*Integer.parseInt(rows);
			
			/**
			 * searchjson 查询条件
			 */
			JSONObject json=jsonObject.fromObject(searchjson);
			/**
			 * 查询条件
			 */
			String where = "where 1=1 ";
			String billtype = json.getString("billtype");
			String createtime = json.getString("createtime");
			String storclid = json.getString("storclid");
			where += storclid != null && !storclid.trim().equals("")?" and storclid = '"+storclid+"'":"";
			where += billtype != null && !billtype.trim().equals("")?" and billtype = '"+billtype+"'":"";
			where += createtime != null && !createtime.trim().equals("")?" and createtime like '"+createtime+"%'":"";
			/**
			 * 查询数据sql
			 */
			String sql = "select * from ( "
					+ " select s.clcode,s.clname,i.taskcode,u.USERNAME as inusername,t.*," 
					+ " bl.storclid,bl.surplus,bl.billnum,s.unitname "
					+ " from stor_bill t " 
					+ " left join stor_billlist bl on bl.billid = t.billid "
					+ " left join stor_cl s on s.storclid = bl.storclid "
					+ " left join basic_user u on u.USERID = t.INUSER "
					+ " left join taskinfo i on i.taskid = t.taskid "
					+ " ) t "+where+" "+order+" limit "+num+","+rows+" ";
			String titl[] = {"billid","cusid","storclid","billtype","billnum","taskid","inuser","createtime","memo",
					"inusername","taskcode","clcode","clname","surplus","unitname"};
			/**
			 * 数据总条数
			 */
			String sqlt = "select count(*) total from (select s.clcode,s.clname,i.taskcode,u.USERNAME as inusername,t.*," 
					+ " bl.storclid,bl.surplus,bl.billnum "
					+ " from stor_bill t " 
					+ " left join stor_billlist bl on bl.billid = t.billid "
					+ " left join stor_cl s on s.storclid = bl.storclid "
					+ " left join basic_user u on u.USERID = t.INUSER "
					+ " left join taskinfo i on i.taskid = t.taskid "
					+ " ) t "+where+" "+order;
			
			//total 总行数
			String title[] = {"total"};
			map.put("total", tservice.getSelect(sqlt, title).get(0).get("total"));
			
			//rows 格式为[{id:1,name:2},{id:1,name:2}...]
			map.put("rows", tservice.getSelect(sql, titl)); 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 删除
	 * value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value="/delete",produces="application/json; charset=utf-8")
	@ResponseBody
	public String delete(String tablejson) throws UnsupportedEncodingException{
		System.out.println("删除数据id有"+tablejson);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice = new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map map = new HashMap();
		String ids = tablejson.replace("\"", "'").replace("[", "").replace("]", "");
		//判断是否有领用记录和申请记录
		String sql1 = "select * from stor_billlist t " +
				" left join stor_bill b on b.billid = t.billid " +
				" where t.STORCLID in("+ids+") and b.BILLTYPE = '2'";
		String sql2 = "select * from stor_clapplylist t where t.STORCLID in("+ids+") ";
		List<Map<String, String>> list = tservice.getSelect(sql1);
		List<Map<String, String>> alist = tservice.getSelect(sql2);
		if(alist != null && alist.size() > 0) {
			map.put("info", "0");
			map.put("text", "已存在材料申请记录，不能删除");
			return new JSONObject().fromObject(map).toString();
		} 
		else if(list != null && list.size() > 0) {
			map.put("info", "0");
			map.put("text", "已存在材料领用记录，不能删除");
			return new JSONObject().fromObject(map).toString();
		}
		String sql = "delete from STOR_CL where STORCLID in ("+ids+")";
		int r = tservice.DeleteSQL2(sql);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return new JSONObject().fromObject(map).toString();
		}
		//删除入库记录
		String sql4 = "delete from STOR_BILL WHERE BILLID in (select billid from stor_billlist where STORCLID in ("+ids+"))";
		r = tservice.DeleteSQL2(sql4);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return new JSONObject().fromObject(map).toString();
		}
		//删除入库记录
		String sql3 = "delete from STOR_BILLlist WHERE STORCLID in ("+ids+")";
		r = tservice.DeleteSQL2(sql3);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return new JSONObject().fromObject(map).toString();
		}
		tservice.commitExe_close();
	    return new JSONObject().fromObject(map).toString();
	}
	
	/**
	 * 查询领用明细 根据申请单号
	 * @param page
	 * @param rows
	 * @param sort
	 * @param sortOrder
	 * @param searchjson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/getApplyList",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String getApplyList(String tablejson, String applytype) throws IOException{
		
		System.out.println("--进入(matStor/getApplyList)--"+applytype+"==="+tablejson);
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		Util util = new Util();
		ModelAndView modelAndView = new ModelAndView();
		Map<String, List<Map>> userList = new HashMap<String, List<Map>>();
		Map map = new HashMap();
		try {
			 User user = (User) LoginController.getSession().getAttribute("user");
			 String cusid = user.getCusid();
			 String sql = "select i.taskcode,i.tasktitle,t.* from stor_clapply t " +
			 		" left join taskinfo i on i.taskid = t.taskid " +
			 		" where t.APPLYCODE = '"+tablejson+"' and t.APPLYTYPE = '"+applytype+"'";
			 List<Map<String, String>> list = tservice.getSelect(sql);
			 if(list != null && list.size() == 1) {
				 String state = list.get(0).get("state");
				 if("3".equals(state)) {
					 String textInfo = "";
					 if("1".equals(applytype)) {
						 textInfo = "申请单号已出库";
					 } else if("2".equals(applytype)) {
						 textInfo = "申请单号已入库";
					 }
					 map.put("info", "0");
					 map.put("textinfo", textInfo);
					 return JSONObject.fromObject(map).toString();
				 }
				 String billtype = "";
				 if("1".equals(applytype)) {
					 billtype = "2";
				 } else if("2".equals(applytype)){
					 billtype = "3";
				 } 
				 //查询明细信息
				 String zbsql = " select s.CLCODE,s.CLNAME,s.UNITNAME,cast(ifnull(tmp.ycknum,0) as char) ycknum,t.* "
						 + " from stor_clapplylist t "
						 + " left join stor_cl s on s.STORCLID = t.STORCLID "
						 + " left join ( "
						 + " select bl.STORCLID,sum(ifnull(bl.BILLNUM,0)) ycknum from stor_billlist bl "
						 + " left join stor_bill b on b.BILLID = bl.BILLID "
						 + " where b.BILLTYPE = '"+billtype+"' and b.APPLYID = '"+list.get(0).get("clapplyid")+"' "
						 + " GROUP BY bl.STORCLID "
						 + " ) tmp on tmp.STORCLID = t.STORCLID "
						 + " where t.CLAPPLYID = '"+list.get(0).get("clapplyid")+"'";
				 String titl[] = {"storclid","clcode","clname","unitname","ycknum","applynum"};
				 List<Map<String, String>> alist = tservice.getSelect(zbsql,titl);
				 map.put("subList", alist);
				 map.put("map", list.get(0));
				 map.put("info", "1");
				 map.put("textinfo", "操作成功");
			 } else if(list.size() > 1) {
				 map.put("info", "0");
				 map.put("textinfo", "根据申请编号查询到两个申请单");
			 } else {
				 map.put("info", "0");
				 map.put("textinfo", "申请单号不存在");
			 }
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
	    return JSONObject.fromObject(map).toString();
	}
	
	/**
	 * 出入库记录表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/billListForm")
	@ResponseBody
	public ModelAndView billListForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("storclid", tablejson);
			modelAndView.setViewName("materialManage/matStor/billList");
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

			modelAndView.setViewName("materialManage/matStor/billAdd");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
		
	}
	
	/**
	 * 出库表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ckForm")
	public ModelAndView ckForm(String tablejson) throws Exception {
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {

			modelAndView.setViewName("materialManage/matStor/billOutBound");
			
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
		String code = "matStor";
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
			modelAndView.setViewName("materialManage/matStor/list");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;

	}
	/**
	 * 获取编号
	 * @return
	 */
	public String getCode(String cusid) {
		String result = "";
		String nowDate = new java.text.SimpleDateFormat("yyMMdd").format(new java.util.Date()); 
		String sql = "";
		String code = nowDate+"0001";
		BeanService_Transaction tservice = new BeanService_Transaction();
		try {
			sql = "select ifnull(max(SUBSTR(t.BILLCODE,1,10))+1,'"+code+"') CODE from STOR_BILL t where t.BILLCODE like '"+nowDate+"%'";
			List<Map<String, String>> list = tservice.getSelect(sql);
			if(list != null && list.size() > 0) {
				result = list.get(0).get("code");
			}
			result = result+cusid;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return result;
	}
}
