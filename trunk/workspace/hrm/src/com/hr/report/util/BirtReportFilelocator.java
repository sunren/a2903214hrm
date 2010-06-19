package com.hr.report.util;

import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;

public final class BirtReportFilelocator {
    private static BirtReportFilelocator instance = new BirtReportFilelocator();

    private static final SysConfigManager sysConfigManager = PropertiesFileConfigManager
            .getInstance();

    public BirtReportFilelocator getInstance() {
        return instance;
    }

    public static String getBirtReportFileRelativeLocation(Integer type) {
        String result = sysConfigManager.getProperty("report.dir");
        if (type.intValue() == 1)
            result = sysConfigManager.getProperty("report.syspredefine");
        else if (type.intValue() == 9) {
            result = sysConfigManager.getProperty("report.custpredefine");
        }
        result = result.replace("\\", "/");
        return result + "/";
    }

    public static String getBirtReportFileAbsoluteLocation(ServletContext context, Integer type) {
        String result = "";

        String outputPath = context.getRealPath("/");
        outputPath = outputPath + "/";

        result = outputPath.replace("\\", "/");
        String relativePath = getBirtReportFileRelativeLocation(type);
        result = result + relativePath;
        File outputFolder = new File(outputPath);
        if ((!outputFolder.exists()) && (!outputFolder.mkdirs())) {
            try {
                throw new IOException("创建自定义报表输出文件目录时发生错误");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.util.BirtReportFilelocator JD-Core Version: 0.5.4
 */