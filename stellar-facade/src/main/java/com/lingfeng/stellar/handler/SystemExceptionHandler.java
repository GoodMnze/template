package com.lingfeng.stellar.handler;

import com.lingfeng.stellar.response.HttpResponse;
import com.lingfeng.stellar.response.ResponseCode;
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
        log.error("系统异常",e);
        return HttpResponse.fail(ResponseCode.SYSTEM_ERROR.getCode(),ResponseCode.SYSTEM_ERROR.getMsg());
    }
}
