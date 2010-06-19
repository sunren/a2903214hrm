package com.hr.profile.domain.base;

import com.hr.base.BaseDomain;
import com.hr.compensation.domain.Empbenefit;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.examin.domain.Leavebalance;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.PositionBase;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class BaseEmployee extends BaseDomain implements Serializable {
    public static String REF = "Employee";
    public static String PROP_EMP_POLITICS = "empPolitics";
    public static String PROP_EMP_MACHINE_NO = "empMachineNo";
    public static String PROP_EMP_BENEFIT_TYPE = "empBenefitType";
    public static String PROP_EMP_FNAME = "empFname";
    public static String PROP_EMP_SCHOOL = "empSchool";
    public static String PROP_EMP_IDENTIFICATION_NO = "empIdentificationNo";
    public static String PROP_EMP_CURR_ZIP = "empCurrZip";
    public static String PROP_CONFIG = "config";
    public static String PROP_EMP_DISTINCT_NO = "empDistinctNo";
    public static String PROP_EMP_LAST_CHANGE_BY = "empLastChangeBy";
    public static String PROP_EMP_WORK_DATE = "empWorkDate";
    public static String PROP_EMP_MSN = "empMsn";
    public static String PROP_EMP_MOBILE = "empMobile";
    public static String PROP_EMP_MARITAL = "empMarital";
    public static String PROP_EMP_WORK_PHONE = "empWorkPhone";
    public static String PROP_EMP_TERMINATE_DATE = "empTerminateDate";
    public static String PROP_EMP_HOME_ZIP = "empHomeZip";
    public static String PROP_EMP_CONFIRM_DATE = "empConfirmDate";
    public static String PROP_EMP_EMAIL = "empEmail";
    public static String PROP_EMP_NATION = "empNation";
    public static String PROP_EMP_STATUS = "empStatus";
    public static String PROP_EMP_BLOOD = "empBlood";
    public static String PROP_EMP_TYPE = "empType";
    public static String PROP_EMP_LAST_CHANGE_TIME = "empLastChangeTime";
    public static String PROP_EMP_PERF_DATE = "empPerfDate";
    public static String PROP_EMP_NAME = "empName";
    public static String PROP_EMP_GENDER = "empGender";
    public static String PROP_EMP_BIRTH_DATE = "empBirthDate";
    public static String PROP_EMP_SPECIALITY = "empSpeciality";
    public static String PROP_EMP_URGENT_CON_METHOD = "empUrgentConMethod";
    public static String PROP_EMP_IMAGE = "empImage";
    public static String PROP_EMP_COMMENTS = "empComments";
    public static String PROP_EMP_DIPLOMA = "empDiploma";
    public static String PROP_EMP_CITY_NO = "empCityNo";
    public static String PROP_EMP_HOME_ADDR = "empHomeAddr";
    public static String PROP_EMP_URGENT_CONTACT = "empUrgentContact";
    public static String PROP_EMP_LOCATION_NO = "empLocationNo";
    public static String PROP_EMP_IDENTIFICATION_TYPE = "empIdentificationType";
    public static String PROP_EMP_PERF_RATE = "empPerfRate";
    public static String PROP_EMP_CREATE_TIME = "empCreateTime";
    public static String PROP_EMP_IMPORT_BY_INTERFACE = "empImportByInterface";
    public static String PROP_EMP_SHIFT_TYPE = "empShiftType";
    public static String PROP_EMP_SHIFT_NO = "empShiftNo";
    public static String PROP_EMP_JOIN_DATE = "empJoinDate";
    public static String PROP_EMP_HOME_PHONE = "empHomePhone";
    public static String PROP_EMP_CURR_ADDR = "empCurrAddr";
    public static String PROP_EMP_MNAME = "empMname";
    public static String PROP_EMP_LNAME = "empLname";
    public static String PROP_ID = "id";
    public static String PROP_EMP_DEPT_NO = "empDeptNo";
    public static String PROP_EMP_CREATE_BY = "empCreateBy";
    public static String PROP_EMP_LBSET = "empleaveblances";
    public static String PROP_EMP_CONTRACT = "contract";
    public static String PROP_EMP_RESIDENCE_LOC = "empResidenceLoc";
    public static String PROP_EMP_PROFILE_LOC = "empProfileLoc";
    public static String PROP_EMP_QUIT_ID = "quit";
    public static String PROP_EMP_BENEFIT = "benefit";
    public static String PROP_EMP_PB_NO = "empPbNo";

    private int hashCode = -2147483648;
    private String id;
    private String empDistinctNo;
    private Integer empMachineNo;
    private String empName;
    private String empFname;
    private String empMname;
    private String empLname;
    private Date empJoinDate;
    private Date empWorkDate;
    private Date empConfirmDate;
    private Date empPerfDate;
    private String empPerfRate;
    private String empUrgentContact;
    private String empUrgentConMethod;
    private String empEmail;
    private String empMsn;
    private Integer empStatus;
    private Date empTerminateDate;
    private Integer empImportByInterface = Integer.valueOf(0);
    private Integer empShiftType = Integer.valueOf(0);
    private String empShiftNo;
    private Integer empMarital;
    private Integer empGender;
    private String empNation;
    private String empPolitics;
    private String empDiploma;
    private String empSpeciality;
    private String empSchool;
    private Integer empIdentificationType;
    private String empIdentificationNo;
    private Date empBirthDate;
    private String empHomePhone;
    private String empWorkPhone;
    private String empMobile;
    private String empBlood;
    private String empHomeAddr;
    private String empCurrAddr;
    private String empCurrZip;
    private String empHomeZip;
    private String empImage;
    private String empComments;
    private String empCityNo;
    private Date empCreateTime;
    private Date empLastChangeTime;
    private String empResidenceLoc;
    private String empProfileLoc;
    private String empResume1;
    private String empResume2;
    private String empAdditional1;
    private String empAdditional2;
    private String empAdditional3;
    private String empAdditional4;
    private String empAdditional5;
    private String empAdditional6;
    private String empAdditional7;
    private String empAdditional8;
    private String empAdditional9;
    private String empAdditional10;
    private String empAdditional11;
    private String empAdditional12;
    private String empAdditional13;
    private String empAdditional14;
    private String empAdditional15;
    private String empAdditional16;
    private Empsalaryconfig config;
    private Emptype empType;
    private Department empDeptNo;
    private Employee empCreateBy;
    private Employee empLastChangeBy;
    private Location empLocationNo;
    private Empcontract contract;
    private Empquit quit;
    private BenefitType empBenefitType;
    private Empbenefit benefit;
    private PositionBase empPbNo;
    private Set<Leavebalance> empleaveblances;

    public BaseEmployee() {
        initialize();
    }

    public BaseEmployee(String id) {
        setId(id);
        initialize();
    }

    public BaseEmployee(String id, String empDistinctNo, Integer empMachineNo, Emptype empType,
            Department empDeptNo, String empName, Date empJoinDate, Integer empStatus,
            Integer empGender, Integer empIdentificationType, String empIdentificationNo,
            Date empBirthDate, Date empCreateTime, Date empLastChangeTime) {
        setId(id);
        setEmpDistinctNo(empDistinctNo);
        setEmpMachineNo(empMachineNo);
        setEmpType(empType);
        setEmpDeptNo(empDeptNo);
        setEmpName(empName);
        setEmpJoinDate(empJoinDate);
        setEmpStatus(empStatus);
        setEmpGender(empGender);
        setEmpIdentificationType(empIdentificationType);
        setEmpIdentificationNo(empIdentificationNo);
        setEmpBirthDate(empBirthDate);
        setEmpCreateTime(empCreateTime);
        setEmpLastChangeTime(empLastChangeTime);
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

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpFname() {
        return this.empFname;
    }

    public void setEmpFname(String empFname) {
        this.empFname = empFname;
    }

    public String getEmpLname() {
        return this.empLname;
    }

    public void setEmpLname(String empLname) {
        this.empLname = empLname;
    }

    public Date getEmpJoinDate() {
        return this.empJoinDate;
    }

    public void setEmpJoinDate(Date empJoinDate) {
        this.empJoinDate = empJoinDate;
    }

    public Date getEmpWorkDate() {
        return this.empWorkDate;
    }

    public void setEmpWorkDate(Date empWorkDate) {
        this.empWorkDate = empWorkDate;
    }

    public Date getEmpConfirmDate() {
        return this.empConfirmDate;
    }

    public void setEmpConfirmDate(Date empConfirmDate) {
        this.empConfirmDate = empConfirmDate;
    }

    public Date getEmpPerfDate() {
        return this.empPerfDate;
    }

    public void setEmpPerfDate(Date empPerfDate) {
        this.empPerfDate = empPerfDate;
    }

    public String getEmpPerfRate() {
        return this.empPerfRate;
    }

    public void setEmpPerfRate(String empPerfRate) {
        this.empPerfRate = empPerfRate;
    }

    public String getEmpUrgentContact() {
        return this.empUrgentContact;
    }

    public void setEmpUrgentContact(String empUrgentContact) {
        this.empUrgentContact = empUrgentContact;
    }

    public String getEmpUrgentConMethod() {
        return this.empUrgentConMethod;
    }

    public void setEmpUrgentConMethod(String empUrgentConMethod) {
        this.empUrgentConMethod = empUrgentConMethod;
    }

    public String getEmpEmail() {
        return this.empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpMsn() {
        return this.empMsn;
    }

    public void setEmpMsn(String empMsn) {
        this.empMsn = empMsn;
    }

    public Integer getEmpStatus() {
        return this.empStatus;
    }

    public void setEmpStatus(Integer empStatus) {
        this.empStatus = empStatus;
    }

    public Date getEmpTerminateDate() {
        return this.empTerminateDate;
    }

    public void setEmpTerminateDate(Date empTerminateDate) {
        this.empTerminateDate = empTerminateDate;
    }

    public Integer getEmpImportByInterface() {
        return this.empImportByInterface;
    }

    public void setEmpImportByInterface(Integer empImportByInterface) {
        this.empImportByInterface = empImportByInterface;
    }

    public Integer getEmpMarital() {
        return this.empMarital;
    }

    public void setEmpMarital(Integer empMarital) {
        this.empMarital = empMarital;
    }

    public Integer getEmpGender() {
        return this.empGender;
    }

    public void setEmpGender(Integer empGender) {
        this.empGender = empGender;
    }

    public String getEmpNation() {
        return this.empNation;
    }

    public void setEmpNation(String empNation) {
        this.empNation = empNation;
    }

    public String getEmpPolitics() {
        return this.empPolitics;
    }

    public void setEmpPolitics(String empPolitics) {
        this.empPolitics = empPolitics;
    }

    public String getEmpDiploma() {
        return this.empDiploma;
    }

    public void setEmpDiploma(String empDiploma) {
        this.empDiploma = empDiploma;
    }

    public String getEmpSpeciality() {
        return this.empSpeciality;
    }

    public void setEmpSpeciality(String empSpeciality) {
        this.empSpeciality = empSpeciality;
    }

    public String getEmpSchool() {
        return this.empSchool;
    }

    public void setEmpSchool(String empSchool) {
        this.empSchool = empSchool;
    }

    public Integer getEmpIdentificationType() {
        return this.empIdentificationType;
    }

    public void setEmpIdentificationType(Integer empIdentificationType) {
        this.empIdentificationType = empIdentificationType;
    }

    public String getEmpIdentificationNo() {
        return this.empIdentificationNo;
    }

    public void setEmpIdentificationNo(String empIdentificationNo) {
        this.empIdentificationNo = empIdentificationNo;
    }

    public Date getEmpBirthDate() {
        return this.empBirthDate;
    }

    public void setEmpBirthDate(Date empBirthDate) {
        this.empBirthDate = empBirthDate;
    }

    public String getEmpHomePhone() {
        return this.empHomePhone;
    }

    public void setEmpHomePhone(String empHomePhone) {
        this.empHomePhone = empHomePhone;
    }

    public String getEmpWorkPhone() {
        return this.empWorkPhone;
    }

    public void setEmpWorkPhone(String empWorkPhone) {
        this.empWorkPhone = empWorkPhone;
    }

    public String getEmpMobile() {
        return this.empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public String getEmpBlood() {
        return this.empBlood;
    }

    public void setEmpBlood(String empBlood) {
        this.empBlood = empBlood;
    }

    public String getEmpHomeAddr() {
        return this.empHomeAddr;
    }

    public void setEmpHomeAddr(String empHomeAddr) {
        this.empHomeAddr = empHomeAddr;
    }

    public String getEmpCurrAddr() {
        return this.empCurrAddr;
    }

    public void setEmpCurrAddr(String empCurrAddr) {
        this.empCurrAddr = empCurrAddr;
    }

    public String getEmpCurrZip() {
        return this.empCurrZip;
    }

    public void setEmpCurrZip(String empCurrZip) {
        this.empCurrZip = empCurrZip;
    }

    public String getEmpHomeZip() {
        return this.empHomeZip;
    }

    public void setEmpHomeZip(String empHomeZip) {
        this.empHomeZip = empHomeZip;
    }

    public String getEmpImage() {
        return this.empImage;
    }

    public void setEmpImage(String empImage) {
        this.empImage = empImage;
    }

    public String getEmpComments() {
        return this.empComments;
    }

    public void setEmpComments(String empComments) {
        this.empComments = empComments;
    }

    public String getEmpCityNo() {
        return this.empCityNo;
    }

    public void setEmpCityNo(String empCityNo) {
        this.empCityNo = empCityNo;
    }

    public Date getEmpCreateTime() {
        return this.empCreateTime;
    }

    public void setEmpCreateTime(Date empCreateTime) {
        this.empCreateTime = empCreateTime;
    }

    public Date getEmpLastChangeTime() {
        return this.empLastChangeTime;
    }

    public void setEmpLastChangeTime(Date empLastChangeTime) {
        this.empLastChangeTime = empLastChangeTime;
    }

    public Empsalaryconfig getConfig() {
        return this.config;
    }

    public void setConfig(Empsalaryconfig config) {
        this.config = config;
    }

    public Emptype getEmpType() {
        return this.empType;
    }

    public void setEmpType(Emptype empType) {
        this.empType = empType;
    }

    public Department getEmpDeptNo() {
        return this.empDeptNo;
    }

    public void setEmpDeptNo(Department empDeptNo) {
        this.empDeptNo = empDeptNo;
    }

    public Employee getEmpCreateBy() {
        return this.empCreateBy;
    }

    public void setEmpCreateBy(Employee empCreateBy) {
        this.empCreateBy = empCreateBy;
    }

    public Employee getEmpLastChangeBy() {
        return this.empLastChangeBy;
    }

    public void setEmpLastChangeBy(Employee empLastChangeBy) {
        this.empLastChangeBy = empLastChangeBy;
    }

    public Location getEmpLocationNo() {
        return this.empLocationNo;
    }

    public void setEmpLocationNo(Location empLocationNo) {
        this.empLocationNo = empLocationNo;
    }

    public Set<Leavebalance> getEmpleaveblances() {
        return this.empleaveblances;
    }

    public void setEmpleaveblances(Set<Leavebalance> empleaveblances) {
        this.empleaveblances = empleaveblances;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Employee))
            return false;

        Employee employee = (Employee) obj;
        if ((null == getId()) || (null == employee.getId()))
            return false;
        return getId().equals(employee.getId());
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

    public String getEmpDistinctNo() {
        return this.empDistinctNo;
    }

    public void setEmpDistinctNo(String empDistinctNo) {
        this.empDistinctNo = empDistinctNo;
    }

    public Integer getEmpMachineNo() {
        return this.empMachineNo;
    }

    public void setEmpMachineNo(Integer empMachineNo) {
        this.empMachineNo = empMachineNo;
    }

    public Empcontract getContract() {
        return this.contract;
    }

    public void setContract(Empcontract contract) {
        this.contract = contract;
    }

    public String getEmpResidenceLoc() {
        return this.empResidenceLoc;
    }

    public void setEmpResidenceLoc(String empResidenceLoc) {
        this.empResidenceLoc = empResidenceLoc;
    }

    public String getEmpProfileLoc() {
        return this.empProfileLoc;
    }

    public void setEmpProfileLoc(String empProfileLoc) {
        this.empProfileLoc = empProfileLoc;
    }

    public Empquit getQuit() {
        return this.quit;
    }

    public void setQuit(Empquit quit) {
        this.quit = quit;
    }

    public BenefitType getEmpBenefitType() {
        return this.empBenefitType;
    }

    public void setEmpBenefitType(BenefitType empBenefitType) {
        this.empBenefitType = empBenefitType;
    }

    public Empbenefit getBenefit() {
        return this.benefit;
    }

    public void setBenefit(Empbenefit benefit) {
        this.benefit = benefit;
    }

    public String getEmpMname() {
        return this.empMname;
    }

    public void setEmpMname(String empMname) {
        this.empMname = empMname;
    }

    public Integer getEmpShiftType() {
        return this.empShiftType;
    }

    public void setEmpShiftType(Integer empShiftType) {
        this.empShiftType = empShiftType;
    }

    public String getEmpShiftNo() {
        return this.empShiftNo;
    }

    public void setEmpShiftNo(String empShiftNo) {
        this.empShiftNo = empShiftNo;
    }

    public String getEmpAdditional1() {
        return this.empAdditional1;
    }

    public void setEmpAdditional1(String empAdditional1) {
        this.empAdditional1 = empAdditional1;
    }

    public String getEmpAdditional2() {
        return this.empAdditional2;
    }

    public void setEmpAdditional2(String empAdditional2) {
        this.empAdditional2 = empAdditional2;
    }

    public String getEmpAdditional3() {
        return this.empAdditional3;
    }

    public void setEmpAdditional3(String empAdditional3) {
        this.empAdditional3 = empAdditional3;
    }

    public String getEmpAdditional4() {
        return this.empAdditional4;
    }

    public void setEmpAdditional4(String empAdditional4) {
        this.empAdditional4 = empAdditional4;
    }

    public String getEmpAdditional5() {
        return this.empAdditional5;
    }

    public void setEmpAdditional5(String empAdditional5) {
        this.empAdditional5 = empAdditional5;
    }

    public String getEmpAdditional6() {
        return this.empAdditional6;
    }

    public void setEmpAdditional6(String empAdditional6) {
        this.empAdditional6 = empAdditional6;
    }

    public String getEmpAdditional7() {
        return this.empAdditional7;
    }

    public void setEmpAdditional7(String empAdditional7) {
        this.empAdditional7 = empAdditional7;
    }

    public String getEmpAdditional8() {
        return this.empAdditional8;
    }

    public void setEmpAdditional8(String empAdditional8) {
        this.empAdditional8 = empAdditional8;
    }

    public String getEmpAdditional9() {
        return this.empAdditional9;
    }

    public void setEmpAdditional9(String empAdditional9) {
        this.empAdditional9 = empAdditional9;
    }

    public String getEmpAdditional10() {
        return this.empAdditional10;
    }

    public void setEmpAdditional10(String empAdditional10) {
        this.empAdditional10 = empAdditional10;
    }

    public String getEmpAdditional11() {
        return this.empAdditional11;
    }

    public void setEmpAdditional11(String empAdditional11) {
        this.empAdditional11 = empAdditional11;
    }

    public String getEmpAdditional12() {
        return this.empAdditional12;
    }

    public void setEmpAdditional12(String empAdditional12) {
        this.empAdditional12 = empAdditional12;
    }

    public String getEmpAdditional13() {
        return this.empAdditional13;
    }

    public void setEmpAdditional13(String empAdditional13) {
        this.empAdditional13 = empAdditional13;
    }

    public String getEmpAdditional14() {
        return this.empAdditional14;
    }

    public void setEmpAdditional14(String empAdditional14) {
        this.empAdditional14 = empAdditional14;
    }

    public String getEmpAdditional15() {
        return this.empAdditional15;
    }

    public void setEmpAdditional15(String empAdditional15) {
        this.empAdditional15 = empAdditional15;
    }

    public String getEmpAdditional16() {
        return this.empAdditional16;
    }

    public void setEmpAdditional16(String empAdditional16) {
        this.empAdditional16 = empAdditional16;
    }

    public String getEmpResume1() {
        return this.empResume1;
    }

    public void setEmpResume1(String empResume1) {
        this.empResume1 = empResume1;
    }

    public String getEmpResume2() {
        return this.empResume2;
    }

    public void setEmpResume2(String empResume2) {
        this.empResume2 = empResume2;
    }

    public PositionBase getEmpPbNo() {
        return this.empPbNo;
    }

    public void setEmpPbNo(PositionBase empPbNo) {
        this.empPbNo = empPbNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.base.BaseEmployee JD-Core Version: 0.5.4
 */