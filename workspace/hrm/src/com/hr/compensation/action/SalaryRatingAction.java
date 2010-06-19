package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IJobgradeBO;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class SalaryRatingAction extends BaseAction {
    private List alljobgrade;

    public String execute() throws Exception {
        IJobgradeBO jobgradebo = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
        this.alljobgrade = jobgradebo.FindAllJobgrade();

        return "success";
    }

    public List getAlljobgrade() {
        return this.alljobgrade;
    }

    public void setAlljobgrade(List alljobgrade) {
        this.alljobgrade = alljobgrade;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.SalaryRatingAction JD-Core Version: 0.5.4
 */