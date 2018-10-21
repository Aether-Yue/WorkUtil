package com.sinosoft.ummp.task.job.impl;
/*****************************************************
 * HISTORY
 * 
 * 2018/01/08 YEL 创建文件
 * 
 * ----- 增加JOB类
 * 
 * 广西消保数据提取批处理
 *****************************************************/
import java.math.BigDecimal;

import com.sinosoft.platform.core.spring.DBContextHolder;
import com.sinosoft.ummp.application.ExtractService;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.util.UMMPContext;
  
/**
 * 广西消保数据提取批处理
 * @author YEL 
 * @date 2018-01-08
 * @company SINOSOFT
 */
public class GXXBExtractDataJob extends SuperJob{

	ExtractService service = null;
	public GXXBExtractDataJob(){
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
		System.out.println("================= 广西消保数据提取批处理开始 =================");
		try{
			BigDecimal comId = new BigDecimal(UMMPContext.SUBMIT_PLAT_GXXB);
			boolean result = extract(comId);
			System.out.println("- 广西消保数据提取批处理结果："+result);
		}catch(Exception e){
			DBContextHolder.recover();
			throw e;
		}finally{
			System.out.println("==================== 广西消保数据提取批处理结束 =================");
		}
		return true;
	}

 }
