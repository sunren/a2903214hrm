package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.examin.domain.Attendshift;
import java.io.Serializable;
import java.math.BigDecimal;

public abstract class BaseAttendshift extends BaseDomain implements Serializable {
    public static String REF = "Attendshift";
    public static String PROP_Night_Shift = "attsNightShift";
    public static String PROP_ATTS_IS_DEFAULT = "attsIsDefault";
    public static String PROP_ATTS_DESC = "attsDesc";
    public static String PROP_ATTS_SORT_ID = "attsSortId";
    public static String PROP_ATTS_NAME = "attsName";
    public static String PROP_ATTS_SHORT_NAME = "attsShortName";
    public static String PROP_ATTS_COLOR = "attsColor";
    public static String PROP_ID = "id";
    public static String PROP_ATTS_SESSION = "attsSession";
    public static String PROP_ATTS_WORKING_HOUR = "attsWorkingHour";
    public static String PROP_ATTS_STATUS = "attsStatus";
    public static String PROP_ATTS_STRICKED = "attsStricked";

    private int hashCode = -2147483648;
    private String id;
    private String attsName;
    private String attsDesc;
    private Integer attsNightShift;
    private String attsColor;
    private String attsShortName;
    private Integer attsIsDefault = Integer.valueOf(0);
    private Integer attsSortId;
    private String attsSession;
    private BigDecimal attsWorkingHour;
    private Integer attsStatus;
    private Integer attsStricked;

    public BaseAttendshift() {
        initialize();
    }

    public BaseAttendshift(String id) {
        setId(id);
        initialize();
    }

    public BaseAttendshift(String id, String attsName, Integer attsNightShift, String attsColor,
            Integer attsSortId) {
        setId(id);
        setAttsName(attsName);
        setAttsNightShift(attsNightShift);
        setAttsColor(attsColor);
        setAttsSortId(attsSortId);
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

    public String getAttsName() {
        return this.attsName;
    }

    public void setAttsName(String attsName) {
        this.attsName = attsName;
    }

    public String getAttsDesc() {
        return this.attsDesc;
    }

    public void setAttsDesc(String attsDesc) {
        this.attsDesc = attsDesc;
    }

    public String getAttsColor() {
        return this.attsColor;
    }

    public void setAttsColor(String attsColor) {
        this.attsColor = attsColor;
    }

    public String getAttsShortName() {
        return this.attsShortName;
    }

    public void setAttsShortName(String attsShortName) {
        this.attsShortName = attsShortName;
    }

    public Integer getAttsIsDefault() {
        return this.attsIsDefault;
    }

    public void setAttsIsDefault(Integer attsIsDefault) {
        this.attsIsDefault = attsIsDefault;
    }

    public Integer getAttsSortId() {
        return this.attsSortId;
    }

    public void setAttsSortId(Integer attsSortId) {
        this.attsSortId = attsSortId;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Attendshift))
            return false;

        Attendshift attendshift = (Attendshift) obj;
        if ((null == getId()) || (null == attendshift.getId()))
            return false;
        return getId().equals(attendshift.getId());
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

    public Integer getAttsNightShift() {
        return this.attsNightShift;
    }

    public void setAttsNightShift(Integer attsNightShift) {
        this.attsNightShift = attsNightShift;
    }

    public String getAttsSession() {
        return this.attsSession;
    }

    public void setAttsSession(String attsSession) {
        this.attsSession = attsSession;
    }

    public BigDecimal getAttsWorkingHour() {
        return this.attsWorkingHour;
    }

    public void setAttsWorkingHour(BigDecimal attsWorkingHour) {
        this.attsWorkingHour = attsWorkingHour;
    }

    public Integer getAttsStatus() {
        return this.attsStatus;
    }

    public void setAttsStatus(Integer attsStatus) {
        this.attsStatus = attsStatus;
    }

    public Integer getAttsStricked() {
        return this.attsStricked;
    }

    public void setAttsStricked(Integer attsStricked) {
        this.attsStricked = attsStricked;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.base.BaseAttendshift JD-Core Version: 0.5.4
 */