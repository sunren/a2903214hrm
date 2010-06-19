package com.hr.examin.bo;

import com.hr.examin.bo.interfaces.IAttenddailyBO;
import com.hr.examin.dao.interfaces.IAttenddailyDAO;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.shift.AttendDailyHandler;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public class AttenddailyBOImpl implements IAttenddailyBO {
    private IAttenddailyDAO attenddailyDAO;
    private List<Attendshift> attdShiftList;

    public List searchAttenddaily(Pager page, String emp, Employee employee, Date dayFrom,
            Date dayTo, Integer searchType, Integer attdType, boolean isExport, List empList) {
        List emps = empList;
        AttendDailyHandler handler = new AttendDailyHandler();
        handler.setEmpList(emps);
        handler.setSearchType(searchType);
        handler.setEndDate(dayTo);
        handler.setStartDate(dayFrom);
        handler.setPager(page);
        List rs = handler.generateRecords();
        return rs;
    }

    public List<Employee> searchEmployee(DetachedCriteria dc) {
        return this.attenddailyDAO.findByCriteria(dc);
    }

    public IAttenddailyDAO getAttenddailyDAO() {
        return this.attenddailyDAO;
    }

    public void setAttenddailyDAO(IAttenddailyDAO attenddailyDAO) {
        this.attenddailyDAO = attenddailyDAO;
    }

    public List<Attendshift> getAttdShiftList() {
        return this.attdShiftList;
    }

    public void setAttdShiftList(List<Attendshift> attdShiftList) {
        this.attdShiftList = attdShiftList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.AttenddailyBOImpl JD-Core Version: 0.5.4
 */