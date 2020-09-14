package com.cqmi.controller.equipManage;

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
@RequestMapping("/equip")
public class EquipController extends BasicAction{
	
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
		System.out.println("--进入(equip/list)--");
		BeanService_Transaction tservice=new BeanService_Transaction();
		
		if(searchjson != null) {
			searchjson =new String(searchjson.getBytes("ISO8859-1"), "UTF-8"); // java.net.URLDecoder.decode(actjson,"utf-8"); 
		}
		
		String order="";
		if(sort!=null&&!sort.trim().equals("")){
			order=(" order by "+sort+" "+sortOrder);
		}else{
			order = " order by equipname ";
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
		
		String equipcode = json.getString("equipcode");
		String equipname = json.getString("equipname");
		String estate = json.getString("estate");
		
		where += equipcode != null && !equipcode.trim().equals("")?" and t.equipcode like '%"+equipcode+"%'":"";
		where += equipname != null && !equipname.trim().equals("")?" and equipname like '%"+equipname+"%'":"";
		where += estate != null && !estate.trim().equals("")?" and estate = '"+estate+"'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from basic_equip t "
				  +" "+where+" "+order+" limit "+num+","+rows ;
		/**
		 * 数据总条数
		 */
		String sqlt =" select count(*) total from basic_equip t  "
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
	 * 新增数据 
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/save",produces="application/json; charset=utf-8")
	@ResponseBody
	public String save(String tablejson) throws IOException{
		System.out.println("--进入(equip/save)--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		//获取当前时间
		String datetime = util.getNowTime();
		
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
				
		//tablejson 转为json
		JSONObject json=jsonObject.fromObject(tablejson);
		List<String> lt=new ArrayList<String>();
				
		/**
		 * 防注入使用
		 */
		String sql = " insert into basic_equip(CUSID,EQUIPCODE,EQUIPNAME," +
				" DEPARTID,DEPARTNAME,ESTATE," +
				" EQUIPDESC,MEMO,CREATEUSER," +
				" CREATETIME) " + 
				" value (?,?,?," +
				" ?,?,?," +
				" ?,?,?," +
				" ?) ";
		String cusid = user.getCusid();
		String equipcode = json.optString("equipcode");
		String equipname = json.optString("equipname");
		String departid = json.optString("departid");
		String departname = json.optString("departname");
		String estate = json.optString("estate");
		String equipdesc = json.optString("equipdesc");
		String memo = json.optString("memo");
		String creater = user.getUsername();
		String creattime = datetime;
		
		//注意参数顺序
		lt.add(cusid);lt.add(equipcode);lt.add(equipname);
		lt.add(departid);lt.add(departname);lt.add(estate);
		lt.add(equipdesc);lt.add(memo);lt.add(creater);
		lt.add(creattime);
		
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		int r;
		Map map = new HashMap();
		r = tservice.InsertSQL2(sql, lt);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			
			tservice.rollbackExe_close();
		}
		tservice.commitExe_close();
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 修改 表数据
	 *  value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/update",produces="application/json; charset=utf-8")
	@ResponseBody
	public String update(String tablejson) throws IOException{
		System.out.println("--进入(equip/update)--"+tablejson);
		JSONObject jsonObject=new JSONObject();
		JSONObject json=jsonObject.fromObject(tablejson);
		BeanService_Transaction tservice=new BeanService_Transaction();
		//开起事物
		tservice.OpenTransaction();
		//获取参数值
		String equipid = json.optString("equipid");
		String equipcode = json.optString("equipcode");
		String equipname = json.optString("equipname");
		String departid = json.optString("departid");
		String departname = json.optString("departname");
		String estate = json.optString("estate");
		String equipdesc = json.optString("equipdesc");
		String memo = json.optString("memo");
		
		List<String> lt=new ArrayList<String>();
		String sql= " update basic_equip set " +
				" equipcode=?,equipname=?,departid=?," +
				" departname=?,estate=?,equipdesc=?,memo=? " +
				" where equipid=? " ;
		
		//注意参数顺序
		lt.add(equipcode);lt.add(equipname);lt.add(departid);
		lt.add(departname);lt.add(estate);lt.add(equipdesc);
		lt.add(memo);lt.add(equipid);
		
		Map map = new HashMap();
		int r = tservice.UpdateSQL2(sql, lt);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
			tservice.rollbackExe_close();
		}else{
			tservice.commitExe_close();
		}
	    return jsonObject.fromObject(map).toString();
	}
	
	/**
	 * 删除数据
	 * value=""  访问路径
	 * produces="application/json; charset=utf-8" 防止回传汉字乱码
	 * @param tablejson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
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
		String sql = "delete from basic_equip where equipid in ("+tablejson.replace("\"", "'").replace("[", "").replace("]", "")+")";
		int r = tservice.DeleteSQL2(sql);
		if(r == 0){
			map.put("info", "0");
			map.put("text", "操作失败请联系管理员");
		
			tservice.rollbackExe_close();
		}else{
			tservice.commitExe_close();
		}
	    return new JSONObject().fromObject(map).toString();
		
	}
	
	/**
	 * 判断编码是否存在
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/codeIsExist",produces="application/json; charset=utf-8")
	@ResponseBody
	public String isExist(String tablejson) throws IOException {
		/**
		 * 也可以使用 SQLProxy对象操作
		 */
		BeanService_Transaction tservice = new BeanService_Transaction();
		Map map = new HashMap();
		try {
			JSONObject jsonObject = new JSONObject();
			JSONObject json = jsonObject.fromObject(tablejson);
			String equipcode = json.optString("equipcode");
			String equipid = json.optString("equipid");
			//判断编码是否存在
			String sql = "select * from basic_equip t where t.equipcode = ?";
			List<String> slt = new ArrayList<String>();
			slt.add(equipcode);
			List<Map<String, String>> list = tservice.getSelect(sql, slt);
			
			if(equipid == null || "".equals(equipid)) {
				//判断编码是否存在
				if(list != null && list.size() > 0) {
					map.put("info", "0");
					map.put("textinfo", "设备编码已存在，请重新输入！！！");
				} else {
					map.put("info", "1");
				}
			} else {
				String sql1 = "select * from basic_equip t where t.equipid = ?";
				List<String> lt = new ArrayList<String>();
				lt.add(equipid);
				List<Map<String, String>> lmap = tservice.getSelect(sql1, lt);
				String code = "";
				if(lmap != null && lmap.size() > 0) {
					code = lmap.get(0).get("equipcode");
				}
				if(!code.equals(equipcode)) {
					if(list != null && list.size() > 0) {
						map.put("info", "0");
						map.put("textinfo", "设备编码已存在，请重新输入！！！");
					} else {
						map.put("info", "1");
					}
				} else {
					map.put("info", "1");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			map.put("info", "2");
		}
		return new JSONObject().fromObject(map).toString();
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
			
			String sql = " select * from basic_equip where equipid = '"+tablejson+"'";
			List<Map<String, String>> lmap = tservice.getSelect(sql);
			if(lmap != null && lmap.size() > 0) {
				modelAndView.addObject("map", lmap.get(0));
			}
			modelAndView.setViewName("equipManage/equipInfo");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
		
	}
	
	/**
	 * 修改表单
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateForm")
	@ResponseBody
	public ModelAndView updateForm(String tablejson) throws Exception {
		
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String sql = " select * from basic_equip where equipid = '"+tablejson+"'";
			List<Map<String, String>> lmap = tservice.getSelect(sql);
			if(lmap != null && lmap.size() > 0) {
				modelAndView.addObject("map", lmap.get(0));
			}
			List<Map<String, String>> deptList = util.getDeptInfo(tservice, cusid);
			modelAndView.addObject("deptList", deptList);
			modelAndView.setViewName("equipManage/equipUpdate");
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
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			//查询部门信息
			
			List<Map<String, String>> lmap = util.getDeptInfo(tservice, cusid);
			modelAndView.setViewName("equipManage/equipAdd");
			modelAndView.addObject("deptList", lmap);
			
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
		String code = "equipManage";
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
			modelAndView.setViewName("equipManage/list");
		} else {
			modelAndView.setViewName("nopower");
		}
		return modelAndView;

	}
}
