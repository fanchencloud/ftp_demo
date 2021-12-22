package com.example.ftp_demo.vo;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Version: V1.0    <br/>
 * Datetime:   2021/12/21 2:57   <br/>
 * Description: 统一返回格式
 *
 * @author: chen
 */
@Data
public class Msg {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 封装有效数据
     * 具体的内容
     */
    private Map<String, Object> data;

    /**
     * 私有化构造函数，防止手动创建实例
     */
    private Msg() {
    }

    /**
     * 成功返回
     */
    public static Msg success() {
        Msg result = new Msg();
        result.setCode(200);
        result.setMessage("success");
        return result;
    }

    /**
     * 附带消息成功返回
     *
     * @param msg 返回消息内容
     */
    public static Msg msg(String msg) {
        Msg result = new Msg();
        result.setCode(200);
        result.setMessage(msg);
        return result;
    }

    /**
     * 请求失败
     */
    public static Msg fail() {
        Msg result = new Msg();
        result.setCode(400);
        result.setMessage("fail");
        return result;
    }

    /**
     * 请求失败
     */
    public static Msg failMessage(String msg) {
        Msg result = new Msg();
        result.setCode(400);
        if (CharSequenceUtil.isBlankOrUndefined(msg)) {
            result.setMessage("fail");
        } else {
            result.setMessage(msg);
        }
        return result;
    }

    /**
     * 无权限访问
     */
    public static Msg noPermission() {
        Msg result = new Msg();
        result.setCode(401);
        result.setMessage("no permission");
        return result;
    }

    /**
     * 错误请求
     */
    public static Msg error() {
        Msg result = new Msg();
        result.setCode(500);
        result.setMessage("error");
        return result;
    }

    /**
     * 错误
     *
     * @param code 状态码
     * @param msg  消息
     * @return ResultBean
     */
    public static Msg error(Integer code, String msg) {
        Msg result = new Msg();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    /**
     * 错误
     *
     * @param msg 消息
     * @return ResultBean
     */
    public static Msg errorMessage(String msg) {
        Msg result = new Msg();
        result.setCode(100);
        result.setMessage(msg);
        return result;
    }

    public Msg add(String key, Object value) {
        if (this.data == null) {
            this.data = new HashMap<>(4);
        }
        this.data.put(key, value);
        return this;
    }
}
