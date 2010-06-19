package com.hr.compensation.action;

import com.hr.base.FileOperate;
import com.hr.base.io.Exportinfo;
import com.hr.compensation.bo.ICompaplanBo;
import com.hr.compensation.bo.IEmpBenefitPlanBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.IEmpsalarydatadefBo;
import com.hr.compensation.bo.IEmpsalaryperiodBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.compensation.domain.Empsalaryperiod;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.exportfile.ExportPayslip;
import com.hr.exportfile.ExportSalary;
import com.hr.exportfile.ExportSalaryToBank;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.output.FieldOperate;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.ObjectUtils;

public class SearchSalaryPaid extends CompAction {
    private static final long serialVersionUID = -5413735265L;
    private Empsalarypay salaryPaid = new Empsalarypay();

    private Pager page = new Pager();
    private List<Employee> salaryPaidList;
    private List<SalaryPaidConverge> salaryPaidConvergeList;
    private List<String> years;
    private String year;
    private String month;
    private String yearmonth;
    private String emp;
    private List<Jobgrade> jobgradeList;
    private List<Empsalaryacctversion> acctVersionList;
    private Integer espdStatus;
    private Employee employee = new Employee();
    private Integer status;
    private Integer compaplanCounts;
    private String searchOrExport;
    private FileInputStream docStream = null;
    private String contentDisposition = null;

    private String serverFileName = null;
    private String searchEmpSalType;
    private List<Employee> empList;
    DecimalFormat decimalFormat = new DecimalFormat("00");
    private String outmatchModelId;
    private String outputIoName = "OEmpSalaryPay";
    private Integer beneAddtionalCount = Integer.valueOf(0);

    public SearchSalaryPaid() {
        this.year = DateUtil.formatTodayToS("yyyy");
        this.month = DateUtil.formatTodayToS("MM");
    }

    public String execute() throws Exception {
        String yearmonth = this.year + this.month;

        getDrillDownList();
        getHiddenList();

        IEmpsalaryperiodBo salaryperiod = (IEmpsalaryperiodBo) getBean("empsalaryperiodBO");
        this.espdStatus = salaryperiod.getEspdStatus(yearmonth);
        if (this.espdStatus.intValue() == 3) {
            this.page.setSplit(false);
            setSalaryPaidList(new ArrayList());
            return "success";
        }

        DetachedCriteria dcPay = searchPay_DC();
        addCriteriaPay(dcPay, yearmonth);

        DetachedCriteria dcEmp = searchEmpWithConfig_DC();
        addCriteriaEmp(dcEmp, yearmonth);

        List<Empsalarypay> payList = new ArrayList();
        List empList = new ArrayList();
        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        if (("exportBank".equals(this.searchOrExport))
                || ("exportSalary".equals(this.searchOrExport))
                || ("exportPayslip".equals(this.searchOrExport))
                || ("exportPayroll".equals(this.searchOrExport))) {
            payList = salaryPaidBo.findPayPaid(dcPay, null);

            if ((payList == null) || (payList.size() == 0)) {
                addActionError("无薪资发放数据可以导出！");
                return "success";
            }
            return exportData(payList);
        }

        if ("noPaid".equals(this.searchEmpSalType)) {
            empList = salaryPaidBo.findPayNoPaid(dcPay, dcEmp, this.page);
        } else {
            payList = salaryPaidBo.findPayPaid(dcPay, this.page);
            if ((payList != null) && (payList.size() > 0)) {
                for (Empsalarypay pay : payList) {
                    Employee tempEmp = pay.getEspEmpno();
                    tempEmp.setEmpsalarypay(pay);
                    empList.add(tempEmp);
                }
            }
        }
        setSalaryPaidList(empList);
        return "success";
    }

