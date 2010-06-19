package com.hr.security.domain;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public class Authority extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Authority";
    public static String PROP_AUTHORITY_DESC = "authorityDesc";
    public static String PROP_AUTHORITY_MODULE_NO = "authorityModuleNo";
    public static String PROP_AUTHORITY_ACTION_NO = "authorityActionNo";
    public static String PROP_ID = "id";
    public static String PROP_AUTHORITY_CONDITION_NO = "authorityConditionNo";

    private int hashCode = -2147483648;
    private Integer id;
    private String authorityModuleNo;
    private String authorityActionNo;
    private Integer authorityConditionNo;
    private String authorityDesc;

    public Authority() {
        initialize();
    }

    public Authority(Integer id) {
        setId(id);
        initialize();
    }

    public Authority(Integer id, String authorityModuleNo, String authorityActionNo,
            Integer authorityConditionNo) {
        setId(id);
        setAuthorityModuleNo(authorityModuleNo);
        setAuthorityActionNo(authorityActionNo);
        setAuthorityConditionNo(authorityConditionNo);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getAuthorityModuleNo() {
        return this.authorityModuleNo;
    }

    public void setAuthorityModuleNo(String authorityModuleNo) {
        this.authorityModuleNo = authorityModuleNo;
    }

    public String getAuthorityActionNo() {
        return this.authorityActionNo;
    }

    public void setAuthorityActionNo(String authorityActionNo) {
        this.authorityActionNo = authorityActionNo;
    }

    public Integer getAuthorityConditionNo() {
        return this.authorityConditionNo;
    }

    public void setAuthorityConditionNo(Integer authorityConditionNo) {
        this.authorityConditionNo = authorityConditionNo;
    }

    public String getAuthorityDesc() {
        return this.authorityDesc;
    }

    public void setAuthorityDesc(String authorityDesc) {
        this.authorityDesc = authorityDesc;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Authority))
            return false;

        Authority authority = (Authority) obj;
        if ((null == getId()) || (null == authority.getId()))
            return false;
        return getId().equals(authority.getId());
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.domain.Authority JD-Core Version: 0.5.4
 */