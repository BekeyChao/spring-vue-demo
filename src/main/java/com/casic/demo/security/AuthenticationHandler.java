package com.casic.demo.security;

import com.casic.demo.entity.RestResult;
import com.casic.demo.entity.SysUser;
import com.casic.demo.utils.ResultGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationHandler implements AuthenticationFailureHandler, AuthenticationSuccessHandler, LogoutSuccessHandler {
    Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);

    private final ResultGenerator generator;

    @Autowired
    public AuthenticationHandler(ResultGenerator generator) {
        this.generator = generator;
    }

    /**
     * 登陆登陆异常的操作
     * @param exception 异常消息
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        RestResult restResult = generator.getFailResult(exception.getLocalizedMessage());
        ObjectMapper mapper = new ObjectMapper();
        returnJson(response, mapper.writeValueAsString(restResult));
    }

    /**
     * 处理登陆成功后的操作
     * @param authentication 用户信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        RestResult restResult = generator.getSuccessResult("登陆成功", sysUser);
        returnJson(response, restResult);
    }

    /**
     * 处理登出成功后的操作
     * @param authentication 用户信息
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        RestResult restResult = generator.getSuccessResult("登出成功");
        returnJson(response, restResult);
    }

    /**
     * 简单封装返回json数据
     */
    private void returnJson(HttpServletResponse response, Object object) {
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            writer = response.getWriter();
            writer.print(mapper.writeValueAsString(object));
        } catch (IOException e) {
            logger.warn("登陆失效状态发送异常");
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
