package com.common.base.dao.impl;

import com.common.utils.StringExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL适配器
 *
 * @author DongJun
 */
public class SQL extends StringExpression {
    private int page;//页码，从1开始
    private int rows;//每页行数
    private boolean retrievepages;//是否获取总页数

    public boolean ifRetrievePages() {
        return retrievepages;
    }

    /**
     * 设置是否同时获取数据总页数
     *
     * 可通过重写getCountSQL方法获得新的sql语句
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
    public SQL setPage(int page) {
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
    public SQL setRows(int rows) {
        this.rows = rows;
        return this;
    }

    /**
     * 适配带可变参数的sql语句,参数用?通配符替换
     *
     * @param sql
     * @param params
     */
    public SQL(String sql, Object... params) {
        super(sql);
        setPage(-1);
        setRows(-1);
        setRetrievePages(true);
        for (Object obj : params) {
            this.r(getDftToken(), obj.toString());
        }
    }

    /**
     * 转换成sql语句
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * 获得HQL语句
     *
     * @return HQL语句
     */
    public String getSQLString() {
        return super.toString();
    }

    /**
     * 将查询语句转换成获取数据数量的SQL
     */
    public SQL getCountSQL() {
        String countsql = this.toString();
        if (countsql.toLowerCase().startsWith("select")) {
            return (SQL) new SQL(countsql).r("select", "from", " count(*) ");
        } else {
            return null;
        }
    }
}
