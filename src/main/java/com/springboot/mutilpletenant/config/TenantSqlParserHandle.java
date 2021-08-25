package com.springboot.mutilpletenant.config;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;

import java.util.List;

/**
 * @Author taowei
 * @content TenantSqlParser扩展
 * @Date 2021/8/24
 * @Param
 **/
public class TenantSqlParserHandle extends TenantSqlParser {
    /**
     * insert 语句处理
     */
    @Override
    public void processInsert(Insert insert) {
        if (getTenantHandler().doTableFilter(insert.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        if (isAleadyExistTenantColumn(insert)) {
            return;
        }
        insert.getColumns().add(new Column(getTenantHandler().getTenantIdColumn()));
        if (insert.getSelect() != null) {
            processPlainSelect((PlainSelect) insert.getSelect().getSelectBody(), true);
        } else if (insert.getItemsList() != null) {
            ItemsList itemsList = insert.getItemsList();
            if (itemsList instanceof MultiExpressionList) {
                ((MultiExpressionList) itemsList).getExprList().forEach(el -> el.getExpressions().add(getTenantHandler().getTenantId(false)));
            } else {
                ((ExpressionList) insert.getItemsList()).getExpressions().add(getTenantHandler().getTenantId(false));
            }
        } else {
            throw ExceptionUtils.mpe("Failed to process multiple-table update, please exclude the tableName or statementId");
        }
    }

    /**
     * 判断是否存在租户id列字段
     * @param insert
     * @return 如果已经存在，则绕过不执行
     */
    private boolean isAleadyExistTenantColumn(Insert insert) {
        List<Column> columns = insert.getColumns();
        if(columns == null || columns.size() <= 0){
            return false;
        }
        String tenantIdColumn = getTenantHandler().getTenantIdColumn();
        return columns.stream().map(Column::getColumnName).anyMatch(tenantId -> tenantId.equals(tenantIdColumn));
    }
}
