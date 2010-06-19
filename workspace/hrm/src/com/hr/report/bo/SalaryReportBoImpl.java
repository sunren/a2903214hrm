package com.hr.report.bo;

import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Jobgrade;
import com.hr.profile.domain.Employee;
import com.hr.report.action.compensation.support.SalaryReportObject;
import com.hr.report.action.compensation.support.SalaryReportParamBean;
import com.hr.report.action.profile.support.DimissionRateBean;
import com.hr.report.dao.IReportDAO;
import com.hr.util.BaseCrit;
import com.hr.util.MyTools;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class SalaryReportBoImpl implements SalaryReportBo {
    private IReportDAO reportDao;

    public List getObjects(String itemId, String expression) {
        Map resultMap = new HashMap();
        List configList = getEmployeeSalaryConfigList();

        List<Empsalaryacctitems> accItems = getAccountItemsByDataId(new String[] { itemId });
        for (Iterator i$ = configList.iterator(); i$.hasNext();) {
            Empsalaryconfig config = (Empsalaryconfig) i$.next();
            for (Empsalaryacctitems tmp : accItems)
                if (config.getEscEsavId().getId().equals(tmp.getEsaiEsav().getId())) {
                    int seq = tmp.getEsaiDataSeq().intValue();
                    BigDecimal salary = (BigDecimal) config.getDecry("EscColumn" + seq, config
                            .getEmployee().getId(), MyTools.BIGDECIMAL);
                    String name = (String) getValue(config, expression);
                    if (resultMap.containsKey(name)) {
                        BigDecimal total = (BigDecimal) resultMap.get(name);
                        resultMap.put(name, total.add(salary));
                        break;
                    }
                    resultMap.put(name, salary);

                    break;
                }
        }

        Empsalaryconfig config;
        return getResultFromMap(resultMap);
    }

    private List getResultFromMap(Map<String, BigDecimal> resultMap) {
        List result = new ArrayList();
        for (String key : resultMap.keySet()) {
            SalaryReportObject obj = new SalaryReportObject();
            obj.setName(key);
            obj.setTotal((BigDecimal) resultMap.get(key));
            result.add(obj);
        }
        return result;
    }

    private static String getNext(String current) {
        String result = "";
        String[] arr = current.split("/");
        int year = Integer.valueOf(arr[0]).intValue();
        int month = Integer.valueOf(arr[1]).intValue();
        if (month == 12)
            result = year + 1 + "/01";
        else {
            result = year
                    + "/"
                    + ((month + 1 > 9) ? Integer.valueOf(month + 1) : new StringBuilder()
                            .append("0").append(month + 1).toString());
        }
        return result;
    }

    private List<Empsalaryconfig> getEmployeeSalaryConfigList() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryconfig.class);
        detachedCriteria.setFetchMode("employee", FetchMode.JOIN);
        detachedCriteria.setFetchMode("employee.empType", FetchMode.JOIN);
        detachedCriteria.setFetchMode("employee.empDeptNo", FetchMode.JOIN);
        detachedCriteria.setFetchMode("escEsavId", FetchMode.JOIN);
        detachedCriteria.setFetchMode("escEsavId.esavEsac", FetchMode.JOIN);
        detachedCriteria.setFetchMode("escJobgrade", FetchMode.JOIN);
        detachedCriteria.createAlias("employee", "employee");
        detachedCriteria.add(Restrictions.eq("employee.empStatus", Integer.valueOf(1)));
        return this.reportDao.findByCriteria(detachedCriteria);
    }

    private Object getValue(Object obj, String expression) {
        try {
            return PropertyUtils.getProperty(obj, expression);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Empsalaryacctitems> getAccountItemsByDataId(String[] itemIds) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctitems.class);
        detachedCriteria.add(Restrictions.in("esaiEsdd.esddId", itemIds));
        return this.reportDao.findByCriteria(detachedCriteria);
    }

    public IReportDAO getReportDao() {
        return this.reportDao;
    }

    public void setReportDao(IReportDAO reportDao) {
        this.reportDao = reportDao;
    }

    public <E> E loadObject(Class<E> obj, Object id, String[] fetch) {
        return this.reportDao.loadObject(obj, id, fetch, new boolean[0]);
    }

    public List getSalaryReportHistory(SalaryReportParamBean params) {
        List salaryPays = querySalaryPayList(params);

        List<Empsalaryacctitems> items = getAccountItemsByDataId(new String[] { params.getItemId() });

        Map resultMap = new TreeMap();
        for (Iterator i$ = salaryPays.iterator(); i$.hasNext();) {
            Empsalarypay pay = (Empsalarypay) i$.next();
            for (Empsalaryacctitems tmpAccountItem : items) {
                String acccid = tmpAccountItem.getEsaiEsav().getId();
                if (acccid.equals(pay.getEspEsavId().getId())) {
                    int seq = tmpAccountItem.getEsaiDataSeq().intValue();
                    String name = pay.getEspYearmonth().substring(0, 4) + "/"
                            + pay.getEspYearmonth().substring(4);
                    BigDecimal salary = (BigDecimal) pay.getDecry("EspColumn" + seq, pay
                            .getEspEmpno().getId(), MyTools.BIGDECIMAL);

                    if (resultMap.containsKey(name)) {
                        BigDecimal tmp = (BigDecimal) resultMap.get(name);
                        resultMap.put(name, tmp.add(salary));
                        break;
                    }
                    resultMap.put(name, salary);

                    break;
                }
            }
        }

        Empsalarypay pay;
        return getResultFromMap(resultMap);
    }

    private Map<String, BigDecimal> initMap(String start, String end) {
        Map map = new TreeMap();
        BigDecimal zero = new BigDecimal(0);
        while (end.compareTo(start) > 0) {
            map.put(start, zero);
            start = getNext(start);
        }
        return map;
    }

    private List<Empsalarypay> querySalaryPayList(SalaryReportParamBean params) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarypay.class);
        detachedCriteria.setFetchMode("espDept", FetchMode.JOIN);

        BaseCrit.addDC(detachedCriteria, Empsalarypay.PROP_ESP_DEPT, Department.PROP_ID,
                       new Object[] { new Department(params.getDepartmentId()) });
        BaseCrit.addDC(detachedCriteria, Empsalarypay.PROP_ESP_JOBGRADE, Jobgrade.PROP_ID,
                       new Object[] { new Jobgrade(params.getJobgradeId()) });
        BaseCrit.addDC(detachedCriteria, Empsalarypay.PROP_ESP_YEARMONTH, "between", new String[] {
                params.getStartYear() + params.getStartMonth(),
                params.getEndYear() + params.getEndMonth() });

        return this.reportDao.findByCriteria(detachedCriteria);
    }

    public List getSalaryReportCost(SalaryReportParamBean params) {
        List salaryPays = querySalaryPayList(params);

        List<Empsalaryacctitems> items = getAccountItemsByDataId(new String[] { params.getItemId(),
                params.getItemId2() });

        Map<String, BigDecimal> resultMap = new TreeMap<String, BigDecimal>();
        for (Iterator i$ = salaryPays.iterator(); i$.hasNext();) {
            Empsalarypay pay = (Empsalarypay) i$.next();
            for (Empsalaryacctitems tmpAccountItem : items) {
                String acccid = tmpAccountItem.getEsaiEsav().getId();
                if (pay.getEspEsavId().getId().equals(acccid)) {
                    int seq = tmpAccountItem.getEsaiDataSeq().intValue();
                    BigDecimal salary = (BigDecimal) pay.getDecry("EspColumn" + seq, pay
                            .getEspEmpno().getId(), MyTools.BIGDECIMAL);

                    String name = pay.getEspYearmonth().substring(0, 4) + "/"
                            + pay.getEspYearmonth().substring(4) + "-"
                            + tmpAccountItem.getEsaiEsdd().getEsddName();

                    if (resultMap.containsKey(name)) {
                        BigDecimal tmp = (BigDecimal) resultMap.get(name);
                        resultMap.put(name, tmp.add(salary));
                    } else {
                        resultMap.put(name, salary);
                    }
                }
            }
        }

        Empsalarypay pay;
        List result = new ArrayList();
        for (String key : resultMap.keySet()) {
            SalaryReportObject obj = new SalaryReportObject();
            String[] tmpArray = key.split("-");
            obj.setName(tmpArray[0]);
            obj.setName2(tmpArray[1]);
            obj.setTotal((BigDecimal) resultMap.get(key));
            result.add(obj);
        }
        return result;
    }

    public List getEmployeeDimissionRate(String startDate, String endDate) {
        List result = new ArrayList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = format.parse(startDate);
            end = format.parse(endDate);
        } catch (ParseException e) {
            start = new Date();
            end = new Date();
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.setFetchMode("config", FetchMode.JOIN);
        detachedCriteria.setFetchMode("config.escJobgrade", FetchMode.JOIN);
        List<Employee> empList = this.reportDao.findByCriteria(detachedCriteria);
        List<Jobgrade> jobgradeList = this.reportDao.findByCriteria(DetachedCriteria
                .forClass(Jobgrade.class));
        Map<String, DimissionRateBean> map = new HashMap<String, DimissionRateBean>();
        for (Jobgrade grade : jobgradeList) {
            map.put(grade.getJobGradeName(), new DimissionRateBean());
        }
        for (Employee emp : empList) {
            Empsalaryconfig config = emp.getConfig();
            String jobgradeName = "NaN";
            if ((config != null) && (config.getId().length() != 0)) {
                jobgradeName = emp.getConfig().getEscJobgrade().getJobGradeName();
            }

            DimissionRateBean bean = (DimissionRateBean) map.get(jobgradeName);
            bean = (bean == null) ? new DimissionRateBean() : bean;
            int empStatus = emp.getEmpStatus().intValue();

            if (emp.getEmpJoinDate().compareTo(start) < 0) {
                if (empStatus == 1) {
                    bean.setOriginal(bean.getOriginal() + 1);
                } else if ((emp.getEmpTerminateDate() != null)
                        && (!start.after(emp.getEmpTerminateDate()))) {
                    bean.setOriginal(bean.getOriginal() + 1);
                }

            } else if (!end.before(emp.getEmpJoinDate())) {
                bean.setActive(bean.getActive() + 1);
            }

            if ((empStatus == 0) && (emp.getEmpTerminateDate() != null)
                    && (!start.after(emp.getEmpTerminateDate()))
                    && (!end.before(emp.getEmpTerminateDate()))) {
                bean.setDimission(bean.getDimission() + 1);
            }

            if (!end.before(emp.getEmpJoinDate())) {
                if (empStatus == 1) {
                    bean.setTotal(bean.getTotal() + 1);
                } else if ((emp.getEmpTerminateDate() != null)
                        && (emp.getEmpTerminateDate().after(end))) {
                    bean.setTotal(bean.getTotal() + 1);
                }
            }

            map.put(jobgradeName, bean);
        }
        for (String key : map.keySet()) {
            DimissionRateBean bean = (DimissionRateBean) map.get(key);
            bean.setName(key);
            result.add(bean);
        }
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.bo.SalaryReportBoImpl JD-Core Version: 0.5.4
 */