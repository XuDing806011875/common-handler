package com.dxt.common.result;

import com.dxt.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统统一返回值对象
 * @param <T>
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 2281603431578227864L;

    /**
     * 状态
     */
    private Status status = new Status("0", "");

    /**
     * 返回对象
     */
    private T result;

    /**
     * 附加说明
     */
    private Map<String, Object> attachment;

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    /**
     * 执行成功并生成返回对象方法。
     * @param result
     * @return
     */
    public static Result success(Object result) {
        Result dataResult = new Result();
        dataResult.setResult(result);
        return dataResult;
    }

    /**
     * 部分成功并生成返回对象方法
     * @param result
     * @return
     */
    public static Result part(Object result)
    {
        Result dataResult = new Result();
        dataResult.setResult(result);
        dataResult.status.setStatusCode(ErrorCode.PART_ERROR.getErrorCode());
        dataResult.status.setStatusReason(ErrorCode.PART_ERROR.getErrorReason());
        return dataResult;
    }

    /**
     * 部分成功并生成返回对象方法（加附加说明）
     * @param key
     * @param value
     * @return
     */
    public static Result makePart(String key, Object value,Object result)
    {
        Result dataResult = new Result();
        dataResult.setResult(result);
        dataResult.addAttachment(key,value);
        dataResult.status.setStatusCode(ErrorCode.PART_ERROR.getErrorCode());
        dataResult.status.setStatusReason(ErrorCode.PART_ERROR.getErrorReason());
        return dataResult;
    }

    /**
     * 执行成功并生成返回对象方法。（加泛型限制）
     * @param result
     * @return
     */
    public static <T> Result<T> success(Object result, Class<T> type)
    {
        Result dataResult = new Result();
        dataResult.setResult(result);
        return dataResult;
    }

    /**
     * 执行异常并生成返回对象方法
     * @param errorCode
     * @param errMsg
     * @return
     */
    public static Result faild(String errorCode, String errMsg) {
        Result dataResult = new Result();
        dataResult.setStatus(new Status(errorCode, errMsg));
        return dataResult;
    }

    /**
     * 执行成功添加附属说明
     * @param key
     * @param value
     * @return
     */
    public static Result successMake(String key, Object value, Object result) {
        Result dataResult = new Result();
        dataResult.setResult(result);
        dataResult.addAttachment(key,value);
        return dataResult;
    }

    /**
     * 判断执行成功
     * @return
     */
    @JsonIgnore
    public boolean isSuccess() {
        return "0".equals(getStatus().getStatusCode());
    }

    /**
     * 判断执行异常
     * @return
     */
    @JsonIgnore
    public boolean isFailed()
    {
        return !isSuccess();
    }

    /**
     * 获取附加说明
     * @return
     */
    public Map<String, Object> getAttachment() {
        return this.attachment;
    }

    /**
     * 添加附加说明
     * @param key
     * @param value
     */
    public void addAttachment(String key, Object value) {
        if (this.attachment == null) {
            this.attachment = new HashMap();
        }
        this.attachment.put(key, value);
    }

    /**
     * 添加附加说明
     * @param attachment
     */
    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
}
