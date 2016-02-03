package com.example.controller.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONModel {
	private String string;
	private Integer integer;
	private Double double1;
	private Float float1;
	private Long long1;
	private int int1;
	private double doublt1;

	private List<String> list;
	private Map<String, Object> map;
	private Set<Integer> set;

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public JSONModel() {
		super();
	}

	public Integer getInteger() {
		return integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public Double getDouble1() {
		return double1;
	}

	public void setDouble1(Double double1) {
		this.double1 = double1;
	}

	public Float getFloat1() {
		return float1;
	}

	public void setFloat1(Float float1) {
		this.float1 = float1;
	}

	public Long getLong1() {
		return long1;
	}

	public void setLong1(Long long1) {
		this.long1 = long1;
	}

	public int getInt1() {
		return int1;
	}

	public void setInt1(int int1) {
		this.int1 = int1;
	}

	public double getDoublt1() {
		return doublt1;
	}

	public void setDoublt1(double doublt1) {
		this.doublt1 = doublt1;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Set<Integer> getSet() {
		return set;
	}

	public void setSet(Set<Integer> set) {
		this.set = set;
	}
}
