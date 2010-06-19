package com.hr.profile.bo;

import com.hr.base.FileOperate;
import com.hr.profile.dao.IEmpAdditionalDao;
import com.hr.profile.domain.Employee;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class EmpAdditionalBoImpl implements IEmpAdditionalBo {
    private IEmpAdditionalDao empAdditionalDao;

    public void saveResume(String resume1, String resume2, String empId) {
        Employee emp = (Employee) this.empAdditionalDao.loadObject(Employee.class, empId, null,
                                                                   new boolean[0]);
        if (emp == null) {
            return;
        }
        if (StringUtils.isNotEmpty(resume1)) {
            FileOperate.deleteFile("sys.profile.file.path", emp.getEmpResume1());
            emp.setEmpResume1(resume1);
        }
        if (StringUtils.isNotEmpty(resume2)) {
            FileOperate.deleteFile("sys.profile.file.path", emp.getEmpResume2());
            emp.setEmpResume2(resume2);
        }
        this.empAdditionalDao.saveOrupdate(emp);
    }

    public String deleteResume(String empId, int resumeSeq) {
        if ((resumeSeq > 2) || (resumeSeq < 1)) {
            return "error";
        }
        String hql = "update Employee set empResume" + resumeSeq + "=null where id='" + empId + "'";
        this.empAdditionalDao.exeHql(hql);
        return "success";
    }

    public void save(Employee employee, String empId) {
        Employee old = (Employee) this.empAdditionalDao.loadObject(Employee.class, empId, null,
                                                                   new boolean[0]);
        if (old == null)
            return;
        try {
            for (int i = 1; i <= 16; ++i) {
                BeanUtils.setProperty(old, "empAdditional" + i, BeanUtils.getProperty(employee,
                                                                                      "empAdditional"
                                                                                              + i));
            }
            this.empAdditionalDao.saveOrupdate(old);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public IEmpAdditionalDao getEmpAdditionalDao() {
        return this.empAdditionalDao;
    }

    public void setEmpAdditionalDao(IEmpAdditionalDao empAdditionalDao) {
        this.empAdditionalDao = empAdditionalDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.EmpAdditionalBoImpl JD-Core Version: 0.5.4
 */