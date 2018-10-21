package com.sinosoft.platform.quartz.application;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
/**
 * 简单任务实现类：实现简单批处理操作可在该类中写一个方法在quartz.xml 中配置即可
 * @author Darker.Wang
 * @date 2016-10-10 下午3:30:12
 * @company SINOSOFT
 */
@Service("simpleService")
public class SimpleService implements Serializable,ApplicationContextAware  {

	
	private  static ApplicationContext context = null;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SimpleService.class);
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
		logger.debug("- ApplicationContext: "+context);
	}
	
	public boolean testSimpleServiceMethod(){
		System.out.println("- testSimpleServiceMethod : 测试简单JOB开始了");
		return true;
	}

}
