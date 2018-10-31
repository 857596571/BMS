package com.modules.system.controller;

import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.modules.system.entity.SysMenu;
import com.modules.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
    public Result<List<SysMenu>> list(SysMenu menu) {
        return Result.success(systemService.findMenuList(menu));
    }

    /**
     * 保存
     * @param menu
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysMenu menu) {
        SysMenu res = systemService.saveMenu(menu);
        return Result.success(res);
    }

    /**
     * 更新菜单状态
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/updateStates")
    public Result updateStates(@RequestBody Map<String, String> param) {
        systemService.updateMenuStates(param);
        return Result.success();
    }

    /**
     * 删除菜单
     * @param sysMenu
     * @return
     */
    @GetMapping(value = "/delete")
    public Result delete(SysMenu sysMenu) {
        systemService.deleteMenu(sysMenu);
        return Result.success();
    }

    /**
     * 更新排序
     * @param list
     * @return
     */
    @PostMapping(value = "/updateSorts")
    public Result updateSorts(@RequestBody List<SysMenu> list){
        systemService.updateMenuSorts(list);
        return Result.success();
    }

}
