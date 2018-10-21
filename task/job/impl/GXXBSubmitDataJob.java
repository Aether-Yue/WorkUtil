package com.sinosoft.ummp.task.job.impl;
/*****************************************************
 * HISTORY
 * 
 * 2018/01/08 YEL 创建文件
 * 
 * ----- 增加JOB类
 * 
 * 广西消保报送批处理-数据上传 
 *****************************************************/
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.exception.CISExceptionRunable;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;
 
/**
 * 广西消保报送批处理
 * 保全信息同步上传
 * @author YEL
 * @date 2018-01-08
 * @company SINOSOFT
 */
@Service
public class GXXBSubmitDataJob extends SuperJob implements SubmitJob {
	
	String[] busstypes = new String[]{"ImportCaseinfo"};
	
	public GXXBSubmitDataJob(){
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
	
	@Override
	public boolean submit(String startDate, String endDate) throws CISException,Exception {
		System.out.println("=================广西消保统一报送批处理开始 =================");
		for(String busstype : busstypes){
			//获取平台
			System.out.println("=================广西消保报送批处理-" + busstype + "-开始 =================");
			try{
				String bussNo = "";
				boolean result = submit(
						new BigDecimal(UMMPContext.SUBMIT_PLAT_GXXB), 
						busstype, 
						startDate, 
						endDate, 
						bussNo);
				System.out.println("- current extract = " + busstype
						+ " and result : " + result);
			}catch(Exception e){
				String errorMsg = "广西消保业务类型：" + busstype + "执行失败！<br>";
				System.out.println(errorMsg);
				CISException cis = new CISException(errorMsg + e.getMessage(),
						CISExceptionRunable.EXCEPTION_ERROR_L5);
				cis.setException(e);
				throw cis;
			}finally{
				System.out.println("=================广西消保报送批处理-" + busstype + "-结束 =================");	
			}
		}
		System.out.println("=================广西消保统一报送批处理结束 =================");
		return true;
		
	}
}
