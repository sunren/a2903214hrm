package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DownFile extends BaseAction {
    private static final long serialVersionUID = 324903150L;
    private FileInputStream docStream;
    private String contentDisposition;
    private String fileName;

    public DownFile() {
        this.docStream = null;

        this.contentDisposition = null;
    }

    public String downTrainingFile() {
        String forward = "success";
        try {
            String postFix = this.fileName.substring(this.fileName.lastIndexOf('.'));
            forward = downLoadFile(this.fileName, "filename=\"" + this.contentDisposition + postFix
                    + "\"");
        } catch (Exception e) {
        }
        return forward;
    }

    public String downLoadFile(String fileName, String downName) throws Exception {
        try {
            File input = FileOperate.getFile("sys.training.material.path", fileName);
            if (input == null) {
                return "error";
            }
            this.docStream = new FileInputStream(input);
            this.contentDisposition = downName;
            if (downName == null)
                this.contentDisposition = "download";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error";
        } catch (Exception e) {
            throw new Exception();
        }
        return "download";
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.action.DownFile JD-Core Version: 0.5.4
 */