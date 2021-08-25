package com.springboot.mutilpletenant.config;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.springboot.mutilpletenant.context.BaseContextHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author taowei
 * @content 租户处理器 -主要实现mybatis-plus
 * @Date 2021/8/20
 * @Param
 **/
@Slf4j
@Component
public class MyTenantHandler implements TenantHandler {

    /**
     * 多租户id
     */
    private static final String TENANT_ID_COLUMN_NAME = "tenant_id";

    private static final List<String> FILTER_TABLE_LIST = new ArrayList<>();

    static {
        //不需要不用tenant_id过滤的表
        FILTER_TABLE_LIST.add("td_master_tenant");
    }

    @Override
    public Expression getTenantId(boolean where) {
        Long tenantId = Long.valueOf(BaseContextHandler.getTenantId());
        log.info("当前租户为{}", tenantId);
        return new LongValue(BaseContextHandler.getTenantId());
    }

    @Override
    public String getTenantIdColumn() {
        return TENANT_ID_COLUMN_NAME;
    }

    @Override
    public boolean doTableFilter(String tableName) {
        return FILTER_TABLE_LIST.stream().anyMatch(x->x.contains(tableName.toLowerCase()));
    }
}
