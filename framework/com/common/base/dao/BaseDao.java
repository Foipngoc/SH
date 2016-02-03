package com.common.base.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.querycondition.ObjectQuery;

/**
 * BaseDao定义一些常用的接口
 * <p/>
 * find2版本只查数据，不查总页数，查询时由于不需要查找总数，查询速度更快
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
     * 查找所有对象
     */
    public BaseRecords<?> find(ObjectQuery query);

    /**
     * 查找唯一对象，如果对象不存在，返回NULL
     */
    public Object findUnique(ObjectQuery query);

    /**
     * 获得记录数
     */
    public long count(ObjectQuery query);
}
