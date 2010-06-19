package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.base.Status;
import com.hr.base.io.Exportinfo;
import com.hr.compensation.bo.ICompaplanBo;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.configuration.domain.StatusconfPK;
import com.hr.exportfile.ExportSalaryAdj;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.Pager;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

public class SearchCompaplan extends BaseAction implements Status {
    private static final long serialVersionUID = -48988238265L;
    private String searchOrExport;
    private FileInputStream docStream;
    private String contentDisposition;
    private String fileName;
    private Empsalaryadj compaplan;
    private Pager page;
    private String syscoments;
    private List<Jobgrade> jobgradePro;
    private List<Empsalaryacctversion> acctVersionList;
    private List<Empsalaryadj> compaplanList;
    private List<Location> locations;
    private List<Statusconf> status;
    private List<Statusconf> empStatus;
    private List<Department> departs;
    private List<Emptype> empType;

    public SearchCompaplan() {
        this.searchOrExport = null;
        this.docStream = null;
        this.contentDisposition = null;

        this.compaplan = new Empsalaryadj();
        this.page = new Pager();

        this.jobgradePro = new ArrayList();
    }

    public String execute() throws Exception {
        DetachedCriteria dc = searchPayAdjHist_DC();
        addCriteria(dc);

        getDrillDownList();

        ICompaplanBo compaplanBo = (ICompaplanBo) getBean("compaplanBo");

        if ("export".equals(this.searchOrExport)) {
            try {
                List exportResult = compaplanBo.mysearchCompaplan(dc, null);

                if ((exportResult == null) || (exportResult.size() == 0)) {
                    addActionError("无调薪计划数据可以导出！");
                    return "success";
                }

                Exportinfo exportSalary = new Exportinfo();
                IEmpSalaryAcctitemsBo esaitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                        .getBean("empsalaryacctitemsBo");
                List[] titles = exportSalary.getSalaryadjSheetAndTitle(exportResult, esaitemsBo);
                List[] results = exportSalary.getSalaryadjResult(exportResult, titles);

                String filePath = FileOperate.getFileHomePath();
                String xslFileName = "export-"
                        + FileOperate.buildFileName()
                        + PropertiesFileConfigManager.getInstance().getProperty("sys.saveDir.type")
                                .trim();
                filePath = filePath + xslFileName;

                ExportSalaryAdj export = new ExportSalaryAdj(results, titles);
                export.export(filePath);

                File file = new File(filePath);
                FileInputStream fileInputStream = new FileInputStream(file);

                setDocStream(fileInputStream);
                String downFileName = "salaryAdj.xls";
                setContentDisposition("filename=\"" + downFileName + "\"");
                setFileName(xslFileName);
                clearErrorsAndMessages();
                return "download";
            } catch (Exception e) {
                addActionError("数据导出失败＄1�7");
                e.printStackTrace();
                return "success";
            }

        }

        this.compaplanList = compaplanBo.mysearchCompaplan(dc, this.page);
        if (this.compaplanList.isEmpty())
            return "success";

        compaplanBo.calcSalaryAdj(this.compaplanList);

        return "success";
    }

    private DetachedCriteria searchPayAdjHist_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Empsalaryadj.class);
        dc.setFetchMode("esaEmpno.empDeptNo", FetchMode.JOIN);
        dc.setFetchMode("esaEmpno.empType", FetchMode.JOIN);
        dc.setFetchMode("esaEmpno.empLocationNo", FetchMode.JOIN);
        dc.setFetchMode("esaEcptypeId", FetchMode.JOIN);
        dc.setFetchMode("esaJobgradeCur", FetchMode.JOIN);
        dc.setFetchMode("esaJobgradePro", FetchMode.JOIN);
        dc.setFetchMode("esaEsavIdCur", FetchMode.JOIN);
        dc.setFetchMode("esaEsavIdPro", FetchMode.JOIN);
        dc.createAlias("esaEmpno", "emp");
        return dc;
    }

    private void addCriteria(DetachedCriteria dc) {
        if (this.compaplan.getEsaEmpno() != null) {
            Employee employee = this.compaplan.getEsaEmpno();

            BaseCrit.addEmpDC(dc, "emp", employee.getEmpName());
            BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO, "emp."
                    + Employee.PROP_EMP_PB_NO, null, employee.getEmpDeptNo());
            BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_TYPE, Emptype.PROP_ID,
                           new Object[] { employee.getEmpType() });
            BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_LOCATION_NO, Location.PROP_ID,
                           new Object[] { employee.getEmpLocationNo() });
            BaseCrit.addDC(dc, "emp." + Employee.PROP_EMP_STATUS, "eq", new Integer[] { employee
                    .getEmpStatus() });
        }

        BaseCrit.addDC(dc, Empsalaryadj.PROP_ESA_STATUS, "eq", new Integer[] { this.compaplan
                .getEsaStatus() });
    }

    private void getDrillDownList() {
        this.departs = getDrillDown("Department", new String[0]);
        this.locations = getDrillDown("Location", new String[0]);
        this.empType = getDrillDown("EmpType", new String[0]);
        this.jobgradePro = getDrillDown("Jobgrade", new String[0]);
        this.acctVersionList = getDrillDown("EmpSalaryAcct", new String[0]);
        this.status = getDrillDown("CompaplanStatus", new String[0]);
        this.empStatus = getDrillDown("EmpStatus", new String[0]);
    }

    /** @deprecated */
    public String getEcpStatus(int statusNo) {
        for (Statusconf stconf : this.status) {
            if (stconf.getId().getStatusconfNo().intValue() == statusNo)
                return stconf.getStatusconfDesc();
        }
        return "";
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public Empsalaryadj getCompaplan() {
        return this.compaplan;
    }

    public void setCompaplan(Empsalaryadj compaplan) {
        this.compaplan = compaplan;
    }

    public List<Empsalaryadj> getCompaplanList() {
        return this.compaplanList;
    }

    public void setCompaplanList(List<Empsalaryadj> compaplanList) {
        this.compaplanList = compaplanList;
    }

    public List<Statusconf> getEmpStatus() {
        return this.empStatus;
    }

    public void setEmpStatus(List<Statusconf> empStatus) {
        this.empStatus = empStatus;
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

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Jobgrade> getJobgradePro() {
        return this.jobgradePro;
    }

    public void setJobgradePro(List<Jobgrade> jobgradePro) {
        this.jobgradePro = jobgradePro;
    }

    public List<Empsalaryacctversion> getAcctVersionList() {
        return this.acctVersionList;
    }

    public void setAcctVersionList(List<Empsalaryacctversion> acctVersionList) {
        this.acctVersionList = acctVersionList;
    }

    public List<Emptype> getEmpType() {
        return this.empType;
    }

    public void setEmpType(List<Emptype> empType) {
        this.empType = empType;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Statusconf> getStatus() {
        return this.status;
    }

    public void setStatus(List<Statusconf> status) {
        this.status = status;
    }

    public String getSyscoments() {
        return this.syscoments;
    }

    public void setSyscoments(String syscoments) {
        this.syscoments = syscoments;
    }

    public List<Department> getDeparts() {
        return this.departs;
    }

    public void setDeparts(List<Department> departs) {
        this.departs = departs;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.SearchCompaplan JD-Core Version: 0.5.4
 */