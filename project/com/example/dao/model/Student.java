package com.example.dao.model;

import java.sql.Timestamp;

/**
 * Student entity. @author MyEclipse Persistence Tools
 */

public class Student implements java.io.Serializable {

	// Fields

	private Integer stuid;
	private Room room;
	private String name;
	private Integer age;
	private Timestamp born;

	// Constructors

	/** default constructor */
	public Student() {
	}

	/** minimal constructor */
	public Student(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	/** full constructor */
	public Student(Room room, String name, Integer age, Timestamp born) {
		this.room = room;
		this.name = name;
		this.age = age;
		this.born = born;
	}

	// Property accessors

	public Integer getStuid() {
		return this.stuid;
	}

	public void setStuid(Integer stuid) {
		this.stuid = stuid;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Timestamp getBorn() {
		return this.born;
	}

	public void setBorn(Timestamp born) {
		this.born = born;
	}

}