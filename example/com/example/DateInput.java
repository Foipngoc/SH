package com.example;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;

public class DateInput {

	@Range(min = 1, message = "输入过小")
	private int page;

	@Range(min = 1, max = 10, message = "输入在1-10之间")
	private int rows;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
