package com.sinosoft.platform.core.spring.cxf.impl;

import java.util.concurrent.Callable;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.BindingType;

import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.sinosoft.platform.core.spring.cxf.FrontProcessor;
import com.sinosoft.platform.core.spring.cxf.FrontProcessorService;
/**
 * 前置机服务接口实现类
 * @author Darker.Wang
 * @date 2017-4-11 下午5:04:18
 * @company SINOSOFT
 */
@WebService
@SOAPBinding(style=Style.RPC)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class FrontProcessorImpl implements FrontProcessor,ApplicationContextAware {

	@Autowired
	LoggingOutInterceptor loggingOutInterceptor;
	@Autowired
	FrontProcessorService frontProcessorService;
	public ApplicationContext context;
	/* (non-Javadoc)
	 * @see com.sinosoft.platform.core.spring.cxf.CXFWebserviceServer#SDPTService(java.lang.String)
	 */
	@Override
	public String getResult(String sendMsg) throws Exception {
		ActionRunnable action = new ActionRunnable(sendMsg);
		return action.call().toString();
	}
	/**
	 * 内部线程处理类，可获取到返回值的线程
	 * @author Darker.Wang
	 * @date 2017-4-11 下午5:38:37
	 * @company SINOSOFT
	 */
	@SuppressWarnings("rawtypes")
	class ActionRunnable implements Callable{
		public String rtvMsg = null;
		public String sendMsg = null;
		ActionRunnable(String sendMsg){
			this.sendMsg = sendMsg;
		}
		/* (non-Javadoc)
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public Object call() throws Exception {
			// TODO 执行前置机服务
			//frontProcessorService.setApplicationContext(context);
			System.out.println("\n- +++++++ FrontProcessorService is doing +++++++");
			rtvMsg = frontProcessorService.doAction(sendMsg);
			if(rtvMsg == null){
				System.out.println("- FrontProcessor Deal Wrong : Cause maybe there no implementor of FrontProcessorService!");
			}
			System.out.println("- +++++++ FrontProcessorService was down +++++++");
			return rtvMsg;
		}
	}
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.context = arg0;
	}

}
