/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;

import com.sinosoft.platform.core.spring.DBContextHolder;
import com.sinosoft.ummp.application.ExtractService;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * 保单登记数据提取批处理
 * @author Darker.Wang
 * @date 2016-8-18 下午2:06:37
 * @company SINOSOFT
 */
public class PRPExtractDataJob extends SuperJob{

	ExtractService service = null;
	public PRPExtractDataJob(){
		//设置为提数群组
		this.setJobGroup("JOB_EXTRACT");
	}
	
	/**
	 * 提数，提完数日期置后：提数日期在T_DEF_CODE_DICT中配置 code_type=SYS_VAR code=START_DATE/END_DATE
	 * @throws Exception 
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		System.out.println("================= 保单登记数据提取批处理开始 =================");
		try{
			BigDecimal comId = new BigDecimal(UMMPContext.SUBMIT_PLAT_PRP);
			boolean result = extract(comId);
			System.out.println("- 保单登记数据提取批处理结果："+result);
		}catch(Exception e){
			DBContextHolder.recover();
			throw e;
		}finally{
			System.out.println("================= 保单登记数据提取批处理结束 =================");
		}
		return true;
	}

 }
