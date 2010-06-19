package com.hr.base;

import java.io.Serializable;
import java.util.HashSet;

public class UsersAuthority implements Serializable {
    private static final long serialVersionUID = -9030895684799083660L;
    private String moduleNo;
    private String conditionNo;
    private String filter;

    public UsersAuthority(String inputModuleNo) {
        this.moduleNo = inputModuleNo;
        this.conditionNo = null;
        this.filter = null;
    }

    public UsersAuthority(String inputModuleNo, String inputConditionNo) {
        this.moduleNo = inputModuleNo;
        this.conditionNo = inputConditionNo;
        this.filter = null;
    }

    public UsersAuthority(String inputModuleNo, String inputConditionNo, String inputFilter) {
        this.moduleNo = inputModuleNo;
        this.conditionNo = inputConditionNo;
        this.filter = inputFilter;
    }

    public String getModuleNo() {
        return this.moduleNo;
    }

    public void setModuleNo(String moduleNo) {
        this.moduleNo = moduleNo;
    }

    public String getConditionNo() {
        return this.conditionNo;
    }

    public void setConditionNo(String conditionNo) {
        this.conditionNo = conditionNo;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + ((this.moduleNo == null) ? 0 : this.moduleNo.hashCode());

        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (super.getClass() != obj.getClass())
            return false;
        UsersAuthority other = (UsersAuthority) obj;
        if (this.moduleNo == null)
            if (other.moduleNo != null)
                return false;
            else if (!this.moduleNo.equals(other.moduleNo))
                return false;
        if (this.conditionNo == null) {
            return true;
        }
        return this.conditionNo.equals(other.conditionNo);
    }

    public static void main(String[] args) {
        HashSet set = new HashSet();
        set.add(new UsersAuthority("411", "AA", null));
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.base.UsersAuthority JD-Core Version: 0.5.4
 */