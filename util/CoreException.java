package com.sinosoft.platform.core.exception;

import org.apache.log4j.Logger;

/**
 * 异常封装处理类，用以对异常信息进行处理。<br>
 * 说明：当指定异常级别时才会进行处理，对应不同异常级别走不同的处理机制<br>
 * 用法：当需要对异常信息进行处理时，推介使用信息&级别构造器，默认走异常处理机制。否则需要手动调用处理机制<br>
 * 备注：dealOff 为异常处理机制断开开关，默认为false,未断开，处理机制正常进行。true 则断开处理机制，自动调用和手动调用都将失效。
 * @author Darker.Wang
 * @date 2016-8-10 下午2:58:46
 * @company SINOSOFT
 */
public class CoreException extends Exception{

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CoreException.class);
	
	
	//field
	private String message;//异常信息
	private String operator;//操作者可能对应UserCode
	private String function;//异常函数
	private String errorCode;//错误编码
	private String clazz;//异常类名称
	private String date;//异常日期(字符串形式)
	private String time;//异常事件(字符串形式)
	private int level;//异常级别 对应 CoreExceptionRunable 接口中的级别
	private Exception exception;//具体异常信息类
	private boolean dealOff = false;//处理机制断开标识， 默认为 false=不断开,可调用，当为 true 时手动调用和自动调用都失效
	
	
	/**
	 * 异常处理主函数
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 默认空构造器，若需异常处理机制则指定异常级别(level) 后手动调用处理 dealException()
	 * @throws CoreException 
	 */
	public CoreException(){
		// TODO Auto-generated constructor stub
	}
	/**
	 * 指定异常界别构造器，默认会走异常处理机制，无需指定处理
	 * @param msg 异常信息
	 * @param level 异常级别
	 */
	public CoreException(String msg,int level){
		message = msg;
		this.level = level;
	}
	/**
	 * 无级别构造器，若需异常处理机制则指定异常级别(level) 后手动调用处理 dealException()
	 * @param msg
	 */
	public CoreException(String msg){
		logger.debug("CoreException message: "+msg);
		message = msg;
	}
	/**
	 * 
	 * @param msg
	 * @param e
	 */
	public CoreException(String msg,Exception e){
		message = msg;
		exception = e;
	}
	/**
	 * 指定异常信息和级别以及具体异常构造器，默认自动调用异常处理机制，无需收到再次调用
	 * @param msg 异常信息
	 * @param level 异常级别
	 * @param e 具体异常
	 */
	public CoreException(String msg,int level,Exception e){
		this.message = msg;
		this.level = level;
		this.exception = e;
	}
	/**
	 * 指定异常编码构造器，默认自动调用异常处理机制，无需收到再次调用
	 * @param msg 异常信息
	 * @param level 异常级别
	 * @param errorCode 异常编码
	 */
	public CoreException(String msg,int level,String errorCode){
		this.message = msg;
		this.level = level;
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * 处理开机制断开标识， 默认为 false=不断开，可调用，当为 true 时手动调用和自动调用都失效
	 * @return
	 */
	public boolean isDealOff() {
		return dealOff;
	}
	/**
	 *设置处理机制断开标识，默认为false=不断开，可调用，当为 true 时手动调用和自动调用都失效
	 * @param dealOff
	 */
	public void setDealOff(boolean dealOff) {
		this.dealOff = dealOff;
	}
}
