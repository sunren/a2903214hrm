package com.hr.io.domain;

import com.hr.base.BaseDomain;
import java.io.Serializable;

public class Iomatch extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    private String iomatchId;
    private Iodef iodef;
    private String iomatchFieldName;
    private String iomatchFieldDesc;
    private Integer iomatchRequired;
    private Integer iomatchLength;
    private String iomatchValidType;
    private Integer iomatchSortId;

    public Iomatch() {
    }

    public Iomatch(String iomatchId, Iodef iodef, String iomatchFieldName, Integer iomatchRequired,
            Integer iomatchLength, Integer iomatchNeedValid, Integer iomatchSortId) {
        this.iomatchId = iomatchId;
        this.iodef = iodef;
        this.iomatchFieldName = iomatchFieldName;
        this.iomatchRequired = iomatchRequired;
        this.iomatchLength = iomatchLength;
        this.iomatchSortId = iomatchSortId;
    }

    public Iomatch(String iomatchId, Iodef iodef, String iomatchFieldName, String iomatchFieldDesc,
            Integer iomatchRequired, Integer iomatchLength, Integer iomatchNeedValid,
            String iomatchValidType, Integer iomatchSortId) {
        this.iomatchId = iomatchId;
        this.iodef = iodef;
        this.iomatchFieldName = iomatchFieldName;
        this.iomatchFieldDesc = iomatchFieldDesc;
        this.iomatchRequired = iomatchRequired;
        this.iomatchLength = iomatchLength;
        this.iomatchValidType = iomatchValidType;
        this.iomatchSortId = iomatchSortId;
    }

    public String getIomatchId() {
        return this.iomatchId;
    }

    public void setIomatchId(String iomatchId) {
        this.iomatchId = iomatchId;
    }

    public Iodef getIodef() {
        return this.iodef;
    }

    public void setIodef(Iodef iodef) {
        this.iodef = iodef;
    }

    public String getIomatchFieldName() {
        return this.iomatchFieldName;
    }

    public void setIomatchFieldName(String iomatchFieldName) {
        this.iomatchFieldName = iomatchFieldName;
    }

    public String getIomatchFieldDesc() {
        return this.iomatchFieldDesc;
    }

    public void setIomatchFieldDesc(String iomatchFieldDesc) {
        this.iomatchFieldDesc = iomatchFieldDesc;
    }

    public Integer getIomatchRequired() {
        return this.iomatchRequired;
    }

    public void setIomatchRequired(Integer iomatchRequired) {
        this.iomatchRequired = iomatchRequired;
    }

    public Integer getIomatchLength() {
        return this.iomatchLength;
    }

    public void setIomatchLength(Integer iomatchLength) {
        this.iomatchLength = iomatchLength;
    }

    public String getIomatchValidType() {
        return this.iomatchValidType;
    }

    public void setIomatchValidType(String iomatchValidType) {
        this.iomatchValidType = iomatchValidType;
    }

    public Integer getIomatchSortId() {
        return this.iomatchSortId;
    }

    public void setIomatchSortId(Integer iomatchSortId) {
        this.iomatchSortId = iomatchSortId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.domain.Iomatch JD-Core Version: 0.5.4
 */