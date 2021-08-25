package com.springboot.mutilpletenant.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回参数
 */
@Data
@ApiModel(value = "统一返回参数对象",description = "统一返回参数")
public class R<T> implements Serializable {

    private static final long serialVersionUID = 8842525634451996950L;

    /**
     * 是否成功
     */
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    /**
     * 返回码
     */
    @ApiModelProperty(value = "返回码")
    private Integer code;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "返回消息")
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private T data;


    public R() {
    }


    /**
     * 成功静态方法,无传参
     */
    public static <T>R<T> ok() {
        R<T>  r = new R<T> ();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    /**
     * 成功静态方法，传msg
     */
    public static <T>R<T> ok(String msg) {
        R<T>  r = new R<T> ();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage(msg);
        return r;
    }

    /**
     * 成功静态方法，传数据对象
     */
    public static <T>R<T> ok(T data) {
        R<T>  r = new R<T> ();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setData(data);
        return r;
    }

    /**
     * 成功静态方法，传数据对象
     */
    public static <T>R<T> ok(T data,String message) {
        R<T>  r = new R<T> ();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setData(data);
        r.setMessage(message);
        return r;
    }

    /**
     * 失败静态方法，无传参
     */
    public static <T>R<T>  error() {
        R<T>  r = new R<T> ();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    /**
     * 失败静态方法，传msg
     */
    public static <T>R<T>  error(String msg) {
        R<T>  r = new R<T> ();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage(msg);
        return r;
    }

    /**
     * 失败静态方法，传code和msg
     */
    public static <T>R<T>  error(int code, String msg) {
        R<T>  r = new R<T> ();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }

    public T getData() {
        return data;
    }
}
