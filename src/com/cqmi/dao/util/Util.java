package com.cqmi.dao.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cqmi.controller.login.LoginController;
import com.cqmi.controller.login.bean.User;
import com.cqmi.db.service.BeanService_Transaction;

public class Util {
	
	/**
	 * 往附件表中加入数据
	 * @param tservice
	 * @param file
	 * @param fid
	 * @param user
	 * @param tableid
	 * @param tablecolid
	 * @return
	 * @throws IOException
	 */
	public String insertAttach(BeanService_Transaction tservice, CommonsMultipartFile []file, 
			String fid, User user, String tableid, String tablecolid) throws IOException {
		
		String result = "1";
		
		int r;
		String userid = user.getUserid();
		String username = user.getUsername();
		String cusid = user.getCusid();
		String createtime = this.getNowTime();
		try {
			//往附件表中增加数据
			for(int i=0; i<file.length; i++) {
				
				List<String> lt_a = new ArrayList<String>();
				String sourcefile = file[i].getOriginalFilename();
				String savefile = sourcefile.substring(0, sourcefile.lastIndexOf("."))+"_"+System.currentTimeMillis()+sourcefile.substring(sourcefile.lastIndexOf("."),sourcefile.length());
//				String savefile = System.currentTimeMillis()+sourcefile.substring(sourcefile.lastIndexOf("."),sourcefile.length());
				String filesize = file[i].getSize()+"";
				
				String insert_esql = "insert into attach(cusid,fid,type,tableid,tablecolid,fieldid,fieldname," +
						" savefile,sourcefile,filesize,donetime,creator,creatername) " +
						" values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				lt_a.add(cusid);lt_a.add(fid);lt_a.add("F");
				lt_a.add(tableid);lt_a.add(tablecolid);lt_a.add("");lt_a.add("");
				lt_a.add(savefile);lt_a.add(sourcefile);lt_a.add(filesize);
				lt_a.add(createtime);lt_a.add(userid);lt_a.add(username);
				r = tservice.InsertSQL2(insert_esql, lt_a);
				if(r == 0){
					result = "0";
					tservice.rollbackExe_close();
				}
				/**
		        *文件保存 
		        */
				String userinfo = user.getUserid()+"-"+user.getUsercode();
		        String path = LoginController.getSession().getServletContext()
		                .getRealPath("/") + "resources/cusid"+user.getCusid()+"/"+tableid.replace("_", ""); //+"/" +uploadfilename; 
		        
		        File folder = new File(path);
		        path+="/" +savefile;
		        /**
		         * 判断文件路径是否存在，
		         */
				if (!folder.exists() && !folder.isDirectory()) {
					if(folder.mkdirs()){
						 File newFile=new File(path);
					     //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
					     file[i].transferTo(newFile); 
					}else{
	//					map.put("info", "0");
	//					map.put("textinfo", "服务器繁忙，请稍后");
	//					return JSONObject.fromObject(map).toString();
						result = "0";
					}
	//				System.out.println("创建目录成功");
				}else{
					 File newFile=new File(path);
				     //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
				     file[i].transferTo(newFile); 
				}  
			}
		} catch(Exception e) {
			e.printStackTrace();
			result = "0";
		}
		return result;
	}
	
