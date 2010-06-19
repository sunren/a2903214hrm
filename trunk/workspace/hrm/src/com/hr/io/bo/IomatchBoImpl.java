package com.hr.io.bo;

import com.hr.io.dao.IomatchDaoImpl;
import com.hr.io.domain.Iomatch;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class IomatchBoImpl implements IIomatchBo {
    private IomatchDaoImpl iomatchdao;

    public String deleteIomatch(Iomatch iomatch) {
        return null;
    }

    public List getIomatches(String iodefId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Iomatch.class);
        detachedCriteria.add(Restrictions.eq("iodef.ioId", iodefId));
        detachedCriteria.addOrder(Order.asc("iomatchSortId"));
        List result = this.iomatchdao.findByCriteria(detachedCriteria);
        return result;
    }

    public String insertIomatch(Iomatch iomatch) {
        return null;
    }

    public String updateIomatch(Iomatch iomatch) {
        return null;
    }

    public IomatchDaoImpl getIomatchdao() {
        return this.iomatchdao;
    }

    public void setIomatchdao(IomatchDaoImpl iomatchdao) {
        this.iomatchdao = iomatchdao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.bo.IomatchBoImpl JD-Core Version: 0.5.4
 */