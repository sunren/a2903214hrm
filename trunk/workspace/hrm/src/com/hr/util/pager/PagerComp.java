package com.hr.util.pager;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.UIBean;

public class PagerComp extends UIBean {
    public final String TEMPLATE = "pager";
    private String pageNo;
    private String totalPages;
    private String totalRows;
    private String start;
    private String end;
    private String formId;
    private String styleClass;
    private String theme;

    public PagerComp(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return "pager";
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (this.pageNo != null) {
            addParameter("pageNo", findValue(this.pageNo));
        }

        if (this.totalPages != null) {
            addParameter("totalPages", findValue(this.totalPages));
        }

        if (this.totalRows != null) {
            addParameter("totalRows", findValue(this.totalRows));
        }

        if (this.start != null) {
            addParameter("start", findValue(this.start));
        }

        if (this.end != null) {
            addParameter("end", findValue(this.end));
        }

        if (this.formId != null) {
            addParameter("formId", findValue(this.formId));
        }

        if (this.styleClass != null) {
            addParameter("styleClass", findValue(this.styleClass));
        }

        if (this.theme != null)
            addParameter("theme", findValue(this.theme));
    }

    public String getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.pager.PagerComp JD-Core Version: 0.5.4
 */