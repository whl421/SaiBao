package com.cqmi.controller.orderi;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cqmi.controller.login.LoginController;
import com.cqmi.controller.login.bean.User;
import com.cqmi.controller.orderi.http.HttpOrder;
import com.cqmi.controller.orderi.util.TUtil;
import com.cqmi.db.service.BeanService_Transaction;
import com.cqmi.db.util.ParaUtil;

@Controller
@RequestMapping("/orderCheck")
public class OrderInsertCheck {

	/**
	 * 确认表单
	 * 
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/middleCheck")
	public ModelAndView middleCheck(String tablejson) throws Exception {

		// 定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jsonObject = new JSONObject();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String userid = user.getUserid();

			String xssql = "select * from ORDERFORM t where t.ORDERID = '" + tablejson + "'";
			List<Map<String, String>> xsmap = tservice.getSelect(xssql);

			if (xsmap.size() > 0 && xsmap != null) {
				modelAndView.addObject("map", xsmap.get(0));
			}
			// 查询附件信息
			String sel_fjsql = "select * from attach t where t.CUSID = '" + cusid + "' and t.fid = '" + tablejson + "' "
					+ " and t.tableid = 'orderform' and t.tablecolid = 'orderid'";
			List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql);

			modelAndView.addObject("cusid", cusid);
			modelAndView.addObject("username", user.getUserid() + "-" + user.getUsercode());
			modelAndView.addObject("fjmap", fjmap);
			modelAndView.setViewName("orderManage/orderForm/orderCheckMid");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭链接
			tservice.Close();
		}
		return modelAndView;
	}

	/**
	 * 确认表单
	 * 
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orderCheckOver")
	public ModelAndView orderCheckOver(String tablejson) throws Exception {

		// 定义
		BeanService_Transaction tservice = new BeanService_Transaction();
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jsonObject = new JSONObject();
		try {
			User user = (User) LoginController.getSession().getAttribute("user");
			String cusid = user.getCusid();
			String userid = user.getUserid();

			String xssql = "select * from ORDERFORM t where t.ORDERID = '" + tablejson + "'";
			List<Map<String, String>> xsmap = tservice.getSelect(xssql);

			if (xsmap.size() > 0 && xsmap != null) {
				modelAndView.addObject("map", xsmap.get(0));
			}
			// 查询附件信息
			String sel_fjsql = "select * from attach t where t.CUSID = '" + cusid + "' and t.fid = '" + tablejson + "' "
					+ " and t.tableid = 'orderform' and t.tablecolid = 'orderid'";
			List<Map<String, String>> fjmap = tservice.getSelect(sel_fjsql);

			modelAndView.addObject("cusid", cusid);
			modelAndView.addObject("username", user.getUserid() + "-" + user.getUsercode());
			modelAndView.addObject("fjmap", fjmap);
			modelAndView.setViewName("orderManage/orderForm/orderCheckOver");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭链接
			tservice.Close();
		}
		return modelAndView;
	}

	/**
	 * 
	 * @param ordercode
	 * @param desc
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@RequestMapping(value = "/insertCheck", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String insertCheck(@RequestParam(value = "file", required = false) MultipartFile[] file, String ordercode,
			String qrmemo, String ostate) throws IOException {
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		//System.out.println("token start：" + datetime);
		String inspectedCase = qrmemo;
		String status = ostate;
		Map msgs = new HashMap();
		String msg = "";
		boolean fileerror = false;
		String filemsg = "";

		ServletContext sct = LoginController.getRequest().getSession().getServletContext();
		String token = sct.getAttribute("token") + "";
		HttpOrder http = new HttpOrder();
		if ("null".equals(token) || "".equals(token)) {
			token = http.getTokenByUrlpost();
			sct.setAttribute("token", token);
		}
		//System.out.println(token);
		datetime = tempDate.format(new java.util.Date());
		//System.out.println("token over：" + datetime);
		if (ordercode == null || "".equals(ordercode)) {
			msg = msg + "委托单号不能为空！;";
		}
		if (inspectedCase == null || "".equals(inspectedCase)) {
			msg = msg + "验收说明不能为空！;";
		}
		if (status == null || "".equals(status)) {
			msg = msg + "状态不能为空！;";
		}
		if ("null".equals(token) || "".equals(token)) {
			token = http.getTokenByUrlpost();
			sct.setAttribute("token", token);
			if ("null".equals(token) || "".equals(token)) {
				msg = msg + "总平台token获取失败！;";
			}
		}
		if ("".equals(msg)) {
			BeanService_Transaction tservice = new BeanService_Transaction();
			tservice.OpenTransaction();
			User user = (User) LoginController.getSession().getAttribute("user");

			datetime = tempDate.format(new java.util.Date());
			//System.out.println("start：" + datetime);

			String fileIds = "";
			if (file != null) {
				if (file.length > 0 && file[0].getSize() > 0 && !file[0].isEmpty()) {
					String newid = "";
					String title[] = { "id" };
					List<Map<String, String>> list = tservice.getSelect("select LAST_INSERT_ID() as id", title);
					if (list != null && list.size() > 0) {
						newid = list.get(0).get("id");
					} else {
						msg = msg + "操作失败请联系管理员";
						tservice.rollbackExe_close();
					}
					// 往附件表中增加数据
					TUtil util = new TUtil();
					List<Map> rets = util.insertAttach(tservice, file, newid, user, "orderform", "orderid");
					
					for (Map ret : rets) {
						datetime = tempDate.format(new java.util.Date());
						String path = ret.get("sourcefilesavepath") + "";
						String result = ret.get("result") + "";
						if ("0".equals(result) || "".equals(result)) {
							msg = msg + "文件存储失败请联系管理员,ErrorCode=" + result;
							break;
						} 
						File f = new File(path);
						//System.out.println(f.length() + "file http start：" + datetime);
						Map mm = http.uploadFile(token, f, "file", -1);
						datetime = tempDate.format(new java.util.Date());
						//System.out.println("file http over：" + datetime);
						try {
							String desc = mm.get("desc") + "";
							// {"code":"rest.success","desc":"成功","result":{"fileName":"考题_1590740150257_1590923486395.doc","id":"253961315503049209"}}
							// {"code":"msg.file.exceedSize","desc":"上传文件不能超过10M"}
							if (mm.get("result") == null) {
								filemsg = "文件上传失败，反馈" + desc + "，需重新上传！";
								fileerror = true;
								break;
							} else {
								String id = ((Map) mm.get("result")).get("id") + "";
								if ("null".equals(id) || "".equals(id)) {
									filemsg = "文件上传反馈ID错误，文件关联失败，需重新上传！";
									fileerror = true;
									break;
								} else {
									if ("".equals(fileIds)) {
										fileIds = id;
									} else {
										fileIds = ((Map) mm.get("result")).get("id") + "," + fileIds;
									}
									filemsg = "文件上传成功";
								}
							}
							f.delete();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			}

			datetime = tempDate.format(new java.util.Date());
			//System.out.println("http.send start：" + datetime);

			Map m = http.sendInserCheckUrlpost(ordercode, inspectedCase, status, fileIds, token);

			datetime = tempDate.format(new java.util.Date());
			//System.out.println("http.send over：" + datetime);
			String code = m.get("code") + "";
			String desc = m.get("desc") + "";
			String submit = "失败";
			if (code.contains("rest.success")) {
				submit = "成功";
				if (fileerror == false) {

				} else {
					submit = "数据成功，但文件上传失败";
				}
				msg = msg + "委托单更新总平台成功，反馈" + desc + "！";
			} else {
				msg = msg + ";委托单更新总平台失败，" + desc + "，" + fileerror + "，请重新填写、上传文件等数据！";
				msgs.put("info", "0");
				msgs.put("text", msg);
				sct.setAttribute("token", "");
			}
			List<String> lt = new ArrayList<String>();
			String userid = user.getUserid();
			String col_CHECKTIME = "MIDCHECKTIME";
			String col_CHECKUSERID = "MIDCHECKUSERID";
			String col_INSPECTEDCASE = "MIDINSPECTEDCASE";
			String col_INSPECTSUBMIT = "MIDINSPECTSUBMIT";
			String col_FILEIDS = "MIDFILEIDS";
			if ("01".equals(status)) {// 中验收标记
				col_CHECKTIME = "MIDCHECKTIME";
				col_CHECKUSERID = "MIDCHECKUSERID";
				col_INSPECTEDCASE = "MIDINSPECTEDCASE";
				col_INSPECTSUBMIT = "MIDINSPECTSUBMIT";
				col_FILEIDS = "MIDFILEIDS";
			}
			if ("02".equals(status)) {// 验收标记
				col_CHECKTIME = "CHECKTIME";
				col_CHECKUSERID = "CHECKUSERID";
				col_INSPECTEDCASE = "INSPECTEDCASE";
				col_INSPECTSUBMIT = "INSPECTSUBMIT";
				col_FILEIDS = "FILEIDS";
			}
			String sql = "update orderform t set t." + col_INSPECTEDCASE + " = ?, " + "t." + col_CHECKTIME + " = ?, t."
					+ col_CHECKUSERID + " = ? , t." + col_INSPECTSUBMIT + " = ? , t." + col_FILEIDS + " = ? "
					+ " where t.ORDERCODE = ? ";
			lt.add(inspectedCase);
			lt.add(ParaUtil.getNowTime());
			lt.add(userid);
			lt.add(submit);
			lt.add(fileIds);
			lt.add(ordercode);
			int r = tservice.UpdateSQL2(sql, lt);
			if (r == 0) {
				System.out.println(lt);
				System.out.println(sql);
				tservice.rollbackExe_close();
				msg = msg + ";委托单更新失败！";
				msgs.put("info", "0");
				msgs.put("text", msg);
			} else {
				if (fileerror == false) {
					msg = msg + "本地更新成功！";
				} else {
					msg = msg + "本地更新成功！但是，" + filemsg;
					msgs.put("info", "0");
				}
				msgs.put("text", msg);
			}
			tservice.Close();
		} else {
			msgs.put("info", "0");
			msgs.put("text", msg);
		}

		JSONObject jsonObject = new JSONObject();
		return jsonObject.fromObject(msgs).toString();
	}
}
