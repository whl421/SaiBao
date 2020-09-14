package com.cqmi.controller.abilityManage;

import java.io.File;
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

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cqmi.controller.login.LoginController;
import com.cqmi.controller.login.bean.User;
import com.cqmi.dao.util.Util;
import com.cqmi.db.action.BasicAction;
import com.cqmi.db.service.BeanService_Transaction;

@Controller
public class abilityController extends BasicAction{
	
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
	@RequestMapping(value="ability/abilitylist",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String abilitylist(String page,String rows,String sort,String sortOrder,String searchjson) throws IOException{
		System.out.println("--进入--");
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by abilitycode ";
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
		String where="where 1=1 and cusid = '"+cusid+"'";
		
		String abilityname=json.getString("abilityname");
		
		where+=abilityname!=null&&!abilityname.trim().equals("")?" and abilityname like '%"+abilityname+"%'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql = " select * from basic_ability "
				   + where+" "+order+" limit "+num+","+rows ;
		String title[] = {"abilityid","cusid","abilitycode","abilityname","abilitymemo","createuser","createtime","memo"};
		/**
		 * 数据总条数
		 */
		String sqlt =" select count(*) total from basic_ability  "
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
	 *  value="ability/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="ability/abilitysave",produces="application/json; charset=utf-8")
	@ResponseBody
	public String abilitysave(@RequestParam(value ="file",required = false)CommonsMultipartFile []file,
			String abilitycode, String abilityname, String abilitymemo, String memo) throws IOException{
		System.out.println("--进入--");
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		//获取当前时间
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
				
		//tablejson 转为json
		List<String> lt=new ArrayList<String>();
				
		/**
		 * 防注入使用
		 */
		String sql = " insert into basic_ability (cusid,abilitycode,abilityname,abilitymemo,memo,createuser,createtime) "
				   + " value (?,?,?,?,?,?,?) ";
		String cusid = user.getCusid();
		String creater = user.getUsername();
		String creattime = datetime;
		
		//注意参数顺序
		lt.add(cusid);lt.add(abilitycode);lt.add(abilityname);lt.add(abilitymemo);lt.add(memo);
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
		//获取刚插入的ID
		String newid = "";
		String title[] = {"id"};
		List<Map<String, String>> list = tservice.getSelect("select LAST_INSERT_ID() as id",title);
//				System.out.println("==="+list.get(0));
		if(list != null && list.size()>0) {
			newid = list.get(0).get("id");
		} else {
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
		}
		if(file.length > 0 && file[0].getSize() != 0) {
			//往附件表中增加数据
			String result = util.insertAttach(tservice, file, newid, user, "basic_ability", "abilityid");
			if("0".equals(result)) {
				r = 0;
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
			}
		}
		map.put("info", "1");
		map.put("text", "操作成功");	
		tservice.commitExe_close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 修改 表数据
	 *  value="ability/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="ability/abilityupdate",produces="application/json; charset=utf-8")
	@ResponseBody
	public String abilityupdate(@RequestParam(value ="file",required = false)CommonsMultipartFile []file,
			String abilitycode, String abilityname, String abilitymemo, String memo,String abilityid) throws IOException{
		System.out.println("--进入--");
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		//获取当前登录人
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
		List<String> lt=new ArrayList<String>();
		String sql= " update basic_ability set abilitycode=?,abilityname=?,abilitymemo=?,memo=? where abilityid=? " ;
		
		//注意参数顺序
		lt.add(abilitycode);lt.add(abilityname);lt.add(abilitymemo);lt.add(memo);lt.add(abilityid);
		
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		int r=tservice.UpdateSQL2(sql, lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		if(file.length > 0 && file[0].getSize() != 0) {
			//删除附件
			String del_sql = "delete from attach where cusid = '"+cusid+"' and fid = '"+abilityid+"' " +
					" and tableid = 'basic_ability' and tablecolid = 'abilityid'";
			boolean result = tservice.DeleteSQL(del_sql);
			if(result == false) {
				r = 0;
				map.put("info", "0");
				map.put("text", "操作失败请联系管理员");
				tservice.rollbackExe_close();
				return jsonObject.fromObject(map).toString();
			}
			//新增附件
			//往附件表中增加数据
			String e_result = util.insertAttach(tservice, file, abilityid, user, "basic_ability", "abilityid");
			if("0".equals(e_result)) {
				r = 0;
				map.put("info", "0");
				map.put("text", "汇报失败请联系管理员");
				tservice.rollbackExe_close();
				return jsonObject.fromObject(map).toString();
			}
		}
		map.put("info", "1");
		map.put("text", "操作成功");
		tservice.commitExe_close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 删除数据
	 * value="ability/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="ability/abilitydelete",produces="application/json; charset=utf-8")
	@ResponseBody
	public String abilitydelete(String tablejson) throws UnsupportedEncodingException{
		System.out.println("删除数据id有"+tablejson);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		String ids = tablejson.replace("\"", "'").replace("[", "").replace("]", "");
		String sql="delete from basic_ability where abilityid in ("+ids+")";
		int r=tservice.DeleteSQL2(sql);
		if(r==0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return new JSONObject().fromObject(map).toString();
		}
		//删除附件
		String dsql = "delete from attach where fid in("+ids+") and tableid = 'basic_ability' and tablecolid = 'abilityid'";
		boolean flag = tservice.DeleteSQL(dsql);
		if(!flag) {
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
			return new JSONObject().fromObject(map).toString();
		}
		tservice.commitExe_close();
	    return new JSONObject().fromObject(map).toString();
		
	}
	
	/**
	 * 查看/修改表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("ability/abilityload")
	@ResponseBody
	public ModelAndView abilityload(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		Util util = new Util();
		ModelAndView modelAndView = new ModelAndView();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String sql=" select * from basic_ability where abilityid='"+tablejson+"'";
			List<Map<String, String>> lmap=tservice.getSelect(sql);
			modelAndView.addObject("map", lmap.get(0));
			//查询附件信息
			String sel_fjsql = "select * from attach t where t.CUSID = '"+cusid+"' and t.fid = '"+tablejson+"' " +
					" and t.tableid = 'basic_ability' and t.tablecolid = 'abilityid'";
			List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql);
			if(fjmap != null && fjmap.size() > 0) {
				modelAndView.addObject("fjmap", fjmap);
			}
			modelAndView.setViewName("abilityManage/ability_updateform");
			
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
	@RequestMapping("ability/abilityadd")
	public ModelAndView useradd(String tablejson) throws Exception {
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		Util util = new Util();
		ModelAndView modelAndView = new ModelAndView();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			
			modelAndView.setViewName("abilityManage/ability_addform");
			
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
	@RequestMapping("ability/abilityindex")
	public ModelAndView abilityindex() throws Exception {
		/**
		 * 权限判定
		 * lstaskfp  角色菜单中的菜单编号
		 */
		User user= ((User)LoginController.getSession().getAttribute("user"));
		List<Map<String, String>> bpower=user.getBpower("abilitymanage");		//tservice.getSelect(G_b_sql);
		/**
		 * 1.确认当前模块权限，
		 * 2.获取按钮权限
		 */
		boolean boo=false;
		Map mb=new HashMap();
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals("abilitymanage"))boo=true;
			if(!map.get("type").equals("2"))mb.put(map.get("htmlcode"), "1");
			 
		}
		
		ModelAndView modelAndView = new ModelAndView();
		
		//角色权限增加
		if(boo){
			modelAndView.addObject("button", mb);
			modelAndView.setViewName("abilityManage/ability_list");
		}else
			modelAndView.setViewName("nopower");
			
		return modelAndView;

	}
	
	/**
	 * 下载文件
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="ability/downLoad")
	public ResponseEntity<byte[]> fileDownload(HttpServletRequest request,String id) throws Exception {
		
		BeanService_Transaction tservice = new BeanService_Transaction();
		User user = (User) LoginController.getSession().getAttribute("user");
		String userinfo = user.getUserid()+"-"+user.getUsercode();
		String tableid = "basic_ability";
		String savefile = "";
		String sourcefile = "";
		//查询附件信息
		String sel_fjsql = "select * from attach t where t.ID = ?";
		List<String> lt = new ArrayList<String>();
		lt.add(id);
		List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql, lt);
		if(fjmap != null && fjmap.size() > 0) {
			savefile = fjmap.get(0).get("savefile");
			sourcefile = fjmap.get(0).get("sourcefile");
		}
		//指定要下载的文件所在路径
		String path = LoginController.getSession().getServletContext()
                .getRealPath("/") + "resources/cusid"+user.getCusid()+"/"+tableid.replace("_", "")+"/" +savefile;
		//创建该文件对象
		File file = new File(path);
		//设置响应头
		HttpHeaders headers = new HttpHeaders();
	
		//通知浏览器以下载的方式打开文件
		
		headers.setContentDispositionFormData("attachment;",new String(sourcefile.getBytes(), "iso8859-1"));
		//定义以流的形式下载返回文件数据
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		
		
		//使用Spring MVC框架的ResponseEntity对象封装返回下载数据
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.OK);
	}
}
