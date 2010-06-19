package com.hr.configuration.bo;

import com.hr.configuration.dao.IEmpTypeDAO;
import com.hr.configuration.domain.Emptype;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpTypeBOImpl implements IEmpTypeBO {
    private IEmpTypeDAO dao;

    public EmpTypeBOImpl() {
        this.dao = null;
    }

    public List FindAllEmpType() {
        DetachedCriteria dc = DetachedCriteria.forClass(Emptype.class);
        dc.addOrder(Order.asc("emptypeSortId"));
        return this.dao.findByCriteria(dc);
    }

    public List<Emptype> FindEnabledEmpType() {
        DetachedCriteria dc = DetachedCriteria.forClass(Emptype.class);
        dc.add(Restrictions.eq(Emptype.PROP_EMPTYPE_STATUS, new Integer(1)));
        dc.addOrder(Order.asc("emptypeSortId"));
        return this.dao.findByCriteria(dc);
    }

    public boolean addEmptype(Emptype emp) {
        try {
            String trimmedEmptypeName = emp.getEmptypeName().trim();
            List old = this.dao.exeHqlList(" from Emptype where emptypeName ='"
                    + trimmedEmptypeName + "'");

            if ((old == null) || (old.size() == 0)) {
                this.dao.saveObject(emp);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delEmptype(Class<Emptype> name, String id) {
        try {
            List check = new ArrayList();
            boolean isRefed = false;
            check = this.dao.exeHqlList("select count(*) from Empsalarypay where espEmptype = '"
                    + id + "'");

            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                isRefed = true;
            }
            if (!isRefed) {
                check = this.dao.exeHqlList("select count(*) from Employee where empType = '" + id
                        + "'");

                if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                    isRefed = true;
                }
            }
            Emptype type = (Emptype) this.dao.loadObject(name, id, null, new boolean[0]);

            if ((!isRefed) && (type != null)) {
                this.dao.deleteObject(type);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String updateEmptype(Emptype emptype) {
        if (emptype.getEmptypeStatus().intValue() == 0) {
            List check = new ArrayList();
            String hql = "select count(*) from Employee where empType = '" + emptype.getId() + "'";
            check = this.dao.exeHqlList(hql);
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return "1";
            }
        }
        List old = this.dao.exeHqlList(" from Emptype where emptypeName ='"
                + emptype.getEmptypeName() + "' and id!='" + emptype.getId() + "'");

        if ((old == null) || (old.size() == 0)) {
            Emptype oldType = (Emptype) this.dao.loadObject(Emptype.class, emptype.getId(), null,
                                                            new boolean[0]);
            oldType.setEmptypeName(emptype.getEmptypeName());
            oldType.setEmptypeDesc(emptype.getEmptypeDesc());
            oldType.setEmptypeStatus(emptype.getEmptypeStatus());
            oldType.setEmptypeSortId(emptype.getEmptypeSortId());
            this.dao.updateObject(oldType);
            return "0";
        }
        return "2";
    }

    public void saveEmpTypeIdByBatch(String[] ids) {
        if (ids == null)
            return;
        int sort = 1;
        int len = ids.length;
        for (int i = 0; i < len; ++i) {
            this.dao.exeHql("update Emptype set emptypeSortId=" + sort + " where id ='" + ids[i]
                    + "'");

            ++sort;
        }
    }

    public IEmpTypeDAO getDao() {
        return this.dao;
    }

    public void setDao(IEmpTypeDAO dao) {
        this.dao = dao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.EmpTypeBOImpl JD-Core Version: 0.5.4
 */