package com.casic.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PermissionAccessDecisionManager  implements AccessDecisionManager{

    private final Logger logger = LoggerFactory.getLogger(PermissionAccessDecisionManager.class);

    private AccessDecisionVoter<Object> roleVoter;

    public PermissionAccessDecisionManager() {
        roleVoter = new PermissionVoter();
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        int result = roleVoter.vote(authentication,object,configAttributes);
        switch (result) {
            case (RoleVoter.ACCESS_DENIED):
                throw new AccessDeniedException("用户权限不足");
            case (RoleVoter.ACCESS_GRANTED):
                logger.debug(authentication.getPrincipal() + "因为拥有权限而通过");
                return;
            default:
                throw new AccessDeniedException("不支持的用户类型");
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
