package com.sinosoft.ummp.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.sinosoft.platform.core.util.CError;
import com.sinosoft.platform.core.util.CErrors;
import com.sinosoft.platform.core.util.DateUtils;
import com.sinosoft.platform.core.util.WebFile;
import com.sinosoft.ummp.config.ProFile;

/**
 * 使用前请先调用登录方法进行登录
 * @author Darker.Wang
 * @date 2015-12-25
 */
public class USFTPClient {
	
	private Logger logger = Logger.getLogger(USFTPClient.class);
	public CErrors cErrors = new CErrors();
	public String  FTP_TYPE = "";
	public static final String  FTP_TYPE_UPLOAD = "U";
	public static final String  FTP_TYPE_DOWNLOAD = "D";
	/**
	 * 将信息配置在 com\config\service.peoperties
	 * @param server
	 */
	public USFTPClient(String server,String ftptype){
		this.FTP_TYPE = ftptype;
		Properties prop = null;
		try {
			prop = ProFile.getInstance().getProFile("service");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(prop == null){
			logger.info("service.properties not exist ");
		}else{
			// 拆分配置信息，包括：ip、用户、密码、文件在服务器上的路径
			ip = prop.getProperty(server);
			port = Integer.parseInt(prop.getProperty(server + "_port"));
			user = prop.getProperty(server + "_user");
			pass = prop.getProperty(server + "_pass");
			basedir = prop.getProperty(server + "_filepath");
		}
	}
	public USFTPClient(String host, int port, String username, String password){
		this.ip = host;
		this.port = port;
		this.user = username;
		this.pass = password;
	}
	private String ip;
	private int port;
	private String user;
	private String pass;
	private String basedir;
	private ChannelSftp sftpClient = null;
	public String resultMessage;
	public ChannelSftp getSftpClient() {
		return sftpClient;
	}

	public void setSftpClient(ChannelSftp sftpClient) {
		this.sftpClient = sftpClient;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getBasedir() {
		return basedir;
	}
	public void setBasedir(String basedir) {
		this.basedir = basedir;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * 断开连接，释放资源
	 * @return
	 */
	public boolean disConnect(){
		if(sftpClient != null){
			try {
				if(sftpClient.getSession() != null){
					sftpClient.getSession().disconnect();
				}
				sftpClient.disconnect();
				sftpClient.exit();
			} catch (JSchException e) {
				e.printStackTrace();
			};
			sftpClient = null;
		}
		resultMessage = null;
		System.gc();
		return true;
	}
	/**
	 * 连接SFTP服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public boolean login() {
		try {
			logger.info("时间： "+DateUtils.getCurrentDateTime() + " LOGINING SFTP [ IP："+ip+",PORT:"+port+",USER:"+user+",PASS:"+pass+"]");
			JSch jsch = new JSch();
			jsch.getSession(user, ip, port);
			Session sshSession = jsch.getSession(user, ip, port);
			logger.info("SESSION CREATED .");
			
			sshSession.setPassword(pass);
			Properties sshConfig = new Properties();
			
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			
			logger.info("SESSION CONNECTED AND WITING FOR OPENT CHANNEL...");
			Channel channel = sshSession.openChannel("sftp");
			logger.info("OPENED CHANNEL.");
			
			channel.connect();
			sftpClient = (ChannelSftp) channel;
			
			logger.info("CONNECTED SUCCESS " + ip + ".");
		} catch (Exception e) {
			resultMessage = "LOGIN TO SFTP SERVICE FAIL";
			logger.error(resultMessage);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public boolean upload(String directory, String uploadFile, ChannelSftp sftp) {
		try {
			logger.info("UPLOAD FILE PATH: "+directory);
			sftp = ChannelSftpCD(directory,sftp);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			resultMessage = "UPLOAD FILE ："+e.getMessage();
			logger.error(resultMessage);
			e.printStackTrace();
			return false;
		}
		logger.info("UPLOAD FILE DONE: "+uploadFile);
		return true;
	}

	/**
	 * ChannelSftp 进入指定文件
	 * @param directory 路径必须存在
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	private ChannelSftp ChannelSftpCD(String directory, ChannelSftp sftp) throws SftpException{
		try{
			logger.info("HOME : "+sftp.getHome());
			if(directory != null && !directory.equals("")){
				logger.info("CD DIR: "+directory);
				//此处报错：No such file
				sftp.cd(directory);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("NO such file: "+directory);
			return sftp;
		}finally{
			
		}
		return sftp;
	}
	
	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录 下载目录
	 * @param downloadFile
	 *            下载的文件 名字及类型
	 * @param saveFile
	 *            存在本地的路径,包含文件名则为指定文件名，否则为默认的下载文件的名字和类型
	 * @param sftp
	 */
	public boolean download(String directory, String downloadFile,String saveFile, ChannelSftp sftp) {
		try {
			//directory：/data/000031/bddj/download/此处路径已经是download，不处理
			if(directory != null && directory !=""){
				//directory = sftp.getHome()+"/"+directory.replace("/", "");
			}else{
				directory = sftp.getHome();
			}
			sftp = ChannelSftpCD(directory,sftp);
			saveFile = saveFile.replace("\\", "/").replace("//", "/");
			if(!saveFile.endsWith("/")){
				saveFile +="/";
			}
			String dirs=saveFile.substring(0, saveFile.lastIndexOf("/"));
			dirs=dirs.substring(0, dirs.lastIndexOf("/"));
			File file2 = new File(dirs);
			if(!file2.exists()){
				System.out.println("创建文件夹："+file2);
				file2.mkdirs();
			}
			logger.info("DOWNLOAD DIRE HOME: "+directory);
			logger.info("DOWNLOAD FILE CONF: "+downloadFile);
			logger.info("SAVE     FILE PATH: "+saveFile);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file,false));
			logger.info("DOWNLOAD FILE DOWN: "+downloadFile);
		} catch (Exception e) {
			resultMessage = "DOWNLOAD FILE EXCEPTION ："+e.getMessage();
			logger.error(resultMessage);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public boolean delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			directory = sftp.getHome()+"/"+directory.replace("/", "");
			sftp = ChannelSftpCD(directory,sftp);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage = "DELETE FILE EXCEPTION ："+e.getMessage();
			logger.error(resultMessage);
			return false;
		}
		return true;
	}

	/**
	 * 列出指定目录下的文件，无目录爆出NoSuchFile异常
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	@SuppressWarnings("rawtypes")
	public Vector listFiles(String show,ChannelSftp sftp)
			throws SftpException {
		String directory = ".";
		String home = sftp.getHome();
		logger.info( " HOME : "+sftp.getHome());
		if(show != null && !show.equals("") ){
			if(!show.contains(home)){
				directory = home+"/"+show.replace("/", "");
				logger.info(" directory : "+directory);
			}else{
				
			}
			logger.info(" SHOW : "+show);
//			logger.info(" SHOW : "+directory);
			
//			return sftp.ls(directory);
			return sftp.ls(show);
		}
		return sftp.ls(directory);
	}
	
	/**
	 * 获取到服务器的指定文件
	 * @param directory
	 * @param sftp
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<WebFile> listWebBean(String directory, ChannelSftp sftp) {
		List<WebFile> files = new ArrayList<WebFile>();
		try {
			Vector v = listFiles(directory,sftp);
			Iterator iter = v.iterator();
			while(iter.hasNext()){
				WebFile bean = new WebFile();
				ChannelSftp.LsEntry  entry = (ChannelSftp.LsEntry) iter.next();
				String mdate = entry.getAttrs().getMtimeString();
				String adate = entry.getAttrs().getMtimeString();
				
				String mDate = DateUtils.convert2Date(mdate);
				String aDate = DateUtils.convert2Date(adate);
				
				bean.setFileName(entry.getFilename());
				bean.setMakeTime(mDate);//the first maketime
				bean.setModifyTime(aDate);//the last access time
				bean.setParentName(directory);
				bean.setSize(entry.getAttrs().getSize());
				bean.setDir(entry.getAttrs().isDir());
				bean.setGId(entry.getAttrs().getGId());
				bean.setLongName(entry.getLongname());
				String filename = entry.getFilename();
				logger.debug("filename: "+filename);
				int x = filename.indexOf(".");
				if(!entry.getAttrs().isDir() && x > 1){
					files.add(bean);
				}
				files.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return files;
	}
	/**
	 * 获取服务器指定日期的文件
	 * @param directory 指定路径
	 * @param sftp ChannelSftp
	 * @param startDate 起始日期
	 * @param endDate 截至日期
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("rawtypes")
	public List<WebFile> listWebBeanByDate(String directory, ChannelSftp sftp,String startDate,String endDate) throws ParseException {
		List<WebFile> files = new ArrayList<WebFile>();
		try {
			if(startDate == null && endDate == null){
				return listWebBean(directory,sftp);
			}
			logger.info("====== 进入指定日期下载："+startDate+" "+endDate);
			Vector v = listFiles(directory,sftp);
			Iterator iter = v.iterator();
			while(iter.hasNext()){
				WebFile bean = new WebFile();
				ChannelSftp.LsEntry  entry = (ChannelSftp.LsEntry) iter.next();
				String mdate = entry.getAttrs().getMtimeString();
				String adate = entry.getAttrs().getMtimeString();
				
//				String mDate = DateUtils.convert2Date(mdate);
//				String aDate = DateUtils.convert2Date(adate);
				String mDate = DateUtils.convertLocalDate(mdate);
				String aDate = DateUtils.convertLocalDate(adate);
				
				int a1 = DateUtils.compareDate(aDate, startDate);
				int a2 = DateUtils.compareDate(aDate, endDate);
				int m1 = DateUtils.compareDate(mDate, startDate);
				int m2 = DateUtils.compareDate(mDate, endDate);
				
				logger.debug("mDate: "+mDate +" aDate:"+aDate + " startDate:"+startDate+" endDate:"+endDate);
				if((a1>=0 && a2<=0) || (m1>=0 && m2<=0)){
					
					bean.setFileName(entry.getFilename());
					bean.setMakeTime(mDate);//the first maketime
					bean.setModifyTime(aDate);//the last access time
					bean.setParentName(directory);
					bean.setSize(entry.getAttrs().getSize());
					bean.setDir(entry.getAttrs().isDir());
					bean.setGId(entry.getAttrs().getGId());
					bean.setLongName(entry.getLongname());
					String filename = entry.getFilename();
					String filetime = entry.getAttrs().getAtimeString();
					logger.debug(filename+"\t"+filetime);
					int x = filename.indexOf(".");
					if(!entry.getAttrs().isDir() && x > 1){
						files.add(bean);
					}
				}
			}
		} catch (SftpException e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.errorMessage = "No such file";
			cError.functionName = "listWebBeanByDate";
			cErrors.addOneError(cError);
			return null;
		}
		
		return files;
	}
	
	/**
	 * 显示该路径下的文件信息
	 * @param sftp
	 * @param show
	 */
	@SuppressWarnings("rawtypes")
	public String[][] show(String show,ChannelSftp  sftp){
		List<String> rtvname = new ArrayList<String>();
		List<String> rtvtime = new ArrayList<String>();
		String[][] rtv = null;
		try {
			Vector v = listFiles(show,sftp);
			Iterator iter = v.iterator();
			while(iter.hasNext()){
				ChannelSftp.LsEntry  entry = (ChannelSftp.LsEntry) iter.next();
				String filename = entry.getFilename();
				String filetime = entry.getAttrs().getAtimeString();
				logger.debug(filename+"\t"+filetime);
				rtvname.add(filename);
				rtvtime.add(filetime);
			}
			rtv = new String[rtvname.size()][2];
			for(int i = 0; i<rtvname.size(); i++){
				rtv[i][0] = rtvname.get(i);
				rtv[i][1] = rtvtime.get(i);
			}
		} catch (SftpException e) {
			e.printStackTrace();
			return null;
		}
		return rtv;
	}
	public static void main(String[] args) {
		try {
		USFTPClient sfu = new USFTPClient("UPLOAD_FTPServer","U");
		System.out.println(sfu.login());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
