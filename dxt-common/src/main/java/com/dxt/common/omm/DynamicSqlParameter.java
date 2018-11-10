package com.dxt.common.omm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数封装类
 */
public class DynamicSqlParameter implements Serializable {


    private static final long serialVersionUID = 7749745554778802005L;

    /**
     * 参数使用Like方式查询设置key与value{字段名：值}
     */
    private Map<String, String> like;

    /**
     * 参数使用==方式查询设置key与value{字段名：值}
     */
    private Map<String, String> equal;

    /**
     * 参数使用!=方式查询设置key与value{字段名：值}
     */
    private Map<String, String> notequal;

    /**
     * 参数使用以xxx开始的方式查询设置key与value{字段名：值}
     */
    private Map<String, String> startwith;

    /**
     * 参数使用以xxx结束方式查询设置key与value{字段名：值}
     */
    private Map<String, String> endwith;

    /**
     * 参数使用in方式查询ID{值}
     */
    private Map<String, Object> inMap;

    /**
     * 参数使用not in方式查询ID{值}
     */
    private Map<String, Object> notInMap;

    /**
     * 查询规则
     */
    private String op;

    /**
     * 需要修改的参数列与值{字段名：值}
     */
    private Map<String, Object> updateValue;

    /** 去除自身ID情况下使用 */
    private String noId;

    /** 开始页码 */
    private Integer page;

    /** 每页多少 */
    private Integer rows;

    /** 共计多少页 */
    private Integer pagesize;

    /** 排序名称 */
    private String order;

    /** 排序规则desc/asc */
    private String sort;

    public DynamicSqlParameter() {
    }

    public void putLike(String key,String value){
        if(this.like == null){
            this.like = new HashMap<>();
        }
        this.like.put(key,value);
    }

    public void putEqual(String key,String value){
        if(this.equal == null){
            this.equal = new HashMap<>();
        }
        this.equal.put(key,value);
    }

    public void putNotEqual(String key,String value){
        if(this.notequal == null){
            this.notequal = new HashMap<>();
        }
        this.notequal.put(key,value);
    }

    public void putStartWith(String key,String value){
        if(this.startwith == null){
            this.startwith = new HashMap<>();
        }
        this.startwith.put(key,value);
    }

    public void putEndWith(String key,String value){
        if(this.endwith == null){
            this.endwith = new HashMap<>();
        }
        this.endwith.put(key,value);
    }

    public void putinMap(String key,Object value){
        if(this.inMap == null){
            this.inMap = new HashMap<>();
        }
        this.inMap.put(key,value);
    }

    public void putNotInMap(String key,Object value){
        if(this.notInMap == null){
            this.notInMap = new HashMap<>();
        }
        this.notInMap.put(key,value);
    }

    /**
     *
     * @param equalKey
     *            等于的条件
     * @param equalIdValue
     *            等于的值
     * @param updateValueMap
     *            需要修改的键值对
     * @return
     */
    public static DynamicSqlParameter getUpdateDynamicSqlParameter(
            String equalKey, String equalIdValue,
            Map<String, Object> updateValueMap) {

        DynamicSqlParameter parameter = null;
        if (null == equalKey || null == equalIdValue || null == updateValueMap
                || "".equals(equalKey) || "".equals(equalIdValue)) {
            return parameter;
        }

        parameter = new DynamicSqlParameter();
        Map<String, String> equalMap = new HashMap<String, String>();
        // Map<String, String> updateValueMap = new HashMap<String, String>();
        equalMap.put(equalKey, equalIdValue);
        // updateValueMap.put(updateKey, updateValue);
        parameter.setEqual(equalMap);
        parameter.setUpdateValue(updateValueMap);
        return parameter;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        if (!(like == null || like.size() == 0)) {
            buff.append("[包含条件:").append(like).append("]");
        }
        if (!(equal == null || equal.size() == 0)) {
            buff.append("[等于条件:").append(equal).append("]");
        }
        if (!(notequal == null || notequal.size() == 0)) {
            buff.append("[不等于条件:").append(notequal).append("]");
        }
        if (!(startwith == null || startwith.size() == 0)) {
            buff.append("[以...条件开始:").append(startwith).append("]");
        }
        if (!(endwith == null || endwith.size() == 0)) {
            buff.append("[以...条件结束:").append(endwith).append("]");
        }
        if (!(inMap == null || inMap.size() == 0)) {
            buff.append("[在...中条件:").append(inMap).append("]");
        }
        if (!(notInMap == null || notInMap.size() == 0)) {
            buff.append("[不在...中条件:").append(notInMap).append("]");
        }
        return buff.toString();
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Map<String, String> getLike() {
        return like;
    }

    public void setLike(Map<String, String> like) {
        this.like = like;
    }

    public Map<String, String> getEqual() {
        return equal;
    }

    public void setEqual(Map<String, String> equal) {
        this.equal = equal;
    }

    public Map<String, String> getNotequal() {
        return notequal;
    }

    public void setNotequal(Map<String, String> notequal) {
        this.notequal = notequal;
    }

    public Map<String, String> getStartwith() {
        return startwith;
    }

    public void setStartwith(Map<String, String> startwith) {
        this.startwith = startwith;
    }

    public Map<String, String> getEndwith() {
        return endwith;
    }

    public void setEndwith(Map<String, String> endwith) {
        this.endwith = endwith;
    }

    public Map<String, Object> getInMap() {
        return inMap;
    }

    public void setInMap(Map<String, Object> inMap) {
        this.inMap = inMap;
    }

    public Map<String, Object> getNotInMap() {
        return notInMap;
    }

    public void setNotInMap(Map<String, Object> notInMap) {
        this.notInMap = notInMap;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Map<String, Object> getUpdateValue() {
        return updateValue;
    }

    public void setUpdateValue(Map<String, Object> updateValue) {
        this.updateValue = updateValue;
    }

    public String getNoId() {
        return noId;
    }

    public void setNoId(String noId) {
        this.noId = noId;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
