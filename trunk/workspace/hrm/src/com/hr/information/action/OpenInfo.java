package com.hr.information.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.FileOperate;
import com.hr.information.bo.IInformationBo;
import com.hr.information.domain.Information;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class OpenInfo extends BaseAction implements Constants {
    private static final long serialVersionUID = -4234940898871087425L;
    private Information info;
    private String infoId;
    private String picPath;
    private String filePath;
    private static final Logger logger = Logger.getLogger(OpenInfo.class);

    public OpenInfo() {
        this.info = null;
    }

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("测试：execute() - SearchInfo.action");
        }

        IInformationBo infoBo = (IInformationBo) getBean("informationBo");
        HttpServletRequest request = ServletActionContext.getRequest();
        if ((request.getParameter("infoId") != null)
                && (request.getParameter("infoId").length() > 0)) {
            try {
                this.infoId = request.getParameter("infoId");
                String[] fetch = new String[2];
                fetch[0] = "infoClass";
                fetch[1] = "infoCreateBy";
                this.info = infoBo.loadInfo(this.infoId, fetch);
                if ((this.info != null) && (this.info.getInfoFileName() != null)
                        && (this.info.getInfoFileName().trim().length() > 0)) {
                    this.filePath = (FileOperate.getFileHomePath() + PropertiesFileConfigManager
                            .getInstance().getProperty("sys.information.file.path"));
                    if ((this.filePath == null) || (this.filePath.trim().length() == 0)
                            || (!FileIsExist(this.filePath, this.info.getInfoFileName())))
                        this.info.setInfoFileName(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("execute() -SearchInfo- end");
        }
        return "success";
    }

    public boolean FileIsExist(String pathName, String txtfilename) {
        if (!pathName.substring(pathName.length() - 1).equalsIgnoreCase("/"))
            pathName = pathName + "/";
        File dire = new File(pathName);
        if (!dire.exists()) {
            dire.mkdir();
        } else if (dire.isDirectory()) {
            File[] file = dire.listFiles();
            File templet = null;
            String name = "";
            for (int len = 0; len < file.length; ++len) {
                templet = file[len];
                name = templet.getName();

                if ((txtfilename != null) && (txtfilename.compareToIgnoreCase(name) == 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Information getInfo() {
        return this.info;
    }

    public void setInfo(Information info) {
        this.info = info;
    }

    public String getInfoId() {
        return this.infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPicPath() {
        return this.picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.information.action.OpenInfo JD-Core Version: 0.5.4
 */