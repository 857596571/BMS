package com.common.mapper;

import java.util.List;
import java.util.Map;

/**
 * DAO基类
 */
public interface BaseMapper<T> {

    /**
     * 获取单条数据
     *
     * @param id 主键
     * @return T t
     */
    T get(String id);

    /**
     * 获取单条数据
     *
     * @param entity T
     * @return T t
     */
    T get(T entity);

    /**
     * 查询数据列表
     */
    List<T> findList();

    /**
     * 查询数据列表
     *
     * @param entity T
     * @return List<T> list
     */
    List<T> findList(T entity);

    /**
     * 查询数据列表
     *
     * @param queryMap 查询条件
     * @return List<T> list
     */
    List<T> findList(Map<String, Object> queryMap);

    /**
     * 插入数据
     *
     * @param entity T
     * @return int int
     */
    int insert(T entity);

    /**
     * 更新数据
     *
     * @param entity T
     * @return int int
     */
    int update(T entity);

    /**
     * 更新数据（只更新有值字段）
     *
     * @param entity 实体对象
     */
    int updateCondition(T entity);

    /**
     * 删除数据
     *
     * @param entity T
     * @return int int
     */
    int delete(T entity);

    /**
     * 删除数据
     *
     * @param id entity id
     * @return int int
     */
    int deleteById(String id);

}