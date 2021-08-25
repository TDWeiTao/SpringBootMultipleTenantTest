package com.springboot.mutilpletenant.entity.base;

import lombok.Data;

/**
 * @Author taowei
 * @content 基类
 * @Date 2021/8/24
 * @Param
 **/
@Data
public class BaseEntity {
    /** 租户id */
    private Long tenantId;
}
