package com.hr.configuration.dao;

import com.hr.base.Status;
import com.hr.configuration.domain.Statusconf;
import com.hr.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

public class StatusDAOImpl extends HibernateUtil implements IStatusDAO, Status {
    public List<Statusconf> getStatusByTable(String tableName) {
        Criteria crite = getSession().createCriteria(Statusconf.class);
        crite.add(Expression.eq("id.statusconfTablename", tableName.trim()));
        return crite.list();
    }

    public Statusconf getStatusByName(String tableName, String statusName) {
        Criteria crite = getSession().createCriteria(Statusconf.class);
        crite.add(Expression.eq("id.statusconfTablename", tableName.trim()));
        crite.add(Expression.like("statusconfDesc", "%" + statusName + "%"));
        return (Statusconf) crite.uniqueResult();
    }

    public List getCompeplanStatusInRecruitplan(String tableName) {
        Criteria crite = getSession().createCriteria(Statusconf.class);
        crite.add(Expression.eq("id.statusconfTablename", tableName.trim()));

        return crite.list();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.dao.StatusDAOImpl JD-Core Version: 0.5.4
 */