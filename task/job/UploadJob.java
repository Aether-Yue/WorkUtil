/**
 * 
 */
package com.sinosoft.ummp.task.job;

import com.sinosoft.ummp.exception.CISException;

/**
 * 文件上载接口
 * @author Darker.Wang
 * @date 2016-10-25 下午2:04:32
 * @company SINOSOFT
 */
public interface UploadJob {

	/**
	 * 文件上载批处理
	 * @param batchNo
	 * @return
	 * @throws CISException 
	 * @throws Exception 
	 */
	public boolean uploadFile() throws Exception ;

}
