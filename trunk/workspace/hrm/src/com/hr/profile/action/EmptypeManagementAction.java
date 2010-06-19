package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class EmptypeManagementAction extends BaseAction {
    private List emptypeList;

    public String execute() throws Exception {
        IEmpTypeBO bo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        this.emptypeList = bo.FindAllEmpType();
        return "success";
    }

    public List getEmptypeList() {
        return this.emptypeList;
    }

    public void setEmptypeList(List emptypeList) {
        this.emptypeList = emptypeList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.EmptypeManagementAction JD-Core Version: 0.5.4
 */