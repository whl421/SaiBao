package com.cqmi.controller.dataimport;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cqmi.controller.login.bean.User;
import com.cqmi.controller.login.util.ExcelUilt;
import com.cqmi.dao.util.Util;
import com.cqmi.db.dao.Loggerlog;
import com.cqmi.db.service.BeanService_Transaction;

@Controller
public class kqImpController {
	    
	Util util = new Util();
	
    /**
	 * 文件上传
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="kqimport/upkq",produces="application/json; charset=utf-8")
	public ModelAndView upkq(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("dataimport/upkq");
		return modelAndView;
	}
	
	/*
	 * 文件上传
     * 采用file.Transto 来保存上传的文件
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="kqimport/fileUpload",produces="application/json; charset=utf-8")
    @ResponseBody
    public String  fileUpload(@RequestParam(value ="file",required = false)CommonsMultipartFile file,String manageIntegral) 
    		throws IOException {
    	
    	BeanService_Transaction tservice = new BeanService_Transaction();
    	tservice.OpenTransaction();
    	User user=(User)kqImpController.getSession().getAttribute("user");
    	String cusid = user.getCusid();
        String fileName=file.getOriginalFilename();
        String name=file.getName();
        Long size=file.getSize();
        
        System.out.println("manageIntegral="+manageIntegral);
        System.out.println("fileName="+fileName);
        System.out.println("name="+name);
        System.out.println("size="+size);
        System.out.println("getContentType="+file.getContentType());
        System.out.println("getStorageDescription="+file.getStorageDescription());
        
        Loggerlog.log("info").warn("-客户:"+user.getCusid()+",用户:"+user.getUserid()+",访问用户"+user.getUsername()+",上传文件：");
      
        String uploadfilename=System.currentTimeMillis()+fileName;
        Map map=new HashMap();
       /**
        *文件保存 
        */
        String path = kqImpController.getSession().getServletContext()
                .getRealPath("/") + "resources/cusid"+user.getCusid()+"/"+"kqimport"; //+"/" +uploadfilename; 
        
        File folder = new File(path);
        path+="/" +uploadfilename;
        
        /**
		 * 文件读取，可以不保存文件
		 * 文件EXCEL直接读流
		 * 			返回的list集合，集合内未MAP对象，key为第一行标题
		 *          可以不保存文件，直接读取文件
		 */
        ExcelUilt excelutil=new ExcelUilt();
        
        /**
         * 判断文件路径是否存在，
         */
		if (!folder.exists() && !folder.isDirectory()) {
			if(folder.mkdirs()){
				 File newFile=new File(path);
			     //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
			     file.transferTo(newFile); 
			}else{
				map.put("info", "0");
				map.put("textinfo", "服务器繁忙，请稍后");
				
//				excelutil.WbIs_Colse();
				return JSONObject.fromObject(map).toString(); 
			}
			System.out.println("创建目录成功");
		}else{
			 File newFile=new File(path);
		     //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		     file.transferTo(newFile); 
		}  
//		excelutil.WbIs_Colse();
		
		List<Map<String,String>> lemap=excelutil.Excel_NoColse(fileName, 0, path);
		
