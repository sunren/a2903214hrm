package com.hr.examin.core;

import com.hr.examin.bo.interfaces.IAttendmonthlyBO;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.examin.bo.interfaces.IOvertimerequestBo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import java.util.HashMap;
import java.util.Map;

public abstract class ExaminBoFactory {
    public abstract IAttendmonthlyBO createAttendmonthlyBo();

    public ILeaverequestBO createLeaverequestBo() {
        return (ILeaverequestBO) getBean("leaverequestBO");
    }

    public IOvertimerequestBo createOvertimerequestBO() {
        return (IOvertimerequestBo) getBean("overtimerequestBo");
    }

    protected abstract String[] getDisplayDayProperties();

    protected abstract String[] getDisplayHourProperties();

    public Map<String, String> createJSPDisplayTextMap() {
        Map map = new HashMap();
        String[] dayProps = getDisplayDayProperties();
        for (int i = 0; i < dayProps.length; ++i) {
            map.put(dayProps[i], "天");
        }
        String[] hourProps = getDisplayHourProperties();
        for (int i = 0; i < hourProps.length; ++i) {
            map.put(hourProps[i], "小时");
        }
        return map;
    }

    public static ExaminBoFactory getInstance() {
        String shiftEnable = DatabaseSysConfigManager.getInstance()
                .getProperty("sys.examin.leave.mode");

        if (shiftEnable.indexOf("hour") != -1) {
            return (ExaminBoFactory) getBean("hourExaminFactory");
        }
        return (ExaminBoFactory) getBean("defaultExaminFactory");
    }

    protected static Object getBean(String beanName) {
        return SpringBeanFactory.getBean(beanName);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.core.ExaminBoFactory JD-Core Version: 0.5.4
 */