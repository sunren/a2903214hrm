package com.hr.importfile.bo;

public class ErrorMessage {
    private String rowNum = null;

    private String errorContent = null;

    private String errorType = null;

    private String errorDiscript = null;

    public ErrorMessage(String rowNum, String errorContent, String errorType, String errorDiscript) {
        this.rowNum = rowNum;
        this.errorContent = errorContent;
        this.errorType = errorType;
        this.errorDiscript = errorDiscript;
    }

    public String getErrorDiscript() {
        return this.errorDiscript;
    }

    public void setErrorDiscript(String errorDiscript) {
        this.errorDiscript = errorDiscript;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getErrorContent() {
        return this.errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.importfile.bo.ErrorMessage JD-Core Version: 0.5.4
 */