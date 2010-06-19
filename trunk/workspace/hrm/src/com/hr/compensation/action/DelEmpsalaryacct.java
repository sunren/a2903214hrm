package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.compensation.bo.ICompaplanBo;
import com.hr.compensation.bo.IEmpBenefitPlanBo;
import com.hr.compensation.bo.IEmpSalaryAcctversionBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctversion;

public class DelEmpsalaryacct extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String id;
    private String msgNoData;
    private String msgHistVer;
    private String msgHasConfig;
    private String msgHasPaid;
    private String msgHasCompPlan;
    private String msgHasBenePlan;
    private String msgSucc;

    public DelEmpsalaryacct() {
        this.msgNoData = "薪资帐套版本不存在，请刷新后重试＄1�7";
        this.msgHistVer = "{0}为历史版本，不允许删除！";
        this.msgHasConfig = "{0}存在员工薪资配置数据，不允许删除＄1�7";
        this.msgHasPaid = "{0}存在员工薪资发放数据，不允许删除＄1�7";
        this.msgHasCompPlan = "{0}存在员工调薪记录，不允许删除＄1�7";
        this.msgHasBenePlan = "{0}存在社保缴费历史，不允许删除＄1�7";
        this.msgSucc = "{0}已删除！";
    }

    public String execute() throws Exception {
        IEmpSalaryAcctversionBo esavBo = (IEmpSalaryAcctversionBo) getBean("empsalaryacctversionBo");
        Empsalaryacctversion esav = esavBo.loadObject(this.id, new String[] { "esavEsac" });
        if (esav == null) {
            addActionErrorInfo(this.msgNoData, new Object[0]);
            return "input";
        }

        if (esav.getEsavValidTo() != null) {
            addActionErrorInfo(this.msgHistVer, new Object[] { esav.getEsavEsac().getEsacName() });
            return "input";
        }

        ISalaryconfBo salaryConfBo = (ISalaryconfBo) getBean("salaryconfBo");
        int num = salaryConfBo.hasSalaryConfigByAcctVersion(this.id);
        if (num > 0) {
            addActionErrorInfo(this.msgHasConfig, new Object[] { esav.getEsavEsac().getEsacName() });
            return "input";
        }

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        if (salaryPaidBo.hasSalaryPayByAcctVersion(this.id) > 0) {
            addActionErrorInfo(this.msgHasPaid, new Object[] { esav.getEsavEsac().getEsacName() });
            return "input";
        }

        ICompaplanBo compaplanBo = (ICompaplanBo) getBean("compaplanBo");
        if (compaplanBo.hasSalaryAdjByAcctVersion(this.id) > 0) {
            addActionErrorInfo(this.msgHasCompPlan,
                               new Object[] { esav.getEsavEsac().getEsacName() });
            return "input";
        }

        IEmpBenefitPlanBo benefitPlanBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        int planNum = benefitPlanBo.hasBenefitPlanByAcctVersion(this.id);
        if (planNum > 0) {
            addActionErrorInfo(this.msgHasBenePlan,
                               new Object[] { esav.getEsavEsac().getEsacName() });
            return "input";
        }

        addSuccessInfo(this.msgSucc, new Object[] { esav.getEsavEsac().getEsacName() });
        esavBo.deleteAcctVersion(this.id, esav.getEsavEsac().getId());
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.DelEmpsalaryacct JD-Core Version: 0.5.4
 */