package com.common.base.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * Created by DJ on 2016/1/26.
 */
public abstract class CriteriaGetter implements QueryCondition {
    private int page;//页码，从1开始
    private int rows;//每页行数
    private boolean retrievepages;//是否获取总页数

    public CriteriaGetter() {
        setPage(-1);
        setRows(-1);
        setRetrievePages(true);
    }

    /**
     * 获得关联查询条件
     *
     * @return
     */
    public abstract Criteria getCriteria(Session session);

    @Override
    public boolean ifRetrievePages() {
        return retrievepages;
    }

    @Override
    public QueryCondition setRetrievePages(boolean retrievepages) {
        this.retrievepages = retrievepages;
        return this;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public QueryCondition setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public QueryCondition setRows(int rows) {
        this.rows = rows;
        return this;
    }
}
