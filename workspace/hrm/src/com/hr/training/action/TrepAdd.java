package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import com.hr.training.bo.ITrcourseplanBO;
import com.hr.training.bo.ITremployeeplanBO;
import com.hr.training.domain.Trcourseplan;
import com.hr.training.domain.Tremployeeplan;
import com.hr.util.Pager;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrepAdd extends BaseAction {
    private List trcpList;
    private String trcpAddId;
    private String trcName;
    private String trcpTeacher;
    private String trcpLocation;
    private String comments;
    private String trpId;
    private Pager page;
    private int errorFlag;

    public TrepAdd() {
        this.page = new Pager();

        this.errorFlag = 0;
    }

    public String execute() {
        if (this.trcpAddId == null) {
            return "params_error";
        }
        Employee curEmp = getCurrentEmp();
        IEmployeeBo employeeBo = (IEmployeeBo) getBean("empBo");
        curEmp = employeeBo.loadEmp(curEmp.getId(), new String[] { Employee.PROP_EMP_PB_NO });

        ITrcourseplanBO trcourseplanBO = (ITrcourseplanBO) getBean("trcourseplanBO");
        Trcourseplan trpTrcp = trcourseplanBO.loadById(this.trcpAddId);

        if (trpTrcp == null) {
            if (this.errorFlag == 0) {
                this.errorFlag = 1;
                addActionError("对不起，该培训计划不存在！");
            } else {
                this.errorFlag = 0;
                clearErrorsAndMessages();
            }

            return "error";
        }

        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) BaseAction
                .getBean("tremployeeplanBO");
        if (tremployeeplanBO.hasApplied(curEmp.getId(), this.trcpAddId)) {
            return "repeatSubmit";
        }
        Date today = new Date();

        if ((trpTrcp.getTrcpEnrollStartDate() != null)
                && (trpTrcp.getTrcpEnrollStartDate().after(today))) {
            if (this.errorFlag == 0) {
                this.errorFlag = 1;
                addActionError("对不起，还未到报名时间！");
            } else {
                this.errorFlag = 0;
                clearErrorsAndMessages();
            }

            return "error";
        }
        if ((trpTrcp.getTrcpEnrollEndDate() != null)
                && (trpTrcp.getTrcpEnrollEndDate().before(today))) {
            if (this.errorFlag == 0) {
                this.errorFlag = 1;
                addActionError("对不起，报名时间已过");
            } else {
                this.errorFlag = 0;
                clearErrorsAndMessages();
            }

            return "error";
        }

        if ((trpTrcp.getTrcpDeptLimit() != null)
                && (trpTrcp.getTrcpDeptLimit().trim().length() > 0)) {
            IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
            if (trpTrcp.getTrcpDeptLimit().indexOf(
                                                   deptbo.loadDepartmentByNo(
                                                                             curEmp.getEmpDeptNo()
                                                                                     .getId(),
                                                                             new String[0])
                                                           .getDepartmentName()) < 0) {
                if (this.errorFlag == 0) {
                    this.errorFlag = 1;
                    addActionError("对不起，此培训不对您所在的部门开放！");
                } else {
                    this.errorFlag = 0;
                    clearErrorsAndMessages();
                }
                return "error";
            }
        }
        if ((trpTrcp.getTrcpAttendeeNo() != null)
                && (tremployeeplanBO.countAppliedAmount(this.trcpAddId) >= trpTrcp
                        .getTrcpAttendeeNo().intValue())) {
            if (this.errorFlag == 0) {
                this.errorFlag = 1;
                addActionError("对不起，申请人数已达上限，不能申请！");
            } else {
                this.errorFlag = 0;
                clearErrorsAndMessages();
            }
            return "error";
        }

        Tremployeeplan trep = new Tremployeeplan();
        trep.setTrpTraineeNo(curEmp);
        trep.setTrpTrcp(trpTrcp);
        trep.setTrpTraineeNo(curEmp);
        trep.setTrpTraineeDept(curEmp.getEmpDeptNo());

        if ((this.authorityCondition == "ALL") || (this.authorityCondition == "DEPT"))
            trep.setTrpStatus(new Integer(11));
        else
            trep.setTrpStatus(new Integer(2));
        trep.setTrpTraineeJobTitle(curEmp.getEmpPbNo().getPbJobTitle());
        trep.setTrpCreateBy(curEmp);
        trep.setTrpLastChangeBy(curEmp);
        tremployeeplanBO.save(trep);
        addActionMessage("申请培训成功！");
        try {
            ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
            logBO.addToSyslog("tremployeeplan", getCurrentEmpNo(), getCurrentEmpNo(), trep
                    .getTrpId().toString(), 0, "提交申请", this.comments);
            IEmailsendBO emailsendBo = (IEmailsendBO) getBean("emailsendBO");

            Map paramUserEmail = new HashMap();
            paramUserEmail.put("SENDER", getCurrentEmp());
            paramUserEmail.put("URL", "training/trepDeptApprove.actionn");
            emailsendBo.sendEmailToDept(null, "TREPSubmit", getCurrentEmpNo(), paramUserEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.comments = null;
        return "success";
    }

    public String trepAddInit() {
        ITrcourseplanBO trcourseplanBO = (ITrcourseplanBO) BaseAction.getBean("trcourseplanBO");
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");

        Employee trainee = empBo.loadEmp(getCurrentEmpNo(), null);
        this.trcpList = trcourseplanBO.getEnrollableTrcp(trainee, this.trcName, this.trcpTeacher,
                                                         this.trcpLocation, this.page);

        return "success";
    }

    public String reSubmit() {
        Employee curEmp = getCurrentEmp();
        if (this.trpId == null) {
            return "params_error";
        }

        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) BaseAction
                .getBean("tremployeeplanBO");
        Tremployeeplan trep = tremployeeplanBO.loadById(this.trpId.toString());
        if (trep == null) {
            addActionError("该培训计划不存在！");
            return "error";
        }
        if (trep.getTrpStatus().intValue() != 21) {
            addActionError("只有状态为已拒绝时才能重新提交！");
            return "error";
        }
        Date today = new Date();

        if ((trep.getTrpTrcp().getTrcpEnrollStartDate() != null)
                && (trep.getTrpTrcp().getTrcpEnrollStartDate().after(today))) {
            addActionError("对不起，还未到报名时间！");
            return "error";
        }
        if ((trep.getTrpTrcp().getTrcpEnrollEndDate() != null)
                && (trep.getTrpTrcp().getTrcpEnrollEndDate().before(today))) {
            addActionError("对不起，报名时间已过期");
            return "error";
        }
        label236:
        if ((trep.getTrpTrcp().getTrcpDeptLimit() != null)
                && (trep.getTrpTrcp().getTrcpDeptLimit().trim().length() > 0)) {
            String[] depts = trep.getTrpTrcp().getTrcpDeptLimit().split(", ");
            for (int i = 0; i < depts.length; ++i) {
                if (curEmp.getEmpDeptNo().getId().equals(depts[i]))
                    break label236;
            }
            addActionError("对不起，此培训不对您所在的部门开放！");
            return "error";
        }
        if ((trep.getTrpTrcp().getTrcpAttendeeNo() != null)
                && (tremployeeplanBO.countAppliedAmount(trep.getTrpTrcp().getTrcpId()) >= trep
                        .getTrpTrcp().getTrcpAttendeeNo().intValue())) {
             addActionError("对不起，申请人数已达上限，不能申请！");
            return "error";
        }

        trep.setTrpTraineeDept(curEmp.getEmpDeptNo());

        if ((this.authorityCondition == "ALL") || (this.authorityCondition == "DEPT"))
            trep.setTrpStatus(new Integer(11));
        else {
            trep.setTrpStatus(new Integer(2));
        }
        trep.setTrpLastChangeBy(curEmp);
        tremployeeplanBO.saveOrupdate(trep);

        addActionMessage("重新提交培训申请成功！");
        ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
        logBO.addToSyslog("tremployeeplan", getCurrentEmpNo(), getCurrentEmpNo(), trep.getTrpId()
                .toString(), 0, "重新提交", this.comments);
        this.comments = null;
        return "success";
    }

    public String deleteTrep() {
        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) BaseAction
                .getBean("tremployeeplanBO");
        if (this.trpId == null) {
            return "params_error";
        }
        Tremployeeplan trepLoad = tremployeeplanBO.loadById(this.trpId.toString());
        if (trepLoad == null) {
            addActionError("对不起，要删除的培训计划不存在！");
            return "error";
        }

        if (!trepLoad.getTrpTraineeNo().getId().equals(getCurrentEmpNo())) {
            return "noauth";
        }
        tremployeeplanBO.delete(this.trpId);
        addActionMessage("删除培训计划成功！");
        return "success";
    }

    public List getTrcpList() {
        return this.trcpList;
    }

    public void setTrcpList(List trcpList) {
        this.trcpList = trcpList;
    }

    public String getTrcpAddId() {
        return this.trcpAddId;
    }

    public void setTrcpAddId(String trcpAddId) {
        this.trcpAddId = trcpAddId;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
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

    public String getTrcpLocation() {
        return this.trcpLocation;
    }

    public void setTrcpLocation(String trcpLocation) {
        this.trcpLocation = trcpLocation;
    }

    public String getTrpId() {
        return this.trpId;
    }

    public void setTrpId(String trpId) {
        this.trpId = trpId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.action.TrepAdd JD-Core Version: 0.5.4
 */