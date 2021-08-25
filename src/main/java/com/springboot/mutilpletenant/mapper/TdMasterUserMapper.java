package com.springboot.mutilpletenant.mapper;

import com.springboot.mutilpletenant.entity.TdMasterUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-08-24
 */
public interface TdMasterUserMapper extends BaseMapper<TdMasterUser> {

    /**
     * @Author taowei
     * @content 新增去掉id，防止报id不能为null异常
     * @Date 2021/8/24
     * @Param [tdMasterUser]
     **/
    @Insert({
            "insert into td_master_user (" +
                    "user_name, " +
                    "password, " +
                    "create_time, " +
                    "nick_name, " +
                    "tenant_id, " +
                    "age)",
            "values (#{userName,jdbcType=VARCHAR}, " +
                    "#{password,jdbcType=INTEGER}, " +
                    "#{createTime,jdbcType=VARCHAR}, " +
                    "#{nickName,jdbcType=VARCHAR}, " +
                    "#{tenantId,jdbcType=VARCHAR}, " +
                    "#{age,jdbcType=DATE})"
    })
    @Override
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insert(TdMasterUser tdMasterUser);
}
