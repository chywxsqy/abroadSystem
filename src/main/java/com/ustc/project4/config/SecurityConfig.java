package com.ustc.project4.config;

import com.ustc.project4.controller.filter.AuthenticationTokenFilter;
import com.ustc.project4.service.UserService;
import com.ustc.project4.util.Project4Constant;
import com.ustc.project4.util.Project4Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements Project4Constant {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationTokenFilter authenticationTokenFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    //AuthenticationManager:认证的核心接口
    //AuthenticationManagerBuilder:上面的build工具
    //providerManager:AuthenticationManager的实现类
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //内置的认证规则
////        auth.userDetailsService(userService).passwordEncoder(new Pbkdf2PasswordEncoder("12345"));
//        //自定义认证规则
//        //AuthenticationProvider:providerManager持有一组AuthenticationProvider，每个providerManager
//        // 负责一种认证(委托模式）
//        auth.authenticationProvider(new AuthenticationProvider() {
//            //Authentication:认证信息
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                return null;
//            }
//            //当前的AuthenticationProvider支持何种类型认证
//            @Override
//            public boolean supports(Class<?> aClass) {
//                return false;
//            }
//        });
//    }

    //不用security提供的认证，只使用security的授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //授权
        http.authorizeRequests()
                .antMatchers(
                        "/logout",
                        "/user/myInfo",
                        "/user/avatar",
                        "/user/password",
                        "/user/passwordS",
                        "/discussPost",
                        "comment"
                )
                .hasAnyAuthority(
                        "user",
                        "admin",
                        "moderator")
                .anyRequest().permitAll()
                .and().csrf().disable();

        //权限不够时的处理
        http.exceptionHandling()
                //未登录时
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//                        System.out.println("config");
//                        System.out.println(request.getCharacterEncoding());
//                        System.out.println(response.getCharacterEncoding());
//                        response.setCharacterEncoding("utf-8");
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(Project4Util.getJSONString(CODE_NO_LOGIN, "你还未登录！", null));
                    }
                })
                //权限不足
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
//                        response.setCharacterEncoding("utf-8");
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(Project4Util.getJSONString(CODE_DENIED, "权限不足！", null));
                    }
                });

        //跳过(覆盖默认)logout功能，让自己的logout逻辑能执行
        http.logout().logoutUrl("/xxxxxx");

        //增加自定义过滤器
        // 加上addFilterBefore执行token拦截器
        http .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
