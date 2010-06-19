package com.hr.compensation.domain.base;

import com.hr.base.DaoBean;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.configuration.domain.Ecptype;
import com.hr.configuration.domain.Jobgrade;
import com.hr.profile.domain.Employee;
import java.math.BigDecimal;
import java.util.Date;

public abstract class BaseEmpsalaryadj extends DaoBean {
    public static String REF = "Empcompaplan";
    public static String PROP_ESA_LAST_CHANGE_TIME = "esaLastChangeTime";
    public static String PROP_ESA_INCRRATE_CUR = "esaIncrrateCur";
    public static String PROP_ESA_ECPTYPE_ID = "esaEcptypeId";
    public static String PROP_ESA_COMMENTS = "esaComments";
    public static String PROP_ESA_CREATE_BY = "esaCreateBy";
    public static String PROP_ESA_JOBTITLE_CUR = "esaJobtitleCur";
    public static String PROP_ESA_JOBGRADE_PRO = "esaJobgradePro";
    public static String PROP_ESA_EFFDATE_PRO = "esaCurEffDate";
    public static String PROP_ESA_LAST_CHANGE_BY = "esaLastChangeBy";
    public static String PROP_ESA_NEXT_APPROVER = "esaNextApprover";
    public static String PROP_ESA_EMPNO = "esaEmpno";
    public static String PROP_ESA_EFFDATE_CUR = "esaEffdateCur";
    public static String PROP_ESA_STATUS = "esaStatus";
    public static String PROP_ESA_INCRRATE_PRO = "esaIncrratePro";
    public static String PROP_ESA_CREATE_TIME = "esaCreateTime";
    public static String PROP_ESA_JOBTITLE_PRO = "esaJobtitlePro";
    public static String PROP_ESA_JOBGRADE_CUR = "esaJobgradeCur";
    public static String PROP_ESA_NEED_GM_APPROVE = "esaNeedGmApprove";

    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private BigDecimal esaColumn1Cur;
    private BigDecimal esaColumn2Cur;
    private BigDecimal esaColumn3Cur;
    private BigDecimal esaColumn4Cur;
    private BigDecimal esaColumn5Cur;
    private BigDecimal esaColumn6Cur;
    private BigDecimal esaColumn7Cur;
    private BigDecimal esaColumn8Cur;
    private BigDecimal esaColumn9Cur;
    private BigDecimal esaColumn10Cur;
    private BigDecimal esaColumn11Cur;
    private BigDecimal esaColumn12Cur;
    private BigDecimal esaColumn13Cur;
    private BigDecimal esaColumn14Cur;
    private BigDecimal esaColumn15Cur;
    private BigDecimal esaColumn16Cur;
    private BigDecimal esaColumn17Cur;
    private BigDecimal esaColumn18Cur;
    private BigDecimal esaColumn19Cur;
    private BigDecimal esaColumn20Cur;
    private BigDecimal esaColumn21Cur;
    private BigDecimal esaColumn22Cur;
    private BigDecimal esaColumn23Cur;
    private BigDecimal esaColumn24Cur;
    private BigDecimal esaColumn25Cur;
    private BigDecimal esaColumn26Cur;
    private BigDecimal esaColumn27Cur;
    private BigDecimal esaColumn28Cur;
    private BigDecimal esaColumn29Cur;
    private BigDecimal esaColumn30Cur;
    private BigDecimal esaColumn31Cur;
    private BigDecimal esaColumn32Cur;
    private BigDecimal esaColumn33Cur;
    private BigDecimal esaColumn34Cur;
    private BigDecimal esaColumn35Cur;
    private BigDecimal esaColumn36Cur;
    private BigDecimal esaColumn37Cur;
    private BigDecimal esaColumn38Cur;
    private BigDecimal esaColumn39Cur;
    private BigDecimal esaColumn40Cur;
    private BigDecimal esaColumn41Cur;
    private BigDecimal esaColumn42Cur;
    private BigDecimal esaColumn43Cur;
    private BigDecimal esaColumn44Cur;
    private BigDecimal esaColumn45Cur;
    private BigDecimal esaColumn46Cur;
    private BigDecimal esaColumn47Cur;
    private BigDecimal esaColumn48Cur;
    private BigDecimal esaColumn1Pro;
    private BigDecimal esaColumn2Pro;
    private BigDecimal esaColumn3Pro;
    private BigDecimal esaColumn4Pro;
    private BigDecimal esaColumn5Pro;
    private BigDecimal esaColumn6Pro;
    private BigDecimal esaColumn7Pro;
    private BigDecimal esaColumn8Pro;
    private BigDecimal esaColumn9Pro;
    private BigDecimal esaColumn10Pro;
    private BigDecimal esaColumn11Pro;
    private BigDecimal esaColumn12Pro;
    private BigDecimal esaColumn13Pro;
    private BigDecimal esaColumn14Pro;
    private BigDecimal esaColumn15Pro;
    private BigDecimal esaColumn16Pro;
    private BigDecimal esaColumn17Pro;
    private BigDecimal esaColumn18Pro;
    private BigDecimal esaColumn19Pro;
    private BigDecimal esaColumn20Pro;
    private BigDecimal esaColumn21Pro;
    private BigDecimal esaColumn22Pro;
    private BigDecimal esaColumn23Pro;
    private BigDecimal esaColumn24Pro;
    private BigDecimal esaColumn25Pro;
    private BigDecimal esaColumn26Pro;
    private BigDecimal esaColumn27Pro;
    private BigDecimal esaColumn28Pro;
    private BigDecimal esaColumn29Pro;
    private BigDecimal esaColumn30Pro;
    private BigDecimal esaColumn31Pro;
    private BigDecimal esaColumn32Pro;
    private BigDecimal esaColumn33Pro;
    private BigDecimal esaColumn34Pro;
    private BigDecimal esaColumn35Pro;
    private BigDecimal esaColumn36Pro;
    private BigDecimal esaColumn37Pro;
    private BigDecimal esaColumn38Pro;
    private BigDecimal esaColumn39Pro;
    private BigDecimal esaColumn40Pro;
    private BigDecimal esaColumn41Pro;
    private BigDecimal esaColumn42Pro;
    private BigDecimal esaColumn43Pro;
    private BigDecimal esaColumn44Pro;
    private BigDecimal esaColumn45Pro;
    private BigDecimal esaColumn46Pro;
    private BigDecimal esaColumn47Pro;
    private BigDecimal esaColumn48Pro;
    private String esaComments;
    private Date esaCreateTime;
    private Date esaCurEffDate;
    private Date esaLastChangeTime;
    private Integer esaStatus;
    private Integer esaChanged;
    private Integer esaNeedGmApprove;
    private BigDecimal esaCurIncrRate;
    private BigDecimal esaCurIncrRate1;
    private Employee esaCreateBy;
    private Employee esaEmpno;
    private Jobgrade esaJobgradeCur;
    private Jobgrade esaJobgradePro;
    private Employee esaLastChangeBy;
    private Employee esaNextApprover;
    private Ecptype esaEcptypeId;
    private Empsalaryacctversion esaEsavIdCur;
    private Empsalaryacctversion esaEsavIdPro;

