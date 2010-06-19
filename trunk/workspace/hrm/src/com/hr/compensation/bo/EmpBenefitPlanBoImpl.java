package com.hr.compensation.bo;

import com.hr.compensation.dao.IEmpBenefitDao;
import com.hr.compensation.domain.Empbenefit;
import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import com.hr.util.StringUtil;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.ObjectUtils;

public class EmpBenefitPlanBoImpl implements IEmpBenefitPlanBo {
    private static final Logger logger = Logger.getLogger(EmpBenefitBoImpl.class);
    private IEmpBenefitDao empBenefitDao;

    public List<Empbenefitplan> findEbp(DetachedCriteria dc, Pager page, String searchOrExport) {
        List<Empbenefitplan> ebpList = null;

        if ((page == null) || ("export".equals(searchOrExport))) {
            ebpList = this.empBenefitDao.findByCriteria(dc);
        } else {
            page.splitPage(dc);
            ebpList = this.empBenefitDao.findByCriteria(dc, page.getPageSize(), page
                    .getCurrentPage());
        }

        if ((ebpList != null) && (ebpList.size() > 0)) {
            for (Empbenefitplan ebp : ebpList) {
                ebp.decryEMPPlan(ebp);
            }
        }
        return ebpList;
    }

    public void calcEbp(List<Empbenefitplan> ebpList) {
        if ((ebpList == null) || (ebpList.size() > 0))
            return;

        IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        Map acctItemsMap = itemsBo.getItemsByEmpbenefitplan(ebpList);

        for (Empbenefitplan ebp : ebpList)
            ebp = calcEbpForPage(ebp, (List) acctItemsMap.get(ebp.getEbpEsavId().getId()));
    }

    public void processDataForExport(List<Empbenefitplan> ebpList,
            List<Empsalarydatadef> dataDefList) {
        IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        Map acctItemsMap = itemsBo.getItemsByEmpbenefitplan(ebpList);

        List acctItems = null;
        if (ebpList != null)
            for (Empbenefitplan ebp : ebpList) {
                acctItems = (List) acctItemsMap.get(ebp.getEbpEsavId().getId());
                generateOutputList(ebp, dataDefList, acctItems);
            }
    }

