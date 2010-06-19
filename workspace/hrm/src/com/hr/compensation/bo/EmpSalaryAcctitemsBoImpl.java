package com.hr.compensation.bo;

import com.hr.compensation.dao.IEmpSalaryAcctitemsDao;
import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.profile.domain.Employee;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.ObjectUtils;

public class EmpSalaryAcctitemsBoImpl implements IEmpSalaryAcctitemsBo {
    private IEmpSalaryAcctitemsDao empsalaryacctitemsDao;

    public List<Empsalaryacctitems> getItemsByAcctversion(String acctversionId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctitems.class);
        detachedCriteria.setFetchMode(Empsalaryacctitems.PROP_ESAI_ESDD, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq("esaiEsav.id", acctversionId));
        detachedCriteria.addOrder(Order.asc(Empsalaryacctitems.PROP_ESAI_DATA_SEQ));
        return this.empsalaryacctitemsDao.findByCriteria(detachedCriteria);
    }

    public Map<String, List<Empsalaryacctitems>> getItemsByAcctversion(List<String> acctversionIds) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctitems.class);
        detachedCriteria.setFetchMode(Empsalaryacctitems.PROP_ESAI_ESDD, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.in("esaiEsav.id", acctversionIds));
        detachedCriteria.addOrder(Order.asc(Empsalaryacctitems.PROP_ESAI_DATA_SEQ));
        List<Empsalaryacctitems> acctItems = this.empsalaryacctitemsDao
                .findByCriteria(detachedCriteria);

        Map cacheMap = new HashMap();
        for (Empsalaryacctitems item : acctItems) {
            String key = item.getEsaiEsav().getId();
            List tmpList = (List) cacheMap.get(key);
            if (tmpList == null) {
                tmpList = new ArrayList();
            }
            tmpList.add(item);
            cacheMap.put(key, tmpList);
        }
        return cacheMap;
    }

    public List<Empsalaryacctitems> getAllItemsByEsavIds(List<String> acctversionIds) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctitems.class);
        detachedCriteria.createAlias("esaiEsav", "esaiEsav", 1);
        detachedCriteria.createAlias("esaiEsav.esavEsac", "esavEsac", 1);
        detachedCriteria.setFetchMode(Empsalaryacctitems.PROP_ESAI_ESDD, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.in("esaiEsav.id", acctversionIds));
        detachedCriteria.addOrder(Order.asc(Empsalaryacctitems.PROP_ESAI_DATA_SEQ));
        List acctItems = this.empsalaryacctitemsDao.findByCriteria(detachedCriteria);

        return acctItems;
    }

    public Map<String, List<Empsalaryacctitems>> getAcctItemsByPay(List<Empsalarypay> payList) {
        if ((payList == null) || (payList.size() == 0))
            return null;

        Set strBuffer = new TreeSet();
        for (Empsalarypay item : payList) {
            String key = item.getEspEsavId().getId();
            strBuffer.add(key);
        }
        List acctversionIds = new ArrayList();
        acctversionIds.addAll(strBuffer);
        if (acctversionIds.size() > 0) {
            return getItemsByAcctversion(acctversionIds);
        }
        return null;
    }

    public Map<String, List<Empsalaryacctitems>> getItemsByEmpbenefitplan(
            List<Empbenefitplan> ebpList) {
        Set esavIds = new TreeSet();
        String key = null;
        if (ebpList == null) {
            return null;
        }
        for (Empbenefitplan ebp : ebpList) {
            key = ebp.getEbpEsavId().getId();
            esavIds.add(key);
        }
        List acctversionIds = new ArrayList();
        acctversionIds.addAll(esavIds);
        if (acctversionIds.size() > 0) {
            return getItemsByAcctversion(acctversionIds);
        }
        return null;
    }

    public Map<String, List<Empsalaryacctitems>> getItemsBySalaryConf(
            List<Empsalaryconfig> salaryConfList) {
        Set strBuffer = new TreeSet();
        for (Empsalaryconfig item : salaryConfList) {
            String key = item.getEscEsavId().getId();
            strBuffer.add(key);
        }
        List acctversionIds = new ArrayList();
        acctversionIds.addAll(strBuffer);
        if (acctversionIds.size() > 0) {
            return getItemsByAcctversion(acctversionIds);
        }
        return null;
    }

    /** @deprecated */
    public Map<String, List<Empsalaryacctitems>> getItemsByEmployeeConf(List<Employee> EmpList) {
        Set strBuffer = new TreeSet();
        for (Employee item : EmpList) {
            if (item.getConfig() != null){
                String key = item.getConfig().getEscEsavId().getId();
                strBuffer.add(key);
            }
        }
        List acctversionIds = new ArrayList();
        acctversionIds.addAll(strBuffer);
        if (acctversionIds.size() > 0) {
            return getItemsByAcctversion(acctversionIds);
        }
        return null;
    }

    /** @deprecated */
    public boolean setAcctItemsConfig(List<Employee> empList) {
        Set strBuffer = new TreeSet();
        boolean result = true;
        for (Employee item : empList) {
            if (item.getConfig() == null) {
                result = false;
            }

            String key = item.getConfig().getEscEsavId().getId();
            strBuffer.add(key);
        }

        if (strBuffer.size() == 0)
            return false;

        List acctversionIds = new ArrayList();
        acctversionIds.addAll(strBuffer);
        Map acctItemsMap = getItemsByAcctversion(acctversionIds);
        for (Employee item : empList) {
            Empsalaryconfig config = item.getConfig();
            if (config != null)
                config.setAcctItems((List) acctItemsMap.get(config.getEscEsavId().getId()));
        }
        return result;
    }

    /** @deprecated */
    public boolean setAcctItemsPay(List<Empsalarypay> payList) {
        if ((payList == null) || (payList.size() == 0))
            return false;

        Map acctItemsMap = getAcctItemsByPay(payList);

        for (Empsalarypay pay : payList) {
            pay.setAcctItems((List) acctItemsMap.get(pay.getEspEsavId().getId()));
        }
        return true;
    }

    public boolean setAcctItemsPay(Empsalarypay[] payList) {
        if (ObjectUtils.isEmpty(payList))
            return false;

        Map acctItemsMap = getAcctItemsByPay(Arrays.asList(payList));
        if ((acctItemsMap == null) || (acctItemsMap.size() == 0))
            return false;
        for (Empsalarypay pay : payList) {
            pay.setAcctItems((List) acctItemsMap.get(pay.getEspEsavId().getId()));
        }
        return true;
    }

    public boolean setAcctItemsConfig(Empsalaryconfig[] configList) {
        if (ObjectUtils.isEmpty(configList))
            return false;

        Map acctItemsMap = getItemsBySalaryConf(Arrays.asList(configList));
        if ((acctItemsMap == null) || (acctItemsMap.size() == 0))
            return false;
        for (Empsalaryconfig config : configList) {
            config.setAcctItems((List) acctItemsMap.get(config.getEscEsavId().getId()));
        }
        return true;
    }

    public Map<String, List<Empsalaryacctitems>> getItemsBySalaryAdj(List<Empsalaryadj> salaryAdjs) {
        Set strBuffer = new TreeSet();
        for (Empsalaryadj item : salaryAdjs) {
            String key = item.getEsaEsavIdCur().getId();
            strBuffer.add(key);
            key = item.getEsaEsavIdPro().getId();
            strBuffer.add(key);
        }
        List acctversionIds = new ArrayList();
        acctversionIds.addAll(strBuffer);
        if (acctversionIds.size() > 0) {
            return getItemsByAcctversion(acctversionIds);
        }
        return null;
    }

    public List<Empsalaryacctitems> findAll() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctitems.class);
        detachedCriteria.addOrder(Order.asc(Empsalaryacctitems.PROP_ESAI_DATA_SEQ));
        return this.empsalaryacctitemsDao.findByCriteria(detachedCriteria);
    }

    public Boolean deleteEmpsalaryacctitems(String esavId) {
        try {
            String hql = "delete Empsalaryacctitems as esai where esai.esaiEsav.id='" + esavId
                    + "'";
            this.empsalaryacctitemsDao.exeHql(hql);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.valueOf(false);
    }

    public Boolean insertEmpsalaryacctitems(Empsalaryacctitems empsalaryacctitems) {
        try {
            this.empsalaryacctitemsDao.saveObject(empsalaryacctitems);
            return Boolean.valueOf(true);
        } catch (Exception e) {
        }
        return Boolean.valueOf(false);
    }

    public <T> boolean saveOrupdate(Collection<T> objs) {
        try {
            this.empsalaryacctitemsDao.saveOrupdate(objs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Empsalaryacctitems loadObject(String esaID, String[] fetch) {
        String hql = "from Empsalaryacctitems as esai where id='" + esaID + "'";
        return (Empsalaryacctitems) this.empsalaryacctitemsDao.loadObjectByEsaId(hql);
    }

    public List exeHqlList(String hql) {
        return this.empsalaryacctitemsDao.exeHqlList(hql);
    }

    public List getObjects(Class clas, String[] fetch) {
        return this.empsalaryacctitemsDao.getObjects(clas, fetch);
    }

    public IEmpSalaryAcctitemsDao getEmpsalaryacctitemsDao() {
        return this.empsalaryacctitemsDao;
    }

    public void setEmpsalaryacctitemsDao(IEmpSalaryAcctitemsDao empsalaryacctitemsDao) {
        this.empsalaryacctitemsDao = empsalaryacctitemsDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.EmpSalaryAcctitemsBoImpl JD-Core Version: 0.5.4
 */