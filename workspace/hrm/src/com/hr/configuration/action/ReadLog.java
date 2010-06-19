package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.base.Status;
import com.hr.base.UsersAuthority;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Syslog;
import com.hr.examin.bo.interfaces.IWorkFlowBO;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.bo.IHasAuthBO;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

public class ReadLog extends BaseAction implements Status {
    IEmployeeBo empBo;
    IWorkFlowBO workflowBo;
    List<String> Fathers;
    String empNo;
    String flowType;

    public ReadLog() {
        this.empBo = ((IEmployeeBo) getBean("empBo"));

        this.workflowBo = ((IWorkFlowBO) getBean("workFlowBO"));
        this.Fathers = new ArrayList();

        this.empNo = "";
        this.flowType = "leaverequest";
    }

    public List getLogs(String ID, String tableName, HttpSession session) {
        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        List logList = logBO.getLogs(tableName, ID);
        if (logList.size() > 0) {
            Syslog tmpLog = (Syslog) logList.get(0);

            this.empNo = tmpLog.getSlEmpno();
        }

        if ("recruitplan".equals(tableName)) {
            if ((hasAuth(601)) || (hasAuthModuleCondition(611, 3))) {
                return logList;
            }
            if (hasAuthModuleCondition(611, 1)) {
                if ((this.empNo != null) && (this.empNo.equals(getCurrentEmpNo(session)))) {
                    return logList;
                }
                if (hasAuthModuleCondition(611, 2)) {
                    if (checkDeptInCharge(getCurrentEmp(), getDept(this.empNo))) {
                        return logList;
                    }
                    return null;
                }
            }
        }
        if ("tremployeeplan".equals(tableName)) {
            if ((hasAuth(301)) || (hasAuthModuleCondition(311, 3))) {
                return logList;
            }
            if (hasAuthModuleCondition(311, 1)) {
                if (this.empNo.equals(getCurrentEmpNo(session))) {
                    return logList;
                }
                if (hasAuthModuleCondition(311, 2)) {
                    if (checkDeptInCharge(getCurrentEmp(), getDept(this.empNo))) {
                        return logList;
                    }
                    return null;
                }
            }
        }
        Employee currentEmp = getCurrentEmp();
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");

        String[] fetch = { Employee.PROP_EMP_DEPT_NO };

        Employee emp = empBo.loadEmp(this.empNo, fetch);
        currentEmp = empBo.loadEmp(currentEmp.getId(), fetch);

        if ("leaverequest".equals(tableName)) {
            if ((hasAuth(401)) || (hasAuthModuleCondition(411, 3)))
                return logList;
            if (hasAuthModuleCondition(411, 1)) {
                if (this.empNo.equals(getCurrentEmpNo(session)))
                    return logList;
                if (hasAuthModuleCondition(411, 2)) {
                    if (empBo.checkEmpInCharge(currentEmp, emp)) {
                        return logList;
                    }

                    return null;
                }
            }
        }

        if ("overtimerequest".equals(tableName)) {
            if ((hasAuth(401)) || (hasAuthModuleCondition(411, 3))) {
                return logList;
            }
            if (hasAuthModuleCondition(411, 1)) {
                if (this.empNo.equals(getCurrentEmpNo(session))) {
                    return logList;
                }
                if (hasAuthModuleCondition(411, 2)) {
                    if (empBo.checkEmpInCharge(currentEmp, emp)) {
                        return logList;
                    }
                    return null;
                }
            }
        }
        if ("empcompaplan".equals(tableName)) {
            if ((hasAuth(201)) || (hasAuthModuleCondition(211, 3))) {
                return logList;
            }
            if (hasAuth(211)) {
                if (getFather(this.empNo).contains(getCurrentEmpNo(session))) {
                    return logList;
                }
                return null;
            }
        }
        return logList;
    }

    public String getCurrentEmpNo(HttpSession session) {
        try {
            return ((Userinfo) session.getAttribute("userinfo")).getId();
        } catch (Exception e) {
        }
        return null;
    }

    public Department getDept(String no) {
        String[] fetch = { "empDeptNo" };
        return this.empBo.loadEmp(no, fetch).getEmpDeptNo();
    }

    public List getFather(String empNo) {
        if ((this.empBo.loadEmp(empNo, null) == null)
                || (this.empBo.loadEmp(empNo, null).getEmpSupNo() == null)
                || (this.empBo.loadEmp(empNo, null).getEmpSupNo().getId().equals(empNo))) {
            return this.Fathers;
        }
        String tempStr = this.empBo.loadEmp(empNo, null).getEmpSupNo().getId();
        this.Fathers.add(tempStr);
        empNo = this.empBo.loadEmp(empNo, null).getEmpSupNo().getId();
        getFather(empNo);

        return this.Fathers;
    }

    public boolean hasAuth(int moduleId) {
        IHasAuthBO bo = (IHasAuthBO) SpringBeanFactory.getBean("hasService");
        UsersAuthority userAuthority = new UsersAuthority(String.valueOf(moduleId));
        boolean has = false;
        try {
            has = bo.hasDWRAuth(userAuthority);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return has;
    }

    public boolean hasAuthModuleCondition(int moduleId, int condition) {
        IHasAuthBO bo = (IHasAuthBO) SpringBeanFactory.getBean("hasService");
        UsersAuthority userAuthority = new UsersAuthority(String.valueOf(moduleId), String
                .valueOf(condition));
        boolean has = false;
        try {
            has = bo.hasDWRAuth(userAuthority);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return has;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.ReadLog JD-Core Version: 0.5.4
 */