    private void generateOutputList(Empbenefitplan ebp, List<Empsalarydatadef> dataDefList,
            List<Empsalaryacctitems> acctItems) {
        Map outPutList = new HashMap();

        Class ebpClass = Empbenefitplan.class;
        Method ebpMethod = null;
        BigDecimal itemValue = null;
        boolean find = false;
        for (Empsalarydatadef datadef : dataDefList) {
            find = false;
            for (Empsalaryacctitems item : acctItems) {
                if (datadef.getEsddId().equals(item.getEsaiEsdd().getEsddId())) {
                    try {
                        ebpMethod = ebpClass.getMethod("getEbpColumn" + item.getEsaiDataSeq(),
                                                       new Class[0]);
                        itemValue = (BigDecimal) ebpMethod.invoke(ebp, new Object[0]);

                        outPutList.put(datadef.getEsddId(), itemValue);

                        find = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            if (!find) {
                outPutList.put(datadef.getEsddId(), new BigDecimal(0.0D));
            }

        }

        ebp.setOutPutList(outPutList);
    }

    public Empbenefitplan calcEbpForPage(Empbenefitplan ebp, List<Empsalaryacctitems> items) {
        List sumList = new ArrayList();
        for (int i = 0; i <= 20; ++i) {
            sumList.add(new BigDecimal(0));
        }

        Class ebpClass = ebp.getClass();
        Method ebpMethod = null;
        BigDecimal temp = null;
        Empsalaryacctitems esai = null;
        int i = 0;
        for (int j = items.size(); i < j; ++i) {
            esai = (Empsalaryacctitems) items.get(i);
            try {
                ebpMethod = ebpClass
                        .getMethod("getEbpColumn" + esai.getEsaiDataSeq(), new Class[0]);
                temp = (BigDecimal) ebpMethod.invoke(ebp, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (temp != null) {
                sumList.set(esai.getEsaiEsdd().getEsddDataType().intValue(), ((BigDecimal) sumList
                        .get(esai.getEsaiEsdd().getEsddDataType().intValue())).add(temp));
            }
        }
        ebp.setShowColumn15((BigDecimal) sumList.get(15));
        ebp.setShowColumn16((BigDecimal) sumList.get(16));

        return ebp;
    }

    public List<Empbenefitplan> searchEbpByEmpandMonth(String empId, String yearMonth,
            String[] yearmonthArr) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefitplan.class);
        detachedCriteria.add(Restrictions.eq(Empbenefitplan.PROP_EBP_STATUS, new Integer(1)));
        detachedCriteria.add(Restrictions.eq("ebpEmpno.id", empId));
        detachedCriteria.add(Restrictions.eq(Empbenefitplan.PROP_EBP_YEARMONTH, yearMonth));
        detachedCriteria.add(Restrictions
                .in(Empbenefitplan.PROP_EBP_BELONG_YEARMONTH, yearmonthArr));
        List ebpList = this.empBenefitDao.findByCriteria(detachedCriteria);

        return ebpList;
    }

    public Map<String, Empbenefitplan> findEbpYMs(String[] yearMonths, Integer status,
            boolean isSum, String[] empIds) {
        List ebpList = findEbpBatch(yearMonths, null, status, empIds);
        if ((ebpList == null) || (ebpList.size() == 0))
            return null;
        if ((ebpList == null) || (ebpList.size() == 0))
            return null;

        return generateEbpMap(ebpList, true, isSum);
    }

    public Map<String, Empbenefitplan> findEbpBelongYMs(String[] belongYearMonths, Integer status,
            boolean isSum, String[] empIds) {
        List ebpList = findEbpBatch(null, belongYearMonths, status, empIds);
        if ((ebpList == null) || (ebpList.size() == 0))
            return null;

        return generateEbpMap(ebpList, false, isSum);
    }

    private Map<String, Empbenefitplan> generateEbpMap(List<Empbenefitplan> ebpList,
            boolean isYearMonth, boolean isSum) {
        if ((ebpList == null) || (ebpList.size() == 0))
            return null;

        Map ebpMap = new HashMap();
        String key = null;
        for (Empbenefitplan ebp : ebpList) {
            if (isYearMonth)
                key = ebp.getEbpYearMonth() + " " + ebp.getEbpEmpno().getEmpDistinctNo() + " "
                        + ebp.getEbpEmpno().getEmpName();
            else {
                key = ebp.getEbpBelongYearmonth() + " " + ebp.getEbpEmpno().getEmpDistinctNo()
                        + " " + ebp.getEbpEmpno().getEmpName();
            }

            if (!isSum) {
                ebpMap.put(key, ebp);
            } else {
                Empbenefitplan ebpSum = (Empbenefitplan) ebpMap.get(key);
                if (ebpSum == null)
                    ebpSum = new Empbenefitplan();
                ebpSum = sumEbpYMs(ebp, ebpSum);
                ebpMap.put(key, ebpSum);
            }
        }
        return ebpMap;
    }

    private List<Empbenefitplan> findEbpBatch(String[] yearMonths, String[] belongYearMonths,
            Integer status, String[] empIds) {
        if (empIds == null)
            return null;

        DetachedCriteria dc = DetachedCriteria.forClass(Empbenefitplan.class);
        if (status != null)
            dc.add(Restrictions.eq(Empbenefitplan.PROP_EBP_STATUS, status));
        dc.add(Restrictions.in("ebpEmpno.id", empIds));
        if (yearMonths != null) {
            dc.add(Restrictions.in(Empbenefitplan.PROP_EBP_YEARMONTH, yearMonths));
            dc.addOrder(Order.asc(Empbenefitplan.PROP_EBP_YEARMONTH));
        } else {
            dc.add(Restrictions.in(Empbenefitplan.PROP_EBP_BELONG_YEARMONTH, belongYearMonths));
            dc.addOrder(Order.asc(Empbenefitplan.PROP_EBP_BELONG_YEARMONTH));
        }
        List<Empbenefitplan> ebpList = this.empBenefitDao.findByCriteria(dc);
        if ((ebpList == null) || (ebpList.size() == 0))
            return null;

        for (Empbenefitplan ebp : ebpList) {
            ebp.decryEMPPlan(ebp);
        }
        return ebpList;
    }

    private Empbenefitplan sumEbpYMs(Empbenefitplan ebp, Empbenefitplan ebpSum) {
        if (ebp == null)
            return ebpSum;
        if (ebpSum == null)
            return null;

        try {
            for (int i = 1; i <= 48; ++i) {
                Object ebpValue = PropertyUtils.getProperty(ebp, "ebpColumn" + i);
                Object ebpSumValue = PropertyUtils.getProperty(ebpSum, "ebpColumn" + i);
                if (ebpValue != null)
                    if (ebpSumValue == null) {
                        PropertyUtils.setProperty(ebpSum, "ebpColumn" + i, ebpValue);
                    } else {
                        BigDecimal value = ((BigDecimal) ebpValue).add((BigDecimal) ebpSumValue);
                        PropertyUtils.setProperty(ebpSum, "ebpColumn" + i, value.setScale(2));
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ebpSum;
    }

    public Empbenefitplan deductEbpYMs(Empbenefitplan ebp, Empbenefitplan ebpSum) {
        if (ebp == null)
            return ebpSum;
        ebp = reverseEbpYMs(ebp);
        return sumEbpYMs(ebp, ebpSum);
    }

    private Empbenefitplan reverseEbpYMs(Empbenefitplan ebp) {
        if (ebp == null)
            return null;
        try {
            for (int i = 1; i <= 48; ++i) {
                Object ebpValue = PropertyUtils.getProperty(ebp, "ebpColumn" + i);
                if (ebpValue != null) {
                    BigDecimal newValue = ((BigDecimal) ebpValue).multiply(new BigDecimal("-1.00"));
                    PropertyUtils.setProperty(ebp, "ebpColumn" + i, newValue.setScale(2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ebp;
    }

    public Empbenefitplan searchEbpById(String ebpId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefitplan.class);
        detachedCriteria.createAlias(Empbenefitplan.PROP_EBP_EMP_NO, "ebpEmpno", 1);
        detachedCriteria.createAlias("ebpEmpno.config", "config", 1);
        detachedCriteria.add(Restrictions.eq("ebpId", ebpId));
        List ebpList = this.empBenefitDao.findByCriteria(detachedCriteria);

        return ((ebpList != null) && (ebpList.size() > 0)) ? (Empbenefitplan) ebpList.get(0) : null;
    }

    public Empbenefitplan searchEmpbenefitByEmpMonth(String empId, String yearMonth) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefit.class);
        detachedCriteria.add(Restrictions.eq("employee.id", empId));
        String nextYearMonth = getNextMonth(yearMonth);

        LogicalExpression exp1 = Restrictions.and(Restrictions.isNull("ebfEndMonth"), Restrictions
                .le("ebfStartMonth", nextYearMonth));

        LogicalExpression exp21 = Restrictions.or(Restrictions.or(Restrictions
                .between("ebfStartMonth", yearMonth, nextYearMonth), Restrictions
                .between("ebfEndMonth", yearMonth, nextYearMonth)), Restrictions.and(Restrictions
                .lt("ebfStartMonth", yearMonth), Restrictions.gt("ebfEndMonth", nextYearMonth)));

        detachedCriteria.add(Restrictions.or(exp1, Restrictions.and(Restrictions
                .isNotNull("ebfEndMonth"), exp21)));
        List<Empbenefit> benefitList = this.empBenefitDao.findByCriteria(detachedCriteria);
        Empbenefitplan plan = null;
        for (Empbenefit empBenefit : benefitList) {
            if (isDateInPeriod(yearMonth, empBenefit.getEbfStartMonth(), empBenefit
                    .getEbfEndMonth())) {
                plan = new Empbenefitplan(empBenefit);
            }
        }
        return plan;
    }

    private String getNextMonth(String current) {
        Integer year = Integer.valueOf(current.substring(0, 4));
        Integer month = Integer.valueOf(current.substring(4, 6));
        Integer nextYear = Integer.valueOf(0);
        Integer nextMonth = Integer.valueOf(0);
        if (month.intValue() == 12) {
            nextMonth = Integer.valueOf(1);
            nextYear = Integer.valueOf(year.intValue() + 1);
        } else {
            nextMonth = Integer.valueOf(month.intValue() + 1);
            nextYear = year;
        }
        String nextYearMonth = nextYear
                + ((nextMonth.intValue() < 10) ? "0" + nextMonth : new StringBuilder()
                        .append(nextMonth).append("").toString());
        return nextYearMonth;
    }

    private boolean isDateInPeriod(String current, String dateStart, String dateEnd) {
        if (current.compareTo(dateStart) < 0) {
            return false;
        }

        return (!StringUtils.isNotEmpty(dateEnd)) || (current.compareTo(dateEnd) <= 0);
    }

    public List<Empbenefitplan> searchEbpByEmpMonths(String empId, String[] yearMonths) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefitplan.class);
        detachedCriteria.createAlias(Empbenefitplan.PROP_EBP_EMP_NO, "ebpEmpno", 1);
        detachedCriteria.add(Restrictions.eq("ebpEmpno.id", empId));
        detachedCriteria.add(Restrictions.in(Empbenefitplan.PROP_EBP_YEARMONTH, yearMonths));
        detachedCriteria.add(Restrictions.eq(Empbenefitplan.PROP_EBP_STATUS, new Integer(0)));
        List ebpList = this.empBenefitDao.findByCriteria(detachedCriteria);

        return ebpList;
    }

    public Empbenefitplan searchInitEbpByEmpMonth(String empId, String yearMonth) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefitplan.class);
        detachedCriteria.createAlias(Empbenefitplan.PROP_EBP_EMP_NO, "ebpEmpno", 1);
        detachedCriteria.add(Restrictions.eq("ebpEmpno." + Employee.PROP_ID, empId));
        detachedCriteria.add(Restrictions.eq(Empbenefitplan.PROP_EBP_YEARMONTH, yearMonth));
        detachedCriteria.add(Restrictions.eq(Empbenefitplan.PROP_EBP_STATUS, new Integer(0)));
        List ebpList = this.empBenefitDao.findByCriteria(detachedCriteria);
        if ((ebpList != null) && (ebpList.size() > 0)) {
            Empbenefitplan initEbp = (Empbenefitplan) ebpList.get(0);
            initEbp.decryEMPPlan(initEbp);
            return initEbp;
        }
        return null;
    }

    public String batchSaveEbp(List<Empbenefitplan> ebpList) {
        this.empBenefitDao.saveOrupdate(ebpList);

        return "succ";
    }

    public boolean setAddEbpSum(String yearMonth, Empsalarypay[] payArray) {
        if (ObjectUtils.isEmpty(payArray))
            return false;

        String[] empList = new String[payArray.length];
        for (int i = 0; i < payArray.length; ++i) {
            empList[i] = payArray[i].getEspEmpno().getId();
        }

        Map ebpMap = getSumEbpsOfMonth(yearMonth, empList);
        for (Empsalarypay pay : payArray) {
            Empbenefitplan additionalPlan = (Empbenefitplan) ebpMap.get(pay.getEspEmpno().getId()
                    + "" + yearMonth);
            pay.setAddBenefitPlan(additionalPlan);

            if (additionalPlan == null)
                pay.setEspBenefitPlans(Integer.valueOf(0));
            else
                pay.setEspBenefitPlans(additionalPlan.getEmpMonthCount());
        }
        return true;
    }

    public Map<String, Empbenefitplan> getSumEbpsOfMonth(String yearMonth, String[] empIds) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefitplan.class);
        detachedCriteria.createAlias(Empbenefitplan.PROP_EBP_EMP_NO, "ebpEmpno", 1);
        if ((empIds != null) && (empIds.length > 0)) {
            detachedCriteria.add(Restrictions.in("ebpEmpno.id", empIds));
        }
        detachedCriteria.add(Restrictions.eq(Empbenefitplan.PROP_EBP_YEARMONTH, yearMonth));
        detachedCriteria.add(Restrictions.eq(Empbenefitplan.PROP_EBP_STATUS, new Integer(1)));
        List<Empbenefitplan> ebpList = this.empBenefitDao.findByCriteria(detachedCriteria);

        Map monthEbpsMap = new HashMap();
        String key = null;
        Empbenefitplan currPlan = null;
        Empbenefitplan tempPlan = null;
        for (Empbenefitplan plan : ebpList) {
            try {
                tempPlan = (Empbenefitplan) plan.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            tempPlan.decryEMPPlan(tempPlan);
            key = tempPlan.getEbpEmpno().getId() + "" + tempPlan.getEbpYearMonth();

            currPlan = (Empbenefitplan) monthEbpsMap.get(key);
            if (currPlan == null) {
                monthEbpsMap.put(key, tempPlan);
                tempPlan.setEmpMonthCount(Integer
                        .valueOf(tempPlan.getEmpMonthCount().intValue() + 1));
            }

            addToCurrPlan(currPlan, tempPlan);
            currPlan.setEmpMonthCount(Integer.valueOf(currPlan.getEmpMonthCount().intValue() + 1));
        }
        return monthEbpsMap;
    }

    public Map<String, Empbenefitplan> getSumEbpsOfAllMonth(String empId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefitplan.class);
        detachedCriteria.createAlias(Empbenefitplan.PROP_EBP_EMP_NO, "ebpEmpno", 1);
        detachedCriteria.add(Restrictions.eq("ebpEmpno.id", empId));
        List<Empbenefitplan> ebpList = this.empBenefitDao.findByCriteria(detachedCriteria);

        Map monthEbpsMap = new HashMap();
        String key = null;
        Empbenefitplan currPlan = null;
        Empbenefitplan tempPlan = null;
        for (Empbenefitplan plan : ebpList) {
            try {
                tempPlan = (Empbenefitplan) plan.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            tempPlan.decryEMPPlan(tempPlan);
            key = tempPlan.getEbpEmpno().getId() + "" + tempPlan.getEbpYearMonth();

            currPlan = (Empbenefitplan) monthEbpsMap.get(key);
            if (currPlan == null) {
                monthEbpsMap.put(key, tempPlan);
            }

            addToCurrPlan(currPlan, tempPlan);
        }
        return monthEbpsMap;
    }

    public List<Empbenefitplan> sumEbpList(List<Empbenefitplan> ebpList, String searchType) {
        if ((ebpList == null) || (ebpList.size() == 0)) {
            return null;
        }

        Map sumEbpsMap = new HashMap();
        String key = null;
        Empbenefitplan currPlan = null;
        Empbenefitplan tempPlan = null;

        Integer status = null;
        for (Empbenefitplan plan : ebpList) {
            tempPlan = plan;

            if ("yearMonth".equals(searchType)) {
                key = tempPlan.getEbpEmpno().getId() + tempPlan.getEbpYearMonth();
                tempPlan.setEbpBelongYearmonth(null);
                status = new Integer(2);
            }
            if ("belongYearMonth".equals(searchType)) {
                key = tempPlan.getEbpEmpno().getId() + tempPlan.getEbpBelongYearmonth();
                tempPlan.setEbpYearMonth(null);
                status = new Integer(3);
            }

            currPlan = (Empbenefitplan) sumEbpsMap.get(key);
            if (currPlan == null) {
                sumEbpsMap.put(key, tempPlan);
                tempPlan.setEbpStatus(status);
            }

            addToCurrPlan(currPlan, tempPlan);
            currPlan.setEbpStatus(status);
        }

        List result = new ArrayList();
        result.addAll(sumEbpsMap.values());

        return result;
    }

    private void addToCurrPlan(Empbenefitplan currPlan, Empbenefitplan plan) {
        Class planClass = currPlan.getClass();
        Method plangetMethod = null;
        Method plansetMethod = null;
        BigDecimal planBD1 = null;
        BigDecimal planBD2 = null;
        try {
            for (int i = 1; i <= 48; ++i) {
                plansetMethod = planClass.getMethod("setEbpColumn" + i,
                                                    new Class[] { BigDecimal.class });
                plangetMethod = planClass.getMethod("getEbpColumn" + i, new Class[0]);
                planBD1 = (BigDecimal) plangetMethod.invoke(currPlan, new Object[0]);
                planBD2 = (BigDecimal) plangetMethod.invoke(plan, new Object[0]);
                if (planBD1 == null)
                    continue;
                if (planBD2 == null) {
                    continue;
                }
                plansetMethod.invoke(currPlan, new Object[] { planBD1.add(planBD2) });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addCurrPlan(String empId, Empsalarypay pay) {
        Empbenefitplan addPlanSum = pay.getAddBenefitPlan();
        Empbenefitplan plan = new Empbenefitplan();
        Integer plans = pay.getEspBenefitPlans();

        plan.setEbpEsavId(pay.getEspEsavId());
        plan.setEbpEmpno(pay.getEspEmpno());
        plan.setEbpYearMonth(pay.getEspYearmonth());
        plan.setEbpBelongYearmonth(pay.getEspYearmonth());
        plan.setEbpStatus(new Integer(0));
        plan.setEbpCreateBy(empId);
        plan.setEbpCreateTime(new Date());
        plan.setEbpLastChangeBy(empId);
        plan.setEbpLastChangeTime(plan.getEbpCreateTime());

        Empbenefit benefit = pay.getEspEmpno().getBenefit();
        if (benefit != null) {
            plan.setEbpHousingAmountb(benefit.getEbfHousingAmount());
            plan.setEbpInsuranceAmountb(benefit.getEbfInsuranceAmount());
            plan.setEbpPensionAmountb(benefit.getEbfPensionAmount());
            plan.setEbpBelongYearmonth(benefit.getBelongYearMonth());
        }

        boolean hasBenefit = false;
        for (Empsalaryacctitems item : pay.getAcctItems()) {
            if ((item.getEsaiEsdd().getEsddDataType().intValue() >= 10)
                    && (item.getEsaiEsdd().getEsddDataType().intValue() <= 16)) {
                try {
                    BigDecimal benefitItemBD = new BigDecimal("0.00");
                    Object value = PropertyUtils.getProperty(pay, "espColumn"
                            + item.getEsaiDataSeq());
                    if (value != null)
                        benefitItemBD = (BigDecimal) value;

                    if (addPlanSum != null) {
                        Object valueAdd = PropertyUtils.getProperty(addPlanSum, "ebpColumn"
                                + item.getEsaiDataSeq());
                        if (valueAdd != null)
                            benefitItemBD = benefitItemBD.subtract((BigDecimal) valueAdd);
                    }
                    PropertyUtils.setProperty(plan, "ebpColumn" + item.getEsaiDataSeq(),
                                              benefitItemBD);
                    hasBenefit = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (hasBenefit) {
            pay.setBenefitPlan(plan);
            if (plans == null)
                pay.setEspBenefitPlans(Integer.valueOf(1));
            else {
                pay.setEspBenefitPlans(Integer.valueOf(plans.intValue() + 1));
            }
            return true;
        }
        pay.setBenefitPlan(null);
        pay.setEspBenefitPlans(Integer.valueOf(0));
        return false;
    }

    public boolean deleteEbpById(String ebpId) {
        String hql = "delete from Empbenefitplan where id='" + ebpId + "'";
        this.empBenefitDao.exeHql(hql);
        return true;
    }

    public boolean deleteEbpByYm(String yearMonth, Integer status, String[] empIds) {
        String hql = "delete from Empbenefitplan where";
        if ((empIds != null) && (empIds.length > 0)) {
            hql = hql + " ebpEmpno.id in (" + StringUtil.splitForIn(empIds) + ") and ";
        }
        if (status == null)
            hql = hql + " ebpYearMonth='" + yearMonth + "'";
        else if ((status.intValue() == 0) || (status.intValue() == 1))
            hql = hql + " ebpYearMonth='" + yearMonth + "' and ebpStatus=" + status;
        else
            return false;

        this.empBenefitDao.exeHql(hql);
        return true;
    }

    public Integer getBeneAddCount(String yearMonth) {
        List result = new ArrayList();
        String hql = "select count(*) from Empbenefitplan where ebpYearMonth='" + yearMonth
                + "' and ebpStatus=1";
        result = this.empBenefitDao.exeHqlList(hql);
        return Integer.valueOf(Integer.parseInt(result.get(0).toString()));
    }

    public List<String> getAllYears() {
        String hql = "select distinct substring(ebpYearMonth,1,4) from Empbenefitplan";
        List years = this.empBenefitDao.exeHqlList(hql);
        if (years.size() == 0) {
            return new ArrayList();
        }
        return years;
    }

    public List<Empbenefitplan> getEbpList(String yearMonth, String empId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefitplan.class);
        detachedCriteria.add(Restrictions.eq("ebpYearMonth", yearMonth));
        detachedCriteria.add(Restrictions.eq("ebpEmpno.id", empId));
        detachedCriteria.addOrder(Order.desc("ebpBelongYearmonth"));
        List ebpList = this.empBenefitDao.findByCriteria(detachedCriteria);
        return ebpList;
    }

    public boolean updateEbp(Empbenefitplan ebp) {
        this.empBenefitDao.updateObject(ebp);
        return true;
    }

    public int hasBenefitPlanByAcctVersion(String versionId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefitplan.class);
        detachedCriteria.add(Restrictions.eq("ebpEsavId.id", versionId));
        detachedCriteria.setProjection(Projections.rowCount());
        List list = this.empBenefitDao.findByCriteria(detachedCriteria);
        return ((Integer) list.get(0)).intValue();
    }

    public IEmpBenefitDao getEmpBenefitDao() {
        return this.empBenefitDao;
    }

    public void setEmpBenefitDao(IEmpBenefitDao empBenefitDao) {
        this.empBenefitDao = empBenefitDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.EmpBenefitPlanBoImpl JD-Core Version: 0.5.4
 */