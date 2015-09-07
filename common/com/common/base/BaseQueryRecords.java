package com.common.base;

import java.util.List;

/**
 * 用于返回数据库查询的结果，分页信息等。
 * 
 * @author DongJun
 * 
 */
public class BaseQueryRecords<T> {
	private List<T> data;// 返回数据
	private long total;// 记录总数
	private long page;// 页码
	private long rows;// 每页记录行数
	private long pages;// 总页数

	/**
	 * 默认构造
	 */
	public BaseQueryRecords() {

	}

	/**
	 * 构造函数， 指定分页信息
	 * 
	 * @param data
	 * @param total
	 * @param page
	 * @param rows
	 */
	public BaseQueryRecords(List<T> data, long total, long page, long rows) {
		this.data = data;
		this.total = total;
		this.page = page;
		this.rows = rows;
		this.pages = generatePages(this.total, this.rows);
	}

	/**
	 * 构造函数， 默认数据页数为1
	 * 
	 * @param data
	 */
	public BaseQueryRecords(List<T> data) {
		this.data = data;
		this.total = data.size();
		this.page = 1;
		// 解決 当数据为空的时候， 出现除0错误
		if (this.total == 0)
			this.rows = 1;
		else
			this.rows = this.total;
		this.pages = generatePages(this.total, this.rows);
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getRows() {
		return rows;
	}

	public void setRows(long rows) {
		this.rows = rows;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

	/**
	 * 通过总数与每页行数获得总页数
	 * 
	 * @param total
	 * @param rows
	 * @return
	 */
	private long generatePages(long total, long rows) {
		long pages = total / rows;
		if (total % rows != 0) {
			pages++;
		}
		return pages;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
