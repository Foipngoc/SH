package com.example.dao;

import com.common.base.dao.impl.querycondition.CriteriaGetter;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
                .find(new CriteriaGetter() {
                    @Override
                    public Criteria getCriteria(Session session) {
                        return session.createCriteria(Student.class);
                    }
                });

        return student;
    }

    public BaseRecords<Student> queryAllStu(int page, int rows) {
        @SuppressWarnings("unchecked")
        BaseRecords<Student> student = (BaseRecords<Student>) super
                .find(new CriteriaGetter(page, rows) {
                    @Override
                    public Criteria getCriteria(Session session) {
                        return session.createCriteria(Student.class).createCriteria("room");
                    }
                });

        return student;
    }

    public Student queryStu(final int id) {
        return (Student) super
                .findUnique(new CriteriaGetter() {
                    @Override
                    public Criteria getCriteria(Session session) {
                        return session.createCriteria(Student.class).add(Restrictions.eq("id", id)).createCriteria("room");
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public BaseRecords<Room> queryRoom(int id) {
        return (BaseRecords<Room>) super.find(
                new CriteriaGetter() {
                    @Override
                    public Criteria getCriteria(Session session) {
                        return session.createCriteria(Room.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                                .createCriteria("students", JoinType.LEFT_OUTER_JOIN);
                    }
                }
        );
    }
}
