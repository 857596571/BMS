package com.modules.system.controller;

import com.common.api.Paging;
import com.common.utils.http.ResponseMessage;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.modules.system.entity.SysRole;
import com.modules.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色
 */
@RestController
@RequestMapping(value = "/sys/role")
public class SysRoleController extends BaseController {
    @Autowired
    private SystemService systemService;

    /**
     * 列表
     * @param role
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseMessage<List<SysRole>> list(SysRole role) {
        return Result.success(systemService.findRoleList(role));
    }

    /**
     * 验证编码是否唯一
     * @param role
     * @return
     */
    @GetMapping(value = "/isCodeExists")
    public ResponseMessage isCodeExists(SysRole role) {
        return Result.success(systemService.isRoleCodeExists(role));
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    @PostMapping(value = "/save")
    public ResponseMessage<SysRole> saveRole(@RequestBody SysRole role) {
        return Result.success(systemService.saveRole(role));
    }

    /**
     * 更新角色状态
     * @param param
     * @return
     */
    @PostMapping(value = "/updateStates")
    public ResponseMessage updateStates(@RequestBody Map<String, String> param) {
        systemService.updateRoleStates(param);
        return Result.success();
    }

    /**
     * 保存角色功能权限
     * @param role
     * @return
     */
    @PostMapping(value = "/saveMenuAuth")
    public ResponseMessage<SysRole> saveMenuAuth(@RequestBody SysRole role) {
        return Result.success(systemService.saveRoleMenuAuth(role));
    }

    /**
     * 分配角色用户
     * @param role
     * @return
     */
    @PostMapping(value = "/assignRole")
    public ResponseMessage assignRole(@RequestBody SysRole role) {
        systemService.assignRole(role);
        return Result.success();
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    public ResponseMessage delete(@PathVariable("id") String id) {
        systemService.deleteRoleById(id);
        return Result.success();
    }

    /**
     * 删除指定角色下的指定用户
     * @param role
     * @return
     */
    @PostMapping(value = "/deleteRoleUser")
    public ResponseMessage deleteRoleUser(@RequestBody SysRole role) {
        systemService.deleteRoleUser(role);
        return Result.success();
    }

}
