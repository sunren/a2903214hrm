package com.hr.compensation.bo;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.Status;
import com.hr.compensation.action.SalaryPaidConverge;
import com.hr.compensation.dao.ISalaryPaidDAO;
import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.compensation.domain.Empsalaryperiod;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emailsend;
import com.hr.examin.bo.ExaminDateUtil;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class SalaryPaidBoImpl implements ISalaryPaidBo, Status, Constants {
    private ISalaryPaidDAO salaryPaidDAO;

    public List getObjects(Class clas, String[] fetch) {
        List result = this.salaryPaidDAO.getObjects(clas, fetch);
        return result;
    }

    public Empsalarypay loadSalaryPay(Object id, String[] fetch) {
        return (Empsalarypay) this.salaryPaidDAO.loadObject(Empsalarypay.class, id, fetch,
                                                            new boolean[0]);
    }

    public boolean saveOrupdateObject(Object obj) {
        try {
            this.salaryPaidDAO.saveOrupdate(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public <T> boolean saveOrupdate(Collection<T> objs) {
        try {
            this.salaryPaidDAO.saveOrupdate(objs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Empsalarypay> findByCriteria(DetachedCriteria dcPay) {
        return this.salaryPaidDAO.findByCriteria(dcPay);
    }

    public List<Empsalarypay> findPay(DetachedCriteria dcPay, Pager page) {
        if (page == null) {
            BaseAction.addOrders(dcPay, null, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO
                    + "-up" });
            return this.salaryPaidDAO.findByCriteria(dcPay);
        }
        BaseAction.addOrders(dcPay, page, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO
                + "-up" });
        page.splitPage(dcPay);
        return this.salaryPaidDAO.findByCriteria(dcPay, page.getPageSize(), page.getCurrentPage());
    }

    public List<Empsalarypay> findPayPaid(DetachedCriteria dcPay, Pager page) {
        List<Empsalarypay> espList = new ArrayList();

        if (page == null) {
            BaseAction.addOrders(dcPay, null, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO
                    + "-up" });
            espList = this.salaryPaidDAO.findByCriteria(dcPay);
        } else {
            BaseAction.addOrders(dcPay, page, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO
                    + "-up" });
            page.splitPage(dcPay);
            espList = this.salaryPaidDAO.findByCriteria(dcPay, page.getPageSize(), page
                    .getCurrentPage());
        }

        if ((espList == null) || (espList.size() == 0))
            return null;

        IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        itemsBo.setAcctItemsPay(espList);

        for (Empsalarypay pay : espList) {
            try {
                pay.decryEMPPaid(pay);
                calSalaryPayforPage(pay, pay.getAcctItems());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return espList;
    }

    public List<Employee> findPayNoPaid(DetachedCriteria dcPay, DetachedCriteria dcEmp, Pager page) {
        BaseAction.addOrders(dcPay, null, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO
                + "-up" });
        List<Empsalarypay> espList = this.salaryPaidDAO.findByCriteria(dcPay);

        List<Employee> result = new ArrayList();

        if ((StringUtils.isNotEmpty(page.getOrder()))
                && ("esavEsac.esacName".equalsIgnoreCase(page.getOrder()
                        .substring(0, page.getOrder().indexOf("-"))))) {
            page.setOrder("");
        }

        List<Employee> empList = searchEmployee(dcEmp, page);
        boolean hasPaid = false;
        for (Employee currEmp : empList) {
            hasPaid = false;
            for (Empsalarypay esp : espList) {
                if ((currEmp != null) && (currEmp.getId() != null)
                        && (currEmp.getId().equals(esp.getEspEmpno().getId()))) {
                    hasPaid = true;
                    break;
                }
            }
            if (!hasPaid) {
                result.add(currEmp);
            }

        }

        String[] empIds = new String[result.size()];
        int i = 0;
        for (Employee currEmp : result) {
            empIds[i] = currEmp.getId();
            ++i;
        }

        if ((empIds == null) || (empIds.length == 0)) {
            page.setTotalPages(1);
            page.setTotalRows(0);
            return result;
        }

        dcEmp.add(Restrictions.in("id", empIds));
        return searchEmployee(dcEmp, page);
    }

    public List<Employee> searchEmployee(DetachedCriteria dcEmp, Pager page) {
        if (page == null) {
            BaseAction.addOrders(dcEmp, null,
                                 new String[] { Employee.PROP_EMP_DISTINCT_NO + "-up" });
            return this.salaryPaidDAO.findByCriteria(dcEmp);
        }
        BaseAction.addOrders(dcEmp, page, new String[] { Employee.PROP_EMP_DISTINCT_NO + "-up" });
        page.splitPage(dcEmp);
        return this.salaryPaidDAO.findByCriteria(dcEmp, page.getPageSize(), page.getCurrentPage());
    }

    public boolean haveBeneItems(List<Empsalaryacctitems> items) {
        if ((items == null) || (items.size() == 0))
            return false;
        for (Empsalaryacctitems item : items) {
            if ((item.getEsaiEsdd().getEsddDataType().intValue() >= 10)
                    && (item.getEsaiEsdd().getEsddDataType().intValue() <= 16)) {
                return true;
            }
        }

        return false;
    }

    public void addAddtionalEbpToPay(List<Empsalaryacctitems> acctItems, Empsalarypay pay,
            Empbenefitplan plan, boolean add0And1) {
        if (plan == null)
            return;
        try {
            for (Empsalaryacctitems item : acctItems) {
                if (item.getEsaiEsdd().getEsddDataType().intValue() < 10)
                    continue;
                if (item.getEsaiEsdd().getEsddDataType().intValue() > 16) {
                    continue;
                }
                if ((((item.getEsaiDataIsCalc().intValue() == 0) || (item.getEsaiDataIsCalc()
                        .intValue() == 1)))
                        && (!add0And1)) {
                    continue;
                }

                BigDecimal benefitItemBD = new BigDecimal("0.00");
                Object value = PropertyUtils.getProperty(pay, "espColumn" + item.getEsaiDataSeq());
                if (value != null)
                    benefitItemBD = (BigDecimal) value;

                if (plan != null) {
                    Object valueAdd = PropertyUtils.getProperty(plan, "ebpColumn"
                            + item.getEsaiDataSeq());
                    if (valueAdd != null)
                        benefitItemBD = benefitItemBD.add((BigDecimal) valueAdd);
                }
                PropertyUtils.setProperty(pay, "espColumn" + item.getEsaiDataSeq(), benefitItemBD);
                if (item.getEsaiDataIsCalc().intValue() == 2)
                    item.setEsaiDataIsCalc(Integer.valueOf(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean batchInitPaidSave(String yearmonth, List<Empsalarypay> payList) {
        if ((payList == null) || (payList.size() == 0))
            return false;

        List planList = new ArrayList();
        for (Empsalarypay pay : payList) {
            Empbenefitplan plan = pay.getBenefitPlan();
            if (plan != null) {
                plan.encryEMPPlan(plan);
                planList.add(pay.getBenefitPlan());
            }

            pay.encryEMPPaid(pay);

            Employee emp = pay.getEspEmpno();
            Empsalaryconfig config = emp.getConfig();
            pay.setEspEmpno(new Employee(emp.getId()));
            pay.setEspEmpconfig(new Empsalaryconfig(config.getId()));
        }

        deleteSalaryPaidByDate(yearmonth);

        Empsalaryperiod emppaidperiod = new Empsalaryperiod();
        emppaidperiod.setEspdYearmonth(yearmonth);
        emppaidperiod.setEspdStatus(Integer.valueOf(0));
        saveOrupdateObject(emppaidperiod);

        saveOrupdate(payList);
        if ((planList != null) && (planList.size() > 0)) {
            saveOrupdate(planList);
        }
        return true;
    }

    public String batchUpdatePay(String yearmonth, List<Empsalarypay> payList) {
        if ((payList == null) || (payList.size() == 0))
            return "";

        List planList = new ArrayList();
        Set empIds = new HashSet();
        empIds.add(((Empsalarypay) payList.get(0)).getEspEmpno().getId());
        StringBuffer strBuf = new StringBuffer("");
        for (Empsalarypay pay : payList) {
            Empbenefitplan plan = pay.getBenefitPlan();
            if (plan != null) {
                plan.encryEMPPlan(plan);
                planList.add(plan);
                empIds.add(pay.getEspEmpno().getId());
            }

            pay.encryEMPPaid(pay);

            strBuf.append(pay.getEspEmpno().getEmpName()).append("、");

            Employee emp = pay.getEspEmpno();
            Empsalaryconfig config = emp.getConfig();
            pay.setEspEmpno(new Employee(emp.getId()));
            pay.setEspEmpconfig(new Empsalaryconfig(config.getId()));
        }

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) SpringBeanFactory.getBean("empbenefitplanBo");
        ebpBo.deleteEbpByYm(yearmonth, Integer.valueOf(0), (String[]) empIds
                .toArray(new String[empIds.size()]));

        saveOrupdate(payList);
        if ((planList != null) && (planList.size() > 0)) {
            saveOrupdate(planList);
        }

        String str = new String("");
        if (strBuf.length() > 0) {
            str = str + strBuf.substring(0, strBuf.length() - 1);
        }
        return str;
    }

    public boolean batchUpdatePayImport(String yearmonth, List<Empsalarypay> payList) {
        if ((payList == null) || (payList.size() == 0))
            return false;

        List planList = new ArrayList();
        Set empIds = new HashSet();
        for (Empsalarypay pay : payList) {
            Employee emp = pay.getEspEmpno();
            Empsalaryconfig config = emp.getConfig();
            pay.setEspEmpno(new Employee(emp.getId()));
            pay.setEspEmpconfig(new Empsalaryconfig(config.getId()));
            Empbenefitplan tempPlan = pay.getBenefitPlan();
            if (tempPlan != null) {
                tempPlan.encryEMPPlan(tempPlan);
                planList.add(tempPlan);
            }
            empIds.add(emp.getId());
            pay.encryEMPPaid(pay);
        }

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) SpringBeanFactory.getBean("empbenefitplanBo");
        ebpBo.deleteEbpByYm(yearmonth, Integer.valueOf(0), (String[]) empIds
                .toArray(new String[empIds.size()]));

        saveOrupdate(payList);

        if ((planList != null) && (planList.size() > 0)) {
            saveOrupdate(planList);
        }
        return true;
    }

    public List<SalaryPaidConverge> findNeedApprovePaids(List departs, String yearmonth,
            Empsalaryperiod espd) {
        List result = new ArrayList();
        if (espd == null) {
            result.clear();
            return result;
        }

        String config = DatabaseSysConfigManager.getInstance().getProperty("sys.examin.month.sum");
        Date[] dateArr = ExaminDateUtil.getDateArray(yearmonth, config);

        Department depart = null;
        for (int i = 0; i < departs.size(); ++i) {
            SalaryPaidConverge salaryPaidConverge = new SalaryPaidConverge();
            depart = (Department) departs.get(i);

            salaryPaidConverge.setEmpDepartment(depart.getDepartmentName());

            salaryPaidConverge.setEmpNumber(getMonthEmpNumber(depart, dateArr[0], dateArr[1]));

            Date lastMStart = DateUtil.dateTimeAdd(dateArr[0], -1, 2);
            Date lastMEnd = DateUtil.dateAdd(dateArr[0], -1);
            salaryPaidConverge
                    .setLastMonthEmpNumber(getMonthEmpNumber(depart, lastMStart, lastMEnd));

            salaryPaidConverge.setQuitEmpNumber(getMonthQuitEmpNumber(depart, dateArr));

            salaryPaidConverge.setJoinEmpNumber(getMonthJoinEmpNumber(depart, dateArr));

            String year = yearmonth.substring(0, 4);
            int month = Integer.parseInt(yearmonth.substring(4));
            BigDecimal salaryAmount = getMonthSalaryAmount(depart, year, Integer.valueOf(month));
            salaryPaidConverge.setSalaryAmount(salaryAmount);
            BigDecimal lastMonthSalaryAmount;
            if (month == 1) {
                Integer lastMonth = Integer.valueOf(12);
                String lastYear = Integer.valueOf(Integer.parseInt(year) - 1).toString();
                lastMonthSalaryAmount = getMonthSalaryAmount(depart, lastYear, lastMonth);
            } else {
                lastMonthSalaryAmount = getMonthSalaryAmount(depart, year, Integer
                        .valueOf(month - 1));
            }
            salaryPaidConverge.setLastMonthSalaryAmount(lastMonthSalaryAmount);

            salaryPaidConverge.setSalaryDifference(salaryAmount.subtract(lastMonthSalaryAmount));

            result.add(salaryPaidConverge);
        }

        result.add(getTotalSarlayPaidConverge(result));

        return result;
    }

    public boolean deleteSalaryPaid(Empsalarypay salarypaid) {
        try {
            this.salaryPaidDAO.deleteObject(salarypaid);
            String hql = "delete from Empbenefitplan where ebpYearMonth='"
                    + salarypaid.getEspYearmonth() + "' " + " and ebpStatus=0 and ebpEmpno.id='"
                    + salarypaid.getEspEmpno().getId() + "'";

            this.salaryPaidDAO.exeHql(hql);
            return true;
        } catch (Exception e) {
            System.out.print(e);
        }
        return false;
    }

    private boolean deleteSalaryPaidByDate(String yearmonth) {
        try {
            this.salaryPaidDAO.exeHql("delete from Empsalarypay where espYearmonth = '" + yearmonth
                    + "'");

            this.salaryPaidDAO.exeHql("delete from Empbenefitplan where ebpYearMonth = '"
                    + yearmonth + "' and ebpStatus = 0");

            this.salaryPaidDAO.exeHql("delete from Empsalaryperiod where espdYearmonth='"
                    + yearmonth + "'");
        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
        return true;
    }

    public List<Empsalarypay> processForOutput(List<Empsalarypay> list,
            List<Empsalarydatadef> dataDefList) {
        Set versionIdSet = new HashSet();

        HashMap acctitemsMap = new HashMap();
        for (int i = 0; i < list.size(); ++i) {
            Empsalarypay esp = (Empsalarypay) list.get(i);
            try {
                calSalaryPayforOutput(esp, dataDefList, versionIdSet, acctitemsMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.set(i, esp);
        }

        Collections.sort(list, new Comparator() {
            public int compare(Object obj1, Object obj2) {
                Empsalarypay emp1 = (Empsalarypay) obj1;
                Empsalarypay emp2 = (Empsalarypay) obj2;
                return emp1.getEspDept().getId().compareTo(emp2.getEspDept().getId());
            }
        });
        return list;
    }

    public List<Empsalarypay> findMySalaryPaid(DetachedCriteria dc, Pager page) {
        if (page == null) {
            BaseAction.addOrders(dc, null,
                                 new String[] { Empsalarypay.PROP_ESP_YEARMONTH + "-down" });
            return this.salaryPaidDAO.findByCriteria(dc);
        }
        BaseAction.addOrders(dc, page, new String[] { Empsalarypay.PROP_ESP_YEARMONTH + "-down" });
        page.splitPage(dc);
        return this.salaryPaidDAO.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public List<Empsalarypay> searchMySalaryPaid(List<Empsalarypay> espList, String startYearMonth,
            String endYearMonth) {
        IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        Map acctItemsMap = itemsBo.getAcctItemsByPay(espList);

        IEmpsalaryperiodBo salaryperiod = (IEmpsalaryperiodBo) SpringBeanFactory
                .getBean("empsalaryperiodBO");
        List paidYearMonthList = salaryperiod.getPaidPeriod(new Integer(2), startYearMonth,
                                                            endYearMonth);

        for (int i = 0; i < espList.size();) {
            Empsalarypay esp = (Empsalarypay) espList.get(i);
            if (paidYearMonthList.contains(esp.getEspYearmonth())) {
                ++i;
            } else {
                espList.remove(esp);
                continue;
            }
            try {
                esp.decryEMPPaid(esp);
                esp.setYearAndMonth(esp.getEspYearmonth());
                calSalaryPayforPage(esp, (List) acctItemsMap.get(esp.getEspEsavId().getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return espList;
    }

    public String[][] getBeneItemsByPay(String payId) {
        Empsalarypay payOrig = loadSalaryPay(payId, new String[0]);
        Empsalarypay pay = null;
        try {
            pay = (Empsalarypay) payOrig.clone();
        } catch (CloneNotSupportedException e2) {
            e2.printStackTrace();
        }
        pay.decryEMPPaid(pay);

        Employee employee = pay.getEspEmpno();

        IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");

        List<Empsalaryacctitems> esacItems = itemsBo.getItemsByAcctversion(pay.getEspEsavId()
                .getId());

        int beneItemsCount = 0;
        List<Empsalaryacctitems> beneItems = new ArrayList();
        for (Empsalaryacctitems item : esacItems) {
            if ((item.getEsaiEsdd() != null)
                    && (item.getEsaiEsdd().getEsddDataType().intValue() >= 10)
                    && (item.getEsaiEsdd().getEsddDataType().intValue() <= 16)) {
                ++beneItemsCount;
                beneItems.add(item);
            }

        }

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) SpringBeanFactory.getBean("empbenefitplanBo");
        List<Empbenefitplan> ebpList = ebpBo.getEbpList(pay.getEspYearmonth(), employee.getId());

        String[][] result = new String[beneItemsCount + 1][ebpList.size() + 2];

        result[0][0] = "项目名称";
        int rowCount = 1;
        for (Empsalaryacctitems item : beneItems) {
            result[rowCount][0] = item.getEsaiEsdd().getEsddName();
            ++rowCount;
        }

        Class ownerClass = Empbenefitplan.class;

        BigDecimal ebpBD = null;
        result[0][1] = "汇总";
        rowCount = 1;
        for (Empsalaryacctitems item : beneItems) {
            try {
                result[rowCount][1] = "0.00";
            } catch (Exception e) {
                e.printStackTrace();
            }
            ++rowCount;
        }

        int monthColumn = 2;
        for (Empbenefitplan plan : ebpList) {
            plan.decryEMPPlan(plan);
            result[0][monthColumn] = plan.getEbpBelongYearmonth();

            rowCount = 1;
            for (Empsalaryacctitems item : beneItems) {
                try {
                    Method ebpMethod = ownerClass.getMethod("getEbpColumn" + item.getEsaiDataSeq(),
                                                            new Class[0]);

                    ebpBD = (BigDecimal) ebpMethod.invoke(plan, new Object[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result[rowCount][monthColumn] = ((ebpBD != null) ? ebpBD.toString() : "0.00");

                result[rowCount][1] = ((ebpBD != null) ? new BigDecimal(result[rowCount][1])
                        .add(ebpBD).toString() : result[rowCount][1]);

                ++rowCount;
            }

            ++monthColumn;
        }

        return result;
    }

    public Empsalarypay calSalaryPayforPage(Empsalarypay esp, List<Empsalaryacctitems> acctItems)
            throws Exception {
        if (esp == null)
            return null;

        List sumList = new ArrayList();
        for (int i = 0; i <= 20; ++i) {
            sumList.add(new BigDecimal("0.00"));
        }

        BigDecimal temp = new BigDecimal("0.00");
        BigDecimal temp1 = new BigDecimal("0.00");
        for (int i = 0; i < acctItems.size(); ++i) {
            Empsalaryacctitems esai = (Empsalaryacctitems) acctItems.get(i);

            temp = (BigDecimal) sumList.get(esai.getEsaiEsdd().getEsddDataType().intValue());
            temp1 = (BigDecimal) PropertyUtils.getProperty(esp, "espColumn" + (i + 1));
            sumList.set(esai.getEsaiEsdd().getEsddDataType().intValue(), temp.add(temp1));
        }

        esp.setShowColumn1((BigDecimal) sumList.get(1));
        esp.setShowColumn4((BigDecimal) sumList.get(4));
        esp.setShowColumn7((BigDecimal) sumList.get(7));
        esp.setShowColumn8((BigDecimal) sumList.get(8));
        esp.setShowColumn15((BigDecimal) sumList.get(15));
        esp.setShowColumn17((BigDecimal) sumList.get(17));
        esp.setShowColumn18((BigDecimal) sumList.get(18));
        esp.setShowColumn19((BigDecimal) sumList.get(19));

        return esp;
    }

    private Empsalarypay calSalaryPayforOutput(Empsalarypay esp,
            List<Empsalarydatadef> dataDefList, Set<String> versionIdSet,
            HashMap<String, Empsalaryacctitems> acctitemsMap) throws Exception {
        if (esp == null) {
            return null;
        }

        String versionId = esp.getEspEsavId().getId();

        if (versionIdSet.add(versionId)) {
            IEmpSalaryAcctitemsBo esaitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");

            List acctitemsList = esaitemsBo.getItemsByAcctversion(esp.getEspEsavId().getId());

            for (int i = 0; i < dataDefList.size(); ++i) {
                Empsalarydatadef datadef = (Empsalarydatadef) dataDefList.get(i);

                for (int j = 0; j < acctitemsList.size(); ++j) {
                    Empsalaryacctitems acctitems = (Empsalaryacctitems) acctitemsList.get(j);
                    if (acctitems.getEsaiEsdd().getEsddId().equals(datadef.getEsddId())) {
                        acctitemsMap.put(versionId + datadef.getEsddId(), acctitems);
                    }
                }
            }
        }

        Map outPutList = new HashMap();
        for (int i = 0; i < dataDefList.size(); ++i) {
            Empsalarydatadef datadef = (Empsalarydatadef) dataDefList.get(i);
            Empsalaryacctitems acctitems = (Empsalaryacctitems) acctitemsMap.get(versionId
                    + datadef.getEsddId());
            if (acctitems != null) {
                Class ownerClass = esp.getClass();
                Method espMethod = ownerClass
                        .getMethod("getEspColumn" + acctitems.getEsaiDataSeq(), new Class[0]);

                BigDecimal value = (BigDecimal) espMethod.invoke(esp, new Object[0]);
                outPutList.put(datadef.getEsddId(), value);
            } else {
                outPutList.put(datadef.getEsddId(), new BigDecimal(0.0D));
            }
        }
        esp.setOutPutList(outPutList);

        return esp;
    }

    private String calSalarypaidForURL(Empsalarypay esp,
            Hashtable<String, List<Empsalaryacctitems>> itemsTable) throws Exception {
        StringBuffer url = new StringBuffer();
        List itemsList = null;
        if ((itemsTable == null) || (itemsTable.get(esp.getEspEsavId().getId()) == null)) {
            IEmpSalaryAcctitemsBo esaitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");

            itemsList = esaitemsBo.getItemsByAcctversion(esp.getEspEsavId().getId());
            itemsTable.put(esp.getEspEsavId().getId(), itemsList);
        } else {
            itemsList = (List) itemsTable.get(esp.getEspEsavId().getId());
        }

        url.append(esp.getEspEmpno().getEmpName() + "，您好！<br>");
        url.append(esp.getEspYearmonth().substring(0, 4) + "年" + esp.getEspYearmonth().substring(4) + "月薪水发放信息，如下：<br><br>");

        url
                .append("<table border='1' width='100%' solidcellpadding=0  cellspacing=0 style='font-size: 10pt'>");

        url.append("<TBODY>");
        url.append("<TR>");
        url.append("<TD width='30%' bgcolor=#ECF6FB>员工姓名</TD>");
        url.append("<TD width='20%'>" + esp.getEspEmpno().getEmpName() + "</TD>");
        url.append("<TD width='30%' bgcolor=#ECF6FB>薪水发放年月</TD>");
        url.append("<TD width='20%'>" + esp.getEspYearmonth() + "</TD>");
        url.append("</TR>");

        Class ownerClass = esp.getClass();
        for (int i = 0; i < itemsList.size(); ++i) {
            Empsalaryacctitems acctitems = (Empsalaryacctitems) itemsList.get(i);
            String name = acctitems.getEsaiEsdd().getEsddName();
            if (i % 2 == 0) {
                url.append("<TR>");
            }
            url.append("<TD bgcolor=#ECF6FB>" + name + "</TD>");
            Method espMethod = ownerClass.getMethod("getEspColumn" + acctitems.getEsaiDataSeq(),
                                                    new Class[0]);

            BigDecimal data = (BigDecimal) espMethod.invoke(esp, new Object[0]);

            url.append("<TD>" + data + "</TD>");
            if ((i % 2 == 1) || (i == itemsList.size() - 1))
                url.append("</TR>");
        }
        url.append("</TBODY>");
        url.append("</TABLE>");
        url.append("<br>");
        url.append("<br>");

        SysConfigManager config = PropertiesFileConfigManager.getInstance();
        url.append(config.getProperty("email.sys.mailSystemName"));
        url.append("<br>");
        url.append(config.getProperty("email.sys.mailAdminPhone"));
        url.append("<br>");
        return url.toString();
    }

    public Empsalarypay getSalaryByEmpId(String empId, String yearmonth) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarypay.class);
        detachedCriteria.createAlias("espEmpno", "espEmpno", 1);
        detachedCriteria.setFetchMode("espEmpconfig", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Empsalarypay.PROP_ESP_YEARMONTH, yearmonth));
        detachedCriteria.add(Restrictions.eq("espEmpno.id", empId));
        detachedCriteria.setProjection(null);

        List result = this.salaryPaidDAO.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() > 0)) {
            return (Empsalarypay) result.get(0);
        }
        return null;
    }

    public Employee getEmployeeByESP(Empsalarypay esp) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.setFetchMode("config", FetchMode.JOIN);
        detachedCriteria.setFetchMode("config.escJobgrade", FetchMode.JOIN);
        detachedCriteria.setFetchMode("config.escEsavId", FetchMode.JOIN);
        detachedCriteria.setFetchMode("empBenefitType", FetchMode.JOIN);
        detachedCriteria.setFetchMode("benefit", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Employee.PROP_ID, esp.getEspEmpno().getId()));
        List empList = this.salaryPaidDAO.findByCriteria(detachedCriteria);
        if (empList.size() > 0) {
            return (Employee) empList.get(0);
        }
        return null;
    }

    public String checkSalaryPay(String empId, String year, String month, String salaryValue,
            Integer status) throws Exception {
        if ((salaryValue == null) || (salaryValue.length() <= 0)) {
            return "nullinput";
        }

        Empsalarypay esp = getSalaryByEmpId(empId, year + month);
        if (esp == null) {
            return "noEmpsalarypay";
        }

        String[] salary = salaryValue.split(",");
        List salaryList = new ArrayList();
        for (int i = 0; i < salary.length; ++i) {
            salaryList.add(salary[i]);
        }

        Employee emp = getEmployeeByESP(esp);
        if (emp == null) {
            return "noEmployee";
        }

        Empsalaryconfig config = emp.getConfig();
        config.decryEmpSalaryConf(config);

        IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");

        List acctList = esaiBo.getItemsByAcctversion(config.getEscEsavId().getId());

        StringBuffer errorFields = new StringBuffer();

        if (acctList != null) {
            for (int i = 0; i < acctList.size(); ++i) {
                Empsalaryacctitems item = (Empsalaryacctitems) acctList.get(i);
                if ((item.getEsaiDataIsCalc().intValue() != 2) || (status.intValue() != 1)
                        || (((String) salaryList.get(i)).equalsIgnoreCase("x"))
                        || (item.getEsaiEsdd().getEsddDataType().intValue() == 9)) {
                    continue;
                }
                errorFields.append("A").append(item.getEsaiDataSeq()).append("、");
            }
        }

        if (errorFields.length() > 0) {
            return errorFields.toString().substring(0, errorFields.length() - 1);
        }
        return "SUCCESS";
    }

    public List<Empsalarypay> getSalaryPaidbyEsav(String esavId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarypay.class);
        detachedCriteria.createAlias("espEmpno", "espEmpno", 1);
        detachedCriteria.setFetchMode("espEmpno.empBenefitType", FetchMode.JOIN);
        detachedCriteria.setFetchMode("espEmpno.benefit", FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empsalarypay.PROP_ESP_JOBGRADE, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Empsalarypay.PROP_ESP_ESAV_ID + ".id", esavId));
        List result = this.salaryPaidDAO.findByCriteria(detachedCriteria);
        return result;
    }

    public Empsalarypay getSalaryPaidbyEspId(String espId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarypay.class);
        detachedCriteria.createAlias("espEmpno", "espEmpno", 1);
        detachedCriteria.setFetchMode("espEmpno.benefit", FetchMode.JOIN);
        detachedCriteria.setFetchMode("espEmpno.empBenefitType", FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empsalarypay.PROP_ESP_EMPCONFIG, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empsalarypay.PROP_ESP_JOBGRADE, FetchMode.JOIN);
        detachedCriteria.setFetchMode(Empsalarypay.PROP_ESP_ESAV_ID, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Empsalarypay.PROP_ID, espId));
        List result = this.salaryPaidDAO.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() > 0)) {
            return (Empsalarypay) result.get(0);
        }
        return null;
    }

    private int getMonthEmpNumber(Department depart, Date monthStart, Date monthEnd) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq("empDeptNo.id", depart.getId()));

        detachedCriteria.add(Restrictions
                .or(Restrictions.sqlRestriction("emp_status=1 and emp_join_date<=?", monthEnd,
                                                Hibernate.DATE), Restrictions
                        .sqlRestriction("emp_status=0 and emp_terminate_date >=?", monthStart,
                                        Hibernate.DATE)));

        return this.salaryPaidDAO.findRowCountByCriteria(detachedCriteria);
    }

    private int getMonthQuitEmpNumber(Department depart, Date[] dateArr) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq("empDeptNo.id", depart.getId()));
        detachedCriteria.add(Restrictions.eq("empStatus", Integer.valueOf(0)));
        detachedCriteria.add(Restrictions.ge("empTerminateDate", dateArr[0]));
        detachedCriteria.add(Restrictions.le("empTerminateDate", dateArr[1]));

        return this.salaryPaidDAO.findRowCountByCriteria(detachedCriteria);
    }

    private int getMonthJoinEmpNumber(Department depart, Date[] dateArr) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq("empDeptNo.id", depart.getId()));

        detachedCriteria.add(Restrictions.ge("empJoinDate", dateArr[0]));
        detachedCriteria.add(Restrictions.le("empJoinDate", dateArr[1]));

        return this.salaryPaidDAO.findRowCountByCriteria(detachedCriteria);
    }

    private BigDecimal getMonthSalaryAmount(Department depart, String year, Integer month) {
        DetachedCriteria dc = DetachedCriteria.forClass(Empsalarypay.class);
        dc.createAlias("espEmpno", "emp", 1);
        dc.setFetchMode(Empsalarypay.PROP_ESP_EMPCONFIG, FetchMode.JOIN);
        dc.add(Restrictions.eq("emp.empDeptNo.id", depart.getId()));

        if (month.intValue() < 10)
            dc.add(Restrictions.eq(Empsalarypay.PROP_ESP_YEARMONTH, year + "0" + month.toString()));
        else {
            dc.add(Restrictions.eq(Empsalarypay.PROP_ESP_YEARMONTH, year + month.toString()));
        }
        List salaryPay = this.salaryPaidDAO.findByCriteria(dc);
        BigDecimal salaryAmount = new BigDecimal(0.0D);

        if (salaryPay != null) {
            IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");

            Map acctItemsMap = itemsBo.getAcctItemsByPay(salaryPay);

            for (int j = 0; j < salaryPay.size(); ++j) {
                Empsalarypay esp = (Empsalarypay) salaryPay.get(j);
                try {
                    esp.decryEMPPaid(esp);
                    calSalaryPayforPage(esp, (List) acctItemsMap.get(esp.getEspEsavId().getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (esp.getShowColumn8() != null)
                    salaryAmount = salaryAmount.add(esp.getShowColumn8());
            }
        }
        return salaryAmount;
    }

    private SalaryPaidConverge getTotalSarlayPaidConverge(List<SalaryPaidConverge> result) {
        SalaryPaidConverge totalSalaryPaidConverge = new SalaryPaidConverge();
        totalSalaryPaidConverge.setEmpDepartment("所有部门");
        int empNumber = 0;
        int lastMonthEmpNumber = 0;
        int quitEmpNumber = 0;
        int joinEmpNumber = 0;
        BigDecimal salaryAmount = new BigDecimal(0.0D);
        BigDecimal lastMonthSalaryAmount = new BigDecimal(0.0D);
        BigDecimal salaryAmountDifference = new BigDecimal(0.0D);
        SalaryPaidConverge salaryPaidConverge = null;
        for (int i = 0; i < result.size(); ++i) {
            salaryPaidConverge = (SalaryPaidConverge) result.get(i);
            empNumber += salaryPaidConverge.getEmpNumber();
            lastMonthEmpNumber += salaryPaidConverge.getLastMonthEmpNumber();
            quitEmpNumber += salaryPaidConverge.getQuitEmpNumber();
            joinEmpNumber += salaryPaidConverge.getJoinEmpNumber();
            salaryAmount = salaryAmount.add(salaryPaidConverge.getSalaryAmount());
            lastMonthSalaryAmount = lastMonthSalaryAmount.add(salaryPaidConverge
                    .getLastMonthSalaryAmount());

            salaryAmountDifference = salaryAmountDifference.add(salaryPaidConverge
                    .getSalaryDifference());
        }

        totalSalaryPaidConverge.setEmpNumber(empNumber);
        totalSalaryPaidConverge.setLastMonthEmpNumber(lastMonthEmpNumber);
        totalSalaryPaidConverge.setQuitEmpNumber(quitEmpNumber);
        totalSalaryPaidConverge.setJoinEmpNumber(joinEmpNumber);
        totalSalaryPaidConverge.setSalaryAmount(salaryAmount);
        totalSalaryPaidConverge.setLastMonthSalaryAmount(lastMonthSalaryAmount);
        totalSalaryPaidConverge.setSalaryDifference(salaryAmountDifference);
        return totalSalaryPaidConverge;
    }

    public List<Emailsend> getEmailList(List<Empsalarypay> result, String createrId) {
        List emailList = new ArrayList();

        Hashtable itemsTable = new Hashtable();
        SysConfigManager fileConfigManager = PropertiesFileConfigManager.getInstance();
        for (int i = 0; i < result.size(); ++i) {
            Empsalarypay esp = (Empsalarypay) result.get(i);
            try {
                String url = calSalarypaidForURL(esp, itemsTable);

                Emailsend email = new Emailsend();
                email.setEsTitle(esp.getEspYearmonth().substring(0, 4) + "年"
                        + esp.getEspYearmonth().substring(4) + "月薪资邮件发送");
                email.setEsContent(url);
                email.setEsFrom(fileConfigManager.getProperty("email.sys.sender"));
                email.setEsTo(esp.getEspEmpno().getEmpEmail());
                email.setEmpName(esp.getEspEmpno().getEmpName());

                emailList.add(email);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emailList;
    }

    public int hasSalaryPayByAcctVersion(String acctversionId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarypay.class);
        detachedCriteria.add(Restrictions.eq("espEsavId.id", acctversionId));
        detachedCriteria.setProjection(Projections.rowCount());
        List list = this.salaryPaidDAO.findByCriteria(detachedCriteria);
        return ((Integer) list.get(0)).intValue();
    }

    public ISalaryPaidDAO getSalaryPaidDAO() {
        return this.salaryPaidDAO;
    }

    public void setSalaryPaidDAO(ISalaryPaidDAO salaryPaidDAO) {
        this.salaryPaidDAO = salaryPaidDAO;
    }

    public List<Empsalarypay> getDecriedSalaryPaidForEmailSend(String[] emps, String year,
            Integer month) {
        if ((emps == null) || (emps.length == 0)) {
            return new ArrayList(0);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarypay.class);
        detachedCriteria.createAlias("espEmpno", "emp", 1);

        if (month.intValue() < 10) {
            detachedCriteria.add(Restrictions.eq(Empsalarypay.PROP_ESP_YEARMONTH, year + "0"
                    + month.toString()));
        } else {
            detachedCriteria.add(Restrictions.eq(Empsalarypay.PROP_ESP_YEARMONTH, year
                    + month.toString()));
        }

        if (emps.length > 0) {
            detachedCriteria.add(Restrictions.in("emp.id", emps));
        }

        List result = this.salaryPaidDAO.findByCriteria(detachedCriteria);

        IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");

        Map acctItemsMap = itemsBo.getAcctItemsByPay(result);

        for (int i = 0; i < result.size(); ++i) {
            Empsalarypay esp = (Empsalarypay) result.get(i);
            try {
                esp.decryEMPPaid(esp);
                calSalaryPayforPage(esp, (List) acctItemsMap.get(esp.getEspEsavId().getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.set(i, esp);
        }

        return result;
    }

    public Boolean shuffleSalaryPay(List<Empsalarypay> salaryPayList, Map<String, Integer> fromMap,
            List<Empsalaryacctitems> toItems) {
        for (int i = 0; i < salaryPayList.size(); ++i) {
            try {
                Empsalarypay salaryPay = (Empsalarypay) ((Empsalarypay) salaryPayList.get(i))
                        .clone();
                Class fromPayClass = salaryPay.getClass();
                Class toPayClass = ((Empsalarypay) salaryPayList.get(i)).getClass();
                String empId = salaryPay.getEspEmpno().getId();
                String dataDefId = new String();

                for (int j = 0; j < toItems.size(); ++j) {
                    dataDefId = ((Empsalaryacctitems) toItems.get(j)).getEsaiEsdd().getEsddId();
                    Integer fromPos = (Integer) fromMap.get(dataDefId);
                    BigDecimal getValue;
                    if (fromPos != null) {
                        if (fromPos.intValue() == j)
                            continue;
                        Method getMethod = fromPayClass.getMethod("getEspColumn"
                                + (fromPos.intValue() + 1), new Class[0]);

                        getValue = (BigDecimal) getMethod.invoke(salaryPay, new Object[0]);
                    } else {
                        getValue = MyTools.encryDecimal(empId, new BigDecimal(0));
                    }
                    Method setMethod = toPayClass.getMethod("setEspColumn" + (j + 1),
                                                            new Class[] { BigDecimal.class });

                    setMethod.invoke(salaryPayList.get(i), new Object[] { getValue });
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Boolean.valueOf(false);
            }
        }
        salarypayReduceSize(salaryPayList);
        return Boolean.valueOf(true);
    }

    private Boolean salarypayReduceSize(List<Empsalarypay> salaryPayList) {
        for (int i = 0; i < salaryPayList.size(); ++i) {
            try {
                ((Empsalarypay) salaryPayList.get(i)).setEspEmpno(new Employee(
                        ((Empsalarypay) salaryPayList.get(i)).getEspEmpno().getId()));
            } catch (Exception e) {
                e.printStackTrace();
                return Boolean.valueOf(false);
            }
        }
        return Boolean.valueOf(true);
    }

    public boolean isSalaryPayRecordsExist(String empId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarypay.class);
        detachedCriteria.add(Restrictions.eq("espEmpno.id", empId));
        return this.salaryPaidDAO.findByCriteria(detachedCriteria).size() > 0;
    }

    public List<Employee> searchEmployeeHasSalaryPay(String yearmonth) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarypay.class);
        detachedCriteria.setFetchMode("espEmpno", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Empsalarypay.PROP_ESP_YEARMONTH, yearmonth));
        return this.salaryPaidDAO.findByCriteria(detachedCriteria);
    }

    public String getGivenMonthAvgPay(String empId, String[] yearMonthArr) {
        String yearMonthStr = "";
        for (int i = 0; (yearMonthArr != null) && (i < yearMonthArr.length); ++i) {
            yearMonthStr = yearMonthStr + "'" + yearMonthArr[i] + "',";
        }
        yearMonthStr = yearMonthStr.substring(0, yearMonthStr.length() - 1);
        String hql = "from Empsalarypay where espEmpno = '" + empId + "' and espYearmonth in ("
                + yearMonthStr + ")";

        List<Empsalarypay> espList = this.salaryPaidDAO.exeHqlList(hql);
        IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");

        Map acctItemsMap = itemsBo.getAcctItemsByPay(espList);
        BigDecimal avgPay_b = new BigDecimal(0);
        BigDecimal avgPay = new BigDecimal(0);
        int count = 0;

        List periodList = getPaidPeriod(yearMonthStr);
        for (Empsalarypay pay : espList) {
            try {
                pay.decryEMPPaid(pay);
                calSalaryPayforPage(pay, (List) acctItemsMap.get(pay.getEspEsavId().getId()));
                if (periodList.contains(pay.getEspYearmonth())) {
                    avgPay_b = avgPay_b.add(pay.getShowColumn8());
                    avgPay = avgPay.add(pay.getShowColumn19());
                    ++count;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (count == 0) {
            return "0.00,0.00";
        }
        avgPay_b = avgPay_b.divide(new BigDecimal(count), 2);
        avgPay = avgPay.divide(new BigDecimal(count), 2);
        return avgPay_b.toString() + "," + avgPay.toString();
    }

    private List<String> getPaidPeriod(String yearMonthStr) {
        List list = new ArrayList();
        String hql = "from Empsalaryperiod where espdYearmonth in (" + yearMonthStr
                + ") and espdStatus=2";

        List<Empsalaryperiod> espdList = this.salaryPaidDAO.exeHqlList(hql);
        for (Empsalaryperiod period : espdList) {
            list.add(period.getEspdYearmonth());
        }
        return list;
    }

    public boolean savePayAndInitEbp(Empsalarypay pay) {
        pay.encryEMPPaid(pay);
        this.salaryPaidDAO.saveOrupdate(pay);

        IEmpBenefitBo empbenefitBo = (IEmpBenefitBo) SpringBeanFactory.getBean("empbenefitBo");
        empbenefitBo.deleteBenefitPlan(pay.getEspYearmonth(), Integer.valueOf(0),
                                       new String[] { pay.getEspEmpno().getId() });

        Empbenefitplan plan = pay.getBenefitPlan();
        if (plan != null) {
            plan.encryEMPPlan(plan);
            this.salaryPaidDAO.saveOrupdate(plan);
        }
        return true;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.SalaryPaidBoImpl JD-Core Version: 0.5.4
 */