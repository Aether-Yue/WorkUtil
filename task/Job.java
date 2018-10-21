package com.sinosoft.ummp.task;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;

import com.sinosoft.platform.core.application.JobLogService;
import com.sinosoft.platform.core.application.SequenceService;
import com.sinosoft.platform.core.domain.model.TDefJobLog;
import com.sinosoft.platform.core.util.Constants;
import com.sinosoft.platform.core.util.DateUtils;
import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.exception.CISExceptionRunable;

/**
 * <p>Title：调用批处理类</p>
 * <p>Description：根据传入的类名调用不同的批处理类（必传）</p>
 * @author Darker.Wang
 * @date 2016-8-16 下午6:22:38
 * @company SINOSOFT
 * @version 1.0
 */
public class Job {
	private static ApplicationContext context;
	/**
	 * 执行MAIN方法
	 * @param args
	 */
	public static void main(String[] args) {
		doJob(args);
	}
	/**
	 * 执行JOB，开放接口,此处返回不代表批处理成功还是失败，只代表执行完毕与否 1-是 0-否
	 * @param args
	 */
    public static int doJob(String[] args){
    	/**
		 * 目前支持参数：第一1个 类名，暂不支持其他
		 */
    	String title;
    	String content;
		int result = 0;
		String className = args[0];
		try {
			result = interfaces(className);
		}catch (CISException e) {
			// TODO CISException 处理
			e.printStackTrace();
			System.out.println(className+" 执行异常 ："+DateUtils.getCurrentDateAndTime());
			System.out.println("- CISException : "+e.getMessage());
		    title = "【ICP 邮件提醒】自动提示: SHELL "+className+" 执行失败";
			content = "<br> 批处理："+className+" 执行完毕，执行结果："+result+" 请进行相应的检查。<br> 异常信息：<br>"+e.getMessage()+" <br>";
			errorNotice(title,content);
			result = 1;//衡返回
			return result;
		}catch (SQLException e){
			// TODO SQLException 处理
			e.printStackTrace();
			System.out.println(className+" SQL异常 ："+DateUtils.getCurrentDateAndTime());
			System.out.println("- SQLException : "+e.getMessage());
			title = "【ICP 邮件提醒】自动提示: SHELL "+className+" 执行失败";
			content = "<br> 批处理："+className+" 执行异常，执行结果："+result+" 请进行相应的检查。<br> 异常信息：<br>"+e.getMessage()+" <br>";
			errorNotice(title,content);
			result = 1;//衡返回
			return result;
		} catch (Exception e) {
			// TODO Exception 处理
			e.printStackTrace();
			System.out.println(className+" 提数异常 ："+DateUtils.getCurrentDateAndTime());
			System.out.println("- Exception : "+e.getMessage());
			title = "【ICP 邮件提醒】自动提示: SHELL "+className+" 执行异常";
			content = "<br> 批处理："+className+" 执行异常，执行结果："+result+" 请进行相应的检查。<br> 异常信息：<br>"+e.getMessage()+" <br>";
			errorNotice(title,content);
			result = 1;//衡返回
			return result;
		}finally{
			System.gc();//唤醒JVM垃圾回收
			showResult(className,result);
		}
		return 1;
    }
    /**
     * 显示执行结果
     * @param jobName
     * @param result
     */
    private static void showResult(String jobName,int result){
    	//TODO 显示执行结果
    	switch(result){
    		case 1 :
    			System.out.println("批处理："+jobName+" 执行成功：\n"+result);
    		break;
    		case 0 :
    			System.out.println("批处理："+jobName+" 执行失败：\n"+result);
    		break;
    		case -1 :
    			System.out.println("批处理："+jobName+" 执行异常：\n"+result);
    		break;
    		default:
    			System.out.println("批处理："+jobName+" 返回越界：\n"+result);
    	}
    }
	/**
	 * job接口,1-成功  0-失败 [非0即true 原则]
	 * @param className
	 * @throws CISException
	 */
	protected static int interfaces(String className) throws CISException,SQLException,Exception{
		int result = 0;
        System.out.println("- JobClassName : "+className);
        TDefJobLog jobLog = null;
        JobLogService jobLogService = null ;
        SuperJob newInstance;
		try {
			//TODO 获取执行JOB对象
			newInstance = newInstance(className);
		}catch (ClassNotFoundException e) {
			CISException cis = new CISException("初始化SupperJob异常，找不到类："+className);
			cis.setLevel(CISExceptionRunable.EXCEPTION_ERROR_L3);
			cis.setException(e);
			throw cis;
		}catch (Exception e) {
			CISException cis = new CISException("初始化SupperJob异常："+e.getMessage());
			cis.setLevel(CISExceptionRunable.EXCEPTION_ERROR_L3);
			cis.setMessage(e.getMessage());
			cis.setException(e);
			throw cis;
		}
        try {
        	context = newInstance.context;
			//TODO 1、构造插入批处理日志：
			jobLogService = (JobLogService) newInstance.context.getBean("jobLogService");
			SequenceService sequenceService = (SequenceService) newInstance.context.getBean("sequenceService");
			BigDecimal id = sequenceService.getBigSeqValByName(JobLogService.JOB_LOG_SEQ);
			System.out.println("- Job Log ID : "+id);
			String jobGroup = newInstance.getJobGroup();
			jobLog = buildJobLog(jobGroup,className,DateUtils.getCurrentTime(),null,"2","正在执行...");
			jobLog.setId(id);
			jobLogService.insertJobLog(jobLog);
			//TODO 2、执行JOB
			if(newInstance.excute()){//成功返回0
				jobLog.setEndTime(DateUtils.getCurrentTime());
				jobLog.setJobStatus("9");
				jobLog.setRunMsg("执行成功");
				result = 1;
			}else{
				jobLog.setEndTime(DateUtils.getCurrentTime());
				jobLog.setJobStatus("4");
				jobLog.setRunMsg("执行失败");
				result = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "执行JOB异常: "+e.getMessage();
			jobLog.setEndTime(DateUtils.getCurrentTime());
			jobLog.setJobStatus("3");
			jobLog.setRunMsg(msg);
			CISException cis = new CISException(msg);
			cis.setLevel(CISExceptionRunable.EXCEPTION_ERROR_L3);
			cis.setException(e);
			throw cis;
		}
        finally{
			if(jobLogService != null){
				jobLog.setLastModifyTime(DateUtils.getCurrentTimestamp());
				jobLogService.updateJobLog(jobLog);
			}else{
				System.out.println(className+"执行失败，原因: SPRING 中获取容器 JobLogService 失败！");
				result = -1;
			}
			if(newInstance != null){
				newInstance.destroy();
			}
		}
        return result;
	}
	/**
	 * 获取执行对象
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static SuperJob newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException{
		String tClassName = "com.sinosoft.ummp.task.job.impl."+className;
    	System.out.println("- JobClassNameDetail : "+tClassName);
		Class<?> c = Class.forName(tClassName);
		SuperJob newInstance = (SuperJob) c.newInstance();
		System.out.println("- LoginInfo: "+newInstance.loginInfo.getUserCode());
		System.out.println("- GenericXmlApplicationContext: "+newInstance.context);
		return newInstance;
	}
	/**
	 * 获取JOB壳子，不包含ID
	 * @param className
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	private static TDefJobLog buildJobLog(String jobGroup,String className,String startTime,String endTime,String status,String runMsg){
		TDefJobLog jobLog = new TDefJobLog();
		jobLog.setJobName(className.toUpperCase());
		jobLog.setJobCode(className);
		jobLog.setJobGroup(jobGroup);
		jobLog.setJobStatus(status);
		jobLog.setRunMsg(runMsg);
		jobLog.setStartTime(startTime);
		jobLog.setEndTime(endTime);
		jobLog.setJobType("SHELL");
		jobLog.setOperator(Constants.JOB_USER);
		jobLog.setInsertTime(DateUtils.getCurrentTimestamp());
		jobLog.setLastModifyTime(DateUtils.getCurrentTimestamp());
		return jobLog;
	}
	/**
	 * JOB执行异常通知
	 * @param title
	 * @param content
	 */
	public static void errorNotice(String title,String content){
		System.out.println("- JOB 错误通知开始："+DateUtils.getCurrentDateTime());
		JobNoticer jobNoticer = new JobNoticer();
		jobNoticer.setTitle(title);
		jobNoticer.setContent(content);
		jobNoticer.setApplicationContext(context);
		jobNoticer.doNotice("SHELL_EXCEPTION");//固定的异常，如若没有则不会发送邮件，若需要监控，则在监控管理中配置名称为其的事件包即可
		System.out.println("- JOB 错误通知结束："+DateUtils.getCurrentDateTime());
	}
}
