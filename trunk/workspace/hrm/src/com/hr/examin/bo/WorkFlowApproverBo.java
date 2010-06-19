package com.hr.examin.bo;

import com.hr.examin.bo.interfaces.IWorkFlowApproverBo;
import com.hr.examin.dao.interfaces.IWorkFlowApproverDAO;
import com.hr.examin.domain.WorkFlowApprover;
import com.hr.profile.domain.Employee;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class WorkFlowApproverBo implements IWorkFlowApproverBo {
    private IWorkFlowApproverDAO workFlowApproverDAO;

    public List findItems(String clazz) {
        Class c = null;
        try {
            c = Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            return new ArrayList(0);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(c);
        return this.workFlowApproverDAO.findByCriteria(detachedCriteria);
    }

    public IWorkFlowApproverDAO getWorkFlowApproverDAO() {
        return this.workFlowApproverDAO;
    }

    public void setWorkFlowApproverDAO(IWorkFlowApproverDAO workFlowApproverDAO) {
        this.workFlowApproverDAO = workFlowApproverDAO;
    }

    public List findAll() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(WorkFlowApprover.class);
        return this.workFlowApproverDAO.findByCriteria(detachedCriteria);
    }

    public void deleteWorkFlowApproverById(String id) {
        this.workFlowApproverDAO.deleteObject(WorkFlowApprover.class, id);
    }

    public String addWorkFlowApprover(WorkFlowApprover w) {
        DetachedCriteria dc = DetachedCriteria.forClass(WorkFlowApprover.class);
        dc.add(Restrictions.eq("workFlowApproverId", w.getWorkFlowApproverId()));
        dc.add(Restrictions.eq("workFlowApproverType", w.getWorkFlowApproverType()));
        List exist = this.workFlowApproverDAO.findByCriteria(dc);
        if (exist.size() > 0) {
            return "该项目的审批流程已设置，不能重复设置";
        }
        this.workFlowApproverDAO.saveObject(w);
        return null;
    }

    public String updateWorkFlowApprover(WorkFlowApprover w) {
        WorkFlowApprover dbWorkFlow = (WorkFlowApprover) this.workFlowApproverDAO
                .loadObject(WorkFlowApprover.class, w.getId(), null, new boolean[0]);
        if (dbWorkFlow == null) {
            return "您要更新的数据不存在或已经被删除，请重试";
        }
        dbWorkFlow.setWorkFlowLimit(w.getWorkFlowLimit());
        dbWorkFlow.setWorkFlowApproverInd(w.getWorkFlowApproverInd());
        this.workFlowApproverDAO.updateObject(dbWorkFlow);
        return null;
    }

    public List getManagers() {
        String hql = "select distinct empSupNo.id from Employee";
        List sups = this.workFlowApproverDAO.exeHqlList(hql);
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        if ((sups != null) && (sups.size() > 0)) {
            dc.add(Restrictions.in("id", sups.toArray()));
        }
        dc.add(Restrictions.eq("empStatus", new Integer(1)));
        dc.addOrder(Order.asc("empName"));
        dc.setFetchMode("empDeptNo", FetchMode.JOIN);
        return this.workFlowApproverDAO.findByCriteria(dc);
    }

    public List<WorkFlowApprover> getAllWorkFlowApprover(String workFlowApproverId) {
        DetachedCriteria dc = DetachedCriteria.forClass(WorkFlowApprover.class);
        dc.add(Restrictions.eq("workFlowApproverId", workFlowApproverId));
        return this.workFlowApproverDAO.findByCriteria(dc);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.WorkFlowApproverBo JD-Core Version: 0.5.4
 */