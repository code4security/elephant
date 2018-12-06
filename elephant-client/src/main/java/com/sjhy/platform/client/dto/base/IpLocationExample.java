package com.sjhy.platform.client.dto.base;

import java.util.ArrayList;
import java.util.List;

public class IpLocationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IpLocationExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * ip记录表
     * 
     * @author HJ
     * 
     * @date 2018-12-06
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

        public Criteria andStartIpNumIsNull() {
            addCriterion("start_ip_num is null");
            return (Criteria) this;
        }

        public Criteria andStartIpNumIsNotNull() {
            addCriterion("start_ip_num is not null");
            return (Criteria) this;
        }

        public Criteria andStartIpNumEqualTo(Long value) {
            addCriterion("start_ip_num =", value, "startIpNum");
            return (Criteria) this;
        }

        public Criteria andStartIpNumNotEqualTo(Long value) {
            addCriterion("start_ip_num <>", value, "startIpNum");
            return (Criteria) this;
        }

        public Criteria andStartIpNumGreaterThan(Long value) {
            addCriterion("start_ip_num >", value, "startIpNum");
            return (Criteria) this;
        }

        public Criteria andStartIpNumGreaterThanOrEqualTo(Long value) {
            addCriterion("start_ip_num >=", value, "startIpNum");
            return (Criteria) this;
        }

        public Criteria andStartIpNumLessThan(Long value) {
            addCriterion("start_ip_num <", value, "startIpNum");
            return (Criteria) this;
        }

        public Criteria andStartIpNumLessThanOrEqualTo(Long value) {
            addCriterion("start_ip_num <=", value, "startIpNum");
            return (Criteria) this;
        }

        public Criteria andStartIpNumIn(List<Long> values) {
            addCriterion("start_ip_num in", values, "startIpNum");
            return (Criteria) this;
        }

        public Criteria andStartIpNumNotIn(List<Long> values) {
            addCriterion("start_ip_num not in", values, "startIpNum");
            return (Criteria) this;
        }

        public Criteria andStartIpNumBetween(Long value1, Long value2) {
            addCriterion("start_ip_num between", value1, value2, "startIpNum");
            return (Criteria) this;
        }

        public Criteria andStartIpNumNotBetween(Long value1, Long value2) {
            addCriterion("start_ip_num not between", value1, value2, "startIpNum");
            return (Criteria) this;
        }

        public Criteria andEndIpNumIsNull() {
            addCriterion("end_ip_num is null");
            return (Criteria) this;
        }

        public Criteria andEndIpNumIsNotNull() {
            addCriterion("end_ip_num is not null");
            return (Criteria) this;
        }

        public Criteria andEndIpNumEqualTo(Long value) {
            addCriterion("end_ip_num =", value, "endIpNum");
            return (Criteria) this;
        }

        public Criteria andEndIpNumNotEqualTo(Long value) {
            addCriterion("end_ip_num <>", value, "endIpNum");
            return (Criteria) this;
        }

        public Criteria andEndIpNumGreaterThan(Long value) {
            addCriterion("end_ip_num >", value, "endIpNum");
            return (Criteria) this;
        }

        public Criteria andEndIpNumGreaterThanOrEqualTo(Long value) {
            addCriterion("end_ip_num >=", value, "endIpNum");
            return (Criteria) this;
        }

        public Criteria andEndIpNumLessThan(Long value) {
            addCriterion("end_ip_num <", value, "endIpNum");
            return (Criteria) this;
        }

        public Criteria andEndIpNumLessThanOrEqualTo(Long value) {
            addCriterion("end_ip_num <=", value, "endIpNum");
            return (Criteria) this;
        }

        public Criteria andEndIpNumIn(List<Long> values) {
            addCriterion("end_ip_num in", values, "endIpNum");
            return (Criteria) this;
        }

        public Criteria andEndIpNumNotIn(List<Long> values) {
            addCriterion("end_ip_num not in", values, "endIpNum");
            return (Criteria) this;
        }

        public Criteria andEndIpNumBetween(Long value1, Long value2) {
            addCriterion("end_ip_num between", value1, value2, "endIpNum");
            return (Criteria) this;
        }

        public Criteria andEndIpNumNotBetween(Long value1, Long value2) {
            addCriterion("end_ip_num not between", value1, value2, "endIpNum");
            return (Criteria) this;
        }

        public Criteria andLocidIsNull() {
            addCriterion("locid is null");
            return (Criteria) this;
        }

        public Criteria andLocidIsNotNull() {
            addCriterion("locid is not null");
            return (Criteria) this;
        }

        public Criteria andLocidEqualTo(Integer value) {
            addCriterion("locid =", value, "locid");
            return (Criteria) this;
        }

        public Criteria andLocidNotEqualTo(Integer value) {
            addCriterion("locid <>", value, "locid");
            return (Criteria) this;
        }

        public Criteria andLocidGreaterThan(Integer value) {
            addCriterion("locid >", value, "locid");
            return (Criteria) this;
        }

        public Criteria andLocidGreaterThanOrEqualTo(Integer value) {
            addCriterion("locid >=", value, "locid");
            return (Criteria) this;
        }

        public Criteria andLocidLessThan(Integer value) {
            addCriterion("locid <", value, "locid");
            return (Criteria) this;
        }

        public Criteria andLocidLessThanOrEqualTo(Integer value) {
            addCriterion("locid <=", value, "locid");
            return (Criteria) this;
        }

        public Criteria andLocidIn(List<Integer> values) {
            addCriterion("locid in", values, "locid");
            return (Criteria) this;
        }

        public Criteria andLocidNotIn(List<Integer> values) {
            addCriterion("locid not in", values, "locid");
            return (Criteria) this;
        }

        public Criteria andLocidBetween(Integer value1, Integer value2) {
            addCriterion("locid between", value1, value2, "locid");
            return (Criteria) this;
        }

        public Criteria andLocidNotBetween(Integer value1, Integer value2) {
            addCriterion("locid not between", value1, value2, "locid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * ip记录表
     * 
     * @author HJ
     * 
     * @date 2018-12-06
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