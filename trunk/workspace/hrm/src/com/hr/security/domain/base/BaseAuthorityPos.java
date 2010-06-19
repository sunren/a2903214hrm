package com.hr.security.domain.base;

import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Position;
import com.hr.security.domain.AuthorityPos;
import java.io.Serializable;

public abstract class BaseAuthorityPos implements Serializable {
    public static String REF = "AuthorityPos";
    public static String PROP_AP_AUTH_VALIDATE = "apAuthValidate";
    public static String PROP_AP_DEPT = "apDept";
    public static String PROP_AP_POS = "apPos";
    public static String PROP_AP_MODULE = "apModule";
    public static String PROP_ID = "id";
    public static String PROP_AP_LOC = "apLoc";

    private int hashCode = -2147483648;
    private String id;
    private String apModule;
    private String apAuthValidate;
    private Department apDept;
    private Position apPos;
    private Location apLoc;

    public BaseAuthorityPos() {
        initialize();
    }

    public BaseAuthorityPos(String id) {
        setId(id);
        initialize();
    }

    public BaseAuthorityPos(String id, Position apPos, String apModule, String apAuthValidate) {
        setId(id);
        setApPos(apPos);
        setApModule(apModule);
        setApAuthValidate(apAuthValidate);
        initialize();
    }

    protected void initialize() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getApModule() {
        return this.apModule;
    }

    public void setApModule(String apModule) {
        this.apModule = apModule;
    }

    public String getApAuthValidate() {
        return this.apAuthValidate;
    }

    public void setApAuthValidate(String apAuthValidate) {
        this.apAuthValidate = apAuthValidate;
    }

    public Department getApDept() {
        return this.apDept;
    }

    public void setApDept(Department apDept) {
        this.apDept = apDept;
    }

    public Position getApPos() {
        return this.apPos;
    }

    public void setApPos(Position apPos) {
        this.apPos = apPos;
    }

    public Location getApLoc() {
        return this.apLoc;
    }

    public void setApLoc(Location apLoc) {
        this.apLoc = apLoc;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof AuthorityPos))
            return false;

        AuthorityPos authorityPos = (AuthorityPos) obj;
        if ((null == getId()) || (null == authorityPos.getId()))
            return false;
        return getId().equals(authorityPos.getId());
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
 * com.hr.security.domain.base.BaseAuthorityPos JD-Core Version: 0.5.4
 */