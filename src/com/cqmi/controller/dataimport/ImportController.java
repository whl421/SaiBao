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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class ImportController {
	    
	Util util = new Util();
	
    /**
	 * 文件上传
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping(value="userimport/upuser",produces="application/json; charset=utf-8")
	public ModelAndView upuser(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("dataimport/upuser");
		return modelAndView;
	}
	
	/*
	 * 文件上传
     * 采用file.Transto 来保存上传的文件
     */
    @RequestMapping(value="userimport/fileUpload",produces="application/json; charset=utf-8")
    @ResponseBody
    public String  fileUpload(@RequestParam(value ="file",required = false)CommonsMultipartFile file,String manageIntegral) 
    		throws IOException {
    	
    	BeanService_Transaction tservice = new BeanService_Transaction();
    	tservice.OpenTransaction();
    	User user=(User)ImportController.getSession().getAttribute("user");
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
        String path = ImportController.getSession().getServletContext()
                .getRealPath("/") + "resources/cusid"+user.getCusid()+"/"+"userimport"; //+"/" +uploadfilename; 
        
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
			Map<String, String> phoneMap = new HashMap<String, String>();
			int i = 2;
			//查询启动分
			String sql1 = "select * from basic_startinfo t where t.CUSID = '"+cusid+"'";
			List<Map<String, String>> sList = tservice.getSelect(sql1);
			String startjf = "0";
			if(sList != null && sList.size() > 0) {
				startjf = sList.get(0).get("startjf");
				if(startjf == null) startjf = "0";
			}
			for (Map<String,String> mp : lemap) {
//				System.out.println("==lemap=="+lemap);
	        	List<String> list = new ArrayList<String>();
	            for (Entry<String,String> entry : mp.entrySet()) {
	                System.out.println(entry.getKey()+":"+entry.getValue()+",");
	                list.add(entry.getValue());
	            }
	            if(phoneMap.containsKey(list.get(3))) {
	            	errStr = "手机号码【"+list.get(3)+"】重复";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
	            } else {
	            	phoneMap.put(list.get(3), list.get(3));
	            }
	            String username = list.get(0);		//姓名
	            String gxname = list.get(1);		//花名
                String departname = list.get(2);	//部门
                String phone = list.get(3);			//手机号码
                String sexname = list.get(4);		//性别
                String gh =list.get(5);				//工号
                String mail = list.get(6);			//邮箱
                String rzdate = list.get(7);		//入职日期
                String pzmc = list.get(8);			//奖扣权限
                String pname = list.get(9);			//职位
                String tname = list.get(10);		//职称
                String ename = list.get(11);		//学历
                String sname = list.get(12);		//技能证书
                String birthday = list.get(13);		//生日
                String byschool = list.get(14);		//毕业学校
                String major = list.get(15);		//专业
                String officephone = list.get(16);	//办公室电话
                String card = list.get(17);			//身份证
                String memo = list.get(18);			//备注
                
                if("".equals(username) || username == null) {
                	errStr = "第"+i+"行中的姓名不能为空";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
                }
                if("".equals(departname) || departname == null) {
                	errStr = "第"+i+"行中的部门不能为空";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
                }
                if("".equals(gh) || gh == null) {
//                	errStr = "第"+i+"行中的工号不能为空";
//                	map.put("info", "0");
//                    map.put("textinfo", errStr);
//                    break;
                }
//                if("".equals(rzdate) || rzdate == null) {
//                	errStr = "第"+i+"行中的入职日期不能为空";
//                	map.put("info", "0");
//                    map.put("textinfo", errStr);
//                    break;
//                }
                if("".equals(sexname) || sexname == null) {
                	errStr = "第"+i+"行中的性别不能为空";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
                }
                
                String departid = "";
                String departcode = "";
                //判断部门是否存在
                String sel_dsql = "select * from basic_depart t where t.CUSID = ? and t.DEPARTNAME = ?";
                List<String> dlt = new ArrayList<String>();
                dlt.add(cusid);dlt.add(departname);
                List<Map<String, String>> deptMap = tservice.getSelect(sel_dsql, dlt);
                if(deptMap != null && deptMap.size()>0) {
                	departid = deptMap.get(0).get("departid");
                	departcode = deptMap.get(0).get("departcode");
                } else {
                	errStr = "第"+i+"行中的部门在系统中不存在，请在部门管理中新增";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
                }
                //日期格式判断
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if(!"".equals(rzdate) && rzdate != null) {
	                try {
	                	Date date = format.parse(rzdate);
	            	} catch (ParseException e) {
	            		errStr = "第"+i+"行中的入职日期格式不对，正确格式为2009-01-01";
	                	map.put("info", "0");
	                    map.put("textinfo", errStr);
	                    break;
	            	}
                }
                //查询奖扣权限
                String configid = "";
                if(!"".equals(pzmc) && pzmc != null) {
                    String jksql = "select * from jk_jfconfig t where t.pzmc = ? and t.cusid = ?";
                    List<String> jklt = new ArrayList<String>();
                    jklt.add(pzmc);jklt.add(cusid);
                    List<Map<String, String>> jkMap = tservice.getSelect(jksql, jklt);
                    if(jkMap != null && jkMap.size()>0) {
                    	configid = jkMap.get(0).get("configid");
                    } else {
                    	errStr = "第"+i+"行中的奖扣权限在系统中不存在，请在奖扣积分>>奖扣权限配置中新增";
                    	map.put("info", "0");
                        map.put("textinfo", errStr);
                        break;
                    }
                }
                //查询职位
                String pid = "";
                String shjs = "2";
                if(!"".equals(pname) && pname != null) {
                    String zwsql = "select * from basic_position t where t.pname = ? and t.cusid = ? ";
                    List<String> zwlt = new ArrayList<String>();
                    zwlt.add(pname);zwlt.add(cusid);
                    List<Map<String, String>> zwMap = tservice.getSelect(zwsql, zwlt);
                    if(zwMap != null && zwMap.size()>0) {
                    	pid = zwMap.get(0).get("pid");
                    	//获取职位信息
        				String sql11 = "select count(*) num from basic_position t where t.CUSID = '"+cusid+"' and cast(t.ZWDJ as signed) < " +
        						" (select cast(t.ZWDJ as signed) from basic_position t where t.PID = '"+pid+"') ";
        				String title[] = {"num"};
        				List<Map<String, String>> positionmap = tservice.getSelect(sql11, title);
        				if(positionmap != null && positionmap.size() > 0) {
        					String num = positionmap.get(0).get("num");
        					if("0".equals(num)) {
    							shjs = "0";
    						} else if("1".equals(num)) {
    							shjs = "1";
    						} else if(num.compareTo("2") >= 0){
    							shjs = "2";
    						}
        				}
        				
                    } else {
                    	errStr = "第"+i+"行中的职位在系统中不存在";
                    	map.put("info", "0");
                        map.put("textinfo", errStr);
                        break;
                    }
                }
                //查询职称
                String tid = "";
                if(!"".equals(tname) && tname != null) {
                    String zcsql = "select * from basic_title t where t.tname = ? and t.cusid = ? ";
                    List<String> zclt = new ArrayList<String>();
                    zclt.add(tname);zclt.add(cusid);
                    List<Map<String, String>> zcMap = tservice.getSelect(zcsql, zclt);
                    if(zcMap != null && zcMap.size()>0) {
                    	tid = zcMap.get(0).get("tid");
                    } else {
                    	errStr = "第"+i+"行中的职称在系统中不存在";
                    	map.put("info", "0");
                        map.put("textinfo", errStr);
                        break;
                    }
                }
                //查询学历
                String eid = "";
                if(!"".equals(ename) && ename != null) {
                    String edusql = "select * from basic_education t where t.ename = ? and t.cusid = ? ";
                    List<String> edult = new ArrayList<String>();
                    edult.add(ename);edult.add(cusid);
                    List<Map<String, String>> eduMap = tservice.getSelect(edusql, edult);
                    if(eduMap != null && eduMap.size()>0) {
                    	eid = eduMap.get(0).get("eid");
                    } else {
                    	errStr = "第"+i+"行中的学历在系统中不存在";
                    	map.put("info", "0");
                        map.put("textinfo", errStr);
                        break;
                    }
                }
                //查询技术证书
                String sid = "";
                String skillnames = "";
                if(!"".equals(sname) && sname != null) {
                	sname = sname.replace("，", ",");
                	String snames[] = sname.split(",");
                	for(int k=0; k<snames.length; k++) {
                		String skillsql = "select * from basic_skill t where t.sname = ? and t.cusid = ? ";
                        List<String> slt = new ArrayList<String>();
                        slt.add(snames[k]);slt.add(cusid);
                        List<Map<String, String>> skillMap = tservice.getSelect(skillsql, slt);
                        if(skillMap != null && skillMap.size()>0) {
                        	String skillid = skillMap.get(0).get("sid");
                        	String skillname = skillMap.get(0).get("sname");
                        	if("".equals(sid) || sid == null) {
                        		sid = skillid;
                        		skillnames = skillname;
                        	} else {
                        		sid += "," + skillid;
                        		skillnames += "," + skillname;
                        	}
                        } else {
                        	errStr = "第"+i+"行中的技能证书["+snames[k]+"]在系统中不存在";
                        	map.put("info", "0");
                            map.put("textinfo", errStr);
                            return JSONObject.fromObject(map).toString();
                        }
                	}
                }
                //性别
                String sex = "";
                if("男".equals(sexname)) {
                	sex = "1";
                } else if("女".equals(sexname)) {
                	sex = "0";
                } else {
                	errStr = "第"+i+"行中的性别不正确";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    break;
                }
                //生日
                if(!"".equals(birthday) && birthday != null) {
                	try {
                    	Date date = format.parse(birthday);
                	} catch (ParseException e) {
                		errStr = "第"+i+"行中的生日格式错误，正确格式为2009-01-01";
                    	map.put("info", "0");
                        map.put("textinfo", errStr);
                       	break;
                	}
                }
                //手机号码
                String regex = "^(((13[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(15[0-3]{1})|(15[4-9]{1})|(18[0-9]{1})|(199))+\\d{8})$";
                Pattern p = Pattern.compile(regex);
                if (!p.matches(regex, phone)) {
                	errStr = "第"+i+"行中的手机号码错误";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                   	break;
                } 
                //判断手机号码是否存在
                String psql = "select * from basic_user t where t.phone = ? and cusid =?";
                List<String> plt = new ArrayList<String>();
                plt.add(phone);plt.add(cusid);
                List<Map<String, String>> pMap = tservice.getSelect(psql, plt);
                if(pMap != null && pMap.size()>0) {
                	errStr = "第"+i+"行中的手机号码已存在";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                   	break;
                }
                //邮箱
                if(!"".equals(mail) && mail != null) {
	                if(!mail.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
	                	errStr = "第"+i+"行中的邮箱错误";
	                	map.put("info", "0");
	                    map.put("textinfo", errStr);
	                    break;
	                }
                }
                //办公室电话
                
                //身份证号码
                if(!"".equals(card)) {
                	if(!isIdNum(card)) {
                    	errStr = "第"+i+"行中的身份证号码错误";
                    	map.put("info", "0");
                        map.put("textinfo", errStr);
                        break;
                    }
                }
                //查询是新增还是更新
                String usql = "select * from basic_user t where t.CUSID = ? and t.phone = ?";
                List<String> ult = new ArrayList<String>();
                ult.add(cusid);ult.add(phone);
                List<Map<String, String>> userMap = tservice.getSelect(usql, ult);
                if(userMap != null && userMap.size()>0) {
                	String userid = userMap.get(0).get("userid");
                	//更新用户
                	List<String> lt=new ArrayList<String>();
            		String sql= " update basic_user set usercode=?,username=?,workcode=?,departid=?,departcode=?,departname=?, "
            				  + " pid=?,pname=?,tid=?,tname=?,sid=?,sname=?,eid=?,ename=?,configid=?,pzmc=?,joindate=?, "
            				  + " card=?,birthday=?,sex=?,phone=?,emall=?,officephone=?,byschool=?, "
            				  + " major=?,photo=?,memo=?,gxname=?,shjs='"+shjs+"' where userid=? " ;
            		//userstatus=?,
            		//注意参数顺序
            		lt.add(gh);lt.add(username);lt.add("");lt.add(departid);lt.add(departcode);
            		lt.add(departname);lt.add(pid);lt.add(pname);lt.add(tid);lt.add(tname);
            		lt.add(sid);lt.add(skillnames);lt.add(eid);lt.add(ename);lt.add(configid);
            		lt.add(pzmc);lt.add(rzdate);lt.add(card);lt.add(birthday);
            		lt.add(sex);lt.add(phone);lt.add(mail);lt.add(officephone);lt.add(byschool);
            		lt.add(major);lt.add("");lt.add(memo);lt.add(gxname);lt.add(userid);
            		int r = tservice.UpdateSQL2(sql, lt);
            		if(r==0){
            			errStr = "导入失败请联系管理员";
            			map.put("info", "0");
            			map.put("textinfo", "导入失败请联系管理员");
            			tservice.rollbackExe_close();
            		}
            		
                } else {
                	List<String> lt=new ArrayList<String>();
                	//新增用户
                	String sql = " insert into basic_user (cusid,usercode,username,workcode,departid,departcode,departname, "
         				   + " pwd,pid,pname,tid,tname,sid,sname,eid,ename,configid,pzmc,joindate,state,userstatus,card,birthday,sex, "
         				   + " phone,emall,officephone,byschool,major,photo,memo,createid,creater,creattime,ispm,gxname,shjs) "
         				   + " value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	         		String pwd = "-41149678231811287725040083248417372098";
	         		String state = "1";
	         		String userstatus = "1";
	         		String photo = "";
	         		String createid = user.getUserid();
	         		String creater = user.getUsername();
	         		String creattime = util.getNowTime();
	         		
	         		//注意参数顺序
	         		lt.add(cusid);lt.add(gh);lt.add(username);lt.add("");lt.add(departid);
	         		lt.add(departcode);lt.add(departname);lt.add(pwd);lt.add(pid);lt.add(pname);
	         		lt.add(tid);lt.add(tname);lt.add(sid);lt.add(skillnames);lt.add(eid);
	         		lt.add(ename);lt.add(configid);lt.add(pzmc);
	         		lt.add(rzdate);lt.add(state);lt.add(userstatus);lt.add(card);
	         		lt.add(birthday);lt.add(sex);lt.add(phone);lt.add(mail);lt.add(officephone);
	         		lt.add(byschool);lt.add(major);lt.add(photo);lt.add(memo);lt.add(createid);
	         		lt.add(creater);lt.add(creattime);lt.add("1");lt.add(gxname);lt.add(shjs);
	         		int r = tservice.InsertSQL2(sql, lt);
	        		if(r==0){
	        			errStr = "导入失败请联系管理员";
	        			map.put("info", "0");
	        			map.put("textinfo", "导入失败请联系管理员");
	        			tservice.rollbackExe_close();
	        		}
	        		//获取刚插入的ID
					String new_userid = "";
					String title[] = {"id"};
					List<Map<String, String>> list1 = tservice.getSelect("select LAST_INSERT_ID() as id",title);
					if(list1 != null && list1.size()>0) {
						new_userid = list1.get(0).get("id");
					} else {
						map.put("info", "0");
						map.put("text", "汇报失败请联系管理员");
						tservice.rollbackExe_close();
					}
	        		//往积分记录表写数据
	    			String jftype = "11";
	    			String jftypename = "启动分";
	    			String zzfs = startjf;
	    			String dfdate = util.getNowDate();
	    			String csrid = "";
	    			String csrname = "";
	    			String csdate = "";
	    			String zsrid = "";
	    			String zsrname = "";
	    			String zsdate = util.getNowTime();
	    			String tablename = "basic_user";
	    			String tableidname = "userid";
	    			String tableid = new_userid;
	    			String rwmc = "新用户启动分";
	    			String rwnr = "新用户启动分";
	    			
	    			String addjfclasssql = "insert into tj_classintegral (cusid,jftype,jftypename,userid,username,departid, "
	    					 + "departname,zzfs,dfdate,csrid,csrname,csdate,zsrid,zsrname,zsdate,tablename,tableidname, "
	    					 + "tableid,createid,creater,creattime,title,content) value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?)";
	    			List<String> lt1 = new ArrayList<String>();
	    			lt1.add(cusid);lt1.add(jftype);lt1.add(jftypename);lt1.add(new_userid);lt1.add(username);lt1.add(departid);
	    			lt1.add(departname);lt1.add(zzfs);lt1.add(dfdate);lt1.add(csrid);lt1.add(csrname);lt1.add(csdate);
	    			lt1.add(zsrid);lt1.add(zsrname);lt1.add(zsdate);lt1.add(tablename);lt1.add(tableidname);lt1.add(tableid);
	    			lt1.add(user.getUserid());lt1.add(user.getUsername());lt1.add(util.getNowTime());lt1.add(rwmc);lt1.add(rwnr);
	    			r = tservice.InsertSQL2(addjfclasssql, lt1);
	    			if(r == 0) {
	    				map.put("info", "0");
	    				map.put("text", "新增积分记录失败，请联系管理员");
	    				tservice.rollbackExe_close();
	    			}
                }
                i++;
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
			map.put("text", "导入失败请联系管理员");
			tservice.rollbackExe_close();
		} finally {
			
		}
        return JSONObject.fromObject(map).toString(); 
    }
    
    /**
     * 数据校验
     * @param lemap
     * @return
     */
    public Map validate(List<Map<String,String>> lemap) {
    	
    	BeanService_Transaction tservice = new BeanService_Transaction();
    	User user = (User)ImportController.getSession().getAttribute("user");
    	String cusid = user.getCusid();
    	Map map = new HashMap();
    	String errStr = "";
    	for (Map<String,String> mp : lemap) {
        	int i = 1;
            for (Entry<String,String> entry : mp.entrySet()) {
                System.out.println(entry.getKey()+":"+entry.getValue()+",");
                String username = entry.getValue();
                String departname = entry.getValue();
                String gh = entry.getValue();
                String zh = entry.getValue();
                String rzdate = entry.getValue();
                String jkqx = entry.getValue();
                String zw = entry.getValue();
                String zc = entry.getValue();
                String edu = entry.getValue();
                String skill = entry.getValue();
                String sexname = entry.getValue();
                String birthday = entry.getValue();
                String byschool = entry.getValue();
                String major = entry.getValue();
                String photo = entry.getValue();
                String mail = entry.getValue();
                String officephone = entry.getValue();
                String card = entry.getValue();
                String memo = entry.getValue();
                
                String departid = "";
                //判断部门是否存在
                String sel_dsql = "select * from basic_depart t where t.CUSID = ? and t.DEPARTNAME = ?";
                List<String> dlt = new ArrayList<String>();
                dlt.add(cusid);dlt.add(departname);
                List<Map<String, String>> deptMap = tservice.getSelect(sel_dsql, dlt);
                if(deptMap != null && deptMap.size()>0) {
                	departid = deptMap.get(0).get("departid");
                } else {
                	errStr = "第"+i+"行中的部门在系统中不存在，请在部门管理中新增";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
                }
                //日期格式判断
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                	Date date = format.parse(rzdate);
            	} catch (ParseException e) {
            		errStr = "第"+i+"行中的入职日期格式不对，正确格式为2009-01-01";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
            	}
                //查询奖扣权限
                String configid = "";
                String jksql = "select * from jk_jfconfig t where t.pzmc = ? and t.cusid = ?";
                List<String> jklt = new ArrayList<String>();
                jklt.add(jkqx);jklt.add(cusid);
                List<Map<String, String>> jkMap = tservice.getSelect(jksql, jklt);
                if(jkMap != null && jkMap.size()>0) {
                	configid = jkMap.get(0).get("configid");
                } else {
                	errStr = "第"+i+"行中的奖扣权限在系统中不存在，请在奖扣积分>>奖扣权限配置中新增";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
                }
                //查询职位
                String pid = "";
                String zwsql = "select * from basic_position t where t.pname = ? and t.cusid = ? ";
                List<String> zwlt = new ArrayList<String>();
                zwlt.add(zw);zwlt.add(cusid);
                List<Map<String, String>> zwMap = tservice.getSelect(zwsql, zwlt);
                if(zwMap != null && zwMap.size()>0) {
                	pid = zwMap.get(0).get("pid");
                } else {
                	errStr = "第"+i+"行中的职位在系统中不存在";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
                }
                //查询职称
                String tid = "";
                String zcsql = "select * from basic_title t where t.tname = ? and t.cusid = ? ";
                List<String> zclt = new ArrayList<String>();
                zclt.add(zc);zclt.add(cusid);
                List<Map<String, String>> zcMap = tservice.getSelect(zcsql, zclt);
                if(zwMap != null && zwMap.size()>0) {
                	tid = zwMap.get(0).get("tid");
                } else {
                	errStr = "第"+i+"行中的职称在系统中不存在";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
                }
                //查询学历
                String eid = "";
                String edusql = "select * from basic_education t where t.ename = ? and t.cusid = ? ";
                List<String> edult = new ArrayList<String>();
                edult.add(edu);edult.add(cusid);
                List<Map<String, String>> eduMap = tservice.getSelect(edusql, edult);
                if(eduMap != null && eduMap.size()>0) {
                	eid = eduMap.get(0).get("eid");
                } else {
                	errStr = "第"+i+"行中的学历在系统中不存在";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
                }
                //查询技术证书
                String sid = "";
                String skillsql = "select * from basic_skill t where t.sname = ? and t.cusid = ? ";
                List<String> slt = new ArrayList<String>();
                slt.add(edu);slt.add(cusid);
                List<Map<String, String>> skillMap = tservice.getSelect(skillsql, slt);
                if(skillMap != null && skillMap.size()>0) {
                	sid = skillMap.get(0).get("sid");
                } else {
                	errStr = "第"+i+"行中的技术证书在系统中不存在";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
                }
                //性别
                String sex = "";
                if("男".equals(sexname)) {
                	sex = "1";
                } else if("女".equals(sexname)) {
                	sex = "0";
                } else {
                	errStr = "第"+i+"行中的性别不正确";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
                }
                //生日
                try {
                	Date date = format.parse(birthday);
            	} catch (ParseException e) {
            		errStr = "第"+i+"行中的生日格式错误，正确格式为2009-01-01";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
            	}
                //手机号码
                String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
                Pattern p = Pattern.compile(regex);
                if (!p.matches(regex, photo)) {
                	errStr = "第"+i+"行中的手机号码错误";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
                } 
                //邮箱
                if(!mail.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
                	errStr = "第"+i+"行中的邮箱错误";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
                }
                //办公室电话
                
                //身份证号码
                if(!isIdNum(card)) {
                	errStr = "第"+i+"行中的身份证号码错误";
                	map.put("info", "0");
                    map.put("textinfo", errStr);
                    return map;
                }
                i++;
            }
            System.out.println();
        }
    	return map;
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
     * 判断身份证格式
     * 
     * @param idNum
     * @return
     */
    public boolean isIdNum(String idNum) {

        // 中国公民身份证格式：长度为15或18位，最后一位可以为字母
        Pattern idNumPattern = Pattern.compile("(\\d{17}[0-9a-zA-Z])");

        // 格式验证
        if (!idNumPattern.matcher(idNum).matches())
            return false;

        // 合法性验证
        int year = 0;
        int month = 0;
        int day = 0;

        if (idNum.length() == 18) {

            // 二代身份证

//            System.out.println("二代身份证：" + idNum);

            // 提取身份证上的前6位以及出生年月日
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");

            Matcher birthDateMather = birthDatePattern.matcher(idNum);

            if (birthDateMather.find()) {

                year = Integer.valueOf(birthDateMather.group(1));
                month = Integer.valueOf(birthDateMather.group(2));
                day = Integer.valueOf(birthDateMather.group(3));
            }

        }

        // 年份判断，100年前至今

        Calendar cal = Calendar.getInstance();

        // 当前年份
        int currentYear = cal.get(Calendar.YEAR);

        if (year <= currentYear - 100 || year > currentYear)
            return false;

        // 月份判断
        if (month < 1 || month > 12)
            return false;

        // 日期判断

        // 计算月份天数

        int dayCount = 31;

        switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            dayCount = 31;
            break;
        case 2:
            // 2月份判断是否为闰年
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                dayCount = 29;
                break;
            } else {
                dayCount = 28;
                break;
            }
        case 4:
        case 6:
        case 9:
        case 11:
            dayCount = 30;
            break;
        }

//        System.out.println(String.format("生日：%d年%d月%d日", year, month, day));

//        System.out.println(month + "月份有：" + dayCount + "天");

        if (day < 1 || day > dayCount)
            return false;

        return true;
    }
}
