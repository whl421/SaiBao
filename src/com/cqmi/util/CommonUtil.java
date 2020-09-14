package com.cqmi.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cqmi.db.service.BeanService_Transaction;

public class CommonUtil {

	/**
	 * 获取按区域分组的加油站
	 * @return
	 */
	public Map<String, List<Map>> getGroupStation(BeanService_Transaction tservice) {
		
		Map dataItem; // 数据库中查询到的每条记录 
		Map<String, List<Map>> resultMap = new LinkedHashMap<String, List<Map>>(); // 最终要的结果 
		String sql = "select a.AREANAME,t.* from basic_gasstation t\n" +
				"left join basic_area a on a.AREAID = t.AREAID\n" +
				"where t.STATIONSTATE = '1'\n" +
				"order by a.AREACODE,t.STATIONCODE";
		
		String title[] = {"areaname","stationid","stationname","stationcode"};
		List<Map<String, String>> userList = tservice.getSelect(sql,title);
		for(int i=0; i<userList.size(); i++){ 
			
		    dataItem = userList.get(i); 
		    if(resultMap.containsKey(dataItem.get("areaname"))){ 

		        resultMap.get(dataItem.get("areaname")).add(dataItem); 
		    }else{ 
		    	
		        List<Map> list = new ArrayList<Map>(); 
		        list.add(dataItem); 
		        resultMap.put((String)dataItem.get("areaname"),list); 
		    } 
		}
		return resultMap;
	}
}
