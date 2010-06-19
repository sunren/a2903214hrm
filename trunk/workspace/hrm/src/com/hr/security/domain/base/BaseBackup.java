package com.hr.security.domain.base;

import com.hr.base.BaseDomain;
import com.hr.security.domain.Backup;
import java.io.Serializable;

public abstract class BaseBackup extends BaseDomain implements Serializable {
    public static String REF = "Backup";
    public static String PROP_FILE_CREAT_TIME = "fileCreatTime";
    public static String PROP_FILE_NAME = "fileName";
    public static String PROP_ID = "id";
    public static String PROP_FILE_SIZE = "fileSize";

    private int hashCode = -2147483648;
    private String id;
    private String fileName;
    private Integer fileSize;
    private String fileCreatTime;

    public BaseBackup() {
        initialize();
    }

    public BaseBackup(String id) {
        setId(id);
        initialize();
    }

    public BaseBackup(String id, String fileName, Integer fileSize, String fileCreatTime) {
        setId(id);
        setFileName(fileName);
        setFileSize(fileSize);
        setFileCreatTime(fileCreatTime);
        initialize();
    }

    protected void initialize() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileCreatTime() {
        return this.fileCreatTime;
    }

    public void setFileCreatTime(String fileCreatTime) {
        this.fileCreatTime = fileCreatTime;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Backup))
            return false;

        Backup backup = (Backup) obj;
        if ((null == getId()) || (null == backup.getId()))
            return false;
        return getId().equals(backup.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.domain.base.BaseBackup JD-Core Version: 0.5.4
 */