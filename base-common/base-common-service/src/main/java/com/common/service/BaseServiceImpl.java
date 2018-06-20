package com.common.service;

import com.common.api.DataEntity;
import com.common.api.Paging;
import com.common.mapper.BaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Service基类
 */
@Transactional(readOnly = true)
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends DataEntity> implements BaseService<T> {

    /**
     * 持久层对象
     */
    @Autowired
    protected M mapper;

    /**
     * 获取单条数据
     *
     * @param id 主键
     * @return 数据实体
     */
    @Override
    public T get(String id) {
        return mapper.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    @Override
    public T get(T entity) {
        return mapper.get(entity);
    }

    /**
     * 查询数据列表
     */
    @Override
    public List<T> findList() {
        return mapper.findList();
    }

    /**
     * 查询列表数据
     *
     * @param entity 实体对象
     * @return 实体对象列表
     */
    @Override
    public List<T> findList(T entity) {
        return mapper.findList(entity);
    }

    /**
     * 查询列表数据
     *
     * @param queryMap 查询条件
     * @return 实体对象列表
     */
    @Override
    public List<T> findList(Map<String, Object> queryMap) {
        return mapper.findList(queryMap);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity 实体对象
     * @return 分页数据
     */
    @Override
    public PageInfo<T> findPage(Paging page, T entity) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<T> list = mapper.findList(entity);
        return new PageInfo<>(list);
    }

    /**
     * 查询分页数据
     *
     * @param page     分页对象
     * @param queryMap 查询条件
     * @return 分页数据
     */
    @Override
    public PageInfo<T> findPage(Paging page, Map<String, Object> queryMap) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<T> list = mapper.findList(queryMap);
        return new PageInfo<>(list);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    @Override
    @Transactional(readOnly = false)
    public T save(T entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            mapper.insert(entity);
        } else {
            entity.preUpdate();
            mapper.update(entity);
        }
        return entity;
    }

    /**
     * 删除数据
     *
     * @param entity 实体对象
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(T entity) {
        mapper.delete(entity);
    }

    /**
     * 更新数据（只更新有值字段）
     *
     * @param entity 实体对象
     */
    @Override
    @Transactional(readOnly = false)
    public void updateCondition(T entity) {
        mapper.updateCondition(entity);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteById(String id) {
        mapper.deleteById(id);
    }

}
