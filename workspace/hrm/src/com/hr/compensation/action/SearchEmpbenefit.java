package com.hr.compensation.action;

import com.hr.compensation.bo.IEmpBenefitBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empbenefit;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.bo.IBenefitTypeBO;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.configuration.domain.StatusconfPK;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.report.model.api.util.StringUtil;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

public class SearchEmpbenefit extends CompAction {
    private static final long serialVersionUID = 1L;
    private Employee employee;
    private String empNameOrDist;
    private List<Employee> empbenefitList;
    private Pager page = new Pager();
    private String employeeId;
    private Employee emp = new Employee();
    private String[][] benefitValue;
    private String ebfId;
    List<Empbenefit> BenefitList;
    private List<Department> deptList;
    private List<Location> locationList;
    private List<Statusconf> empStatus;
    private List<BenefitType> ebfTypeList;
    private List<Emptype> empTypeList;
    private String inputstartBtfYear;
    private String inputstartBtfMonth;
    private String searchOrExport = null;
    private FileInputStream docStream = null;
    private String contentDisposition = null;

    private String serverFileName = null;
    private String outmatchModelId;
    private List<Empsalarypay> benefitPayvalueList;
    private String year;
    private String month;
    private String amountCategory;
    private String amountFrom;
    private String amountTo;
    private Empbenefit benefit;
    private String empBenefitTypeId;
    private String startBtfYear;
    private String startBtfMonth;
    private List<String> yearList;
    private List<String> monthList;
    public static final String outputIoName = "OEmpSalaryBenefit";
    public static final String inputIoName = "IEmpSalaryBenefitUpd";

    public SearchEmpbenefit() {
        this.employee = new Employee();
        this.employee.setEmpStatus(Integer.valueOf(1));
    }

    public String execute() throws Exception {
        getDrillDownList();

        this.empbenefitList = searchEmpBenefit();

        ISalaryconfBo salaryconfBo = (ISalaryconfBo) SpringBeanFactory.getBean("salaryconfBo");
        salaryconfBo.calcSalaryConfByTypes(this.empbenefitList);

        if ("export".equals(this.searchOrExport)) {
            return downloadToExcel(this.empbenefitList, "OEmpSalaryBenefit", this.outmatchModelId,
                                   "");
        }

        return "success";
    }

    private List<Employee> searchEmpBenefit() {
        DetachedCriteria dcEmpBenefit = searchEmpBenefit_DC();
        addCriteria(dcEmpBenefit);

        IEmpBenefitBo empbenefitBo = (IEmpBenefitBo) getBean("empbenefitBo");
        return empbenefitBo.findEmpBenefit(dcEmpBenefit, this.page, this.searchOrExport);
    }

    private DetachedCriteria searchEmpBenefit_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        dc.setFetchMode(Employee.PROP_EMP_DEPT_NO, FetchMode.JOIN);
        dc.setFetchMode(Employee.PROP_EMP_LOCATION_NO, FetchMode.JOIN);
        dc.setFetchMode(Employee.PROP_EMP_TYPE, FetchMode.JOIN);
        dc.setFetchMode(Employee.PROP_EMP_BENEFIT_TYPE, FetchMode.JOIN);

