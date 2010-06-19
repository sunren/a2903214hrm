package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.ILocationBO;
import com.hr.spring.SpringBeanFactory;
import java.util.List;
import javax.servlet.http.HttpSession;

public class RegistUserInitAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List jobtitleList;
    private List emptypeList;
    private List locationList;
    private List departmentList;

    public RegistUserInitAction() {
        this.jobtitleList = null;

        this.emptypeList = null;

        this.locationList = null;

        this.departmentList = null;
    }

    public String execute() throws Exception {
        HttpSession session = getSession();
        session.setAttribute("loginModel", "multiple");
        session.setAttribute("clientNo", "00000001");

        IDepartmentBO departmentBO = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        this.departmentList = departmentBO.FindAllDepartment();
        IEmpTypeBO emptypeBo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        this.emptypeList = emptypeBo.FindAllEmpType();
        ILocationBO locationBO = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.locationList = locationBO.FindAllLocation();
        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.jobtitleList = jobTitleBo.FindAllJobTitle();
        return "success";
    }

    public List getDepartmentList() {
        return this.departmentList;
    }

    public void setDepartmentList(List departmentList) {
        this.departmentList = departmentList;
    }

    public List getEmptypeList() {
        return this.emptypeList;
    }

    public void setEmptypeList(List emptypeList) {
        this.emptypeList = emptypeList;
    }

    public List getJobtitleList() {
        return this.jobtitleList;
    }

    public void setJobtitleList(List jobtitleList) {
        this.jobtitleList = jobtitleList;
    }

    public List getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List locationList) {
        this.locationList = locationList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.RegistUserInitAction JD-Core Version: 0.5.4
 */