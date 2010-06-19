package com.hr.compensation.bo;

import com.hr.compensation.dao.IEmpBenefitDao;
import com.hr.compensation.domain.Empbenefit;
import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.BenefitType;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.ObjectUtils;

public class EmpBenefitBoImpl implements IEmpBenefitBo {
    private static final Logger logger = Logger.getLogger(EmpBenefitBoImpl.class);
    private IEmpBenefitDao empBenefitDao;

    public List<Employee> findEmpBenefit(DetachedCriteria dc, Pager page, String searchOrExport) {
        if ((page == null) || ("export".equals(searchOrExport))) {
            return this.empBenefitDao.findByCriteria(dc);
        }

        page.splitPage(dc);
        return this.empBenefitDao.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    /** @deprecated */
    public String insertBenefit(Empbenefit benefit, String currEmpId) {
        String error = "";
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        Employee emp = empBo.loadEmp(benefit.getEmployee().getId(), null);
        Empbenefit oldBenefit = emp.getBenefit();

        if (oldBenefit != null) {
            if (benefit.getEbfStartMonth().compareTo(oldBenefit.getEbfStartMonth()) < 0) {
                error = emp.getEmpName() + "调整记录的生效年月不能早于原记录的生效年月！";
                return error;
            }
            oldBenefit.setEbfEndMonth(DateUtil.yearMonthAdd(benefit.getEbfStartMonth(), -1, 2));
            this.empBenefitDao.updateObject(oldBenefit);
        }

        benefit.setEbfId(getUUID());
        this.empBenefitDao.saveObject(benefit);
        if ((benefit.getBeneType() != null) && (!"".equals(benefit.getBeneType().trim()))) {
            emp.setEmpBenefitType(new BenefitType(benefit.getBeneType()));
        }
        if (benefit != null) {
            emp.setBenefit(benefit);
        }

        emp.setEmpLastChangeBy(new Employee(currEmpId));
        emp.setEmpLastChangeTime(new Date());
        this.empBenefitDao.updateObject(emp);
        return error;
    }

    public String insertNewBenefit(Empbenefit oldEbf, Empbenefit newEbf, Employee emp) {
        this.empBenefitDao.updateObject(oldEbf);
        this.empBenefitDao.saveObject(newEbf);
        this.empBenefitDao.updateObject(emp);
        if (emp.getConfig() != null) {
            this.empBenefitDao.updateObject(emp.getConfig());
        }
        return "SUCC";
    }

    public String addBenefit(Employee emp) {
        Date lastChangeTime = new Date();

        Empbenefit newEbf = emp.getBenefit();
        newEbf.setEbfId(MyTools.getUUID());
        newEbf.setEmployee(new Employee(emp.getId()));
        this.empBenefitDao.saveObject(newEbf);

        emp.setEmpLastChangeTime(lastChangeTime);
        this.empBenefitDao.updateObject(emp);

        Empsalaryconfig config = emp.getConfig();
        if (config != null) {
            config.setEscLastChangeTime(lastChangeTime);
            config.setEscLastChangeBy(emp.getEmpLastChangeBy().getId());
            config.encryEmpSalaryConf(config);
            this.empBenefitDao.updateObject(config);
        }
        return "SUCC";
    }

    public String updateBenefit(Empbenefit oldEbf, Employee emp) {
        Date lastChangeTime = new Date();

        if (oldEbf != null) {
            String ebfEndMonth = DateUtil.yearMonthAdd(emp.getBenefit().getEbfStartMonth(), -1, 2);
            oldEbf.setEbfEndMonth(ebfEndMonth);
            this.empBenefitDao.updateObject(oldEbf);
        }

        Empbenefit newEbf = emp.getBenefit();
        newEbf.setEmployee(emp);
        newEbf.setEbfId(emp.getBenefit().getEbfId());
        this.empBenefitDao.updateObject(newEbf);

        emp.setEmpLastChangeTime(lastChangeTime);
        this.empBenefitDao.updateObject(emp);

        Empsalaryconfig config = emp.getConfig();
        if (config != null) {
            config.setEscLastChangeTime(lastChangeTime);
            config.setEscLastChangeBy(emp.getEmpLastChangeBy().getId());
            config.encryEmpSalaryConf(config);
            this.empBenefitDao.updateObject(config);
        }
        return "SUCC";
    }

    public String deleteBenefit(Employee employee, String ebfId, String empNo) {
        String hql = "delete from Empbenefit where ebfId='" + ebfId + "'";
        this.empBenefitDao.exeHql(hql);
        List oldList = searchByEmpNo(employee.getId());
        employee.setEmpLastChangeBy(new Employee(empNo));
        employee.setEmpLastChangeTime(new Date());
        if ((oldList != null) && (oldList.size() > 0)) {
            Empbenefit old = (Empbenefit) oldList.get(0);
            old.setEbfEndMonth(null);
            old.setEmployee(employee);
            this.empBenefitDao.updateObject(old);
            employee.setBenefit(old);
            this.empBenefitDao.updateObject(employee);
        } else {
            employee.setBenefit(null);
            this.empBenefitDao.updateObject(employee);
        }
        return null;
    }

    public boolean deleteBenefitPlan(String yearmonth, Integer status, String[] empIds) {
        if ((empIds == null) || (empIds.length == 0))
            return false;

        StringBuffer hqlBuffer = new StringBuffer();
        for (String empId : empIds) {
            hqlBuffer.append("ebpEmpno.id='" + empId + "' or ");
        }

        String hql = "delete from Empbenefitplan where ebpYearMonth='" + yearmonth + "' and ";
        if (status != null)
            hql = hql + "ebpStatus='" + status + "' and ";
        hql = hql + "(" + hqlBuffer.toString();
        hql = hql.substring(0, hql.length() - 3) + ")";
        this.empBenefitDao.exeHql(hql);

        return true;
    }

    public List<Empbenefit> searchByEmpNo(String employeeId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefit.class);
        detachedCriteria.setFetchMode(Empbenefit.PROP_EBF_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions.eq(Empbenefit.PROP_EBF_EMPLOYEE + "." + Employee.PROP_ID,
                                             employeeId));

        detachedCriteria.addOrder(Order.desc(Empbenefit.PROP_EBF_EBFSTARTMONTH));
        List result = this.empBenefitDao.findByCriteria(detachedCriteria);
        return result;
    }

