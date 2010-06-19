package com.hr.compensation.domain.base;

import com.hr.base.BaseDomain;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalarydatadef;
import java.io.Serializable;

public abstract class BaseEmpsalaryacctitems extends BaseDomain implements Serializable {
    public static String REF = "Empsalaryacctitems";

    public static String PROP_ESAI_DATA_IS_CALC = "esaiDataIsCalc";

    public static String PROP_ESAI_ESDD = "esaiEsdd";
    public static String PROP_ESAI_DATA_ROUNDING = "esaiDataRounding";
    public static String PROP_ESAI_DATA_CALC = "esaiDataCalc";
    public static String PROP_ESAI_DATA_SEQ = "esaiDataSeq";
    public static String PROP_ESAI_ESAV = "esaiEsav";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private Integer esaiDataSeq;
    private Integer esaiDataIsCalc;
    private String esaiDataCalc;
    private Integer esaiDataRounding;
    private Empsalarydatadef esaiEsdd;
    private Empsalaryacctversion esaiEsav;

    public BaseEmpsalaryacctitems() {
        initialize();
    }

    public BaseEmpsalaryacctitems(String id) {
        setId(id);
        initialize();
    }

    public BaseEmpsalaryacctitems(String id, Empsalaryacctversion esaiEsav, Integer esaiDataSeq,
            Integer esaiDataIsCalc, Integer esaiDataRounding) {
        setId(id);
        setEsaiEsav(esaiEsav);
        setEsaiDataSeq(esaiDataSeq);
        setEsaiDataIsCalc(esaiDataIsCalc);
        setEsaiDataRounding(esaiDataRounding);
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

    public Integer getEsaiDataSeq() {
        return this.esaiDataSeq;
    }

    public void setEsaiDataSeq(Integer esaiDataSeq) {
        this.esaiDataSeq = esaiDataSeq;
    }

    public Integer getEsaiDataIsCalc() {
        return this.esaiDataIsCalc;
    }

    public void setEsaiDataIsCalc(Integer esaiDataIsCalc) {
        this.esaiDataIsCalc = esaiDataIsCalc;
    }

    public String getEsaiDataCalc() {
        return this.esaiDataCalc;
    }

    public void setEsaiDataCalc(String esaiDataCalc) {
        this.esaiDataCalc = esaiDataCalc;
    }

    public Integer getEsaiDataRounding() {
        return this.esaiDataRounding;
    }

    public void setEsaiDataRounding(Integer esaiDataRounding) {
        this.esaiDataRounding = esaiDataRounding;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Empsalaryacctitems))
            return false;

        Empsalaryacctitems empsalaryacctitems = (Empsalaryacctitems) obj;
        if ((null == getId()) || (null == empsalaryacctitems.getId()))
            return false;
        return getId().equals(empsalaryacctitems.getId());
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

    public Empsalarydatadef getEsaiEsdd() {
        return this.esaiEsdd;
    }

    public void setEsaiEsdd(Empsalarydatadef esaiEsdd) {
        this.esaiEsdd = esaiEsdd;
    }

    public Empsalaryacctversion getEsaiEsav() {
        return this.esaiEsav;
    }

    public void setEsaiEsav(Empsalaryacctversion esaiEsav) {
        this.esaiEsav = esaiEsav;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.domain.base.BaseEmpsalaryacctitems JD-Core Version: 0.5.4
 */