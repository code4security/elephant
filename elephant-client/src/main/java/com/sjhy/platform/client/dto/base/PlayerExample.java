package com.sjhy.platform.client.dto.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PlayerExample() {
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
     * 玩家信息表
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

        public Criteria andServerIdIsNull() {
            addCriterion("server_id is null");
            return (Criteria) this;
        }

        public Criteria andServerIdIsNotNull() {
            addCriterion("server_id is not null");
            return (Criteria) this;
        }

        public Criteria andServerIdEqualTo(Integer value) {
            addCriterion("server_id =", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotEqualTo(Integer value) {
            addCriterion("server_id <>", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThan(Integer value) {
            addCriterion("server_id >", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("server_id >=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThan(Integer value) {
            addCriterion("server_id <", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThanOrEqualTo(Integer value) {
            addCriterion("server_id <=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdIn(List<Integer> values) {
            addCriterion("server_id in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotIn(List<Integer> values) {
            addCriterion("server_id not in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdBetween(Integer value1, Integer value2) {
            addCriterion("server_id between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("server_id not between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andDeviceModelIsNull() {
            addCriterion("device_model is null");
            return (Criteria) this;
        }

        public Criteria andDeviceModelIsNotNull() {
            addCriterion("device_model is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceModelEqualTo(String value) {
            addCriterion("device_model =", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelNotEqualTo(String value) {
            addCriterion("device_model <>", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelGreaterThan(String value) {
            addCriterion("device_model >", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelGreaterThanOrEqualTo(String value) {
            addCriterion("device_model >=", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelLessThan(String value) {
            addCriterion("device_model <", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelLessThanOrEqualTo(String value) {
            addCriterion("device_model <=", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelLike(String value) {
            addCriterion("device_model like", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelNotLike(String value) {
            addCriterion("device_model not like", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelIn(List<String> values) {
            addCriterion("device_model in", values, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelNotIn(List<String> values) {
            addCriterion("device_model not in", values, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelBetween(String value1, String value2) {
            addCriterion("device_model between", value1, value2, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelNotBetween(String value1, String value2) {
            addCriterion("device_model not between", value1, value2, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdIsNull() {
            addCriterion("device_uniquely_id is null");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdIsNotNull() {
            addCriterion("device_uniquely_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdEqualTo(String value) {
            addCriterion("device_uniquely_id =", value, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdNotEqualTo(String value) {
            addCriterion("device_uniquely_id <>", value, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdGreaterThan(String value) {
            addCriterion("device_uniquely_id >", value, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdGreaterThanOrEqualTo(String value) {
            addCriterion("device_uniquely_id >=", value, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdLessThan(String value) {
            addCriterion("device_uniquely_id <", value, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdLessThanOrEqualTo(String value) {
            addCriterion("device_uniquely_id <=", value, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdLike(String value) {
            addCriterion("device_uniquely_id like", value, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdNotLike(String value) {
            addCriterion("device_uniquely_id not like", value, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdIn(List<String> values) {
            addCriterion("device_uniquely_id in", values, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdNotIn(List<String> values) {
            addCriterion("device_uniquely_id not in", values, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdBetween(String value1, String value2) {
            addCriterion("device_uniquely_id between", value1, value2, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andDeviceUniquelyIdNotBetween(String value1, String value2) {
            addCriterion("device_uniquely_id not between", value1, value2, "deviceUniquelyId");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNull() {
            addCriterion("channel_name is null");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNotNull() {
            addCriterion("channel_name is not null");
            return (Criteria) this;
        }

        public Criteria andChannelNameEqualTo(String value) {
            addCriterion("channel_name =", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotEqualTo(String value) {
            addCriterion("channel_name <>", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThan(String value) {
            addCriterion("channel_name >", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThanOrEqualTo(String value) {
            addCriterion("channel_name >=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThan(String value) {
            addCriterion("channel_name <", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThanOrEqualTo(String value) {
            addCriterion("channel_name <=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLike(String value) {
            addCriterion("channel_name like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotLike(String value) {
            addCriterion("channel_name not like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameIn(List<String> values) {
            addCriterion("channel_name in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotIn(List<String> values) {
            addCriterion("channel_name not in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameBetween(String value1, String value2) {
            addCriterion("channel_name between", value1, value2, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotBetween(String value1, String value2) {
            addCriterion("channel_name not between", value1, value2, "channelName");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeIsNull() {
            addCriterion("first_login_time is null");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeIsNotNull() {
            addCriterion("first_login_time is not null");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeEqualTo(Date value) {
            addCriterion("first_login_time =", value, "firstLoginTime");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeNotEqualTo(Date value) {
            addCriterion("first_login_time <>", value, "firstLoginTime");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeGreaterThan(Date value) {
            addCriterion("first_login_time >", value, "firstLoginTime");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("first_login_time >=", value, "firstLoginTime");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeLessThan(Date value) {
            addCriterion("first_login_time <", value, "firstLoginTime");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeLessThanOrEqualTo(Date value) {
            addCriterion("first_login_time <=", value, "firstLoginTime");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeIn(List<Date> values) {
            addCriterion("first_login_time in", values, "firstLoginTime");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeNotIn(List<Date> values) {
            addCriterion("first_login_time not in", values, "firstLoginTime");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeBetween(Date value1, Date value2) {
            addCriterion("first_login_time between", value1, value2, "firstLoginTime");
            return (Criteria) this;
        }

        public Criteria andFirstLoginTimeNotBetween(Date value1, Date value2) {
            addCriterion("first_login_time not between", value1, value2, "firstLoginTime");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpIsNull() {
            addCriterion("first_login_ip is null");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpIsNotNull() {
            addCriterion("first_login_ip is not null");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpEqualTo(String value) {
            addCriterion("first_login_ip =", value, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpNotEqualTo(String value) {
            addCriterion("first_login_ip <>", value, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpGreaterThan(String value) {
            addCriterion("first_login_ip >", value, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpGreaterThanOrEqualTo(String value) {
            addCriterion("first_login_ip >=", value, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpLessThan(String value) {
            addCriterion("first_login_ip <", value, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpLessThanOrEqualTo(String value) {
            addCriterion("first_login_ip <=", value, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpLike(String value) {
            addCriterion("first_login_ip like", value, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpNotLike(String value) {
            addCriterion("first_login_ip not like", value, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpIn(List<String> values) {
            addCriterion("first_login_ip in", values, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpNotIn(List<String> values) {
            addCriterion("first_login_ip not in", values, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpBetween(String value1, String value2) {
            addCriterion("first_login_ip between", value1, value2, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andFirstLoginIpNotBetween(String value1, String value2) {
            addCriterion("first_login_ip not between", value1, value2, "firstLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNull() {
            addCriterion("last_login_time is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNotNull() {
            addCriterion("last_login_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeEqualTo(Date value) {
            addCriterion("last_login_time =", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotEqualTo(Date value) {
            addCriterion("last_login_time <>", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThan(Date value) {
            addCriterion("last_login_time >", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_login_time >=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThan(Date value) {
            addCriterion("last_login_time <", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_login_time <=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIn(List<Date> values) {
            addCriterion("last_login_time in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotIn(List<Date> values) {
            addCriterion("last_login_time not in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeBetween(Date value1, Date value2) {
            addCriterion("last_login_time between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_login_time not between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIsNull() {
            addCriterion("last_login_ip is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIsNotNull() {
            addCriterion("last_login_ip is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpEqualTo(String value) {
            addCriterion("last_login_ip =", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotEqualTo(String value) {
            addCriterion("last_login_ip <>", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpGreaterThan(String value) {
            addCriterion("last_login_ip >", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpGreaterThanOrEqualTo(String value) {
            addCriterion("last_login_ip >=", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLessThan(String value) {
            addCriterion("last_login_ip <", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLessThanOrEqualTo(String value) {
            addCriterion("last_login_ip <=", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLike(String value) {
            addCriterion("last_login_ip like", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotLike(String value) {
            addCriterion("last_login_ip not like", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIn(List<String> values) {
            addCriterion("last_login_ip in", values, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotIn(List<String> values) {
            addCriterion("last_login_ip not in", values, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpBetween(String value1, String value2) {
            addCriterion("last_login_ip between", value1, value2, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotBetween(String value1, String value2) {
            addCriterion("last_login_ip not between", value1, value2, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andIpRegionIsNull() {
            addCriterion("ip_region is null");
            return (Criteria) this;
        }

        public Criteria andIpRegionIsNotNull() {
            addCriterion("ip_region is not null");
            return (Criteria) this;
        }

        public Criteria andIpRegionEqualTo(Integer value) {
            addCriterion("ip_region =", value, "ipRegion");
            return (Criteria) this;
        }

        public Criteria andIpRegionNotEqualTo(Integer value) {
            addCriterion("ip_region <>", value, "ipRegion");
            return (Criteria) this;
        }

        public Criteria andIpRegionGreaterThan(Integer value) {
            addCriterion("ip_region >", value, "ipRegion");
            return (Criteria) this;
        }

        public Criteria andIpRegionGreaterThanOrEqualTo(Integer value) {
            addCriterion("ip_region >=", value, "ipRegion");
            return (Criteria) this;
        }

        public Criteria andIpRegionLessThan(Integer value) {
            addCriterion("ip_region <", value, "ipRegion");
            return (Criteria) this;
        }

        public Criteria andIpRegionLessThanOrEqualTo(Integer value) {
            addCriterion("ip_region <=", value, "ipRegion");
            return (Criteria) this;
        }

        public Criteria andIpRegionIn(List<Integer> values) {
            addCriterion("ip_region in", values, "ipRegion");
            return (Criteria) this;
        }

        public Criteria andIpRegionNotIn(List<Integer> values) {
            addCriterion("ip_region not in", values, "ipRegion");
            return (Criteria) this;
        }

        public Criteria andIpRegionBetween(Integer value1, Integer value2) {
            addCriterion("ip_region between", value1, value2, "ipRegion");
            return (Criteria) this;
        }

        public Criteria andIpRegionNotBetween(Integer value1, Integer value2) {
            addCriterion("ip_region not between", value1, value2, "ipRegion");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 玩家信息表
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