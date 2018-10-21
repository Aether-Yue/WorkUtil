package com.sinosoft.ummp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.platform.core.util.CError;
import com.sinosoft.platform.core.util.CErrors;
/**
 * CSV 工具类
 * @author MrWang  move by pzz
 * @date 2016-09-28
 */
public class CSVUtil {

	public  CErrors cErrors = new CErrors();
	public  boolean isCOSucess=false;
	private  String ENCODE = "UTF-8";
	
	public  String getEncode() {
		return ENCODE;
	}

	public  void setEncode(String encode) {
		ENCODE = encode;
	}

	public CSVUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 数字校验
	 * @param value
	 * @return
	 */
	public boolean checkNumber(String value){
		String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
		return value.matches(regex);
	}
	/**
	 * 纯数字校验
	 * @param value
	 * @return
	 */
	public boolean checkNumberPure(String value){
		String regex = "^([1-9]\\d*)||([0]\\d*)";
		return value.matches(regex);
	}
	/**
     * 导出
     * @param file csv文件(路径+文件名)
     * @param dataList 数据
     * @return
     */
    public boolean exportCsv(File file, List<List<String>> dataList,boolean append){
        boolean isSucess=false;
        
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        
        try {
            out = new FileOutputStream(file,append);//true 追加
            osw = new OutputStreamWriter(out,ENCODE);
            bw = new BufferedWriter(osw);
            int index = 0;
            if(dataList!=null && !dataList.isEmpty()){
                for(List<String> datas : dataList){
                	StringBuffer sb = new StringBuffer("");
                	Iterator<String> iter = datas.iterator();
                	while(iter.hasNext()){
                		String data = iter.next();
                		sb.append(data);
                		if(iter.hasNext()){
                			sb.append(",");
                		}
                	}
                	if(index < dataList.size()){
//                		sb.append("\r\n");
                		sb.append(System.getProperty("line.separator"));//根据系统获取换行符
                	}
                	//System.out.println(sb.toString());
                	bw.write(sb.toString());
                	index ++;
                }
            }
            
            //流FLUSH
            bw.flush();
            osw.flush();
            out.flush();
            
            isSucess=true;
        } catch (Exception e) {
            CError cError = new CError();
            cError.errorMessage = "导出数据流异常："+e.getMessage();
            cErrors.addOneError(cError);
        	isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        
        return isSucess;
    }
	/**
     * 导出,默认往文件中追加
     * @param file csv文件(路径+文件名)
     * @param dataList 数据
     * @return
     */
    public boolean exportCsv(File file, List<List<String>> dataList){
        boolean isSucess=false;
        
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        
        try {
            out = new FileOutputStream(file,true);//true 追加
            osw = new OutputStreamWriter(out,ENCODE);
            bw = new BufferedWriter(osw);
            int index = 0;
            if(dataList!=null && !dataList.isEmpty()){
                for(List<String> datas : dataList){
                	StringBuffer sb = new StringBuffer("");
                	Iterator<String> iter = datas.iterator();
                	while(iter.hasNext()){
                		String data = iter.next();
                		sb.append(data);
                		if(iter.hasNext()){
                			sb.append(",");
                		}
                	}
                	if(index < dataList.size()){
                		sb.append("\n");
                	}
                	System.out.println(sb.toString());
                	bw.write(sb.toString());
                	index ++;
                }
            }
            
            //流FLUSH
            bw.flush();
            osw.flush();
            out.flush();
            
            isSucess=true;
        } catch (Exception e) {
            CError cError = new CError();
            cError.errorMessage = "导出数据流异常："+e.getMessage();
            cErrors.addOneError(cError);
        	isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        
        return isSucess;
    }
    /**
     * 导入
     * @param file csv文件(路径+文件)
     * @return
     */
    public List<String[]> importCsv(File file){
        List<String[]> dataList=new ArrayList<String[]>();
        
        BufferedReader br=null;
        try { 
            br = new BufferedReader(new FileReader(file));
            String line = ""; 
            while ((line = br.readLine()) != null 
            		&& !"".equals(line.trim()) 
            		&& line.contains(",")) {
            	line = new String(line.trim().getBytes(),ENCODE);
            	String[] lines = line.split(",");
            	if(lines != null && lines.length > 0){
            		dataList.add(lines);	
            	}
            	//System.out.println("- CSV LINE : "+line);
            	line = null;
            }
        }catch (Exception e) {
        	e.printStackTrace();
        	CError cError = new CError();
            cError.errorMessage = "导入数据异常："+e.getMessage();
            cErrors.addOneError(cError);
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("- include title all : "+dataList.size()+" rows");
        return dataList;
    }
    
    public static void main(String args[]){
    	System.out.println(BigDecimal.class.getName());
    	String reg = "JS047956123,210,2016-12-29,2016-12-29,00,,000031,江苏,OFE";
    	String[] result = reg.split(",",-1);
    	System.out.println(result.length);
    	for(String s : result){
    		System.out.println(s);
    	}
    }
}
