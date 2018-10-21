package com.sinosoft.platform.quartz.application;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.CronExpression;

import com.sinosoft.platform.core.exception.CoreException;
import com.sinosoft.platform.core.util.DataGridModel;
import com.sinosoft.platform.core.util.LoginInfo;
import com.sinosoft.platform.quartz.domain.model.JobDetails;
import com.sinosoft.platform.quartz.domain.model.Tcschedulerallocate;

/**
 * 简单任务批处理服接口
 * @author Darker.Wang
 * @date 2016-10-10 下午3:28:48
 * @company SINOSOFT
 */
public interface SchedulerService {
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param cronExpression
	 *            Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 */
	void schedule(String cronExpression, String jobName) throws Exception;

	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param name
	 *            Quartz CronTrigger名称
	 * @param cronExpression
	 *            Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 */
	void schedule(String name, String cronExpression, String jobName) throws Exception;

	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param name
	 *            Quartz CronTrigger名称
	 * @param cronExpression
	 *            Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 * @param group
	 *            Quartz CronExpression Group
	 */
	void schedule(String name, String cronExpression, String group, String jobName) throws Exception;
	/**
	 * 增加计划描述
	 * @param name
	 * @param cronExpression
	 * @param group
	 * @param jobName
	 * @param tempPlanToDescribe
	 * @throws Exception
	 */
	void schedule(String name, String cronExpression, String group, String jobName,String tempPlanToDescribe) throws Exception;
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param cronExpression
	 *            Quartz CronExpression
	 */
	void schedule(CronExpression cronExpression, String jobName) throws Exception;

	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param name
	 *            Quartz CronTrigger名称
	 * @param cronExpression
	 *            Quartz CronExpression
	 */
	void schedule(String name, CronExpression cronExpression, String jobName) throws Exception;

	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param name
	 *            Quartz CronTrigger名称
	 * @param cronExpression
	 *            Quartz CronExpression
	 * @param group
	 *            Quartz CronExpression Group
	 */
	void schedule(String name, CronExpression cronExpression, String group, String jobName) throws Exception;
	

	/**
	 * 在startTime时执行调试一次
	 * 
	 * @param startTime
	 *            调度开始时间
	 */
	void schedule(Date startTime, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试一次
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(Date startTime, String group, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试一次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 */
	void schedule(String name, Date startTime, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试一次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(String name, Date startTime, String group, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 */
	void schedule(Date startTime, Date endTime, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(Date startTime, Date endTime, String group, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 */
	void schedule(String name, Date startTime, Date endTime, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(String name, Date startTime, Date endTime, String group, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 */
	void schedule(Date startTime, Date endTime, int repeatCount, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(Date startTime, Date endTime, int repeatCount, String group, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 */
	void schedule(String name, Date startTime, Date endTime, int repeatCount, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(String name, Date startTime, Date endTime, int repeatCount, String group, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param repeatInterval
	 *            执行时间隔间
	 */
	void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param repeatInterval
	 *            执行时间隔间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 * @author planDesc
	 *            计划描述  
	 */
	void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval, String group, String jobName) throws Exception;

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param repeatInterval
	 *            执行时间隔间
 	 * @author planDesc
	 *            计划描述  
	 */
	void schedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, String jobName,String planDesc) throws Exception;
	/**
	 * 
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param repeatCount
	 * @param repeatInterval
	 * @param jobName
	 * @param planDesc
	 * @param map
	 * @throws CoreException
	 */
	@SuppressWarnings("rawtypes")
	void schedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, String jobName,String planDesc,Map map) throws CoreException;
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param repeatInterval
	 *            执行时间隔间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, String group, String jobName,String planToDescribe,int delayRunTime,int trydelayoperationcount,List<BigDecimal> sqlIds) throws Exception;
	/**
	 * Trigger 参数,以com.sundoctor.example.Constant常量为键封装的Map
	 * 
	 * @param map
	 */
	void schedule(Map<String, Object> filterMap) throws Exception;

	/**
	 * 取得所有调度Triggers
	 * 
	 * @return
	 */
	Map<String, Object> getPageList(DataGridModel dgm, String jobName, String trigger_name) throws Exception;
	
	/**
	 * 取得所有任务
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List queryJob() throws Exception;

	/**
	 * 根据名称和组别暂停Tigger
	 * 
	 * @param triggerName
	 * @param group
	 */
	void pauseTrigger(String triggerName, String group) throws Exception;

	/**
	 * 恢复Trigger
	 * 
	 * @param triggerName
	 * @param group
	 */
	void resumeTrigger(String triggerName, String group) throws Exception;

	/**
	 * 删除Trigger
	 * 
	 * @param triggerName
	 * @param group
	 */
	boolean removeTrigger(String triggerName, String group) throws Exception;

	/**
	 * 查询基本任务信息列表
	 * 
	 */
	Map<String, Object> getPageList(DataGridModel dgm, JobDetails  jobDetails ) throws Exception;
	
	/**
	 * 
	 * 查询基本调度关系信息列表
	 */
	Map<String, Object> getPageList(DataGridModel dgm, Tcschedulerallocate tcschedulerallocate ) throws Exception;
		
	/**
	 * 
	 * 任务名称下拉查询
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List queryJobTnameCombox() throws Exception;
	
   /**
    * 新增基本任务
    * 
    */
   void addBaseTask(JobDetails jobdetails) throws Exception;
   
   /**
    * 新增基本任务
    * 
    */
   void addSchedulerAllocate(Tcschedulerallocate tcschedulerallocate) throws Exception;
   
   
	/**
	 * 根据任务名称下拉选择任务的相应处理方法
	 * @param jobtname
	 * @return
	 */
   
   public JobDetails queryJobNameByTname(String jobtname) throws Exception;

    /**
     * 获取描述信息
     * @param jobName
     */
	String getDescInfo(String jobName) throws Exception;

	/**
	 * 保存前置校验算法配置信息
	 * @param map
	 * @param flag 
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	boolean saveVerifyInfo(Map map,String flag, LoginInfo loginInfo) throws Exception;

	/**
	 * 分页查询
	 * @param dgm
	 * @param searchParam
	 */
	Map<String,Object> getDataSqlCodeList(DataGridModel dgm, String searchParam) throws Exception;

	/**
	 * 删除
	 * @param id
	 */
	void deleteGetDataSqlCode(BigDecimal id) throws Exception;

	/**
	 * 根据id查询用于回显页面
	 * @param id
	 * @return
	 */
	Map<String,Object> findGetDataSqlCodeById(BigDecimal id) throws Exception;
	/**
	 * 保存更新后的信息	
	 */
	void saveUpdateVerifyInfo(Map<String, Object> map, LoginInfo loginInfo) throws Exception;

	/**
	 * 手动执行批处理 查询校验列表
	 * @param dgm 
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	Map<String, Object> getVerifyList(DataGridModel dgm) throws SQLException, IOException , Exception;

	/**
	 * 查询存在的job
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List queryExistJob() throws Exception;
	/**
	 * 查询监控列表信息
	 * @param dgm
	 * @return
	 */
	Map<String, Object> queryMonitoringList(DataGridModel dgm) throws Exception;
	
	/**
	 * 分页查询批处理日志
	 * @param dgm
	 * @param param
	 * @return
	 */
	Map<String, Object> queryBatchLogList(DataGridModel dgm, String param) throws Exception;

	/**
	 * 删除执行日志
	 * @param logWork
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int deleteTriggerLogs(String logWork,String startTime, String endTime);
	/**
	 * 删除执行日志
	 * @param id
	 * @return
	 */
	int deleteTriggerLog(String id);
}
