package com.cqmi.db.util;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cqmi.db.bean.TreeBean;
import com.google.gson.Gson;

public class JsonUtil {
	private String returnjson;
	/**
	 * ���� ��Ϊact �ĺ����������ΪMap
	 * @param act
	 * @param actjson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getActJson(String act,String actjson){
		Map<String, String> actmap=StringToMap(actjson);
		Method method;
		try {
			method = JsonUtil.class.getMethod(act, Map.class);
			method.invoke(this, actmap);
			if(returnjson!=null)return returnjson;
		} catch (Exception e) {
			e.printStackTrace();
		}  
			return "{\"info\":\"0\",\"textinfo\":\"��������æ������ʧ��\"}";
	}
	
	public void gettext1(){
		
		
		
		returnjson=MapToString(null);
	}
	 

	/**
	 * @param json
	 * @return Map
	 */
	@SuppressWarnings("rawtypes")
	public Map StringToMap(String json){
		try {
			Map map=new Gson().fromJson(json,Map.class);
			return map==null?null:map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/** 
	 * �������:MapToString 
	 * �������:map 
	 * ����ֵ:String  
	*/  
	@SuppressWarnings("rawtypes")
	public String  MapToString(Map remap){  
		try {
			String restr=new Gson().toJson(remap);
			return restr==null?null:restr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��Ϊ�� true
	 * @param value
	 * @return
	 */
	public boolean IsNotNull(String value){
		if(value!=null){
			if(value.trim().equals("")){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	/**
	 * ��õ�ǰʱ��
	 * @return
	 */
	public String GetTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());	
	}
	/**
	 * ����3λС��
	 * @param numfolat
	 * @return
	 */
	public String getXS3(String numfloat){
		
		try {
			double nfloat=Double.parseDouble(numfloat==null?"0":numfloat);
			return new DecimalFormat("######0.000").format(nfloat); 
		} catch (Exception e) {
			e.printStackTrace();
			return numfloat;
		}
	}
	/**
	 * �õ����νṹ�����нڵ�ɵ�������ڵ�id
	 * @param trees
	 * @return
	 */
	public String getTreeAll(List<TreeBean> trees)throws Exception{
		if(trees==null||trees.size()==0)return "";
		Map<String, String> ctree=new HashMap<String, String>();
		
		Map<String, String> sons=new  HashMap<String, String>();
		
		List<Integer> ids=new ArrayList<Integer>();
		for (int i = 0; i < trees.size(); i++) {
			TreeBean tb=trees.get(i);	
			sons.put(tb.getParentid(), i+"");
		}
		
		for (int i = 0; i < trees.size(); i++) {
			TreeBean tb=trees.get(i);	
			String id=tb.getTreeid();
			String pid=tb.getParentid().trim();
			String departlevel=tb.getDepartlevel();
//			System.out.println(sons.get(id));
			if(sons.get(id)==null){
				ids.add(i);
				String li=ctree.get(pid);
				String liid=ctree.get(pid+"id");
				if(li==null||li.trim().equals(""))li="";
				if(liid==null||liid.trim().equals(""))liid="";
				li+="<li id='"+id+"'><span class='sontree'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span name='tree' id='"+id+"' departlevel='"+departlevel+"' onclick=\"usetree(this,event)\">"+tb.getTreename()+"</span></li>";
				liid=(liid+","+id);
				ctree.put(pid, li);
				ctree.put(pid+"id", liid);
			}
		}
		if(ids.size()>0){
			for (int i = ids.size()-1; i>=0 ; i--) {
				trees.remove((int)ids.get(i));
			}
		}
		
		
//		int i=0;
//		while (ctree.get("root")==null&&i<20) {
		ctree=treeul(trees, ctree);
//			i++;
//		}
		
		return "<ul id='tree'>"+ctree.get("root")+"</ul>";
	}
	
	private Map<String, String> treeul(List<TreeBean> trees,Map<String, String> ctrees){
		Map<String, String> sons=new  HashMap<String, String>();
		List<Integer> ids=new ArrayList<Integer>();
		for (int i = 0; i < trees.size(); i++) {
			TreeBean tb=trees.get(i);	
			sons.put(tb.getParentid(), i+"");
		}
		for (int i = 0; i < trees.size(); i++) {
			TreeBean tb=trees.get(i);	
			String bid=tb.getTreeid();
			if(sons.get(bid)==null){
				ids.add(i);
				String li=ctrees.get(tb.getParentid());
				String lid=ctrees.get(tb.getParentid()+"id");
				String departlevel=tb.getDepartlevel();
				if(lid==null||lid.trim().equals(""))lid="";
				if(li==null||li.trim().equals(""))li="";
				li+="<li id='"+bid+"' url='url' ><span class='parenttree'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span name='tree' id='"+bid+"'  departlevel='"+departlevel+"'  onclick=\"usetree(this,event)\" >"+
						tb.getTreename()+"</span><ul>"+ctrees.get(bid)+"</ul></li>";
				ctrees.put(tb.getParentid(), li);
				lid=(lid+","+bid);
				ctrees.put(tb.getParentid()+"id",lid);
				ctrees.remove(bid);
				ctrees.remove(bid+"id");
				
			}
		}
		
		if(ids.size()>0){
			for (int i = ids.size()-1; i>=0 ; i--) {
				trees.remove((int)ids.get(i));
			}
		}
		if(trees.size()>0){
			ctrees=treeul(trees,ctrees);
		}
		
		return ctrees;
	}
	
	
//	private Map<String, String> treeul(List<TreeBean> trees,Map<String, String> ctrees){
//		boolean tboo=true;
//		while (tboo) {
//			int i=0;
//			for (TreeBean ben : trees) {
//				String bid=ben.getTreeid().trim();
//				String liids=ctrees.get(bid+"id");
//				if(liids!=null&&!liids.trim().equals("")){
//					String []liid=liids.split(",");
//				    boolean boo=true;
//				    for (TreeBean en : trees) {
//						String pid=en.getParentid().trim();
//						if(pid.equals(bid)){
//							boolean bo=true;
//							for (String sid : liid) {
//								if(sid.equals(en.getTreeid())){
//									bo=false;
//								}
//							}
//							if(bo){
//								boo=false;
//							}
//						}
//						
//					}
//					if(boo){
//						i++;
//						String li=ctrees.get(ben.getParentid());
//						String lid=ctrees.get(ben.getParentid()+"id");
//						String departlevel=ben.getDepartlevel();
//						if(lid==null||lid.trim().equals(""))lid="";
//						if(li==null||li.trim().equals(""))li="";
//						li+="<li id='"+bid+"' url='url' ><span class='parenttree'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span name='tree' id='"+bid+"'  departlevel='"+departlevel+"'  onclick=\"usetree(this,event)\" >"+
//											 ben.getTreename()+"</span><ul>"+ctrees.get(bid)+"</ul></li>";
//						ctrees.put(ben.getParentid(), li);
//						System.out.println(ben.getParentid());
//						lid+=(lid+","+bid);
//						ctrees.put(ben.getParentid()+"id",lid);
////						System.out.println("pid:"+ben.getParentid()+"---------"+li);
//						ctrees.remove(bid);
//						ctrees.remove(bid+"id");
//					}	
//				}
//			}
//			if(i==0)tboo=false;
//		}
//		
//		
//		return ctrees;
//	}
	
	
	
	
	
//	private Map<String, String> treeul(List<TreeBean> trees,Map<String, String> ctrees){
//		boolean tboo=true;
//		while (tboo) {
//			int i=0;
//			for (TreeBean ben : trees) {
//				String bid=ben.getTreeid().trim();
//				String liids=ctrees.get(bid+"id");
//				if(liids!=null&&!liids.trim().equals("")){
//					String []liid=liids.split(",");
//				    boolean boo=true;
//				    for (TreeBean en : trees) {
//						String pid=en.getParentid().trim();
//						if(pid.equals(bid)){
//							boolean bo=true;
//							for (String sid : liid) {
//								if(sid.equals(en.getTreeid())){
//									bo=false;
//								}
//							}
//							if(bo){
//								boo=false;
//							}
//						}
//					}
//					if(boo){
//						i++;
//						String li=ctrees.get(ben.getParentid());
//						String lid=ctrees.get(ben.getParentid()+"id");
//						String departlevel=ben.getDepartlevel();
//						if(lid==null||lid.trim().equals(""))lid="";
//						if(li==null||li.trim().equals(""))li="";
//						li+="<li id='"+bid+"' url='url' ><span class='parenttree'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span name='tree' id='"+bid+"'  departlevel='"+departlevel+"'  onclick=\"usetree(this,event)\" >"+
//											 ben.getTreename()+"</span><ul>"+ctrees.get(bid)+"</ul></li>";
//						ctrees.put(ben.getParentid(), li);
//						lid+=(lid+","+bid);
//						ctrees.put(ben.getParentid()+"id",lid);
////						System.out.println("pid:"+ben.getParentid()+"---------"+li);
//						ctrees.remove(bid);
//						ctrees.remove(bid+"id");
//					}	
//				}
//			}
//			if(i==0)tboo=false;
//		}
//		
//		
//		return ctrees;
//	}
	
	
	
	
	
	
	/**
	 * �õ����νṹ�����нڵ�ɵ�������ڵ�id,�ӽڵ���Ч
	 * @param trees
	 * @return
	 */
	public String getTreeParnet(List<TreeBean> trees)throws Exception{
		if(trees==null||trees.size()==0)return "";
		String rootid="";
		Map<String, String> ctree=new HashMap<String, String>();
		for (TreeBean treeBean : trees) {
			String pid=treeBean.getParentid().trim();
			String id=treeBean.getTreeid();
			String departlevel=treeBean.getDepartlevel();
			boolean bo1=true;
			boolean bo2=true;
			for (TreeBean ben : trees) {
				String cid=ben.getTreeid();
				if(pid.trim().equals(cid.trim())){
					bo1=false;
					break;
				}
			}
			for (TreeBean ben : trees) {
				String cpid=ben.getParentid();
				if(cpid.trim().equals(id.trim())){
					bo2=false;
					break;
				}
			}
			if(bo2){
				String li=ctree.get(pid);
				String liid=ctree.get(pid+"id");
				if(li==null||li.trim().equals(""))li="";
				if(liid==null||liid.trim().equals(""))liid="";
				li+="<li id='"+id+"'><span class='sontree'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span name='tree' id='"+id+"' departlevel='"+departlevel+"' >"+treeBean.getTreename()+"</span></li>";
				liid+=(liid+","+id);
				ctree.put(pid, li);
				ctree.put(pid+"id", liid);
//				System.out.println("pid:"+pid+"+++"+li);
			}
			if(bo1){
				if(!rootid.equals("")){
					return "";
				}
				rootid=treeBean.getParentid(); //���ڵ�
			}
		}
		int i=0;
		while (ctree.get(rootid)==null&&i<20) {
			ctree=treeul(trees, ctree);
			i++;
		}
		
		return "<ul id='tree'>"+ctree.get(rootid)+"</ul>";
	}
	
	
	/**
	 * �õ����νṹ�����нڵ�ɵ�������ӽڵ�id
	 * @param trees
	 * @return
	 */
	public String getTreeChild(List<TreeBean> trees){
		if(trees==null||trees.size()==0)return "";
		
		
		return "";
	}
}
