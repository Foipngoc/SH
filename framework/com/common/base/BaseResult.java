package com.common.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据返回结果集，常用于json返回或service层与action层数据交换
 *
 * @author DongJun
 */
public class BaseResult {
    private int resultcode;// 常用于返回操作结果码
    private String resultdesc;// 常用于返回操作结果描述

    public static final int RESULT_OK = 0; // 成功结果码
    public static final int RESULT_FAILED = -1; // 失败结果码

    private BaseRecords<?> records = null; // 返回查询结果
    private Map<String, Object> map = null;// 返回自定义数据
    private Object obj = null;// 返回自定义数据

    /**
     * 默认构造
     */
    public BaseResult() {
    }

    /**
     * 带结果码与结果描述构造函数
     *
     * @param code
     * @param desc
     */
    public BaseResult(int code, String desc) {
        this.resultcode = code;
        this.resultdesc = desc;
    }

    /**
     * 带结果码与结果描述构造函数
     *
     * @param code
     * @param desc
     * @param records
     */
    public BaseResult(int code, String desc, BaseRecords<?> records) {
        this.resultcode = code;
        this.resultdesc = desc;
        this.records = records;
    }

    /**
     * 带结果码与结果描述构造函数
     *
     * @param code
     * @param desc
     */
    public BaseResult(int code, String desc, Map<String, Object> map) {
        this.resultcode = code;
        this.resultdesc = desc;
        this.map = map;
    }

    /**
     * 带结果码与结果描述构造函数
     *
     * @param code
     * @param desc
     */
    public BaseResult(int code, String desc, Object obj) {
        this.resultcode = code;
        this.resultdesc = desc;
        this.obj = obj;
    }

    /**
     * 获取结果码
     *
     * @return
     */
    public int getResultcode() {
        return resultcode;
    }

    /**
     * 设置结果码
     *
     * @param resultcode
     * @return 自身
     */
    public BaseResult setResultcode(int resultcode) {
        this.resultcode = resultcode;
        return this;
    }

    /**
     * 获得结果描述
     *
     * @return
     */
    public String getResultdesc() {
        return resultdesc;
    }

    /**
     * 设置结果描述
     *
     * @param resultdesc
     * @return 自身
     */
    public BaseResult setResultdesc(String resultdesc) {
        this.resultdesc = resultdesc;
        return this;
    }

    /**
     * 获得自定义map数据
     *
     * @return
     */
    public Map<String, Object> getMap() {
        return this.map;
    }

    /**
     * 设置自定义map数据
     *
     * @param map
     * @return 自身
     */
    public BaseResult setMap(Map<String, Object> map) {
        this.map = map;
        return this;
    }

    /**
     * 添加自定义数据到map中
     *
     * @param key
     * @param val
     * @return 自身
     */
    public BaseResult addToMap(String key, Object val) {
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        map.put(key, val);
        return this;
    }

    /**
     * 从map中删除自定义数据
     *
     * @param key
     * @return 自身
     */
    public BaseResult removeFromMap(String key) {
        if (map != null) {
            map.remove(key);
        }
        return this;
    }

    /**
     * 从map中获得自定义数据
     *
     * @param key
     * @return
     */
    public Object getFromMap(String key) {
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    /**
     * 获得自定义对象
     *
     * @return
     */
    public Object getObj() {
        return obj;
    }

    /**
     * 设置自定义对象
     *
     * @param obj
     * @return 自身
     */
    public BaseResult setObj(Object obj) {
        this.obj = obj;
        return this;
    }

    /**
     * 获得查询记录集
     *
     * @return
     */
    public BaseRecords<?> getRecords() {
        return records;
    }

    /**
     * 设置查询记录集
     *
     * @param records
     * @return 自身
     */
    public BaseResult setRecords(BaseRecords<?> records) {
        this.records = records;
        return this;
    }

    /**
     * 判断结果是否正确
     *
     * @return
     */
    public boolean ifResultOK() {
        return this.resultcode == RESULT_OK;
    }

    /**
     * 失败结果， 错误原因为： 未知原因
     */
    public static BaseResult newResultFailed() {
        return new BaseResult(RESULT_FAILED, "未知原因");
    }

    /**
     * 失败结果，原因
     *
     * @param reason
     */
    public static BaseResult newResultFailed(String reason) {
        return new BaseResult(RESULT_FAILED, reason);
    }

    /**
     * 构造函数并自带数据
     *
     * @param reason
     * @param map
     */
    public static BaseResult newResultFailed(String reason, Map<String, Object> map) {
        return new BaseResult(RESULT_FAILED, reason, map);
    }

    /**
     * 失败结果并自带数据
     *
     * @param reason
     * @param obj
     */
    public static BaseResult newResultFailed(String reason, Object obj) {
        return new BaseResult(RESULT_FAILED, reason, obj);
    }

    /**
     * 自定义非0结果码
     *
     * @param resultcode
     */
    public static BaseResult newResultFailed(int resultcode) {
        if (resultcode == RESULT_OK)
            throw new RuntimeException("失败的结果码不能为0");
        return new BaseResult(resultcode, "未知原因");
    }

    /**
     * 自定义非0结果码跟原因
     *
     * @param resultcode
     * @param reason
     */
    public static BaseResult newResultFailed(int resultcode, String reason) {
        if (resultcode == RESULT_OK)
            throw new RuntimeException("失败的结果码不能为0");
        return new BaseResult(resultcode, reason);
    }

    /**
     * 自定义非0结果码跟原因 并提供map数据
     *
     * @param resultcode
     * @param reason
     * @param map
     */
    public static BaseResult newResultFailed(int resultcode, String reason,
                                             Map<String, Object> map) {
        if (resultcode == RESULT_OK)
            throw new RuntimeException("失败的结果码不能为0");
        return new BaseResult(resultcode, reason, map);
    }

    /**
     * 自定义非0结果码跟原因， 并提供obj数据
     *
     * @param resultcode
     * @param reason
     * @param obj
     */
    public static BaseResult newResultFailed(int resultcode, String reason, Object obj) {
        if (resultcode == RESULT_OK)
            throw new RuntimeException("失败的结果码不能为0");
        return new BaseResult(resultcode, reason, obj);
    }


    /**
     * 成功
     */
    public static BaseResult newResultOK() {
        return new BaseResult(RESULT_OK, "");
    }

    /**
     * 成功，带查询记录集数据
     *
     * @param records
     */
    public static BaseResult newResultOK(BaseRecords<?> records) {
        return new BaseResult(RESULT_OK, "", records);
    }

    /**
     * 成功，带map数据
     *
     * @param map
     */
    public static BaseResult newResultOK(Map<String, Object> map) {
        return new BaseResult(RESULT_OK, "", map);
    }

    /**
     * 成功，带obj数据
     *
     * @param obj
     */
    public static BaseResult newResultOK(Object obj) {
        return new BaseResult(RESULT_OK, "", obj);
    }
}
