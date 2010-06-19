package com.hr.profile.bo;

import com.hr.configuration.domain.Department;
import com.hr.profile.dao.IEmpTransferDao;
import com.hr.profile.domain.EmpHistOrg;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emptransfer;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpTransferBoImpl implements IEmpTransferBo {
    IEmpTransferDao empTransferDao;

    public List<Emptransfer> findTransferByEmpNo(String empNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emptransfer.class);
        detachedCriteria.setFetchMode(Emptransfer.PROP_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.setFetchMode(Emptransfer.PROP_EFT_NEW_DEPT_NO, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Emptransfer.PROP_EFT_OLD_DEPT_NO, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Emptransfer.PROP_EFT_OLD_PB_NO, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Emptransfer.PROP_EFT_NEW_PB_NO, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Emptransfer.PROP_EMPLOYEE + "." + Employee.PROP_ID,
                                             empNo));
        detachedCriteria.addOrder(Order.desc(Emptransfer.PROP_EFT_CREATE_DATE));
        List result = this.empTransferDao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Emptransfer> findTransfer(DetachedCriteria dc, Pager page, String searchOrExport) {
        if ((page == null) || ("export".equals(searchOrExport))) {
            return this.empTransferDao.findByCriteria(dc);
        }

        page.splitPage(dc);
        return this.empTransferDao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public Emptransfer getById(String transferId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emptransfer.class);
        detachedCriteria.setFetchMode(Emptransfer.PROP_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.setFetchMode(Emptransfer.PROP_EFT_NEW_DEPT_NO, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Emptransfer.PROP_EFT_OLD_DEPT_NO, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Emptransfer.PROP_EFT_ID, transferId));
        List result = this.empTransferDao.findByCriteria(detachedCriteria);
        if (result.size() == 0) {
            return null;
        }
        return (Emptransfer) result.get(0);
    }

    public void delete(String etfId) {
        Emptransfer emptransfer = getById(etfId);
        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        Employee employee = employeeBo.loadEmp(emptransfer.getEmployee().getId(), null);
        employee.setEmpDeptNo(new Department(emptransfer.getEftOldDeptNo().getId()));
        employee.setEmpPbNo(new PositionBase(emptransfer.getEftOldPbId().getId()));
        employeeBo.updateEmp(employee, employee.getId());
        String hql = "delete from Emptransfer where eftId='" + etfId + "'";
        this.empTransferDao.exeHql(hql);
    }

    public void update(Emptransfer newEmpTransfer, String transferId) {
        Emptransfer emptransfer = getById(transferId);
        emptransfer.setEftTransferDate(newEmpTransfer.getEftTransferDate());
        emptransfer.setEftTransferType(newEmpTransfer.getEftTransferType());
        emptransfer.setEftReason(newEmpTransfer.getEftReason());
        emptransfer.setEftComments(newEmpTransfer.getEftComments());
        emptransfer.setEmployee(newEmpTransfer.getEmployee());
        emptransfer.setEftNewDeptNo(newEmpTransfer.getEftNewDeptNo());
        emptransfer.setEftLastChangeBy(newEmpTransfer.getEftLastChangeBy());
        emptransfer.setEftLastChangeTime(newEmpTransfer.getEftLastChangeTime());
        this.empTransferDao.saveOrupdate(emptransfer);
    }

    public List<Emptransfer> getEftByEmp(Employee emp) {
        DetachedCriteria dc = DetachedCriteria.forClass(Emptransfer.class);
        dc.createAlias(Emptransfer.PROP_EMPLOYEE, "emp", 1);
        dc.add(Restrictions.eq(Emptransfer.PROP_EMPLOYEE, emp));
        dc.addOrder(Order.asc(Emptransfer.PROP_EFT_TRANSFER_DATE));
        return this.empTransferDao.findByCriteria(dc);
    }

    public boolean saveImportList(List<Emptransfer> transList, List<Employee> empList,
            List<Position> posList, List<EmpHistOrg> emphistorgList) {
        if (transList.size() == 0)
            return true;

        this.empTransferDao.saveOrupdate(transList);
        this.empTransferDao.saveOrupdate(empList);
        this.empTransferDao.saveOrupdate(posList);
        this.empTransferDao.saveOrupdate(emphistorgList);

        return true;
    }

    public IEmpTransferDao getEmpTransferDao() {
        return this.empTransferDao;
    }

    public void setEmpTransferDao(IEmpTransferDao empTransferDao) {
        this.empTransferDao = empTransferDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.EmpTransferBoImpl JD-Core Version: 0.5.4
 */