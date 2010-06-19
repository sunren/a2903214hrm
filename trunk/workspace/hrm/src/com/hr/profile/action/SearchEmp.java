package com.hr.profile.action;

import com.hr.base.ComonBeans;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Location;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Empaddconf;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.SimpleEmployee;
import com.hr.util.BaseCrit;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import com.hr.util.StringUtil;
import com.hr.util.SysConfigManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class SearchEmp extends ProfileAction {
    private static final long serialVersionUID = -4234940898871087425L;
    SysConfigManager dbConfigManager = DatabaseSysConfigManager.getInstance();
    private Employee emp;
    private Employee batchemp = new Employee();

    private String searchOrExport = null;

    private Pager page = new Pager();
    private List<SimpleEmployee> managers;
    private Map<String, String> eqTypeMap;
    private Map<String, Map<String, String>> eqReasonMap;
    private List<Department> departs;
    private List<Location> locations;
    private List<BenefitType> ebfTypeList;
    private List<Emptype> empTypes;
    private List<JobTitle> empJobTitles;
    private List<Employee> empList;
    private List<Empaddconf> additionalList;
    private Empaddconf additional;
    private String outmatchModelId;
    public final String outputIoName = "OEmployeeBasic";

    public final String inputIoName = "IEmployeeBasic";

    public SearchEmp() {
        this.emp = new Employee();
        this.emp.setEmpShiftType(null);
    }

    public String execute() throws Exception {
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        try {
            if (("update".equalsIgnoreCase(this.page.getOperate()))
                    || ("delete".equalsIgnoreCase(this.page.getOperate()))) {
                this.batchemp.setIds(this.emp.getIds().trim());
                String oper = ("delete".equalsIgnoreCase(this.page.getOperate())) ? "删除" : "修改";
                List errors = empBo.batchUpdateOrDel(this.batchemp, this.page, getCurrentEmpNo());

                if ((errors != null) && (errors.size() > 0))
                    addErrorInfo(errors);
                else if ((this.batchemp.getIds() != null) && (this.batchemp.getIds().length() > 0)) {
                    addSuccessInfo(StringUtil.message(this.msgSuccOperate, new Object[] { oper }));
                }

                this.batchemp = new Employee();
            } else {
                this.emp.setIds(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(this.msgErrorOperate);
        }

        getDrillDownList();

        this.empList = searchEmployee();

        for (Employee employee : this.empList) {
            employee.setJoinYear(MyTools.getWorkYearProfile(employee.getEmpJoinDate(), new Date()));
        }

        if ("export".equals(this.searchOrExport)) {
            super.getClass();
            return downloadToExcel(this.empList, "OEmployeeBasic", this.outmatchModelId, "");
        }

        return "success";
    }

    private List<Employee> searchEmployee() {
        DetachedCriteria dcEmp = searchEmp_DC();

        addCriteria(dcEmp);

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        return empBo.searchAndExportEmp(dcEmp, this.page, this.searchOrExport);
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

    public List<String> searchNoOrName(String searchKey) {
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        List result = new ArrayList();

        if ((searchKey == null) || (searchKey.trim().length() == 0)) {
            return result;
        }
        searchKey = searchKey.trim();

        String searchProperty = Employee.PROP_EMP_DISTINCT_NO;
        for (int i = 0; i < searchKey.length(); ++i) {
            int ch = searchKey.charAt(i);
            if (ch > 255) {
                searchProperty = Employee.PROP_EMP_NAME;
                break;
            }
        }
        String hql = "select " + searchProperty + " from Employee where " + searchProperty
                + " like '%" + searchKey + "%' order by " + searchProperty;
        result = empBo.exeHqlList(hql.toString(), new int[] { 10 });
        return result;
    }

    private DetachedCriteria searchEmp_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class, "emp");

        dc.createAlias(Employee.PROP_EMP_DEPT_NO, "empOrgDept", 1);
        dc.createAlias(Employee.PROP_EMP_PB_NO, "empPbNo", 1);
        dc.createAlias(Employee.PROP_EMP_LOCATION_NO, "empLocationNo", 1);
        dc.createAlias(Employee.PROP_EMP_TYPE, "empType", 1);

        if ("export".equals(this.searchOrExport)) {
            dc.createAlias(Employee.PROP_EMP_BENEFIT_TYPE, "empBenefitType", 1);
            dc.createAlias(Employee.PROP_EMP_CONTRACT, "contract", 1);
        }

        return dc;
    }

    private void addCriteria(DetachedCriteria dc) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "", 1);
        }

        addOrders(dc, this.page, new String[] {
                "empOrgDept." + Department.PROP_DEPARTMENT_SORT_ID + "-down",
                Employee.PROP_EMP_DISTINCT_NO + "-up" });

        BaseCrit.addEmpDC(dc, "", this.emp.getEmpNoName());
        BaseCrit.addDeptDC(dc, Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_PB_NO, Integer
                .valueOf(1), this.emp.getEmpDeptNo());
        BaseCrit.addDC(dc, Employee.PROP_EMP_STATUS, "eq", new Integer[] { Integer.valueOf(1) });
        BaseCrit.addDC(dc, Employee.PROP_EMP_TYPE, Emptype.PROP_ID, new Object[] { this.emp
                .getEmpType() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID, new Object[] { this.emp
                .getEmpLocationNo() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_PB_NO, PositionBase.PROP_ID, new Object[] { this.emp
                .getEmpPbNo() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_BENEFIT_TYPE, "id", new Object[] { this.emp
                .getEmpBenefitType() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_NAME, "like", new String[] { this.emp.getEmpName() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_GENDER, "eq",
                       new Integer[] { this.emp.getEmpGender() });
        BaseCrit
                .addDC(dc, Employee.PROP_EMP_DISTINCT_NO, "like", new String[] { this.emp.getId() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_MARITAL, "eq", new Integer[] { this.emp
                .getEmpMarital() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_WORK_PHONE, "like", new String[] { this.emp
                .getEmpWorkPhone() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_BLOOD, "eq", new String[] { this.emp.getEmpBlood() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_RESIDENCE_LOC, "like", new String[] { this.emp
                .getEmpResidenceLoc() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_CITY_NO, "like", new String[] { this.emp
                .getEmpCityNo() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_PROFILE_LOC, "like", new String[] { this.emp
                .getEmpProfileLoc() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_NATION, "like",
                       new String[] { this.emp.getEmpNation() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_MOBILE, "like",
                       new String[] { this.emp.getEmpMobile() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_POLITICS, "like", new String[] { this.emp
                .getEmpPolitics() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_HOME_PHONE, "like", new String[] { this.emp
                .getEmpHomePhone() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_SCHOOL, "like",
                       new String[] { this.emp.getEmpSchool() });
        BaseCrit
                .addDC(dc, Employee.PROP_EMP_EMAIL, "like", new String[] { this.emp.getEmpEmail() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_SPECIALITY, "like", new String[] { this.emp
                .getEmpSpeciality() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_IDENTIFICATION_NO, "like", new String[] { this.emp
                .getEmpIdentificationNo() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_DIPLOMA, "like", new String[] { this.emp
                .getEmpDiploma() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_CURR_ADDR, "like", new String[] { this.emp
                .getEmpCurrAddr() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_MSN, "like", new String[] { this.emp.getEmpMsn() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_HOME_ADDR, "like", new String[] { this.emp
                .getEmpHomeAddr() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_URGENT_CONTACT, "like", new String[] { this.emp
                .getEmpUrgentContact() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_COMMENTS, "like", new String[] { this.emp
                .getEmpComments() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_URGENT_CON_METHOD, "like", new String[] { this.emp
                .getEmpUrgentConMethod() });
        BaseCrit.addDCDateRange(dc, Employee.PROP_EMP_BIRTH_DATE, this.emp.getEmpBirthDate(),
                                this.emp.getBirthDate());
        BaseCrit.addDCDateRange(dc, Employee.PROP_EMP_WORK_DATE, this.emp.getEmpWorkDate(),
                                this.emp.getWorkDate());
        BaseCrit.addDCDateRange(dc, Employee.PROP_EMP_JOIN_DATE, this.emp.getEmpJoinDate(),
                                this.emp.getJoinDate());
        BaseCrit.addDCDateRange(dc, Employee.PROP_EMP_CONFIRM_DATE, this.emp.getEmpConfirmDate(),
                                this.emp.getConfirmDate());
        BaseCrit.addDC(dc, Employee.PROP_EMP_SHIFT_TYPE, "eq", new Integer[] { this.emp
                .getEmpShiftType() });
        BaseCrit.addDC(dc, Employee.PROP_EMP_SHIFT_NO, "like", new String[] { this.emp
                .getEmpShiftNo() });

        if (this.additional != null) {
            if ((StringUtils.isEmpty(this.additional.getValue()))
                    && (StringUtils.isEmpty(this.additional.getValue2())))
                return;
            Integer seq = this.additional.getEadcSeq();
            String propertyName = "empAdditional" + seq;
            if ((seq != null) && (seq.intValue() != 0)) {
                String type = this.additional.getEadcFieldType();
                if (("date".equals(type)) || ("number".equals(type))) {
                    if ((StringUtils.isNotEmpty(this.additional.getValue()))
                            && (StringUtils.isNotEmpty(this.additional.getValue2())))
                        dc.add(Restrictions.between(propertyName, this.additional.getValue(),
                                                    this.additional.getValue2()));
                    else if (StringUtils.isNotEmpty(this.additional.getValue()))
                        dc.add(Restrictions.ge(propertyName, this.additional.getValue()));
                    else if (StringUtils.isNotEmpty(this.additional.getValue2()))
                        dc.add(Restrictions.le(propertyName, this.additional.getValue2()));
                } else if ("select".equals(type))
                    dc.add(Restrictions.eq(propertyName, this.additional.getValue()));
                else
                    dc.add(Restrictions.like(propertyName, this.additional.getValue(),
                                             MatchMode.ANYWHERE));
            }
        }
    }

    private void getDrillDownList() {
        this.departs = getDrillDown("Department", new String[0]);
        this.empTypes = getDrillDown("EmpType", new String[0]);
        this.locations = getDrillDown("Location", new String[0]);
        this.additionalList = getDrillDown("Empaddconf", new String[] { "empAdditional" });
        this.ebfTypeList = getDrillDown("BenefitType", new String[0]);

        this.eqTypeMap = ComonBeans.getValuesToMap(Empquit.PROP_EQ_TYPE, new boolean[] { false });
        this.eqTypeMap.remove("1");
        this.eqReasonMap = ComonBeans.getSecondLevelSelect(this.eqTypeMap, Empquit.PROP_EQ_REASON,
                                                           new boolean[0]);
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

    public List<Department> getDeparts() {
        return this.departs;
    }

    public void setDeparts(List<Department> departs) {
        this.departs = departs;
    }

    public List<SimpleEmployee> getManagers() {
        return this.managers;
    }

    public void setManagers(List<SimpleEmployee> managers) {
        this.managers = managers;
    }

    public List<Employee> getEmpList() {
        return this.empList;
    }

    public void setEmpList(List<Employee> empList) {
        this.empList = empList;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }

    public List<JobTitle> getEmpJobTitles() {
        return this.empJobTitles;
    }

    public void setEmpJobTitles(List<JobTitle> empJobTitles) {
        this.empJobTitles = empJobTitles;
    }

    public Employee getBatchemp() {
        return this.batchemp;
    }

    public void setBatchemp(Employee batchemp) {
        this.batchemp = batchemp;
    }

    public List<BenefitType> getEbfTypeList() {
        return this.ebfTypeList;
    }

    public void setEbfTypeList(List<BenefitType> ebfTypeList) {
        this.ebfTypeList = ebfTypeList;
    }

    public List getAdditionalList() {
        return this.additionalList;
    }

    public void setAdditionalList(List additionalList) {
        this.additionalList = additionalList;
    }

    public Empaddconf getAdditional() {
        return this.additional;
    }

    public void setAdditional(Empaddconf additional) {
        this.additional = additional;
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }

    public Map<String, String> getEqTypeMap() {
        return this.eqTypeMap;
    }

    public void setEqTypeMap(Map<String, String> eqTypeMap) {
        this.eqTypeMap = eqTypeMap;
    }

    public Map<String, Map<String, String>> getEqReasonMap() {
        return this.eqReasonMap;
    }

    public void setEqReasonMap(Map<String, Map<String, String>> eqReasonMap) {
        this.eqReasonMap = eqReasonMap;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.SearchEmp JD-Core Version: 0.5.4
 */