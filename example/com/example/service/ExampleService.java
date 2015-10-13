package com.example.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.common.base.BaseQueryRecords;
import com.example.dao.ExampleDao;
import com.example.dao.Room;
import com.example.dao.Student;

@Service("exampleService")
@Transactional
public class ExampleService {
	@Resource(name = "exampleDao")
	private ExampleDao exampleDao;
	
	public ExampleService() {
		System.out.println("ExampleService inited!");
	}

	public BaseQueryRecords<Student> queryAllStudent() {
		return this.exampleDao.queryAllStu();
	}

	public Student queryStu(int id) {
		return this.exampleDao.queryStu(id);
	}

	public BaseQueryRecords<Room> queryRoom(int id) {
		return this.exampleDao.queryRoom(id);
	}
}
