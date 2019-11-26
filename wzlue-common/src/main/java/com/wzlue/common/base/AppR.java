package com.wzlue.common.base;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * APP返回数据
 *
 * @author chenshun
 * @email wzlue.com
 * @date 2016年10月27日 下午9:59:27
 */
public class AppR extends HashMap<String, Object>{
    private static final long serialVersionUID = 1L;

    public AppR() {
        super.put("code", 0);
        super.put("message", "请求成功");
        super.put("data",new HashMap<String,Object>());

    }

    public AppR(String message) {
        super.put("code", 0);
        super.put("message", message);
        super.put("data",new HashMap<String,Object>());

    }

    public AppR(Integer code,String message) {
        super.put("code", code);
        super.put("message", message);
        super.put("data",new HashMap<String,Object>());

    }

    public static AppR error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static AppR error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static AppR error(int code, String msg) {
        return new AppR(code,msg);
    }

    public static AppR ok(String msg) {
        AppR r = new AppR(msg);
        return r;
    }

    public static AppR ok(Map<String, Object> map) {
        AppR r = new AppR();
        r.putAll(map);
        return r;
    }

    public static AppR ok() {
        return new AppR();
    }

    public AppR put(String key, Object value) {
        Map<String,Object> data =(Map<String,Object>) super.get("data");
        data.put(key, value);
        return this;
    }

    public AppR putDataAll(Map<String,Object> map) {
        Map<String,Object> data =(Map<String,Object>) super.get("data");
        data.putAll(map);
        return this;
    }
}
