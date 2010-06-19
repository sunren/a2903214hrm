package com.hr.compensation.action;

import com.hr.base.Constants;
import com.hr.base.FileOperate;
import com.hr.base.Status;
import com.hr.compensation.bo.ICompaplanBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.exportfile.ExportSalaryConfig;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.Pager;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;

public class SalaryConfigAction extends CompAction implements Status, Constants {
    private static final long serialVersionUID = -4234940898871087425L;
    private String searchOrExport = null;
    private FileInputStream docStream = null;
    private String contentDisposition = null;
    private String serverFileName = null;
    private Employee emp;
    private List<Jobgrade> jobgradeList;
    private List<Empsalaryacctversion> acctList;
    private List<Employee> employeeList;
    private List<Statusconf> empStatus;
    private List<Department> departs;
    private List<Emptype> emptype;
    private List<Location> location;
    private String employeeForQuery;
    private List<String> costCenterList;
    private Empsalaryconfig empsalaryconfig;
    private String id;
    private Employee employee;
    private String defaultAcct;
    private Pager page = new Pager();

    public SalaryConfigAction() {
        this.emp = new Employee();
        this.emp.setEmpStatus(Integer.valueOf(1));
    }

    public String execute() throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        addCriteria(dc);

        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "", 2);
            if (this.emp.getEmpStatus() == null)
                this.emp.setEmpStatus(Integer.valueOf(1));
        }
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        setEmpStatus(empBo.getEmpStatus());

        getDrillDownList();

        ISalaryconfBo salaryConfBo = (ISalaryconfBo) getBean("salaryconfBo");
        if (("export".equals(this.searchOrExport))
                && (this.authorityCondition.equalsIgnoreCase("HR"))) {
            Map map = salaryConfBo.findSalaryConfigByNoPage(dc, this.page, this.emp);
            if (map == null) {
                addActionError("本次查询没有数据可以导出＄1�7");
                return "success";
            }
            try {
                String filePath = FileOperate.getFileHomePath();
                String xslFileName = "export-"
                        + FileOperate.buildFileName()
                        + PropertiesFileConfigManager.getInstance().getProperty("sys.saveDir.type")
                                .trim();

                filePath = filePath + xslFileName;
                ExportSalaryConfig exportSalaryConfig = new ExportSalaryConfig(map);
                exportSalaryConfig.export(filePath);

                File file = new File(filePath);
                FileInputStream fileInputStream = new FileInputStream(file);
                setDocStream(fileInputStream);
                String downFileName = "salaryConfig.xls";
                setContentDisposition("filename=\"" + downFileName + "\"");

                setServerFileName(filePath);
                clearErrorsAndMessages();
                return "download";
            } catch (Exception e) {
                addActionError("数据导出失败＄1�7");
                e.printStackTrace();
                return "success";
            }
        }
        addOrders(dc, this.page, new String[] {
                "config." + Empsalaryconfig.PROP_ESC_LAST_CHANGE_TIME + "-down",
                Employee.PROP_EMP_DISTINCT_NO + "-up" });
        setEmployeeList(salaryConfBo.findSalaryConfig(dc, this.page, this.emp));

        return "success";
    }

    private void addCriteria(DetachedCriteria dc) {
        BaseCrit.addEmpDC(dc, null, this.emp.getEmpName());
        BaseCrit.addDeptDC(dc, Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_PB_NO, null, this.emp
                .getEmpDeptNo());
        BaseCrit.addDC(dc, Employee.PROP_EMP_TYPE, Emptype.PROP_ID, new Object[] { this.emp
                .getEmpType() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID, new Object[] { this.emp
                .getEmpLocationNo() });

        BaseCrit.addDC(dc, Employee.PROP_EMP_STATUS, "eq",
                       new Integer[] { this.emp.getEmpStatus() });
        BaseCrit.addDC(dc, "config." + Empsalaryconfig.PROP_ESC_COST_CENTER, "eq",
                       new String[] { this.emp.getCostCenter() });
        if (this.emp.getConfig() != null) {
            BaseCrit.addDC(dc, "config." + Empsalaryconfig.PROP_ESC_JOBGRADE, Jobgrade.PROP_ID,
                           new Object[] { this.emp.getConfig().getEscJobgrade() });
            BaseCrit.addDC(dc, "config." + Empsalaryconfig.PROP_ESC_ESAV_ID,
                           Empsalaryacctversion.PROP_ID, new Object[] { this.emp.getConfig()
                                   .getEscEsavId() });
        }
    }

    private void getDrillDownList() {
        this.departs = getDrillDown("Department", new String[0]);
        this.location = getDrillDown("Location", new String[0]);
        this.emptype = getDrillDown("EmpType", new String[0]);
        this.costCenterList = getDrillDown("CostCenter", new String[0]);
        this.jobgradeList = getDrillDown("Jobgrade", new String[0]);
        this.acctList = getDrillDown("EmpSalaryAcct", new String[0]);
    }

    public String updateSalaryConfig() {
        if ((StringUtils.isEmpty(this.id))
                || (StringUtils.isEmpty(this.empsalaryconfig.getEscBankAccountNo()))
                || (this.empsalaryconfig.getEscJobgrade() == null)
                || (this.empsalaryconfig.getEscEsavId() == null)) {
            addErrorInfo(this.msgParamEx, new Object[] { "paramex" });
            return "input";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee employee = empBo.loadEmp(this.id, new String[] { "config" });
        Empsalaryconfig config = employee.getConfig();
        if (config == null) {
            addErrorInfo(this.msgNoConfig, new Object[] { "noconfig", employee.getEmpName() });
            return "input";
        }

        config.setEscBankName(this.empsalaryconfig.getEscBankName().trim());
        config.setEscJobgrade(this.empsalaryconfig.getEscJobgrade());
        config.setEscEsavId(this.empsalaryconfig.getEscEsavId());
        config.setEscBankAccountNo(this.empsalaryconfig.getEscBankAccountNo().trim());
        config
                .setEscCostCenter((this.empsalaryconfig.getEscCostCenter() != null) ? this.empsalaryconfig
                        .getEscCostCenter().trim()
                        : null);
        try {
            for (int i = 0; i < 48; ++i) {
                Object escValue = PropertyUtils.getProperty(this.empsalaryconfig, "escColumn"
                        + (i + 1));
                if (escValue != null)
                    PropertyUtils.setProperty(config, "escColumn" + (i + 1),
                                              ((BigDecimal) escValue).setScale(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            addErrorInfo(this.msgSystemEx);
            return "input";
        }

        ISalaryconfBo salaryconfBo = (ISalaryconfBo) getBean("salaryconfBo");
        if (salaryconfBo.updateSalaryconf(config, getCurrentEmpNo())) {
            addSuccessInfo(this.msgUpdConfSucc, new Object[] { "SUCC", employee.getEmpName() });
            return "success";
        }
        addErrorInfo(this.msgUpdConfFail, new Object[] { "fail", employee.getEmpName() });
        return "input";
    }

    public String addSalaryConfig() {
        if ((StringUtils.isEmpty(this.id))
                || (StringUtils.isEmpty(this.empsalaryconfig.getEscBankAccountNo()))
                || (this.empsalaryconfig.getEscJobgrade() == null)
                || (this.empsalaryconfig.getEscEsavId() == null)) {
            addErrorInfo(this.msgParamEx, new Object[] { "paramex" });
            return "success";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee employee = empBo.loadEmp(this.id, new String[] { "config" });

        if (employee == null) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "success";
        }
        if (employee.getConfig() != null) {
            addErrorInfo(this.msgHasConfig, new Object[] { "hasconf", employee.getEmpName() });
            return "success";
        }

        this.empsalaryconfig.setId(this.id);
        this.empsalaryconfig.setEmployee(employee);
        this.empsalaryconfig.setEscBankAccountNo(this.empsalaryconfig.getEscBankAccountNo().trim());
        this.empsalaryconfig
                .setEscCostCenter((this.empsalaryconfig.getEscCostCenter() != null) ? this.empsalaryconfig
                        .getEscCostCenter().trim()
                        : null);

        ISalaryconfBo salaryBo = (ISalaryconfBo) getBean("salaryconfBo");
        if (salaryBo.insertSalaryconf(this.empsalaryconfig, getCurrentEmpNo(), employee)) {
            addSuccessInfo(this.msgCreateConfSucc, new Object[] { "SUCC", employee.getEmpName() });
            return "success";
        }
        addErrorInfo(this.msgCreateConfFail, new Object[] { "fail", employee.getEmpName() });
        return "success";
    }

    public String getJobgradeName(String id) {
        for (Jobgrade jobgrade : this.jobgradeList) {
            if (jobgrade.getId().equalsIgnoreCase(id)) {
                return jobgrade.getJobGradeName();
            }
        }
        return "";
    }

    public String addSalaryConfigInit() throws Exception {
        if (StringUtils.isEmpty(this.id)) {
            addErrorInfo(this.msgParamEx, new Object[] { "paramex" });
            return "error";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        this.employee = empBo.loadEmp(this.id, new String[] { "empType", "config" });

        if (this.employee == null) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "error";
        }

        if (this.employee.getConfig() != null) {
            addErrorInfo(this.msgHasConfig, new Object[] { "hasconf", this.employee.getEmpName() });
            return "error";
        }

        this.jobgradeList = getDrillDown("Jobgrade", new String[0]);
        this.acctList = getDrillDown("EmpSalaryAcct", new String[0]);

        return "success";
    }

    public String updateSalaryConfigInit() throws Exception {
        if (StringUtils.isEmpty(this.id)) {
            addErrorInfo(this.msgParamEx, new Object[] { "paramex" });
            return "error";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        this.employee = empBo.loadEmp(this.id, new String[] { Employee.PROP_EMP_TYPE,
                Employee.PROP_EMP_PB_NO });
        if (this.employee == null) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "error";
        }

        ISalaryconfBo salaryBo = (ISalaryconfBo) getBean("salaryconfBo");
        this.empsalaryconfig = salaryBo.loadSalaryconf(this.id, new String[] { "escEsavId",
                "escJobgrade", "escEsavId.esavEsac" });
        if (this.empsalaryconfig == null) {
            addErrorInfo(this.msgNoConfig, new Object[] { "noconf", this.employee.getEmpName() });
            return "error";
        }

        this.empsalaryconfig.decryEmpSalaryConf(this.empsalaryconfig);

        this.jobgradeList = getDrillDown("Jobgrade", new String[0]);
        this.acctList = getDrillDown("EmpSalaryAcct", new String[0]);

        return "success";
    }

    public String deleteSalaryConfig() {
        if (StringUtils.isEmpty(this.id)) {
            addErrorInfo(this.msgParamEx, new Object[] { "paramex" });
            return "error";
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee employeeFromDB = empBo.loadEmp(this.id, new String[] { "config" });
        if (employeeFromDB == null) {
            addErrorInfo(this.msgNoEmp, new Object[] { "noemp" });
            return "error";
        }

        if (employeeFromDB.getConfig() == null) {
            addErrorInfo(this.msgNoConfig, new Object[] { "noconf", employeeFromDB.getEmpName() });
            return "error";
        }

        String errorMsg = "";

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        if (salaryPaidBo.isSalaryPayRecordsExist(this.id)) {
            errorMsg = errorMsg + "存在薪资发放记录＄1�7";
        }

        ICompaplanBo compaplanBo = (ICompaplanBo) getBean("compaplanBo");
        if (compaplanBo.loadCompaplanInfoByEmpNo(this.id) != null) {
            errorMsg = errorMsg + "存在调薪计划＄1�7";
        }

        if (!StringUtils.isEmpty(errorMsg)) {
            addErrorInfo(this.msgDelConfFail, new Object[] { "fail", errorMsg,
                    employeeFromDB.getEmpName() });
            return "error";
        }

        ISalaryconfBo salaryconfigBO = (ISalaryconfBo) SpringBeanFactory.getBean("salaryconfBo");
        if (salaryconfigBO.deleteSalaryconf(this.id, getCurrentEmp())) {
            addSuccessInfo(this.msgDelConfSucc,
                           new Object[] { "SUCC", employeeFromDB.getEmpName() });
            return "success";
        }
        addErrorInfo(this.msgDelConfFail, new Object[] { "fail", "", employeeFromDB.getEmpName() });
        return "success";
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List<Statusconf> getEmpStatus() {
        return this.empStatus;
    }

    public void setEmpStatus(List<Statusconf> empStatus) {
        this.empStatus = empStatus;
    }

    public List<Employee> getEmployeeList() {
        return this.employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Department> getDeparts() {
        return this.departs;
    }

    public void setDeparts(List<Department> departs) {
        this.departs = departs;
    }

    public List<Emptype> getEmptype() {
        return this.emptype;
    }

    public void setEmptype(List<Emptype> emptype) {
        this.emptype = emptype;
    }

    public List<Location> getLocation() {
        return this.location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    public void setAcctList(List<Empsalaryacctversion> acctList) {
        this.acctList = acctList;
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
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

    public String getEmployeeForQuery() {
        return this.employeeForQuery;
    }

    public void setEmployeeForQuery(String employeeForQuery) {
        this.employeeForQuery = employeeForQuery;
    }

    public List<Jobgrade> getJobgradeList() {
        return this.jobgradeList;
    }

    public void setJobgradeList(List<Jobgrade> jobgradeList) {
        this.jobgradeList = jobgradeList;
    }

    public List<Empsalaryacctversion> getAcctList() {
        return this.acctList;
    }

    public String getServerFileName() {
        return this.serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public Empsalaryconfig getEmpsalaryconfig() {
        return this.empsalaryconfig;
    }

    public void setEmpsalaryconfig(Empsalaryconfig empsalaryconfig) {
        this.empsalaryconfig = empsalaryconfig;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDefaultAcct() {
        return this.defaultAcct;
    }

    public void setDefaultAcct(String defaultAcct) {
        this.defaultAcct = defaultAcct;
    }

    public List<String> getCostCenterList() {
        return this.costCenterList;
    }

    public void setCostCenterList(List<String> costCenterList) {
        this.costCenterList = costCenterList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.SalaryConfigAction JD-Core Version: 0.5.4
 */