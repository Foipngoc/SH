package com.common.base.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.base.BaseRecords;
import com.common.base.dao.BaseDao;

// 默认声明baseDao Bean.
public class BaseDaoDB implements BaseDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    // 注入sessionFacory Bean
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 获得当前的hibernate session。
     *
     * @return session
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
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
            session = getSession();
            session.save(o);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            session = getSession();
            session.delete(o);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            session = getSession();
            session.update(o);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            session = getSession();
            session.saveOrUpdate(o);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 查找所有类型为E的对象集
     *
     * @param cls : 待查找的对象类型
     * @return : 对象集
     */
    @Override
    public BaseRecords<?> find(Class<?> cls) {
        return this.find(cls, -1, -1);
    }

    /**
     * 分页查找所有类型为E的对象集
     *
     * @param cls  : 待查找的对象类型
     * @param page : 页码
     * @param rows : 每页条数
     * @return : 对象集
     */
    @Override
    public BaseRecords<?> find(Class<?> cls, long page, long rows) {
        Session session = null;
        try {
            session = getSession();
            Criteria criteria = session.createCriteria(cls);

            // page和rows 都 >0 时返回分页数据
            if (page > 0 && rows > 0) {
                long total = count(cls);
                criteria.setFirstResult((int) ((page - 1) * rows));
                criteria.setMaxResults((int) rows);
                return new BaseRecords(criteria.list(), total, page, rows);
            } else {
                return new BaseRecords(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 查找满足某一条件的所有类型为E的对象
     *
     * @param cls   : 待查找对象类型
     * @param key   : 条件名
     * @param value ： 条件值
     * @return ： 对象集
     */
    @Override
    public BaseRecords<?> find(Class<?> cls, String key, Object value) {
        return this.find(cls, key, value, -1, -1);
    }

    /**
     * 分页查找满足某一条件的所有类型为E的对象
     *
     * @param cls   : 待查找对象类型
     * @param key   : 条件名
     * @param value ： 条件值
     * @param page  : 页码
     * @param rows  : 每页行数
     * @return ： 对象集
     */
    @Override
    public BaseRecords<?> find(Class<?> cls, String key, Object value, long page, long rows) {
        Session session = null;
        try {
            session = getSession();
            Criteria criteria = session.createCriteria(cls);

            criteria.add(Restrictions.eq(key, value));

            if (page > 0 && rows > 0) {
                long total = count(cls, key, value);
                criteria.setFirstResult((int) ((page - 1) * rows));
                criteria.setMaxResults((int) rows);
                return new BaseRecords(criteria.list(), total, page, rows);
            } else {
                return new BaseRecords(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 排序查找所有对象
     *
     * @param cls     : 待查找对象类型
     * @param orderby : 待排序字段
     * @param ifdesc  : true--> DESC排序,false--> ASC排序
     * @return ： 对象集
     */
    @Override
    public BaseRecords<?> findOrderBy(Class<?> cls, String orderby, boolean ifdesc) {
        return this.findOrderBy(cls, orderby, ifdesc, -1, -1);
    }

    /**
     * 分页排序查找所有对象
     *
     * @param cls     : 待查找对象类型
     * @param orderby : 待排序字段
     * @param ifdesc  : true--> DESC排序,false--> ASC排序
     * @param page    : 页码
     * @param rows    : 每页数量
     * @return ： 对象集
     */
    @Override
    public BaseRecords<?> findOrderBy(Class<?> cls, String orderby, boolean ifdesc, long page, long rows) {
        Session session = null;
        try {
            session = getSession();
            Criteria criteria = session.createCriteria(cls);
            if (orderby != null && !orderby.equals("")) {
                if (ifdesc)
                    criteria.addOrder(Order.desc(orderby));
                else
                    criteria.addOrder(Order.asc(orderby));
            }
            // page和rows 都 >0 时返回分页数据
            if (page > 0 && rows > 0) {
                long total = count(cls);
                criteria.setFirstResult((int) ((page - 1) * rows));
                criteria.setMaxResults((int) rows);
                return new BaseRecords(criteria.list(), total, page, rows);
            } else {
                return new BaseRecords(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 排序查找满足某一条件的所有对象带分页
     *
     * @param cls     : 待查找对象类型
     * @param key     : 条件字段
     * @param value   : 条件值
     * @param orderby : 待排序字段
     * @param ifdesc  : true--> DESC排序,false--> ASC排序
     * @return ： 对象集
     */
    @Override
    public BaseRecords<?> findOrderBy(Class<?> cls, String key, Object value, String orderby, boolean ifdesc) {
        return this.findOrderBy(cls, key, value, orderby, ifdesc, -1, -1);
    }

    /**
     * 分页排序查找满足某一条件的所有对象带分页
     *
     * @param cls     : 待查找对象类型
     * @param key     : 条件字段
     * @param value   : 条件值
     * @param orderby : 待排序字段
     * @param ifdesc  : true--> DESC排序,false--> ASC排序
     * @param page    : 页码
     * @param rows    : 每页数量
     * @return ： 对象集
     */
    @Override
    public BaseRecords<?> findOrderBy(Class<?> cls, String key, Object value, String orderby, boolean ifdesc, long page, long rows) {
        Session session = null;
        try {
            session = getSession();
            Criteria criteria = session.createCriteria(cls);

            if (orderby != null && !orderby.equals("")) {
                if (ifdesc)
                    criteria.addOrder(Order.desc(orderby));
                else
                    criteria.addOrder(Order.asc(orderby));
            }

            criteria.add(Restrictions.eq(key, value));

            if (page > 0 && rows > 0) {
                long total = count(cls, key, value);
                criteria.setFirstResult((int) ((page - 1) * rows));
                criteria.setMaxResults((int) rows);
                return new BaseRecords(criteria.list(), total, page, rows);
            } else {
                return new BaseRecords(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 查找满足某条件的类型为E的唯一对象
     *
     * @param cls   : 待查找对象类型
     * @param key   : 条件名
     * @param value : 条件值
     * @return : 对象
     */
    @Override
    public Object findUnique(Class<?> cls, String key, Object value) {
        Session session = null;
        try {
            session = getSession();
            Criteria criteria = session.createCriteria(cls);
            criteria.add(Restrictions.eq(key, value));
            criteria.setFirstResult(0);
            criteria.setMaxResults(1);
            return criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获得类型为E的对象数
     *
     * @param cls ： 待查找的对象类型
     * @return ： 对象的数量
     */
    @Override
    public long count(Class<?> cls) {
        Session session = null;
        try {
            session = getSession();
            Criteria criteria = session.createCriteria(cls);
            criteria.setProjection(Projections.rowCount());
            Object cntObj = criteria.uniqueResult();
            return getCountFromObj(cntObj);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获得满足某条件的类型为E的对象数
     *
     * @param cls   : 待查找对象类型
     * @param key   : 条件名
     * @param value : 条件值
     * @return ： 对象数量
     */
    @Override
    public long count(Class<?> cls, String key, Object value) {
        Session session = null;
        try {
            session = getSession();
            Criteria criteria = session.createCriteria(cls);
            criteria.add(Restrictions.eq(key, value));
            criteria.setProjection(Projections.rowCount());
            Object cntObj = criteria.uniqueResult();
            return getCountFromObj(cntObj);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
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
            session = getSession();
            Query q = session.createSQLQuery(sql.toString());
            return q.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            session = getSession();
            Query q = session.createQuery(hql.toString());
            return q.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            session = getSession();
            Query q = session.createSQLQuery(sql.toString());
            return q.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            session = getSession();
            Query q = session.createQuery(hql.toString());
            return q.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            session = getSession();
            Query q = session.createQuery(hql.toString());
            q.setFirstResult(0);
            q.setMaxResults(1);
            return q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 使用hql语句查询数据集
     * 注： 分页时， 查询总记录数的hql语句只是将select ** 转换成select count(*)
     *
     * @param hql ： hql语句
     * @return ： 数据集
     */
    protected BaseRecords<?> find(HQL hql) {
        return find(hql, -1, -1);
    }

    /**
     * 使用hql语句查询数据集，当page和rows同时>0时,搜索结果会自动分页.
     * 注： 分页时， 查询总记录数的hql语句只是将select ** 转换成select count(*)
     *
     * @param hql  ： hql语句
     * @param page ： 页码
     * @param rows ： 每页行数
     * @return ： 数据集
     */
    protected BaseRecords<?> find(HQL hql, long page, long rows) {
        return _find(hql, hql.toCountHQL(), page, rows);
    }

    /**
     * 使用hql语句查询数据集，当page和rows同时>0时,搜索结果会自动分页 分页时， 如果需要返回页数，请传入counthql，否则传入null
     *
     * @param hql      ： hql语句
     * @param counthql : 计数hql语句
     * @param page     ： 页码
     * @param rows     ： 每页行数
     * @return ： 数据集
     */
    private BaseRecords<?> _find(HQL hql, HQL counthql, long page,
                                 long rows) {
        Session session = null;
        try {
            session = getSession();
            Query q = session.createQuery(hql.toString());

            if (page > 0 && rows > 0) { // 分页
                long total = 0;
                if (counthql != null)
                    total = count(counthql); // 获得总记录数
                q.setFirstResult((int) ((page - 1) * rows));
                q.setMaxResults((int) rows);
                return new BaseRecords(q.list(), total, page, rows);
            } else {
                // 不分页
                return new BaseRecords(q.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            session = getSession();
            Query q = session.createSQLQuery(sql.toString());
            q.setFirstResult(0);
            q.setMaxResults(1);
            return q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 使用sql语句查询数据集
     * <p/>
     * 注： 分页时，查询总记录数的sql语句只是将select ** 转换成select count(*)
     *
     * @param sql ： sql语句
     * @return ： 数据集
     */
    protected BaseRecords<?> find(SQL sql) {
        return find(sql, -1, -1);
    }

    /**
     * 使用sql语句查询数据 集，当page和rows同时>0时，搜索结果会自动分页
     * <p/>
     * 注： 分页时，查询总记录数的sql语句只是将select ** 转换成select count(*)
     *
     * @param sql  ： sql语句
     * @param page ： 页码
     * @param rows ： 每页行数
     * @return ： 数据集
     */
    protected BaseRecords<?> find(SQL sql, long page, long rows) {
        return _find(sql, sql.toCountSQL(), page, rows);
    }

    /**
     * 使用sql语句查询数据 集, 当page和rows同时>0时，搜索结果会自动分页,
     * 分页时，如果需要返回页数，请传入countsql，否则传入null
     *
     * @param sql      ： sql语句
     * @param countsql : 获得记录总数sql
     * @param page     ： 页码
     * @param rows     ： 每页行数
     * @return ： 数据集
     */
    private BaseRecords<?> _find(SQL sql, SQL countsql, long page,
                                 long rows) {
        Session session = null;
        try {
            session = getSession();
            Query q = session.createSQLQuery(sql.toString());

            if (page > 0 && rows > 0) { // 分页
                long total = 0;
                if (countsql != null)
                    total = count(countsql); // 获得记录总数
                q.setFirstResult((int) ((page - 1) * rows));
                q.setMaxResults((int) rows);
                return new BaseRecords(q.list(), total, page, rows);
            } else {
                // 查询全部
                return new BaseRecords(q.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            session = getSession();
            Query q = session.createQuery(hql.toString());
            Object cntObj = q.uniqueResult();
            return getCountFromObj(cntObj);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            session = getSession();
            Query q = session.createSQLQuery(sql.toString());
            Object cntObj = q.uniqueResult();
            return getCountFromObj(cntObj);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


  /*  *//**
     * 使用opensession来执行sql语句
     *
     * @param sql ：sql语句
     * @return: 响应数量
     *//*
    protected int executeSql1(SQL sql) {
        int ret = 0;
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createSQLQuery(sql.toString());
            ret = q.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null)
                session.getTransaction().rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return ret;
    }

    *//**
     * 使用opensession来执行hql语句
     *
     * @param hql : hql语句
     * @return: 响应数量
     *//*
    protected int executeHql1(HQL hql) {
        int ret = 0;
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql.toString());
            ret = q.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null)
                session.getTransaction().rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return ret;
    }

    *//**
     * 使用opensession，使用hql语句查询数据集，当page和rows同时>0时,搜索结果会自动分页 分页时， 如果需要返回页数，请传入counthql，否则传入null
     *
     * @param hql      ： hql语句
     * @param counthql : 计数hql语句
     * @param page     ： 页码
     * @param rows     ： 每页行数
     * @return： 数据集
     *//*
    protected BaseRecords<?> find1(HQL hql, HQL counthql, long page,
                                   long rows) {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            Query q = session.createQuery(hql.toString());

            if (page > 0 && rows > 0) { // 分页
                long total = 0;
                if (counthql != null)
                    total = count(counthql); // 获得总记录数
                q.setFirstResult((int) ((page - 1) * rows));
                q.setMaxResults((int) rows);
                return new BaseRecords(q.list(), total, page, rows);
            } else {
                // 不分页
                return new BaseRecords(q.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }

    *//**
     * 使用opensession，使用sql语句查询数据 集, 当page和rows同时>0时，搜索结果会自动分页,
     * 分页时，如果需要返回页数，请传入countsql，否则传入null
     *
     * @param sql      ： sql语句
     * @param countsql : 获得记录总数sql
     * @param page     ： 页码
     * @param rows     ： 每页行数
     * @return: 数据集
     *//*
    protected BaseRecords<?> find1(SQL sql, SQL countsql, long page,
                                   long rows) {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            Query q = session.createSQLQuery(sql.toString());

            if (page > 0 && rows > 0) { // 分页
                long total = 0;
                if (countsql != null)
                    total = count(countsql); // 获得记录总数
                q.setFirstResult((int) ((page - 1) * rows));
                q.setMaxResults((int) rows);
                return new BaseRecords(q.list(), total, page, rows);
            } else {
                // 查询全部
                return new BaseRecords(q.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }*/


    /*******************************************使用这些hibernate关联查询方法进行数据查询*******************************************/

    /**
     * 获得某对象的关联查询配置
     *
     * @param modelClz 关联查询的对象
     * @return 关联查询对象
     */
    protected Criteria getCriteria(Class<?> modelClz) {
        return getSession().createCriteria(modelClz);
    }

    /**
     * 通过关联查询配置查询记录计数
     *
     * @param criteria 关联查询对象
     * @return 数据数量
     */
    protected long count(Criteria criteria) {
        Object cntObj = criteria.setProjection(Projections.rowCount())
                .uniqueResult();
        return getCountFromObj(cntObj);
    }

    /**
     * 通过关联查询配置查询记录
     *
     * @param criteria 关联查询对象
     * @return ： 数据集
     */
    protected BaseRecords<?> find(Criteria criteria) {
        return this.find(criteria, -1, -1);
    }

    /**
     * 通过关联查询配置分页查询记录
     *
     * @param criteria 关联查询对象
     * @param page     当前页
     * @param rows     每页条数
     * @return ： 数据集
     */
    protected BaseRecords<?> find(Criteria criteria, long page, long rows) {
        try {
            if (page > 0 && rows > 0) { // 分页
                long total = 0;
                criteria.setFirstResult((int) ((page - 1) * rows));
                criteria.setMaxResults((int) rows);
                List data = criteria.list();
                total = count(criteria); // 获得总记录数
                return new BaseRecords(data, total, page, rows);
            } else {
                // 不分页
                return new BaseRecords(criteria.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 从结果集中解析出数据量
     *
     * @param cntObj 需解析对象
     * @return ： 数据集
     */
    private Long getCountFromObj(Object cntObj) {
        if (cntObj != null) {
            if (cntObj instanceof BigInteger)
                return ((BigInteger) cntObj).longValue();
            else if (cntObj instanceof BigDecimal)
                return ((BigDecimal) cntObj).longValue();
            else if (cntObj instanceof Long)
                return (Long) cntObj;
            else
                return (long) cntObj;
        } else {
            return 0L;
        }
    }
}
