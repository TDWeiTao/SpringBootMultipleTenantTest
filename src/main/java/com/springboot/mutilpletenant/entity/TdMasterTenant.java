package com.springboot.mutilpletenant.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Johan
 * @since 2021-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TdMasterTenant对象", description="")
public class TdMasterTenant extends Model<TdMasterTenant> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
