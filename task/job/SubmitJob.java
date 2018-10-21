/**
 * 
 */
package com.sinosoft.ummp.task.job;

import com.sinosoft.ummp.exception.CISException;

/**
 * 平台业务报送JOB统一接口
 * @author Darker.Wang
 * @date 2016-8-11 上午11:12:51
 * @company SINOSOFT
 */
public interface SubmitJob {
	/**
	 * 报送JOB实现方法入口
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws CISException 
	 * @throws Exception 
	 */
	public boolean submit(String startDate,String endDate) throws Exception;
}
