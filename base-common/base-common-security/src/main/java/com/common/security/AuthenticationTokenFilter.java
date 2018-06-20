package com.common.security;

import cn.hutool.core.util.StrUtil;
import com.common.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 校验过滤器
 *
 * @author dcp
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    /**
     * 携带Token的HTTP头
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * Token工具类
     */
    @Autowired
    private AbstractTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        String authHeader = request.getHeader(TOKEN_HEADER);

        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE,PUT");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Headers","Authorization, Origin, X-Requested-With, Content-Type, Accept");
        if(request.getMethod().equals("OPTIONS")) {
            response.setStatus(200);
            chain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith(AbstractTokenUtil.TOKEN_TYPE_BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        final String authToken = StrUtil.subSuf(authHeader, 7);
        String username = StrUtil.isNotBlank(authToken) ? jwtTokenUtil.getUsernameFromToken(authToken) : null;

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtTokenUtil.validateToken(authToken, request)) {
            UserDetails userDetails = jwtTokenUtil.getUserDetails(authToken, request);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}