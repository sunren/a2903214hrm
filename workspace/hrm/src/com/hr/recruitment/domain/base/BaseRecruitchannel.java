package com.hr.recruitment.domain.base;

import com.hr.base.BaseDomain;
import com.hr.recruitment.domain.Recruitapplier;
import com.hr.recruitment.domain.Recruitchannel;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseRecruitchannel extends BaseDomain implements Serializable {
    public static String REF = "Recruitchannel";
    public static String PROP_RECCH_EMAIL = "recchEmail";
    public static String PROP_RECCH_RESP_PERSON = "recchRespPerson";
    public static String PROP_RECCH_ADDR = "recchAddr";
    public static String PROP_RECCH_NAME = "recchName";
    public static String PROP_RECCH_COMMENTS = "recchComments";
    public static String PROP_RECCH_ADDR_ZIP = "recchAddrZip";
    public static String PROP_RECCH_PHONE = "recchPhone";
    public static String PROP_ID = "id";
    public static String PROP_RECCH_CITY_NO = "recchCityNo";

    private int hashCode = -2147483648;
    private String id;
    private String recchName;
    private String recchRespPerson;
    private String recchPhone;
    private String recchEmail;
    private String recchCityNo;
    private String recchAddr;
    private String recchAddrZip;
    private String recchComments;
    private Set<Recruitapplier> recruitappliers;

    public BaseRecruitchannel() {
        initialize();
    }

    public BaseRecruitchannel(String id) {
        setId(id);
        initialize();
    }

    public BaseRecruitchannel(String id, String recchName, String recchRespPerson) {
        setId(id);
        setRecchName(recchName);
        setRecchRespPerson(recchRespPerson);
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

    public String getRecchName() {
        return this.recchName;
    }

    public void setRecchName(String recchName) {
        this.recchName = recchName;
    }

    public String getRecchRespPerson() {
        return this.recchRespPerson;
    }

    public void setRecchRespPerson(String recchRespPerson) {
        this.recchRespPerson = recchRespPerson;
    }

    public String getRecchPhone() {
        return this.recchPhone;
    }

    public void setRecchPhone(String recchPhone) {
        this.recchPhone = recchPhone;
    }

    public String getRecchEmail() {
        return this.recchEmail;
    }

    public void setRecchEmail(String recchEmail) {
        this.recchEmail = recchEmail;
    }

    public String getRecchCityNo() {
        return this.recchCityNo;
    }

    public void setRecchCityNo(String recchCityNo) {
        this.recchCityNo = recchCityNo;
    }

    public String getRecchAddr() {
        return this.recchAddr;
    }

    public void setRecchAddr(String recchAddr) {
        this.recchAddr = recchAddr;
    }

    public String getRecchAddrZip() {
        return this.recchAddrZip;
    }

    public void setRecchAddrZip(String recchAddrZip) {
        this.recchAddrZip = recchAddrZip;
    }

    public String getRecchComments() {
        return this.recchComments;
    }

    public void setRecchComments(String recchComments) {
        this.recchComments = recchComments;
    }

    public Set<Recruitapplier> getRecruitappliers() {
        return this.recruitappliers;
    }

    public void setRecruitappliers(Set<Recruitapplier> recruitappliers) {
        this.recruitappliers = recruitappliers;
    }

    public void addTorecruitappliers(Recruitapplier recruitapplier) {
        if (null == getRecruitappliers())
            setRecruitappliers(new TreeSet());
        getRecruitappliers().add(recruitapplier);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Recruitchannel))
            return false;

        Recruitchannel recruitchannel = (Recruitchannel) obj;
        if ((null == getId()) || (null == recruitchannel.getId()))
            return false;
        return getId().equals(recruitchannel.getId());
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
 * com.hr.recruitment.domain.base.BaseRecruitchannel JD-Core Version: 0.5.4
 */