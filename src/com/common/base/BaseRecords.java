package com.common.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于返回数据库查询的结果，分页信息等。
 *
 * @author DongJun
 */
public class BaseRecords<T> {
    private List<T> data;// 返回数据
    private long total;// 记录总数
    private long page;// 页码
    private long rows;// 每页记录行数
    private long pages;// 总页数

    /**
     * 默认构造
     */
    public BaseRecords() {
        this.data = new ArrayList<>();
        this.total = 0;
        this.page = 1;
        this.rows = 1;
        this.pages = 0;
    }

    /**
     * 构造函数， 指定分页信息
     *
     * @param data           ，数据
     * @param total，总记录数
     * @param page，当前页号，从1开始
     * @param rows，每页记录条数
     */
    public BaseRecords(List<T> data, long total, long page, long rows) {
        this.data = data;
        this.total = total;
        this.page = page;
        this.rows = rows;
        this.pages = generatePages(this.total, this.rows);
    }

    /**
     * 构造函数， 默认数据页号为1，每页记录数为记录总数
     *
     * @param data，数据
     */
    public BaseRecords(List<T> data) {
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

    /**
     * 获得记录总数
     *
     * @return
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置记录总数
     *
     * @param total
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 获得当前页号
     *
     * @return
     */
    public long getPage() {
        return page;
    }

    /**
     * 设置当前页号
     *
     * @param page
     */
    public void setPage(long page) {
        this.page = page;
    }

    /**
     * 获得每页记录条数
     *
     * @return
     */
    public long getRows() {
        return rows;
    }

    /**
     * 设置每页记录条数
     *
     * @param rows
     */
    public void setRows(long rows) {
        this.rows = rows;
    }

    /**
     * 获得总页数
     *
     * @return
     */
    public long getPages() {
        return pages;
    }

    /**
     * 设置总页数
     *
     * @param pages
     */
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

    /**
     * 获得当前数据集
     *
     * @return
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 设置当前数据集
     *
     * @param data
     */
    public void setData(List<T> data) {
        this.data = data;
    }
}
