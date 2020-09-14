package com.cqmi.db.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParaUtil {
	/**
	 * ��������
	 */
	public static int number=0;
	public static int datetime=1;
	public static int string=3;
	
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * ����
	 * @return
	 */
	public static  String getNowDate() {
		return df.format(new Date()) + "";
	}
	/**
	 * ����ʱ��
	 * @return
	 */
	public static  String getNowTime() {
		return dft.format(new Date()) + "";
	}
	/**
	 * ��������
	 */
	public static String KEY_MD5 = "MD5"; 
	/**
	 * ��������
	 * @param inputStr
	 * @return
	 */
	public static String getResult(String inputStr)
	  {
//	    System.out.println("=======����ǰ������:"+inputStr);
	    BigInteger bigInteger=null;
	    try {
	     MessageDigest md = MessageDigest.getInstance(KEY_MD5); 
	     byte[] inputData = inputStr.getBytes();
	     md.update(inputData); 
	     bigInteger = new BigInteger(md.digest()); 
	    } catch (Exception e) {e.printStackTrace();}
//	    System.out.println("MD���ܺ�:" + bigInteger.toString()); 
	    return bigInteger.toString();
	  }
	
	
}
