package com.cqmi.controller.login;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.coobird.thumbnailator.builders.BufferedImageBuilder;
import net.sf.json.JSONArray;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Decoder;

import com.cqmi.controller.login.bean.User;
import com.cqmi.controller.login.bean.UserBean;
import com.cqmi.controller.login.util.RoleMenuUtil;
import com.cqmi.dao.util.Util;
import com.cqmi.db.dao.Loggerlog;
import com.cqmi.db.service.BeanService_Transaction;
import com.cqmi.db.util.ParaUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
public class LoginController {
	
	/**
	 * 查看菜单
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/user",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView user(String usecode,String password,String num) throws IOException, SQLException{
		String _password=password;
		if(usecode!=null){
			System.out.println("----------"+usecode);
			//usecode =new String(usecode.getBytes("ISO8859-1"), "UTF-8"); 
		}
		if(password!=null){
			password =new String(password.getBytes("ISO8859-1"), "UTF-8"); 
			password=ParaUtil.getResult(password);
		}
		if(num!=null){
			num =new String(num.getBytes("ISO8859-1"), "UTF-8"); 
		}else num="0";
		
		System.out.println("------------------------");
		System.out.println("usecode="+usecode+",password="+password+",num:"+num);
		System.out.println("------------------------");
		BeanService_Transaction tservice=new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {	
			User user = (User) getSession().getAttribute("user");
			if(user!=null){
				getSession().removeAttribute("user");
				//modelAndView.setViewName("manageIntegral/index");
//				modelAndView.setViewName("redirect:/login/index.action");
//				return modelAndView;
				
//				modelAndView.addObject("textinfo", "当前客户端用户未退出");
//				modelAndView.addObject("info", "1");
//				modelAndView.addObject("usecode", usecode);
//				modelAndView.addObject("password", _password);
//				modelAndView.setViewName("manageIntegral/login");
//				return modelAndView;
			}
			
			if(usecode==null||usecode.trim().equals("")){
				modelAndView.addObject("num", "0");
				modelAndView.setViewName("manageIntegral/login");
				return modelAndView;
			}
			
			//获取当前时间
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
			String nowdate = tempDate.format(new java.util.Date());
			
			String cusid = "";
			String dqdate = "";
			String hytype = "";
			String state = "";
			List<String> lt=new ArrayList<String>();
			lt.add(password);lt.add(usecode);lt.add(usecode);
			
			String user_sql = " select c.cusname,c.hytype,c.dqdate,u.* from basic_user u "
						    + " left join basic_customer c on c.cusid = u.cusid "
						    + " where u.pwd = ? and (u.phone = ? or u.emall = ?) ";
			List<UserBean> umap=tservice.getSelect(user_sql,lt,UserBean.class);
			
			if(umap.size()==0){
				tservice.Close();
				modelAndView.addObject("num", Integer.parseInt(num)+1);
				modelAndView.addObject("textinfo", "用户名或密码不对");
				modelAndView.addObject("info", "1");
				modelAndView.addObject("usecode", usecode);
				modelAndView.addObject("password", _password);
				modelAndView.setViewName("manageIntegral/login");
				return modelAndView;
			}else if(umap.size()>1){
				List<Map<String, String>> userlist=new ArrayList<Map<String,String>>();
				for (UserBean userbean : umap) {
					Map<String, String> map=new HashMap<String, String>();
					map.put("cusid", userbean.getCusid());
					map.put("cusname", userbean.getCusname());
					userlist.add(map);
				}
				modelAndView.addObject("uselist", JSONArray.fromObject(userlist));
				modelAndView.addObject("info", "2");
				modelAndView.addObject("usecode", usecode);
				modelAndView.addObject("password", _password);
				modelAndView.setViewName("manageIntegral/login");
				return modelAndView;
			}else{
				cusid = umap.get(0).getCusid();
				dqdate = umap.get(0).getDqdate();
				hytype = umap.get(0).getHytype();
				state = umap.get(0).getState();
			}
			if(state == "0" || "0".equals(state)) {
//				tservice.Close();
				modelAndView.addObject("num", Integer.parseInt(num)+1);
				modelAndView.addObject("textinfo", "该用户账号已被冻结，请联系管理员重新开通");
				modelAndView.setViewName("manageIntegral/login");
				return modelAndView;
			}
//			if(nowdate.compareTo(dqdate) > 0) {
////				tservice.Close();
//				modelAndView.addObject("num", Integer.parseInt(num)+1);
//				modelAndView.addObject("textinfo", "会员已到期，请充值后重新登陆");
//				modelAndView.setViewName("manageIntegral/login");
//				return modelAndView;
//			}
			
			if(umap.size()==1){
				String G_b_sql="select m.* from menu m left join menurole mr on m.id=mr.menuid left join roleuser ru on ru.roleid=mr.roleid" +
						" where ru.userid="+umap.get(0).getUserid();//+" and ru.cusid="+cusid;
				/**
				 * 权限判定
				 */
				 List<Map<String, String>> bpower=tservice.getSelect(G_b_sql);
				 if(_password.equals("123456")){
					 //初始密码提醒修改
					 Map<String, String> cmp=new HashMap<String, String>();
					 cmp.put("htmlcode", "-11111");
					 cmp.put("iscspwd", "1");
					 cmp.put("parentid", "-11111");
					 cmp.put("id", "iscspwd");
					 bpower.add(0, cmp);
				 }else{
					 Map<String, String> cmp=new HashMap<String, String>();
					 cmp.put("htmlcode", "-11111");
					 cmp.put("iscspwd", "0");
					 cmp.put("parentid", "-11111");
					 cmp.put("id", "iscspwd");
					 bpower.add(0, cmp);
				 }
				
