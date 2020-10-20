package com.wh.utils;

import com.wh.core.CommonConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-14 19:26
 */
@ApiModel("响应信息主体")
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("返回标记：成功标记=0，失败标记=1")
    private int code;
    @ApiModelProperty("返回信息")
    private String msg;
    @ApiModelProperty("数据")
    private T data;

    public static <T> R<T> ok() {
        return (R<T>) restResult((Object) null, CommonConstants.SUCCESS, (String) null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, (String) null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return (R<T>) restResult((Object) null, CommonConstants.FAIL, (String) null);
    }

    public static <T> R<T> failed(String msg) {
        return (R<T>) restResult((Object) null, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, (String) null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    @Override
    public String toString() {
        return "R(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }

    public R() {
    }

    public R(final int code, final String msg, final T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public R<T> setCode(final int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public R<T> setMsg(final String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public R<T> setData(final T data) {
        this.data = data;
        return this;
    }
}
