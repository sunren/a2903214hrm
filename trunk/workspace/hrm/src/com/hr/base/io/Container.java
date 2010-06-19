package com.hr.base.io;

public class Container {
    private String headName = null;

    private String propertyName = null;

    private String typeName = null;

    private boolean needChangToName = false;

    public Container(String headName, String propertyName, String typeName, boolean needChangToName) {
        this.headName = headName;
        this.propertyName = propertyName;
        this.typeName = typeName;
        this.needChangToName = needChangToName;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getHeadName() {
        return this.headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isNeedChangToName() {
        return this.needChangToName;
    }

    public void setNeedChangToName(boolean needChangToName) {
        this.needChangToName = needChangToName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.base.io.Container JD-Core Version: 0.5.4
 */