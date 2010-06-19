package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.examin.bo.interfaces.IOvertimetypeBo;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class OverworkTypeAction extends BaseAction {
    private List allOtType;

    public String execute() throws Exception {
        IOvertimetypeBo ot_BO = (IOvertimetypeBo) SpringBeanFactory.getBean("overtimetypeBO");
        this.allOtType = ot_BO.FindAllOtType();
        return "success";
    }

    public List getAllOtType() {
        return this.allOtType;
    }

    public void setAllOtType(List allOtType) {
        this.allOtType = allOtType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.OverworkTypeAction JD-Core Version: 0.5.4
 */