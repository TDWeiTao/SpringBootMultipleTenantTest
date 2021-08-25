package com.springboot.mutilpletenant.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.springboot.mutilpletenant.entity.TdMasterUser;
import com.springboot.mutilpletenant.mapper.TdMasterUserMapper;
import com.springboot.mutilpletenant.service.TdMasterUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-08-24
 */
@Service
public class TdMasterUserServiceImpl extends ServiceImpl<TdMasterUserMapper, TdMasterUser> implements TdMasterUserService {

    @Resource
    private TdMasterUserMapper tdMasterUserMapper;

    @Override
    @DS("slave_1")
    public List<TdMasterUser> selectByCondition(LambdaQueryWrapper<TdMasterUser> queryWrapper) {
        return tdMasterUserMapper.selectList(queryWrapper);
    }

    @Override
    public boolean insertUsers(TdMasterUser user) {
        return tdMasterUserMapper.insert(user) > 0;
    }
}
