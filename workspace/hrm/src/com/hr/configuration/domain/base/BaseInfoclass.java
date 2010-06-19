package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Infoclass;
import com.hr.information.domain.Information;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseInfoclass extends BaseDomain implements Serializable {
    public static String REF = "Infoclass";
    public static String PROP_INFOCLASS_STATUS = "infoclassStatus";
    public static String PROP_INFOCLASS_SORT_ID = "infoclassSortId";
    public static String PROP_INFOCLASS_NAME = "infoclassName";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String infoclassName;
    private Integer infoclassSortId;
    private Integer infoclassStatus;
    private Set<Information> informations;

    public BaseInfoclass() {
        initialize();
    }

    public BaseInfoclass(String id) {
        setId(id);
        initialize();
    }

    public BaseInfoclass(String id, String infoclassName, Integer infoclassSortId,
            Integer infoclassStatus) {
        setId(id);
        setInfoclassName(infoclassName);
        setInfoclassSortId(infoclassSortId);
        setInfoclassStatus(infoclassStatus);
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

    public String getInfoclassName() {
        return this.infoclassName;
    }

    public void setInfoclassName(String infoclassName) {
        this.infoclassName = infoclassName;
    }

    public Integer getInfoclassSortId() {
        return this.infoclassSortId;
    }

    public void setInfoclassSortId(Integer infoclassSortId) {
        this.infoclassSortId = infoclassSortId;
    }

    public Integer getInfoclassStatus() {
        return this.infoclassStatus;
    }

    public void setInfoclassStatus(Integer infoclassStatus) {
        this.infoclassStatus = infoclassStatus;
    }

    public Set<Information> getInformations() {
        return this.informations;
    }

    public void setInformations(Set<Information> informations) {
        this.informations = informations;
    }

    public void addToinformations(Information information) {
        if (null == getInformations())
            setInformations(new TreeSet());
        getInformations().add(information);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Infoclass))
            return false;

        Infoclass infoclass = (Infoclass) obj;
        if ((null == getId()) || (null == infoclass.getId()))
            return false;
        return getId().equals(infoclass.getId());
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
 * com.hr.configuration.domain.base.BaseInfoclass JD-Core Version: 0.5.4
 */