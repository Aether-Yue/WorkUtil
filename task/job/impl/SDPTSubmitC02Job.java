/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;

import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * 山东预警平台：C02-保单撤销(承保/保全)
 * @author Darker.Wang
 * @date 2017-10-17 上午9:40:13
 * @company SINOSOFT
 */
public class SDPTSubmitC02Job extends SuperJob implements SubmitJob{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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

	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.job.SubmitJob#submit(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean submit(String startDate, String endDate)
			throws CISException, Exception {
		//TODO 报送开始
		System.out.println("================= 山东预警数据报送,C02-保单撤销(承保/保全)批处理开始 =================");
		try{
			String bussType = "C02";
			String bussNo = "";
			return submit(new BigDecimal(UMMPContext.SUBMIT_PLAT_SDPT), bussType, startDate, endDate, bussNo);
		}catch(Exception e){
			throw e;
		}finally{
			System.out.println("================= 山东预警数据报送，C02-保单撤销(承保/保全)批处理结束 =================");	
		}
	}

}