    public BaseEmpsalaryadj() {
        initialize();
    }

    public BaseEmpsalaryadj(String id) {
        setId(id);
        initialize();
    }

    public BaseEmpsalaryadj(String id, Employee esaEmpno, Employee esaLastChangeBy,
            Ecptype esaEcptypeId, Employee esaCreateBy, Integer esaStatus, Date esaCreateTime,
            Date esaLastChangeTimee) {
        setId(id);
        setEsaEmpno(esaEmpno);
        setEsaLastChangeBy(esaLastChangeBy);
        setEsaEcptypeId(esaEcptypeId);
        setEsaCreateBy(esaCreateBy);
        setEsaStatus(esaStatus);
        setEsaCreateTime(esaCreateTime);
        setEsaLastChangeTime(esaLastChangeTimee);
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

    public String getEsaComments() {
        return this.esaComments;
    }

    public void setEsaComments(String esaComments) {
        this.esaComments = esaComments;
    }

    public Date getEsaCreateTime() {
        return this.esaCreateTime;
    }

    public void setEsaCreateTime(Date esaCreateDate) {
        this.esaCreateTime = esaCreateDate;
    }

    public Date getEsaCurEffDate() {
        return this.esaCurEffDate;
    }

    public void setEsaCurEffDate(Date esaEffdateCur) {
        this.esaCurEffDate = esaEffdateCur;
    }

    public Date getEsaLastChangeTime() {
        return this.esaLastChangeTime;
    }

    public void setEsaLastChangeTime(Date esaLastChangeDate) {
        this.esaLastChangeTime = esaLastChangeDate;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Empsalaryadj))
            return false;

        Empsalaryadj empcompaplan = (Empsalaryadj) obj;
        if ((null == getId()) || (null == empcompaplan.getId()))
            return false;
        return getId().equals(empcompaplan.getId());
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

    public Employee getEsaCreateBy() {
        return this.esaCreateBy;
    }

