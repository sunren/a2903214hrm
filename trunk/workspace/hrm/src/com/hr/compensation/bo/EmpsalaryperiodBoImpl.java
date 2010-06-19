package com.hr.compensation.bo;

import com.hr.compensation.dao.IEmpsalaryperiodDAO;
import com.hr.compensation.domain.Empsalaryperiod;
import com.hr.util.BaseCrit;
import com.hr.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.axis.utils.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class EmpsalaryperiodBoImpl implements IEmpsalaryperiodBo {
    private IEmpsalaryperiodDAO empsalaryperiodDAO;

    public List<String> getAllYear() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryperiod.class);
        List yearmonth = this.empsalaryperiodDAO.findByCriteria(detachedCriteria
                .setProjection(Projections.distinct(Projections.min("espdYearmonth"))));

        if ((yearmonth == null) || (yearmonth.size() == 0))
            return null;

        Integer endYear = Integer.valueOf(DateUtil.getYear(new Date()));
        Integer startYear = endYear;
        if (DateUtil.getMonth(new Date()) >= 11) {
            endYear = Integer.valueOf(endYear.intValue() + 1);
        }

        if ((yearmonth != null) && (yearmonth.size() > 0) && (yearmonth.get(0) != null)) {
            startYear = Integer.decode(((String) yearmonth.get(0)).substring(0, 4));
        }
        List year = new ArrayList();
        Integer localInteger1;
        Integer localInteger2;
        for (Integer i = startYear; i.intValue() <= endYear.intValue(); localInteger2 = i = Integer
                .valueOf(i.intValue() + 1)) {
            year.add(i.toString());

            localInteger1 = i;
        }

        return year;
    }

    public List<String> getPaidPeriod(Integer status, String startYearMonth, String endYearMonth) {
        if ((StringUtils.isEmpty(startYearMonth)) || (StringUtils.isEmpty(endYearMonth)))
            return null;

        DetachedCriteria dc = DetachedCriteria.forClass(Empsalaryperiod.class);
        BaseCrit.addDC(dc, Empsalaryperiod.PROP_ESPD_YEARMONTH, "between", new String[] {
                startYearMonth, endYearMonth });
        if (status != null)
            BaseCrit.addDC(dc, Empsalaryperiod.PROP_ESPD_STATUS, "eq", new Integer[] { status });
        return this.empsalaryperiodDAO.findByCriteria(dc.setProjection(Projections
                .distinct(Projections.property(Empsalaryperiod.PROP_ESPD_YEARMONTH))));
    }

    public List<Empsalaryperiod> getPayPeriods(Integer status, String[] yearMonthArray) {
        if ((yearMonthArray == null) || (yearMonthArray.length == 0))
            return null;

        DetachedCriteria dc = DetachedCriteria.forClass(Empsalaryperiod.class);
        BaseCrit.addDC(dc, Empsalaryperiod.PROP_ESPD_YEARMONTH, "in", yearMonthArray);
        if (status != null)
            BaseCrit.addDC(dc, Empsalaryperiod.PROP_ESPD_STATUS, "eq", new Integer[] { status });
        return this.empsalaryperiodDAO.findByCriteria(dc);
    }

    public IEmpsalaryperiodDAO getEmpsalaryperiodDAO() {
        return this.empsalaryperiodDAO;
    }

    public void setEmpsalaryperiodDAO(IEmpsalaryperiodDAO empsalaryperiodDAO) {
        this.empsalaryperiodDAO = empsalaryperiodDAO;
    }

    public Integer getEspdStatus(String yearmonth) {
        Empsalaryperiod espd = loadEspdStatus(yearmonth);
        if (espd != null) {
            return espd.getEspdStatus();
        }
        return new Integer(3);
    }

    public Integer getEspdStatusNew(String yearmonth) {
        Empsalaryperiod espd = loadEspdStatus(yearmonth);
        if (espd != null) {
            return espd.getEspdStatus();
        }
        return null;
    }

    public Empsalaryperiod loadEspdStatus(String yearmonth) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryperiod.class);
        detachedCriteria.add(Restrictions.eq(Empsalaryperiod.PROP_ESPD_YEARMONTH, yearmonth));
        detachedCriteria.addOrder(Order.desc(Empsalaryperiod.PROP_ESPD_STATUS));
        List result = this.empsalaryperiodDAO.findByCriteria(detachedCriteria);
        if (result.size() > 0) {
            return (Empsalaryperiod) result.get(0);
        }
        return null;
    }

    public void updateConfirmSubmit(String yearmonth, Integer status) {
        Empsalaryperiod period = loadEspdStatus(yearmonth);
        if ((status.intValue() == 2) && (period.getEspdStatus().intValue() == 1)) {
            period.setEspdStatus(status);
            this.empsalaryperiodDAO.updateObject(period);
        } else if ((status.intValue() == 0) && (period.getEspdStatus().intValue() == 1)) {
            period.setEspdStatus(status);
            this.empsalaryperiodDAO.updateObject(period);
        } else if ((status.intValue() == 1) && (period.getEspdStatus().intValue() == 0)) {
            period.setEspdStatus(status);
            this.empsalaryperiodDAO.updateObject(period);
        } else if ((status.intValue() == 0) && (period.getEspdStatus().intValue() == 2)) {
            period.setEspdStatus(status);
            this.empsalaryperiodDAO.updateObject(period);
        }
    }

    public boolean saveOrupdateObject(Object obj) {
        try {
            this.empsalaryperiodDAO.saveOrupdate(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.bo.EmpsalaryperiodBoImpl JD-Core Version: 0.5.4
 */