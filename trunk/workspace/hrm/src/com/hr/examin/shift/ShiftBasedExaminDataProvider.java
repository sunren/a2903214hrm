package com.hr.examin.shift;

import com.hr.examin.core.AbstractExaminDataProvider;
import com.hr.examin.dao.interfaces.IAttdoriginaldataDAO;
import com.hr.examin.dao.interfaces.IEmpshiftDAO;
import com.hr.examin.dao.interfaces.ILeaverequestDAO;
import com.hr.examin.dao.interfaces.IOvertimerequestDAO;
import com.hr.examin.domain.Attdoriginaldata;
import com.hr.examin.domain.Empshift;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;
import com.hr.profile.domain.Employee;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ShiftBasedExaminDataProvider extends AbstractExaminDataProvider {
    public List provideLeaveRequestData() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.endDate);
        cal.add(5, 1);
        Date tempEndDate = cal.getTime();

        Integer[] status = { Integer.valueOf(15), Integer.valueOf(16) };
        List resultList = new ArrayList();
        ILeaverequestDAO dao = (ILeaverequestDAO) getBean("leaverequestDAO");
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Leaverequest.class);
        if ((getEmpIds() != null) && (getEmpIds().length > 0)) {
            detachedCrteria.add(Restrictions.in("lrEmpNo.id", getEmpIds()));
        }
        detachedCrteria.add(Restrictions.in(Leaverequest.PROP_LR_STATUS, status));
        List lrList = dao.findByCriteria(detachedCrteria);
        Leaverequest lr = null;
        for (int i = 0; (lrList != null) && (i < lrList.size()); ++i) {
            lr = (Leaverequest) lrList.get(i);
            if ((((lr.getLrStartDate().getTime() > this.startDate.getTime()) || (lr.getLrEndDate()
                    .getTime() < this.startDate.getTime())))
                    && (((lr.getLrStartDate().getTime() <= this.startDate.getTime()) || (lr
                            .getLrEndDate().getTime() >= tempEndDate.getTime())))
                    && (((lr.getLrStartDate().getTime() > tempEndDate.getTime() + 86400000L) || (lr
                            .getLrEndDate().getTime() < tempEndDate.getTime() + 86400000L)))
                    && (((lr.getLrStartDate().getTime() < this.startDate.getTime()) || (tempEndDate
                            .getTime() > lr.getLrEndDate().getTime())))) {
                continue;
            }

            resultList.add(lr);
        }

        return resultList;
    }

    public Map<String, List<Attdoriginaldata>> provideOriginalData() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.endDate);
        cal.add(5, 1);
        Date tempEndDate = cal.getTime();

        IAttdoriginaldataDAO dao = (IAttdoriginaldataDAO) getBean("attdoriginaldataDAO");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Attdoriginaldata.class);
        if ((getEmpIds() != null) && (getEmpIds().length > 0)) {
            detachedCriteria.add(Restrictions.in("attdEmp.id", getEmpIds()));
        }
        detachedCriteria.add(Restrictions.ge(Attdoriginaldata.PROP_AOD_ATTD_DATE, this.startDate));
        detachedCriteria.add(Restrictions.le(Attdoriginaldata.PROP_AOD_ATTD_DATE, tempEndDate));
        detachedCriteria.addOrder(Order.asc(Attdoriginaldata.PROP_AOD_CARD_TIME));
        List<Attdoriginaldata> resultList = dao.findByCriteria(detachedCriteria);

        Map origEmpDateMap = new HashMap();
        String key = null;
        List tempList = null;
        for (Attdoriginaldata data : resultList) {
            key = data.getAttdEmp().getId() + "|" + data.getAodAttdDate().getTime();
            if (origEmpDateMap.get(key) == null) {
                tempList = new ArrayList();
                tempList.add(data);
                origEmpDateMap.put(key, tempList);
            } else {
                tempList = (List) origEmpDateMap.get(key);
                tempList.add(data);
                origEmpDateMap.put(key, tempList);
            }
        }

        return origEmpDateMap;
    }

    public List provideOvertimeRequestData() {
        List resultList = new ArrayList();

        Integer[] status = { Integer.valueOf(15), Integer.valueOf(16) };
        IOvertimerequestDAO dao = (IOvertimerequestDAO) getBean("overtimerequestDAO");
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Overtimerequest.class);
        if ((getEmpIds() != null) && (getEmpIds().length > 0)) {
            detachedCrteria.add(Restrictions.in("orEmpNo.id", getEmpIds()));
        }
        detachedCrteria.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, status));
        List orList = dao.findByCriteria(detachedCrteria);
        Overtimerequest or = null;
        for (int i = 0; (orList != null) && (i < orList.size()); ++i) {
            or = (Overtimerequest) orList.get(i);
            if ((((or.getOrStartDate().getTime() > this.startDate.getTime()) || (or.getOrEndDate()
                    .getTime() < this.startDate.getTime())))
                    && (((or.getOrStartDate().getTime() <= this.startDate.getTime()) || (or
                            .getOrEndDate().getTime() >= this.endDate.getTime())))
                    && (((or.getOrStartDate().getTime() > this.endDate.getTime() + 86400000L) || (or
                            .getOrEndDate().getTime() < this.endDate.getTime() + 86400000L)))
                    && (((or.getOrStartDate().getTime() < this.startDate.getTime()) || (this.endDate
                            .getTime() > or.getOrEndDate().getTime())))) {
                continue;
            }

            resultList.add(or);
        }

        return resultList;
    }

    public String[] getEmpIds() {
        String[] empIds = new String[this.emps.size()];
        Employee emp = null;
        for (int i = 0; (this.emps != null) && (i < this.emps.size()); ++i) {
            emp = (Employee) this.emps.get(i);
            empIds[i] = emp.getId();
        }

        return empIds;
    }

    public List provideEmpShiftData() {
        IEmpshiftDAO dao = (IEmpshiftDAO) getBean("empshiftDAO");
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Empshift.class);
        detachedCrteria.setFetchMode("empshiftShiftNo", FetchMode.JOIN);
        detachedCrteria.add(Restrictions.ge("empshiftDate", this.startDate));
        detachedCrteria.add(Restrictions.le("empshiftDate", this.endDate));
        String[] ids = getEmpIds();
        if (ids.length > 0) {
            detachedCrteria.add(Restrictions.in("empshiftEmpNo.id", ids));
        }
        return dao.findByCriteria(detachedCrteria);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.shift.ShiftBasedExaminDataProvider JD-Core Version: 0.5.4
 */