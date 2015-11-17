package com.example.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.common.base.BaseRecords;
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

    public BaseRecords<Student> queryAllStudent() {
        return this.exampleDao.queryAllStu();
    }

    public BaseRecords<Student> queryAllStudent(int page, int rows) {
        return this.exampleDao.queryAllStu(page, rows);
    }

    public Student queryStu(int id) {
        return this.exampleDao.queryStu(id);
    }

    public BaseRecords<Room> queryRoom(int id) {
        return this.exampleDao.queryRoom(id);
    }
}
