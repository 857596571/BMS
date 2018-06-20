package com.modules.system.controller;

import com.common.security.util.UserUtil;
import com.common.utils.http.ResponseMessage;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.modules.system.entity.SysMenu;
import com.modules.system.security.model.AuthUser;
import com.modules.system.service.SystemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单
 */
@RestController
@RequestMapping(value = "/sys/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private SystemService systemService;

    /**
     * 列表
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseMessage<List<SysMenu>> list() {
        AuthUser user = UserUtil.getCurrentUser();
        return Result.success(systemService.findMenuList(user.getId()));
    }

    /**
     * 保存
     * @param menu
     * @return
     */
    @PostMapping(value = "/save")
    public ResponseMessage save(@Valid @RequestBody SysMenu menu) {
        SysMenu res = systemService.saveMenu(menu);
        return Result.success(res);
    }

    /**
     * 删除菜单
     * @param menu
     * @return
     */
    @DeleteMapping(value = "/deleteById")
    public ResponseMessage delete(SysMenu menu) {
        systemService.deleteMenuById(menu);
        return Result.success();
    }

    /**
     * 更新排序
     * @param list
     * @return
     */
    @PostMapping(value = "/updateSorts")
    public ResponseMessage updateSorts(@RequestBody List<SysMenu> list){
        systemService.updateMenuSorts(list);
        return Result.success();
    }

}
