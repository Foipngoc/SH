package com.common.baseresult;

import java.util.Map;

/**
 * 成功结果
 * 
 * @author DJ
 * 
 */
public class BaseResultOK extends BaseResult {
	public BaseResultOK() {
		super(RESULT_OK, "");
	}

	public BaseResultOK(BaseQueryRecords<?> records) {
		super(RESULT_OK, "", records);
	}

	public BaseResultOK(Map<String, Object> map) {
		super(RESULT_OK, "", map);
	}

	public BaseResultOK(Object obj) {
		super(RESULT_OK, "", obj);
	}
}
