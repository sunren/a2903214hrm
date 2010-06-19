package com.hr.exportfile;

import com.hr.base.FileOperate;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public abstract class ExportFileAbstract {
    protected String filePath;
    protected FileOutputStream fileOutputStream;

    public ExportFileAbstract(String filePath) {
        this.filePath = (FileOperate.getFileHomePath() + filePath);
    }

    public void creatFile(String fileExtendsType, List<String>[] sheetList) throws Exception {
        String fileName = "export-" + FileOperate.buildFileName() + "." + fileExtendsType;
        File file = new File(this.filePath);
        if (!file.exists())
            file.mkdirs();
        this.filePath += fileName;
        this.fileOutputStream = new FileOutputStream(this.filePath);
    }

    public abstract void insertTitle(String paramString, List<ExportContainer> paramList)
            throws Exception;

    public abstract void insertContent(String paramString, List<ExportContainer> paramList)
            throws Exception;

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileOutputStream getFileOutputStream() {
        return this.fileOutputStream;
    }

    public void setFileOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.exportfile.ExportFileAbstract JD-Core Version: 0.5.4
 */