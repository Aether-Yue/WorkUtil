package com.sinosoft.platform.quartz.application.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.platform.core.application.SqlParamService;
import com.sinosoft.platform.core.domain.mapper.SqlAdapterMapper;
import com.sinosoft.platform.core.domain.mapper.TDefSqlInfoMapper;
import com.sinosoft.platform.core.domain.mapper.TDefSqlMapper;
import com.sinosoft.platform.core.domain.model.SqlAdapter;
import com.sinosoft.platform.core.util.DateUtils;
import com.sinosoft.platform.quartz.application.QuartzLogService;
import com.sinosoft.platform.quartz.application.SchedulerService;
import com.sinosoft.platform.quartz.domain.mapper.QrtzSimpleTriggersMapper;
import com.sinosoft.platform.quartz.domain.mapper.QrtzTriggerLogMapper;
import com.sinosoft.platform.quartz.domain.mapper.QrtzTriggersMapper;
import com.sinosoft.platform.quartz.domain.model.QrtzSimpleTriggers;
import com.sinosoft.platform.quartz.domain.model.QrtzSimpleTriggersExample;
import com.sinosoft.platform.quartz.domain.model.QrtzTriggerLog;
import com.sinosoft.platform.quartz.domain.model.QrtzTriggers;
import com.sinosoft.platform.quartz.domain.model.QrtzTriggersExample;
import common.Logger;
/**
 * QARTZ 批处理日志 服务实现类
 * @author Darker.Wang
 * @date 2016-7-1 下午1:49:47
 */
@Service(value="quartzLogService")
public class QuartzLogServiceImpl  implements QuartzLogService  {
	
	@Autowired
	QrtzTriggerLogMapper qrtzTriggerLogMapper;
	@Autowired
	TDefSqlMapper tDefSqlMapper;
	@Autowired
	TDefSqlInfoMapper tDefSqlInfoMapper;
	@Autowired
	SqlParamService sqlParamService;
	@Autowired
	SqlAdapterMapper sqlAdapterMapper;
	@Autowired
	QrtzTriggersMapper qrtzTriggersMapper;
	@Autowired
	QrtzSimpleTriggersMapper qrtzSimpleTriggersMapper;
	
	private static Logger logger = Logger.getLogger(QuartzLogServiceImpl.class);
	
	public QuartzLogServiceImpl(){}
	
