package com.common.base.dao.impl.querycondition;

import com.common.base.dao.impl.SimpleStatment;
import com.common.base.dao.impl.SessionHandler;
import com.common.utils.StringExpression;

/**
 * HQL语句适配器
 *
 * @author DongJun
 */
public class HQL extends StringExpression implements SimpleStatment {
    private int page = -1;//页码，从1开始
    private int rows = -1;//每页行数
    private boolean retrievepages = true;//是否获取总页数
    private SessionHandler sessionHandler = null;

    public HQL(SimpleStatment simpleStatment){
        super("");
        setRetrievePages(simpleStatment.ifRetrievePages());
        setPage(simpleStatment.getPage());
        setRows(simpleStatment.getRows());
        setSessionHandler(simpleStatment.getSessionHandler());
    }

    /**
     * 适配带可变参数的hql语句,参数用?通配符替换
     *
     * @param hql    hql语句
     * @param params 参数
     */
    public HQL(String hql, Object... params) {
        super(hql);
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

    @Override
    public boolean ifRetrievePages() {
        return this.retrievepages;
    }

    @Override
    public HQL setRetrievePages(boolean retrievepages) {
        this.retrievepages = retrievepages;
        return this;
    }

    @Override
    public int getPage() {
        return this.page;
    }

    @Override
    public HQL setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public HQL setRows(int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public HQL setPaging(int page, int rows) {
        setPage(page);
        setRows(rows);
        return this;
    }

    @Override
    public SessionHandler getSessionHandler() {
        return this.sessionHandler;
    }

    @Override
    public HQL setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
        return this;
    }
}
