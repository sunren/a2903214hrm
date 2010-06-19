package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IAttdMachineBO;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class AttdMachineAction extends BaseAction {
    private List allAttdMachine;

    public String execute() throws Exception {
        IAttdMachineBO attdMachineBO = (IAttdMachineBO) SpringBeanFactory.getBean("attdMachineBO");
        this.allAttdMachine = attdMachineBO.FindAllAttdMachine();
        return "success";
    }

    public List getAllAttdMachine() {
        return this.allAttdMachine;
    }

    public void setAllAttdMachine(List allAttdMachine) {
        this.allAttdMachine = allAttdMachine;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.AttdMachineAction JD-Core Version: 0.5.4
 */