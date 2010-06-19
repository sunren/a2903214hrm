package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Userinfo;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DeleteRecord extends BaseAction {
    private static final long serialVersionUID = -4234940898871087425L;
    private static final String AUTHMODULE = "911";
    private String id;
    private static final Logger logger = Logger.getLogger(DeleteRecord.class);

    public String delEmp() throws Exception {
        if (StringUtils.isEmpty(this.id))
            return "success";

        if (getCurrentEmpNo().equalsIgnoreCase(this.id)) {
            addErrorInfo("不能删除本人的基本资料！");
            return "success";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee delEmp = empBo.loadEmp(this.id, null);
        if (delEmp == null)
            return "success";

        UserBo userBo = (UserBo) getBean("userService");
        Userinfo oldUserinfo = userBo.getUserById(this.id);
        if (userBo.checkAuthModule(oldUserinfo, "911")) {
            addErrorInfo("删除失败，此用户为系统管理员，请到系统模块取消次用户的管理员权限后删除！");
            return "success";
        }

        List errors = null;
        String empName = delEmp.getEmpName();
        try {
            errors = empBo.deleteEmp(delEmp);
        } catch (Exception e) {
            e.printStackTrace();
            addErrorInfo("删除员工基本资料失败");
            return "success";
        }

        if ((errors != null) && (errors.size() > 0))
            addErrorInfo(errors);
        else {
            addSuccessInfo(empName + "的基本资料已被删除");
        }
        return "success";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.DeleteRecord JD-Core Version: 0.5.4
 */