/**
 * 
 */
package com.sinosoft.ummp.task.job;


/**
 * 平台业务提数JOB统一接口
 * @author Darker.Wang
 * @date 2016-8-11 上午11:11:59
 * @company SINOSOFT
 */
public interface ExtractJob {

	/**
	 * 提数JOB实现方法入口
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception 
	 */
	public boolean extract(String startDate,String endDate) throws Exception;

}
