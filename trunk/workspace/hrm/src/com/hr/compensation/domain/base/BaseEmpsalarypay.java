package com.hr.compensation.domain.base;

import com.hr.base.DaoBean;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;
import java.math.BigDecimal;

public abstract class BaseEmpsalarypay extends DaoBean implements Serializable {
    public static String REF = "Empsalarypay";
    public static String PROP_ESP_EMPNO = "espEmpno";
    public static String PROP_ESP_COLUMN30 = "espColumn30";
    public static String PROP_ESP_COST_CENTER = "espCostCenter";
    public static String PROP_ESP_COLUMN23 = "espColumn23";
    public static String PROP_ESP_COLUMN45 = "espColumn45";
    public static String PROP_ESP_BANK_NAME = "espBankName";
    public static String PROP_ESP_COLUMN48 = "espColumn48";
    public static String PROP_ESP_COLUMN15 = "espColumn15";
    public static String PROP_ESP_COLUMN20 = "espColumn20";
    public static String PROP_ESP_COLUMN7 = "espColumn7";
    public static String PROP_ESP_ESAV_ID = "espEsavId";
    public static String PROP_ESP_COLUMN10 = "espColumn10";
    public static String PROP_ESP_COLUMN42 = "espColumn42";
    public static String PROP_ESP_COLUMN29 = "espColumn29";
    public static String PROP_ESP_COLUMN43 = "espColumn43";
    public static String PROP_ESP_COLUMN41 = "espColumn41";
    public static String PROP_ESP_YEARMONTH = "espYearmonth";
    public static String PROP_ESP_COLUMN27 = "espColumn27";
    public static String PROP_ESP_COLUMN34 = "espColumn34";
    public static String PROP_ESP_COLUMN4 = "espColumn4";
    public static String PROP_ESP_COLUMN19 = "espColumn19";
    public static String PROP_ESP_COLUMN25 = "espColumn25";
    public static String PROP_ESP_COLUMN3 = "espColumn3";
    public static String PROP_ESP_COLUMN14 = "espColumn14";
    public static String PROP_ESP_COLUMN47 = "espColumn47";
    public static String PROP_ESP_CHANGED = "espChanged";
    public static String PROP_ESP_COLUMN31 = "espColumn31";
    public static String PROP_ESP_COLUMN36 = "espColumn36";
    public static String PROP_ESP_COLUMN17 = "espColumn17";
    public static String PROP_ESP_COLUMN6 = "espColumn6";
    public static String PROP_ESP_COLUMN24 = "espColumn24";
    public static String PROP_ESP_EMPTYPE = "espEmptype";
    public static String PROP_ESP_BENEFIT_PLANS = "espBenefitPlans";
    public static String PROP_ESP_BANK_ACCOUNT_NO = "espBankAccountNo";
    public static String PROP_ESP_COLUMN16 = "espColumn16";
    public static String PROP_ESP_COLUMN1 = "espColumn1";
    public static String PROP_ESP_COLUMN38 = "espColumn38";
    public static String PROP_ESP_COLUMN8 = "espColumn8";
    public static String PROP_ESP_COLUMN26 = "espColumn26";
    public static String PROP_ESP_COLUMN37 = "espColumn37";
    public static String PROP_ESP_COLUMN18 = "espColumn18";
    public static String PROP_ESP_COLUMN2 = "espColumn2";
    public static String PROP_ESP_COLUMN21 = "espColumn21";
    public static String PROP_ESP_COLUMN28 = "espColumn28";
    public static String PROP_ESP_COMMENT = "espComment";
    public static String PROP_ESP_COLUMN32 = "espColumn32";
    public static String PROP_ESP_COLUMN35 = "espColumn35";
    public static String PROP_ESP_JOBGRADE = "espJobgrade";
    public static String PROP_ESP_COLUMN9 = "espColumn9";
    public static String PROP_ESP_COLUMN40 = "espColumn40";
    public static String PROP_ESP_DEPT = "espDept";
    public static String PROP_ESP_COLUMN13 = "espColumn13";
    public static String PROP_ESP_COLUMN44 = "espColumn44";
    public static String PROP_ESP_COLUMN11 = "espColumn11";
    public static String PROP_ESP_COLUMN33 = "espColumn33";
    public static String PROP_ESP_COLUMN5 = "espColumn5";
    public static String PROP_ESP_COLUMN22 = "espColumn22";
    public static String PROP_ESP_COLUMN12 = "espColumn12";
    public static String PROP_ESP_COLUMN39 = "espColumn39";
    public static String PROP_ESP_COLUMN46 = "espColumn46";
    public static String PROP_ID = "id";
    public static String PROP_ESP_EMPCONFIG = "espEmpconfig";
    public static String PROP_ESP_LOCATION = "espLocation";
    public static String PROP_ESP_PB_NO = "espPbNo";

