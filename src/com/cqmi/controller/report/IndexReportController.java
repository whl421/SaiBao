package com.cqmi.controller.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class IndexReportController extends BasicAction{
	
	/**
	 * 首页统计报表
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("report/indexreport")
	public ModelAndView indexreport() throws Exception {
		//定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		Util util = new Util();
		ModelAndView modelAndView = new ModelAndView();
		try {
			//获取登录信息
			User user = (User) LoginController.getSession().getAttribute("user");
			String userid = user.getUserid();
			String departid = user.getDepartid();
			String cusid = user.getCusid();
			//获取当前日期
			SimpleDateFormat tempDate0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowtime = tempDate0.format(new java.util.Date());
			//获取当前日期
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
			String today = tempDate.format(new java.util.Date());
			
			SimpleDateFormat tempDate2 = new SimpleDateFormat("yyyy-MM");
			String nowmonth = tempDate2.format(new java.util.Date());
			
			SimpleDateFormat tempDate3 = new SimpleDateFormat("yyyy");
			String nowyear = tempDate3.format(new java.util.Date());
			//=====================================查询单项积分=====================================
			//个人当月单项加分
			String jfsql = " select cast(jcf as char)jcf,cast(gdjf as char)gdjf,cast(gdkf as char)gdkf,cast(jkjf as char)jkjf,cast(jkkf as char)jkkf, "
						 + " cast(lsjf as char)lsjf,cast(lskf as char)lskf,cast(xsjf as char)xsjf,cast(kqjf as char)kqjf, "
						 + " cast(kqkf as char)kqkf,cast(qtjf as char)qtjf,cast(qtkf as char)qtkf "
						 + " from (  "
						 + " 	select ifnull(sum(zzfs),0) jcf from tj_classintegral " 
						 + " 	where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and jftype in (1,2) "
						 + " )jcf, "
						 + " (select ifnull(sum(zzfs),0) gdjf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs > 0 and jftype in (3,4,5) "
						 + " )gdjf, "
						 + " (select ifnull(sum(zzfs),0) gdkf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs < 0 and jftype in (3,4,5) "
						 + " )gdkf, "
						 + " (select ifnull(sum(zzfs),0) jkjf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs > 0 and jftype = '6' and state <> '2' "
						 + " )jkjf, "
						 + " (select ifnull(sum(zzfs),0) jkkf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs < 0 and jftype = '6' and state <> '2' "
						 + " )jkkf, "
						 + " (select ifnull(sum(zzfs),0) lsjf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs > 0 and jftype = '7' "
						 + " )lsjf, "
						 + " (select ifnull(sum(zzfs),0) lskf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs < 0 and jftype = '7' "
						 + " )lskf, "
						 + " (select ifnull(sum(zzfs),0) xsjf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs > 0 and jftype = '8' "
						 + " )xsjf, "
						 + " (select ifnull(sum(zzfs),0) kqjf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs > 0 and jftype = '9' "
						 + " )kqjf, "
						 + " (select ifnull(sum(zzfs),0) kqkf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs < 0 and jftype = '9' "
						 + " )kqkf, "
						 + " (select ifnull(sum(zzfs),0) qtjf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs > 0 and jftype in (10,11,12) "
						 + " )qtjf, "
						 + " (select ifnull(sum(zzfs),0) qtkf from tj_classintegral " 
						 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and zzfs < 0 and jftype in (10,11,12) "
						 + " )qtkf "; 
			List<Map<String, String>> monthjfmap = tservice.getSelect(jfsql);
			
			//=====================================奖扣积分=====================================
			//个人当月奖扣加分
			String jkjf = monthjfmap.get(0).get("jkjf");
			//个人当月奖扣扣分
			String jkkf = monthjfmap.get(0).get("jkkf");
			//个人当月奖扣总分
			int alljkfs = Integer.parseInt(jkjf) + Integer.parseInt(jkkf);
			
			//=====================================基础积分=====================================
			//基础分
			String jcf = monthjfmap.get(0).get("jcf");
			//个人固定总分
			int alljcfs = Integer.parseInt(jcf);
			
			//=====================================每日/周/月积分=====================================
			//个人当月固定任务加分
			String gdjf = monthjfmap.get(0).get("gdjf");
			//个人当月固定任务扣分
			String gdkf = monthjfmap.get(0).get("gdkf");
			//个人当月固定任务总分
			int allgdfs = Integer.parseInt(gdjf) + Integer.parseInt(gdkf);
			
			//=====================================临时任务积分=====================================
			//个人当月临时任务加分
			String lsjf = monthjfmap.get(0).get("lsjf");
			//个人当月临时任务扣分
			String lskf = monthjfmap.get(0).get("lskf");
			//个人当月临时任务总分
			int alllsfs = Integer.parseInt(lsjf) + Integer.parseInt(lskf);
			
			//=====================================悬赏任务积分=====================================
			//个人当月悬赏任务积分
			String xsjf = monthjfmap.get(0).get("xsjf");
			int allxsjf = Integer.parseInt(xsjf);
			
			//=====================================考勤积分=====================================
			//个人当月考勤加分
			String kqjf = monthjfmap.get(0).get("kqjf");
			//个人当月考勤扣分
			String kqkf = monthjfmap.get(0).get("kqkf");
			//个人当月考勤总分
			int allkqfs = Integer.parseInt(kqjf) + Integer.parseInt(kqkf);
		
			//=====================================其他积分=====================================
			//个人当月其他加分
			String qtjf = monthjfmap.get(0).get("qtjf");
			//个人当月其他扣分
			String qtkf = monthjfmap.get(0).get("qtkf");
			//个人当月其他总分
			int allqtfs = Integer.parseInt(qtjf) + Integer.parseInt(qtkf);

			//=====================================汇总积分=====================================
			//个人当月总分
			int summonthzf = alljcfs + alljkfs + allgdfs + alllsfs + allxsjf + allkqfs + allqtfs;
			
			//个人当月加分总分
			int summonthjf = Integer.parseInt(jcf) + Integer.parseInt(jkjf) + Integer.parseInt(gdjf) 
							+ Integer.parseInt(lsjf) + Integer.parseInt(xsjf) 
							 + Integer.parseInt(kqjf) + Integer.parseInt(qtjf);
			
			//个人当月扣分总分
			int summonthkf = Integer.parseInt(jkkf) + Integer.parseInt(gdkf) + Integer.parseInt(lskf) 
							+ Integer.parseInt(qtkf) + Integer.parseInt(kqkf);
			
			//=====================================单项积分占比=====================================
			BigDecimal alljcfszb = new BigDecimal(0);
			BigDecimal alljkfszb = new BigDecimal(0);
			BigDecimal allgdfszb = new BigDecimal(0);
			BigDecimal alllsfszb = new BigDecimal(0);
			BigDecimal allxsjfzb = new BigDecimal(0);
			BigDecimal allkqfszb = new BigDecimal(0);
			BigDecimal allqtfszb = new BigDecimal(0);
			if(summonthzf != 0){
				//基础分
				float alljcfs2 = Float.parseFloat(alljcfs+"")/Float.parseFloat(summonthzf+"")*100;
				alljcfszb = new BigDecimal(alljcfs2).setScale(2, BigDecimal.ROUND_HALF_UP);
				//奖扣积分
				float alljkfs2 = Float.parseFloat(alljkfs+"")/Float.parseFloat(summonthzf+"")*100;
				alljkfszb = new BigDecimal(alljkfs2).setScale(2, BigDecimal.ROUND_HALF_UP);
				//固定任务
				float allgdfs2 = Float.parseFloat(allgdfs+"")/Float.parseFloat(summonthzf+"")*100;
				allgdfszb = new BigDecimal(allgdfs2).setScale(2, BigDecimal.ROUND_HALF_UP);
				//临时任务
				float alllsfs2 = Float.parseFloat(alllsfs+"")/Float.parseFloat(summonthzf+"")*100;
				alllsfszb = new BigDecimal(alllsfs2).setScale(2, BigDecimal.ROUND_HALF_UP);
				//悬赏任务
				float allxsjf2 = Float.parseFloat(allxsjf+"")/Float.parseFloat(summonthzf+"")*100;
				allxsjfzb = new BigDecimal(allxsjf2).setScale(2, BigDecimal.ROUND_HALF_UP);
				//考勤
				float allkqfs2 = Float.parseFloat(allkqfs+"")/Float.parseFloat(summonthzf+"")*100;
				allkqfszb = new BigDecimal(allkqfs2).setScale(2, BigDecimal.ROUND_HALF_UP);
				//其他
				float allqtfs2 = Float.parseFloat(allqtfs+"")/Float.parseFloat(summonthzf+"")*100;
				allqtfszb = new BigDecimal(allqtfs2).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			
			
			//=====================================查询个人月度总分排名=====================================
			String monthjfpmsql = " select * from ( "
							   + " 		select CONCAT('第',@rank:=@rank + 1,'名') AS rank_no,a.* from ( "
							   + " 		select sum(t.zzfs) grzf,t.userid "
							   + " 		from tj_classintegral t "
							   + "		left join basic_user u on u.userid = t.userid "	
							   + " 		where t.cusid = '"+cusid+"' and substring(dfdate,1,7) = '"+nowmonth+"' and t.state <> '2' and u.state = '1' "
							   + " 		group by t.userid "
							   + " 		)a,(SELECT @rank:= 0) b " 
							   + " 		order by a.grzf desc "
							   + " )c "
							   + " where c.userid = '"+userid+"' ";
			List<Map<String, String>> monthjfpmmap = tservice.getSelect(monthjfpmsql);
			
			String monthzfpm = "--";
			if(monthjfpmmap != null && monthjfpmmap.size() > 0){
				monthzfpm = monthjfpmmap.get(0).get("rank_no");
			}
			//=====================================查询个人年度总分=====================================
			String yearjfsql = " select ifnull(cast(sum(zzfs) as char),0)yearzf from tj_classintegral "
							+ " where cusid = '"+cusid+"' and userid = '"+userid+"' and substring(dfdate,1,4) = '"+nowyear+"' and state <> '2' ";
			List<Map<String, String>> yearjfmap = tservice.getSelect(yearjfsql);
			
			String yearzf = yearjfmap.get(0).get("yearzf");
			
			//=====================================查询个人年度总分排名=====================================
			String yearjfpmsql = " select * from ( "
							   + " 		select CONCAT('第',@rank:=@rank + 1,'名') AS rank_no,a.* from ( "
							   + " 		select sum(t.zzfs) grzf,t.userid "
							   + " 		from tj_classintegral t "
							   + "		left join basic_user u on u.userid = t.userid "	
							   + " 		where t.cusid = '"+cusid+"' and substring(dfdate,1,4) = '"+nowyear+"' and t.state <> '2' and u.state = '1' "
							   + " 		group by t.userid "
							   + " 		)a,(SELECT @rank:= 0) b " 
							   + " 		order by a.grzf desc "
							   + " )c "
							   + " where c.userid = '"+userid+"' ";
			List<Map<String, String>> yearjfpmmap = tservice.getSelect(yearjfpmsql);
			
			String yearzfpm = "--";
			if(yearjfpmmap != null && yearjfpmmap.size() > 0){
				yearzfpm = yearjfpmmap.get(0).get("rank_no");
			}
			//=====================================查询个人累计总分 =====================================
			String alljfsql = " select ifnull(cast(sum(zzfs) as char),0)allzf from tj_classintegral "
							 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and state <> '2' ";
			List<Map<String, String>> alljfmap = tservice.getSelect(alljfsql);
	
			String allzf = alljfmap.get(0).get("allzf");
			
			//=====================================查询个人累计总分排名=====================================
			String alljfpmsql = " select * from ( "
							   + " 		select CONCAT('第',@rank:=@rank + 1,'名') AS rank_no,a.* from ( "
							   + " 		select sum(t.zzfs) grzf,t.userid "
							   + " 		from tj_classintegral t "
							   + "		left join basic_user u on u.userid = t.userid "	
							   + " 		where t.cusid = '"+cusid+"' and t.state <> '2' and u.state = '1' "
							   + " 		group by t.userid "
							   + " 		)a,(SELECT @rank:= 0) b " 
							   + " 		order by a.grzf desc "
							   + " )c "
							   + " where c.userid = '"+userid+"' ";
			List<Map<String, String>> alljfpmmap = tservice.getSelect(alljfpmsql);
			
			String allzfpm = "--";
			if(alljfpmmap != null && alljfpmmap.size() > 0){
				allzfpm = alljfpmmap.get(0).get("rank_no");
			}
			//=====================================查询个人当日得分 =====================================
			String dayjfsql = " select ifnull(cast(sum(zzfs) as char),0)dayzf from tj_classintegral "
							 + " where cusid = '"+cusid+"' and userid = '"+userid+"' and dfdate = '"+today+"' and state <> '2' ";
			List<Map<String, String>> dayjfmap = tservice.getSelect(dayjfsql);
	
			String dayzf = dayjfmap.get(0).get("dayzf");
			
			//=====================================查询个人最近7天奖扣分=====================================
			String bdate = "";
//			Calendar c=Calendar.getInstance();
//			if(c.get(c.MONTH)+1 < 10){
//				bdate=c.get(c.YEAR)+"-0"+(c.get(c.MONTH)+1)+"-"+(c.get(c.DAY_OF_MONTH)-6);
//			}else{
//				bdate=c.get(c.YEAR)+"-"+(c.get(c.MONTH)+1)+"-"+(c.get(c.DAY_OF_MONTH)-6);
//			}
			
			Date date=tempDate.parse(today);
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DAY_OF_MONTH, -29);
	        Date newTime = calendar.getTime();
	        
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        bdate = format.format(newTime);
	        
			System.out.println(".............30天前............." + bdate);
			String jk7sql = " select ifnull(jf.jfzf,0)jfzf,ifnull(kf.kfzf,0)kfzf,d.cdate,substring(d.cdate,6,10)cday from ( "
					      + " 	SELECT DATE_FORMAT(ADDDATE('"+bdate+"',INTERVAL @d DAY),'%Y-%m-%d') AS cdate ,@d :=@d + 1 day  "
					      + " 	FROM tj_classintegral,(SELECT @d := 0) temp  "
					      + " 	WHERE ADDDATE('"+bdate+"',INTERVAL @d DAY) <= DATE_FORMAT('"+today+"', '%Y-%m-%d')  "
					      + " )d "
					      + " left join ( "
					      + " 	select sum(zzfs)jfzf,dfdate from tj_classintegral where cusid = '"+cusid+"' and userid = '"+userid+"' and zzfs > 0 and state <> '2' group by dfdate "
					      + " )jf on CAST(jf.dfdate AS CHAR) = CAST(d.cdate AS CHAR) "
					      + " left join ( "
					      + " 	select sum(zzfs)kfzf,dfdate from tj_classintegral where cusid = '"+cusid+"' and userid = '"+userid+"' and zzfs < 0 and state <> '2' group by dfdate "
					      + " )kf on CAST(kf.dfdate AS CHAR) = CAST(d.cdate AS CHAR) "
					      + " order by cdate  "; 
			
			List<Map<String, String>> jk7map = tservice.getSelect(jk7sql);
			
			//=====================================查询公告=====================================
			String ggsql = " select u.*,t.* from basic_noticeuser u " 
						 + " left join  basic_notice t on u.noticeid=t.noticeid "
						 + " where u.userid='"+userid+"' and t.cusid='"+cusid+"' and u.noticestate='0' "
						 + " and t.validdate>='"+nowtime+"' and t.fbtime<='"+nowtime+"' "
						 + " order by t.CREATETIME desc limit 0,15 ";
			List<Map<String, String>> ggmap = tservice.getSelect(ggsql);
			
			
			//曲线图：7日曲线分
			Map dayjk = new HashMap();
			if(jk7map.size() == 30){
			//日期
				dayjk.put("d1", jk7map.get(0).get("cday"));
				dayjk.put("d2", jk7map.get(1).get("cday"));
				dayjk.put("d3", jk7map.get(2).get("cday"));
				dayjk.put("d4", jk7map.get(3).get("cday"));
				dayjk.put("d5", jk7map.get(4).get("cday"));
				dayjk.put("d6", jk7map.get(5).get("cday"));
				dayjk.put("d7", jk7map.get(6).get("cday"));
				dayjk.put("d8", jk7map.get(7).get("cday"));
				dayjk.put("d9", jk7map.get(8).get("cday"));
				dayjk.put("d10", jk7map.get(9).get("cday"));
				dayjk.put("d11", jk7map.get(10).get("cday"));
				dayjk.put("d12", jk7map.get(11).get("cday"));
				dayjk.put("d13", jk7map.get(12).get("cday"));
				dayjk.put("d14", jk7map.get(13).get("cday"));
				dayjk.put("d15", jk7map.get(14).get("cday"));
				dayjk.put("d16", jk7map.get(15).get("cday"));
				dayjk.put("d17", jk7map.get(16).get("cday"));
				dayjk.put("d18", jk7map.get(17).get("cday"));
				dayjk.put("d19", jk7map.get(18).get("cday"));
				dayjk.put("d20", jk7map.get(19).get("cday"));
				dayjk.put("d21", jk7map.get(20).get("cday"));
				dayjk.put("d22", jk7map.get(21).get("cday"));
				dayjk.put("d23", jk7map.get(22).get("cday"));
				dayjk.put("d24", jk7map.get(23).get("cday"));
				dayjk.put("d25", jk7map.get(24).get("cday"));
				dayjk.put("d26", jk7map.get(25).get("cday"));
				dayjk.put("d27", jk7map.get(26).get("cday"));
				dayjk.put("d28", jk7map.get(27).get("cday"));
				dayjk.put("d29", jk7map.get(28).get("cday"));
				dayjk.put("d30", jk7map.get(29).get("cday"));
				//个人每日加分
				dayjk.put("jf1", jk7map.get(0).get("jfzf"));
				dayjk.put("jf2", jk7map.get(1).get("jfzf"));
				dayjk.put("jf3", jk7map.get(2).get("jfzf"));
				dayjk.put("jf4", jk7map.get(3).get("jfzf"));
				dayjk.put("jf5", jk7map.get(4).get("jfzf"));
				dayjk.put("jf6", jk7map.get(5).get("jfzf"));
				dayjk.put("jf7", jk7map.get(6).get("jfzf"));
				dayjk.put("jf8", jk7map.get(7).get("jfzf"));
				dayjk.put("jf9", jk7map.get(8).get("jfzf"));
				dayjk.put("jf10", jk7map.get(9).get("jfzf"));
				dayjk.put("jf11", jk7map.get(10).get("jfzf"));
				dayjk.put("jf12", jk7map.get(11).get("jfzf"));
				dayjk.put("jf13", jk7map.get(12).get("jfzf"));
				dayjk.put("jf14", jk7map.get(13).get("jfzf"));
				dayjk.put("jf15", jk7map.get(14).get("jfzf"));
				dayjk.put("jf16", jk7map.get(15).get("jfzf"));
				dayjk.put("jf17", jk7map.get(16).get("jfzf"));
				dayjk.put("jf18", jk7map.get(17).get("jfzf"));
				dayjk.put("jf19", jk7map.get(18).get("jfzf"));
				dayjk.put("jf20", jk7map.get(19).get("jfzf"));
				dayjk.put("jf21", jk7map.get(20).get("jfzf"));
				dayjk.put("jf22", jk7map.get(21).get("jfzf"));
				dayjk.put("jf23", jk7map.get(22).get("jfzf"));
				dayjk.put("jf24", jk7map.get(23).get("jfzf"));
				dayjk.put("jf25", jk7map.get(24).get("jfzf"));
				dayjk.put("jf26", jk7map.get(25).get("jfzf"));
				dayjk.put("jf27", jk7map.get(26).get("jfzf"));
				dayjk.put("jf28", jk7map.get(27).get("jfzf"));
				dayjk.put("jf29", jk7map.get(28).get("jfzf"));
				dayjk.put("jf30", jk7map.get(29).get("jfzf"));
				
				//个人每日扣分
				dayjk.put("kf1", jk7map.get(0).get("kfzf"));
				dayjk.put("kf2", jk7map.get(1).get("kfzf"));
				dayjk.put("kf3", jk7map.get(2).get("kfzf"));
				dayjk.put("kf4", jk7map.get(3).get("kfzf"));
				dayjk.put("kf5", jk7map.get(4).get("kfzf"));
				dayjk.put("kf6", jk7map.get(5).get("kfzf"));
				dayjk.put("kf7", jk7map.get(6).get("kfzf"));
				dayjk.put("kf8", jk7map.get(7).get("kfzf"));
				dayjk.put("kf9", jk7map.get(8).get("kfzf"));
				dayjk.put("kf10", jk7map.get(9).get("kfzf"));
				dayjk.put("kf11", jk7map.get(10).get("kfzf"));
				dayjk.put("kf12", jk7map.get(11).get("kfzf"));
				dayjk.put("kf13", jk7map.get(12).get("kfzf"));
				dayjk.put("kf14", jk7map.get(13).get("kfzf"));
				dayjk.put("kf15", jk7map.get(14).get("kfzf"));
				dayjk.put("kf16", jk7map.get(15).get("kfzf"));
				dayjk.put("kf17", jk7map.get(16).get("kfzf"));
				dayjk.put("kf18", jk7map.get(17).get("kfzf"));
				dayjk.put("kf19", jk7map.get(18).get("kfzf"));
				dayjk.put("kf20", jk7map.get(19).get("kfzf"));
				dayjk.put("kf21", jk7map.get(20).get("kfzf"));
				dayjk.put("kf22", jk7map.get(21).get("kfzf"));
				dayjk.put("kf23", jk7map.get(22).get("kfzf"));
				dayjk.put("kf24", jk7map.get(23).get("kfzf"));
				dayjk.put("kf25", jk7map.get(24).get("kfzf"));
				dayjk.put("kf26", jk7map.get(25).get("kfzf"));
				dayjk.put("kf27", jk7map.get(26).get("kfzf"));
				dayjk.put("kf28", jk7map.get(27).get("kfzf"));
				dayjk.put("kf29", jk7map.get(28).get("kfzf"));
				dayjk.put("kf30", jk7map.get(29).get("kfzf"));
				
			}
			
			//个人当月总分
			Map monthjkmap = new HashMap();
			monthjkmap.put("summonthzf", summonthzf);
			monthjkmap.put("summonthjf", summonthjf);
			monthjkmap.put("summonthkf", summonthkf);
			monthjkmap.put("dayzf", dayzf);
			monthjkmap.put("yearzf", yearzf);
			monthjkmap.put("allzf", allzf);
			monthjkmap.put("monthzfpm", monthzfpm);
			monthjkmap.put("yearzfpm", yearzfpm);
			monthjkmap.put("allzfpm", allzfpm);
			
			Map monthdxjkmap = new HashMap();
			//个人当月单项总分
			monthdxjkmap.put("alljcfs", alljcfs);
			monthdxjkmap.put("alljkfs", alljkfs);
			monthdxjkmap.put("allgdfs", allgdfs);
			monthdxjkmap.put("alllsfs", alllsfs);
			monthdxjkmap.put("allxsjf", allxsjf);
			monthdxjkmap.put("allkqfs", allkqfs);
			monthdxjkmap.put("allqtfs", allqtfs);
			//个人当月单项总分占比
			monthdxjkmap.put("alljcfszb", alljcfszb);
			monthdxjkmap.put("alljkfszb", alljkfszb);
			monthdxjkmap.put("allgdfszb", allgdfszb);
			monthdxjkmap.put("alllsfszb", alllsfszb);
			monthdxjkmap.put("allxsjfzb", allxsjfzb);
			monthdxjkmap.put("allkqfszb", allkqfszb);
			monthdxjkmap.put("allqtfszb", allqtfszb);
			
			Map<String, String> useridmap = new HashMap<String, String>();
			useridmap.put("userid", userid);
			useridmap.put("nowmonth", nowmonth);
			
			modelAndView.addObject("monthjkmap", monthjkmap);
			modelAndView.addObject("monthdxjkmap", monthdxjkmap);
			modelAndView.addObject("dayjk", dayjk);
			modelAndView.addObject("userid", useridmap);
			modelAndView.addObject("ggmap", ggmap);
			
			modelAndView.setViewName("report/indexreport");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//关闭链接
			tservice.Close();
		}
		return modelAndView;
	}
	
	
}
