package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IContractTypeBo;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class ContractTypeAction extends BaseAction {
    private List allContractType;

    public String execute() throws Exception {
        IContractTypeBo contractBo = (IContractTypeBo) SpringBeanFactory.getBean("contractTypeBo");
        this.allContractType = contractBo.FindAllEcptype();

        return "success";
    }

    public List getAllContractType() {
        return this.allContractType;
    }

    public void setAllContractType(List allContractType) {
        this.allContractType = allContractType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.ContractTypeAction JD-Core Version: 0.5.4
 */