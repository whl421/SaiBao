package com.cqmi.controller.common;

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
@RequestMapping("/common")
public class CommonController extends BasicAction{
	
	Util util = new Util();
	
	/**
	 * 设备信息
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/equipListForm")
	public ModelAndView equipListForm() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("common/selectEquipList");
		
		return modelAndView;

	}
	/**
	 * 任务信息
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/taskInfoForm")
	public ModelAndView taskInfoForm() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("common/selectTaskInfo");
		
		return modelAndView;

	}
	/**
	 * 库存信息
	 * @param tablejson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/storclForm")
	public ModelAndView storclForm() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("common/selectStorClInfo");
		
		return modelAndView;

	}
}
