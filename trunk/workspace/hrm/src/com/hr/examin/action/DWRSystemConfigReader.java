package com.hr.examin.action;

import com.hr.base.DWRUtil;
import com.hr.examin.bo.LrDataCheckImpl;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.SystemPropertiesReader;

public class DWRSystemConfigReader {
    public String readLeaveRequestRemindContent(Leaverequest leaveRequest) {
        if (DWRUtil.checkAuth("empLRAddDo", "execute").endsWith("error")) {
            return "您无权执行此操作，请登录后重试！";
        }
        LrDataCheckImpl check = (LrDataCheckImpl) SpringBeanFactory.getBean("lrDataCheck");
        String msg = check.getLrTimeInfo(leaveRequest);
        SystemPropertiesReader reader = SystemPropertiesReader.getInstance();
        boolean remindEnable = reader.getBoolean("LR.Remind");
        if (!remindEnable) {
            return msg;
        }

        String remindString = msg + "\n\n";
        remindString = remindString
                + reader.getValue(new StringBuilder().append("LR.Remind.Wording_")
                        .append(leaveRequest.getLrLtNo().getLtSpecialCat()).toString(), reader
                        .getValue("LR.Remind.Wording"));
        return remindString;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.DWRSystemConfigReader JD-Core Version: 0.5.4
 */