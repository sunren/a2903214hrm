package com.hr.configuration.domain.base;

import com.hr.base.DaoBean;
import com.hr.configuration.domain.Syslog;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseSyslog extends DaoBean implements Serializable {
    public static String REF = "Syslog";
    public static String PROP_SL_TABLE_NAME = "slTableName";
    public static String PROP_SL_COMMENTS = "slComments";
    public static String PROP_SL_RECORD_ID = "slRecordId";
    public static String PROP_SL_TYPE = "slType";
    public static String PROP_ID = "id";
    public static String PROP_SL_ACTION = "slAction";
    public static String PROP_SL_CHANGE_EMPNO = "slChangeEmpno";
    public static String PROP_SL_CHANGE_TIME = "slChangeTime";
    public static String PROP_SL_EMPNO = "slEmpno";

    private int hashCode = -2147483648;
    private String id;
    private Integer slType;
    private String slTableName;
    private String slEmpno;
    private String slRecordId;
    private String slAction;
    private Date slChangeTime;
    private String slComments;
    private Employee slChangeEmpno;

    public BaseSyslog() {
        initialize();
    }

    public BaseSyslog(String id) {
        setId(id);
        initialize();
    }

    public BaseSyslog(String id, Employee slChangeEmpno, Integer slType, String slTableName,
            String slRecordId, String slAction, Date slChangeTime) {
        setId(id);
        setSlChangeEmpno(slChangeEmpno);
        setSlType(slType);
        setSlTableName(slTableName);
        setSlRecordId(slRecordId);
        setSlAction(slAction);
        setSlChangeTime(slChangeTime);
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

    public Integer getSlType() {
        return this.slType;
    }

    public void setSlType(Integer slType) {
        this.slType = slType;
    }

    public String getSlTableName() {
        return this.slTableName;
    }

    public void setSlTableName(String slTableName) {
        this.slTableName = slTableName;
    }

    public String getSlEmpno() {
        return this.slEmpno;
    }

    public void setSlEmpno(String slEmpno) {
        this.slEmpno = slEmpno;
    }

    public String getSlRecordId() {
        return this.slRecordId;
    }

    public void setSlRecordId(String slRecordId) {
        this.slRecordId = slRecordId;
    }

    public String getSlAction() {
        return this.slAction;
    }

    public void setSlAction(String slAction) {
        this.slAction = slAction;
    }

    public Date getSlChangeTime() {
        return this.slChangeTime;
    }

    public void setSlChangeTime(Date slChangeTime) {
        this.slChangeTime = slChangeTime;
    }

    public String getSlComments() {
        return this.slComments;
    }

    public void setSlComments(String slComments) {
        this.slComments = slComments;
    }

    public Employee getSlChangeEmpno() {
        return this.slChangeEmpno;
    }

    public void setSlChangeEmpno(Employee slChangeEmpno) {
        this.slChangeEmpno = slChangeEmpno;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Syslog))
            return false;

        Syslog syslog = (Syslog) obj;
        if ((null == getId()) || (null == syslog.getId()))
            return false;
        return getId().equals(syslog.getId());
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
 * com.hr.configuration.domain.base.BaseSyslog JD-Core Version: 0.5.4
 */