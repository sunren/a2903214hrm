package com.hr.report.action.training;

import com.hr.base.BaseAction;
import com.hr.report.bo.IReportBo;
import com.hr.training.bo.ITrcourseBO;
import com.hr.training.domain.Trcourse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class TrainingReportAction extends BaseAction {
    private static final long serialVersionUID = 6285066077414769882L;
    private List<String> yearList;
    private List<Trcourse> trcourseList;
    private String year;
    private String trType;
    private String reportSelect;
    private String designSelect;

    public TrainingReportAction() {
        this.yearList = new ArrayList();

        this.designSelect = "";
    }

    @SuppressWarnings("unchecked")
    public String execute() throws Exception {
        ITrcourseBO trc_BO = (ITrcourseBO) getBean("trcourseBO");
        trcourseList = trc_BO.loadAll();
        IReportBo r_BO = (IReportBo) getBean("reportBo");
        List<Timestamp> dateList = r_BO
                .exeHqlList("SELECT distinct tr.trcpStartDate FROM Trcourseplan tr");
        Calendar ca = Calendar.getInstance();
        String yearTemp = null;
        for (Timestamp date : dateList) {
            ca.setTime(new Date(date.getTime()));

            yearTemp = String.valueOf(ca.get(1));
            if (!this.yearList.contains(yearTemp)) {
                this.yearList.add(yearTemp);
            }
        }
        String reportID = getRequest().getParameter("reportSelect");
        if (reportID != null) {
            if (reportID.equals("report1")) {
                designSelect = "tr";
            } else {
                year = Calendar.getInstance().get(1) + "";
                return "error";
            }
        } else {
            designSelect = "none";
        }
        return "success";
    }

    //    public String execute()
    //    throws Exception
    //  {
    //    ITrcourseBO trc_BO = (ITrcourseBO)getBean("trcourseBO");
    //    this.trcourseList = trc_BO.loadAll();
    //    IReportBo r_BO = (IReportBo)getBean("reportBo");
    //
    //    List dateList = r_BO.exeHqlList("SELECT distinct tr.trcpStartDate FROM Trcourseplan tr");
    //
    //    Calendar ca = Calendar.getInstance();
    //    String yearTemp = null;
    //    for (Timestamp date : dateList)
    //    {
    //      ca.setTime(new Date(date.getTime()));
    //
    //      yearTemp = String.valueOf(ca.get(1));
    //      if (!this.yearList.contains(yearTemp))
    //      {
    //        this.yearList.add(yearTemp);
    //      }
    //    }
    //
    //    String reportID = getRequest().getParameter("reportSelect");
    //    if (reportID != null) {
    //      if (reportID.equals("report1")) {
    //        this.designSelect = "tr"; break label207:
    //      }
    //      this.year = (Calendar.getInstance().get(1) + "");
    //      return "error";
    //    }
    //
    //    this.designSelect = "none";
    //
    //    label207: return "success";
    //  }

    public String executeTRShow() throws Exception {
        ServletRequest request = getRequest();

        if ((getRequest().getParameter("_format") != null)
                && (getRequest().getParameter("_format").equals("pdf")))
            request.setAttribute("_format", "PDF");
        else {
            request.setAttribute("_format", "HTML");
        }

        Map params = new HashMap();
        if ((this.year == null) || (this.year.trim().length() == 0)) {
            this.year = (Calendar.getInstance().get(1) + "");
        }
        params.put("trYear", this.year);

        if ((getRequest().getParameter("trType") != null)
                && (getRequest().getParameter("trType").length() > 0)) {
            params.put("trType", getRequest().getParameter("trType"));
            ITrcourseBO trc_BO = (ITrcourseBO) getBean("trcourseBO");
            params.put("trTypeName", trc_BO.loadTrc(getRequest().getParameter("trType").trim())
                    .getTrcName());
            request.setAttribute("_report", "/report/training/TrcourseplanOneByYear.rptdesign");
        } else {
            request.setAttribute("_report", "/report/training/TrcourseplanAllByYear.rptdesign");
        }
        request.setAttribute("_params", params);
        return "success";
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTrType() {
        return this.trType;
    }

    public void setTrType(String trType) {
        this.trType = trType;
    }

    public List<String> getYearList() {
        return this.yearList;
    }

    public void setYearList(List<String> yearList) {
        this.yearList = yearList;
    }

    public List<Trcourse> getTrcourseList() {
        return this.trcourseList;
    }

    public void setTrcourseList(List<Trcourse> trcourseList) {
        this.trcourseList = trcourseList;
    }

    public String getReportSelect() {
        return this.reportSelect;
    }

    public void setReportSelect(String reportSelect) {
        this.reportSelect = reportSelect;
    }

    public String getDesignSelect() {
        return this.designSelect;
    }

    public void setDesignSelect(String designSelect) {
        this.designSelect = designSelect;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.training.TrainingReportAction JD-Core Version: 0.5.4
 */