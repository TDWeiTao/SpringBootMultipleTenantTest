package com.springboot.mutilpletenant.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.mutilpletenant.context.BaseContextHandler;
import com.springboot.mutilpletenant.entity.TdMasterUser;
import com.springboot.mutilpletenant.mapper.TdMasterUserMapper;
import com.springboot.mutilpletenant.result.R;
import com.springboot.mutilpletenant.service.TdMasterUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author taowei
 * @since 2021-08-24
 */
@Slf4j
@Controller
@RequestMapping("/v1/user")
public class TdMasterUserController {

    @Resource
    private TdMasterUserMapper tdMasterUserMapper;

    @Autowired
    private TdMasterUserService tdMasterUserService;

    /**
     * 获取所有用户数据
     * @param
     * @return
     */
    @GetMapping("/allusers")
    @ResponseBody
    private R allusers() {
        String tenantId = BaseContextHandler.getTenantId();
        log.info("当前租户:{}", tenantId);
        List<TdMasterUser> users = tdMasterUserMapper.selectList(null);
        return R.ok(users);
    }

    /**
     * 获取slave用户数据
     * @param age 年龄
     * @return
     */
    @GetMapping("/salveusers/{age}")
    @ResponseBody
    private R salveusers(@PathVariable("age") int age) {
        LambdaQueryWrapper<TdMasterUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TdMasterUser::getAge, age);
        List<TdMasterUser> users = tdMasterUserService.selectByCondition(queryWrapper);
        return R.ok(users);
    }

    /**
     * 创建用户
     * @param
     * @return
     */
    @PostMapping("/users")
    @ResponseBody
    private R users(@RequestBody TdMasterUser user) {
        user.setCreateTime(new Date());
        if(tdMasterUserService.insertUsers(user)){
            return R.ok("用户新增成功！");
        }
        return R.error("用户新增失败！");
    }
}

