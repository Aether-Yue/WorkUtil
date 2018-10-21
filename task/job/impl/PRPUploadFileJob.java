/**
 * 
 */
package com.sinosoft.ummp.task.job.impl;

import com.sinosoft.platform.core.util.CError;
import com.sinosoft.ummp.application.UploadFileService;
import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.exception.CISExceptionRunable;
import com.sinosoft.ummp.task.SuperJob;
import com.sinosoft.ummp.task.job.UploadJob;

/**
 * 保单登记文件上载批处理
 * @author Darker.Wang
 * @date 2016-8-18 下午2:06:05
 * @company SINOSOFT
 */
public class PRPUploadFileJob extends SuperJob implements UploadJob{
	
	public PRPUploadFileJob(){
		//设置为报送群组
		this.setJobGroup("JOB_UPLOAD");
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.SuperJob#excute()
	 */
	@Override
	public boolean excute() throws Exception {
		// TODO Auto-generated method stub
		return uploadFile();
		
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.task.job.UploadJob#uploadFile(java.lang.String)
	 */
	@Override
	public boolean uploadFile() throws Exception {
		//获取平台
		System.out.println("================= 保单登记文件上载批处理开始 =================");
		UploadFileService service = (UploadFileService) context.getBean(UploadFileService.class);
		try{
			UploadFileService.cErrors.clearErrors();
			 boolean result = service.uploadFile(loginInfo);
			 if(!result){
				 CError cError = UploadFileService.cErrors.getError(0);
				 if(cError.errorType != null && cError.errorType.equals("0")){
					 new CISException(cError.errorMessage,CISExceptionRunable.EXCEPTION_ERROR_L3);
					 System.out.println("================= 保单登记文件上载批处理: "+cError.errorMessage);
				 }
			 }
		}catch(Exception e){
			throw e;
		}finally{
			System.out.println("================= 保单登记文件上载批处理结束 =================");	
		}
		return true;
	}
}
