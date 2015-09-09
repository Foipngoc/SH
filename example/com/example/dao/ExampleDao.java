package com.example.dao;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metamodel.domain.Superclass;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.common.base.BaseQueryRecords;
import com.common.basedao.impl.BaseDaoDB;

@Repository("exampleDao")
public class ExampleDao extends BaseDaoDB {
	public BaseQueryRecords<Student> queryAllStu() {
		return (BaseQueryRecords<Student>) super.find(super.getCriteria(
				Student.class).createCriteria("room"));
	}

	public Student queryStu(int id) {
		return (Student) super
				.find(super.getCriteria(Student.class)
						.add(Restrictions.eq("id", id)).createCriteria("room"))
				.getData().get(0);
	}

	public BaseQueryRecords<Room> queryRoom(int id) {
		return (BaseQueryRecords<Room>) super.find(super
				.getCriteria(Room.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.createCriteria("students", JoinType.LEFT_OUTER_JOIN));
	}
}
