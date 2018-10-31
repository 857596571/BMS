package com.modules.quartz.controller;

import com.common.utils.http.ResponseMessage;
import com.common.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.modules.quartz.entity.QuartzTask;
import com.modules.quartz.service.QuartzTaskService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 任务调度资源
 * 
 * @author xmh
 * @email 
 * @date 2018-07-22 00:03:20
 */
@Api(description = "任务调度资源接口")
@Validated
@RestController
@RequestMapping("/quartzTask")
public class QuartzTaskController extends BaseController {
    
    @Autowired
    private QuartzTaskService quartzTaskService;
	
    /**
     * 列表
     */
    @ApiOperation(value = "任务调度资源分页列表", response = QuartzTask.class)
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
    public ResponseMessage<PageInfo<QuartzTask>> list(Paging page, QuartzTask query) {
        return Result.success(quartzTaskService.findPage(page, query));
    }
	
	
    /**
     * 信息
     */
    @ApiOperation(value = "任务调度资源信息详情", response = QuartzTask.class)
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
    public ResponseMessage<QuartzTask> info(@PathVariable("id") String id) {
        return Result.success(quartzTaskService.get(id));
    }
	
    /**
     * 保存
	 */
    @ApiOperation(value = "添加修改任务调度资源信息", response = QuartzTask.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @PostMapping("/save")
    public ResponseMessage<QuartzTask> save(@RequestBody QuartzTask quartzTask) {
        return	Result.success(quartzTaskService.save(quartzTask));
    }
	
    /**
     * 删除
     */
    @ApiOperation(value = "删除某条任务调度资源信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @GetMapping("/delete/{id}")
    public ResponseMessage delete(@PathVariable("id") String id) {
        quartzTaskService.deleteById(id);
        return Result.success();
    }

    /**
     * 更新状态
     */
    @ApiOperation(value = "更新调度资源状态")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @PostMapping("/updateState")
    public ResponseMessage updateState(@RequestBody QuartzTask task) {
        quartzTaskService.updateState(task);
        return Result.success();
    }

    /**
     * 更新状态
     */
    @ApiOperation(value = "立即运行一次调度任务")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @PostMapping("/runOne")
    public ResponseMessage runOne(@RequestBody QuartzTask task) {
        quartzTaskService.runOne(task);
        return Result.success();
    }

}
