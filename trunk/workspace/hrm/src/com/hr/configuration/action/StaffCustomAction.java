package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.profile.bo.IEmpAddConfBo;
import java.util.List;

public class StaffCustomAction extends BaseAction {
    private List allEmpaddconf;
    private List<Integer> fieldList;
    private static final int EMPCONFIGNUMBER = 16;

    public String execute() throws Exception {
        IEmpAddConfBo empAddConfBo = (IEmpAddConfBo) BaseAction.getBean("empAddConfBo");
        this.allEmpaddconf = empAddConfBo.listByTable("empadditional");

        this.fieldList = empAddConfBo.listAllActiveFieldsByTable("empadditional", Integer
                .valueOf(16));

        return "success";
    }

    public List getAllEmpaddconf() {
        return this.allEmpaddconf;
    }

    public void setAllEmpaddconf(List allEmpaddconf) {
        this.allEmpaddconf = allEmpaddconf;
    }

    public List<Integer> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<Integer> fieldList) {
        this.fieldList = fieldList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.StaffCustomAction JD-Core Version: 0.5.4
 */