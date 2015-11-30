package com.example.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;

@Repository("exampleDao")
public class ExampleDao extends BaseDaoDB {

    public ExampleDao() {
        System.out.println("ExampleDao inited!!");
    }

    public BaseRecords<Student> queryAllStu() {
        @SuppressWarnings("unchecked")
        BaseRecords<Student> student = (BaseRecords<Student>) super
                .find(super.getCriteria(Student.class));

        return student;
    }

    public BaseRecords<Student> queryAllStu(int page, int rows) {
        @SuppressWarnings("unchecked")
        BaseRecords<Student> student = (BaseRecords<Student>) super
                .find(super.getCriteria(Student.class).createCriteria("room"), page, rows);

        return student;
    }

    public Student queryStu(int id) {
        return (Student) super
                .find(super.getCriteria(Student.class)
                        .add(Restrictions.eq("id", id)).createCriteria("room"))
                .getData().get(0);
    }

    @SuppressWarnings("unchecked")
    public BaseRecords<Room> queryRoom(int id) {
        return (BaseRecords<Room>) super.find(super
                .getCriteria(Room.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createCriteria("students", JoinType.LEFT_OUTER_JOIN));
    }
}
