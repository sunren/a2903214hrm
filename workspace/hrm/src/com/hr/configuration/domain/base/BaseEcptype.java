package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.configuration.domain.Ecptype;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseEcptype extends BaseDomain implements Serializable {
    public static String REF = "Ecptype";
    public static String PROP_ECPTYPE_DESC = "ecptypeDesc";
    public static String PROP_ID = "id";
    public static String PROP_ECPTYPE_NAME = "ecptypeName";
    public static String PROP_ECPTYPE_SORT_ID = "ecptypeSortId";

    private int hashCode = -2147483648;
    private String id;
    private String ecptypeName;
    private String ecptypeDesc;
    private Integer ecptypeSortId;
    private Set<Empsalaryadj> empcompaplans;

    public BaseEcptype() {
        initialize();
    }

    public BaseEcptype(String id) {
        setId(id);
        initialize();
    }

    public BaseEcptype(String id, String ecptypeName) {
        setId(id);
        setEcptypeName(ecptypeName);
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

    public String getEcptypeName() {
        return this.ecptypeName;
    }

    public void setEcptypeName(String ecptypeName) {
        this.ecptypeName = ecptypeName;
    }

    public String getEcptypeDesc() {
        return this.ecptypeDesc;
    }

    public void setEcptypeDesc(String ecptypeDesc) {
        this.ecptypeDesc = ecptypeDesc;
    }

    public Set<Empsalaryadj> getEmpcompaplans() {
        return this.empcompaplans;
    }

    public void setEmpcompaplans(Set<Empsalaryadj> empcompaplans) {
        this.empcompaplans = empcompaplans;
    }

    public void addToempcompaplans(Empsalaryadj empcompaplan) {
        if (null == getEmpcompaplans())
            setEmpcompaplans(new TreeSet());
        getEmpcompaplans().add(empcompaplan);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Ecptype))
            return false;

        Ecptype ecptype = (Ecptype) obj;
        if ((null == getId()) || (null == ecptype.getId()))
            return false;
        return getId().equals(ecptype.getId());
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

    public Integer getEcptypeSortId() {
        return this.ecptypeSortId;
    }

    public void setEcptypeSortId(Integer ecptypeSortId) {
        this.ecptypeSortId = ecptypeSortId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseEcptype JD-Core Version: 0.5.4
 */