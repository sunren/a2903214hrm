package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.compensation.bo.IEmpSalaryAcctBo;
import com.hr.compensation.bo.IEmpSalaryAcctversionBo;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.util.DateUtil;
import com.hr.util.Interpreter;
import com.hr.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddEmpsalaryacct extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Empsalaryacct empsalaryacct;
    private Empsalaryacctversion empsalaryacctversion;
    private String[] dataDefIds;
    private Integer[] dataTypes;
    private Integer[] dataIsCalcs;
    private String[] dataCalcs;
    private Integer[] dataRoundings;
    private int acctitemNos;
    private String msgNoName;
    private String msgDupliName;
    private String msgNotComplete;
    private String msgIllegalForm;
    private String msgSucc;

    public AddEmpsalaryacct() {
        this.empsalaryacctversion = new Empsalaryacctversion();

        this.msgNoName = "请设置帐套名称！";
        this.msgDupliName = "帐套名称重复＄1�7";
        this.msgNotComplete = "项目种类不完整，帐套必须包含“{0}”！";
        this.msgIllegalForm = "您输入的 A{0}公式不合法：{1}";
        this.msgSucc = "{0}已创建成功！";
    }

    public String execute() throws Exception {
        if (this.empsalaryacct == null) {
            return "input";
        }

        if (this.empsalaryacct.getEsacName() == null) {
            addActionErrorInfo(this.msgNoName, new Object[0]);
            return "input";
        }

        this.empsalaryacct.setEsacName(this.empsalaryacct.getEsacName().trim());

        String info = commonCheck();
        if (!info.equals("SUCC")) {
            addErrorInfo(info);
            return "input";
        }

        List newItems = getNewAcctItems();

        this.empsalaryacctversion.setEsavEsac(this.empsalaryacct);
        Date today = new Date();
        String curempId = getCurrentEmpNo();
        this.empsalaryacctversion.setEsavCreateBy(curempId);
        this.empsalaryacctversion.setEsavCreateTime(today);
        this.empsalaryacctversion.setEsavLastChangeBy(curempId);
        this.empsalaryacctversion.setEsavLastChangeTime(today);
        this.empsalaryacctversion.setEsavValidFrom(DateUtil.convDateTimeToDate(today));
        this.empsalaryacctversion.setEsavValidTo(null);

        IEmpSalaryAcctversionBo esavBo = (IEmpSalaryAcctversionBo) getBean("empsalaryacctversionBo");
        esavBo.addAcctVersion(this.empsalaryacctversion, newItems);

        addSuccessInfo(this.msgSucc, new Object[] { this.empsalaryacct.getEsacName() });
        this.empsalaryacct = null;
        return "success";
    }

    private String commonCheck() {
        IEmpSalaryAcctBo esaBo = (IEmpSalaryAcctBo) getBean("empsalaryacctBo");
        if (esaBo.searchAcctNames(this.empsalaryacct.getEsacName(), null)) {
            return StringUtil.message(this.msgDupliName, new Object[0]);
        }

        for (int i = this.dataDefIds.length; i > 0; --i) {
            if (this.dataDefIds[(i - 1)] == null)
                continue;
            if (this.dataDefIds[(i - 1)].length() != 0) {
                this.acctitemNos = i;
                break;
            }
        }

        Boolean hasBasicSalary = Boolean.valueOf(false);
        Boolean hasIncomeBfTax = Boolean.valueOf(false);
        Boolean hasTax = Boolean.valueOf(false);
        Boolean hasIncomeAfTax = Boolean.valueOf(false);
        Interpreter interpreter = new Interpreter();
        for (int i = 0; i < this.acctitemNos; ++i)
            if (this.dataTypes[i].intValue() == 1) {
                hasBasicSalary = Boolean.valueOf(true);
            } else if (this.dataTypes[i].intValue() == 8) {
                hasIncomeBfTax = Boolean.valueOf(true);
            } else if (this.dataTypes[i].intValue() == 18) {
                hasTax = Boolean.valueOf(true);
            } else {
                if (this.dataTypes[i].intValue() != 19)
                    continue;
                hasIncomeAfTax = Boolean.valueOf(true);
            }
        if (hasBasicSalary.booleanValue() != true)
            return StringUtil.message(this.msgNotComplete, new Object[] { "基本工资" });
        if (hasIncomeBfTax.booleanValue() != true)
            return StringUtil.message(this.msgNotComplete, new Object[] { "税前收入" });
        if (hasTax.booleanValue() != true)
            return StringUtil.message(this.msgNotComplete, new Object[] { "扄1�7得税" });
        if (hasIncomeAfTax.booleanValue() != true)
            return StringUtil.message(this.msgNotComplete, new Object[] { "税后收入" });

        StringBuffer formularError = new StringBuffer();
        for (int i = 0; i < this.acctitemNos; ++i) {
            if (this.dataIsCalcs[i].intValue() == 2) {
                String info = interpreter.formulaValidate(this.dataCalcs[i], new Empsalaryconfig());
                if (!info.equals("SUCC")) {
                    formularError.append(StringUtil.message(this.msgIllegalForm, new Object[] {
                            Integer.valueOf(i + 1), info }));
                }
            }
        }

        if (formularError.toString().length() > 0) {
            return formularError.toString();
        }

        return "SUCC";
    }

    private List<Empsalaryacctitems> getNewAcctItems() {
        List returnList = new ArrayList();

        for (int i = 0; i < this.acctitemNos; ++i) {
            if ((this.dataDefIds[i] == null) || (this.dataDefIds[i].length() == 0))
                continue;
            if (i >= 48) {
                continue;
            }
            Empsalaryacctitems empsalaryacctitem = new Empsalaryacctitems();
            empsalaryacctitem.setEsaiEsdd(new Empsalarydatadef(this.dataDefIds[i]));
            empsalaryacctitem.setEsaiDataIsCalc(this.dataIsCalcs[i]);
            if (this.dataCalcs[i] != null)
                empsalaryacctitem.setEsaiDataCalc(this.dataCalcs[i].replaceAll(" ", "")
                        .toUpperCase());
            empsalaryacctitem.setEsaiDataRounding(this.dataRoundings[i]);
            empsalaryacctitem.setEsaiDataSeq(Integer.valueOf(i + 1));
            empsalaryacctitem.setEsaiEsav(this.empsalaryacctversion);
            returnList.add(empsalaryacctitem);
        }
        return returnList;
    }

    public void validate() {
    }

    public Empsalaryacct getEmpsalaryacct() {
        return this.empsalaryacct;
    }

    public void setEmpsalaryacct(Empsalaryacct empsalaryacct) {
        this.empsalaryacct = empsalaryacct;
    }

    public String[] getDataDefIds() {
        return this.dataDefIds;
    }

    public void setDataDefIds(String[] dataDefIds) {
        this.dataDefIds = dataDefIds;
    }

    public Integer[] getDataIsCalcs() {
        return this.dataIsCalcs;
    }

    public void setDataIsCalcs(Integer[] dataIsCalcs) {
        this.dataIsCalcs = dataIsCalcs;
    }

    public String[] getDataCalcs() {
        return this.dataCalcs;
    }

    public void setDataCalcs(String[] dataCalcs) {
        this.dataCalcs = dataCalcs;
    }

    public Integer[] getDataRoundings() {
        return this.dataRoundings;
    }

    public void setDataRoundings(Integer[] dataRoundings) {
        this.dataRoundings = dataRoundings;
    }

    public Empsalaryacctversion getEmpsalaryacctversion() {
        return this.empsalaryacctversion;
    }

    public void setEmpsalaryacctversion(Empsalaryacctversion empsalaryacctversion) {
        this.empsalaryacctversion = empsalaryacctversion;
    }

    public Integer[] getDataTypes() {
        return this.dataTypes;
    }

    public void setDataTypes(Integer[] dataTypes) {
        this.dataTypes = dataTypes;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.AddEmpsalaryacct JD-Core Version: 0.5.4
 */