package com.sinosoft.platform.quartz.domain.model;

import java.util.ArrayList;
import java.util.List;

public class QrtzTriggerLogExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public QrtzTriggerLogExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdIsNull() {
			addCriterion("ID is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("ID is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(String value) {
			addCriterion("ID =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(String value) {
			addCriterion("ID <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(String value) {
			addCriterion("ID >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(String value) {
			addCriterion("ID >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(String value) {
			addCriterion("ID <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(String value) {
			addCriterion("ID <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLike(String value) {
			addCriterion("ID like", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotLike(String value) {
			addCriterion("ID not like", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<String> values) {
			addCriterion("ID in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<String> values) {
			addCriterion("ID not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(String value1, String value2) {
			addCriterion("ID between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(String value1, String value2) {
			addCriterion("ID not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andTriggerNameIsNull() {
			addCriterion("TRIGGER_NAME is null");
			return (Criteria) this;
		}

		public Criteria andTriggerNameIsNotNull() {
			addCriterion("TRIGGER_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andTriggerNameEqualTo(String value) {
			addCriterion("TRIGGER_NAME =", value, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameNotEqualTo(String value) {
			addCriterion("TRIGGER_NAME <>", value, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameGreaterThan(String value) {
			addCriterion("TRIGGER_NAME >", value, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameGreaterThanOrEqualTo(String value) {
			addCriterion("TRIGGER_NAME >=", value, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameLessThan(String value) {
			addCriterion("TRIGGER_NAME <", value, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameLessThanOrEqualTo(String value) {
			addCriterion("TRIGGER_NAME <=", value, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameLike(String value) {
			addCriterion("TRIGGER_NAME like", value, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameNotLike(String value) {
			addCriterion("TRIGGER_NAME not like", value, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameIn(List<String> values) {
			addCriterion("TRIGGER_NAME in", values, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameNotIn(List<String> values) {
			addCriterion("TRIGGER_NAME not in", values, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameBetween(String value1, String value2) {
			addCriterion("TRIGGER_NAME between", value1, value2, "triggerName");
			return (Criteria) this;
		}

		public Criteria andTriggerNameNotBetween(String value1, String value2) {
			addCriterion("TRIGGER_NAME not between", value1, value2,
					"triggerName");
			return (Criteria) this;
		}

		public Criteria andJobGroupIsNull() {
			addCriterion("JOB_GROUP is null");
			return (Criteria) this;
		}

		public Criteria andJobGroupIsNotNull() {
			addCriterion("JOB_GROUP is not null");
			return (Criteria) this;
		}

		public Criteria andJobGroupEqualTo(String value) {
			addCriterion("JOB_GROUP =", value, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupNotEqualTo(String value) {
			addCriterion("JOB_GROUP <>", value, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupGreaterThan(String value) {
			addCriterion("JOB_GROUP >", value, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupGreaterThanOrEqualTo(String value) {
			addCriterion("JOB_GROUP >=", value, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupLessThan(String value) {
			addCriterion("JOB_GROUP <", value, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupLessThanOrEqualTo(String value) {
			addCriterion("JOB_GROUP <=", value, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupLike(String value) {
			addCriterion("JOB_GROUP like", value, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupNotLike(String value) {
			addCriterion("JOB_GROUP not like", value, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupIn(List<String> values) {
			addCriterion("JOB_GROUP in", values, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupNotIn(List<String> values) {
			addCriterion("JOB_GROUP not in", values, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupBetween(String value1, String value2) {
			addCriterion("JOB_GROUP between", value1, value2, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobGroupNotBetween(String value1, String value2) {
			addCriterion("JOB_GROUP not between", value1, value2, "jobGroup");
			return (Criteria) this;
		}

		public Criteria andJobNameIsNull() {
			addCriterion("JOB_NAME is null");
			return (Criteria) this;
		}

		public Criteria andJobNameIsNotNull() {
			addCriterion("JOB_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andJobNameEqualTo(String value) {
			addCriterion("JOB_NAME =", value, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameNotEqualTo(String value) {
			addCriterion("JOB_NAME <>", value, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameGreaterThan(String value) {
			addCriterion("JOB_NAME >", value, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameGreaterThanOrEqualTo(String value) {
			addCriterion("JOB_NAME >=", value, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameLessThan(String value) {
			addCriterion("JOB_NAME <", value, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameLessThanOrEqualTo(String value) {
			addCriterion("JOB_NAME <=", value, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameLike(String value) {
			addCriterion("JOB_NAME like", value, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameNotLike(String value) {
			addCriterion("JOB_NAME not like", value, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameIn(List<String> values) {
			addCriterion("JOB_NAME in", values, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameNotIn(List<String> values) {
			addCriterion("JOB_NAME not in", values, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameBetween(String value1, String value2) {
			addCriterion("JOB_NAME between", value1, value2, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobNameNotBetween(String value1, String value2) {
			addCriterion("JOB_NAME not between", value1, value2, "jobName");
			return (Criteria) this;
		}

		public Criteria andJobDescIsNull() {
			addCriterion("JOB_DESC is null");
			return (Criteria) this;
		}

		public Criteria andJobDescIsNotNull() {
			addCriterion("JOB_DESC is not null");
			return (Criteria) this;
		}

		public Criteria andJobDescEqualTo(String value) {
			addCriterion("JOB_DESC =", value, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescNotEqualTo(String value) {
			addCriterion("JOB_DESC <>", value, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescGreaterThan(String value) {
			addCriterion("JOB_DESC >", value, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescGreaterThanOrEqualTo(String value) {
			addCriterion("JOB_DESC >=", value, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescLessThan(String value) {
			addCriterion("JOB_DESC <", value, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescLessThanOrEqualTo(String value) {
			addCriterion("JOB_DESC <=", value, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescLike(String value) {
			addCriterion("JOB_DESC like", value, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescNotLike(String value) {
			addCriterion("JOB_DESC not like", value, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescIn(List<String> values) {
			addCriterion("JOB_DESC in", values, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescNotIn(List<String> values) {
			addCriterion("JOB_DESC not in", values, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescBetween(String value1, String value2) {
			addCriterion("JOB_DESC between", value1, value2, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andJobDescNotBetween(String value1, String value2) {
			addCriterion("JOB_DESC not between", value1, value2, "jobDesc");
			return (Criteria) this;
		}

		public Criteria andStartTimeIsNull() {
			addCriterion("START_TIME is null");
			return (Criteria) this;
		}

		public Criteria andStartTimeIsNotNull() {
			addCriterion("START_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andStartTimeEqualTo(String value) {
			addCriterion("START_TIME =", value, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeNotEqualTo(String value) {
			addCriterion("START_TIME <>", value, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeGreaterThan(String value) {
			addCriterion("START_TIME >", value, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeGreaterThanOrEqualTo(String value) {
			addCriterion("START_TIME >=", value, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeLessThan(String value) {
			addCriterion("START_TIME <", value, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeLessThanOrEqualTo(String value) {
			addCriterion("START_TIME <=", value, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeLike(String value) {
			addCriterion("START_TIME like", value, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeNotLike(String value) {
			addCriterion("START_TIME not like", value, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeIn(List<String> values) {
			addCriterion("START_TIME in", values, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeNotIn(List<String> values) {
			addCriterion("START_TIME not in", values, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeBetween(String value1, String value2) {
			addCriterion("START_TIME between", value1, value2, "startTime");
			return (Criteria) this;
		}

		public Criteria andStartTimeNotBetween(String value1, String value2) {
			addCriterion("START_TIME not between", value1, value2, "startTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeIsNull() {
			addCriterion("END_TIME is null");
			return (Criteria) this;
		}

		public Criteria andEndTimeIsNotNull() {
			addCriterion("END_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andEndTimeEqualTo(String value) {
			addCriterion("END_TIME =", value, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeNotEqualTo(String value) {
			addCriterion("END_TIME <>", value, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeGreaterThan(String value) {
			addCriterion("END_TIME >", value, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
			addCriterion("END_TIME >=", value, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeLessThan(String value) {
			addCriterion("END_TIME <", value, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeLessThanOrEqualTo(String value) {
			addCriterion("END_TIME <=", value, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeLike(String value) {
			addCriterion("END_TIME like", value, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeNotLike(String value) {
			addCriterion("END_TIME not like", value, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeIn(List<String> values) {
			addCriterion("END_TIME in", values, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeNotIn(List<String> values) {
			addCriterion("END_TIME not in", values, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeBetween(String value1, String value2) {
			addCriterion("END_TIME between", value1, value2, "endTime");
			return (Criteria) this;
		}

		public Criteria andEndTimeNotBetween(String value1, String value2) {
			addCriterion("END_TIME not between", value1, value2, "endTime");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeIsNull() {
			addCriterion("EXECUTE_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeIsNotNull() {
			addCriterion("EXECUTE_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeEqualTo(Short value) {
			addCriterion("EXECUTE_TYPE =", value, "executeType");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeNotEqualTo(Short value) {
			addCriterion("EXECUTE_TYPE <>", value, "executeType");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeGreaterThan(Short value) {
			addCriterion("EXECUTE_TYPE >", value, "executeType");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeGreaterThanOrEqualTo(Short value) {
			addCriterion("EXECUTE_TYPE >=", value, "executeType");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeLessThan(Short value) {
			addCriterion("EXECUTE_TYPE <", value, "executeType");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeLessThanOrEqualTo(Short value) {
			addCriterion("EXECUTE_TYPE <=", value, "executeType");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeIn(List<Short> values) {
			addCriterion("EXECUTE_TYPE in", values, "executeType");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeNotIn(List<Short> values) {
			addCriterion("EXECUTE_TYPE not in", values, "executeType");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeBetween(Short value1, Short value2) {
			addCriterion("EXECUTE_TYPE between", value1, value2, "executeType");
			return (Criteria) this;
		}

		public Criteria andExecuteTypeNotBetween(Short value1, Short value2) {
			addCriterion("EXECUTE_TYPE not between", value1, value2,
					"executeType");
			return (Criteria) this;
		}

		public Criteria andRunStatusIsNull() {
			addCriterion("RUN_STATUS is null");
			return (Criteria) this;
		}

		public Criteria andRunStatusIsNotNull() {
			addCriterion("RUN_STATUS is not null");
			return (Criteria) this;
		}

		public Criteria andRunStatusEqualTo(Short value) {
			addCriterion("RUN_STATUS =", value, "runStatus");
			return (Criteria) this;
		}

		public Criteria andRunStatusNotEqualTo(Short value) {
			addCriterion("RUN_STATUS <>", value, "runStatus");
			return (Criteria) this;
		}

		public Criteria andRunStatusGreaterThan(Short value) {
			addCriterion("RUN_STATUS >", value, "runStatus");
			return (Criteria) this;
		}

		public Criteria andRunStatusGreaterThanOrEqualTo(Short value) {
			addCriterion("RUN_STATUS >=", value, "runStatus");
			return (Criteria) this;
		}

		public Criteria andRunStatusLessThan(Short value) {
			addCriterion("RUN_STATUS <", value, "runStatus");
			return (Criteria) this;
		}

		public Criteria andRunStatusLessThanOrEqualTo(Short value) {
			addCriterion("RUN_STATUS <=", value, "runStatus");
			return (Criteria) this;
		}

		public Criteria andRunStatusIn(List<Short> values) {
			addCriterion("RUN_STATUS in", values, "runStatus");
			return (Criteria) this;
		}

		public Criteria andRunStatusNotIn(List<Short> values) {
			addCriterion("RUN_STATUS not in", values, "runStatus");
			return (Criteria) this;
		}

		public Criteria andRunStatusBetween(Short value1, Short value2) {
			addCriterion("RUN_STATUS between", value1, value2, "runStatus");
			return (Criteria) this;
		}

		public Criteria andRunStatusNotBetween(Short value1, Short value2) {
			addCriterion("RUN_STATUS not between", value1, value2, "runStatus");
			return (Criteria) this;
		}

		public Criteria andLogFlagIsNull() {
			addCriterion("LOG_FLAG is null");
			return (Criteria) this;
		}

		public Criteria andLogFlagIsNotNull() {
			addCriterion("LOG_FLAG is not null");
			return (Criteria) this;
		}

		public Criteria andLogFlagEqualTo(Short value) {
			addCriterion("LOG_FLAG =", value, "logFlag");
			return (Criteria) this;
		}

		public Criteria andLogFlagNotEqualTo(Short value) {
			addCriterion("LOG_FLAG <>", value, "logFlag");
			return (Criteria) this;
		}

		public Criteria andLogFlagGreaterThan(Short value) {
			addCriterion("LOG_FLAG >", value, "logFlag");
			return (Criteria) this;
		}

		public Criteria andLogFlagGreaterThanOrEqualTo(Short value) {
			addCriterion("LOG_FLAG >=", value, "logFlag");
			return (Criteria) this;
		}

		public Criteria andLogFlagLessThan(Short value) {
			addCriterion("LOG_FLAG <", value, "logFlag");
			return (Criteria) this;
		}

		public Criteria andLogFlagLessThanOrEqualTo(Short value) {
			addCriterion("LOG_FLAG <=", value, "logFlag");
			return (Criteria) this;
		}

		public Criteria andLogFlagIn(List<Short> values) {
			addCriterion("LOG_FLAG in", values, "logFlag");
			return (Criteria) this;
		}

		public Criteria andLogFlagNotIn(List<Short> values) {
			addCriterion("LOG_FLAG not in", values, "logFlag");
			return (Criteria) this;
		}

		public Criteria andLogFlagBetween(Short value1, Short value2) {
			addCriterion("LOG_FLAG between", value1, value2, "logFlag");
			return (Criteria) this;
		}

		public Criteria andLogFlagNotBetween(Short value1, Short value2) {
			addCriterion("LOG_FLAG not between", value1, value2, "logFlag");
			return (Criteria) this;
		}

		public Criteria andLogMsgIsNull() {
			addCriterion("LOG_MSG is null");
			return (Criteria) this;
		}

		public Criteria andLogMsgIsNotNull() {
			addCriterion("LOG_MSG is not null");
			return (Criteria) this;
		}

		public Criteria andLogMsgEqualTo(String value) {
			addCriterion("LOG_MSG =", value, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgNotEqualTo(String value) {
			addCriterion("LOG_MSG <>", value, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgGreaterThan(String value) {
			addCriterion("LOG_MSG >", value, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgGreaterThanOrEqualTo(String value) {
			addCriterion("LOG_MSG >=", value, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgLessThan(String value) {
			addCriterion("LOG_MSG <", value, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgLessThanOrEqualTo(String value) {
			addCriterion("LOG_MSG <=", value, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgLike(String value) {
			addCriterion("LOG_MSG like", value, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgNotLike(String value) {
			addCriterion("LOG_MSG not like", value, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgIn(List<String> values) {
			addCriterion("LOG_MSG in", values, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgNotIn(List<String> values) {
			addCriterion("LOG_MSG not in", values, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgBetween(String value1, String value2) {
			addCriterion("LOG_MSG between", value1, value2, "logMsg");
			return (Criteria) this;
		}

		public Criteria andLogMsgNotBetween(String value1, String value2) {
			addCriterion("LOG_MSG not between", value1, value2, "logMsg");
			return (Criteria) this;
		}

		public Criteria andRcStatusIsNull() {
			addCriterion("RC_STATUS is null");
			return (Criteria) this;
		}

		public Criteria andRcStatusIsNotNull() {
			addCriterion("RC_STATUS is not null");
			return (Criteria) this;
		}

		public Criteria andRcStatusEqualTo(Short value) {
			addCriterion("RC_STATUS =", value, "rcStatus");
			return (Criteria) this;
		}

		public Criteria andRcStatusNotEqualTo(Short value) {
			addCriterion("RC_STATUS <>", value, "rcStatus");
			return (Criteria) this;
		}

		public Criteria andRcStatusGreaterThan(Short value) {
			addCriterion("RC_STATUS >", value, "rcStatus");
			return (Criteria) this;
		}

		public Criteria andRcStatusGreaterThanOrEqualTo(Short value) {
			addCriterion("RC_STATUS >=", value, "rcStatus");
			return (Criteria) this;
		}

		public Criteria andRcStatusLessThan(Short value) {
			addCriterion("RC_STATUS <", value, "rcStatus");
			return (Criteria) this;
		}

		public Criteria andRcStatusLessThanOrEqualTo(Short value) {
			addCriterion("RC_STATUS <=", value, "rcStatus");
			return (Criteria) this;
		}

		public Criteria andRcStatusIn(List<Short> values) {
			addCriterion("RC_STATUS in", values, "rcStatus");
			return (Criteria) this;
		}

		public Criteria andRcStatusNotIn(List<Short> values) {
			addCriterion("RC_STATUS not in", values, "rcStatus");
			return (Criteria) this;
		}

		public Criteria andRcStatusBetween(Short value1, Short value2) {
			addCriterion("RC_STATUS between", value1, value2, "rcStatus");
			return (Criteria) this;
		}

		public Criteria andRcStatusNotBetween(Short value1, Short value2) {
			addCriterion("RC_STATUS not between", value1, value2, "rcStatus");
			return (Criteria) this;
		}

		public Criteria andInsertTimeIsNull() {
			addCriterion("INSERT_TIME is null");
			return (Criteria) this;
		}

		public Criteria andInsertTimeIsNotNull() {
			addCriterion("INSERT_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andInsertTimeEqualTo(String value) {
			addCriterion("INSERT_TIME =", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeNotEqualTo(String value) {
			addCriterion("INSERT_TIME <>", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeGreaterThan(String value) {
			addCriterion("INSERT_TIME >", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeGreaterThanOrEqualTo(String value) {
			addCriterion("INSERT_TIME >=", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeLessThan(String value) {
			addCriterion("INSERT_TIME <", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeLessThanOrEqualTo(String value) {
			addCriterion("INSERT_TIME <=", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeLike(String value) {
			addCriterion("INSERT_TIME like", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeNotLike(String value) {
			addCriterion("INSERT_TIME not like", value, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeIn(List<String> values) {
			addCriterion("INSERT_TIME in", values, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeNotIn(List<String> values) {
			addCriterion("INSERT_TIME not in", values, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeBetween(String value1, String value2) {
			addCriterion("INSERT_TIME between", value1, value2, "insertTime");
			return (Criteria) this;
		}

		public Criteria andInsertTimeNotBetween(String value1, String value2) {
			addCriterion("INSERT_TIME not between", value1, value2,
					"insertTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeIsNull() {
			addCriterion("LAST_MODIFY_TIME is null");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeIsNotNull() {
			addCriterion("LAST_MODIFY_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeEqualTo(String value) {
			addCriterion("LAST_MODIFY_TIME =", value, "lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeNotEqualTo(String value) {
			addCriterion("LAST_MODIFY_TIME <>", value, "lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeGreaterThan(String value) {
			addCriterion("LAST_MODIFY_TIME >", value, "lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeGreaterThanOrEqualTo(String value) {
			addCriterion("LAST_MODIFY_TIME >=", value, "lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeLessThan(String value) {
			addCriterion("LAST_MODIFY_TIME <", value, "lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeLessThanOrEqualTo(String value) {
			addCriterion("LAST_MODIFY_TIME <=", value, "lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeLike(String value) {
			addCriterion("LAST_MODIFY_TIME like", value, "lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeNotLike(String value) {
			addCriterion("LAST_MODIFY_TIME not like", value, "lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeIn(List<String> values) {
			addCriterion("LAST_MODIFY_TIME in", values, "lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeNotIn(List<String> values) {
			addCriterion("LAST_MODIFY_TIME not in", values, "lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeBetween(String value1, String value2) {
			addCriterion("LAST_MODIFY_TIME between", value1, value2,
					"lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andLastModifyTimeNotBetween(String value1, String value2) {
			addCriterion("LAST_MODIFY_TIME not between", value1, value2,
					"lastModifyTime");
			return (Criteria) this;
		}

		public Criteria andOperatorIsNull() {
			addCriterion("OPERATOR is null");
			return (Criteria) this;
		}

		public Criteria andOperatorIsNotNull() {
			addCriterion("OPERATOR is not null");
			return (Criteria) this;
		}

		public Criteria andOperatorEqualTo(String value) {
			addCriterion("OPERATOR =", value, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorNotEqualTo(String value) {
			addCriterion("OPERATOR <>", value, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorGreaterThan(String value) {
			addCriterion("OPERATOR >", value, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorGreaterThanOrEqualTo(String value) {
			addCriterion("OPERATOR >=", value, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorLessThan(String value) {
			addCriterion("OPERATOR <", value, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorLessThanOrEqualTo(String value) {
			addCriterion("OPERATOR <=", value, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorLike(String value) {
			addCriterion("OPERATOR like", value, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorNotLike(String value) {
			addCriterion("OPERATOR not like", value, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorIn(List<String> values) {
			addCriterion("OPERATOR in", values, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorNotIn(List<String> values) {
			addCriterion("OPERATOR not in", values, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorBetween(String value1, String value2) {
			addCriterion("OPERATOR between", value1, value2, "operator");
			return (Criteria) this;
		}

		public Criteria andOperatorNotBetween(String value1, String value2) {
			addCriterion("OPERATOR not between", value1, value2, "operator");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table QRTZ_TRIGER_LOG
	 * @mbggenerated  Mon Aug 15 11:58:13 CST 2016
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table QRTZ_TRIGER_LOG
     *
     * @mbggenerated do_not_delete_during_merge Fri Jul 01 15:39:29 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}