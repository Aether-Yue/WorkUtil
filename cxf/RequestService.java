/**
 * 
 */
package com.sinosoft.platform.core.spring.cxf;

/**
 * 请求类型处理接口
 * @author Darker.Wang
 * @date 2017-4-11 下午6:15:25
 * @company SINOSOFT
 */
public interface RequestService {
	public final static String encoding = "UTF-8";
	/**
	 * 处理请求
	 * @param requestType
	 * @param request
	 * @return
	 */
	public Object dealRequest(String requestType,Object reqObj);
}
