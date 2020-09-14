package com.cqmi.controller.login.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUilt {

	/**
	 * 根据excel文件路径和 sheet名称读取文件
	 * 
	 * @param filePath
	 * @param sheetname
	 * @return
	 */
	public List<Map<String,String>>  Excel(String filePath,String sheetname) {
		InputStream is=null;
		Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String columns[] =null; // {"name","age","score"};
        wb = readExcel(filePath,is);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheet(sheetname);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            
            columns=new String[colnum];
            if(row !=null){
                for (int j=0;j<colnum;j++){
                    cellData = (String) getCellFormatValue(row.getCell(j));
                    columns[j]=cellData;
                }
            }
            
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                    	
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }
        try {
			if(wb!=null)wb.close();
			if(is!=null)is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return list;
    }
	
	/**
	 * 根据excel文件名称和 sheet名称读取文件 及excel文件流读取
	 * 
	 * @param filePath
	 * @param sheetname
	 * @return
	 */
	public List<Map<String,String>>  Excel(String fileName,String sheetname,InputStream is) {
		Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String columns[] =null; // {"name","age","score"};
        wb = readExcel_InputStream(fileName,is);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheet(sheetname);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            
            columns=new String[colnum];
            if(row !=null){
                for (int j=0;j<colnum;j++){
                    cellData = (String) getCellFormatValue(row.getCell(j));
                    columns[j]=cellData;
                }
            }
            
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }
        try {
			if(wb!=null)wb.close();
			if(is!=null)is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return list;
    }
	/**
	 * 根据excel文件路径和 sheet序列（排序0开始） 
	 * 
	 * @param filePath
	 * @param sheetname
	 * @return
	 */
	public List<Map<String,String>>  Excel(String filePath,int sheetindex) {
		InputStream is=null;
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String columns[] =null;       // {"name","age","score"};
        wb = readExcel(filePath,is);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(sheetindex);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            
            columns=new String[colnum];
            if(row !=null){
                for (int j=0;j<colnum;j++){
                    cellData = (String) getCellFormatValue(row.getCell(j));
                    columns[j]=cellData;
                }
            }
            
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }
        try {
			if(wb!=null)wb.close();
			if(is!=null)is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        //遍历解析出来的list
//        for (Map<String,String> map : list) {
//            for (Entry<String,String> entry : map.entrySet()) {
//                System.out.print(entry.getKey()+":"+entry.getValue()+",");
//            }
//            System.out.println();
//        }
        return list;
    }
	/**
	 * 根据excel文件名称和 sheet序列（排序0开始） 及excel文件流读取
	 * 
	 * @param filePath
	 * @param sheetname
	 * @return
	 */
	public List<Map<String,String>>  Excel_NoColse(String fileName,int sheetindex,String _url) {
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String columns[] =null;       // {"name","age","score"};
        Workbook wb = readExcel_filePath(fileName,_url);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(sheetindex);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            
            columns=new String[colnum];
            if(row !=null){
                for (int j=0;j<colnum;j++){
                    cellData = (String) getCellFormatValue(row.getCell(j));
                    columns[j]=cellData;
                }
            }
            
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        } 
        try {
			if(wb!=null)wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return list;
    }
	private  Workbook wb =null;
	private InputStream is=null;
	public List<Map<String,String>>  Excel_NoColse(String fileName,int sheetindex,InputStream is) {
        this.is=is;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String columns[] =null;       // {"name","age","score"};
        wb = readExcel_InputStream(fileName,is);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(sheetindex);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            
            columns=new String[colnum];
            if(row !=null){
                for (int j=0;j<colnum;j++){
                    cellData = (String) getCellFormatValue(row.getCell(j));
                    columns[j]=cellData;
                }
            }
            
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }
        return list;
    }
	public void WbIs_Colse(){
		 try {
				if(wb!=null)wb.close();
				if(is!=null)is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	/**
	 * 根据excel文件名称和 sheet序列（排序0开始） 及excel文件流读取
	 * 
	 * @param filePath
	 * @param sheetname
	 * @return
	 */
	public List<Map<String,String>>  Excel(String fileName,int sheetindex,InputStream is) {
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String columns[] =null;       // {"name","age","score"};
        wb = readExcel_InputStream(fileName,is);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(sheetindex);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            
            columns=new String[colnum];
            if(row !=null){
                for (int j=0;j<colnum;j++){
                    cellData = (String) getCellFormatValue(row.getCell(j));
                    columns[j]=cellData;
                }
            }
            
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }
        try {
			if(wb!=null)wb.close();
			if(is!=null)is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//        //遍历解析出来的list
//        for (Map<String,String> map : list) {
//            for (Entry<String,String> entry : map.entrySet()) {
//                System.out.print(entry.getKey()+":"+entry.getValue()+",");
//            }
//            System.out.println();
//        }
        return list;
    }
	
    //读取excel
    public Workbook readExcel(String filePath,InputStream is){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
//        String extString = filePath.substring(filePath.lastIndexOf("."));
        try {
            is = new FileInputStream(filePath);
            if(filePath.endsWith(".xls")){		//".xls".equals(extString)
                return wb =  new HSSFWorkbook(is);
            }else if(filePath.endsWith(".xlsx")){ //".xlsx".equals(extString)
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
    //直接读流文件
    public Workbook readExcel_filePath(String fileName,String filePath){
        Workbook wb = null;
        if(fileName==null){
            return null;
        }
        FileInputStream is=null;
        try {
            is = new FileInputStream(filePath);
            if(fileName.endsWith(".xls")){
                return wb =  new HSSFWorkbook(is);
            }else if(fileName.endsWith(".xlsx")){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	 try {
     			if(is!=null)is.close();
     		} catch (IOException e) {
     			e.printStackTrace();
     		}
        }
        return wb;
    }
    //直接读流文件
    public Workbook readExcel_InputStream(String fileName,InputStream is){
        Workbook wb = null;
        if(fileName==null){
            return null;
        }
        try {
//            is = new FileInputStream(filePath);
            if(fileName.endsWith(".xls")){
                return wb =  new HSSFWorkbook(is);
            }else if(fileName.endsWith(".xlsx")){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
    
    
    
    public String getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC:{
            	DecimalFormat df = new DecimalFormat("#");
            	String dfs=df.format(cell.getNumericCellValue());
//                cellValue = String.valueOf(cell.getNumericCellValue());
                cellValue=dfs;
                break;
            }
            case Cell.CELL_TYPE_FORMULA:{
                //判断cell是否为日期格式
                if(DateUtil.isCellDateFormatted(cell)){
                    //转换为日期格式YYYY-mm-dd
                    cellValue = cell.getDateCellValue();
                }else{
                    //数字
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            case Cell.CELL_TYPE_STRING:{
                cellValue = cell.getRichStringCellValue().getString();
                break;
            }
            default:
                cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return (String) cellValue;
    }
    
    
    public HSSFWorkbook getHSSFWorkbook(List<Map<String, String>> rows,String[] nameid,String excelname){
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet = wb.createSheet(excelname);
		//sheet.setColumnWidth((short)0,(short)(256*35));  
		HSSFRow titleRow = sheet.createRow(0);

		for (int i = 0; i < nameid.length; i++) {
			HSSFCell cell = titleRow.createCell((short)i);
			sheet.setColumnWidth((short)i,(short)(256*35)); 
			cell.setCellValue(nameid[i]);
		}
		for (int i = 0; i < rows.size(); i++) {
			HSSFRow Row = sheet.createRow(i+1);
			String roww="";
			for (int j = 0; j < nameid.length; j++) {
				HSSFCell cell = Row.createCell((short)j);
				cell.setCellValue(rows.get(i).get(nameid[j]));
//				roww+=rows.get(i).get(nameid[j])+",";
			}
//			System.out.println(roww);
		}
		System.out.println("导出文件EXCEL准备完毕，导出中............");
		return wb;
	}
    
    public HSSFWorkbook getHSSFWorkbook(List<List<String>> rows,String excelname){
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet = wb.createSheet(excelname);
		//sheet.setColumnWidth((short)0,(short)(256*35));  
		HSSFRow titleRow = sheet.createRow(0);

		 
		for (int i = 0; i < rows.size(); i++) {
			HSSFRow Row = sheet.createRow(i);
			String roww="";
			for (int j = 0; j < rows.get(i).size(); j++) {
				HSSFCell cell = Row.createCell((short)j);
				cell.setCellValue(rows.get(i).get(j));
			}
		}
		System.out.println("导出文件EXCEL准备完毕，导出中............");
		return wb;
	}
    
}

