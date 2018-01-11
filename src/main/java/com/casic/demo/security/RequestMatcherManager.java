package com.casic.demo.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 请求匹配默认实现 {@link AntPathRequestMatcher}
 * 集合实现方式为HashSet
 * 同时也是SpringMVC请求匹配默认实现
 */
public class RequestMatcherManager {
    // 管理matcher的容器
    private Set<RequestMatcher> matcherSet;
    // 分隔符 默认,
    private String separator;
    // 允许标志 默认 ALL
    private String allPermit;

    /**
     * @param request 请求信息
     * @return 符合任意一个matches则返回true
     */
    public boolean matches(HttpServletRequest request) {
        for (RequestMatcher matcher : matcherSet) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 无参构造器
     */
    public RequestMatcherManager() {
        matcherSet = new HashSet<>();
        this.separator = ",";
        this.allPermit = "ALL";
    }

    /**
     * 清除RequestMatcher
     */
    public void clear() {
        matcherSet.clear();
    }

    public void addMatcher(RequestMatcher matcher) {
        matcherSet.add(matcher);
    }

    private void addMatcher(String pattern, String method) {
        if (!StringUtils.hasText(method) || allPermit.equals(method)) {
            method = null;
        }
        addMatcher(new AntPathRequestMatcher(pattern, method));
    }

    /**
     * 批量添加matcher
     * @param matchers 任意集合类型，需实现foreach接口
     */
    public void addMatchers(Collection<RequestMatcher> matchers) {
        matchers.forEach(this::addMatcher);
    }

    /**
     * 简易版本的批量添加matcher
     * @param patternString 将会以分隔符分解（默认为 ,）为数组类型，再通过创建AntRequest添加
     * @param method 指定的方法类型 ， 为空 或者为 ALL_ALLOW(默认值为ALL) 则默认匹配所有方法
     * {@link org.springframework.web.bind.annotation.RequestMethod}
     */
    public void addMatchers(String patternString, String method) {
        String[] patterns = patternString.split(separator);
        for (String pattern : patterns) {
            addMatcher(pattern, method);
        }
    }

    /**
     * 重载版本的addMatchers
     */
    public void addMatchers(String patternString) {
        String[] patterns = patternString.split(separator);
        for (String pattern : patterns) {
            addMatcher(pattern, null);
        }
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setAllPermit(String allPermit) {
        this.allPermit = allPermit;
    }
}
