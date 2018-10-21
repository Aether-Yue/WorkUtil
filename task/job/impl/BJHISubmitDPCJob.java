package com.sinosoft.ummp.task.job.impl;
/*****************************************************
 * HISTORY
 * 
 * 2017/12/05 YEL 创建文件
 * 
 * ----- 增加JOB类
 * 
 * 北京健康险报送批处理-承保信息同步上传
 *****************************************************/
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * 北京健康险报送批处理
 * 承保信息同步上传
 * @author YEL
 * @date 2017-12-05
 * @company SINOSOFT
 */
@Service
public class BJHISubmitDPCJob extends SuperJob implements SubmitJob {
	
	public BJHISubmitDPCJob(){
		//设置为报送群组
		this.setJobGroup("JOB_SUBMIT");
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		//获取前一天
		String startDate = calDate(getCurrentDate(),-1,"D");
		String endDate = calDate(getCurrentDate(),-1,"D");
		return submit(startDate,endDate);
	}
	
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.job.SubmitJob#submit(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean submit(String startDate, String endDate) throws CISException,Exception {
		//获取平台
		System.out.println("=================北京健康险报送批处理-承保信息同步上传开始 =================");
		try{
			
			String bussType = "GetPolicyInfo";
			String bussNo = "";
			return submit(new BigDecimal(UMMPContext.SUBMIT_PLAT_BJHI), bussType, startDate, endDate, bussNo);
		}catch(Exception e){
			throw e;
		}finally{
			System.out.println("=================北京健康险报送批处理-承保信息同步上传结束 =================");	
		}
	}
}
