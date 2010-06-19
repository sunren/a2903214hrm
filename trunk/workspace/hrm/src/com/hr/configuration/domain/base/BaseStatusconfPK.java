package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.StatusconfPK;
import java.io.Serializable;

public abstract class BaseStatusconfPK extends BaseDomain implements Serializable {
    protected int hashCode = -2147483648;
    private Integer statusconfNo;
    private String statusconfTablename;

    public BaseStatusconfPK() {
    }

    public BaseStatusconfPK(Integer statusconfNo, String statusconfTablename) {
        setStatusconfNo(statusconfNo);
        setStatusconfTablename(statusconfTablename);
    }

    public Integer getStatusconfNo() {
        return this.statusconfNo;
    }

    public void setStatusconfNo(Integer statusconfNo) {
        this.statusconfNo = statusconfNo;
    }

    public String getStatusconfTablename() {
        return this.statusconfTablename;
    }

    public void setStatusconfTablename(String statusconfTablename) {
        this.statusconfTablename = statusconfTablename;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof StatusconfPK))
            return false;

        StatusconfPK mObj = (StatusconfPK) obj;
        if ((null != getStatusconfNo()) && (null != mObj.getStatusconfNo())) {
            if (!getStatusconfNo().equals(mObj.getStatusconfNo())) {
                return false;
            }
        } else {
            return false;
        }
        if ((null != getStatusconfTablename()) && (null != mObj.getStatusconfTablename())) {
            if (!getStatusconfTablename().equals(mObj.getStatusconfTablename())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            StringBuilder sb = new StringBuilder();
            if (null != getStatusconfNo()) {
                sb.append(getStatusconfNo().hashCode());
                sb.append(":");
            } else {
                return super.hashCode();
            }
            if (null != getStatusconfTablename()) {
                sb.append(getStatusconfTablename().hashCode());
                sb.append(":");
            } else {
                return super.hashCode();
            }
            this.hashCode = sb.toString().hashCode();
        }
        return this.hashCode;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseStatusconfPK JD-Core Version: 0.5.4
 */