package com.dxt.common.exception;

public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 3291170335384811136L;

    private String errorCode;
    private String errorReason;

    public SystemException(String errorCode, String errorReason)
    {
        super("errorCode:" + errorCode + "-errorReason:" + errorReason);
        this.errorCode = errorCode;
        this.errorReason = errorReason;
    }

    public SystemException(ErrorCode errorCode) {
        this(errorCode.getErrorCode(), errorCode.getErrorReason());
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorReason() {
        return this.errorReason;
    }

    public SystemException setCause(Throwable t) {
        super.initCause(t);
        return this;
    }
}
