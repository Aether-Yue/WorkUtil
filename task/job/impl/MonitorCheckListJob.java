/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import java.util.List;

import com.sinosoft.platform.core.domain.mapper.TDefCodeDictMapper;
import com.sinosoft.platform.core.domain.model.TDefCodeDict;
import com.sinosoft.platform.core.domain.model.TDefCodeDictExample;
import com.sinosoft.ummp.application.EmailSinoService;
import com.sinosoft.ummp.domain.mapper.TDefMonitorMapper;
import com.sinosoft.ummp.domain.model.TDefMonitor;
import com.sinosoft.ummp.domain.model.TDefMonitorExample;
import com.sinosoft.ummp.task.SuperJob;

/**
 * ICP监控邮件通知批处理，用以发送ICP监控事件包
 * @author YEL
 * @date 2018-1-29 
 * @company SINOSOFT
 */
public class MonitorCheckListJob extends SuperJob {
	
	/**
	 * 
	 */
	public MonitorCheckListJob() {
		this.setJobGroup("JOB_MONITOR");
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		// Auto-generated method stub
		System.out.println("========================邮件批处理开始执行=========================");
		
		EmailSinoService service = (EmailSinoService)context.getBean(EmailSinoService.class);
		TDefMonitorMapper tDefMonitorMapper = (TDefMonitorMapper)context.getBean(TDefMonitorMapper.class);
		TDefCodeDictMapper tDefCodeDictMapper = context.getBean(TDefCodeDictMapper.class);
		String monitorCodeString = null;
//		List<TDefMonitor> list = tDefMonitorMapper.selectUsableMonitors();
		
		TDefCodeDictExample exampleCodeDict = new TDefCodeDictExample();
		TDefCodeDictExample.Criteria criteria = exampleCodeDict.createCriteria();
		criteria.andCodeTypeEqualTo("ICP_SEND_EMAIL");
		criteria.andEsStatusEqualTo(Short.parseShort("1"));
		List<TDefCodeDict> codes = tDefCodeDictMapper.selectByExample(exampleCodeDict);
		for (TDefCodeDict tDefCodeDict : codes) {
			System.out.println("ICP_SEND_EMAIL = " + tDefCodeDict.getCode());
			monitorCodeString = tDefCodeDict.getCode();
		}
		
		// String monitorCodeString = "ICP_checkList";
		TDefMonitorExample example = new TDefMonitorExample();
		example.createCriteria().andMonitorCodeEqualTo(monitorCodeString);
		List<TDefMonitor> list = tDefMonitorMapper.selectByExample(example);
				
		if(list == null || list.size() <= 0){
			System.out.println("========================邮件批处理无对应监控事件");
			return true;
		}
		for (TDefMonitor tDefMonitor : list) {
			try {
				service.sendEmail(tDefMonitor); 
			} catch (Exception e) {
				throw e;
			}finally{
				System.out.println("========================邮件批处理执行完毕=========================");	
			}
		}
		
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
