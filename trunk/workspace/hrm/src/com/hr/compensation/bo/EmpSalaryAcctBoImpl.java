package com.hr.compensation.bo;

import com.hr.compensation.dao.IEmpSalaryAcctDao;
import com.hr.compensation.dao.IEmpSalaryAcctversionDao;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.util.Pager;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpSalaryAcctBoImpl implements IEmpSalaryAcctBo {
    private IEmpSalaryAcctDao empsalaryacctDao;
    private IEmpSalaryAcctversionDao empsalaryacctversionDao;

    public List getObjects(Class clas, String[] fetch) {
        return this.empsalaryacctDao.getObjects(clas, fetch);
    }

    public Empsalaryacct loadObject(String id, String[] fetch) {
        return (Empsalaryacct) this.empsalaryacctDao.loadObject(Empsalaryacct.class, id, fetch,
                                                                new boolean[0]);
    }

    public Boolean deleteEmpsalaryacct(Object id) {
        try {
            this.empsalaryacctDao.deleteObject(id);
            return Boolean.valueOf(true);
        } catch (Exception e) {
        }
        return Boolean.valueOf(false);
    }

    public Boolean deleteEmpsalaryacct(String esaId) {
        try {
            String hql = "delete Empsalaryacct as esa where id='" + esaId + "'";
            this.empsalaryacctDao.exeHql(hql);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.valueOf(false);
    }

    public Boolean insertEmpsalaryacct(Empsalaryacct empsalaryacct) {
        try {
            this.empsalaryacctDao.saveObject(empsalaryacct);
            return Boolean.valueOf(true);
        } catch (Exception e) {
        }
        return Boolean.valueOf(false);
    }

    public boolean searchAcctNames(String acctName, String acctId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctversion.class);
        detachedCriteria.setFetchMode("esavEsac", FetchMode.JOIN);
        detachedCriteria.createAlias("esavEsac", "esac");
        if (acctName != null)
            detachedCriteria.add(Restrictions.eq("esac.esacName", acctName.trim()));
        else {
            return false;
        }
        if (acctId != null) {
            detachedCriteria.add(Restrictions.not(Restrictions.eq("esac.id", acctId.trim())));
        }
        List list = this.empsalaryacctversionDao.findByCriteria(detachedCriteria);

        return (list != null) && (list.size() != 0);
    }

    public List<Empsalaryacctversion> searchEmpsalaryacct(Empsalaryacct empsalaryacct, Pager page,
            String versionFlag) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctversion.class);
        detachedCriteria.setFetchMode("esavEsac", FetchMode.JOIN);
        detachedCriteria.createAlias("esavEsac", "esac");
        if (empsalaryacct.getEsacName() != null)
            detachedCriteria.add(Restrictions.like("esac.esacName", empsalaryacct.getEsacName()
                    .trim(), MatchMode.ANYWHERE));
        if (versionFlag == null) {
            detachedCriteria.add(Restrictions.isNull("esavValidTo"));
        }
        checkOrderMethod(detachedCriteria, page.getOrder());
        detachedCriteria.addOrder(Order.desc(Empsalaryacctversion.PROP_ESAV_VALID_FROM));
        return this.empsalaryacctversionDao.findByCriteria(detachedCriteria);
    }

    public Empsalaryacctversion getMaxValidToEsav(String acctId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctversion.class);
        detachedCriteria.setFetchMode("esavEsac", FetchMode.JOIN);
        detachedCriteria.createAlias("esavEsac", "esac");
        detachedCriteria.add(Restrictions.eq("esac.id", acctId));
        detachedCriteria.addOrder(Order.desc("esavValidTo"));

        List list = this.empsalaryacctversionDao.findByCriteria(detachedCriteria);
        return (Empsalaryacctversion) list.get(0);
    }

    public Boolean updateEmpsalaryacct(Empsalaryacct empsalaryacct) {
        try {
            this.empsalaryacctDao.updateObject(empsalaryacct);
            return Boolean.valueOf(true);
        } catch (Exception e) {
        }
        return Boolean.valueOf(false);
    }

    private void checkOrderMethod(DetachedCriteria detachedCriteria, String order) {
        if ((order != null) && (order.trim().length() > 1) && (order.indexOf('-') != -1)) {
            String[] pram = order.split("-");
            if ((pram[0] == null) || (pram[0].length() < 1)) {
                return;
            }
            String[] fetch = pram[0].split("\\.");
            String orde = pram[0];
            if (fetch.length > 1) {
                String str = "";
                String fetc = "";
                for (int len = 0; len < fetch.length - 1; ++len) {
                    if ("esavEsac".equals(fetc + fetch[len])) {
                        fetc = fetc + fetch[len] + ".";
                        str = "esac.";
                    } else {
                        detachedCriteria.createAlias(fetc + fetch[len], "ord" + len);
                        fetc = fetc + fetch[len] + ".";
                        str = "ord" + len + ".";
                    }
                }

                orde = str + pram[0].substring(pram[0].lastIndexOf(".") + 1);
            }
            if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up")))
                detachedCriteria.addOrder(Order.asc(orde));
            else
                detachedCriteria.addOrder(Order.desc(orde));
        }
    }

    public Empsalaryacctversion getAcctVersionByAcctId(String acctId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctversion.class);
        detachedCriteria.add(Restrictions.isNull("esavValidTo"));
        detachedCriteria.add(Restrictions.eq("esavEsac.id", acctId));
        List versions = this.empsalaryacctDao.findByCriteria(detachedCriteria);
        if (versions.isEmpty()) {
            return null;
        }
        return (Empsalaryacctversion) versions.get(0);
    }

    public IEmpSalaryAcctDao getEmpsalaryacctDao() {
        return this.empsalaryacctDao;
    }

    public void setEmpsalaryacctDao(IEmpSalaryAcctDao empsalaryacctDao) {
        this.empsalaryacctDao = empsalaryacctDao;
    }

    public IEmpSalaryAcctversionDao getEmpsalaryacctversionDao() {
        return this.empsalaryacctversionDao;
    }

    public void setEmpsalaryacctversionDao(IEmpSalaryAcctversionDao empsalaryacctversionDao) {
        this.empsalaryacctversionDao = empsalaryacctversionDao;
    }

    public List searchAllEmpsalaryacct() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacct.class);
        return this.empsalaryacctDao.findByCriteria(detachedCriteria);
    }

    public List<Empsalaryacctversion> searchAllEmpsalaryacctVersionInUse() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctversion.class);
        detachedCriteria.add(Restrictions.isNull("esavValidTo"));
        detachedCriteria.setFetchMode("esavEsac", FetchMode.JOIN);
        return this.empsalaryacctDao.findByCriteria(detachedCriteria);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.EmpSalaryAcctBoImpl JD-Core Version: 0.5.4
 */