    private int hashCode = -2147483648;
    private String id;
    private String espBankName;
    private String espBankAccountNo;
    private String espCostCenter;
    private String espYearmonth;
    private Integer espBenefitPlans;
    private Integer espChanged = Integer.valueOf(0);
    private String espComment;
    private BigDecimal espColumn1;
    private BigDecimal espColumn2;
    private BigDecimal espColumn3;
    private BigDecimal espColumn4;
    private BigDecimal espColumn5;
    private BigDecimal espColumn6;
    private BigDecimal espColumn7;
    private BigDecimal espColumn8;
    private BigDecimal espColumn9;
    private BigDecimal espColumn10;
    private BigDecimal espColumn11;
    private BigDecimal espColumn12;
    private BigDecimal espColumn13;
    private BigDecimal espColumn14;
    private BigDecimal espColumn15;
    private BigDecimal espColumn16;
    private BigDecimal espColumn17;
    private BigDecimal espColumn18;
    private BigDecimal espColumn19;
    private BigDecimal espColumn20;
    private BigDecimal espColumn21;
    private BigDecimal espColumn22;
    private BigDecimal espColumn23;
    private BigDecimal espColumn24;
    private BigDecimal espColumn25;
    private BigDecimal espColumn26;
    private BigDecimal espColumn27;
    private BigDecimal espColumn28;
    private BigDecimal espColumn29;
    private BigDecimal espColumn30;
    private BigDecimal espColumn31;
    private BigDecimal espColumn32;
    private BigDecimal espColumn33;
    private BigDecimal espColumn34;
    private BigDecimal espColumn35;
    private BigDecimal espColumn36;
    private BigDecimal espColumn37;
    private BigDecimal espColumn38;
    private BigDecimal espColumn39;
    private BigDecimal espColumn40;
    private BigDecimal espColumn41;
    private BigDecimal espColumn42;
    private BigDecimal espColumn43;
    private BigDecimal espColumn44;
    private BigDecimal espColumn45;
    private BigDecimal espColumn46;
    private BigDecimal espColumn47;
    private BigDecimal espColumn48;
    private Jobgrade espJobgrade;
    private Department espDept;
    private PositionBase espPbNo;
    private Emptype espEmptype;
    private Employee espEmpno;
    private Location espLocation;
    private Empsalaryconfig espEmpconfig;
    private Empsalaryacctversion espEsavId;

    public BaseEmpsalarypay() {
        initialize();
    }

    public BaseEmpsalarypay(String id) {
        setId(id);
        initialize();
    }