    public String initPaid() throws Exception {
        String msgInitSucc = "重新初始化{0}年{1}月的薪资数据成功！";

        String yearmonth = this.year + this.month;

        ICompaplanBo compaplanBo = (ICompaplanBo) SpringBeanFactory.getBean("compaplanBo");
        if (compaplanBo.searchCompaplanSubmitStatus(yearmonth).intValue() > 0) {
            compaplanBo.updateCompaplanForSlary(yearmonth, getCurrentEmpNo());
        }

        DetachedCriteria dcConf = searchConfigWithEmp_DC();
        addCriteriaInit(dcConf, yearmonth);

        ISalaryconfBo salaryConfBo = (ISalaryconfBo) getBean("salaryconfBo");
        List<Empsalaryconfig> configList = salaryConfBo.findConfig(dcConf, null);

        List<Empsalarypay> payList = new ArrayList();
        for (Empsalaryconfig config : configList) {
            config.decryEmpSalaryConf(config);
            Empsalarypay pay = copyConfigToPay(config);
            pay.setEspYearmonth(yearmonth);
            payList.add(pay);
        }
        Empsalarypay[] payArray = (Empsalarypay[]) payList
                .toArray(new Empsalarypay[payList.size()]);

        setPayAdd(yearmonth, payArray);

        getDrillDownList();

        interpretPay(yearmonth, getCurrentEmpNo(), true, payArray);
        if (ObjectUtils.isEmpty(payArray)) {
            addErrorInfo(this.msgNoData, new Object[] { "null", yearmonth + "薪资发放" });
            return "success";
        }

        payList = Arrays.asList(payArray);
        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        salaryPaidBo.batchInitPaidSave(yearmonth, payList);
        this.espdStatus = Integer.valueOf(0);

        DetachedCriteria dcPay = searchPay_DC();
        addCriteriaPay(dcPay, yearmonth);

        DetachedCriteria dcEmp = searchEmpWithConfig_DC();
        addCriteriaEmp(dcEmp, yearmonth);

        if ("noPaid".equals(this.searchEmpSalType)) {
            List paidEmpList = salaryPaidBo.findPayNoPaid(dcPay, dcEmp, this.page);
            setSalaryPaidList(paidEmpList);
        } else {
            payList = salaryPaidBo.findPayPaid(dcPay, this.page);
            if ((payList != null) && (payList.size() > 0)) {
                List empList = new ArrayList();
                for (Empsalarypay pay : payList) {
                    Employee tempEmp = pay.getEspEmpno();
                    tempEmp.setEmpsalarypay(pay);
                    empList.add(tempEmp);
                }
                setSalaryPaidList(empList);
            }

        }

        getHiddenList();

        addSuccessInfo(msgInitSucc, new Object[] { this.year, this.month });
        return "success";
    }

