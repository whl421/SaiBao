package com.cqmi.controller.standard;

import java.io.IOException;
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
import com.cqmi.db.action.BasicAction;
import com.cqmi.db.service.BeanService_Transaction;

@Controller
public class StaClassController extends BasicAction{
	
	
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
	@RequestMapping(value="standard/staclasslist",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String staclasslist(String page,String rows,String sort,String sortOrder,String searchjson,String actjson) throws IOException{
		System.out.println("--进入--");
//		System.out.println(page); 
//		System.out.println(rows); 
//		System.out.println(sort);
//		System.out.println(sortOrder); 
//		System.out.println(searchjson);
//		System.out.println("--结束--");
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by a.sclasscode ";
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
		String where="where 1=1 and a.cusid = '"+cusid+"' ";
		String name=json.getString("sclassname");
		where+=name!=null&&!name.trim().equals("")?" and a.sclassname like '%"+name+"%'":"";
		if(!actjson.equals("-1")){
			where += "and a.sclassid='"+actjson+"' or a.parentid='"+actjson+"' ";
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
		String sql=" select a.* from (select j.sclassname as parentname,t.* from basic_standardclass as t  "
				  +" left join basic_standardclass as j on j.sclassid = t.parentid )a "
				  +" "+where+" "+order+" limit "+num+","+rows ;
		String title[] = {"parentname","sclassid","cusid","sclasscode","sclassname","parentid","memo","createuser","createtime"};
		/**
		 * 数据总条数
		 */
		String sqlt="select count(*) total from basic_standardclass a "+where;
		//开启数据库链接
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
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
	 *  value="user/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="standard/staclasssave",produces="application/json; charset=utf-8")
	@ResponseBody
	public String staclasssave(String tablejson) throws IOException{
		System.out.println("--进入--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		//获取当前时间
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		
		/**
		 * tablejson 转为json
		 */
		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();


		//=============================自动生成部门编号=============================
		
		String cusid = user.getCusid();
		//String sclasscode=json.optString("sclasscode");
		String sclassname=json.optString("sclassname");
		String parentid=json.optString("parentid");
		String memo=json.optString("memo");
		String createuser = user.getUsername();
		String createtime = datetime;
		
		//查询上级分类code
		String newcode = "";	//新增CODE
		
		if(parentid != "" && !"".equals(parentid)){
			String sql1 = " select sclasscode from basic_standardclass where parentid = '"+parentid+"' order by sclasscode desc ";
			List<Map<String, String>> dcmap = tservice.getSelect(sql1);
			
			if(dcmap != null && dcmap.size() > 0){
				String sclasscode = dcmap.get(0).get("sclasscode");

			    String qstr = sclasscode.substring(0,sclasscode.length()-2);

			    String hstr = sclasscode.substring(sclasscode.length()-2,sclasscode.length());
			    String newstr = Integer.parseInt(hstr)+1+"";
			    if(newstr.length() < 2){
			        newstr = "0"+newstr;
			    }
			    newcode = qstr+newstr;
			}else{
				String sqla="select sclasscode from basic_standardclass t where t.sclassid = '"+parentid+"'";
		        List<Map<String, String>> dcmap2 = tservice.getSelect(sqla);
		        if(dcmap2!=null && dcmap2.size()>0){
		        	newcode = dcmap2.get(0).get("sclasscode");
		        	newcode += ".01";
		        }else{
		        	newcode = "01";
			    }
			}
		}else{
			String sqla="select cast(max(sclasscode+0)+1 as char) sclasscode from basic_standardclass where parentid ='0' order by sclasscode desc  ";
	        List<Map<String, String>> dcmap2 = tservice.getSelect(sqla);

	        if(dcmap2!=null && dcmap2.size()>0){
	        	newcode = dcmap2.get(0).get("sclasscode");
	        	if(newcode.equals("")){
	        		newcode = "01";
	        	}else{
	        		if(Integer.parseInt(newcode) < 10){
		        		newcode = "0" + newcode;
		        	}
			    }
		    }
		}
		
		String sql="insert into basic_standardclass (cusid,sclasscode,sclassname,parentid,memo,createuser,createtime) value (?,?,?,?,?,?,?) ";
		
		if(parentid.equals("") || parentid.equals(null) || parentid.equals("NULL")) parentid = "0";
		//注意参数顺序
		lt.add(cusid);lt.add(newcode);lt.add(sclassname);lt.add(parentid);lt.add(memo);
		lt.add(createuser);lt.add(createtime);
		
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		int r=tservice.InsertSQL2(sql, lt);
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
	 * 修改 表数据
	 *  value="user/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="standard/staclassupdate",produces="application/json; charset=utf-8")
	@ResponseBody
	public String staclassupdate(String tablejson) throws IOException{
		System.out.println("--进入--"+tablejson);
		JSONObject jsonObject = new JSONObject();
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map<String, String> map = new HashMap<String, String>();
		try {
			/**
			 * tablejson 数据
			 */
			JSONObject json = jsonObject.fromObject(tablejson);
			List<String> lt = new ArrayList<String>();
			String sql = " update basic_standardclass set sclasscode = ?,sclassname = ?,parentid = ?,memo = ? "
					   + " where sclassid = ? ";
			String sclasscode = json.optString("sclasscode");
			String sclassname = json.optString("sclassname");
			String parentid = json.optString("parentid");
			String memo = json.optString("memo");
			String sclassid = json.optString("sclassid");
			
			if(parentid.equals("") || parentid.equals(null) || parentid.equals("NULL")) parentid = "0";
			//注意参数顺序
			lt.add(sclasscode);lt.add(sclassname);lt.add(parentid);lt.add(memo);lt.add(sclassid);
			
			int r=tservice.UpdateSQL2(sql, lt);
			if(r == 0){
				map.put("info", "0");
				map.put("text", "修改失败请联系管理员");
				tservice.rollbackExe_close();
			} else {
				tservice.commitExe_close();
			}
		} catch(Exception e) {
			e.printStackTrace();
			map.put("info", "0");
			map.put("text", "修改失败请联系管理员");
			tservice.rollbackExe_close();
		} finally {
			
		}
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 删除数据
	 * value="user/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="standard/staclassdelete",produces="application/json; charset=utf-8")
	@ResponseBody
	public String staclassdelete(String tablejson) throws UnsupportedEncodingException{
		System.out.println("删除数据id有"+tablejson);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		String sql="delete from basic_standardclass where sclassid in ("+tablejson.replace("\"", "'").replace("[", "").replace("]", "")+")";
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
	 * 查看表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("standard/staclassload")
	@ResponseBody
	public ModelAndView staclassload(String tablejson) throws Exception {
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
				
		String sql=" select j.sclassname as parentname,t.* from basic_standardclass as t "
				  +" left join basic_standardclass as j on j.sclassid = t.parentid "
				  +" where t.sclassid='"+tablejson+"'";
		String title[] = {"parentname","sclassid","cusid","sclasscode","sclassname","parentid","memo","createuser","createtime"};
		List<Map<String, String>> lmap=tservice.getSelect(sql,title);
		
		String sql2 = "select * from basic_standardclass where cusid = '"+cusid+"' ";
		List<Map<String, String>> classmap = tservice.getSelect(sql2);
		
		ModelAndView modelAndView = new ModelAndView();
		
		tservice.Close();
		
		modelAndView.addObject("jfclass", classmap);
		modelAndView.addObject("map", lmap.get(0));
		modelAndView.setViewName("standard/staclass_loadform");
		
		return modelAndView;
		
	}
	
	/**
	 * 查看表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("standard/fromuf8info")
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
	@RequestMapping("standard/setfrominfo")
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
	//	modelAndView.setViewName("redirect:/user/fromuf8info.action");
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
	@RequestMapping("standard/staclassadd")
	public ModelAndView staclassadd(String tablejson) throws Exception {
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
		BeanService_Transaction tservice = new BeanService_Transaction();
		
		String sql = "select * from basic_standardclass where cusid = '"+cusid+"' ";
		List<Map<String, String>> lmap = tservice.getSelect(sql);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("jfclass", lmap);
		modelAndView.setViewName("standard/staclass_addform");
		
		return modelAndView;
		
	}
	 
	/**
	 * 最高父节点为0
	 * 获取树形数据
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="standard/staclasstree",produces="application/json; charset=utf-8")
	@ResponseBody
	public String staclasstree() throws IOException, SQLException{
		System.out.println("--获取树节点--");
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
		//获取树形结构
				BeanService_Transaction tservice=new BeanService_Transaction();
				String sql="select *,sclassname text from basic_standardclass where cusid = '"+cusid+"' ";
				String title[]={"sclassid","text","parentid"};
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
							String id=(String)treemap.get(i).get("sclassid");
							String pid=(String)treemap.get(i).get("parentid");
							if(!keymap.containsKey(id)&&!pid.equals("0")){
								if(!keymap.containsKey(id)){
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
						}
						tree=false;
						//将下一级节点放入上一级节点
						for(int i=treemap.size()-1;i>=0;i--){
							String id=(String)treemap.get(i).get("sclassid");
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
	@RequestMapping("standard/staclassindex")
	public ModelAndView staclassindex() throws Exception {
		/**
		 * 权限判定
		 * lstaskfp  角色菜单中的菜单编号
		 */
		User user= ((User)LoginController.getSession().getAttribute("user"));
		List<Map<String, String>> bpower=user.getBpower("staclass");		//tservice.getSelect(G_b_sql);
		/**
		 * 1.确认当前模块权限，
		 * 2.获取按钮权限
		 */
		boolean boo=false;
		Map mb=new HashMap();
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals("staclass"))boo=true;
			if(!map.get("type").equals("2"))mb.put(map.get("htmlcode"), "1");
			 
		}
		
		ModelAndView modelAndView = new ModelAndView();
		
		//角色权限增加
		if(boo){
			modelAndView.addObject("button", mb);
			modelAndView.setViewName("standard/staclass");
		}else
			modelAndView.setViewName("nopower");
		
		return modelAndView;

	}
}
