package com.hr.security.bo.impl;

import com.hr.security.bo.BackupRBo;
import com.hr.security.dao.BackupDao;
import com.hr.security.domain.Backup;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Pager;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class BackupRBoImpl implements BackupRBo {
    private BackupDao backupDAO;

    public BackupRBoImpl() {
        this.backupDAO = null;
    }

    public boolean saveBackup(String mysqlBinPath, String filePath, String dataName,
            String userName, String password) {
        return BackUpTools.backup(mysqlBinPath, filePath, dataName, userName, password);
    }

    public List<Backup> searchAll(String filePath, Pager pager) {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList();
        }
        File[] files = file.listFiles();
        List backList = new ArrayList();
        for (int i = 0; i < files.length; ++i) {
            File newFile = files[i];
            if (!newFile.isFile()) {
                continue;
            }
            String fileName = newFile.getName();
            if (fileName.length() < 20) {
                continue;
            }
            Backup backup = new Backup();
            backup.setFileName(fileName);
            backup.setFileSize(Integer.valueOf((int) newFile.length() / 1024));
            String time = fileName.substring(7, fileName.lastIndexOf("."));
            if (time.length() != 19) {
                backup.setFileCreatTime(time);
            } else {
                time = time.substring(0, 10) + " " + time.substring(11).replaceAll("-", ":");
                backup.setFileCreatTime(time);
            }
            backList.add(backup);
        }
        List list = new ArrayList();
        int count = 0;
        while (backList.size() > 0) {
            Backup back = (Backup) backList.get(0);
            count = 0;
            for (int i = 1; i < backList.size(); ++i) {
                if (back.getFileCreatTime()
                        .compareTo(((Backup) backList.get(i)).getFileCreatTime()) < 0) {
                    back = (Backup) backList.get(i);
                    count = i;
                }
            }
            list.add(back);
            backList.remove(count);
        }

        splitPage(list, pager);
        if (list.size() < pager.getPageSize()) {
            return list;
        }
        int begain = pager.getPageSize() * (pager.getCurrentPage() - 1);
        int end = pager.getPageSize() + begain;
        if (end > list.size()) {
            end = list.size();
        }
        List newList = new ArrayList();
        for (int i = begain; i < end; ++i) {
            newList.add(list.get(i));
        }
        return newList;
    }

    public boolean resume(String mysqlBinPath, String id, String dataName, String userName,
            String password) {
        return BackUpTools.resume(mysqlBinPath, id, dataName, userName, password);
    }

    public BackupDao getBackupDAO() {
        return this.backupDAO;
    }

    public void setBackupDAO(BackupDao backupDAO) {
        this.backupDAO = backupDAO;
    }

    private void splitPage(List<Backup> list, Pager page) {
        int size = list.size();

        SysConfigManager sysConfigManager = PropertiesFileConfigManager.getInstance();
        int pageSize = Integer.valueOf(
                                       DatabaseSysConfigManager.getInstance()
                                               .getProperty("sys.split.pages")).intValue();

        page.init(size, pageSize);

        if ((page.getOperate() != null) && ("previous".equalsIgnoreCase(page.getOperate()))) {
            page.previous();
        }
        if ((page.getOperate() != null) && ("next".equalsIgnoreCase(page.getOperate()))) {
            page.next();
        }
        if ((page.getOperate() != null) && ("first".equalsIgnoreCase(page.getOperate()))) {
            page.first();
        }
        if ((page.getOperate() != null) && ("last".equalsIgnoreCase(page.getOperate())))
            page.last();
    }

    public String sqlbin(String path) {
        if (!new File(path).exists()) {
            System.out.println("******no file******");
            return "error";
        }
        SysConfigManager sysConfigManager = PropertiesFileConfigManager.getInstance();
        sysConfigManager.setProperty("sys.security.sqlbin.path", path);
        sysConfigManager.saveChange();
        return "success";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.impl.BackupRBoImpl JD-Core Version: 0.5.4
 */