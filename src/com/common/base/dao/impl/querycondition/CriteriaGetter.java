package com.common.base.dao.impl.querycondition;

import com.common.base.dao.impl.SimpleStatment;
import com.common.base.dao.impl.SessionHandler;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * Created by DJ on 2016/1/26.
 */
public abstract class CriteriaGetter implements SimpleStatment {
    private int page;//页码，从1开始
    private int rows;//每页行数
    private boolean retrievepages;//是否获取总页数
    private SessionHandler sessionHandler = null;

    public CriteriaGetter() {
        setPage(-1);
        setRows(-1);
        setRetrievePages(true);
    }

    public CriteriaGetter(int page, int rows) {
        setPage(page);
        setRows(rows);
        setRetrievePages(true);
    }

    public CriteriaGetter(int page, int rows, boolean retrievepages) {
        setPage(page);
        setRows(rows);
        setRetrievePages(retrievepages);
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
    public CriteriaGetter setRetrievePages(boolean retrievepages) {
        this.retrievepages = retrievepages;
        return this;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public CriteriaGetter setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public CriteriaGetter setRows(int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public CriteriaGetter setPaging(int page, int rows) {
        setPage(page);
        setRows(rows);
        return this;
    }

    @Override
    public SessionHandler getSessionHandler() {
        return this.sessionHandler;
    }

    public CriteriaGetter setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
        return this;
    }
}
