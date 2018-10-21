/**
 * 
 */
package com.sinosoft.ummp.task;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.sinosoft.platform.core.exception.CoreException;
import com.sinosoft.ummp.application.EmailSinoService;
import com.sinosoft.ummp.domain.mapper.TDefMonitorMapper;
import com.sinosoft.ummp.domain.model.TDefMonitor;
import com.sinosoft.ummp.domain.model.TDefMonitorExample;

/**
 * Job工作邮件通知通用类，monitorCode 必须设置，且在T_DEF_Monitor表中有对应配置
 * @author Darker.Wang
 * @date 2016-12-9 上午10:43:18
 * @company SINOSOFT
 */
public class JobNoticer {

	private ApplicationContext context;//容器上下文
	private String title;//通知标题
	private String content;//通知上下文
	/**
	 * 获取容器上下文
	 * @return
	 */
	public ApplicationContext getApplicationContext() {
		return context;
	}
	/**
	 * 设置容器上下文
	 * @param context
	 */
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}
	/**
	 * 获取通知标题
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置通知标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取通知主体内容
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置通知的主体内容，支持HTML
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 */
	public JobNoticer() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 邮件通知：
	 * @param monitorCode 事件包，在T_DEF_Monitor中配置的事件包
	 */
	public void doNotice(String monitorCode){
		EmailSinoService service = (EmailSinoService)context.getBean(EmailSinoService.class);
		TDefMonitorMapper tDefMonitorMapper = context.getBean(TDefMonitorMapper.class);
		TDefMonitorExample example = new TDefMonitorExample();
		example.createCriteria().andMonitorCodeEqualTo(monitorCode);
		List<TDefMonitor> tDefMonitors = tDefMonitorMapper.selectByExample(example);
		for(int i = 0; i < tDefMonitors.size(); i++){
			try {
				System.out.println("- 正在通知："+tDefMonitors.get(i).getMonitorCode());
				service.setTitle(title);
				service.setContent(content);
				service.setApplicationContext(context);
				service.sendEmail(tDefMonitors.get(i));
			} catch (CoreException e) {
				System.out.println("- EmailNoticer 通知异常：");
				e.printStackTrace();
			}
		}
	}
	
}
