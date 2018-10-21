package com.sinosoft.platform.quartz.application.impl;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import oracle.sql.CLOB;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.platform.core.application.SequenceService;
import com.sinosoft.platform.core.domain.mapper.SqlAdapterMapper;
import com.sinosoft.platform.core.domain.mapper.TDefJdbcMapper;
import com.sinosoft.platform.core.domain.mapper.TDefSqlInfoMapper;
import com.sinosoft.platform.core.domain.mapper.TDefSqlMapper;
import com.sinosoft.platform.core.domain.mapper.TDefSqlParamsMapper;
import com.sinosoft.platform.core.domain.model.TDefJdbc;
import com.sinosoft.platform.core.domain.model.TDefJdbcExample;
import com.sinosoft.platform.core.domain.model.TDefSql;
import com.sinosoft.platform.core.domain.model.TDefSqlExample;
import com.sinosoft.platform.core.domain.model.TDefSqlInfo;
import com.sinosoft.platform.core.domain.model.TDefSqlInfoExample;
import com.sinosoft.platform.core.domain.model.TDefSqlParamsExample;
import com.sinosoft.platform.core.exception.CoreException;
import com.sinosoft.platform.core.spring.quartz.Constant;
import com.sinosoft.platform.core.util.DataGridModel;
import com.sinosoft.platform.core.util.LoginInfo;
import com.sinosoft.platform.quartz.application.SchedulerService;
import com.sinosoft.platform.quartz.application.SchedulerUtils;
import com.sinosoft.platform.quartz.domain.mapper.QrtzJobDetailsMapper;
import com.sinosoft.platform.quartz.domain.mapper.QrtzTriggerLogMapper;
import com.sinosoft.platform.quartz.domain.mapper.QrtzTriggersMapper;
import com.sinosoft.platform.quartz.domain.model.JobDetails;
import com.sinosoft.platform.quartz.domain.model.QrtzJobDetails;
import com.sinosoft.platform.quartz.domain.model.QrtzJobDetailsExample;
import com.sinosoft.platform.quartz.domain.model.QrtzTriggerLogExample;
import com.sinosoft.platform.quartz.domain.model.QrtzTriggers;
import com.sinosoft.platform.quartz.domain.model.QrtzTriggersExample;
import com.sinosoft.platform.quartz.domain.model.Tcschedulerallocate;
import com.sinosoft.platform.quartz.domain.model.dao.JobDetailsDAO;
import com.sinosoft.platform.quartz.domain.model.dao.TcschedulerallocateDAO;

/**
 * 简单批处理服务实现类
 * @author Darker.Wang
 * @date 2016-10-10 下午3:29:26
 * @company SINOSOFT
 */
