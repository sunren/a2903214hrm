package com.hr.profile.bo;

import com.hr.base.Status;
import com.hr.profile.dao.IEmpQuitDao;
import com.hr.profile.domain.EmpHistOrg;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpQuitBoImpl implements IEmpQuitBo, Status {
    IEmpQuitDao empQuitDao;

    public List<Empquit> searchByEmpNo(String empNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empquit.class);
        detachedCriteria.setFetchMode(Empquit.PROP_EMPLOYEE, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empquit.PROP_EQ_PERMISSION, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empquit.PROP_EQ_DEPT_NO, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empquit.PROP_EQ_PB_NO, FetchMode.JOIN);
        detachedCriteria
                .add(Restrictions.eq(Empquit.PROP_EMPLOYEE + "." + Employee.PROP_ID, empNo));
        detachedCriteria.addOrder(Order.desc(Empquit.PROP_EQ_CREATE_DATE));
        List result = this.empQuitDao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Employee> findEmpQuit(DetachedCriteria dc, Pager page, String searchOrExport) {
        if ((page == null) || ("export".equals(searchOrExport))) {
            return this.empQuitDao.findByCriteria(dc);
        }

        page.splitPage(dc);
        return this.empQuitDao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public List<Empquit> findQuitEmp(DetachedCriteria dc, Pager page, String searchOrExport) {
        if ((page == null) || ("export".equals(searchOrExport))) {
            return this.empQuitDao.findByCriteria(dc);
        }

        page.splitPage(dc);
        return this.empQuitDao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public Empquit getById(String quitId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empquit.class);
        detachedCriteria.setFetchMode(Empquit.PROP_EMPLOYEE, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empquit.PROP_EQ_PERMISSION, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Empquit.PROP_EQ_ID, quitId));
        List result = this.empQuitDao.findByCriteria(detachedCriteria);
        if (result.size() == 0) {
            return null;
        }
        return (Empquit) result.get(0);
    }

    public String saveEmpQuit(Empquit empQuit) {
        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        IEmpHistOrgBo emphistorgBo = (IEmpHistOrgBo) SpringBeanFactory.getBean("empHistOrgBo");
        Employee employee = employeeBo.loadEmp(empQuit.getEmployee().getId(), null);
        Date empTerDate = employee.getEmpTerminateDate();
        Integer status = Integer.valueOf(Integer.parseInt(empQuit.getEqType()));

        OrgDeptOperateHelper helper = new OrgDeptOperateHelper();
        Employee currentEmp = helper.getCurrentEmp();

        if (status.intValue() == 1) {
            Position pos = posBo.getPosById(empQuit.getPosition().getId(),
                                            new String[] { Position.PROP_POSITION_PB_ID });
            employee.setEmpStatus(Integer.valueOf(1));

            empQuit.setEqDeptNo(pos.getPositionPbId().getPbDeptId());
            empQuit.setEqPbNo(pos.getPositionPbId());
            this.empQuitDao.saveObject(empQuit);
            employee.setEmpTerminateDate(null);
            employee.setQuit(null);
            employee.setEmpDeptNo(pos.getPositionPbId().getPbDeptId());
            employee.setEmpPbNo(pos.getPositionPbId());
            employee.setEmpLastChangeTime(new Date());
            employee.setEmpLastChangeBy(currentEmp);
            this.empQuitDao.saveOrupdate(employee);

            String parentPosId = (pos.getPositionParentId() != null) ? pos.getPositionParentId()
                    .getId() : null;
            posBo.batchSetEmpPos(parentPosId, pos.getPositionPbId().getId(),
                                 new String[] { employee.getId() });

            emphistorgBo.saveEmpHistOrg(employee, employee.getEmpDeptNo(), employee.getEmpPbNo());
        } else {
            employee.setEmpStatus(Integer.valueOf(0));
            employee.setEmpTerminateDate(empQuit.getEqDate());

            empQuit.setEqDeptNo(employee.getEmpDeptNo());
            empQuit.setEqPbNo(employee.getEmpPbNo());
            this.empQuitDao.saveObject(empQuit);
            employee.setQuit(empQuit);
            employee.setEmpLastChangeTime(new Date());
            employee.setEmpLastChangeBy(currentEmp);
            this.empQuitDao.saveOrupdate(employee);

            Position pos = posBo.getPosByEmpNo(employee.getId(),
                                               new String[] { Position.PROP_POSITION_PB_ID });
            pos.setPositionEmpNo(null);
            this.empQuitDao.saveOrupdate(pos);

            EmpHistOrg latestHistOrg = emphistorgBo.getLatestEmphistorg(employee);

            if (latestHistOrg != null) {
                latestHistOrg.setEhoValidTo(new Date());
                latestHistOrg.setEhoIsLatest(new Integer(0));
                this.empQuitDao.saveOrupdate(latestHistOrg);
            }
        }

        return "success";
    }

    public String delete(String etfId) {
        Empquit empQuit = getById(etfId);
        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        Employee employee = employeeBo.loadEmp(empQuit.getEmployee().getId(), null);
        Integer empStatus = employee.getEmpStatus();
        Date empTerDate = employee.getEmpTerminateDate();
        Empquit q = null;
        if (Integer.parseInt(empQuit.getEqType()) == 1) {
            int direct = employeeBo.countDirectEmpNumbers(employee.getId(), new Integer(1));
            if (direct > 0) {
                return employee.getEmpName() + "存在下属或下属员工中有活动员工，状态不能改为\"离职\"";
            }
            employee.setEmpStatus(Integer.valueOf(0));
            employee.setEmpTerminateDate(new Date());
            String hql = "from Empquit where employee.id='" + employee.getId()
                    + "' and eqType<>1 order by eqLastChangeTime desc";
            List rs = this.empQuitDao.exeHqlList(hql);
            if (rs.size() > 0) {
                q = (Empquit) rs.get(0);
                employee.setQuit(q);
            }
        } else {
            employee.setEmpStatus(Integer.valueOf(1));
            employee.setEmpTerminateDate(null);
            employee.setQuit(null);
        }

        if (q != null) {
            employee.setEmpTerminateDate(q.getEqDate());
        }
        List errors = employeeBo.updateEmp(employee, employee.getId());
        if (errors.size() > 0) {
            employee.setEmpStatus(empStatus);
            employee.setEmpTerminateDate(empTerDate);
            return (String) errors.get(0);
        }
        String hql = "delete from Empquit where eqId='" + etfId + "'";
        this.empQuitDao.exeHql(hql);
        return null;
    }

    public void update(Empquit empQuit, String quitId) {
        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        Employee employee = employeeBo.loadEmp(empQuit.getEmployee().getId(), null);

        Integer status = Integer.valueOf(Integer.parseInt(empQuit.getEqType()));

        if (status.intValue() == 1)
            status = Integer.valueOf(1);
        else {
            status = Integer.valueOf(0);
        }
        employee.setEmpStatus(status);
        employee.setEmpTerminateDate(empQuit.getEqDate());
        employeeBo.updateEmp(employee, employee.getId());

        Empquit oldQuit = getById(quitId);
        oldQuit.setEmployee(empQuit.getEmployee());
        oldQuit.setEqDate(empQuit.getEqDate());
        oldQuit.setEqPermission(empQuit.getEqPermission());
        oldQuit.setEqReason(empQuit.getEqReason());
        oldQuit.setEqType(empQuit.getEqType());
        oldQuit.setErComments(empQuit.getErComments());
        this.empQuitDao.saveOrupdate(oldQuit);
    }

    public IEmpQuitDao getEmpQuitDao() {
        return this.empQuitDao;
    }

    public void setEmpQuitDao(IEmpQuitDao empQuitDao) {
        this.empQuitDao = empQuitDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.EmpQuitBoImpl JD-Core Version: 0.5.4
 */