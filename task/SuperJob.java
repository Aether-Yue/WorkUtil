/**
 * 
 */
package com.sinosoft.ummp.task;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.platform.core.application.CodeQueryService;
import com.sinosoft.platform.core.domain.model.TDefCodeDict;
import com.sinosoft.platform.core.domain.pojo.ParamPOJO;
import com.sinosoft.platform.core.spring.DBContextHolder;
import com.sinosoft.platform.core.util.DateUtils;
import com.sinosoft.platform.core.util.LoginInfo;
import com.sinosoft.ummp.application.ExtractService;
import com.sinosoft.ummp.application.SubmitService;
import com.sinosoft.ummp.application.impl.ExtractServiceImpl;
import com.sinosoft.ummp.application.impl.SubmitServiceImpl;
import com.sinosoft.ummp.domain.mapper.TDefTransexcMapper;
import com.sinosoft.ummp.domain.model.TDefTransexc;
import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.task.job.impl.PRPExtractDataJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * 批处理超级父类，实现了Runnable 接口<br>
 * 1、若是当线程使用，则直接启动就行，若是当类使用，则调用excute方法<br>
 * 2、可在子类构造器中调用 setJobGroup("SYS-AUTO") 申明群组<br>
 * 3、若是手动执行，必要时请在结束后调用 destroy 销毁缓存
 * 
 * @author Darker.Wang
 * @date 2016-8-11 上午11:05:45
 * @company SINOSOFT
 * @version 1.0
 */
