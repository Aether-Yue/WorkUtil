/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.sinosoft.platform.core.application.SqlAdapterService;
import com.sinosoft.platform.core.domain.model.SqlAdapter;
import com.sinosoft.platform.core.domain.pojo.ParamPOJO;
import com.sinosoft.platform.core.exception.CoreException;
import com.sinosoft.ummp.application.CreateFileService;
import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.exception.CISExceptionRunable;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.util.UMMPContext;

import config.Config;

/**
 * 保单登记平台创建文件批处理<br>
 * 创建文件前会进行校验必须执行过的传输无都成功，否则不进行创建文件
 * @author Darker.Wang
 * @date 2016-10-25 下午4:53:28
 * @company SINOSOFT
 */
public class PRPCreateFileJob extends SuperJob {

	/**
	 * 
	 */
	public PRPCreateFileJob() {
		// TODO Auto-generated constructor stub
		super.setJobGroup("JOB_CREATE");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("================= 保单登记创建文件批处理开始 =================");
		ParamPOJO pojo = new ParamPOJO();
		try {
			int interval = Integer.parseInt(Config.getProperties().getProperty("PRP_INTERVAL"));
			String startDate = calDate(getCurrentDate(),interval,"D");
			String endDate = calDate(getCurrentDate(),interval,"D");
			System.out.println("================= StartDate: "+startDate+" endDate: "+endDate+" =================");
			
			//不能创建文件，则状态保持在1，后面的处理则不会进行 add by darker 2016-12-10 18:00
			if(!checkWhenCreateFile(startDate,endDate)){
				System.out.println("================= 保单登记创建文件批处理结束：不符合创建文件 =================");
				return true;
			}
			
			CreateFileService service = context.getBean(CreateFileService.class);
			
			pojo.setStartDate(startDate);
			pojo.setEndDate(endDate);
			
			pojo.setBussType("INC");
			pojo.setBussFlag("1");
			pojo.setBatchType("INC");
			pojo.setDataType("I");//寿险
			pojo.setInsureType("L");
			pojo.setUserCode(loginInfo.getUserCode());
			pojo.setComId(new BigDecimal(UMMPContext.SUBMIT_PLAT_PRP));
			
			return service.submitData(loginInfo, pojo);
		}catch(CoreException e){
			//无数据异常抓取
			if(e.getErrorCode().equals("NODATA")){
				System.out.println("无对应数据可生成文件："+pojo);
				return true;
			}
			CISException cis = new CISException(e.getMessage(),CISExceptionRunable.EXCEPTION_ERROR_L3);
			cis.setException(e);
			throw cis;
		}catch (Exception e) {
			throw e;
		}finally{
			System.out.println("================= 保单登记创建文件批处理结束 =================");
		}
	}
	/**
	 * 创建文件前校验
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private boolean checkWhenCreateFile(String startDate,String endDate){
		String sql = "select * from (SELECT DISTINCT BATCH_no,data_status,state_flag,buss_type,plat_com, "
				+" to_char(action_date,'yyyy-mm-dd') AS action_date,action_msg, "
				+" to_char(start_date,'yyyy-mm-dd') AS start_date, "
				+" to_char(end_date,'yyyy-mm-dd') AS end_date  "
				+" from t_def_action_trace WHERE sub_buss_type= 'EB' "
				+" AND plat_com = 1 AND start_date = DATE '"+startDate+"' AND end_date = DATE '"+endDate+"' "
				+" AND batch_no = (SELECT max(batch_no) FROM t_def_action_trace WHERE " 
				+" sub_buss_type= 'EB' "
				+" AND plat_com = 1 AND start_date = DATE '"+startDate+"' AND end_date = DATE '"+endDate+"' "
				+" ) AND data_status <> '1' ORDER BY batch_no ) WHERE ROWNUM = 1";
		SqlAdapterService sqlAdapterService = context.getBean(SqlAdapterService.class);
		System.out.println("- checkWhenCreateFile : "+sql);
		SqlAdapter sqlAdapter = new SqlAdapter();
		sqlAdapter.setSql(sql);
		List<Map> result = sqlAdapterService.excuteQuery(sqlAdapter);
		if(result != null && result.size() > 0){
			Map map = result.get(0);
			String data_status = (String) map.get("DATA_STATUS");
			String state_flag = (String) map.get("STATE_FLAG");
			String buss_type = (String) map.get("BUSS_TYPE");
			String action_msg = (String) map.get("ACTION_MSG");
			System.out.println("- 当前提数：buss_type : "+buss_type);
			System.out.println("- 提数存在：data_status : "+data_status);
			System.out.println("- 提数存在：state_flag : "+state_flag);
			System.out.println("- 提数存在：action_msg : "+action_msg);
			System.out.println("- 不能进行创建文件.");
			return false;
		}
		return true;
	}
}
