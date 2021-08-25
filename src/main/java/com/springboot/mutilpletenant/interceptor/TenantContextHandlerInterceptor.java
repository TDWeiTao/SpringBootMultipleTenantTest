package com.springboot.mutilpletenant.interceptor;


import com.baomidou.dynamic.datasource.toolkit.StringUtils;
import com.springboot.mutilpletenant.context.BaseContextHandler;
import com.springboot.mutilpletenant.exception.BizException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author taowei
 * @content
 * @Date 2021/8/18
 * @Param 租户上下文拦截器
 **/
@Slf4j
@NoArgsConstructor
public class TenantContextHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            log.info("未映射到方法,url:{}", request.getRequestURL());
            return false;
        }

        String tenantId = request.getParameter("tenantId");

        if (StringUtils.isBlank(tenantId)) {
            throw BizException.TENANT_IS_NULL;
        }
        BaseContextHandler.setTenant(tenantId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
