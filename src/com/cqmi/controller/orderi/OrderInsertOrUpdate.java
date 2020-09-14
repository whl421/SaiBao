package com.cqmi.controller.orderi;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cqmi.controller.login.LoginController;
import com.cqmi.dao.util.Util;
import com.cqmi.db.service.BeanService_Transaction;
import com.cqmi.db.util.ParaUtil;

import md5.JavaMD5;

@Controller
@RequestMapping("/dispatch")
public class OrderInsertOrUpdate {
	String urli = "http://ts-zuul.liyantech.cn";
	Util util = new Util();

	/**
	 * 
	 * @param demandId
	 * @param title
	 * @param bider
	 * @param employer
	 * @param budget
	 * @param endDate
	 * @param status
	 * @param contacts
	 * @param phone
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@RequestMapping(value = "/insert", produces = "application/json; charset=utf-8") // ,method
																						// =
																						// RequestMethod.POST
	@ResponseBody
	public String insertOrUpdate(String demandId, String title, String bider, String employer, String budget,
			String startDate, String intermediateDate, String endDate, String status, String contacts, String phone)
					throws IOException {
		Map msgs = new HashMap();
		String msg = "";
		String type = "";
		String result = "error";

		String Key = "OvyjKkrP";
		String secretKey = "3ac74ea09e9f4593991d5962ca490a50";
		String SN = LoginController.getRequest().getHeader("SN") + "";// access-token
		String HKEY = LoginController.getRequest().getHeader("KEY") + "";// access-token
		if (!Key.equals(HKEY)) {
			msg = msg + "KEY校验失败！;";
		}

		String paras = "/dispatch/insert?bider=" + bider + "&budget=" + budget + "&contacts=" + contacts + "&demandId="
				+ demandId + "&employer=" + employer + "&endDate=" + endDate + "&intermediateDate=" + intermediateDate
				+ "&phone=" + phone + "&startDate=" + startDate + "&status=" + status + "&title=" + title + secretKey;
		String urlencode = URLEncoder.encode(paras, LoginController.getRequest().getCharacterEncoding());
		JavaMD5 str = new md5.JavaMD5();
		String _SN = "";
		try {
			_SN = str.getMD5ofStr(urlencode.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!_SN.toLowerCase().equals(SN.toLowerCase())) {
			msg = msg + "SN校验失败！;";
		}

		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);

		demandId = LoginController.getRequest().getParameter("demandId") + "";
		if (demandId == null || "".equals(demandId) || "null".equals(demandId)) {
			msg = msg + "需求编号不能为空！;";
		}
		title = LoginController.getRequest().getParameter("title") + "";
		if (title == null || "".equals(title) || "null".equals(title)) {
			msg = msg + "需求名称不能为空！;";
		}
		bider = LoginController.getRequest().getParameter("bider") + "";
		if (bider == null || "".equals(bider) || "null".equals(bider)) {
			msg = msg + "中标者不能为空！;";
		}
		employer = LoginController.getRequest().getParameter("employer") + "";
		if (employer == null || "".equals(employer) || "null".equals(employer)) {
			msg = msg + "雇主不能为空！;";
		}
		budget = LoginController.getRequest().getParameter("budget") + "";
		if (budget == null || "".equals(budget) || "null".equals(budget)) {
			msg = msg + "需求预算不能为空！;";
		} else {
			try {
				Double.parseDouble(budget);
			} catch (Exception e) {
				msg = msg + "需求预算浮点数转换错误！;";
			}
		}
		startDate = LoginController.getRequest().getParameter("startDate") + "";
		if (startDate == null || "".equals(startDate) || "null".equals(startDate)) {
			msg = msg + "任务开始时间不能为空！;";
		} else {
			try {
				startDate = tempDate.format(df.parse(startDate));
			} catch (Exception e) {
				msg = msg + "任务开始时间转换错误，只识别Sat Aug 22 14:32:12 CST 2020此格式！;";
			}
		}
		intermediateDate = LoginController.getRequest().getParameter("intermediateDate") + "";
		if (intermediateDate == null || "".equals(intermediateDate) || "null".equals(intermediateDate)) {
			// msg = msg + "中验时间不能为空！;";
		} else {
			try {
				intermediateDate = tempDate.format(df.parse(intermediateDate));
			} catch (Exception e) {
				msg = msg + "中验时间转换错误，只识别Sat Aug 22 14:32:12 CST 2020此格式！;";
			}
		}
		endDate = LoginController.getRequest().getParameter("endDate") + "";
		if (endDate == null || "".equals(endDate) || "null".equals(endDate)) {
			msg = msg + "任务截止时间不能为空！;";
		} else {
			try {
				endDate = tempDate.format(df.parse(endDate));
			} catch (Exception e) {
				msg = msg + "任务截止时间转换错误，只识别Sat Aug 22 14:32:12 CST 2020此格式！;";
			}
		}
		status = LoginController.getRequest().getParameter("status") + "";
		if (status == null || "".equals(status) || "null".equals(status)) {
			msg = msg + "需求状态不能为空！;";
		}
		contacts = LoginController.getRequest().getParameter("contacts") + "";
		if (contacts == null || "".equals(contacts) || "null".equals(contacts)) {
			msg = msg + "联系人不能为空！;";
		}
		phone = LoginController.getRequest().getParameter("phone") + "";
		if (phone == null || "".equals(phone)) {
			msg = msg + "联系方式不能为空！;";
		}

		if ("".equals(msg)) {
			BeanService_Transaction tservice = new BeanService_Transaction();
			String sqlt = "select ordercode,ostate from orderform " + " where ORDERCODE='" + demandId + "' ";
			String titl[] = { "ordercode", "ostate" };
			List<Map<String, String>> list = tservice.getSelect(sqlt, titl);
			String ordercode = "";
			String ostate = "";
			if (list == null || list.size() == 0) {

			} else {
				Map<String, String> d = tservice.getSelect(sqlt, titl).get(0);
				if (d != null) {
					ordercode = d.get("ordercode");
					ostate = d.get("ostate");
				}
			}
			if (ordercode != null && !"".equals(ordercode)) {
				type = "update";
				// 有 update
				List<String> lt = new ArrayList<String>();
				String sql = "update orderform t set t.ORDERNAME = ?, t.ORDERUSER = ?, "
						+ " t.KHNAME = ?, t.ORDERPRICE = ?,t.STARTDATE = ?,t.INTERMEDIATEDATE = ?,"
						+ " t.ENDDATE = ?, t.STATUS = ?, t.CONTACTS = ?, t.PHONE = ? "
						+ ",t.ORDERTIME = ? where t.ORDERCODE = ? ";
				lt.add(title);
				lt.add(bider);
				lt.add(employer);
				lt.add(budget);
				lt.add(startDate);
				lt.add(intermediateDate);
				lt.add(endDate);
				lt.add(status);
				lt.add(contacts);
				lt.add(phone);
				lt.add(ParaUtil.getNowTime());// 委托时间
				lt.add(demandId);
				boolean isupdate = true;
				if (isupdate && "1".equals(ostate)) {
					int r = tservice.UpdateSQL2(sql, lt);
					if (r == 0) {
						tservice.rollbackExe_close();
						msg = "委托单还未接收，可以更新，委托单更新失败！";
					} else {
						msg = "委托单还未接收，可以更新，更新成功！";
						result = "success";
					}
				} else {
					msg = "委托单已接收并锁定，不能更新，请联系管理员！";
				}
			} else {
				type = "insert";
				// insert
				String sql = "insert into orderform (CUSID,ORDERCODE,ORDERNAME,ORDERUSER,KHNAME,ORDERPRICE,"
						+ " STARTDATE,INTERMEDIATEDATE, ENDDATE,STATUS,CONTACTS,PHONE,OSTATE,ORDERTIME) " + " value ("
						+ " ?,?,?,?,?,?," + " ?,?," + " ?,?,?,?,?,?" + " ) ";
				List<String> lt = new ArrayList<String>();
				lt.add("1");
				lt.add(demandId);
				lt.add(title);
				lt.add(bider);
				lt.add(employer);
				lt.add(budget);
				lt.add(startDate);
				lt.add(intermediateDate);
				lt.add(endDate);
				lt.add(status);
				lt.add(contacts);
				lt.add(phone);
				lt.add("1");// 状态 1新委托
				lt.add(ParaUtil.getNowTime());// 委托时间

				int r = tservice.UpdateSQL2(sql, lt);
				if (r == 0) {
					tservice.rollbackExe_close();
					msg = "委托单新增失败！";
				} else {
					msg = "新增成功！";
					result = "success";
				}
			}
			tservice.Close();
		}

		msgs.put("result", result);
		msgs.put("msg", msg);
		msgs.put("type", type);

		JSONObject jsonObject = new JSONObject();
		return jsonObject.fromObject(msgs).toString();
	}
}