    public void setEsaCreateBy(Employee esaCreateBy) {
        this.esaCreateBy = esaCreateBy;
    }

    public Employee getEsaEmpno() {
        return this.esaEmpno;
    }

    public void setEsaEmpno(Employee esaEmpno) {
        this.esaEmpno = esaEmpno;
    }

    public Jobgrade getEsaJobgradeCur() {
        return this.esaJobgradeCur;
    }

    public void setEsaJobgradeCur(Jobgrade esaJobgradeCur) {
        this.esaJobgradeCur = esaJobgradeCur;
    }

    public Jobgrade getEsaJobgradePro() {
        return this.esaJobgradePro;
    }

    public void setEsaJobgradePro(Jobgrade esaJobgradePro) {
        this.esaJobgradePro = esaJobgradePro;
    }

    public Employee getEsaLastChangeBy() {
        return this.esaLastChangeBy;
    }

    public void setEsaLastChangeBy(Employee esaLastChangeBy) {
        this.esaLastChangeBy = esaLastChangeBy;
    }

    public Employee getEsaNextApprover() {
        return this.esaNextApprover;
    }

    public void setEsaNextApprover(Employee esaNextApprover) {
        this.esaNextApprover = esaNextApprover;
    }

    public Integer getEsaStatus() {
        return this.esaStatus;
    }

    public void setEsaStatus(Integer esaStatus) {
        this.esaStatus = esaStatus;
    }

    public Ecptype getEsaEcptypeId() {
        return this.esaEcptypeId;
    }

    public void setEsaEcptypeId(Ecptype esaType) {
        this.esaEcptypeId = esaType;
    }

    public BigDecimal getEsaColumn10Cur() {
        return this.esaColumn10Cur;
    }

    public void setEsaColumn10Cur(BigDecimal esaColumn10Cur) {
        this.esaColumn10Cur = esaColumn10Cur;
    }

    public BigDecimal getEsaColumn10Pro() {
        return this.esaColumn10Pro;
    }

    public void setEsaColumn10Pro(BigDecimal esaColumn10Pro) {
        this.esaColumn10Pro = esaColumn10Pro;
    }

    public BigDecimal getEsaColumn11Cur() {
        return this.esaColumn11Cur;
    }

    public void setEsaColumn11Cur(BigDecimal esaColumn11Cur) {
        this.esaColumn11Cur = esaColumn11Cur;
    }

    public BigDecimal getEsaColumn11Pro() {
        return this.esaColumn11Pro;
    }

    public void setEsaColumn11Pro(BigDecimal esaColumn11Pro) {
        this.esaColumn11Pro = esaColumn11Pro;
    }

    public BigDecimal getEsaColumn12Cur() {
        return this.esaColumn12Cur;
    }

    public void setEsaColumn12Cur(BigDecimal esaColumn12Cur) {
        this.esaColumn12Cur = esaColumn12Cur;
    }

    public BigDecimal getEsaColumn12Pro() {
        return this.esaColumn12Pro;
    }

    public void setEsaColumn12Pro(BigDecimal esaColumn12Pro) {
        this.esaColumn12Pro = esaColumn12Pro;
    }

    public BigDecimal getEsaColumn13Cur() {
        return this.esaColumn13Cur;
    }

    public void setEsaColumn13Cur(BigDecimal esaColumn13Cur) {
        this.esaColumn13Cur = esaColumn13Cur;
    }

    public BigDecimal getEsaColumn13Pro() {
        return this.esaColumn13Pro;
    }

    public void setEsaColumn13Pro(BigDecimal esaColumn13Pro) {
        this.esaColumn13Pro = esaColumn13Pro;
    }

    public BigDecimal getEsaColumn14Cur() {
        return this.esaColumn14Cur;
    }

    public void setEsaColumn14Cur(BigDecimal esaColumn14Cur) {
        this.esaColumn14Cur = esaColumn14Cur;
    }

    public BigDecimal getEsaColumn14Pro() {
        return this.esaColumn14Pro;
    }

    public void setEsaColumn14Pro(BigDecimal esaColumn14Pro) {
        this.esaColumn14Pro = esaColumn14Pro;
    }

    public BigDecimal getEsaColumn15Cur() {
        return this.esaColumn15Cur;
    }

    public void setEsaColumn15Cur(BigDecimal esaColumn15Cur) {
        this.esaColumn15Cur = esaColumn15Cur;
    }

    public BigDecimal getEsaColumn15Pro() {
        return this.esaColumn15Pro;
    }

