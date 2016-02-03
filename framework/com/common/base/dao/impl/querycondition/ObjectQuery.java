package com.common.base.dao.impl.querycondition;

import com.common.base.dao.impl.SimpleStatment;
import com.common.base.dao.impl.SessionHandler;
import com.common.utils.KVEntry;

/**
 * Created by DJ on 2016/1/26.
 */
public class ObjectQuery implements SimpleStatment {
    private int page = -1;//页码，从1开始
    private int rows = -1;//每页行数
    private boolean retrievepages = true;//是否获取总页数
    private Class<?> cls = null;//待查询的对象
    private KVEntry<String, Object> kv = null;
    private KVEntry<String, Boolean> order = null;
    private SessionHandler sessionHandler = null;

    @Override
    public boolean ifRetrievePages() {
        return retrievepages;
    }

    @Override
    public ObjectQuery setRetrievePages(boolean retrievepages) {
        this.retrievepages = retrievepages;
        return this;
    }

    @Override
    public int getPage() {
        return this.page;
    }

    @Override
    public ObjectQuery setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public ObjectQuery setRows(int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public ObjectQuery setPaging(int page, int rows) {
        setPage(page);
        setRows(rows);
        return this;
    }

    /**
     * 查询所有对象
     */
    public ObjectQuery(Class<?> cls) {
        this.cls = cls;
    }

    /**
     * 查询满足某一条件的所有对象
     */
    public ObjectQuery(Class<?> cls, String key, Object val) {
        this.cls = cls;
        setKeyVal(key, val);
    }

    /**
     * 排序查询所有对象
     */
    public ObjectQuery(Class<?> cls, String orderby, boolean ifdesc) {
        this.cls = cls;
        setOrder(orderby, ifdesc);
    }

    /**
     * 排序查询满足某一条件的所有对象
     */
    public ObjectQuery(Class<?> cls, String key, Object val, String orderby, boolean ifdesc) {
        this.cls = cls;
        setKeyVal(key, val);
        setOrder(orderby, ifdesc);
    }

    /**
     * 查询所有对象
     */
    public ObjectQuery(Class<?> cls, int page, int rows) {
        this.cls = cls;
        setPage(page);
        setRows(rows);
    }

    /**
     * 查询满足某一条件的所有对象
     */
    public ObjectQuery(Class<?> cls, String key, Object val, int page, int rows) {
        this.cls = cls;
        setKeyVal(key, val);
        setPage(page);
        setRows(rows);
    }

    /**
     * 排序查询所有对象
     */
    public ObjectQuery(Class<?> cls, String orderby, boolean ifdesc, int page, int rows) {
        this.cls = cls;
        setOrder(orderby, ifdesc);
        setPage(page);
        setRows(rows);
    }

    /**
     * 排序查询满足某一条件的所有对象
     */
    public ObjectQuery(Class<?> cls, String key, Object val, String orderby, boolean ifdesc, int page, int rows) {
        this.cls = cls;
        setKeyVal(key, val);
        setOrder(orderby, ifdesc);
        setPage(page);
        setRows(rows);
    }

    public Class<?> getCls() {
        return cls;
    }

    public KVEntry<String, Object> getKeyVal() {
        return kv;
    }

    public ObjectQuery setKeyVal(String key, Object value) {
        this.kv = new KVEntry<>(key, value);
        return this;
    }

    public KVEntry<String, Boolean> getOrder() {
        return order;
    }

    public ObjectQuery setOrder(String orderby, boolean ifdesc) {
        this.order = new KVEntry<>(orderby, ifdesc);
        return this;
    }

    @Override
    public SessionHandler getSessionHandler() {
        return this.sessionHandler;
    }

    public ObjectQuery setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
        return this;
    }
}
