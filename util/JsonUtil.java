package com.sinosoft.platform.core.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.sinosoft.platform.core.util.XMLLogUtil.LogTask;

/**
 * JSON操作工具类
 * @author Darker.Wang
 * @date 2016-8-8 下午4:38:24
 * @company SINOSOFT
 * @version 1.0
 */
public class JsonUtil {

	public JsonUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 集合List转成JSON字符串
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String conver2Json(List list){
		JSONArray jsono = JSONArray.fromObject(list);
		return jsono.toString();
	}
	/**
	 * 集合对象等转换成JSON字符串
	 * @param obj
	 * @return
	 */
	public static String conver2Json(Object obj){
		JSONObject jsono = JSONObject.fromObject(obj);
		return jsono.toString();
	}
	/**
	 * 实例化XML格式字符串成JSON格式字符串
	 * @param xml
	 * @return
	 */
	public static String xml2Json(String xml){
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXBuilder sb = new  SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 实例化指定XML格式的文件成JSON格式字符串
	 * @param file
	 * @return
	 */
	public static String xml2Json(File file){
		JSONObject obj = new JSONObject();
		try {
			SAXBuilder sb = new  SAXBuilder();
			Document doc = sb.build(file);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 启动线程，调用底层存储，JSON报文保存至本地
	 * @param fileName
	 * @param fileStr
	 */
	public static void saveLocal(String fileName,String jsonStr){
		LogTask task = new LogTask(fileName,jsonStr);
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
		String jsonStr;
		LogTask(String fileName,String jsonStr){
			this.fileName = fileName;
			this.jsonStr = jsonStr;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("- JSON路径: "+fileName);
			if(jsonStr != null){

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
					}else{
						pfile.deleteOnExit();
						pfile.mkdirs();
						pfile = null;
					}
					os = new FileOutputStream(file);
					writer = new PrintWriter(os);
					char[] buf = jsonStr.toCharArray();
					writer.write(buf);
					writer.flush();
					os.flush();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(os != null) {
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}	
					}
					if(writer != null) {
						try {
							writer.close();
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}
			
			}
		}
	}
	
	/**
	 * JSON对象格式化
	 * @param jsonStr
	 * @return String
	 */
	public static String formatJson(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int i=0;i<jsonStr.length();i++){
            char c = jsonStr.charAt(i);
            if(level>0&&'\n'==jsonForMatStr.charAt(jsonForMatStr.length()-1)){
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
            case '{':
            case '[':
                jsonForMatStr.append(c+"\n");
                level++;
                break;
            case ',':
                jsonForMatStr.append(c+"\n");
                break;
            case '}':
            case ']':
                jsonForMatStr.append("\n");
                level--;
                jsonForMatStr.append(getLevelStr(level));
                jsonForMatStr.append(c);
                break;
            default:
                jsonForMatStr.append(c);
                break;
            }
        }
         
        return jsonForMatStr.toString();
 
    }
     
    private static String getLevelStr(int level){
        StringBuffer levelStr = new StringBuffer();
        for(int levelI = 0;levelI<level ; levelI++){
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
	/**
	 * 迭代遍历元素，转换成Map集合
	 * @param root
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map iterateElement(Element root){
		List node = root.getChildren();
		Element et = null;
		Map obj = new HashMap();
		List list = null;
		for(int i = 0; i < node.size(); i++){
			list = new LinkedList();
			et = (Element) node.get(i);
			if(et.getTextTrim().equals("")){
				if(et.getChildren() == null || et.getChildren().size() == 0){
					continue;
				}
				if(obj.containsKey(et.getName())){
					list = (List)obj.get(et.getName());
				}
				list.add(iterateElement(et));
				obj.put(et.getName(),list);
			}else{
				if(obj.containsKey(et.getName())){
					list = (List) obj.get(et.getName());
				}
				list.add(et.getTextTrim());
				obj.put(et.getName(), list);
			}
		}
		return obj;
	}
	/**
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String elementStr =
				 "<PDL>"
				+"<PDL-Head>"
				+"<RequestType>SummaryCheckResultQuery</RequestType>"
				+"<CompanyCode>000031</CompanyCode>"
				+"<SerialNo>00000003397295</SerialNo>"
				+"<TimeStamp>20160303150923</TimeStamp>"
				+"<DataType>L</DataType>"
				+"</PDL-Head>"
				+"<PDL-Body>"
				+"<QueryNo>Q6a76fe52-e8a2-43b9-ba76-e62e3251be7f</QueryNo>"
				+"</PDL-Body>"
				+"</PDL>";
		String reqTestXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+elementStr;
		Element rootElement = new Element("PDL");
		Element sonElement = new Element("PDL-Head");
		sonElement.addContent(new Element("PDL").addContent("11"));
		sonElement.addContent(new Element("PDL").addContent("9"));
		rootElement.addContent(sonElement);
		rootElement.addContent(new Element("PDL-Head2").addContent("1"));
		String json = xml2Json(reqTestXML);
		System.out.println(json);
		Map map = iterateElement(rootElement);
		System.out.println(map);
	}

}