        dc.createAlias(Employee.PROP_CONFIG, "config", 1);
        dc.createAlias(Employee.PROP_EMP_PB_NO, "empPbNo", 1);
        dc.createAlias(Employee.PROP_EMP_BENEFIT, "benefit", 1);
        return dc;
    }

    private void addCriteria(DetachedCriteria dc) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "", 2);
        }

        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "", 2);
        }

        addOrders(dc, this.page, new String[] {
                "benefit." + Empbenefit.PROP_EBF_EBFSTARTMONTH + "-down",
                Employee.PROP_EMP_DISTINCT_NO + "-up" });

        BaseCrit.addEmpDC(dc, null, this.empNameOrDist);

        BaseCrit.addDC(dc, Employee.PROP_EMP_DISTINCT_NO, "like", new String[] { this.employee
                .getEmpDistinctNo() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_NAME, "like", new String[] { this.employee
                .getEmpName() });
        BaseCrit.addDeptDC(dc, Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_PB_NO, Integer
                .valueOf(1), this.employee.getEmpDeptNo());
        BaseCrit.addDC(dc, Employee.PROP_EMP_STATUS, "eq", new Integer[] { this.employee
                .getEmpStatus() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_TYPE, Emptype.PROP_ID, new Object[] { this.employee
                .getEmpType() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                       new Object[] { this.employee.getEmpLocationNo() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_BENEFIT_TYPE, "id", new Object[] { this.employee
                .getEmpBenefitType() });

        if ((this.employee.getEmpIdentificationNo() != null)
                && (this.employee.getEmpIdentificationNo().length() > 0)) {
            BaseCrit.addDC(dc, Employee.PROP_EMP_IDENTIFICATION_TYPE, "eq",
                           new Integer[] { new Integer(0) });
            BaseCrit.addDC(dc, Employee.PROP_EMP_IDENTIFICATION_NO, "like",
                           new String[] { this.employee.getEmpIdentificationNo() });
        }

        if (this.employee.getBenefit() != null) {
            BaseCrit.addDC(dc, "benefit." + Empbenefit.PROP_EBF_EBFSTARTMONTH, "like",
                           new String[] { this.employee.getBenefit().getEbfStartMonth() });
            BaseCrit.addDC(dc, "benefit." + Empbenefit.PROP_EBF_EBFPENSIONNO, "like",
                           new String[] { this.employee.getBenefit().getEbfPensionNo() });
            BaseCrit.addDC(dc, "benefit." + Empbenefit.PROP_EBF_EBFHOUSINGNO, "like",
                           new String[] { this.employee.getBenefit().getEbfHousingNo() });
            BaseCrit.addDC(dc, "benefit." + Empbenefit.PROP_EBF_EBFMEDICALNO, "like",
                           new String[] { this.employee.getBenefit().getEbfMedicalNo() });
        }

        if (!StringUtil.isEmpty(this.amountCategory))
            if ("pension".equals(this.amountCategory))
                BaseCrit.addDC(dc, "benefit." + Empbenefit.PROP_EBF_EBFPENSIONAMOUNT, "between",
                               new BigDecimal[] { new BigDecimal(this.amountFrom),
                                       new BigDecimal(this.amountTo) });
            else if ("house".equals(this.amountCategory))
                BaseCrit.addDC(dc, "benefit." + Empbenefit.PROP_EBF_EBFINSURANCEAMOUNT, "between",
                               new BigDecimal[] { new BigDecimal(this.amountFrom),
                                       new BigDecimal(this.amountTo) });
            else if ("insurance".equals(this.amountCategory))
                BaseCrit.addDC(dc, "benefit." + Empbenefit.PROP_EBF_EBFMEDICALNO, "between",
                               new BigDecimal[] { new BigDecimal(this.amountFrom),
                                       new BigDecimal(this.amountTo) });
    }

    private void getDrillDownList() {
        this.deptList = getDrillDown("Department", new String[0]);
        this.locationList = getDrillDown("Location", new String[0]);
        this.empTypeList = getDrillDown("EmpType", new String[0]);
        this.ebfTypeList = getDrillDown("BenefitType", new String[0]);
        this.empStatus = getDrillDown("EmpStatus", new String[0]);

        this.year = DateUtil.formatTodayToS("yyyy");
        this.month = DateUtil.formatTodayToS("MM");
    }

    public String addBenefitInit() throws Exception {
        if (StringUtils.isEmpty(this.employeeId)) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "input";
        }

        String[] fetch = { Employee.PROP_EMP_BENEFIT_TYPE, Employee.PROP_EMP_BENEFIT,
                Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_LOCATION_NO, Employee.PROP_EMP_PB_NO,
                Employee.PROP_EMP_TYPE };

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        this.emp = empBo.loadEmp(this.employeeId, fetch);
        if (this.emp == null) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "input";
        }

        if (this.emp.getBenefit() != null) {
            addActionError("该员工已经存在社保记录！");
            return "input";
        }

        this.startBtfYear = DateUtil.formatTodayToS("yyyy");
        this.startBtfMonth = DateUtil.formatTodayToS("MM");

        this.ebfTypeList = getDrillDown("BenefitType", new String[0]);
        this.yearList = getDrillDown("EmpSalaryPeriod", new String[0]);

        return "success";
    }

    public String addNewBenefit() throws Exception {
        if ((this.emp.getBenefit().getEbfHousingAmount() == null)
                && (this.emp.getBenefit().getEbfInsuranceAmount() == null)
                && (this.emp.getBenefit().getEbfPensionAmount() == null)) {
            addErrorInfo(this.msgNoBaseErr, new Object[] { "nobasep" });
            return "input";
        }

        Empsalaryconfig config = loadConfig(this.employeeId, null);
        if (config == null) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "input";
        }

        if (config.getEmployee().getBenefit() != null) {
            addErrorInfo(this.msghasBenefit, new Object[] { "hasbenefit" });
            return "input";
        }

        Employee newEmp = config.getEmployee();
        newEmp.setBenefit(this.emp.getBenefit());
        IBenefitTypeBO bfTypeBo = (IBenefitTypeBO) SpringBeanFactory.getBean("benefitTypeBo");
        newEmp.setEmpBenefitType(bfTypeBo.searchById(this.emp.getEmpBenefitType().getId()));
        newEmp.setEmpLastChangeBy(getCurrentEmp());

        if (config.getId() != null) {
            IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");
            esaiBo.setAcctItemsConfig(new Empsalaryconfig[] { config });

            ISalaryconfBo confBo = (ISalaryconfBo) getBean("salaryconfBo");
            confBo.interpretConfig(new Empsalaryconfig[] { config });
        } else {
            newEmp.setConfig(null);
        }

        IEmpBenefitBo empbenefitBo = (IEmpBenefitBo) getBean("empbenefitBo");
        empbenefitBo.addBenefit(newEmp);
        addSuccessInfo(this.msgCreateBeneSucc, new Object[] { "SUCC", newEmp.getEmpName() });
        return "success";
    }

    public String updateBenefitInit() throws Exception {
        if (StringUtils.isEmpty(this.employeeId)) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "input";
        }

        String[] fetch = { Employee.PROP_EMP_BENEFIT_TYPE, Employee.PROP_EMP_BENEFIT,
                Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_LOCATION_NO, Employee.PROP_EMP_PB_NO,
                Employee.PROP_EMP_TYPE };

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        this.emp = empBo.loadEmp(this.employeeId, fetch);
        if (this.emp == null) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "input";
        }

        if (this.emp.getBenefit() == null) {
            addActionError("该员工不存在社保记录！");
            return "input";
        }

        ISalaryconfBo salaryBo = (ISalaryconfBo) getBean("salaryconfBo");
        this.benefitValue = salaryBo.findSalaryBenefitValue(this.employeeId);

        IEmpBenefitBo empbenefitBo = (IEmpBenefitBo) getBean("empbenefitBo");
        this.BenefitList = empbenefitBo.searchByEmpNo(this.employeeId);

        this.benefitPayvalueList = empbenefitBo.searchbenefitpayvalueByEmpId(this.emp.getId());

        String YearOrMonth = this.emp.getBenefit().getEbfStartMonth();
        this.startBtfYear = YearOrMonth.substring(0, 4);
        this.startBtfMonth = YearOrMonth.substring(4, 6);

        this.ebfTypeList = getDrillDown("BenefitType", new String[0]);
        this.yearList = getDrillDown("EmpSalaryPeriod", new String[0]);

        return "success";
    }

    public String updateBenefit() throws Exception {
        if ((this.emp.getBenefit().getEbfHousingAmount() == null)
                && (this.emp.getBenefit().getEbfInsuranceAmount() == null)
                && (this.emp.getBenefit().getEbfPensionAmount() == null)) {
            addErrorInfo(this.msgNoBaseErr, new Object[] { "nobasep" });
            return "input";
        }

        Empsalaryconfig config = loadConfig(this.employeeId, null);
        if (config == null) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "input";
        }

        if (config.getEmployee().getBenefit() == null) {
            addErrorInfo(this.msgNoBenefit, new Object[] { "nobenefit" });
            return "input";
        }

        IEmpBenefitBo empbenefitBo = (IEmpBenefitBo) getBean("empbenefitBo");
        Empbenefit oldBenefit = empbenefitBo.loadPrevBenefit(this.employeeId);

        if ((oldBenefit != null)
                && (this.emp.getBenefit().getEbfStartMonth().compareTo(
                                                                       oldBenefit
                                                                               .getEbfStartMonth()) < 0)) {
            addErrorInfo(this.msgStartYMTooEarly, new Object[] { "tooearly" });
            return "input";
        }

        Employee newEmp = config.getEmployee();
        this.emp.getBenefit().setEbfId(newEmp.getBenefit().getEbfId());
        newEmp.setBenefit(this.emp.getBenefit());
        IBenefitTypeBO bfTypeBo = (IBenefitTypeBO) SpringBeanFactory.getBean("benefitTypeBo");
        newEmp.setEmpBenefitType(bfTypeBo.searchById(this.emp.getEmpBenefitType().getId()));
        newEmp.setEmpLastChangeBy(getCurrentEmp());

        if (config.getId() != null) {
            IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");
            esaiBo.setAcctItemsConfig(new Empsalaryconfig[] { config });

            ISalaryconfBo confBo = (ISalaryconfBo) getBean("salaryconfBo");
            confBo.interpretConfig(new Empsalaryconfig[] { config });
        } else {
            newEmp.setConfig(null);
        }

        empbenefitBo.updateBenefit(oldBenefit, newEmp);
        addSuccessInfo(this.msgUpdBeneSucc, new Object[] { "SUCC", newEmp.getEmpName() });
        return "success";
    }

    public String delEmpbenefit() throws Exception {
        if ((StringUtils.isEmpty(this.employeeId)) || (StringUtils.isEmpty(this.ebfId))) {
            addErrorInfo("参数错误，请刷新后重试！");
            return "input";
        }
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee empl = empBo.loadEmp(this.employeeId, new String[] { "benefit" });
        if ((empl == null) || (empl.getBenefit() == null)
                || (StringUtils.isEmpty(empl.getBenefit().getEbfId()))
                || (!StringUtils.equals(empl.getBenefit().getEbfId(), this.ebfId))) {
            addErrorInfo("参数错误，请刷新后重试！");
            return "input";
        }
        IEmpBenefitBo empbenefitBo = (IEmpBenefitBo) getBean("empbenefitBo");
        String error = empbenefitBo.deleteBenefit(empl, this.ebfId, getCurrentEmpNo());

        if (StringUtils.isNotEmpty(error))
            addErrorInfo(error);
        else {
            addSuccessInfo("删除" + empl.getEmpName() + "的社保记录成功！");
        }
        return "success";
    }

    public String getEmpStatusName(int id) {
        for (Statusconf statusconf : this.empStatus) {
            if (statusconf.getId().getStatusconfNo().intValue() == id) {
                return statusconf.getStatusconfDesc();
            }
        }

        return "无状态";
    }

    /** @deprecated */
    public String initAddPageData() {
        if (StringUtils.isEmpty(this.employeeId)) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "input";
        }
        String[] fetch = { Employee.PROP_EMP_BENEFIT_TYPE, Employee.PROP_EMP_BENEFIT,
                Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_LOCATION_NO, Employee.PROP_EMP_PB_NO,
                Employee.PROP_EMP_TYPE, Employee.PROP_CONFIG,
                Employee.PROP_CONFIG + "." + Empsalaryconfig.PROP_ESC_JOBGRADE,
                Employee.PROP_CONFIG + "." + Empsalaryconfig.PROP_ESC_ESAV_ID };

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        this.emp = empBo.loadEmp(this.employeeId, fetch);
        if (this.emp == null) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "input";
        }

        if (this.emp.getBenefit() != null) {
            addActionError("该员工已经存在社保记录！");
            return "input";
        }

        if (this.benefit == null) {
            this.benefit = new Empbenefit();
        }

        this.startBtfYear = DateUtil.formatTodayToS("yyyy");
        this.startBtfMonth = DateUtil.formatTodayToS("MM");

        this.ebfTypeList = getDrillDown("BenefitType", new String[0]);
        this.yearList = getDrillDown("EmpSalaryPeriod", new String[0]);

        return null;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List<Employee> getEmpbenefitList() {
        return this.empbenefitList;
    }

    public void setEmpbenefitList(List<Employee> empbenefitList) {
        this.empbenefitList = empbenefitList;
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public List<Location> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Statusconf> getEmpStatus() {
        return this.empStatus;
    }

    public void setEmpStatus(List<Statusconf> empStatus) {
        this.empStatus = empStatus;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public String[][] getBenefitValue() {
        return this.benefitValue;
    }

    public void setBenefitValue(String[][] benefitValue) {
        this.benefitValue = benefitValue;
    }

    public static long getSerialVersionUID() {
        return 1L;
    }

    public String getEbfId() {
        return this.ebfId;
    }

    public void setEbfId(String ebfId) {
        this.ebfId = ebfId;
    }

    public List<Empbenefit> getBenefitList() {
        return this.BenefitList;
    }

    public void setBenefitList(List<Empbenefit> benefitList) {
        this.BenefitList = benefitList;
    }

    public List<BenefitType> getEbfTypeList() {
        return this.ebfTypeList;
    }

    public void setEbfTypeList(List<BenefitType> ebfTypeList) {
        this.ebfTypeList = ebfTypeList;
    }

    public String getInputstartBtfYear() {
        return this.inputstartBtfYear;
    }

    public void setInputstartBtfYear(String inputstartBtfYear) {
        this.inputstartBtfYear = inputstartBtfYear;
    }

    public String getInputstartBtfMonth() {
        return this.inputstartBtfMonth;
    }

    public void setInputstartBtfMonth(String inputstartBtfMonth) {
        this.inputstartBtfMonth = inputstartBtfMonth;
    }

    public String getEmpNameOrDist() {
        return this.empNameOrDist;
    }

    public void setEmpNameOrDist(String empNameOrDist) {
        this.empNameOrDist = empNameOrDist;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Empsalarypay> getBenefitPayvalueList() {
        return this.benefitPayvalueList;
    }

    public void setBenefitPayvalueList(List<Empsalarypay> benefitPayvalueList) {
        this.benefitPayvalueList = benefitPayvalueList;
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }

    public FileInputStream getDocStream() {
        return this.docStream;
    }

    public void setDocStream(FileInputStream docStream) {
        this.docStream = docStream;
    }

    public String getContentDisposition() {
        return this.contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public String getServerFileName() {
        return this.serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Emptype> getEmpTypeList() {
        return this.empTypeList;
    }

    public void setEmpTypeList(List<Emptype> empTypeList) {
        this.empTypeList = empTypeList;
    }

    public String getAmountCategory() {
        return this.amountCategory;
    }

    public void setAmountCategory(String amountCategory) {
        this.amountCategory = amountCategory;
    }

    public String getAmountFrom() {
        return this.amountFrom;
    }

    public void setAmountFrom(String amountFrom) {
        this.amountFrom = amountFrom;
    }

    public String getAmountTo() {
        return this.amountTo;
    }

    public void setAmountTo(String amountTo) {
        this.amountTo = amountTo;
    }

    public List<String> getMonthList() {
        return this.monthList;
    }

    public void setMonthList(List<String> monthList) {
        this.monthList = monthList;
    }

    public String getStartBtfMonth() {
        return this.startBtfMonth;
    }

    public void setStartBtfMonth(String startBtfMonth) {
        this.startBtfMonth = startBtfMonth;
    }

    public String getStartBtfYear() {
        return this.startBtfYear;
    }

    public void setStartBtfYear(String startBtfYear) {
        this.startBtfYear = startBtfYear;
    }

    public List<String> getYearList() {
        return this.yearList;
    }

    public void setYearList(List<String> yearList) {
        this.yearList = yearList;
    }

    public Empbenefit getBenefit() {
        return this.benefit;
    }

    public void setBenefit(Empbenefit benefit) {
        this.benefit = benefit;
    }

    public String getEmpBenefitTypeId() {
        return this.empBenefitTypeId;
    }

    public void setEmpBenefitTypeId(String empBenefitTypeId) {
        this.empBenefitTypeId = empBenefitTypeId;
    }

    public static String getInputIoName() {
        return "IEmpSalaryBenefitUpd";
    }

    public static String getOutputIoName() {
        return "OEmpSalaryBenefit";
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.SearchEmpbenefit JD-Core Version: 0.5.4
 */