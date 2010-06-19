package com.hr.report.bo;

import com.hr.base.FileOperate;
import com.hr.report.dao.IReportDAO;
import com.hr.report.domain.ReportDef;
import com.hr.report.domain.ReportParams;
import com.hr.report.domain.ReportSets;
import com.hr.report.template.AbstractTemplate;
import com.hr.report.template.factory.TemplateFactory;
import com.hr.report.util.BirtReportFilelocator;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CustomizeReportBoImpl implements CustomizeReportBo {
    private IReportDAO reportDAO;

    public List<String> addReport(List<ReportSets> reportSets, ReportDef report,
            List<ReportParams> params) {
        List errors = new ArrayList();

        report.setReportLastChangeTime(new Date());
        String reportFileName = UUID.randomUUID().toString();
        report.setReportFile(reportFileName + ".rptdesign");
        try {
            this.reportDAO.saveObject(report);
        } catch (Exception ex) {
            errors.add("保存新报表时发生异常");
            ex.printStackTrace();
        }

        String sql = ReportParametersHandler.execute(report, params, reportSets, report
                .getReportMainTable());
        report.setSql(sql);

        AbstractTemplate template = TemplateFactory.getReportTemplate(report);
        try {
            template.buildReport(reportFileName + ".rptdesign", report.getReportId());
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("生成报表文件时发生错误，操作失败");
            return errors;
        }

        return errors;
    }

    public List<String> deleteReport(String reportId) {
        List errors = new ArrayList();

        ReportDef report = (ReportDef) this.reportDAO.loadObject(ReportDef.class, reportId, null,
                                                                 new boolean[0]);
        if (report == null) {
            errors.add("找不到指定的记录，请求参数错误！");
            return errors;
        }

        String setHql = "delete from ReportSets  r where r.reportSetsReportDef.reportId='"
                + reportId + "'";
        String paramHql = "delete from ReportParams  r where r.reportDef.reportId='" + reportId
                + "'";
        try {
            this.reportDAO.exeHql(setHql);
            this.reportDAO.exeHql(paramHql);
            this.reportDAO.deleteObject(report);
        } catch (Exception ex) {
            errors.add("删除报表记录时发生错误，" + ex.getMessage());
            ex.printStackTrace();
            return errors;
        }

        ServletContext context = ServletActionContext.getServletContext();
        String fullName = BirtReportFilelocator.getBirtReportFileAbsoluteLocation(context, report
                .getReportType())
                + report.getReportFile();
        File temp = new File(fullName);
        if (temp.exists()) {
            temp.delete();
            temp = null;
        }
        return errors;
    }

    public List getObjects(Class clas, String[] fetch) {
        return this.reportDAO.getObjects(clas, fetch);
    }

    public List<String> updateReport(List<ReportSets> reportSets, ReportDef report,
            List<ReportParams> params) {
        String reportId = report.getReportId();

        List errors = addReport(reportSets, report, params);
        if (!errors.isEmpty()) {
            return errors;
        }

        return deleteReport(reportId);
    }

    public IReportDAO getReportDAO() {
        return this.reportDAO;
    }

    public void setReportDAO(IReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    public List<ReportDef> findReport(ReportDef report) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportDef.class);

        detachedCriteria.addOrder(Order.desc("reportLastChangeTime"));
        if (report != null) {
            if (StringUtils.isNotEmpty(report.getReportName())) {
                detachedCriteria.add(Restrictions.like("reportName", "%" + report.getReportName()
                        + "%"));
            }

            if ((report.getReportModule() != null) && (report.getReportModule().intValue() != 0)) {
                detachedCriteria.add(Restrictions.eq("reportModule", report.getReportModule()));
            }

            if ((report.getReportType() == null) || (report.getReportType().intValue() == 0))
                detachedCriteria.add(Restrictions.eq("reportType", Integer.valueOf(0)));
            else {
                detachedCriteria.add(Restrictions.in("reportType", new Integer[] {
                        Integer.valueOf(1), Integer.valueOf(9) }));
            }
        }
        return this.reportDAO.findByCriteria(detachedCriteria);
    }

    public ReportDef loadReport(String id, String[] fetch) {
        return (ReportDef) this.reportDAO.loadObject(ReportDef.class, id, fetch, new boolean[0]);
    }

    public List<String> addReport(ReportDef report, File reportFile) {
        List errors = new ArrayList();
        if (reportFile == null) {
            errors.add("报表文件为空，操作失败！");
            return errors;
        }

        String fileName = UUID.randomUUID().toString();
        try {
            String configString = "report.dir";
            if (report.getReportType().intValue() == 1)
                configString = "report.syspredefine";
            else if (report.getReportType().intValue() == 9) {
                configString = "report.custpredefine";
            }
            FileOperate.buildFile(reportFile, configString, fileName + ".rptdesign");
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("上传预定义报表文件时发生错误，操作失败！");
            return errors;
        }

        report.setReportFile(fileName + ".rptdesign");
        report.setReportCreateTime(new Date());
        report.setReportLastChangeTime(new Date());
        try {
            this.reportDAO.saveObject(report);
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("预定义报表创建时发生异常，操作失败！");
        }
        return errors;
    }

    public List<String> updateReport(ReportDef report, File reportFile) {
        List errors = new ArrayList();

        ReportDef reportDef = (ReportDef) this.reportDAO.loadObject(ReportDef.class, report
                .getReportId(), null, new boolean[0]);
        if (reportDef == null) {
            errors.add("您要更新的记录不存在或已经被删除，操作失败！");
            return errors;
        }

        if (reportFile != null) {
            try {
                FileOperate.deleteFile("report.dir", reportDef.getReportFile());
            } catch (Exception ex) {
            }

            String fileName = UUID.randomUUID().toString();
            reportDef.setReportFile(fileName + ".rptdesign");
            try {
                FileOperate.buildFile(reportFile, "report.dir", fileName + ".rptdesign");
            } catch (Exception ex) {
                ex.printStackTrace();
                errors.add("上传预定义报表文件时发生错误，操作失败！");
                return errors;
            }

        }

        reportDef.setReportAuthority(report.getReportAuthority());
        reportDef.setReportDescription(report.getReportDescription());
        reportDef.setReportModule(report.getReportModule());
        reportDef.setReportName(report.getReportName());

        reportDef.setReportLastChangeTime(new Date());
        try {
            this.reportDAO.saveObject(reportDef);
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.add("预定义报表修改时发生异常，操作失败！");
        }
        return errors;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.bo.CustomizeReportBoImpl JD-Core Version: 0.5.4
 */