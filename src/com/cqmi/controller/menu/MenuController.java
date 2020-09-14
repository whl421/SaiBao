package com.cqmi.controller.menu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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
import com.cqmi.db.service.BeanService_Transaction;

//import org.apache.commons.collections.map.ListOrderedMap;

@Controller
public class MenuController {
	
	/**
	 * 查看菜单
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/menutable",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView menutable(String parentid,String textinfo) throws IOException, SQLException{
		if(textinfo != null&&!textinfo.trim().equals("")) {
			textinfo =new String(textinfo.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		if(parentid==null||parentid.trim().equals(""))parentid="1"; 
		
		
		User user=((User)LoginController.getSession().getAttribute("user"));
		/**
		 * 权限判定
		 */
		List<Map<String, String>> bpower=user.getBpower("menutable");   //tservice.getSelect(G_b_sql);
		/**
		 * 1.确认当前模块权限，
		 * 2.获取按钮权限
		 */
		boolean boo=false;
		Map mb=new HashMap();
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals("menutable"))boo=true;
			if(!map.get("type").equals("2"))mb.put(map.get("htmlcode"), "1");
		}
		
		
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.addObject("button", mb);
		if(!boo){
			modelAndView.setViewName("nopower");
			return modelAndView;
		}
			 
			
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		String sql="select * from menu where parentid='"+parentid+"' order by menu.order ";
//		String sqlt="select *,htmlname text from menu order by menu.order";
//		String title[]={"id","text","parentid","type"};
		String ptype="";
		List<Map<String, String>> menutable=tservice.getSelect(sql);
		
		
		String sqlt="select *,htmlname text from menu order by menu.order";
		String title[]={"id","text","parentid","type"};
		List<Map<String, String>> treemaps=tservice.getSelect(sqlt,title);
		
		tservice.Close();
		
		List<Map> treemap=new ArrayList<Map>();
		
		List<Integer> index=new ArrayList<Integer>();
		/**
		 * 装入父节点
		 */
		for(int i=0;i<treemaps.size();i++){
			String pid=treemaps.get(i).get("parentid");
			if(treemaps.get(i).get("id").equals(parentid))ptype=(String) treemaps.get(i).get("type");
			if(pid.trim().equals("0")){
				treemap.add(treemaps.get(i));
				index.add(i);
			}
		}
		for(int i=index.size()-1;i>=0;i--){
			treemaps.remove(index.get(i));
		}
		
		
		boolean tree=true;
		if(treemap.size()==0||treemaps.size()==0)tree=false;
		
		List<Map> idmap=new ArrayList<Map>();
		List<Map> idmaps=new ArrayList<Map>();
		while(tree){
			index.clear();
			if(idmap.size()==0&&idmaps.size()==0){
				for (Map map : treemap) {
					String id=(String) map.get("id");
					List<Map> nodes=new ArrayList<Map>();
					for(int i=0;i<treemaps.size();i++){
						String pid=(String) treemaps.get(i).get("parentid");
						if(pid.trim().equals(id)){
							idmap.add(treemaps.get(i));
							nodes.add(treemaps.get(i));
							index.add(i);
						}
					}
					if(nodes.size()>0){
						map.put("nodes", nodes);
					}
				}
			}else
			if(idmap.size()>0&&idmaps.size()==0){
				for (Map map : idmap) {
					String id=(String) map.get("id");
					List<Map> nodes=new ArrayList<Map>();
					for(int i=0;i<treemaps.size();i++){
						String pid=(String) treemaps.get(i).get("parentid");
						if(pid.trim().equals(id)){
							idmaps.add(treemaps.get(i));
							nodes.add(treemaps.get(i));
							index.add(i);
						}
					}
					if(nodes.size()>0){
						map.put("nodes", nodes);
					}
				}
				idmap.clear();
			}else
			if(idmap.size()==0&&idmaps.size()>0){
				for (Map map : idmaps) {
					String id=(String) map.get("id");
					List<Map> nodes=new ArrayList<Map>();
					for(int i=0;i<treemaps.size();i++){
						String pid=(String) treemaps.get(i).get("parentid");
						if(pid.trim().equals(id)){
							idmap.add(treemaps.get(i));
							nodes.add(treemaps.get(i));
							index.add(i);
						}
					}
					if(nodes.size()>0){
						map.put("nodes", nodes);
					}
				}
				idmaps.clear();
			}
			
			if(index.size()==0)
				tree=false;
			else{
				for(int i=index.size()-1;i>=0;i--){
					treemaps.remove(index.get(i));
				}
			}
			 
		} 
		
		// treemap 树形对象
		
		
		JSONArray jsonarray=new JSONArray();
		
		if(textinfo!=null)modelAndView.addObject("textinfo", textinfo);
		modelAndView.addObject("menutable", jsonarray.fromObject(menutable));
		modelAndView.addObject("menutree", jsonarray.fromObject(treemap));
		modelAndView.addObject("parentid", parentid); 
		modelAndView.addObject("ptype", ptype); 
		modelAndView.setViewName("menurole/menu/menu");
		
		return modelAndView;
	}
	
	
	/**
	 *新增菜单
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/addmenu",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView addmenu(String tablejson) throws IOException, SQLException{
		System.out.println(tablejson);
		if(tablejson != null&&!tablejson.trim().equals("")) {
			System.out.println(new String(tablejson.getBytes("ISO8859-1"), "UTF-8"));
			System.out.println(new String(tablejson.getBytes("UTF-8"), "UTF-8"));
			System.out.println(new String(tablejson.getBytes("GBK"), "UTF-8"));
			System.out.println(java.net.URLDecoder.decode(tablejson,"utf-8"));
			
			tablejson =new String(tablejson.getBytes("ISO8859-1"), "UTF-8"); // ; 
		}
		System.out.println(tablejson);
//		System.out.println(tablejson+">>>>>>>>>>>>>>>");
		JSONObject jsonObject=new JSONObject();
		/**
		 * tablejson 数据
		 */
		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();
		String insert_sql="insert into menu (htmlcode,htmlname,urlaction,icon,parentid,`order`,type) value (?,?,?,?,?,?,?)";
		String htmlcode=json.optString("htmlcode");
		String htmlname=json.optString("htmlname");
		String urlaction=json.optString("urlaction");
		String icon=json.optString("icon");
		
		String parentid=json.optString("parentid");
		String order=json.optString("order");
		String type=json.optString("type");
		//注意参数顺序
		lt.add(htmlcode);lt.add(htmlname);lt.add(urlaction);lt.add(icon);
		lt.add(parentid);lt.add(order);lt.add(type);
		
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		tservice.OpenTransaction();
		int r=tservice.InsertSQL2(insert_sql, lt);
		if(r==0)
			tservice.rollbackExe();
		else
			tservice.commitExe();
		 
		tservice.Close();
		
		ModelAndView modelAndView = new ModelAndView();
		if(r==0)modelAndView.addObject("textinfo", "新增失败");
		modelAndView.addObject("parentid", parentid);
		
		modelAndView.setViewName("redirect:/menu/menutable.action");
		
		return modelAndView;
	}
	
	/**
	 *编辑菜单
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/updatemenu",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView updatemenu(String actjson) throws IOException, SQLException{
		if(actjson != null&&!actjson.trim().equals("")) {
			actjson =new String(actjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		JSONObject jsonObject=new JSONObject();
		/**
		 * tablejson 数据
		 */
		JSONObject json=jsonObject.fromObject(actjson);
		List<String> lt=new ArrayList<String>();
		String insert_sql="update menu set menu.htmlcode=?,menu.htmlname=?,menu.urlaction=?,menu.icon=?,menu.parentid=?,menu.`order`=?,menu.type=? where id=?";
		
		String htmlcode=json.optString("htmlcode");
		String htmlname=json.optString("htmlname");
		String urlaction=json.optString("urlaction");
		String icon=json.optString("icon");
		String parentid=json.optString("parentid");
		String order=json.optString("order");
		String type=json.optString("type");
		String ids=json.optString("id");
		//注意参数顺序
		lt.add(htmlcode);lt.add(htmlname);lt.add(urlaction);lt.add(icon);
		lt.add(parentid);lt.add(order);lt.add(type);lt.add(ids);
		
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		tservice.OpenTransaction();
		int r=tservice.UpdateSQL2(insert_sql, lt);
		if(r==0)
			tservice.rollbackExe();
		else
			tservice.commitExe();
		
		tservice.Close();
		
		
		ModelAndView modelAndView = new ModelAndView();
		if(r==0)modelAndView.addObject("textinfo", "编辑失败");
		modelAndView.addObject("parentid", parentid);
		
		modelAndView.setViewName("redirect:/menu/menutable.action");
		
		return modelAndView;
	}
	
	
	/**
	 *删除菜单
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/removemenu",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView removemenu(String tablejson) throws IOException, SQLException{
		if(tablejson != null&&!tablejson.trim().equals("")) {
			tablejson =new String(tablejson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		JSONObject jsonObject=new JSONObject();
		/**
		 * tablejson 数据
		 */
		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();
		String delete_sql="delete from menu where id=?";
		//注意参数顺序
		String ids=json.optString("id");
		String parentid=json.optString("parentid");
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		tservice.OpenTransaction();
		String sqlt="select *,htmlname text from menu order by menu.order";
		String title[]={"id","text","parentid","type"};
		List<Map<String, String>> treemaps=tservice.getSelect(sqlt,title);
		
		Map<String, String> keyid=new HashMap<String, String>();
		boolean boos=true;
		int r=0;
		while(boos){
			int i=0;
			for (Map<String, String> map : treemaps) {
				if(map.get("parentid").equals(ids)||keyid.containsKey(map.get("parentid"))){
					keyid.put(map.get("id"), "1");
					i++;
					lt.add(0, map.get("id"));
					System.out.println("lt.size()="+lt.size());
					r=tservice.DeleteSQL2(delete_sql, lt);
					if(r==0){
						tservice.rollbackExe();
						boos=false;
						break;
					}
				}
			}
			if(i==0)boos=false;
		}
		
		lt.add(0, ids);
		System.out.println("lt.size()="+lt.size());
		r=tservice.DeleteSQL2(delete_sql, lt);
		if(r==0){
			tservice.rollbackExe();
		}else
			tservice.commitExe();
		
		tservice.Close();
		
		
		ModelAndView modelAndView = new ModelAndView();
		if(r==0)modelAndView.addObject("textinfo", "删除失败");
		modelAndView.addObject("parentid", parentid);
		
		modelAndView.setViewName("redirect:/menu/menutable.action");
		
		return modelAndView;
	}
	
	
	
	/**
	 *移动菜单
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/movemenu",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView movemenu(String tablejson) throws IOException, SQLException{
		if(tablejson != null&&!tablejson.trim().equals("")) {
			tablejson =new String(tablejson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		System.out.println("-----------"+tablejson);
		
		JSONObject jsonObject=new JSONObject();
		/**
		 * tablejson 数据
		 */
		JSONObject json=jsonObject.fromObject(tablejson);
		
		List<String> lt=new ArrayList<String>();
		String move_sql="update menu set `order`=? where id=?";
		//注意参数顺序
		String upid=json.optString("upid");
		String uporder=json.optString("uporder");
		String downid=json.optString("downid");
		String downorder=json.optString("downorder");
		String parentid=json.optString("parentid");
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		tservice.OpenTransaction();
		lt.add(uporder);lt.add(upid);
		int r=tservice.UpdateSQL2(move_sql, lt);
		if(r==0){
			tservice.rollbackExe();
		} 
		lt.set(0, downorder);lt.set(1, downid);
		r=tservice.UpdateSQL2(move_sql, lt);
		if(r==0){
			tservice.rollbackExe();
		}else
			tservice.commitExe();
		
		tservice.Close();
		
		
		ModelAndView modelAndView = new ModelAndView();
		if(r==0)modelAndView.addObject("textinfo", "移动失败");
		modelAndView.addObject("parentid", parentid);
		
		modelAndView.setViewName("redirect:/menu/menutable.action");
		
		return modelAndView;
	}
	
	
	
	/**
	 *新增角色
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/addrole",produces="application/json; charset=utf-8")
	@ResponseBody
	public String addrole(String tablejson) throws IOException, SQLException{
//		if(tablejson != null&&!tablejson.trim().equals("")) {
//			tablejson =new String(tablejson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
//		}
		System.out.println(tablejson);
		
		JSONObject jsonObject=new JSONObject();
		/**
		 * tablejson 数据
		 */
		JSONObject json=jsonObject.fromObject(tablejson);
		
		String rolecode=json.optString("rolecode");
		String rolename=json.optString("rolename");
		String desc=json.optString("desc");
		String cusid=((User)LoginController.getSession().getAttribute("user")).getCusid();
		
		List<String> lt=new ArrayList<String>();
		lt.add(rolecode);lt.add(rolename);lt.add(cusid);
		String s_sql="select * from role where ( rolecode=? or rolename=? ) and cusid=?";
		
		Map<String, String> info=new HashMap<String, String>();
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		List<Map<String, String>> lrole=tservice.getSelect(s_sql, lt);
		if(lrole.size()!=0){
			info.put("info", "0");
			info.put("textinfo", "编号或者名称已存在");
			tservice.Close();
			return jsonObject.fromObject(info).toString();
		}
		
		tservice.OpenTransaction();
		lt.clear();
		lt.add(rolecode);lt.add(rolename);lt.add(desc);lt.add(cusid);
		String i_sql="insert into role (rolecode,rolename,`desc`,cusid) value (?,?,?,?)";
		int r=tservice.InsertSQL2(i_sql, lt);
		if(r==0){
			info.put("info", "1");
			info.put("textinfo", "网络繁忙，请稍后");
			tservice.rollbackExe();
		}else{
			tservice.commitExe();
		}
			
		tservice.Close();
		
		return jsonObject.fromObject(info).toString();
	}
	
	
	/**
	 *修改角色
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/updaterole",produces="application/json; charset=utf-8")
	@ResponseBody
	public String updaterole(String tablejson) throws IOException, SQLException{
//		if(tablejson != null&&!tablejson.trim().equals("")) {
//			tablejson =new String(tablejson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
//		}
		
		JSONObject jsonObject=new JSONObject();
		/**
		 * tablejson 数据
		 */
		JSONObject json=jsonObject.fromObject(tablejson);
		String id=json.optString("id");
		String rolecode=json.optString("rolecode");
		String rolename=json.optString("rolename");
		String desc=json.optString("desc");
		
		List<String> lt=new ArrayList<String>();
		lt.add(rolecode);lt.add(rolename);lt.add(desc);lt.add(id);
		String u_sql="update role set rolecode=?,rolename=?,`desc`=? where id=?";
		
		Map<String, String> info=new HashMap<String, String>();
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		tservice.OpenTransaction();
		int r=tservice.UpdateSQL2(u_sql, lt);
		if(r==0){
			info.put("info", "0");
			info.put("textinfo", "服务器繁忙，请稍后");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(info).toString();
		}
		tservice.commitExe_close();
		
		return jsonObject.fromObject(info).toString();
	}
	
	
	/**
	 *删除角色
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/removerole",produces="application/json; charset=utf-8")
	@ResponseBody
	public String removerole(String tablejson) throws IOException, SQLException{
		if(tablejson != null&&!tablejson.trim().equals("")) {
			tablejson =new String(tablejson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		JSONObject jsonObject=new JSONObject();
		/**
		 * tablejson 数据
		 */
		
		tablejson=tablejson.replace("\"", "").replace("[", "").replace("]", "");
		String [] ids=tablejson.split(",");
		boolean boo=true;
		List<String> lt=new ArrayList<String>();
		String wids="(";
		for(int i=0;i<ids.length;i++){
			String id=ids[i];
			if(id!=null&&!id.trim().equals("")){
				lt.add(id);
				if(id.trim().equals("1")||id=="1"||id.trim().equals("2")||id=="2")boo=false;
				wids+="?,";
			}
		}
		Map<String, String> info=new HashMap<String, String>();
		if(!boo){
			info.put("info", "0");
			info.put("textinfo", "管理员不能删除");
			return jsonObject.fromObject(info).toString();
		}
		
		wids=wids.endsWith(",")?wids.substring(0, wids.length()-1)+")":wids+")";
		String d_sql="delete from role where id in "+wids;
		
		
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		tservice.OpenTransaction();
		int r=tservice.DeleteSQL2(d_sql, lt);
		if(r==0){
			info.put("info", "0");
			info.put("textinfo", "服务器繁忙，请稍后");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(info).toString();
		}
		
		String du_sql="delete from roleuser where roleid in "+wids;
		r=tservice.DeleteSQL2(du_sql, lt);
		
		du_sql="delete from menurole where roleid in "+wids;
		r=tservice.DeleteSQL2(du_sql, lt);
