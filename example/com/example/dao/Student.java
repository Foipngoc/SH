package com.example.dao;
// default package

import java.sql.Timestamp;

/**
 * Student entity. @author MyEclipse Persistence Tools
 */

public class Student implements java.io.Serializable {

	// Fields

	private Integer id;
	private Room room;
	private String name;
	private Integer age;
	private Timestamp born;

	// Constructors

	/** default constructor */
	public Student() {
	}

	/** full constructor */
	public Student(Room room, String name, Integer age, Timestamp born) {
		this.room = room;
		this.name = name;
		this.age = age;
		this.born = born;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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