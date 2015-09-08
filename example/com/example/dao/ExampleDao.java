package com.example.dao;

import org.hibernate.metamodel.domain.Superclass;
import org.springframework.stereotype.Repository;

import com.common.base.BaseQueryRecords;
import com.common.basedao.impl.BaseDaoDB;

@Repository("exampleDao")
public class ExampleDao extends BaseDaoDB {
	public BaseQueryRecords<Student> queryAllStu(){
		return (BaseQueryRecords<Student>) super.find(super.getCriteria(Student.class).createCriteria("room"));
	}
}
