package com.hr.information.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.information.bo.IInformationBo;
import com.hr.information.domain.Information;
import com.hr.spring.SpringBeanFactory;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class CreateInfoInit extends BaseAction implements Constants {
    private static final long serialVersionUID = -4234940898871087425L;
    private List classList;
    private Information info;
    private List locationList;
    private List departmentList;
    private List buList;
    private static final Logger logger = Logger.getLogger(CreateInfoInit.class);

    public CreateInfoInit() {
        this.locationList = null;

        this.departmentList = null;

        this.buList = null;
    }

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("测试：execute() - SearchInfo.action");
        }

        HttpServletRequest request = ServletActionContext.getRequest();
        String infoId = null;
        if ((request.getParameter("infoId") != null)
                && (request.getParameter("infoId").trim().length() > 0)) {
            try {
                infoId = request.getParameter("infoId");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        IInformationBo infoBo = (IInformationBo) getBean("informationBo");

        if ((infoId != null) && (infoId.length() > 0)) {
            this.info = infoBo.loadOne(infoId);
        }
        this.classList = infoBo.getInfoClassBySortId();
        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.locationList = localbo.FindEnabledLocation();
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        this.departmentList = deptbo.FindEnabledDepartment();

        if (logger.isDebugEnabled()) {
            logger.debug("execute() -SearchInfo- end");
        }
        return "success";
    }

    public List getClassList() {
        return this.classList;
    }

    public void setClassList(List classList) {
        this.classList = classList;
    }

    public Information getInfo() {
        return this.info;
    }

    public void setInfo(Information info) {
        this.info = info;
    }

    public List getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List locationList) {
        this.locationList = locationList;
    }

    public List getDepartmentList() {
        return this.departmentList;
    }

    public void setDepartmentList(List departmentList) {
        this.departmentList = departmentList;
    }

    public List getBuList() {
        return this.buList;
    }

    public void setBuList(List buList) {
        this.buList = buList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.information.action.CreateInfoInit JD-Core Version: 0.5.4
 */