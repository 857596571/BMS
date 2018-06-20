package com.modules.system.controller;

import com.common.utils.http.ResponseMessage;
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
     * 获取制定编码下的所有子字典
     * @param code 编码
     * @return
     */
	@GetMapping(value = "/getListByParentCode")
	public ResponseMessage<List<SysDict>> listByCode(String code) {
		return Result.success(systemService.getListByParentCode(code));
	}

    /**
     * 列表
     * @param sysDict
     * @return
     */
	@GetMapping(value = "/list")
	public ResponseMessage<List<SysDict>> list(SysDict sysDict){
        return Result.success(systemService.findDictList(sysDict));
	}

    /**
     * 判断编码是否唯一
     * @param sysDict
     * @return
     */
	@PostMapping(value = "/isCodeExists")
	public ResponseMessage isCodeExists(@RequestBody SysDict sysDict){
        return Result.success(systemService.isDictCodeExists(sysDict));
	}

    /**
     * 保存字典
     * @param sysDict
     * @return
     */
	@PostMapping(value = "/save")
	public ResponseMessage save(@RequestBody SysDict sysDict){
        return Result.success(systemService.saveDict(sysDict));
	}

    /**
     * 更新字典排序
     * @param list
     * @return
     */
	@PostMapping(value = "/updateSorts")
	public ResponseMessage updateSorts(@RequestBody List<SysDict> list){
		systemService.updateDictSorts(list);
        return Result.success();
	}

    /**
     * 更新字典状态
     * @param param
     * @return
     */
	@PostMapping(value = "/updateStates")
	public ResponseMessage updateStates(@RequestBody Map<String, String> param){
		systemService.updateDictStates(param);
        return Result.success();
	}

	/**
	 * 删除字典
	 * @param sysDict
	 * @return
	 */
	@DeleteMapping(value = "/deleteById")
	public ResponseMessage<SysDict> delete(SysDict sysDict){
		systemService.deleteDictById(sysDict);
		return Result.success();
	}

}
