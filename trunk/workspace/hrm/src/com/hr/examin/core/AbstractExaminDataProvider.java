package com.hr.examin.core;

import com.hr.examin.dao.interfaces.ILeavecalendarDAO;
import com.hr.examin.domain.Attdoriginaldata;
import com.hr.examin.domain.Leavecalendar;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;

public abstract class AbstractExaminDataProvider {
    protected List<Employee> emps;
    protected Date startDate;
    protected Date endDate;

    public abstract List provideLeaveRequestData();

    public abstract List provideOvertimeRequestData();

    public List<Leavecalendar> provideLeaveCalendarData() {
        ILeavecalendarDAO dao = (ILeavecalendarDAO) getBean("leavecalendarDAO");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavecalendar.class);
        return dao.findByCriteria(detachedCriteria);
    }

    public List provideEmpShiftData() {
        return new ArrayList(0);
    }

    public Map<String, String> provideDatabaseConfigProperties() {
        return DatabaseSysConfigManager.getInstance().getProperties();
    }

    public abstract Map<String, List<Attdoriginaldata>> provideOriginalData();

    public List<Employee> getEmps() {
        return this.emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static Object getBean(String beanName) {
        return SpringBeanFactory.getBean(beanName);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.core.AbstractExaminDataProvider JD-Core Version: 0.5.4
 */