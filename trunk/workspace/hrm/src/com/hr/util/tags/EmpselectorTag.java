package com.hr.util.tags;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

public class EmpselectorTag extends AbstractUITag {
    private static final long serialVersionUID = 1L;
    private String hiddenFieldName;
    private String isShowDisable;
    private String isShowPb;
    private String condition;

    public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
        return new EmpselectorComp(arg0, arg1, arg2);
    }

    protected void populateParams() {
        super.populateParams();
        EmpselectorComp selector = (EmpselectorComp) this.component;
        selector.setId(this.id);
        selector.setName(this.name);
        selector.setHiddenFieldName(this.hiddenFieldName);
        selector.setIsShowDisable(this.isShowDisable);
        selector.setIsShowPb(this.isShowPb);
        selector.setCondition(this.condition);
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

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.tags.EmpselectorTag JD-Core Version: 0.5.4
 */