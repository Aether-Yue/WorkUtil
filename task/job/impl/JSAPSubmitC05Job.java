package com.sinosoft.ummp.task.job.impl;
/*****************************************************
 * HISTORY
 * 
 * 2017/12/21 ZY 创建文件
 * 
 * ----- 增加JOB类
 * 
 * 江苏中介报送批处理-批单登记
 *****************************************************/
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;
 
/**
 * 江苏中介报送批处理
 * 批单登记
 * @author ZY
 * @date 2017-12-21
 * @company SINOSOFT
 */ 
@Service
public class JSAPSubmitC05Job extends SuperJob implements SubmitJob {
	
	public JSAPSubmitC05Job(){
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
		System.out.println("=================江苏中介报送批处理-批单登记上传开始=================");
		try{
			
			String bussType = "C05";
			String bussNo = "";
			return submit(new BigDecimal(UMMPContext.SUBMIT_PLAT_JSAP), bussType, startDate, endDate, bussNo);
		}catch(Exception e){
			throw e;
		}finally{
			System.out.println("=================江苏中介报送批处理-批单登记上传结束 =================");	
		}
	}
}
