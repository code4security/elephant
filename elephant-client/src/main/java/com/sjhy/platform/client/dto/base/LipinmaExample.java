package com.sjhy.platform.client.dto.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LipinmaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LipinmaExample() {
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
     * 礼品码信息表
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andLpNameIsNull() {
            addCriterion("lp_name is null");
            return (Criteria) this;
        }

        public Criteria andLpNameIsNotNull() {
            addCriterion("lp_name is not null");
            return (Criteria) this;
        }

        public Criteria andLpNameEqualTo(String value) {
            addCriterion("lp_name =", value, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameNotEqualTo(String value) {
            addCriterion("lp_name <>", value, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameGreaterThan(String value) {
            addCriterion("lp_name >", value, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameGreaterThanOrEqualTo(String value) {
            addCriterion("lp_name >=", value, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameLessThan(String value) {
            addCriterion("lp_name <", value, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameLessThanOrEqualTo(String value) {
            addCriterion("lp_name <=", value, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameLike(String value) {
            addCriterion("lp_name like", value, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameNotLike(String value) {
            addCriterion("lp_name not like", value, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameIn(List<String> values) {
            addCriterion("lp_name in", values, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameNotIn(List<String> values) {
            addCriterion("lp_name not in", values, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameBetween(String value1, String value2) {
            addCriterion("lp_name between", value1, value2, "lpName");
            return (Criteria) this;
        }

        public Criteria andLpNameNotBetween(String value1, String value2) {
            addCriterion("lp_name not between", value1, value2, "lpName");
            return (Criteria) this;
        }

        public Criteria andDescTxtIsNull() {
            addCriterion("desc_txt is null");
            return (Criteria) this;
        }

        public Criteria andDescTxtIsNotNull() {
            addCriterion("desc_txt is not null");
            return (Criteria) this;
        }

        public Criteria andDescTxtEqualTo(String value) {
            addCriterion("desc_txt =", value, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtNotEqualTo(String value) {
            addCriterion("desc_txt <>", value, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtGreaterThan(String value) {
            addCriterion("desc_txt >", value, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtGreaterThanOrEqualTo(String value) {
            addCriterion("desc_txt >=", value, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtLessThan(String value) {
            addCriterion("desc_txt <", value, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtLessThanOrEqualTo(String value) {
            addCriterion("desc_txt <=", value, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtLike(String value) {
            addCriterion("desc_txt like", value, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtNotLike(String value) {
            addCriterion("desc_txt not like", value, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtIn(List<String> values) {
            addCriterion("desc_txt in", values, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtNotIn(List<String> values) {
            addCriterion("desc_txt not in", values, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtBetween(String value1, String value2) {
            addCriterion("desc_txt between", value1, value2, "descTxt");
            return (Criteria) this;
        }

        public Criteria andDescTxtNotBetween(String value1, String value2) {
            addCriterion("desc_txt not between", value1, value2, "descTxt");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdIsNull() {
            addCriterion("lp_reward_id is null");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdIsNotNull() {
            addCriterion("lp_reward_id is not null");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdEqualTo(String value) {
            addCriterion("lp_reward_id =", value, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdNotEqualTo(String value) {
            addCriterion("lp_reward_id <>", value, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdGreaterThan(String value) {
            addCriterion("lp_reward_id >", value, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdGreaterThanOrEqualTo(String value) {
            addCriterion("lp_reward_id >=", value, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdLessThan(String value) {
            addCriterion("lp_reward_id <", value, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdLessThanOrEqualTo(String value) {
            addCriterion("lp_reward_id <=", value, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdLike(String value) {
            addCriterion("lp_reward_id like", value, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdNotLike(String value) {
            addCriterion("lp_reward_id not like", value, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdIn(List<String> values) {
            addCriterion("lp_reward_id in", values, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdNotIn(List<String> values) {
            addCriterion("lp_reward_id not in", values, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdBetween(String value1, String value2) {
            addCriterion("lp_reward_id between", value1, value2, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpRewardIdNotBetween(String value1, String value2) {
            addCriterion("lp_reward_id not between", value1, value2, "lpRewardId");
            return (Criteria) this;
        }

        public Criteria andLpNumIsNull() {
            addCriterion("lp_num is null");
            return (Criteria) this;
        }

        public Criteria andLpNumIsNotNull() {
            addCriterion("lp_num is not null");
            return (Criteria) this;
        }

        public Criteria andLpNumEqualTo(Integer value) {
            addCriterion("lp_num =", value, "lpNum");
            return (Criteria) this;
        }

        public Criteria andLpNumNotEqualTo(Integer value) {
            addCriterion("lp_num <>", value, "lpNum");
            return (Criteria) this;
        }

        public Criteria andLpNumGreaterThan(Integer value) {
            addCriterion("lp_num >", value, "lpNum");
            return (Criteria) this;
        }

        public Criteria andLpNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("lp_num >=", value, "lpNum");
            return (Criteria) this;
        }

        public Criteria andLpNumLessThan(Integer value) {
            addCriterion("lp_num <", value, "lpNum");
            return (Criteria) this;
        }

        public Criteria andLpNumLessThanOrEqualTo(Integer value) {
            addCriterion("lp_num <=", value, "lpNum");
            return (Criteria) this;
        }

        public Criteria andLpNumIn(List<Integer> values) {
            addCriterion("lp_num in", values, "lpNum");
            return (Criteria) this;
        }

        public Criteria andLpNumNotIn(List<Integer> values) {
            addCriterion("lp_num not in", values, "lpNum");
            return (Criteria) this;
        }

        public Criteria andLpNumBetween(Integer value1, Integer value2) {
            addCriterion("lp_num between", value1, value2, "lpNum");
            return (Criteria) this;
        }

        public Criteria andLpNumNotBetween(Integer value1, Integer value2) {
            addCriterion("lp_num not between", value1, value2, "lpNum");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNull() {
            addCriterion("begin_time is null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNotNull() {
            addCriterion("begin_time is not null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeEqualTo(String value) {
            addCriterion("begin_time =", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotEqualTo(String value) {
            addCriterion("begin_time <>", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThan(String value) {
            addCriterion("begin_time >", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThanOrEqualTo(String value) {
            addCriterion("begin_time >=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThan(String value) {
            addCriterion("begin_time <", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThanOrEqualTo(String value) {
            addCriterion("begin_time <=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLike(String value) {
            addCriterion("begin_time like", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotLike(String value) {
            addCriterion("begin_time not like", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIn(List<String> values) {
            addCriterion("begin_time in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotIn(List<String> values) {
            addCriterion("begin_time not in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeBetween(String value1, String value2) {
            addCriterion("begin_time between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotBetween(String value1, String value2) {
            addCriterion("begin_time not between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("end_time like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("end_time not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 礼品码信息表
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