package com.cqmi.controller.taskManage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.axis2.databinding.types.soapencoding.Array;
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
@RequestMapping("/taskInfo")
public class TaskInfoController {
	
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
		String userid = user.getUserid();
				
		/**
		 * 查询条件
		 */
		String where="where 1=1 ";
		String taskcode = json.getString("taskcode");
		String tasktitle = json.getString("tasktitle");
		String taskstate = json.getString("taskstate");
		where += taskcode != null && !taskcode.trim().equals("")?" and t.taskcode like '%"+taskcode+"%'":"";
		where += tasktitle != null && !tasktitle.trim().equals("")?" and t.tasktitle like '%"+tasktitle+"%'":"";
		where += taskstate != null && !taskstate.trim().equals("")?" and taskstate = '"+taskstate+"'":"";
		
		/**
		 * 查询数据sql
		 */
		String sql=" select * from (select u.username,t.* from taskinfo t " +
				" left join basic_user u on u.userid = t.CREATEUSER ) t "
				  +" "+where+" "+order+" limit "+num+","+rows ;
		
		String title[] = {"taskid","cusid","orderid","taskcode","tasktitle","taskmemo","taskstate","createuser","createtime","username"};
		/**
		 * 数据总条数
		 */
		String sqlt="select count(1) total from taskinfo t "+where;
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
	
	
}
