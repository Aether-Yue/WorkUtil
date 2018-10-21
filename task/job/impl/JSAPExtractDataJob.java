package com.sinosoft.ummp.task.job.impl;
/*****************************************************
 * HISTORY
 * 
 * 2017/12/21 ZY 创建文件
 * 
 * ----- 增加JOB类
 * 
 * 江苏中介数据提取批处理
 *****************************************************/
import java.math.BigDecimal;

import com.sinosoft.platform.core.spring.DBContextHolder;
import com.sinosoft.ummp.application.ExtractService;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * 江苏中介数据提取批处理
 * @author ZY
 * @date 2017-12-21
 * @company SINOSOFT
 */
public class JSAPExtractDataJob extends SuperJob{
   
	ExtractService service = null;
	public JSAPExtractDataJob(){
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
		System.out.println("================= 江苏中介数据提取批处理开始 =================");
		try{
			BigDecimal comId = new BigDecimal(UMMPContext.SUBMIT_PLAT_JSAP);
			boolean result = extract(comId);
			System.out.println("- 江苏中介数据提取批处理结果："+result);
		}catch(Exception e){
			DBContextHolder.recover();
			throw e;
		}finally{
			System.out.println("================= 江苏中介数据提取批处理结束 =================");
		} 
		return true;
	}

 }