//		if(r==0){
//			info.put("info", "0");
//			info.put("textinfo", "服务器繁忙，请稍后");
//			tservice.rollbackExe_close();
//			return jsonObject.fromObject(info).toString();
//		}
		
		tservice.commitExe_close();
		
		return jsonObject.fromObject(info).toString();
	}
	
	/**
	 *得到角色列表
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/role",produces="application/json; charset=utf-8")
	@ResponseBody
	public String role() throws IOException, SQLException{
		
		String cusid=((User)LoginController.getSession().getAttribute("user")).getCusid();
		
		JSONArray jsonObject=new JSONArray();
		 
		
		List<String> lt=new ArrayList<String>();
		lt.add(cusid);
		String s_sql="select * from role where cusid=?";
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		List<Map<String, String>> lrole=tservice.getSelect(s_sql, lt);
		
		
		tservice.Close();
		
		return jsonObject.fromObject(lrole).toString();
	}
	
	
	/**
	 * 新增角色jsp
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/addrolejsp",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView addrolejsp() throws IOException, SQLException{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("menurole/roleuser/addrole");
		return modelAndView;
	}
	
	/**
	 * 编辑角色jsp
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/updaterolejsp",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView updaterolejsp() throws IOException, SQLException{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("menurole/roleuser/updaterole");
		return modelAndView;
	}
	
	/**
	 * 新增角色人员jsp
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/roleadduser",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView roleadduser(String roleid) throws IOException, SQLException{
		if(roleid != null&&!roleid.trim().equals("")) {
			roleid =new String(roleid.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		String cusid=((User)LoginController.getSession().getAttribute("user")).getCusid();
		List<String> lt=new ArrayList<String>();
		lt.add(cusid);lt.add(roleid);lt.add(cusid);
//		for (String string : lt) {
//			System.out.println(string);
//		}
		String s_sql="select * from basic_user where cusid=? and IFNULL(issa,'')<>1 and userid not in (select IFNULL(userid,'') from roleuser where roleid=? and cusid=?) order by departname";
		BeanService_Transaction tservice=new BeanService_Transaction();
		List<Map<String, String>> lmap=tservice.getSelect(s_sql,lt);
		tservice.Close();
		
		Map dataItem; // 数据库中查询到的每条记录 
		Map<String, List<Map>> resultMap = new HashMap<String, List<Map>>(); // 最终要的结果 
		for(int i=0; i<lmap.size(); i++){ 
			
		    dataItem = lmap.get(i); 
		    if(resultMap.containsKey(dataItem.get("departname"))){ 

		        resultMap.get(dataItem.get("departname")).add(dataItem); 
		    }else{ 
		        List<Map> list = new ArrayList<Map>(); 
		        list.add(dataItem); 
		        resultMap.put((String)dataItem.get("departname"),list); 
		    } 
		}
		
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.addObject("sluser",resultMap);
		modelAndView.addObject("roleid",roleid);
		modelAndView.setViewName("menurole/roleuser/roleadduser");
		return modelAndView;
	}
	
	
	
	/**
	 * 角色人员新增
	 * value="user/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="menu/roleuseradd",produces="application/json; charset=utf-8")
	@ResponseBody
	public String roleuseradd(String tablejson) throws UnsupportedEncodingException{
		JSONObject jsonObject=new JSONObject();
		/**
		 * tablejson 数据
		 */
		JSONObject json=jsonObject.fromObject(tablejson);
		String userid=json.optString("userid");
		String roleid=json.optString("roleid");
		
		
		System.out.println("角色人员新增有"+userid);
		String cusid=((User)LoginController.getSession().getAttribute("user")).getCusid();
		List<String> lsql=new ArrayList<String>();
		String[] ils=userid.replace("[", "").replace("]", "").replace("\"", "").split(",");
		
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		Map map=new HashMap();
		//开起事物
		tservice.OpenTransaction();
		int r=0;
		for (String st : ils) {
			if(st!=null&&!st.trim().equals(""))
			{	
				List<String> lt=new ArrayList<String>();
				lt.add(st);lt.add(roleid);lt.add(cusid);
				r=tservice.InsertSQL2("insert into roleuser (userid,roleid,cusid) value (?,?,?)", lt);
				if(r==0){
					tservice.rollbackExe_close();
					map.put("info", "0");
					map.put("textinfo", "服务器繁忙！请稍后");
					break;
				}
			}
		}
		 
		if(r==0)
			return new JSONObject().fromObject(map).toString();
		else
			tservice.commitExe_close();
		
		 
		
	    return new JSONObject().fromObject(map).toString();
		
	}
	
	/**
	 * 获取角色人员
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/roleuser",produces="application/json; charset=utf-8")
	@ResponseBody
	public String roleuser(String roleid,String type,String roleuserid) throws IOException, SQLException{
		if(roleid != null&&!roleid.trim().equals("")) {
			roleid =new String(roleid.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		if(type != null&&!type.trim().equals("")) {
			type =new String(type.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		if(roleuserid != null&&!roleuserid.trim().equals("")) {
			roleuserid =new String(roleuserid.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		//仅仅是刷新
//		if(type.equals("refresh")){
//			
//		}
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		String cusid=((User)LoginController.getSession().getAttribute("user")).getCusid();
		//删数据刷新
		if(type.equals("remove")){
			List<String> lt=new ArrayList<String>();
			lt.add(roleuserid);
			String d_sql="delete from roleuser where id=?";
			tservice.OpenTransaction();
			int r=tservice.DeleteSQL2(d_sql, lt);
			if(r==0)
				tservice.rollbackExe();
			else
				tservice.commitExe();
		}
		
		List<String> lt=new ArrayList<String>();
		lt.add(cusid);lt.add(roleid);
		String s_sql="select r.id,u.userid,u.departname,u.usercode,u.username,u.sex,u.pname from basic_user u left join roleuser r on u.userid=r.userid" +
				" where u.cusid=? and r.roleid=? and IFNULL(u.issa,'')<>1  order by u.departname";
		String [] title={"id","userid","departname","usercode","username","sex","pname"};
		List<Map<String, String>> lmap=tservice.getSelect(s_sql,lt);
		tservice.Close();
		
		
		JSONArray jsonarray=new JSONArray();
		 
		return jsonarray.fromObject(lmap).toString();
	}
	
	
	
	/**
	 * 角色人员jsp
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/roleuserjsp",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView roleuserjsp() throws IOException, SQLException{
		
		User user= ((User)LoginController.getSession().getAttribute("user"));
		
		/**
		 * 权限判定
		 */
		List<Map<String, String>> bpower=user.getBpower("roleuserjsp");   //tservice.getSelect(G_b_sql);
		/**
		 * 1.确认当前模块权限，
		 * 2.获取按钮权限
		 */
		boolean boo=false;
		Map mb=new HashMap();
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals("roleuserjsp"))boo=true;
			if(!map.get("type").equals("2"))mb.put(map.get("htmlcode"), "1");
		}
		
		
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.addObject("button", mb);
		if(boo)
			modelAndView.setViewName("menurole/roleuser/roleuser");
		else
			modelAndView.setViewName("nopower");
		return modelAndView;
	}
	
	
	/**
	 * 角色权限设置
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/rolemenujsp",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView rolemenujsp() throws IOException, SQLException{
		User user= ((User)LoginController.getSession().getAttribute("user"));
		String cusid=user.getCusid();
//		String Userid=user.getUserid();
//		
		List<String> lt=new ArrayList<String>();
		lt.add(cusid);
		String s_sql="select * from role where cusid=? ";
		BeanService_Transaction tservice=new BeanService_Transaction();
//		/**
//		 * 权限判定
//		 */
		List<Map<String, String>> bpower=user.getBpower("rolemenujsp");		//tservice.getSelect(G_b_sql);
