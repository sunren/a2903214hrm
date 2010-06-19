package com.hr.compensation.bo;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.Status;
import com.hr.compensation.action.SalaryConfigAssist;
import com.hr.compensation.dao.ISalaryconfDAO;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.configuration.dao.IStatusDAO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.InterpreterExecuteContext;
import com.hr.util.Pager;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class SalaryconfBoImpl implements ISalaryconfBo, Constants, Status {
    private ISalaryconfDAO salaryconfDAO;
    private IStatusDAO statusDAO;

    public boolean deleteSalaryconf(String id, Employee emp) {
        Empsalaryconfig salary = (Empsalaryconfig) this.salaryconfDAO
                .loadObject(Empsalaryconfig.class, id, new String[] { "employee" }, new boolean[0]);
        if (salary == null)
            return false;
        this.salaryconfDAO.deleteObject(salary);
        Employee employee = salary.getEmployee();
        employee.setConfig(null);
        employee.setEmpLastChangeBy(emp);
        employee.setEmpLastChangeTime(new Date());
        this.salaryconfDAO.updateObject(employee);
        return true;
    }

    public boolean saveOrupdateObject(Object obj) {
        try {
            this.salaryconfDAO.saveOrupdate(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public <T> boolean saveOrupdate(Collection<T> objs) {
        try {
            this.salaryconfDAO.saveOrupdate(objs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List getObjects(Class clas, String[] fetch) {
        List result = this.salaryconfDAO.getObjects(clas, fetch);
        return result;
    }

    public Object getObject(Class clas, Object id, String[] fetch) {
        Object result = this.salaryconfDAO.loadObject(clas, id, fetch, new boolean[0]);
        return result;
    }

    public Empsalaryconfig loadSalaryconf(Object id, String[] fetch) {
        return (Empsalaryconfig) this.salaryconfDAO.loadObject(Empsalaryconfig.class, id, fetch,
                                                               new boolean[0]);
    }

    public boolean saveOrUpdateEsc(Empsalaryconfig config, String empNo, Employee employee) {
        try {
            Date today = new Date();
            if (config.getEscCreateTime() == null) {
                config.setEscCreateTime(today);
                config.setEscCreateBy(empNo);
            }

            config.setEscLastChangeTime(today);
            config.setEscLastChangeBy(empNo);

            config.encryEmpSalaryConf(config);
            this.salaryconfDAO.saveOrupdate(config);

            employee.setConfig(config);
            employee.setEmpLastChangeTime(today);
            employee.setEmpLastChangeBy(new Employee(empNo));
            this.salaryconfDAO.updateObject(employee);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertSalaryconf(Empsalaryconfig salary, String empNo, Employee employee) {
        if (loadSalaryconf(salary.getId(), new String[0]) != null) {
            return false;
        }

        Date today = new Date();
        salary.setEscCreateTime(today);
        salary.setEscLastChangeTime(today);
        salary.setEscCreateBy(empNo);
        salary.setEscLastChangeBy(empNo);

        salary.encryEmpSalaryConf(salary);
        this.salaryconfDAO.saveObject(salary);

        employee.setConfig(salary);
        employee.setEmpLastChangeTime(new Date());
        employee.setEmpLastChangeBy(new Employee(empNo));
        this.salaryconfDAO.updateObject(employee);

        return true;
    }

    public boolean updateSalaryconf(Empsalaryconfig salaryconf, String empNo) {
        try {
            salaryconf.setEscLastChangeBy(empNo);
            salaryconf.setEscLastChangeTime(new Date());
            salaryconf.encryEmpSalaryConf(salaryconf);
            this.salaryconfDAO.updateObject(salaryconf);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Employee findConfigByEmpId(String id) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.setFetchMode("empType", FetchMode.JOIN);
        detachedCriteria.setFetchMode("config", FetchMode.JOIN);
        detachedCriteria.setFetchMode("config.escJobgrade", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Employee.PROP_ID, id));

        List result = this.salaryconfDAO.findByCriteria(detachedCriteria);
        Employee employeeFromDB = null;

        if (result != null) {
            employeeFromDB = (Employee) result.get(0);
        }
        return employeeFromDB;
    }

    public Map<String, List[]> findSalaryConfigByNoPage(DetachedCriteria dc, Pager page,
            Employee emp) {
        dc.setFetchMode("empType", FetchMode.JOIN);
        dc.setFetchMode("empDeptNo", FetchMode.JOIN);
        dc.setFetchMode("empLocationNo", FetchMode.JOIN);
        dc.createAlias("config", "config", 1);
        dc.createAlias("config.escJobgrade", "config.escJobgrade", 1);
        dc.createAlias("config.escEsavId", "config.escEsavId", 1);
        dc.createAlias("config.escEsavId.esavEsac", "config.escEsavId.esavEsac", 1);

        dc.add(Restrictions.isNotNull("config"));

        dc.addOrder(Order.asc("empDistinctNo"));
        List<Employee> result = this.salaryconfDAO.findByCriteria(dc);
        if ((result == null) || (result.size() == 0))
            return null;

        Map dataDefMap = getAcctDataDefCache();

        Map resultMap = new HashMap();
        for (Employee employee : result) {
            Empsalaryconfig cfg = employee.getConfig();
            cfg.decryEmpSalaryConf(cfg);
            String accountName = cfg.getEscEsavId().getEsavEsac().getEsacName();
            List[] tmpListArray = (List[]) resultMap.get(accountName);
            if (tmpListArray == null) {
                tmpListArray = new ArrayList[] { new ArrayList(), new ArrayList() };
                tmpListArray[0].add("工号");
                tmpListArray[0].add("姓名");
                tmpListArray[0].add("部门");
                tmpListArray[0].add("用工形式");
                tmpListArray[0].add("工作地区");
                tmpListArray[0].add("状态");
                tmpListArray[0].add("银行帐号");
                tmpListArray[0].add("银行开户行");
                tmpListArray[0].add("成本中心");
                tmpListArray[0].add("薪资级别");
                tmpListArray[0].add("薪资帐套");
                tmpListArray[0].addAll((Collection) dataDefMap.get(cfg.getEscEsavId().getId()));
            }
            List contentList = tmpListArray[1];
            SalaryConfigAssist salaryConfigAssist = new SalaryConfigAssist();
            salaryConfigAssist.setEmpNo(employee.getEmpDistinctNo());
            salaryConfigAssist.setEmpName(employee.getEmpName());
            salaryConfigAssist.setEmpDept(employee.getEmpDeptNo().getDepartmentName());
            salaryConfigAssist.setEmpType(employee.getEmpType().getEmptypeName());
            salaryConfigAssist.setEmpLocation(employee.getEmpLocationNo().getLocationName());
            salaryConfigAssist
                    .setEmpStatus((employee.getEmpStatus().intValue() == 0) ? "离职" : "在职");
            salaryConfigAssist.setSalaryLevel(cfg.getEscJobgrade().getJobGradeName());
            salaryConfigAssist.setSalaryAccept(cfg.getEscEsavId().getId());
            salaryConfigAssist.setAcceptName(cfg.getEscEsavId().getEsavEsac().getEsacName());
            salaryConfigAssist.setEmpBankName(cfg.getEscBankName());
            salaryConfigAssist.setEmpCount(cfg.getEscBankAccountNo());
            salaryConfigAssist.setCostCenter(cfg.getEscCostCenter());

            String tmpTitle = (String) tmpListArray[0].get(tmpListArray[0].size() - 1);
            int size = Integer.valueOf(tmpTitle.split("-")[0].substring(1)).intValue();
            salaryConfigAssist.setList(getExportList(cfg, size));
            contentList.add(salaryConfigAssist);

            resultMap.put(accountName, tmpListArray);
        }
        return resultMap;
    }

    private List<BigDecimal> getExportList(Empsalaryconfig cfg, int size) {
        List list = new ArrayList();
        for (int i = 1; i <= size; ++i) {
            try {
                BigDecimal tmp = (BigDecimal) PropertyUtils.getProperty(cfg, "escColumn" + i);
                list.add(tmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private Map<String, List<String>> getAcctDataDefCache() {
        Map cache = new HashMap();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctitems.class);
        detachedCriteria.addOrder(Order.asc("esaiDataSeq"));
        detachedCriteria.setFetchMode("esaiEsdd", FetchMode.JOIN);
        List<Empsalaryacctitems> allItems = this.salaryconfDAO.findByCriteria(detachedCriteria);
        for (Empsalaryacctitems item : allItems) {
            List tmpList = (List) cache.get(item.getEsaiEsav().getId());
            if (tmpList == null) {
                tmpList = new ArrayList();
            }
            tmpList.add("A" + item.getEsaiDataSeq() + "-" + item.getEsaiEsdd().getEsddName());
            cache.put(item.getEsaiEsav().getId(), tmpList);
        }
        return cache;
    }

    public List<Empsalaryconfig> findConfig(DetachedCriteria dc, Pager page) {
        if (page == null) {
            BaseAction.addOrders(dc, null, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO
                    + "-up" });
            return this.salaryconfDAO.findByCriteria(dc);
        }
        BaseAction.addOrders(dc, page, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO
                + "-up" });
        page.splitPage(dc);
        return this.salaryconfDAO.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public List<Employee> findSalaryConfig(DetachedCriteria dc, Pager page, Employee emp) {
        dc.createAlias("empType", "empType", 1);
        dc.createAlias("empDeptNo", "empDeptNo", 1);
        dc.createAlias("config", "config", 1);
        dc.createAlias("config.escJobgrade", "escJobgrade", 1);
        dc.createAlias("config.escEsavId", "escEsavId", 1);
        dc.createAlias("escEsavId.esavEsac", "esavEsac", 1);

        page.splitPage(dc);
        List empConfigList = this.salaryconfDAO.findByCriteria(dc, page.getPageSize(), page
                .getCurrentPage());

        IEmpSalaryAcctitemsBo empSalaryAcctitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        Map itemsMap = empSalaryAcctitemsBo.getItemsByEmployeeConf(empConfigList);

        for (Iterator it = empConfigList.iterator(); it.hasNext();) {
            Employee employeeTemp = (Employee) it.next();

            Empsalaryconfig esc = employeeTemp.getConfig();
            if (esc != null) {
                try {
                    esc.decryEmpSalaryConf(esc);
                    List acctList = (List) itemsMap.get(esc.getEscEsavId().getId());
                    calcSalaryConfByType(acctList, esc);
                    employeeTemp.setConfig(esc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return empConfigList;
    }

    public List<Empsalaryconfig> getSalaryConfigByAcctVersion(String acctversionId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryconfig.class);
        detachedCriteria.setFetchMode(Empsalaryconfig.PROP_EMPLOYEE, FetchMode.JOIN);
        detachedCriteria.setFetchMode("employee.empBenefitType", FetchMode.JOIN);
        detachedCriteria.setFetchMode("employee.benefit", FetchMode.JOIN);
        detachedCriteria.setFetchMode("escJobgrade", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq("escEsavId.id", acctversionId));
        List<Empsalaryconfig> configList = this.salaryconfDAO.findByCriteria(detachedCriteria);
        if (configList.size() > 0) {
            for (Empsalaryconfig config : configList)
                config.decryEmpSalaryConf(config);
        }
        return configList;
    }

    public int hasSalaryConfigByAcctVersion(String acctversionId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryconfig.class);
        detachedCriteria.add(Restrictions.eq("escEsavId.id", acctversionId));
        detachedCriteria.setProjection(Projections.rowCount());
        List list = this.salaryconfDAO.findByCriteria(detachedCriteria);
        return ((Integer) list.get(0)).intValue();
    }

    public List<Empsalaryconfig> getConfigByIds(String[] ids) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryconfig.class);
        detachedCriteria.setFetchMode("employee", FetchMode.JOIN);
        detachedCriteria.setFetchMode("employee.empBenefitType", FetchMode.JOIN);
        detachedCriteria.setFetchMode("employee.benefit", FetchMode.JOIN);
        detachedCriteria.setFetchMode("escJobgrade", FetchMode.JOIN);
        detachedCriteria.setFetchMode("escEsavId", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.in(Empsalaryconfig.PROP_ID, ids));
        List salaryConfigList = this.salaryconfDAO.findByCriteria(detachedCriteria);
        return salaryConfigList;
    }

    public boolean calcSalaryConfByTypes(List<Employee> empConfigList) {
        IEmpSalaryAcctitemsBo empSalaryAcctitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        Map itemsMap = empSalaryAcctitemsBo.getItemsByEmployeeConf(empConfigList);

        for (Employee emp : empConfigList) {
            if (emp.getConfig() == null) {
                continue;
            }
            Empsalaryconfig esc = emp.getConfig();
            esc.decryEmpSalaryConf(esc);

            calcSalaryConfByType((List) itemsMap.get(emp.getConfig().getEscEsavId().getId()), esc);
        }
        return true;
    }

    public Empsalaryconfig calcSalaryConfByType(List<Empsalaryacctitems> acctList,
            Empsalaryconfig esc) {
        if ((acctList == null) || (acctList.size() <= 0) || (esc == null))
            return null;

        try {
            List sumList = new ArrayList();
            for (int i = 0; i <= 20; ++i) {
                sumList.add(new BigDecimal(0));
            }
            Class ownerClass = esc.getClass();

            int i = 0;
            for (int j = acctList.size(); i < j; ++i) {
                Empsalaryacctitems esai = (Empsalaryacctitems) acctList.get(i);

                Method espMethod = ownerClass.getMethod("getEscColumn" + esai.getEsaiDataSeq(),
                                                        new Class[0]);
                BigDecimal temp = (BigDecimal) espMethod.invoke(esc, new Object[0]);
                if (temp != null) {
                    sumList.set(esai.getEsaiEsdd().getEsddDataType().intValue(),
                                ((BigDecimal) sumList.get(esai.getEsaiEsdd().getEsddDataType()
                                        .intValue())).add(temp));
                }
            }
            esc.setShowColumn1((BigDecimal) sumList.get(1));
            esc.setShowColumn4((BigDecimal) sumList.get(4));
            esc.setShowColumn7((BigDecimal) sumList.get(7));
            esc.setShowColumn8((BigDecimal) sumList.get(8));
            esc.setShowColumn17((BigDecimal) sumList.get(17));
            esc.setShowColumn18((BigDecimal) sumList.get(18));
            esc.setShowColumn19((BigDecimal) sumList.get(19));
            esc.setShowColumn20((BigDecimal) sumList.get(20));

            esc.setShowColumn10((BigDecimal) sumList.get(10));
            esc.setShowColumn11((BigDecimal) sumList.get(11));
            esc.setShowColumn12((BigDecimal) sumList.get(12));
            esc.setShowColumn13((BigDecimal) sumList.get(13));
            esc.setShowColumn14((BigDecimal) sumList.get(14));
            esc.setShowColumn15((BigDecimal) sumList.get(15));
            esc.setShowColumn16((BigDecimal) sumList.get(16));

            return esc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[][] findSalaryBenefitValue(String empId) {
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        String[] fetch = { "config" };
        Employee emp = empBo.loadEmp(empId, fetch);
        if (emp == null)
            return (String[][]) null;
        Empsalaryconfig esc = emp.getConfig();

        if (esc != null) {
            esc.decryEmpSalaryConf(esc);
            IEmpSalaryAcctitemsBo empSalaryAcctitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");
            List acctList = empSalaryAcctitemsBo.getItemsByAcctversion(esc.getEscEsavId().getId());
            calcSalaryConfByType(acctList, esc);
            String[][] returnValue = new String[7][2];
            returnValue[0][0] = "个人缴社保";
            returnValue[0][1] = esc.getShowColumn10().toString();
            returnValue[1][0] = "公司代缴社保";
            returnValue[1][1] = esc.getShowColumn11().toString();
            returnValue[2][0] = "个人缴公积金";
            returnValue[2][1] = esc.getShowColumn12().toString();
            returnValue[3][0] = "公司代缴公积金";
            returnValue[3][1] = esc.getShowColumn13().toString();
            returnValue[4][0] = "其他福利项";
            returnValue[4][1] = esc.getShowColumn14().toString();
            returnValue[5][0] = "个人缴社保总额";
            returnValue[5][1] = esc.getShowColumn15().toString();
            returnValue[6][0] = "公司代缴社保总额";
            returnValue[6][1] = esc.getShowColumn16().toString();

            return returnValue;
        }
        return (String[][]) null;
    }

    public String batchUpdateConfig(String currEmpNo, List<Empsalaryconfig> configList) {
        if ((configList == null) || (configList.size() == 0))
            return "";

        Date changeTime = new Date();
        StringBuffer strBuf = new StringBuffer("");
        for (Empsalaryconfig config : configList) {
            config.setEscLastChangeBy(currEmpNo);
            config.setEscLastChangeTime(changeTime);
            config.encryEmpSalaryConf(config);

            strBuf.append(config.getEmployee().getEmpName()).append("、");

            Employee emp = config.getEmployee();
            config.setEmployee(new Employee(emp.getId()));
        }

        saveOrupdate(configList);

        String str = new String("");
        if (strBuf.length() > 0) {
            str = str + strBuf.substring(0, strBuf.length() - 1);
        }
        return str;
    }

    public List<Empsalaryacctitems> getConfigItemsById(String configId) {
        Empsalaryconfig config = loadSalaryconf(configId, new String[0]);
        if (config == null) {
            return null;
        }

        config.decryEmpSalaryConf(config);
        IEmpSalaryAcctitemsBo empSalaryAcctitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        List esacItems = empSalaryAcctitemsBo.getItemsByAcctversion(config.getEscEsavId().getId());
        Empsalaryacctitems item = new Empsalaryacctitems();
        Class ownerClass = config.getClass();
        try {
            for (int i = 0; i < esacItems.size(); ++i) {
                item = (Empsalaryacctitems) esacItems.get(i);
                Method escMethod = ownerClass.getMethod("getEscColumn" + item.getEsaiDataSeq(),
                                                        new Class[0]);
                item.setItemConfigValue((BigDecimal) escMethod.invoke(config, new Object[0]));
                item.setItemValue((BigDecimal) escMethod.invoke(config, new Object[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return esacItems;
    }

    public Boolean deleteSalaryConfig(String esavId) {
        try {
            String hql = "delete Empsalaryconfig as esc where esc.escEsavId.id='" + esavId + "'";
            this.salaryconfDAO.exeHql(hql);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.valueOf(false);
    }

    public List<Empsalaryconfig> changeSalaryConfig(String acctversionId,
            Map<String, Integer> oldMap, List<Empsalaryacctitems> newItems) {
        List configList = getSalaryConfigByAcctVersion(acctversionId);
        if (configList.size() == 0)
            return configList;

        shuffleSalaryConfig(configList, oldMap, newItems);

        interpretConfig((Empsalaryconfig[]) configList.toArray(new Empsalaryconfig[configList
                .size()]));

        salaryconfigSimplify(configList);

        return configList;
    }

    private Boolean shuffleSalaryConfig(List<Empsalaryconfig> salaryConfigList,
            Map<String, Integer> fromMap, List<Empsalaryacctitems> toItems) {
        for (int i = 0; i < salaryConfigList.size(); ++i) {
            try {
                Empsalaryconfig salaryconfig = (Empsalaryconfig) ((Empsalaryconfig) salaryConfigList
                        .get(i)).clone();

                String dataDefId = new String();

                for (int j = 0; j < toItems.size(); ++j) {
                    dataDefId = ((Empsalaryacctitems) toItems.get(j)).getEsaiEsdd().getEsddId();
                    Integer fromPos = (Integer) fromMap.get(dataDefId);

                    if ((fromPos != null)
                            && (((Empsalaryacctitems) toItems.get(j)).getEsaiDataIsCalc()
                                    .intValue() == 0))
                        if (fromPos.intValue() != j) {
                            Object escValue = PropertyUtils.getProperty(salaryconfig, "escColumn"
                                    + (fromPos.intValue() + 1));
                            if (escValue == null)
                                PropertyUtils.setProperty(salaryConfigList.get(i), "escColumn"
                                        + (j + 1), new BigDecimal("0.00"));
                            else
                                PropertyUtils.setProperty(salaryConfigList.get(i), "escColumn"
                                        + (j + 1), ((BigDecimal) escValue).setScale(2));
                        } else
                            PropertyUtils.setProperty(salaryConfigList.get(i), "escColumn"
                                    + (j + 1), new BigDecimal("0.00"));
                }

                ((Empsalaryconfig) salaryConfigList.get(i)).setAcctItems(toItems);
            } catch (Exception e) {
                e.printStackTrace();
                return Boolean.valueOf(false);
            }
        }
        return Boolean.valueOf(true);
    }

    public void interpretConfig(Empsalaryconfig[] configArr) {
        if ((configArr == null) || (configArr.length == 0))
            return;

        InterpreterExecuteContext context = new InterpreterExecuteContext(null);
        context.execute(configArr);
    }

    private Boolean salaryconfigSimplify(List<Empsalaryconfig> salaryConfigList) {
        if ((salaryConfigList == null) || (salaryConfigList.size() == 0))
            return Boolean.valueOf(false);

        for (Empsalaryconfig config : salaryConfigList) {
            config.setEmployee(new Employee(config.getEmployee().getId()));
            config.setEscJobgrade(new Jobgrade(config.getEscJobgrade().getId()));
            config.encryEmpSalaryConf(config);
        }
        return Boolean.valueOf(true);
    }

    public List<String> getAllCostCenter() {
        String hql = "select distinct escCostCenter from Empsalaryconfig where escCostCenter is not null";
        List costCenterList = this.salaryconfDAO.exeHqlList(hql);

        return costCenterList;
    }

    public List<Employee> getEmployeeWithConfig(String[] empIds) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.setFetchMode("config", FetchMode.JOIN);
        detachedCriteria.setFetchMode("config.escJobgrade", FetchMode.JOIN);
        detachedCriteria.setFetchMode("config.escEsavId", FetchMode.JOIN);
        detachedCriteria.setFetchMode("empBenefitType", FetchMode.JOIN);
        detachedCriteria.setFetchMode("benefit", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.in(Employee.PROP_ID, empIds));
        List empList = this.salaryconfDAO.findByCriteria(detachedCriteria);

        return empList;
    }

    public List<Empsalaryconfig> getAllEmpsalaryconfig() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryconfig.class);
        detachedCriteria.addOrder(Order.asc(Empsalaryconfig.PROP_ID));
        return this.salaryconfDAO.findByCriteria(detachedCriteria);
    }

    public IStatusDAO getStatusDAO() {
        return this.statusDAO;
    }

    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }

    public ISalaryconfDAO getSalaryconfDAO() {
        return this.salaryconfDAO;
    }

    public void setSalaryconfDAO(ISalaryconfDAO salaryconfDAO) {
        this.salaryconfDAO = salaryconfDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.SalaryconfBoImpl JD-Core Version: 0.5.4
 */