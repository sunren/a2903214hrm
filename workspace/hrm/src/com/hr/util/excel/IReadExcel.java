package com.hr.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import jxl.Workbook;

public abstract class IReadExcel {
    protected FileInputStream inputStream;
    protected Workbook workbook;
    private File inputFile;

    public IReadExcel() {
    }

    public IReadExcel(File inputFile) {
        setInputFile(inputFile);
    }

    public IReadExcel(String filePath) {
        setInputFile(new File(filePath));
    }

    public List<String[]> getContent() throws Exception {
        openFile();
        List result = readContent();
        closeFile();
        return result;
    }

    public void openFile() throws Exception {
        this.inputStream = new FileInputStream(this.inputFile);
        this.workbook = Workbook.getWorkbook(this.inputStream);
    }

    public void closeFile() throws Exception {
        this.workbook.close();
        this.workbook = null;
        this.inputStream.close();
        this.inputStream = null;
    }

    protected abstract List<String[]> readContent() throws Exception;

    public File getInputFile() {
        return this.inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.excel.IReadExcel JD-Core Version: 0.5.4
 */