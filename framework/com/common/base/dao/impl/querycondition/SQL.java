package com.common.base.dao.impl.querycondition;

import com.common.base.dao.impl.SimpleStatment;
import com.common.base.dao.impl.SessionHandler;
import com.common.utils.StringExpression;

import java.util.HashMap;
import java.util.Map;

/**
 * SQL适配器
 *
 * @author DongJun
 */
public class SQL extends StringExpression implements SimpleStatment {
    private int page = -1;//页码，从1开始
    private int rows = -1;//每页行数
    private boolean retrievepages = true;//是否获取总页数
    //change by Will at 2016年8月24日14:58:47
    private Map<String,Object> paramMap = null;
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
     * @param id sql语句id
     * @param params 参数
     */
    //change by Will at 2016年8月24日14:58:47
    public SQL(String id, Object... params) {
        super(id);
//        for (Object obj : params) {
//            this.r(getDftToken(), obj.toString());
//        }
        int i = 1;
        paramMap = new HashMap<>();
        for (Object obj: params) {
            String key = "a" + (i++);
            this.r(getDftToken(), ":"+key);
            paramMap.put(key,obj);
        }
    }


    //change by Will at 2016年8月24日14:58:47
    /**
     * 带参数sql
     * @param id 例：select * from Student where stuid =:stuid
     * @param paramMap 例子{stuid=id,value=1}
     */
    public SQL(String id,Map<String, Object> paramMap) {
        super(id);
        this.paramMap = paramMap;
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
            return (SQL) new SQL(countsql,paramMap).r("select", "from", " count(*) ");
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

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
