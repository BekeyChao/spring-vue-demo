package com.casic.demo.handler;

import com.casic.demo.entity.RestResult;
import com.casic.demo.entity.ResultCode;
import com.casic.demo.utils.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理中心
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    static final Logger looger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ResultGenerator generator;

    @Autowired
    public GlobalExceptionHandler(ResultGenerator generator) {
        this.generator = generator;
    }

    /**
     * 为参数验证添加异常处理器
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult handleConstraintViolationException(ConstraintViolationException cve) {
        //这里简化处理了，cve.getConstraintViolations 会得到所有错误信息的迭代，可以酌情处理
        String errorMessage = cve.getConstraintViolations().iterator().next().getMessage();
        return generator.getFailResult(errorMessage);
    }

    /**
     * 主键/唯一约束违反异常
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public RestResult handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        //如果注册两个相同的用户名到报这个异常
        return generator.getFailResult("违反主键/唯一约束条件");
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public RestResult handleRuntimeException(RuntimeException exception) {
        return generator.getFreeResult(ResultCode.INTERNAL_SERVER_ERROR,exception.getMessage(),null);
    }

    /**
     * 全局异常处理，作为兜底
     */
    @ExceptionHandler(Exception.class)
    public RestResult handleRuntimeException(Exception exception) {
        return generator.getFreeResult(ResultCode.INTERNAL_SERVER_ERROR,exception.getMessage(),null);
    }
}
