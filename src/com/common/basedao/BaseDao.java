package com.common.basedao;

import com.common.base.BaseQueryRecords;

/**
 * BaseDao定义一些常用的接口，此类接口不应与数据持久层是何种而不同 约定调用者也知道查询时返回的数据类型是多少
 * 
 * @author DongJun
 * 
 * @param <?>
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
	public BaseQueryRecords<?> find(Class<?> cls);

	/**
	 * 查找所有对象，并排序
	 */
	public BaseQueryRecords<?> findOrderBy(Class<?> cls, String orderby,
										   boolean ifdesc);

	/**
	 * 查找所有对象 带分页
	 */
	public BaseQueryRecords<?> find(Class<?> cls, long page, long rows);

	/**
	 * 查找所有对象 带分页, 并排序
	 */
	public BaseQueryRecords<?> findOrderBy(Class<?> cls, String orderby,
										   boolean ifdesc, long page, long rows);

	/**
	 * 查找满足某一条件的所有对象
	 */
	public BaseQueryRecords<?> find(Class<?> cls, String key, Object value);

	/**
	 * 查找满足某一条件的所有对象, 并排序
	 */
	public BaseQueryRecords<?> findOrderBy(Class<?> cls, String key,
										   Object value, String orderby, boolean ifdesc);

	/**
	 * 查找满足某一条件的所有对象 带分布
	 */
	public BaseQueryRecords<?> find(Class<?> cls, String key, Object value,
									long page, long rows);

	/**
	 * 查找满足某一条件的所有对象 带分布,并排序
	 */
	public BaseQueryRecords<?> findOrderBy(Class<?> cls, String key,
										   Object value, String orderby, boolean ifdesc, long page, long rows);

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
