package com.example.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.common.base.BaseQueryRecords;
import com.common.utils.LogUtils;
import com.example.dao.ExampleDao;
import com.example.dao.Student;

@Service("exampleService")
@Transactional
public class ExampleService {
	@Resource(name = "exampleDao")
	private ExampleDao exampleDao;

	private Logger logger = LogUtils.getLogger(getClass());

	public BaseQueryRecords<Student> queryAllStudent() {
		logger.error("ExampleService Begin");
		BaseQueryRecords<Student> result = (BaseQueryRecords<Student>) exampleDao
				.find(Student.class);
		logger.error("ExampleService End");
		return result;
	}
}
