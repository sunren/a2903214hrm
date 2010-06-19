package com.hr.io.action;

import com.hr.base.BaseDownloadAction;
import com.hr.io.bo.IInmatchModelBO;
import com.hr.io.domain.InmatchModel;
import com.hr.io.domain.Iodef;

public class DownloadModel extends BaseDownloadAction {
    private static final long serialVersionUID = 1L;
    private String immId;

    public String execute() throws Exception {
        if ((this.immId == null) || (this.immId.length() < 1)) {
            return "error";
        }
        IInmatchModelBO inmatchModelBO = (IInmatchModelBO) getBean("inmatchModelBO");
        InmatchModel inmatchModel = inmatchModelBO
                .loadObject(this.immId, new String[] { InmatchModel.PROP_IMM_IO });
        Iodef iodef = inmatchModel.getImmIo();

        if ((inmatchModel.getImmInputType().equals("excel")) && (excelModelDownload(inmatchModel))) {
            return "download";
        }

        return "error";
    }

    public String getImmId() {
        return this.immId;
    }

    public void setImmId(String immId) {
        this.immId = immId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.action.DownloadModel JD-Core Version: 0.5.4
 */