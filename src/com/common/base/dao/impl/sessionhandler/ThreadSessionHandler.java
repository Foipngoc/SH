package com.common.base.dao.impl.sessionhandler;

import com.common.base.dao.impl.SessionHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by DJ on 2016/1/26.
 *
 * 针对 getCurrentSession版本，所有的事务及异常处理均交由spring管理
 */
public class ThreadSessionHandler implements SessionHandler {
    @Override
    public Session openSession(SessionFactory sessionFactory) {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void beginTransaction(Session session) throws Exception {

    }

    @Override
    public void commitTransaction(Session session) throws Exception {

    }

    @Override
    public void closeSession(Session session) {

    }

    @Override
    public void doException(Exception e, Session session) {
        e.printStackTrace();
    }
}
