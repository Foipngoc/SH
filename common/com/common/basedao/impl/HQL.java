package com.common.basedao.impl;

import java.util.ArrayList;
import java.util.List;

import com.common.basedao.BaseExpression;

/**
 * HQL语句适配器
 * 
 * @author DongJun
 * 
 */
public class HQL implements BaseExpression {
	private String hql = null;
	private List<Object> mparams = null;

	/**
	 * 适配带可变参数的hql语句,参数用?通配符替换
	 * 
	 * @param hql
	 * @param params
	 */
	public HQL(String hql, Object... params) {
		this.hql = hql;
		this.mparams = new ArrayList<>();
		for (int i = 0; i < params.length; i++) {
			mparams.add(params[i]);
		}
	}

	/**
	 * 适配带可变参数的hql语句,参数用?通配符替换
	 * 
	 * @param hql
	 * @param params
	 */
	public HQL(String hql, List<Object> params) {
		this.hql = hql;
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
		if (hql != null) {
			ret = hql;
			if (mparams != null) {
				// 替换通配符
				for (int i = 0; i < mparams.size(); i++) {
					Object val = mparams.get(i);
					if (val != null)
						ret = ret.replaceFirst("\\?", val.toString());
				}
			}
		}
		return ret;
	}

	/**
	 * 将查询语句转换成获取数据数量的HQL
	 */
	public HQL toCountHQL() {
		String counthql = new String(this.hql);
		if (counthql.toLowerCase().startsWith("select")) {
			int idx = counthql.toLowerCase().indexOf("from");
			return new HQL("select count(*) " + counthql.substring(idx),
					this.mparams);
		} else if (counthql.toLowerCase().startsWith("from")) {
			return new HQL("select count(*) " + counthql, this.mparams);
		} else {
			return null;
		}
	}
}
