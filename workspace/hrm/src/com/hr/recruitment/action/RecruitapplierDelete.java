package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.recruitment.bo.IRecruitapplierBo;
import com.hr.recruitment.domain.Recruitapplier;
import java.util.List;

public class RecruitapplierDelete extends BaseAction {
    private String applierId;

    public String execute() throws Exception {
        IRecruitapplierBo recruitapplierBo = (IRecruitapplierBo) getBean("applierBo");
        Recruitapplier tempRa = recruitapplierBo.loadApplier(this.applierId, null);
        if (recruitapplierBo.deleteApplier(this.applierId).size() < 1) {
            FileOperate.deleteFile("sys.recruitment.applier.path", tempRa.getRecaAttachmentName());
            addSuccessInfo("删除应聘者" + tempRa.getRecaName() + "成功。");
            tempRa = null;
            return "success";
        }

        tempRa = null;
        return "error";
    }

    public String dwrDelte(String id) {
        IRecruitapplierBo recruitapplierBo = (IRecruitapplierBo) getBean("applierBo");
        Recruitapplier tempRa = recruitapplierBo.loadApplier(id, null);
        String recaName = tempRa.getRecaName();
        if (recruitapplierBo.deleteApplier(id).size() < 1) {
            FileOperate.deleteFile("sys.recruitment.applier.path", tempRa.getRecaAttachmentName());
            tempRa = null;
            return "删除应聘者(" + recaName + ")成功。";
        }

        tempRa = null;
        return "删除应聘者(" + recaName + ")失败。";
    }

    public String getApplierId() {
        return this.applierId;
    }

    public void setApplierId(String applierId) {
        this.applierId = applierId;
    }
}