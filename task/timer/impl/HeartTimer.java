/**
 * 
 */
package com.sinosoft.ummp.task.timer.impl;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.sinosoft.ummp.quartz.HeartBeatService;

import config.Config;

/**
 * @author Darker.Wang
 * @date 2016-12-17 下午2:39:47
 * @company SINOSOFT
 */
@Component("heartTimer")
public class HeartTimer implements ApplicationListener<ContextRefreshedEvent>{

	ScheduledThreadPoolExecutor excutor = new ScheduledThreadPoolExecutor(5);
	private String timerDesc;
	private long initialDelay;//延迟多少秒执行
	private long period;//每个多少秒执行
	/**
	 * @param corePoolSize
	 */
	
	
	
	public HeartTimer(long period,long initialDelay) {
		System.out.println("- HeartTimer 初始化了：initialDelay="+initialDelay+" period="+period);
		this.initialDelay = initialDelay;
		this.period = period;
	}

	public HeartTimer() {
		
	}

	@Autowired
	HeartBeatService heartBeatService;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

   class ExcuteHeart implements Runnable{
		HeartBeatService hbs;
		ExcuteHeart(HeartBeatService heartBeatService){
			this.hbs = heartBeatService;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean result = false;
			String IP = Config.getAppsIP();
			setTimerDesc("心跳监控: "+IP);
			result = hbs.hearBeat();
			System.out.println("心跳监控: "+IP+" "+result);
		}
		
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.timer.SuperTimer#excute()
	 */
	public boolean excute() throws Exception {
		// TODO Auto-generated method stub
		if(heartBeatService != null && period != 0){
			excutor.scheduleAtFixedRate(new ExcuteHeart(heartBeatService), initialDelay, period, TimeUnit.MILLISECONDS);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.timer.SuperTimer#setTimerDesc(java.lang.String)
	 */
	public void setTimerDesc(String timerDesc) {
		// TODO Auto-generated method stub
		this.timerDesc = timerDesc;
	}
	public String getTimerDesc(){
		return timerDesc;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("- spring application init ");
		if(event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")){
			System.out.println("- and was Root WebApplicationContext ");
			try {
				this.excute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
