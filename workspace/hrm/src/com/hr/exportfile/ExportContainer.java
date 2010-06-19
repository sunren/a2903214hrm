package com.hr.exportfile;

import jxl.write.WritableCellFormat;

public class ExportContainer {
    private String contentValue;
    private WritableCellFormat cellFormat;

    public ExportContainer(String contentValue, WritableCellFormat cellFormat) {
        this.contentValue = contentValue;
        this.cellFormat = cellFormat;
    }

    public WritableCellFormat getCellFormat() {
        return this.cellFormat;
    }

    public void setCellFormat(WritableCellFormat cellFormat) {
        this.cellFormat = cellFormat;
    }

    public String getContentValue() {
        return this.contentValue;
    }

    public void setContentValue(String contentValue) {
        this.contentValue = contentValue;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.exportfile.ExportContainer JD-Core Version: 0.5.4
 */