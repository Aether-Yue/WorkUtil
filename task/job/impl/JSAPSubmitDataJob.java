/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.exception.CISExceptionRunable;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;

import config.Config;

/**
 * 江苏中介报送批处理:<br>
 * 中介机构校验_承保:C01CB 中介机构校验_保全:C01BQ <br>
 * 
 * @author ZY
 * @date 2017-12-21 上午10:50:00
 * @company SINOSOFT
 */
@Service
public class JSAPSubmitDataJob extends SuperJob implements SubmitJob {

	String[] busstypes = new String[] {
			"C01CB", "C01BQ", "C03", "C05", "C07"
			};

	public JSAPSubmitDataJob() {
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
		// TODO 获取前一天数据
		int interval = Integer.parseInt(
				Config.getProperties().getProperty(
						"PRP_INTERVAL"));
		String startDate = calDate(getCurrentDate(), interval, "D");
		String endDate = calDate(getCurrentDate(), interval, "D");
		return submit(startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.ummp.task.job.SubmitJob#submit(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean submit(String startDate, String endDate) throws Exception {
		System.out.println("================= 江苏中介统一报送批处理开始 =================");
		for (String busstype : busstypes) {
			try {
				System.out.println("================= 江苏中介报送-" + busstype + "-批处理开始 =================");
				String bussNo = "";
				boolean result = submit(
						new BigDecimal(UMMPContext.SUBMIT_PLAT_JSAP),
						busstype, 
						startDate,
						endDate, 
						bussNo);
				System.out.println("- current extract = " + busstype + " and result : " + result);
			} catch (Exception e) {
				String errorMsg = "江苏中介业务类型：" + busstype + "执行失败！<br>";
				System.out.println(errorMsg);
				CISException cis = new CISException(errorMsg + e.getMessage(),
						CISExceptionRunable.EXCEPTION_ERROR_L5);
				cis.setException(e);
				throw cis;
			} finally {
				System.out.println("================= 江苏中介数据报送-" + busstype + "-批处理结束 =================");
			}
		}
		System.out.println("================= 江苏中介统一报送批处理结束 =================");
		return true;
	}
}
