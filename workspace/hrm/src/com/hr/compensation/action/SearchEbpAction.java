package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.compensation.bo.IEmpBenefitPlanBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.IEmpsalaryperiodBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.domain.Empbenefit;
import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.compensation.domain.Empsalaryperiod;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.configuration.domain.StatusconfPK;
import com.hr.examin.bo.interfaces.IAttendmonthlyBO;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import com.hr.util.StringUtil;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.axis.utils.StringUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.DetachedCriteria;

public class SearchEbpAction extends CompAction {
    private static final long serialVersionUID = 1L;
    private String empCondition;
    private Employee emp;
    private Pager page;
    private List<Statusconf> empStatus;
    private List<Location> locationList;
    private List<BenefitType> ebfTypeList;
    private String yearMonth;
    private List<Empbenefitplan> ebpList;
    private String ebpId;
    private String canEdit;
    private String empId;
    private String addyearmonth1;
    private String ebpPensionAmount1;
    private String ebpHousingAmount1;
    private String ebpInsuranceAmount1;
    private String memo1;
    private String addyearmonth2;
    private String ebpPensionAmount2;
    private String ebpHousingAmount2;
    private String ebpInsuranceAmount2;
    private String memo2;
    private String addyearmonth3;
    private String ebpPensionAmount3;
    private String ebpHousingAmount3;
    private String ebpInsuranceAmount3;
    private String memo3;
    private String addyearmonth4;
    private String ebpPensionAmount4;
    private String ebpHousingAmount4;
    private String ebpInsuranceAmount4;
    private String memo4;
    private String addyearmonth5;
    private String ebpPensionAmount5;
    private String ebpHousingAmount5;
    private String ebpInsuranceAmount5;
    private String memo5;
    private String addyearmonth6;
    private String ebpPensionAmount6;
    private String ebpHousingAmount6;
    private String ebpInsuranceAmount6;
    private String memo6;

    public SearchEbpAction() {
        this.emp = new Employee();

        this.page = new Pager();

        this.locationList = null;

        this.yearMonth = DateUtil.formatTodayToS("yyyy-MM");
    }

    public String execute() throws Exception {
        getDrillDownList();

        this.ebpList = searchEbp();

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        ebpBo.calcEbp(this.ebpList);

        setCanEdit(getEspStatus(this.yearMonth));
        return "success";
    }

    private List<Empbenefitplan> searchEbp() {
        DetachedCriteria dc = searchBenefitPlan_DC();

        addCriteria(dc);

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        return ebpBo.findEbp(dc, this.page, null);
    }

