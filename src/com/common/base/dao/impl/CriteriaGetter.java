package com.common.base.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * Created by DJ on 2016/1/26.
 */
public abstract class CriteriaGetter {
    private int page;//页码，从1开始
    private int rows;//每页行数
    private boolean retrievepages;//是否获取总页数

    public CriteriaGetter() {
        setPage(-1);
        setRows(-1);
        setRetrievePages(true);
    }

    public boolean ifRetrievePages() {
        return retrievepages;
    }

    /**
     * 设置是否同时获取数据总页数
     * <p/>
     * 可通过重写getCountHQL方法获得新的sql语H句
     *
     * @param retrievepages
     */
    public void setRetrievePages(boolean retrievepages) {
        this.retrievepages = retrievepages;
    }

    public int getPage() {
        return page;
    }

    /**
     * 设置页码，从1开始
     *
     * @param page 页码
     * @return 自身
     */
    public CriteriaGetter setPage(int page) {
        this.page = page;
        return this;
    }

    public int getRows() {
        return rows;
    }

    /**
     * @param rows 每页行数
     * @return 自身
     */
    public CriteriaGetter setRows(int rows) {
        this.rows = rows;
        return this;
    }

    /**
     * 获得关联查询条件
     *
     * @return
     */
    public abstract Criteria getCriteria(Session session);
}
