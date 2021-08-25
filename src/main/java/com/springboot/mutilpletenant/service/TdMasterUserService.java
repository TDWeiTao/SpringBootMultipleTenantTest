package com.springboot.mutilpletenant.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.mutilpletenant.entity.TdMasterUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-08-24
 */
public interface TdMasterUserService extends IService<TdMasterUser> {

    /**
     * @Author taowei
     * @content 条件查询
     * @Date 2021/8/24
     * @Param []
     **/
    public List<TdMasterUser> selectByCondition(LambdaQueryWrapper<TdMasterUser> queryWrapper);

    /**
     * @Author taowei
     * @content 保存用户数据
     * @Date 2021/8/24
     * @Param [user]
     **/
    public boolean insertUsers(TdMasterUser user);

}
