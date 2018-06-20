package com.modules.system.controller;

import com.common.security.AuthenticationTokenFilter;
import com.common.utils.IPUtils;
import com.common.utils.http.ResponseMessage;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.modules.system.entity.SysUser;
import com.modules.system.security.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 鉴权
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController extends BaseController {
    /**
     * 权限管理
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 用户信息服务
     */
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * Token工具
     */
    @Autowired
    private TokenUtil jwtTokenUtil;

    /**
     * 获取令牌
     * @param request
     * @param response
     * @param sysUser
     * @return
     */
    @PostMapping(value = "/token")
    public ResponseMessage createAuthenticationToken(HttpServletRequest request, HttpServletResponse response, @RequestBody SysUser sysUser) {
        Map<String, Object> tokenMap = new HashMap<>();
        ResponseMessage responseMessage = new ResponseMessage();

        // Perform the securitycd
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(sysUser.getLoginName(), sysUser.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String token = jwtTokenUtil.generateToken(userDetails, request);

            // Return the token

            tokenMap.put("access_token", token);
            tokenMap.put("expires_in", jwtTokenUtil.getExpiration());
            tokenMap.put("token_type", TokenUtil.TOKEN_TYPE_BEARER);
        } catch (UsernameNotFoundException e) { //用户找不到
            return Result.error(1001, "该账号不存在");
        } catch (BadCredentialsException e) { //坏的凭据
            return Result.error(1002, "登录账号或密码错误(未分配角色或角色已冻结)");
        } catch (AccountExpiredException e) { //账户过期
            return Result.error(1003, "该账户已过期");
        } catch (LockedException e) { //账户锁定
            return Result.error(1004, "该账户已锁定");
        } catch (DisabledException e) { //账户不可用
            return Result.error(1005, "该账户已冻结");
        } catch (CredentialsExpiredException e) { //证书过期
            tokenMap.put("code", 1006);
            return Result.error(1006, "证书过期");
        }
        return Result.success(tokenMap);
    }

    /**
     * 刷新令牌
     * @param request
     * @return
     */
    @GetMapping(value = "/refresh")
    public ResponseMessage refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader(AuthenticationTokenFilter.TOKEN_HEADER);
        String token = tokenHeader.split(" ")[1];

        String username = jwtTokenUtil.getUsernameFromToken(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String refreshedToken = jwtTokenUtil.generateToken(userDetails, request);

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", refreshedToken);
        tokenMap.put("expires_in", jwtTokenUtil.getExpiration());
        tokenMap.put("token_type", TokenUtil.TOKEN_TYPE_BEARER);
        return Result.success(tokenMap);
    }

    /**
     * 删除令牌
     * @param request
     * @return
     */
    @DeleteMapping(value = "/token")
    public ResponseMessage deleteAuthenticationToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader(AuthenticationTokenFilter.TOKEN_HEADER);
        String token = tokenHeader.split(" ")[1];

        jwtTokenUtil.removeToken(token, request);

        return Result.success();
    }

}
