package com.hr.compensation.action;

import com.hr.base.DWRUtil;
import com.hr.compensation.bo.IEmpBenefitPlanBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.IEmpsalarydatadefBo;
import com.hr.compensation.bo.IEmpsalaryperiodBo;
import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalaryperiod;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import com.hr.util.output.FieldOperate;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public class SearchBeneHistoryAction extends CompAction {
    private static final long serialVersionUID = 1L;
    private String empCondition;
    private Employee emp;
    private Pager page;
    private String orderFieldName;
    private List<Department> deptList;
    private List<Emptype> empType;
    private List<Location> locationList;
    private List<BenefitType> ebfTypeList;
    private List<Empbenefitplan> ebpList;
    private String canEdit;
    private Integer beneCategory;
    private List<String> years;
    private String startYear;
    private String startMonth;
    private String endYear;
    private String endMonth;
    private String yearMonthCate;
    private String exportType;
    private String searchOrExport;
    private String outmatchModelId;
    private String outputIoName;

    public SearchBeneHistoryAction() {
        this.emp = new Employee();

        this.page = new Pager();

        this.deptList = null;
        this.empType = null;
        this.locationList = null;

        this.startYear = DateUtil.formatTodayToS("yyyy");
        this.startMonth = DateUtil.formatTodayToS("MM");

        this.endYear = DateUtil.formatTodayToS("yyyy");
        this.endMonth = DateUtil.formatTodayToS("MM");

        this.yearMonthCate = "0";

        this.outputIoName = "OEmpBenefitHistory";
    }

    public String execute() throws Exception {
        getDrillDownList();

        this.ebpList = searchEbp();

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        ebpBo.calcEbp(this.ebpList);

        if ("export".equals(this.searchOrExport)) {
            if (("yearMonth".equals(this.exportType))
                    || ("belongYearMonth".equals(this.exportType))) {
                this.ebpList = ebpBo.sumEbpList(this.ebpList, this.exportType);
            }

            IEmpsalarydatadefBo empsalarydatadefBo = (IEmpsalarydatadefBo) SpringBeanFactory
                    .getBean("empsalarydatadefBo");
            List dataDefList = empsalarydatadefBo.getBenedatadefs();
            List fieldAddList = generateAddTitleList(dataDefList);
            ebpBo.processDataForExport(this.ebpList, dataDefList);

            return downloadToExcel(this.ebpList, this.outputIoName, this.outmatchModelId,
                                   "ebpEmpno", fieldAddList);
        }

        return "success";
    }

    private List<Empbenefitplan> searchEbp() {
        DetachedCriteria dc = searchBenefitPlan_DC();

        addCriteria(dc);

        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        return ebpBo.findEbp(dc, this.page, this.searchOrExport);
    }

    private DetachedCriteria searchBenefitPlan_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empbenefitplan.class);
        dc.createAlias(Empbenefitplan.PROP_EBP_EMP_NO, "emp", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "dept", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_LOCATION_NO, "location", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_TYPE, "empType", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_BENEFIT_TYPE, "beneType", 1);
        dc.createAlias(Empbenefitplan.PROP_EBP_EBP_ESAV_ID, "ebpEsavId", 1);
        dc.createAlias(Empbenefitplan.PROP_EBP_EBP_ESAV_ID + "."
                + Empsalaryacctversion.PROP_ESAV_ESAC, "esac", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_PB_NO, "empPbNo", 1);

        return dc;
    }

    private void addCriteria(DetachedCriteria dc) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "emp", 2);
        }
        addOrders(dc, this.page, new String[] { this.orderFieldName,
                "emp." + Employee.PROP_EMP_DISTINCT_NO + "-up" });

        BaseCrit.addEmpDC(dc, "emp", this.emp.getEmpName());
        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, null, this.emp.getEmpDeptNo());
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                       new Object[] { this.emp.getEmpLocationNo() });
        BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_BENEFIT_TYPE, "id", new Object[] { this.emp
                .getEmpBenefitType() });

        BaseCrit.addDC(dc, Empbenefitplan.PROP_EBP_STATUS, "eq",
                       new Integer[] { this.beneCategory });

        if ("0".equals(this.yearMonthCate)) {
            BaseCrit.addDC(dc, Empbenefitplan.PROP_EBP_YEARMONTH, "ge",
                           new String[] { this.startYear + this.startMonth });
            BaseCrit.addDC(dc, Empbenefitplan.PROP_EBP_YEARMONTH, "le", new String[] { this.endYear
                    + this.endMonth });
            this.orderFieldName = (Empbenefitplan.PROP_EBP_YEARMONTH + "-down");
        } else if ("1".equals(this.yearMonthCate)) {
            BaseCrit.addDC(dc, Empbenefitplan.PROP_EBP_BELONG_YEARMONTH, "ge",
                           new String[] { this.startYear + this.startMonth });
            BaseCrit.addDC(dc, Empbenefitplan.PROP_EBP_BELONG_YEARMONTH, "le",
                           new String[] { this.endYear + this.endMonth });
            this.orderFieldName = (Empbenefitplan.PROP_EBP_BELONG_YEARMONTH + "-down");
        }
    }

    private void getDrillDownList() {
        this.deptList = getDrillDown("Department", new String[0]);
        this.locationList = getDrillDown("Location", new String[0]);
        this.empType = getDrillDown("EmpType", new String[0]);
        this.ebfTypeList = getDrillDown("BenefitType", new String[0]);
        this.empStatus = getDrillDown("EmpStatus", new String[0]);
        this.years = getDrillDown("ebpYear", new String[0]);
    }

    private List<FieldOperate> generateAddTitleList(List<Empsalarydatadef> dataDefList) {
        List addTitleList = new ArrayList();
        Empsalarydatadef datadef = null;
        for (int i = 0; i < dataDefList.size(); ++i) {
            datadef = (Empsalarydatadef) dataDefList.get(i);
            FieldOperate operate = new FieldOperate();
            operate.setColWidth(15);
            operate.setDataType("decimal");
            operate.setFieldName("outPutList." + datadef.getEsddId());
            operate.setShowName(datadef.getEsddName());
            addTitleList.add(operate);
        }

        FieldOperate operate = new FieldOperate();
        operate.setColWidth(30);
        operate.setDataType("string");
        operate.setFieldName("ebpComments");
        operate.setShowName("备注");
        addTitleList.add(operate);

        return addTitleList;
    }

    public List<Empsalaryacctitems> getEbpItemsById(String ebpId) {
        String flt = DWRUtil.checkAuth("getEbpById", "getEbpById");
        if ("error".equalsIgnoreCase(flt)) {
            return null;
        }
        IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
        Empbenefitplan ebp = ebpBo.searchEbpById(ebpId);
        ebp.decryEMPPlan(ebp);

        IEmpSalaryAcctitemsBo itemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        List<Empsalaryacctitems> acctItems = itemsBo.getItemsByAcctversion(ebp.getEbpEsavId()
                .getId());
        Class ownerClass = ebp.getClass();
        Method ebpMethod = null;
        BigDecimal ebpBD = null;
        for (Empsalaryacctitems item : acctItems) {
            try {
                ebpMethod = ownerClass.getMethod("getEbpColumn" + item.getEsaiDataSeq(),
                                                 new Class[0]);
                ebpBD = (BigDecimal) ebpMethod.invoke(ebp, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            item.setItemValue(ebpBD);
        }

        return acctItems;
    }

    public String getEspStatus(String yearMonth) {
        yearMonth = yearMonth.replaceAll("-", "");
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(4);
        IEmpsalaryperiodBo periodBo = (IEmpsalaryperiodBo) getBean("empsalaryperiodBO");
        Empsalaryperiod period = periodBo.loadEspdStatus(year + month);
        if ((period == null) || (period.getEspdStatus().intValue() == 0)) {
            return "true";
        }

        return "false";
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public List<Emptype> getEmpType() {
        return this.empType;
    }

    public void setEmpType(List<Emptype> empType) {
        this.empType = empType;
    }

    public List<Location> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<BenefitType> getEbfTypeList() {
        return this.ebfTypeList;
    }

    public void setEbfTypeList(List<BenefitType> ebfTypeList) {
        this.ebfTypeList = ebfTypeList;
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public String getEmpCondition() {
        return this.empCondition;
    }

    public void setEmpCondition(String empCondition) {
        this.empCondition = empCondition;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List<Empbenefitplan> getEbpList() {
        return this.ebpList;
    }

    public void setEbpList(List<Empbenefitplan> ebpList) {
        this.ebpList = ebpList;
    }

    public String getOrderFieldName() {
        return this.orderFieldName;
    }

    public void setOrderFieldName(String orderFieldName) {
        this.orderFieldName = orderFieldName;
    }

    public String getCanEdit() {
        return this.canEdit;
    }

    public void setCanEdit(String canEdit) {
        this.canEdit = canEdit;
    }

    public Integer getBeneCategory() {
        return this.beneCategory;
    }

    public void setBeneCategory(Integer beneCategory) {
        this.beneCategory = beneCategory;
    }

    public String getEndMonth() {
        return this.endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getEndYear() {
        return this.endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getStartMonth() {
        return this.startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getStartYear() {
        return this.startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public List<String> getYears() {
        return this.years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }

    public String getOutputIoName() {
        return this.outputIoName;
    }

    public String getExportType() {
        return this.exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getYearMonthCate() {
        return this.yearMonthCate;
    }

    public void setYearMonthCate(String yearMonthCate) {
        this.yearMonthCate = yearMonthCate;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.SearchBeneHistoryAction JD-Core Version: 0.5.4
 */