//		/**
//		 * 1.确认当前模块权限，
//		 * 2.获取按钮权限
//		 */
		boolean boo=false;
		Map mb=new HashMap();
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals("rolemenujsp"))boo=true;
			if(!map.get("type").equals("2"))mb.put(map.get("htmlcode"), "1");
			 
		}
//		
		
		List<Map<String, String>> lmap=tservice.getSelect(s_sql,lt);
		
		tservice.Close();
		
		 
		ModelAndView modelAndView = new ModelAndView();
		if(boo){
			modelAndView.addObject("userMap", lmap);
			modelAndView.addObject("button", mb);
			modelAndView.setViewName("menurole/rolemenu/rolemenu");
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
	@RequestMapping(value="menu/rolemenu",produces="application/json; charset=utf-8")
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
		 
//		String s_sql="select * from menu where id not in (select IFNULL(menuid,'') from menurole where cusid=? and roleid=?) order by menu.order";
		
//		String s_sql="select IFNULL(menuid,'') from menurole where cusid=? and roleid=?";
		BeanService_Transaction tservice=new BeanService_Transaction();
//		List<Map<String, String>> lmap=tservice.getSelect(s_sql,lt);
//		
		
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
						+ " r on r.menuid=m.id left join menurole re on re.menuid=m.id and re.roleid=? where m.id<>3 and m.parentid<>3 and r.roleid=? order by m.type,m.order";
			}
		}
