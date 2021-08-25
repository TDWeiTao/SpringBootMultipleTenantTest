package com.springboot.mutilpletenant.context;

/**
 * @Author taowei
 * @content
 * @Date 2021/8/18
 * @Param 线程隔离变量类
 **/
public class BaseContextHandler {
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void setTenant(String tenantId){
        THREAD_LOCAL.set(tenantId);
    }

    public static String getTenantId(){

        return THREAD_LOCAL.get();
    }

    public static void clear(){
        THREAD_LOCAL.remove();
    }
}