	@Override
	@Transactional
	public boolean insertLog(Long id, JobDetail jobDetial, Trigger trigger,int type) throws Exception {
		 Date sdate =   trigger.getStartTime();
		 Date edate =   trigger.getEndTime();
		 logger.debug("Insert Quartz Log：id = "+id+" jobName = "+jobDetial.getName()+"\t endTime: "+edate+" 执行结果 【1-正常 0-异常】   "+type);
		 QrtzTriggerLog record = new QrtzTriggerLog();
		 record.setId(id.toString());
		 record.setTriggerName(trigger.getName());
		 record.setJobGroup(jobDetial.getGroup());
		 record.setJobName(trigger.getJobName());
		 record.setJobDesc(trigger.getDescription());
		 record.setStartTime(DateUtils.DateToString(sdate));
		 record.setEndTime(DateUtils.DateToString(edate));
		 record.setInsertTime(DateUtils.DateToString(new Date()));
		 
		 record.setLogFlag(Short.parseShort(""+type));
		 if(type == 1){
				record.setLogMsg("执行成功");
		 }else{
				record.setLogMsg("执行失败");
		 }
		 record.setOperator("SYS-AUTO");
		 record.setExecuteType(Short.parseShort("0")); //0-计划 1-手动（默认0）
		 record.setRunStatus(Short.parseShort("2"));//2 运行中
		 record.setRcStatus(Short.parseShort("1"));
		 record.setLastModifyTime(DateUtils.DateToString(sdate));
		 qrtzTriggerLogMapper.insert(record);
		 return true;
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.platform.core.application.quartz.QuartzLogService#insertLog(java.lang.Long, org.quartz.JobDetail, org.quartz.Trigger, int, java.lang.String)
	 */
	@Override
	public boolean insertLog(Long id, JobDetail jobDetial, Trigger trigger,
			int type, String msg) {
		 Date sdate =   trigger.getStartTime();
		 Date edate =   trigger.getEndTime();
		 logger.debug("Insert Quartz Log：id = "+id+" jobName = "+jobDetial.getName()+"\t endTime: "+edate+" 执行结果 【1-正常 0-异常】   "+type);
		 QrtzTriggerLog record = new QrtzTriggerLog();
		 record.setId(id.toString());
		 record.setTriggerName(trigger.getName());
		 record.setJobGroup(jobDetial.getGroup());
		 record.setJobName(trigger.getJobName());
		 record.setJobDesc(trigger.getDescription());
		 record.setStartTime(DateUtils.DateToString(sdate));
		 record.setEndTime(DateUtils.DateToString(edate));
		 record.setInsertTime(DateUtils.DateToString(new Date()));
		 record.setLogFlag(Short.parseShort(""+type));
		 if(msg != null && !"".equals(msg)){
			 record.setLogMsg(msg);
		 }else{
			 if(type == 1){
					record.setLogMsg("执行成功");
			 }else{
					record.setLogMsg("执行失败");
			 }  
		 }
		 record.setOperator("SYS-JOB");
		 record.setExecuteType(Short.parseShort("0")); //0-计划 1-手动（默认0）
		 record.setRunStatus(Short.parseShort("2"));//2 运行中
		 record.setRcStatus(Short.parseShort("1"));
		 record.setLastModifyTime(DateUtils.DateToString(sdate));
		 qrtzTriggerLogMapper.insert(record);
		 return true;
	}
	
	@Override
	public boolean uploadLog(Long id, JobDetail jobDetial, Trigger trigger,int type) {
		logger.debug("Update Quartz Log ： id = "+id+" jobName = "+jobDetial.getName()+" 执行结果 【1-正常 0-异常】   "+type);
		QrtzTriggerLog record = qrtzTriggerLogMapper.selectByPrimaryKey(id.toString());
		if(record != null){
			if(type == 1){
				record.setLogMsg("执行成功");
			}else{
				record.setLogMsg("执行失败");
			}
			record.setEndTime(DateUtils.DateToString(new Date()));
			record.setLogFlag(Short.parseShort(""+type));//更新状态
			qrtzTriggerLogMapper.updateByPrimaryKey(record);
		}else{
			logger.debug("Update Quartz Log ： id = "+id+" jobName = "+jobDetial.getName()+" 无结果可更新");
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see com.sinosoft.platform.core.application.quartz.QuartzLogService#uploadLog(java.lang.Long, org.quartz.JobDetail, org.quartz.Trigger, int, java.lang.String)
	 */
	@Override
	public boolean uploadLog(Long id, JobDetail jobDetial, Trigger trigger,
			int type, String msg) {
		logger.debug("Update Quartz Log ： id = "+id+" jobName = "+jobDetial.getName()+" 执行结果 【1-正常 0-异常】   "+type);
		QrtzTriggerLog record = qrtzTriggerLogMapper.selectByPrimaryKey(id.toString());
		if(record != null){
			record.setLogFlag(Short.parseShort(""+type));//更新状态
			if(msg != null && !"".equals(msg)){
				 record.setLogMsg(msg);
			 }else{
				 if(type == 1){
						record.setLogMsg("执行成功");
				 }else{
						record.setLogMsg("执行失败");
				 }  
			 }
			record.setEndTime(DateUtils.DateToString(new Date()));
			qrtzTriggerLogMapper.updateByPrimaryKey(record);
		}else{
			logger.debug("Update Quartz Log ： id = "+id+" ,name = "+jobDetial.getName()+" 无结果可更新");
		}
		return true;
	}
	/**
	 * 根据sql_id获取sql脚本
	 * @param sqlId
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private String getSql(BigDecimal sqlId ) throws SQLException, IOException{
		try{
			String sql = sqlParamService.getSqlBySqlId(sqlId, null);
			return sql;
		}catch(Exception e){
			return null;
		}finally{
			
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean batchVerify(JobDetail jobDetial, Trigger trigger) throws Exception {
		
		logger.debug("============批处理:"+trigger.getJobName()+" 前置校验开始===============");
		
		Integer delayOperationTime = (Integer) trigger.getJobDataMap().get("delayOperationTime");        //延迟运行时间 
		Integer trydelayoperationcount = (Integer) trigger.getJobDataMap().get("trydelayoperationcount");//延迟运行次数 
		List<BigDecimal> sqlIds = (List<BigDecimal>) trigger.getJobDataMap().get("sqlIds");              //获取sqlId
		List<String> lists = new ArrayList<String>();  // 存放查询的结果非0即true
		String triggerName = trigger.getName();        // 触发器名称UUID生成
		//无校验SQL 直接返回 true
		if(sqlIds != null && sqlIds.size() > 0){
			for(BigDecimal sqlId : sqlIds){
				BigDecimal zero = new BigDecimal(0);
				if(sqlId.equals(zero)){
					continue;
				}
				String sql = getSql(sqlId);
				if(sql != null){
					System.out.println("- SQL : "+sql);
					SqlAdapter sqlAdapter = new SqlAdapter();
					sqlAdapter.setSql(sql);
					List<Map> list = sqlAdapterMapper.excuteQuery(sqlAdapter);
					if(list != null && list.size() > 0){
						Map map = list.get(0);
						//只取第一个
						Iterator iter = map.keySet().iterator();
						if(iter.hasNext()){
							String key = (String) iter.next();
							lists.add(""+map.get(key));
						}
					}
				}
			}
		}else{
			logger.debug("============批处理:"+trigger.getJobName()+" 无前置校验===============");			
		}
		SchedulerService schedulerService = new SchedulerServiceImpl();
		// 校验不通过则重新构造一个触发器
		if (lists.contains("false")) {
			if (trydelayoperationcount == 0) {
				removeRepeatTriggers(triggerName, schedulerService);
			} else {

				String trigger_name = triggerName;                         // 原来的uuid + 新构造的uuid
				if (trigger_name.length() > 36 && !trigger_name.startsWith("HB")) {
					trigger_name = trigger_name.substring(0,trigger_name.lastIndexOf("&"));
				}
				Date startTime = DateUtils.calDate(trigger.getStartTime(),delayOperationTime, "s"); // 开始时间 = 上一个job开始时间+延迟时间
				Date endTime = trigger.getEndTime();                        // 结束时间
				int executeCount = query_repeat_count(triggerName);         // 执行次数
				String job_name = jobDetial.getName();                      // 作业名称
				String planToDescribe = trigger.getDescription();           // 作业描述
				int repeatInterval = (int) (query_Repeat_interval(triggerName) / 1000); //执行间隔
				//构造新的触发器 【待下次执行】
				schedulerService.schedule(trigger_name, startTime, endTime,
						executeCount, repeatInterval * 1000,
						Scheduler.DEFAULT_GROUP, job_name, planToDescribe,
						delayOperationTime, --trydelayoperationcount, sqlIds);
			}
			return false;
		}
		if (triggerName.length() > 36) {
			removeRepeatTriggers(triggerName, schedulerService);
		}
		return true;
	}
	
	//删除重复的构造
	private void removeRepeatTriggers(String triggerName,SchedulerService schedulerService) throws Exception {
		
		String substring = triggerName.substring(0, triggerName.lastIndexOf("&"));
		QrtzTriggersExample example = new QrtzTriggersExample();
		QrtzTriggersExample.Criteria criteria = example.createCriteria();
		criteria.andTriggerNameNotLike(substring+"&%");
		List<QrtzTriggers> triggers = qrtzTriggersMapper.selectByExample(example);
		if(triggers != null && triggers.size() > 0){
			for(QrtzTriggers trigger : triggers){
				schedulerService.removeTrigger(trigger.getTriggerName(), "DEFAULT"); //移除重复构造的触发器
				//QrtzTriggersKey  key = new QrtzTriggersKey();
				//key.setTriggerGroup(trigger.getTriggerGroup());
				//key.setTriggerName(trigger.getTriggerName());
				//qrtzTriggersMapper.deleteByPrimaryKey(key);
			}
		}
	}
	
	//查询重复间隔
	private Long query_Repeat_interval(String triggerName) throws Exception{
		Long repeat_interval = null;
		QrtzSimpleTriggersExample example = new QrtzSimpleTriggersExample();
		example.createCriteria().andTriggerNameEqualTo(triggerName);
		List<QrtzSimpleTriggers> sTriggers = qrtzSimpleTriggersMapper.selectByExample(example);
		if(sTriggers != null && sTriggers.size() > 0){
			repeat_interval = sTriggers.get(0).getRepeatInterval();
		}
		return repeat_interval;
	}
	
	//查询执行次数
	private Integer query_repeat_count(String triggerName) throws Exception{
		Integer repeat_interval = 0;
		QrtzSimpleTriggersExample example = new QrtzSimpleTriggersExample();
		example.createCriteria().andTriggerNameEqualTo(triggerName);
		List<QrtzSimpleTriggers> sTriggers = qrtzSimpleTriggersMapper.selectByExample(example);
		if(sTriggers != null && sTriggers.size() > 0){
			repeat_interval = sTriggers.get(0).getRepeatCount();
		}
		return repeat_interval;
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.platform.quartz.application.QuartzLogService#insertLog(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public QrtzTriggerLog insertLog(String id, String trigerName, String jobGroup,
			String jobName, String jobDesc, String startTime,String endTime,String runStatus) {
		
		 logger.debug("Insert Quartz Log：id = "+id+" jobName = "+jobName);
		 QrtzTriggerLog record = new QrtzTriggerLog();
		 record.setId(id.toString());
		 record.setTriggerName(trigerName);
		 record.setJobGroup(jobGroup);
		 record.setJobName(jobName);
		 record.setJobDesc(jobDesc);
		 record.setStartTime(startTime);
		 record.setEndTime(endTime);
		 record.setLogFlag(Short.parseShort("1"));
		 record.setLogMsg("执行开始");
		 record.setOperator("SYS-AUTO");
		 record.setExecuteType(Short.parseShort("0")); //0-计划 1-手动（默认0）
		 record.setRunStatus(Short.parseShort(runStatus));//2 运行中
		 record.setRcStatus(Short.parseShort("1"));
		 record.setInsertTime(DateUtils.DateToString(new Date()));
		 record.setLastModifyTime(DateUtils.getCurrentDateTime());
		 qrtzTriggerLogMapper.insert(record);
		return record;
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.platform.quartz.application.QuartzLogService#uploadLog(com.sinosoft.platform.quartz.domain.model.QrtzTriggerLog)
	 */
	@Override
	public boolean uploadLog(QrtzTriggerLog log) {
		qrtzTriggerLogMapper.updateByPrimaryKey(log);
		return true;
	}
}
