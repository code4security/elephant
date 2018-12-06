package com.sjhy.platform.client.dto.base;

import java.util.ArrayList;
import java.util.List;

public class ServerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ServerExample() {
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
     * 服务器信息表
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

        public Criteria andLanguageIsNull() {
            addCriterion("language is null");
            return (Criteria) this;
        }

        public Criteria andLanguageIsNotNull() {
            addCriterion("language is not null");
            return (Criteria) this;
        }

        public Criteria andLanguageEqualTo(byte[] value) {
            addCriterion("language =", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotEqualTo(byte[] value) {
            addCriterion("language <>", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageGreaterThan(byte[] value) {
            addCriterion("language >", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageGreaterThanOrEqualTo(byte[] value) {
            addCriterion("language >=", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageLessThan(byte[] value) {
            addCriterion("language <", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageLessThanOrEqualTo(byte[] value) {
            addCriterion("language <=", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageIn(List<byte[]> values) {
            addCriterion("language in", values, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotIn(List<byte[]> values) {
            addCriterion("language not in", values, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageBetween(byte[] value1, byte[] value2) {
            addCriterion("language between", value1, value2, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotBetween(byte[] value1, byte[] value2) {
            addCriterion("language not between", value1, value2, "language");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(byte[] value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(byte[] value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(byte[] value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(byte[] value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(byte[] value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(byte[] value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<byte[]> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<byte[]> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(byte[] value1, byte[] value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(byte[] value1, byte[] value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIsNull() {
            addCriterion("is_available is null");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIsNotNull() {
            addCriterion("is_available is not null");
            return (Criteria) this;
        }

        public Criteria andIsAvailableEqualTo(Boolean value) {
            addCriterion("is_available =", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotEqualTo(Boolean value) {
            addCriterion("is_available <>", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableGreaterThan(Boolean value) {
            addCriterion("is_available >", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_available >=", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableLessThan(Boolean value) {
            addCriterion("is_available <", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableLessThanOrEqualTo(Boolean value) {
            addCriterion("is_available <=", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIn(List<Boolean> values) {
            addCriterion("is_available in", values, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotIn(List<Boolean> values) {
            addCriterion("is_available not in", values, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableBetween(Boolean value1, Boolean value2) {
            addCriterion("is_available between", value1, value2, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_available not between", value1, value2, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNull() {
            addCriterion("recommend is null");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNotNull() {
            addCriterion("recommend is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendEqualTo(Short value) {
            addCriterion("recommend =", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotEqualTo(Short value) {
            addCriterion("recommend <>", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThan(Short value) {
            addCriterion("recommend >", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThanOrEqualTo(Short value) {
            addCriterion("recommend >=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThan(Short value) {
            addCriterion("recommend <", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThanOrEqualTo(Short value) {
            addCriterion("recommend <=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendIn(List<Short> values) {
            addCriterion("recommend in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotIn(List<Short> values) {
            addCriterion("recommend not in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendBetween(Short value1, Short value2) {
            addCriterion("recommend between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotBetween(Short value1, Short value2) {
            addCriterion("recommend not between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(byte[] value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(byte[] value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(byte[] value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(byte[] value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(byte[] value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(byte[] value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<byte[]> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<byte[]> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(byte[] value1, byte[] value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(byte[] value1, byte[] value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpInternalIsNull() {
            addCriterion("ip_internal is null");
            return (Criteria) this;
        }

        public Criteria andIpInternalIsNotNull() {
            addCriterion("ip_internal is not null");
            return (Criteria) this;
        }

        public Criteria andIpInternalEqualTo(String value) {
            addCriterion("ip_internal =", value, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalNotEqualTo(String value) {
            addCriterion("ip_internal <>", value, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalGreaterThan(String value) {
            addCriterion("ip_internal >", value, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalGreaterThanOrEqualTo(String value) {
            addCriterion("ip_internal >=", value, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalLessThan(String value) {
            addCriterion("ip_internal <", value, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalLessThanOrEqualTo(String value) {
            addCriterion("ip_internal <=", value, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalLike(String value) {
            addCriterion("ip_internal like", value, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalNotLike(String value) {
            addCriterion("ip_internal not like", value, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalIn(List<String> values) {
            addCriterion("ip_internal in", values, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalNotIn(List<String> values) {
            addCriterion("ip_internal not in", values, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalBetween(String value1, String value2) {
            addCriterion("ip_internal between", value1, value2, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andIpInternalNotBetween(String value1, String value2) {
            addCriterion("ip_internal not between", value1, value2, "ipInternal");
            return (Criteria) this;
        }

        public Criteria andPortNumberIsNull() {
            addCriterion("port_number is null");
            return (Criteria) this;
        }

        public Criteria andPortNumberIsNotNull() {
            addCriterion("port_number is not null");
            return (Criteria) this;
        }

        public Criteria andPortNumberEqualTo(Integer value) {
            addCriterion("port_number =", value, "portNumber");
            return (Criteria) this;
        }

        public Criteria andPortNumberNotEqualTo(Integer value) {
            addCriterion("port_number <>", value, "portNumber");
            return (Criteria) this;
        }

        public Criteria andPortNumberGreaterThan(Integer value) {
            addCriterion("port_number >", value, "portNumber");
            return (Criteria) this;
        }

        public Criteria andPortNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("port_number >=", value, "portNumber");
            return (Criteria) this;
        }

        public Criteria andPortNumberLessThan(Integer value) {
            addCriterion("port_number <", value, "portNumber");
            return (Criteria) this;
        }

        public Criteria andPortNumberLessThanOrEqualTo(Integer value) {
            addCriterion("port_number <=", value, "portNumber");
            return (Criteria) this;
        }

        public Criteria andPortNumberIn(List<Integer> values) {
            addCriterion("port_number in", values, "portNumber");
            return (Criteria) this;
        }

        public Criteria andPortNumberNotIn(List<Integer> values) {
            addCriterion("port_number not in", values, "portNumber");
            return (Criteria) this;
        }

        public Criteria andPortNumberBetween(Integer value1, Integer value2) {
            addCriterion("port_number between", value1, value2, "portNumber");
            return (Criteria) this;
        }

        public Criteria andPortNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("port_number not between", value1, value2, "portNumber");
            return (Criteria) this;
        }

        public Criteria andStartUrlIsNull() {
            addCriterion("start_url is null");
            return (Criteria) this;
        }

        public Criteria andStartUrlIsNotNull() {
            addCriterion("start_url is not null");
            return (Criteria) this;
        }

        public Criteria andStartUrlEqualTo(String value) {
            addCriterion("start_url =", value, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlNotEqualTo(String value) {
            addCriterion("start_url <>", value, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlGreaterThan(String value) {
            addCriterion("start_url >", value, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlGreaterThanOrEqualTo(String value) {
            addCriterion("start_url >=", value, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlLessThan(String value) {
            addCriterion("start_url <", value, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlLessThanOrEqualTo(String value) {
            addCriterion("start_url <=", value, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlLike(String value) {
            addCriterion("start_url like", value, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlNotLike(String value) {
            addCriterion("start_url not like", value, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlIn(List<String> values) {
            addCriterion("start_url in", values, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlNotIn(List<String> values) {
            addCriterion("start_url not in", values, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlBetween(String value1, String value2) {
            addCriterion("start_url between", value1, value2, "startUrl");
            return (Criteria) this;
        }

        public Criteria andStartUrlNotBetween(String value1, String value2) {
            addCriterion("start_url not between", value1, value2, "startUrl");
            return (Criteria) this;
        }

        public Criteria andVersionNumIsNull() {
            addCriterion("version_num is null");
            return (Criteria) this;
        }

        public Criteria andVersionNumIsNotNull() {
            addCriterion("version_num is not null");
            return (Criteria) this;
        }

        public Criteria andVersionNumEqualTo(byte[] value) {
            addCriterion("version_num =", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotEqualTo(byte[] value) {
            addCriterion("version_num <>", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumGreaterThan(byte[] value) {
            addCriterion("version_num >", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumGreaterThanOrEqualTo(byte[] value) {
            addCriterion("version_num >=", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumLessThan(byte[] value) {
            addCriterion("version_num <", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumLessThanOrEqualTo(byte[] value) {
            addCriterion("version_num <=", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumIn(List<byte[]> values) {
            addCriterion("version_num in", values, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotIn(List<byte[]> values) {
            addCriterion("version_num not in", values, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumBetween(byte[] value1, byte[] value2) {
            addCriterion("version_num between", value1, value2, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotBetween(byte[] value1, byte[] value2) {
            addCriterion("version_num not between", value1, value2, "versionNum");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 服务器信息表
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