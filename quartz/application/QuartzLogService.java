/**
 * 
 */
package com.sinosoft.platform.quartz.application;

import java.sql.SQLException;

import org.quartz.JobDetail;
import org.quartz.Trigger;

import com.sinosoft.platform.quartz.domain.model.QrtzTriggerLog;
import com.sinosoft.ummp.exception.CISException;

/**
 * QUARTZ 日志服务接口
 * @author Darker.Wang
 * @date 2016-7-1 下午1:48:16
 * 
 */
public interface QuartzLogService {
	
	/**
	 * 初始化获取插入Log
	 * @param id 执行ID System.CurrentTime+readom
	 * @param trigerName 名称
	 * @param jobGroup 群组
	 * @param jobName job名称
	 * @param jobDesc 描述
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @param runStatus 运行状态  默认 2- 运行中 9-执行完毕
	 * @return
	 */
	public QrtzTriggerLog insertLog(String id,String trigerName,String jobGroup,String jobName,String jobDesc,String startTime,String endTime,String runStatus);
	/**
	 * 批处理校验
	 * @param jobDetial
	 * @param trigger
	 * @return
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public boolean batchVerify(JobDetail jobDetial,Trigger trigger) throws SQLException, Exception;

	/**
	 * 插入日志
	 * @param id
	 * @param jobDetial
	 * @param trigger
	 * @param type 1-正常 0-异常
	 * @return
	 * @throws CISException 
	 */
	public boolean insertLog(Long id,JobDetail jobDetial,Trigger trigger,int type) throws Exception;
	/**
	 * 更新日志
	 * @param id
	 * @param jobDetial
	 * @param trigger
	 * @param type 1-正常 0-异常
	 * @return
	 */
	public boolean uploadLog(Long id,JobDetail jobDetial,Trigger trigger,int type);

	/**
	 * @param id
	 * @param tJobDetial
	 * @param tTrigger
	 * @param i
	 * @param string
	 * @return 
	 */
	public boolean insertLog(Long id, JobDetail tJobDetial, Trigger tTrigger,
			int i, String string);

	/**
	 * @param id
	 * @param tJobDetial
	 * @param tTrigger
	 * @param i
	 * @param string
	 */
	public boolean uploadLog(Long id, JobDetail tJobDetial, Trigger tTrigger,
			int i, String string);
	/**
	 * 根据ID修改
	 * @param log
	 * @return
	 */
	public boolean uploadLog(QrtzTriggerLog log);
}
