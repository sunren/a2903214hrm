package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class HolidayTypeAction extends BaseAction {
    private List allLeaveType;

    public String execute() throws Exception {
        ILeavetypeBO lt_BO = (ILeavetypeBO) SpringBeanFactory.getBean("leavetypeBO");
        this.allLeaveType = lt_BO.FindAllLeaveType();

        return "success";
    }

    public List getAllLeaveType() {
        return this.allLeaveType;
    }

    public void setAllLeaveType(List allLeaveType) {
        this.allLeaveType = allLeaveType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.HolidayTypeAction JD-Core Version: 0.5.4
 */