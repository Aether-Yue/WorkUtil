package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.exception.CISExceptionRunable;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * 四川意健险报送批处理
 * @author None
 */
@Service
public class SCYJSubmitDataJob extends SuperJob implements SubmitJob {
	
	String[] busstypes = new String[]{"TOKEN","UCB","UBQ","UJB"};

	public SCYJSubmitDataJob(){
		//设置为报送群组
		this.setJobGroup("JOB_SUBMIT");
	}
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
		System.out.println("=================四川意健险报送统一批处理同步上传开始=================");
		for(String busstype : busstypes){
			//获取平台
			System.out.println("=================四川意健险报送批处理-" + busstype + "-信息同步上传开始=================");
			try{
				String bussNo = "";
				boolean result = submit(
						new BigDecimal(UMMPContext.SUBMIT_PLAT_SCYJ),
						busstype, 
						startDate, 
						endDate, 
						bussNo);
				System.out.println("- current extract = " + busstype + " and result : " + result);
			}catch(Exception e){
				String errorMsg = "四川意健险业务类型：" + busstype + "执行失败！<br>";
				System.out.println(errorMsg);
				CISException cis = new CISException(errorMsg + e.getMessage(),
						CISExceptionRunable.EXCEPTION_ERROR_L5);
				cis.setException(e);
			}finally{
				System.out.println("=================四川意健险报送批处理-" + busstype + "-信息同步上传结束 =================");	
			}
		}
		System.out.println("=================四川意健险报送统一批处理同步上传结束=================");
		return true;
	}
}
