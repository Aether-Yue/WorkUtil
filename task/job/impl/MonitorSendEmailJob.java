/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import java.util.List;

import com.sinosoft.ummp.application.EmailSinoService;
import com.sinosoft.ummp.domain.mapper.TDefMonitorMapper;
import com.sinosoft.ummp.domain.model.TDefMonitor;
import com.sinosoft.ummp.task.SuperJob;

/**
 * 邮件通知批处理，用以定时定期通知事件包
 * @author Darker.Wang
 * @date 2016-9-20 下午7:12:37
 * @company SINOSOFT
 */
public class MonitorSendEmailJob extends SuperJob {
	
	/**
	 * 
	 */
	public MonitorSendEmailJob() {
		this.setJobGroup("JOB_MONITOR");
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("========================邮件批处理开始执行=========================");
		EmailSinoService service = (EmailSinoService)context.getBean(EmailSinoService.class);
		TDefMonitorMapper tDefMonitorMapper = (TDefMonitorMapper)context.getBean(TDefMonitorMapper.class);
		List<TDefMonitor> list = tDefMonitorMapper.selectUsableMonitors();
		if(list == null || list.size() <= 0){
			System.out.println("========================邮件批处理无对应监控事件");
			return true;
		}
		try {
			for(TDefMonitor tDefMonitor : list){
				service.sendEmail(tDefMonitor);
			}
		} catch (Exception e) {
			throw e;
		}finally{
			System.out.println("========================邮件批处理执行完毕=========================");	
		}
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
