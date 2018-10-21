package com.sinosoft.platform.quartz.domain.model.base;

import com.sinosoft.platform.quartz.domain.model.dao.ScheduleinfoDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseScheduleinfoDAO {

	public BaseScheduleinfoDAO () {}

	// query name references


	public static ScheduleinfoDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static ScheduleinfoDAO getInstance () {
		if (null == instance) instance = new com.sinosoft.platform.quartz.domain.model.dao.ScheduleinfoDAO();
		return instance;
	}

  public Class<?> getReferenceClass () {
        return com.sinosoft.platform.quartz.domain.model.Scheduleinfo.class;
    }


    /**
     * Cast the object as a com.sinosoft.platform.quartz.domain.model.Scheduleinfo
     */
    public com.sinosoft.platform.quartz.domain.model.Scheduleinfo cast (Object object) {
		return null;
    }

    public com.sinosoft.platform.quartz.domain.model.Scheduleinfo load(java.lang.String key)
        throws org.springframework.dao.DataAccessException {
			return null;
    }

    public com.sinosoft.platform.quartz.domain.model.Scheduleinfo get(java.lang.String key)
        throws org.springframework.dao.DataAccessException {
			return null;
    }

    public java.util.List<?> loadAll()
        throws org.springframework.dao.DataAccessException {
			return null;
    }


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param scheduleinfo a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(com.sinosoft.platform.quartz.domain.model.Scheduleinfo scheduleinfo)
		throws org.springframework.dao.DataAccessException {
			return null;
	}



	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param scheduleinfo a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.sinosoft.platform.quartz.domain.model.Scheduleinfo scheduleinfo)
		throws org.springframework.dao.DataAccessException {
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param scheduleinfo a transient instance containing updated state
	 */
	public void update(com.sinosoft.platform.quartz.domain.model.Scheduleinfo scheduleinfo) 
		throws org.springframework.dao.DataAccessException {
	}



	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
		throws org.springframework.dao.DataAccessException {
	}



	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param scheduleinfo the instance to be removed
	 */
	public void delete(com.sinosoft.platform.quartz.domain.model.Scheduleinfo scheduleinfo)
		throws org.springframework.dao.DataAccessException {
	}
	
	


}