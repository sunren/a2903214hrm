package com.hr.base.quartz;

import com.hr.security.bo.BackupRBo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

public class DatabaseBackupService {
    private PropertiesFileConfigManager fileConfigManager;

    public DatabaseBackupService() {
        this.fileConfigManager = PropertiesFileConfigManager.getInstance();
    }

    public void executeBackup() {
        String fileName = "backup-" + getDateDb() + ".sql";
        BackupRBo backupRBo = (BackupRBo) SpringBeanFactory.getBean("backupRBo");
        String sqlBinPath = this.fileConfigManager.getProperty("sys.security.sqlbin.path").trim();

        String floaderPath = getHomePath()
                + this.fileConfigManager.getProperty("sys.security.backup.path");

        String filePath = floaderPath + fileName;
        String dataBase = this.fileConfigManager.getDatabaseSchemaName();
        String userName = this.fileConfigManager.getProperty("jdbc.username");
        String password = this.fileConfigManager.getProperty("jdbc.password");

        backupRBo.saveBackup(changePath(sqlBinPath), changePath(filePath), dataBase, userName,
                             password);

        deleteBackupFiles(floaderPath);
    }

    private String getDateDb() {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String mydate = sp.format(new Date());
        return mydate;
    }

    private String changePath(String original) {
        char[] chars = original.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == '\\') {
                chars[i] = '/';
            }
        }
        return new String(chars).replaceAll("//", "/");
    }

    private void deleteBackupFiles(String floadorPath) {
        File folder = new File(floadorPath);
        int defaultCopies = 30;
        if (folder.isDirectory()) {
            String[] files = folder.list();
            String copies = DatabaseSysConfigManager.getInstance().getProperty("sys.backup.copies");
            if (StringUtils.isNotEmpty(copies)) {
                defaultCopies = Integer.parseInt(copies);
            }
            if (files.length <= defaultCopies) {
                return;
            }
            Arrays.sort(files);
            int loopSize = files.length - defaultCopies;
            for (int i = 0; i < loopSize; ++i)
                new File(floadorPath + files[i]).delete();
        }
    }

    public String getHomePath() {
        String path = "";
        String backupPath = this.fileConfigManager.getProperty("sys.security.backup.path");
        try {
            File f = new PropertiesConfiguration("sys_config.properties").getFile();
            f = f.getParentFile().getParentFile().getParentFile().getParentFile();
            path = f.getAbsolutePath();
            if (!new File(path + "/file/" + backupPath).exists())
                new File(path + "/file/" + backupPath).mkdirs();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return path + "/file/";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.base.quartz.DatabaseBackupService JD-Core Version: 0.5.4
 */