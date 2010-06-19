package com.hr.importfile.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.io.bo.IIodefBo;
import com.hr.io.domain.Iodef;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.FileCopyUtils;

public class ImportFileAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private File file;
    private String iodefId;
    private String fileHasPath;
    private String fileExtends;
    private String paramString;
    private String attdDateFrom;
    private String attdDateTo;
    private String year;
    private String month;

    public ImportFileAction() {
        this.file = null;

        this.iodefId = null;

        this.fileHasPath = null;

        this.fileExtends = null;

        this.paramString = null;
    }

    public String execute() throws Exception {
        IIodefBo iodefBo = (IIodefBo) SpringBeanFactory.getBean("iodefbo");
        Iodef iodef = iodefBo.searchIodefByName(this.iodefId);

        if (iodef == null) {
            addErrorInfo("接口配置对象为空，请与客服联系！");
            return "success";
        }

        if (!hasMultipleAuth(iodef.getIoAuthority())) {
            addErrorInfo("对不起,您不具有操作权限!");
            return "input";
        }

        String fileNameWithoutExtends = iodef.getIoName();

        String checkFile = importFile(fileNameWithoutExtends);
        if (checkFile != null) {
            addErrorInfo(checkFile);
            return "input";
        }
        return "success";
    }

    public String importFile(String fileNameWithoutExtends) {
        if (this.file == null)
            return "文件选择出错,请点击\"浏览\"重新选择!";

        String fileLengthLimit = PropertiesFileConfigManager.getInstance()
                .getProperty("sys.importDir.length");
        if ((fileLengthLimit != null)
                && (this.file.length() > Integer.parseInt(fileLengthLimit.trim()) * 1024))
            return "文件过大，请将文件拆分后上传！";

        String filePath = FileOperate.getFileHomePath()
                + PropertiesFileConfigManager.getInstance().getProperty("sys.importDir.path");
        if ("true".equals(this.fileHasPath)) {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Employee currentEmployee = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
            filePath = filePath + currentEmployee.getId() + "/";
        }

        deleteOldFile(filePath + fileNameWithoutExtends);

        File newFileParent = new File(filePath);
        if (!newFileParent.exists())
            newFileParent.mkdirs();
        File newFile = new File(filePath + fileNameWithoutExtends + "." + this.fileExtends);
        try {
            FileCopyUtils.copy(this.file, newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "保存文件出错，请重试！";
        }
        return null;
    }

    private void deleteOldFile(String fileWioutExtend) {
        String[] extend = PropertiesFileConfigManager.getInstance()
                .getProperty("sys.importDir.type").split(",");
        for (int i = 0; i < extend.length; ++i) {
            String fileName = fileWioutExtend + extend[i];
            File oldFile = new File(fileName);
            FileOperate.deleteFile(oldFile);
        }
    }

    public String getFileExtends() {
        return this.fileExtends;
    }

    public void setFileExtends(String fileExtends) {
        this.fileExtends = fileExtends;
    }

    public String getFileHasPath() {
        return this.fileHasPath;
    }

    public void setFileHasPath(String fileHasPath) {
        this.fileHasPath = fileHasPath;
    }

    public String getIodefId() {
        return this.iodefId;
    }

    public void setIodefId(String iodefId) {
        this.iodefId = iodefId;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getParamString() {
        return this.paramString;
    }

    public void setParamString(String paramString) {
        this.paramString = paramString;
    }

    public String getAttdDateFrom() {
        return this.attdDateFrom;
    }

    public void setAttdDateFrom(String attdDateFrom) {
        this.attdDateFrom = attdDateFrom;
    }

    public String getAttdDateTo() {
        return this.attdDateTo;
    }

    public void setAttdDateTo(String attdDateTo) {
        this.attdDateTo = attdDateTo;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}