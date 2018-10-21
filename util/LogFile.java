/**
 * 
 */
package com.sinosoft.platform.core.util;

import java.io.File;
import java.io.Serializable;

/**
 * 日志文件类,用于封装服务器上文件类
 * @author Darker.Wang
 * @date 2016-6-2 下午9:44:25
 */
public class LogFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String filePath;
	private String makeTime;
	private String modifyTime;
	private String maker;
	private String modifyer;
	private String size;
	private File file;
	private int isDect;//1-是 0 否
	private int isFile;//1-是 0 否
	
	
	@Override
	public String toString() {
		return "LogFile [fileName=" + fileName + ", filePath=" + filePath
				+ ", makeTime=" + makeTime + ", modifyTime=" + modifyTime
				+ ", maker=" + maker + ", modifyer=" + modifyer + ", isDect="
				+ isDect + ", isFile=" + isFile + "]";
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getModifyer() {
		return modifyer;
	}

	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getIsDect() {
		return isDect;
	}

	public void setIsDect(int isDect) {
		this.isDect = isDect;
	}

	public int getIsFile() {
		return isFile;
	}

	public void setIsFile(int isFile) {
		this.isFile = isFile;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	public LogFile() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
