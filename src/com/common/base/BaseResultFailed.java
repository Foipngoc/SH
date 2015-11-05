package com.common.base;

import java.util.Map;

/**
 * 失败结果
 * 
 * 默认resultcode为-1
 * 
 * 也可以自行指定resultcode，但是不能为0
 * 
 * @author DJ
 * 
 */
public class BaseResultFailed extends BaseResult {
	public BaseResultFailed() {
		super(RESULT_FAILED, "未知原因");
	}

	public BaseResultFailed(String reason) {
		super(RESULT_FAILED, reason);
	}

	public BaseResultFailed(String reason, Map<String, Object> map) {
		super(RESULT_FAILED, reason, map);
	}

	public BaseResultFailed(String reason, Object obj) {
		super(RESULT_FAILED, reason, obj);
	}

	public BaseResultFailed(int resultcode) {
		super(resultcode, "未知原因");
		if (resultcode == RESULT_OK)
			throw new RuntimeException("失败的结果码不能为0");
	}

	public BaseResultFailed(int resultcode, String reason) {
		super(resultcode, reason);
		if (resultcode == RESULT_OK)
			throw new RuntimeException("失败的结果码不能为0");
	}

	public BaseResultFailed(int resultcode, String reason,
			Map<String, Object> map) {
		super(resultcode, reason, map);
		if (resultcode == RESULT_OK)
			throw new RuntimeException("失败的结果码不能为0");
	}

	public BaseResultFailed(int resultcode, String reason, Object obj) {
		super(resultcode, reason, obj);
		if (resultcode == RESULT_OK)
			throw new RuntimeException("失败的结果码不能为0");
	}
}
