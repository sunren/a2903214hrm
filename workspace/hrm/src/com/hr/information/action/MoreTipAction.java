package com.hr.information.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.homepage.bo.IIFindTip;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;

public class MoreTipAction extends BaseAction implements Constants {
    private List tipList;
    private static final long serialVersionUID = 1L;

    public String execute() throws Exception {
        Map session = ServletActionContext.getContext().getSession();
        Employee viewUser = getCurrentEmp();
        if (viewUser == null)
            return "login";
        IEmployeeBo empbo = (IEmployeeBo) getBean("empBo");
        Employee emp = empbo.loadEmp(viewUser.getId(), null);
        String[] depNo = emp.getDeptInChargeOld();
        IIFindTip findTip = (IIFindTip) getBean("findTip");
        this.tipList = getTipListInit(findTip, viewUser.getId(), depNo);
        if ((hasAuth(801) == true) || (hasAuthModuleCondition(802, 3) == true)) {
            viewUser = null;
        }
        return "success";
    }

    public List getTipListInit(IIFindTip findTip, String userId, String[] depNo) {
        List tempList = new ArrayList();

        if (hasAuth(101)) {
            tempList.addAll(findTip.getTip101());
            tempList.addAll(findTip.getEmployeeAdditionalTip());
            tempList.addAll(findTip.getTip1012());
        }

        if (hasAuth(111)) {
            tempList.addAll(findTip.getTip111(userId));
        }

        if (hasAuth(401)) {
            tempList.addAll(findTip.getTip401());
        }

        if (hasAuth(301)) {
            tempList.addAll(findTip.getTip301());
        }

        if (hasAuth(601)) {
            tempList.addAll(findTip.getTip601());
        }

        if (hasAuth(201)) {
            tempList.addAll(findTip.getTip201());
        }

        if ((hasAuthModuleCondition(411, 2)) || (hasAuthModuleCondition(411, 3))) {
            tempList.addAll(findTip.getTip4112(getCurrentPosNo()));
        }

        if (hasAuth(411)) {
            tempList.addAll(findTip.getTip411(userId));
        }

        if (hasAuthModuleCondition(301, 2)) {
            tempList.addAll(findTip.getTip3012());
        }

        if ((hasAuthModuleCondition(311, 2)) && (depNo != null)) {
            tempList.addAll(findTip.getTip3112(depNo));
        }

        if (hasAuth(311)) {
            tempList.addAll(findTip.getTip311(userId));
        }

        if (hasAuthModuleCondition(601, 2)) {
            tempList.addAll(findTip.getTip6012());
        }

        if ((hasAuthModuleCondition(611, 2)) && (depNo != null)) {
            tempList.addAll(findTip.getTip6112(depNo));
        }

        if (hasAuth(611)) {
            tempList.addAll(findTip.getTip611(userId));
        }

        if (hasAuthModuleCondition(201, 2)) {
            tempList.addAll(findTip.getTip2012());
        }

        if (hasAuth(211)) {
            tempList.addAll(findTip.getTip2112(userId));
            tempList.addAll(findTip.getTip211(userId));
        }

        return tempList;
    }

    public List getTipList() {
        return this.tipList;
    }

    public void setTipList(List tipList) {
        this.tipList = tipList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.information.action.MoreTipAction JD-Core Version: 0.5.4
 */