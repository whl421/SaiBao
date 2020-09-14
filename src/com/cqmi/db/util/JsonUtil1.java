package com.cqmi.db.util;
//package com.yh.voservice.maintest;
//
//import java.lang.reflect.Method;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.google.gson.Gson;
//import com.yh.bean.TreeBean;
//
//public class JsonUtil {
//	private String returnjson;
//	/**
//	 * 调用 名为act 的函数，函数传入参数为Map
//	 * @param act
//	 * @param actjson
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public String getActJson(String act,String actjson){
//		Map<String, String> actmap=StringToMap(actjson);
//		Method method;
//		try {
//			method = JsonUtil.class.getMethod(act, Map.class);
//			method.invoke(this, actmap);
//			if(returnjson!=null)return returnjson;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}  
//			return "{\"info\":\"0\",\"textinfo\":\"服务器繁忙，访问失败\"}";
//	}
//	
//	public void gettext1(){
//		
//		
//		
//		returnjson=MapToString(null);
//	}
//	 
//
//	/**
//	 * @param json
//	 * @return Map
//	 */
//	@SuppressWarnings("rawtypes")
//	public Map StringToMap(String json){
//		try {
//			Map map=new Gson().fromJson(json,Map.class);
//			return map==null?null:map;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	/** 
//	 * 方法名称:MapToString 
//	 * 传入参数:map 
//	 * 返回值:String  
//	*/  
//	@SuppressWarnings("rawtypes")
//	public String  MapToString(Map remap){  
//		try {
//			String restr=new Gson().toJson(remap);
//			return restr==null?null:restr;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	/**
//	 * 不为空 true
//	 * @param value
//	 * @return
//	 */
//	public boolean IsNotNull(String value){
//		if(value!=null){
//			if(value.trim().equals("")){
//				return false;
//			}else{
//				return true;
//			}
//		}else{
//			return false;
//		}
//	}
//	/**
//	 * 获得当前时间
//	 * @return
//	 */
//	public String GetTime(){
//		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());	
//	}
//	/**
//	 * 保留3位小数
//	 * @param numfolat
//	 * @return
//	 */
//	public String getXS3(String numfloat){
//		
//		try {
//			double nfloat=Double.parseDouble(numfloat==null?"0":numfloat);
//			return new DecimalFormat("######0.000").format(nfloat); 
//		} catch (Exception e) {
//			e.printStackTrace();
//			return numfloat;
//		}
//	}
//	/**
//	 * 得到树形结构，所有节点可点击反馈节点id
//	 * @param trees
//	 * @return
//	 */
//	public String getTreeAll(List<TreeBean> trees)throws Exception{
//		if(trees==null||trees.size()==0)return "";
//		String rootid="";
//		Map<String, String> ctree=new HashMap<String, String>();
//		for (TreeBean treeBean : trees) {
//			String pid=treeBean.getParentid().trim();
//			String id=treeBean.getTreeid();
//			String departlevel=treeBean.getDepartlevel();
//			boolean bo1=true;
//			boolean bo2=true;
//			for (TreeBean ben : trees) {
//				String cid=ben.getTreeid();
//				if(pid.trim().equals(cid.trim())){
//					bo1=false;
//					break;
//				}
//			}
//			for (TreeBean ben : trees) {
//				String cpid=ben.getParentid();
//				if(cpid.trim().equals(id.trim())){
//					bo2=false;
//					break;
//				}
//			}
//			if(bo2){
//				String li=ctree.get(pid);
//				String liid=ctree.get(pid+"id");
//				if(li==null||li.trim().equals(""))li="";
//				if(liid==null||liid.trim().equals(""))liid="";
//				li+="<li id='"+id+"'><span class='sontree'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span name='tree' id='"+id+"' departlevel='"+departlevel+"' onclick=\"usetree(this,event)\">"+treeBean.getTreename()+"</span></li>";
//				liid+=(liid+","+id);
//				ctree.put(pid, li);
//				ctree.put(pid+"id", liid);
////				System.out.println("pid:"+pid+"+++"+li);
//			}
//			if(bo1){
//				if(!rootid.equals("")){
//					return "";
//				}
//				rootid=treeBean.getParentid(); //主节点
//			}
//		}
//		int i=0;
//		while (ctree.get(rootid)==null&&i<20) {
//			ctree=treeul(trees, ctree);
//			i++;
//		}
//		
//		return "<ul id='tree'>"+ctree.get(rootid)+"</ul>";
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
//	/**
//	 * 得到树形结构，所有节点可点击反馈节点id,子节点无效
//	 * @param trees
//	 * @return
//	 */
//	public String getTreeParnet(List<TreeBean> trees)throws Exception{
//		if(trees==null||trees.size()==0)return "";
//		String rootid="";
//		Map<String, String> ctree=new HashMap<String, String>();
//		for (TreeBean treeBean : trees) {
//			String pid=treeBean.getParentid().trim();
//			String id=treeBean.getTreeid();
//			String departlevel=treeBean.getDepartlevel();
//			boolean bo1=true;
//			boolean bo2=true;
//			for (TreeBean ben : trees) {
//				String cid=ben.getTreeid();
//				if(pid.trim().equals(cid.trim())){
//					bo1=false;
//					break;
//				}
//			}
//			for (TreeBean ben : trees) {
//				String cpid=ben.getParentid();
//				if(cpid.trim().equals(id.trim())){
//					bo2=false;
//					break;
//				}
//			}
//			if(bo2){
//				String li=ctree.get(pid);
//				String liid=ctree.get(pid+"id");
//				if(li==null||li.trim().equals(""))li="";
//				if(liid==null||liid.trim().equals(""))liid="";
//				li+="<li id='"+id+"'><span class='sontree'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span name='tree' id='"+id+"' departlevel='"+departlevel+"' >"+treeBean.getTreename()+"</span></li>";
//				liid+=(liid+","+id);
//				ctree.put(pid, li);
//				ctree.put(pid+"id", liid);
////				System.out.println("pid:"+pid+"+++"+li);
//			}
//			if(bo1){
//				if(!rootid.equals("")){
//					return "";
//				}
//				rootid=treeBean.getParentid(); //主节点
//			}
//		}
//		int i=0;
//		while (ctree.get(rootid)==null&&i<20) {
//			ctree=treeul(trees, ctree);
//			i++;
//		}
//		
//		return "<ul id='tree'>"+ctree.get(rootid)+"</ul>";
//	}
//	
//	
//	/**
//	 * 得到树形结构，所有节点可点击反馈子节点id
//	 * @param trees
//	 * @return
//	 */
//	public String getTreeChild(List<TreeBean> trees){
//		if(trees==null||trees.size()==0)return "";
//		
//		
//		return "";
//	}
//}
