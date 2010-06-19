package com.hr.compensation.domain.base;

import com.hr.base.DaoBean;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.profile.domain.Employee;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BaseEmpbenefitplan extends DaoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String PROP_EBP_ID = "ebpId";
    public static String PROP_EBP_EMP_NO = "ebpEmpno";
    public static String PROP_EBP_YEARMONTH = "ebpYearMonth";
    public static String PROP_EBP_BELONG_YEARMONTH = "ebpBelongYearmonth";
    public static String PROP_EBP_STATUS = "ebpStatus";
    public static String PROP_EBP_CREATE_BY = "ebpCreateBy";
    public static String PROP_EBP_CREATE_TIME = "ebpCreateTime";
    public static String PROP_EBP_LAST_CHANGE_BY = "ebpLastChangeBy";
    public static String PROP_EBP_LAST_CHANGE_TIME = "ebpLastChangeTime";
    public static String PROP_EBP_EBP_ESAV_ID = "ebpEsavId";
    private String ebpId;
    private String ebpYearMonth;
    private String ebpBelongYearmonth;
    private BigDecimal ebpPensionAmountb;
    private BigDecimal ebpHousingAmountb;
    private BigDecimal ebpInsuranceAmountb;
    private Integer ebpStatus;
    private String ebpComments;
    private String ebpCreateBy;
    private Date ebpCreateTime;
    private String ebpLastChangeBy;
    private Date ebpLastChangeTime;
    private Empsalaryacctversion ebpEsavId;
    BigDecimal zero = new BigDecimal("0.00");
    private BigDecimal ebpColumn1;
    private BigDecimal ebpColumn2;
    private BigDecimal ebpColumn3;
    private BigDecimal ebpColumn4;
    private BigDecimal ebpColumn5;
    private BigDecimal ebpColumn6;
    private BigDecimal ebpColumn7;
    private BigDecimal ebpColumn8;
    private BigDecimal ebpColumn9;
    private BigDecimal ebpColumn10;
    private BigDecimal ebpColumn11;
    private BigDecimal ebpColumn12;
    private BigDecimal ebpColumn13;
    private BigDecimal ebpColumn14;
    private BigDecimal ebpColumn15;
    private BigDecimal ebpColumn16;
    private BigDecimal ebpColumn17;
    private BigDecimal ebpColumn18;
    private BigDecimal ebpColumn19;
    private BigDecimal ebpColumn20;
    private BigDecimal ebpColumn21;
    private BigDecimal ebpColumn22;
    private BigDecimal ebpColumn23;
    private BigDecimal ebpColumn24;
    private BigDecimal ebpColumn25;
    private BigDecimal ebpColumn26;
    private BigDecimal ebpColumn27;
    private BigDecimal ebpColumn28;
    private BigDecimal ebpColumn29;
    private BigDecimal ebpColumn30;
    private BigDecimal ebpColumn31;
    private BigDecimal ebpColumn32;
    private BigDecimal ebpColumn33;
    private BigDecimal ebpColumn34;
    private BigDecimal ebpColumn35;
    private BigDecimal ebpColumn36;
    private BigDecimal ebpColumn37;
    private BigDecimal ebpColumn38;
    private BigDecimal ebpColumn39;
    private BigDecimal ebpColumn40;
    private BigDecimal ebpColumn41;
    private BigDecimal ebpColumn42;
    private BigDecimal ebpColumn43;
    private BigDecimal ebpColumn44;
    private BigDecimal ebpColumn45;
    private BigDecimal ebpColumn46;
    private BigDecimal ebpColumn47;
    private BigDecimal ebpColumn48;
    private Employee ebpEmpno;

    public BaseEmpbenefitplan() {
        initialize();
    }

    protected void initialize() {
    }

    public Employee getEbpEmpno() {
        return this.ebpEmpno;
    }

    public void setEbpEmpno(Employee ebpEmpno) {
        this.ebpEmpno = ebpEmpno;
    }

    public BigDecimal getEbpHousingAmountb() {
        return this.ebpHousingAmountb;
    }

    public void setEbpHousingAmountb(BigDecimal ebpHousingAmonntb) {
        this.ebpHousingAmountb = ebpHousingAmonntb;
    }

    public String getEbpId() {
        return this.ebpId;
    }

    public void setEbpId(String ebpId) {
        this.ebpId = ebpId;
    }

    public BigDecimal getEbpInsuranceAmountb() {
        return this.ebpInsuranceAmountb;
    }

    public void setEbpInsuranceAmountb(BigDecimal ebpInsuranceAmountb) {
        this.ebpInsuranceAmountb = ebpInsuranceAmountb;
    }

    public BigDecimal getEbpPensionAmountb() {
        return this.ebpPensionAmountb;
    }

    public void setEbpPensionAmountb(BigDecimal ebpPensionAmountb) {
        this.ebpPensionAmountb = ebpPensionAmountb;
    }

    public String getEbpYearMonth() {
        return this.ebpYearMonth;
    }

    public void setEbpYearMonth(String ebpYearMonth) {
        this.ebpYearMonth = ebpYearMonth;
    }

    public String getEbpBelongYearmonth() {
        return this.ebpBelongYearmonth;
    }

    public void setEbpBelongYearmonth(String ebpBelongYearmonth) {
        this.ebpBelongYearmonth = ebpBelongYearmonth;
    }

    public String getEbpCreateBy() {
        return this.ebpCreateBy;
    }

    public void setEbpCreateBy(String ebpCreateBy) {
        this.ebpCreateBy = ebpCreateBy;
    }

    public Date getEbpCreateTime() {
        return this.ebpCreateTime;
    }

    public void setEbpCreateTime(Date ebpCreateTime) {
        this.ebpCreateTime = ebpCreateTime;
    }

    public String getEbpLastChangeBy() {
        return this.ebpLastChangeBy;
    }

    public void setEbpLastChangeBy(String ebpLastChangeBy) {
        this.ebpLastChangeBy = ebpLastChangeBy;
    }

    public Date getEbpLastChangeTime() {
        return this.ebpLastChangeTime;
    }

    public void setEbpLastChangeTime(Date ebpLastChangeTime) {
        this.ebpLastChangeTime = ebpLastChangeTime;
    }

    public Integer getEbpStatus() {
        return this.ebpStatus;
    }

    public void setEbpStatus(Integer ebpStatus) {
        this.ebpStatus = ebpStatus;
    }

    public String getEbpComments() {
        return this.ebpComments;
    }

    public void setEbpComments(String ebpComments) {
        this.ebpComments = ebpComments;
    }

    public BigDecimal getEbpColumn1() {
        return this.ebpColumn1;
    }

    public void setEbpColumn1(BigDecimal ebpColumn1) {
        this.ebpColumn1 = ebpColumn1;
    }

    public BigDecimal getEbpColumn10() {
        return this.ebpColumn10;
    }

    public void setEbpColumn10(BigDecimal ebpColumn10) {
        this.ebpColumn10 = ebpColumn10;
    }

    public BigDecimal getEbpColumn11() {
        return this.ebpColumn11;
    }

    public void setEbpColumn11(BigDecimal ebpColumn11) {
        this.ebpColumn11 = ebpColumn11;
    }

    public BigDecimal getEbpColumn12() {
        return this.ebpColumn12;
    }

    public void setEbpColumn12(BigDecimal ebpColumn12) {
        this.ebpColumn12 = ebpColumn12;
    }

    public BigDecimal getEbpColumn13() {
        return this.ebpColumn13;
    }

    public void setEbpColumn13(BigDecimal ebpColumn13) {
        this.ebpColumn13 = ebpColumn13;
    }

    public BigDecimal getEbpColumn14() {
        return this.ebpColumn14;
    }

    public void setEbpColumn14(BigDecimal ebpColumn14) {
        this.ebpColumn14 = ebpColumn14;
    }

    public BigDecimal getEbpColumn15() {
        return this.ebpColumn15;
    }

    public void setEbpColumn15(BigDecimal ebpColumn15) {
        this.ebpColumn15 = ebpColumn15;
    }

    public BigDecimal getEbpColumn16() {
        return this.ebpColumn16;
    }

    public void setEbpColumn16(BigDecimal ebpColumn16) {
        this.ebpColumn16 = ebpColumn16;
    }

    public BigDecimal getEbpColumn17() {
        return this.ebpColumn17;
    }

    public void setEbpColumn17(BigDecimal ebpColumn17) {
        this.ebpColumn17 = ebpColumn17;
    }

    public BigDecimal getEbpColumn18() {
        return this.ebpColumn18;
    }

    public void setEbpColumn18(BigDecimal ebpColumn18) {
        this.ebpColumn18 = ebpColumn18;
    }

    public BigDecimal getEbpColumn19() {
        return this.ebpColumn19;
    }

    public void setEbpColumn19(BigDecimal ebpColumn19) {
        this.ebpColumn19 = ebpColumn19;
    }

    public BigDecimal getEbpColumn2() {
        return this.ebpColumn2;
    }

    public void setEbpColumn2(BigDecimal ebpColumn2) {
        this.ebpColumn2 = ebpColumn2;
    }

    public BigDecimal getEbpColumn20() {
        return this.ebpColumn20;
    }

    public void setEbpColumn20(BigDecimal ebpColumn20) {
        this.ebpColumn20 = ebpColumn20;
    }

    public BigDecimal getEbpColumn21() {
        return this.ebpColumn21;
    }

    public void setEbpColumn21(BigDecimal ebpColumn21) {
        this.ebpColumn21 = ebpColumn21;
    }

    public BigDecimal getEbpColumn22() {
        return this.ebpColumn22;
    }

    public void setEbpColumn22(BigDecimal ebpColumn22) {
        this.ebpColumn22 = ebpColumn22;
    }

    public BigDecimal getEbpColumn23() {
        return this.ebpColumn23;
    }

    public void setEbpColumn23(BigDecimal ebpColumn23) {
        this.ebpColumn23 = ebpColumn23;
    }

    public BigDecimal getEbpColumn24() {
        return this.ebpColumn24;
    }

    public void setEbpColumn24(BigDecimal ebpColumn24) {
        this.ebpColumn24 = ebpColumn24;
    }

    public BigDecimal getEbpColumn25() {
        return this.ebpColumn25;
    }

    public void setEbpColumn25(BigDecimal ebpColumn25) {
        this.ebpColumn25 = ebpColumn25;
    }

    public BigDecimal getEbpColumn26() {
        return this.ebpColumn26;
    }

    public void setEbpColumn26(BigDecimal ebpColumn26) {
        this.ebpColumn26 = ebpColumn26;
    }

    public BigDecimal getEbpColumn27() {
        return this.ebpColumn27;
    }

    public void setEbpColumn27(BigDecimal ebpColumn27) {
        this.ebpColumn27 = ebpColumn27;
    }

    public BigDecimal getEbpColumn28() {
        return this.ebpColumn28;
    }

    public void setEbpColumn28(BigDecimal ebpColumn28) {
        this.ebpColumn28 = ebpColumn28;
    }

    public BigDecimal getEbpColumn29() {
        return this.ebpColumn29;
    }

    public void setEbpColumn29(BigDecimal ebpColumn29) {
        this.ebpColumn29 = ebpColumn29;
    }

    public BigDecimal getEbpColumn3() {
        return this.ebpColumn3;
    }

    public void setEbpColumn3(BigDecimal ebpColumn3) {
        this.ebpColumn3 = ebpColumn3;
    }

    public BigDecimal getEbpColumn30() {
        return this.ebpColumn30;
    }

    public void setEbpColumn30(BigDecimal ebpColumn30) {
        this.ebpColumn30 = ebpColumn30;
    }

    public BigDecimal getEbpColumn31() {
        return this.ebpColumn31;
    }

    public void setEbpColumn31(BigDecimal ebpColumn31) {
        this.ebpColumn31 = ebpColumn31;
    }

    public BigDecimal getEbpColumn32() {
        return this.ebpColumn32;
    }

    public void setEbpColumn32(BigDecimal ebpColumn32) {
        this.ebpColumn32 = ebpColumn32;
    }

    public BigDecimal getEbpColumn33() {
        return this.ebpColumn33;
    }

    public void setEbpColumn33(BigDecimal ebpColumn33) {
        this.ebpColumn33 = ebpColumn33;
    }

    public BigDecimal getEbpColumn34() {
        return this.ebpColumn34;
    }

    public void setEbpColumn34(BigDecimal ebpColumn34) {
        this.ebpColumn34 = ebpColumn34;
    }

    public BigDecimal getEbpColumn35() {
        return this.ebpColumn35;
    }

    public void setEbpColumn35(BigDecimal ebpColumn35) {
        this.ebpColumn35 = ebpColumn35;
    }

    public BigDecimal getEbpColumn36() {
        return this.ebpColumn36;
    }

    public void setEbpColumn36(BigDecimal ebpColumn36) {
        this.ebpColumn36 = ebpColumn36;
    }

    public BigDecimal getEbpColumn37() {
        return this.ebpColumn37;
    }

    public void setEbpColumn37(BigDecimal ebpColumn37) {
        this.ebpColumn37 = ebpColumn37;
    }

    public BigDecimal getEbpColumn38() {
        return this.ebpColumn38;
    }

    public void setEbpColumn38(BigDecimal ebpColumn38) {
        this.ebpColumn38 = ebpColumn38;
    }

    public BigDecimal getEbpColumn39() {
        return this.ebpColumn39;
    }

    public void setEbpColumn39(BigDecimal ebpColumn39) {
        this.ebpColumn39 = ebpColumn39;
    }

    public BigDecimal getEbpColumn4() {
        return this.ebpColumn4;
    }

    public void setEbpColumn4(BigDecimal ebpColumn4) {
        this.ebpColumn4 = ebpColumn4;
    }

    public BigDecimal getEbpColumn40() {
        return this.ebpColumn40;
    }

    public void setEbpColumn40(BigDecimal ebpColumn40) {
        this.ebpColumn40 = ebpColumn40;
    }

    public BigDecimal getEbpColumn41() {
        return this.ebpColumn41;
    }

    public void setEbpColumn41(BigDecimal ebpColumn41) {
        this.ebpColumn41 = ebpColumn41;
    }

    public BigDecimal getEbpColumn42() {
        return this.ebpColumn42;
    }

    public void setEbpColumn42(BigDecimal ebpColumn42) {
        this.ebpColumn42 = ebpColumn42;
    }

    public BigDecimal getEbpColumn43() {
        return this.ebpColumn43;
    }

    public void setEbpColumn43(BigDecimal ebpColumn43) {
        this.ebpColumn43 = ebpColumn43;
    }

    public BigDecimal getEbpColumn44() {
        return this.ebpColumn44;
    }

    public void setEbpColumn44(BigDecimal ebpColumn44) {
        this.ebpColumn44 = ebpColumn44;
    }

    public BigDecimal getEbpColumn45() {
        return this.ebpColumn45;
    }

    public void setEbpColumn45(BigDecimal ebpColumn45) {
        this.ebpColumn45 = ebpColumn45;
    }

    public BigDecimal getEbpColumn46() {
        return this.ebpColumn46;
    }

    public void setEbpColumn46(BigDecimal ebpColumn46) {
        this.ebpColumn46 = ebpColumn46;
    }

    public BigDecimal getEbpColumn47() {
        return this.ebpColumn47;
    }

    public void setEbpColumn47(BigDecimal ebpColumn47) {
        this.ebpColumn47 = ebpColumn47;
    }

    public BigDecimal getEbpColumn48() {
        return this.ebpColumn48;
    }

    public void setEbpColumn48(BigDecimal ebpColumn48) {
        this.ebpColumn48 = ebpColumn48;
    }

    public BigDecimal getEbpColumn5() {
        return this.ebpColumn5;
    }

    public void setEbpColumn5(BigDecimal ebpColumn5) {
        this.ebpColumn5 = ebpColumn5;
    }

    public BigDecimal getEbpColumn6() {
        return this.ebpColumn6;
    }

    public void setEbpColumn6(BigDecimal ebpColumn6) {
        this.ebpColumn6 = ebpColumn6;
    }

    public BigDecimal getEbpColumn7() {
        return this.ebpColumn7;
    }

    public void setEbpColumn7(BigDecimal ebpColumn7) {
        this.ebpColumn7 = ebpColumn7;
    }

    public BigDecimal getEbpColumn8() {
        return this.ebpColumn8;
    }

    public void setEbpColumn8(BigDecimal ebpColumn8) {
        this.ebpColumn8 = ebpColumn8;
    }

    public BigDecimal getEbpColumn9() {
        return this.ebpColumn9;
    }

    public void setEbpColumn9(BigDecimal ebpColumn9) {
        this.ebpColumn9 = ebpColumn9;
    }

    public Empsalaryacctversion getEbpEsavId() {
        return this.ebpEsavId;
    }

    public void setEbpEsavId(Empsalaryacctversion ebpEsavId) {
        this.ebpEsavId = ebpEsavId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.domain.base.BaseEmpbenefitplan JD-Core Version: 0.5.4
 */