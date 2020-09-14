package com.cqmi.controller.standard;

import java.io.File;
import java.io.IOException;
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
public class StaInfoController extends BasicAction{
	
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
	@RequestMapping(value="stainfo/stainfolist",produces="application/json; charset=utf-8") //,method = RequestMethod.POST
	@ResponseBody
	public String stainfolist(String page,String rows,String sort,String sortOrder,String searchjson,String actjson) throws IOException{
		System.out.println("--进入--");
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by t.filename ";
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
		String where = " where 1=1 and t.cusid = '"+cusid+"' ";
		String filename = json.getString("filename");
		String createuser = json.getString("createuser");
		
		where+=filename!=null&&!filename.trim().equals("")?" and t.filename like '%"+filename+"%'":"";
		where+=createuser!=null&&!createuser.trim().equals("")?" and t.createuser like '%"+createuser+"%'":"";
		
		if(!actjson.equals("-1")){
			where += "and c.sclassid='"+actjson+"' or c.parentid='"+actjson+"' ";
		}
		/**
		 * 查询数据sql
		 */
		String sql = " select c.sclassname,tmp.IDS,tmp.files,t.* from basic_standard t "
				   + " left join basic_standardclass c on c.sclassid = t.sclassid "
				   + " left join ( "
				   + " select FID,GROUP_CONCAT(id) ids,GROUP_CONCAT(t.SOURCEFILE) files from attach t where t.CUSID = '"+cusid+"' "
				   + " and t.tableid = 'basic_standard' and t.tablecolid = 'standardid' "
				   + " GROUP BY FID "
				   + " ) tmp on tmp.FID = t.STANDARDID "
				  +" "+where+" "+order+" limit "+num+","+rows ;
		String title[] = {"sclassname","standardid","cusid","sclassid","filename","filepath","createuser","createtime","memo","ids","files"};
		/**
		 * 数据总条数
		 */
		String sqlt = " select count(*) total from ( "
					+ " select t.* from basic_standard t "
					+ " left join basic_standardclass c on c.sclassid = t.sclassid " 
					+ where
					+ " ) a ";
					
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
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@RequestMapping(value="stainfo/stainfosave",produces="application/json; charset=utf-8")
	@ResponseBody
	public String stainfosave(@RequestParam(value ="file",required = false)CommonsMultipartFile []file,
			String filename, String filepath, String sclassid, String memo) throws IOException{
		System.out.println("--进入--");
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
//		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();

		//=============================自动生成部门编号=============================
		
		String cusid = user.getCusid();
//		String sclassid=json.optString("sclassid");
//		String filename=json.optString("filename");
//		String filepath=json.optString("filepath");
//		String memo=json.optString("memo");
		String createuser = user.getUsername();
		String createtime = datetime;
		
		String sql="insert into basic_standard (cusid,sclassid,filename,filepath,memo,createuser,createtime) value (?,?,?,?,?,?,?) ";
		
		//注意参数顺序
		lt.add(cusid);lt.add(sclassid);lt.add(filename);lt.add(filepath);lt.add(memo);
		lt.add(createuser);lt.add(createtime);
		
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		int r=tservice.InsertSQL2(sql, lt);
		if(r==0){
			map.put("info", "0");
			map.put("text", "提交失败请联系管理员");
			tservice.rollbackExe_close();
			return jsonObject.fromObject(map).toString();
		}
		//获取刚插入的ID
		String newid = "";
		String title[] = {"id"};
		List<Map<String, String>> list = tservice.getSelect("select LAST_INSERT_ID() as id",title);
//		System.out.println("==="+list.get(0));
		if(list != null && list.size()>0) {
			newid = list.get(0).get("id");
		} else {
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
		}
		if(file.length > 0 && file[0].getSize() != 0) {
			//往附件表中增加数据
			String result = util.insertAttach(tservice, file, newid, user, "basic_standard", "standardid");
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
	 *  value="user/testdeletetable"  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="stainfo/stainfoupdate",produces="application/json; charset=utf-8")
	@ResponseBody
	public String stainfoupdate(String tablejson) throws IOException{
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
	@RequestMapping(value="stainfo/stainfodelete",produces="application/json; charset=utf-8")
	@ResponseBody
	public String stainfodelete(String tablejson) throws UnsupportedEncodingException{
		System.out.println("删除数据id有"+tablejson);
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		Map map=new HashMap();
		String sql="delete from basic_standard where standardid in ("+tablejson.replace("\"", "'").replace("[", "").replace("]", "")+")";
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
	@RequestMapping("stainfo/stainfoload")
	@ResponseBody
	public ModelAndView stainfoload(String tablejson) throws Exception {
		
		BeanService_Transaction tservice=new BeanService_Transaction();
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
				
		String sql=" select j.sclassname,t.* from basic_standard as t "
				  +" left join basic_standardclass as j on j.sclassid = t.sclassid "
				  +" where t.standardid='"+tablejson+"'";
		List<Map<String, String>> lmap=tservice.getSelect(sql);
			
		ModelAndView modelAndView = new ModelAndView();
		
		if(lmap != null && lmap.size() > 0) {
			modelAndView.addObject("map", lmap.get(0));
		}
		//查询附件信息
		String sel_fjsql = "select * from attach t where t.CUSID = '"+cusid+"' and t.fid = '"+tablejson+"' " +
				" and t.tableid = 'basic_standard' and t.tablecolid = 'standardid'";
		List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql);
		if(fjmap != null && fjmap.size() > 0) {
			modelAndView.addObject("fjmap", fjmap);
		}
		modelAndView.setViewName("standard/stainfo_loadform");
		tservice.Close();
		return modelAndView;
		
	}
 
	/**
	 * 新增表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("stainfo/stainfoadd")
	public ModelAndView stainfoadd(String tablejson) throws Exception {
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String cusid = user.getCusid();
		BeanService_Transaction tservice = new BeanService_Transaction();
		
		String sql = "select * from basic_standardclass where cusid = '"+cusid+"' ";
		List<Map<String, String>> lmap = tservice.getSelect(sql);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("jfclass", lmap);
		modelAndView.setViewName("standard/stainfo_addform");
		
		return modelAndView;
		
	}
	 
	/**
	 * 最高父节点为0
	 * 获取树形数据
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="stainfo/stainfotree",produces="application/json; charset=utf-8")
	@ResponseBody
	public String stainfotree() throws IOException, SQLException{
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
	@RequestMapping("stainfo/stainfoindex")
	public ModelAndView stainfoindex() throws Exception {
		/**
		 * 权限判定
		 * lstaskfp  角色菜单中的菜单编号
		 */
		User user= ((User)LoginController.getSession().getAttribute("user"));
		List<Map<String, String>> bpower=user.getBpower("stainfo");		//tservice.getSelect(G_b_sql);
		/**
		 * 1.确认当前模块权限，
		 * 2.获取按钮权限
		 */
		boolean boo=false;
		Map mb=new HashMap();
		for (Map<String, String> map : bpower) {
			if(map.get("htmlcode").equals("stainfo"))boo=true;
			if(!map.get("type").equals("2"))mb.put(map.get("htmlcode"), "1");
			 
		}
		
		ModelAndView modelAndView = new ModelAndView();
		
		//角色权限增加
		if(boo){
			modelAndView.addObject("button", mb);
			modelAndView.setViewName("standard/stainfo");
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
	@RequestMapping(value="stainfo/downLoad")
	public ResponseEntity<byte[]> fileDownload(HttpServletRequest request,String id) throws Exception {
		
		BeanService_Transaction tservice = new BeanService_Transaction();
		User user = (User) LoginController.getSession().getAttribute("user");
		String userinfo = user.getUserid()+"-"+user.getUsercode();
		String tableid = "basic_standard";
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
