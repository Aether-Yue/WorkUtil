package com.sinosoft.platform.quartz.application;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerUtils {

	public static Scheduler getScheduler() throws SchedulerException{
		
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		 return scheduler;
	}
	
	public static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = getScheduler();
		JobDetail jobDetail = scheduler.getJobDetail("simpleService_domain", "DEFAULT");
		System.out.println(jobDetail);
	}
}