public abstract class SuperJob implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			excute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GenericXmlApplicationContext context = new GenericXmlApplicationContext();
	public LoginInfo loginInfo = new LoginInfo();
	public String jobGroup = "DEFAULT";
	public CodeQueryService codeQueryService = null;

	/**
	 * 设置批处理群组，可在每个批处理构造器中声明，否则默认：DEFAULT
	 * 
	 * @param jobGroup
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/**
	 * 获取JOB群组,默认为：DEFAULT
	 * 
	 * @return
	 */
	public String getJobGroup() {
		return this.jobGroup;
	}

	/**
	 * 销毁Content，系统回收资源。必要时请在执行完毕之后调用
	 */
	public void destroy() {
		context.destroy();
		context = null;
		loginInfo = null;
		jobGroup = null;
		System.gc();
	}

	/**
	 * 父类构造器初始化SPRING MVC 配置文件
	 */
	public SuperJob() {
		// TODO Auto-generated constructor stub
		context.setValidating(false);
		// TODO 只加载spring对应的Bean即可，不用全部配置文件都加载
		context.load("classpath*:spring/context-datasource-jdbc.xml");
		context.load("classpath*:spring/infrastructure-persistence.xml");
		context.refresh();
		// TODO Auto-generated set userCode
		loginInfo.setUserCode(UMMPContext.AUTO_USER);
		codeQueryService = context.getBean(CodeQueryService.class);
		System.out.println("- load spring context success: [loginInfo:"
				+ loginInfo + ",codeQueryService:" + codeQueryService + "]");
	}

	/**
	 * 具体JOB开始方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract boolean excute() throws Exception;

	/**
	 * 数据提取具体实现方法：<br>
	 * 1、根据指定的实现类和平台提数，一般实现类与平台统一的 2、被保护的类，不能修改之 3、提数先从指定配置表T_DEF_CODE_DICT
	 * 中获取对应的参数 4、参数中AUTO_FLAG标识
	 * 1-自动，根据当前日期以及实际日期来进行提数，有必要则会重置日期，0-表示自定义的日期提数，指定的日期进行提数
	 * 5、该方法适用于一天提数一次的批，按照T+1的规则，否则自己实现excute() 方式实现提数
	 * 
	 * @param service
	 * @param comId
	 * @return
	 * @throws Exception
	 */
	protected boolean extract(BigDecimal comId) throws Exception {
		// TODO 业务实现
		System.out.println("================= current extract job begin and comId is " + comId + " =================");
		String startDate = null;// 起始日期
		String endDate = null;// 截至日期
		boolean result = false;// 提数结果
		Map<String, Object> tcdm = null;
		try {
			// 获取提数参数
			tcdm = getExtractParam(comId);
			tcdm.put("COM_ID", comId);
			startDate = (String) tcdm.get("START_DATE");
			endDate = (String) tcdm.get("END_DATE");
			if(3 == comId.intValue()){
				startDate = startDate + " 00:00:00";
				endDate = endDate + " 23:59:59";
			}
			ExtractService service = context.getBean(ExtractServiceImpl.class);
			System.out.println("- extract data beging ==> comId: " + comId + " StartDate: " + startDate + " endDate: " + endDate);
			// 查询传输
			TDefTransexcMapper tDefTransexcMapper = context.getBean(TDefTransexcMapper.class);
			List<TDefTransexc> list = tDefTransexcMapper.getTransByComId(comId);
			if (list == null || list.size() <= 0) {
				System.out.println("- there is not trans and platCom is " + comId);
				return true;
			}
			String tBatchNo = "TN-" + DateUtils.getCurrentDateAndTime();
			service.setBatchNo(tBatchNo);
			for (TDefTransexc trans : list) {
				System.out.println("- extract data dooing ==> trans :" + trans.getExcCode());
				BigDecimal excId = trans.getExcId();
				ParamPOJO pojo = new ParamPOJO();
				pojo.setStartDate(startDate);
				pojo.setEndDate(endDate);
				pojo.setBussNo("");// 设置为空，否则ORACEL匹配成NULL
				pojo.setUserCode(loginInfo.getUserCode());// 设置操作用户
				pojo.setComId(comId);// 设置操作平台
				service.clearException();
				result = service.extract(pojo, excId);
				if (!result) {
					System.out.println("- extract data failed and trans ：" + trans.getExcCode());
				}
			}
		} catch (Exception e) {
			DBContextHolder.recover();
			throw e;
		} finally {
			if(result) {
				if (comId.intValue() == 4) {
					Integer extractTimeInterval = Integer.valueOf((String) tcdm.get("EXTRACT_TIME_INTERVAL"));
					orderExtractParam(tcdm, extractTimeInterval, "m");
				} else {
					orderExtractParam(tcdm, 1, "D");
				}
			}
			
			System.out.println("================= current extract job is down =================");
		}
		return true;
	}

	/**
	 * 数据报送：
	 * 
	 * @param comId
	 *            平台编码
	 * @param bussType
	 *            业务类型
	 * @param startDate
	 *            起始日期
	 * @param endDate
	 *            截至日期
	 * @return
	 * @throws CISException
	 * @throws Exception
	 */
	public boolean submit(BigDecimal comId, String bussType, String startDate,
			String endDate, String bussNo) throws CISException, Exception {
		// 获取平台
		SubmitService service = (SubmitService) context
				.getBean(SubmitServiceImpl.class);
		System.out.println("================= current batchjob ：" + bussType
				+ " =================");
		System.out.println("================= StartDate: " + startDate
				+ " endDate: " + endDate + " =================");
		System.out
				.println("- 特别申明，当前报送不用日期锁定，符合条件的都将会报送(ps : no date well all data)");
		try {
			ParamPOJO pojo = new ParamPOJO();
			if (startDate != null && !"".equals(startDate)) {
				pojo.setStartDate(startDate);
			}
			if (endDate != null && !"".equals(endDate)) {
				pojo.setEndDate(endDate);
			}
			if (bussNo == null) {
				pojo.setBussNo("");
			}
			pojo.setBussType(bussType);
			pojo.setUserCode(loginInfo.getUserCode());// 设置操作用户
			pojo.setComId(comId);// 设置操作平台
			service.setApplicationContext(context);// 将容器值往后传递
			Map<String, String> map = service.submit(comId, pojo);
			System.out.println("- current batchjob result : " + map.get("msg"));
		} catch (Exception e) {
			throw e;
		} finally {
			System.out.println("================= current batchjob ："
					+ bussType + " down =================");
		}
		return true;
	}

	/**
	 * 根据要求重新定向参数
	 * 
	 * @param tcdm
	 * @param i
	 * @param unit
	 */
	@SuppressWarnings("unchecked")
	public void orderExtractParam(Map<String, Object> tcdm, int i, String unit) {
		System.out.println("- extract data over and orderint extract params ... ");
		Map<String, TDefCodeDict> tcds = (Map<String, TDefCodeDict>) tcdm.get("SYS_VAR");
		String startDate = (String) tcdm.get("START_DATE");
		String endDate = (String) tcdm.get("END_DATE");

		TDefCodeDict efCode = tcds.get("EXTRACT_FLAG");
		TDefCodeDict startCode = tcds.get("START_DATE");
		TDefCodeDict endCode = tcds.get("END_DATE");
		
		List<TDefCodeDict> ucds = new ArrayList<TDefCodeDict>();
		String newStartDate = calDate(startDate, i, unit);// 起始日期后置1
		String newEndDate = calDate(endDate, i, unit);// 截至日期后置1
		efCode.setCodeAlias("0");// 提数标识恢复
		startCode.setCodeAlias(newStartDate);
		endCode.setCodeAlias(newEndDate);
		ucds.add(startCode);
		ucds.add(endCode);
		ucds.add(efCode);
		updateCodeDicts(ucds);
	}

	/**
	 * 功能:得到某时间单位,在某个时间段内往后或往前推某个时间段的日期 日期格式：yyyy--MM--dd unit:Y,M,D
	 * 
	 * @param baseDate
	 *            相对日期
	 * @param interval
	 *            正值向后 负值向前
	 * @param unit
	 *            时间间隔单位，可以为 Y（年）、M（月）、D（天）
	 * @return
	 */
	public String calDate(String baseDate, int interval, String unit) {
		try {
			return DateUtils.calDate(baseDate, interval, unit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能:得到某时间单位,在某个时间段内往后或往前推某个时间段的日期 日期格式：yyyy--MM--dd unit:Y,M,D
	 * 
	 * @param baseDate
	 *            相对日期
	 * @param interval
	 *            正值向后 负值向前
	 * @param unit
	 *            时间间隔单位，可以为 Y（年）、M（月）、D（天）
	 * @return
	 */
	public Date calDate(Date baseDate, int interval, String unit) {
		return DateUtils.calDate(baseDate, interval, unit);
	}

	/**
	 * 获取指定字典表数据
	 * 
	 * @param codeType
	 * @param code
	 * @param comId
	 * @return
	 */
	public TDefCodeDict getCodeDict(String codeType, String code,
			BigDecimal comId) {
		return codeQueryService.getQueryCode(codeType, code, comId);
	}

	/**
	 * 根据类型获取字段表数据
	 * 
	 * @param codeType
	 * @param comId
	 * @return
	 */
	public Map<String, TDefCodeDict> getCodeDict(String codeType,BigDecimal comId) {
		Map<String, TDefCodeDict> rtvMap = new HashMap<String, TDefCodeDict>();
		List<TDefCodeDict> tcds = codeQueryService.queryCode(codeType, comId);
		for (TDefCodeDict tcd : tcds) {
			rtvMap.put(tcd.getCode(), tcd);
		}
		return rtvMap;
	}

	/**
	 * 更新字典表数据
	 * 
	 * @param codeDict
	 * @return
	 */
	public int updateCodeDict(TDefCodeDict codeDict) {
		return codeQueryService.updateQueryCode(codeDict);
	}

	/**
	 * 批量更新字典表数据
	 * 
	 * @param codeDict
	 * @return
	 */
	@Transactional
	public int updateCodeDicts(List<TDefCodeDict> codeDicts) {
		int i = 0;
		for (TDefCodeDict codeDict : codeDicts) {
			codeQueryService.updateQueryCode(codeDict);
			i++;
		}
		return i;
	}

	/**
	 * 获取提数参数，包括起始日期，截至日期等，需要再行添加
	 * 
	 * @param comId
	 * @return
	 */
	public Map<String, Object> getExtractParam(BigDecimal comId) {
		Map<String, TDefCodeDict> tcdm = getCodeDict("SYS_VAR", comId);
		Map<String, Object> rtvMap = new HashMap<String, Object>();
		String startDate = tcdm.get("START_DATE").getCodeAlias();
		String endDate = tcdm.get("END_DATE").getCodeAlias();
		String extractTimeInterva = null;
		if(4 == comId.intValue()) {
			extractTimeInterva = tcdm.get("EXTRACT_TIME_INTERVAL").getCodeAlias();
			rtvMap.put("EXTRACT_TIME_INTERVAL", extractTimeInterva);
		}
		String autoFlag = "1";// 默认自动，需要走自动恢复
		if (tcdm.get("AUTO_FLAG") != null) {
			autoFlag = tcdm.get("AUTO_FLAG").getCodeAlias();
		}
		if (autoFlag.equals("1")) {
			boolean is = isRevortExtract(startDate, comId, -1, "D");
			if (is) {
				// 重新获取起始日期和截至日期
				tcdm = getCodeDict("SYS_VAR", comId);
				startDate = tcdm.get("START_DATE").getCodeAlias();
				endDate = tcdm.get("END_DATE").getCodeAlias();
			}
		}
		
		rtvMap.put("SYS_VAR", tcdm);
		rtvMap.put("START_DATE", startDate);
		rtvMap.put("END_DATE", endDate);
		return rtvMap;
	}

	/**
	 * 恢复提数设置，防止提数异常导致后续提数不能进行，若默认标识非自动，则会默认修改为自动1
	 * 
	 * @param extractDate
	 *            实际提数日期
	 * @param comId
	 *            提数平台，参照T_DEF_PLAT_COM
	 * @param interval
	 *            正值向后 负值向前(与当前日期进行比较)
	 * @param unit
	 *            时间间隔单位，可以为 Y（年）、M（月）、D（天）
	 * @return 是否
	 */
	@Transactional
	public boolean isRevortExtract(String extractDate, BigDecimal comId,
			int interval, String unit) {
		String realDate = "";
		int i;
		realDate = DateUtils.getDateString(calDate(new Date(), interval, unit));
		try {
			i = DateUtils.compareDate(extractDate, realDate);
			if (i == 0) {
				return false;
			}
		} catch (ParseException e) {
				e.printStackTrace();
				// 日期相比异常，则默认更新同步系统日期实际提数日期
		}
		String noticeMsg = "- 提数日期，系统：" + extractDate + " 实际: " + realDate;
		System.out.println(noticeMsg);
		System.out.println("- 同步提数系统实际日期和系统日期...");
		List<TDefCodeDict> tcds = codeQueryService.queryCode("SYS_VAR", comId);
		for (TDefCodeDict tcd : tcds) {
			boolean upFlag = false;
			if (tcd != null && tcd.getCode().trim().equals("START_DATE")) {
				if(comId.intValue() == 4) {
					realDate = realDate.substring(0, 10) + " 00:00:00";
				}
				tcd.setCodeAlias(realDate);
				upFlag = true;
			}
			if (tcd != null && tcd.getCode().trim().equals("END_DATE")) {
				if(comId.intValue() == 4) {
					realDate = realDate.substring(0, 10) + " 00:30:00";
				}
				tcd.setCodeAlias(realDate);// 目前已提数日期和截至日期为一天
				upFlag = true;
		    }
			if (tcd != null && tcd.getCode().trim().equals("EXTRACT_FLAG")) {
				tcd.setCodeAlias("0");// 更改为非在提数状态
				upFlag = true;
			}
			if (tcd != null && tcd.getCode().trim().equals("AUTO_FLAG")
					&& tcd.getCodeAlias() != null
					&& tcd.getCodeAlias().equals("0")) {
				tcd.setCodeAlias("1");// 更改为自动校验状态
				upFlag = true;
			}
			if (upFlag) {
				// 恢复提数设置
				codeQueryService.updateQueryCode(tcd);
			}
		}
		String msg = "- 平台： comId=" + comId + " 系统提数日期与实际提数日期不一致，已同步!<br>"
				+ noticeMsg;
		Noticer noticer = new Noticer("【ICP 邮件提醒】 参数同步", msg);
		noticer.start();
		return true;
	}

	/**
	 * 通知器
	 * 
	 * @author Darker.Wang
	 * @date 2017-2-7 上午11:27:43
	 * @company SINOSOFT
	 */
	public class Noticer extends Thread {
		JobNoticer jobNoticer = new JobNoticer();
		String title;
		String content;

		public Noticer(String title, String content) {
			this.title = title;
			this.content = content;
		}

		@Override
		public void run() {
			jobNoticer.setApplicationContext(context);
			jobNoticer.setContent(content);
			jobNoticer.setTitle(title);
			jobNoticer.doNotice("SHELL_EXCEPTION");// 系统默认SHELL执行异常通知
		}
	}

	/**
	 * 得到当前系统日期 author: Darker.Wang
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: Darker.Wang modify by dongming 2009-01-09
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime() {
		String pattern = "HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 输入日期类型变量，返回日期字符串 yyyy-MM-dd
	 * 
	 * @param date
	 * @return String
	 */
	public static String getDateString(Date date) {
		String dateString = null;
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			dateString = simpleDateFormat.format(date);
		}
		return dateString;
	}

	/**
	 * <b>两个日期比较</b>
	 * <p>
	 * aDate 大于 bDate 返回 1， aDate 小于 bDate 返回 -1 ， aDate 等于 bDate 返回 0
	 * </p>
	 * 
	 * @param aDate
	 *            比较日期
	 * @param bDate
	 *            参照日期
	 * @return
	 * @throws ParseException
	 */
	public static int compareDate(String aDate, String bDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date tAdate = sdf.parse(aDate);
		Date tBdate = sdf.parse(bDate);
		int i = tAdate.compareTo(tBdate);
		return i;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SuperJob job = new PRPExtractDataJob();
		String extractDate = "2017-02-01 00:00:00";
		boolean realDate = job.isRevortExtract(extractDate, new BigDecimal(4),
				-1, "D");
		System.out.println(realDate);
	}

}
