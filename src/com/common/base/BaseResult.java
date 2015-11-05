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

    private BaseQueryRecords<?> records = null; // 返回查询结果
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
    public BaseResult(int code, String desc, BaseQueryRecords<?> records) {
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
    public BaseQueryRecords<?> getRecords() {
        return records;
    }

    /**
     * 设置查询记录集
     *
     * @param records
     * @return 自身
     */
    public BaseResult setRecords(BaseQueryRecords<?> records) {
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
}
