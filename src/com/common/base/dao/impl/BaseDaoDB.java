package com.common.base.dao.impl;

import java.util.List;

import com.common.base.dao.impl.querycondition.*;
import com.common.base.dao.impl.sessionhandler.ThreadSessionHandler;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.base.BaseRecords;
import com.common.base.dao.BaseDao;

// 默认声明baseDao Bean.
public class BaseDaoDB implements BaseDao {
    //session处理器
    private SessionHandler dftSessionHandler = new ThreadSessionHandler();

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    // 注入sessionFacory Bean
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionHandler getSessionHandler(SimpleStatment query) {
        if (query != null && query.getSessionHandler() != null)
            return query.getSessionHandler();
        return dftSessionHandler;
    }

    /**
     * 保存对象
     *
     * @param o : 待保存对象
     */
    @Override
    public void save(Object o) {
        Session session = null;
        try {
            session = getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).openSession(getSessionFactory());
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).beginTransaction(session);
            session.save(o instanceof OBJECT ? ((OBJECT) o).getObj() : o);
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).commitTransaction(session);
        } catch (Exception e) {
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).doException(e, session);
        } finally {
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).closeSession(session);
        }
    }

    /**
     * 删除对象
     *
     * @param o : 待删除对象
     */
    @Override
    public void delete(Object o) {
        Session session = null;
        try {
            session = getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).openSession(getSessionFactory());
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).beginTransaction(session);
            session.delete(o instanceof OBJECT ? ((OBJECT) o).getObj() : o);
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).commitTransaction(session);
        } catch (Exception e) {
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).doException(e, session);
        } finally {
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).closeSession(session);
        }
    }

    /**
     * 更新对象
     *
     * @param o : 待更新的对象
     */
    @Override
    public void update(Object o) {
        Session session = null;
        try {
            session = getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).openSession(getSessionFactory());
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).beginTransaction(session);
            session.update(o instanceof OBJECT ? ((OBJECT) o).getObj() : o);
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).commitTransaction(session);
        } catch (Exception e) {
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).doException(e, session);
        } finally {
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).closeSession(session);
        }
    }

    /**
     * 保存或更新对象
     *
     * @param o : 待保存或更新的对象
     */
    @Override
    public void saveOrUpdate(Object o) {
        Session session = null;
        try {
            session = getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).openSession(getSessionFactory());
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).beginTransaction(session);
            session.saveOrUpdate(o instanceof OBJECT ? ((OBJECT) o).getObj() : o);
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).commitTransaction(session);
        } catch (Exception e) {
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).doException(e, session);
        } finally {
            getSessionHandler(o instanceof OBJECT ? (OBJECT) o : null).closeSession(session);
        }
    }

    /**
     * 分页排序查找满足某一条件的所有对象带分页
     *
     * @param query : 查找
     * @return ： 对象集
     */
    @Override
    public BaseRecords find(final ObjectQuery query) {
        return find(new CriteriaGetter(query.getPage(), query.getRows(), query.ifRetrievePages()) {
            @Override
            public Criteria getCriteria(Session session) {
                Criteria criteria = session.createCriteria(query.getCls());
                if (query.getOrder() != null) {
                    if (query.getOrder().getValue())
                        criteria.addOrder(Order.desc(query.getOrder().getKey()));
                    else
                        criteria.addOrder(Order.asc(query.getOrder().getKey()));
                }
                if (query.getKeyVal() != null)
                    criteria.add(Restrictions.eq(query.getKeyVal().getKey(), query.getKeyVal().getValue()));

                return criteria;
            }
        }.setSessionHandler(query.getSessionHandler()));
    }

    /**
     * 查找满足某条件的类型为E的唯一对象
     *
     * @param query : 查找
     * @return : 对象
     */
    @Override
    public Object findUnique(final ObjectQuery query) {
        return find(new CriteriaGetter() {
            @Override
            public Criteria getCriteria(Session session) {
                Criteria criteria = session.createCriteria(query.getCls());
                if (query.getKeyVal() != null)
                    criteria.add(Restrictions.eq(query.getKeyVal().getKey(), query.getKeyVal().getValue()));

                return criteria;
            }
        }.setSessionHandler(query.getSessionHandler()));
    }

    /**
     * 获得满足某条件的类型为E的对象数
     *
     * @param query : 查找
     * @return ： 对象数量
     */
    @Override
    public long count(final ObjectQuery query) {
        return count(new CriteriaGetter() {
            @Override
            public Criteria getCriteria(Session session) {
                Criteria criteria = session.createCriteria(query.getCls());
                if (query.getKeyVal() != null)
                    criteria.add(Restrictions.eq(query.getKeyVal().getKey(), query.getKeyVal().getValue()));
                return criteria;
            }
        }.setSessionHandler(query.getSessionHandler()));
    }

    /*******************************************以下方法非BaseDao定义***********************************************/

    /**
     * 使用SQL语句删除
     *
     * @param sql ： sql语句
     * @return : 响应数目
     */
    protected int delete(SQL sql) {
        Session session = null;
        try {
            session = getSessionHandler(sql).openSession(getSessionFactory());
            getSessionHandler(sql).beginTransaction(session);
            Query q = session.createSQLQuery(sql.getSQLString());
            int ret = q.executeUpdate();
            getSessionHandler(sql).commitTransaction(session);
            return ret;
        } catch (Exception e) {
            getSessionHandler(sql).doException(e, session);
            return 0;
        } finally {
            getSessionHandler(sql).closeSession(session);
        }
    }

    /**
     * 使用HQL语句删除
     *
     * @param hql : hql语句
     * @return : 响应数目
     */
    protected int delete(HQL hql) {
        Session session = null;
        try {
            session = getSessionHandler(hql).openSession(getSessionFactory());
            getSessionHandler(hql).beginTransaction(session);
            Query q = session.createQuery(hql.getHQLString());
            int ret = q.executeUpdate();
            getSessionHandler(hql).commitTransaction(session);
            return ret;
        } catch (Exception e) {
            getSessionHandler(hql).doException(e, session);
            return 0;
        } finally {
            getSessionHandler(hql).closeSession(session);
        }
    }

    /**
     * 使用SQL语句更新
     *
     * @param sql : sql语句
     * @return : 响应数目
     */
    protected int update(SQL sql) {
        Session session = null;
        try {
            session = getSessionHandler(sql).openSession(getSessionFactory());
            getSessionHandler(sql).beginTransaction(session);
            Query q = session.createSQLQuery(sql.getSQLString());
            int ret = q.executeUpdate();
            getSessionHandler(sql).commitTransaction(session);
            return ret;
        } catch (Exception e) {
            getSessionHandler(sql).doException(e, session);
            return 0;
        } finally {
            getSessionHandler(sql).closeSession(session);
        }
    }

    /**
     * 使用HQL语句更新
     *
     * @param hql ： hql语句
     * @return : 响应数目
     */
    protected int update(HQL hql) {
        Session session = null;
        try {
            session = getSessionHandler(hql).openSession(getSessionFactory());
            getSessionHandler(hql).beginTransaction(session);
            Query q = session.createQuery(hql.getHQLString());
            int ret = q.executeUpdate();
            getSessionHandler(hql).commitTransaction(session);
            return ret;
        } catch (Exception e) {
            getSessionHandler(hql).doException(e, session);
            return 0;
        } finally {
            getSessionHandler(hql).closeSession(session);
        }
    }

    /**
     * 使用hql语句查询唯一数据记录，如果不存在，返回NULL
     *
     * @param hql ： hql语句
     * @return ： 记录
     */
    protected Object findUnique(HQL hql) {
        Session session = null;
        try {
            session = getSessionHandler(hql).openSession(getSessionFactory());
            Query q = session.createQuery(hql.getHQLString());
            return q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            getSessionHandler(hql).closeSession(session);
        }
    }

    /**
     * 使用hql语句查询数据集，当page和rows同时>0时,搜索结果会自动分页 分页时， 如果需要返回页数，请传入counthql，否则传入null
     *
     * @param hql ： hql语句
     * @return ： 数据集
     */
    protected BaseRecords<?> find(HQL hql) {
        Session session = null;
        try {
            session = getSessionHandler(hql).openSession(getSessionFactory());
            Query q = session.createQuery(hql.getHQLString());

            if (hql.getPage() > 0 && hql.getRows() > 0) { // 分页
                long total = 0;
                if (hql.ifRetrievePages())
                    total = count(hql.getCountHQL()); // 获得总记录数
                q.setFirstResult((hql.getPage() - 1) * hql.getRows());
                q.setMaxResults(hql.getRows());
                return new BaseRecords(q.list(), total, hql.getPage(), hql.getRows());
            } else {
                // 不分页
                return new BaseRecords(q.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseRecords<>();
        } finally {
            getSessionHandler(hql).closeSession(session);
        }
    }

    /**
     * 使用sql语句查找唯一记录，如果不存在，返回NULL
     *
     * @param sql ： sql语句
     * @return ： 数据集
     */
    protected Object findUnique(SQL sql) {
        Session session = null;
        try {
            session = getSessionHandler(sql).openSession(getSessionFactory());
            Query q = session.createSQLQuery(sql.getSQLString());
            return q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            getSessionHandler(sql).closeSession(session);
        }
    }

    /**
     * 使用sql语句查询数据 集, 当page和rows同时>0时，搜索结果会自动分页,
     * 分页时，如果需要返回页数，请传入countsql，否则传入null
     *
     * @param sql ： sql语句
     * @return ： 数据集
     */
    protected BaseRecords<?> find(SQL sql) {
        Session session = null;
        try {
            session = getSessionHandler(sql).openSession(getSessionFactory());
            Query q = session.createSQLQuery(sql.getSQLString());

            if (sql.getPage() > 0 && sql.getRows() > 0) { // 分页
                long total = 0;
                if (sql.ifRetrievePages())
                    total = count(sql.getCountSQL()); // 获得记录总数
                q.setFirstResult((sql.getPage() - 1) * sql.getRows());
                q.setMaxResults(sql.getRows());
                return new BaseRecords(q.list(), total, sql.getPage(), sql.getRows());
            } else {
                // 查询全部
                return new BaseRecords(q.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseRecords<>();
        } finally {
            getSessionHandler(sql).closeSession(session);
        }
    }

    /**
     * 使用hql语句获得记录数
     *
     * @param hql : hql语句
     * @return ： 数据集
     */
    protected long count(HQL hql) {
        Session session = null;
        try {
            session = getSessionHandler(hql).openSession(getSessionFactory());
            Query q = session.createQuery(hql.getHQLString());
            Object cntObj = q.uniqueResult();
            return ((Number) cntObj).longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            getSessionHandler(hql).closeSession(session);
        }
    }

    /**
     * 使用sql语句获得记录数
     *
     * @param sql ： sql语句
     * @return : 记录数
     */
    protected long count(SQL sql) {
        Session session = null;
        try {
            session = getSessionHandler(sql).openSession(getSessionFactory());
            Query q = session.createSQLQuery(sql.getSQLString());
            Object cntObj = q.uniqueResult();
            return ((Number) cntObj).longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            getSessionHandler(sql).closeSession(session);
        }
    }

    /*******************************************
     * 使用这些hibernate关联查询方法进行数据查询
     *******************************************/

    /**
     * 通过关联查询配置查询记录计数
     *
     * @param criteriaGetter 关联查询对象
     * @return 数据数量
     */
    protected long count(CriteriaGetter criteriaGetter) {
        Session session = null;
        try {
            session = getSessionHandler(criteriaGetter).openSession(getSessionFactory());
            Criteria criteria = criteriaGetter.getCriteria(session);
            Object cntObj = criteria.setProjection(Projections.rowCount())
                    .uniqueResult();
            return ((Number) cntObj).longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            getSessionHandler(criteriaGetter).closeSession(session);
        }
    }

    /**
     * 通过关联查询配置分页查询记录
     *
     * @param criteriaGetter 关联查询对象
     * @return ： 数据集
     */
    protected BaseRecords<?> find(CriteriaGetter criteriaGetter) {
        Session session = null;
        try {
            session = getSessionHandler(criteriaGetter).openSession(getSessionFactory());
            Criteria criteria = criteriaGetter.getCriteria(session);
            if (criteriaGetter.getPage() > 0 && criteriaGetter.getRows() > 0) { // 分页
                long total = 0;
                criteria.setFirstResult((criteriaGetter.getPage() - 1) * criteriaGetter.getRows());
                criteria.setMaxResults(criteriaGetter.getRows());
                List data = criteria.list();
                if (criteriaGetter.ifRetrievePages())
                    total = count(criteriaGetter); // 获得总记录数
                return new BaseRecords(data, total, criteriaGetter.getPage(), criteriaGetter.getRows());
            } else {
                // 不分页
                return new BaseRecords(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseRecords<>();
        } finally {
            getSessionHandler(criteriaGetter).closeSession(session);
        }
    }

    /**
     * 获得唯一对象，如果没有对象，返回null
     *
     * @param criteriaGetter
     * @return 对象
     */
    protected Object findUnique(CriteriaGetter criteriaGetter) {
        Session session = null;
        try {
            session = getSessionHandler(criteriaGetter).openSession(getSessionFactory());
            Criteria criteria = criteriaGetter.getCriteria(session);
            return criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            getSessionHandler(criteriaGetter).closeSession(session);
        }
    }
}
