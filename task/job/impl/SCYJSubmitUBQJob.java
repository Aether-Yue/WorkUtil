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
 * 保全批处理类
 *
 */
@Service
public class SCYJSubmitUBQJob extends SuperJob implements SubmitJob{

	public static void main(String[] args) {
		
	}

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
		System.out.println("================= 四川意健险数据报送,UBQ保全T+1上传批处理开始 =================");
		try{
			String bussType = "UBQ";
			String bussNo = "";
			return submit(new BigDecimal(UMMPContext.SUBMIT_PLAT_SCYJ), bussType, startDate, endDate, bussNo);
		}catch(Exception e){
			throw e;
		}finally{
			System.out.println("================= 四川意健险数据报送,UBQ保全T+1上传批处理结束 =================");	
		}
	}

}
