package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.SysConfigManager;
import java.net.URLDecoder;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class MyBasicInfo extends BaseAction implements Constants {
    private static final long serialVersionUID = -4234940898871087425L;
    SysConfigManager dbConfigManager;
    String exShiftEnable;
    private static final Logger logger = Logger.getLogger(MyBasicInfo.class);
    private String empNo;
    private String empName;
    private String newName;
    private String createFlag;

    public MyBasicInfo() {
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String execute() throws Exception {
        if ((this.empName != null) && (this.empName.trim().length() > 0))
            this.empName = new String(this.empName.getBytes("ISO8859_1"), "GBK");
        else {
            this.empName = ((String) (String) getSession().getAttribute("empName"));
        }

        this.exShiftEnable = this.dbConfigManager.getProperty("sys.examin.shift.enable").trim();

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        if ((this.empNo != null) && (this.empNo.length() > 0)) {
            Employee emp = empBo.loadEmp(this.empNo, null);
            if (emp == null) {
                return "noemp";
            }
            if (("OWN".equals(this.authorityCondition)) && (!this.empNo.equals(getCurrentEmpNo()))) {
                return "noauth";
            }
            if (("SUB".equals(this.authorityCondition))
                    && (!empBo.checkEmpInCharge(getCurrentEmp(), emp))) {
                return "noauth";
            }
        } else {
            this.empNo = getCurrentEmpNo();
        }
        if (StringUtils.isNotEmpty(this.newName)) {
            this.empName = URLDecoder.decode(new String(this.newName.getBytes("ISO8859_1"), "GBK"),
                                             "GBK");
        }
        logger.info("empNo:" + this.empNo);
        logger.info("empName : " + this.empName);
        return "success";
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getNewName() {
        return this.newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getCreateFlag() {
        return this.createFlag;
    }

    public void setCreateFlag(String createFlag) {
        this.createFlag = createFlag;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.MyBasicInfo JD-Core Version: 0.5.4
 */