package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.bo.CustomizeReportBo;
import com.hr.spring.SpringBeanFactory;
import javax.servlet.ServletContext;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public class DeleteReportAction extends BaseAction {
    private String reportId;

    public String execute() throws Exception {
        if (StringUtils.isEmpty(this.reportId)) {
            addErrorInfo("请求参数错误，删除失败！");
            return "input";
        }
        CustomizeReportBo customizeReportBo = (CustomizeReportBo) SpringBeanFactory
                .getBean("customizeReportBo");
        ServletContext context = ServletActionContext.getServletContext();
        customizeReportBo.deleteReport(this.reportId);
        addSuccessInfo("删除报表记录成功");
        return "success";
    }

    public String getReportId() {
        return this.reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.action.customize.DeleteReportAction JD-Core Version: 0.5.4
 */