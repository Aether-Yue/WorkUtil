package com.sinosoft.platform.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 文件上载处理器<br>
 * 1、调用 doPost 处理<br>
 * 2、调用 getParameters 获取参数信息，返回Map对象,文件以流获取<br>
 * 3、正常处理之
 * @author gyas-itpingw
 * 
 */
public class FileUploadServletUtil extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ENCODE = "UTF-8";
	private Map<String,Object> tMap = new HashMap<String,Object> ();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UnsupportedEncodingException {
		request.setCharacterEncoding(ENCODE);
		String contentType = request.getContentType();
		System.out.println("REQUEST CONTENT TYPE: " + contentType);
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(
				diskFileItemFactory);
		servletFileUpload.setHeaderEncoding(ENCODE);
		// 按照传统方式获取数据
		List<FileItem> listItem;
		try {
			listItem = servletFileUpload.parseRequest(request);

			for (int i = 0; i < listItem.size(); i++) {
				FileItem fi = listItem.get(i);
				String fieldContentType = fi.getContentType();
				System.out.println("FiledContentType:" + fieldContentType);
				String fieldName = fi.getFieldName();
				if (fieldContentType != null) {
					InputStream filedIns = fi.getInputStream();
					if (filedIns != null) {
						tMap.put(fieldName, filedIns);
					}
				} else {
					String fieldStringValue = fi.getString();
					if (fieldStringValue != null) {
						tMap.put(fieldName, fieldStringValue);
					}
				}
			}
		} catch (FileUploadException e1) {
			System.out.println("构建上载解析失败：" + e1.getMessage());
			e1.printStackTrace();
		}
	}

	public Map<String,Object> getParameters() {
		return tMap;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
