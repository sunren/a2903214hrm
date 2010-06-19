package com.hr.compensation.bo;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.Status;
import com.hr.compensation.dao.ICompaplanDAO;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.configuration.dao.IStatusDAO;
import com.hr.configuration.domain.Statusconf;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CompaplanBoImpl implements ICompaplanBo, Status, Constants {
    private ICompaplanDAO compaplanDAO;
    private IStatusDAO statusDAO;

    public boolean deleteCompaplan(Empsalaryadj empcompaplan) {
        if (empcompaplan == null)
            return false;
        try {
            this.compaplanDAO.deleteObject(empcompaplan);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public <E> List<E> getObjects(Class<E> clas, String[] fetch) {
        return this.compaplanDAO.getObjects(clas, fetch);
    }

    public Empsalaryadj loadCompaplan(Object id, String[] fetch) {
        return (Empsalaryadj) this.compaplanDAO.loadObject(Empsalaryadj.class, id, fetch,
                                                           new boolean[0]);
    }

    public boolean insertCompaplan(Empsalaryadj compaplan, String currentuserNo) {
        try {
            Date today = new Date();
            compaplan.setEsaCreateTime(today);
            compaplan.setEsaLastChangeTime(today);
            Employee employee = new Employee(currentuserNo);
            compaplan.setEsaCreateBy(employee);
            compaplan.setEsaLastChangeBy(employee);
            compaplan.setEsaChanged(Integer.valueOf(0));
            compaplan.encryEmpSalaryAdj(compaplan);
            this.compaplanDAO.saveObject(compaplan);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer searchCompaplanSubmitStatus(String yearmonth) {
        int year = Integer.parseInt(yearmonth.substring(0, 4));
        int month = Integer.parseInt(yearmonth.substring(4));
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(year + "-" + month + "-" + 1);
            detachedCriteria.add(Restrictions.eq(Empsalaryadj.PROP_ESA_EFFDATE_PRO, date));
            detachedCriteria
                    .add(Restrictions.eq(Empsalaryadj.PROP_ESA_STATUS, Integer.valueOf(11)));
            Integer counts = Integer.valueOf(this.compaplanDAO
                    .findRowCountByCriteria(detachedCriteria));
            return counts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.valueOf(0);
    }

    public List<Empsalaryadj> mysearchCompaplan(DetachedCriteria dc, Pager page) {
        if (page == null) {
            BaseAction.addOrders(dc, page, new String[] { Empsalaryadj.PROP_ESA_EMPNO + "-up" });
            return this.compaplanDAO.findByCriteria(dc);
        }

        BaseAction.addOrders(dc, page, new String[] { Empsalaryadj.PROP_ESA_EFFDATE_PRO + "-down",
                "emp." + Employee.PROP_EMP_NAME + "-up" });
        page.splitPage(dc);
        return this.compaplanDAO.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    private Empsalaryadj calEsaByItems(Empsalaryadj esa,
            Map<String, List<Empsalaryacctitems>> itemCache) throws Exception {
        Class ownerClass = esa.getClass();
        String cur = esa.getEsaEsavIdCur().getId();
        String pro = esa.getEsaEsavIdPro().getId();

        List<Empsalaryacctitems> curItems = (List) itemCache.get(cur);
        for (Empsalaryacctitems item : curItems) {
            if (item.getEsaiEsdd().getEsddDataType().intValue() == 1) {
                Method esaMethod = ownerClass.getMethod("getEsaColumn" + item.getEsaiDataSeq()
                        + "Cur", new Class[0]);

                esa.setBasicSalary((BigDecimal) esaMethod.invoke(esa, new Object[0]));
            }
            if (item.getEsaiEsdd().getEsddDataType().intValue() == 8) {
                Method esaMethod = ownerClass.getMethod("getEsaColumn" + item.getEsaiDataSeq()
                        + "Cur", new Class[0]);

                esa.setMoneybeforetax((BigDecimal) esaMethod.invoke(esa, new Object[0]));
            }
        }
        List<Empsalaryacctitems> proItems = (List) itemCache.get(pro);
        for (Empsalaryacctitems item : proItems) {
            if (item.getEsaiEsdd().getEsddDataType().intValue() == 1) {
                Method esaMethod = ownerClass.getMethod("getEsaColumn" + item.getEsaiDataSeq()
                        + "Pro", new Class[0]);

                esa.setNewBasicSalary((BigDecimal) esaMethod.invoke(esa, new Object[0]));
            }
            if (item.getEsaiEsdd().getEsddDataType().intValue() == 8) {
                Method esaMethod = ownerClass.getMethod("getEsaColumn" + item.getEsaiDataSeq()
                        + "Pro", new Class[0]);

                esa.setNewmoneybeforetax((BigDecimal) esaMethod.invoke(esa, new Object[0]));
            }
        }
        return esa;
    }

    private Empsalaryadj calEmpsalaryadj(Empsalaryadj empsalaryadj,
            List<Empsalaryacctitems> relatedesaiList) {
        Class ownerClass = empsalaryadj.getClass();
        try {
            for (Empsalaryacctitems esai : relatedesaiList) {
                if (esai.getEsaiEsdd().getEsddDataType().intValue() == 1) {
                    Method esaMethod = ownerClass.getMethod("getEsaColumn" + esai.getEsaiDataSeq()
                            + "Pro", new Class[0]);
                    empsalaryadj.setNewBasicSalary((BigDecimal) esaMethod.invoke(empsalaryadj,
                                                                                 new Object[0]));
                }
                if (esai.getEsaiEsdd().getEsddDataType().intValue() == 8) {
                    Method esaMethod = ownerClass.getMethod("getEsaColumn" + esai.getEsaiDataSeq()
                            + "Pro", new Class[0]);
                    empsalaryadj.setNewmoneybeforetax((BigDecimal) esaMethod.invoke(empsalaryadj,
                                                                                    new Object[0]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return empsalaryadj;
    }

    public int hasSalaryAdjByAcctVersion(String acctversionId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        detachedCriteria.add(Restrictions.or(Restrictions.eq("esaEsavIdCur.id", acctversionId),
                                             Restrictions.eq("esaEsavIdPro.id", acctversionId)));

        detachedCriteria.setProjection(Projections.rowCount());
        List list = this.compaplanDAO.findByCriteria(detachedCriteria);
        return ((Integer) list.get(0)).intValue();
    }

    public List<Empsalaryconfig> mysearchBatchCompaplan(DetachedCriteria dc, Pager page) {
        BaseAction.addOrders(dc, page, new String[] { "emp." + Employee.PROP_EMP_NAME + "-up" });
        page.splitPage(dc);
        return this.compaplanDAO.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public boolean calcSalaryConfWithComp(List<Empsalaryconfig> configList) {
        if (configList.isEmpty())
            return false;

        IEmpSalaryAcctitemsBo empSalaryAcctitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        Map itemsMap = empSalaryAcctitemsBo.getItemsBySalaryConf(configList);

        Map compaplansMap = getEmpsalaryadjMap();

        for (Empsalaryconfig salaryconf : configList) {
            salaryconf.decryEmpSalaryConf(salaryconf);
            Empsalaryadj compaplanFromDB = (Empsalaryadj) compaplansMap.get(salaryconf
                    .getEmployee().getId());
            if (compaplanFromDB != null) {
                compaplanFromDB.decryEmpSalaryAdj(compaplanFromDB);
                calEmpsalaryadj(compaplanFromDB, (List) itemsMap.get(compaplanFromDB
                        .getEsaEsavIdPro().getId()));

                salaryconf.setComp(compaplanFromDB);
                salaryconf.setNewBasicSalary(compaplanFromDB.getNewBasicSalary());
                salaryconf.setNewmoneybeforetax(compaplanFromDB.getNewmoneybeforetax());
            }

            ISalaryconfBo salaryconfBo = (ISalaryconfBo) SpringBeanFactory.getBean("salaryconfBo");
            salaryconfBo.calcSalaryConfByType((List) itemsMap
                    .get(salaryconf.getEscEsavId().getId()), salaryconf);
        }
        return true;
    }

    public boolean calcSalaryAdj(List<Empsalaryadj> result) {
        IEmpSalaryAcctitemsBo empSalaryAcctitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        Map itemsMap = empSalaryAcctitemsBo.getItemsBySalaryAdj(result);
        try {
            for (Empsalaryadj esa : result) {
                esa.decryEmpSalaryAdj(esa);
                esa = calEsaByItems(esa, itemsMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /** @deprecated */
    private Map<String, List<Empsalaryacctitems>> createAcctItemsMap() {
        DetachedCriteria dcforEsa = DetachedCriteria.forClass(Empsalaryacctitems.class);
        dcforEsa.setFetchMode("esaiEsav", FetchMode.JOIN);
        dcforEsa.addOrder(Order.asc("esaiDataSeq"));
        List<Empsalaryacctitems> esaiList = this.compaplanDAO.findByCriteria(dcforEsa);
        Map itemsMap = new HashMap();
        for (Empsalaryacctitems item : esaiList) {
            List items = (List) itemsMap.get(item.getEsaiEsav().getId());
            if (items == null) {
                items = new ArrayList();
            }
            items.add(item);
            itemsMap.put(item.getEsaiEsav().getId(), items);
        }
        return itemsMap;
    }

    private Empsalaryconfig calSalaryConfig(Empsalaryconfig salaryConfig,
            List<Empsalaryacctitems> esaiList) {
        Class ownerClass = Empsalaryconfig.class;
        try {
            for (Empsalaryacctitems esai : esaiList) {
                if (esai.getEsaiEsdd().getEsddDataType().intValue() == 1) {
                    Method escMethod = ownerClass.getMethod("getEscColumn" + esai.getEsaiDataSeq(),
                                                            new Class[0]);

                    salaryConfig.setBasicSalary((BigDecimal) escMethod.invoke(salaryConfig,
                                                                              new Object[0]));
                }

                if (esai.getEsaiEsdd().getEsddDataType().intValue() == 8) {
                    Method escMethod = ownerClass.getMethod("getEscColumn" + esai.getEsaiDataSeq(),
                                                            new Class[0]);

                    salaryConfig.setMoneybeforetax((BigDecimal) escMethod.invoke(salaryConfig,
                                                                                 new Object[0]));
                }
            }

            return salaryConfig;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateSalaryConf(Empsalaryadj compaplan, String currentUserId) {
        try {
            Empsalaryconfig salaryconfigFromDB = (Empsalaryconfig) this.compaplanDAO
                    .loadObject(Empsalaryconfig.class, compaplan.getEsaEmpno().getId(), null,
                                new boolean[0]);

            salaryconfigFromDB.setEscLastChangeBy(currentUserId);
            salaryconfigFromDB.setEscLastChangeTime(new Date());
            salaryconfigFromDB.setEscLastEffDate(compaplan.getEsaCurEffDate());
            salaryconfigFromDB.setEscLastIncrRate(compaplan.getEsaCurIncrRate());
            Class escClass = salaryconfigFromDB.getClass();
            Class esaClass = compaplan.getClass();

            for (int i = 1; i <= 48; ++i) {
                Method esaMethod = esaClass.getMethod("getEsaColumn" + i + "Pro", new Class[0]);
                Method escMethod = escClass.getMethod("setEscColumn" + i,
                                                      new Class[] { BigDecimal.class });

                escMethod.invoke(salaryconfigFromDB, new Object[] { (BigDecimal) esaMethod
                        .invoke(compaplan, new Object[0]) });
            }

            salaryconfigFromDB.setEscEsavId(compaplan.getEsaEsavIdPro());
            if (compaplan.getEsaJobgradePro() != null) {
                salaryconfigFromDB.setEscJobgrade(compaplan.getEsaJobgradePro());
            }

            this.compaplanDAO.updateObject(salaryconfigFromDB);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public Empsalaryadj loadCompaplanInfoByEmpNo(String empNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        detachedCriteria.add(Restrictions.eq("esaEmpno.id", empNo));
        detachedCriteria.add(Restrictions.eq("esaStatus", Integer.valueOf(11)));
        List result = this.compaplanDAO.findByCriteria(detachedCriteria);
        if (result.size() > 0) {
            return (Empsalaryadj) result.get(0);
        }
        return null;
    }

    public Empsalaryadj loadCompaplanInfoByEmpNo(String empNo, Date date) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        detachedCriteria.add(Restrictions.eq("esaEmpno.id", empNo));
        detachedCriteria.add(Restrictions.eq("esaStatus", Integer.valueOf(41)));
        detachedCriteria.add(Restrictions.ge("esaCurEffDate", date));
        detachedCriteria.addOrder(Order.asc("esaCurEffDate"));
        List result = this.compaplanDAO.findByCriteria(detachedCriteria);
        if (result.size() > 0) {
            return (Empsalaryadj) result.get(0);
        }
        return null;
    }

    private Map<String, Empsalaryadj> getEmpsalaryadjMap() {
        Map empsalaryadjs = new HashMap();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        detachedCriteria.setFetchMode("esaEcptypeId", FetchMode.JOIN);
        detachedCriteria.setFetchMode("esaJobgradePro", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq("esaStatus", Integer.valueOf(11)));
        List<Empsalaryadj> result = this.compaplanDAO.findByCriteria(detachedCriteria);
        for (Empsalaryadj adj : result) {
            empsalaryadjs.put(adj.getEsaEmpno().getId(), adj);
        }
        return empsalaryadjs;
    }

    public void updateCompaplan(Empsalaryadj compaplan, Empsalaryadj compaplanFromDB,
            String currentuserNo) {
        compaplanFromDB.setEsaComments(compaplan.getEsaComments());
        compaplanFromDB.setEsaCurIncrRate(compaplan.getEsaCurIncrRate());
        compaplanFromDB.setEsaCurIncrRate1(compaplan.getEsaCurIncrRate1());
        compaplanFromDB.setEsaJobgradePro(compaplan.getEsaJobgradePro());
        compaplanFromDB.setEsaEsavIdPro(compaplan.getEsaEsavIdPro());
        compaplanFromDB.setEsaEcptypeId(compaplan.getEsaEcptypeId());
        compaplanFromDB.setEsaCurEffDate(compaplan.getEsaCurEffDate());
        compaplanFromDB.setEsaLastChangeTime(new Date());
        compaplanFromDB.setEsaLastChangeBy(new Employee(currentuserNo));
        compaplanFromDB.setEsaChanged(Integer.valueOf(0));
        try {
            Class oldClass = compaplanFromDB.getClass();
            Class compaClass = compaplan.getClass();

            for (int i = 1; i <= 48; ++i) {
                Method compaMethod = compaClass.getMethod("getEsaColumn" + i + "Cur", new Class[0]);
                Method oldMethod = oldClass.getMethod("setEsaColumn" + i + "Cur",
                                                      new Class[] { BigDecimal.class });

                oldMethod.invoke(compaplanFromDB, new Object[] { (BigDecimal) compaMethod
                        .invoke(compaplan, new Object[0]) });

                compaMethod = compaClass.getMethod("getEsaColumn" + i + "Pro", new Class[0]);
                oldMethod = oldClass.getMethod("setEsaColumn" + i + "Pro",
                                               new Class[] { BigDecimal.class });

                oldMethod.invoke(compaplanFromDB, new Object[] { (BigDecimal) compaMethod
                        .invoke(compaplan, new Object[0]) });
            }

            compaplanFromDB.encryEmpSalaryAdj(compaplanFromDB);
            this.compaplanDAO.updateObject(compaplanFromDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateCompaplanForSlary(String yearmonth, String currentEmpNo) {
        int year = Integer.parseInt(yearmonth.substring(0, 4));
        int month = Integer.parseInt(yearmonth.substring(4));
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(year + "-" + month + "-" + 1);
            detachedCriteria.add(Restrictions.eq(Empsalaryadj.PROP_ESA_EFFDATE_PRO, date));
            detachedCriteria
                    .add(Restrictions.eq(Empsalaryadj.PROP_ESA_STATUS, Integer.valueOf(11)));
            List<Empsalaryadj> result = this.compaplanDAO.findByCriteria(detachedCriteria);
            for (Empsalaryadj empcompaplan : result) {
                updateSalaryConf(empcompaplan, currentEmpNo);
                empcompaplan.setEsaStatus(Integer.valueOf(41));
                this.compaplanDAO.updateObject(empcompaplan);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Statusconf> getCompStatus() {
        return this.statusDAO.getStatusByTable("empsalaryadj");
    }

    public int updateBackupSalaryConfig(String empId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        detachedCriteria.add(Restrictions.eq("esaEmpno.id", empId));

        detachedCriteria.addOrder(Order.desc("esaCurEffDate"));

        List list = this.compaplanDAO.findByCriteria(detachedCriteria);

        DetachedCriteria detachedC = DetachedCriteria.forClass(Empsalaryconfig.class);
        detachedC.add(Restrictions.eq("employee.id", empId));
        List configList = this.compaplanDAO.findByCriteria(detachedC);
        Empsalaryconfig config = (Empsalaryconfig) configList.get(0);
        if (list.size() == 0) {
            return 0;
        }
        Empsalaryadj salaryadj = (Empsalaryadj) list.get(0);
        int status = salaryadj.getEsaStatus().intValue();
        if (status == 11) {
            return 2;
        }

        try {
            Class ownerClass = config.getClass();
            Class adjClass = salaryadj.getClass();
            for (int i = 1; i <= 48; ++i) {
                Method adjMethod = adjClass.getMethod("getEsaColumn" + i + "Cur", new Class[0]);
                Object object = adjMethod.invoke(salaryadj, new Object[0]);

                if (object != null) {
                    BigDecimal value = (BigDecimal) object;

                    Method escMethod = ownerClass.getMethod("setEscColumn" + i,
                                                            new Class[] { BigDecimal.class });

                    escMethod.invoke(config, new Object[] { value });
                }
            }
            this.compaplanDAO.deleteObject(salaryadj);
            if (status == 41)
                this.compaplanDAO.updateObject(config);
            else {
                this.compaplanDAO.deleteObject(config);
            }

            return 1;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public List<Empsalaryacctitems> getCompaItemsById(String id, String curOrpro) {
        Empsalaryadj compa = loadCompaplan(id, null);
        if (compa == null) {
            return null;
        }

        compa.decryEmpSalaryAdj(compa);
        IEmpSalaryAcctitemsBo empSalaryAcctitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");

        Empsalaryacctitems item = new Empsalaryacctitems();
        if (curOrpro.equalsIgnoreCase("cur")) {
            List esacItems = empSalaryAcctitemsBo.getItemsByAcctversion(compa.getEsaEsavIdCur()
                    .getId());

            Class ownerClass = compa.getClass();
            try {
                for (int i = 0; i < esacItems.size(); ++i) {
                    item = (Empsalaryacctitems) esacItems.get(i);
                    Method escMethod = ownerClass.getMethod("getEsaColumn" + item.getEsaiDataSeq()
                            + "Cur", new Class[0]);

                    item.setItemConfigValue((BigDecimal) escMethod.invoke(compa, new Object[0]));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return esacItems;
        }
        List esacItems = empSalaryAcctitemsBo
                .getItemsByAcctversion(compa.getEsaEsavIdPro().getId());

        Class ownerClass = compa.getClass();
        try {
            for (int i = 0; i < esacItems.size(); ++i) {
                item = (Empsalaryacctitems) esacItems.get(i);
                Method escMethod = ownerClass.getMethod("getEsaColumn" + item.getEsaiDataSeq()
                        + "Pro", new Class[0]);

                item.setItemConfigValue((BigDecimal) escMethod.invoke(compa, new Object[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return esacItems;
    }

    public IStatusDAO getStatusDAO() {
        return this.statusDAO;
    }

    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }

    public ICompaplanDAO getCompaplanDAO() {
        return this.compaplanDAO;
    }

    public void setCompaplanDAO(ICompaplanDAO compaplanDAO) {
        this.compaplanDAO = compaplanDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.CompaplanBoImpl JD-Core Version: 0.5.4
 */