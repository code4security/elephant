package com.sjhy.platform.client.dto.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerBanListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PlayerBanListExample() {
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
     * 玩家封账号表

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

        public Criteria andRoleIdIsNull() {
            addCriterion("role_id is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("role_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Long value) {
            addCriterion("role_id =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Long value) {
            addCriterion("role_id <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Long value) {
            addCriterion("role_id >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("role_id >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Long value) {
            addCriterion("role_id <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Long value) {
            addCriterion("role_id <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Long> values) {
            addCriterion("role_id in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Long> values) {
            addCriterion("role_id not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Long value1, Long value2) {
            addCriterion("role_id between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Long value1, Long value2) {
            addCriterion("role_id not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andBanMinuteIsNull() {
            addCriterion("ban_minute is null");
            return (Criteria) this;
        }

        public Criteria andBanMinuteIsNotNull() {
            addCriterion("ban_minute is not null");
            return (Criteria) this;
        }

        public Criteria andBanMinuteEqualTo(Integer value) {
            addCriterion("ban_minute =", value, "banMinute");
            return (Criteria) this;
        }

        public Criteria andBanMinuteNotEqualTo(Integer value) {
            addCriterion("ban_minute <>", value, "banMinute");
            return (Criteria) this;
        }

        public Criteria andBanMinuteGreaterThan(Integer value) {
            addCriterion("ban_minute >", value, "banMinute");
            return (Criteria) this;
        }

        public Criteria andBanMinuteGreaterThanOrEqualTo(Integer value) {
            addCriterion("ban_minute >=", value, "banMinute");
            return (Criteria) this;
        }

        public Criteria andBanMinuteLessThan(Integer value) {
            addCriterion("ban_minute <", value, "banMinute");
            return (Criteria) this;
        }

        public Criteria andBanMinuteLessThanOrEqualTo(Integer value) {
            addCriterion("ban_minute <=", value, "banMinute");
            return (Criteria) this;
        }

        public Criteria andBanMinuteIn(List<Integer> values) {
            addCriterion("ban_minute in", values, "banMinute");
            return (Criteria) this;
        }

        public Criteria andBanMinuteNotIn(List<Integer> values) {
            addCriterion("ban_minute not in", values, "banMinute");
            return (Criteria) this;
        }

        public Criteria andBanMinuteBetween(Integer value1, Integer value2) {
            addCriterion("ban_minute between", value1, value2, "banMinute");
            return (Criteria) this;
        }

        public Criteria andBanMinuteNotBetween(Integer value1, Integer value2) {
            addCriterion("ban_minute not between", value1, value2, "banMinute");
            return (Criteria) this;
        }

        public Criteria andIsBanIsNull() {
            addCriterion("is_ban is null");
            return (Criteria) this;
        }

        public Criteria andIsBanIsNotNull() {
            addCriterion("is_ban is not null");
            return (Criteria) this;
        }

        public Criteria andIsBanEqualTo(Boolean value) {
            addCriterion("is_ban =", value, "isBan");
            return (Criteria) this;
        }

        public Criteria andIsBanNotEqualTo(Boolean value) {
            addCriterion("is_ban <>", value, "isBan");
            return (Criteria) this;
        }

        public Criteria andIsBanGreaterThan(Boolean value) {
            addCriterion("is_ban >", value, "isBan");
            return (Criteria) this;
        }

        public Criteria andIsBanGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_ban >=", value, "isBan");
            return (Criteria) this;
        }

        public Criteria andIsBanLessThan(Boolean value) {
            addCriterion("is_ban <", value, "isBan");
            return (Criteria) this;
        }

        public Criteria andIsBanLessThanOrEqualTo(Boolean value) {
            addCriterion("is_ban <=", value, "isBan");
            return (Criteria) this;
        }

        public Criteria andIsBanIn(List<Boolean> values) {
            addCriterion("is_ban in", values, "isBan");
            return (Criteria) this;
        }

        public Criteria andIsBanNotIn(List<Boolean> values) {
            addCriterion("is_ban not in", values, "isBan");
            return (Criteria) this;
        }

        public Criteria andIsBanBetween(Boolean value1, Boolean value2) {
            addCriterion("is_ban between", value1, value2, "isBan");
            return (Criteria) this;
        }

        public Criteria andIsBanNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_ban not between", value1, value2, "isBan");
            return (Criteria) this;
        }

        public Criteria andBanTimeIsNull() {
            addCriterion("ban_time is null");
            return (Criteria) this;
        }

        public Criteria andBanTimeIsNotNull() {
            addCriterion("ban_time is not null");
            return (Criteria) this;
        }

        public Criteria andBanTimeEqualTo(Date value) {
            addCriterion("ban_time =", value, "banTime");
            return (Criteria) this;
        }

        public Criteria andBanTimeNotEqualTo(Date value) {
            addCriterion("ban_time <>", value, "banTime");
            return (Criteria) this;
        }

        public Criteria andBanTimeGreaterThan(Date value) {
            addCriterion("ban_time >", value, "banTime");
            return (Criteria) this;
        }

        public Criteria andBanTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ban_time >=", value, "banTime");
            return (Criteria) this;
        }

        public Criteria andBanTimeLessThan(Date value) {
            addCriterion("ban_time <", value, "banTime");
            return (Criteria) this;
        }

        public Criteria andBanTimeLessThanOrEqualTo(Date value) {
            addCriterion("ban_time <=", value, "banTime");
            return (Criteria) this;
        }

        public Criteria andBanTimeIn(List<Date> values) {
            addCriterion("ban_time in", values, "banTime");
            return (Criteria) this;
        }

        public Criteria andBanTimeNotIn(List<Date> values) {
            addCriterion("ban_time not in", values, "banTime");
            return (Criteria) this;
        }

        public Criteria andBanTimeBetween(Date value1, Date value2) {
            addCriterion("ban_time between", value1, value2, "banTime");
            return (Criteria) this;
        }

        public Criteria andBanTimeNotBetween(Date value1, Date value2) {
            addCriterion("ban_time not between", value1, value2, "banTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 玩家封账号表

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