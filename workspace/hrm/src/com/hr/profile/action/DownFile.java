package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import java.io.FileInputStream;

public class DownFile extends BaseAction {
    private static final long serialVersionUID = 324903150L;
    private FileInputStream docStream;
    private String contentDisposition;
    private String fileName;

    public DownFile() {
        this.docStream = null;
        this.contentDisposition = null;
    }

    public String downProfile() throws Exception {
        String forward = null;
        try {
            forward = downLoadFile(this.fileName, "attachment;filename=\""
                    + this.contentDisposition + "\"");
        } catch (Exception e) {
            forward = null;
        }
        return forward;
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

    public String downLoadFile(String fileName, String downName) throws Exception {
        this.docStream = FileOperate.getFileInputStream("sys.profile.file.path", fileName);
        this.contentDisposition = downName;
        if (this.docStream == null) {
            return "nofile";
        }
        return "download";
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
 * com.hr.profile.action.DownFile JD-Core Version: 0.5.4
 */