package com.common.base.dao.impl;

/**
 * Created by DJ on 2016/1/26.
 */
public interface QueryCondition {

    /**
     * 获得是否查询同时获得总页数
     *
     * @return 是/否
     */
    public boolean ifRetrievePages();

    /**
     * 设置是否同时获取数据总页数
     * 可通过重写getCountHQL方法获得新的sql语H句
     *
     * @param retrievepages true/false
     */
    public QueryCondition setRetrievePages(boolean retrievepages);

    /**
     * 获得当前页数
     *
     * @return 当前页数
     */
    public int getPage();

    /**
     * 设置页码，从1开始
     *
     * @param page 页码
     * @return 自身
     */
    public QueryCondition setPage(int page);

    /**
     * 获得当前查询每页数量
     *
     * @return 每页数量
     */
    public int getRows();

    /**
     * @param rows 每页行数
     * @return 自身
     */
    public QueryCondition setRows(int rows);

    /**
     * 设置分页参数
     *
     * @param page 页码，从1开始
     * @param rows 每页数量
     * @return 自身
     */
    public QueryCondition setPaging(int page, int rows);
}
