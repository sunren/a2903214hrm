package com.hr.compensation.domain.base;

import com.hr.base.BaseDomain;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.math.BigDecimal;

public abstract class BaseEmpbenefit extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "Empbenefit";
    public static String PROP_EBF_EBFID = "ebfId";
    public static String PROP_EBF_EBFSTARTMONTH = "ebfStartMonth";
    public static String PROP_EBF_EBFENDMONTH = "ebfEndMonth";
    public static String PROP_EBF_EBFPENSIONNO = "ebfPensionNo";
    public static String PROP_EBF_EBFMEDICALNO = "ebfMedicalNo";
    public static String PROP_EBF_EBFHOUSINGNO = "ebfHousingNo";
    public static String PROP_EBF_EBFPENSIONAMOUNT = "ebfPensionAmount";
    public static String PROP_EBF_EBFHOUSINGAMOUNT = "ebfHousingAmount";
    public static String PROP_EBF_EBFINSURANCEAMOUNT = "ebfInsuranceAmount";
    public static String PROP_EBF_EBFCOMMENTS = "ebfComments";
    public static String PROP_EBF_EMPLOYEE = "employee";
    private String ebfId;
    private String ebfStartMonth;
    private String ebfEndMonth;
    private String ebfPensionNo;
    private String ebfMedicalNo;
    private String ebfHousingNo;
    private BigDecimal ebfPensionAmount;
    private BigDecimal ebfHousingAmount;
    private BigDecimal ebfInsuranceAmount;
    private String ebfComments;
    private Employee employee;

    public BaseEmpbenefit() {
        initialize();
    }

    protected void initialize() {
    }

    public BaseEmpbenefit(String ebfId) {
        setEbfId(ebfId);
        initialize();
    }

    public BaseEmpbenefit(String ebfId, Employee employee, String ebfStartMonth) {
        this.ebfId = ebfId;
        this.ebfStartMonth = ebfStartMonth;
    }

    public BaseEmpbenefit(String ebfId, Employee employee, String ebfStartMonth,
            String ebfEndMonth, String ebfPensionNo, String ebfMedicalNo, String ebfHousingNo,
            BigDecimal ebfPensionAmount, BigDecimal ebfHousingAmount,
            BigDecimal ebfInsuranceAmount, String ebfComments) {
        this.ebfId = ebfId;
        this.ebfStartMonth = ebfStartMonth;
        this.ebfEndMonth = ebfEndMonth;
        this.ebfPensionNo = ebfPensionNo;
        this.ebfMedicalNo = ebfMedicalNo;
        this.ebfHousingNo = ebfHousingNo;
        this.ebfPensionAmount = ebfPensionAmount;
        this.ebfHousingAmount = ebfHousingAmount;
        this.ebfInsuranceAmount = ebfInsuranceAmount;
        this.ebfComments = ebfComments;
        this.employee = employee;
    }

    public String getEbfId() {
        return this.ebfId;
    }

    public void setEbfId(String ebfId) {
        this.ebfId = ebfId;
    }

    public String getEbfStartMonth() {
        return this.ebfStartMonth;
    }

    public void setEbfStartMonth(String ebfStartMonth) {
        this.ebfStartMonth = ebfStartMonth;
    }

    public String getEbfEndMonth() {
        return this.ebfEndMonth;
    }

    public void setEbfEndMonth(String ebfEndMonth) {
        this.ebfEndMonth = ebfEndMonth;
    }

    public String getEbfPensionNo() {
        return this.ebfPensionNo;
    }

    public void setEbfPensionNo(String ebfPensionNo) {
        this.ebfPensionNo = ebfPensionNo;
    }

    public String getEbfMedicalNo() {
        return this.ebfMedicalNo;
    }

    public void setEbfMedicalNo(String ebfMedicalNo) {
        this.ebfMedicalNo = ebfMedicalNo;
    }

    public String getEbfHousingNo() {
        return this.ebfHousingNo;
    }

    public void setEbfHousingNo(String ebfHousingNo) {
        this.ebfHousingNo = ebfHousingNo;
    }

    public BigDecimal getEbfPensionAmount() {
        return this.ebfPensionAmount;
    }

    public void setEbfPensionAmount(BigDecimal ebfPensionAmount) {
        this.ebfPensionAmount = ebfPensionAmount;
    }

    public BigDecimal getEbfHousingAmount() {
        return this.ebfHousingAmount;
    }

    public void setEbfHousingAmount(BigDecimal ebfHousingAmount) {
        this.ebfHousingAmount = ebfHousingAmount;
    }

    public BigDecimal getEbfInsuranceAmount() {
        return this.ebfInsuranceAmount;
    }

    public void setEbfInsuranceAmount(BigDecimal ebfInsuranceAmount) {
        this.ebfInsuranceAmount = ebfInsuranceAmount;
    }

    public String getEbfComments() {
        return this.ebfComments;
    }

    public void setEbfComments(String ebfComments) {
        this.ebfComments = ebfComments;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.domain.base.BaseEmpbenefit JD-Core Version: 0.5.4
 */