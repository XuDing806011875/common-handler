package com.dxt.common.exception;

import org.apache.commons.lang.StringUtils;

public enum  ErrorCode {

    PART_ERROR("1000", "部分成功"),
    OTHER_ERROR("1001", "其他错误"),
    CALC_MD5_ERROR("1002", "calc MD5 error"),
    SYSTEM_LIMITER("1003", "系统限流保护"),
    API_LIMITER("1004", "API限流保护"),
    ILLEGAL_CONCURRENT_CALL("1005", "非法重复调用"),
    TIME_OUT("1006", "系统超时"),
    CONNECTION_TIME_OUT("1007", "连接超时"),
    VERIFY_FAILED("1008", "验签失败"),
    VERIFY_EXPIRE("1009", "签名已过期"),
    UNZIP_FAILED("1010", "解压失败"),
    ENCODER_FAILED("1011", "编码失败"),
    DECODER_FAILED("1012", "解码失败"),
    CONNECT_ERROR("1013", "连接异常"),
    MISSING_SERVER_PROVIDER("1014", "缺失服务提供者"),
    SYSTEM_ERROR("1015", "系统错误"),

    PARAM_ERROR("1500", "参数错误"),

    NOT_SUPPORT_METHOD("1501", "不支持的调用"),
    NOT_SUPPORT_OPERATOR("1502", "不支持的操作"),
    SERVER_REFUSE("1502", "服务拒绝"),
    ILLEGAL_ARGUMENT("1503", "非法参数"),
    JSON_SERIALIZE_FAIL("1504", "json序列化失败"),
    CONCURRENT_CACHE_KEY_TIMEOUT("1505", "concurrent cache key timeout"),
    PARAM_MISSING("1506", "缺少参数");

    private String errorCode;
    private String errorReason;

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorReason()
    {
        return this.errorReason;
    }

    ErrorCode(String errorCode, String errorReason) {
        this.errorCode = errorCode;
        this.errorReason = errorReason;
    }

    /**
     * 判断执行码是否存在
     * @param code
     * @return
     */
    public static boolean isMember(String code) {
        if(StringUtils.isBlank(code)){
            return false;
        }
        for (ErrorCode errorCode : values()) {
            if (code.equals(errorCode.getErrorCode())) {
                return true;
            }
        }
        return false;
    }
}
