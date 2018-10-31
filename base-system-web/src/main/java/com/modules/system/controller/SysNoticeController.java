package com.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.modules.system.entity.SysNotice;
import com.modules.system.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



/**
 * 通知公告表
 * 
 * @author henrycao
 * @email 631079326@qq.com
 * @date 2017-06-12 23:15:41
 */
@RestController
@RequestMapping(value = "/sys/notice")
public class SysNoticeController extends BaseController {
	@Autowired
	private SysNoticeService sysNoticeService;
	
	/**
	 * 列表
	 */
	@GetMapping("value = /list")
	@PreAuthorize("hasAuthority('sys:notice:list')")
	public Result<IPage<SysNotice>> list(Page page, SysNotice query){
        return Result.success(sysNoticeService.page(page, query));
	}
	
	
	/**
	 * 信息
	 */
	@GetMapping(value = "/info/{id}")
	@PreAuthorize("hasAuthority('sys:notice:info')")
	public Result info(@PathVariable("id") String id){
        return Result.success(sysNoticeService.getById(id));
	}
	
	/**
	 * 保存
	 */
	@PostMapping(value = "/save")
	@PreAuthorize("hasAuthority('sys:notice:save')")
	public Result save(@RequestBody SysNotice sysNotice){
		sysNoticeService.save(sysNotice);
        return Result.success();
	}
	
	/**
	 * 删除
	 */
	@DeleteMapping(value = "/delete")
	@PreAuthorize("hasAuthority('sys:notice:delete')")
	public Result delete(@PathVariable("id") String id){
		sysNoticeService.deleteById(id);
        return Result.success();
	}
	
}
