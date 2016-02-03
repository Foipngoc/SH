package com.example.dao.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Room entity. @author MyEclipse Persistence Tools
 */

public class Room implements java.io.Serializable {

	// Fields

	private Integer romid;
	private Integer roomnum;
	private Set students = new HashSet(0);

	// Constructors

	/** default constructor */
	public Room() {
	}

	/** minimal constructor */
	public Room(Integer roomnum) {
		this.roomnum = roomnum;
	}

	/** full constructor */
	public Room(Integer roomnum, Set students) {
		this.roomnum = roomnum;
		this.students = students;
	}

	// Property accessors

	public Integer getRomid() {
		return this.romid;
	}

	public void setRomid(Integer romid) {
		this.romid = romid;
	}

	public Integer getRoomnum() {
		return this.roomnum;
	}

	public void setRoomnum(Integer roomnum) {
		this.roomnum = roomnum;
	}

	public Set getStudents() {
		return this.students;
	}

	public void setStudents(Set students) {
		this.students = students;
	}

}