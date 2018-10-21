package com.sinosoft.platform.quartz.domain.model;

import java.util.ArrayList;
import java.util.List;

public class QrtzJobDetailsExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    public QrtzJobDetailsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
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

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
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

        public Criteria andDescriptionIsNull() {
            addCriterion("DESCRIPTION is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("DESCRIPTION is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("DESCRIPTION =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("DESCRIPTION <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("DESCRIPTION >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("DESCRIPTION <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("DESCRIPTION like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("DESCRIPTION not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("DESCRIPTION in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("DESCRIPTION not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("DESCRIPTION between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPTION not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andJobClassNameIsNull() {
            addCriterion("JOB_CLASS_NAME is null");
            return (Criteria) this;
        }

        public Criteria andJobClassNameIsNotNull() {
            addCriterion("JOB_CLASS_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andJobClassNameEqualTo(String value) {
            addCriterion("JOB_CLASS_NAME =", value, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameNotEqualTo(String value) {
            addCriterion("JOB_CLASS_NAME <>", value, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameGreaterThan(String value) {
            addCriterion("JOB_CLASS_NAME >", value, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("JOB_CLASS_NAME >=", value, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameLessThan(String value) {
            addCriterion("JOB_CLASS_NAME <", value, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameLessThanOrEqualTo(String value) {
            addCriterion("JOB_CLASS_NAME <=", value, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameLike(String value) {
            addCriterion("JOB_CLASS_NAME like", value, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameNotLike(String value) {
            addCriterion("JOB_CLASS_NAME not like", value, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameIn(List<String> values) {
            addCriterion("JOB_CLASS_NAME in", values, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameNotIn(List<String> values) {
            addCriterion("JOB_CLASS_NAME not in", values, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameBetween(String value1, String value2) {
            addCriterion("JOB_CLASS_NAME between", value1, value2, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andJobClassNameNotBetween(String value1, String value2) {
            addCriterion("JOB_CLASS_NAME not between", value1, value2, "jobClassName");
            return (Criteria) this;
        }

        public Criteria andIsDurableIsNull() {
            addCriterion("IS_DURABLE is null");
            return (Criteria) this;
        }

        public Criteria andIsDurableIsNotNull() {
            addCriterion("IS_DURABLE is not null");
            return (Criteria) this;
        }

        public Criteria andIsDurableEqualTo(String value) {
            addCriterion("IS_DURABLE =", value, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableNotEqualTo(String value) {
            addCriterion("IS_DURABLE <>", value, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableGreaterThan(String value) {
            addCriterion("IS_DURABLE >", value, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableGreaterThanOrEqualTo(String value) {
            addCriterion("IS_DURABLE >=", value, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableLessThan(String value) {
            addCriterion("IS_DURABLE <", value, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableLessThanOrEqualTo(String value) {
            addCriterion("IS_DURABLE <=", value, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableLike(String value) {
            addCriterion("IS_DURABLE like", value, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableNotLike(String value) {
            addCriterion("IS_DURABLE not like", value, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableIn(List<String> values) {
            addCriterion("IS_DURABLE in", values, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableNotIn(List<String> values) {
            addCriterion("IS_DURABLE not in", values, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableBetween(String value1, String value2) {
            addCriterion("IS_DURABLE between", value1, value2, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsDurableNotBetween(String value1, String value2) {
            addCriterion("IS_DURABLE not between", value1, value2, "isDurable");
            return (Criteria) this;
        }

        public Criteria andIsVolatileIsNull() {
            addCriterion("IS_VOLATILE is null");
            return (Criteria) this;
        }

        public Criteria andIsVolatileIsNotNull() {
            addCriterion("IS_VOLATILE is not null");
            return (Criteria) this;
        }

        public Criteria andIsVolatileEqualTo(String value) {
            addCriterion("IS_VOLATILE =", value, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileNotEqualTo(String value) {
            addCriterion("IS_VOLATILE <>", value, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileGreaterThan(String value) {
            addCriterion("IS_VOLATILE >", value, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileGreaterThanOrEqualTo(String value) {
            addCriterion("IS_VOLATILE >=", value, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileLessThan(String value) {
            addCriterion("IS_VOLATILE <", value, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileLessThanOrEqualTo(String value) {
            addCriterion("IS_VOLATILE <=", value, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileLike(String value) {
            addCriterion("IS_VOLATILE like", value, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileNotLike(String value) {
            addCriterion("IS_VOLATILE not like", value, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileIn(List<String> values) {
            addCriterion("IS_VOLATILE in", values, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileNotIn(List<String> values) {
            addCriterion("IS_VOLATILE not in", values, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileBetween(String value1, String value2) {
            addCriterion("IS_VOLATILE between", value1, value2, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsVolatileNotBetween(String value1, String value2) {
            addCriterion("IS_VOLATILE not between", value1, value2, "isVolatile");
            return (Criteria) this;
        }

        public Criteria andIsStatefulIsNull() {
            addCriterion("IS_STATEFUL is null");
            return (Criteria) this;
        }

        public Criteria andIsStatefulIsNotNull() {
            addCriterion("IS_STATEFUL is not null");
            return (Criteria) this;
        }

        public Criteria andIsStatefulEqualTo(String value) {
            addCriterion("IS_STATEFUL =", value, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulNotEqualTo(String value) {
            addCriterion("IS_STATEFUL <>", value, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulGreaterThan(String value) {
            addCriterion("IS_STATEFUL >", value, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulGreaterThanOrEqualTo(String value) {
            addCriterion("IS_STATEFUL >=", value, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulLessThan(String value) {
            addCriterion("IS_STATEFUL <", value, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulLessThanOrEqualTo(String value) {
            addCriterion("IS_STATEFUL <=", value, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulLike(String value) {
            addCriterion("IS_STATEFUL like", value, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulNotLike(String value) {
            addCriterion("IS_STATEFUL not like", value, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulIn(List<String> values) {
            addCriterion("IS_STATEFUL in", values, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulNotIn(List<String> values) {
            addCriterion("IS_STATEFUL not in", values, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulBetween(String value1, String value2) {
            addCriterion("IS_STATEFUL between", value1, value2, "isStateful");
            return (Criteria) this;
        }

        public Criteria andIsStatefulNotBetween(String value1, String value2) {
            addCriterion("IS_STATEFUL not between", value1, value2, "isStateful");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryIsNull() {
            addCriterion("REQUESTS_RECOVERY is null");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryIsNotNull() {
            addCriterion("REQUESTS_RECOVERY is not null");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryEqualTo(String value) {
            addCriterion("REQUESTS_RECOVERY =", value, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryNotEqualTo(String value) {
            addCriterion("REQUESTS_RECOVERY <>", value, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryGreaterThan(String value) {
            addCriterion("REQUESTS_RECOVERY >", value, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryGreaterThanOrEqualTo(String value) {
            addCriterion("REQUESTS_RECOVERY >=", value, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryLessThan(String value) {
            addCriterion("REQUESTS_RECOVERY <", value, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryLessThanOrEqualTo(String value) {
            addCriterion("REQUESTS_RECOVERY <=", value, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryLike(String value) {
            addCriterion("REQUESTS_RECOVERY like", value, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryNotLike(String value) {
            addCriterion("REQUESTS_RECOVERY not like", value, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryIn(List<String> values) {
            addCriterion("REQUESTS_RECOVERY in", values, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryNotIn(List<String> values) {
            addCriterion("REQUESTS_RECOVERY not in", values, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryBetween(String value1, String value2) {
            addCriterion("REQUESTS_RECOVERY between", value1, value2, "requestsRecovery");
            return (Criteria) this;
        }

        public Criteria andRequestsRecoveryNotBetween(String value1, String value2) {
            addCriterion("REQUESTS_RECOVERY not between", value1, value2, "requestsRecovery");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 06 17:03:54 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table QRTZ_JOB_DETAILS
     *
     * @mbggenerated Mon Jun 06 17:03:54 CST 2016
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

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
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
}