package com.sinosoft.platform.quartz.domain.model.base;

import com.sinosoft.platform.quartz.domain.model.dao.JobDetailsDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseJobDetailsDAO {

	public BaseJobDetailsDAO () {}

	// query name references


	public static JobDetailsDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static JobDetailsDAO getInstance () {
		if (null == instance) instance = new com.sinosoft.platform.quartz.domain.model.dao.JobDetailsDAO();
		return instance;
	}

  public Class<?> getReferenceClass () {
        return com.sinosoft.platform.quartz.domain.model.JobDetails.class;
    }


    /**
     * Cast the object as a com.sinosoft.platform.quartz.domain.model.JobDetails
     */
    public com.sinosoft.platform.quartz.domain.model.JobDetails cast (Object object) {
		return null;
    }
}