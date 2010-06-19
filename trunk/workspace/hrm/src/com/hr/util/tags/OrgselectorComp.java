package com.hr.util.tags;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.UIBean;

public class OrgselectorComp extends UIBean {
    public final String TEMPLATE = "orgselector";
    private String hiddenFieldName;
    private String isShowDisable;
    private String isShowPb;

    public OrgselectorComp(ValueStack stack, HttpServletRequest request,
            HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return "orgselector";
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (this.isShowDisable != null)
            addParameter("isShowDisable", findString(this.isShowDisable));
        else {
            addParameter("isShowDisable", "hide");
        }

        if (this.isShowPb != null)
            addParameter("isShowPb", findString(this.isShowPb));
        else {
            addParameter("isShowPb", "show");
        }

        if (this.hiddenFieldName != null) {
            addParameter("hiddenFieldName", findString(this.hiddenFieldName));
            addParameter("hiddenFieldNameValue", findValue(this.hiddenFieldName));
        }
    }

    public String getIsShowDisable() {
        return this.isShowDisable;
    }

    public void setIsShowDisable(String isShowDisable) {
        this.isShowDisable = isShowDisable;
    }

    public String getIsShowPb() {
        return this.isShowPb;
    }

    public void setIsShowPb(String isShowPb) {
        this.isShowPb = isShowPb;
    }

    public String getHiddenFieldName() {
        return this.hiddenFieldName;
    }

    public void setHiddenFieldName(String hiddenFieldName) {
        this.hiddenFieldName = hiddenFieldName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.tags.OrgselectorComp JD-Core Version: 0.5.4
 */