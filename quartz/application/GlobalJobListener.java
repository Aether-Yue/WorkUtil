package com.sinosoft.platform.quartz.application;



import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;




public class GlobalJobListener  implements JobListener {
	
	
	
	private static final Logger log = Logger.getLogger(GlobalJobListener.class);

	@Override
	public String getName() {
		
		return "GlobalJob";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
		log.debug("job执行后执行");
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
		
		log.debug("job执行前执行");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		
		if(jobException!=null){
			try {
				
				System.out.println("异常出现了!");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
