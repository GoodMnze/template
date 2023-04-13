package com.mnze.restfull.response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class HttpResponse {

    private static final Integer SUCCESS_CODE = 200;
    private static final String SUCCESS_MSG = "";

    private static final Integer SYSTEM_ERROR_CODE = 500;
    private static final String SYSTEM_ERROR_MSG = "系统异常";


    public static Map<String, Object> success() {
        return success(null);
    }

    public static Map<String, Object> success(Object data) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("code", SUCCESS_CODE);
        map.put("message", SUCCESS_MSG);
        map.put("data", data);

        return map;
    }

    public static Map<String, Object> successWithMsg(Integer code ,String msg, Object data) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("code", code);
        map.put("message", msg);
        map.put("data", data);
        return map;
    }

    public static Map<String, Object> successWithPage(int totalCount, Collection<?> items) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", totalCount);
        map.put("items", items);

        return success(map);
    }

    public static Map<String, Object> fail() {

        return fail(SYSTEM_ERROR_CODE, SYSTEM_ERROR_MSG);
    }

    public static Map<String, Object> fail(Integer code) {
        return fail(code, null);
    }


    public static Map<String, Object> fail(Integer code, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", null);
        return map;
    }

}