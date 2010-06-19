package com.hr.compensation.domain.base;

import com.hr.base.BaseDomain;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseEmpsalaryacct extends BaseDomain implements Serializable {
    public static String REF = "Empsalaryacct";
    public static String PROP_ESAC_DESC = "esacDesc";
    public static String PROP_ESAC_NAME = "esacName";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String esacName;
    private String esacDesc;
    private Set<Empsalaryacctitems> empsalaryacctitems;
    private Set<Empsalaryacctversion> empsalaryacctversion;

    public BaseEmpsalaryacct() {
        initialize();
    }

    public BaseEmpsalaryacct(String id) {
        setId(id);
        initialize();
    }

    public BaseEmpsalaryacct(String id, String esacName) {
        setId(id);
        setEsacName(esacName);
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

    public String getEsacName() {
        return this.esacName;
    }

    public void setEsacName(String esacName) {
        this.esacName = esacName;
    }

    public String getEsacDesc() {
        return this.esacDesc;
    }

    public void setEsacDesc(String esacDesc) {
        this.esacDesc = esacDesc;
    }

    public Set<Empsalaryacctitems> getEmpsalaryacctitems() {
        return this.empsalaryacctitems;
    }

    public void setEmpsalaryacctitems(Set<Empsalaryacctitems> empsalaryacctitems) {
        this.empsalaryacctitems = empsalaryacctitems;
    }

    public void addToempsalaryacctitems(Empsalaryacctitems empsalaryacctitems) {
        if (null == getEmpsalaryacctitems())
            setEmpsalaryacctitems(new TreeSet());
        getEmpsalaryacctitems().add(empsalaryacctitems);
    }

    public Set<Empsalaryacctversion> getEmpsalaryacctversion() {
        return this.empsalaryacctversion;
    }

    public void setEmpsalaryacctversion(Set<Empsalaryacctversion> empsalaryacctversion) {
        this.empsalaryacctversion = empsalaryacctversion;
    }

    public void addToempsalaryacctversion(Empsalaryacctversion empsalaryacctversion) {
        if (null == getEmpsalaryacctversion())
            setEmpsalaryacctversion(new TreeSet());
        getEmpsalaryacctversion().add(empsalaryacctversion);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Empsalaryacct))
            return false;

        Empsalaryacct empsalaryacct = (Empsalaryacct) obj;
        if ((null == getId()) || (null == empsalaryacct.getId()))
            return false;
        return getId().equals(empsalaryacct.getId());
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
 * com.hr.compensation.domain.base.BaseEmpsalaryacct JD-Core Version: 0.5.4
 */