package com.hr.compensation.domain.base;

import com.hr.base.DaoBean;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.configuration.domain.Jobgrade;
import com.hr.profile.domain.Employee;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public abstract class BaseEmpsalaryconfig extends DaoBean {
    public static String REF = "Empsalaryconfig";
    public static String PROP_EMPLOYEE = "employee";
    public static String PROP_ESC_LAST_INCR_RATE = "escLastIncrRate";
    public static String PROP_ESC_LAST_INCR_RATE1 = "escLastIncrRate1";
    public static String PROP_ESC_LAST_CHANGE_TIME = "escLastChangeTime";
    public static String PROP_ESC_LAST_EFF_DATE = "escLastEffDate";
    public static String PROP_ESC_CREATE_BY = "escCreateBy";
    public static String PROP_ESC_BANK_ACCOUNT_NO = "escBankAccountNo";
    public static String PROP_ESC_BANK_NAME = "escBankName";
    public static String PROP_ESC_COST_CENTER = "escCostCenter";
    public static String PROP_ESC_JOBGRADE = "escJobgrade";
    public static String PROP_ESC_ESAV_ID = "escEsavId";
    public static String PROP_ESC_CREATE_TIME = "escCreateTime";
    public static String PROP_ESC_LAST_CHANGE_BY = "escLastChangeBy";
    public static String PROP_ID = "id";
    public static String PROP_ESC_COLUMN1 = "escColumn1";
    public static String PROP_ESC_COLUMN2 = "escColumn2";
    public static String PROP_ESC_COLUMN3 = "escColumn3";
    public static String PROP_ESC_COLUMN4 = "escColumn4";
    public static String PROP_ESC_COLUMN5 = "escColumn5";
    public static String PROP_ESC_COLUMN6 = "escColumn6";
    public static String PROP_ESC_COLUMN7 = "escColumn7";
    public static String PROP_ESC_COLUMN8 = "escColumn8";
    public static String PROP_ESC_COLUMN9 = "escColumn9";
    public static String PROP_ESC_COLUMN10 = "escColumn10";
    public static String PROP_ESC_COLUMN11 = "escColumn11";
    public static String PROP_ESC_COLUMN12 = "escColumn12";
    public static String PROP_ESC_COLUMN13 = "escColumn13";
    public static String PROP_ESC_COLUMN14 = "escColumn14";
    public static String PROP_ESC_COLUMN15 = "escColumn15";
    public static String PROP_ESC_COLUMN16 = "escColumn16";
    public static String PROP_ESC_COLUMN17 = "escColumn17";
    public static String PROP_ESC_COLUMN18 = "escColumn18";
    public static String PROP_ESC_COLUMN19 = "escColumn19";
    public static String PROP_ESC_COLUMN20 = "escColumn20";
    public static String PROP_ESC_COLUMN21 = "escColumn21";
    public static String PROP_ESC_COLUMN22 = "escColumn22";
    public static String PROP_ESC_COLUMN23 = "escColumn23";
    public static String PROP_ESC_COLUMN24 = "escColumn24";
    public static String PROP_ESC_COLUMN25 = "escColumn25";
    public static String PROP_ESC_COLUMN26 = "escColumn26";
    public static String PROP_ESC_COLUMN27 = "escColumn27";
    public static String PROP_ESC_COLUMN28 = "escColumn28";
    public static String PROP_ESC_COLUMN29 = "escColumn29";
    public static String PROP_ESC_COLUMN30 = "escColumn30";
    public static String PROP_ESC_COLUMN31 = "escColumn31";
    public static String PROP_ESC_COLUMN32 = "escColumn32";
    public static String PROP_ESC_COLUMN33 = "escColumn33";
    public static String PROP_ESC_COLUMN34 = "escColumn34";
    public static String PROP_ESC_COLUMN35 = "escColumn35";
    public static String PROP_ESC_COLUMN36 = "escColumn36";
    public static String PROP_ESC_COLUMN37 = "escColumn37";
    public static String PROP_ESC_COLUMN38 = "escColumn38";
    public static String PROP_ESC_COLUMN39 = "escColumn39";
    public static String PROP_ESC_COLUMN40 = "escColumn40";
    public static String PROP_ESC_COLUMN41 = "escColumn41";
    public static String PROP_ESC_COLUMN42 = "escColumn42";
    public static String PROP_ESC_COLUMN43 = "escColumn43";
    public static String PROP_ESC_COLUMN44 = "escColumn44";
    public static String PROP_ESC_COLUMN45 = "escColumn45";
    public static String PROP_ESC_COLUMN46 = "escColumn46";
    public static String PROP_ESC_COLUMN47 = "escColumn47";
    public static String PROP_ESC_COLUMN48 = "escColumn48";

    private int hashCode = -2147483648;
    private String id;
    private Date escLastEffDate;
    private BigDecimal escLastIncrRate;
    private BigDecimal escLastIncrRate1;
    private String escBankAccountNo;
    private String escBankName;
    private String escCostCenter;
    private String escCreateBy;
    private Date escCreateTime;
    private String escLastChangeBy;
    private Date escLastChangeTime;
    private BigDecimal escColumn1;
    private BigDecimal escColumn2;
    private BigDecimal escColumn3;
    private BigDecimal escColumn4;
    private BigDecimal escColumn5;
    private BigDecimal escColumn6;
    private BigDecimal escColumn7;
    private BigDecimal escColumn8;
    private BigDecimal escColumn9;
    private BigDecimal escColumn10;
    private BigDecimal escColumn11;
    private BigDecimal escColumn12;
    private BigDecimal escColumn13;
    private BigDecimal escColumn14;
    private BigDecimal escColumn15;
    private BigDecimal escColumn16;
    private BigDecimal escColumn17;
    private BigDecimal escColumn18;
    private BigDecimal escColumn19;
    private BigDecimal escColumn20;
    private BigDecimal escColumn21;
    private BigDecimal escColumn22;
    private BigDecimal escColumn23;
    private BigDecimal escColumn24;
    private BigDecimal escColumn25;
    private BigDecimal escColumn26;
    private BigDecimal escColumn27;
    private BigDecimal escColumn28;
    private BigDecimal escColumn29;
    private BigDecimal escColumn30;
    private BigDecimal escColumn31;
    private BigDecimal escColumn32;
    private BigDecimal escColumn33;
    private BigDecimal escColumn34;
    private BigDecimal escColumn35;
    private BigDecimal escColumn36;
    private BigDecimal escColumn37;
    private BigDecimal escColumn38;
    private BigDecimal escColumn39;
    private BigDecimal escColumn40;
    private BigDecimal escColumn41;
    private BigDecimal escColumn42;
    private BigDecimal escColumn43;
    private BigDecimal escColumn44;
    private BigDecimal escColumn45;
    private BigDecimal escColumn46;
    private BigDecimal escColumn47;
    private BigDecimal escColumn48;
    private Employee employee;
    private Jobgrade escJobgrade;
    private Empsalaryacctversion escEsavId;
    static DecimalFormat df = new DecimalFormat("#.00");

    public BaseEmpsalaryconfig() {
        initialize();
    }

    public BaseEmpsalaryconfig(String id) {
        setId(id);
        initialize();
    }

    public BaseEmpsalaryconfig(String id, Jobgrade escJobgrade, String escCreateBy,
            Date escCreateTime, String escLastChangeBy, Date escLastChangeTime) {
        setId(id);
        setEscJobgrade(escJobgrade);
        setEscCreateBy(escCreateBy);
        setEscCreateTime(escCreateTime);
        setEscLastChangeBy(escLastChangeBy);
        setEscLastChangeTime(escLastChangeTime);
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

    public Date getEscLastEffDate() {
        return this.escLastEffDate;
    }

    public void setEscLastEffDate(Date escLastEffDate) {
        this.escLastEffDate = escLastEffDate;
    }

    public String getEscBankAccountNo() {
        return this.escBankAccountNo;
    }

    public void setEscBankAccountNo(String escBankAccountNo) {
        this.escBankAccountNo = escBankAccountNo;
    }

    public String getEscCreateBy() {
        return this.escCreateBy;
    }

    public void setEscCreateBy(String escCreateBy) {
        this.escCreateBy = escCreateBy;
    }

    public Date getEscCreateTime() {
        return this.escCreateTime;
    }

    public void setEscCreateTime(Date escCreateTime) {
        this.escCreateTime = escCreateTime;
    }

    public String getEscLastChangeBy() {
        return this.escLastChangeBy;
    }

    public void setEscLastChangeBy(String escLastChangeBy) {
        this.escLastChangeBy = escLastChangeBy;
    }

    public Date getEscLastChangeTime() {
        return this.escLastChangeTime;
    }

    public void setEscLastChangeTime(Date escLastChangeTime) {
        this.escLastChangeTime = escLastChangeTime;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Jobgrade getEscJobgrade() {
        return this.escJobgrade;
    }

    public void setEscJobgrade(Jobgrade escJobgrade) {
        this.escJobgrade = escJobgrade;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Empsalaryconfig))
            return false;

        Empsalaryconfig empsalaryconf = (Empsalaryconfig) obj;
        if ((null == getId()) || (null == empsalaryconf.getId()))
            return false;
        return getId().equals(empsalaryconf.getId());
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

    public BigDecimal getEscColumn1() {
        if (this.escColumn1 != null) {
            this.escColumn1 = new BigDecimal(df.format(this.escColumn1.doubleValue()));
        }
        return this.escColumn1;
    }

    public void setEscColumn1(BigDecimal escColumn1) {
        this.escColumn1 = escColumn1;
    }

    public BigDecimal getEscColumn10() {
        if (this.escColumn10 != null) {
            this.escColumn10 = new BigDecimal(df.format(this.escColumn10.doubleValue()));
        }
        return this.escColumn10;
    }

    public void setEscColumn10(BigDecimal escColumn10) {
        this.escColumn10 = escColumn10;
    }

    public BigDecimal getEscColumn11() {
        if (this.escColumn11 != null) {
            this.escColumn11 = new BigDecimal(df.format(this.escColumn11.doubleValue()));
        }
        return this.escColumn11;
    }

    public void setEscColumn11(BigDecimal escColumn11) {
        this.escColumn11 = escColumn11;
    }

    public BigDecimal getEscColumn12() {
        if (this.escColumn12 != null) {
            this.escColumn12 = new BigDecimal(df.format(this.escColumn12.doubleValue()));
        }
        return this.escColumn12;
    }

    public void setEscColumn12(BigDecimal escColumn12) {
        this.escColumn12 = escColumn12;
    }

    public BigDecimal getEscColumn13() {
        if (this.escColumn13 != null) {
            this.escColumn13 = new BigDecimal(df.format(this.escColumn13.doubleValue()));
        }
        return this.escColumn13;
    }

    public void setEscColumn13(BigDecimal escColumn13) {
        this.escColumn13 = escColumn13;
    }

    public BigDecimal getEscColumn14() {
        if (this.escColumn14 != null) {
            this.escColumn14 = new BigDecimal(df.format(this.escColumn14.doubleValue()));
        }
        return this.escColumn14;
    }

    public void setEscColumn14(BigDecimal escColumn14) {
        this.escColumn14 = escColumn14;
    }

    public BigDecimal getEscColumn15() {
        if (this.escColumn15 != null) {
            this.escColumn15 = new BigDecimal(df.format(this.escColumn15.doubleValue()));
        }
        return this.escColumn15;
    }

    public void setEscColumn15(BigDecimal escColumn15) {
        this.escColumn15 = escColumn15;
    }

    public BigDecimal getEscColumn16() {
        if (this.escColumn16 != null) {
            this.escColumn16 = new BigDecimal(df.format(this.escColumn16.doubleValue()));
        }
        return this.escColumn16;
    }

    public void setEscColumn16(BigDecimal escColumn16) {
        this.escColumn16 = escColumn16;
    }

    public BigDecimal getEscColumn17() {
        if (this.escColumn17 != null) {
            this.escColumn17 = new BigDecimal(df.format(this.escColumn17.doubleValue()));
        }
        return this.escColumn17;
    }

    public void setEscColumn17(BigDecimal escColumn17) {
        this.escColumn17 = escColumn17;
    }

    public BigDecimal getEscColumn18() {
        if (this.escColumn18 != null) {
            this.escColumn18 = new BigDecimal(df.format(this.escColumn18.doubleValue()));
        }
        return this.escColumn18;
    }

    public void setEscColumn18(BigDecimal escColumn18) {
        this.escColumn18 = escColumn18;
    }

    public BigDecimal getEscColumn19() {
        if (this.escColumn19 != null) {
            this.escColumn19 = new BigDecimal(df.format(this.escColumn19.doubleValue()));
        }
        return this.escColumn19;
    }

    public void setEscColumn19(BigDecimal escColumn19) {
        this.escColumn19 = escColumn19;
    }

    public BigDecimal getEscColumn2() {
        if (this.escColumn2 != null) {
            this.escColumn2 = new BigDecimal(df.format(this.escColumn2.doubleValue()));
        }
        return this.escColumn2;
    }

    public void setEscColumn2(BigDecimal escColumn2) {
        this.escColumn2 = escColumn2;
    }

    public BigDecimal getEscColumn20() {
        if (this.escColumn20 != null) {
            this.escColumn20 = new BigDecimal(df.format(this.escColumn20.doubleValue()));
        }
        return this.escColumn20;
    }

    public void setEscColumn20(BigDecimal escColumn20) {
        this.escColumn20 = escColumn20;
    }

    public BigDecimal getEscColumn21() {
        if (this.escColumn21 != null) {
            this.escColumn21 = new BigDecimal(df.format(this.escColumn21.doubleValue()));
        }
        return this.escColumn21;
    }

    public void setEscColumn21(BigDecimal escColumn21) {
        this.escColumn21 = escColumn21;
    }

    public BigDecimal getEscColumn22() {
        if (this.escColumn22 != null) {
            this.escColumn22 = new BigDecimal(df.format(this.escColumn22.doubleValue()));
        }
        return this.escColumn22;
    }

    public void setEscColumn22(BigDecimal escColumn22) {
        this.escColumn22 = escColumn22;
    }

    public BigDecimal getEscColumn23() {
        if (this.escColumn23 != null) {
            this.escColumn23 = new BigDecimal(df.format(this.escColumn23.doubleValue()));
        }
        return this.escColumn23;
    }

    public void setEscColumn23(BigDecimal escColumn23) {
        this.escColumn23 = escColumn23;
    }

    public BigDecimal getEscColumn24() {
        if (this.escColumn24 != null) {
            this.escColumn24 = new BigDecimal(df.format(this.escColumn24.doubleValue()));
        }
        return this.escColumn24;
    }

    public void setEscColumn24(BigDecimal escColumn24) {
        this.escColumn24 = escColumn24;
    }

    public BigDecimal getEscColumn25() {
        if (this.escColumn25 != null) {
            this.escColumn25 = new BigDecimal(df.format(this.escColumn25.doubleValue()));
        }
        return this.escColumn25;
    }

    public void setEscColumn25(BigDecimal escColumn25) {
        this.escColumn25 = escColumn25;
    }

    public BigDecimal getEscColumn26() {
        if (this.escColumn26 != null) {
            this.escColumn26 = new BigDecimal(df.format(this.escColumn26.doubleValue()));
        }
        return this.escColumn26;
    }

    public void setEscColumn26(BigDecimal escColumn26) {
        this.escColumn26 = escColumn26;
    }

    public BigDecimal getEscColumn27() {
        if (this.escColumn27 != null) {
            this.escColumn27 = new BigDecimal(df.format(this.escColumn27.doubleValue()));
        }
        return this.escColumn27;
    }

    public void setEscColumn27(BigDecimal escColumn27) {
        this.escColumn27 = escColumn27;
    }

    public BigDecimal getEscColumn28() {
        if (this.escColumn28 != null) {
            this.escColumn28 = new BigDecimal(df.format(this.escColumn28.doubleValue()));
        }
        return this.escColumn28;
    }

    public void setEscColumn28(BigDecimal escColumn28) {
        this.escColumn28 = escColumn28;
    }

    public BigDecimal getEscColumn29() {
        if (this.escColumn29 != null) {
            this.escColumn29 = new BigDecimal(df.format(this.escColumn29.doubleValue()));
        }
        return this.escColumn29;
    }

    public void setEscColumn29(BigDecimal escColumn29) {
        this.escColumn29 = escColumn29;
    }

    public BigDecimal getEscColumn3() {
        if (this.escColumn3 != null) {
            this.escColumn3 = new BigDecimal(df.format(this.escColumn3.doubleValue()));
        }
        return this.escColumn3;
    }

    public void setEscColumn3(BigDecimal escColumn3) {
        this.escColumn3 = escColumn3;
    }

    public BigDecimal getEscColumn30() {
        if (this.escColumn30 != null) {
            this.escColumn30 = new BigDecimal(df.format(this.escColumn30.doubleValue()));
        }
        return this.escColumn30;
    }

    public void setEscColumn30(BigDecimal escColumn30) {
        this.escColumn30 = escColumn30;
    }

    public BigDecimal getEscColumn31() {
        if (this.escColumn31 != null) {
            this.escColumn31 = new BigDecimal(df.format(this.escColumn31.doubleValue()));
        }
        return this.escColumn31;
    }

    public void setEscColumn31(BigDecimal escColumn31) {
        this.escColumn31 = escColumn31;
    }

    public BigDecimal getEscColumn32() {
        if (this.escColumn32 != null) {
            this.escColumn32 = new BigDecimal(df.format(this.escColumn32.doubleValue()));
        }
        return this.escColumn32;
    }

    public void setEscColumn32(BigDecimal escColumn32) {
        this.escColumn32 = escColumn32;
    }

    public BigDecimal getEscColumn4() {
        if (this.escColumn4 != null) {
            this.escColumn4 = new BigDecimal(df.format(this.escColumn4.doubleValue()));
        }
        return this.escColumn4;
    }

    public void setEscColumn4(BigDecimal escColumn4) {
        this.escColumn4 = escColumn4;
    }

    public BigDecimal getEscColumn5() {
        if (this.escColumn5 != null) {
            this.escColumn5 = new BigDecimal(df.format(this.escColumn5.doubleValue()));
        }
        return this.escColumn5;
    }

    public void setEscColumn5(BigDecimal escColumn5) {
        this.escColumn5 = escColumn5;
    }

    public BigDecimal getEscColumn6() {
        if (this.escColumn6 != null) {
            this.escColumn6 = new BigDecimal(df.format(this.escColumn6.doubleValue()));
        }
        return this.escColumn6;
    }

    public void setEscColumn6(BigDecimal escColumn6) {
        this.escColumn6 = escColumn6;
    }

    public BigDecimal getEscColumn7() {
        if (this.escColumn7 != null) {
            this.escColumn7 = new BigDecimal(df.format(this.escColumn7.doubleValue()));
        }
        return this.escColumn7;
    }

    public void setEscColumn7(BigDecimal escColumn7) {
        this.escColumn7 = escColumn7;
    }

    public BigDecimal getEscColumn8() {
        if (this.escColumn8 != null) {
            this.escColumn8 = new BigDecimal(df.format(this.escColumn8.doubleValue()));
        }
        return this.escColumn8;
    }

    public void setEscColumn8(BigDecimal escColumn8) {
        this.escColumn8 = escColumn8;
    }

    public BigDecimal getEscColumn9() {
        if (this.escColumn9 != null) {
            this.escColumn9 = new BigDecimal(df.format(this.escColumn9.doubleValue()));
        }
        return this.escColumn9;
    }

    public void setEscColumn9(BigDecimal escColumn9) {
        this.escColumn9 = escColumn9;
    }

    public BigDecimal getEscLastIncrRate() {
        return this.escLastIncrRate;
    }

    public void setEscLastIncrRate(BigDecimal escLastIncrRate) {
        this.escLastIncrRate = escLastIncrRate;
    }

    public BigDecimal getEscLastIncrRate1() {
        return this.escLastIncrRate1;
    }

    public void setEscLastIncrRate1(BigDecimal escLastIncrRate1) {
        this.escLastIncrRate1 = escLastIncrRate1;
    }

    public BigDecimal getEscColumn33() {
        if (this.escColumn33 != null) {
            this.escColumn33 = new BigDecimal(df.format(this.escColumn33.doubleValue()));
        }
        return this.escColumn33;
    }

    public void setEscColumn33(BigDecimal escColumn33) {
        this.escColumn33 = escColumn33;
    }

    public BigDecimal getEscColumn34() {
        if (this.escColumn34 != null) {
            this.escColumn34 = new BigDecimal(df.format(this.escColumn34.doubleValue()));
        }
        return this.escColumn34;
    }

    public void setEscColumn34(BigDecimal escColumn34) {
        this.escColumn34 = escColumn34;
    }

    public BigDecimal getEscColumn35() {
        if (this.escColumn35 != null) {
            this.escColumn35 = new BigDecimal(df.format(this.escColumn35.doubleValue()));
        }
        return this.escColumn35;
    }

    public void setEscColumn35(BigDecimal escColumn35) {
        this.escColumn35 = escColumn35;
    }

    public BigDecimal getEscColumn36() {
        if (this.escColumn36 != null) {
            this.escColumn36 = new BigDecimal(df.format(this.escColumn36.doubleValue()));
        }
        return this.escColumn36;
    }

    public void setEscColumn36(BigDecimal escColumn36) {
        this.escColumn36 = escColumn36;
    }

    public BigDecimal getEscColumn37() {
        if (this.escColumn37 != null) {
            this.escColumn37 = new BigDecimal(df.format(this.escColumn37.doubleValue()));
        }
        return this.escColumn37;
    }

    public void setEscColumn37(BigDecimal escColumn37) {
        this.escColumn37 = escColumn37;
    }

    public BigDecimal getEscColumn38() {
        if (this.escColumn38 != null) {
            this.escColumn38 = new BigDecimal(df.format(this.escColumn38.doubleValue()));
        }
        return this.escColumn38;
    }

    public void setEscColumn38(BigDecimal escColumn38) {
        this.escColumn38 = escColumn38;
    }

    public BigDecimal getEscColumn39() {
        if (this.escColumn39 != null) {
            this.escColumn39 = new BigDecimal(df.format(this.escColumn39.doubleValue()));
        }
        return this.escColumn39;
    }

    public void setEscColumn39(BigDecimal escColumn39) {
        this.escColumn39 = escColumn39;
    }

    public BigDecimal getEscColumn40() {
        if (this.escColumn40 != null) {
            this.escColumn40 = new BigDecimal(df.format(this.escColumn40.doubleValue()));
        }
        return this.escColumn40;
    }

    public void setEscColumn40(BigDecimal escColumn40) {
        this.escColumn40 = escColumn40;
    }

    public BigDecimal getEscColumn41() {
        if (this.escColumn41 != null) {
            this.escColumn41 = new BigDecimal(df.format(this.escColumn41.doubleValue()));
        }
        return this.escColumn41;
    }

    public void setEscColumn41(BigDecimal escColumn41) {
        this.escColumn41 = escColumn41;
    }

    public BigDecimal getEscColumn42() {
        if (this.escColumn42 != null) {
            this.escColumn42 = new BigDecimal(df.format(this.escColumn42.doubleValue()));
        }
        return this.escColumn42;
    }

    public void setEscColumn42(BigDecimal escColumn42) {
        this.escColumn42 = escColumn42;
    }

    public BigDecimal getEscColumn43() {
        if (this.escColumn43 != null) {
            this.escColumn43 = new BigDecimal(df.format(this.escColumn43.doubleValue()));
        }
        return this.escColumn43;
    }

    public void setEscColumn43(BigDecimal escColumn43) {
        this.escColumn43 = escColumn43;
    }

    public BigDecimal getEscColumn44() {
        if (this.escColumn44 != null) {
            this.escColumn44 = new BigDecimal(df.format(this.escColumn44.doubleValue()));
        }
        return this.escColumn44;
    }

    public void setEscColumn44(BigDecimal escColumn44) {
        this.escColumn44 = escColumn44;
    }

    public BigDecimal getEscColumn45() {
        if (this.escColumn45 != null) {
            this.escColumn45 = new BigDecimal(df.format(this.escColumn45.doubleValue()));
        }
        return this.escColumn45;
    }

    public void setEscColumn45(BigDecimal escColumn45) {
        this.escColumn45 = escColumn45;
    }

    public BigDecimal getEscColumn46() {
        if (this.escColumn46 != null) {
            this.escColumn46 = new BigDecimal(df.format(this.escColumn46.doubleValue()));
        }
        return this.escColumn46;
    }

    public void setEscColumn46(BigDecimal escColumn46) {
        this.escColumn46 = escColumn46;
    }

    public BigDecimal getEscColumn47() {
        if (this.escColumn47 != null) {
            this.escColumn47 = new BigDecimal(df.format(this.escColumn47.doubleValue()));
        }
        return this.escColumn47;
    }

    public void setEscColumn47(BigDecimal escColumn47) {
        this.escColumn47 = escColumn47;
    }

    public BigDecimal getEscColumn48() {
        if (this.escColumn48 != null) {
            this.escColumn48 = new BigDecimal(df.format(this.escColumn48.doubleValue()));
        }
        return this.escColumn48;
    }

    public void setEscColumn48(BigDecimal escColumn48) {
        this.escColumn48 = escColumn48;
    }

    public String getEscBankName() {
        return this.escBankName;
    }

    public void setEscBankName(String escBankName) {
        this.escBankName = escBankName;
    }

    public Empsalaryacctversion getEscEsavId() {
        return this.escEsavId;
    }

    public void setEscEsavId(Empsalaryacctversion escEsavId) {
        this.escEsavId = escEsavId;
    }

    public String getEscCostCenter() {
        return this.escCostCenter;
    }

    public void setEscCostCenter(String escCostCenter) {
        this.escCostCenter = escCostCenter;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.domain.base.BaseEmpsalaryconfig JD-Core Version: 0.5.4
 */