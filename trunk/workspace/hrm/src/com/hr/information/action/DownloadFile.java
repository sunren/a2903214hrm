package com.hr.information.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.FileOperate;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.io.FileInputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class DownloadFile extends BaseAction implements Constants {
    private static final long serialVersionUID = 1L;
    private FileInputStream docStream;
    private String contentDisposition;
    private String fileName;

    public DownloadFile() {
        this.docStream = null;
        this.contentDisposition = null;
    }

    public String execute() throws Exception {
        String fileName = "";
        HttpServletRequest request = ServletActionContext.getRequest();
        fileName = request.getParameter("fileName");
        if ((fileName == null) || (fileName.length() == 0)) {
            return "input";
        }
        try {
            String scFilePath = FileOperate.getFileHomePath()
                    + PropertiesFileConfigManager.getInstance()
                            .getProperty("sys.information.file.path");

            if (scFilePath == null) {
                return "input";
            }
            String tmpFileName = scFilePath + fileName;
            File dire = new File(tmpFileName);
            if (!dire.exists()) {
                return "input";
            }
            setDocStream(new FileInputStream(tmpFileName));
            setContentDisposition(getDownName(fileName));
            setFileName(getFileDownLoadName(fileName));
            return "download";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "input";
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

    public String getFileDownLoadName(String fileName) {
        if ((fileName == null) || (fileName.equals(""))) {
            return "exportFile" + fileName;
        }
        return fileName;
    }

    public String getDownName(String fileName) {
        return "filename=\"" + getFileDownLoadName(fileName) + "\"";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.information.action.DownloadFile JD-Core Version: 0.5.4
 */