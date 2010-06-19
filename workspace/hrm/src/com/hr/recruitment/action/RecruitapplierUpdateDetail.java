package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.base.Status;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.bo.IRecruitapplierBo;
import com.hr.recruitment.bo.IRecruitchannelBo;
import com.hr.recruitment.bo.IRecruitplanBo;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitchannel;
import com.hr.recruitment.domain.Recruitplan;
import java.io.File;
import java.util.List;

public class RecruitapplierUpdateDetail extends BaseAction implements Status {
    private String id;
    private List RecruitchannelList;
    private List RecruitplanList;
    private Recruitapplier applier;
    private List applierStatus;
    private List error;
    File file;
    String fileContentType;
    String fileFileName;

    public String execute() throws Exception {
        IRecruitapplierBo applierBO = (IRecruitapplierBo) getBean("applierBo");
        IRecruitchannelBo recruitchannelBO = (IRecruitchannelBo) getBean("channelBo");
        IRecruitplanBo recruitplanBO = (IRecruitplanBo) getBean("recruitplanBO");
        ISysLogBO logBO = (ISysLogBO) BaseAction.getBean("logBO");
        this.RecruitplanList = recruitplanBO.getObjects(Recruitplan.class, null);
        this.RecruitchannelList = recruitchannelBO.getObjects(Recruitchannel.class, null);
        this.applierStatus = applierBO.getApplierStatus();

        int temp_RAStatus = this.applier.getRecaStatus().intValue();
        if ((temp_RAStatus != 2) && (temp_RAStatus != 3) && (temp_RAStatus != 4)) {
            this.applier.setRecaInterviewTime(null);
        }
        this.error = applierBO.updateApplier(this.applier, getCurrentEmpNo());
        try {
            logBO.addToSyslog("recruitapplier", getCurrentEmpNo(), this.applier.getRecaCreateBy()
                    .getId(), this.applier.getId().toString(), 0, "更新信息", this.applier
                    .getRecaComment());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.error.size() < 1) {
            this.id = this.applier.getRecaPlan().getId();
            addSuccessInfo("更新应聘耄1�7" + this.applier.getRecaName() + "成功〄1�7");
            this.applier = null;
            return "success";
        }
        addErrorInfo(this.error);
        return "input";
    }

    public void upload(String fileFileName) throws Exception {
        if (("".equals(fileFileName)) || (fileFileName == null)) {
            return;
        }

        String type = fileFileName.substring(fileFileName.lastIndexOf("."));
        String newFileName = FileOperate.buildFileName() + type;
        String result = FileOperate.buildFile(this.file, "sys.recruitment.applier.path",
                                              newFileName, "sys.recruitment.applier.type",
                                              "sys.recruitment.applier.length");

        FileOperate
                .deleteFile("sys.recruitment.applier.path", this.applier.getRecaAttachmentName());

        if ("success".equals(result))
            this.applier.setRecaAttachmentName(newFileName);
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

    public List getError() {
        return this.error;
    }

    public void setError(List error) {
        this.error = error;
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

    public Recruitapplier getApplier() {
        return this.applier;
    }

    public void setApplier(Recruitapplier applier) {
        this.applier = applier;
    }

    public List getApplierStatus() {
        return this.applierStatus;
    }

    public void setApplierStatus(List applierStatus) {
        this.applierStatus = applierStatus;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.recruitment.action.RecruitapplierUpdateDetail JD-Core Version: 0.5.4
 */