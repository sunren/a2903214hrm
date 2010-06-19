package com.hr.information.action;

import com.hr.base.DWRUtil;
import com.hr.information.bo.IInformationBo;
import com.hr.information.domain.Information;
import com.hr.spring.SpringBeanFactory;
import java.util.Date;

public class InfoAction {
    private static final long serialVersionUID = -4234940898871087425L;
    protected static final String SUCC = "SUCC";
    protected static final String FAIL = "FAIL";
    protected static final String NOAUTH = "NOAUTH";
    private IInformationBo infoBo;

    private void setIInformationBo() {
        this.infoBo = ((IInformationBo) SpringBeanFactory.getBean("informationBo"));
    }

    public String addInformation(Information info) {
        String flt = DWRUtil.checkAuth("searchInfo", "execute");
        if ("error".equalsIgnoreCase(flt))
            return "NOAUTH";
        setIInformationBo();
        info.setInfoContent(info.getInfoContent().replace("/n/n", "<br><br>"));
        if (this.infoBo.saveObject(info)) {
            return String.valueOf(info.getId());
        }
        return "FAIL";
    }

    public String delInformation(String infoId) {
        String flt = DWRUtil.checkAuth("searchInfo", "execute");
        if ("error".equalsIgnoreCase(flt))
            return "NOAUTH";
        setIInformationBo();
        Information info = this.infoBo.loadOne(infoId);
        if (info != null) {
            return (this.infoBo.deleteInfo(info)) ? "SUCC" : "related";
        }
        return null;
    }

    public String updateInformation(Information info) {
        String flt = DWRUtil.checkAuth("searchInfo", "execute");
        if ("error".equalsIgnoreCase(flt))
            return "NOAUTH";
        setIInformationBo();
        Information tmpInfo = this.infoBo.loadOne(info.getId());
        if (tmpInfo != null) {
            tmpInfo.setInfoTitle(info.getInfoTitle());
            tmpInfo.setInfoBreif(info.getInfoBreif());
            tmpInfo.setInfoContent(info.getInfoContent());
            tmpInfo.setInfoStatus(info.getInfoStatus());
            tmpInfo.setInfoLastChangeTime(new Date());
            if (this.infoBo.updateInfo(tmpInfo)) {
                return "SUCC";
            }
        }
        return "FAIL";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.information.action.InfoAction JD-Core Version: 0.5.4
 */