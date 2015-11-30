package com.common.base.dao.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL适配器
 * 
 * @author DongJun
 * 
 */
public class SQL {
	private String sql = null;
	private List<Object> mparams = null;

	/**
	 * 适配带可变参数的sql语句,参数用?通配符替换
	 * 
	 * @param sql
	 * @param params
	 */
	public SQL(String sql, Object... params) {
		this.sql = sql;
		this.mparams = new ArrayList<>();
		for (int i = 0; i < params.length; i++) {
			mparams.add(params[i]);
		}
	}

	/**
	 * 适配带可变参数的sql语句,参数用?通配符替换
	 * 
	 * @param sql
	 * @param params
	 */
	public SQL(String sql, List<Object> params) {
		this.sql = sql;
		this.mparams = new ArrayList<>();
		for (int i = 0; i < params.size(); i++) {
			mparams.add(params.get(i));
		}
	}

	/**
	 * 转换成sql语句
	 */
	@Override
	public String toString() {
		String ret = "";
		if (sql != null) {
			ret = sql;
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
	 * 将查询语句转换成获取数据数量的SQL
	 */
	public SQL toCountSQL() {
		String countsql = new String(this.sql);
		if (countsql.toLowerCase().startsWith("select")) {
			int idx = countsql.toLowerCase().indexOf("from");
			return new SQL("select count(*) " + countsql.substring(idx),
					this.mparams);
		} else {
			return null;
		}
	}
}
