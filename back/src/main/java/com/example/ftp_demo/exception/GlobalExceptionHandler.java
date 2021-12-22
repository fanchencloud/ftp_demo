package com.example.ftp_demo.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.example.ftp_demo.vo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Version: V1.0    <br/>
 * Datetime:   2021/12/21 4:50   <br/>
 * Description: 全局异常处理
 *
 * @author: chen
 */
@Slf4j
@RestControllerAdvice
@Order(2)
public class GlobalExceptionHandler {
    /**
     * 未登录异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotLoginException.class)
    public Msg notLoginException(NotLoginException e) {
        log.error("错误信息: {} ", e.getMessage());
        return Msg.error(101, e.getMessage());
    }

    /**
     * 权限异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotPermissionException.class)
    public Msg notLoginException(NotPermissionException e) {
        log.error("错误信息: {} ", e.getMessage());
        return Msg.error(401, e.getMessage());
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Msg httpReqMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("错误信息:{}", e.getLocalizedMessage());
        return Msg.error(100, "请求方式不支持");
    }

    /**
     * 通用异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Msg exception(Exception e) {
        return Msg.failMessage(e.getMessage());
    }
}
