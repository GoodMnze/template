package com.mnze.restfull.response;

import java.util.HashMap;
import java.util.Map;

public enum ResponseCode {
    /** 成功响应，不应被修改 */
    SUCCESS(0, "请求成功"),
    /** 预期外的错误，上下游需要极度重视，并且敦促改正 */
    SYSTEM_ERROR(99999, "系统异常，请联系维护团队尽快解决"),

    /** 1000 - 1100  业务异常 **/
    REQUEST_PARAMETER_INVALID (1000, "请求参数非法参数");

    /** 响应码信息 */
    final int code;
    /** 响应信息 */
    final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    static {
        // 运行时兜底检查是否存在重复响应码，不允许此场景发生
        Map<Integer, Boolean> map = new HashMap<>();
        for (ResponseCode responseCode : ResponseCode.values()) {
            Boolean res = map.putIfAbsent(responseCode.getCode(), true);
            if (res != null) {
                map.clear();
                map = null;
                throw new RuntimeException(String.format("启动时自检，发现重复的响应码: %d, 请检查响应码设计是否正确！", responseCode.getCode()));
            }
        }
        map.clear();
        map = null;
    }

}