@Transactional
@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	private QrtzJobDetailsMapper qrtzJobDetailsMapper;
	@Autowired
	private QrtzTriggersMapper qrtzTriggersMapper;

	@Autowired
	private TDefJdbcMapper tDefJdbcMapper;

	@Resource
	private TDefSqlMapper tDefSqlMapper;
	@Resource
	private TDefSqlInfoMapper tDefSqlInfoMapper;
	@Resource
	private TDefSqlParamsMapper tDefSqlParamsMapper;

	@Autowired
	SqlAdapterMapper sqlAdapterMapper;

	@Autowired
	SequenceService sequenceService;

	@Autowired
	public Scheduler scheduler;

	private JobDetail jobDetail;

	@Autowired
	JobDetailsDAO jobDetailsDAO;
	@Autowired
	TcschedulerallocateDAO tcSchedulerallocateDAO;

	@Autowired
	QrtzTriggerLogMapper qrtzTriggerLogMapper;
	
	private static final Logger logger = Logger.getLogger(SchedulerServiceImpl.class);
	private static final String String = null;

	public void setJobDetail(JobDetail jobDetail) {
		System.out.println("- JobDetail : "+jobDetail);
		this.jobDetail = jobDetail;
	}

	@Autowired
	public void setScheduler(@Qualifier("quartzScheduler") Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void schedule(String cronExpression, String jobName)
			throws CoreException {
		schedule("", cronExpression, jobName);
	}

	public void schedule(String name, String cronExpression, String jobName)
			throws CoreException {
		schedule(name, cronExpression, Scheduler.DEFAULT_GROUP, jobName);
	}

	// 表达式调度 and 模板调度
	public void schedule(String name, String cronExpression, String group,
			String jobName) throws CoreException {
		try {
			if (cronExpression == null || "".equals(cronExpression)) {
				throw new CoreException("cronExpression不能为空");
			}
			if (jobName == null || "".equals(jobName)) {
				throw new CoreException("jobName不能为空");
			}
			schedule(name, new CronExpression(cronExpression), group, jobName);
		} catch (ParseException e) {
			throw new CoreException("cronExpression解析错误", e);
		}
	}

	/**
	 * 表达式调度 and 模板调度
	 */
	public void schedule(String name, String cronExpression, String group,
			String jobName, String tempPlanToDescribe) throws CoreException {
		System.out.println("- SchedulerServiceImpl schedule begin : name : " +name+" jobName : "+jobName);
		try {
			if (cronExpression == null || "".equals(cronExpression)) {
				throw new CoreException("cronExpression不能为空");
			}
			if (jobName == null || "".equals(jobName)) {
				throw new CoreException("jobName不能为空");
			}
			schedule(name, new CronExpression(cronExpression), group, jobName,
					tempPlanToDescribe);
		} catch (ParseException e) {
			throw new CoreException("cronExpression解析错误", e);
		}
	}

	public void schedule(CronExpression cronExpression, String jobName)
			throws CoreException {
		System.out.println("- SchedulerServiceImpl schedule begin : jobName : "+jobName);
		schedule(null, cronExpression, jobName);
	}

	public void schedule(String name, CronExpression cronExpression,
			String jobName) throws CoreException {
		schedule(name, cronExpression, Scheduler.DEFAULT_GROUP, jobName);
	}

	// 表达式调度
	public void schedule(String name, CronExpression cronExpression,
			String group, String jobName) throws CoreException {
		System.out.println("- SchedulerServiceImpl schedule begin : name "+name+" jobName : "+jobName);
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		} else {
			// 在名称后添加UUID，保证名称的唯一性
			name += "&" + UUID.randomUUID().toString();
			System.out.println("schedule：" + name);
		}

		try {
			if (jobName != null & !"".equals(jobName)) {
				jobDetail = scheduler.getJobDetail(jobName, group);
			}
			scheduler.addJob(jobDetail, true);

			CronTrigger cronTrigger = new CronTrigger(name, group,
					jobDetail.getName(), Scheduler.DEFAULT_GROUP);
			cronTrigger.setCronExpression(cronExpression);
			scheduler.scheduleJob(cronTrigger);
		} catch (SchedulerException e) {
			throw new CoreException(e.getMessage());
		}
	}

	// 表达式调度
	public void schedule(String name, CronExpression cronExpression,
			String group, String jobName, String tempPlanToDescribe)
			throws CoreException {
		System.out.println("- SchedulerServiceImpl schedule begin : name "+name+" jobName : "+jobName);
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		} else {
			// 在名称后添加UUID，保证名称的唯一性
			name += "&" + UUID.randomUUID().toString();
			System.out.println("schedule：" + name);
		}

		try {
			if (jobName != null & !"".equals(jobName)) {
				jobDetail = scheduler.getJobDetail(jobName, group);
			}
			scheduler.addJob(jobDetail, true);

			CronTrigger cronTrigger = new CronTrigger(name, group,
					jobDetail.getName(), Scheduler.DEFAULT_GROUP);
			cronTrigger.setDescription(tempPlanToDescribe); // 添加计划描述信息
			cronTrigger.setCronExpression(cronExpression);
			scheduler.scheduleJob(cronTrigger);
		} catch (SchedulerException e) {
			throw new CoreException(e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	public void schedule(Date startTime, String jobName) throws CoreException {
		System.out.println("- SchedulerServiceImpl schedule begin : jobName "+jobName+" startTime : "+startTime.toLocaleString());
		schedule(startTime, Scheduler.DEFAULT_GROUP, jobName);
	}

	@SuppressWarnings("deprecation")
	public void schedule(Date startTime, String group, String jobName)
			throws CoreException {
		System.out.println("- SchedulerServiceImpl schedule begin : jobName "+jobName+" startTime : "+startTime.toLocaleString());
		schedule(startTime, null, group, jobName);
	}

	@SuppressWarnings("deprecation")
	public void schedule(String name, Date startTime, String jobName)
			throws CoreException {
		System.out.println("- SchedulerServiceImpl schedule begin : jobName "+jobName+" startTime : "+startTime.toLocaleString());
		schedule(name, startTime, Scheduler.DEFAULT_GROUP, jobName);
	}

	public void schedule(String name, Date startTime, String group,
			String jobName) throws CoreException {
		try {
			schedule(name, startTime, null, group, jobName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void schedule(Date startTime, Date endTime, String jobName)
			throws CoreException {
		schedule(startTime, endTime, Scheduler.DEFAULT_GROUP, jobName);
	}

	public void schedule(Date startTime, Date endTime, String group,
			String jobName) throws CoreException {
		schedule(startTime, endTime, 0, group, jobName);
	}

	public void schedule(String name, Date startTime, Date endTime,
			String jobName) throws CoreException {
		schedule(name, startTime, endTime, Scheduler.DEFAULT_GROUP, jobName);
	}

	@SuppressWarnings("deprecation")
	public void schedule(String name, Date startTime, Date endTime,
			String group, String jobName) throws CoreException {
		System.out.println("- SchedulerServiceImpl schedule begin : jobName "+jobName
				+" startTime : "  +startTime.toLocaleString()
				+" endTime : " + endTime.toLocaleString());
		schedule(name, startTime, endTime, 0, group, jobName);
	}

	public void schedule(Date startTime, Date endTime, int repeatCount,
			String jobName) throws CoreException {
		schedule(startTime, endTime, 0, Scheduler.DEFAULT_GROUP, jobName);
	}

	public void schedule(Date startTime, Date endTime, int repeatCount,
			String group, String jobName) throws CoreException {
		schedule(null, startTime, endTime, 0, group, jobName);
	}

	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, String jobName) throws CoreException {
		schedule(name, startTime, endTime, 0, Scheduler.DEFAULT_GROUP, jobName);
	}

	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, String group, String jobName) throws CoreException {
		schedule(name, startTime, endTime, 0, 1L, group, jobName);
	}

	public void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval, String jobName) {
		schedule(startTime, endTime, repeatCount, repeatInterval,
				Scheduler.DEFAULT_GROUP, jobName);
	}

	public void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval, String group, String jobName, String planDesc)
			throws CoreException {
		schedule(null, startTime, endTime, repeatCount, repeatInterval, group,
				jobName);
	}

	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval, String jobName,
			String planDesc) throws CoreException {
		this.schedule(name, startTime, endTime, repeatCount, repeatInterval,
				Scheduler.DEFAULT_GROUP, jobName, planDesc);
	}

	// 简单调度
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval, String group, String jobName,
			String planDesc) throws CoreException {
		if (startTime == null || "".equals(startTime)) {
			throw new CoreException("startTime不能为空");
		}
		if (jobName == null || "".equals(jobName)) {
			throw new CoreException("jobName不能为空");
		}
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		} else {
			// 在名称后添加UUID，保证名称的唯一性
			name += "&" + UUID.randomUUID().toString();
			System.out.println("name:" + name);
		}

		try {
			if (jobName != null & !"".equals(jobName)) {
				jobDetail = scheduler.getJobDetail(jobName, group);
			}
			scheduler.addJob(jobDetail, true);
			SimpleTrigger simpleTrigger = new SimpleTrigger(name, group,
					jobName, Scheduler.DEFAULT_GROUP, startTime, endTime,
					repeatCount, repeatInterval);
			simpleTrigger.setDescription(planDesc);
			scheduler.scheduleJob(simpleTrigger);
		} catch (SchedulerException e) {
			throw new CoreException(e.getMessage());
		}
	}

	/**
	 * 
	 * 前台页面传送Map集合，里面是页面录入的基本信息。
	 * 
	 * @throws CoreException
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public void schedule(Map<String, Object> map) throws CoreException {
		String trigger_type = (String) map.get(Constant.TRIGGERTYPE); // 触发器类型
		String trigger_name = (String) map.get(Constant.TRIGGERNAME); // 触发器名称
		String job_name = (String) map.get(Constant.JOBNAME); // 作业名称

		System.out.println("- SchedulerServiceImpl schedule begin :" + trigger_type + "||" + trigger_name);
		try {
			if (job_name != null & !"".equals(job_name)) {
				jobDetail = scheduler.getJobDetail(job_name,
						Scheduler.DEFAULT_GROUP);
			}
			if (trigger_type.equals("simpletrigger")
					|| trigger_type == "simpletrigger") {
				int unit = Integer.valueOf(String.valueOf(map.get("unit"))); // 执行间隔单位
				String t_starttime = String
						.valueOf(map.get(Constant.STARTTIME)); // 开始时间
				String t_endtime = String.valueOf(map.get(Constant.ENDTIME)); // 结束时间
				String repeatCount = (String) map.get(Constant.REPEATCOUNT); // 执行次数
				String t_repeatInterval = String.valueOf(map
						.get(Constant.REPEATINTERVEL)); // 执行间隔

				String planToDescribe = (String) map.get("planToDescribe"); // 计划描述
				String delayOperationTime = (String) map
						.get("delayOperationTime"); // 延迟运行时间
				String delayOperationTimeUnit = (String) map
						.get("delayOperationTimeUnit"); // 延迟单位
				String trydelayoperationcount = (String) map
						.get("trydelayoperationcount"); // 延迟运行次数
				int delayRunTime = 0;
				int trydelayRuncount = 0;
				if (StringUtils.isNotBlank(delayOperationTime)) {
					delayRunTime = Integer.parseInt(delayOperationTime)
							* Integer.parseInt(delayOperationTimeUnit); // 秒
				}
				if (StringUtils.isNotBlank(trydelayoperationcount)) {
					trydelayRuncount = Integer.parseInt(trydelayoperationcount); // 运行次数
				}
				List<BigDecimal> sqlIds = (List<BigDecimal>) map.get("sqlId");

				if (t_starttime == null || "".equals(t_starttime)) {
					throw new CoreException("请输入开始时间");
				}
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date startTime = dateFormat.parse(t_starttime);
				Date endTime = null;
				if (t_endtime != null && !"".equals(t_endtime)) {
					endTime = dateFormat.parse(t_endtime);
				}
				int executeCount = 1;
				long repeatInterval = 60L;
				if (StringUtils.isNotBlank(repeatCount)) {
					executeCount = Integer.parseInt(repeatCount);
				}

				if (executeCount != -1) {
					--executeCount;
				}

				if (t_repeatInterval != null && !"".equals(t_repeatInterval)) {
					repeatInterval = Long.valueOf(t_repeatInterval);
				}
				// 简单调度
				schedule(trigger_name, startTime, endTime, executeCount,
						repeatInterval * unit, Scheduler.DEFAULT_GROUP,
						job_name, planToDescribe, delayRunTime,
						trydelayRuncount, sqlIds);
			} else if (trigger_type.equals("crontrigger")
					|| trigger_type == "crontrigger") {
				String cron_expression = (String) map
						.get(Constant.CRONEXPRESSION);
				String cronlanToDescribe = (String) map
						.get("cronlanToDescribe");
				// 表达式调度
				schedule(trigger_name, cron_expression,
						Scheduler.DEFAULT_GROUP, job_name, cronlanToDescribe);
			} else if (trigger_type.equals("templatetrigger")
					|| trigger_type == "templatetrigger") {
				String template_type = (String) map.get("template_type");
				String executeTime = (String) map.get("executeTime");
				String tempPlanToDescribe = (String) map
						.get("tempPlanToDescribe");
				String executeHour = executeTime.substring(0, 2);
				String executeMinute = executeTime.substring(3, 5);
				String executeSecond = executeTime.substring(6, 8);
				String cron_expression = "";
				int type = Integer.valueOf(template_type.substring(8));
				switch (type) {
				default:
					System.out.println("default");
				case 1:
					cron_expression = executeSecond + " " + executeMinute + " "
							+ executeHour + " * * ?";
				case 2:
					System.out.println(2);
				}
				// 模板调度
				schedule(trigger_name, cron_expression,
						Scheduler.DEFAULT_GROUP, job_name, tempPlanToDescribe);

			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new CoreException("添加调度失败" + e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new CoreException("日期格式解析失败");
		}
	}

	/**
	 * 列表查询
	 */
	@Transactional
	public Map<String, Object> getPageList(DataGridModel dgm, String jobName,
			String trigger_name) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("jobName", jobName);
		paramMap.put("queryStartIndex", dgm.getStartIndex());
		paramMap.put("queryEndIndex", dgm.getEndIndex());
		int total = qrtzJobDetailsMapper.queryHomeWorkCounts(paramMap);
		List<Map<String, String>> list = qrtzJobDetailsMapper
				.queryHomeWorkListInfo(paramMap);
		result.put("total", total);
		result.put("rows", list);
		return result;
		// return quartzDao.getPageList(dgm, jobName, trigger_name);
	}

	public void pauseTrigger(String triggerName, String group)
			throws CoreException {
		try {
			scheduler.pauseTrigger(triggerName, group);// 停止触发器
		} catch (SchedulerException e) {
			throw new CoreException(e.getMessage());
		}
	}

	public void resumeTrigger(String triggerName, String group)
			throws CoreException {
		try {
			scheduler.resumeTrigger(triggerName, group);// 重启触发器
		} catch (SchedulerException e) {
			throw new CoreException(e.getMessage());
		}
	}

	public boolean removeTrigger(String triggerName, String group)
			throws CoreException {
		try {
			if (scheduler == null) {
				scheduler = SchedulerUtils.getScheduler();
			}
			scheduler.pauseTrigger(triggerName, group);// 停止触发器
			return scheduler.unscheduleJob(triggerName, group);// 移除触发器
		} catch (SchedulerException e) {
			throw new CoreException(e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private Date parseDate(String time) {
		try {
			return DateUtils.parseDate(time,
					new String[] { "yyyy-MM-dd HH:mm" });
		} catch (ParseException e) {
			logger.error("日期格式错误{}，正确格式为：yyyy-MM-dd HH:mm"+time);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 任务名称下拉查询
	 */
	@SuppressWarnings("rawtypes")
	public List queryJobTnameCombox() {
		return null;
	}

	/**
	 * 
	 * 基本任务信息查询
	 */
	@Transactional
	public Map<String, Object> getPageList(DataGridModel dgm,
			JobDetails jobDetails) {
		return null;

	}

	/**
	 * 新增基本任务
	 * 
	 */
	@Transactional
	public void addBaseTask(JobDetails jobdetails) {
		System.out.println("新增基本任务成功");
		jobDetailsDAO.save(jobdetails);
	}

	@Transactional
	public JobDetails queryJobNameByTname(String jobtname) {
		return null;
	}

	@Transactional
	public void addSchedulerAllocate(Tcschedulerallocate tcschedulerallocate) {
		System.out.println("新增调度关系分配");
		try {
			tcSchedulerallocateDAO.save(tcschedulerallocate);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional
	public Map<String, Object> getPageList(DataGridModel dgm,
			Tcschedulerallocate tcschedulerallocate) {
		return null;

	}

	/**
	 * 查询所有任务
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public List queryJob() {
		List<QrtzJobDetails> exitResult = qrtzJobDetailsMapper
				.selectByExample(new QrtzJobDetailsExample());

		if (exitResult != null && exitResult.size() > 0) {
			return exitResult;
		}
		return null;
		// return quartzDao.queryJob();
	}

	@Override
	public String getDescInfo(String jobName) {
		String descInfo = null;
		QrtzJobDetailsExample example = new QrtzJobDetailsExample();
		example.createCriteria().andJobNameEqualTo(jobName);
		List<QrtzJobDetails> qrtzJobDetails = this.qrtzJobDetailsMapper
				.selectByExample(example);
		if (qrtzJobDetails != null && qrtzJobDetails.size() > 0) {
			QrtzJobDetails qrtzJobDetail = qrtzJobDetails.get(0);
			descInfo = qrtzJobDetail.getDescription();
			return descInfo;
		}
		return descInfo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean saveVerifyInfo(Map map, String flag, LoginInfo loginInfo)
			throws CoreException, CoreException {

		// 从Map中获取参数
		String sqlCode = (String) map.get("sqlCode"); // SQL_CPDE
		String validity = (String) map.get("validity"); // 是否有效
		String desc = (String) map.get("desc"); // OK
		String sql = (String) map.get("sql"); // OK
		String sqlType = (String) map.get("sqlType"); // SQL_TYPE
		String dataSourceName = (String) map.get("dataSourceName"); // OK

		// 获取主键
		BigDecimal sqlId = sequenceService.getBigSeqValByName("SEQ_T_DEF_SQL");
		// 获取主键
		BigDecimal sqlInfoId = sequenceService.getBigSeqValByName("SEQ_T_DEF_SQL_INFO");

		// 校验算法编码否重复定义
		TDefSqlExample checkSqlCode = new TDefSqlExample();
		checkSqlCode.createCriteria().andSqlCodeEqualTo(sqlCode);
		List<TDefSql> selectByExample = this.tDefSqlMapper
				.selectByExample(checkSqlCode);
		if (selectByExample.size() != 0) {
			//throw new CoreException("该算法编码已存在，请重新定义！");
			return false;
		}

		TDefJdbcExample example = new TDefJdbcExample();
		example.createCriteria().andJdbcCodeEqualTo(dataSourceName);
		List<TDefJdbc> tDefJdbcs = this.tDefJdbcMapper.selectByExample(example);

		TDefSql tDefSql = new TDefSql();
		tDefSql.setSqlId(sqlId);	
		tDefSql.setSqlType(sqlType);
		tDefSql.setSqlCode(sqlCode);
		tDefSql.setSqlDesc(desc);
		tDefSql.setInsertTime(new Date());
		tDefSql.setComId(loginInfo.getCompanyId());
		tDefSql.setOperator(loginInfo.getUserCode());
		tDefSql.setEsStatus(new Short(validity));
		tDefSql.setSqlFromJdbcId(tDefJdbcs.get(0).getId());
		tDefSql.setSubBussType(flag);
		this.tDefSqlMapper.insertSelective(tDefSql);

		TDefSqlInfo tDefSqlInfo = new TDefSqlInfo();
		tDefSqlInfo.setId(sqlInfoId);
		tDefSqlInfo.setSqlId(sqlId);
		tDefSqlInfo.setSqlInfo(sql);
		tDefSqlInfo.setSqlType(sqlType);
		this.tDefSqlInfoMapper.insert(tDefSqlInfo);
		return true;
	}

	@Override
	public Map<String, Object> getDataSqlCodeList(DataGridModel dgm,
			String searchParam) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		String validity = null;
		String algorithmCoding = null;
		String sqlDesc = null;
		String dataSourceName = null;
		if (StringUtils.isNotBlank(searchParam) && !searchParam.equals("{}")) {
			JSONObject fromObject = JSONObject.fromObject(searchParam);
			validity = (String) fromObject.get("validity");
			algorithmCoding = (String) fromObject.get("algorithmCoding");
			sqlDesc = (String) fromObject.get("sqlDesc");
			dataSourceName = (String) fromObject.get("dataSourceName");
		}

		paramMap.put("validity", validity);
		paramMap.put("algorithmCoding", algorithmCoding);
		paramMap.put("sqlDesc", sqlDesc);
		paramMap.put("dataSourceName", dataSourceName);
		paramMap.put("queryStartIndex", dgm.getStartIndex());
		paramMap.put("queryEndIndex", dgm.getEndIndex());

		int total = qrtzJobDetailsMapper.queryGetDataCounts(paramMap);
		List<Map<String, String>> list = qrtzJobDetailsMapper
				.queryGetDataListInfo(paramMap);
		result.put("total", total);
		result.put("rows", list);
		return result;

	}

	@Override
	@Transactional
	public void deleteGetDataSqlCode(BigDecimal id) {

		// 先查询后删除
		TDefSql tDefSql = this.tDefSqlMapper.selectByPrimaryKey(id);
		this.tDefSqlMapper.deleteByPrimaryKey(tDefSql.getSqlId());

		TDefSqlInfoExample tDefSqlInfoExample = new TDefSqlInfoExample();
		tDefSqlInfoExample.createCriteria().andSqlIdEqualTo(tDefSql.getSqlId());
		this.tDefSqlInfoMapper.deleteByExample(tDefSqlInfoExample);

		TDefSqlParamsExample tDefSqlParamsExample = new TDefSqlParamsExample();
		tDefSqlParamsExample.createCriteria().andSqlIdEqualTo(
				tDefSql.getSqlId());
		this.tDefSqlParamsMapper.deleteByExample(tDefSqlParamsExample);

	}

	@Override
	public Map<String, Object> findGetDataSqlCodeById(BigDecimal id) {

		Map<String, Object> map = new HashMap<String, Object>();
		TDefSql tDefSql = this.tDefSqlMapper.selectByPrimaryKey(id);

		TDefSqlInfoExample tDefSqlInfoExample = new TDefSqlInfoExample();
		tDefSqlInfoExample.createCriteria().andSqlIdEqualTo(id);
		List<TDefSqlInfo> tDefSqlInfos = this.tDefSqlInfoMapper
				.selectByExampleWithBLOBs(tDefSqlInfoExample);
		TDefSqlInfo tDefSqlInfo = tDefSqlInfos.get(0);

		// 获取数据源名称
		BigDecimal idd = tDefSql.getSqlFromJdbcId();
		TDefJdbc tDefJdbc = this.tDefJdbcMapper.selectByPrimaryKey(idd);

		map.put("tDefSql", tDefSql);
		map.put("tDefSqlInfo", tDefSqlInfo);
		map.put("tDefJdbc", tDefJdbc);

		return map;
	}

	@Override
	public void saveUpdateVerifyInfo(Map<String, Object> map,
			LoginInfo loginInfo) throws CoreException {

		// 从Map中获取参数
		String sqlId = (String) map.get("sqlId"); // 主键ID
		String sqlCode = (String) map.get("sqlCode"); // OK
		String dataSourceName = (String) map.get("dataSourceName"); // OK
		String sqlType = (String) map.get("sqlType");//对应SQLTYPE
		String validity = (String) map.get("validity"); // OK
		String desc = (String) map.get("desc"); // OK
		String sql = (String) map.get("sql"); // OK

		TDefSqlExample checkSqlCode = new TDefSqlExample();
		checkSqlCode.createCriteria().andSqlCodeEqualTo(sqlCode);
		List<TDefSql> selectByExample = this.tDefSqlMapper
				.selectByExample(checkSqlCode);
		if (selectByExample.size() == 2) {
			if (!selectByExample.get(0).getSqlId()
					.equals(new BigDecimal(sqlId))) {
				throw new CoreException("算法编码已存在，请重新修改！");
			}
		}

		TDefJdbcExample example = new TDefJdbcExample();
		example.createCriteria().andJdbcCodeEqualTo(dataSourceName);
		List<TDefJdbc> tDefJdbcs = this.tDefJdbcMapper.selectByExample(example);

		TDefSql tDefSql = this.tDefSqlMapper.selectByPrimaryKey(new BigDecimal(
				sqlId));
		tDefSql.setSqlType(sqlType);
		tDefSql.setSqlCode(sqlCode);
		tDefSql.setSqlDesc(desc);
		tDefSql.setSqlFromJdbcId(tDefJdbcs.get(0).getId());
		tDefSql.setLastModifyTime(new Date());
		tDefSql.setComId(loginInfo.getCompanyId());
		tDefSql.setOperator(loginInfo.getUserCode());
		tDefSql.setEsStatus(new Short(validity));
		this.tDefSqlMapper.updateByPrimaryKeySelective(tDefSql);

		TDefSqlInfo tDefSqlInfo = new TDefSqlInfo();
		TDefSqlInfoExample tDefSqlInfoExample = new TDefSqlInfoExample();
		tDefSqlInfoExample.createCriteria().andSqlIdEqualTo(
				new BigDecimal(sqlId));
		List<TDefSqlInfo> tDefSqlInfos = this.tDefSqlInfoMapper
				.selectByExample(tDefSqlInfoExample);
		TDefSqlInfo TDefSqlInfo = tDefSqlInfos.get(0);
		BigDecimal sqlInfoId = TDefSqlInfo.getId();
		tDefSqlInfo.setSqlType(sqlType);
		tDefSqlInfo.setId(sqlInfoId);
		tDefSqlInfo.setSqlInfo(sql);
		this.tDefSqlInfoMapper.updateByPrimaryKeySelective(tDefSqlInfo);
	}

	/**
	 * 手动执行批处理 查询校验列表
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Override
	public Map<String, Object> getVerifyList(DataGridModel dgm)
			throws SQLException, IOException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		paramMap.put("queryStartIndex", dgm.getStartIndex());
		paramMap.put("queryEndIndex", dgm.getEndIndex());
		int total = tDefSqlMapper.selectCountBatchConfigSQL();
		List<Map<String, Object>> lists = tDefSqlMapper
				.selectBatchConfigSQL(paramMap);
		for (Map<String, Object> map : lists) {
			CLOB value = (CLOB) map.get("sqlInfo");
			if (value != null) {
				map.put("sqlInfo", ClotToString(value));
			}
		}
		result.put("total", total);
		result.put("rows", lists);
		return result;
	}

	private String ClotToString(CLOB clob) throws SQLException, IOException {
		String date = null;
		Reader r = clob.getCharacterStream();
		char[] c = new char[(int) (clob.length())];
		r.read(c);
		date = new String(c);
		return date;
	}

	@Override
	public List<QrtzTriggers> queryExistJob() {

		QrtzTriggersExample qrtzTriggersExample = new QrtzTriggersExample();
		List<QrtzTriggers> qrtzTriggers = this.qrtzTriggersMapper
				.selectByExample(qrtzTriggersExample);
		return qrtzTriggers;
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval, String group, String jobName,
			String planToDescribe, int delayRunTime,
			int trydelayoperationcount, List<BigDecimal> sqlIds)
			throws CoreException {

		if (startTime == null || "".equals(startTime)) {
			throw new CoreException("startTime不能为空");
		}
		if (jobName == null || "".equals(jobName)) {
			throw new CoreException("jobName不能为空");
		}
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		} else {

			name += "&" + UUID.randomUUID().toString(); // 在名称后添加UUID，保证名称的唯一性
			System.out.println("name:" + name);
		}

		try {

			if (scheduler == null) {
				scheduler = SchedulerUtils.getScheduler();
			}
			if (jobName != null & !"".equals(jobName)) {
				jobDetail = scheduler.getJobDetail(jobName, group);
			}
			scheduler.addJob(jobDetail, true); // 作业的详细信息、添加到-->调度容器中
			SimpleTrigger simpleTrigger = new SimpleTrigger(name, group,
					jobName, Scheduler.DEFAULT_GROUP, startTime, endTime,
					repeatCount, repeatInterval);
			simpleTrigger.setDescription(planToDescribe); // 作业的描述信息
			simpleTrigger.getJobDataMap().put("delayOperationTime",
					delayRunTime); // 延迟运行时间 以秒为单位 ok
			simpleTrigger.getJobDataMap().put("trydelayoperationcount",
					trydelayoperationcount); // 延迟运行次数 ok
			simpleTrigger.getJobDataMap().put("sqlIds", sqlIds); // T_DEF_SQL的主键
			scheduler.scheduleJob(simpleTrigger); // 简单触发器注册到-->调度容器中
		} catch (SchedulerException e) {
			throw new CoreException(e.getMessage());
		}

	}

	@Override
	public Map<String, Object> queryMonitoringList(DataGridModel dgm) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("queryStartIndex", dgm.getStartIndex());
		paramMap.put("queryEndIndex", dgm.getEndIndex());
		int total = qrtzJobDetailsMapper.queryMonitoringCounts(paramMap);
		List<Map<String, String>> list = qrtzJobDetailsMapper
				.queryMonitoringListInfo(paramMap);
		result.put("total", total);
		result.put("rows", list);
		return result;
	}

	@Override
	public Map<String, Object> queryBatchLogList(DataGridModel dgm, String param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		String homework = null;
		String run_mode = null;
		String startDate = null;
		String endDate = null;
		if (StringUtils.isNotBlank(param) && !param.equals("{}")) {
			JSONObject fromObject = JSONObject.fromObject(param);
			homework = (String) fromObject.get("homework");
			run_mode = (String) fromObject.get("run_mode");
			startDate = (String) fromObject.get("startDate");
			endDate = (String) fromObject.get("endDate");
		}

		// 查询条件
		paramMap.put("homework", homework);
		paramMap.put("run_mode", run_mode);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);

		paramMap.put("queryStartIndex", dgm.getStartIndex());
		paramMap.put("queryEndIndex", dgm.getEndIndex());

		int total = qrtzJobDetailsMapper.queryBatchLogCounts(paramMap);
		List<Map<String, String>> list = qrtzJobDetailsMapper
				.queryBatchLogListInfo(paramMap);
		result.put("total", total);
		result.put("rows", list);
		return result;
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval, String group, String jobName) {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void schedule(java.lang.String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval, java.lang.String jobName,
			java.lang.String planDesc, Map map) throws CoreException {
		schedule(name, startTime, endTime, repeatCount, repeatInterval,
				Scheduler.DEFAULT_GROUP, jobName, planDesc, map);

	}

	// 简单调度
	@SuppressWarnings("rawtypes")
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval, String group, String jobName,
			String planDesc, Map map) throws CoreException {
		if (startTime == null || "".equals(startTime)) {
			throw new CoreException("startTime不能为空");
		}
		if (jobName == null || "".equals(jobName)) {
			throw new CoreException("jobName不能为空");
		}
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		} else {
			// 在名称后添加UUID，保证名称的唯一性
			name += "&" + UUID.randomUUID().toString();
			System.out.println("name:" + name);
		}

		try {
			if (jobName != null & !"".equals(jobName)) {
				jobDetail = scheduler.getJobDetail(jobName, group);
			}
			scheduler.addJob(jobDetail, true);
			SimpleTrigger simpleTrigger = new SimpleTrigger(name, group,
					jobName, Scheduler.DEFAULT_GROUP, startTime, endTime,
					repeatCount, repeatInterval);
			simpleTrigger.setDescription(planDesc);
			simpleTrigger.getJobDataMap().put("pojo", map);
			scheduler.scheduleJob(simpleTrigger);
		} catch (SchedulerException e) {
			throw new CoreException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.platform.quartz.application.SchedulerService#deleteTriggerLog(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int deleteTriggerLogs(
			java.lang.String logWork, java.lang.String startTime,
			java.lang.String endTime) {
		QrtzTriggerLogExample example = new QrtzTriggerLogExample();
		QrtzTriggerLogExample.Criteria criteria = example.createCriteria();
		if(logWork != null && !logWork.equals("")){
			criteria.andJobNameEqualTo(logWork);
		}
		if(startTime != null && !startTime.equals("")){
			criteria.andStartTimeGreaterThan(startTime);
		}
		if(endTime != null && !endTime.equals("")){
			criteria.andEndTimeLessThan(endTime);
		}
		int  i = qrtzTriggerLogMapper.deleteByExample(example);
		return i;
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.platform.quartz.application.SchedulerService#deleteTriggerLog(java.math.BigDecimal)
	 */
	@Override
	public int deleteTriggerLog(String id) {
		// TODO Auto-generated method stub
		int  i = qrtzTriggerLogMapper.deleteByPrimaryKey(id);
		return i;
	}
}
