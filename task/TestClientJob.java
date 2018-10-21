/**
 * 
 */
package com.sinosoft.ummp.task;


/**
 * JOB批处理测试端
 * @author Darker.Wang
 * @date 2016-8-11 下午8:30:35
 * @company SINOSOFT
 */
public class TestClientJob {
	//0、PRPExtractDataJob
	//1、PRPCreateFileJob 2、PRPUploadFileJob 
	//3、PRPSubmitIUJob   4、PRPSubmitIRQJob 
	//5、PRPSubmitSCJob   6、PRPSubmitSCRQJob
	//7、PRPSubmitFUJob   8、PRPSubmitFRQJob
	public static void main(String args[]){
		//提数
		args = new String[]{"SHIAExtractDataJob"}; 
		
		//报送
//		args = new String[]{"SHIASubmitDataJob"}; 
		
		//创建文件
//		args = new String[]{"PRPCreateFileJob"}; 
		
		//上载文件
//		args = new String[]{"PRPUploadFileJob"}; 
		
		//文件上载成功通知
//		args = new String[]{"PRPSubmitIUJob"}; 
		
		//批次处理结果查询
//		args = new String[]{"PRPSubmitIRQJob"};
		
//		Job.doJob(args);
	}
}
