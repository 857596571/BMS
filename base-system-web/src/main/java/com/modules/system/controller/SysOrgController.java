package com.modules.system.controller;

import com.common.api.Paging;
import com.common.utils.http.ResponseMessage;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.modules.system.entity.SysOrg;
import com.modules.system.service.SystemService;
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
	public ResponseMessage<List<SysOrg>> list(SysOrg org){
        return Result.success(systemService.findOrgList(org));
	}

	/**
	 * 验证机构编码是否唯一
	 * @param org
	 * @return
	 */
	@GetMapping(value = "/isCodeExists")
	public ResponseMessage<Boolean> isCodeExists(SysOrg org){
		return Result.success(systemService.isOrgCodeExists(org));
	}

	/**
	 * 保存机构
	 * @param org
	 * @return
	 */
	@PostMapping(value = "/save")
	public ResponseMessage<SysOrg> save(@RequestBody SysOrg org){
		return Result.success(systemService.saveOrg(org));
	}

	/**
	 * 更新机构排序
	 * @param list
	 * @return
	 */
	@PostMapping(value = "/updateSorts")
	public ResponseMessage<SysOrg> updateSorts(@RequestBody List<SysOrg> list){
		systemService.updateOrgSorts(list);
		return Result.success();
	}

	/**
	 * 删除机构
	 * @param sysOrg
	 * @return
	 */
	@GetMapping(value = "/delete")
	public ResponseMessage delete(SysOrg sysOrg) {
		systemService.deleteOrg(sysOrg);
		return Result.success();
	}

}
