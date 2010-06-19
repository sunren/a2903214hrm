package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class UpdateBasicInfo extends BaseAction implements Constants {
    private static final long serialVersionUID = -5442588725L;
    private static final Logger logger = Logger.getLogger(UpdateBasicInfo.class);
    private String empNo;

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("测试：execute() - CreateInfo.action");
        }

        if (this.empNo == null) {
            setEmpNo(getCurrentEmpNo());
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        String[] fetch = { "empSupNo", "empName" };
        Employee emp = empBo.loadEmp(this.empNo.trim(), fetch);
        if ((emp == null) || (emp.getId() == null)) {
            return "params_error";
        }

        getSession().setAttribute("curr_oper_no", this.empNo);
        if (logger.isDebugEnabled()) {
            logger.debug("execute() -CreateInfo- end");
        }
        return "success";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.UpdateBasicInfo JD-Core Version: 0.5.4
 */