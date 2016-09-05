package com.example.dao;

import com.common.base.BaseRecords;
import com.example.dao.model.Room;
import com.example.dao.model.Student;

/**
 * Created by Will on 2016/9/5 13:58.
 */
public interface IExampleDao {
    BaseRecords<Student> queryAllStu();

    BaseRecords<Student> queryAllStu(int page, int rows);

    Student queryStu(final int id);

    BaseRecords<Room> queryRoom(int id);

    Student queryStuByName(String name);
}
