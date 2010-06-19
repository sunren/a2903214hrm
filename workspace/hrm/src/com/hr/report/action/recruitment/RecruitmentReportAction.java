package com.hr.report.action.recruitment;

import com.hr.base.BaseAction;
import com.hr.report.bo.IReportBo;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class RecruitmentReportAction extends BaseAction {
    private static final long serialVersionUID = 4005170458776122443L;
    private String reportSelect;
    private String designSelect;
    private String year;
    private String startM;
    private String endM;
    private List<String> yearList;

    public RecruitmentReportAction() {
        this.yearList = new ArrayList();
    }

    public String execute() throws Exception {
        IReportBo r_BO = (IReportBo) getBean("reportBo");
        List<Timestamp> dateList = r_BO
                .exeHqlList("SELECT distinct ra.recaCreateTime  FROM Recruitapplier ra");
        Calendar ca = Calendar.getInstance();
        String yearTemp = null;
        for (Timestamp date : dateList) {

            ca.setTime(new Date(date.getTime()));
            yearTemp = String.valueOf(ca.get(1));
            if (!yearList.contains(yearTemp)) {
                yearList.add(yearTemp);
            }
        }
        String reportID = getRequest().getParameter("reportSelect");
        if (reportID != null) {
            if (reportID.equals("report1")) {
                designSelect = "rp";
            } else if (reportID.equals("report2")) {
                designSelect = "ra";
            } else {
                return "error";
            }
        } else {
            startM = "m1";
            endM = "m12";
            year = Calendar.getInstance().get(1) + "";
            designSelect = "none";
        }
        return "success";
    }

    //
    //    public String execute()
    //    throws Exception
    //  {
    //    IReportBo r_BO = (IReportBo)getBean("reportBo");
    //
    //    List dateList = r_BO.exeHqlList("SELECT distinct ra.recaCreateTime  FROM Recruitapplier ra");
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
    //    String reportID = getRequest().getParameter("reportSelect");
    //    if (reportID != null) {
    //      if (reportID.equals("report1")) {
    //        this.designSelect = "rp"; break label216:
    //      }if (reportID.equals("report2")) {
    //        this.designSelect = "ra"; break label216:
    //      }
    //      return "error";
    //    }
    //
    //    this.startM = "m1";
    //    this.endM = "m12";
    //    this.year = (Calendar.getInstance().get(1) + "");
    //    this.designSelect = "none";
    //
    //    label216: return "success";
    //  }

    public String executeRPByNow() throws Exception {
        ServletRequest request = getRequest();

        if ((getRequest().getParameter("_format") != null)
                && (getRequest().getParameter("_format").equals("pdf")))
            request.setAttribute("_format", "PDF");
        else {
            request.setAttribute("_format", "HTML");
        }

        Map params = new HashMap();

        request.setAttribute("_params", params);

        request.setAttribute("_report", "/report/recruitment/RecruitplanByNow.rptdesign");
        return "success";
    }

    public String executeRAByTime() throws Exception {
        Date startTime = null;
        Date endTime = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        if ((this.year == null) || (this.year.trim().length() == 0)) {
            this.year = (Calendar.getInstance().get(1) + "");
        }
        try {
            c.setTime(df.parse(this.year + "-" + getRequest().getParameter("startM").substring(1)
                    + "-1"));
            c.set(5, c.getActualMinimum(5));
            startTime = c.getTime();
            c.setTime(df.parse(this.year + "-" + getRequest().getParameter("endM").substring(1)
                    + "-1"));
            c.set(5, c.getActualMaximum(5));
            endTime = c.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return "error";
        }
        ServletRequest request = getRequest();

        if ((getRequest().getParameter("_format") != null)
                && (getRequest().getParameter("_format").equals("pdf")))
            request.setAttribute("_format", "PDF");
        else {
            request.setAttribute("_format", "HTML");
        }

        Map params = new HashMap();

        params.put("param_StartTime", df.format(startTime));
        params.put("param_EndTime", df.format(endTime));
        request.setAttribute("_params", params);

        request.setAttribute("_report", "/report/recruitment/RecruitapplierByTime.rptdesign");
        return "success";
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStartM() {
        return this.startM;
    }

    public void setStartM(String startM) {
        this.startM = startM;
    }

    public String getEndM() {
        return this.endM;
    }

    public void setEndM(String endM) {
        this.endM = endM;
    }

    public List<String> getYearList() {
        return this.yearList;
    }

    public void setYearList(List<String> yearList) {
        this.yearList = yearList;
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
 * com.hr.report.action.recruitment.RecruitmentReportAction JD-Core Version: 0.5.4
 */