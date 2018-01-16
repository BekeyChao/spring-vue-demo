package com.casic.demo.security;

/**
 * 公共资源配置，为防止因没有配置数据库导致的无限重定向
 */
public class PublicResourceConfig {
    public static final String STATIC_RESOURCES = "/,/**/*.html,/**/*.js,/**/*.css,/**/*.icon";

    public static final String UNAUTHORIZED = "/public/unauthorized";
}
