package com.cqmi.controller.orderManage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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
@RequestMapping("/orderRec")
public class OrderRecController {
	
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
		String sql=" select * from (select * from orderform t  ) t "
				  +" "+where+" "+order+" limit "+num+","+rows ;
		String title[] = {"orderid","cusid","khname","ordercode","ordername","orderuser","ordertime","ostate",
				"orderprice","pricetime","qrmemo","tsusername","tsdate","departcode","departname","liststate",
				"confirmuser","confirmmemo","listdate","createtime"};
		/**
		 * 数据总条数
		 */
		String sqlt="select count(1) total from orderform t "+where;
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
		String code = "orderRec";
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
			modelAndView.setViewName("orderManage/orderRec/list");
		} else {
			modelAndView.setViewName("nopower");
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
	 * 查看表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/infoForm")
	public ModelAndView infoForm(String tablejson) throws Exception {
		
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
			//查询部门信息
			List<Map<String, String>> lmap = util.getDeptInfo(tservice, cusid);
			modelAndView.addObject("cusid", cusid);
			modelAndView.addObject("username", user.getUserid()+"-"+user.getUsercode());
			modelAndView.addObject("fjmap",fjmap);
			modelAndView.addObject("deptList", lmap);
			modelAndView.setViewName("orderManage/orderRec/infoOrderRec");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
	}
}
