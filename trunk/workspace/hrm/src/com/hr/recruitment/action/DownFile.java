package com.hr.recruitment.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import java.io.FileInputStream;

public class DownFile extends BaseAction {
    private FileInputStream docStream;
    private String contentDisposition;
    private String fileName;

    public DownFile() {
        this.docStream = null;
        this.contentDisposition = null;
    }

    public String downProfile() {
        String forward = null;
        try {
            forward = downLoadFile(this.fileName, "filename=\"" + this.fileName + "\"");
        } catch (Exception e) {
            forward = null;
        }
        return forward;
    }

    public String downLoadFile(String fileName, String downName) {
        this.docStream = FileOperate.getFileInputStream("sys.recruitment.applier.path", fileName);
        this.contentDisposition = downName;
        if (this.docStream == null) {
            return "fileError";
        }
        return "download";
    }

    public String getContentDisposition() {
        return this.contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public FileInputStream getDocStream() {
        return this.docStream;
    }

    public void setDocStream(FileInputStream docStream) {
        this.docStream = docStream;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.recruitment.action.DownFile JD-Core Version: 0.5.4
 */