	/**
	 * 下载文件
	 * @param request
	 * @param id       附件ID
	 * @param tableId  表名
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<byte[]> fileDownload(HttpServletRequest request,String id, String tableId) throws Exception {
		
		BeanService_Transaction tservice = new BeanService_Transaction();
		User user = (User) LoginController.getSession().getAttribute("user");
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
                .getRealPath("/") + "resources/cusid"+user.getCusid()+"/"+tableId.replace("_", "")+"/" +savefile;
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
	
	/**
	 * 获取部门信息
	 * @return
	 */
	public List<Map<String, String>> getDeptInfo(BeanService_Transaction tservice, String cusid) {
		
		String sql = "select * from basic_depart where STATE = '1' and cusid = '"+cusid+"' order by departcode";
		List<Map<String, String>> deptMap = tservice.getSelect(sql);
		return deptMap;
	}
	/**
	 * 获取按部门分组的用户
	 * @return
	 */
	public Map<String, List<Map>> getGroupUser(BeanService_Transaction tservice, String cusid) {
		
		Map dataItem; // 数据库中查询到的每条记录 
		Map<String, List<Map>> resultMap = new LinkedHashMap<String, List<Map>>(); // 最终要的结果 
		String sql = " select d.departname nowdepartname,u.* from basic_user u "
				   + " left join basic_depart d on d.departid = u.departid " 
				   + " where u.STATE = '1' and u.cusid = '"+cusid+"' and ifnull(u.issa,0) <> '1' " 
				   + " order by d.departcode,u.username ";
		
		String title[] = {"nowdepartname","userid","username"};
		List<Map<String, String>> userList = tservice.getSelect(sql,title);
		for(int i=0; i<userList.size(); i++){ 
			
		    dataItem = userList.get(i); 
		    if(resultMap.containsKey(dataItem.get("nowdepartname"))){ 

		        resultMap.get(dataItem.get("nowdepartname")).add(dataItem); 
		    }else{ 
		    	
		        List<Map> list = new ArrayList<Map>(); 
		        list.add(dataItem); 
		        resultMap.put((String)dataItem.get("nowdepartname"),list); 
		    } 
		}
		return resultMap;
	}
	/**
	 * 获取按部门分组的用户
	 * @return
	 */
	public Map<String, List<Map>> getUserByDeptId(BeanService_Transaction tservice, String cusid, String departid) {
		
		Map dataItem; // 数据库中查询到的每条记录 
		Map<String, List<Map>> resultMap = new LinkedHashMap<String, List<Map>>(); // 最终要的结果 
		String sql = " select d.departname nowdepartname,u.* from basic_user u " 
				   + " left join basic_depart d on d.departid = u.departid " 
				   + " where u.STATE = '1' and u.cusid = '"+cusid+"' and ifnull(u.issa,0) <> '1'" ;
		if(!"".equals(departid) && departid != null) {
			sql += "and departid = '"+departid+"'";
		}
		sql += "  order by d.departcode,u.username ";
		
		String title[] = {"nowdepartname","userid","username"};
		List<Map<String, String>> userList = tservice.getSelect(sql,title);
		for(int i=0; i<userList.size(); i++){ 
			
		    dataItem = userList.get(i); 
		    if(resultMap.containsKey(dataItem.get("nowdepartname"))){ 

		        resultMap.get(dataItem.get("nowdepartname")).add(dataItem); 
		    }else{ 
		    	
		        List<Map> list = new ArrayList<Map>(); 
		        list.add(dataItem); 
		        resultMap.put((String)dataItem.get("nowdepartname"),list); 
		    } 
		}
		return resultMap;
	}
	/**
	 * 获取部门下员工
	 * @param tservice
	 * @param cusid
	 * @param departid
	 * @return
	 */
	public List<Map<String, String>> getUserList(BeanService_Transaction tservice, String cusid, String departCode) {
		
		List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
		String sql = "select t.USERID,t.USERNAME from basic_user t\n" +
				"left join basic_depart d on d.DEPARTID = t.DEPARTID\n" +
				"where t.CUSID = '"+cusid+"' and d.DEPARTCODE like '"+departCode+"%'";
		userList = tservice.getSelect(sql);
		return userList;
	}
	