    public void setEsaColumn15Pro(BigDecimal esaColumn15Pro) {
        this.esaColumn15Pro = esaColumn15Pro;
    }

    public BigDecimal getEsaColumn16Cur() {
        return this.esaColumn16Cur;
    }

    public void setEsaColumn16Cur(BigDecimal esaColumn16Cur) {
        this.esaColumn16Cur = esaColumn16Cur;
    }

    public BigDecimal getEsaColumn16Pro() {
        return this.esaColumn16Pro;
    }

    public void setEsaColumn16Pro(BigDecimal esaColumn16Pro) {
        this.esaColumn16Pro = esaColumn16Pro;
    }

    public BigDecimal getEsaColumn17Cur() {
        return this.esaColumn17Cur;
    }

    public void setEsaColumn17Cur(BigDecimal esaColumn17Cur) {
        this.esaColumn17Cur = esaColumn17Cur;
    }

    public BigDecimal getEsaColumn17Pro() {
        return this.esaColumn17Pro;
    }

    public void setEsaColumn17Pro(BigDecimal esaColumn17Pro) {
        this.esaColumn17Pro = esaColumn17Pro;
    }

    public BigDecimal getEsaColumn18Cur() {
        return this.esaColumn18Cur;
    }

    public void setEsaColumn18Cur(BigDecimal esaColumn18Cur) {
        this.esaColumn18Cur = esaColumn18Cur;
    }

    public BigDecimal getEsaColumn18Pro() {
        return this.esaColumn18Pro;
    }

    public void setEsaColumn18Pro(BigDecimal esaColumn18Pro) {
        this.esaColumn18Pro = esaColumn18Pro;
    }

    public BigDecimal getEsaColumn19Cur() {
        return this.esaColumn19Cur;
    }

    public void setEsaColumn19Cur(BigDecimal esaColumn19Cur) {
        this.esaColumn19Cur = esaColumn19Cur;
    }

    public BigDecimal getEsaColumn19Pro() {
        return this.esaColumn19Pro;
    }

    public void setEsaColumn19Pro(BigDecimal esaColumn19Pro) {
        this.esaColumn19Pro = esaColumn19Pro;
    }

    public BigDecimal getEsaColumn1Cur() {
        return this.esaColumn1Cur;
    }

    public void setEsaColumn1Cur(BigDecimal esaColumn1Cur) {
        this.esaColumn1Cur = esaColumn1Cur;
    }

    public BigDecimal getEsaColumn1Pro() {
        return this.esaColumn1Pro;
    }

    public void setEsaColumn1Pro(BigDecimal esaColumn1Pro) {
        this.esaColumn1Pro = esaColumn1Pro;
    }

    public BigDecimal getEsaColumn20Cur() {
        return this.esaColumn20Cur;
    }

    public void setEsaColumn20Cur(BigDecimal esaColumn20Cur) {
        this.esaColumn20Cur = esaColumn20Cur;
    }

    public BigDecimal getEsaColumn20Pro() {
        return this.esaColumn20Pro;
    }

    public void setEsaColumn20Pro(BigDecimal esaColumn20Pro) {
        this.esaColumn20Pro = esaColumn20Pro;
    }

    public BigDecimal getEsaColumn21Cur() {
        return this.esaColumn21Cur;
    }

    public void setEsaColumn21Cur(BigDecimal esaColumn21Cur) {
        this.esaColumn21Cur = esaColumn21Cur;
    }

    public BigDecimal getEsaColumn21Pro() {
        return this.esaColumn21Pro;
    }

    public void setEsaColumn21Pro(BigDecimal esaColumn21Pro) {
        this.esaColumn21Pro = esaColumn21Pro;
    }

    public BigDecimal getEsaColumn22Cur() {
        return this.esaColumn22Cur;
    }

    public void setEsaColumn22Cur(BigDecimal esaColumn22Cur) {
        this.esaColumn22Cur = esaColumn22Cur;
    }

    public BigDecimal getEsaColumn22Pro() {
        return this.esaColumn22Pro;
    }

    public void setEsaColumn22Pro(BigDecimal esaColumn22Pro) {
        this.esaColumn22Pro = esaColumn22Pro;
    }

    public BigDecimal getEsaColumn23Cur() {
        return this.esaColumn23Cur;
    }

    public void setEsaColumn23Cur(BigDecimal esaColumn23Cur) {
        this.esaColumn23Cur = esaColumn23Cur;
    }

    public BigDecimal getEsaColumn23Pro() {
        return this.esaColumn23Pro;
    }

    public void setEsaColumn23Pro(BigDecimal esaColumn23Pro) {
        this.esaColumn23Pro = esaColumn23Pro;
    }

