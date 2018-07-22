package com.modules.system.controller;

import com.common.api.Paging;
import com.common.utils.http.ResponseMessage;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.modules.system.entity.SysLog;
import com.modules.system.service.SysLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
	public ResponseMessage<PageInfo<SysLog>> list(Paging page, SysLog query){
        return Result.success(sysLogService.findPage(page, query));
	}

	/**
	 * 保存
	 */
	@PostMapping(value = "/save")
	public ResponseMessage<SysLog> save(@RequestBody SysLog sysLog){
        return Result.success(sysLogService.save(sysLog));
	}
	
	/**
	 * 删除
	 */
	@DeleteMapping(value = "/id")
	public ResponseMessage delete(@PathVariable("id") String id){
		sysLogService.deleteById(id);
        return Result.success();
	}
	
}