	/**
	 * 获取按权限分组的用户
	 * @return
	 */
	public Map<String, List<Map>> getUserByFsId(BeanService_Transaction tservice, String cusid, String fs) {
		
		Map dataItem; // 数据库中查询到的每条记录 
		Map<String, List<Map>> resultMap = new LinkedHashMap<String, List<Map>>(); // 最终要的结果 
		String sql = " select d.departname nowdepartname,u.* from basic_user u "
				   + " left join jk_jfconfig c on c.configid = u.configid "
				   + " left join basic_depart d on d.departid = u.departid " 
				   + " where u.cusid = '"+cusid+"' and ifnull(issa,0) <> '1'";
		
		if(!"".equals(fs) && fs != null && Integer.parseInt(fs) >= 0){
			sql += " and "+fs+" >= c.jfxx and "+fs+" <= c.jfsx";
		}
		if(!"".equals(fs) && fs != null && Integer.parseInt(fs) <= 0){
			sql += " and "+fs+" <= c.kfxx and "+fs+" >= c.kfsx";
		}
		sql += "  order by d.departcode,u.username ";
		
		String title[] = {"nowdepartname","userid","username"};
		List<Map<String, String>> userList = tservice.getSelect(sql,title);
		for(int i=0; i<userList.size(); i++){ 
			
		    dataItem = userList.get(i); 
		    if(resultMap.containsKey(dataItem.get("nowdepartname"))){ 

		        resultMap.get(dataItem.get("nowdepartname")).add(dataItem); 
		    }else{ 
		    	
		        List<Map> list = new ArrayList<Map>(); 
		        list.add(dataItem); 
		        resultMap.put((String)dataItem.get("nowdepartname"),list); 
		    } 
		}
		return resultMap;
	}
	
	/**
	 * 获取职级不高于自己的分组用户
	 * 用于筛选奖扣人
	 */
	public Map<String, List<Map>> getUserByZWDJ(BeanService_Transaction tservice, String cusid) {
		//获取登录信息
		User user = (User) LoginController.getSession().getAttribute("user");
		String userid = user.getUserid();
		
		Map dataItem; // 数据库中查询到的每条记录 
		Map<String, List<Map>> resultMap = new LinkedHashMap<String, List<Map>>(); // 最终要的结果 
		
		List<Map<String, String>> userList = new ArrayList<Map<String,String>>();
		
		//查询当前用户职级
		String zjql = "select ifnull(pp.zwdj,0) zwdj from basic_user uu left join basic_position pp on uu.pid = pp.pid where uu.userid = '"+userid+"' ";
		List<Map<String, String>> zjmap = tservice.getSelect(zjql);
		String zwdj = zjmap.get(0).get("zwdj");
		
		//查询没有职级的人员
		if(zwdj == "0" || "0".equals(zwdj)){
			String sql = " select d.departname nowdepartname,u.* from basic_user u "
					   + " left join basic_position p on p.pid = u.pid "
					   + " left join basic_depart d on d.departid = u.departid " 
					   + " where u.cusid = '"+cusid+"' and ifnull(issa,0) <> '1' "
					   + " and (p.zwdj = '' or p.zwdj is null ) ";
			sql += "  order by d.departcode,u.username ";
			String title[] = {"nowdepartname","userid","username"};
			userList = tservice.getSelect(sql,title);
		}else{
			String sql = " select d.departname nowdepartname,u.* from basic_user u "
					   + " left join basic_position p on p.pid = u.pid "
					   + " left join basic_depart d on d.departid = u.departid " 
					   + " where u.cusid = '"+cusid+"' and ifnull(issa,0) <> '1' "
					   + " and (cast(p.zwdj as signed) >= ifnull((select pp.zwdj from basic_user uu left join basic_position pp on uu.pid = pp.pid where uu.userid = '"+userid+"'),0)" +
					   		" or p.zwdj = '' or p.ZWDJ is null ) ";
			sql += "  order by d.departcode,u.username ";
			String title[] = {"nowdepartname","userid","username"};
			userList = tservice.getSelect(sql,title);
		}
		
		
		for(int i=0; i<userList.size(); i++){ 
			
		    dataItem = userList.get(i); 
		    if(resultMap.containsKey(dataItem.get("nowdepartname"))){ 

		        resultMap.get(dataItem.get("nowdepartname")).add(dataItem); 
		    }else{ 
		    	
		        List<Map> list = new ArrayList<Map>(); 
		        list.add(dataItem); 
		        resultMap.put((String)dataItem.get("nowdepartname"),list); 
		    } 
		}
		return resultMap;
	}
	
