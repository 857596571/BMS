package com.modules.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.api.Paging;
import com.common.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.modules.system.entity.SysNotice;
import com.modules.system.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



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
	public Page<SysNotice> list(Page page, Map<String, Object> query){
        return sysNoticeService.queryPage(page, query);
	}
	
	
	/**
	 * 信息
	 */
	@GetMapping(value = "/info/{id}")
	@PreAuthorize("hasAuthority('sys:notice:info')")
	public SysNotice info(@PathVariable("id") String id){
        return sysNoticeService.queryObject(id);
	}
	
	/**
	 * 保存
	 */
	@PostMapping(value = "/save")
	@PreAuthorize("hasAuthority('sys:notice:save')")
	public SysNotice save(@RequestBody SysNotice sysNotice){
        return	sysNoticeService.save(sysNotice);
	}
	
	/**
	 * 删除
	 */
	@DeleteMapping(value = "/delete")
	@PreAuthorize("hasAuthority('sys:notice:delete')")
	public ResponseEntity delete(@PathVariable("id") String id){
		sysNoticeService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
	}
	
}
