package com.sjhy.platform.client.dto.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerLipinmaListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PlayerLipinmaListExample() {
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
     * 玩家领取的礼品码记录信息表
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLpListIdIsNull() {
            addCriterion("lp_list_id is null");
            return (Criteria) this;
        }

        public Criteria andLpListIdIsNotNull() {
            addCriterion("lp_list_id is not null");
            return (Criteria) this;
        }

        public Criteria andLpListIdEqualTo(String value) {
            addCriterion("lp_list_id =", value, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdNotEqualTo(String value) {
            addCriterion("lp_list_id <>", value, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdGreaterThan(String value) {
            addCriterion("lp_list_id >", value, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdGreaterThanOrEqualTo(String value) {
            addCriterion("lp_list_id >=", value, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdLessThan(String value) {
            addCriterion("lp_list_id <", value, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdLessThanOrEqualTo(String value) {
            addCriterion("lp_list_id <=", value, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdLike(String value) {
            addCriterion("lp_list_id like", value, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdNotLike(String value) {
            addCriterion("lp_list_id not like", value, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdIn(List<String> values) {
            addCriterion("lp_list_id in", values, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdNotIn(List<String> values) {
            addCriterion("lp_list_id not in", values, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdBetween(String value1, String value2) {
            addCriterion("lp_list_id between", value1, value2, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpListIdNotBetween(String value1, String value2) {
            addCriterion("lp_list_id not between", value1, value2, "lpListId");
            return (Criteria) this;
        }

        public Criteria andLpIdIsNull() {
            addCriterion("lp_id is null");
            return (Criteria) this;
        }

        public Criteria andLpIdIsNotNull() {
            addCriterion("lp_id is not null");
            return (Criteria) this;
        }

        public Criteria andLpIdEqualTo(Integer value) {
            addCriterion("lp_id =", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdNotEqualTo(Integer value) {
            addCriterion("lp_id <>", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdGreaterThan(Integer value) {
            addCriterion("lp_id >", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("lp_id >=", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdLessThan(Integer value) {
            addCriterion("lp_id <", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdLessThanOrEqualTo(Integer value) {
            addCriterion("lp_id <=", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdIn(List<Integer> values) {
            addCriterion("lp_id in", values, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdNotIn(List<Integer> values) {
            addCriterion("lp_id not in", values, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdBetween(Integer value1, Integer value2) {
            addCriterion("lp_id between", value1, value2, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdNotBetween(Integer value1, Integer value2) {
            addCriterion("lp_id not between", value1, value2, "lpId");
            return (Criteria) this;
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

        public Criteria andPlayerIdIsNull() {
            addCriterion("player_id is null");
            return (Criteria) this;
        }

        public Criteria andPlayerIdIsNotNull() {
            addCriterion("player_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlayerIdEqualTo(Long value) {
            addCriterion("player_id =", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdNotEqualTo(Long value) {
            addCriterion("player_id <>", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdGreaterThan(Long value) {
            addCriterion("player_id >", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("player_id >=", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdLessThan(Long value) {
            addCriterion("player_id <", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdLessThanOrEqualTo(Long value) {
            addCriterion("player_id <=", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdIn(List<Long> values) {
            addCriterion("player_id in", values, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdNotIn(List<Long> values) {
            addCriterion("player_id not in", values, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdBetween(Long value1, Long value2) {
            addCriterion("player_id between", value1, value2, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdNotBetween(Long value1, Long value2) {
            addCriterion("player_id not between", value1, value2, "playerId");
            return (Criteria) this;
        }

        public Criteria andGameIdIsNull() {
            addCriterion("game_id is null");
            return (Criteria) this;
        }

        public Criteria andGameIdIsNotNull() {
            addCriterion("game_id is not null");
            return (Criteria) this;
        }

        public Criteria andGameIdEqualTo(Integer value) {
            addCriterion("game_id =", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotEqualTo(Integer value) {
            addCriterion("game_id <>", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdGreaterThan(Integer value) {
            addCriterion("game_id >", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("game_id >=", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdLessThan(Integer value) {
            addCriterion("game_id <", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdLessThanOrEqualTo(Integer value) {
            addCriterion("game_id <=", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdIn(List<Integer> values) {
            addCriterion("game_id in", values, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotIn(List<Integer> values) {
            addCriterion("game_id not in", values, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdBetween(Integer value1, Integer value2) {
            addCriterion("game_id between", value1, value2, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotBetween(Integer value1, Integer value2) {
            addCriterion("game_id not between", value1, value2, "gameId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNull() {
            addCriterion("channel_id is null");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNotNull() {
            addCriterion("channel_id is not null");
            return (Criteria) this;
        }

        public Criteria andChannelIdEqualTo(String value) {
            addCriterion("channel_id =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(String value) {
            addCriterion("channel_id <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(String value) {
            addCriterion("channel_id >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(String value) {
            addCriterion("channel_id >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(String value) {
            addCriterion("channel_id <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(String value) {
            addCriterion("channel_id <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLike(String value) {
            addCriterion("channel_id like", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotLike(String value) {
            addCriterion("channel_id not like", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<String> values) {
            addCriterion("channel_id in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<String> values) {
            addCriterion("channel_id not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(String value1, String value2) {
            addCriterion("channel_id between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(String value1, String value2) {
            addCriterion("channel_id not between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andActivateTimeIsNull() {
            addCriterion("activate_time is null");
            return (Criteria) this;
        }

        public Criteria andActivateTimeIsNotNull() {
            addCriterion("activate_time is not null");
            return (Criteria) this;
        }

        public Criteria andActivateTimeEqualTo(Date value) {
            addCriterion("activate_time =", value, "activateTime");
            return (Criteria) this;
        }

        public Criteria andActivateTimeNotEqualTo(Date value) {
            addCriterion("activate_time <>", value, "activateTime");
            return (Criteria) this;
        }

        public Criteria andActivateTimeGreaterThan(Date value) {
            addCriterion("activate_time >", value, "activateTime");
            return (Criteria) this;
        }

        public Criteria andActivateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("activate_time >=", value, "activateTime");
            return (Criteria) this;
        }

        public Criteria andActivateTimeLessThan(Date value) {
            addCriterion("activate_time <", value, "activateTime");
            return (Criteria) this;
        }

        public Criteria andActivateTimeLessThanOrEqualTo(Date value) {
            addCriterion("activate_time <=", value, "activateTime");
            return (Criteria) this;
        }

        public Criteria andActivateTimeIn(List<Date> values) {
            addCriterion("activate_time in", values, "activateTime");
            return (Criteria) this;
        }

        public Criteria andActivateTimeNotIn(List<Date> values) {
            addCriterion("activate_time not in", values, "activateTime");
            return (Criteria) this;
        }

        public Criteria andActivateTimeBetween(Date value1, Date value2) {
            addCriterion("activate_time between", value1, value2, "activateTime");
            return (Criteria) this;
        }

        public Criteria andActivateTimeNotBetween(Date value1, Date value2) {
            addCriterion("activate_time not between", value1, value2, "activateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 玩家领取的礼品码记录信息表
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