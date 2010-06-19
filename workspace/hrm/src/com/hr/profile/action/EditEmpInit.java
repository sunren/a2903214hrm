package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.bo.IBaseBo;
import com.hr.configuration.bo.IBenefitTypeBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Location;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.MyTools;
import com.hr.util.SysConfigManager;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.report.model.api.util.StringUtil;

public class EditEmpInit extends BaseAction implements Constants {
    private static final long serialVersionUID = -4487105795L;
    SysConfigManager dbConfigManager;
    private Employee emp;
    private String connectionType;
    private String connectionNo;
    private List<Location> locations;
    private List<Emptype> empTypes;
    private List<JobTitle> empJobTitles;
    private List<BenefitType> benefitTypes;
    String exShiftEnable;
    private String createFlag;
    private String empNo;
    private String empName;

    public EditEmpInit() {
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String createInit() throws Exception {
        IEmpTypeBO bo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        this.empTypes = bo.FindEnabledEmpType();

        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.locations = localbo.FindEnabledLocation();
        localbo = null;

        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.empJobTitles = jobTitleBo.FindEnabledJobTitle();

        IBenefitTypeBO benefitTypeBo = (IBenefitTypeBO) SpringBeanFactory.getBean("benefitTypeBo");
        this.benefitTypes = benefitTypeBo.findAll();

        this.exShiftEnable = this.dbConfigManager.getProperty("sys.examin.shift.enable").trim();

        return "success";
    }

    public String updateInit() throws Exception {
        if ((StringUtil.isEmpty(this.empNo)) || (this.empNo.equals("null")))
            this.empNo = getCurrentEmpNo();

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        String[] fetch = { Employee.PROP_EMP_TYPE, Employee.PROP_EMP_PB_NO,
                Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_LOCATION_NO,
                Employee.PROP_EMP_BENEFIT_TYPE };

        this.emp = empBo.loadEmp(this.empNo, fetch);
        this.empName = this.emp.getEmpName();

        if (!this.empNo.equals(getCurrentEmpNo())) {
            if ("OWN".equals(this.authorityCondition))
                return "noauth";
            if (((("SUB".equals(this.authorityCondition)) || ("HRSUB"
                    .equals(this.authorityCondition))))
                    && (!checkEmpInCharge(this.emp, 1))) {
                return "noauth";
            }
        }

        String empMsn = this.emp.getEmpMsn();
        if ((empMsn != null) && (!"".equals(empMsn))) {
            if (empMsn.startsWith("QQ:")) {
                this.connectionType = "1";
                this.connectionNo = empMsn.replace("QQ:", "");
            } else if (empMsn.startsWith("WW:")) {
                this.connectionType = "2";
                this.connectionNo = empMsn.replace("WW:", "");
            } else {
                this.connectionType = "0";
                this.connectionNo = empMsn;
            }

        }

        this.exShiftEnable = this.dbConfigManager.getProperty("sys.examin.shift.enable").trim();

        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        List tempLogList = logBO.getLogs("employee", this.empNo);
        getEmp().setLogs(tempLogList);

        this.emp.setWorkYear(MyTools.getWorkYearProfile(this.emp.getEmpWorkDate(), new Date()));
        this.emp.setJoinYear(MyTools.getWorkYearProfile(this.emp.getEmpJoinDate(), new Date()));

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position empPos = posBo.getPosByEmpNo(this.emp.getId(),
                                              new String[] { Position.PROP_POSITION_PB_ID });
        this.emp.setPosition(empPos);

        if ((this.authorityCondition.equals("HR")) || (this.authorityCondition.equals("HRSUB"))) {
            this.empTypes = getDrillDown("EmpType", new String[0]);
            this.locations = getDrillDown("Location", new String[0]);
            this.benefitTypes = getDrillDown("BenefitType", new String[0]);

            if (StringUtils.isNotEmpty(this.createFlag)) {
                addSuccessInfo("员工" + this.emp.getEmpName() + "创建成功，您可以继续修改详细资料〄1�7");
            }

            return "success";
        }

        return "general";
    }

    public String getYearOrMonth(Float joinYear) {
        int value = 0;
        String result = "";
        if (joinYear.floatValue() >= 1.0F) {
            value = Integer.parseInt(joinYear.intValue() + "");
            result = result + value + "幄1�7";
        } else {
            joinYear = Float.valueOf(joinYear.floatValue() * 100.0F);
            value = Integer.parseInt(joinYear.intValue() + "");
            result = result + value + "个月";
        }

        return result;
    }

    public String getAutoNo(String model) {
        String result = "";
        if (model == null) {
            return "";
        }
        model = model.trim();
        if (model.length() == 0) {
            return "";
        }
        IBaseBo bo = (IBaseBo) SpringBeanFactory.getBean("baseService");
        result = bo.getNoByPrefix("Employee", model, "empDistinctNo");
        return result;
    }

    public String selfInit() throws Exception {
        return updateInit();
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public List<Emptype> getEmpTypes() {
        return this.empTypes;
    }

    public void setEmpTypes(List<Emptype> empTypes) {
        this.empTypes = empTypes;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<JobTitle> getEmpJobTitles() {
        return this.empJobTitles;
    }

    public void setEmpJobTitles(List<JobTitle> empJobTitles) {
        this.empJobTitles = empJobTitles;
    }

    public String getConnectionType() {
        return this.connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getConnectionNo() {
        return this.connectionNo;
    }

    public void setConnectionNo(String connectionNo) {
        this.connectionNo = connectionNo;
    }

    public List<BenefitType> getBenefitTypes() {
        return this.benefitTypes;
    }

    public void setBenefitTypes(List<BenefitType> benefitTypes) {
        this.benefitTypes = benefitTypes;
    }

    public String getCreateFlag() {
        return this.createFlag;
    }

    public void setCreateFlag(String createFlag) {
        this.createFlag = createFlag;
    }

    public String getExShiftEnable() {
        return this.exShiftEnable;
    }

    public void setExShiftEnable(String exShiftEnable) {
        this.exShiftEnable = exShiftEnable;
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.EditEmpInit JD-Core Version: 0.5.4
 */