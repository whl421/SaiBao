package com.cqmi.controller.login.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.coobird.thumbnailator.Thumbnails;

import com.cqmi.controller.login.bean.User;
import com.cqmi.db.dao.Loggerlog;
import com.cqmi.db.service.BeanService_Transaction;

public class RoleMenuUtil {
	/**
	 * 获取
	 * @param userid
	 * @param cusid
	 * @param tservice
	 * @return
	 */
//	public static List<Map<String, String>> getPower(String userid,String cusid,String htmlcode,BeanService_Transaction tservice){
//		String G_b_sql="select m.* from (select m.* from menu m,menu n where n.htmlcode='"+htmlcode+"' and (m.parentid=n.id or m.id=n.id)) m left join menurole mr on m.id=mr.menuid left join roleuser ru on ru.roleid=mr.roleid" +
//				" where ru.userid="+userid+" and ru.cusid="+cusid;
//		/**
//		 * 权限获取
//		 */
//		List<Map<String, String>> bpower=tservice.getSelect(G_b_sql);
//		return bpower;
//	}
	/**
	 * 待阅读消息
	 * @param user
	 * @param tservice
	 * @return
	 */
	public Map<String, String> getWaitRead(User user,BeanService_Transaction tservice){
		Map<String, String> mp=new HashMap<String, String>();
		int gg=Integer.parseInt(getWriteReadGG(user, tservice));
		mp.put("ggrd", gg+"");
		mp.put("total", gg+"");
		return mp;
	}
	
	/**
	 * 待阅读公告
	 * @param user
	 * @param tservice
	 * @return
	 */
    public String getWriteReadGG(User user,BeanService_Transaction tservice){
		String cusid=user.getCusid();
		String userid=user.getUserid();
    	List<String> lt=new ArrayList<String>();
		String now_validdate=getTime_byFormat("yyyy-MM-dd HH:mm");
		String []totals={"total"};
		 lt.add(cusid);lt.add(userid);lt.add(cusid);lt.add("0");lt.add(now_validdate);lt.add(now_validdate);
		String where="where u.cusid=? and u.userid=? and t.cusid=? and u.noticestate=? and t.validdate>=? and t.fbtime<=? ";
		String sqlt="select count(*) total from basic_noticeuser u left join  basic_notice t  on u.noticeid=t.noticeid "+where;
		
		String total=tservice.getSelect(sqlt,lt,totals).get(0).get("total");
		return total;
    }
    /**
     * 公告刷新
     * 
     */
    public String GGRDRefresh(BeanService_Transaction tservice){
		String NowTime=getTime_byFormat("yyyy-MM-dd HH:mm");
		//获取需要扣分的公告
		String needr_title[]={"cusid","noticeuserid","cutscore","userid","username","departid","departname"};
    	String needr_sql="select n.cusid,n.noticeuserid,n.cutscore,n.userid,u.username,u.departid,u.departname from basic_noticeuser n left join basic_user u on n.userid=u.userid where n.validdate<='"+NowTime+"' and n.isoverdue<>'1' and n.noticestate='0' ";
    	List<Map<String, String>> needboticelist=tservice.getSelect(needr_sql,needr_title);
    	if(needboticelist.size()>0){
    		
    		List<String> u_sqls=new ArrayList<String>();
    		List<String> tj_sqls=new ArrayList<String>();
    		String _NowTime=getTime_byFormat("yyyy-MM-dd HH:mm:ss");
    		for (Map<String, String> map : needboticelist) {
				String noticeuserid=map.get("noticeuserid");
				String cutscore=map.get("cutscore");
				String cusid=map.get("cusid");
				String userid=map.get("userid");
				String username=map.get("username");
				String departid=map.get("departid");
				String departname=map.get("departname");
				
				if(cutscore.trim().equals("")||cutscore.trim().equals("0")){
					String u_sql="update basic_noticeuser set isoverdue='1' where noticeuserid='"+noticeuserid+"'";
					u_sqls.add(u_sql);
				}else{
					String u_sql="update basic_noticeuser set isoverdue='1',cutscore='-"+cutscore+"' where noticeuserid='"+noticeuserid+"'";
					String tj_sql="insert into tj_classintegral (cusid,jftype,jftypename,userid,username,"
							+ "departid,departname,zzfs,dfdate,tablename,tableidname,tableid,createid,"
							+ "creater,creattime,title,content) values ('"+cusid+"','10','公告阅读','"+userid+"','"+username+"','"+departid
							+"','"+departname+"','-"+cutscore+"','"+_NowTime.substring(0, 10).trim()+"','basic_noticeuser','noticeuserid','"+
							noticeuserid+"','"+userid+"','"+username+"','"+_NowTime+"','公告阅读减分','公告阅读减分')";
					u_sqls.add(u_sql);
					tj_sqls.add(tj_sql);
				}
				
			}
    		int r=0;
    		tservice.OpenTransaction();
    		for (String u_sql : u_sqls) {
    			r=tservice.UpdateSQL2(u_sql);
    			if(r==0)break;
    		}
    		if(r==0){
    			 Loggerlog.log("sqlproxy").warn("==》》》》》》》》》》》》==过期公告同步失败");
    			 tservice.rollbackExe();
    			 return "";
    		}
    		for (String tj_sql : tj_sqls) {
				r=tservice.InsertSQL2(tj_sql);
				if(r==0)break;
			}
    		if(r==0){
   			 Loggerlog.log("sqlproxy").warn("==》》》》》》》》》》》》==过期公告同步失败");
   			tservice.rollbackExe();
   			 return "";
    		}
    		
    		 tservice.commitExe();
    	} 
    		return "";
    }
    
