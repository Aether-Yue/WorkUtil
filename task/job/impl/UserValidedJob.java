package com.sinosoft.ummp.task.job.impl;

import com.sinosoft.platform.core.application.UserService;
import com.sinosoft.ummp.exception.CISException;
import com.sinosoft.ummp.exception.CISExceptionRunable;
import com.sinosoft.ummp.task.SuperJob;

/**
 * 用户过期批处理
 * @author Darker.Wang
 * @date 2016-9-1 下午5:55:04
 * @company SINOSOFT
 */
public class UserValidedJob extends SuperJob{
	
	/**
	 * 每个批处理必须在构造器中申明群组
	 */
	public UserValidedJob(){
		//申明群组：如有必要，一组JOB必须声明相同的群组
		this.setJobGroup("JOB_VALIDED");
	}
	@Override
	public boolean excute() {
		
		// TODO Auto-generated method stub
		UserService userService = (UserService) context.getBean("userService");
		if(userService == null){
			String mesg = "用户有效期批处理失败：原因是为获取容器中处理对象 UserService 为空";
			new CISException(mesg,CISExceptionRunable.EXCEPTION_ERROR_L5);
			System.out.println("UserValidedJob 执行失败：0");
			System.out.println(mesg);
			return false;
		}
		boolean result = userService.userValidityCheck();
		return result;
	}

}
