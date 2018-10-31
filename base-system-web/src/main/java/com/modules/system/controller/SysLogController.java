package com.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.modules.system.entity.SysLog;
import com.modules.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统日志
 */
@RestController
@RequestMapping(value = "/sys/log")
public class SysLogController extends BaseController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@GetMapping(value = "/list")
	public Result<IPage<SysLog>> list(Page page, SysLog query){
        return Result.success(sysLogService.page(page, query));
	}

	/**
	 * 保存
	 */
	@PostMapping(value = "/save")
	public Result save(@RequestBody SysLog sysLog){
		sysLogService.save(sysLog);
        return Result.success();
	}
	
	/**
	 * 删除
	 */
	@DeleteMapping(value = "/id")
	public Result delete(@PathVariable("id") String id){
		sysLogService.removeById(id);
        return Result.success();
	}
	
}
