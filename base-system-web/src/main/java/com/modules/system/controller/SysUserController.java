package com.modules.system.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.security.util.AuthUserUtil;
import com.common.utils.exception.BusinessException;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.common.web.util.YmlConfig;
import com.modules.system.entity.SysUser;
import com.modules.system.security.model.AuthUser;
import com.modules.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * 用户
 */
@Validated
@RestController
@RequestMapping(value = "/sys/user")
public class SysUserController extends BaseController {

    /**
     * 系统用户服务
     */
    @Autowired
    private SystemService systemService;
    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 属性文件读取
     */
    @Autowired
    private YmlConfig ymlConfig;

    /**
     * 获取当前登录的用户信息
     * @return
     */
    @GetMapping(value = "/info")
    public Result getCurrentUserInfo() {
        return Result.success(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    /**
     * 重置密码
     * @param dto
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/resetPassword")
    public Result resetPassword(@RequestBody Dict dto) {
        String oldPassword = dto.getStr("oldPassword");
        String newPassword = dto.getStr("newPassword");
        String type = dto.getStr("type");
        String id = dto.getStr("id");

        SysUser user = new SysUser();
        if(StrUtil.isNotEmpty(id)) {
            user = systemService.getUserById(id);
        } else {
            AuthUser authUser = AuthUserUtil.getCurrentUser();
            user.setId(authUser.getId());
            user.setPassword(authUser.getPassword());
        }
        if(StrUtil.isNotBlank(type) && type.equals("INIT")) {
            //重置为初始密码 "123456"
            systemService.updateUserPasswordById(user.getId(),
                    passwordEncoder.encode(ymlConfig.getStr("initialPassword")),
                    null);
        }
        // 重置密码
        if ((StrUtil.isNotBlank(oldPassword) && StrUtil.isNotBlank(newPassword))) {
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return Result.error("旧密码错误");
            }
            systemService.updateUserPasswordById(user.getId(),
                    passwordEncoder.encode(newPassword),
                    new Date());
        }
        return Result.success();
    }

    /**
     * 分页列表
     * @param user
     * @param page
     * @return
     */
    @GetMapping(value = "/list")
    public Result<Page<SysUser>> list(SysUser user, Paging page) {
        return Result.success(systemService.findUserPage(page, user));
    }

    /**
     * 获取指定角色的用户列表
     * @param roleId
     * @return
     */
    @GetMapping(value = "/getUsersByRoleId")
    public Result getUsersByRoleId(String roleId) {
        return Result.success(systemService.getUsersByRoleId(roleId));

    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping(value = "/save")
    public Result saveUser(@Valid @RequestBody SysUser user) {
        if (user.getIsNewRecord() && StrUtil.isBlank(user.getPassword())) {
            //如果为新增用户并未设置密码则生成初始密码
            user.setPassword(passwordEncoder.encode(ymlConfig.getStr("initialPassword")));
        }
        return Result.success(systemService.saveUser(user));
    }

    /**
     * 判断登录账号是否唯一
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/isExists")
    public Result isCodeExists(SysUser user) {
        return Result.success(systemService.isUserExists(user));
    }

    /**
     * 更新用户状态
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/updateStates")
    public Result updateStates(@RequestBody Map<String, String> param) {
        systemService.updateUserStates(param);
        return Result.success();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        systemService.deleteUserById(id);
        return Result.success();
    }

}
