//package com.cqmi.db.util;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//
//public class log_file {
//	
//	public static void logFile(String username,String usercode,String menulist,String button){
//		synchronized(log_file.class){
//			String datelog=ParaUtil.getNowTime()+">>>>���Ӽ�¼---�û���:"+username+",�û�code:"+usercode+",��ģ�飺"+menulist+",ʹ�ù��ܣ�"+button;
//			String filename=getlogfilename();
//			writelogFile(filename, datelog);
//		}
//	}
//	
////	private static String getlogfilename(){ 
////		String path =log_file.class.getResource("/").toString().replace("file:/", "").replace("WEB-INF/classes/", "").replace("/", "\\");
////		if(path.endsWith("bin")||path.endsWith("BIN"))path=path.substring(0, path.length()-4);
////		path+="/log/";
////		return path+ParaUtil.getNowDate()+".text";
////	}
//	
//	public static void writelogFile(String filename, String conent) {   
//		 File dirFile = new File(filename);
//	        //�ļ�������ʱ�����������ļ���
//	        if(!dirFile.isDirectory())dirFile.getParentFile().mkdir();
//	       
//	        if(!dirFile.exists()){
//	        	try {
//					dirFile.createNewFile();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//	        }
//	    BufferedWriter out = null;   
//	    try {   
//	      out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dirFile, true)));   
//	      out.write("\r\n"+conent);   
//	    } catch (Exception e) {   
//	    	e.printStackTrace();   
//	    } finally {   
//	      try {   
//	        if(out != null) out.close();   
//	      } catch (IOException e) {   
//	         e.printStackTrace();   
//	      }   
//	    } 
//	} 
//	
//	@SuppressWarnings("unused")
//	private boolean writeToFile(String fileName,String write){
//		boolean boo=true;
//		FileWriter fw = null;
//		 BufferedWriter bw =null;
//	    try {
//	        File file = new File(fileName);
//	        //�ļ�������ʱ�����������ļ���
//	        if(!file.exists()){
//	            file.createNewFile();
//	        }
//	        fw = new FileWriter(file,false);
//	        bw = new BufferedWriter(fw);
//	        bw.write(write);
//	        
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        boo=false;
//	    }finally{
//				try {
//					if(bw!=null)bw.close(); 
//			    	if(fw!=null)fw.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//	    }
//	    return boo;
//	} 
//}
