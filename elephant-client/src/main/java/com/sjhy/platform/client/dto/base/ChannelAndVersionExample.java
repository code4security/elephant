package com.sjhy.platform.client.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class ChannelAndVersionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChannelAndVersionExample() {
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
     * 渠道和版本信息表
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

        public Criteria andChannelInfoIsNull() {
            addCriterion("channel_info is null");
            return (Criteria) this;
        }

        public Criteria andChannelInfoIsNotNull() {
            addCriterion("channel_info is not null");
            return (Criteria) this;
        }

        public Criteria andChannelInfoEqualTo(String value) {
            addCriterion("channel_info =", value, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoNotEqualTo(String value) {
            addCriterion("channel_info <>", value, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoGreaterThan(String value) {
            addCriterion("channel_info >", value, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoGreaterThanOrEqualTo(String value) {
            addCriterion("channel_info >=", value, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoLessThan(String value) {
            addCriterion("channel_info <", value, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoLessThanOrEqualTo(String value) {
            addCriterion("channel_info <=", value, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoLike(String value) {
            addCriterion("channel_info like", value, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoNotLike(String value) {
            addCriterion("channel_info not like", value, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoIn(List<String> values) {
            addCriterion("channel_info in", values, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoNotIn(List<String> values) {
            addCriterion("channel_info not in", values, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoBetween(String value1, String value2) {
            addCriterion("channel_info between", value1, value2, "channelInfo");
            return (Criteria) this;
        }

        public Criteria andChannelInfoNotBetween(String value1, String value2) {
            addCriterion("channel_info not between", value1, value2, "channelInfo");
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

        public Criteria andVersionNumEqualTo(String value) {
            addCriterion("version_num =", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotEqualTo(String value) {
            addCriterion("version_num <>", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumGreaterThan(String value) {
            addCriterion("version_num >", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumGreaterThanOrEqualTo(String value) {
            addCriterion("version_num >=", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumLessThan(String value) {
            addCriterion("version_num <", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumLessThanOrEqualTo(String value) {
            addCriterion("version_num <=", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumLike(String value) {
            addCriterion("version_num like", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotLike(String value) {
            addCriterion("version_num not like", value, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumIn(List<String> values) {
            addCriterion("version_num in", values, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotIn(List<String> values) {
            addCriterion("version_num not in", values, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumBetween(String value1, String value2) {
            addCriterion("version_num between", value1, value2, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionNumNotBetween(String value1, String value2) {
            addCriterion("version_num not between", value1, value2, "versionNum");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadIsNull() {
            addCriterion("version_download is null");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadIsNotNull() {
            addCriterion("version_download is not null");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadEqualTo(String value) {
            addCriterion("version_download =", value, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadNotEqualTo(String value) {
            addCriterion("version_download <>", value, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadGreaterThan(String value) {
            addCriterion("version_download >", value, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadGreaterThanOrEqualTo(String value) {
            addCriterion("version_download >=", value, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadLessThan(String value) {
            addCriterion("version_download <", value, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadLessThanOrEqualTo(String value) {
            addCriterion("version_download <=", value, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadLike(String value) {
            addCriterion("version_download like", value, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadNotLike(String value) {
            addCriterion("version_download not like", value, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadIn(List<String> values) {
            addCriterion("version_download in", values, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadNotIn(List<String> values) {
            addCriterion("version_download not in", values, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadBetween(String value1, String value2) {
            addCriterion("version_download between", value1, value2, "versionDownload");
            return (Criteria) this;
        }

        public Criteria andVersionDownloadNotBetween(String value1, String value2) {
            addCriterion("version_download not between", value1, value2, "versionDownload");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 渠道和版本信息表
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