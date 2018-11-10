package com.dxt.common.exception;

import com.dxt.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultExceptionHandler {

    Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Result<?> defaultErrorHandler(HttpServletRequest request, Exception ex) {
        logger.error(ErrorCode.SYSTEM_ERROR.getErrorReason(),ex);
        return Result.faild(ErrorCode.SYSTEM_ERROR.getErrorCode(),ErrorCode.SYSTEM_ERROR.getErrorReason());
    }

    @ResponseBody
    @ExceptionHandler({SystemException.class})
    public Result<?> defaultErrorHandler(HttpServletRequest request, SystemException ex) {
        logger.warn(ex.getErrorReason(),ex);
        return Result.faild(ex.getErrorCode(),ex.getErrorReason());
    }
}
