package com.mnze.exception.handler;

import com.mnze.response.HttpResponse;
import com.mnze.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author xiaolingfeng
 * @create 2024/5/27
 */

@Slf4j
@ResponseBody
@ControllerAdvice
public class SystemExceptionHandler {
    @ExceptionHandler(Exception.class)
    Map<String,Object> systemErrorHandler(Exception e){
        log.error("未知异常，请联系管理员");
        return HttpResponse.fail(ResponseCode.SYSTEM_ERROR.getCode(),ResponseCode.SYSTEM_ERROR.getMsg());
    }
}