//			sqlt="select m.*,m.htmlname text,IFNULL(r.menuid,'') ischeck from menu m left join menurole"
//					+ " r on r.menuid=m.id and r.cusid=? and r.roleid=? where m.id<>3 and m.parentid<>3 order by m.type,m.order";
//		
		
		
		String title[]={"id","text","parentid","ischeck"};
		List<Map<String, String>> treemapss=tservice.getSelect(sqlt,lt,title);
		List<Map> treemaps=new ArrayList<Map>();
		for (Map map : treemapss) {
			treemaps.add(map);
		}
		tservice.Close();
		
	//	List<String> ltcheck=new ArrayList<String>();
		
		List<Map> treemap=new ArrayList<Map>();
		 
		
		List<Integer> index=new ArrayList<Integer>();
		/**
		 * 装入父节点
		 */
		Map s=new HashMap();
		s.put("checked", true);
		for(int i=0;i<treemaps.size();i++){
			String ischeck=(String) treemaps.get(i).get("ischeck");
			if(ischeck!=null&&!ischeck.trim().equals("")){
		//		ltcheck.add(ischeck);
				treemaps.get(i).put("state", s);
			}
			String pid=(String) treemaps.get(i).get("parentid");
			if(pid.trim().equals("0")){
				treemap.add(treemaps.get(i));
				index.add(i);
			}
		}
		for(int i=index.size()-1;i>=0;i--){
			treemaps.remove(index.get(i));
		}
		
		
		boolean tree=true;
		if(treemap.size()==0||treemaps.size()==0)tree=false;
		
		List<Map> idmap=new ArrayList<Map>();
		List<Map> idmaps=new ArrayList<Map>();
		while(tree){
			index.clear();
			if(idmap.size()==0&&idmaps.size()==0){
				for (Map map : treemap) {
					String id=(String) map.get("id");
					List<Map> nodes=new ArrayList<Map>();
					for(int i=0;i<treemaps.size();i++){
						String pid=(String) treemaps.get(i).get("parentid");
						if(pid.trim().equals(id)){
							idmap.add(treemaps.get(i));
							nodes.add(treemaps.get(i));
							index.add(i);
						}
					}
					if(nodes.size()>0){
						map.put("nodes", nodes);
					}
				}
			}else
			if(idmap.size()>0&&idmaps.size()==0){
				for (Map map : idmap) {
					String id=(String) map.get("id");
					List<Map> nodes=new ArrayList<Map>();
					for(int i=0;i<treemaps.size();i++){
						String pid=(String) treemaps.get(i).get("parentid");
						if(pid.trim().equals(id)){
							idmaps.add(treemaps.get(i));
							nodes.add(treemaps.get(i));
							index.add(i);
						}
					}
					if(nodes.size()>0){
						map.put("nodes", nodes);
					}
				}
				idmap.clear();
			}else
			if(idmap.size()==0&&idmaps.size()>0){
				for (Map map : idmaps) {
					String id=(String) map.get("id");
					List<Map> nodes=new ArrayList<Map>();
					for(int i=0;i<treemaps.size();i++){
						String pid=(String) treemaps.get(i).get("parentid");
						if(pid.trim().equals(id)){
							idmap.add(treemaps.get(i));
							nodes.add(treemaps.get(i));
							index.add(i);
						}
					}
					if(nodes.size()>0){
						map.put("nodes", nodes);
					}
				}
				idmaps.clear();
			}
			
			if(index.size()==0)
				tree=false;
			else{
				for(int i=index.size()-1;i>=0;i--){
					treemaps.remove(index.get(i));
				}
			}
			 
		} 
		
		
		// treemap 树形对象   ltcheck
		/**
		 * 移除最高节点
		 */
		List<Map> trees=new ArrayList<Map>();
		for (Map map : treemap) {
			List<Map<String, Object>> nodes=(List<Map<String, Object>>) map.get("nodes");
			if(nodes!=null){
				trees.addAll(nodes);
			}
		}
		
		Map remap=new HashMap();
		remap.put("tree", trees);
	//	remap.put("ltcheck", ltcheck);
		JSONObject jsonobject=new JSONObject(); 
		
		 
		return jsonobject.fromObject(remap).toString();
	}
	
	
	/**
	 * 角色权限设置 权限列表
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="menu/setrolemenu",produces="application/json; charset=utf-8")
	@ResponseBody
	public String setrolemenu(String tablejson) throws IOException, SQLException{
		if(tablejson != null&&!tablejson.trim().equals("")) {
			tablejson =new String(tablejson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		JSONObject jsonobject=new JSONObject();
		
		JSONObject table=jsonobject.fromObject(tablejson);
		
		String roleid=table.optString("roleid");
		String menuid=table.optString("menuid");
		/**
		 * 1 添加权限 0是取消权限
		 */
		String type=table.optString("type");
		
		User user=(User)LoginController.getSession().getAttribute("user");
		String cusid=user.getCusid();
		String userid=user.getUserid();
		
		List<String> lt=new ArrayList<String>();
