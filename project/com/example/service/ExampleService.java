package com.example.service;

import com.common.base.BaseRecords;
import com.common.base.service.BaseService;
import com.example.dao.ExampleDao;
import com.example.dao.model.Room;
import com.example.dao.model.Student;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("exampleService")
public class ExampleService extends BaseService{
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


    @Test
    public void dotest() {
        BaseRecords<Student> stus = this.queryAllStudent();
        System.out.println("Size:" + stus.getData().size());
    }

    public Student queryStu(int id) {
        return this.exampleDao.queryStu(id);
    }

    public Student queryStuByName(String name) {
        return this.exampleDao.queryStuByName(name);
    }

    public BaseRecords<Room> queryRoom(int id) {
        return this.exampleDao.queryRoom(id);
    }
}
