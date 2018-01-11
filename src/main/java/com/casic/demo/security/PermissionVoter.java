package com.casic.demo.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

public class PermissionVoter implements AccessDecisionVoter<Object> {
    /**
     *
     * @param authentication 用户凭证
     * @param object FilterInvocation对象
     * @param attributes not used
     * @return 当用户携带的url满足request时赞成， 否则反对
     *          如果都是不支持类型的凭证，返回 0
     */
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        Iterator<? extends GrantedAuthority> iterator = authentication.getAuthorities().iterator();
        boolean unSupportsAuth = false;
        while (iterator.hasNext()) {
            GrantedAuthority ga = iterator.next();
            if (ga instanceof PermissionGrantedAuthority) {
                PermissionGrantedAuthority auth = (PermissionGrantedAuthority)ga;
                if (auth.getManager().matches(request)) {
                    return AccessDecisionVoter.ACCESS_GRANTED;
                }
            }else {
                unSupportsAuth = true;
            }
        }
        if (unSupportsAuth) {
            return AccessDecisionVoter.ACCESS_ABSTAIN;
        }
        return AccessDecisionVoter.ACCESS_DENIED;
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
