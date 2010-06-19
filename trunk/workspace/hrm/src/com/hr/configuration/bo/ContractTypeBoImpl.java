package com.hr.configuration.bo;

import com.hr.configuration.dao.IContractTypeDao;
import com.hr.configuration.domain.ContractType;
import com.hr.profile.domain.Empcontract;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ContractTypeBoImpl implements IContractTypeBo {
    private IContractTypeDao contractDao;

    public List FindAllEcptype() {
        DetachedCriteria dc = DetachedCriteria.forClass(ContractType.class);
        dc.addOrder(Order.asc("ectSortId"));
        return this.contractDao.findByCriteria(dc);
    }

    public void addContractType(ContractType contractType) {
        this.contractDao.saveObject(contractType);
    }

    public String delContractType(String id) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empcontract.class);
        detachedCriteria.add(Restrictions.eq("contractType.id", id));
        List list = this.contractDao.findByCriteria(detachedCriteria);
        if (list.size() == 0) {
            this.contractDao.deleteObject(ContractType.class, id);
            return "";
        }
        return "该合同类型正在被使用，不能删除！";
    }

    public void updateContractType(ContractType contractType) {
        this.contractDao.updateObject(contractType);
    }

    public IContractTypeDao getContractDao() {
        return this.contractDao;
    }

    public void setContractDao(IContractTypeDao contractDao) {
        this.contractDao = contractDao;
    }

    public List findByName(String name) {
        DetachedCriteria dc = DetachedCriteria.forClass(ContractType.class);
        dc.add(Restrictions.eq("ectName", name));
        return this.contractDao.findByCriteria(dc);
    }

    public void saveContractTypeIdByBatch(String[] ids) {
        if (ids == null)
            return;
        int length = ids.length;
        int sortId = 1;
        for (int i = 0; i < length; ++i) {
            this.contractDao.exeHql("update ContractType set ectSortId=" + sortId + " where id ='"
                    + ids[i] + "'");
            ++sortId;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.ContractTypeBoImpl JD-Core Version: 0.5.4
 */