package com.hr.util.output;

import java.awt.Color;

public class FieldOperate {
    private String fieldName;
    private String showName;
    private String normalName;
    private String statisitcName;
    private String dataType;
    private String format;
    private String statisticMethod;
    private int colWidth = 15;

    private int groupOrder = 0;
    private Color colBgColor;
    private String statisticDataType;
    private String statisticFormat;
    public final String NAME_SEPERATOR = "-";

    public FieldOperate() {
    }

    public FieldOperate(String fieldName, String showName, String dataType, String format) {
        setFieldName(fieldName);
        setShowName(showName);
        setDataType(dataType);
        setFormat(format);
    }

    public FieldOperate(String fieldName, String showName, String dataType, String format,
            int colWidth) {
        setFieldName(fieldName);
        setShowName(showName);
        setDataType(dataType);
        setFormat(format);
        setColWidth(colWidth);
    }

    public FieldOperate(String fieldName, String showName, String dataType, String format,
            String statisticMethod, int colWidth, int groupOrder) {
        setFieldName(fieldName);
        setShowName(showName);
        setDataType(dataType);
        setFormat(format);
        setStatisticMethod(statisticMethod);
        setColWidth(colWidth);
        setGroupOrder(groupOrder);
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        if ((format != null) && (format.length() < 1)) {
            format = null;
        }
        this.format = format;
    }

    public String getStatisticMethod() {
        return this.statisticMethod;
    }

    public void setStatisticMethod(String statisticMethod) {
        if ((statisticMethod == null) || (statisticMethod.length() < 1)) {
            this.statisticMethod = null;
            this.statisticDataType = null;
            this.statisticFormat = null;
        } else {
            this.statisticMethod = statisticMethod;
            if (this.statisticMethod.compareTo("count") == 0) {
                this.statisticDataType = "integer";
                this.statisticFormat = null;
            } else {
                this.statisticDataType = this.dataType;
                this.statisticFormat = this.format;
            }
        }
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
        int place = showName.lastIndexOf("-");
        if (place > 0) {
            this.normalName = showName.substring(0, place);
            this.statisitcName = showName.substring(place + 1);
        } else {
            this.normalName = showName;
            this.statisitcName = showName;
        }
    }

    public String getNormalName() {
        return this.normalName;
    }

    public String getStatisitcName() {
        return this.statisitcName;
    }

    public int getColWidth() {
        return this.colWidth;
    }

    public void setColWidth(int colWidth) {
        if (colWidth > 0)
            this.colWidth = colWidth;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Color getColBgColor() {
        return this.colBgColor;
    }

    public void setColBgColor(Color colBgColor) {
        this.colBgColor = colBgColor;
    }

    public int getGroupOrder() {
        return this.groupOrder;
    }

    public void setGroupOrder(int groupOrder) {
        if (Math.abs(groupOrder) > 2) {
            groupOrder = 0;
        }
        this.groupOrder = groupOrder;
    }

    public String getStatisticDataType() {
        return this.statisticDataType;
    }

    public String getStatisticFormat() {
        return this.statisticFormat;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.output.FieldOperate JD-Core Version: 0.5.4
 */