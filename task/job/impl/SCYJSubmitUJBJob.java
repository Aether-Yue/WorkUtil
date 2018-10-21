package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * @author None
 * 四川意健险
 * 拒保批处理类
 */
@Service
public class SCYJSubmitUJBJob extends SuperJob implements SubmitJob{

	@Override
	public boolean excute() throws Exception {
		String startDate = calDate(getCurrentDate(),-1,"D");
		String endDate = calDate(getCurrentDate(),-1,"D");
		return submit(startDate,endDate);
	}

	@Override
	public boolean submit(String startDate, String endDate)
			throws CISException, Exception {
		//TODO 报送开始
		System.out.println("================= 四川意健险数据报送,UJB保单拒保T+1上传批处理开始 =================");
		try{
			String bussType = "UJB";
			String bussNo = "";
			return submit(new BigDecimal(UMMPContext.SUBMIT_PLAT_SCYJ), bussType, startDate, endDate, bussNo);
		}catch(Exception e){
			throw e;
		}finally{
			System.out.println("================= 四川意健险数据报送,UJB保单拒保T+1上传批处理结束 =================");	
		}
	}

}
