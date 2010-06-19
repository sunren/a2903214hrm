package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.training.bo.ITremployeeplanBO;
import com.hr.training.domain.Tremployeeplan;
import com.hr.util.Pager;
import java.util.List;

public class MyTrainingList extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List<Tremployeeplan> myTrainingList;
    private String trcName;
    private String trcpTeacher;
    private String trcpLocation;
    private Integer trpStatus;
    private Pager page;

    public MyTrainingList() {
        this.page = new Pager();
    }

    public String execute() {
        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
        String traineeNo = getCurrentEmpNo();
        this.trcName = ((this.trcName == null) ? null : this.trcName.trim());

        if ((this.trpStatus != null) && (this.trpStatus.intValue() == 0)) {
            this.trpStatus = null;
        }
        this.myTrainingList = tremployeeplanBO.search(traineeNo, this.trcName, this.trcpTeacher,
                                                      this.trcpLocation, this.trpStatus, this.page);

        return "success";
    }

    public static String getTreStatus(int statusNo) {
        return TrepStatus.getTreStatus(statusNo);
    }

    public List<Tremployeeplan> getMyTrainingList() {
        return this.myTrainingList;
    }

    public void setMyTrainingList(List<Tremployeeplan> myTrainingList) {
        this.myTrainingList = myTrainingList;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getTrcpLocation() {
        return this.trcpLocation;
    }

    public String getTrcName() {
        return this.trcName;
    }

    public void setTrcName(String trcName) {
        this.trcName = trcName;
    }

    public String getTrcpTeacher() {
        return this.trcpTeacher;
    }

    public void setTrcpTeacher(String trcpTeacher) {
        this.trcpTeacher = trcpTeacher;
    }

    public void setTrcpLocation(String trcpLocation) {
        this.trcpLocation = trcpLocation;
    }

    public Integer getTrpStatus() {
        return this.trpStatus;
    }

    public void setTrpStatus(Integer trpStatus) {
        this.trpStatus = trpStatus;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.action.MyTrainingList JD-Core Version: 0.5.4
 */