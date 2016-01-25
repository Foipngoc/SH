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
    /**
     * 适配带可变参数的hql语句,参数用?通配符替换
     *
     * @param hql
     * @param params
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
     * 将查询语句转换成获取数据数量的HQL
     */
    public HQL toCountHQL() {
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
