package com.mnze.restfull.exception;

import com.mnze.restfull.response.HttpResponse;
import com.mnze.restfull.response.ResponseCode;
import jdk.internal.dynalink.beans.StaticClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaolingfeng
 * @date 2023/4/12 1:15 PM
 */

@Slf4j
@ResponseBody
@ControllerAdvice
public class ValidationExceptionHandler{

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
     *
     * @Validated @Valid仅对于表单提交有效，对于以json格式提交将会失效）
     */
    @ExceptionHandler(BindException.class)
    public Map<String, Object> BindExceptionHandler(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        Set<String> messages = new HashSet<>(allErrors.size());
        messages.addAll(allErrors.stream().filter(Objects::nonNull)
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        log.warn("GET请求参数校验异常：{}",messages);
        return HttpResponse.fail(ResponseCode.REQUEST_PARAMETER_INVALID.getCode(),messages.toString());
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *
     * @param e
     * @return
     * @NotBlank @NotNull @NotEmpty
     */
    @ExceptionHandler(ConstraintViolationException.class)
    Map<String,Object> constraintViolationHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(violation -> String.format("%s ( '%s' )： %s", violation.getPropertyPath().toString(),
                        violation.getInvalidValue(), violation.getMessage()))
                .collect(Collectors.toList()));
        log.warn("Params 请求参数校验异常：{}",messages);
        return HttpResponse.fail(ResponseCode.REQUEST_PARAMETER_INVALID.getCode(),messages.toString());
    }

    /**
     * @param e 异常类
     * @return 响应
     * @Validated @Valid 前端提交的方式为json格式有效
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String,Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        Set<String> messages = new HashSet<>(allErrors.size());
        messages.addAll(allErrors.stream().filter(Objects::nonNull)
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        log.warn("POST 请求参数校验异常：{}",messages);
        return HttpResponse.fail(ResponseCode.REQUEST_PARAMETER_INVALID.getCode(),messages.toString());
    }

    @ExceptionHandler(Exception.class)
    Map<String,Object> systemErrorHandler(Exception e){
        log.error("未知异常，请联系管理员");
        return HttpResponse.fail(ResponseCode.SYSTEM_ERROR.getCode(),ResponseCode.SYSTEM_ERROR.getMsg());
    }
}
