package com.cqmi.controller.organize;

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
public class UserController extends BasicAction{
	
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
	@RequestMapping(value="userinfo/userlist",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String userlist(String page,String rows,String sort,String sortOrder,String searchjson,String actjson) throws IOException{
		System.out.println("--进入--");
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by a.usercode ";
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
		String where="where 1=1 and ifnull(a.issa,0) <> '1' and a.cusid = '"+cusid+"'";
		
		String state=json.getString("state");
		String name=json.getString("username");
		String parent = json.getString("parent");
		
		where+=name!=null&&!name.trim().equals("")?" and a.username like '%"+name+"%'":"";
		where+=state!=null&&!state.trim().equals("")?" and a.state = '"+state+"'":"";
		
		
		if(!actjson.equals("-1")){
			//查询部门parentid
			String getidsql = " select departcode,parentid from basic_depart where departid = '"+actjson+"' ";
			List<Map<String, String>> idmap = tservice.getSelect(getidsql);
			String parentid = idmap.get(0).get("parentid");
			String departcode = idmap.get(0).get("departcode");
			//是否显示子部门人员
			if(parent.equals("1")){
				if(!parentid.equals("0")){
					where += " and a.dcode like '"+departcode+"%' ";
				}
			}else{
				where += " and a.departid='"+actjson+"' ";
			}
		}
		
//		String sex=json.getString("sex");
//		where+=sex!=null&&!sex.trim().equals("")?" and sex in ('"+sex.replace(",", "','")+"')":"";
//		String dayb=json.getString("dayb");
//		where+=dayb!=null&&!dayb.trim().equals("")?" and day >='"+dayb+"'":"";
//		String daye=json.getString("daye");
//		where+=daye!=null&&!daye.trim().equals("")?" and day <='"+daye+"'":"";
		/**
		 * 查询数据sql
		 */
		String sql=" select a.* from (select d.departcode dcode,d.parentid,t.* from basic_user t "
				  +" left join basic_depart d on d.departid = t.departid)a "
				  +" "+where+" "+order+" limit "+num+","+rows ;
		String title[] = {"userid","parentid","usercode","username","emall","departname","pname","tname","ename","pzmc","joindate","sex","birthday","phone","userstatus","state","gxname"};
		/**
		 * 数据总条数
		 */
		String sqlt =" select count(*) total from (select t.*,d.departcode dcode,d.parentid from basic_user t "
				    +" left join basic_depart d on d.departid = t.departid)a  "
				    +" "+where;
		
		/**
		 * map中为table的数据组装
		 */
		Map map=new HashMap();
		//total 总行数
		map.put("total", tservice.getSelect(sqlt).get(0).get("total"));
		
		//rows 格式为[{id:1,name:2},{id:1,name:2}...]
		map.put("rows", tservice.getSelect(sql,title)); 
		//关闭链接
		tservice.Close();
		
		
//		System.out.println(jsonObject.fromObject(map).toString());
	    return jsonObject.fromObject(map).toString();
	}
	/**
	 * 新增数据 
	 *  value="userinfo/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="userinfo/usersave",produces="application/json; charset=utf-8")
	@ResponseBody
	public String usersave(String tablejson) throws IOException{
		System.out.println("--进入--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		//获取当前时间
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
				
		//tablejson 转为json
		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();
				
		/**
		 * 防注入使用
		 */
		String sql = " insert into basic_user (cusid,username,departid,departname,pwd,state,userstatus,sex, "
				   + " phone,memo,createid,creater,creattime) "
				   + " value (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		String cusid = user.getCusid();
		String username = json.optString("username");
		String departid = json.optString("departid");
		String departname = json.optString("departname");
		String pwd = "-41149678231811287725040083248417372098";
		String state = "1";
		String userstatus = json.optString("userstatus");
		String sex = json.optString("sex");
		String phone = json.optString("phone");
		String memo = json.optString("memo");
		String createid = user.getUserid();
		String creater = user.getUsername();
		String creattime = datetime;
		
		//注意参数顺序
		lt.add(cusid);lt.add(username);lt.add(departid);lt.add(departname);lt.add(pwd);lt.add(state);
		lt.add(userstatus);lt.add(sex);lt.add(phone);lt.add(memo);lt.add(createid);
		lt.add(creater);lt.add(creattime);
		
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		int r;
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		r=tservice.InsertSQL2(sql, lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
			
			tservice.rollbackExe_close();
		}
		
		tservice.commitExe_close();
		
		
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 修改 表数据
	 *  value="userinfo/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="userinfo/userupdate",produces="application/json; charset=utf-8")
	@ResponseBody
	public String userupdate(String tablejson) throws IOException{
		System.out.println("--进入--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		JSONObject json=jsonObject.fromObject(tablejson);
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		//获取参数值
		String userid = json.optString("userid");
		String username = json.optString("username");
		String departid = json.optString("departid");
		String departname = json.optString("departname");
		String userstatus = json.optString("userstatus");
		String sex = json.optString("sex");
		String phone = json.optString("phone");
		String memo = json.optString("memo");
		
		List<String> lt=new ArrayList<String>();
		String sql= " update basic_user set username=?,departid=?,departname=?, "
				  + " userstatus=?,sex=?,phone=?,memo=? where userid=? " ;
		
		//注意参数顺序
		lt.add(username);lt.add(departid);lt.add(departname);lt.add(userstatus);lt.add(sex);
		lt.add(phone);lt.add(memo);lt.add(userid);
		
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		int r=tservice.UpdateSQL2(sql, lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
		
			tservice.rollbackExe_close();
		}else{
			tservice.commitExe_close();
		}
		
		
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 更新状态（冻结/恢复）
	 *  value="userinfo/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="userinfo/userupdatestate",produces="application/json; charset=utf-8")
	@ResponseBody
	public String userupdatestate(String tablejson) throws IOException{
		System.out.println("--进入--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		JSONObject json=jsonObject.fromObject(tablejson);
		
		//获取参数值
		String userid = json.optString("userid");
		String state = json.optString("state");
		
		List<String> lt=new ArrayList<String>();
		String sql= " update basic_user set state=? where userid=? " ;
		
		//注意参数顺序
		lt.add(state);lt.add(userid);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		int r=tservice.UpdateSQL2(sql, lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
		
			tservice.rollbackExe_close();
		}else{
			tservice.commitExe_close();
		}
		
		
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 删除数据
	 * value="userinfo/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="userinfo/userdelete",produces="application/json; charset=utf-8")
	@ResponseBody
	public String userdelete(String tablejson) throws UnsupportedEncodingException{
		System.out.println("删除数据id有"+tablejson);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		String sql="delete from basic_user where userid in ("+tablejson.replace("\"", "'").replace("[", "").replace("]", "")+")";
		int r=tservice.DeleteSQL2(sql);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
		
			tservice.rollbackExe_close();
		}else{
			tservice.commitExe_close();
		}
		
	    return new JSONObject().fromObject(map).toString();
		
	}
	/**
	 * 查看/修改表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userinfo/userload")
	@ResponseBody
	public ModelAndView userload(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		Util util = new Util();
		ModelAndView modelAndView = new ModelAndView();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			//获取部门信息
			List<Map<String, String>> deptlmap = util.getDeptInfo(tservice,user.getCusid());
			
			String sql=" select * from basic_user where userid='"+tablejson+"'";
			List<Map<String, String>> lmap=tservice.getSelect(sql);
			modelAndView.addObject("map", lmap.get(0));
			modelAndView.addObject("deptList", deptlmap);
			modelAndView.setViewName("organize/user_updateform");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		
		return modelAndView;
		
	}
	
	/**
	 * 验证手机号
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userinfo/phoneYZ")
	@ResponseBody
	public String phoneYZ(String tablejson,String userid) throws Exception {
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		JSONObject jsonObject=new JSONObject();
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
		
		String sql=" select * from basic_user where phone = '"+tablejson+"' and cusid = '"+cusid+"' ";
		if(userid != "" && userid != null){
			sql += " and userid <> '"+userid+"' ";
		}
		
		List<Map<String, String>> lmap = tservice.getSelect(sql);
		Map map=new HashMap();
		if(lmap.size() > 0){
			map.put("isok", "no");
		}else{
			map.put("isok", "yes");
		}
		//关闭链接
		tservice.Close();
		
		return jsonObject.fromObject(map).toString();
		
	}
	
	/**
	 * 验证工号
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userinfo/usercodeYZ")
	@ResponseBody
	public String usercodeYZ(String tablejson,String userid) throws Exception {
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		JSONObject jsonObject=new JSONObject();
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
		
		String sql=" select * from basic_user where usercode = '"+tablejson+"' and cusid = '"+cusid+"' ";
		if(userid != "" && userid != null){
			sql += " and userid <> '"+userid+"' ";
		}
		
		List<Map<String, String>> lmap = tservice.getSelect(sql);
		Map map=new HashMap();
		if(lmap.size() > 0){
			map.put("isok", "no");
		}else{
			map.put("isok", "yes");
		}
		//关闭链接
		tservice.Close();
		
		return jsonObject.fromObject(map).toString();
		
	}
	
	/**
	 * 验证用户上限
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userinfo/usernumYZ")
	@ResponseBody
	public String usernumYZ() throws Exception {
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		JSONObject jsonObject=new JSONObject();
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
		
		String sql = " select usernum,a.nownum from basic_customer c "
				   + " left join ( "
				   + " 	select count(*) nownum,cusid from basic_user where cusid = '"+cusid+"' and state = '1' "
				   + " )a on a.cusid = c.cusid "
				   + " where a.cusid = '"+cusid+"' ";
		List<Map<String, String>> lmap = tservice.getSelect(sql);
		String usernum = lmap.get(0).get("usernum");	//客户人数上限
		String nownum = lmap.get(0).get("nownum");		//当前用户数
		
		Map map=new HashMap();
		if(Integer.parseInt(nownum) == Integer.parseInt(usernum)){
			map.put("isok", "no");
		}else{
			map.put("isok", "yes");
		}
		//关闭链接
		tservice.Close();
		
		return jsonObject.fromObject(map).toString();
		
	}
	
	/**
	 * 重置密码
	 */
	@RequestMapping(value="userinfo/updatepwd",produces="application/json; charset=utf-8")
	@ResponseBody
	public String updatepwd(String tablejson) throws UnsupportedEncodingException{
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		
		String pwd = "-41149678231811287725040083248417372098";
		
		//重置密码
		String sql="update basic_user set pwd = ? where userid = ? ";
		
		List<String> lt=new ArrayList<String>();
		lt.add(pwd);lt.add(tablejson);
		
		int r=tservice.UpdateSQL2(sql,lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "重置密码失败,请联系管理员");
		
			tservice.rollbackExe_close();
		}else{
			tservice.commitExe_close();
		}
		
	    return new JSONObject().fromObject(map).toString();
		
	}
	
	/**
	 * 查看表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userinfo/fromuf8info")
	@ResponseBody
	public ModelAndView fromuf8info() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("utf8-jsp/index");
		
		return modelAndView;
		
	}
	
	
	/**
	 * 查看表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userinfo/setfrominfo")
	@ResponseBody
	public ModelAndView setfrominfo(String input,String select,String editorValue) throws Exception {
		if(input != null) {
			input =new String(input.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		if(select != null) {
			select =new String(select.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		if(editorValue != null) {
			editorValue =new String(editorValue.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		System.out.println(input);
		
		System.out.println(select);
		
		System.out.println(editorValue);
		
		ModelAndView modelAndView = new ModelAndView();
	//	modelAndView.addObject("boo", "1");
	//	modelAndView.setViewName("redirect:/userinfo/fromuf8info.action");
	//content
		modelAndView.addObject("input", input);
		modelAndView.addObject("select", select);
		modelAndView.addObject("content", editorValue);
		modelAndView.setViewName("utf8-jsp/show");
		return modelAndView;
		
	}
 
	/**
	 * 新增表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userinfo/useradd")
	public ModelAndView useradd(String tablejson) throws Exception {
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		Util util = new Util();
		ModelAndView modelAndView = new ModelAndView();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			//获取部门信息
			List<Map<String, String>> deptlmap = util.getDeptInfo(tservice,user.getCusid());
			
			modelAndView.addObject("deptList", deptlmap);
			modelAndView.setViewName("organize/user_addform");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
		
	}
	 
	/**
	 * 最高父节点为0
	 * 获取树形数据
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="userinfo/usertree",produces="application/json; charset=utf-8")
	@ResponseBody
	public String usertree() throws IOException, SQLException{
		System.out.println("--获取树节点--");
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
		//获取树形结构
				BeanService_Transaction tservice=new BeanService_Transaction();
				String sql="select *,departname text from basic_depart where cusid = '"+cusid+"' ";
				String title[]={"departid","text","parentid"};
				List<Map<String, String>> treemaps=tservice.getSelect(sql,title);
				
				List<Map> treemap=new ArrayList<Map>();
				for (Map map : treemaps) {
					treemap.add(map);
				}
				tservice.Close();
				
				boolean tree=true;
				if(treemap.size()==0)tree=false;
				int b=0;
				while(tree){
					Map<String, List<Map<String, Object>>> mlmap=new HashMap<String, List<Map<String,Object>>>();
						//获取父节点
						Map<String, String> keymap=new HashMap<String, String>();
						for (Map<String, String> pmap : treemap) {
							keymap.put(pmap.get("parentid"), "1");
						}
						//获取已无父节点的子节点（下一级节点）
						for(int i=treemap.size()-1;i>=0;i--){
							String id=(String)treemap.get(i).get("departid");
							String pid=(String)treemap.get(i).get("parentid");
							if(!keymap.containsKey(id)&&!pid.equals("0")){
								
								if(mlmap.containsKey(pid)){
									//组装子节点
									mlmap.get(pid).add(treemap.get(i));
								}else{
									List<Map<String, Object>> idmap=new ArrayList<Map<String,Object>>();
									idmap.add(treemap.get(i));
									mlmap.put(pid, idmap);
								}
								treemap.remove(i);
							}
						}
						tree=false;
						//将下一级节点放入上一级节点
						for(int i=treemap.size()-1;i>=0;i--){
							String id=(String)treemap.get(i).get("departid");
							if(mlmap.containsKey(id)){
								if(treemap.get(i).containsKey("nodes")){
									List<Map<String, Object>> nodes=(List<Map<String, Object>>) treemap.get(i).get("nodes");
									nodes.addAll(mlmap.get(id));
								}else{
									treemap.get(i).put("nodes", mlmap.get(id));
								}
								
							}
							String pid=(String)treemap.get(i).get("parentid");
							/**
							 * 组装完成
							 * 		判定，都为顶级节点时为 false，跳出循环
							 */
							if(!pid.trim().equals("0")){
								tree=true;
							}
						}
						
						b++;
						if(b>10){
							tree=false;
							System.out.println("异常数据产生，请处理.............");
							throw new SQLException(
							          "platform.dao.SQLProxy: execSQL()|This connection has not been established yet.");
							
						}
						
				} 
		 
		JSONArray jsonObject=new JSONArray();
		
	    return jsonObject.fromObject(treemap).toString();
	}
	
