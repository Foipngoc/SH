package com.example.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.BaseDao;
import com.example.dao.model.Room;
import com.example.dao.model.Student;

/**
 * 实体对象接口必须继承自BaseDao接口
 * Created by Will on 2016/9/5 13:58.
 */
public interface IExampleDao extends BaseDao{
    BaseRecords<Student> queryAllStu();

    BaseRecords<Student> queryAllStu(int page, int rows);

    Student queryStu(final int id);

    BaseRecords<Room> queryRoom(int id);

    Student queryStuByName(String name);
}
