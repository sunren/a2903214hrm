package com.hr.compensation.action;

import com.hr.base.DWRUtil;
import com.hr.compensation.bo.IEmpBenefitBo;
import com.hr.compensation.bo.IEmpSalaryAcctBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.IEmpSalaryAcctversionBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import com.hr.util.Interpreter;
import com.hr.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class UpdateEmpsalaryacct extends CompAction {
    private static final long serialVersionUID = 1L;
    private Empsalaryacct empsalaryacct;
    private Empsalaryacctversion empsalaryacctversion;
    private String[] dataDefIds;
    private Integer[] dataTypes;
    private Integer[] dataIsCalcs;
    private String[] dataCalcs;
    private Integer[] dataRoundings;
    private String[] dataNames;
    private int acctitemNos;
    private String updateFlag;
    List<Empsalaryacctitems> esaiList;
    private List<Empsalaryconfig> salaryConfigFormList;
    private String id;
    private Integer hasDiff;
    private String viewType;
    private String outmatchModelId;
    public static final String outputIoName = "OSalaryAcctitems";
    private String searchOrExport;

    public UpdateEmpsalaryacct() {
        this.empsalaryacctversion = new Empsalaryacctversion();

        this.viewType = "0";

        this.searchOrExport = null;
    }

    public String updateInit() throws Exception {
        IEmpSalaryAcctversionBo esavBo = (IEmpSalaryAcctversionBo) getBean("empsalaryacctversionBo");
        this.empsalaryacctversion = esavBo.loadObject(this.id, new String[0]);

        if (this.empsalaryacctversion == null) {
            addActionError("薪资帐套版本不存在，请检查后重试");
            return "success";
        }

        IEmpSalaryAcctBo esaBo = (IEmpSalaryAcctBo) getBean("empsalaryacctBo");
        this.empsalaryacct = esaBo.loadObject(this.empsalaryacctversion.getEsavEsac().getId(),
                                              new String[0]);

        IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) getBean("empsalaryacctitemsBo");
        List esaiList = esaiBo.getItemsByAcctversion(this.id);

        if ("export".equals(this.searchOrExport)) {
            if ((esaiList == null) || (esaiList.size() == 0)) {
                addActionError("无帐套项目数据可以导出！");
                return "success";
            }
            if (excelDownload(esaiList, "OSalaryAcctitems", this.outmatchModelId)) {
                clearErrorsAndMessages();
                return "download";
            }
            addActionError("数据导出失败!");
            return "success";
        }

        if (esaiList.size() == 0) {
            addActionError("薪资帐套版本无任何项目，不能修改");
            return "success";
        }

        this.acctitemNos = esaiList.size();
        this.dataDefIds = new String[this.acctitemNos];
        this.dataTypes = new Integer[this.acctitemNos];
        this.dataIsCalcs = new Integer[this.acctitemNos];
        this.dataCalcs = new String[this.acctitemNos];
        this.dataRoundings = new Integer[this.acctitemNos];
        this.dataNames = new String[this.acctitemNos];

        for (int i = 0; i < this.acctitemNos; ++i) {
            Empsalaryacctitems item = (Empsalaryacctitems) esaiList.get(i);
            this.dataDefIds[i] = item.getEsaiEsdd().getEsddId();
            this.dataTypes[i] = item.getEsaiEsdd().getEsddDataType();
            this.dataIsCalcs[i] = item.getEsaiDataIsCalc();
            this.dataCalcs[i] = item.getEsaiDataCalc();
            this.dataRoundings[i] = item.getEsaiDataRounding();
            this.dataNames[i] = item.getEsaiEsdd().getEsddName();
        }

        if (this.empsalaryacctversion.getEsavValidTo() != null)
            this.viewType = "1";

        String flag = getRequest().getParameter("flag");
        if ("copy".equals(flag)) {
            this.empsalaryacct.setEsacName(this.empsalaryacct.getEsacName() + "(复件)");
        }

        return "success";
    }

    public String execute() throws Exception {
        if (this.empsalaryacct == null) {
            addActionError("请设置帐套名称和描述");
            return "input";
        }

        this.empsalaryacct.setEsacName(this.empsalaryacct.getEsacName().trim());

        String info = commonCheck();
        if (!info.equals("SUCC")) {
            addErrorInfo(info);
            return "input";
        }

        IEmpSalaryAcctversionBo esavBo = (IEmpSalaryAcctversionBo) getBean("empsalaryacctversionBo");
        Map oldMap = esavBo.getOldAcctItems(this.empsalaryacctversion.getId());
        List newItems = getNewAcctItems();

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) SpringBeanFactory.getBean("salaryPaidBo");
        List salaryPayList = salaryPaidBo.getSalaryPaidbyEsav(this.empsalaryacctversion.getId());

        IEmpBenefitBo empbenefitBo = (IEmpBenefitBo) SpringBeanFactory.getBean("empbenefitBo");
        List planList = empbenefitBo.getBenefitPlanByEsav(this.empsalaryacctversion.getId());
        if ((salaryPayList.size() > 0) || (planList.size() > 0)) {
            int hasDiff = checkDiff(oldMap, newItems);
            if (hasDiff == 1) {
                addErrorInfo("帐套有重大改变，更新将导致薪资发放历史数据异常，请您另存为新版本");
                return "input";
            }
            if (hasDiff == 0) {
                salaryPaidBo.shuffleSalaryPay(salaryPayList, oldMap, newItems);
                empbenefitBo.shuffleBenefitPlan(planList, oldMap, newItems);
            }

        }

        ISalaryconfBo salaryconfBo = (ISalaryconfBo) getBean("salaryconfBo");
        List salaryconfigList = salaryconfBo.changeSalaryConfig(this.empsalaryacctversion.getId(),
                                                                oldMap, newItems);

        this.empsalaryacctversion.setEsavEsac(this.empsalaryacct);
        this.empsalaryacctversion.setEsavLastChangeBy(getCurrentEmpNo());

        esavBo.updateAcctVersion(this.empsalaryacctversion, newItems, salaryconfigList,
                                 salaryPayList, planList);

        addActionMessage("修改" + this.empsalaryacct.getEsacName() + "成功");

        this.empsalaryacct = null;
        this.empsalaryacctversion = null;
        this.dataDefIds = null;
        this.dataIsCalcs = null;
        this.dataCalcs = null;
        this.dataRoundings = null;

        return "success";
    }

    public String geneNewVersion() throws Exception {
        if (this.empsalaryacct == null) {
            addActionError("请设置帐套名称和描述");
            return "input";
        }

        String info = commonCheck();
        if (!info.equals("SUCC")) {
            addErrorInfo(info);
            return "input";
        }

        Date today = DateUtil.convDateTimeToDate(new Date());
        if (DateUtil.dateDiff(today, this.empsalaryacctversion.getEsavValidFrom(), 5) == 0) {
            addActionError("您今天已存过薪资帐套版本，同一帐套一天只能存一个新版本！");
            return "input";
        }

        IEmpSalaryAcctversionBo esavBo = (IEmpSalaryAcctversionBo) getBean("empsalaryacctversionBo");
        Map oldMap = esavBo.getOldAcctItems(this.empsalaryacctversion.getId());
        List newItems = getNewAcctItems();

        ISalaryconfBo salaryconfBo = (ISalaryconfBo) getBean("salaryconfBo");
        List salaryconfigList = salaryconfBo.changeSalaryConfig(this.empsalaryacctversion.getId(),
                                                                oldMap, newItems);

        this.empsalaryacctversion.setEsavEsac(this.empsalaryacct);
        this.empsalaryacctversion.setEsavLastChangeBy(getCurrentEmpNo());

        esavBo.updateAcctVersion(this.empsalaryacctversion, newItems, salaryconfigList);

        addActionMessage("另存帐套" + this.empsalaryacct.getEsacName() + "的新版本成功");

        this.empsalaryacct = null;
        this.empsalaryacctversion = null;
        this.dataDefIds = null;
        this.dataIsCalcs = null;
        this.dataCalcs = null;
        this.dataRoundings = null;

        return "success";
    }

    private String commonCheck() {
        IEmpSalaryAcctBo esaBo = (IEmpSalaryAcctBo) getBean("empsalaryacctBo");
        if (esaBo.searchAcctNames(this.empsalaryacct.getEsacName(), this.empsalaryacct.getId())) {
            return "帐套名称不允许重复！";
        }

        if (this.empsalaryacctversion.getEsavValidTo() != null) {
            return "此帐套为历史帐套，请在最新帐套版本上修改";
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
            return "项目种类不完整，帐套必须包含“基本工资”！";
        if (hasIncomeBfTax.booleanValue() != true)
            return "项目种类不完整，帐套必须包含“税前收入”！";
        if (hasTax.booleanValue() != true)
            return "项目种类不完整，帐套必须包含“所得税”！";
        if (hasIncomeAfTax.booleanValue() != true)
            return "项目种类不完整，帐套必须包含“税后收入”！";

        StringBuffer formularError = new StringBuffer();
        for (int i = 0; i < this.acctitemNos; ++i) {
            if (this.dataIsCalcs[i].intValue() == 2) {
                String info = interpreter.formulaValidate(this.dataCalcs[i], new Empsalaryconfig());
                if (!info.equals("SUCC")) {
                    formularError.append("您输入的公式 A" + (i + 1) + "不合法：" + info);
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

    private int checkDiff(Map<String, Integer> oldMap, List<Empsalaryacctitems> newItems) {
        int hasDiff = -1;
        String dataDefId = new String();

        HashMap tempMap = (HashMap) ((HashMap) oldMap).clone();

        for (int i = 0; i < newItems.size(); ++i) {
            dataDefId = ((Empsalaryacctitems) newItems.get(i)).getEsaiEsdd().getEsddId();
            Integer value = (Integer) tempMap.get(dataDefId);

            if (value == null)
                return 1;

            if (value.intValue() != i)
                hasDiff = 0;
            tempMap.remove(dataDefId);
        }

        if (!tempMap.isEmpty())
            hasDiff = 1;
        return hasDiff;
    }

    public String pageGetDiff(String acctversionId, String[] pageDataDefIds) {
        if (!"HR".equals(DWRUtil.checkAuth("updateesainit", "execute"))) {
            return StringUtil.message(this.msgNoAuth, new Object[] { "noauth" });
        }

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) SpringBeanFactory.getBean("salaryPaidBo");
        if (salaryPaidBo.hasSalaryPayByAcctVersion(acctversionId) == 0) {
            return StringUtil.message(this.msgAcctUpdConf, new Object[] { "WARNING" });
        }

        IEmpSalaryAcctversionBo esavBo = (IEmpSalaryAcctversionBo) getBean("empsalaryacctversionBo");
        Map oldMap = esavBo.getOldAcctItems(acctversionId);

        for (int i = pageDataDefIds.length; i > 0; --i) {
            if (pageDataDefIds[(i - 1)] == null)
                continue;
            if (pageDataDefIds[(i - 1)].length() != 0) {
                this.acctitemNos = i;
                break;
            }
        }
        int hasDiff = -1;

        for (int i = 0; i < this.acctitemNos; ++i) {
            Integer value = (Integer) oldMap.get(pageDataDefIds[i]);

            if (value == null) {
                return StringUtil.message(this.msgAcctUpdErr, new Object[] { "upderr" });
            }

            if (value.intValue() != i)
                hasDiff = 0;
            oldMap.remove(pageDataDefIds[i]);
        }

        if (!oldMap.isEmpty()) {
            return StringUtil.message(this.msgAcctUpdErr, new Object[] { "upderr" });
        }
        if (hasDiff == 0) {
            return StringUtil.message(this.msgAcctUpdConfPay, new Object[] { "WARNING" });
        }
        return StringUtil.message(this.msgAcctUpdConf, new Object[] { "WARNING" });
    }

    public Empsalaryacct getEmpsalaryacct() {
        return this.empsalaryacct;
    }

    public void setEmpsalaryacct(Empsalaryacct empsalaryacct) {
        this.empsalaryacct = empsalaryacct;
    }

    public Empsalaryacctversion getEmpsalaryacctversion() {
        return this.empsalaryacctversion;
    }

    public void setEmpsalaryacctversion(Empsalaryacctversion empsalaryacctversion) {
        this.empsalaryacctversion = empsalaryacctversion;
    }

    public List<Empsalaryconfig> getSalaryConfigFormList() {
        return this.salaryConfigFormList;
    }

    public void setSalaryConfigFormList(List<Empsalaryconfig> salaryConfigFormList) {
        this.salaryConfigFormList = salaryConfigFormList;
    }

    public String getUpdateFlag() {
        return this.updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

    public List<Empsalaryacctitems> getEsaiList() {
        return this.esaiList;
    }

    public void setEsaiList(List<Empsalaryacctitems> esaiList) {
        this.esaiList = esaiList;
    }

    public String[] getDataDefIds() {
        return this.dataDefIds;
    }

    public void setDataDefIds(String[] dataDefIds) {
        this.dataDefIds = dataDefIds;
    }

    public Integer[] getDataTypes() {
        return this.dataTypes;
    }

    public void setDataTypes(Integer[] dataTypes) {
        this.dataTypes = dataTypes;
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViewType() {
        return this.viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String[] getDataNames() {
        return this.dataNames;
    }

    public void setDataNames(String[] dataNames) {
        this.dataNames = dataNames;
    }

    public Integer getHasDiff() {
        return this.hasDiff;
    }

    public void setHasDiff(Integer hasDiff) {
        this.hasDiff = hasDiff;
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }

    public static String getOutputIoName() {
        return "OSalaryAcctitems";
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.UpdateEmpsalaryacct JD-Core Version: 0.5.4
 */