package com.hr.util.pager;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

public class PagerTag extends AbstractUITag {
    private String pageNo;
    private String totalPages;
    private String totalRows;
    private String start;
    private String end;
    private String formId;
    private String styleClass;
    private String theme;

    public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
        return new PagerComp(arg0, arg1, arg2);
    }

    protected void populateParams() {
        super.populateParams();

        PagerComp pager = (PagerComp) this.component;
        pager.setPageNo(this.pageNo);
        pager.setTotalPages(this.totalPages);
        pager.setTotalRows(this.totalRows);
        pager.setStart(this.start);
        pager.setEnd(this.end);
        pager.setFormId(this.formId);
        pager.setStyleClass(this.styleClass);
        pager.setTheme(this.theme);
    }

    public String getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getStyleClass() {
        return this.styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotalRows() {
        return this.totalRows;
    }

    public void setTotalRows(String totalRows) {
        this.totalRows = totalRows;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.pager.PagerTag JD-Core Version: 0.5.4
 */