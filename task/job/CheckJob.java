/**
 * 
 */
package com.sinosoft.ummp.task.job;


/**
 * 数据校验
 * @author Darker.Wang
 * @date 2016-8-11 上午11:13:43
 * @company SINOSOFT
 */
public interface CheckJob {
	public boolean checkData(String startDate,String endDate) throws Exception;
}
