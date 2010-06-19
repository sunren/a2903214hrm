package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.security.bo.BackupRBo;
import com.hr.security.domain.Backup;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class BackupResumeAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private PropertiesFileConfigManager fileConfigManager;
    static Logger logger = Logger.getLogger(Backup.class.getName());
    private String resumeName;
    private String testbin;
    private String sqlbin;
    private String deleteName;
    private List resumeList;
    private Pager pager;

    public BackupResumeAction() {
        this.fileConfigManager = PropertiesFileConfigManager.getInstance();

        this.pager = new Pager();
    }

    public String execute() throws Exception {
        BackupRBo backupRBo = (BackupRBo) SpringBeanFactory.getBean("backupRBo");
        String binPath = this.fileConfigManager.getProperty("sys.security.sqlbin.path").trim();
        if (binPath == null) {
            setTestbin(null);
        }
        binPath = changePath(binPath);
        if (!new File(binPath).exists()) {
            setTestbin(null);
        } else {
            setTestbin(binPath);
        }

        if (getTestbin() == null) {
            String path = getFileHomePath() + "/mysql/bin";
            if (new File(path).exists()) {
                setTestbin(path);
            }
            System.out.println(getFileHomePath() + "/mysql/bin");
        }

        String filePath = FileOperate.getFileHomePath()
                + this.fileConfigManager.getProperty("sys.security.backup.path");
        filePath = changePath(filePath);
        this.resumeList = backupRBo.searchAll(filePath, this.pager);
        return "success";
    }

    public String sqlbin() throws Exception {
        BackupRBo backupRBo = (BackupRBo) SpringBeanFactory.getBean("backupRBo");
        this.sqlbin = changePath(this.sqlbin);
        String binPath = backupRBo.sqlbin(this.sqlbin);
        if ("success".equals(binPath)) {
            addSuccessInfo("保存配置路径成功.服务器将重起,请�1�7�1�7出重新登录�1�7�1�7");
            return "success";
        }
        addActionError("保存配置路径失败＄1�7");
        return "input";
    }

    public String changePath(String original) {
        char[] chars = original.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == '\\') {
                chars[i] = '/';
            }
        }
        return new String(chars).replaceAll("//", "/");
    }

    public String resume() throws Exception {
        BackupRBo backupRBo = (BackupRBo) SpringBeanFactory.getBean("backupRBo");
        String sqlBinPath = this.fileConfigManager.getProperty("sys.security.sqlbin.path").trim();
        boolean hasRightBin = false;
        if ((sqlBinPath != null) && (new File(sqlBinPath).exists())) {
            hasRightBin = true;
        }
        String path = getFileHomePath() + "/mysql/bin";
        if ((!hasRightBin) && (new File(path).exists())) {
            sqlBinPath = path;
        }
        String filePath = FileOperate.getFileHomePath()
                + this.fileConfigManager.getProperty("sys.security.backup.path") + this.resumeName;

        String dataBase = this.fileConfigManager.getDatabaseSchemaName();
        String userName = this.fileConfigManager.getProperty("jdbc.username");
        String password = this.fileConfigManager.getProperty("jdbc.password");
        HttpSession session = getSession();
        Userinfo userinfo = (Userinfo) session.getAttribute("userinfo");
        if (backupRBo.resume(changePath(sqlBinPath), changePath(filePath), dataBase, userName,
                             password)) {
            logger.info(userinfo.getUiUsername() + " 圄1�7" + new Date() + "还原数据成功.");
            addSuccessInfo("数据还原成功〄1�7");
            return "success";
        }
        logger.info(userinfo.getUiUsername() + " 圄1�7" + new Date() + "还原数据失败.");
        addActionError("数据还原失败＄1�7");
        return "input";
    }

    public String saveBackup() throws Exception {
        String fileName = "backup-" + getDateDb() + ".sql";
        BackupRBo backupRBo = (BackupRBo) SpringBeanFactory.getBean("backupRBo");
        String sqlBinPath = this.fileConfigManager.getProperty("sys.security.sqlbin.path").trim();
        boolean hasRightBin = false;
        if ((sqlBinPath != null) && (new File(sqlBinPath).exists())) {
            hasRightBin = true;
        }
        String path = getFileHomePath() + "/mysql/bin";
        if ((!hasRightBin) && (new File(path).exists())) {
            sqlBinPath = path;
        }
        String filePath = FileOperate.getFileHomePath()
                + this.fileConfigManager.getProperty("sys.security.backup.path") + fileName;

        String dataBase = this.fileConfigManager.getDatabaseSchemaName();
        String userName = this.fileConfigManager.getProperty("jdbc.username");
        String password = this.fileConfigManager.getProperty("jdbc.password");
        HttpSession session = getSession();
        Userinfo userinfo = (Userinfo) session.getAttribute("userinfo");

        if (backupRBo.saveBackup(changePath(sqlBinPath), changePath(filePath), dataBase, userName,
                                 password)) {
            if (userinfo != null) {
                logger.info(userinfo.getUiUsername() + " 圄1�7" + new Date() + "备份数据成功.");
                addSuccessInfo("数据备份成功〄1�7");
            }
            return "success";
        }
        logger.info(userinfo.getUiUsername() + " 圄1�7" + new Date() + "备份数据失败.");
        addSuccessInfo("数据备份失败！请配置mysql下bin的绝对路径�1�7�1�7");
        return "input";
    }

    public String delete() throws Exception {
        String filePath = FileOperate.getFileHomePath()
                + this.fileConfigManager.getProperty("sys.security.backup.path") + this.deleteName;

        filePath = changePath(filePath);
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        HttpSession session = getSession();
        Userinfo userinfo = (Userinfo) session.getAttribute("userinfo");
        logger.info(userinfo.getUiUsername() + " 圄1�7" + new Date() + "删除备份成功.");
        addSuccessInfo("删除备份成功");
        return "success";
    }

    private String getFileHomePath() {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request == null)
            request = WebContextFactory.get().getHttpServletRequest();
        String path = request.getSession().getServletContext().getRealPath("");
        File file = new File(path);
        if (!file.exists())
            return path;
        String newPath = file.getParentFile().getAbsolutePath();
        return newPath;
    }

    private String getDateDb() {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String mydate = sp.format(new Date());
        return mydate;
    }

    public List getResumeList() {
        return this.resumeList;
    }

    public void setResumeList(List resumeList) {
        this.resumeList = resumeList;
    }

    public Pager getPager() {
        return this.pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String getDeleteName() {
        return this.deleteName;
    }

    public void setDeleteName(String deleteName) {
        this.deleteName = deleteName;
    }

    public String getResumeName() {
        return this.resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getSqlbin() {
        return this.sqlbin;
    }

    public void setSqlbin(String sqlbin) {
        this.sqlbin = sqlbin;
    }

    public String getTestbin() {
        return this.testbin;
    }

    public void setTestbin(String testbin) {
        this.testbin = testbin;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.BackupResumeAction JD-Core Version: 0.5.4
 */