package com.common.base.dao.impl.querycondition;

import com.common.base.dao.impl.SessionHandler;
import com.common.base.dao.impl.SimpleStatment;

/**
 * Created by DJ on 2016/1/26.
 */
public class OBJECT implements SimpleStatment {
    private Object obj = null;
    private SessionHandler sessionHandler = null;

    @Override
    public boolean ifRetrievePages() {
        return false;
    }

    @Override
    public SimpleStatment setRetrievePages(boolean retrievepages) {
        return null;
    }

    @Override
    public int getPage() {
        return 0;
    }

    @Override
    public SimpleStatment setPage(int page) {
        return null;
    }

    @Override
    public int getRows() {
        return 0;
    }

    @Override
    public SimpleStatment setRows(int rows) {
        return null;
    }

    @Override
    public SimpleStatment setPaging(int page, int rows) {
        return null;
    }

    @Override
    public SessionHandler getSessionHandler() {
        return this.sessionHandler;
    }

    public OBJECT setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public OBJECT setObj(Object obj) {
        this.obj = obj;
        return this;
    }

    public OBJECT(Object obj) {
        setObj(obj);
    }
}
