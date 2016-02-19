package com.common.base.dao.impl.querycondition;

import com.common.base.dao.impl.SimpleStatment;
import com.common.base.dao.impl.SessionHandler;
import com.common.utils.StringExpression;

/**
 * SQL适配器
 *
 * @author DongJun
 */
public class SQL extends StringExpression implements SimpleStatment {
    private int page = -1;//页码，从1开始
    private int rows = -1;//每页行数
    private boolean retrievepages = true;//是否获取总页数
    private SessionHandler sessionHandler = null;

    public SQL(SimpleStatment simpleStatment){
        super("");
        setRetrievePages(simpleStatment.ifRetrievePages());
        setPage(simpleStatment.getPage());
        setRows(simpleStatment.getRows());
        setSessionHandler(simpleStatment.getSessionHandler());
    }

    /**
     * 适配带可变参数的sql语句,参数用?通配符替换
     *
     * @param sql
     * @param params
     */
    public SQL(String sql, Object... params) {
        super(sql);
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
    public SQL setRetrievePages(boolean retrievepages) {
        this.retrievepages = retrievepages;
        return this;
    }

    @Override
    public int getPage() {
        return this.page;
    }

    @Override
    public SQL setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public SQL setRows(int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public SQL setPaging(int page, int rows) {
        setPage(page);
        setRows(rows);
        return this;
    }

    @Override
    public SessionHandler getSessionHandler() {
        return this.sessionHandler;
    }

    @Override
    public SQL setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
        return this;
    }
}
