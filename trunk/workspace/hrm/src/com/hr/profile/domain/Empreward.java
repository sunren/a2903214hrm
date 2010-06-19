package com.hr.profile.domain;

import com.hr.base.ComonBeans;
import com.hr.profile.domain.base.BaseEmpreward;

public class Empreward extends BaseEmpreward {
    private static final long serialVersionUID = 1L;
    private String erTypeMean;

    public Empreward() {
    }

    public Empreward(String erId) {
        super(erId);
    }

    public void setErType(String erType) {
        super.setErType(erType);
        this.erTypeMean = ComonBeans.getParameterValue(PROP_ER_TYPE, new String[] { erType });

        if (this.erTypeMean == null)
            this.erTypeMean = erType;
    }

    public String getErTypeMean() {
        return this.erTypeMean;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.Empreward JD-Core Version: 0.5.4
 */