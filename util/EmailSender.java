package com.sinosoft.platform.core.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import config.Config;

/**
 * 邮件发送器：用于发送邮件。采用java自带的javax.email且确认邮箱已经开启了smtp/pop <br>
 * title 必须赋值<br>
 * sendTo 必须赋值<br>
 * content 必须赋值<br>
 * 其他可选<br>
 * @date 2016-10-29
 */
public class EmailSender {

	
	private String title;//标题：必须
	private String content;//内容：必须
	private File[] attachments;//附件
	private String[] sendTo;//邮件接收者：必须
	private String[] sendCc;//邮件抄送者
	private String[] sendBcc;//邮件密送
	private boolean debug = false;//默认调试不打开
	private String encoding = "UTF-8";
	private static EmailSender instance = null;
	private static Properties properties = null;
	public EmailSender() {

	}
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public File[] getAttachments() {
		return attachments;
	}
	public void setAttachments(File[] attachments) {
		this.attachments = attachments;
	}
	public String[] getSendTo() {
		return sendTo;
	}
	public void setSendTo(String[] sendTo) {
		this.sendTo = sendTo;
	}
	public String[] getSendCc() {
		return sendCc;
	}
	public void setSendCc(String[] sendCc) {
		this.sendCc = sendCc;
	}
	public String[] getSendBcc() {
		return sendBcc;
	}
	public void setSendBcc(String[] sendBcc) {
		this.sendBcc = sendBcc;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public static Properties getProperties() {
		return properties;
	}
	public static void setProperties(Properties properties) {
		EmailSender.properties = properties;
	}
	public static void setInstance(EmailSender instance) {
		EmailSender.instance = instance;
	}
	
	/**
	 * 通过制定邮箱发送：properties 为制定邮件箱协议的配置文件名称，放在Config之下<br>
	 * 配置文件内容如下：<br>
	 * 	mail.smtp.auth=true<br>
		mail.transport.protocol=smtp<br>
		mail.smtp.host=smtp.sina.com.cn<br>
		mail.smtp.port=25<br>
		mail.from.user=54wping@sina.com<br>
		mail.from.pass=54MrWong<br>
	 * @param propertise
	 * @return
	 */
	public static EmailSender getInstance(String propertise) {
		if (instance == null) {
			instance = new EmailSender();
			properties = Config.getProperties(propertise);
		}
		return instance;
	}
	/**
	 * 默认则是通过新浪邮箱发送:
	 * sendTo 必须有
	 * content 必须有
	 * title 必须有
	 * @return
	 */
	public static EmailSender getInstance() {
		if (instance == null) {
			instance = new EmailSender();
			properties = Config.getProperties("service.sina.properties");
		}
		return instance;
	}
	public static EmailSender getInstance(Properties properties){
		instance = new EmailSender();
		EmailSender.properties = properties;
		return instance;
	} 
	/**
	 * 发送邮件方法重载
	 * @param title
	 * @param content
	 * @return
	 */
	public boolean send(String title,String content){
		this.title = title;
		this.content = content;
		return send();
	}
	
	/**
	 * 发送送邮件方法重载
	 * @param title
	 * @param context
	 * @param sendTo
	 * @param sendCc
	 * @return
	 */
	public boolean send(String title,String content,String[] sendTo,String[] sendCc) {
		this.title = title;
		this.content = content;
		this.sendTo = sendTo;
		this.sendCc = sendCc;
		return send();
	}
	/**
	 * 发送邮件，发送前请确保title 和 内容是否有值
	 * @return
	 */
	public boolean send() {
		try {
			System.out.println("- EmailSender be ready to send email ");
			String protocal = properties.getProperty("mail.transport.protocol");
			String host = properties.getProperty("mail.smtp.host");
			String user = properties.getProperty("mail.from.user");
			String pass = properties.getProperty("mail.from.pass");
			
			// 建立会话
			Session session = Session.getInstance(properties);
			session.setDebug(debug);// 开启后有调试信息
			// 建立会话信息
			Message msg = new MimeMessage(session);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
            // 设置发件人
			msg.setFrom(new InternetAddress(user)); 
			
			// 收件人
			if(sendTo == null || sendTo.length <= 0){
				System.out.println("- EmailSender no send to");
				return false;
			}
			String toList = getMailList(sendTo);
			System.out.println("- send to  : "+getMailList(sendTo));
			InternetAddress[] iaToList = InternetAddress.parse(toList);
			msg.setRecipients(Message.RecipientType.TO, iaToList); 
			
			// 抄送人
			if(sendCc != null && sendCc.length > 0){
				String ccList = getMailList(sendCc);
				System.out.println("- send cc  : "+ccList);
				InternetAddress[] iaCcList = InternetAddress.parse(ccList);
				msg.setRecipients(Message.RecipientType.CC, iaCcList); 
			}
			// 密送人
			if(sendBcc != null && sendBcc.length > 0){
				String bccList = getMailList(sendBcc);
				System.out.println("- send bcc : "+bccList);
				InternetAddress[] iaBccList = InternetAddress.parse(bccList);
				msg.setRecipients(Message.RecipientType.BCC, iaBccList); 
			}
			// 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content, "text/html;charset="+encoding);
            multipart.addBodyPart(contentPart);

            if(attachments != null && attachments.length > 0){
            	// 循环添加附件的内容
            	for(int f = 0; f < attachments.length; f ++){
            		File attachment = attachments[f];
            		if (attachment != null && attachment.exists()) {
                        BodyPart attachmentBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(attachment);
                        attachmentBodyPart.setDataHandler(new DataHandler(source));
                        //MimeUtility.encodeWord可以避免文件名乱码
                        attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
                        multipart.addBodyPart(attachmentBodyPart);
                    }
            	}
            }
            
            msg.setSubject(title); // 主题
            // 内容添加到Content
            msg.setContent(multipart);
			// 保存邮件
            msg.saveChanges();
            // 发送日期 可用于自动发送
            msg.setSentDate(new Date()); 
            
			// 邮件服务器进行验证
			Transport tran = session.getTransport(protocal);
			
			tran.connect(host,user,pass);
			// bluebit_cn是用户名，xiaohao是密码
			tran.sendMessage(msg, msg.getAllRecipients()); // 发送
			System.out.println("- your email send success , from ："+user);
			
		} catch (Exception e) {
			System.out.println("- email send exception : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private String getMailList(String[] mailArray) {

		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}
			}
		}
		return toList.toString();
	}
	public static void main(String[] args){
		
	}
}