package com.hr.help.domain.base;

import com.hr.base.BaseDomain;
import com.hr.help.domain.Help;
import com.hr.help.domain.Helpclass;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseHelpclass extends BaseDomain implements Serializable {
    public static String REF = "Helpclass";
    public static String PROP_HC_CLASS_NAME = "hcClassName";
    public static String PROP_HC_BRIEF = "hcBrief";
    public static String PROP_HC_FATHER = "hcFather";
    public static String PROP_ID = "id";
    public static String PROP_HC_SORT_ID = "hcSortId";

    private int hashCode = -2147483648;
    private String id;
    private String hcBrief;
    private String hcClassName;
    private Integer hcSortId;
    private Helpclass hcFather;
    private Set<Helpclass> helpclass;
    private Set<Help> helps;

    public BaseHelpclass() {
        initialize();
    }

    public BaseHelpclass(String id) {
        setId(id);
        initialize();
    }

    public BaseHelpclass(String id, String hcClassName, Integer hcSortId, String hcBrief) {
        setId(id);
        setHcClassName(hcClassName);
        setHcSortId(hcSortId);
        setHcBrief(hcBrief);
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

    public String getHcBrief() {
        return this.hcBrief;
    }

    public void setHcBrief(String hcBrief) {
        this.hcBrief = hcBrief;
    }

    public String getHcClassName() {
        return this.hcClassName;
    }

    public void setHcClassName(String hcClassName) {
        this.hcClassName = hcClassName;
    }

    public Integer getHcSortId() {
        return this.hcSortId;
    }

    public void setHcSortId(Integer hcSortId) {
        this.hcSortId = hcSortId;
    }

    public Helpclass getHcFather() {
        return this.hcFather;
    }

    public void setHcFather(Helpclass hcFather) {
        this.hcFather = hcFather;
    }

    public Set<Helpclass> getHelpclass() {
        return this.helpclass;
    }

    public void setHelpclass(Set<Helpclass> helpclass) {
        this.helpclass = helpclass;
    }

    public void addTohelpclass(Helpclass helpclass) {
        if (null == getHelpclass())
            setHelpclass(new TreeSet());
        getHelpclass().add(helpclass);
    }

    public Set<Help> getHelps() {
        return this.helps;
    }

    public void setHelps(Set<Help> helps) {
        this.helps = helps;
    }

    public void addTohelps(Help help) {
        if (null == getHelps())
            setHelps(new TreeSet());
        getHelps().add(help);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Helpclass))
            return false;

        Helpclass helpclass = (Helpclass) obj;
        if ((null == getId()) || (null == helpclass.getId()))
            return false;
        return getId().equals(helpclass.getId());
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
 * com.hr.help.domain.base.BaseHelpclass JD-Core Version: 0.5.4
 */