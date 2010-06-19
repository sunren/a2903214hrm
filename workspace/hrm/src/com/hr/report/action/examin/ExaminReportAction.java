package com.hr.report.action.examin;

import com.hr.base.BaseAction;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.bo.interfaces.IOvertimetypeBo;
import com.hr.examin.domain.Attendmonthly;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimerequest;
import com.hr.examin.domain.Overtimetype;
import com.hr.report.bo.IExaminReportBo;
import com.hr.report.bo.IReportBo;
import com.hr.spring.SpringBeanFactory;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class ExaminReportAction extends BaseAction {
    private static final long serialVersionUID = -1005017217203415536L;
    private List<Leavetype> lrtypeList;
    private List<Overtimetype> ottypeList;
    private List<String> yearList;
    private List<String> attendmonthlyYearList;
    private String reportSelect;
    private String examinType;
    private String year;
    private String startMonths;
    private String endMonths;
    private String attendmonthlyYear;
    private String attendmonthlyMonths;
    private String includeDeptPass;
    private String designSelect;
    private String reportFileLocation;

    public ExaminReportAction() {
        this.yearList = new ArrayList();

        this.attendmonthlyYearList = new ArrayList();

        this.designSelect = "";
    }

    public String execute() throws Exception {
        ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
        lrtypeList = lt_BO.FindAllLeaveType();
        IOvertimetypeBo ot_BO = (IOvertimetypeBo) getBean("overtimetypeBO");
        ottypeList = ot_BO.FindAllOtType();
        IReportBo r_BO = (IReportBo) getBean("reportBo");
        List<Timestamp> tmp1 = r_BO.exeHqlList("SELECT distinct " + Leaverequest.PROP_LR_START_DATE
                + " FROM " + Leaverequest.REF + " lr where " + Leaverequest.PROP_LR_STATUS
                + "=11 or " + Leaverequest.PROP_LR_STATUS + "=12");
        List<Timestamp> tmp2 = r_BO.exeHqlList("SELECT distinct "
                + Overtimerequest.PROP_OR_START_DATE + " FROM " + Overtimerequest.REF + " where "
                + Overtimerequest.PROP_OR_STATUS + "=11 or " + Overtimerequest.PROP_OR_STATUS
                + "=12");
        List<Timestamp> tmp3 = new ArrayList<Timestamp>();
        tmp3.addAll(tmp1);
        tmp3.addAll(tmp2);
        Calendar ca = Calendar.getInstance();
        String tempYear = null;
        Iterator<Timestamp> i$ = tmp3.iterator();
        do {
            if (!i$.hasNext()) {
                break;
            }
            Timestamp date = (Timestamp) i$.next();
            ca.setTime(new Date(date.getTime()));
            tempYear = String.valueOf(ca.get(1));
            if (!yearList.contains(tempYear)) {
                yearList.add(tempYear);
            }
        } while (true);
        attendmonthlyYearList = r_BO.exeHqlList("SELECT distinct " + Attendmonthly.PROP_ATTM_YEAR
                + " FROM " + Attendmonthly.REF + "");
        if (yearList == null || yearList.size() < 1) {
            Calendar datenow = Calendar.getInstance();
            datenow.setTime(new Date());
            yearList.add(String.valueOf(datenow.get(1)));
        }
        if (attendmonthlyYearList == null || attendmonthlyYearList.size() < 1) {
            Calendar datenow = Calendar.getInstance();
            datenow.setTime(new Date());
            attendmonthlyYearList.add(String.valueOf(datenow.get(1)));
        }
        if (getRequest().getParameter("repeated") != null
                && getRequest().getParameter("repeated").length() > 0) {
            designSelect = "repeat";
            return "success";
        }
        String reportID = getRequest().getParameter("reportSelect");
        if (reportID != null) {
            if (reportID.equals("report1")) {
                designSelect = "lr";
            } else if (reportID.equals("report2")) {
                designSelect = "ot";
            } else {
                return "error";
            }
        } else {
            startMonths = "m1";
            endMonths = "m12";
            year = (new StringBuilder()).append(Calendar.getInstance().get(1)).append("")
                    .toString();
            designSelect = "none";
        }
        return "success";
    }

    //
    //    public String execute()
    //    throws Exception
    //  {
    //    ILeavetypeBO lt_BO = (ILeavetypeBO)getBean("leavetypeBO");
    //    this.lrtypeList = lt_BO.FindAllLeaveType();
    //    IOvertimetypeBo ot_BO = (IOvertimetypeBo)getBean("overtimetypeBO");
    //    this.ottypeList = ot_BO.FindAllOtType();
    //
    //    IReportBo r_BO = (IReportBo)getBean("reportBo");
    //    List tmp1 = r_BO.exeHqlList("SELECT distinct " + Leaverequest.PROP_LR_START_DATE + " FROM " + Leaverequest.REF + " lr where " + Leaverequest.PROP_LR_STATUS + "=11 or " + Leaverequest.PROP_LR_STATUS + "=12");
    //    List tmp2 = r_BO.exeHqlList("SELECT distinct " + Overtimerequest.PROP_OR_START_DATE + " FROM " + Overtimerequest.REF + " where " + Overtimerequest.PROP_OR_STATUS + "=11 or " + Overtimerequest.PROP_OR_STATUS + "=12");
    //    List<Timestamp> tmp3 = new ArrayList();
    //    tmp3.addAll(tmp1);
    //    tmp3.addAll(tmp2);
    //
    //    Calendar ca = Calendar.getInstance();
    //    String tempYear = null;
    //    for (Timestamp date : tmp3)
    //    {
    //      ca.setTime(new Date(date.getTime()));
    //
    //      tempYear = String.valueOf(ca.get(1));
    //      if (!this.yearList.contains(tempYear))
    //      {
    //        this.yearList.add(tempYear);
    //      }
    //    }
    //    this.attendmonthlyYearList = r_BO.exeHqlList("SELECT distinct " + Attendmonthly.PROP_ATTM_YEAR + " FROM " + Attendmonthly.REF + "");
    //
    //    if ((this.yearList == null) || (this.yearList.size() < 1)) {
    //      Calendar datenow = Calendar.getInstance();
    //      datenow.setTime(new Date());
    //      this.yearList.add(String.valueOf(datenow.get(1)));
    //    }
    //
    //    if ((this.attendmonthlyYearList == null) || (this.attendmonthlyYearList.size() < 1)) {
    //      Calendar datenow = Calendar.getInstance();
    //      datenow.setTime(new Date());
    //      this.attendmonthlyYearList.add(String.valueOf(datenow.get(1)));
    //    }
    //
    //    if ((getRequest().getParameter("repeated") != null) && (getRequest().getParameter("repeated").length() > 0)) {
    //      this.designSelect = "repeat";
    //      return "success";
    //    }
    //
    //    String reportID = getRequest().getParameter("reportSelect");
    //    if (reportID != null) {
    //      if (reportID.equals("report1")) {
    //        this.designSelect = "lr"; break label611:
    //      }if (reportID.equals("report2")) {
    //        this.designSelect = "ot"; break label611:
    //      }
    //      return "error";
    //    }
    //
    //    this.startMonths = "m1";
    //    this.endMonths = "m12";
    //    this.year = (Calendar.getInstance().get(1) + "");
    //    this.designSelect = "none";
    //
    //    label611: return "success";
    //  }

    public String executeEmpLr() throws Exception {
        ServletRequest request = getRequest();

        if ((getRequest().getParameter("_format") != null)
                && (getRequest().getParameter("_format").equals("pdf"))) {
            request.setAttribute("_format", "PDF");
            request.setAttribute("_contentType", "attachment");
            request.setAttribute("_fileName", "empLrReport");
        } else {
            request.setAttribute("_format", "HTML");
        }

        Map params = new HashMap();
        String year = getRequest().getParameter("year");
        String startmonths = getRequest().getParameter("startMonths").substring(1);
        String endmonths = getRequest().getParameter("endMonths").substring(1);
        String type = getRequest().getParameter("examinType");
        String deptInclude = getRequest().getParameter("includeDeptPass");
        try {
            params.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat(
                    "yyyy-MM-dd").parse(year + "-" + startmonths + "-1")));
            String tmpDate = year + "-" + endmonths + "-1";
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(tmpDate));
            c.set(5, c.getActualMaximum(5));
            params.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            params.put("endTime", year + "-" + endmonths + "-1");
        }
        if ((type != null) && (!type.equals(""))) {
            params.put("lrtype", type);
            ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
            params.put("lrTypeShow", lt_BO.getLeavetype(type).getLtName());
        } else {
            params.put("lrtype", "all");
            params.put("lrTypeShow", "全部类型");
        }
        if ((deptInclude != null) && (!deptInclude.equals(""))) {
            params.put("lrStatus", new Integer(1));
            params.put("lrStatusShow", "包含");
        } else {
            params.put("lrStatus", new Integer(0));
            params.put("lrStatusShow", "不包含");
        }
        request.setAttribute("_params", params);

        request.setAttribute("_report", "/report/examin/Leaverequest_ByTime_Chartreport.rptdesign");
        return "success";
    }

    public String executeEmpOt() throws Exception {
        ServletRequest request = getRequest();

        if ((getRequest().getParameter("_format") != null)
                && (getRequest().getParameter("_format").equals("pdf"))) {
            request.setAttribute("_format", "PDF");
            request.setAttribute("_contentType", "attachment");
            request.setAttribute("_fileName", "empOtReport");
        } else {
            request.setAttribute("_format", "HTML");
        }

        Map params = new HashMap();

        String year = getRequest().getParameter("year");
        String startmonths = getRequest().getParameter("startMonths").substring(1);
        String endmonths = getRequest().getParameter("endMonths").substring(1);
        String type = getRequest().getParameter("examinType");
        String deptInclude = getRequest().getParameter("includeDeptPass");
        try {
            params.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat(
                    "yyyy-MM-dd").parse(year + "-" + startmonths + "-1")));
            String tmpDate = year + "-" + endmonths + "-1";
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(tmpDate));
            c.set(5, c.getActualMaximum(5));
            params.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            params.put("endTime", year + "-" + endmonths + "-1");
        }
        if ((type != null) && (!type.equals(""))) {
            params.put("lrtype", type);
            IOvertimetypeBo ot_BO = (IOvertimetypeBo) getBean("overtimetypeBO");
            params.put("lrTypeShow", ot_BO.searchByID(type).getOtName());
        } else {
            params.put("lrtype", "all");
            params.put("lrTypeShow", "全部类型");
        }
        if ((deptInclude != null) && (!deptInclude.equals(""))) {
            params.put("lrStatus", new Integer(1));
            params.put("lrStatusShow", "包含");
        } else {
            params.put("lrStatus", new Integer(0));
            params.put("lrStatusShow", "不包含");
        }

        request.setAttribute("_params", params);

        request
                .setAttribute("_report",
                              "/report/examin/Overtime_ByTime_Chartreport.rptd.rptdesign");
        return "success";
    }

    public String executeEmpAly() throws Exception {
        ServletRequest request = getRequest();

        if ((getRequest().getParameter("_format") != null)
                && (getRequest().getParameter("_format").equals("pdf"))) {
            request.setAttribute("_format", "PDF");
            request.setAttribute("_contentType", "attachment");
            request.setAttribute("_fileName", "empAttendMonthlyReport");
        } else {
            request.setAttribute("_format", "HTML");
        }

        Map params = new HashMap();
        String title = "";
        String reportTypeSelect = getRequest().getParameter("reportTypeSelect");
        String attendmonthlyYear = getRequest().getParameter("attendmonthlyYear");
        String attendmonthlyMonths = getRequest().getParameter("attendmonthlyMonths");
        if (reportTypeSelect.equals("1"))
            title = attendmonthlyYear + "年" + attendmonthlyMonths + "月" + "考勤报表（按部门）-请假（天）";
        else if (reportTypeSelect.equals("2"))
            title = attendmonthlyYear + "年" + attendmonthlyMonths + "月" + "考勤报表（按部门）-加班（小时）";
        else if (reportTypeSelect.equals("3"))
            title = attendmonthlyYear + "年" + attendmonthlyMonths + "月" + "考勤报表（按部门）-迟到（次）";
        else if (reportTypeSelect.equals("4"))
            title = attendmonthlyYear + "年" + attendmonthlyMonths + "月" + "考勤报表（按部门）-早退（次）";
        else if (reportTypeSelect.equals("5")) {
            title = attendmonthlyYear + "年" + attendmonthlyMonths + "月" + "考勤报表（按部门）-旷工（天）";
        }
        params.put("title", title);

        IExaminReportBo examinReportBo = (IExaminReportBo) SpringBeanFactory
                .getBean("examinReportBo");

        List attendMlyList = examinReportBo.getAlltendMlyObjects(reportTypeSelect,
                                                                 attendmonthlyYear,
                                                                 attendmonthlyMonths);
        params.put("attendMlyList", attendMlyList);
        request.setAttribute("_params", params);

        request.setAttribute("_report", "/report/examin/attendmonthly.rptdesign");
        return "success";
    }

    public List<Leavetype> getLrtypeList() {
        return this.lrtypeList;
    }

    public void setLrtypeList(List<Leavetype> lrtypeList) {
        this.lrtypeList = lrtypeList;
    }

    public List<Overtimetype> getOttypeList() {
        return this.ottypeList;
    }

    public void setOttypeList(List<Overtimetype> ottypeList) {
        this.ottypeList = ottypeList;
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

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStartMonths() {
        return this.startMonths;
    }

    public void setStartMonths(String startMonths) {
        this.startMonths = startMonths;
    }

    public String getEndMonths() {
        return this.endMonths;
    }

    public void setEndMonths(String endMonths) {
        this.endMonths = endMonths;
    }

    public String getDesignSelect() {
        return this.designSelect;
    }

    public void setDesignSelect(String designSelect) {
        this.designSelect = designSelect;
    }

    public String getExaminType() {
        return this.examinType;
    }

    public void setExaminType(String examinType) {
        this.examinType = examinType;
    }

    public String getIncludeDeptPass() {
        return this.includeDeptPass;
    }

    public void setIncludeDeptPass(String includeDeptPass) {
        this.includeDeptPass = includeDeptPass;
    }

    public String getReportFileLocation() {
        return this.reportFileLocation;
    }

    public void setReportFileLocation(String reportFileLocation) {
        this.reportFileLocation = reportFileLocation;
    }

    public String getAttendmonthlyYear() {
        return this.attendmonthlyYear;
    }

    public void setAttendmonthlyYear(String attendmonthlyYear) {
        this.attendmonthlyYear = attendmonthlyYear;
    }

    public String getAttendmonthlyMonths() {
        return this.attendmonthlyMonths;
    }

    public void setAttendmonthlyMonths(String attendmonthlyMonths) {
        this.attendmonthlyMonths = attendmonthlyMonths;
    }

    public List<String> getAttendmonthlyYearList() {
        return this.attendmonthlyYearList;
    }

    public void setAttendmonthlyYearList(List<String> attendmonthlyYearList) {
        this.attendmonthlyYearList = attendmonthlyYearList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.examin.ExaminReportAction JD-Core Version: 0.5.4
 */