    public BigDecimal getEsaColumn24Cur() {
        return this.esaColumn24Cur;
    }

    public void setEsaColumn24Cur(BigDecimal esaColumn24Cur) {
        this.esaColumn24Cur = esaColumn24Cur;
    }

    public BigDecimal getEsaColumn24Pro() {
        return this.esaColumn24Pro;
    }

    public void setEsaColumn24Pro(BigDecimal esaColumn24Pro) {
        this.esaColumn24Pro = esaColumn24Pro;
    }

    public BigDecimal getEsaColumn25Cur() {
        return this.esaColumn25Cur;
    }

    public void setEsaColumn25Cur(BigDecimal esaColumn25Cur) {
        this.esaColumn25Cur = esaColumn25Cur;
    }

    public BigDecimal getEsaColumn25Pro() {
        return this.esaColumn25Pro;
    }

    public void setEsaColumn25Pro(BigDecimal esaColumn25Pro) {
        this.esaColumn25Pro = esaColumn25Pro;
    }

    public BigDecimal getEsaColumn26Cur() {
        return this.esaColumn26Cur;
    }

    public void setEsaColumn26Cur(BigDecimal esaColumn26Cur) {
        this.esaColumn26Cur = esaColumn26Cur;
    }

    public BigDecimal getEsaColumn26Pro() {
        return this.esaColumn26Pro;
    }

    public void setEsaColumn26Pro(BigDecimal esaColumn26Pro) {
        this.esaColumn26Pro = esaColumn26Pro;
    }

    public BigDecimal getEsaColumn27Cur() {
        return this.esaColumn27Cur;
    }

    public void setEsaColumn27Cur(BigDecimal esaColumn27Cur) {
        this.esaColumn27Cur = esaColumn27Cur;
    }

    public BigDecimal getEsaColumn27Pro() {
        return this.esaColumn27Pro;
    }

    public void setEsaColumn27Pro(BigDecimal esaColumn27Pro) {
        this.esaColumn27Pro = esaColumn27Pro;
    }

    public BigDecimal getEsaColumn28Cur() {
        return this.esaColumn28Cur;
    }

    public void setEsaColumn28Cur(BigDecimal esaColumn28Cur) {
        this.esaColumn28Cur = esaColumn28Cur;
    }

    public BigDecimal getEsaColumn28Pro() {
        return this.esaColumn28Pro;
    }

    public void setEsaColumn28Pro(BigDecimal esaColumn28Pro) {
        this.esaColumn28Pro = esaColumn28Pro;
    }

    public BigDecimal getEsaColumn29Cur() {
        return this.esaColumn29Cur;
    }

    public void setEsaColumn29Cur(BigDecimal esaColumn29Cur) {
        this.esaColumn29Cur = esaColumn29Cur;
    }

    public BigDecimal getEsaColumn29Pro() {
        return this.esaColumn29Pro;
    }

    public void setEsaColumn29Pro(BigDecimal esaColumn29Pro) {
        this.esaColumn29Pro = esaColumn29Pro;
    }

    public BigDecimal getEsaColumn2Cur() {
        return this.esaColumn2Cur;
    }

    public void setEsaColumn2Cur(BigDecimal esaColumn2Cur) {
        this.esaColumn2Cur = esaColumn2Cur;
    }

    public BigDecimal getEsaColumn2Pro() {
        return this.esaColumn2Pro;
    }

    public void setEsaColumn2Pro(BigDecimal esaColumn2Pro) {
        this.esaColumn2Pro = esaColumn2Pro;
    }

    public BigDecimal getEsaColumn30Cur() {
        return this.esaColumn30Cur;
    }

    public void setEsaColumn30Cur(BigDecimal esaColumn30Cur) {
        this.esaColumn30Cur = esaColumn30Cur;
    }

    public BigDecimal getEsaColumn30Pro() {
        return this.esaColumn30Pro;
    }

    public void setEsaColumn30Pro(BigDecimal esaColumn30Pro) {
        this.esaColumn30Pro = esaColumn30Pro;
    }

    public BigDecimal getEsaColumn31Cur() {
        return this.esaColumn31Cur;
    }

    public void setEsaColumn31Cur(BigDecimal esaColumn31Cur) {
        this.esaColumn31Cur = esaColumn31Cur;
    }

    public BigDecimal getEsaColumn31Pro() {
        return this.esaColumn31Pro;
    }

    public void setEsaColumn31Pro(BigDecimal esaColumn31Pro) {
        this.esaColumn31Pro = esaColumn31Pro;
    }

