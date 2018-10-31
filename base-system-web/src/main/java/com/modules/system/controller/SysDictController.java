package com.modules.system.controller;

import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.modules.system.entity.SysDict;
import com.modules.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 字典
 */
@RestController
@RequestMapping(value = "/sys/dict")
public class SysDictController extends BaseController {
    @Autowired
    private SystemService systemService;

    /**
     * 获取指定编码下的所有子字典
     *
     * @param code 编码
     * @return
     */
    @GetMapping(value = "/getListByParentCode")
    public Result<List<SysDict>> listByCode(String code) {
        return Result.success(systemService.getListByParentCode(code));
    }

    /**
     * 列表
     *
     * @param sysDict
     * @return
     */
    @GetMapping(value = "/list")
    public Result<List<SysDict>> list(SysDict sysDict) {
        return Result.success(systemService.findDictList(sysDict));
    }

    /**
     * 判断编码是否唯一
     *
     * @param sysDict
     * @return
     */
    @GetMapping(value = "/isCodeExists")
    public Result isCodeExists(SysDict sysDict) {
        return Result.success(systemService.isDictCodeExists(sysDict));
    }

    /**
     * 保存字典
     *
     * @param sysDict
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(@RequestBody SysDict sysDict) {
        return Result.success(systemService.saveDict(sysDict));
    }

    /**
     * 更新字典排序
     *
     * @param list
     * @return
     */
    @PostMapping(value = "/updateSorts")
    public Result updateSorts(@RequestBody List<SysDict> list) {
        systemService.updateDictSorts(list);
        return Result.success();
    }

    /**
     * 更新字典状态
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/updateStates")
    public Result updateStates(@RequestBody Map<String, String> param) {
        systemService.updateDictStates(param);
        return Result.success();
    }

    /**
     * 删除字典
     *
     * @param sysDict
     * @return
     */
    @GetMapping(value = "/delete")
    public Result delete(SysDict sysDict) {
        systemService.deleteDict(sysDict);
        return Result.success();
    }

}