    public Empbenefit loadPrevBenefit(String employeeId) {
        List beneList = searchByEmpNo(employeeId);
        if (beneList.size() < 2) {
            return null;
        }
        return (Empbenefit) beneList.get(1);
    }

    public Empbenefit getEmpbenefitById(String ebfId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefit.class);
        detachedCriteria.setFetchMode(Empbenefit.PROP_EBF_EMPLOYEE, FetchMode.DEFAULT);
        detachedCriteria.add(Restrictions.eq(Empbenefit.PROP_EBF_EBFID, ebfId));
        List result = this.empBenefitDao.findByCriteria(detachedCriteria);
        if (result.size() == 0) {
            return null;
        }
        return (Empbenefit) result.get(0);
    }

    public List<Empsalarypay> searchbenefitpayvalueByEmpId(String empId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarypay.class);
        detachedCriteria.createAlias("espEmpno", "emp", 1);
        detachedCriteria.setFetchMode("espEmpconfig", FetchMode.JOIN);
        detachedCriteria.setFetchMode("espEmpconfig.escJobgrade", FetchMode.JOIN);

        detachedCriteria.add(Restrictions.eq("emp.id", empId));

        detachedCriteria.addOrder(Order.desc(Empsalarypay.PROP_ESP_YEARMONTH));
        List result = this.empBenefitDao.findByCriteria(detachedCriteria);
        if (result.isEmpty()) {
            return new ArrayList();
        }

        Set esavIds = new HashSet();
        for (int i = 0; i < result.size(); ++i) {
            esavIds.add(((Empsalarypay) result.get(i)).getEspEsavId().getId());
        }
        IEmpSalaryAcctitemsBo esaitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        Map acctitemsMap = esaitemsBo.getItemsByAcctversion(new ArrayList(esavIds));

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) SpringBeanFactory.getBean("empbenefitplanBo");
        Map monthsumplanMap = ebpBo.getSumEbpsOfAllMonth(empId);

        List items = null;
        Empbenefitplan sumplan = null;
        Empsalarypay pay = null;

        for (int i = 0; (result != null) && (i < result.size()); ++i) {
            pay = (Empsalarypay) result.get(i);
            items = (List) acctitemsMap.get(pay.getEspEsavId().getId());
            sumplan = (Empbenefitplan) monthsumplanMap.get(empId + "" + pay.getEspYearmonth());
            try {
                result.set(i, calcBenePayForPage(items, pay, sumplan));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Empsalarypay calcBenePayForPage(List<Empsalaryacctitems> items, Empsalarypay pay,
            Empbenefitplan sumplan) {
        Empsalarypay payTemp = null;
        try {
            payTemp = (Empsalarypay) pay.clone();
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }

        List sumList = new ArrayList();
        for (int i = 0; i <= 20; ++i) {
            sumList.add(new BigDecimal(0));
        }

        Class planClass = Empbenefitplan.class;

        BigDecimal sumValue = null;

        int i = 0;
        for (int j = items.size(); i < j; ++i) {
            Empsalaryacctitems esai = (Empsalaryacctitems) items.get(i);
            try {
                Method ebpMethod = planClass.getMethod("getEbpColumn" + esai.getEsaiDataSeq(),
                                                       new Class[0]);
                if (sumplan != null) {
                    sumValue = (BigDecimal) ebpMethod.invoke(sumplan, new Object[0]);
                }
                if (sumValue != null)
                    sumList.set(esai.getEsaiEsdd().getEsddDataType().intValue(),
                                ((BigDecimal) sumList.get(esai.getEsaiEsdd().getEsddDataType()
                                        .intValue())).add(sumValue));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        payTemp.setYearAndMonth(pay.getEspYearmonth());
        payTemp.setShowColumn15((BigDecimal) sumList.get(15));
        payTemp.setShowColumn16((BigDecimal) sumList.get(16));

        return payTemp;
    }

    public void getEmpBenefitNew(String yearmonth, Empsalarypay[] payList) {
        if (ObjectUtils.isEmpty(payList))
            return;

        Employee[] empList = new Employee[payList.length];
        for (int i = 0; i < payList.length; ++i) {
            empList[i] = payList[i].getEspEmpno();
        }
        getEmpBenefit(yearmonth, empList);
    }

    public void getEmpBenefitNew(String yearmonth, Empsalaryconfig[] configList) {
        if ((configList == null) || (configList.length == 0))
            return;

        Employee[] empList = new Employee[configList.length];
        for (int i = 0; i < configList.length; ++i) {
            empList[i] = configList[i].getEmployee();
        }
        getEmpBenefit(yearmonth, empList);
    }

    public void getEmpBenefit(String yearmonth, Employee[] empList) {
        if ((empList == null) || (empList.length == 0))
            return;

        DetachedCriteria dc = DetachedCriteria.forClass(Empbenefit.class);

        Map empMap = new HashMap();
        Set empIds = new HashSet();
        for (Employee emp : empList) {
            emp.setBenefit(null);
            empIds.add(emp.getId());
            empMap.put(emp.getId(), emp);
        }
        if (empList.length < 500) {
            dc.add(Restrictions.in(Empbenefit.PROP_EBF_EMPLOYEE + "." + Employee.PROP_ID, empIds));
        }

        String nextYearMonth = DateUtil.yearMonthAdd(yearmonth, 1, 2);
        dc
                .add(Restrictions.and(Restrictions.le(Empbenefit.PROP_EBF_EBFSTARTMONTH,
                                                      nextYearMonth), Restrictions.disjunction()
                        .add(Restrictions.ge(Empbenefit.PROP_EBF_EBFENDMONTH, yearmonth))
                        .add(Restrictions.isNull(Empbenefit.PROP_EBF_EBFENDMONTH))));

        List<Empbenefit> benefitList = this.empBenefitDao.findByCriteria(dc);
        if ((benefitList == null) || (benefitList.size() == 0))
            return;

        for (Empbenefit benefit : benefitList) {
            Employee emp = (Employee) empMap.get(benefit.getEmployee().getId());
            if (emp.getEmpBenefitType() != null)
                ;
            Integer benePayType = emp.getEmpBenefitType().getBenefitTypePayType();
            if ((yearmonth.equals(benefit.getEbfEndMonth()))
                    && (new Integer(1).equals(benePayType)))
                continue;
            if ((benefit.getEbfStartMonth().compareTo(yearmonth) > 0)
                    && (new Integer(0).equals(benePayType))) {
                continue;
            }

            benefit.setBelongYearMonth(DateUtil.yearMonthAdd(yearmonth, benePayType.intValue(), 2));
            emp.setBenefit(benefit);
        }
    }

    public List<Empbenefitplan> getEmpAdditionalBenefitPlanList(String belongYearMonth,
            Set<String> empIds) {
        if (empIds.isEmpty()) {
            return new ArrayList(0);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefitplan.class);
        detachedCriteria.add(Restrictions.in("ebpEmpno.id", empIds));
        detachedCriteria.add(Restrictions.eq("ebpBelongYearmonth", belongYearMonth));
        return this.empBenefitDao.findByCriteria(detachedCriteria);
    }

    private Map<String, Empbenefitplan> getEmpBenefitPlanMap(String empId) {
        StringBuffer hqlBuffer = new StringBuffer(
                "select ebpYearMonth,sum(ebpHousingAmountb),sum(ebpInsuranceAmountb),sum(ebpPensionAmountb) from Empbenefitplan ");
        hqlBuffer.append(" where ebpEmpno.id ='");
        hqlBuffer.append(empId);
        hqlBuffer.append("' group by ebpYearMonth");
        List<Object[]> result = this.empBenefitDao.exeHqlList(hqlBuffer.toString());
        if (result.isEmpty()) {
            return new HashMap(0);
        }

        Map resultMap = new HashMap();
        for (Object[] obj : result) {
            Empbenefitplan plan = new Empbenefitplan();
            plan.setEbpBelongYearmonth((String) obj[0]);
            plan.setEbpHousingAmountb((BigDecimal) obj[1]);
            plan.setEbpInsuranceAmountb((BigDecimal) obj[2]);
            plan.setEbpPensionAmountb((BigDecimal) obj[3]);
            resultMap.put((String) obj[0], plan);
        }
        return resultMap;
    }

    public List<Empbenefitplan> getBenefitPlanByEsav(String esavId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefitplan.class);
        detachedCriteria.add(Restrictions.eq("ebpEsavId.id", esavId));
        return this.empBenefitDao.findByCriteria(detachedCriteria);
    }

    public boolean shuffleBenefitPlan(List<Empbenefitplan> planList, Map<String, Integer> fromMap,
            List<Empsalaryacctitems> toItems) {
        for (Empbenefitplan plan : planList) {
            try {
                Empbenefitplan clonePlan = (Empbenefitplan) plan.clone();
                String empId = plan.getEbpEmpno().getId();

                for (int j = 0; j < toItems.size(); ++j) {
                    String dataDefId = ((Empsalaryacctitems) toItems.get(j)).getEsaiEsdd()
                            .getEsddId();
                    Integer fromPos = (Integer) fromMap.get(dataDefId);
                    BigDecimal getValue;
                    if (fromPos != null) {
                        if (fromPos.intValue() == j)
                            continue;
                        Method getMethod = Empbenefitplan.class.getMethod("getEbpColumn"
                                + (fromPos.intValue() + 1), new Class[0]);
                        getValue = (BigDecimal) getMethod.invoke(clonePlan, new Object[0]);
                    } else {
                        getValue = MyTools.encryDecimal(empId, new BigDecimal(0));
                    }
                    Method setMethod = Empbenefitplan.class
                            .getMethod("setEbpColumn" + (j + 1), new Class[] { BigDecimal.class });
                    setMethod.invoke(plan, new Object[] { getValue });
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean saveOrupdate(List<Empbenefitplan> planList) {
        this.empBenefitDao.saveOrupdate(planList);
        return true;
    }

    public IEmpBenefitDao getEmpBenefitDao() {
        return this.empBenefitDao;
    }

    public void setEmpBenefitDao(IEmpBenefitDao empBenefitDao) {
        this.empBenefitDao = empBenefitDao;
    }

    private String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.EmpBenefitBoImpl JD-Core Version: 0.5.4
 */