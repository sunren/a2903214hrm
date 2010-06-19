package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.recruitment.bo.IRecruitapplierBo;
import com.hr.recruitment.bo.IRecruitchannelBo;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitchannel;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.util.Pager;
import java.io.File;
import java.util.List;

public class RecruitapplierCreateDetail extends BaseAction {
    private List RecruitchannelList;
    private List RecruitplanList;
    private List applierStatus;
    private Recruitapplier applier;
    private Recruitplan plan;
    private List errors;
    File file;
    String fileContentType;
    String fileFileName;
    private String planID;
    private Pager page;

    public RecruitapplierCreateDetail() {
        this.page = new Pager();
    }

    public String execute() throws Exception {
        IRecruitchannelBo recruitchannelBO = (IRecruitchannelBo) getBean("channelBo");
        IRecruitplanBo recruitplanBO = (IRecruitplanBo) getBean("recruitplanBO");
        IRecruitapplierBo applierBo = (IRecruitapplierBo) getBean("applierBo");
        ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
        this.RecruitplanList = recruitplanBO.getApprovedPlanList();
        this.RecruitchannelList = recruitchannelBO.getObjects(Recruitchannel.class, null);
        this.applierStatus = applierBo.getApplierStatus();
        upload();
        if (this.plan != null) {
            this.planID = this.plan.getId();
            this.applier.setRecaPlan(recruitplanBO.loadRecruitplan(this.planID, null));
        }
        try {
            if ((this.applier.getRecaInterviewer() == null)
                    || (this.applier.getRecaInterviewer().trim().length() < 1)) {
                this.applier.setRecaInterviewer(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.errors = applierBo.insertApplier(this.applier, getCurrentEmpNo());
        try {
            logBO.addToSyslog("recruitapplier", getCurrentEmpNo(), getCurrentEmpNo(), this.applier
                    .getId().toString(), 0, "新增应聘耄1�7", this.applier.getRecaComment());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.errors.size() < 1) {
            addSuccessInfo("应聘耄1�7" + this.applier.getRecaName() + "添加成功〄1�7");
            this.applier = new Recruitapplier();
            return "success";
        }
        String error = (String) this.errors.get(0);
        addErrorInfo(error);
        return "input";
    }

    public Recruitapplier getApplier() {
        return this.applier;
    }

    public void setApplier(Recruitapplier applier) {
        this.applier = applier;
    }

    public void upload() throws Exception {
        if (this.file == null) {
            return;
        }
        String type = this.fileFileName.substring(this.fileFileName.lastIndexOf("."));
        String newFileName = FileOperate.buildFileName() + type;
        FileOperate.buildFile(this.file, "sys.recruitment.applier.path", newFileName,
                              "sys.recruitment.applier.type", "sys.recruitment.applier.length");
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileFileName() {
        return this.fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public List getRecruitchannelList() {
        return this.RecruitchannelList;
    }

    public void setRecruitchannelList(List recruitchannelList) {
        this.RecruitchannelList = recruitchannelList;
    }

    public List getRecruitplanList() {
        return this.RecruitplanList;
    }

    public void setRecruitplanList(List recruitplanList) {
        this.RecruitplanList = recruitplanList;
    }

    public List getApplierStatus() {
        return this.applierStatus;
    }

    public void setApplierStatus(List applierStatus) {
        this.applierStatus = applierStatus;
    }

    public Recruitplan getPlan() {
        return this.plan;
    }

    public void setPlan(Recruitplan plan) {
        this.plan = plan;
    }

    public String getPlanID() {
        return this.planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.action.RecruitapplierCreateDetail JD-Core Version: 0.5.4
 */