package com.dwg.controller.handler;

import com.dwg.exception.AbstractGameException;
import com.dwg.model.support.BaseResponse;
import com.dwg.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@SuppressWarnings("all")
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public BaseResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            baseResponse = handleBaseException(e.getCause());
        }
        baseResponse.setMessage("字段验证错误，请完善后重试！");
        return baseResponse;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        baseResponse.setMessage(String.format("请求字段缺失, 类型为 %s，名称为 %s", e.getParameterType(), e.getParameterName()));
        return baseResponse;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse handleConstraintViolationException(ConstraintViolationException e) {
        BaseResponse<Map<String, String>> baseResponse = handleBaseException(e);

        baseResponse.setMessage("字段验证错误，请完善后重试！");
        baseResponse.setData(ValidationUtils.mapWithValidError(e.getConstraintViolations()));
        return baseResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BaseResponse<Map<String, String>> baseResponse = handleBaseException(e);

        baseResponse.setMessage("字段验证错误，请完善后重试！");
        Map<String, String> errMap = ValidationUtils.mapWithFieldError(e.getBindingResult().getFieldErrors());
        baseResponse.setData(errMap);
        return baseResponse;
    }

    @ExceptionHandler(BindException.class)
    public BaseResponse handleBindException(Exception e) {
        BaseResponse<?> baseResponse = handleBaseException(e);

        //打印校验住的所有的错误信息
        baseResponse.setMessage(((BindException) e).getAllErrors()
                .stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("字段验证错误，请完善后重试！"));
        return baseResponse;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);

        return baseResponse;
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public BaseResponse handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);

        return baseResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);

        baseResponse.setMessage("缺失请求主体");
        return baseResponse;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponse handleNoHandlerFoundException(NoHandlerFoundException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        HttpStatus status = HttpStatus.BAD_GATEWAY;

        baseResponse.setMessage(status.getReasonPhrase());
        return baseResponse;
    }

    @ExceptionHandler(AbstractGameException.class)
    public BaseResponse handleGameException(AbstractGameException e) {
        BaseResponse<Object> baseResponse = handleBaseException(e);

        baseResponse.setData(e.getErrorData());
        return baseResponse;
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse handleGlobalException(Exception e) {
        BaseResponse baseResponse = handleBaseException(e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        baseResponse.setMessage(status.getReasonPhrase());
        return baseResponse;
    }

    private <T> BaseResponse<T> handleBaseException(Throwable t) {
        Assert.notNull(t, "Throwable must not be null");

        log.error("Captured an exception", t);

        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setFlag(false);
        baseResponse.setMessage(t.getMessage());

        return baseResponse;
    }

}

