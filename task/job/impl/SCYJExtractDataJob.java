package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;

import com.sinosoft.platform.core.spring.DBContextHolder;
import com.sinosoft.ummp.application.ExtractService;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * @author None
 * 四川意健险数据提取批处理
 */
public class SCYJExtractDataJob extends SuperJob{
   
	ExtractService service = null;
	public SCYJExtractDataJob(){
		//设置为提数群组
		this.setJobGroup("JOB_EXTRACT");
	}
	
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		System.out.println("=================四川意健险数据提取批处理开始 =================");
		try{
			BigDecimal comId = new BigDecimal(UMMPContext.SUBMIT_PLAT_SCYJ);
			boolean result = extract(comId);
			System.out.println("- 四川意健险数据提取批处理结果："+result);
		}catch(Exception e){
			DBContextHolder.recover();
			throw e;
		}finally{
			System.out.println("================= 四川意健险数据提取批处理结束 =================");
		} 
		return true;
	}

 }
