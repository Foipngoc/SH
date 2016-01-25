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
     * 将查询语句转换成获取数据数量的SQL
     */
    public SQL toCountSQL() {
        String countsql = this.toString();
        if (countsql.toLowerCase().startsWith("select")) {
            return (SQL) new SQL(countsql).r("select", "from", " count(*) ");
        } else {
            return null;
        }
    }
}
