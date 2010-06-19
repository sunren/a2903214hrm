package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Statusconf;
import com.hr.configuration.domain.StatusconfPK;
import java.io.Serializable;

public abstract class BaseStatusconf extends BaseDomain implements Serializable {
    public static String REF = "Statusconf";
    public static String PROP_ID = "id";
    public static String PROP_STATUSCONF_DESC = "statusconfDesc";

    private int hashCode = -2147483648;
    private StatusconfPK id;
    private String statusconfDesc;

    public BaseStatusconf() {
        initialize();
    }

    public BaseStatusconf(StatusconfPK id) {
        setId(id);
        initialize();
    }

    protected void initialize() {
    }

    public StatusconfPK getId() {
        return this.id;
    }

    public void setId(StatusconfPK id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getStatusconfDesc() {
        return this.statusconfDesc;
    }

    public void setStatusconfDesc(String statusconfDesc) {
        this.statusconfDesc = statusconfDesc;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Statusconf))
            return false;

        Statusconf statusconf = (Statusconf) obj;
        if ((null == getId()) || (null == statusconf.getId()))
            return false;
        return getId().equals(statusconf.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseStatusconf JD-Core Version: 0.5.4
 */