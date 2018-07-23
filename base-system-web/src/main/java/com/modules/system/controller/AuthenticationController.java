package com.modules.system.controller;

import cn.hutool.core.util.StrUtil;
import com.common.security.AuthenticationTokenFilter;
import com.common.utils.http.ResponseMessage;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.modules.system.entity.SysUser;
import com.modules.system.entity.resp.AuthenticationResp;
import com.modules.system.security.utils.TokenUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 鉴权
 */
@Api(description = "鉴权接口")
@Validated
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
     * @return
     */
    @ApiOperation(value = "登录鉴权", response = AuthenticationResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @ApiImplicitParams({
            // Paging Param
            @ApiImplicitParam(name = "sysUser", value = "传入JSON", paramType = "query", dataType = "SysUser", required = false),
            @ApiImplicitParam(name = "loginName", value = "登录账号", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "登录密码", paramType = "query", dataType = "String", required = true)
    })
    @PostMapping(value = "/token")
    public ResponseMessage createAuthenticationToken(HttpServletRequest request, @RequestBody SysUser sysUser) {
        AuthenticationResp resp = new AuthenticationResp();
        // Perform the securitycd
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(sysUser.getLoginName(), sysUser.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String token = jwtTokenUtil.generateToken(userDetails, request);

            // Return the token
            resp.setAccess_token(token);
            resp.setExpires_in(jwtTokenUtil.getExpiration());
            resp.setToken_type(TokenUtil.TOKEN_TYPE_BEARER);
        } catch (UsernameNotFoundException e) { //用户找不到
            logger.error("该账号不存在", e.getMessage(), e);
            return Result.error(1001, "该账号不存在");
        } catch (BadCredentialsException e) { //坏的凭据
            logger.error("登录账号或密码错误(未分配角色或角色已冻结)", e.getMessage(), e);
            return Result.error(1002, "登录账号或密码错误(未分配角色或角色已冻结)");
        } catch (AccountExpiredException e) { //账户过期
            logger.error("该账户已过期", e.getMessage(), e);
            return Result.error(1003, "该账户已过期");
        } catch (LockedException e) { //账户锁定
            logger.error("该账户已锁定", e.getMessage(), e);
            return Result.error(1004, "该账户已锁定");
        } catch (DisabledException e) { //账户不可用
            logger.error("该账户已冻结", e.getMessage(), e);
            return Result.error(1005, "该账户已冻结");
        } catch (CredentialsExpiredException e) { //证书过期
            logger.error("证书过期", e.getMessage(), e);
            return Result.error(1006, "证书过期");
        } catch (Exception e) { //证书过期
            logger.error("未知异常", e.getMessage(), e);
            return Result.error(1007, "未知异常");
        }
        return Result.success(resp);
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
        if(StrUtil.isNotBlank(tokenHeader)) {

            String token = tokenHeader.split(" ")[1];

            jwtTokenUtil.removeToken(token, request);
        }

        return Result.success();
    }

}
