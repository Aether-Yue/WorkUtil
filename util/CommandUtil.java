/**
 * 
 */
package com.sinosoft.ummp.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 命令处理工具类
 * @author Darker.Wang
 * @date 2017-1-13 下午8:45:10
 * @company SINOSOFT
 */
public class CommandUtil {
	private static String WIND = "cmd /c ";
	private static String LINUX = "sh -c ";
	private static String ENCODEING = "GBK";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static String getBase(int type){
		switch(type){
		case 1 :
			 return WIND;
		case 2 :
			 return LINUX;
		default :
			return null;
		}
	}
	/**
	 * 执行指定 命令
	 * @param type 1=windows 2=linux
	 * @param command
	 * @return
	 */
	public static String doCommand(int type,String command){
		try {
			String base = getBase(type);
			System.out.println(base+command);
			Process pro = Runtime.getRuntime().exec(base+command);
			int exitValue = pro.waitFor();
			if (0 != exitValue) {  
		           return "do command failed. error code is : " + exitValue;  
		    } 
			InputStream in = pro.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in,ENCODEING));
			String str = null;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				System.out.println(str);
				sb.append(str + "\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "do command failed. exception :" + e.getMessage();
		}
	}
}
