package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.ContractType;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseEmpcontract extends BaseDomain implements Serializable {
    public static String REF = "Empcontract";
    public static String PROP_ECT_ATTATCHMENT = "ectAttatchment";
    public static String PROP_ECT_COMMENTS = "ectComments";
    public static String PROP_EMPLOYEE = "employee";
    public static String PROP_ECT_ID = "ectId";
    public static String PROP_ECT_LAST_CHANGE_BY = "ectLastChangeBy";
    public static String PROP_ECT_CREATE_BY = "ectCreateBy";
    public static String PROP_ECT_NO = "ectNo";
    public static String PROP_ETC_EXPIRE = "etcExpire";
    public static String PROP_ECT_CREATE_DATE = "ectCreateDate";
    public static String PROP_ECT_LAST_CHANGE_TIME = "ectLastChangeTime";
    public static String PROP_ECT_STATUS = "ectStatus";
    public static String PROP_CONTRACT_TYPE = "contractType";
    public static String PROP_ECT_START_DATE = "ectStartDate";
    public static String PROP_ECT_END_DATE = "ectEndDate";

    private int hashCode = -2147483648;
    private String ectId;
    private String ectNo;
    private Date ectStartDate;
    private Date ectEndDate;
    private Integer etcExpire;
    private String ectAttatchment;
    private String ectComments;
    private String ectStatus;
    private String ectCreateBy;
    private Date ectCreateDate;
    private Date ectLastChangeTime;
    private String ectLastChangeBy;
    private ContractType contractType;
    private Employee employee;

    public BaseEmpcontract() {
        initialize();
    }

    public BaseEmpcontract(String ectId) {
        setEctId(ectId);
        initialize();
    }

    protected void initialize() {
    }

    public String getEctId() {
        return this.ectId;
    }

    public void setEctId(String ectId) {
        this.ectId = ectId;
        this.hashCode = -2147483648;
    }

    public String getEctNo() {
        return this.ectNo;
    }

    public void setEctNo(String ectNo) {
        this.ectNo = ectNo;
    }

    public Date getEctStartDate() {
        return this.ectStartDate;
    }

    public void setEctStartDate(Date ectStartDate) {
        this.ectStartDate = ectStartDate;
    }

    public Date getEctEndDate() {
        return this.ectEndDate;
    }

    public void setEctEndDate(Date ectEndDate) {
        this.ectEndDate = ectEndDate;
    }

    public Integer getEtcExpire() {
        return this.etcExpire;
    }

    public void setEtcExpire(Integer etcExpire) {
        this.etcExpire = etcExpire;
    }

    public String getEctAttatchment() {
        return this.ectAttatchment;
    }

    public void setEctAttatchment(String ectAttatchment) {
        this.ectAttatchment = ectAttatchment;
    }

    public String getEctComments() {
        return this.ectComments;
    }

    public void setEctComments(String ectComments) {
        this.ectComments = ectComments;
    }

    public String getEctStatus() {
        return this.ectStatus;
    }

    public void setEctStatus(String ectStatus) {
        this.ectStatus = ectStatus;
    }

    public String getEctCreateBy() {
        return this.ectCreateBy;
    }

    public void setEctCreateBy(String ectCreateBy) {
        this.ectCreateBy = ectCreateBy;
    }

    public Date getEctCreateDate() {
        return this.ectCreateDate;
    }

    public void setEctCreateDate(Date ectCreateDate) {
        this.ectCreateDate = ectCreateDate;
    }

    public Date getEctLastChangeTime() {
        return this.ectLastChangeTime;
    }

    public void setEctLastChangeTime(Date ectLastChangeTime) {
        this.ectLastChangeTime = ectLastChangeTime;
    }

    public String getEctLastChangeBy() {
        return this.ectLastChangeBy;
    }

    public void setEctLastChangeBy(String ectLastChangeBy) {
        this.ectLastChangeBy = ectLastChangeBy;
    }

    public ContractType getContractType() {
        return this.contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Empcontract))
            return false;

        Empcontract empcontract = (Empcontract) obj;
        if ((null == getEctId()) || (null == empcontract.getEctId()))
            return false;
        return getEctId().equals(empcontract.getEctId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getEctId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getEctId().hashCode();
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
 * com.hr.profile.domain.base.BaseEmpcontract JD-Core Version: 0.5.4
 */