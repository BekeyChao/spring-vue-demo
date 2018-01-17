package com.casic.demo.security;

import com.casic.demo.entity.Permission;
import com.casic.demo.entity.SysRole;
import com.casic.demo.service.RoleAndPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 在spring初始化的时候获取资源
 */
@Component
public class PermissionSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final Logger logger = LoggerFactory.getLogger(PermissionSecurityMetadataSource.class);

    // 配置匿名用户访问的资源
    private RequestMatcherManager publicManager = new RequestMatcherManager();
    // 配置登陆用户可以访问的资源
    private RequestMatcherManager authenticatedManager = new RequestMatcherManager();

    private final RoleAndPermissionService roleAndPermissionService;

    private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    private static final String ROLE_AUTHENTICATED = "ROLE_AUTHENTICATED";

    @Autowired
    public PermissionSecurityMetadataSource(RoleAndPermissionService roleAndPermissionService) {
        this.roleAndPermissionService = roleAndPermissionService;
        updateRequests();
    }

    /**
     * 核心方法，获取url 所需要的权限（角色）
     * @return 若返回null 则不拦截此url
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        boolean isAnonymousUser = principal.equals("anonymousUser");
        logger.debug(principal + "正在访问：" + request.getRequestURI() );
        // 先检查是否访问用户资源
        if (!isAnonymousUser && access(authenticatedManager, request)) {
            logger.debug(principal + "因为访问用户资源而通过");
            return null;
        }
        // 检查是否访问公共资源
        if (access(publicManager, request)) {
            logger.debug(principal + "因为访问公共资源而通过");
            return null;
        }
        // 匿名用户访问公共资源抛出未登录异常
        if (isAnonymousUser) {
            throw new AccessDeniedException("未登录");
        }
        // 随意返回一个集合
        Collection<ConfigAttribute> collection = new HashSet<>();
        collection.add(new SecurityConfig("ROLE"));
        return collection;
    }

    /**
     * 在初始化及资源变动时更新资源
     */
    public void updateRequests() {
        SysRole auth = roleAndPermissionService.findRoleByRoleName(ROLE_AUTHENTICATED);
        updateRequests(auth, authenticatedManager);
        SysRole pub = roleAndPermissionService.findRoleByRoleName(ROLE_ANONYMOUS);
        if (pub == null || pub.getPermissions() == null || pub.getPermissions().size() == 0) {
            // 如果公共资源未设置， 添加默认的公共资源
            logger.debug("数据库中未发现设定的公共资源，添加默认资源");
            publicManager.clear();
            publicManager.addMatchers(PublicResourceConfig.STATIC_RESOURCES);
            publicManager.addMatchers(PublicResourceConfig.UNAUTHORIZED);
        } else {
            updateRequests(pub, publicManager);
        }
        logger.debug("公共资源 及 用户资源 加载完毕" );
    }

    private void updateRequests(SysRole sysRole, RequestMatcherManager requests) {
        requests.clear();
        if (sysRole == null) {
            return;
        }
        List<Permission> permissions = sysRole.getPermissions();
        if (permissions == null || permissions.size() == 0) {
            return;
        }
        permissions.forEach(permission -> {
            requests.addMatchers(permission.getResources(), permission.getMethod());
        });
    }

    private boolean access(RequestMatcherManager manager, HttpServletRequest request) {
        return manager.matches(request);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
