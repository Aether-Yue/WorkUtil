/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sinosoft.platform.core.application.SqlAdapterService;
import com.sinosoft.platform.core.domain.model.SqlAdapter;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.SubmitJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * 保单登记报送批处理:SummaryCheck
 * @author Darker.Wang
 * @date 2016-8-18 下午2:06:05
 * @company SINOSOFT
 */
@Service
public class PRPSubmitSCJob extends SuperJob implements SubmitJob {
	
	public PRPSubmitSCJob(){
		//设置为报送群组
		this.setJobGroup("JOB_SUBMIT");
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		// TODO 获取前两天
		String startDate = calDate(getCurrentDate(),-2,"D");
		String endDate = calDate(getCurrentDate(),-2,"D");
		return submit(startDate,endDate);
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.job.SubmitJob#submit(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean submit(String startDate, String endDate) throws Exception {
		//获取平台
		System.out.println("================= 保单登记数据报送,汇总对账通知批处理开始 =================");
		try{
			if(!checkSumBefore(startDate,endDate)){
				System.out.println("- 当前汇总日期：startDate : "+startDate+" endDate : "+endDate+" 有错误数据，不能进行汇总对账！");
				return true;
			}
			String bussType = "SummaryCheck";
			String bussNo = "";
			return submit(new BigDecimal(UMMPContext.SUBMIT_PLAT_PRP), bussType, startDate, endDate, bussNo);
		}catch(Exception e){
			throw e;
		}finally{
			System.out.println("================= 保单登记数据报送，汇总对账通知批处理结束 =================");	
		}
	}
	/**
	 * 校验是否需要进行汇总
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private boolean checkSumBefore(String startDate, String endDate){
		String sql = "SELECT count(stateflag) AS counts  FROM t_prp_lcpoltransaction WHERE transdate BETWEEN DATE '"+startDate+"' AND DATE '"+endDate+"' AND stateflag LIKE '%E%'";
		SqlAdapter sqlAdapter = new SqlAdapter();
		sqlAdapter.setSql(sql);
		SqlAdapterService sqlAdapterService = context.getBean(SqlAdapterService.class);
		int i = sqlAdapterService.excuteQueryCount(sqlAdapter).intValue();
		if(i > 0){
			return false;
		}
		return true;
	}
}
