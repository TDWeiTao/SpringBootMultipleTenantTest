package com.springboot.mutilpletenant.service.impl;

import com.springboot.mutilpletenant.entity.TdMasterTenant;
import com.springboot.mutilpletenant.mapper.TdMasterTenantMapper;
import com.springboot.mutilpletenant.service.TdMasterTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Johan
 * @since 2021-08-24
 */
@Service
public class TdMasterTenantServiceImpl extends ServiceImpl<TdMasterTenantMapper, TdMasterTenant> implements TdMasterTenantService {

}
