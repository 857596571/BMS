package com.modules.system.controller;

import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.modules.system.entity.SysOrg;
import com.modules.system.service.SystemService;
import com.modules.system.utils.DataFilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构
 */
@RestController
@RequestMapping(value = "/sys/org")
public class SysOrgController extends BaseController {
	@Autowired
	private SystemService systemService;

    /**
     * 列表
     * @param org
     * @return
     */
	@GetMapping(value = "/list")
	public Result<List<SysOrg>> list(SysOrg org){
		DataFilterUtils.dataScopeFilter(org, "a", "su");
        return Result.success(systemService.findOrgList(org));
	}

	/**
	 * 验证机构编码是否唯一
	 * @param org
	 * @return
	 */
	@GetMapping(value = "/isCodeExists")
	public Result<Boolean> isCodeExists(SysOrg org){
		return Result.success(systemService.isOrgCodeExists(org));
	}

	/**
	 * 保存机构
	 * @param org
	 * @return
	 */
	@PostMapping(value = "/save")
	public Result<SysOrg> save(@RequestBody SysOrg org){
		return Result.success(systemService.saveOrg(org));
	}

	/**
	 * 更新机构排序
	 * @param list
	 * @return
	 */
	@PostMapping(value = "/updateSorts")
	public Result<SysOrg> updateSorts(@RequestBody List<SysOrg> list){
		systemService.updateOrgSorts(list);
		return Result.success();
	}

	/**
	 * 删除机构
	 * @param sysOrg
	 * @return
	 */
	@GetMapping(value = "/delete")
	public Result delete(SysOrg sysOrg) {
		systemService.deleteOrg(sysOrg);
		return Result.success();
	}

}