				 getSession().setAttribute("user", new User(bpower,umap.get(0)));  
				 
				
				 
				 modelAndView.setViewName("redirect:/login/index");
			}else{
				 modelAndView.addObject("num", Integer.parseInt(num)+1);
				 modelAndView.addObject("textinfo", "用户权限获取失败");
				 modelAndView.setViewName("manageIntegral/login");
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
	 * 查看菜单  选择客户
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/usercusid",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView usercusid(String usecodecusid,String passwordcusid,String cusidcusid){
		String _password=passwordcusid,
				password=passwordcusid,
				usecode=usecodecusid,
				cusid=cusidcusid,
				num="";
		try {
			if(usecode!=null){
				System.out.println("----------"+usecode);
			}
			if(password!=null){
				password =new String(password.getBytes("ISO8859-1"), "UTF-8"); 
				password=ParaUtil.getResult(password);
			}
			if(num!=null){
				num =new String(num.getBytes("ISO8859-1"), "UTF-8"); 
			}else num="0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("------------------------");
		System.out.println("usecode="+usecode+",password="+password+",num:"+num);
		System.out.println("------------------------");
		BeanService_Transaction tservice=new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {	
			User user = (User) getSession().getAttribute("user");
			if(user!=null){
				getSession().removeAttribute("user");
			}
			
			if(usecode==null||usecode.trim().equals("")){
				modelAndView.addObject("num", "0");
				modelAndView.setViewName("manageIntegral/login");
				return modelAndView;
			}
			
			//获取当前时间
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
			String nowdate = tempDate.format(new java.util.Date());
			
			String dqdate = "";
			String hytype = "";
			String state = "";
			List<String> lt=new ArrayList<String>();
			lt.add(password);lt.add(usecode);lt.add(usecode);lt.add(cusid);
			
			String user_sql = " select c.cusname,c.hytype,c.dqdate,u.* from basic_user u "
						    + " left join basic_customer c on c.cusid = u.cusid "
						    + " where u.pwd = ? and (u.phone = ? or u.emall = ?) and u.cusid=?";
			List<UserBean> umap=tservice.getSelect(user_sql,lt,UserBean.class);
			
			if(umap.size()==0||umap.size()>1){
				tservice.Close();
			//	modelAndView.addObject("num", Integer.parseInt(num)+1);
				modelAndView.addObject("textinfo", "登录类型异常,登录失败");
				modelAndView.addObject("info", "1");
				modelAndView.addObject("usecode", usecode);
				modelAndView.addObject("password", _password);
				modelAndView.setViewName("manageIntegral/login");
				return modelAndView;
			}else{
				cusid = umap.get(0).getCusid();
				dqdate = umap.get(0).getDqdate();
				hytype = umap.get(0).getHytype();
				state = umap.get(0).getState();
			}
			if(state == "0" || "0".equals(state)) {
				tservice.Close();
				modelAndView.addObject("num", Integer.parseInt(num)+1);
				modelAndView.addObject("textinfo", "该用户账号已被冻结，请联系管理员重新开通");
				modelAndView.setViewName("manageIntegral/login");
				return modelAndView;
			}
//			if(nowdate.compareTo(dqdate) > 0) {
//				tservice.Close();
//				modelAndView.addObject("num", Integer.parseInt(num)+1);
//				modelAndView.addObject("textinfo", "会员已到期，请充值后重新登陆");
//				modelAndView.setViewName("manageIntegral/login");
//				return modelAndView;
//			}
			
			if(umap.size()==1){
				String G_b_sql="select m.* from menu m left join menurole mr on m.id=mr.menuid left join roleuser ru on ru.roleid=mr.roleid" +
						" where ru.userid="+umap.get(0).getUserid();//+" and ru.cusid="+cusid;
				/**
				 * 权限判定
				 */
				 List<Map<String, String>> bpower=tservice.getSelect(G_b_sql);
				 if(_password.equals("123456")){
					 //初始密码提醒修改
					 Map<String, String> cmp=new HashMap<String, String>();
					 cmp.put("htmlcode", "-11111");
					 cmp.put("iscspwd", "1");
					 cmp.put("parentid", "-11111");
					 cmp.put("id", "iscspwd");
					 bpower.add(0, cmp);
				 }else{
					 Map<String, String> cmp=new HashMap<String, String>();
					 cmp.put("htmlcode", "-11111");
					 cmp.put("iscspwd", "0");
					 cmp.put("parentid", "-11111");
					 cmp.put("id", "iscspwd");
					 bpower.add(0, cmp);
				 }
				
				 getSession().setAttribute("user", new User(bpower,umap.get(0)));  
				 
				 modelAndView.setViewName("redirect:/login/index");
			}else{
				 modelAndView.addObject("num", Integer.parseInt(num)+1);
				 modelAndView.addObject("textinfo", "用户权限获取失败");
				 modelAndView.setViewName("manageIntegral/login");
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
	 * 查看/修改表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login/userinfo")
	@ResponseBody
	public ModelAndView userinfo(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		Util util = new Util();
		ModelAndView modelAndView = new ModelAndView();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			//获取职位信息
			String sql1 = "select * from basic_position where cusid = '"+cusid+"' ";
			List<Map<String, String>> positionmap = tservice.getSelect(sql1);
			//获取职称信息
			String sql2 = "select * from basic_title where cusid = '"+cusid+"' ";
			List<Map<String, String>> titlemap = tservice.getSelect(sql2);
			//获取学历信息
			String sql3 = "select * from basic_education where cusid = '"+cusid+"' ";
			List<Map<String, String>> educationmap = tservice.getSelect(sql3);
			//获取技术证书信息
			String sql4 = "select * from basic_skill where cusid = '"+cusid+"' ";
			List<Map<String, String>> skillmap = tservice.getSelect(sql4);
			//获取奖扣权限信息
			String sql5 = "select * from jk_jfconfig where cusid = '"+cusid+"' ";
			List<Map<String, String>> configmap = tservice.getSelect(sql5);
			//获取部门信息
			List<Map<String, String>> deptlmap = util.getDeptInfo(tservice,user.getCusid());
			
			String sql=" select * from basic_user where userid='"+tablejson+"'";
			List<Map<String, String>> lmap=tservice.getSelect(sql);
			
			modelAndView.addObject("map", lmap.get(0));
			modelAndView.addObject("deptList", deptlmap);
			modelAndView.addObject("position", positionmap);
			modelAndView.addObject("title", titlemap);
			modelAndView.addObject("education", educationmap);
			modelAndView.addObject("skill", skillmap);
			modelAndView.addObject("jkconfig", configmap);
			modelAndView.setViewName("manageIntegral/userinfo");
		
			//modelAndView.setViewName("organize/user_updateform");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		
		return modelAndView;
	}
	
	/**
	 * 查看菜单
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/loginout",produces="application/json; charset=utf-8")
	public ModelAndView loginout(){
		LoginController.getSession().removeAttribute("user");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("textinfo", "注销成功");
		modelAndView.setViewName("manageIntegral/login");
		
		return modelAndView;
	}
	
	/**
	 * 查看菜单
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/index",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request) throws IOException, SQLException{
		
		
		String loginstate=request.getParameter("loginstate");
		System.out.println("loginstate--------"+loginstate);
		
		User user=(User)LoginController.getSession().getAttribute("user");
		//为1表示是初始密码
		String iscspwd=user.getBpower().get(0).get("iscspwd");
		
		String cusid=user.getCusid();
		String userid=user.getUserid();
		String type2 = user.getHytype();
		String titles[]={"id","icon","urlaction","order","parentid","htmlname","type","htmlcode"};
		String g_sql="select distinct m.id dns,m.* from menu m left join menurole mr on m.id=mr.menuid left join roleuser ru on ru.roleid=mr.roleid " +
				"where ru.userid='"+userid+"' order by m.order";
		BeanService_Transaction tservice=new BeanService_Transaction();
		List<Map<String, String>> treemaps=tservice.getSelect(g_sql,titles);
		
		RoleMenuUtil util=new RoleMenuUtil();
		//待阅读公告
	    Map<String, String> waitread= util.getWaitRead(user, tservice); //  tservice.getSelect(sqlt,lt).get(0).get("total");
				
	    util.GGRDRefresh(tservice);
	    
		
		tservice.Close();
		
		String cp=request.getContextPath();
		
		List<Map<String, Object>> menumap=new ArrayList<Map<String,Object>>();
		for (Map map : treemaps) {
			String type=(String) map.get("type");
			if(((String)map.get("urlaction"))!=null&&!((String)map.get("urlaction")).trim().equals(""))
				map.put("urlaction", cp+"/"+map.get("urlaction"));
			else
				map.put("urlaction", "#");
			if(type.trim().equals("1"))
				menumap.add(map);
		}
		
		for (Map<String, Object> map : menumap) {
			String id=(String) map.get("id");
			for (Map cmap : treemaps) {
				String type=(String) cmap.get("type");
				String parentid=(String) cmap.get("parentid");
				if(type.trim().equals("2")&&parentid.equals(id)){
					if(map.containsKey("list")){
						List<Map<String, String>> clt=(List<Map<String, String>>) map.get("list");
						clt.add(cmap);
					}else{
						List<Map<String, String>> clt=new ArrayList<Map<String,String>>();
						clt.add(cmap);
						map.put("list", clt);
					}
				}
			}
		}
		
		
		for(Map<String, Object> map : menumap){
			if(map.containsKey("list")){
				map.put("class", "");
			}else{
				map.put("class", "J_menuItem");
			}
		}
		if(menumap.size()==0)menumap=null;
		ModelAndView modelAndView = new ModelAndView();
		if(iscspwd.equals("1"))modelAndView.addObject("cstate", "1");
		
		Map<String, Object> cmap = new HashMap<String, Object>();
		cmap.put("dqdate", user.getDqdate());
		cmap.put("hytype", user.getHytype());
		
		modelAndView.addObject("cmap", cmap);
		modelAndView.addObject("waitread", waitread);
		modelAndView.addObject("menuMap", menumap);
		modelAndView.addObject("user", (User)getSession().getAttribute("user"));
		modelAndView.setViewName("manageIntegral/index");
		
		return modelAndView;
	}
	/**
	 * 待阅读 系统通知
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/caleWaitRead",produces="application/json; charset=utf-8")
	@ResponseBody
	public String caleWaitRead(){
		Map map=new HashMap();
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		try {
			User user=(User)LoginController.getSession().getAttribute("user");
			//待阅读公告
			Map<String, String> waitread=new RoleMenuUtil().getWaitRead(user, tservice); //  tservice.getSelect(sqlt,lt).get(0).get("total");
			map.put("waitread", waitread);	
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("info", "0");
			map.put("textinfo", "系统公告刷新失败");
		} finally{
			tservice.Close();
		}
		
		return JSONObject.fromObject(map).toString();
	}
	/**
	 * 查看菜单
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/myscore",produces="application/json; charset=utf-8")
	@ResponseBody
	public ModelAndView myscore() throws IOException, SQLException{
//		if(textinfo != null&&!textinfo.trim().equals("")) {
//			textinfo =new String(textinfo.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
//		}
//		if(parentid==null||parentid.trim().equals(""))parentid="1"; 
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		
		String sqlt="select * from menu order by menu.order";
		List<Map<String, String>> treemaps=tservice.getSelect(sqlt);
		
		tservice.Close();
		
		
		for (Map<String, String> map : treemaps) {
			String urlaction=map.get("urlaction");
			if(urlaction==null||urlaction.trim().equals("")){
				map.put("href", "#");
				map.put("class", "J_menuItem");
			}
		}
		
		Map<String, List<Map<String, String>>> mlmap=new HashMap<String, List<Map<String,String>>>();
		for (Map<String, String> map : treemaps) {
			String type= map.get("type");
			String id=map.get("id");
			String parentid=map.get("parentid");
			if(type.equals("2")){
				if(mlmap.containsKey(parentid)){
					mlmap.get(parentid).add(map);
				}else{
					List<Map<String, String>> idmap=new ArrayList<Map<String, String>>();
					idmap.add(map);
					mlmap.put("parentid", idmap);
				}
			}
		}
		
		List<Map<String, Object>> ulli=new ArrayList<Map<String,Object>>();
		
		for (Map map : treemaps) {
			String type= (String) map.get("type");
			String id=(String) map.get("id");
			if(type.equals("1")){
				if(mlmap.containsKey(id)){
					map.put("lis", mlmap.get(id));
					ulli.add(map);
				}
			}
		}
		
		
		// treemap 树形对象
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("ulli", ulli);
		modelAndView.setViewName("menurole/index");
		
		return modelAndView;
	}
	
	
	
	/**
	 * 密码修改
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/changepwdjsp",produces="application/json; charset=utf-8")
	public ModelAndView changepwdjsp(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("manageIntegral/changepwd");
		return modelAndView;
	}
	/**
	 * 密码修改
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/changepwd",produces="application/json; charset=utf-8")
	@ResponseBody
	public String changepwd(String tablejson){
		JSONObject jsonObject=new JSONObject();
		/**
		 * tablejson 数据
		 */
		System.out.println("tablejson=="+tablejson);
		JSONObject json=jsonObject.fromObject(tablejson);
		String pwd=json.optString("pwd");
		String newpwd=json.optString("newpwd");
		String newpwds=json.optString("newpwds");
		
		Map map=new HashMap();
		if(!newpwd.equals(newpwds)){
			map.put("info", "0");
			map.put("textinfo", "请确认两次输入密码一致");
			
			return json.fromObject(map).toString();
		}
		
		if(newpwd!=null&&newpwd.equals("123456")){
			map.put("info", "0");
			map.put("textinfo", "123456为初始密码，请使用其他密码");
			return json.fromObject(map).toString();
		}
		
		pwd=ParaUtil.getResult(pwd);
		newpwd=ParaUtil.getResult(newpwd);
		
		User user=(User)LoginController.getSession().getAttribute("user");
		String userid=user.getUserid();
		String cusid=user.getCusid();
		BeanService_Transaction tservice=new BeanService_Transaction();
		List<String> lt=new ArrayList<String>();
		lt.add(userid);lt.add(cusid);lt.add(pwd);
		String title[]={"counts"};
		String p_sql="select count(*) counts from basic_user where userid=? and cusid=? and pwd=?";
		
		List<Map<String, String>> lmap=tservice.getSelect(p_sql, lt, title);
		
		if(lmap!=null&&lmap.size()==1&&lmap.get(0).get("counts").equals("1")){
			lt.clear();lt.add(newpwd);lt.add(userid);lt.add(cusid);lt.add(pwd);
			String u_sql="update basic_user set pwd=? where  userid=? and cusid=? and pwd=?";
			tservice.OpenTransaction();
			int r=tservice.UpdateSQL2(u_sql, lt);
			if(r==0){
				tservice.rollbackExe_close();
				map.put("info", "0");
				map.put("textinfo", "服务器繁忙，请稍后提交");
				return jsonObject.fromObject(map).toString();
			}
			tservice.commitExe_close();
			map.put("info", "1");
			//设置密码不是初始密码
			user.getBpower().get(0).put("iscspwd", "0");
			return jsonObject.fromObject(map).toString();
		}else{
			tservice.Close();
			map.put("info", "0");
			map.put("textinfo", "密码输入错误");
			return jsonObject.fromObject(map).toString();
		}
		
	}
	/**
	 * 头像上传
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/changimgjsp",produces="application/json; charset=utf-8")
	public ModelAndView changimgjsp(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("manageIntegral/changimg");
		return modelAndView;
	}
//	/**  
//     * 上传图片_头像
//     * @throws  
//     * ,HttpURLConnection conn   //conn.setChunkedStreamingMode(0);
//     */  
//    @RequestMapping(value="login/cropper",produces="application/json; charset=utf-8")  
//    @ResponseBody  
//    public String cropper(String tablejson, HttpServletRequest request) throws Exception { 
//    	
//    	
//    	User user=(User)LoginController.getSession().getAttribute("user");  
//    	Map map=new HashMap();
//    	BASE64Decoder decoder = new BASE64Decoder();
//    	// 去掉base64编码的头部 如："data:image/jpeg;base64," 如果不去，转换的图片不可以查看  
//    	System.out.println(tablejson);
//    	Loggerlog.log("sqlproxy").warn(tablejson);
////    	tablejson =tablejson.substring(23); 
//    	tablejson =tablejson.split(",")[1];          //tablejson.substring(23);  
//    	byte[] imgByte = decoder.decodeBuffer(tablejson);
////    	
//         for(int i=0;i<imgByte.length;++i){  
//             if(imgByte[i] < 0){//调整异常数据  
//            	 imgByte[i] += 256;  
//             }  
//         }
//        imgByte=RoleMenuUtil.compressPicForScale(imgByte, 10);
//    	FileOutputStream out = null;
//    	String path = LoginController.getSession().getServletContext()
//                .getRealPath("/") + "cusid"+user.getCusid()+"/"+user.getUserid(); //+"/" +uploadfilename; 
//        
//        File folder = new File(path);
//        path+="/titleimg.png";
//		if (!folder.exists() && !folder.isDirectory()) {
//			if(folder.mkdirs()){
//				try {  
//		            out = new FileOutputStream(path); // 输出文件路径  
//		            out.write(imgByte);  
//		        } catch (Exception e) {  
//		            e.printStackTrace();  
//		            map.put("info", "0");
//					map.put("textinfo", "服务器繁忙，请稍后");
//					return JSONObject.fromObject(map).toString(); 
//		        }finally{
//		        	if(out!=null)out.close();
//		        }     
//				
//			}else{
//				map.put("info", "0");
//				map.put("textinfo", "服务器繁忙，请稍后");
//				return JSONObject.fromObject(map).toString(); 
//			}
//			
//		}else{
//			try {  
//	            out = new FileOutputStream(path); // 输出文件路径  
//	            out.write(imgByte);  
//	        } catch (Exception e) {  
//	            e.printStackTrace(); 
//	            map.put("info", "0");
//				map.put("textinfo", "服务器繁忙，请稍后");
//	            return JSONObject.fromObject(map).toString(); 
//	        }finally{
//	        	if(out!=null)out.close();
//	        }  
//			
//		}  
//		return JSONObject.fromObject(map).toString(); 
//    }  
    
	
	/**  
   * 上传图片_头像
   * @throws  
   * ,HttpURLConnection conn   //conn.setChunkedStreamingMode(0);
   */  
  @RequestMapping(value="login/cropper",produces="application/json; charset=utf-8")  
  @ResponseBody  
  public String cropper(@RequestParam(value ="fileimg",required = false)CommonsMultipartFile fileimg, HttpServletRequest request) throws Exception { 
  	
  	
  	User user=(User)LoginController.getSession().getAttribute("user");  
  	Map map=new HashMap();
//  	BASE64Decoder decoder = new BASE64Decoder();
//  	tablejson =tablejson.split(",")[1];          //tablejson.substring(23);  
//  	byte[] imgByte = decoder.decodeBuffer(tablejson);
//  
  	byte[] imgByte =fileimg.getBytes();
       for(int i=0;i<imgByte.length;++i){  
           if(imgByte[i] < 0){//调整异常数据  
          	 imgByte[i] += 256;  
           }  
       }
    imgByte=RoleMenuUtil.compressPicForScale(imgByte, 32);
      
  	FileOutputStream out = null;
  	String path = LoginController.getSession().getServletContext()
              .getRealPath("/")+"resources/cusid"+user.getCusid()+"/"+user.getUserid(); //+"/" +uploadfilename; 
    
  	  if(fileimg.getInputStream()!=null)fileimg.getInputStream().close();
  	
      File folder = new File(path);
      path+="/titleimg.png";
		if (!folder.exists() && !folder.isDirectory()) {
			if(folder.mkdirs()){
				try { 
		            out = new FileOutputStream(path); // 输出文件路径  
		            out.write(imgByte);  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		            map.put("info", "0");
					map.put("textinfo", "服务器繁忙，请稍后");
					return JSONObject.fromObject(map).toString(); 
		        }finally{
		        	if(out!=null)out.close();
		        }     
				
			}else{
				map.put("info", "0");
				map.put("textinfo", "服务器繁忙，请稍后");
				return JSONObject.fromObject(map).toString(); 
			}
			
		}else{
			try {  
	            out = new FileOutputStream(path); // 输出文件路径  
	            out.write(imgByte); 
	         //   handleDpi(new File(path), 64, 64);
	        } catch (Exception e) {  
	            e.printStackTrace(); 
	            map.put("info", "0");
				map.put("textinfo", "服务器繁忙，请稍后");
	            return JSONObject.fromObject(map).toString(); 
	        }finally{
	        	if(out!=null)out.close();
	        }  
			
		}  
		
		return JSONObject.fromObject(map).toString(); 
  }  
    
  /**
   * 改变图片DPI
   *
   * @param file
   * @param xDensity
   * @param yDensity
   */
  private  void handleDpi(File file, int xDensity, int yDensity) {
      try {
          BufferedImage image =ImageIO.read(file);
          JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(new FileOutputStream(file));
          JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(image);
          jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
          jpegEncoder.setJPEGEncodeParam(jpegEncodeParam);
          jpegEncodeParam.setQuality(0.75f, false);
          jpegEncodeParam.setXDensity(xDensity);
          jpegEncodeParam.setYDensity(yDensity);
          jpegEncoder.encode(image, jpegEncodeParam);
          image.flush();
         
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
 
    /**
	 * 文件上传
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/updemojsp",produces="application/json; charset=utf-8")
	public ModelAndView updemojsp(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("manageIntegral/updemojsp");
		return modelAndView;
	}
	/**
	 * 文件上传
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="login/word")
	public ResponseEntity<byte[]> word(HttpServletRequest request) throws Exception {
		
		 
		//指定要下载的文件所在路径
		String path = LoginController.getSession().getServletContext()
                .getRealPath("/") + "resources/word/激分宝系统操作手册.doc";
		//创建该文件对象
		File file = new File(path);
		//设置响应头
		HttpHeaders headers = new HttpHeaders();
	
		//通知浏览器以下载的方式打开文件
		
		headers.setContentDispositionFormData("attachment;",new String("激分宝系统操作手册.doc".getBytes(), "iso8859-1"));
		//定义以流的形式下载返回文件数据
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		
		
		//使用Spring MVC框架的ResponseEntity对象封装返回下载数据
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.OK);
	}
	
	/**
	 * 查看菜单
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="login/depttree",produces="application/json; charset=utf-8")
	@ResponseBody
	public String depttree(){
		User user = (User) getSession().getAttribute("user");
		String cusid = user.getCusid();
		
		String _sql="select t.departid id,t.departid,t.departname text,t.parentid from basic_depart t where t.cusid='"+cusid+"'";
		String title[]={"id","text","parentid","departid"};
		String u_sql="select t.departid,t.departname,t.userid id,t.username name from basic_user t where t.cusid='"+cusid+"' and IFNULL(t.ISSA,'1')!='1' order by t.departid,t.username";
		String u_title[]={"id","name","departid","departname"};
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		List<Map<String, String>> dlmap=tservice.getSelect(_sql, title);
		
		List<Map<String, String>> ulmap=tservice.getSelect(u_sql, u_title);
		
		tservice.Close();
		
		List<Map> treemap=new ArrayList<Map>();
		
		List<Integer> index=new ArrayList<Integer>();
		/**
		 * 装入父节点
		 */
		for(int i=0;i<dlmap.size();i++){
			String pid=dlmap.get(i).get("parentid");
			if(pid.trim().equals("0")){
				treemap.add(dlmap.get(i));
				index.add(i);
			}
		}
		for(int i=index.size()-1;i>=0;i--){
			dlmap.remove(index.get(i));
		}
		
		
		boolean tree=true;
		if(treemap.size()==0||dlmap.size()==0)tree=false;
		
		List<Map> idmap=new ArrayList<Map>();
		List<Map> idmaps=new ArrayList<Map>();
		while(tree){
			index.clear();
			if(idmap.size()==0&&idmaps.size()==0){
				for (Map map : treemap) {
					String id=(String) map.get("id");
					List<Map> nodes=new ArrayList<Map>();
					for(int i=0;i<dlmap.size();i++){
						String pid=(String) dlmap.get(i).get("parentid");
						if(pid.trim().equals(id)){
							idmap.add(dlmap.get(i));
							nodes.add(dlmap.get(i));
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
					for(int i=0;i<dlmap.size();i++){
						String pid=(String) dlmap.get(i).get("parentid");
						if(pid.trim().equals(id)){
							idmaps.add(dlmap.get(i));
							nodes.add(dlmap.get(i));
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
					for(int i=0;i<dlmap.size();i++){
						String pid=(String) dlmap.get(i).get("parentid");
						if(pid.trim().equals(id)){
							idmap.add(dlmap.get(i));
							nodes.add(dlmap.get(i));
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
					dlmap.remove(index.get(i));
				}
			}
			 
		} 
		List<Map> ulmaps=new ArrayList<Map>();
		String _departid="";
		for (Map<String, String> map : ulmap) {
			String departid=map.get("departid");
			map.put("check", "false");
			if(_departid.equals(departid)){
				List<Map> checklist=(List<Map>)ulmaps.get(ulmaps.size()-1).get("userlist");
				checklist.add(map);
			}else{
				_departid=departid;
				Map mp=new HashMap();
				mp.put("groupid", departid);
				mp.put("groupname", map.get("departname"));
				
				List<Map> checklist=new ArrayList<Map>();
				checklist.add(map);
				mp.put("userlist", checklist);
				ulmaps.add(mp);
			}
		}
		  
		Map remap=new HashMap();
		remap.put("depart", treemap);
		remap.put("user", ulmaps);
		return JSONObject.fromObject(remap).toString();
	}
	
	
	/*
	 * 文件上传
     * 采用file.Transto 来保存上传的文件
     */
//    @RequestMapping(value="login/fileUpload",produces="application/json; charset=utf-8")
//    @ResponseBody
//    public String  fileUpload(@RequestParam(value ="file",required = false)CommonsMultipartFile file,String manageIntegral) throws IOException {
//    	User user=(User)LoginController.getSession().getAttribute("user");
//    	
//        String fileName=file.getOriginalFilename();
//        String name=file.getName();
//        Long size=file.getSize();
//        
//        System.out.println("manageIntegral="+manageIntegral);
//        System.out.println("fileName="+fileName);
//        System.out.println("name="+name);
//        System.out.println("size="+size);
//        System.out.println("getContentType="+file.getContentType());
//        System.out.println("getStorageDescription="+file.getStorageDescription());
//        
//        
//        
//        
//        Loggerlog.log("info").warn("-客户:"+user.getCusid()+",用户:"+user.getUserid()+",访问用户"+user.getUsername()+",上传文件：");
//      
//        String uploadfilename=System.currentTimeMillis()+fileName;
//        Map map=new HashMap();
//       /**
//        *文件保存 
//        */
//        String path = LoginController.getSession().getServletContext()
//                .getRealPath("/") + "cusid"+user.getCusid()+"/"+user.getCusid(); //+"/" +uploadfilename; 
//        
//        File folder = new File(path);
//        path+="/" +uploadfilename;
//        /**
//         * 判断文件路径是否存在，
//         */
//		if (!folder.exists() && !folder.isDirectory()) {
//			if(folder.mkdirs()){
//				 File newFile=new File(path);
//			     //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
//			     file.transferTo(newFile); 
//			}else{
//				map.put("info", "0");
//				map.put("textinfo", "服务器繁忙，请稍后");
//				return JSONObject.fromObject(map).toString(); 
//			}
//			System.out.println("创建目录成功");
//		}else{
//			 File newFile=new File(path);
//		     //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
//		     file.transferTo(newFile); 
//		}  
//		
//		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		/**
//		 * 文件读取，可以不保存文件
//		 * 文件EXCEL直接读流
//		 * 			返回的list集合，集合内未MAP对象，key为第一行标题
//		 *          可以不保存文件，直接读取文件
//		 */
//		List<Map<String,String>> lemap=ExcelUilt.Excel(fileName, 0, file.getInputStream());
//		
//		 //遍历解析出来的lemap  LinkedHashMap
//        for (Map<String,String> mp : lemap) {
//            for (Entry<String,String> entry : mp.entrySet()) {
//                System.out.print(entry.getKey()+":"+entry.getValue()+",");
//            }
//            System.out.println();
//        }
//        map.put("info", "1");
//        map.put("textinfo", "汉字在此");
//        
//        return JSONObject.fromObject(map).toString(); 
//    }

	
	
	/**
	 * 获取session
	 * @return
	 */
	public static HttpSession getSession() { 
	    HttpSession session = null; 
	    try { 
	        session = getRequest().getSession(); 
	    } catch (Exception e) {} 
	        return session; 
	} 
	    
	public static HttpServletRequest getRequest() { 
	    ServletRequestAttributes attrs =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); 
	    return attrs.getRequest(); 
	}
	
	
}
