/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;

import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.exception.CISExceptionRunable;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * @author Darker.Wang
 * @date 2017-10-17 上午10:11:52
 * @company SINOSOFT
 */
public class SDPTSubmitDataJob extends SuperJob implements SubmitJob {

	String[] busstypes = new String[]{
			"C01","C02","C03","L01","L02","L03"
			};

	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.job.SubmitJob#submit(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean submit(String startDate, String endDate)
			throws CISException, Exception {
		System.out.println("================= 山东预警统一报送批处理开始 =================");
		for(String busstype : busstypes){
			//TODO 报送开始
			System.out.println("================= 山东预警数据报送,"+busstype+"批处理开始 =================");
			try{
				String bussNo = "";
				boolean result = submit(
						new BigDecimal(UMMPContext.SUBMIT_PLAT_SDPT), 
						busstype, 
						startDate, 
						endDate,
						bussNo);
				System.out.println("- current extract = " + busstype + " and result : " + result);
			}catch(Exception e){
				String errorMsg = "山东预警业务类型：" + busstype + "执行失败！<br>";
				System.out.println(errorMsg);
				CISException cis = new CISException(errorMsg + e.getMessage(),
						CISExceptionRunable.EXCEPTION_ERROR_L5);
				cis.setException(e);
				throw cis;
			}finally{
				System.out.println("================= 山东预警数据报送，"+busstype+"批处理结束 =================");	
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		// TODO 获取前一天
		String startDate = calDate(getCurrentDate(),-1,"D");
		String endDate = calDate(getCurrentDate(),-1,"D");
		return submit(startDate,endDate);
	}

}
