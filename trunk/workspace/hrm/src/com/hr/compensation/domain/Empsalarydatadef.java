package com.hr.compensation.domain;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public class Empsalarydatadef extends BaseDomain implements Serializable {
    private String esddId;
    private String esddName;
    private String esddDesc;
    private Integer esddDataType;
    private Integer esddDataIsCalc = Integer.valueOf(0);
    private Integer esddDataRounding = Integer.valueOf(0);
    private Integer esddDataLength;
    private Integer esddSortId;
    private Integer esddDataOutput;

    public Empsalarydatadef() {
    }

    public Empsalarydatadef(String esddId) {
        this.esddId = esddId;
    }

    public Empsalarydatadef(String esddName, String esddDesc, Integer esddDataType,
            Integer esddDataIsCalc, Integer esddDataRounding, Integer esddDataLength,
            Integer esddDataOutput) {
        this.esddName = esddName;
        this.esddDesc = esddDesc;
        this.esddDataType = esddDataType;
        this.esddDataIsCalc = esddDataIsCalc;
        this.esddDataRounding = esddDataRounding;
        this.esddDataLength = esddDataLength;
        this.esddDataOutput = esddDataOutput;
    }

    public String getEsddId() {
        return this.esddId;
    }

    public void setEsddId(String esddId) {
        this.esddId = esddId;
    }

    public String getEsddName() {
        return this.esddName;
    }

    public void setEsddName(String esddName) {
        this.esddName = esddName;
    }

    public String getEsddDesc() {
        return this.esddDesc;
    }

    public void setEsddDesc(String esddDesc) {
        this.esddDesc = esddDesc;
    }

    public Integer getEsddDataType() {
        return this.esddDataType;
    }

    public void setEsddDataType(Integer esddDataType) {
        this.esddDataType = esddDataType;
    }

    public Integer getEsddDataIsCalc() {
        return this.esddDataIsCalc;
    }

    public void setEsddDataIsCalc(Integer esddDataIsCalc) {
        this.esddDataIsCalc = esddDataIsCalc;
    }

    public Integer getEsddDataRounding() {
        return this.esddDataRounding;
    }

    public void setEsddDataRounding(Integer esddDataRounding) {
        this.esddDataRounding = esddDataRounding;
    }

    public Integer getEsddDataLength() {
        return this.esddDataLength;
    }

    public void setEsddDataLength(Integer esddDataLength) {
        this.esddDataLength = esddDataLength;
    }

    public Integer getEsddSortId() {
        return this.esddSortId;
    }

    public void setEsddSortId(Integer esddSortId) {
        this.esddSortId = esddSortId;
    }

    public Integer getEsddDataOutput() {
        return this.esddDataOutput;
    }

    public void setEsddDataOutput(Integer esddDataOutput) {
        this.esddDataOutput = esddDataOutput;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.domain.Empsalarydatadef JD-Core Version: 0.5.4
 */