	/**
	 * 根据时间格式 获取当前时间
	 * "yyyy-MM-dd HH:mm:ss" 
	 * @param fromat
	 * @return
	 */
	public String getTime_byFormat(String fromat){
		return new SimpleDateFormat(fromat).format(new Date());
	}
	/**
	 * 根据时间格式 获取当前时间
	 * "yyyy-MM-dd HH:mm:ss" 
	 * @param fromat
	 * @return
	 * @throws ParseException 
	 */
	public long getTime_byFormat(String fromat,String datetime) throws ParseException{
		return new SimpleDateFormat(fromat).parse(datetime).getTime();
	}
 
	/**
	 * 判断是否为整数 
	 * @param str 传入的字符串 
	 * @return 是整数返回true,否则返回false 
	 */
	public boolean isInteger(String str) {  
	        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	        return pattern.matcher(str).matches();  
	}
//	/**
//	 * 判断是否为整数 
//	 * @param str 传入的字符串 
//	 * @return 是整数返回true,否则返回false 
//	 */
//	public static boolean isNumeric(String str){
//	    Pattern pattern = Pattern.compile("[0-9]*");
//	    return pattern.matcher(str).matches();   
//	}
	/**
     * 
     * @doc 日期转换星期几
     * @param datetime
     *            日期 例:2017-10-17
     * @return String 例:星期二
     * @author lzy
     * @history 2017年10月17日 上午9:55:30 Create by 【lzy】
     */
    public String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    /**
     * 获取对应日期的下一个月份
     * @return
     * @throws ParseException 
     */
    public String NextMouth_yyyymm(String yyyy_MM_dd){
    	String b_time=yyyy_MM_dd.substring(0,7);
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	String resM="";
		try {
			for(int i=26;i<35;i++){
				String timeb=b_time+(i<10?"-0"+i:"-"+i);
				/**
				 * 判断是否跨月
				 */
				String timee=format.format(format.parse(timeb)).substring(0, 7);
				if(!b_time.equals(timee)){
					resM=timee;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 	return resM;
    }
    /**
     * 获取对应日期
     * @return
     * @throws ParseException 
     */
    public String StrDateToDate(String formats,String datetime_val) throws ParseException{
    	SimpleDateFormat format=new SimpleDateFormat(formats);
    	return format.format(format.parse(datetime_val));
    }
    public boolean DateIsWorkDate(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return (w>=1&&w<=5)?true:false;
    }
//    public static String dateToWeek_df(String yyyyMMdd){
//    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			Date date=format.parse(yyyyMMdd);
//			SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
//			return dateFm.format(date);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//			return "";
//    }
	/**
	 * 计算当前月有多少天
	 * 
	 * @return
	 */
	public int getDays(int year, int month) {
		int days = 0;
		if (month != 2) {
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				days = 30;
			}
		} else {
			// 闰年
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				days = 29;
			else
				days = 28;
		}
		return days;
	}
	
    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @param imageId     影像编号
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
        if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / 1024);
        try {
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
//            logger.info("【图片压缩】imageId={} | 图片原大小={}kb | 压缩后大小={}kb",
//                    imageId, srcSize / 1024, imageBytes.length / 1024);
        } catch (Exception e) {
//            logger.error("【图片压缩】msg=图片压缩失败!", e);
        	return null;
        }
        return imageBytes;
    }
 
    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }

}
