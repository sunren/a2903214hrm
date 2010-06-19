package com.hr.security.bo.impl;

import com.hr.security.domain.Backup;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.apache.log4j.Logger;

public class BackUpTools {
    static Logger logger = Logger.getLogger(Backup.class.getName());

    public static boolean backup(String mysqlBinPath, String filePath, String dataName,
            String userName, String password) {
        if ((mysqlBinPath == null) || (filePath == null)) {
            logger.info("mysqlBinPath == null || filePath == null //" + mysqlBinPath + "--"
                    + filePath);
            return false;
        }
        File file = new File(filePath.substring(0, filePath.lastIndexOf("/")));
        if (!file.exists()) {
            file.mkdirs();
        }
        if ((dataName == null) || (userName == null) || (password == null)) {
            logger.info("dataName == null || userName == null || password == null//" + dataName
                    + "--" + userName + "++" + password);
            return false;
        }
        String[] COMMAND_LAST = new String[3];
        mysqlBinPath = mysqlBinPath.replaceAll(" ", "\" \"");
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filePath));
            pw.println("");
            pw.flush();
            pw.close();
            filePath = filePath.replaceAll(" ", "\" \"");
            if (System.getProperty("os.name").indexOf("Windows") != -1) {
                COMMAND_LAST[0] = "cmd ";
                COMMAND_LAST[1] = "/c ";
                COMMAND_LAST[2] = (mysqlBinPath
                        + "/mysqldump --default-character-set=utf8 -h localhost -u " + userName
                        + (((password != null) && (!password.equals(""))) ? " -p" + password : "")
                        + " -l " + dataName + " >" + filePath);
                logger.info("COMMAND_LAST//" + COMMAND_LAST[0] + COMMAND_LAST[1] + COMMAND_LAST[2]);
                Process process = Runtime.getRuntime().exec(
                                                            COMMAND_LAST[0] + COMMAND_LAST[1]
                                                                    + COMMAND_LAST[2]);
                process.waitFor();
            } else {
                COMMAND_LAST[0] = "/bin/sh";
                COMMAND_LAST[1] = "-c";
                COMMAND_LAST[2] = (mysqlBinPath
                        + "/mysqldump --default-character-set=utf8 -h localhost -u " + userName
                        + (((password != null) && (!password.equals(""))) ? " -p" + password : "")
                        + " -l " + dataName + " >" + filePath);
                logger.info("COMMAND_LAST//" + COMMAND_LAST[0] + COMMAND_LAST[1] + COMMAND_LAST[2]);
                Process process = Runtime.getRuntime().exec(COMMAND_LAST);
                process.destroy();
                process.waitFor();
            }
        } catch (Exception e) {
            logger.info("e//" + e);
            e.printStackTrace();
        }
        return true;
    }

    public static boolean resume(String mysqlBinPath, String filePath, String dataName,
            String userName, String password) {
        if ((mysqlBinPath == null) || (filePath == null)) {
            logger.info("mysqlBinPath == null || filePath == null //" + mysqlBinPath + "--"
                    + filePath);
            return false;
        }
        if ((dataName == null) || (userName == null) || (password == null)) {
            logger.info("dataName == null || userName == null || password == null//" + dataName
                    + "--" + userName + "++" + password);
            return false;
        }
        try {
            File sqlFile = new File(filePath);
            if (!sqlFile.exists()) {
                logger.info("filePath//" + filePath);
                return false;
            }
            filePath = filePath.replaceAll(" ", "\" \"");
            mysqlBinPath = mysqlBinPath.replaceAll(" ", "\" \"");
            String[] COMMAND_TAIL = new String[3];
            if (System.getProperty("os.name").indexOf("Windows") != -1) {
                COMMAND_TAIL[0] = "cmd ";
                COMMAND_TAIL[1] = "/c ";
                COMMAND_TAIL[2] = (mysqlBinPath
                        + "/mysql --default-character-set=utf8 -h localhost -u " + userName
                        + (((password != null) && (!password.equals(""))) ? " -p" + password : "")
                        + " " + dataName + " <" + filePath);
                Process process = Runtime.getRuntime().exec(
                                                            COMMAND_TAIL[0] + COMMAND_TAIL[1]
                                                                    + COMMAND_TAIL[2]);
                process.waitFor();
            } else {
                COMMAND_TAIL[0] = "/bin/sh";
                COMMAND_TAIL[1] = "-c";
                COMMAND_TAIL[2] = (mysqlBinPath
                        + "/mysql --default-character-set=utf8 -h localhost -u " + userName
                        + (((password != null) && (!password.equals(""))) ? " -p" + password : "")
                        + " " + dataName + " <" + filePath);
                logger.info("COMMAND_LAST//" + COMMAND_TAIL[0] + COMMAND_TAIL[1] + COMMAND_TAIL[2]);
                Process process = Runtime.getRuntime().exec(COMMAND_TAIL);
                process.waitFor();
            }
        } catch (Exception e) {
            logger.info("e//" + e);
            return false;
        }
        return true;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.impl.BackUpTools JD-Core Version: 0.5.4
 */