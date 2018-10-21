package com.sinosoft.ummp.task.job.impl;

/*****************************************************
 * HISTORY
 * 
 * 2018/01/05 YEL 创建文件
 * 
 * ----- 增加JOB类
 * 
 * 北京健康险报送批处理-承保信息同步上传
 *****************************************************/
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.exception.CISExceptionRunable;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * 北京健康险报送批处理
 * 
 * @author YEL
 * @date 2017-12-05
 * @company SINOSOFT
 */
@Service
public class BJHISubmitDataJob extends SuperJob implements SubmitJob {

	String[] busstypes = new String[] { "GetPolicyInfo", "GetEndorsementInfo", "GetClaimInfo"};

	public BJHISubmitDataJob() {
		// 设置为报送群组
		this.setJobGroup("JOB_SUBMIT");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		// 获取前一天
		String startDate = calDate(getCurrentDate(), -1, "D");
		String endDate = calDate(getCurrentDate(), -1, "D");
		return submit(startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.ummp.task.job.SubmitJob#submit(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean submit(String startDate, String endDate)
			throws CISException, Exception {
		System.out.println("=================北京健康险统一报送批处理-同步上传开始 =================");
		for (String busstype : busstypes) {
			System.out.println("=================北京健康险统一报送批处理-" + busstype + "-同步上传开始 =================");
			// 获取平台
			try {
				String bussNo = "";
				boolean result = submit(
						new BigDecimal(UMMPContext.SUBMIT_PLAT_BJHI),
						busstype, 
						startDate, 
						endDate, 
						bussNo);
				System.out.println("- current extract = " + busstype
						+ " and result : " + result);
			} catch (Exception e) {
				String errorMsg = "北京健康险业务类型：" + busstype + "执行失败！<br>";
				System.out.println(errorMsg);
				CISException cis = new CISException(errorMsg + e.getMessage(),
						CISExceptionRunable.EXCEPTION_ERROR_L5);
				cis.setException(e);
				throw cis;
			} finally {
				System.out.println("=================北京健康险统一报送批处理-" + busstype + "-同步上传结束 =================");
			}
		}
		System.out.println("=================北京健康险统一报送批处理-同步上传结束 =================");
		return true;

	}
}
