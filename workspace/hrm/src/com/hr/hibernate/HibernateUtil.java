package com.hr.hibernate;

import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateUtil extends HibernateDaoSupport implements IHibernateUtil {
    public Object exeHql(final String hql) {
        return getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setCacheable(true);
                return new Integer(query.executeUpdate());
            }
        }, true);
    }

    public List exeHqlList(String hql) {
        return exeHqlList(hql, -1, 0);
    }

    public List exeHqlList(String hql, int pageSize, int currentPage) {
        Session session = getSession();
        Query query = session.createQuery(hql);
        if (pageSize > 0) {
            query.setFirstResult((currentPage - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        query.setCacheable(true);
        return query.list();
    }

    public List exeHqlList(String clazz, String[] fields, String[] conditions) {
        String hql = "";
        if ((fields != null) && (fields.length > 0)) {
            hql = "select " + fields[0];
            for (int i = 1; i < fields.length; ++i) {
                hql = hql + ", " + fields[i];
            }
        }
        hql = hql + " from " + clazz;
        if (conditions.length > 0) {
            hql = hql + " where " + conditions[0];
            for (int i = 1; i < conditions.length; ++i) {
                hql = hql + " and " + conditions[i];
            }
        }
        return exeHqlList(hql);
    }

    public void updateHqlQuery(String hql) {
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setCacheable(true);
        query.executeUpdate();
    }

    public <E> E loadObject(Class<E> obj, Object id, String[] fetch, boolean[] cacheable) {
        Session session = getSession();
        Criteria criter = session.createCriteria(obj);

        if (fetch != null) {
            for (int i = 0; i < fetch.length; ++i) {
                if ((fetch[i] != null) && (fetch[i].length() > 1)) {
                    criter.setFetchMode(fetch[i], FetchMode.JOIN);
                }
            }
        }
        criter.add(Restrictions.idEq(id));

        if ((fetch == null) && (cacheable != null) && (cacheable.length > 0))
            criter.setCacheable(cacheable[0]);
        else if (fetch == null) {
            criter.setCacheable(true);
        }
        return (E) criter.uniqueResult();
    }

    public <E> E loadObjectByName(Class<E> obj, String propertyName, Object name, String[] fetch,
            boolean[] cacheable) {
        Session session = getSession();
        Criteria criter = session.createCriteria(obj);

        if (fetch != null) {
            for (int i = 0; i < fetch.length; ++i) {
                if ((fetch[i] != null) && (fetch[i].length() > 1)) {
                    criter.setFetchMode(fetch[i], FetchMode.JOIN);
                }
            }
        }
        criter.add(Restrictions.eq(propertyName, name));

        if ((fetch == null) && (cacheable != null) && (cacheable.length > 0))
            criter.setCacheable(cacheable[0]);
        else if (fetch == null) {
            criter.setCacheable(true);
        }
        return (E) criter.uniqueResult();
    }

    public <E> List<E> getObjects(Class<E> clas, String[] fetch) {
        Session session = getSession();

        Criteria criteria = session.createCriteria(clas);
        if (fetch != null) {
            for (int i = 0; i < fetch.length; ++i) {
                if ((fetch[i] != null) && (fetch[i].length() > 1)) {
                    criteria.setFetchMode(fetch[i], FetchMode.JOIN);
                }
            }
        }

        return criteria.list();
    }

    public void saveObject(Object obj) {
        getHibernateTemplate().save(obj);
    }

    public void saveOrupdate(Object obj) {
        getHibernateTemplate().saveOrUpdate(obj);
    }

    public <E> void saveOrupdate(Collection<E> objs) {
        getHibernateTemplate().saveOrUpdateAll(objs);
    }

    public void deleteObject(Object obj) {
        getHibernateTemplate().delete(obj);
    }

    public <E> void deleteObject(Class<E> clazz, Object id) {
        Object o = loadObject(clazz, id, null, new boolean[0]);
        if (o != null)
            deleteObject(o);
    }

    public <E> void deleteCollection(Collection<E> objs) {
        getHibernateTemplate().deleteAll(objs);
    }

    public void updateObject(Object obj) {
        getHibernateTemplate().update(obj);
    }

    public List findByCriteria(final DetachedCriteria detachedCriteria) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                return criteria.list();
            }
        }, true);
    }

    public List findByCriteria(final DetachedCriteria detachedCriteria, final int pageSize, final int currentPage) {
        return (List) getHibernateTemplate().execute(
                                                     new HibernateCallback() {
                                                         public Object doInHibernate(Session session)
                                                                 throws HibernateException {
                                                             Criteria criteria = detachedCriteria
                                                                     .getExecutableCriteria(session);
                                                             if (pageSize > 0) {
                                                                 criteria
                                                                         .setFirstResult((currentPage - 1)
                                                                                 * pageSize);
                                                                 criteria
                                                                         .setMaxResults(pageSize);
                                                             }
                                                             criteria
                                                                     .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                                                             return criteria.list();
                                                         }
                                                     }, true);
    }

    public int findRowCountByCriteria(final DetachedCriteria detachedCriteria) {
        return ((Integer) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                Integer totalCount = (Integer) criteria.setProjection(Projections.rowCount())
                        .uniqueResult();

                criteria.setProjection(null);
                return Integer.valueOf((totalCount == null) ? 0 : totalCount.intValue());
            }
        }, true)).intValue();
    }

    public Object exeHqlObject(String hql, Object[] values) {
        Session session = getSession();
        Query query = session.createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; ++i) {
                query.setParameter(i, values[i]);
            }
        }
        query.setCacheable(true);
        return query.uniqueResult();
    }

    public List findByProjectionDetachedCriteria(final DetachedCriteria detachedCriteria) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                criteria.setResultTransformer(CriteriaSpecification.PROJECTION);
                return criteria.list();
            }
        }, true);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.hibernate.HibernateUtil JD-Core Version: 0.5.4
 */