/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.platform.core.domain.pojo.ParamPOJO;
import com.sinosoft.ummp.application.DealReturnFileService;
import com.sinosoft.ummp.domain.mapper.TPrpLcpoltransactionReturnMapper;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.util.UMMPContext;

/**
 * 保单登记报送批处理:返回文件处理
 * @author Darker.Wang
 * @date 2016-8-18 下午2:06:05
 * @company SINOSOFT
 */
//@Service
public class PRPDealReturnJob extends SuperJob{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean excute() throws Exception {
		try {
			String startDate = calDate(getCurrentDate(),-1,"D");
			String endDate = calDate(getCurrentDate(),-1,"D");
			System.out.println("- PRPDealReturnJob 错误文件批处理开始  StartDate: "+startDate+" endDate: "+endDate+" =================");
			TPrpLcpoltransactionReturnMapper returnMapper = context.getBean(TPrpLcpoltransactionReturnMapper.class);
			Map<String,String> map=new HashMap<String,String>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			List<Map> list = returnMapper.selectReturnFile(map);
			if(list.isEmpty()){
				System.out.println(startDate+" : 没有需要处理的错误返回文件");
				return true;
			}else{
				DealReturnFileService dealService = context.getBean(DealReturnFileService.class);
				for(Map file:list){
					String filePath = (String) file.get("ERRINFOPATH");
					String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
					System.out.println("- 开始处理返回错误文件："+fileName);
					ParamPOJO pojo=new ParamPOJO();
					pojo.setFileName(fileName);
					pojo.setUserCode(loginInfo.getUserCode());//设置操作用户
					pojo.setComId(new BigDecimal(UMMPContext.SUBMIT_PLAT_PRP));//设置操作平台
					boolean result = dealService.submitData(pojo);
					if(result){
						System.out.println("- 错误文件处理成功："+fileName);
					}
//					String title = "【ICP 邮件提醒】 保单登记错误文件处理完毕";
//					String content = "- 保单登记提数：startDate: "+startDate+" endDate: "+endDate+" 存在错误文件，系统已处理完毕!";
//					Noticer noticer = new Noticer(title,content);
//					noticer.start();
					return result;
				}
			}
		}catch (Exception e) {
			throw e;
		}finally{
			System.out.println("- PRPDealReturnJob 错误文件批处理结束");
		}
		return true;
	}
	public static void main(String[] args) {
		String a="/data/download/000031/000031LINC0003402048.zip";
		String fileName = a.substring(a.lastIndexOf("/")+1);
		System.out.println(fileName);
	}
}
