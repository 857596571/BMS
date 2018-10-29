package com.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.api.Paging;
import com.common.utils.http.ResponseMessage;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.modules.system.entity.SysAttachmentInfo;
import com.modules.system.service.SysAttachmentInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统附件表
 * 
 * @author xmh
 * @email 
 * @date 2018-07-29 10:41:23
 */
@Api(description = "系统附件表接口")
@Validated
@RestController
@RequestMapping("/sysAttachmentInfo")
public class SysAttachmentInfoController extends BaseController {
    
    @Autowired
    private SysAttachmentInfoService sysAttachmentInfoService;
	
    /**
     * 列表
     */
    @ApiOperation(value = "系统附件表分页列表", response = SysAttachmentInfo.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @ApiImplicitParams({
            // Filter Param
            @ApiImplicitParam(name = "query", value = "查询条件", paramType = "query", dataType = "Map"),
            // Paging Param
            @ApiImplicitParam(name = "pageNumber", value = "当前页（从0开始）", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", paramType = "query", dataType = "int", required = true)
    })
    @GetMapping("/list")
    public ResponseMessage<IPage<SysAttachmentInfo>> list(Page page, SysAttachmentInfo query) {
        IPage page1 = sysAttachmentInfoService.page(page, query);
        return Result.success(null);
    }
	
	
    /**
     * 信息
     */
    @ApiOperation(value = "系统附件表信息详情", response = SysAttachmentInfo.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Integer id", value = "主键", paramType = "query", dataType = "Integer"),
    })
    @GetMapping("/info/{id}")
    public ResponseMessage<SysAttachmentInfo> info(@PathVariable("id") String id) {
        return Result.success(sysAttachmentInfoService.getById(id));
    }
	
    /**
     * 保存
	 */
    @ApiOperation(value = "添加修改系统附件表信息", response = SysAttachmentInfo.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @PostMapping("/save")
    public ResponseMessage<SysAttachmentInfo> save(@RequestBody SysAttachmentInfo sysAttachmentInfo) {
        sysAttachmentInfoService.save(sysAttachmentInfo);
        return	Result.success();
    }
	
    /**
     * 删除
     */
    @ApiOperation(value = "删除某条系统附件表信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @GetMapping("/delete/{id}")
    public ResponseMessage delete(@PathVariable("id") String id) {
        sysAttachmentInfoService.removeById(id);
        return Result.success();
    }

}
