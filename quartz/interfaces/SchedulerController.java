package com.sinosoft.platform.quartz.interfaces;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.sinosoft.platform.core.domain.model.TDefJdbc;
import com.sinosoft.platform.core.domain.model.TDefSql;
import com.sinosoft.platform.core.domain.model.TDefSqlInfo;
import com.sinosoft.platform.core.util.Constants;
import com.sinosoft.platform.core.util.DataGridModel;
import com.sinosoft.platform.core.util.LoginInfo;
import com.sinosoft.platform.quartz.application.SchedulerService;
import com.sinosoft.platform.quartz.domain.model.JobDetails;
import com.sinosoft.platform.quartz.domain.model.Tcschedulerallocate;

import config.Config;
@Controller
@RequestMapping("/quartz")
@SessionAttributes(Constants.USER_INFO_SESSION)
public class SchedulerController {
	
	@Autowired
	SchedulerService schedulerService;
	private ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger log = Logger.getLogger(SchedulerController.class);

	/**
	 * 跳转页面
	 * 批处理前置
	 */
	@RequestMapping(value="/batchPreposeUrl",method=RequestMethod.GET)
	public String batchPrepose(){
		return "platform/quartz/batchPrepose";
	}
	
	
	/**
	 * 分页查询
	 * 批处理校验列表
	 * @param dgm
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/getDataSqlCode",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDataList(DataGridModel dgm, String queryParam) {
		try {
			return schedulerService.getDataSqlCodeList(dgm, queryParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * 获取页面校验算法url
	 */
	@RequestMapping(value = "/addVerifySqlCodeUrl", method = RequestMethod.GET)
	public ModelAndView addVerifySqlCodeUrl(String flag,ModelMap map) {
		
		map.put("flag", flag);
		return new ModelAndView("platform/quartz/addVerifySqlCode", map);
	}
	