    private DetachedCriteria searchPay_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empsalarypay.class);

        dc.createAlias(Empsalarypay.PROP_ESP_DEPT, "dept", 1);
        dc.createAlias(Empsalarypay.PROP_ESP_EMPNO, "emp", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "empDeptNo", 1);
        dc.createAlias(Empsalarypay.PROP_ESP_PB_NO, "espPbNo", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_PB_NO, "empPbNo", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_LOCATION_NO, "empLocationNo", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_TYPE, "empType", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_BENEFIT_TYPE, "benefitType", 1);
        dc.createAlias(Empsalarypay.PROP_ESP_EMPCONFIG, "config", 1);
        dc.createAlias("config." + Empsalaryconfig.PROP_ESC_JOBGRADE, "jobgrade", 1);
        dc.createAlias(Empsalarypay.PROP_ESP_ESAV_ID, "espEsavId", 1);
        dc.createAlias("espEsavId." + Empsalaryacctversion.PROP_ESAV_ESAC, "esavEsac", 1);
        return dc;
    }

    private void addCriteriaPay(DetachedCriteria dcPay, String yearmonth) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dcPay, "emp", 2);
        }

        BaseCrit.addStatusEmpDC(dcPay, "emp", yearmonth, new String[] { this.searchEmpSalType });

        dcPay.add(Restrictions.eq(Empsalarypay.PROP_ESP_YEARMONTH, yearmonth));
        BaseCrit.addEmpDC(dcPay, "emp", this.employee.getEmpName());
        BaseCrit.addDeptDC(dcPay, "emp." + Employee.PROP_EMP_DEPT_NO, "emp."
                + Employee.PROP_EMP_PB_NO, Integer.valueOf(1), this.employee.getEmpDeptNo());
        BaseCrit.addDC(dcPay, "emp." + Employee.PROP_EMP_TYPE, Emptype.PROP_ID,
                       new Object[] { this.employee.getEmpType() });
        BaseCrit.addDC(dcPay, "emp." + Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                       new Object[] { this.employee.getEmpLocationNo() });

        if (this.employee.getConfig() != null) {
            BaseCrit.addDC(dcPay, "config.escJobgrade", Jobgrade.PROP_ID,
                           new Object[] { this.employee.getConfig().getEscJobgrade() });
            BaseCrit.addDC(dcPay, "espEsavId", Empsalaryacctversion.PROP_ID,
                           new Object[] { this.employee.getConfig().getEscEsavId() });
        }
    }

    private DetachedCriteria searchEmpWithConfig_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class, "emp");

        dc.createAlias(Employee.PROP_CONFIG, "config", 1);
        dc.createAlias("config." + Empsalaryconfig.PROP_ESC_ESAV_ID, "escEsavId", 1);
        dc.createAlias("config." + Empsalaryconfig.PROP_ESC_JOBGRADE, "escJobgrade", 1);
        return dc;
    }

    private void addCriteriaEmp(DetachedCriteria dcEmp, String yearmonth) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dcEmp, "", 2);
        }

        BaseCrit.addStatusEmpDC(dcEmp, null, yearmonth, new String[] { "" });
        BaseCrit.addEmpDC(dcEmp, null, this.employee.getEmpName());
        BaseCrit.addDeptDC(dcEmp, Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_PB_NO, Integer
                .valueOf(1), this.employee.getEmpDeptNo());
        BaseCrit.addDC(dcEmp, Employee.PROP_EMP_TYPE, Emptype.PROP_ID, new Object[] { this.employee
                .getEmpType() });
        BaseCrit.addDC(dcEmp, Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                       new Object[] { this.employee.getEmpLocationNo() });

        if (this.employee.getConfig() != null) {
            BaseCrit.addDC(dcEmp, "config.escJobgrade", Jobgrade.PROP_ID,
                           new Object[] { this.employee.getConfig().getEscJobgrade() });
            BaseCrit.addDC(dcEmp, "config.escEsavId", Empsalaryacctversion.PROP_ID,
                           new Object[] { this.employee.getConfig().getEscEsavId() });
        }
    }

    private DetachedCriteria searchConfigWithEmp_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empsalaryconfig.class);

        dc.createAlias(Empsalaryconfig.PROP_EMPLOYEE, "emp", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_BENEFIT_TYPE, "benefitType", 1);
        dc.createAlias(Empsalaryconfig.PROP_ESC_JOBGRADE, "jobgrade", 1);
        dc.createAlias(Empsalaryconfig.PROP_ESC_ESAV_ID, "espEsavId", 1);
        return dc;
    }

    private void addCriteriaInit(DetachedCriteria dcConf, String yearmonth) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dcConf, "emp", 2);
        }
        BaseCrit.addStatusEmpDC(dcConf, "emp", yearmonth, new String[] { "" });
    }

    public String exportData(List<Empsalarypay> result) {
        if ((this.searchOrExport.equals("exportPayroll")) && (this.espdStatus.intValue() == 2)) {
            try {
                IEmpsalarydatadefBo empsalarydatadefBo = (IEmpsalarydatadefBo) SpringBeanFactory
                        .getBean("empsalarydatadefBo");

                List dataDefList = empsalarydatadefBo.findOutput(Integer.valueOf(1));
                ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
                List exportResult = salaryPaidBo.processForOutput(result, dataDefList);

                List fieldAddList = getFieldAddList(dataDefList);
                return downloadToExcel(exportResult, this.outputIoName, this.outmatchModelId,
                                       "empsalarypay", fieldAddList);
            } catch (Exception e) {
                addActionError("数据导出失败！");
                e.printStackTrace();
                return "success";
            }

        }

        String downFileName = null;

        String filePath = FileOperate.getFileHomePath();
        String xslFileName = "export-" + FileOperate.buildFileName()
                + PropertiesFileConfigManager.getInstance().getProperty("sys.saveDir.type").trim();

        filePath = filePath + xslFileName;
        try {
            if ((this.searchOrExport.equals("exportBank")) && (this.espdStatus.intValue() == 2)) {
                ExportSalaryToBank exportToBank = new ExportSalaryToBank(result, "导出");

                exportToBank.export(filePath);

                downFileName = this.year + "-" + this.month + "salarytoBank.xls";
            } else if (this.searchOrExport.equals("exportSalary")) {
                Exportinfo exportSalary = new Exportinfo();
                IEmpSalaryAcctitemsBo esaitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                        .getBean("empsalaryacctitemsBo");

                List[] titles = exportSalary.getSalarySheetAndTitle(result, esaitemsBo);
                List[] results = exportSalary.getSalaryResult(result, titles);

                ExportSalary export = new ExportSalary(results, titles, false);
                export.export(filePath);

                downFileName = this.year + "-" + this.month + "salary.xls";
            } else if ((this.searchOrExport.equals("exportPayslip"))
                    && (this.espdStatus.intValue() == 2)) {
                Exportinfo exportSalary = new Exportinfo();
                IEmpSalaryAcctitemsBo esaitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                        .getBean("empsalaryacctitemsBo");

                List[] titles = exportSalary.getPayslipTitle(result, esaitemsBo);
                List[] results = exportSalary.getPayslipResult(result, titles);

                ExportPayslip export = new ExportPayslip(results, titles);
                export.export(filePath);

                downFileName = this.year + "-" + this.month + "payslip.xls";
            } else {
                addActionError("数据导出失败！");
                return "success";
            }

            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);

            setDocStream(fileInputStream);

            setContentDisposition("filename=\"" + downFileName + "\"");
            setServerFileName(filePath);
            clearErrorsAndMessages();
        } catch (Exception e) {
            addActionError("数据导出失败！");
            e.printStackTrace();
            return "success";
        }

        return "download";
    }

    private List<FieldOperate> getFieldAddList(List<Empsalarydatadef> datadefList) {
        List resultList = new ArrayList();
        for (int i = 0; i < datadefList.size(); ++i) {
            Empsalarydatadef datadef = (Empsalarydatadef) datadefList.get(i);
            FieldOperate operate = new FieldOperate();
            operate.setColWidth(15);
            operate.setDataType("decimal");
            operate.setFieldName("outPutList." + datadef.getEsddId());
            operate.setShowName(datadef.getEsddName());
            resultList.add(operate);
        }
        return resultList;
    }

    public String confirmSubmit() throws Exception {
        String yearmonth = this.year + this.month;
        IEmpsalaryperiodBo salaryperiod = (IEmpsalaryperiodBo) getBean("empsalaryperiodBO");
        salaryperiod.updateConfirmSubmit(yearmonth, Integer.valueOf(1));
        addSuccessInfo("提交成功！");
        return "success";
    }

    public String viewAllSalarypaid() throws Exception {
        String yearmonth = this.year + this.month;

        getDrillDownList();

        getHiddenList();

        IEmpsalaryperiodBo salaryperiod = (IEmpsalaryperiodBo) getBean("empsalaryperiodBO");
        Empsalaryperiod espd = salaryperiod.loadEspdStatus(yearmonth);
        if (espd != null)
            this.espdStatus = espd.getEspdStatus();

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        setSalaryPaidConvergeList(salaryPaidBo.findNeedApprovePaids(this.departs, yearmonth, espd));

        return "success";
    }

    private void getDrillDownList() {
        this.years = getDrillDown("EmpSalaryPeriod", new String[0]);
        this.departs = getDrillDown("Department", new String[] { "1" });
        this.locationList = getDrillDown("Location", new String[] { "1" });
        this.empTypeList = getDrillDown("EmpType", new String[] { "1" });
        this.jobgradeList = getDrillDown("Jobgrade", new String[0]);
        this.acctVersionList = getDrillDown("EmpSalaryAcct", new String[0]);
    }

    private void getHiddenList() {
        String yearmonth = this.year + this.month;
        ICompaplanBo compaplanBo = (ICompaplanBo) SpringBeanFactory.getBean("compaplanBo");
        setCompaplanCounts(compaplanBo.searchCompaplanSubmitStatus(yearmonth));

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) SpringBeanFactory.getBean("empbenefitplanBo");
        setBeneAddtionalCount(ebpBo.getBeneAddCount(yearmonth));

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        this.empList = salaryPaidBo.searchEmployeeHasSalaryPay(yearmonth);
    }

    public String approve() throws Exception {
        String msgIllegal = "非法参数！当月薪资发放数据必须为已提交状态！";
        String msgApprove = "薪资发放审核通过。";
        String msgReject = "薪资发放已退回重做。";
        String yearmonth = this.year + this.month;
        IEmpsalaryperiodBo salaryperiod = (IEmpsalaryperiodBo) getBean("empsalaryperiodBO");
        if (this.status.intValue() == 2) {
            salaryperiod.updateConfirmSubmit(yearmonth, Integer.valueOf(2));
            this.espdStatus = Integer.valueOf(2);
            addSuccessInfo(msgApprove);
            return "success";
        }
        if (this.status.intValue() == 0) {
            salaryperiod.updateConfirmSubmit(yearmonth, Integer.valueOf(0));
            this.espdStatus = Integer.valueOf(0);
            addSuccessInfo(msgReject);
            return "success";
        }
        addErrorInfo(msgIllegal);
        return "error";
    }

    public List<Empsalarypay> convertResult(List<Employee> result) {
        List convertResult = new ArrayList();
        for (Employee emp : result) {
            Empsalarypay esp = emp.getEmpsalarypay();
            if (esp == null) {
                esp = new Empsalarypay();
            }
            esp.setEspEmpno(emp);
            convertResult.add(esp);
        }
        return convertResult;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public Empsalarypay getSalaryPaid() {
        return this.salaryPaid;
    }

    public void setSalaryPaid(Empsalarypay salaryPaid) {
        this.salaryPaid = salaryPaid;
    }

    public List<Employee> getSalaryPaidList() {
        return this.salaryPaidList;
    }

    public void setSalaryPaidList(List<Employee> salaryPaidList) {
        this.salaryPaidList = salaryPaidList;
    }

    public List<SalaryPaidConverge> getSalaryPaidConvergeList() {
        return this.salaryPaidConvergeList;
    }

    public void setSalaryPaidConvergeList(List<SalaryPaidConverge> salaryPaidConvergeList) {
        this.salaryPaidConvergeList = salaryPaidConvergeList;
    }

    public String getYearmonth() {
        return this.yearmonth;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth;
    }

    public List<Department> getDeparts() {
        return this.departs;
    }

    public void setDeparts(List<Department> departs) {
        this.departs = departs;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEmp() {
        return this.emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getEspdStatus() {
        return this.espdStatus;
    }

    public void setEspdStatus(Integer espdStatus) {
        this.espdStatus = espdStatus;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getYears() {
        return this.years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public List<Employee> getEmpList() {
        return this.empList;
    }

    public void setEmpList(List<Employee> empList) {
        this.empList = empList;
    }

    public List<Emptype> getEmpTypeList() {
        return this.empTypeList;
    }

    public void setEmpTypeList(List<Emptype> empTypeList) {
        this.empTypeList = empTypeList;
    }

    public String getContentDisposition() {
        return this.contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public FileInputStream getDocStream() {
        return this.docStream;
    }

    public void setDocStream(FileInputStream docStream) {
        this.docStream = docStream;
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }

    public List<Jobgrade> getJobgradeList() {
        return this.jobgradeList;
    }

    public void setJobgradeList(List<Jobgrade> jobgradeList) {
        this.jobgradeList = jobgradeList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public void setAcctVersionList(List<Empsalaryacctversion> acctVersionList) {
        this.acctVersionList = acctVersionList;
    }

    public String getServerFileName() {
        return this.serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public Integer getCompaplanCounts() {
        return this.compaplanCounts;
    }

    public void setCompaplanCounts(Integer compaplanCounts) {
        this.compaplanCounts = compaplanCounts;
    }

    public String getSearchEmpSalType() {
        return this.searchEmpSalType;
    }

    public void setSearchEmpSalType(String searchEmpSalType) {
        this.searchEmpSalType = searchEmpSalType;
    }

    public List<Location> getLocationList() {
        return this.locationList;
    }

    public List<Empsalaryacctversion> getAcctVersionList() {
        return this.acctVersionList;
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }

    public String getOutputIoName() {
        return this.outputIoName;
    }

    public void setOutputIoName(String outputIoName) {
        this.outputIoName = outputIoName;
    }

    public Integer getBeneAddtionalCount() {
        return this.beneAddtionalCount;
    }

    public void setBeneAddtionalCount(Integer beneAddtionalCount) {
        this.beneAddtionalCount = beneAddtionalCount;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.SearchSalaryPaid JD-Core Version: 0.5.4
 */