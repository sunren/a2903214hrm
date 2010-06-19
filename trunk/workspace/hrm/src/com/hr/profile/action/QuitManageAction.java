package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IClientBO;
import com.hr.profile.bo.IEmpQuitBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class QuitManageAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private static final String AUTHMODULE = "911";
    private Employee emp;
    private Empquit empquit;
    private Employee qemp;
    private Employee quitemp;

    public String execute() throws Exception {
        if ((null == this.qemp) || (StringUtils.isEmpty(this.qemp.getIds()))) {
            addErrorInfo("请求的参数错误,请选择要复职的员工");
            return "success";
        }
        String[] ids = this.qemp.getIds().split(",");

        IClientBO clientLimit = (IClientBO) SpringBeanFactory.getBean("clientBo");

        String limitInfo = clientLimit.checkLimit("EMP", ids.length);
        if (!"success".equals(limitInfo)) {
            return "overLimitE";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee tmp = null;
        String empNo = getCurrentEmpNo();
        List errors = new ArrayList();
        IEmpQuitBo empQuitBo = (IEmpQuitBo) getBean("empQuitBo");
        for (int i = 0; i < ids.length; ++i) {
            tmp = empBo.loadEmp(ids[i].trim(), null);
            if ((null == tmp) || (tmp.getEmpStatus().intValue() != 0))
                continue;
            this.empquit.setEmployee(tmp);
            this.empquit.setEqDate(this.qemp.getEmpJoinDate());
            this.empquit.setEqCreateBy(empNo);
            this.empquit.setEqCreateDate(new Date());
            this.empquit.setEqLastChangeBy(empNo);
            this.empquit.setEqLastChangeTime(new Date());
            String msg = empQuitBo.saveEmpQuit(this.empquit);
            if (!"success".equalsIgnoreCase(msg)) {
                errors.add(msg);
            }

        }

        if (errors.isEmpty())
            addSuccessInfo("批量复职员工成功");
        else {
            addErrorInfo(errors);
        }
        return "success";
    }

    public String batchQuit() throws Exception {
        if ((null == this.qemp) || (StringUtils.isEmpty(this.qemp.getIds()))) {
            addErrorInfo("请求的参数错误,请选择要离职的员工");
            return "success";
        }
        String currentEmpNo = getCurrentEmpNo();
        String[] ids = this.qemp.getIds().split(",");
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee tmp = null;
        List errors = new ArrayList();
        List tmpList = null;
        IEmpQuitBo empQuitBo = (IEmpQuitBo) getBean("empQuitBo");
        UserBo userBo = (UserBo) getBean("userService");
        for (int i = 0; i < ids.length; ++i) {
            if (ids[i].equals(currentEmpNo)) {
                errors.add(getCurrentEmp().getEmpName() + "是您本人，状态不能改为\"离职\"");
            } else {
                tmp = empBo.loadEmp(ids[i].trim(), null);
                if ((null == tmp) || (tmp.getEmpStatus().intValue() != 1))
                    continue;
                Userinfo oldUserinfo = userBo.getUserById(tmp.getId());
                if (userBo.checkAuthModule(oldUserinfo, "911")) {
                    errors.add("员工" + tmp.getEmpName() + "为系统管理员，请到系统模块取消次该用户的管理员权限后离职");
                } else {
                    this.empquit.setEmployee(tmp);
                    this.empquit.setEqCreateBy(currentEmpNo);
                    this.empquit.setEqCreateDate(new Date());
                    this.empquit.setEqLastChangeBy(currentEmpNo);
                    this.empquit.setEqLastChangeTime(new Date());

                    String msg = empQuitBo.saveEmpQuit(this.empquit);
                    if (!"success".equalsIgnoreCase(msg)) {
                        errors.add(msg);
                    }
                }
            }
        }
        if (errors.isEmpty())
            addSuccessInfo("批量离职成功");
        else {
            addErrorInfo(errors);
        }
        this.qemp.setEmpStatus(Integer.valueOf(1));
        return "success";
    }

    public String batchDelete() throws Exception {
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Pager page = new Pager();
        page.setOperate("delete");
        List errors = empBo.batchUpdateOrDel(this.qemp, page, getCurrentEmpNo());
        if ((errors != null) && (errors.size() > 0))
            addErrorInfo(errors);
        else {
            addSuccessInfo("批量删除员工成功");
        }
        this.qemp.setEmpStatus(Integer.valueOf(1));
        return "success";
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public Empquit getEmpquit() {
        return this.empquit;
    }

    public void setEmpquit(Empquit empquit) {
        this.empquit = empquit;
    }

    public Employee getQemp() {
        return this.qemp;
    }

    public void setQemp(Employee qemp) {
        this.qemp = qemp;
    }

    public Employee getQuitemp() {
        return this.quitemp;
    }

    public void setQuitemp(Employee quitemp) {
        this.quitemp = quitemp;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.QuitManageAction JD-Core Version: 0.5.4
 */