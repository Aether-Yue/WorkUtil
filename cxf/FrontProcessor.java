package com.sinosoft.platform.core.spring.cxf;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.BindingType;
/**
 * 前置机服务接口
 * @author Darker.Wang
 * @date 2017-4-11 下午5:04:38
 * @company SINOSOFT
 */
@WebService
@SOAPBinding(style=Style.RPC)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public interface FrontProcessor {
	public static final String XML_ENCODE = "UTF-8";
	public static final double XML_VERSION = 1.0;
	/**
	 * 请求并获取结果
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public String getResult(String xml) throws Exception;
}