	/**
	 * 进入首页
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userinfo/userindex")
	public ModelAndView userindex() throws Exception {
		/**
		 * 权限判定
		 * lstaskfp  角色菜单中的菜单编号
		 */
		User user= ((User)LoginController.getSession().getAttribute("user"));
		List<Map<String, String>> bpower=user.getBpower("usermanage");		//tservice.getSelect(G_b_sql);
		/**
		 * 1.确认当前模块权限，
		 * 2.获取按钮权限
		 */
		boolean boo=false;
		Map mb=new HashMap();
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals("usermanage"))boo=true;
			if(!map.get("type").equals("2"))mb.put(map.get("htmlcode"), "1");
			 
		}
		
		ModelAndView modelAndView = new ModelAndView();
		
		//角色权限增加
		if(boo){
			modelAndView.addObject("button", mb);
			modelAndView.setViewName("organize/user");
		}else
			modelAndView.setViewName("nopower");
			
		return modelAndView;

	}
	/**
	 * 获取职位等级
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userinfo/getZwdj")
	@ResponseBody
	public String getZwdj(String tablejson) throws Exception {
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		Map map = new HashMap();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String num = "";
			if(!"".equals(tablejson) && tablejson != null) {
				//获取职位信息
				String sql1 = "select count(*) num from basic_position t where t.CUSID = '"+cusid+"' and cast(t.ZWDJ as signed) < " +
						" (select cast(t.ZWDJ as signed) from basic_position t where t.PID = ?) ";
				List<String> lt = new ArrayList<String>();
				lt.add(tablejson);
				String title[] = {"num"};
				List<Map<String, String>> positionmap = tservice.getSelect(sql1, lt, title);
				if(positionmap != null && positionmap.size() > 0) {
					num = positionmap.get(0).get("num");
				}
			}
			map.put("num", num);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return new JSONObject().fromObject(map).toString();
		
	}
}
