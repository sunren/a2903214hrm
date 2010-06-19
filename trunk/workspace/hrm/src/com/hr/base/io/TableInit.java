package com.hr.base.io;

import com.hr.compensation.bo.IEmpSalaryAcctBo;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.IJobgradeBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.bo.IStatBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.configuration.domain.StatusconfPK;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class TableInit {
    private static final long serialVersionUID = 1L;
    private VirtualHashTable empStatusHashtable = null;

    private VirtualHashTable empTypeHashtable = null;

    private VirtualHashTable empsalaryacctHashtable = null;

    private VirtualHashTable groupHashtable = null;

    private VirtualHashTable jobTitleHashtable = null;

    private VirtualHashTable departmentHashtable = null;

    private VirtualHashTable locationHashtable = null;

    private VirtualHashTable pbHashtable = null;

    private VirtualHashTable escJobgradeHashtable = null;

    public VirtualHashTable getVirtualList(String type) {
        if (type.equals("Department"))
            return getDepartmentHashtable();
        if (type.equals("EmpType"))
            return getEmpTypeHashtable();
        if (type.equals("Status"))
            return getEmpStatusHashtable();
        if (type.equals("PositionBase"))
            return getPbHashtable();
        if (type.equals("Location"))
            return getLocationHashtable();
        if (type.equals("EscJobgrade"))
            return getEscJobgradeHashtable();
        if (type.equals("Empsalaryacct"))
            return getEmpsalaryacctHashtable();
        if (type.equals("JobTitle")) {
            return getJobTitleHashtable();
        }
        return null;
    }

    public VirtualHashTable getDepartmentHashtable() {
        if (this.departmentHashtable == null) {
            VirtualHashTable departmentHashtable = new VirtualHashTable();
            IDepartmentBO departmentBO = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
            List departmentList = departmentBO.FindEnabledDepartment();
            int departmentListSize = departmentList.size();
            Department department = null;
            for (int i = 0; i < departmentListSize; ++i) {
                department = (Department) departmentList.get(i);
                departmentHashtable.put(department.getId(), department.getDepartmentName());
            }
            this.departmentHashtable = departmentHashtable;
        }
        return this.departmentHashtable;
    }

    public VirtualHashTable getEmpStatusHashtable() {
        if (this.empStatusHashtable == null) {
            VirtualHashTable empStatusHashtable = new VirtualHashTable();
            IStatBO statBO = (IStatBO) SpringBeanFactory.getBean("statBO");
            List statBOList = statBO.getAllTableStatus("employee");
            int statBOListSize = statBOList.size();
            Statusconf statusconf = null;
            for (int i = 0; i < statBOListSize; ++i) {
                statusconf = (Statusconf) statBOList.get(i);
                empStatusHashtable.put(Integer.toString(statusconf.getId().getStatusconfNo()
                        .intValue()), statusconf.getStatusconfDesc());
            }
            this.empStatusHashtable = empStatusHashtable;
        }
        return this.empStatusHashtable;
    }

    public VirtualHashTable getEmpTypeHashtable() {
        if (this.empTypeHashtable == null) {
            VirtualHashTable empTypeHashtable = new VirtualHashTable();
            IEmpTypeBO empTypeBO = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
            List emptypeList = empTypeBO.FindEnabledEmpType();
            int emptypeListSize = emptypeList.size();
            Emptype emptype = null;
            for (int i = 0; i < emptypeListSize; ++i) {
                emptype = (Emptype) emptypeList.get(i);
                empTypeHashtable.put(emptype.getId(), emptype.getEmptypeName());
            }
            this.empTypeHashtable = empTypeHashtable;
        }
        return this.empTypeHashtable;
    }

    public VirtualHashTable getPbHashtable() {
        if (this.pbHashtable == null) {
            VirtualHashTable pbHashtable = new VirtualHashTable();
            IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
            List PositionBaseList = pbBo.findAllActivePb();
            int pbListSize = PositionBaseList.size();
            PositionBase pb = null;
            for (int i = 0; i < pbListSize; ++i) {
                pb = (PositionBase) PositionBaseList.get(i);
                pbHashtable.put(pb.getId(), pb.getPbName());
            }
            this.pbHashtable = pbHashtable;
        }
        return this.pbHashtable;
    }

    public VirtualHashTable getLocationHashtable() {
        if (this.locationHashtable == null) {
            VirtualHashTable locationHashtable = new VirtualHashTable();
            ILocationBO locationBO = (ILocationBO) SpringBeanFactory.getBean("locationBO");
            List locationList = locationBO.FindEnabledLocation();
            int locationListSize = locationList.size();
            Location location = null;
            for (int i = 0; i < locationListSize; ++i) {
                location = (Location) locationList.get(i);
                locationHashtable.put(location.getId(), location.getLocationName());
            }
            this.locationHashtable = locationHashtable;
        }
        return this.locationHashtable;
    }

    public VirtualHashTable getEscJobgradeHashtable() {
        if (this.escJobgradeHashtable == null) {
            IJobgradeBO jobgradeBO = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
            List jobgradeBOList = jobgradeBO.FindAllJobgrade();
            int jobgradeBOListSize = jobgradeBOList.size();
            Jobgrade jobgrade = null;
            VirtualHashTable escJobgradeHashtable = new VirtualHashTable();
            for (int i = 0; i < jobgradeBOListSize; ++i) {
                jobgrade = (Jobgrade) jobgradeBOList.get(i);
                escJobgradeHashtable.put(jobgrade.getId().trim(), jobgrade.getJobGradeName());
            }
            this.escJobgradeHashtable = escJobgradeHashtable;
        }
        return this.escJobgradeHashtable;
    }

    public VirtualHashTable getEmpsalaryacctHashtable() {
        if (this.empsalaryacctHashtable == null) {
            VirtualHashTable empsalaryacctHashtable = new VirtualHashTable();
            IEmpSalaryAcctBo esaBo = (IEmpSalaryAcctBo) SpringBeanFactory
                    .getBean("empsalaryacctBo");
            List salaryAcctList = esaBo.searchAllEmpsalaryacct();
            int salaryAcctListSize = salaryAcctList.size();
            Empsalaryacct empSalaryAcct = null;
            for (int i = 0; i < salaryAcctListSize; ++i) {
                empSalaryAcct = (Empsalaryacct) salaryAcctList.get(i);
                empsalaryacctHashtable.put(empSalaryAcct.getId(), empSalaryAcct.getEsacName());
            }
            this.empsalaryacctHashtable = empsalaryacctHashtable;
        }
        return this.empsalaryacctHashtable;
    }

    public VirtualHashTable getJobTitleHashtable() {
        if (this.jobTitleHashtable == null) {
            VirtualHashTable jobTitleHashtable = new VirtualHashTable();
            IJobTitleBo esaBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
            List jobTitleList = esaBo.FindEnabledJobTitle();
            int salaryAcctListSize = jobTitleList.size();
            JobTitle jobTitle = null;
            for (int i = 0; i < salaryAcctListSize; ++i) {
                jobTitle = (JobTitle) jobTitleList.get(i);
                jobTitleHashtable.put(jobTitle.getJobtitleNo(), jobTitle.getJobtitleName());
            }
            this.jobTitleHashtable = jobTitleHashtable;
        }
        return this.jobTitleHashtable;
    }

    public void setBusinessunitHashtable(VirtualHashTable businessunitHashtable) {
        this.pbHashtable = businessunitHashtable;
    }

    public void setDepartmentHashtable(VirtualHashTable departmentHashtable) {
        this.departmentHashtable = departmentHashtable;
    }

    public void setEmpsalaryacctHashtable(VirtualHashTable empsalaryacctHashtable) {
        this.empsalaryacctHashtable = empsalaryacctHashtable;
    }

    public void setEmpStatusHashtable(VirtualHashTable empStatusHashtable) {
        this.empStatusHashtable = empStatusHashtable;
    }

    public void setEmpTypeHashtable(VirtualHashTable empTypeHashtable) {
        this.empTypeHashtable = empTypeHashtable;
    }

    public void setEscJobgradeHashtable(VirtualHashTable escJobgradeHashtable) {
        this.escJobgradeHashtable = escJobgradeHashtable;
    }

    public void setLocationHashtable(VirtualHashTable locationHashtable) {
        this.locationHashtable = locationHashtable;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.base.io.TableInit JD-Core Version: 0.5.4
 */