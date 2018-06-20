package com.modules.system.service;

import com.common.service.BaseService;
import com.modules.system.entity.SysNotice;
import com.github.pagehelper.PageInfo;
import  com.common.api.Paging;

import java.util.List;
import java.util.Map;

/**
 * 通知公告表
 */
public interface SysNoticeService extends BaseService<SysNotice> {

    SysNotice queryObject(String id);

    List<SysNotice> queryList(Map<String, Object> map);

    PageInfo<SysNotice> findPage(Paging page, SysNotice sysNotice);

    /**
     * 查询通知公告表列表
     *
     * @param page  分页信息
     * @param query 查询条件
     * @return 用户 page info
     */
    PageInfo<SysNotice> queryPage(Paging page, Map<String, Object> query);

    SysNotice save(SysNotice sysNotice);

    void deleteById(String id);

    void delete(SysNotice sysNotice);
}
