package com.hr.security.domain;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public class Modulecondition extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    private String moduleconditionName;
    private String moduleconditionModuleNo;
    private String moduleconditionDesc;

    public String getModuleconditionDesc() {
        return this.moduleconditionDesc;
    }

    public void setModuleconditionDesc(String moduleconditionDesc) {
        this.moduleconditionDesc = moduleconditionDesc;
    }

    public String getModuleconditionModuleNo() {
        return this.moduleconditionModuleNo;
    }

    public void setModuleconditionModuleNo(String moduleconditionModuleNo) {
        this.moduleconditionModuleNo = moduleconditionModuleNo;
    }

    public String getModuleconditionName() {
        return this.moduleconditionName;
    }

    public void setModuleconditionName(String moduleconditionName) {
        this.moduleconditionName = moduleconditionName;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Modulecondition))
            return false;

        Modulecondition mObj = (Modulecondition) obj;
        if ((null != getModuleconditionModuleNo()) && (null != mObj.getModuleconditionModuleNo())
                && (null != getModuleconditionName()) && (null != mObj.getModuleconditionName())) {
            if ((!getModuleconditionModuleNo().equals(mObj.getModuleconditionModuleNo()))
                    || (!getModuleconditionName().equals(mObj.getModuleconditionName()))) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.domain.Modulecondition JD-Core Version: 0.5.4
 */