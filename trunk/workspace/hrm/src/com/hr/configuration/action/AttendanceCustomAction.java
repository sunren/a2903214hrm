package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.profile.bo.IEmpAddConfBo;
import java.util.List;

public class AttendanceCustomAction extends BaseAction {
    private List allAttdaddconf;
    private List<Integer> attdFreeList;
    private static final int ATTDNUMBER = 24;

    public String execute() throws Exception {
        IEmpAddConfBo empAddConfBo = (IEmpAddConfBo) BaseAction.getBean("empAddConfBo");
        this.allAttdaddconf = empAddConfBo.listByTable("attend");

        this.attdFreeList = empAddConfBo.listAllActiveFieldsByTable("attend", Integer.valueOf(24));

        return "success";
    }

    public List getAllAttdaddconf() {
        return this.allAttdaddconf;
    }

    public void setAllAttdaddconf(List allAttdaddconf) {
        this.allAttdaddconf = allAttdaddconf;
    }

    public List<Integer> getAttdFreeList() {
        return this.attdFreeList;
    }

    public void setAttdFreeList(List<Integer> attdFreeList) {
        this.attdFreeList = attdFreeList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.action.AttendanceCustomAction JD-Core Version: 0.5.4
 */