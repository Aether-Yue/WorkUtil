/**
 * 
 */
package com.sinosoft.platform.core.spring.cxf;

import org.springframework.context.ApplicationContext;

/**
 * 前置机服务处理接口
 * @author Darker.Wang
 * @date 2017-4-11 下午5:18:10
 * @company SINOSOFT
 */
public interface FrontProcessorService {
	/**
	 * 字符型处理
	 * @param sendXml
	 * @return
	 * @throws Exception 
	 */
	public String doAction(String sendXml) throws Exception;

	/**
	 * @param context
	 */
	void setApplicationContext(ApplicationContext context);

	/**
	 * @return
	 */
	ApplicationContext getApplicationContext();
	
}
