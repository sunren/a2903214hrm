package com.hr.io.extend;

public class IOMessageSingle {
    private Integer rowNum;
    private Integer colNum;
    private String message;
    private Integer errorType;

    public IOMessageSingle(String message, Integer errorType, Integer[] rowAndCol) {
        this.message = message;
        this.errorType = errorType;
        if (rowAndCol.length > 0) {
            this.rowNum = rowAndCol[0];
            if (rowAndCol.length > 1)
                this.colNum = rowAndCol[1];
        }
    }

    public Integer getRowNum() {
        return this.rowNum;
    }

    public Integer getColNum() {
        return this.colNum;
    }

    public String getMessage() {
        return this.message;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorType() {
        return this.errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.extend.IOMessageSingle JD-Core Version: 0.5.4
 */