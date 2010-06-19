package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.compensation.bo.IEmpSalaryAcctBo;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.IJobgradeBO;
import com.hr.configuration.domain.Jobgrade;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class JobtitleManagementAction extends BaseAction {
    private List allJobTitle;
    private List<Empsalaryacct> empsalaryacctList;
    private List<Jobgrade> jobgradeList;

    public String execute() throws Exception {
        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.allJobTitle = jobTitleBo.FindAllJobTitle();

        IEmpSalaryAcctBo empSalaryAcctBo = (IEmpSalaryAcctBo) getBean("empsalaryacctBo");
        this.empsalaryacctList = empSalaryAcctBo.getObjects(Empsalaryacct.class, null);

        IJobgradeBO jobgradeBo = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
        this.jobgradeList = jobgradeBo.FindAllJobgrade();
        return "success";
    }

    public List getAllJobTitle() {
        return this.allJobTitle;
    }

    public void setAllJobTitle(List allJobTitle) {
        this.allJobTitle = allJobTitle;
    }

    public List<Empsalaryacct> getEmpsalaryacctList() {
        return this.empsalaryacctList;
    }

    public void setEmpsalaryacctList(List<Empsalaryacct> empsalaryacctList) {
        this.empsalaryacctList = empsalaryacctList;
    }

    public List<Jobgrade> getJobgradeList() {
        return this.jobgradeList;
    }

    public void setJobgradeList(List<Jobgrade> jobgradeList) {
        this.jobgradeList = jobgradeList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.JobtitleManagementAction JD-Core Version: 0.5.4
 */