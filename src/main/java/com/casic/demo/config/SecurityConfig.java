package com.casic.demo.config;

import com.casic.demo.security.AuthenticationHandler;
import com.casic.demo.security.PermissionFilterInterceptor;
import com.casic.demo.security.PublicResourceConfig;
import com.casic.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    UserService userService;
    @Autowired
    PermissionFilterInterceptor permissionFilterInterceptor;
    @Autowired
    AuthenticationHandler authenticationHandler;


    @Bean
    UserDetailsService customUserService() {
        return this.userService;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());  //注入密码加密
        return provider;
    }

    @Bean
    /**
     * BCryptPasswordEncoder 使用BCrypt的强散列哈希加密实现，并可以由客户端指定加密的强度strength，强度越高安全性自然就越高，默认为10.
     * 框架推荐的加密实现
     */
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserService());
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //这里设置里也是无效的
//        web.ignoring().antMatchers("/css/**","/js/**","/index.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() //不拦截跨域
            // 开启匿名访问
            .antMatchers("/**").permitAll()
            // 使用登陆控制
            .anyRequest().authenticated()
            .and()
            .formLogin().loginProcessingUrl("/login").loginPage(PublicResourceConfig.UNAUTHORIZED).successHandler(authenticationHandler).failureHandler(authenticationHandler)
            .and()
            .logout().logoutSuccessHandler(authenticationHandler);
        http.addFilterBefore(permissionFilterInterceptor,FilterSecurityInterceptor.class).csrf().disable();   //添加一个查看权限的Filter
    }
}