    private DetachedCriteria searchBenefitPlan_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empbenefitplan.class);
        dc.createAlias(Empbenefitplan.PROP_EBP_EMP_NO, "emp", 1);
        dc.createAlias("emp.empBenefitType", "beneType", 1);
        dc.createAlias("emp.empDeptNo", "dept", 1);
        dc.createAlias("emp.empLocationNo", "location", 1);
        return dc;
    }

    private void addCriteria(DetachedCriteria dc) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "emp", 2);
        }

        addOrders(dc, this.page, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO + "-up" });

        BaseCrit.addDC(dc, Empbenefitplan.PROP_EBP_STATUS, "eq",
                       new Integer[] { Integer.valueOf(1) });
        BaseCrit.addEmpDC(dc, "emp", this.emp.getEmpName());
        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, Integer.valueOf(1), this.emp
                                   .getEmpDeptNo());
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                       new Object[] { this.emp.getEmpLocationNo() });
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_BENEFIT_TYPE, "id", new Object[] { this.emp
                .getEmpBenefitType() });

        String yearMonthStr = this.yearMonth.replaceAll("-", "");
        BaseCrit.addDC(dc, Empbenefitplan.PROP_EBP_YEARMONTH, "eq", new String[] { yearMonthStr });
    }

    private void getDrillDownList() {
        this.locationList = getDrillDown("Location", new String[0]);
        this.ebfTypeList = getDrillDown("BenefitType", new String[0]);
    }

    public String checkAddData(String empId, String searchYearMonth, String addYearMonths) {
        String flt = DWRUtil.checkAuth("checkAddData", "checkAddData");
        if ("error".equalsIgnoreCase(flt)) {
            return "noauth";
        }
        searchYearMonth = searchYearMonth.replaceAll("-", "");
        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        String[] yearMonthArr = addYearMonths.split(",");

        List<Empbenefitplan> existebpList = ebpBo.searchEbpByEmpandMonth(empId, searchYearMonth,
                                                                         yearMonthArr);

        IEmployeeBo employeeBo = (IEmployeeBo) getBean("empBo");
        Employee employee = employeeBo.loadEmp(empId, new String[] { "empBenefitType" });
        Integer type = Integer.valueOf((employee.getEmpBenefitType() != null) ? employee
                .getEmpBenefitType().getBenefitTypePayType().intValue() : 0);
        Calendar cal = Calendar.getInstance();
        Date searchYearMonthDate = DateUtil.parseDateByFormat(searchYearMonth, "yyyyMM");
        cal.setTime(searchYearMonthDate);
        if (type.intValue() == 1) {
            cal.add(2, 1);
        }
        String cannotAddYearMonth = "";
        String existYearMonth = "";
        for (int i = 0; i < yearMonthArr.length; ++i) {
            if (DateUtil.formatDateToS(cal.getTime(), "yyyyMM").equals(yearMonthArr[i])) {
                cannotAddYearMonth = DateUtil.formatDateToS(cal.getTime(), "yyyy年MM月");
            }

            for (Empbenefitplan plan : existebpList) {
                if (plan.getEbpBelongYearmonth().equals(yearMonthArr[i])) {
                    existYearMonth = existYearMonth
                            + DateUtil.formatDateToS(DateUtil.parseDateByFormat(yearMonthArr[i],
                                                                                "yyyyMM"),
                                                     "yyyy年MM月") + ",";
                    break;
                }
            }
        }

        String info = "";
        if (cannotAddYearMonth.length() > 0) {
            info = info + employee.getEmpName() + "该月份不能补缴 " + cannotAddYearMonth + " 社保";
        }
        if (existYearMonth.indexOf(",") > -1) {
            existYearMonth = existYearMonth.substring(0, existYearMonth.length() - 1);
            info = info + "\n" + employee.getEmpName() + "该月份已经补缴过 " + existYearMonth
                    + " 社保，不允许重复添加";
        }
        if (info.length() > 0) {
            return info;
        }

        return "succ";
    }

    public String saveBeneAddData() {
        Empsalaryconfig config = loadConfig(this.empId, null);

        Map<String, Empbenefit> newBenefitMap = getBaseParams(config.getEmployee());

        if ((newBenefitMap == null) || (newBenefitMap.size() == 0)) {
            addErrorInfo(this.msgNoData, new Object[] { "nodata", "薪资" });
            return "success";
        }

        Set periodSet = new HashSet();
        for (String beneKey : newBenefitMap.keySet()) {
            String yearmonth = beneKey.substring(0, 6);
            periodSet.add(yearmonth);
        }
        String[] periodArr = (String[]) periodSet.toArray(new String[periodSet.size()]);

        String info = checkBenefitPeriodStat(periodArr, this.yearMonth);
        if (!"SUCC".equals(info)) {
            return "success";
        }

        info = checkBenefitCurrMonth(newBenefitMap, this.yearMonth);
        if (!"SUCC".equals(info)) {
            return "success";
        }

        info = checkBenefitDuplicate(newBenefitMap, this.yearMonth, new String[] { config
                .getEmployee().getId() });
        if (!"SUCC".equals(info)) {
            return "success";
        }

        List payList = updatePayBatch_DC(periodArr, new String[] { config.getEmployee().getId() });

        payList = mergePayBenefitConfig(payList, newBenefitMap, config);

        if ((payList == null) || (payList.size() == 0)) {
            return StringUtil.message(this.msgSystemEx, new Object[] { "nopay" });
        }

        IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        if (!esaiBo.setAcctItemsPay((Empsalarypay[]) payList.toArray(new Empsalarypay[payList
                .size()]))) {
            return StringUtil.message(this.msgNoData, new Object[] { "noacct", "帐套数据" });
        }

        IAttendmonthlyBO attendmonBo = (IAttendmonthlyBO) SpringBeanFactory
                .getBean("attendmonthlyBo");
        for (int i = 0; i < periodArr.length; ++i) {
            attendmonBo.setAttmByPay(periodArr[i], (Empsalarypay[]) payList
                    .toArray(new Empsalarypay[payList.size()]));
        }

        for (int i = 0; i < periodArr.length; ++i) {
            interpretPay(periodArr[i], getCurrentEmpNo(), true, (Empsalarypay[]) payList
                    .toArray(new Empsalarypay[payList.size()]));
        }

        if ((payList == null) || (payList.size() == 0)) {
            return StringUtil.message(this.msgSystemEx, new Object[] { "nopay" });
        }

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        Map ebpMap = ebpBo.findEbpBelongYMs(periodArr, null, true, new String[] { config
                .getEmployee().getId() });

        List planList = geneBenefitPlans(payList, ebpMap, this.yearMonth);

        if ((planList == null) || (planList.size() == 0)) {
            return StringUtil.message(this.msgSystemEx, new Object[] { "noplan" });
        }

        ebpBo.batchSaveEbp(planList);
        addSuccessInfo(this.msgAddBeneSucc, new Object[] { "SUCC",
                config.getEmployee().getEmpName(), this.yearMonth });
        return "success";
    }

    private Map<String, Empbenefit> getBaseParams(Employee employee) {
        Map newBenefitMap = new HashMap();

        Empbenefit benefit = new Empbenefit();
        benefit.setEmployee(employee);
        try {
            for (int i = 1; i <= 6; ++i) {
                Object yearmonth = PropertyUtils.getProperty(this, "addyearmonth" + i);
                if (!StringUtils.isEmpty((String) yearmonth)) {
                    benefit.setEbfStartMonth((String) yearmonth);
                    benefit.setEbfEndMonth((String) yearmonth);

                    Object pension = PropertyUtils.getProperty(this, "ebpPensionAmount" + i);
                    if (StringUtils.isEmpty((String) pension))
                        benefit.setEbfPensionAmount(new BigDecimal("0.00"));
                    else {
                        benefit.setEbfPensionAmount(new BigDecimal((String) pension).setScale(2));
                    }

                    Object housing = PropertyUtils.getProperty(this, "ebpHousingAmount" + i);
                    if (StringUtils.isEmpty((String) housing))
                        benefit.setEbfHousingAmount(new BigDecimal("0.00"));
                    else {
                        benefit.setEbfHousingAmount(new BigDecimal((String) housing).setScale(2));
                    }

                    Object insurance = PropertyUtils.getProperty(this, "ebpInsuranceAmount" + i);
                    if (StringUtils.isEmpty((String) insurance))
                        benefit.setEbfInsuranceAmount(new BigDecimal("0.00"));
                    else {
                        benefit.setEbfInsuranceAmount(new BigDecimal((String) insurance)
                                .setScale(2));
                    }

                    if ((benefit.getEbfPensionAmount().compareTo(new BigDecimal(0.0D)) == 0)
                            && (benefit.getEbfHousingAmount().compareTo(new BigDecimal(0.0D)) == 0)
                            && (benefit.getEbfInsuranceAmount().compareTo(new BigDecimal(0.0D)) == 0)) {
                        continue;
                    }

                    Object memo = PropertyUtils.getProperty(this, "memo" + i);
                    if (memo != null)
                        benefit.setEbfComments((String) memo);

                    String key = benefit.getEbfStartMonth();

                    if (employee.getEmpBenefitType().getBenefitTypePayType().intValue() == 1) {
                        key = DateUtil.yearMonthAdd(key, -1, 2);
                    }

                    newBenefitMap.put(key + " " + employee.getEmpDistinctNo() + " "
                            + employee.getEmpName(), benefit);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return newBenefitMap;
    }

    private String checkBenefitPeriodStat(String[] periodArr, String currYM) {
        currYM = currYM.replaceAll("-", "");

        String[] allPeriodArr = StringUtil.merge1D(periodArr, currYM);
        IEmpsalaryperiodBo periodBo = (IEmpsalaryperiodBo) SpringBeanFactory
                .getBean("empsalaryperiodBO");
        List periodList = periodBo.getPayPeriods(null, allPeriodArr);
        for (int i = 0; (periodList != null) && (i < periodList.size()); ++i) {
            Empsalaryperiod period = (Empsalaryperiod) periodList.get(i);
            Integer status = period.getEspdStatus();
            String periodYm = period.getEspdYearmonth();

            if ((periodYm.equals(currYM)) && (!status.equals(Integer.valueOf(0)))) {
                addErrorInfo(this.msgStatErrBeneC, new Object[] { "staterr", periodYm });
                return "staterr";
            }

            if ((!periodYm.equals(currYM)) && (!status.equals(Integer.valueOf(2)))) {
                addErrorInfo(this.msgStatErrBeneP, new Object[] { "staterr", periodYm });
                return "staterr";
            }
        }

        return "SUCC";
    }

    private Empsalaryperiod getPeriodByMonth(List<Empsalaryperiod> periodList, String yearMonth) {
        if ((periodList == null) || (periodList.size() == 0))
            return null;
        for (Empsalaryperiod period : periodList) {
            if ((yearMonth != null) && (yearMonth.equals(period.getEspdYearmonth())))
                return period;
        }

        return null;
    }

    private String checkBenefitCurrMonth(Map<String, Empbenefit> newBenefitMap, String currYM) {
        currYM = currYM.replaceAll("-", "");

        for (String beneKey : newBenefitMap.keySet()) {
            String yearmonth = beneKey.substring(0, 6);
            if (yearmonth.equals(currYM)) {
                addErrorInfo(this.msgNoYmBenefitC, new Object[] { "noym", beneKey });
                return "noym";
            }
        }
        return "SUCC";
    }

    private String checkBenefitDuplicate(Map<String, Empbenefit> newBenefitMap, String currYM,
            String[] empIds) {
        currYM = currYM.replaceAll("-", "");

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        Map ebpMap = ebpBo.findEbpYMs(new String[] { currYM }, new Integer(1), false, empIds);
        if (ebpMap == null)
            return "SUCC";

        for (String beneKey : newBenefitMap.keySet()) {
            String yearmonth = ((Empbenefit) newBenefitMap.get(beneKey)).getEbfStartMonth();

            if (ebpMap.get(yearmonth) != null) {
                addErrorInfo(this.msgNoYmBenefitP, new Object[] { "noym", beneKey });
                return "noym";
            }
        }
        return "SUCC";
    }

    private List<Empsalarypay> updatePayBatch_DC(String[] yearmonth, String[] empIds) {
        DetachedCriteria dc = CompAction.updatePayBatch_DC();

        BaseCrit.addDC(dc, Empsalarypay.PROP_ESP_YEARMONTH, "in", yearmonth);
        BaseCrit.addDC(dc, Empsalarypay.PROP_ID, "in", empIds);
        BaseAction.addOrders(dc, null, new String[] { Empsalarypay.PROP_ESP_YEARMONTH + "-up",
                "emp." + Employee.PROP_EMP_DISTINCT_NO + "-up" });

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) SpringBeanFactory.getBean("salaryPaidBo");
        List<Empsalarypay> payList = salaryPaidBo.findPay(dc, null);
        if ((payList == null) || (payList.size() == 0))
            return null;

        for (Empsalarypay pay : payList) {
            Empsalaryconfig config = pay.getEspEmpconfig();
            pay.decryEMPPaid(pay);
            config.decryEmpSalaryConf(config);
        }
        return payList;
    }

    private List<Empsalarypay> mergePayBenefitConfig(List<Empsalarypay> payList,
            Map<String, Empbenefit> newBenefitMap, Empsalaryconfig config) {
        Iterator it;
        if ((payList == null) || (payList.size() == 0))
            payList = new ArrayList();
        else {
            for (it = payList.iterator(); it.hasNext();) {
                Empsalarypay pay = (Empsalarypay) it.next();
                String key = pay.getEspYearmonth() + " " + pay.getEspEmpno().getEmpDistinctNo()
                        + " " + pay.getEspEmpno().getEmpName();
                Empbenefit benefit = (Empbenefit) newBenefitMap.get(key);
                if (benefit != null) {
                    pay.getEspEmpno().setBenefit(benefit);
                    newBenefitMap.remove(key);
                } else {
                    it.remove();
                }
            }
        }

        for (String key : newBenefitMap.keySet()) {
            Empbenefit benefit = (Empbenefit) newBenefitMap.get(key);
            config.getEmployee().setBenefit(benefit);
            Empsalarypay pay = copyConfigToPay(config);
            pay.setEspYearmonth(key.substring(0, 6));
            payList.add(pay);
        }

        if ((payList == null) || (payList.size() == 0))
            return null;

        for (Empsalarypay pay : payList) {
            pay.setEspBenefitPlans(Integer.valueOf(1));
        }

        return payList;
    }

    private List<Empbenefitplan> geneBenefitPlans(List<Empsalarypay> payList,
            Map<String, Empbenefitplan> ebpMap, String currYM) {
        currYM = currYM.replaceAll("-", "");
        List planList = new ArrayList();
        String lastChangeBy = getCurrentEmp().getId();
        Date lastChangeTime = new Date();
        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        for (Empsalarypay pay : payList) {
            String key = pay.getEspYearmonth() + " " + pay.getEspEmpno().getEmpDistinctNo() + " "
                    + pay.getEspEmpno().getEmpName();
            Empbenefitplan planPaid = null;
            if (ebpMap != null)
                planPaid = (Empbenefitplan) ebpMap.get(key);
            Empbenefitplan planAll = pay.getBenefitPlan();
            Empbenefitplan planRemain = ebpBo.deductEbpYMs(planPaid, planAll);

            if (planRemain != null) {
                planRemain.setEbpEsavId(pay.getEspEsavId());
                planRemain.setEbpEmpno(pay.getEspEmpno());
                planRemain.setEbpYearMonth(currYM);
                String planBelongYearmonth = DateUtil.yearMonthAdd(pay.getEspYearmonth(), pay
                        .getEspEmpno().getEmpBenefitType().getBenefitTypePayType().intValue(), 2);

                planRemain.setEbpBelongYearmonth(planBelongYearmonth);
                planRemain.setEbpStatus(new Integer(1));
                planRemain.setEbpCreateBy(lastChangeBy);
                planRemain.setEbpCreateTime(lastChangeTime);
                planRemain.setEbpLastChangeBy(lastChangeBy);
                planRemain.setEbpLastChangeTime(lastChangeTime);

                planRemain.encryEMPPlan(planRemain);
                planList.add(planRemain);
            }
        }
        return planList;
    }

    public String deleteEbpById() {
        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        Empbenefitplan ebp = ebpBo.searchEbpById(this.ebpId);
        if (ebp == null) {
            addErrorInfo("删除失败！请刷新页面后重试");
            return "success";
        }

        ebpBo.deleteEbpById(this.ebpId);
        String yearMontht = null;
        try {
            yearMontht = DateUtil.formatDateToS(DateUtil.parseDateByFormat(ebp
                    .getEbpBelongYearmonth(), "yyyyMM"), "yyyy年MM月");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addSuccessInfo("您已删除" + ebp.getEbpEmpno().getEmpName() + " 的 " + yearMontht
                + "社保补缴记录，改动将在重新初始化薪资后生效！");
        return "success";
    }

    public List<Empsalaryacctitems> getEbpItemsById(String ebpId) {
        String flt = DWRUtil.checkAuth("getEbpById", "getEbpById");
        if ("error".equalsIgnoreCase(flt))
            return null;

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        Empbenefitplan ebp = ebpBo.searchEbpById(ebpId);
        if (ebp == null)
            return null;

        ebp.decryEMPPlan(ebp);

        IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        List acctItems = itemsBo.getItemsByAcctversion(ebp.getEbpEsavId().getId());

        for (Iterator it = acctItems.iterator(); it.hasNext();) {
            Empsalaryacctitems item = (Empsalaryacctitems) it.next();
            try {
                Object value = PropertyUtils.getProperty(ebp, "ebpColumn" + item.getEsaiDataSeq());
                if (value != null)
                    item.setItemValue((BigDecimal) value);
                else
                    item.setItemValue(new BigDecimal(0.0D));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return acctItems;
    }

    public Empbenefitplan getEbpByEmpMonth(String empId, String yearMonth) {
        String flt = DWRUtil.checkAuth("getEbpByEmpMonth", "getEbpByEmpMonth");
        if ("error".equalsIgnoreCase(flt)) {
            return null;
        }
        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        Empbenefitplan ebp = ebpBo.searchEmpbenefitByEmpMonth(empId, yearMonth);

        return ebp;
    }

    public List<Empbenefitplan> getEbpByEmpMonths(String empId, String[] yearMonthArr) {
        String flt = DWRUtil.checkAuth("getEbpByEmpMonth", "getEbpByEmpMonth");
        if ("error".equalsIgnoreCase(flt)) {
            return null;
        }
        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        List ebpList = ebpBo.searchEbpByEmpMonths(empId, yearMonthArr);

        return ebpList;
    }

    public Empbenefitplan modifyEbp(String ebpId, String columns, String modiComments) {
        String flt = DWRUtil.checkAuth("modifyEbp", "modifyEbp");
        if ("error".equalsIgnoreCase(flt)) {
            return null;
        }

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        Empbenefitplan ebp = ebpBo.searchEbpById(ebpId);
        String[] values = columns.split(",");
        List valueList = new ArrayList();
        for (int i = 0; i < values.length; ++i) {
            DecimalFormat df = new DecimalFormat("#.00");
            valueList.add(new BigDecimal(df.format(Double.parseDouble(values[i]))));
        }

        IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        List<Empsalaryacctitems> acctItems = itemsBo.getItemsByAcctversion(ebp.getEbpEsavId()
                .getId());
        Class ownerClass = ebp.getClass();
        Method ebpMethod = null;
        int index = 0;
        for (Empsalaryacctitems item : acctItems) {
            try {
                ebpMethod = ownerClass.getMethod("setEbpColumn" + item.getEsaiDataSeq(),
                                                 new Class[] { BigDecimal.class });
                ebpMethod.invoke(ebp, new Object[] { valueList.get(index) });
            } catch (Exception e) {
                e.printStackTrace();
            }
            ++index;
        }
        ebp.setEbpLastChangeBy(getCurrentEmp().getId());

        ebp.setEbpLastChangeTime(new Date());
        ebp.setEbpComments(modiComments);
        Empbenefitplan returnEbp = null;
        try {
            returnEbp = (Empbenefitplan) ebp.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        ebp.encryEMPPlan(ebp);
        ebpBo.updateEbp(ebp);

        returnEbp = ebpBo.calcEbpForPage(returnEbp, acctItems);
        return returnEbp;
    }

    public String getEmpStatusName(int id) {
        for (Statusconf statusconf : this.empStatus) {
            if (statusconf.getId().getStatusconfNo().intValue() == id) {
                return statusconf.getStatusconfDesc();
            }
        }

        return "无状态";
    }

    public String getEspStatus(String yearMonth) {
        yearMonth = yearMonth.replaceAll("-", "");
        IEmpsalaryperiodBo periodBo = (IEmpsalaryperiodBo) getBean("empsalaryperiodBO");
        Empsalaryperiod period = periodBo.loadEspdStatus(yearMonth);
        if ((period == null) || (period.getEspdStatus().intValue() == 0)) {
            return "true";
        }
        return "false";
    }

    public List<Location> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<BenefitType> getEbfTypeList() {
        return this.ebfTypeList;
    }

    public void setEbfTypeList(List<BenefitType> ebfTypeList) {
        this.ebfTypeList = ebfTypeList;
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public String getEmpCondition() {
        return this.empCondition;
    }

    public void setEmpCondition(String empCondition) {
        this.empCondition = empCondition;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getYearMonth() {
        return this.yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public List<Empbenefitplan> getEbpList() {
        return this.ebpList;
    }

    public void setEbpList(List<Empbenefitplan> ebpList) {
        this.ebpList = ebpList;
    }

    public List<Statusconf> getEmpStatus() {
        return this.empStatus;
    }

    public void setEmpStatus(List<Statusconf> empStatus) {
        this.empStatus = empStatus;
    }

    public String getEbpId() {
        return this.ebpId;
    }

    public void setEbpId(String ebpId) {
        this.ebpId = ebpId;
    }

    public String getCanEdit() {
        return this.canEdit;
    }

    public void setCanEdit(String canEdit) {
        this.canEdit = canEdit;
    }

    public String getAddyearmonth1() {
        return this.addyearmonth1;
    }

    public void setAddyearmonth1(String addyearmonth1) {
        this.addyearmonth1 = addyearmonth1;
    }

    public String getAddyearmonth2() {
        return this.addyearmonth2;
    }

    public void setAddyearmonth2(String addyearmonth2) {
        this.addyearmonth2 = addyearmonth2;
    }

    public String getAddyearmonth3() {
        return this.addyearmonth3;
    }

    public void setAddyearmonth3(String addyearmonth3) {
        this.addyearmonth3 = addyearmonth3;
    }

    public String getEbpHousingAmount1() {
        return this.ebpHousingAmount1;
    }

    public void setEbpHousingAmount1(String ebpHousingAmount1) {
        this.ebpHousingAmount1 = ebpHousingAmount1;
    }

    public String getEbpHousingAmount2() {
        return this.ebpHousingAmount2;
    }

    public void setEbpHousingAmount2(String ebpHousingAmount2) {
        this.ebpHousingAmount2 = ebpHousingAmount2;
    }

    public String getEbpHousingAmount3() {
        return this.ebpHousingAmount3;
    }

    public void setEbpHousingAmount3(String ebpHousingAmount3) {
        this.ebpHousingAmount3 = ebpHousingAmount3;
    }

    public String getEbpInsuranceAmount1() {
        return this.ebpInsuranceAmount1;
    }

    public void setEbpInsuranceAmount1(String ebpInsuranceAmount1) {
        this.ebpInsuranceAmount1 = ebpInsuranceAmount1;
    }

    public String getEbpInsuranceAmount2() {
        return this.ebpInsuranceAmount2;
    }

    public void setEbpInsuranceAmount2(String ebpInsuranceAmount2) {
        this.ebpInsuranceAmount2 = ebpInsuranceAmount2;
    }

    public String getEbpInsuranceAmount3() {
        return this.ebpInsuranceAmount3;
    }

    public void setEbpInsuranceAmount3(String ebpInsuranceAmount3) {
        this.ebpInsuranceAmount3 = ebpInsuranceAmount3;
    }

    public String getEbpPensionAmount1() {
        return this.ebpPensionAmount1;
    }

    public void setEbpPensionAmount1(String ebpPensionAmount1) {
        this.ebpPensionAmount1 = ebpPensionAmount1;
    }

    public String getEbpPensionAmount2() {
        return this.ebpPensionAmount2;
    }

    public void setEbpPensionAmount2(String ebpPensionAmount2) {
        this.ebpPensionAmount2 = ebpPensionAmount2;
    }

    public String getEbpPensionAmount3() {
        return this.ebpPensionAmount3;
    }

    public void setEbpPensionAmount3(String ebpPensionAmount3) {
        this.ebpPensionAmount3 = ebpPensionAmount3;
    }

    public String getEmpId() {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getMemo1() {
        return this.memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getMemo2() {
        return this.memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    public String getMemo3() {
        return this.memo3;
    }

    public void setMemo3(String memo3) {
        this.memo3 = memo3;
    }

    public String getAddyearmonth4() {
        return this.addyearmonth4;
    }

    public void setAddyearmonth4(String addyearmonth4) {
        this.addyearmonth4 = addyearmonth4;
    }

    public String getAddyearmonth5() {
        return this.addyearmonth5;
    }

    public void setAddyearmonth5(String addyearmonth5) {
        this.addyearmonth5 = addyearmonth5;
    }

    public String getEbpHousingAmount4() {
        return this.ebpHousingAmount4;
    }

    public void setEbpHousingAmount4(String ebpHousingAmount4) {
        this.ebpHousingAmount4 = ebpHousingAmount4;
    }

    public String getEbpHousingAmount5() {
        return this.ebpHousingAmount5;
    }

    public void setEbpHousingAmount5(String ebpHousingAmount5) {
        this.ebpHousingAmount5 = ebpHousingAmount5;
    }

    public String getEbpInsuranceAmount4() {
        return this.ebpInsuranceAmount4;
    }

    public void setEbpInsuranceAmount4(String ebpInsuranceAmount4) {
        this.ebpInsuranceAmount4 = ebpInsuranceAmount4;
    }

    public String getEbpInsuranceAmount5() {
        return this.ebpInsuranceAmount5;
    }

    public void setEbpInsuranceAmount5(String ebpInsuranceAmount5) {
        this.ebpInsuranceAmount5 = ebpInsuranceAmount5;
    }

    public String getEbpPensionAmount4() {
        return this.ebpPensionAmount4;
    }

    public void setEbpPensionAmount4(String ebpPensionAmount4) {
        this.ebpPensionAmount4 = ebpPensionAmount4;
    }

    public String getEbpPensionAmount5() {
        return this.ebpPensionAmount5;
    }

    public void setEbpPensionAmount5(String ebpPensionAmount5) {
        this.ebpPensionAmount5 = ebpPensionAmount5;
    }

    public String getMemo4() {
        return this.memo4;
    }

    public void setMemo4(String memo4) {
        this.memo4 = memo4;
    }

    public String getMemo5() {
        return this.memo5;
    }

    public void setMemo5(String memo5) {
        this.memo5 = memo5;
    }

    public String getAddyearmonth6() {
        return this.addyearmonth6;
    }

    public void setAddyearmonth6(String addyearmonth6) {
        this.addyearmonth6 = addyearmonth6;
    }

    public String getEbpHousingAmount6() {
        return this.ebpHousingAmount6;
    }

    public void setEbpHousingAmount6(String ebpHousingAmount6) {
        this.ebpHousingAmount6 = ebpHousingAmount6;
    }

    public String getEbpInsuranceAmount6() {
        return this.ebpInsuranceAmount6;
    }

    public void setEbpInsuranceAmount6(String ebpInsuranceAmount6) {
        this.ebpInsuranceAmount6 = ebpInsuranceAmount6;
    }

    public String getEbpPensionAmount6() {
        return this.ebpPensionAmount6;
    }

    public void setEbpPensionAmount6(String ebpPensionAmount6) {
        this.ebpPensionAmount6 = ebpPensionAmount6;
    }

    public String getMemo6() {
        return this.memo6;
    }

    public void setMemo6(String memo6) {
        this.memo6 = memo6;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.SearchEbpAction JD-Core Version: 0.5.4
 */