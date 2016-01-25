package com.common.base.dao;

import com.common.base.BaseRecords;

/**
 * BaseDao定义一些常用的接口
 *
 * @author DongJun
 */
public interface BaseDao {
    /**
     * 保存对象
     */
    public void save(Object o);

    /**
     * 删除对象
     */
    public void delete(Object o);

    /**
     * 更新对象
     */
    public void update(Object o);

    /**
     * 保存或更新对象
     */
    public void saveOrUpdate(Object o);

    /**
     * 分页查找所有对象
     */
    public BaseRecords<?> find(Class<?> cls);

    /**
     * 分页查找所有对象
     */
    public BaseRecords<?> find(Class<?> cls, long page, long rows);

    /**
     * 分页查找满足某一条件的所有对象
     */
    public BaseRecords<?> find(Class<?> cls, String key, Object value);

    /**
     * 分页查找满足某一条件的所有对象
     */
    public BaseRecords<?> find(Class<?> cls, String key, Object value, long page, long rows);

    /**
     * 分页排序查找所有对象
     */
    public BaseRecords<?> findOrderBy(Class<?> cls, String orderby, boolean ifdesc);

    /**
     * 分页排序查找所有对象
     */
    public BaseRecords<?> findOrderBy(Class<?> cls, String orderby, boolean ifdesc, long page, long rows);

    /**
     * 分页排序查找满足某一条件的所有对象
     */
    public BaseRecords<?> findOrderBy(Class<?> cls, String key, Object value, String orderby, boolean ifdesc);

    /**
     * 分页排序查找满足某一条件的所有对象
     */
    public BaseRecords<?> findOrderBy(Class<?> cls, String key, Object value, String orderby, boolean ifdesc, long page, long rows);

    /**
     * 查找唯一对象，如果对象不存在，返回NULL
     */
    public Object findUnique(Class<?> cls, String key, Object value);

    /**
     * 获得记录数
     */
    public long count(Class<?> cls);

    /**
     * 获得记录数
     */
    public long count(Class<?> cls, String key, Object value);
}
