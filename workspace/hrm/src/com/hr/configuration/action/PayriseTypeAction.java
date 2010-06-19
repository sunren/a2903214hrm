package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IEcptypeBO;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class PayriseTypeAction extends BaseAction {
    private List allEcptype;

    public String execute() throws Exception {
        IEcptypeBO ecptypebo = (IEcptypeBO) SpringBeanFactory.getBean("ecptypeBO");
        this.allEcptype = ecptypebo.FindAllEcptype();

        return "success";
    }

    public List getAllEcptype() {
        return this.allEcptype;
    }

    public void setAllEcptype(List allEcptype) {
        this.allEcptype = allEcptype;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.PayriseTypeAction JD-Core Version: 0.5.4
 */