    public BigDecimal getEsaColumn32Cur() {
        return this.esaColumn32Cur;
    }

    public void setEsaColumn32Cur(BigDecimal esaColumn32Cur) {
        this.esaColumn32Cur = esaColumn32Cur;
    }

    public BigDecimal getEsaColumn32Pro() {
        return this.esaColumn32Pro;
    }

    public void setEsaColumn32Pro(BigDecimal esaColumn32Pro) {
        this.esaColumn32Pro = esaColumn32Pro;
    }

    public BigDecimal getEsaColumn3Cur() {
        return this.esaColumn3Cur;
    }

    public void setEsaColumn3Cur(BigDecimal esaColumn3Cur) {
        this.esaColumn3Cur = esaColumn3Cur;
    }

    public BigDecimal getEsaColumn3Pro() {
        return this.esaColumn3Pro;
    }

    public void setEsaColumn3Pro(BigDecimal esaColumn3Pro) {
        this.esaColumn3Pro = esaColumn3Pro;
    }

    public BigDecimal getEsaColumn4Cur() {
        return this.esaColumn4Cur;
    }

    public void setEsaColumn4Cur(BigDecimal esaColumn4Cur) {
        this.esaColumn4Cur = esaColumn4Cur;
    }

    public BigDecimal getEsaColumn4Pro() {
        return this.esaColumn4Pro;
    }

    public void setEsaColumn4Pro(BigDecimal esaColumn4Pro) {
        this.esaColumn4Pro = esaColumn4Pro;
    }

    public BigDecimal getEsaColumn5Cur() {
        return this.esaColumn5Cur;
    }

    public void setEsaColumn5Cur(BigDecimal esaColumn5Cur) {
        this.esaColumn5Cur = esaColumn5Cur;
    }

    public BigDecimal getEsaColumn5Pro() {
        return this.esaColumn5Pro;
    }

    public void setEsaColumn5Pro(BigDecimal esaColumn5Pro) {
        this.esaColumn5Pro = esaColumn5Pro;
    }

    public BigDecimal getEsaColumn6Cur() {
        return this.esaColumn6Cur;
    }

    public void setEsaColumn6Cur(BigDecimal esaColumn6Cur) {
        this.esaColumn6Cur = esaColumn6Cur;
    }

    public BigDecimal getEsaColumn6Pro() {
        return this.esaColumn6Pro;
    }

    public void setEsaColumn6Pro(BigDecimal esaColumn6Pro) {
        this.esaColumn6Pro = esaColumn6Pro;
    }

    public BigDecimal getEsaColumn7Cur() {
        return this.esaColumn7Cur;
    }

    public void setEsaColumn7Cur(BigDecimal esaColumn7Cur) {
        this.esaColumn7Cur = esaColumn7Cur;
    }

    public BigDecimal getEsaColumn7Pro() {
        return this.esaColumn7Pro;
    }

    public void setEsaColumn7Pro(BigDecimal esaColumn7Pro) {
        this.esaColumn7Pro = esaColumn7Pro;
    }

    public BigDecimal getEsaColumn8Cur() {
        return this.esaColumn8Cur;
    }

    public void setEsaColumn8Cur(BigDecimal esaColumn8Cur) {
        this.esaColumn8Cur = esaColumn8Cur;
    }

    public BigDecimal getEsaColumn8Pro() {
        return this.esaColumn8Pro;
    }

    public void setEsaColumn8Pro(BigDecimal esaColumn8Pro) {
        this.esaColumn8Pro = esaColumn8Pro;
    }

    public BigDecimal getEsaColumn9Cur() {
        return this.esaColumn9Cur;
    }

    public void setEsaColumn9Cur(BigDecimal esaColumn9Cur) {
        this.esaColumn9Cur = esaColumn9Cur;
    }

    public BigDecimal getEsaColumn9Pro() {
        return this.esaColumn9Pro;
    }

    public void setEsaColumn9Pro(BigDecimal esaColumn9Pro) {
        this.esaColumn9Pro = esaColumn9Pro;
    }

