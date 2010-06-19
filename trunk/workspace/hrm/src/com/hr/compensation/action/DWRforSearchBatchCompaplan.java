package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.DWRUtil;
import com.hr.base.Status;
import com.hr.compensation.bo.ICompaplanBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.IEmpsalaryperiodBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class DWRforSearchBatchCompaplan extends BaseAction implements Status, Constants {
    private Empsalaryadj esa;
    private Empsalaryconfig esc;
    private String curEffDate;
    private String compaplanIds;
    private String employeeId;

    public DWRforSearchBatchCompaplan() {
        this.esa = new Empsalaryadj();

        this.esc = new Empsalaryconfig();
    }

    private Empsalaryadj getCompaByConfig(Empsalaryconfig config, Empsalaryconfig newConfig,
            Empsalaryadj esa) throws Exception {
        Class ownerClass = config.getClass();
        Class adjClass = esa.getClass();

        for (int i = 1; i <= 48; ++i) {
            Method escMethod = ownerClass.getMethod("getEscColumn" + i, new Class[0]);
            Method adjMethod = adjClass.getMethod("setEsaColumn" + i + "Cur",
                                                  new Class[] { BigDecimal.class });

            adjMethod.invoke(esa, new Object[] { (BigDecimal) escMethod.invoke(config,
                                                                               new Object[0]) });

            escMethod = ownerClass.getMethod("getEscColumn" + i, new Class[0]);
            adjMethod = adjClass.getMethod("setEsaColumn" + i + "Pro",
                                           new Class[] { BigDecimal.class });

            adjMethod.invoke(esa, new Object[] { (BigDecimal) escMethod.invoke(newConfig,
                                                                               new Object[0]) });
        }
        esa.setEsaJobgradeCur(config.getEscJobgrade());
        esa.setEsaJobgradePro(newConfig.getEscJobgrade());
        esa.setEsaEsavIdCur(config.getEscEsavId());
        esa.setEsaEsavIdPro(newConfig.getEscEsavId());
        return esa;
    }

    public String deleteCompaplan() {
        if (StringUtils.isEmpty(this.compaplanIds)) {
            return "error";
        }
        ICompaplanBo compaplanBo = (ICompaplanBo) getBean("compaplanBo");
        Empsalaryadj compaplanFromDB = compaplanBo.loadCompaplan(this.compaplanIds,
                                                                 new String[] { "esaEmpno" });
        if (compaplanFromDB == null) {
            return "error";
        }

        String empName = compaplanFromDB.getEsaEmpno().getEmpName();
        if (compaplanBo.deleteCompaplan(compaplanFromDB)) {
            addActionMessage("删除员工" + empName + "的调薪计划成功！");
            return "success";
        }
        addActionMessage("删除员工" + empName + "的调薪计划失败！");
        return "error";
    }

    public List<Empsalaryacctitems> getAcctItemsById(String acctversionId, String configId)
            throws Exception {
        String hasAuth = DWRUtil.checkAuth("searchBatchCompaplan", "execute");
        if ("error".equals(hasAuth)) {
            return null;
        }
        IEmpSalaryAcctitemsBo acctItemBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");

        ISalaryconfBo salaryconf = (ISalaryconfBo) getBean("salaryconfBo");
        Empsalaryconfig config = salaryconf.loadSalaryconf(configId, new String[0]);
        config.decryEmpSalaryConf(config);
        List<Empsalaryacctitems> acctList = acctItemBo.getItemsByAcctversion(acctversionId);
        Class ownerClass;
        if (config != null) {
            ownerClass = config.getClass();
            for (Empsalaryacctitems esai : acctList) {
                Method escMethod = ownerClass.getMethod("getEscColumn" + esai.getEsaiDataSeq(),
                                                        new Class[0]);

                esai.setItemValue((BigDecimal) escMethod.invoke(config, new Object[0]));
            }
        }
        return acctList;
    }

    public List<Empsalaryacctitems> getConfigItemsById(String configId) {
        String hasAuth = DWRUtil.checkAuth("searchSalaryPaid", "execute");
        if ("error".equals(hasAuth)) {
            return null;
        }
        ISalaryconfBo salaryconf = (ISalaryconfBo) getBean("salaryconfBo");
        return salaryconf.getConfigItemsById(configId);
    }

    public String addEmpsalaryadj() throws Exception {
        String currentUserNo = getCurrentEmpNo();

        String empId = this.esa.getEsaEmpno().getId();
        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        Employee emp = employeeBo.loadEmp(empId, new String[] { "config" });
        if ((emp == null) || (emp.getConfig() == null)) {
            return "error";
        }

        IEmpsalaryperiodBo periodBo = (IEmpsalaryperiodBo) SpringBeanFactory
                .getBean("empsalaryperiodBO");
        String[] yearMonthArr = this.curEffDate.split("-");
        Integer periodStatus = periodBo.getEspdStatusNew(yearMonthArr[0] + yearMonthArr[1]);
        if ((periodStatus != null) && (periodStatus.intValue() == 2)) {
            if (yearMonthArr[1].charAt(0) == '0') {
                yearMonthArr[1] = yearMonthArr[1].substring(1);
            }
            addErrorInfo(yearMonthArr[0] + "年" + yearMonthArr[1] + "月薪资发放已封帐，不允许调整");
            return "success";
        }

        String empName = emp.getEmpName();

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(this.curEffDate + "-" + "1");
        ICompaplanBo compaplanBo = (ICompaplanBo) getBean("compaplanBo");
        Empsalaryadj ajs = compaplanBo.loadCompaplanInfoByEmpNo(empId, date);
        if (ajs != null) {
            String[] dateArr = DateUtil.formatDateToS(ajs.getEsaCurEffDate(), "yyyy-MM").split("-");
            addErrorInfo("员工" + empName + dateArr[0] + "年" + dateArr[1]
                    + "月已经有一条生效的调薪计划，操作失败！");
            return "success";
        }

        Empsalaryconfig config = emp.getConfig();
        config.decryEmpSalaryConf(config);
        this.esa.setEsaCurEffDate(date);

        this.esa = getCompaByConfig(config, this.esc, this.esa);

        BigDecimal[] incrRate = calIncreace(config, this.esc);
        this.esa.setEsaCurIncrRate(incrRate[0]);
        this.esa.setEsaCurIncrRate1(incrRate[1]);

        Empsalaryadj compaplan = this.esa;
        Empsalaryadj compaplanFromDB = compaplanBo.loadCompaplanInfoByEmpNo(empId);
        if (compaplanFromDB == null) {
            compaplan.setEsaStatus(Integer.valueOf(11));
            if (compaplanBo.insertCompaplan(compaplan, currentUserNo)) {
                addActionMessage("添加员工" + empName + "的调薪计划成功！");
                return "success";
            }
            addErrorInfo("添加员工" + empName + "的调薪计划出错！");
            return "error";
        }
        try {
            compaplanFromDB.decryEmpSalaryAdj(compaplanFromDB);
            compaplanBo.updateCompaplan(compaplan, compaplanFromDB, currentUserNo);
            addActionMessage("修改员工" + empName + "的调薪计划成功！");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            addErrorInfo("修改员工" + empName + "的调薪计划出错！");
        }
        return "error";
    }

    private BigDecimal[] calIncreace(Empsalaryconfig config, Empsalaryconfig newConfig)
            throws Exception {
        BigDecimal[] rs = { new BigDecimal(0), new BigDecimal(0) };
        BigDecimal bs = new BigDecimal(0);
        BigDecimal newbs = new BigDecimal(0);

        BigDecimal beforTax = new BigDecimal(0);
        BigDecimal beforTaxNew = new BigDecimal(0);

        IEmpSalaryAcctitemsBo acctItemBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");

        List acctList = acctItemBo.getItemsByAcctversion(config.getEscEsavId().getId());

        Empsalaryacctitems item = new Empsalaryacctitems();
        Class ownerClass = config.getClass();
        for (int i = 0; i < acctList.size(); ++i) {
            item = (Empsalaryacctitems) acctList.get(i);
            Method escMethod = ownerClass.getMethod("getEscColumn" + item.getEsaiDataSeq(),
                                                    new Class[0]);

            if (item.getEsaiEsdd().getEsddDataType().intValue() == 1)
                bs = (BigDecimal) escMethod.invoke(config, new Object[0]);
            else if (item.getEsaiEsdd().getEsddDataType().intValue() == 8) {
                beforTax = (BigDecimal) escMethod.invoke(config, new Object[0]);
            }
        }

        acctList = acctItemBo.getItemsByAcctversion(newConfig.getEscEsavId().getId());
        ownerClass = newConfig.getClass();
        for (int i = 0; i < acctList.size(); ++i) {
            item = (Empsalaryacctitems) acctList.get(i);
            Method escMethod = ownerClass.getMethod("getEscColumn" + item.getEsaiDataSeq(),
                                                    new Class[0]);

            if (item.getEsaiEsdd().getEsddDataType().intValue() == 1)
                newbs = (BigDecimal) escMethod.invoke(newConfig, new Object[0]);
            else if (item.getEsaiEsdd().getEsddDataType().intValue() == 8) {
                beforTaxNew = (BigDecimal) escMethod.invoke(newConfig, new Object[0]);
            }
        }

        DecimalFormat df = new DecimalFormat("#.00");
        if (bs.intValue() != 0) {
            BigDecimal result = newbs.subtract(bs).divide(bs, 4, 4).multiply(new BigDecimal(100));

            rs[0] = new BigDecimal(df.format(result));
        }
        if (beforTax.intValue() != 0) {
            BigDecimal result = beforTaxNew.subtract(beforTax).divide(beforTax, 4, 4)
                    .multiply(new BigDecimal(100));

            rs[1] = new BigDecimal(df.format(result));
        }
        return rs;
    }

    public List<Empsalaryacctitems> getCompaItemsById(String id, String curOrpro) {
        String hasAuth = DWRUtil.checkAuth("searchBatchCompaplan", "execute");
        if ("error".equals(hasAuth)) {
            return null;
        }
        ICompaplanBo compaplanBo = (ICompaplanBo) getBean("compaplanBo");
        return compaplanBo.getCompaItemsById(id, curOrpro);
    }

    public Empsalaryadj getEsa() {
        return this.esa;
    }

    public void setEsa(Empsalaryadj esa) {
        this.esa = esa;
    }

    public Empsalaryconfig getEsc() {
        return this.esc;
    }

    public void setEsc(Empsalaryconfig esc) {
        this.esc = esc;
    }

    public String getCompaplanIds() {
        return this.compaplanIds;
    }

    public void setCompaplanIds(String compaplanIds) {
        this.compaplanIds = compaplanIds;
    }

    public String getCurEffDate() {
        return this.curEffDate;
    }

    public void setCurEffDate(String curEffDate) {
        this.curEffDate = curEffDate;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.DWRforSearchBatchCompaplan JD-Core Version: 0.5.4
 */