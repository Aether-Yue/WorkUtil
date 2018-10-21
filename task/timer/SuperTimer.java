/**
 * 
 */
package com.sinosoft.ummp.task.timer;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.sinosoft.platform.core.exception.CoreException;
import com.sinosoft.platform.core.util.DateUtils;
import com.sinosoft.platform.core.util.NumberUtils;
import com.sinosoft.platform.quartz.application.QuartzLogService;
import com.sinosoft.platform.quartz.domain.model.QrtzTriggerLog;
import com.sinosoft.ummp.exception.CISException;

/**
 * @author Darker.Wang
 * @date 2016-12-17 下午2:21:37
 * @company SINOSOFT
 */
public abstract class SuperTimer extends TimerTask{

	@Autowired
	QuartzLogService quartzLogService;
	protected String timerDesc;
	public String getTimerDesc(){
		return timerDesc;
	}
	/**
	 * 自定义设置TIMER描述
	 * @param timerDesc
	 */
	public abstract void setTimerDesc(String timerDesc);
	//自定义上下文
	private ApplicationContext applicationContext;
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	public abstract boolean excute() throws Exception;
	/**
	 * 初始化函数，也可做一些判断等
	 * @throws CoreException
	 */
	private void init() throws CoreException{
		if(quartzLogService == null){
			if(applicationContext == null){
				CoreException core = new CoreException();
				core.setClazz(this.getClass().getName());
				core.setMessage("application init false or not a selfdef");
				throw core;
			}
			quartzLogService = applicationContext.getBean(QuartzLogService.class);
		}
	}
	 /** 
	 * @param id 执行ID System.CurrentTime+readom
	 * @param trigerName 名称
	 * @param jobGroup 群组
	 * @param jobName job名称
	 * @param jobDesc 描述
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @param runStatus 运行状态  默认 2- 运行中 9-执行完毕
	 **/
	@Override
	// 定时器入口，run()里面是你的任务作业
	public void run() {
		
		String id = DateUtils.getCurrentTime("yyyyMMddHHmmssSSS")+"-"+NumberUtils.getRandom(4);
		String className = this.getClass().getName();
		String jobName = className;
		if(className.lastIndexOf(".") > 0){
			jobName = className.substring(className.lastIndexOf(".")+1);
		}
		String startTime = DateUtils.getCurrentDateTime();
		String logMsg = "执行成功";
		if(timerDesc == null || "".equals(timerDesc)){
			timerDesc = "系统TIMER执行";
		}
		QrtzTriggerLog log = quartzLogService.insertLog(id, className,"DEFAULT",jobName,timerDesc,startTime,null,"2");
		boolean result = false;
		System.out.println("- TIMER "+className + "  执行定时器开始 ...........................");
		
		try {
			init();
			System.out.println("- TIMER " + className + " 执行中 ... ");
			result = excute();
			System.out.println("- TIMER " + className + " 执行结果：" + result);
		}catch (CoreException e) {
			// TODO 异常邮件通知
			e.printStackTrace();
			logMsg = "执行异常: CoreException";
			log.setLogFlag(Short.parseShort("0"));
			System.out.println("- TIMER " + className + " 执行异常：" + e.getMessage());
		}catch (CISException e) {
			// TODO 异常邮件通知
			e.printStackTrace();
			logMsg = "执行异常: CISException";
			log.setLogFlag(Short.parseShort("0"));
			System.out.println("- TIMER " + className + " 执行异常：" + e.getMessage());
		}catch (Exception e) {
			// TODO 异常邮件通知
			e.printStackTrace();
			logMsg = "执行异常: Exception";
			log.setLogFlag(Short.parseShort("0"));
			System.out.println("- TIMER " + className + " 执行异常：" + e.getMessage());
		}finally{
			log.setEndTime(DateUtils.getCurrentDateTime());
			log.setLastModifyTime(DateUtils.getCurrentDateTime());
			log.setLogMsg(logMsg);
			log.setRunStatus(Short.parseShort("9"));
			if(result){
				log.setLogFlag(Short.parseShort("1"));
			}
			quartzLogService.uploadLog(log);
			System.out.println("- TIMER "+className + "  执行定时器完毕 =======================");
		}
		
	}
	/**
	 * 
	 */
	public SuperTimer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
