package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IOrgheadsBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Orgheads;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class OrgHeadsDWR extends BaseAction {
    private static final String ERROR = "ERROR";
    private static final String EXISTED = "EXISTED";
    private static final String SUCC = "SUCC";
    private static final String FAIL = "FAIL";
    private List<Department> deptList;
    private List<Location> locationList;
    private List<Orgheads> orgHeads;
    IEmployeeBo empBo;

    public OrgHeadsDWR() {
        this.empBo = ((IEmployeeBo) getBean("empBo"));
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public List<Location> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Orgheads> getOrgHeads() {
        return this.orgHeads;
    }

    public void setOrgHeads(List<Orgheads> orgHeads) {
        this.orgHeads = orgHeads;
    }

    private boolean checkAuthForEmployee() {
        String flt = DWRUtil.checkAuth("profileConfig", "execute");

        return !"error".equalsIgnoreCase(flt);
    }

    public Orgheads addOrgheads(Orgheads orgheads) {
        if (checkAuthForEmployee()) {
            try {
                IOrgheadsBo orgheadsbo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");
                if (orgheadsbo.addOrgheads(orgheads)) {
                    return orgheads;
                }
                orgheads.setOrgheadsResponsibility("EXISTED");
                return orgheads;
            } catch (Exception e) {
                orgheads.setOrgheadsResponsibility("FAIL");
                return orgheads;
            }
        }
        orgheads.setOrgheadsResponsibility("NOAUTH");
        return orgheads;
    }

    public String updateOrgheads(Orgheads orgheads) {
        if (checkAuthForEmployee()) {
            try {
                IOrgheadsBo orgheadsbo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");
                if (orgheadsbo.updateOrgheads(orgheads)) {
                    return orgheads.getOrgheadsResponsibility();
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delOrgheads(String id) {
        if (checkAuthForEmployee()) {
            try {
                IOrgheadsBo orgheadsbo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");
                if (orgheadsbo.delOrgheads(Orgheads.class, id)) {
                    return "SUCC";
                }
                return "FAIL";
            } catch (Exception e) {
                return "ERROR";
            }
        }
        return "noauth";
    }

    public String execute() throws Exception {
        this.deptList = this.empBo.getObjects(Department.class, null);
        this.locationList = this.empBo.getObjects(Location.class, null);
        ILocationBO locationBo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        IDepartmentBO dept_BO = (IDepartmentBO) getBean("departmentBO");

        this.orgHeads = this.empBo.getObjects(Orgheads.class, null);
        for (Orgheads head : this.orgHeads) {
            String tmp = head.getOrgheadsResponsibility();
            if ("deptheader".equalsIgnoreCase(tmp)) {
                Department dept = dept_BO
                        .loadDepartmentByNo(head.getOrgheadsOrgNo(), new String[0]);
                head.setOrgName(dept.getDepartmentName());
            } else if ("braheader".equalsIgnoreCase(tmp)) {
                Location lo = locationBo.loadLocation(head.getOrgheadsOrgNo());
                head.setOrgName(lo.getLocationName());
            }
            Employee e = this.empBo.loadEmp(head.getOrgheadsEmpNo(), null);
            if ((null != e) && (null != e.getId())) {
                head.setEmpName(e.getEmpName());
            }
        }
        return "success";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.OrgHeadsDWR JD-Core Version: 0.5.4
 */