/**
 * 
 */
package com.sinosoft.platform.core.util;

import java.util.List;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description:用以封装与服务器交互时文件载体 </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: Sinosoft</p>
 * @author Darker.Wang
 * @date 2016-7-27 下午12:02:17
 * @company SINOSOFT
 */
public class WebFile {

	private static final long serialVersionUID = 1L;
	private String fileName;//名称
	private String longName;//长名称
	private String parentName;//父类文件夹名
	private String makeTime;//创建日期
	private String modifyTime;//最后一次修改日期
	private int gId;//GroupId
	private long size;//大小
	private boolean isDir;//是否是文件夹
	
	private List<WebFile> files;//内部文件名称 isDir = true 时其内部有值
	
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public int getGId() {
		return gId;
	}
	public void setGId(int i) {
		this.gId = i;
	}
	public List<WebFile> getFiles() {
		return files;
	}
	public void setFiles(List<WebFile> files) {
		this.files = files;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMakeTime() {
		return makeTime;
	}
	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public boolean isDir() {
		return isDir;
	}
	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
