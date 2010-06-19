package com.hr.profile.bo;

import com.hr.base.FileOperate;
import com.hr.base.Status;
import com.hr.profile.dao.IEmpContractDao;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Employee;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpContractBoImpl implements IEmpContractBo, Status {
    IEmpContractDao empContractDao;

    public boolean saveContract(Empcontract contract) {
        try {
            this.empContractDao.saveObject(contract);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Empcontract> searchByEmpNo(String empNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empcontract.class);
        detachedCriteria.setFetchMode(Empcontract.PROP_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.setFetchMode(Empcontract.PROP_CONTRACT_TYPE, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Empcontract.PROP_EMPLOYEE + "." + Employee.PROP_ID,
                                             empNo));
        detachedCriteria.addOrder(Order.desc(Empcontract.PROP_ECT_START_DATE));
        List result = this.empContractDao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Empcontract> findContract(DetachedCriteria dc, Pager page, String searchOrExport) {
        if ((page == null) || ("export".equals(searchOrExport))) {
            return this.empContractDao.findByCriteria(dc);
        }

        page.splitPage(dc);
        return this.empContractDao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public List<Employee> findContractByEmp(DetachedCriteria dc, Pager page, String searchOrExport) {
        if ((page == null) || ("export".equals(searchOrExport))) {
            return this.empContractDao.findByCriteria(dc);
        }

        page.splitPage(dc);
        return this.empContractDao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public Empcontract getById(String contractId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empcontract.class);
        detachedCriteria.setFetchMode(Empcontract.PROP_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.setFetchMode(Empcontract.PROP_CONTRACT_TYPE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions.eq(Empcontract.PROP_ECT_ID, contractId));
        List result = this.empContractDao.findByCriteria(detachedCriteria);
        if (result.size() == 0) {
            return null;
        }
        return (Empcontract) result.get(0);
    }

    public String insert(Empcontract empContract) {
        String error = "";
        Employee e = (Employee) this.empContractDao.loadObject(Employee.class, empContract
                .getEmployee().getId(), new String[] { "contract" }, new boolean[0]);
        Empcontract old = e.getContract();
        if (old != null) {
            Date newStartDate = empContract.getEctStartDate();

            if (!newStartDate.after(old.getEctStartDate())) {
                error = old.getEmployee().getEmpName() + "在"
                        + DateUtil.formatDate(old.getEctStartDate()) + "签署前一份合同，新合同起始日期必须晚于此日期";

                return error;
            }
            old.setEctLastChangeBy(empContract.getEctLastChangeBy());
            old.setEctLastChangeTime(empContract.getEctLastChangeTime());
            old.setEctStatus("2");

            this.empContractDao.updateObject(old);
        }
        this.empContractDao.saveObject(empContract);
        return error;
    }

    public void delete(String ectId) {
        String hql = "delete from Empcontract where ectId='" + ectId + "'";
        this.empContractDao.exeHql(hql);
    }

    public String update(Empcontract empContract, String ContractId) {
        Empcontract oldContract = getById(ContractId);
        if (empContract.getEctAttatchment() != null) {
            if (oldContract != null) {
                FileOperate.deleteFile("sys.profile.file.path", oldContract.getEctAttatchment());
            }
            oldContract.setEctAttatchment(empContract.getEctAttatchment());
        } else if (oldContract != null) {
            empContract.setEctAttatchment(oldContract.getEctAttatchment());
        }

        String error = "";
        Empcontract old = getLastContract(ContractId, empContract.getEmployee().getId());
        if ((old != null) && (!old.getEctStartDate().before(empContract.getEctStartDate()))) {
            error = oldContract.getEmployee().getEmpName() + "在"
                    + DateUtil.formatDate(old.getEctStartDate()) + "签署前一份合同，新合同起始日期必须晚于此日期";

            return error;
        }

        oldContract.setEctNo(empContract.getEctNo());
        oldContract.setEctStartDate(empContract.getEctStartDate());
        oldContract.setEctEndDate(empContract.getEctEndDate());
        oldContract.setEtcExpire(empContract.getEtcExpire());
        oldContract.setEctComments(empContract.getEctComments());
        oldContract.setEctStatus(empContract.getEctStatus());
        oldContract.setEmployee(empContract.getEmployee());
        oldContract.setContractType(empContract.getContractType());
        oldContract.setEctLastChangeBy(empContract.getEctLastChangeBy());
        oldContract.setEctLastChangeTime(empContract.getEctLastChangeTime());

        this.empContractDao.saveOrupdate(oldContract);
        return error;
    }

    private Empcontract getLastContract(String id, String empId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empcontract.class);
        detachedCriteria.add(Restrictions.ne("ectId", id));
        detachedCriteria.add(Restrictions.eq("employee.id", empId));
        detachedCriteria.addOrder(Order.desc("ectStartDate"));
        List list = this.empContractDao.findByCriteria(detachedCriteria);
        if ((list != null) && (list.size() > 0)) {
            return (Empcontract) list.get(0);
        }
        return null;
    }

    public boolean deleteAttach(String contractId, String fileName) {
        try {
            FileOperate.deleteFile("sys.profile.file.path", fileName);
            String hql = "update Empcontract set ectAttatchment=null where ectId='" + contractId
                    + "'";
            this.empContractDao.exeHql(hql);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public void checkOrderMethod(DetachedCriteria detachedCriteria, String order) {
        if ((order != null) && (order.trim().length() > 1) && (order.indexOf('-') != -1)) {
            String[] pram = order.split("-");
            if ((pram[0] == null) || (pram[0].length() < 1)) {
                detachedCriteria.addOrder(Order.desc(order));
            }
            String orde = pram[0];
            if ((orde != null) && (!"empJoinDate".equals(orde))) {
                if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up")))
                    detachedCriteria.addOrder(Order.asc(orde));
                else
                    detachedCriteria.addOrder(Order.desc(orde));
            } else if ("empJoinDate".equals(orde)) {
                if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up")))
                    detachedCriteria.addOrder(Order.desc(orde));
                else {
                    detachedCriteria.addOrder(Order.asc(orde));
                }
            }
        }
        detachedCriteria.addOrder(Order.desc(Employee.PROP_EMP_CONTRACT + "."
                + Empcontract.PROP_ECT_END_DATE));
        detachedCriteria.addOrder(Order.asc("empDistinctNo"));
    }

    public List<Empcontract> getContractListByEmp(Employee emp) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empcontract.class);
        detachedCriteria.add(Restrictions.eq(Empcontract.PROP_EMPLOYEE, emp));
        detachedCriteria.addOrder(Order.asc(Empcontract.PROP_ECT_START_DATE));
        return this.empContractDao.findByCriteria(detachedCriteria);
    }

    public int hasConflictInTime(Empcontract contract) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empcontract.class);
        detachedCriteria.add(Restrictions.eq(Empcontract.PROP_EMPLOYEE, contract.getEmployee()));
        detachedCriteria.add(Restrictions.eq(Empcontract.PROP_ETC_EXPIRE, contract.getEtcExpire()));
        detachedCriteria.add(Restrictions.eq(Empcontract.PROP_ECT_START_DATE, contract
                .getEctStartDate()));
        if (contract.getEctEndDate() != null) {
            detachedCriteria.add(Restrictions.eq(Empcontract.PROP_ECT_END_DATE, contract
                    .getEctEndDate()));
        }
        int num = this.empContractDao.findRowCountByCriteria(detachedCriteria);
        if (num == 1) {
            return 1;
        }

        detachedCriteria = null;
        detachedCriteria = DetachedCriteria.forClass(Empcontract.class);
        detachedCriteria.add(Restrictions.eq(Empcontract.PROP_EMPLOYEE, contract.getEmployee()));
        if (contract.getEtcExpire().intValue() == 1) {
            detachedCriteria.add(Restrictions.or(Restrictions.eq(Empcontract.PROP_ETC_EXPIRE,
                                                                 Integer.valueOf(1)), Restrictions
                    .gt(Empcontract.PROP_ECT_END_DATE, contract.getEctStartDate())));
        } else {
            detachedCriteria.add(Restrictions.and(Restrictions.eq(Empcontract.PROP_ETC_EXPIRE,
                                                                  Integer.valueOf(1)), Restrictions
                    .lt(Empcontract.PROP_ECT_START_DATE, contract.getEctEndDate())));
            detachedCriteria.add(Restrictions.and(Restrictions.eq(Empcontract.PROP_ETC_EXPIRE,
                                                                  Integer.valueOf(0)), Restrictions
                    .or(Restrictions.and(Restrictions.ge(Empcontract.PROP_ECT_START_DATE, contract
                            .getEctStartDate()), Restrictions.le(Empcontract.PROP_ECT_END_DATE,
                                                                 contract.getEctEndDate())),
                        Restrictions.or(Restrictions.and(Restrictions
                                .le(Empcontract.PROP_ECT_START_DATE, contract.getEctStartDate()),
                                                         Restrictions
                                                                 .gt(Empcontract.PROP_ECT_END_DATE,
                                                                     contract.getEctStartDate())),
                                        Restrictions.and(Restrictions
                                                .lt(Empcontract.PROP_ECT_START_DATE, contract
                                                        .getEctEndDate()), Restrictions
                                                .ge(Empcontract.PROP_ECT_END_DATE, contract
                                                        .getEctEndDate()))))));
        }

        num = this.empContractDao.findRowCountByCriteria(detachedCriteria);
        if (num > 0) {
            return -1;
        }
        return 0;
    }

    public boolean insertList(List<Empcontract> conList) {
        if (conList.size() > 0) {
            try {
                this.empContractDao.saveOrupdate(conList);
                Employee employee = ((Empcontract) conList.get(0)).getEmployee();
                this.empContractDao.saveOrupdate(employee);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public IEmpContractDao getEmpContractDao() {
        return this.empContractDao;
    }

    public void setEmpContractDao(IEmpContractDao empContractDao) {
        this.empContractDao = empContractDao;
    }

    public void updateObj(Object obj) {
        this.empContractDao.updateObject(obj);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.EmpContractBoImpl JD-Core Version: 0.5.4
 */