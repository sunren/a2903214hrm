package com.hr.util.excel;

import java.io.FileOutputStream;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public abstract class IExportToExcel {
    protected FileOutputStream fileOutputStream;
    protected WritableWorkbook writableWorkbook;
    protected String filePathName;
    protected String sheetDefaultName = "sheet";
    protected String statisticSheetDefaultName = "stattistic";
    protected ExcelFormat format = new ExcelFormat();
    protected boolean append = true;
    public static final String SheetNameNotAllowedCharcater = "?*[]:/\\";
    public static final int rowHeightRate = 20;

    public IExportToExcel() throws Exception {
        setFormat(new ExcelFormat());
    }

    public IExportToExcel(String filePathName) throws Exception {
        setFilePathName(filePathName);
        setFormat(new ExcelFormat());
    }

    public IExportToExcel(String filePathName, ExcelFormat format) throws Exception {
        setFilePathName(filePathName);
        setFormat(format);
    }

    public void exportOnce() throws Exception {
        openFile();
        buildContent();
        closeFile();
    }

    public void openFile() throws Exception {
        this.fileOutputStream = new FileOutputStream(this.filePathName);
        this.writableWorkbook = Workbook.createWorkbook(this.fileOutputStream);
    }

    public void closeFile() throws Exception {
        this.writableWorkbook.close();
        this.writableWorkbook = null;
        this.fileOutputStream.close();
        this.fileOutputStream = null;
    }

    protected abstract void buildContent() throws Exception;

    public WritableSheet createSheet(String sheetName, int index, WritableWorkbook wb) {
        String sNNew = sheetName.replaceAll("[\\?\\*\\[\\]\\:\\/\\\\]", " ");
        return wb.createSheet(sNNew, index);
    }

    public String getFilePathName() {
        return this.filePathName;
    }

    public void setFilePathName(String filePathName) {
        this.filePathName = filePathName;
    }

    public ExcelFormat getFormat() {
        return this.format;
    }

    public void setFormat(ExcelFormat format) throws Exception {
        if (format == null)
            this.format = new ExcelFormat();
        else
            this.format = format;
    }

    public String getSheetDefaultName() {
        return this.sheetDefaultName;
    }

    public String getStatisticSheetDefaultName() {
        return this.statisticSheetDefaultName;
    }

    public void setSheetDefaultName(String sheetDefaultName) {
        this.sheetDefaultName = sheetDefaultName;
    }

    public void setStatisticSheetDefaultName(String statisticSheetDefaultName) {
        this.statisticSheetDefaultName = statisticSheetDefaultName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.excel.IExportToExcel JD-Core Version: 0.5.4
 */