//		lt.add(cusid);lt.add(roleid); 
		Map remap=new HashMap();
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(type.trim().equals("1")){
			lt.add(menuid);
			String g_sql="select parentid from menu where id=?";
			List<Map<String, String>> pmap=tservice.getSelect(g_sql,lt);
			String parentid=pmap.get(0).get("parentid");
			if(parentid.equals("0")||parentid.equals("1")){
				lt.clear();
				lt.add(roleid);lt.add(menuid);lt.add(cusid);
				String s_sql="select * from menurole where roleid=? and menuid=? and cusid=?";
				List<Map<String, String>> lmap=tservice.getSelect(s_sql,lt);
				if(lmap.size()==0){
					String i_sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
					tservice.OpenTransaction();
					int r=tservice.InsertSQL2(i_sql, lt);
					if(r==0){
						tservice.rollbackExe_close();
						remap.put("info", "0");
						remap.put("textinfo", "服务器繁忙，请稍后");
						return jsonobject.fromObject(remap).toString();
					}
					
					tservice.commitExe_close();
					return jsonobject.fromObject(remap).toString();
				}
			}else{
				lt.clear();
				lt.add(parentid);
				String gg_sql="select parentid from menu where id=?";
				List<Map<String, String>> ppmap=tservice.getSelect(gg_sql,lt);
				String parentids=ppmap.get(0).get("parentid");
				lt.clear();
				lt.add(roleid);lt.add(menuid);lt.add(parentid);lt.add(parentids);lt.add(cusid);
				String s_sql="select menuid from menurole where roleid=? and ( menuid=? or menuid=? or menuid=? ) and cusid=?";
				List<Map<String, String>> lmap=tservice.getSelect(s_sql,lt);
				if(lmap.size()==0){
					lt.clear();
					lt.add(roleid);lt.add(menuid);lt.add(cusid);
					String i_sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
					tservice.OpenTransaction();
					int r=tservice.InsertSQL2(i_sql, lt);
					if(r==0){
						tservice.rollbackExe_close();
						remap.put("info", "0");
						remap.put("textinfo", "服务器繁忙，请稍后");
						return jsonobject.fromObject(remap).toString();
					}
					lt.clear();
					lt.add(roleid);lt.add(parentid);lt.add(cusid);
					i_sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
					r=tservice.InsertSQL2(i_sql, lt);
					if(r==0){
						tservice.rollbackExe_close();
						remap.put("info", "0");
						remap.put("textinfo", "服务器繁忙，请稍后");
						return jsonobject.fromObject(remap).toString();
					}
					lt.clear();
					lt.add(roleid);lt.add(parentids);lt.add(cusid);
					i_sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
					r=tservice.InsertSQL2(i_sql, lt);
					if(r==0){
						tservice.rollbackExe_close();
						remap.put("info", "0");
						remap.put("textinfo", "服务器繁忙，请稍后");
						return jsonobject.fromObject(remap).toString();
					}
					
					tservice.commitExe_close();
					remap.put("info", "5");
					remap.put("lid1", menuid);
					remap.put("lid2", parentids);
					remap.put("lid3", parentid);
					return jsonobject.fromObject(remap).toString();
					
				}
				
				if(lmap.size()==1){
					String ckid=lmap.get(0).get("menuid");
					tservice.OpenTransaction();
					if(!menuid.equals(ckid)){
						lt.clear();
						lt.add(roleid);lt.add(menuid);lt.add(cusid);
						String i_sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
						int r=tservice.InsertSQL2(i_sql, lt);
						if(r==0){
							tservice.rollbackExe_close();
							remap.put("info", "0");
							remap.put("textinfo", "服务器繁忙，请稍后");
							return jsonobject.fromObject(remap).toString();
						}
					}
					if(!parentid.equals(ckid)){
						lt.clear();
						lt.add(roleid);lt.add(parentid);lt.add(cusid);
						String i_sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
						int r=tservice.InsertSQL2(i_sql, lt);
						if(r==0){
							tservice.rollbackExe_close();
							remap.put("info", "0");
							remap.put("textinfo", "服务器繁忙，请稍后");
							return jsonobject.fromObject(remap).toString();
						}
					}
					if(!parentids.equals(ckid)){
						lt.clear();
						lt.add(roleid);lt.add(parentids);lt.add(cusid);
						String i_sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
						int r=tservice.InsertSQL2(i_sql, lt);
						if(r==0){
							tservice.rollbackExe_close();
							remap.put("info", "0");
							remap.put("textinfo", "服务器繁忙，请稍后");
							return jsonobject.fromObject(remap).toString();
						}
					}
					tservice.commitExe_close();
					remap.put("info", "5");
					remap.put("lid1", menuid);
					remap.put("lid2", parentids);
					remap.put("lid3", parentid);
					return jsonobject.fromObject(remap).toString();
				}
				
				if(lmap.size()==2){
					String ckid=lmap.get(0).get("menuid");
					String cjid=lmap.get(1).get("menuid");
					tservice.OpenTransaction();
					if(!menuid.equals(ckid)&&!menuid.equals(cjid)){
						lt.clear();
						lt.add(roleid);lt.add(menuid);lt.add(cusid);
						String i_sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
						int r=tservice.InsertSQL2(i_sql, lt);
						if(r==0){
							tservice.rollbackExe_close();
							remap.put("info", "0");
							remap.put("textinfo", "服务器繁忙，请稍后");
							return jsonobject.fromObject(remap).toString();
						}
					}
					if(!parentid.equals(ckid)&&!parentid.equals(cjid)){
						lt.clear();
						lt.add(roleid);lt.add(parentid);lt.add(cusid);
						String i_sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
						int r=tservice.InsertSQL2(i_sql, lt);
						if(r==0){
							tservice.rollbackExe_close();
							remap.put("info", "0");
							remap.put("textinfo", "服务器繁忙，请稍后");
							return jsonobject.fromObject(remap).toString();
						}
					}
					if(!parentids.equals(ckid)&&!parentids.equals(cjid)){
						lt.clear();
						lt.add(roleid);lt.add(parentids);lt.add(cusid);
						String i_sql="insert into menurole (roleid,menuid,cusid) value (?,?,?)";
						int r=tservice.InsertSQL2(i_sql, lt);
						if(r==0){
							tservice.rollbackExe_close();
							remap.put("info", "0");
							remap.put("textinfo", "服务器繁忙，请稍后");
							return jsonobject.fromObject(remap).toString();
						}
					}
					tservice.commitExe_close();
					remap.put("info", "5");
					remap.put("lid", "["+menuid+","+parentids+","+parentid+"]");
					return jsonobject.fromObject(remap).toString();
					
				}
				
			}
			
			return jsonobject.fromObject(remap).toString();
		}
		
		if(type.trim().equals("0")){
			lt.add(roleid);lt.add(menuid);lt.add(cusid);
			String s_sql="select * from menurole where roleid=? and menuid=? and cusid=?";
			List<Map<String, String>> lmap=tservice.getSelect(s_sql,lt);
			if(lmap.size()!=0){
				String d_sql="delete from menurole where roleid=? and menuid=? and cusid=?";
				tservice.OpenTransaction();
				int r=tservice.InsertSQL2(d_sql, lt);
				if(r==0){
					tservice.rollbackExe_close();
					remap.put("info", "0");
					remap.put("textinfo", "服务器繁忙，请稍后");
					return jsonobject.fromObject(remap).toString();
				}
				
				tservice.commitExe_close();
				return jsonobject.fromObject(remap).toString();
			}
			return jsonobject.fromObject(remap).toString();
		}
		
		remap.put("info", "0");
		remap.put("textinfo", "网络异常，联系终极管理员");
		return jsonobject.fromObject(remap).toString();
	}
	
	
}
