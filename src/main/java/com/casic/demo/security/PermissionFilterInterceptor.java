package com.casic.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * https://www.cnblogs.com/final-elysion/p/6278180.html
 * 该过滤器需要注入FilterInvocationSecurityMetadataSource，AccessDecisionManager 和 UserDetailsService
 */
@Component
public class PermissionFilterInterceptor extends AbstractSecurityInterceptor implements Filter{

    private static final String FILTER_APPLIED = "__spring_security_permissionFilterInterceptor_filterApplied";

    @Autowired
    PermissionSecurityMetadataSource metadataSource;

    @Autowired
    public void setPermissionAccessDecisionManager(PermissionAccessDecisionManager permissionAccessDecisionManager) {
        super.setAccessDecisionManager(permissionAccessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request,response,chain);
        invoke(fi);
    }

    /**
     * @see FilterSecurityInterceptor
     */
    private void invoke(FilterInvocation fi) throws  IOException, ServletException {
        // 每次请求都会执行两次验证，所以在第一次请求的时候，为request设置一个自定义attribute，避免第二执行过滤
        if ((fi.getRequest() != null)
                && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } else {
            if (fi.getRequest() != null) {
                fi.getRequest().setAttribute(FILTER_APPLIED , Boolean.TRUE);
            }
            InterceptorStatusToken token = super.beforeInvocation(fi);
            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            }finally {
                super.finallyInvocation(token);
            }
            super.afterInvocation(token, null);
        }
    }



    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.metadataSource;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void destroy() {
    }
}