    public BaseEmpsalarypay(String id, Jobgrade espJobgrade, Department espDept,
            Emptype espEmptype, Employee espEmpno, Location espLocation,
            Empsalaryconfig espEmpconfig, Empsalaryacctversion espEsavId, String espYearmonth,
            Integer espBenefitPlans, Integer espChanged) {
        setId(id);
        setEspJobgrade(espJobgrade);
        setEspDept(espDept);
        setEspEmptype(espEmptype);
        setEspEmpno(espEmpno);
        setEspLocation(espLocation);
        setEspEmpconfig(espEmpconfig);
        setEspEsavId(espEsavId);
        setEspYearmonth(espYearmonth);
        setEspBenefitPlans(espBenefitPlans);
        setEspChanged(espChanged);
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

    public String getEspBankName() {
        return this.espBankName;
    }

    public void setEspBankName(String espBankName) {
        this.espBankName = espBankName;
    }

    public String getEspBankAccountNo() {
        return this.espBankAccountNo;
    }

    public void setEspBankAccountNo(String espBankAccountNo) {
        this.espBankAccountNo = espBankAccountNo;
    }

    public String getEspCostCenter() {
        return this.espCostCenter;
    }

    public void setEspCostCenter(String espCostCenter) {
        this.espCostCenter = espCostCenter;
    }

    public String getEspYearmonth() {
        return this.espYearmonth;
    }

    public void setEspYearmonth(String espYearmonth) {
        this.espYearmonth = espYearmonth;
    }

    public Integer getEspBenefitPlans() {
        return this.espBenefitPlans;
    }

    public void setEspBenefitPlans(Integer espBenefitPlans) {
        this.espBenefitPlans = espBenefitPlans;
    }

    public Integer getEspChanged() {
        return this.espChanged;
    }

    public void setEspChanged(Integer espChanged) {
        this.espChanged = espChanged;
    }

    public String getEspComment() {
        return this.espComment;
    }

    public void setEspComment(String espComment) {
        this.espComment = espComment;
    }

    public BigDecimal getEspColumn1() {
        return this.espColumn1;
    }

    public void setEspColumn1(BigDecimal espColumn1) {
        this.espColumn1 = espColumn1;
    }

    public BigDecimal getEspColumn2() {
        return this.espColumn2;
    }

    public void setEspColumn2(BigDecimal espColumn2) {
        this.espColumn2 = espColumn2;
    }

    public BigDecimal getEspColumn3() {
        return this.espColumn3;
    }

    public void setEspColumn3(BigDecimal espColumn3) {
        this.espColumn3 = espColumn3;
    }

    public BigDecimal getEspColumn4() {
        return this.espColumn4;
    }

    public void setEspColumn4(BigDecimal espColumn4) {
        this.espColumn4 = espColumn4;
    }

    public BigDecimal getEspColumn5() {
        return this.espColumn5;
    }

    public void setEspColumn5(BigDecimal espColumn5) {
        this.espColumn5 = espColumn5;
    }

    public BigDecimal getEspColumn6() {
        return this.espColumn6;
    }

    public void setEspColumn6(BigDecimal espColumn6) {
        this.espColumn6 = espColumn6;
    }

    public BigDecimal getEspColumn7() {
        return this.espColumn7;
    }

    public void setEspColumn7(BigDecimal espColumn7) {
        this.espColumn7 = espColumn7;
    }

    public BigDecimal getEspColumn8() {
        return this.espColumn8;
    }

    public void setEspColumn8(BigDecimal espColumn8) {
        this.espColumn8 = espColumn8;
    }

    public BigDecimal getEspColumn9() {
        return this.espColumn9;
    }

    public void setEspColumn9(BigDecimal espColumn9) {
        this.espColumn9 = espColumn9;
    }

    public BigDecimal getEspColumn10() {
        return this.espColumn10;
    }

    public void setEspColumn10(BigDecimal espColumn10) {
        this.espColumn10 = espColumn10;
    }

    public BigDecimal getEspColumn11() {
        return this.espColumn11;
    }

    public void setEspColumn11(BigDecimal espColumn11) {
        this.espColumn11 = espColumn11;
    }

    public BigDecimal getEspColumn12() {
        return this.espColumn12;
    }

    public void setEspColumn12(BigDecimal espColumn12) {
        this.espColumn12 = espColumn12;
    }

    public BigDecimal getEspColumn13() {
        return this.espColumn13;
    }

    public void setEspColumn13(BigDecimal espColumn13) {
        this.espColumn13 = espColumn13;
    }

    public BigDecimal getEspColumn14() {
        return this.espColumn14;
    }

    public void setEspColumn14(BigDecimal espColumn14) {
        this.espColumn14 = espColumn14;
    }

    public BigDecimal getEspColumn15() {
        return this.espColumn15;
    }

    public void setEspColumn15(BigDecimal espColumn15) {
        this.espColumn15 = espColumn15;
    }

    public BigDecimal getEspColumn16() {
        return this.espColumn16;
    }

    public void setEspColumn16(BigDecimal espColumn16) {
        this.espColumn16 = espColumn16;
    }

    public BigDecimal getEspColumn17() {
        return this.espColumn17;
    }

    public void setEspColumn17(BigDecimal espColumn17) {
        this.espColumn17 = espColumn17;
    }

    public BigDecimal getEspColumn18() {
        return this.espColumn18;
    }

    public void setEspColumn18(BigDecimal espColumn18) {
        this.espColumn18 = espColumn18;
    }

    public BigDecimal getEspColumn19() {
        return this.espColumn19;
    }

    public void setEspColumn19(BigDecimal espColumn19) {
        this.espColumn19 = espColumn19;
    }

    public BigDecimal getEspColumn20() {
        return this.espColumn20;
    }

    public void setEspColumn20(BigDecimal espColumn20) {
        this.espColumn20 = espColumn20;
    }

    public BigDecimal getEspColumn21() {
        return this.espColumn21;
    }

    public void setEspColumn21(BigDecimal espColumn21) {
        this.espColumn21 = espColumn21;
    }

    public BigDecimal getEspColumn22() {
        return this.espColumn22;
    }

    public void setEspColumn22(BigDecimal espColumn22) {
        this.espColumn22 = espColumn22;
    }

    public BigDecimal getEspColumn23() {
        return this.espColumn23;
    }

    public void setEspColumn23(BigDecimal espColumn23) {
        this.espColumn23 = espColumn23;
    }

    public BigDecimal getEspColumn24() {
        return this.espColumn24;
    }

    public void setEspColumn24(BigDecimal espColumn24) {
        this.espColumn24 = espColumn24;
    }

    public BigDecimal getEspColumn25() {
        return this.espColumn25;
    }

    public void setEspColumn25(BigDecimal espColumn25) {
        this.espColumn25 = espColumn25;
    }

    public BigDecimal getEspColumn26() {
        return this.espColumn26;
    }

    public void setEspColumn26(BigDecimal espColumn26) {
        this.espColumn26 = espColumn26;
    }

    public BigDecimal getEspColumn27() {
        return this.espColumn27;
    }

    public void setEspColumn27(BigDecimal espColumn27) {
        this.espColumn27 = espColumn27;
    }

    public BigDecimal getEspColumn28() {
        return this.espColumn28;
    }

    public void setEspColumn28(BigDecimal espColumn28) {
        this.espColumn28 = espColumn28;
    }

    public BigDecimal getEspColumn29() {
        return this.espColumn29;
    }

    public void setEspColumn29(BigDecimal espColumn29) {
        this.espColumn29 = espColumn29;
    }

    public BigDecimal getEspColumn30() {
        return this.espColumn30;
    }

    public void setEspColumn30(BigDecimal espColumn30) {
        this.espColumn30 = espColumn30;
    }

    public BigDecimal getEspColumn31() {
        return this.espColumn31;
    }

    public void setEspColumn31(BigDecimal espColumn31) {
        this.espColumn31 = espColumn31;
    }

    public BigDecimal getEspColumn32() {
        return this.espColumn32;
    }

    public void setEspColumn32(BigDecimal espColumn32) {
        this.espColumn32 = espColumn32;
    }

    public BigDecimal getEspColumn33() {
        return this.espColumn33;
    }

    public void setEspColumn33(BigDecimal espColumn33) {
        this.espColumn33 = espColumn33;
    }

    public BigDecimal getEspColumn34() {
        return this.espColumn34;
    }

    public void setEspColumn34(BigDecimal espColumn34) {
        this.espColumn34 = espColumn34;
    }

    public BigDecimal getEspColumn35() {
        return this.espColumn35;
    }

    public void setEspColumn35(BigDecimal espColumn35) {
        this.espColumn35 = espColumn35;
    }

    public BigDecimal getEspColumn36() {
        return this.espColumn36;
    }

    public void setEspColumn36(BigDecimal espColumn36) {
        this.espColumn36 = espColumn36;
    }

    public BigDecimal getEspColumn37() {
        return this.espColumn37;
    }

    public void setEspColumn37(BigDecimal espColumn37) {
        this.espColumn37 = espColumn37;
    }

    public BigDecimal getEspColumn38() {
        return this.espColumn38;
    }

    public void setEspColumn38(BigDecimal espColumn38) {
        this.espColumn38 = espColumn38;
    }

    public BigDecimal getEspColumn39() {
        return this.espColumn39;
    }

    public void setEspColumn39(BigDecimal espColumn39) {
        this.espColumn39 = espColumn39;
    }

    public BigDecimal getEspColumn40() {
        return this.espColumn40;
    }

    public void setEspColumn40(BigDecimal espColumn40) {
        this.espColumn40 = espColumn40;
    }

    public BigDecimal getEspColumn41() {
        return this.espColumn41;
    }

    public void setEspColumn41(BigDecimal espColumn41) {
        this.espColumn41 = espColumn41;
    }

    public BigDecimal getEspColumn42() {
        return this.espColumn42;
    }

    public void setEspColumn42(BigDecimal espColumn42) {
        this.espColumn42 = espColumn42;
    }

    public BigDecimal getEspColumn43() {
        return this.espColumn43;
    }

    public void setEspColumn43(BigDecimal espColumn43) {
        this.espColumn43 = espColumn43;
    }

    public BigDecimal getEspColumn44() {
        return this.espColumn44;
    }

    public void setEspColumn44(BigDecimal espColumn44) {
        this.espColumn44 = espColumn44;
    }

    public BigDecimal getEspColumn45() {
        return this.espColumn45;
    }

    public void setEspColumn45(BigDecimal espColumn45) {
        this.espColumn45 = espColumn45;
    }

    public BigDecimal getEspColumn46() {
        return this.espColumn46;
    }

    public void setEspColumn46(BigDecimal espColumn46) {
        this.espColumn46 = espColumn46;
    }

    public BigDecimal getEspColumn47() {
        return this.espColumn47;
    }

    public void setEspColumn47(BigDecimal espColumn47) {
        this.espColumn47 = espColumn47;
    }

    public BigDecimal getEspColumn48() {
        return this.espColumn48;
    }

    public void setEspColumn48(BigDecimal espColumn48) {
        this.espColumn48 = espColumn48;
    }

    public Jobgrade getEspJobgrade() {
        return this.espJobgrade;
    }

    public void setEspJobgrade(Jobgrade espJobgrade) {
        this.espJobgrade = espJobgrade;
    }

    public Department getEspDept() {
        return this.espDept;
    }

    public void setEspDept(Department espDept) {
        this.espDept = espDept;
    }

    public Emptype getEspEmptype() {
        return this.espEmptype;
    }

    public void setEspEmptype(Emptype espEmptype) {
        this.espEmptype = espEmptype;
    }

    public Employee getEspEmpno() {
        return this.espEmpno;
    }

    public void setEspEmpno(Employee espEmpno) {
        this.espEmpno = espEmpno;
    }

    public Location getEspLocation() {
        return this.espLocation;
    }

    public void setEspLocation(Location espLocation) {
        this.espLocation = espLocation;
    }

    public Empsalaryconfig getEspEmpconfig() {
        return this.espEmpconfig;
    }

    public void setEspEmpconfig(Empsalaryconfig espEmpconfig) {
        this.espEmpconfig = espEmpconfig;
    }

    public Empsalaryacctversion getEspEsavId() {
        return this.espEsavId;
    }

    public void setEspEsavId(Empsalaryacctversion espEsavId) {
        this.espEsavId = espEsavId;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Empsalarypay))
            return false;

        Empsalarypay empsalarypay = (Empsalarypay) obj;
        if ((null == getId()) || (null == empsalarypay.getId()))
            return false;
        return getId().equals(empsalarypay.getId());
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

    public PositionBase getEspPbNo() {
        return this.espPbNo;
    }

    public void setEspPbNo(PositionBase espPbNo) {
        this.espPbNo = espPbNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.domain.base.BaseEmpsalarypay JD-Core Version: 0.5.4
 */