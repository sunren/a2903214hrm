package com.hr.examin.domain.base;

import com.hr.base.BaseDomain;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.examin.domain.Attendmonthly;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;
import java.math.BigDecimal;

public abstract class BaseAttendmonthly extends BaseDomain implements Serializable {
    public static String REF = "Attendmonthly";
    public static String PROP_ATTM_FIELD12 = "attmField12";
    public static String PROP_ATTM_LEAVE_MATERNITY_DAYS = "attmLeaveMaternityDays";
    public static String PROP_ATTM_LEAVE_ANNUAL_HOURS = "attmLeaveAnnualHours";
    public static String PROP_ATTM_FIELD08 = "attmField08";
    public static String PROP_ATTM_DUTY_HOURS = "attmDutyHours";
    public static String PROP_ATTM_FIELD13 = "attmField13";
    public static String PROP_ATTM_LEAVE_OUTER_HOURS = "attmLeaveOuterHours";
    public static String PROP_ATTM_FIELD03 = "attmField03";
    public static String PROP_ATTM_FIELD24 = "attmField24";
    public static String PROP_ATTM_LOCATION = "attmLocation";
    public static String PROP_ATTM_FIELD17 = "attmField17";
    public static String PROP_ATTM_LEAVE_SICK01_DAYS = "attmLeaveSick01Days";
    public static String PROP_ATTM_LEAVE_OTHER_HOURS = "attmLeaveOtherHours";
    public static String PROP_ATTM_ABSENT_HOURS = "attmAbsentHours";
    public static String PROP_ATTM_FIELD10 = "attmField10";
    public static String PROP_ATTM_FIELD23 = "attmField23";
    public static String PROP_ATTM_LEAVE_SICK_HOURS = "attmLeaveSickHours";
    public static String PROP_ATTM_LEAVE_CASUAL_HOURS = "attmLeaveCasualHours";
    public static String PROP_ATTM_FIELD09 = "attmField09";
    public static String PROP_ATTM_EMP_ID = "attmEmpId";
    public static String PROP_ATTM_OVERTIME_HOURS = "attmOvertimeHours";
    public static String PROP_ATTM_FIELD01 = "attmField01";
    public static String PROP_ATTM_LEAVE_SICK02_HOURS = "attmLeaveSick02Hours";
    public static String PROP_ATTM_YEARMONTH = "attmYearmonth";
    public static String PROP_ATTM_LEAVE_SICK_DAYS = "attmLeaveSickDays";
    public static String PROP_ATTM_LEAVE_TIAOXIU01_HOURS = "attmLeaveTiaoxiu01Hours";
    public static String PROP_ATTM_FIELD04 = "attmField04";
    public static String PROP_ATTM_FIELD15 = "attmField15";
    public static String PROP_ATTM_ON_DUTY_DAYS = "attmOnDutyDays";
    public static String PROP_ATTM_FIELD20 = "attmField20";
    public static String PROP_ATTM_MONTH = "attmMonth";
    public static String PROP_ATTM_LEAVE_TRAVEL_DAYS = "attmLeaveTravelDays";
    public static String PROP_ATTM_FIELD14 = "attmField14";
    public static String PROP_ATTM_DUTY_DAYS = "attmDutyDays";
    public static String PROP_ATTM_LEAVE_SICK02_DAYS = "attmLeaveSick02Days";
    public static String PROP_ATTM_OT_HOLIDAY_HOURS = "attmOtHolidayHours";
    public static String PROP_ATTM_LEAVE_SICK01_HOURS = "attmLeaveSick01Hours";
    public static String PROP_ATTM_ON_DUTY_HOURS = "attmOnDutyHours";
    public static String PROP_ATTM_LEAVE_TIAOXIU_DAYS = "attmLeaveTiaoxiuDays";
    public static String PROP_ATTM_LEAVE_ANNUAL_DAYS = "attmLeaveAnnualDays";
    public static String PROP_ATTM_EARLY_LEAVE = "attmEarlyLeave";
    public static String PROP_ATTM_OT_NORMAL_HOURS = "attmOtNormalHours";
    public static String PROP_ATTM_LEAVE_WEDDING_HOURS = "attmLeaveWeddingHours";
    public static String PROP_ATTM_OFF_DUTY_HOURS = "attmOffDutyHours";
    public static String PROP_ATTM_FIELD18 = "attmField18";
    public static String PROP_ATTM_LEAVE_HOURS = "attmLeaveHours";
    public static String PROP_ATTM_FIELD19 = "attmField19";
    public static String PROP_ATTM_LEAVE_MATERNITY_HOURS = "attmLeaveMaternityHours";
    public static String PROP_ATTM_FIELD02 = "attmField02";
    public static String PROP_ATTM_LEAVE_WEDDING_DAYS = "attmLeaveWeddingDays";
    public static String PROP_ATTM_LEAVE_DAYS = "attmLeaveDays";
    public static String PROP_ATTM_LEAVE_TIAOXIU01_DAYS = "attmLeaveTiaoxiu01Days";
    public static String PROP_ATTM_LEAVE_TIAOXIU_HOURS = "attmLeaveTiaoxiuHours";
    public static String PROP_ATTM_EMPTYPE = "attmEmptype";
    public static String PROP_ATTM_OT_WEEKEND_HOURS = "attmOtWeekendHours";
    public static String PROP_ATTM_YEAR = "attmYear";
    public static String PROP_ATTM_LATE_TIMES = "attmLateTimes";
    public static String PROP_ATTM_OFF_DUTY_DAYS = "attmOffDutyDays";
    public static String PROP_ATTM_ABSENT_DAYS = "attmAbsentDays";
    public static String PROP_ATTM_LEAVE_TRAVEL_HOURS = "attmLeaveTravelHours";
    public static String PROP_ATTM_DEPT = "attmDept";
    public static String PROP_ATTM_FIELD21 = "attmField21";
    public static String PROP_ATTM_FIELD07 = "attmField07";
    public static String PROP_ATTM_LEAVE_CASUAL_DAYS = "attmLeaveCasualDays";
    public static String PROP_ATTM_FIELD06 = "attmField06";
    public static String PROP_ATTM_LEAVE_OTHER_DAYS = "attmLeaveOtherDays";
    public static String PROP_ATTM_COMMENTS = "attmComments";
    public static String PROP_ATTM_LEAVE_OUTER_DAYS = "attmLeaveOuterDays";
    public static String PROP_ATTM_FIELD16 = "attmField16";
    public static String PROP_ATTM_FIELD05 = "attmField05";
    public static String PROP_ID = "id";
    public static String PROP_ATTM_FIELD11 = "attmField11";
    public static String PROP_ATTM_FIELD22 = "attmField22";
    public static String PROP_ATTM_PB_NO = "attmPbNo";

