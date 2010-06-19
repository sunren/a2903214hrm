package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.base.io.Exportinfo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.ISalaryPaidBo;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.exportfile.ExportSalary;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.axis.utils.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class MySalaryPaid extends BaseAction {
    private static final long serialVersionUID = -5413735265L;
    private Pager page;
    private List<Empsalarypay> salaryPaidList;
    private List<String> years;
    private String startYear;
    private String startMonth;
    private String endYear;
    private String endMonth;
    private String empId;
    private String empName;
    private String searchOrExport;
    private FileInputStream docStream;
    private String contentDisposition;
    private String serverFileName;

    public MySalaryPaid() {
        this.page = new Pager();

        this.startYear = DateUtil.formatTodayToS("yyyy");
        this.startMonth = "01";
        this.endYear = DateUtil.formatTodayToS("yyyy");
        this.endMonth = DateUtil.formatTodayToS("MM");

        this.docStream = null;

        this.contentDisposition = null;

        this.serverFileName = null;
    }

    public String execute() throws Exception {
        if (StringUtils.isEmpty(this.empId)) {
            this.empId = getCurrentEmpNo();
            this.empName = getCurrentEmp().getEmpName();
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee employee = empBo.loadEmp(this.empId, null);
        if (employee == null) {
            addErrorInfo("不存在此员工");
            return "error";
        }

        DetachedCriteria dcPay = searchPay_DC();
        addCriteria(dcPay);

        getDrillDownList();

        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        List espList = salaryPaidBo.findMySalaryPaid(dcPay, this.page);
        setSalaryPaidList(salaryPaidBo.searchMySalaryPaid(espList,
                                                          this.startYear + this.startMonth,
                                                          this.endYear + this.endMonth));

        setSalaryPaidList(espList);
        if ("exportSalary".equals(this.searchOrExport)) {
            if ((espList == null) || (espList.size() == 0)) {
                addActionError("无薪资发放数据可以导出！");
                return "success";
            }
            try {
                List exportResult = espList;
                Exportinfo exportSalary = new Exportinfo();
                IEmpSalaryAcctitemsBo esaitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                        .getBean("empsalaryacctitemsBo");
                List[] titles = exportSalary.getSalarySheetAndTitle(exportResult, esaitemsBo);
                List[] results = exportSalary.getSalaryResult(exportResult, titles);

                String filePath = FileOperate.getFileHomePath();
                String xslFileName = "export-"
                        + FileOperate.buildFileName()
                        + PropertiesFileConfigManager.getInstance().getProperty("sys.saveDir.type")
                                .trim();
                filePath = filePath + xslFileName;

                ExportSalary export = new ExportSalary(results, titles, true);
                export.export(filePath);

                File file = new File(filePath);
                FileInputStream fileInputStream = new FileInputStream(file);
                setDocStream(fileInputStream);
                String downFileName = "(" + this.startYear + "-" + this.startMonth + "to"
                        + this.endYear + "-" + this.endMonth + ")" + "mySalaryPaid.xls";
                setContentDisposition("filename=\"" + downFileName + "\"");

                setServerFileName(filePath);
                clearErrorsAndMessages();
                return "download";
            } catch (Exception e) {
                addActionError("数据导出失败");
                e.printStackTrace();
                return "success";
            }
        }
        return "success";
    }

    private DetachedCriteria searchPay_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empsalarypay.class);
        dc.createAlias("espEmpno", "emp", 1);

        dc.createAlias("emp." + Employee.PROP_EMP_PB_NO, "pb", 1);
        dc.setFetchMode("espEmpconfig", FetchMode.JOIN);
        dc.setFetchMode("espEmpconfig.escJobgrade", FetchMode.JOIN);
        dc.setFetchMode("espEmptype", FetchMode.JOIN);
        dc.setFetchMode("espJobgrade", FetchMode.JOIN);
        dc.setFetchMode("espEsavId", FetchMode.JOIN);
        dc.setFetchMode("espEsavId.esavEsac", FetchMode.JOIN);

        dc.createAlias(Empsalarypay.PROP_ESP_EMPNO + "." + Employee.PROP_EMP_DEPT_NO, "empOrgDept",
                       1);
        dc.createAlias(Empsalarypay.PROP_ESP_EMPNO + "." + Employee.PROP_EMP_LOCATION_NO,
                       "empOrgLoc", 1);
        dc.createAlias(Empsalarypay.PROP_ESP_EMPNO + "." + Employee.PROP_EMP_TYPE, "empType", 1);
        return dc;
    }

    private void addCriteria(DetachedCriteria dc) {
        dc.add(Restrictions.eq("emp.id", this.empId));
        BaseCrit.addDC(dc, "emp." + Employee.PROP_ID, "eq", new String[] { this.empId });
        BaseCrit.addDC(dc, Empsalarypay.PROP_ESP_YEARMONTH, "between", new String[] {
                this.startYear + this.startMonth, this.endYear + this.endMonth });
    }

    private void getDrillDownList() {
        this.years = getDrillDown("EmpSalaryPeriod", new String[0]);
    }

    public String[] defaultComputePay(String empId) {
        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");
        String[] result = new String[4];
        String maxYearmonth = DateUtil.formatDateToS(new Date(), "yyyyMM");

        String[] dateArr = new String[12];
        Calendar cal = Calendar.getInstance();
        Date temp = null;
        temp = DateUtil.parseDateByFormat(maxYearmonth, "yyyyMM");
        cal.setTime(temp);
        for (int i = 0; i < 12; ++i) {
            dateArr[i] = DateUtil.formatDateToS(cal.getTime(), "yyyyMM");
            cal.add(2, -1);
        }

        result[0] = salaryPaidBo.getGivenMonthAvgPay(empId, dateArr);
        result[2] = (DateUtil.formatDateToS(DateUtil.parseDateByFormat(dateArr[11], "yyyyMM"),
                                            "yyyy年MM月")
                + "-" + DateUtil.formatDateToS(DateUtil.parseDateByFormat(dateArr[0], "yyyyMM"),
                                               "yyyy年MM月"));

        temp = new Date();
        cal.setTime(temp);
        cal.add(1, -1);
        cal.set(2, 0);
        for (int i = 0; i < 12; ++i) {
            dateArr[i] = DateUtil.formatDateToS(cal.getTime(), "yyyyMM");
            cal.add(2, 1);
        }

        result[1] = salaryPaidBo.getGivenMonthAvgPay(empId, dateArr);
        result[3] = (DateUtil.formatDateToS(DateUtil.parseDateByFormat(dateArr[0], "yyyyMM"),
                                            "yyyy年MM月")
                + "-" + DateUtil.formatDateToS(DateUtil.parseDateByFormat(dateArr[11], "yyyyMM"),
                                               "yyyy年MM月"));

        return result;
    }

    public String givenComputePay(String empId, String start, String end) {
        ISalaryPaidBo salaryPaidBo = (ISalaryPaidBo) getBean("salaryPaidBo");

        Date startDate = DateUtil.parseDateByFormat(start, "yyyyMM");
        Date endDate = DateUtil.parseDateByFormat(end, "yyyyMM");
        List dateList = new ArrayList();
        Calendar cal = Calendar.getInstance();
        for (Date tempDate = startDate; tempDate.compareTo(endDate) <= 0;) {
            dateList.add(DateUtil.formatDateToS(tempDate, "yyyyMM"));
            cal.setTime(tempDate);
            cal.add(2, 1);
            tempDate = cal.getTime();
        }
        String[] dateArr = new String[dateList.size()];
        for (int i = 0; i < dateList.size(); ++i) {
            dateArr[i] = ((String) dateList.get(i));
        }

        String result = salaryPaidBo.getGivenMonthAvgPay(empId, dateArr);
        return result;
    }

    public String getEndMonth() {
        return this.endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getStartMonth() {
        return this.startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndYear() {
        return this.endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List<String> getYears() {
        return this.years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public List<Empsalarypay> getSalaryPaidList() {
        return this.salaryPaidList;
    }

    public void setSalaryPaidList(List<Empsalarypay> salaryPaidList) {
        this.salaryPaidList = salaryPaidList;
    }

    public String getStartYear() {
        return this.startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpId() {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
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

    public String getServerFileName() {
        return this.serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.MySalaryPaid JD-Core Version: 0.5.4
 */