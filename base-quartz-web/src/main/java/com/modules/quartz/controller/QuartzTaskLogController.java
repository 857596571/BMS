package com.modules.quartz.controller;

import com.common.api.Paging;
import com.common.utils.http.ResponseMessage;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.modules.quartz.entity.QuartzTaskLog;
import com.modules.quartz.service.QuartzTaskLogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 任务调度运行日志
 * 
 * @author xmh
 * @email 
 * @date 2018-07-22 00:03:21
 */
@Api(description = "任务调度运行日志接口")
@Validated
@RestController
@RequestMapping("/quartzTaskLog")
public class QuartzTaskLogController extends BaseController {
    
    @Autowired
    private QuartzTaskLogService quartzTaskLogService;
	
    /**
     * 列表
     */
    @ApiOperation(value = "任务调度运行日志分页列表", response = QuartzTaskLog.class)
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
    public ResponseMessage<PageInfo<QuartzTaskLog>> list(Paging page, QuartzTaskLog query) {
        return Result.success(quartzTaskLogService.findPage(page, query));
    }
	
	
    /**
     * 信息
     */
    @ApiOperation(value = "任务调度运行日志信息详情", response = QuartzTaskLog.class)
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
    public ResponseMessage<QuartzTaskLog> info(@PathVariable("id") String id) {
        return Result.success(quartzTaskLogService.get(id));
    }
	
    /**
     * 保存
	 */
    @ApiOperation(value = "添加修改任务调度运行日志信息", response = QuartzTaskLog.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @PostMapping("/save")
    public ResponseMessage<QuartzTaskLog> save(@RequestBody QuartzTaskLog quartzTaskLog) {
        return	Result.success(quartzTaskLogService.save(quartzTaskLog));
    }
	
    /**
     * 删除
     */
    @ApiOperation(value = "删除某条任务调度运行日志信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @GetMapping("/delete/{id}")
    public ResponseMessage delete(@PathVariable("id") String id) {
        quartzTaskLogService.deleteById(id);
        return Result.success();
    }

}
