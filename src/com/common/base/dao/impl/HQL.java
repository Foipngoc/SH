package com.common.base.dao.impl;

import com.common.utils.StringExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * HQL语句适配器
 *
 * @author DongJun
 */
public class HQL extends StringExpression {
    private int page;//页码，从1开始
    private int rows;//每页行数
    private boolean retrievepages;//是否获取总页数

    public boolean ifRetrievePages() {
        return retrievepages;
    }

    /**
     * 设置是否同时获取数据总页数
     *
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
    public HQL setPage(int page) {
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
    public HQL setRows(int rows) {
        this.rows = rows;
        return this;
    }

    /**
     * 适配带可变参数的hql语句,参数用?通配符替换
     *
     * @param hql    hql语句
     * @param params 参数
     */
    public HQL(String hql, Object... params) {
        super(hql);
        setPage(-1);
        setRows(-1);
        setRetrievePages(true);
        for (Object obj : params) {
            this.r(getDftToken(), obj.toString());
        }
    }

    /**
     * 转换成hql语句
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
    public String getHQLString() {
        return super.toString();
    }

    /**
     * 将查询语句转换成获取数据数量的HQL
     * <p/>
     * 重写该方法以获得查询数量的HQL语句
     */
    public HQL getCountHQL() {
        String counthql = this.toString();
        if (counthql.toLowerCase().startsWith("select")) {
            return (HQL) new HQL(counthql).r("select", "from", " count(*) ");
        } else if (counthql.toLowerCase().startsWith("from")) {
            return new HQL("select count(*) " + counthql);
        } else {
            return null;
        }
    }
}
