/**
 * 
 */
package com.sinosoft.platform.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 报文日志处理工具类
 * @author Darker.Wang
 * @date 2016-9-5 下午1:48:15
 * @company SINOSOFT
 */
public class XMLLogUtil {
	/**
	 * 包含路径
	 * @param fileName  包含路径
	 * @param fileStr
	 */
	public XMLLogUtil() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 启动线程，报文保存至本地【字符串方式】
	 * @param fileName
	 * @param fileStr
	 */
	public static void saveLocal(String fileName,String fileStr){
		LogTask task = new LogTask(fileName,fileStr);
		Thread t = new Thread(task);
		t.start();
	}
	/**
	 * 启动线程，报文保存至本地【字符串方式】
	 * @param fileName
	 * @param fileStr
	 * @param isAppend 是否叠加
	 */
	public static void saveLocal(String fileName,String fileStr,boolean isAppend){
		LogTask task = new LogTask(fileName,fileStr,isAppend);
		Thread t = new Thread(task);
		t.start();
	}
	/**
	 * 启动线程，调用底层存储，报文保存至本地【Doc对象方式】
	 * @param fileName
	 * @param fileStr
	 */
	public static void saveLocal(String fileName,Document xmlDoc){
		LogTask task = new LogTask(fileName,xmlDoc);
		Thread t = new Thread(task);
		t.start();
	}
	/**
	 * 内部类启动线程存储文件
	 * @author Darker.Wang
	 * @date 2016-9-5 下午2:48:42
	 * @company SINOSOFT
	 */
	public static class LogTask implements Runnable{

		String fileName;
		String fileStr;
		Document xmlDoc;
		boolean isAppend;
		LogTask(String fileName,String fileStr){
			this.fileName = fileName;
			this.fileStr = fileStr;
		}
		LogTask(String fileName,String fileStr,boolean isAppend){
			this.fileName = fileName;
			this.fileStr = fileStr;
			this.isAppend = isAppend;
		}
		LogTask(String fileName,Document xmlDoc){
			this.fileName = fileName;
			this.xmlDoc = xmlDoc;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("- XML路径: "+fileName);
			if(xmlDoc != null){
				save(fileName,xmlDoc);
			}
			else if(fileStr != null){
				if(isAppend){
					save(fileName,fileStr,true);	
				}else{
					save(fileName,fileStr,false);	
				}
			}
		}
	}
	/**
	 * 将文件添加到一个文件中，是否覆盖
	 * @param fileName 文件名称
	 * @param msg 信息
	 * @param isAppend 添加还是覆盖
	 * @return
	 */
	private static boolean save(String fileName,String msg,boolean isAppend){
		// TODO Auto-generated method stub
		OutputStream os = null;
		PrintWriter writer = null;
		try {
			File file = new File(fileName);
			String pd = file.getParent();
			File pfile = new File(pd);
			if(!pfile.exists()){
				pfile.mkdirs();
				pfile = null;
			}
			os = new FileOutputStream(file,isAppend);
			writer = new PrintWriter(os);
			if(isAppend){
				msg = "\n======"+DateUtils.getCurrentDateTime()+"======\n"+msg;	
			}
			char[] buf = msg.toCharArray();
			writer.write(buf);
			writer.flush();
			os.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}	
			}
			if(writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}	
			}
		}
		return true;
	}
	/**
	 * 存储方法,底层存储
	 * @param fileName
	 * @param fileStr
	 * @return
	 */
	public static boolean save(String fileName,Document xmlDoc) {
		// TODO Auto-generated method stub
		OutputStream os = null;
		XMLWriter xmlWiter = null;
		String encoding = xmlDoc.getXMLEncoding();
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(encoding);
			format.setIndent(true);
			format.setNewlines(true);
			format.setIndent(" ");
			
			File file = new File(fileName);
			String pd = file.getParent();
			File pfile = new File(pd);
			if(!pfile.exists()){
				pfile.mkdirs();
				pfile = null;
			}
			os = new FileOutputStream(file);
			xmlWiter = new XMLWriter(os,format);
			xmlWiter.write(xmlDoc);
			//关闭流
			if(xmlWiter != null){
				xmlWiter.flush();
				xmlWiter.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}	
			}
		}
		return true;
	}
	/**
	 * 字符串型存储方法,底层存储
	 * @param fileName
	 * @param fileStr
	 * @return
	 */
	public static boolean saveAsString(String fileName,String obj) {
		// TODO Auto-generated method stub
		return save(fileName,obj,false);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
	}


}
