package com.hr.information.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.configuration.domain.Infoclass;
import com.hr.information.bo.IInformationBo;
import com.hr.information.domain.Information;
import com.hr.security.domain.Userinfo;
import com.hr.util.Pager;
import com.opensymphony.xwork2.ActionContext;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class SearchInfo extends BaseAction implements Constants {
    private static final long serialVersionUID = -4234940898871087425L;
    private List infoList;
    private List classList;
    private Pager page;
    private String searchKey;
    private String delInfoId;
    private String classId;
    private Infoclass infoclass;

    public SearchInfo() {
        this.page = new Pager();

        this.searchKey = null;

        this.delInfoId = null;

        this.infoclass = null;
    }

    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        if ((request.getParameter("classId") != null)
                && (request.getParameter("classId").trim().length() > 0)) {
            try {
                this.classId = request.getParameter("classId");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IInformationBo infoBo = (IInformationBo) getBean("informationBo");
        if ((this.delInfoId != null) && (this.delInfoId.length() > 0)) {
            Information delInfo = infoBo.loadOne(this.delInfoId);
            infoBo.deleteInfo(delInfo);
        }
        if ((this.classId != null) && (this.classId.length() > 0)) {
            this.infoclass = infoBo.getInfoclass(this.classId);
        }
        if (hasAuth(801) == true) {
            this.infoList = infoBo.getListbyClassId(this.classId, this.page, this.searchKey, null,
                                                    null);
        } else if (hasAuthModuleCondition(802, 3) == true) {
            this.infoList = infoBo.getListbyClassId(this.classId, this.page, this.searchKey, null,
                                                    Integer.valueOf(1));
        } else if (hasAuthModuleCondition(802, 2) == true) {
            Map session = ServletActionContext.getContext().getSession();
            Userinfo viewUser = (Userinfo) session.get("userinfo");
            this.infoList = infoBo.getListbyClassId(this.classId, this.page, this.searchKey,
                                                    viewUser, Integer.valueOf(1));
        }
        this.classList = infoBo.getInfoClassBySortId();
        ServletActionContext.getRequest().setAttribute("resultList", this.infoList);
        return "success";
    }

    public List getInfoList() {
        return this.infoList;
    }

    public void setInfoList(List infoList) {
        this.infoList = infoList;
    }

    public List getClassList() {
        return this.classList;
    }

    public void setClassList(List classList) {
        this.classList = classList;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getClassId() {
        return this.classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getDelInfoId() {
        return this.delInfoId;
    }

    public void setDelInfoId(String delInfoId) {
        this.delInfoId = delInfoId;
    }

    public Infoclass getInfoclass() {
        return this.infoclass;
    }

    public void setInfoclass(Infoclass infoclass) {
        this.infoclass = infoclass;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.information.action.SearchInfo JD-Core Version: 0.5.4
 */