package com.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * HQL语句适配器
 *
 * @author DongJun
 */
public class StringExpression {
    private String token = "\\?";
    private String str = null;
    private List<Object> mparams = null;

    /**
     * 适配带可变参数的hql语句,参数用?通配符替换
     *
     * @param str
     * @param params
     */
    public StringExpression(String str, Object... params) {
        this.str = str;
        this.mparams = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            mparams.add(params[i]);
        }
    }

    /**
     * 适配带可变参数的hql语句,参数用?通配符替换
     *
     * @param str
     * @param params
     */
    public StringExpression(String str, List<Object> params) {
        this.str = str;
        this.mparams = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            mparams.add(params.get(i));
        }
    }


    /**
     * 适配带可变参数的hql语句,参数用?通配符替换
     *
     * @param token  点位符
     * @param str
     * @param params
     */
    public StringExpression(String token, String str, Object... params) {
        this.token = token;
        this.str = str;
        this.mparams = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            mparams.add(params[i]);
        }
    }

    /**
     * 适配带可变参数的hql语句,参数用?通配符替换
     *
     * @param token  点位符
     * @param str
     * @param params
     */
    public StringExpression(String token, String str, List<Object> params) {
        this.token = token;
        this.str = str;
        this.mparams = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            mparams.add(params.get(i));
        }
    }

    /**
     * 转换成hql语句
     */
    @Override
    public String toString() {
        String ret = "";
        if (str != null) {
            ret = str;
            if (mparams != null) {
                // 替换通配符
                for (int i = 0; i < mparams.size(); i++) {
                    Object val = mparams.get(i);
                    if (val != null)
                        ret = ret.replaceFirst(token, val.toString());
                }
            }
        }
        return ret;
    }
}