	/**
	 * 获取职级不低于自己的分组用户
	 * 用于筛选审核人
	 */
	public Map<String, List<Map>> getUserByZWDJ_g(BeanService_Transaction tservice, String cusid,String userid) {
		//获取登录信息
//		User user = (User) LoginController.getSession().getAttribute("user");
//		String userid = user.getUserid();
		
		Map dataItem; // 数据库中查询到的每条记录 
		Map<String, List<Map>> resultMap = new LinkedHashMap<String, List<Map>>(); // 最终要的结果 
		String sql = " select d.departname nowdepartname,u.* from basic_user u "
				   + " left join basic_position p on p.pid = u.pid "
				   + " left join basic_depart d on d.departid = u.departid " 
				   + " where u.cusid = '"+cusid+"' and ifnull(issa,0) <> '1' "
				   + " and cast(p.zwdj as signed) < ifnull((select pp.zwdj from basic_user uu left join basic_position pp on uu.pid = pp.pid where uu.userid = '"+userid+"'),9999) ";
		sql += "  order by u.departcode,u.username ";
		String title[] = {"nowdepartname","userid","username"};
		List<Map<String, String>> userList = tservice.getSelect(sql,title);
		for(int i=0; i<userList.size(); i++){ 
			
		    dataItem = userList.get(i); 
		    if(resultMap.containsKey(dataItem.get("nowdepartname"))){ 

		        resultMap.get(dataItem.get("nowdepartname")).add(dataItem); 
		    }else{ 
		    	
		        List<Map> list = new ArrayList<Map>(); 
		        list.add(dataItem); 
		        resultMap.put((String)dataItem.get("nowdepartname"),list); 
		    } 
		}
		return resultMap;
	}
	
	//获取人员名称
	public String getUsername(String userid, BeanService_Transaction tservice) {
		
		String username = "";
		String sql = "select * from basic_user where userid ="+userid;
		List<Map<String, String>> ulist = tservice.getSelect(sql);
		if(ulist != null && ulist.size()>0) {
			username = ulist.get(0).get("username");
		}
		return username;
	}
	//获取当前时间
	public String getNowTime() {
		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()); 
	}
	//获取当前日期
	public String getNowDate() {
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); 
	}
	//获取当年
	public String getNowYear() {
		return new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()); 
	}
	//获取当年
	public String getNowYm() {
		return new java.text.SimpleDateFormat("yyyyMM").format(new java.util.Date()); 
	}
	
	/**
	 * 获取本周开始日期
	 * @return
	 */
	public String getWeekStart(){

        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if(dayWeek == 1){
            dayWeek = 8;
        }
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
        Date time = cal.getTime();
        String weekBegin = new SimpleDateFormat("yyyy-MM-dd").format(time);  
//        System.out.println("所在周星期一的日期：" + weekBegin);  
        return weekBegin;
    }

    /**

     * 获取本周的最后一天

     * @return String

     * **/

    public String getWeekEnd(){

    	Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if(dayWeek == 1){
            dayWeek = 8;
        }
        cal.add(Calendar.DATE, 8-dayWeek);
        Date time = cal.getTime();
        String weekEnd = new SimpleDateFormat("yyyy-MM-dd").format(time);  
//        System.out.println("所在周星期日的日期：" + weekEnd);  
        return weekEnd;
    }
    /**
     * 获取当月第一天
     * @return
     */
    public String getMonthStart() {
    	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        //获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();//获取当前日期 
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String firstDay = format.format(cal_1.getTime());
        return firstDay;
	}
    /**
     * 获取当月最后一天
     * @return
     */
	public String getMonthEnd() {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();   
        cale.set(Calendar.DAY_OF_MONTH,cale.getActualMaximum(Calendar.DAY_OF_MONTH));//设置为1号,当前日期既为本月第一天 
        String lastDay = format.format(cale.getTime());
        return lastDay;
	}

}
