package com.dxt.common.result;

import java.io.Serializable;

/**
 * 执行状态类
 */
public class Status implements Serializable {

    private static final long serialVersionUID = 4249579812172238888L;

    /**
     * 执行编码，默认成功
     */
    private String statusCode = "0";

    /**
     * 执行说明
     */
    private String statusReason;

    public Status() { }

    public Status(String statusCode, String message) {
        this.statusCode = statusCode;
        this.statusReason = message;
    }

    public String toString()
    {
        return "{\"statusCode\":" + this.statusCode + ",\"statusReason\":\"" + this.statusReason + "\"}";
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getStatusReason()
    {
        return this.statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
}
