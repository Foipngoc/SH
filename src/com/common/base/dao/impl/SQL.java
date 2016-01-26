package com.common.base.dao.impl;

import com.common.utils.StringExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL适配器
 *
 * @author DongJun
 */
public class SQL extends StringExpression implements QueryCondition {
    private int page;//页码，从1开始
    private int rows;//每页行数
    private boolean retrievepages;//是否获取总页数


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
        return this.page;
    }

    @Override
    public QueryCondition setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public QueryCondition setRows(int rows) {
        this.rows = rows;
        return this;
    }
}
