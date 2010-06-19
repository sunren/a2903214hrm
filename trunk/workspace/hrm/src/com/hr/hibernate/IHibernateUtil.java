package com.hr.hibernate;

import java.util.Collection;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IHibernateUtil {
    public abstract List exeHqlList(String paramString);

    public abstract List exeHqlList(String paramString, int paramInt1, int paramInt2);

    public abstract List exeHqlList(String paramString, String[] paramArrayOfString1,
            String[] paramArrayOfString2);

    public abstract void updateHqlQuery(String paramString);

    public abstract Object exeHql(String paramString);

    public abstract void saveOrupdate(Object paramObject);

    public abstract <E> void saveOrupdate(Collection<E> paramCollection);

    public abstract void saveObject(Object paramObject);

    public abstract <E> List<E> getObjects(Class<E> paramClass, String[] paramArrayOfString);

    public abstract <E> E loadObject(Class<E> paramClass, Object paramObject,
            String[] paramArrayOfString, boolean[] paramArrayOfBoolean);

    public abstract <E> E loadObjectByName(Class<E> paramClass, String paramString,
            Object paramObject, String[] paramArrayOfString, boolean[] paramArrayOfBoolean);

    public abstract void deleteObject(Object paramObject);

    public abstract <E> void deleteObject(Class<E> paramClass, Object paramObject);

    public abstract <E> void deleteCollection(Collection<E> paramCollection);

    public abstract void updateObject(Object paramObject);

    public abstract List findByCriteria(DetachedCriteria paramDetachedCriteria);

    public abstract List findByCriteria(DetachedCriteria paramDetachedCriteria, int paramInt1,
            int paramInt2);

    public abstract int findRowCountByCriteria(DetachedCriteria paramDetachedCriteria);

    public abstract Object exeHqlObject(String paramString, Object[] paramArrayOfObject);

    public abstract List findByProjectionDetachedCriteria(DetachedCriteria paramDetachedCriteria);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.hibernate.IHibernateUtil JD-Core Version: 0.5.4
 */