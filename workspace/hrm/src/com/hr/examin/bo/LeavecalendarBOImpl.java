package com.hr.examin.bo;

import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Location;
import com.hr.examin.bo.interfaces.ILeavecalendarBO;
import com.hr.examin.dao.interfaces.ILeavecalendarDAO;
import com.hr.examin.domain.Leavecalendar;
import com.hr.util.DateUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class LeavecalendarBOImpl implements ILeavecalendarBO {
    private ILeavecalendarDAO lc_DAO;
    private ILocationBO l_BO;

    public Leavecalendar loadLeavecalendar(String lcId) {
        String[] fetch = { Leavecalendar.PROP_LC_LOCATION_NO };
        return (Leavecalendar) this.lc_DAO.loadObject(Leavecalendar.class, lcId, fetch,
                                                      new boolean[0]);
    }

    public List<String> insertLeavecalendar(Leavecalendar lc) {
        List result = null;
        if ((lc.getLcLocationNo() != null) && (lc.getLcLocationNo().getId() != null)
                && (lc.getLcLocationNo().getId().length() > 0)
                && (this.l_BO.loadLocation(lc.getLcLocationNo().getId()) == null)) {
            result = new ArrayList();
            result.add("不存在的地址＄1�7");
            return result;
        }

        if (((result = checkBussiness(lc)) != null) && (result.size() > 0))
            return result;
        try {
            this.lc_DAO.saveObject(lc);
        } catch (Exception e) {
            result = new ArrayList();
            result.add(e.getMessage());
        }
        return result;
    }

    public List<String> deleteLeavecalendar(String lcId) {
        List result = null;
        if (loadLeavecalendar(lcId) == null) {
            result = new ArrayList();
            result.add("数据不存在，或�1�7�已经删除！");
            return result;
        }
        try {
            this.lc_DAO.deleteObject(Leavecalendar.class, lcId);
        } catch (Exception e) {
            result = new ArrayList();
            result.add(e.getMessage());
        }
        return result;
    }

    public List<String> updateLeavecalendar(Leavecalendar lc) {
        List result = null;
        if (loadLeavecalendar(lc.getLcId()) == null) {
            result = new ArrayList();
            result.add("数据不存在，或�1�7�已经删除！");
            return result;
        }
        if ((lc.getLcLocationNo() != null) && (lc.getLcLocationNo().getId() != null)
                && (lc.getLcLocationNo().getId().length() > 0)
                && (this.l_BO.loadLocation(lc.getLcLocationNo().getId()) == null)) {
            result = new ArrayList();
            result.add("不存在的地址＄1�7");
            return result;
        }
        if (((result = checkBussiness(lc)) != null) && (result.size() > 0))
            return result;
        try {
            this.lc_DAO.updateLeavecalendar(lc);
        } catch (Exception e) {
            result = new ArrayList();
            result.add(e.getMessage());
        }
        return result;
    }

    private List<String> checkBussiness(Leavecalendar lc) {
        List result = null;
        Calendar lc_calendar = Calendar.getInstance();
        lc_calendar.setTime(lc.getLcDate());
        if ((lc.getLcSign().intValue() == 0)
                && (((lc_calendar.get(7) == 7) || (lc_calendar.get(7) == 1)))) {
            result = new ArrayList();
            result.add("周末默认是休息日，不能重复添加！");
            return result;
        }
        if ((lc.getLcSign().intValue() == 1) && (lc_calendar.get(7) != 7)
                && (lc_calendar.get(7) != 1)) {
            result = new ArrayList();
            result.add("周一至周五默认是工作日，不能重复添加＄1�7");
            return result;
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavecalendar.class);
        detachedCriteria.add(Restrictions.eq(Leavecalendar.PROP_LC_DATE, lc.getLcDate()));
        List templist = this.lc_DAO.findByCriteria(detachedCriteria);
        if ((templist != null) && (templist.size() != 0)) {
            for (int i = 0; i < templist.size(); ++i) {
                Leavecalendar templc = (Leavecalendar) templist.get(i);
                if (lc.getLcId().equals(templc.getLcId())) {
                    continue;
                }
                if (lc.getLcLocationNo() == null) {
                    if (templc.getLcLocationNo() == null) {
                        result = new ArrayList();
                        result.add("与全部地匄1�7 出现冲突＄1�7");
                        return result;
                    }
                    if (lc.getLcSign() == templc.getLcSign()) {
                        result = new ArrayList();
                        result.add("种类与部分地匄1�7 出现冲突＄1�7");
                        return result;
                    }
                } else {
                    if ((templc.getLcLocationNo() != null)
                            && (templc.getLcLocationNo().equals(lc.getLcLocationNo()))) {
                        result = new ArrayList();
                        result.add("地区 出现冲突＄1�7");
                        return result;
                    }
                    if ((templc.getLcLocationNo() == null)
                            && (templc.getLcSign() == lc.getLcSign())) {
                        result = new ArrayList();
                        result.add("种类与全部地匄1�7 出现冲突＄1�7");
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public List<Leavecalendar> getCalendarByYear(int year) {
        Calendar startYesr = Calendar.getInstance();
        startYesr.set(year, 0, 1, 0, 0, 0);
        startYesr.set(14, 0);
        Calendar endYesr = Calendar.getInstance();
        endYesr.set(year, 11, 31, 0, 0, 0);
        endYesr.set(14, 0);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavecalendar.class);
        detachedCriteria.setFetchMode(Leavecalendar.PROP_LC_LOCATION_NO, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.between(Leavecalendar.PROP_LC_DATE, startYesr.getTime(),
                                                  endYesr.getTime()));
        detachedCriteria.addOrder(Order.asc(Leavecalendar.PROP_LC_DATE));
        List calendarList = this.lc_DAO.findByCriteria(detachedCriteria);
        return calendarList;
    }

    /** @deprecated */
    public List<Leavecalendar> getCalendarListByDay(Date start, Date end, Location location) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavecalendar.class);

        detachedCriteria.add(Restrictions.between(Leavecalendar.PROP_LC_DATE, start, end));

        detachedCriteria.add(Restrictions
                .or(Restrictions.isNull(Leavecalendar.PROP_LC_LOCATION_NO), Restrictions
                        .eq(Leavecalendar.PROP_LC_LOCATION_NO, location)));

        detachedCriteria.addOrder(Order.asc(Leavecalendar.PROP_LC_DATE));

        List calendarList = this.lc_DAO.findByCriteria(detachedCriteria);
        return calendarList;
    }

    public List<Leavecalendar> getCalendarListByDay(Date startDate, Date endDate) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavecalendar.class);
        startDate = DateUtil.convDateTimeToDate(startDate);
        endDate = DateUtil.convDateTimeToDate(endDate);

        detachedCriteria.add(Restrictions.between(Leavecalendar.PROP_LC_DATE, startDate, endDate));

        detachedCriteria.addOrder(Order.asc(Leavecalendar.PROP_LC_DATE));

        List result = this.lc_DAO.findByCriteria(detachedCriteria);
        if (result.size() == 0)
            return null;
        return result;
    }

    public int getWorkDays(Date startDate, Date endDate, List<Leavecalendar> lcList, Location empLoc) {
        startDate = DateUtil.convDateTimeToDate(startDate);
        endDate = DateUtil.convDateTimeToDate(endDate);

        int days = DateUtil.getWorkingDay(startDate, endDate);

        if (lcList == null)
            return days;

        for (Leavecalendar lc : lcList) {
            if (DateUtil.dateDiff(lc.getLcDate(), startDate, 5) > 0)
                continue;
            if (DateUtil.dateDiff(lc.getLcDate(), endDate, 5) < 0) {
                continue;
            }

            if ((lc.getLcLocationNo() != null)
                    && (!lc.getLcLocationNo().getId().equals(empLoc.getId()))) {
                continue;
            }
            switch (lc.getLcSign().intValue()) {
            case 0:
                --days;
                break;
            case 1:
                ++days;
                break;
            case 2:
                int weekFlag = DateUtil.getWeekDay(lc.getLcDate());
                if ((weekFlag != 7) && (weekFlag != 1))
                    --days;
                break;
            default:
                continue;
            }
        }
        return days;
    }

    public int isACalendarDay(Date inputDate, List<Leavecalendar> lcList, Location empLoc) {
        inputDate = DateUtil.convDateTimeToDate(inputDate);
        Iterator i$;
        if (lcList != null) {
            for (i$ = lcList.iterator(); i$.hasNext();) {
                Leavecalendar lc = (Leavecalendar) i$.next();
                if (DateUtil.dateDiff(lc.getLcDate(), inputDate, 5) != 0) {
                    continue;
                }
                if ((lc.getLcLocationNo() == null)
                        || (lc.getLcLocationNo().getId().equals(empLoc.getId()))) {
                    return lc.getLcSign().intValue();
                }
            }

        }
        Leavecalendar lc;
        int weekFlag = DateUtil.getWeekDay(inputDate);
        if ((weekFlag == 7) || (weekFlag == 1)) {
            return 0;
        }
        return 1;
    }

    /** @deprecated */
    public int getWorkingDaysByLocal(Date startDate, Date endDate, Location currentLocal) {
        int days = DateUtil.getWorkingDay(startDate, endDate);

        List<Leavecalendar> calendarList = getCalendarListByDay(DateUtil.convDateTimeToDate(startDate), DateUtil
                .convDateTimeToDate(startDate), currentLocal);

        for (Leavecalendar lc : calendarList) {
            if ((lc != null) && (1 == lc.getLcSign().intValue())) {
                ++days;
            }

            int weekFlag = DateUtil.getWeekDay(lc.getLcDate());
            if ((lc != null)
                    && (((0 == lc.getLcSign().intValue()) || ((2 == lc.getLcSign().intValue())
                            && (weekFlag != 7) && (weekFlag != 1))))) {
                --days;
            }
        }
        return days;
    }

    /** @deprecated */
    public int isACalendarDay(Date inpuDate, Location currentLocal) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date changeDate = null;
        try {
            changeDate = df.parse(df.format(inpuDate));
        } catch (ParseException e) {
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavecalendar.class);

        detachedCriteria.add(Restrictions.eq(Leavecalendar.PROP_LC_DATE, changeDate));
        detachedCriteria.add(Restrictions.or(Restrictions.eq(Leavecalendar.PROP_LC_LOCATION_NO,
                                                             currentLocal), Restrictions
                .isNull(Leavecalendar.PROP_LC_LOCATION_NO)));

        List calendarList = this.lc_DAO.findByCriteria(detachedCriteria);
        Calendar dateCal = new GregorianCalendar();
        dateCal.setTime(changeDate);

        if ((calendarList != null) && (calendarList.size() > 0)) {
            Leavecalendar lc = (Leavecalendar) calendarList.get(0);
            return lc.getLcSign().intValue();
        }

        if ((dateCal.get(7) == 1) || (dateCal.get(7) == 7)) {
            return 0;
        }
        return 1;
    }

    public ILeavecalendarDAO getLc_DAO() {
        return this.lc_DAO;
    }

    public void setLc_DAO(ILeavecalendarDAO lc_DAO) {
        this.lc_DAO = lc_DAO;
    }

    public ILocationBO getL_BO() {
        return this.l_BO;
    }

    public void setL_BO(ILocationBO l_bo) {
        this.l_BO = l_bo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.LeavecalendarBOImpl JD-Core Version: 0.5.4
 */