    private int hashCode = -2147483648;
    private String id;
    private String attmYearmonth;
    private String attmYear;
    private String attmMonth;
    private BigDecimal attmDutyDays;
    private BigDecimal attmDutyHours;
    private BigDecimal attmOnDutyDays;
    private BigDecimal attmOnDutyHours;
    private BigDecimal attmOffDutyDays;
    private BigDecimal attmOffDutyHours;
    private BigDecimal attmAbsentDays;
    private BigDecimal attmAbsentHours;
    private BigDecimal attmLateTimes;
    private BigDecimal attmEarlyLeave;
    private BigDecimal attmOvertimeHours;
    private BigDecimal attmOtNormalHours;
    private BigDecimal attmOtWeekendHours;
    private BigDecimal attmOtHolidayHours;
    private BigDecimal attmLeaveDays;
    private BigDecimal attmLeaveHours;
    private BigDecimal attmLeaveAnnualDays;
    private BigDecimal attmLeaveAnnualHours;
    private BigDecimal attmLeaveTiaoxiuDays;
    private BigDecimal attmLeaveTiaoxiuHours;
    private BigDecimal attmLeaveTiaoxiu01Days;
    private BigDecimal attmLeaveTiaoxiu01Hours;
    private BigDecimal attmLeaveCasualDays;
    private BigDecimal attmLeaveCasualHours;
    private BigDecimal attmLeaveSickDays;
    private BigDecimal attmLeaveSickHours;
    private BigDecimal attmLeaveSick01Days;
    private BigDecimal attmLeaveSick01Hours;
    private BigDecimal attmLeaveSick02Days;
    private BigDecimal attmLeaveSick02Hours;
    private BigDecimal attmLeaveWeddingDays;
    private BigDecimal attmLeaveWeddingHours;
    private BigDecimal attmLeaveMaternityDays;
    private BigDecimal attmLeaveMaternityHours;
    private BigDecimal attmLeaveTravelDays;
    private BigDecimal attmLeaveTravelHours;
    private BigDecimal attmLeaveOuterDays;
    private BigDecimal attmLeaveOuterHours;
    private BigDecimal attmLeaveOtherDays;
    private BigDecimal attmLeaveOtherHours;
    private String attmField01;
    private String attmField02;
    private String attmField03;
    private String attmField04;
    private String attmField05;
    private String attmField06;
    private String attmField07;
    private String attmField08;
    private String attmField09;
    private String attmField10;
    private String attmField11;
    private String attmField12;
    private String attmField13;
    private String attmField14;
    private String attmField15;
    private String attmField16;
    private String attmField17;
    private String attmField18;
    private String attmField19;
    private String attmField20;
    private String attmField21;
    private String attmField22;
    private String attmField23;
    private String attmField24;
    private String attmComments;
    private Employee attmEmpId;
    private Department attmDept;
    private Location attmLocation;
    private Emptype attmEmptype;
    private PositionBase attmPbNo;

