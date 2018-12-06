package com.sjhy.platform.client.dto.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerGameOssExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PlayerGameOssExample() {
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
     * 玩家存档信息表
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

        public Criteria andBucketIsNull() {
            addCriterion("bucket is null");
            return (Criteria) this;
        }

        public Criteria andBucketIsNotNull() {
            addCriterion("bucket is not null");
            return (Criteria) this;
        }

        public Criteria andBucketEqualTo(String value) {
            addCriterion("bucket =", value, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketNotEqualTo(String value) {
            addCriterion("bucket <>", value, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketGreaterThan(String value) {
            addCriterion("bucket >", value, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketGreaterThanOrEqualTo(String value) {
            addCriterion("bucket >=", value, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketLessThan(String value) {
            addCriterion("bucket <", value, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketLessThanOrEqualTo(String value) {
            addCriterion("bucket <=", value, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketLike(String value) {
            addCriterion("bucket like", value, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketNotLike(String value) {
            addCriterion("bucket not like", value, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketIn(List<String> values) {
            addCriterion("bucket in", values, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketNotIn(List<String> values) {
            addCriterion("bucket not in", values, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketBetween(String value1, String value2) {
            addCriterion("bucket between", value1, value2, "bucket");
            return (Criteria) this;
        }

        public Criteria andBucketNotBetween(String value1, String value2) {
            addCriterion("bucket not between", value1, value2, "bucket");
            return (Criteria) this;
        }

        public Criteria andObjTypeIsNull() {
            addCriterion("obj_type is null");
            return (Criteria) this;
        }

        public Criteria andObjTypeIsNotNull() {
            addCriterion("obj_type is not null");
            return (Criteria) this;
        }

        public Criteria andObjTypeEqualTo(Integer value) {
            addCriterion("obj_type =", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeNotEqualTo(Integer value) {
            addCriterion("obj_type <>", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeGreaterThan(Integer value) {
            addCriterion("obj_type >", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("obj_type >=", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeLessThan(Integer value) {
            addCriterion("obj_type <", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeLessThanOrEqualTo(Integer value) {
            addCriterion("obj_type <=", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeIn(List<Integer> values) {
            addCriterion("obj_type in", values, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeNotIn(List<Integer> values) {
            addCriterion("obj_type not in", values, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeBetween(Integer value1, Integer value2) {
            addCriterion("obj_type between", value1, value2, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("obj_type not between", value1, value2, "objType");
            return (Criteria) this;
        }

        public Criteria andObjKeyIsNull() {
            addCriterion("obj_key is null");
            return (Criteria) this;
        }

        public Criteria andObjKeyIsNotNull() {
            addCriterion("obj_key is not null");
            return (Criteria) this;
        }

        public Criteria andObjKeyEqualTo(String value) {
            addCriterion("obj_key =", value, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyNotEqualTo(String value) {
            addCriterion("obj_key <>", value, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyGreaterThan(String value) {
            addCriterion("obj_key >", value, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyGreaterThanOrEqualTo(String value) {
            addCriterion("obj_key >=", value, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyLessThan(String value) {
            addCriterion("obj_key <", value, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyLessThanOrEqualTo(String value) {
            addCriterion("obj_key <=", value, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyLike(String value) {
            addCriterion("obj_key like", value, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyNotLike(String value) {
            addCriterion("obj_key not like", value, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyIn(List<String> values) {
            addCriterion("obj_key in", values, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyNotIn(List<String> values) {
            addCriterion("obj_key not in", values, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyBetween(String value1, String value2) {
            addCriterion("obj_key between", value1, value2, "objKey");
            return (Criteria) this;
        }

        public Criteria andObjKeyNotBetween(String value1, String value2) {
            addCriterion("obj_key not between", value1, value2, "objKey");
            return (Criteria) this;
        }

        public Criteria andGoldIsNull() {
            addCriterion("gold is null");
            return (Criteria) this;
        }

        public Criteria andGoldIsNotNull() {
            addCriterion("gold is not null");
            return (Criteria) this;
        }

        public Criteria andGoldEqualTo(Long value) {
            addCriterion("gold =", value, "gold");
            return (Criteria) this;
        }

        public Criteria andGoldNotEqualTo(Long value) {
            addCriterion("gold <>", value, "gold");
            return (Criteria) this;
        }

        public Criteria andGoldGreaterThan(Long value) {
            addCriterion("gold >", value, "gold");
            return (Criteria) this;
        }

        public Criteria andGoldGreaterThanOrEqualTo(Long value) {
            addCriterion("gold >=", value, "gold");
            return (Criteria) this;
        }

        public Criteria andGoldLessThan(Long value) {
            addCriterion("gold <", value, "gold");
            return (Criteria) this;
        }

        public Criteria andGoldLessThanOrEqualTo(Long value) {
            addCriterion("gold <=", value, "gold");
            return (Criteria) this;
        }

        public Criteria andGoldIn(List<Long> values) {
            addCriterion("gold in", values, "gold");
            return (Criteria) this;
        }

        public Criteria andGoldNotIn(List<Long> values) {
            addCriterion("gold not in", values, "gold");
            return (Criteria) this;
        }

        public Criteria andGoldBetween(Long value1, Long value2) {
            addCriterion("gold between", value1, value2, "gold");
            return (Criteria) this;
        }

        public Criteria andGoldNotBetween(Long value1, Long value2) {
            addCriterion("gold not between", value1, value2, "gold");
            return (Criteria) this;
        }

        public Criteria andSaveTimeIsNull() {
            addCriterion("save_time is null");
            return (Criteria) this;
        }

        public Criteria andSaveTimeIsNotNull() {
            addCriterion("save_time is not null");
            return (Criteria) this;
        }

        public Criteria andSaveTimeEqualTo(String value) {
            addCriterion("save_time =", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeNotEqualTo(String value) {
            addCriterion("save_time <>", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeGreaterThan(String value) {
            addCriterion("save_time >", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeGreaterThanOrEqualTo(String value) {
            addCriterion("save_time >=", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeLessThan(String value) {
            addCriterion("save_time <", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeLessThanOrEqualTo(String value) {
            addCriterion("save_time <=", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeLike(String value) {
            addCriterion("save_time like", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeNotLike(String value) {
            addCriterion("save_time not like", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeIn(List<String> values) {
            addCriterion("save_time in", values, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeNotIn(List<String> values) {
            addCriterion("save_time not in", values, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeBetween(String value1, String value2) {
            addCriterion("save_time between", value1, value2, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeNotBetween(String value1, String value2) {
            addCriterion("save_time not between", value1, value2, "saveTime");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andMetaMd5IsNull() {
            addCriterion("meta_md5 is null");
            return (Criteria) this;
        }

        public Criteria andMetaMd5IsNotNull() {
            addCriterion("meta_md5 is not null");
            return (Criteria) this;
        }

        public Criteria andMetaMd5EqualTo(String value) {
            addCriterion("meta_md5 =", value, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5NotEqualTo(String value) {
            addCriterion("meta_md5 <>", value, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5GreaterThan(String value) {
            addCriterion("meta_md5 >", value, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5GreaterThanOrEqualTo(String value) {
            addCriterion("meta_md5 >=", value, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5LessThan(String value) {
            addCriterion("meta_md5 <", value, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5LessThanOrEqualTo(String value) {
            addCriterion("meta_md5 <=", value, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5Like(String value) {
            addCriterion("meta_md5 like", value, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5NotLike(String value) {
            addCriterion("meta_md5 not like", value, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5In(List<String> values) {
            addCriterion("meta_md5 in", values, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5NotIn(List<String> values) {
            addCriterion("meta_md5 not in", values, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5Between(String value1, String value2) {
            addCriterion("meta_md5 between", value1, value2, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andMetaMd5NotBetween(String value1, String value2) {
            addCriterion("meta_md5 not between", value1, value2, "metaMd5");
            return (Criteria) this;
        }

        public Criteria andFnameIsNull() {
            addCriterion("fname is null");
            return (Criteria) this;
        }

        public Criteria andFnameIsNotNull() {
            addCriterion("fname is not null");
            return (Criteria) this;
        }

        public Criteria andFnameEqualTo(String value) {
            addCriterion("fname =", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameNotEqualTo(String value) {
            addCriterion("fname <>", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameGreaterThan(String value) {
            addCriterion("fname >", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameGreaterThanOrEqualTo(String value) {
            addCriterion("fname >=", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameLessThan(String value) {
            addCriterion("fname <", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameLessThanOrEqualTo(String value) {
            addCriterion("fname <=", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameLike(String value) {
            addCriterion("fname like", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameNotLike(String value) {
            addCriterion("fname not like", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameIn(List<String> values) {
            addCriterion("fname in", values, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameNotIn(List<String> values) {
            addCriterion("fname not in", values, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameBetween(String value1, String value2) {
            addCriterion("fname between", value1, value2, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameNotBetween(String value1, String value2) {
            addCriterion("fname not between", value1, value2, "fname");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeIsNull() {
            addCriterion("backup_oss_time is null");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeIsNotNull() {
            addCriterion("backup_oss_time is not null");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeEqualTo(Date value) {
            addCriterion("backup_oss_time =", value, "backupOssTime");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeNotEqualTo(Date value) {
            addCriterion("backup_oss_time <>", value, "backupOssTime");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeGreaterThan(Date value) {
            addCriterion("backup_oss_time >", value, "backupOssTime");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("backup_oss_time >=", value, "backupOssTime");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeLessThan(Date value) {
            addCriterion("backup_oss_time <", value, "backupOssTime");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeLessThanOrEqualTo(Date value) {
            addCriterion("backup_oss_time <=", value, "backupOssTime");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeIn(List<Date> values) {
            addCriterion("backup_oss_time in", values, "backupOssTime");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeNotIn(List<Date> values) {
            addCriterion("backup_oss_time not in", values, "backupOssTime");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeBetween(Date value1, Date value2) {
            addCriterion("backup_oss_time between", value1, value2, "backupOssTime");
            return (Criteria) this;
        }

        public Criteria andBackupOssTimeNotBetween(Date value1, Date value2) {
            addCriterion("backup_oss_time not between", value1, value2, "backupOssTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 玩家存档信息表
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