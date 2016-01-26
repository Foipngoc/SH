package com.common.base.dao.impl.sessionhandler;

import com.common.base.dao.impl.SessionHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by DJ on 2016/1/26.
 *
 * 针对opensession版本，所有的事务及异常处理均需要自行处理
 */
public class LocalSessionHandler implements SessionHandler {
    @Override
    public Session openSession(SessionFactory sessionFactory) throws Exception {
        return sessionFactory.openSession();
    }

    @Override
    public void beginTransaction(Session session) throws Exception {
        session.beginTransaction();
    }

    @Override
    public void commitTransaction(Session session) throws Exception {
        session.getTransaction().commit();
    }

    @Override
    public void closeSession(Session session) {
        if (session != null)
            session.close();
    }

    @Override
    public void doException(Exception e, Session session) {
        e.printStackTrace();
        session.getTransaction().rollback();
    }
}
