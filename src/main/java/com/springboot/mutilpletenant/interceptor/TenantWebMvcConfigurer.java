package com.springboot.mutilpletenant.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author taowei
 * @content 
 * @Date 2021/8/19
 * @Param 需要拦截的路径配置
 **/
@Configuration
public class TenantWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantContextHandlerInterceptor()).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    protected TenantContextHandlerInterceptor tenantContextHandlerInterceptor() {
        return new TenantContextHandlerInterceptor();
    }
}