    public BaseAttendmonthly() {
        initialize();
    }

    public BaseAttendmonthly(String id) {
        setId(id);
        initialize();
    }

    public BaseAttendmonthly(String id, Employee attmEmpId, String attmYearmonth, String attmYear,
            String attmMonth, BigDecimal attmDutyDays, BigDecimal attmDutyHours,
            BigDecimal attmOnDutyDays, BigDecimal attmOnDutyHours, BigDecimal attmOffDutyDays,
            BigDecimal attmOffDutyHours) {
        setId(id);
        setAttmEmpId(attmEmpId);
        setAttmYearmonth(attmYearmonth);
        setAttmYear(attmYear);
        setAttmMonth(attmMonth);
        setAttmDutyDays(attmDutyDays);
        setAttmDutyHours(attmDutyHours);
        setAttmOnDutyDays(attmOnDutyDays);
        setAttmOnDutyHours(attmOnDutyHours);
        setAttmOffDutyDays(attmOffDutyDays);
        setAttmOffDutyHours(attmOffDutyHours);
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

    public String getAttmYearmonth() {
        return this.attmYearmonth;
    }

    public void setAttmYearmonth(String attmYearmonth) {
        this.attmYearmonth = attmYearmonth;
    }

    public String getAttmYear() {
        return this.attmYear;
    }

    public void setAttmYear(String attmYear) {
        this.attmYear = attmYear;
    }

    public String getAttmMonth() {
        return this.attmMonth;
    }

    public void setAttmMonth(String attmMonth) {
        this.attmMonth = attmMonth;
    }

    public BigDecimal getAttmDutyDays() {
        return this.attmDutyDays;
    }

    public void setAttmDutyDays(BigDecimal attmDutyDays) {
        this.attmDutyDays = attmDutyDays;
    }

    public BigDecimal getAttmDutyHours() {
        return this.attmDutyHours;
    }

    public void setAttmDutyHours(BigDecimal attmDutyHours) {
        this.attmDutyHours = attmDutyHours;
    }

    public BigDecimal getAttmOnDutyDays() {
        return this.attmOnDutyDays;
    }

    public void setAttmOnDutyDays(BigDecimal attmOnDutyDays) {
        this.attmOnDutyDays = attmOnDutyDays;
    }

    public BigDecimal getAttmOnDutyHours() {
        return this.attmOnDutyHours;
    }

    public void setAttmOnDutyHours(BigDecimal attmOnDutyHours) {
        this.attmOnDutyHours = attmOnDutyHours;
    }

    public BigDecimal getAttmOffDutyDays() {
        return this.attmOffDutyDays;
    }

    public void setAttmOffDutyDays(BigDecimal attmOffDutyDays) {
        this.attmOffDutyDays = attmOffDutyDays;
    }

    public BigDecimal getAttmOffDutyHours() {
        return this.attmOffDutyHours;
    }

    public void setAttmOffDutyHours(BigDecimal attmOffDutyHours) {
        this.attmOffDutyHours = attmOffDutyHours;
    }

    public BigDecimal getAttmAbsentDays() {
        return this.attmAbsentDays;
    }

    public void setAttmAbsentDays(BigDecimal attmAbsentDays) {
        this.attmAbsentDays = attmAbsentDays;
    }

    public BigDecimal getAttmAbsentHours() {
        return this.attmAbsentHours;
    }

    public void setAttmAbsentHours(BigDecimal attmAbsentHours) {
        this.attmAbsentHours = attmAbsentHours;
    }

    public BigDecimal getAttmLateTimes() {
        return this.attmLateTimes;
    }

    public void setAttmLateTimes(BigDecimal attmLateTimes) {
        this.attmLateTimes = attmLateTimes;
    }

    public BigDecimal getAttmEarlyLeave() {
        return this.attmEarlyLeave;
    }

    public void setAttmEarlyLeave(BigDecimal attmEarlyLeave) {
        this.attmEarlyLeave = attmEarlyLeave;
    }

    public BigDecimal getAttmOvertimeHours() {
        return this.attmOvertimeHours;
    }

    public void setAttmOvertimeHours(BigDecimal attmOvertimeHours) {
        this.attmOvertimeHours = attmOvertimeHours;
    }

    public BigDecimal getAttmOtNormalHours() {
        return this.attmOtNormalHours;
    }

    public void setAttmOtNormalHours(BigDecimal attmOtNormalHours) {
        this.attmOtNormalHours = attmOtNormalHours;
    }

    public BigDecimal getAttmOtWeekendHours() {
        return this.attmOtWeekendHours;
    }

    public void setAttmOtWeekendHours(BigDecimal attmOtWeekendHours) {
        this.attmOtWeekendHours = attmOtWeekendHours;
    }

    public BigDecimal getAttmOtHolidayHours() {
        return this.attmOtHolidayHours;
    }

    public void setAttmOtHolidayHours(BigDecimal attmOtHolidayHours) {
        this.attmOtHolidayHours = attmOtHolidayHours;
    }

    public BigDecimal getAttmLeaveDays() {
        return this.attmLeaveDays;
    }

    public void setAttmLeaveDays(BigDecimal attmLeaveDays) {
        this.attmLeaveDays = attmLeaveDays;
    }

    public BigDecimal getAttmLeaveHours() {
        return this.attmLeaveHours;
    }

    public void setAttmLeaveHours(BigDecimal attmLeaveHours) {
        this.attmLeaveHours = attmLeaveHours;
    }

    public BigDecimal getAttmLeaveAnnualDays() {
        return this.attmLeaveAnnualDays;
    }

    public void setAttmLeaveAnnualDays(BigDecimal attmLeaveAnnualDays) {
        this.attmLeaveAnnualDays = attmLeaveAnnualDays;
    }

    public BigDecimal getAttmLeaveAnnualHours() {
        return this.attmLeaveAnnualHours;
    }

    public void setAttmLeaveAnnualHours(BigDecimal attmLeaveAnnualHours) {
        this.attmLeaveAnnualHours = attmLeaveAnnualHours;
    }

    public BigDecimal getAttmLeaveTiaoxiuDays() {
        return this.attmLeaveTiaoxiuDays;
    }

    public void setAttmLeaveTiaoxiuDays(BigDecimal attmLeaveTiaoxiuDays) {
        this.attmLeaveTiaoxiuDays = attmLeaveTiaoxiuDays;
    }

    public BigDecimal getAttmLeaveTiaoxiuHours() {
        return this.attmLeaveTiaoxiuHours;
    }

    public void setAttmLeaveTiaoxiuHours(BigDecimal attmLeaveTiaoxiuHours) {
        this.attmLeaveTiaoxiuHours = attmLeaveTiaoxiuHours;
    }

    public BigDecimal getAttmLeaveTiaoxiu01Days() {
        return this.attmLeaveTiaoxiu01Days;
    }

    public void setAttmLeaveTiaoxiu01Days(BigDecimal attmLeaveTiaoxiu01Days) {
        this.attmLeaveTiaoxiu01Days = attmLeaveTiaoxiu01Days;
    }

    public BigDecimal getAttmLeaveTiaoxiu01Hours() {
        return this.attmLeaveTiaoxiu01Hours;
    }

    public void setAttmLeaveTiaoxiu01Hours(BigDecimal attmLeaveTiaoxiu01Hours) {
        this.attmLeaveTiaoxiu01Hours = attmLeaveTiaoxiu01Hours;
    }

    public BigDecimal getAttmLeaveCasualDays() {
        return this.attmLeaveCasualDays;
    }

    public void setAttmLeaveCasualDays(BigDecimal attmLeaveCasualDays) {
        this.attmLeaveCasualDays = attmLeaveCasualDays;
    }

    public BigDecimal getAttmLeaveCasualHours() {
        return this.attmLeaveCasualHours;
    }

    public void setAttmLeaveCasualHours(BigDecimal attmLeaveCasualHours) {
        this.attmLeaveCasualHours = attmLeaveCasualHours;
    }

    public BigDecimal getAttmLeaveSickDays() {
        return this.attmLeaveSickDays;
    }

    public void setAttmLeaveSickDays(BigDecimal attmLeaveSickDays) {
        this.attmLeaveSickDays = attmLeaveSickDays;
    }

    public BigDecimal getAttmLeaveSickHours() {
        return this.attmLeaveSickHours;
    }

    public void setAttmLeaveSickHours(BigDecimal attmLeaveSickHours) {
        this.attmLeaveSickHours = attmLeaveSickHours;
    }

    public BigDecimal getAttmLeaveSick01Days() {
        return this.attmLeaveSick01Days;
    }

    public void setAttmLeaveSick01Days(BigDecimal attmLeaveSick01Days) {
        this.attmLeaveSick01Days = attmLeaveSick01Days;
    }

    public BigDecimal getAttmLeaveSick01Hours() {
        return this.attmLeaveSick01Hours;
    }

    public void setAttmLeaveSick01Hours(BigDecimal attmLeaveSick01Hours) {
        this.attmLeaveSick01Hours = attmLeaveSick01Hours;
    }

    public BigDecimal getAttmLeaveSick02Days() {
        return this.attmLeaveSick02Days;
    }

    public void setAttmLeaveSick02Days(BigDecimal attmLeaveSick02Days) {
        this.attmLeaveSick02Days = attmLeaveSick02Days;
    }

    public BigDecimal getAttmLeaveSick02Hours() {
        return this.attmLeaveSick02Hours;
    }

    public void setAttmLeaveSick02Hours(BigDecimal attmLeaveSick02Hours) {
        this.attmLeaveSick02Hours = attmLeaveSick02Hours;
    }

    public BigDecimal getAttmLeaveWeddingDays() {
        return this.attmLeaveWeddingDays;
    }

    public void setAttmLeaveWeddingDays(BigDecimal attmLeaveWeddingDays) {
        this.attmLeaveWeddingDays = attmLeaveWeddingDays;
    }

    public BigDecimal getAttmLeaveWeddingHours() {
        return this.attmLeaveWeddingHours;
    }

    public void setAttmLeaveWeddingHours(BigDecimal attmLeaveWeddingHours) {
        this.attmLeaveWeddingHours = attmLeaveWeddingHours;
    }

    public BigDecimal getAttmLeaveMaternityDays() {
        return this.attmLeaveMaternityDays;
    }

    public void setAttmLeaveMaternityDays(BigDecimal attmLeaveMaternityDays) {
        this.attmLeaveMaternityDays = attmLeaveMaternityDays;
    }

    public BigDecimal getAttmLeaveMaternityHours() {
        return this.attmLeaveMaternityHours;
    }

    public void setAttmLeaveMaternityHours(BigDecimal attmLeaveMaternityHours) {
        this.attmLeaveMaternityHours = attmLeaveMaternityHours;
    }

    public BigDecimal getAttmLeaveTravelDays() {
        return this.attmLeaveTravelDays;
    }

    public void setAttmLeaveTravelDays(BigDecimal attmLeaveTravelDays) {
        this.attmLeaveTravelDays = attmLeaveTravelDays;
    }

    public BigDecimal getAttmLeaveTravelHours() {
        return this.attmLeaveTravelHours;
    }

    public void setAttmLeaveTravelHours(BigDecimal attmLeaveTravelHours) {
        this.attmLeaveTravelHours = attmLeaveTravelHours;
    }

    public BigDecimal getAttmLeaveOuterDays() {
        return this.attmLeaveOuterDays;
    }

    public void setAttmLeaveOuterDays(BigDecimal attmLeaveOuterDays) {
        this.attmLeaveOuterDays = attmLeaveOuterDays;
    }

    public BigDecimal getAttmLeaveOuterHours() {
        return this.attmLeaveOuterHours;
    }

    public void setAttmLeaveOuterHours(BigDecimal attmLeaveOuterHours) {
        this.attmLeaveOuterHours = attmLeaveOuterHours;
    }

    public BigDecimal getAttmLeaveOtherDays() {
        return this.attmLeaveOtherDays;
    }

    public void setAttmLeaveOtherDays(BigDecimal attmLeaveOtherDays) {
        this.attmLeaveOtherDays = attmLeaveOtherDays;
    }

    public BigDecimal getAttmLeaveOtherHours() {
        return this.attmLeaveOtherHours;
    }

    public void setAttmLeaveOtherHours(BigDecimal attmLeaveOtherHours) {
        this.attmLeaveOtherHours = attmLeaveOtherHours;
    }

    public String getAttmField01() {
        return this.attmField01;
    }

    public void setAttmField01(String attmField01) {
        this.attmField01 = attmField01;
    }

    public String getAttmField02() {
        return this.attmField02;
    }

    public void setAttmField02(String attmField02) {
        this.attmField02 = attmField02;
    }

    public String getAttmField03() {
        return this.attmField03;
    }

    public void setAttmField03(String attmField03) {
        this.attmField03 = attmField03;
    }

    public String getAttmField04() {
        return this.attmField04;
    }

    public void setAttmField04(String attmField04) {
        this.attmField04 = attmField04;
    }

    public String getAttmField05() {
        return this.attmField05;
    }

    public void setAttmField05(String attmField05) {
        this.attmField05 = attmField05;
    }

    public String getAttmField06() {
        return this.attmField06;
    }

    public void setAttmField06(String attmField06) {
        this.attmField06 = attmField06;
    }

    public String getAttmField07() {
        return this.attmField07;
    }

    public void setAttmField07(String attmField07) {
        this.attmField07 = attmField07;
    }

    public String getAttmField08() {
        return this.attmField08;
    }

    public void setAttmField08(String attmField08) {
        this.attmField08 = attmField08;
    }

    public String getAttmField09() {
        return this.attmField09;
    }

    public void setAttmField09(String attmField09) {
        this.attmField09 = attmField09;
    }

    public String getAttmField10() {
        return this.attmField10;
    }

    public void setAttmField10(String attmField10) {
        this.attmField10 = attmField10;
    }

    public String getAttmField11() {
        return this.attmField11;
    }

    public void setAttmField11(String attmField11) {
        this.attmField11 = attmField11;
    }

    public String getAttmField12() {
        return this.attmField12;
    }

    public void setAttmField12(String attmField12) {
        this.attmField12 = attmField12;
    }

    public String getAttmField13() {
        return this.attmField13;
    }

    public void setAttmField13(String attmField13) {
        this.attmField13 = attmField13;
    }

    public String getAttmField14() {
        return this.attmField14;
    }

    public void setAttmField14(String attmField14) {
        this.attmField14 = attmField14;
    }

    public String getAttmField15() {
        return this.attmField15;
    }

    public void setAttmField15(String attmField15) {
        this.attmField15 = attmField15;
    }

    public String getAttmField16() {
        return this.attmField16;
    }

    public void setAttmField16(String attmField16) {
        this.attmField16 = attmField16;
    }

    public String getAttmField17() {
        return this.attmField17;
    }

    public void setAttmField17(String attmField17) {
        this.attmField17 = attmField17;
    }

    public String getAttmField18() {
        return this.attmField18;
    }

    public void setAttmField18(String attmField18) {
        this.attmField18 = attmField18;
    }

    public String getAttmField19() {
        return this.attmField19;
    }

    public void setAttmField19(String attmField19) {
        this.attmField19 = attmField19;
    }

    public String getAttmField20() {
        return this.attmField20;
    }

    public void setAttmField20(String attmField20) {
        this.attmField20 = attmField20;
    }

    public String getAttmField21() {
        return this.attmField21;
    }

    public void setAttmField21(String attmField21) {
        this.attmField21 = attmField21;
    }

    public String getAttmField22() {
        return this.attmField22;
    }

    public void setAttmField22(String attmField22) {
        this.attmField22 = attmField22;
    }

    public String getAttmField23() {
        return this.attmField23;
    }

    public void setAttmField23(String attmField23) {
        this.attmField23 = attmField23;
    }

    public String getAttmField24() {
        return this.attmField24;
    }

    public void setAttmField24(String attmField24) {
        this.attmField24 = attmField24;
    }

    public String getAttmComments() {
        return this.attmComments;
    }

    public void setAttmComments(String attmComments) {
        this.attmComments = attmComments;
    }

    public Employee getAttmEmpId() {
        return this.attmEmpId;
    }

    public void setAttmEmpId(Employee attmEmpId) {
        this.attmEmpId = attmEmpId;
    }

    public Department getAttmDept() {
        return this.attmDept;
    }

    public void setAttmDept(Department attmDept) {
        this.attmDept = attmDept;
    }

    public Location getAttmLocation() {
        return this.attmLocation;
    }

    public void setAttmLocation(Location attmLocation) {
        this.attmLocation = attmLocation;
    }

    public Emptype getAttmEmptype() {
        return this.attmEmptype;
    }

    public void setAttmEmptype(Emptype attmEmptype) {
        this.attmEmptype = attmEmptype;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Attendmonthly))
            return false;

        Attendmonthly attendmonthly = (Attendmonthly) obj;
        if ((null == getId()) || (null == attendmonthly.getId()))
            return false;
        return getId().equals(attendmonthly.getId());
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

    public PositionBase getAttmPbNo() {
        return this.attmPbNo;
    }

    public void setAttmPbNo(PositionBase attmPbNo) {
        this.attmPbNo = attmPbNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.domain.base.BaseAttendmonthly JD-Core Version: 0.5.4
 */