	/**
	 * 保存
	 * 前置校验算法配置信息
	 * @param request
	 * @param param
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveVerify" ,produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String,String> saveVerify(String param,String flag,ModelMap model){
		log.info("============param=========== "+ param);
		LoginInfo loginInfo = (LoginInfo)model.get(Constants.USER_INFO_SESSION);
		Map<String,String> result = new HashMap<String,String>();
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			List<LinkedHashMap<String,Object>> list  = objectMapper.readValue(param, List.class);
			 map = list.get(0);
			 //调用业务层进行保存数据s
			 if(schedulerService.saveVerifyInfo(map,flag,loginInfo))
			result.put("msg", "保存成功");
			 else {result.put("msg", "该算法编码已存在，请重新定义！");
			 }
		}catch (Exception e) {
			result.put("msg", "保存失败");
		}
		return result;
	}
	
	/**
	 * 更新
	 * 页面回显信息
	 * @return
	 */
	@RequestMapping(value = "/updateSchedulerUrl", method = RequestMethod.GET)
	public ModelAndView updateSchedulerUrl(BigDecimal id,ModelMap model) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		TDefSql  tDefSql= null;
		TDefSqlInfo tDefSqlInfo = null;
		TDefJdbc tDefJdbc = null; 
		try {
			
			map = schedulerService.findGetDataSqlCodeById(id);
			
			tDefSql = (TDefSql) map.get("tDefSql");
			tDefSqlInfo = (TDefSqlInfo) map.get("tDefSqlInfo");
			tDefJdbc = (TDefJdbc) map.get("tDefJdbc");
			
			model.addAttribute("sqlId",tDefSql.getSqlId());
			model.addAttribute("EsStatus",tDefSql.getEsStatus());
			model.addAttribute("SqlCode",tDefSql.getSqlCode());
			model.addAttribute("sqlType",tDefSql.getSqlType() );
			model.addAttribute("sqlFrom", tDefSql.getSqlFrom());
			model.addAttribute("sqlInto", tDefSql.getSqlInto());
			model.addAttribute("bussType", tDefSql.getBussType());
			model.addAttribute("sql", tDefSqlInfo.getSqlInfo());
			model.addAttribute("desc", tDefSql.getSqlDesc());
			model.addAttribute("dataSourceName", tDefJdbc.getJdbcCode());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("出错了!!!"+e.getMessage());
		}
		return  new ModelAndView("platform/quartz/updateVerifySqlCode",model);
	}

	
	/**
	 * 保存更新
	 * @param request
	 * @param param
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveUpdateVerify" ,produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String,String> saveUpdateVerify(HttpServletRequest request,String param,ModelMap model){
		
		log.info("============param=========== "+ param);
		LoginInfo loginInfo = (LoginInfo)model.get(Constants.USER_INFO_SESSION);
		Map<String,String> result = new HashMap<String,String>();
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			List<LinkedHashMap<String,Object>> list  = objectMapper.readValue(param, List.class);
			 map = list.get(0);
			 //调用业务层进行保存数据
			 schedulerService.saveUpdateVerifyInfo(map,loginInfo);
			result.put("msg", "保存成功");
		}catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "保存失败");
		}
		return result;
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteGetDataSqlCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteGetDataSqlCode(
			@RequestParam("id") List<BigDecimal> ids) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (BigDecimal id : ids) {
				schedulerService.deleteGetDataSqlCode(id);
			}
			map.put("mes", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mes", "删除失败");
		}
		return map;
	}
	
	/**
	 * 跳转页面
	 * 批处理调度
	 */
	@RequestMapping(value = "/listSchedulerUrl", method = RequestMethod.GET)
	public String listMenuUrl() {
		return "platform/quartz/listScheduler";
	}
	
	
	/**
	 * 分页查询
	 * 获取调度数据
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/queryList", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> queryList(DataGridModel dgm,
			HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String job_name = request.getParameter("job_name");
			String trigger_name = request.getParameter("trigger_name");
			return schedulerService.getPageList(dgm, job_name, trigger_name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 跳转页面
	 * 获取页面url
	 */
	@RequestMapping(value = "/addSchedulerUrl", method = RequestMethod.GET)
	public String addScheduler() {
		System.out.println("进入------addSchedule");
		return "platform/quartz/addScheduler";
	}
	
	/**
	 * 添加触发器
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveAdd", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, String> saveAdd(@RequestParam("list") String s, @RequestParam("id") List<BigDecimal> ids) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resultmap = new HashMap<String, String>();
		try {
			String tlist = java.net.URLDecoder.decode(s,Config.getEncoding());
			System.out.println("添加计划任务："+tlist);
			List<LinkedHashMap<String, Object>> list = objectMapper.readValue(tlist, List.class);
			map = list.get(0);
			map.put("sqlId", ids);
			System.out.println("savaAdd: "+map);
			schedulerService.schedule(map);
			resultmap.put("mes", "操作成功");
		} catch (JsonParseException e) {
			e.printStackTrace();
			resultmap.put("mes", "操作失败,原因是" + e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			resultmap.put("mes", "操作失败,原因是" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			resultmap.put("mes", "操作失败,原因是" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			resultmap.put("mes", "操作失败,原因是" + e.getMessage());
		}
		return resultmap;
	}

	/**
	 * 删除触发器
	 */
	@RequestMapping(value = "/deleteScheduler", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(
			@RequestParam("trigger_name") String[] trigger_names) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (String trigger_name : trigger_names) {
				schedulerService.removeTrigger(trigger_name, "DEFAULT");
			}
			map.put("mes", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mes", "删除失败");
		}
		return map;// 重定向
	}

	/**
	 * 暂停触发器
	 */
	@RequestMapping(value = "/pauseScheduler", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, String> pauseScheduler(
			@RequestParam("trigger_name") String[] trigger_names) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (String trigger_name : trigger_names) {
				schedulerService.pauseTrigger(trigger_name, "DEFAULT");
			}
			map.put("mes", "暂停成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mes", "暂停失败");
		}
		return map;// 重定向
	}

	/**
	 * 恢复触发器
	 */
	@RequestMapping(value = "/resumeScheduler", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> resumeScheduler(
			@RequestParam("trigger_name") String[] trigger_names) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (String trigger_name : trigger_names) {
				schedulerService.resumeTrigger(trigger_name, "DEFAULT");
			}
			map.put("mes", "恢复成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mes", "恢复失败");
		}
		return map;// 重定向
	}
	
	@RequestMapping(value="/getDescInfoByJobName",method=RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,String> getDescInfoByJobName(String jobName) throws Exception{
		log.info("=============jobName=============== "+ jobName);
		 Map<String,String> map = new HashMap<String,String>();
		 String info = 	schedulerService.getDescInfo(jobName);
		 map.put("info", info);
		 return map;
	}
	
	/**
	 * 跳转页面
	 * 批处理执行
	 */
	@RequestMapping(value="/handOperationUrl",method=RequestMethod.GET)
	public String handOperation(){
		
		return "platform/quartz/handOperation";
	}
	
	/**
	 * 跳转页面
	 * 批处理日志
	 */
	@RequestMapping(value="/batchLogUrl",method=RequestMethod.GET)
	public String batchLog(){
		return "platform/quartz/batchLog";
	}
	
	/**
	 * 分页查询
	 * 批处理日志
	 * @return
	 */
	@RequestMapping(value="/queryBatchLogList",method=RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,Object> queryBatchLogList(DataGridModel dgm ,String queryParam){
		
		try {
  			return schedulerService.queryBatchLogList(dgm,queryParam);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 分页查询实时监控信息列表
	 * @param dgm
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryMonitoringListInfo", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> queryMonitoringListInfo(DataGridModel dgm,
			HttpServletRequest request) {
		try {
  			return schedulerService.queryMonitoringList(dgm);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 获取所有任务
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryJob", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public List queryJob(DataGridModel dgm, HttpServletRequest request) {
		try {
			List queryJob = schedulerService.queryJob();
			return queryJob;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 获取存在所有任务
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryExistJob", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public List queryExistJob() {
		try {
			List queryJob = schedulerService.queryExistJob();
			return queryJob;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 获取基本任务查询页面
	 */
	@RequestMapping(value = "/listBaseTaskUrl", method = RequestMethod.GET)
	public String listBaseTask() {
		return "platform/quartz/listBaseTask";
	}

	/***
	 * 页面显示基本任务列表请求
	 * 
	 * @param dgm
	 * @param business
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/queryBaseTaskList", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> queryBaseTaskList(DataGridModel dgm,
			JobDetails jobDetails) throws Exception {
		System.out.println("查询出来：" + jobDetails.getJobName() + ""
				+ jobDetails.getId());
		return schedulerService.getPageList(dgm, jobDetails);
	}
	
	/**
	 * 
	 * 页面显示调度关系的列表请求
	 * @param dgm
	 * @param jobDetails
	 * @return
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/querSchedulerAllocateList", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> querSchedulerAllocateList(DataGridModel dgm,
			Tcschedulerallocate tcschedulerallocate) throws Exception {
		System.out.println("查询出来：" + tcschedulerallocate.getJobtname1() + ""
				+ tcschedulerallocate.getId());
		return schedulerService.getPageList(dgm, tcschedulerallocate);
	}

	/**
	 * 查询基本任务下拉名称列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/queryJobTnameCombox", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryJobTnameCombox() {
		JSONArray jsonArray;
		try {
			List reslist = new ArrayList();
			List list = schedulerService.queryJobTnameCombox();

			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				JobDetails tc = (JobDetails) list.get(i);
				map.put("schedulername", tc.getJobTname());
				map.put("id", tc.getJobTname());
				reslist.add(map);
			}
			jsonArray = new JSONArray();
			jsonArray.addAll(reslist);
			System.out.println("jsonArray0000:" + jsonArray.toJSONString());
			return jsonArray.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 根据基本任务名称下拉选择处理方法名 */
	@RequestMapping(value = "/queryJobNameCombox", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, String> queryJobNameCombox(JobDetails jobdetails) {
		System.out.println("---------:"+jobdetails.getJobTname());
		Map<String, String> map = new HashMap<String, String>();
		try {
			JobDetails tjobdetails = schedulerService
					.queryJobNameByTname(jobdetails.getJobTname());
			map.put("jobname", tjobdetails.getJobName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取页面新增基本任务页面
	 */
	@RequestMapping(value = "/addBaseTaskUrl", method = RequestMethod.GET)
	public String addBaseTask() {
		System.out.println("进入addBaseTask");
		return "platform/quartz/addBaseTask";
	}

	/**
	 * 新增基本任务的实现方法
	 * 
	 * */
	@RequestMapping(value = "/saveAddBaseTask", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, String> saveAddBaseTask(JobDetails jobdetails) {

		System.out.println("-----saveAdd--:" + jobdetails.getMakedate()+"||"+jobdetails.getJobName()+jobdetails.getJobTname()+jobdetails.getRemark1());

		Map<String, String> map = new HashMap<String, String>();
		try {

			schedulerService.addBaseTask(jobdetails);
			map.put("mes", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mes", "操作失败,原因是" + e.getMessage());
		}
		return map;
	}
	/**
	 * 新增调度关系配置的实现方法
	 * 
	 */
	@RequestMapping(value = "/saveAddSchedulerAllocate", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, String>saveAddSchedulerAllocate(Tcschedulerallocate tcschedulerallocate) {

		System.out.println("-----saveAdd--:" + tcschedulerallocate.getJobtname1()+"||"+tcschedulerallocate.getJobtname2());

		Map<String, String> map = new HashMap<String, String>();
		try {

			schedulerService.addSchedulerAllocate(tcschedulerallocate);
			map.put("mes", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mes", "操作失败,原因是" + e.getMessage());
		}
		return map;
	}
	
	
	/**
	 * 获取调度配置的页面
	 */
	@RequestMapping(value = "/listSchedulerAllocateUrl", method = RequestMethod.GET)
	public String listSchdulerAllocate() {
		System.out.println("进入------listSchedulerAllocate");
		return  "platform/quartz/listQuartzAllocate";
	}
	
	/**
	 * 获取新增调度分配的页面
	 */
 
	@RequestMapping(value = "/addQuartzAllocateUrl", method = RequestMethod.GET)
	public String addSchdulerAllocate() {
		System.out.println("进入------addQuartzAllocate");
		return "platform/quartz/addQuartzAllocate";
	}
	/**
	 * 
	 * listPathQueryUrl
	 */

	@RequestMapping(value = "/listPathQueryUrl", method = RequestMethod.GET)
	public String listPathQuery() {
		System.out.println("进入--批处理操作轨迹查询----listPathQuery");
		return "platform/quartz/listPathQuery";
	}

	/**
	 * 获取页面url
	 */
	@RequestMapping(value = "/handSchedulerUrl", method = RequestMethod.GET)
	public String handSchedulerUrl() {
		return "platform/quartz/handScheduler";
	}

	/**
	 * 手动执行NFO接口批处理
	 */
	@RequestMapping(value = "/handDealTaskNFOUrl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> handDealTaskNFO() {
		HashMap<String, String> msgMap = new HashMap<String, String>();
		try {
			msgMap.put("msg", "success");
		} catch (Exception e1) {
			e1.printStackTrace();
			msgMap.put("msg", "操作失败,原因是：出现未知异常");
		}
		return msgMap;
	}
	
	@RequestMapping(value = "/deleteTriggerLogs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteTriggerLogs(String logWork,String startTime,String endTime) {
		HashMap<String, String> msgMap = new HashMap<String, String>();
		try {
			int i = schedulerService.deleteTriggerLogs(logWork,startTime,endTime);
			if(i >= 0){
				msgMap.put("msg", "删除成功，共删除："+i+" 条记录。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			msgMap.put("msg", "操作失败,原因是：出现未知异常");
		}
		return msgMap;
	}
	@RequestMapping(value = "/deleteTriggerLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteTriggerLog(String id) {
		HashMap<String, String> msgMap = new HashMap<String, String>();
		try {
			int i = schedulerService.deleteTriggerLog(id);
			if(i >= 0){
				msgMap.put("msg", "删除成功，共删除："+i+" 条记录。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			msgMap.put("msg", "操作失败,原因是：出现未知异常");
		}
		return msgMap;
	}
}
