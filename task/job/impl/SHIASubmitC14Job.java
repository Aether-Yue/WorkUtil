/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;

import config.Config;

/**
 * 上海人身险报送批处理:<br>
 * 团险理赔注销同步上传：SHIAC14<br>
 * @author Darker.Wang
 * @date 2016-8-18 下午2:06:05
 * @company SINOSOFT
 */
@Service
public class SHIASubmitC14Job extends SuperJob implements SubmitJob {
	
	public SHIASubmitC14Job(){
		//设置为报送群组
		this.setJobGroup("JOB_SUBMIT");
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		// TODO 获取前一天数据
		int interval = Integer.parseInt(Config.getProperties().getProperty("PRP_INTERVAL"));
		String startDate = calDate(getCurrentDate(),interval,"D");
		String endDate = calDate(getCurrentDate(),interval,"D");
		return submit(startDate,endDate);
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.job.SubmitJob#submit(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean submit(String startDate, String endDate) throws Exception {
		//获取平台
		System.out.println("================= 上海人身险数据报送批处理开始 =================");
		try{
			String bussType = "SHIAC14";
			String bussNo = "";
			boolean result = submit(new BigDecimal(UMMPContext.SUBMIT_PLAT_SHIA), bussType, startDate, endDate, bussNo);
			System.out.println("- current extract = "+bussType+" and result : "+result);
		}catch(Exception e){
			throw e;
		}finally{
			System.out.println("================= 上海人身险数据报送批处理结束 =================");	
		}
		return true;
	}
}
