package com.hr.profile.domain;

import com.hr.profile.domain.base.BaseEmpaddconf;
import java.util.List;

public class Empaddconf extends BaseEmpaddconf {
    private static final long serialVersionUID = 1L;
    private String value;
    private String fieldName;
    private String value2;
    private List<String> fieldValueList;

    public Empaddconf() {
    }

    public Empaddconf(String eadcId) {
        super(eadcId);
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getFieldValueList() {
        return this.fieldValueList;
    }

    public void setFieldValueList(List<String> fieldValueList) {
        this.fieldValueList = fieldValueList;
    }

    public String getValue2() {
        return this.value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.Empaddconf JD-Core Version: 0.5.4
 */