package com.springboot.mutilpletenant.exception;

import java.io.Serializable;

public class BizException extends RuntimeException implements Serializable {
    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected int code;

    public BizException(){

    }

    public BizException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    /**
     * 实例化异常
     *
     * @param msgFormat
     * @param args
     * @return
     */
    public BizException newInstance(String msgFormat, Object... args) {
        return new BizException(this.code, msgFormat, args);
    }

    /**
     * 数据库操作,insert返回0
     */
    public static final BizException DB_INSERT_RESULT_0 = new BizException(
            10040001, "数据库操作,insert返回0");

    /**
     * 数据库操作,update返回0
     */
    public static final BizException DB_UPDATE_RESULT_0 = new BizException(
            10040002, "数据库操作,update返回0");

    /**
     * 数据库操作,selectOne返回null
     */
    public static final BizException DB_SELECTONE_IS_NULL = new BizException(
            10040003, "数据库操作,selectOne返回null");

    /**
     * 数据库操作,list返回null
     */
    public static final BizException DB_LIST_IS_NULL = new BizException(
            10040004, "数据库操作,list返回null");

    /**
     * 租户id不能为null！
     */
    public static final BizException TENANT_IS_NULL = new BizException(
            10050001, "租户id不能为null！");
}