		//遍历解析出来的lemap  LinkedHashMap
		String errStr = "";
		try {
			Map<String, List<Map>> uMap = new HashMap<String, List<Map>>();
			String timestamp = System.currentTimeMillis()+"";
			for (Map<String,String> mp : lemap) {
				List<Map> uList = new ArrayList<Map>();
				Map ulmap = new HashMap();
	        	int i = 1;
	        	List<String> list = new ArrayList<String>();
	            for (Entry<String,String> entry : mp.entrySet()) {
//	                System.out.println(entry.getKey()+":"+entry.getValue()+",");
	                list.add(entry.getValue());
	            }
	            String usercode = list.get(0);
                String username = list.get(1);
                String dktime =list.get(2);
                
                if("".equals(usercode) || usercode == null) {
                	errStr = "第"+i+"行中的工号不能为空";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
                }
                if("".equals(username) || username == null) {
                	errStr = "第"+i+"行中的姓名不能为空";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
                }
                if("".equals(dktime) || dktime == null) {
                	errStr = "第"+i+"行中的打卡时间不能为空";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
                }
                //日期格式判断
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                	Date date = format.parse(dktime);
            	} catch (ParseException e) {
            		errStr = "第"+i+"行中的打卡时间格式不对，正确格式为2009-01-01 12:09:12";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
            	}
                //查询工号是否存在
                String usql = "select * from basic_user t where t.CUSID = ? and t.usercode = ?";
                List<String> ult = new ArrayList<String>();
                ult.add(cusid);ult.add(usercode);
                List<Map<String, String>> userMap = tservice.getSelect(usql, ult);
                if(userMap != null && userMap.size()>0) {
                	String userid = userMap.get(0).get("userid");
                	String departid = userMap.get(0).get("departid");
                	List<String> lt=new ArrayList<String>();
                	//新增考勤记录
                	String sql = " insert into kq_userlist (KQID,USERID,DEPARTID,DATIME,CREATEUSERID,CREATEUSER,CREATETIME,ISFLAG,TIMESTAMP,CUSID) "
         				   + " value (?,?,?,?,?,?,?,?,?,?) ";
	         		String kqid = "";
	         		String creattime = util.getNowTime();
	         		
	         		//注意参数顺序
	         		lt.add(kqid);lt.add(userid);lt.add(departid);lt.add(dktime);
	         		lt.add(user.getUserid());lt.add(user.getUsername());
	         		lt.add(creattime);lt.add("0");lt.add(timestamp);lt.add(cusid);
	         		int r = tservice.InsertSQL2(sql, lt);
	        		if(r==0){
	        			errStr = "导入失败请联系管理员";
	        			map.put("info", "0");
	        			map.put("textinfo", "导入失败请联系管理员");
	        			tservice.rollbackExe_close();
	        		}
//	        		//存放在Map中
//	        		ulmap.put("usercode", usercode);
//	        		ulmap.put("username", username);
//	        		ulmap.put("userid", userid);
//	        		ulmap.put("cusid", cusid);
//	        		ulmap.put("departid", departid);
//	                if(uMap.containsKey(usercode)) {
//	                	uMap.get(usercode).add(ulmap);
//	                } else {
//	                	uList.add(ulmap);
//	                	uMap.put(usercode, uList);
//	                }
                } else {
                	errStr = "第"+i+"行中的工号在系统中不存在";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
                }
                i++;
	        }
			//查询考勤分数设置好
			String sql = "select * from kq_config t where t.cusid = '"+cusid+"'";
			List<Map<String, String>> kqList = tservice.getSelect(sql);
//			System.out.println("kqlist="+kqList.toString());
			//查询排班记录
			String sql1 = "select w.starttime,w.endtime,t.* from basic_teamuser t " +
					" left join basic_workteam w on w.TEAMID = t.TEAMID where t.cusid = '"+cusid+"'";
			List<Map<String, String>> pbList = tservice.getSelect(sql1);
			//查询考勤数据
			Map<String, List<Map>> kqMap = this.getKqListByUser(tservice, cusid, timestamp);
			Iterator<String> its = kqMap.keySet().iterator();
			while (its.hasNext()) {
				String key = its.next();
				List<Map> lmp = kqMap.get(key);
//				System.out.println("lmp="+lmp.toString());
				if(lmp != null && lmp.size() > 0) {
					String kqlistid = (String)lmp.get(0).get("kqlistid");
					String userid = (String)lmp.get(0).get("userid");
					String username = (String)lmp.get(0).get("username");
					String departid = (String)lmp.get(0).get("departid");
					String departname = (String)lmp.get(0).get("departname");
					String kqdate = ((String)lmp.get(0).get("datime")).substring(0, 10);
					String sbtime = (String)lmp.get(0).get("datime");
					String createuserid = user.getUserid();
					String createuser = user.getUsername();
					String createtime = util.getNowTime();
					String sbresult = "";
					String sbdf = "";
					String xbtime = "";
					String xbresult = "";
					String xbdf = "";
					String stime = "";
					String etime = "";
					if(lmp.size() > 1) {
						xbtime = (String)lmp.get(lmp.size()-1).get("datime");
					} else {
						if(sbtime.compareTo(kqdate+" 12:00:00") > 0) {
							xbtime = sbtime;
							sbtime = "";
						}
					}
					if(pbList != null && pbList.size() > 0) {
						for(int j=0; j<pbList.size(); j++) {
							String starttime = pbList.get(j).get("starttime");
							String endtime = pbList.get(j).get("endtime");
							String uid = pbList.get(j).get("userid");
							String workdate = pbList.get(j).get("workdate");
							if(uid.equals(userid) && kqdate.equals(workdate)) {
								stime = workdate+" "+starttime+":00";
								etime = workdate+" "+endtime+":00";
								break;
							}
						}
					}
					String sb_dkresult = "";
					SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					long sb_minute = 0;
					if(!"".equals(sbtime) && sbtime != null) {
						if(sbtime.compareTo(stime) < 0) {	//早到
							sbresult = "1";
							sb_dkresult = "1";
							long diff = sdf.parse(stime).getTime() - sdf.parse(sbtime).getTime();
							sb_minute = diff/1000/60;
						} else {	//迟到
							sbresult = "2";
							sb_dkresult = "2";
							long diff = sdf.parse(sbtime).getTime() - sdf.parse(stime).getTime();
							sb_minute = diff/1000/60;
						}
					}
					long xb_minute = 0;
					String xb_dkresult = "";
					if(!"".equals(xbtime) && xbtime != null) {
						if(xbtime.compareTo(etime) < 0) {	//早退
							xbresult = "3";
							xb_dkresult = "3";
							long diff = sdf.parse(etime).getTime() - sdf.parse(xbtime).getTime();
							xb_minute = diff/1000/60;
						} else {	//加班
							xbresult = "4";
							xb_dkresult = "4";
							long diff = sdf.parse(xbtime).getTime() - sdf.parse(etime).getTime();
							xb_minute = diff/1000/60;
						}
					}
					//计算分数
					String sb_jsff = "";
					String sb_fdscore = "";
					if(kqList != null && kqList.size() > 0) {
						for(int m=0; m< kqList.size(); m++) {
							String sbtype = kqList.get(m).get("sbtype");
							String result = kqList.get(m).get("sbresult");
							String jsff = kqList.get(m).get("jsff");
							String fdscore = kqList.get(m).get("fdscore");
							if(fdscore == null) {
								fdscore = "";
							}
							if("1".equals(sbtype) && sb_dkresult.equals(result)) {
								sb_jsff = jsff;
								sb_fdscore = fdscore;
								break;
							}
						}
					}
					String xb_jsff = "";
					String xb_fdscore = "";
					if(kqList != null && kqList.size() > 0) {
						for(int n=0; n< kqList.size(); n++) {
							String sbtype = kqList.get(n).get("sbtype");
							String result = kqList.get(n).get("sbresult");
							String jsff = kqList.get(n).get("jsff");
							String fdscore = kqList.get(n).get("fdscore");
							if(fdscore == null) {
								fdscore = "";
							}
							if("2".equals(sbtype) && xb_dkresult.equals(result)) {
								xb_jsff = jsff;
								xb_fdscore = fdscore;
								break;
							}
						}
					}
					//计算上班得分  下班得分
					if(!"".equals(sb_jsff) && sb_jsff != null) {
						sbdf = this.getScore(sb_minute+"", sb_jsff, sb_fdscore);
					}
					if(!"".equals(xb_jsff) && xb_jsff != null) {
						xbdf = this.getScore(xb_minute+"", xb_jsff, xb_fdscore);
					}
//					System.out.println("上班得分="+sbdf);
//					System.out.println("下班得分="+xbdf);
					//插入语句
					String insert_sql = "insert into kq_user(USERID,USERNAME,DEPARTID,DEPARTNAME,KQDATE," +
							" SBTIME,SBRESULT,SBDF,XBTIME,XBRESULT,XBDF,CREATEUSERID,CREATEUSER,CREATETIME,KQLISTID,CUSID) " +
							" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					List<String> lt = new ArrayList<String>();
					lt.add(userid);lt.add(username);lt.add(departid);lt.add(departname);lt.add(kqdate);
					lt.add(sbtime);lt.add(sbresult);lt.add(sbdf);
					lt.add(xbtime);lt.add(xbresult);lt.add(xbdf);
					lt.add(createuserid);lt.add(createuser);lt.add(createtime);lt.add(kqlistid);lt.add(cusid);
					int r = tservice.InsertSQL2(insert_sql, lt);
					if(r == 0) {
						errStr = "新增失败";
						map.put("info", "0");
						map.put("textinfo", "导入失败请联系管理员");
						tservice.rollbackExe_close();
					}
				}
				
			}
			//更新考勤明细
			String update_sql = "update kq_userlist set isflag = '1' where TIMESTAMP = '"+timestamp+"' and CUSID = '"+cusid+"'";
			int result = tservice.UpdateSQL2(update_sql);
			if(result == 0) {
				errStr = "更新失败";
				map.put("info", "0");
				map.put("textinfo", "导入失败请联系管理员");
				tservice.rollbackExe_close();
			}
			System.out.println();
			if("".equals(errStr)) {
				tservice.commitExe_close();
	            map.put("info", "1");
		        map.put("textinfo", "导入成功");
			}
		} catch(Exception e) {
			e.printStackTrace();
			map.put("info", "0");
			map.put("textinfo", "导入失败请联系管理员");
			tservice.rollbackExe_close();
		} finally {
			
		}
        return JSONObject.fromObject(map).toString(); 
    }
    
    
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
	
	/**
	 * 获取按用户分组的考勤记录信息
	 * @return
	 */
	public Map<String, List<Map>> getKqListByUser(BeanService_Transaction tservice, String cusid, String timestamp) {
		
		Map dataItem; // 数据库中查询到的每条记录 
		Map<String, List<Map>> resultMap = new HashMap<String, List<Map>>(); // 最终要的结果 
		String sql = "select u.USERNAME,u.DEPARTNAME,t.* from kq_userlist t " +
				" left join basic_user u on u.USERID = t.USERID " +
				" where t.CUSID = '"+cusid+"' and t.TIMESTAMP = '"+timestamp+"' order by u.USERNAME,t.DATIME";
		List<Map<String, String>> userList = tservice.getSelect(sql);
		for(int i=0; i<userList.size(); i++){ 
			
		    dataItem = userList.get(i); 
		    if(resultMap.containsKey(dataItem.get("userid"))){ 

		        resultMap.get(dataItem.get("userid")).add(dataItem); 
		    }else{ 
		    	
		        List<Map> list = new ArrayList<Map>(); 
		        list.add(dataItem); 
		        resultMap.put((String)dataItem.get("userid"),list); 
		    } 
		}
		return resultMap;
	}
	
	/**
	 * 计算得分
	 * @param min
	 * @param expr
	 * @return
	 * @throws ScriptException
	 */
	public String getScore(String min, String expr, String fdscore) throws ScriptException {
		String score = "";
		String s = expr.replaceAll("[a-zA-z]", min+"");
//        System.out.println(s);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Double result = (Double)engine.eval(s);
        long r = (long) (Math.ceil(result));
//        System.out.println(result);
//        System.out.println(r);
        if(!"".equals(fdscore) && fdscore != null) {
        	if(Double.parseDouble(fdscore) > r) {
        		score = r+"";
        	} else {
        		score = fdscore;
        	}
        } else {
        	score = r+"";
        }
		return score;
	}
}
