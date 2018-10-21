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
 * 上海人身险报送批处理:<br>
 * 客户上载：HIAP01 
 * 团险核保:SHIAU0101 
 * 个险核保:SHIAU0102 
 * 团险承保:SHIAN0101 
 * 个险承保:SHIAN0102 
 * 个险理赔:SHIAC01 
 * 团险理赔:SHIAC04 
 * 团险保全:SHIAE0101 
 * 个险保全:SHIAE0102 
 * 个险理赔注销:SHIAC12 
 * 团险理赔注销:SHIAC14 
 * 保费:SHIAT01 <br>
 * @author Darker.Wang
 * @date 2016-8-18 下午2:06:05
 * @company SINOSOFT
 */
@Service
public class SHIASubmitDataJob extends SuperJob implements SubmitJob {
	/**客户上载：HIAP01
	 * 团险核保:SHIAU0101
	 * 个险核保:SHIAU0102
	 * 团险承保:SHIAN0101
	 * 个险承保:SHIAN0102
	 * 个险理赔:SHIAC01
	 * 团险理赔:SHIAC04
	 * 团险保全:SHIAE0101
	 * 个险保全:SHIAE0102
	 * 个险理赔注销:SHIAC12
	 * 团险理赔注销:SHIAC14
	 * 保费:SHIAT01 **/
	String[] busstypes = new String[] {
			"SHIAP01","SHIAU0101","SHIAU0102","SHIAN0101","SHIAN0102","SHIAC01","SHIAC04","SHIAE0101","SHIAE0102","SHIAC12","SHIAC14","SHIAT01"
			};
	
	public SHIASubmitDataJob(){
		//设置为报送群组
		this.setJobGroup("JOB_SUBMIT");
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		// TODO 获取前一天数据
		int interval = Integer.parseInt(
				Config.getProperties().getProperty(
						"PRP_INTERVAL"));
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
		System.out.println("================= 上海人身险报送统一批处理开始 =================");
		for (String busstype : busstypes) {
			try{
				//获取平台
				System.out.println("================= 上海人身险数据报送-" + busstype + "-批处理开始 =================");
				String bussNo = "";
				boolean result = submit(
						new BigDecimal(UMMPContext.SUBMIT_PLAT_SHIA),
						busstype, 
						startDate, 
						endDate, 
						bussNo);
				System.out.println("- current extract = "+busstype+" and result : "+result);
			}catch(Exception e){
				String errorMsg = "上海人身险业务类型：" + busstype + "执行失败！<br>";
				System.out.println(errorMsg);
				CISException cis = new CISException(errorMsg + e.getMessage(),
						CISExceptionRunable.EXCEPTION_ERROR_L5);
				cis.setException(e);
				throw cis;
			}finally{
				System.out.println("================= 上海人身险数据报送-" + busstype + "-批处理结束 =================");	
			}
		}
		System.out.println("================= 上海人身险数据报送批处理结束 =================");	
		return true;
	}
}
