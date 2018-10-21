package com.sinosoft.ummp.task.job.impl;
/*****************************************************
 * HISTORY
 * 
 * 2017/12/05 YEL 创建文件
 * 
 * ----- 增加JOB类
 * 
 * 北京意外险数据提取批处理
 *****************************************************/
import java.math.BigDecimal;

import com.sinosoft.platform.core.spring.DBContextHolder;
import com.sinosoft.ummp.application.ExtractService;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * 北京意外险数据提取批处理
 * @author YEL
 * @date 2017-12-05
 * @company SINOSOFT
 */
public class BJAIExtractDataJob extends SuperJob{

	ExtractService service = null;
	public BJAIExtractDataJob(){
		this.setJobGroup("JOB_EXTRACT");   //设置为提数群组
	}
	
	/**
	 * 提数，提完数日期置后：提数日期在T_DEF_CODE_DICT中配置 code_type=SYS_VAR code=START_DATE/END_DATE
	 * @throws Exception 
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		System.out.println("================= 北京意外险数据提取批处理开始 =================");
		try{
			BigDecimal comId = new BigDecimal(UMMPContext.SUBMIT_PLAT_BJAI);
			boolean result = extract(comId);
			System.out.println("- 北京意外险数据提取批处理结果："+result);
		}catch(Exception e){
			DBContextHolder.recover();
			throw e;
		}finally{
			System.out.println("================= 北京意外险数据提取批处理结束 =================");
		}
		return true;
	}

 }
