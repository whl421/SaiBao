package com.cqmi.controller.menu;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cqmi.controller.login.LoginController;
import com.cqmi.controller.login.bean.User;
import com.cqmi.controller.login.util.RoleMenuUtil;
import com.cqmi.db.service.BeanService_Transaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class RoleMenuController {

	/**
	 * 角色权限设置
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="rolemenu/rolemenujsp",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView rolemenujsp() throws IOException, SQLException{
		User user= ((User)LoginController.getSession().getAttribute("user"));
		String cusid=user.getCusid();
//		
		List<String> lt=new ArrayList<String>();
		lt.add(cusid);
		String s_sql="select * from role where cusid=? ";
		BeanService_Transaction tservice=new BeanService_Transaction();
//		/**
//		 * 权限判定
//		 */
		List<Map<String, String>> bpower=user.getBpower("rolemenutwojsp");		//tservice.getSelect(G_b_sql);
//		/**
//		 * 1.确认当前模块权限，
//		 * 2.获取按钮权限
//		 */
		boolean boo=false;
		Map mb=new HashMap();
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals("rolemenutwojsp"))boo=true;
			if(!map.get("type").equals("2"))mb.put(map.get("htmlcode"), "1");
			 
		}
//		
		
		List<Map<String, String>> lmap=tservice.getSelect(s_sql,lt);
		
		tservice.Close();
		
		 
		ModelAndView modelAndView = new ModelAndView();
		if(boo){
			modelAndView.addObject("userMap", lmap);
			modelAndView.addObject("role", JSONArray.fromObject(lmap));
			modelAndView.addObject("button", mb);
			modelAndView.setViewName("menurole/rolemenu/rolemenutwo");
		}else
			modelAndView.setViewName("nopower");
		//角色下来
		
		return modelAndView;
	}
	
	/**
	 * 角色权限设置获取权限列表
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="rolemenu/rolemenu",produces="application/json; charset=utf-8")
	@ResponseBody
	public String rolemenu(String roleid) throws IOException, SQLException{
		if(roleid != null&&!roleid.trim().equals("")) {
			roleid =new String(roleid.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		User user=(User)LoginController.getSession().getAttribute("user");
		String cusid=user.getCusid();
		String userid=user.getUserid();
		List<String> lt=new ArrayList<String>();
		lt.add(cusid);lt.add(roleid); 
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		String sqlt="";
		if(userid.equals("1")){
			sqlt="select m.*,m.htmlname text,IFNULL(r.menuid,'') ischeck from menu m left join menurole"
					+ " r on r.menuid=m.id and r.cusid=? and r.roleid=? order by m.type,m.order";
		}else{
			if(cusid.equals("1")){
				sqlt="select m.*,m.htmlname text,IFNULL(r.menuid,'') ischeck from menu m left join menurole"
						+ " r on r.menuid=m.id and r.cusid=? and r.roleid=? where m.id<>3 and m.parentid<>3 order by m.type,m.order";
			}else{
				lt.clear();
				lt.add(roleid);lt.add("2"); 
				sqlt="select m.*,m.htmlname text,IFNULL(re.menuid,'') ischeck from menu m left join menurole"
						+ " r on r.menuid=m.id  left join menurole re on re.menuid=m.id and re.roleid=?  where m.id<>3 and m.parentid<>3 and r.roleid=? order by m.type,m.order";
			}
		}
			
		String title[]={"id","text","parentid","ischeck","type"};
		List<Map<String, String>> treemapss=tservice.getSelect(sqlt,lt,title);
		tservice.Close();
		
		Map<String, List<String>> pidmp=new HashMap<String, List<String>>();
		
		Map<String, Map<String, String>> treemap=new HashMap<String, Map<String, String>>();
		
		for (Map<String, String> map : treemapss) {
			String pid=map.get("parentid");
			String id=map.get("id");
			treemap.put(id, map);
			if(pidmp.containsKey(pid)){
				pidmp.get(pid).add(id);
			}else{
				List<String> lts=new ArrayList<String>();
				lts.add(id);
				pidmp.put(pid, lts);
			}
		}
		
		List<Map<String, String>> tree=new ArrayList<Map<String, String>>();
		 
		for (Map<String, String> map : treemapss) {
			String type=map.get("type");
			if(type!=null&&type.trim().equals("1")){
				//加入菜单
				tree.add(map);
				String id=map.get("id");
				if(pidmp.containsKey(id)){
					List<String> menuids=pidmp.get(id);
					for (String ids : menuids) {
						//加入模块
						tree.add(treemap.get(ids));
						if(pidmp.containsKey(ids)){
							List<String> buttonids=pidmp.get(ids);
							for (String btnid : buttonids) {
								//加入按钮
								tree.add(treemap.get(btnid));
								
								if(pidmp.containsKey(btnid)){
									List<String> rbuttonids=pidmp.get(btnid);
									for (String rbtnid : rbuttonids) {
										//加入页签按钮
										tree.add(treemap.get(rbtnid));
									}
								}
							}
						}
					}
					
				}
			} 
		}
		 
		return JSONArray.fromObject(tree).toString();
	}
	
	
	/**
	 * 角色权限设置 权限列表
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="rolemenu/setrolemenu",produces="application/json; charset=utf-8")
	@ResponseBody
	public String setrolemenu(String tablejson) throws IOException, SQLException{
//		if(tablejson != null&&!tablejson.trim().equals("")) {
//			tablejson =new String(tablejson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
//		}
		Map<String, String> info=new HashMap<String, String>();
		if(tablejson==null||tablejson.trim().equals("")){
			info.put("info", "0");
			info.put("textinfo", "提交参数错误");
			return JSONObject.fromObject(info).toString();
		}
		
		JSONObject table=JSONObject.fromObject(tablejson);
		String roleid=table.optString("role");
		String menuids=table.optString("menu");
		
		if(menuids==null||menuids.trim().equals("")){
			info.put("info", "0");
			info.put("textinfo", "未设置权限操作");
			return JSONObject.fromObject(info).toString();
		}
		
		String _menuids[]=menuids.split(",");
		
		RoleMenuUtil rolemenuutil=new RoleMenuUtil();
		
		int r=0;
		for (String id : _menuids) {
			if(!rolemenuutil.isInteger(id))r=1;
		}
		if(r==1||!rolemenuutil.isInteger(roleid)){
			info.put("info", "0");
			info.put("textinfo", "提交参数异常");
			return JSONObject.fromObject(info).toString();
		}
		User user=(User)LoginController.getSession().getAttribute("user");
		String cusid=user.getCusid();
		String userid=user.getUserid();
		List<String> lt=new ArrayList<String>();
		
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		lt.clear();lt.add(cusid);lt.add(roleid);
		
		String sqlt="";
		if(userid.equals("1"))
			sqlt="select m.*,m.htmlname text,IFNULL(r.menuid,'') ischeck from menu m left join menurole"
					+ " r on r.menuid=m.id and r.cusid=? and r.roleid=? order by m.type,m.order";
		else
			sqlt="select m.*,m.htmlname text,IFNULL(r.menuid,'') ischeck from menu m left join menurole"
					+ " r on r.menuid=m.id and r.cusid=? and r.roleid=? where m.id<>3 and m.parentid<>3 order by m.type,m.order";
		
		String title[]={"id","parentid","type"};
		List<Map<String, String>> treemapss=tservice.getSelect(sqlt,lt,title);
		Map<String, String> idpidmp=new HashMap<String, String>();
		for (Map<String, String> map : treemapss) {
			if(!map.get("type").trim().equals("1")&&!map.get("type").trim().equals("0")){
				idpidmp.put(map.get("id"), map.get("parentid"));
			}
		}
		Map<String, String> pidmp=new HashMap<String, String>();
		for (String id : _menuids) {
			if(idpidmp.containsKey(id)){
				pidmp.put(idpidmp.get(id),"");
				pidmp.put(id,"");
			}
		}
		
		lt.clear();lt.add(roleid);lt.add(cusid);
		String d_sql="delete from menurole where roleid=? and cusid=?";
		tservice.OpenTransaction();
		if(tservice.DeleteSQL(d_sql,lt)){
			
			for (String id : _menuids) {
				if(pidmp.containsKey(id)){
					lt.clear();
					lt.add(roleid);lt.add(id);lt.add(cusid);
					String sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
					r=tservice.InsertSQL2(sql,lt);
					if(r==0)break;
				} 
			}
			
			if(r==0){
				tservice.rollbackExe_close();
				info.put("info", "0");
				info.put("textinfo", "提交失败");
				return JSONObject.fromObject(info).toString();
			}else{
				tservice.commitExe_close();
				info.put("info", "1");
				return JSONObject.fromObject(info).toString();
			}
		}else{
			tservice.rollbackExe_close();
			info.put("info", "0");
			info.put("textinfo", "提交失败");
			return JSONObject.fromObject(info).toString();
		}
		
		
	}
}
