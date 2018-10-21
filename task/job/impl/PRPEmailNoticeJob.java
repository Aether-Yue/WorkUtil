/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import com.sinosoft.ummp.task.JobNoticer;
import com.sinosoft.ummp.task.SuperJob;

/**
 * @author Darker.Wang
 * @date 2016-12-10 下午3:49:54
 * @company SINOSOFT
 */
public class PRPEmailNoticeJob extends SuperJob {

	/**
	 * 
	 */
	public PRPEmailNoticeJob() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		// TODO 邮件通知
		JobNoticer notice = new JobNoticer();
		notice.setApplicationContext(context);
		notice.setContent("<br>统一监管这个时间点的批处理执行异常了，请注意查看处理！<br>");
		notice.setTitle("【ICP 邮件提醒】 SHELL_EXCEPTION");
		notice.doNotice("SHELL_EXCEPTION");
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
