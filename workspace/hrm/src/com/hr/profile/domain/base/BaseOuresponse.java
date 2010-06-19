package com.hr.profile.domain.base;

import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Ouresponse;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;

public abstract class BaseOuresponse implements Serializable {
    public static String REF = "Ouresponse";
    public static String PROP_OUR_RATE = "ourRate";
    public static String PROP_OUR_DEPT = "ourDept";
    public static String PROP_OUR_DESC = "ourDesc";
    public static String PROP_OUR_NAME = "ourName";
    public static String PROP_OUR_PB = "ourPb";
    public static String PROP_OUR_SORT_ID = "ourSortId";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String ourName;
    private String ourDesc;
    private Integer ourRate;
    private Integer ourSortId;
    private PositionBase ourPb;
    private Department ourDept;

    public BaseOuresponse() {
        initialize();
    }

    public BaseOuresponse(String id) {
        setId(id);
        initialize();
    }

    public BaseOuresponse(String id, String ourName, Integer ourSortId) {
        setId(id);
        setOurName(ourName);
        setOurSortId(ourSortId);
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

    public String getOurName() {
        return this.ourName;
    }

    public void setOurName(String ourName) {
        this.ourName = ourName;
    }

    public String getOurDesc() {
        return this.ourDesc;
    }

    public void setOurDesc(String ourDesc) {
        this.ourDesc = ourDesc;
    }

    public Integer getOurRate() {
        return this.ourRate;
    }

    public void setOurRate(Integer ourRate) {
        this.ourRate = ourRate;
    }

    public Integer getOurSortId() {
        return this.ourSortId;
    }

    public void setOurSortId(Integer ourSortId) {
        this.ourSortId = ourSortId;
    }

    public PositionBase getOurPb() {
        return this.ourPb;
    }

    public void setOurPb(PositionBase ourPb) {
        this.ourPb = ourPb;
    }

    public Department getOurDept() {
        return this.ourDept;
    }

    public void setOurDept(Department ourDept) {
        this.ourDept = ourDept;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Ouresponse))
            return false;

        Ouresponse ouresponse = (Ouresponse) obj;
        if ((null == getId()) || (null == ouresponse.getId()))
            return false;
        return getId().equals(ouresponse.getId());
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
 * com.hr.profile.domain.base.BaseOuresponse JD-Core Version: 0.5.4
 */