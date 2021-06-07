package com.ustc.project4.controller.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ustc.project4.entity.User;
import com.ustc.project4.service.UserService;
import com.ustc.project4.util.HostHolder;
import com.ustc.project4.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private UserService userService; //用户信息service

    @Autowired
    private HostHolder hostHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        SecurityContextHolder.clearContext();
        String token = request.getHeader("token");
        try{
            DecodedJWT decodedToken = JWTUtil.getDecodedToken(token);
            int id = Integer.parseInt(decodedToken.getClaim("id").asString());
            User loginUser = userService.findUserById(id);
            hostHolder.setUser(loginUser);

            //构建用户认证结果，并存入SecurityContext，以便于Security获取
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, loginUser.getPassword(),
                    loginUser.getAuthorities());
            SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        } catch (Exception ignore) {
            //token无效
        }

        chain.doFilter(request, response);

        if(hostHolder.getUser()==null) {
            request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
            SecurityContextHolder.clearContext();
        }
        hostHolder.clear();
    }
}