    public int getHashCode() {
        return this.hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public BigDecimal getEsaCurIncrRate() {
        return this.esaCurIncrRate;
    }

    public void setEsaCurIncrRate(BigDecimal esaCurIncrRate) {
        this.esaCurIncrRate = esaCurIncrRate;
    }

    public BigDecimal getEsaCurIncrRate1() {
        return this.esaCurIncrRate1;
    }

    public void setEsaCurIncrRate1(BigDecimal esaCurIncrRate1) {
        this.esaCurIncrRate1 = esaCurIncrRate1;
    }

    public Integer getEsaNeedGmApprove() {
        return this.esaNeedGmApprove;
    }

    public void setEsaNeedGmApprove(Integer esaNeedGmApprove) {
        this.esaNeedGmApprove = esaNeedGmApprove;
    }

    public Integer getEsaChanged() {
        return this.esaChanged;
    }

    public void setEsaChanged(Integer esaChanged) {
        this.esaChanged = esaChanged;
    }

    public BigDecimal getEsaColumn33Cur() {
        return this.esaColumn33Cur;
    }

    public void setEsaColumn33Cur(BigDecimal esaColumn33Cur) {
        this.esaColumn33Cur = esaColumn33Cur;
    }

    public BigDecimal getEsaColumn34Cur() {
        return this.esaColumn34Cur;
    }

    public void setEsaColumn34Cur(BigDecimal esaColumn34Cur) {
        this.esaColumn34Cur = esaColumn34Cur;
    }

    public BigDecimal getEsaColumn35Cur() {
        return this.esaColumn35Cur;
    }

    public void setEsaColumn35Cur(BigDecimal esaColumn35Cur) {
        this.esaColumn35Cur = esaColumn35Cur;
    }

    public BigDecimal getEsaColumn36Cur() {
        return this.esaColumn36Cur;
    }

    public void setEsaColumn36Cur(BigDecimal esaColumn36Cur) {
        this.esaColumn36Cur = esaColumn36Cur;
    }

    public BigDecimal getEsaColumn37Cur() {
        return this.esaColumn37Cur;
    }

    public void setEsaColumn37Cur(BigDecimal esaColumn37Cur) {
        this.esaColumn37Cur = esaColumn37Cur;
    }

    public BigDecimal getEsaColumn38Cur() {
        return this.esaColumn38Cur;
    }

    public void setEsaColumn38Cur(BigDecimal esaColumn38Cur) {
        this.esaColumn38Cur = esaColumn38Cur;
    }

    public BigDecimal getEsaColumn39Cur() {
        return this.esaColumn39Cur;
    }

    public void setEsaColumn39Cur(BigDecimal esaColumn39Cur) {
        this.esaColumn39Cur = esaColumn39Cur;
    }

    public BigDecimal getEsaColumn40Cur() {
        return this.esaColumn40Cur;
    }

    public void setEsaColumn40Cur(BigDecimal esaColumn40Cur) {
        this.esaColumn40Cur = esaColumn40Cur;
    }

    public BigDecimal getEsaColumn41Cur() {
        return this.esaColumn41Cur;
    }

    public void setEsaColumn41Cur(BigDecimal esaColumn41Cur) {
        this.esaColumn41Cur = esaColumn41Cur;
    }

    public BigDecimal getEsaColumn42Cur() {
        return this.esaColumn42Cur;
    }

    public void setEsaColumn42Cur(BigDecimal esaColumn42Cur) {
        this.esaColumn42Cur = esaColumn42Cur;
    }

    public BigDecimal getEsaColumn43Cur() {
        return this.esaColumn43Cur;
    }

    public void setEsaColumn43Cur(BigDecimal esaColumn43Cur) {
        this.esaColumn43Cur = esaColumn43Cur;
    }

    public BigDecimal getEsaColumn44Cur() {
        return this.esaColumn44Cur;
    }

    public void setEsaColumn44Cur(BigDecimal esaColumn44Cur) {
        this.esaColumn44Cur = esaColumn44Cur;
    }

    public BigDecimal getEsaColumn45Cur() {
        return this.esaColumn45Cur;
    }

    public void setEsaColumn45Cur(BigDecimal esaColumn45Cur) {
        this.esaColumn45Cur = esaColumn45Cur;
    }

    public BigDecimal getEsaColumn46Cur() {
        return this.esaColumn46Cur;
    }

    public void setEsaColumn46Cur(BigDecimal esaColumn46Cur) {
        this.esaColumn46Cur = esaColumn46Cur;
    }

    public BigDecimal getEsaColumn47Cur() {
        return this.esaColumn47Cur;
    }

    public void setEsaColumn47Cur(BigDecimal esaColumn47Cur) {
        this.esaColumn47Cur = esaColumn47Cur;
    }

    public BigDecimal getEsaColumn48Cur() {
        return this.esaColumn48Cur;
    }

    public void setEsaColumn48Cur(BigDecimal esaColumn48Cur) {
        this.esaColumn48Cur = esaColumn48Cur;
    }

    public BigDecimal getEsaColumn33Pro() {
        return this.esaColumn33Pro;
    }

    public void setEsaColumn33Pro(BigDecimal esaColumn33Pro) {
        this.esaColumn33Pro = esaColumn33Pro;
    }

    public BigDecimal getEsaColumn34Pro() {
        return this.esaColumn34Pro;
    }

    public void setEsaColumn34Pro(BigDecimal esaColumn34Pro) {
        this.esaColumn34Pro = esaColumn34Pro;
    }

    public BigDecimal getEsaColumn35Pro() {
        return this.esaColumn35Pro;
    }

    public void setEsaColumn35Pro(BigDecimal esaColumn35Pro) {
        this.esaColumn35Pro = esaColumn35Pro;
    }

    public BigDecimal getEsaColumn36Pro() {
        return this.esaColumn36Pro;
    }

    public void setEsaColumn36Pro(BigDecimal esaColumn36Pro) {
        this.esaColumn36Pro = esaColumn36Pro;
    }

    public BigDecimal getEsaColumn37Pro() {
        return this.esaColumn37Pro;
    }

    public void setEsaColumn37Pro(BigDecimal esaColumn37Pro) {
        this.esaColumn37Pro = esaColumn37Pro;
    }

    public BigDecimal getEsaColumn38Pro() {
        return this.esaColumn38Pro;
    }

    public void setEsaColumn38Pro(BigDecimal esaColumn38Pro) {
        this.esaColumn38Pro = esaColumn38Pro;
    }

    public BigDecimal getEsaColumn39Pro() {
        return this.esaColumn39Pro;
    }

    public void setEsaColumn39Pro(BigDecimal esaColumn39Pro) {
        this.esaColumn39Pro = esaColumn39Pro;
    }

    public BigDecimal getEsaColumn40Pro() {
        return this.esaColumn40Pro;
    }

    public void setEsaColumn40Pro(BigDecimal esaColumn40Pro) {
        this.esaColumn40Pro = esaColumn40Pro;
    }

    public BigDecimal getEsaColumn41Pro() {
        return this.esaColumn41Pro;
    }

    public void setEsaColumn41Pro(BigDecimal esaColumn41Pro) {
        this.esaColumn41Pro = esaColumn41Pro;
    }

    public BigDecimal getEsaColumn42Pro() {
        return this.esaColumn42Pro;
    }

    public void setEsaColumn42Pro(BigDecimal esaColumn42Pro) {
        this.esaColumn42Pro = esaColumn42Pro;
    }

    public BigDecimal getEsaColumn43Pro() {
        return this.esaColumn43Pro;
    }

    public void setEsaColumn43Pro(BigDecimal esaColumn43Pro) {
        this.esaColumn43Pro = esaColumn43Pro;
    }

    public BigDecimal getEsaColumn44Pro() {
        return this.esaColumn44Pro;
    }

    public void setEsaColumn44Pro(BigDecimal esaColumn44Pro) {
        this.esaColumn44Pro = esaColumn44Pro;
    }

    public BigDecimal getEsaColumn45Pro() {
        return this.esaColumn45Pro;
    }

    public void setEsaColumn45Pro(BigDecimal esaColumn45Pro) {
        this.esaColumn45Pro = esaColumn45Pro;
    }

    public BigDecimal getEsaColumn46Pro() {
        return this.esaColumn46Pro;
    }

    public void setEsaColumn46Pro(BigDecimal esaColumn46Pro) {
        this.esaColumn46Pro = esaColumn46Pro;
    }

    public BigDecimal getEsaColumn47Pro() {
        return this.esaColumn47Pro;
    }

    public void setEsaColumn47Pro(BigDecimal esaColumn47Pro) {
        this.esaColumn47Pro = esaColumn47Pro;
    }

    public BigDecimal getEsaColumn48Pro() {
        return this.esaColumn48Pro;
    }

    public void setEsaColumn48Pro(BigDecimal esaColumn48Pro) {
        this.esaColumn48Pro = esaColumn48Pro;
    }

    public Empsalaryacctversion getEsaEsavIdCur() {
        return this.esaEsavIdCur;
    }

    public void setEsaEsavIdCur(Empsalaryacctversion esaEsavIdCur) {
        this.esaEsavIdCur = esaEsavIdCur;
    }

    public Empsalaryacctversion getEsaEsavIdPro() {
        return this.esaEsavIdPro;
    }

    public void setEsaEsavIdPro(Empsalaryacctversion esaEsavIdPro) {
        this.esaEsavIdPro = esaEsavIdPro;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.domain.base.BaseEmpsalaryadj JD-Core Version: 0.5.4
 */