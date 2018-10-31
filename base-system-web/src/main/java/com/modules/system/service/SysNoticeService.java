package com.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.modules.system.entity.SysNotice;

import java.util.List;
import java.util.Map;

/**
 * 通知公告表
 */
public interface SysNoticeService extends IService<SysNotice> {

    SysNotice queryObject(String id);

    List<SysNotice> queryList(Map<String, Object> map);

    Page<SysNotice> findPage(Paging page, SysNotice sysNotice);

    /**
     * 查询通知公告表列表
     *
     * @param page  分页信息
     * @param query 查询条件
     * @return 用户 page info
     */
    Page<SysNotice> queryPage(Paging page, Map<String, Object> query);

    void deleteById(String id);

    void delete(SysNotice sysNotice);
}
