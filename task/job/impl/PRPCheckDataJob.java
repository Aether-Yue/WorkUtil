/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import org.springframework.stereotype.Service;

import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.CheckJob;

import config.Config;

/**
 * 保单登记数据校验
 * @author Darker.Wang
 * @date 2016-8-11 下午8:30:21
 * @company SINOSOFT
 */
@Service
public class PRPCheckDataJob extends SuperJob implements CheckJob{
	/**
	 * 每个批处理JOB必须在构造器中申明群组，并且赋值上这条信息
	 */
	public PRPCheckDataJob() {
		// TODO Auto-generated constructor stub
		//申明群组：如有必要，一组JOB必须声明相同的群组
		this.setJobGroup("JOB_CHECK");
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		int interval = Integer.parseInt(Config.getProperties().getProperty("PRP_INTERVAL"));
		String startDate = calDate(getCurrentDate(),interval,"D");
		String endDate = calDate(getCurrentDate(),interval,"D");
		return checkData(startDate,endDate);
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.job.CheckJob#checkData(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkData(String startDate, String endDate) {
		// TODO Auto-generated method stub
		System.out.println("================= 保单登记数据校验批处理开始 =================");
		System.out.println("================= StartDate: "+startDate+" endDate: "+endDate+" =================");
		System.out.println("================= 保单登记数据校验批处理开始 =================");
		return true;
	}
}
