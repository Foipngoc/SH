package com.common.base.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by DJ on 2016/1/25.
 */
public interface SessionHandler {

    /**
     * 如何获得session
     *
     * @return session
     */
    public Session openSession(SessionFactory sessionFactory) throws Exception;

    /**
     * 开始事务
     *
     * @param session session
     */
    public void beginTransaction(Session session) throws Exception;

    /**
     * 提交事务
     *
     * @param session session
     */
    public void commitTransaction(Session session) throws Exception;

    /**
     * 关闭session
     */
    public void closeSession(Session session);

    /**
     * 如果处理异常
     *
     * @param session session
     */
    public void doException(Exception e, Session session);
}
