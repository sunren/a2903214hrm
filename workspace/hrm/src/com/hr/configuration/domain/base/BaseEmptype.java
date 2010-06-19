package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Emptype;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Set;

public abstract class BaseEmptype extends BaseDomain implements Serializable {
    public static String REF = "Emptype";
    public static String PROP_EMPTYPE_NAME = "emptypeName";
    public static String PROP_EMPTYPE_DESC = "emptypeDesc";
    public static String PROP_ID = "id";
    public static String PROP_EMPTYPE_SORTID = "emptypeSortId";
    public static String PROP_EMPTYPE_STATUS = "emptypeStatus";

    private int hashCode = -2147483648;
    private String id;
    private String emptypeName;
    private String emptypeDesc;
    private Integer emptypeSortId;
    private Integer emptypeStatus;
    private Set<Empsalaryconfig> empsalaryconfs;
    private Set<Empsalarypay> empsalarypaids;
    private Set<Employee> employees;

    public BaseEmptype() {
        initialize();
    }

    public BaseEmptype(String id) {
        setId(id);
        initialize();
    }

    public BaseEmptype(String id, String emptypeName, Integer emptypeSortId) {
        setId(id);
        setEmptypeName(emptypeName);
        setEmptypeSortId(emptypeSortId);
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

    public String getEmptypeName() {
        return this.emptypeName;
    }

    public void setEmptypeName(String emptypeName) {
        this.emptypeName = emptypeName;
    }

    public String getEmptypeDesc() {
        return this.emptypeDesc;
    }

    public void setEmptypeDesc(String emptypeDesc) {
        this.emptypeDesc = emptypeDesc;
    }

    public Set<Empsalaryconfig> getEmpsalaryconfs() {
        return this.empsalaryconfs;
    }

    public void setEmpsalaryconfs(Set<Empsalaryconfig> empsalaryconfs) {
        this.empsalaryconfs = empsalaryconfs;
    }

    public Set<Empsalarypay> getEmpsalarypaids() {
        return this.empsalarypaids;
    }

    public void setEmpsalarypaids(Set<Empsalarypay> empsalarypaids) {
        this.empsalarypaids = empsalarypaids;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Integer getEmptypeSortId() {
        return this.emptypeSortId;
    }

    public void setEmptypeSortId(Integer emptypeSortId2) {
        this.emptypeSortId = emptypeSortId2;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Emptype))
            return false;

        Emptype emptype = (Emptype) obj;
        if ((null == getId()) || (null == emptype.getId()))
            return false;
        return getId().equals(emptype.getId());
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

    public Integer getEmptypeStatus() {
        return this.emptypeStatus;
    }

    public void setEmptypeStatus(Integer emptypeStatus) {
        this.emptypeStatus = emptypeStatus;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseEmptype JD-Core Version: 0.5.4
 */