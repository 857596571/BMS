package com.common.service;

import com.common.api.DataEntity;
import com.common.api.Paging;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Service基类接口
 */
public interface BaseService<T extends DataEntity> {

    /**
     * 获取单条数据
     *
     * @param id 主键
     * @return 数据实体
     */
    T get(String id);

    /**
     * 获取单条数据
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    T get(T entity);

    /**
     * 查询数据列表
     */
    List<T> findList();

    /**
     * 查询列表数据
     *
     * @param entity 实体对象
     * @return 实体对象列表
     */
    List<T> findList(T entity);

    /**
     * 查询列表数据
     *
     * @param queryMap 查询条件
     * @return 实体对象列表
     */
    List<T> findList(Map<String, Object> queryMap);

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity 实体对象
     * @return 分页数据
     */
    PageInfo<T> findPage(Paging page, T entity);

    /**
     * 查询分页数据
     *
     * @param page     分页对象
     * @param queryMap 查询条件
     * @return 分页数据
     */
    PageInfo<T> findPage(Paging page, Map<String, Object> queryMap);

    /**
     * 保存数据（插入或更新）
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    T save(T entity);

    /**
     * 更新数据（只更新有值字段）
     *
     * @param entity 实体对象
     */
    void updateCondition(T entity);

    /**
     * 删除数据
     *
     * @param entity 实体对象
     */
    void delete(T entity);

    /**
     * 删除数据
     *
     * @param id 主键
     */
    void deleteById(String id);

}
