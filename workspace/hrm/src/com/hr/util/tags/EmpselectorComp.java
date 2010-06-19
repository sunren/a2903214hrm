package com.hr.util.tags;

import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Location;
import com.hr.spring.SpringBeanFactory;
import com.opensymphony.xwork2.util.ValueStack;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.UIBean;

public class EmpselectorComp extends UIBean {
    public final String TEMPLATE = "empselector";
    private String hiddenFieldName;
    private String isShowDisable;
    private String isShowPb;
    private String condition;
    private String localSelect;

    public EmpselectorComp(ValueStack stack, HttpServletRequest request,
            HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return "empselector";
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

        if (this.condition != null) {
            addParameter("condition", findString(this.condition));
            addParameter("conditionValue", findValue(this.condition));
        }

        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        List<Location> locationList = localbo.FindEnabledLocation();

        this.localSelect = "<select name='location' id='empSelector_location'>";
        this.localSelect += "<option value=''>请选择</option>";
        if ((locationList != null) && (!locationList.isEmpty())) {
            for (Location loc : locationList) {
                this.localSelect = (this.localSelect + "<option value='" + loc.getId() + "'>"
                        + loc.getLocationName() + "</option>");
            }
        }
        this.localSelect += "</select>";
        addParameter("localSelect", this.localSelect);
    }

    public String getHiddenFieldName() {
        return this.hiddenFieldName;
    }

    public void setHiddenFieldName(String hiddenFieldName) {
        this.hiddenFieldName = hiddenFieldName;
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

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLocalSelect() {
        return this.localSelect;
    }

    public void setLocalSelect(String localSelect) {
        this.localSelect = localSelect;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.tags.EmpselectorComp JD-Core Version: 0.5.4
 */