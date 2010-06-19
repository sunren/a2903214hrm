package com.hr.report.bo;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.bo.interfaces.ILeavecalendarBO;
import com.hr.examin.dao.interfaces.ILeaverequestDAO;
import com.hr.examin.dao.interfaces.IOvertimerequestDAO;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimerequest;
import com.hr.examin.domain.Overtimetype;
import com.hr.report.dao.IReportDAO;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class ReportBoImpl implements IReportBo {
    private IReportDAO reportDAO;
    private ILeaverequestDAO lr_DAO;
    private IOvertimerequestDAO ot_DAO;
    private ILeavecalendarBO lc_BO;
    private IDepartmentBO d_BO;
    private ILocationBO l_BO;

    public List getObjects(Class clas, String[] fetch) {
        List result = this.reportDAO.getObjects(clas, fetch);
        return result;
    }

    /** @deprecated */
    public List<TableCell> getEmpLRHistory(String startTime, String endTime, String lrType,
            int lrStatusInclude, String group) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = df.parse(startTime);
            endDate = df.parse(endTime);
            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            c.set(5, c.getActualMinimum(5));
            startDate = c.getTime();
            c.setTime(endDate);
            c.set(5, c.getActualMaximum(5));
            c.set(11, c.getActualMaximum(11));
            endDate = c.getTime();
        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Leaverequest.class);

        detachedCrteria.add(Restrictions.ge(Leaverequest.PROP_LR_START_DATE, startDate));
        detachedCrteria.add(Restrictions.lt(Leaverequest.PROP_LR_END_DATE, endDate));

        if (!lrType.equals("all")) {
            detachedCrteria.add(Restrictions.eq(Leaverequest.PROP_LR_LT_NO, new Leavetype(lrType)));
        }

        if (lrStatusInclude == 1) {
            detachedCrteria.add(Restrictions.or(Restrictions.eq(Leaverequest.PROP_LR_STATUS,
                                                                new Integer(5)), Restrictions
                    .eq(Leaverequest.PROP_LR_STATUS, new Integer(15))));
        } else {
            detachedCrteria.add(Restrictions.eq(Leaverequest.PROP_LR_STATUS, new Integer(15)));
        }
        List lrList = this.lr_DAO.findByCriteria(detachedCrteria);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(5, startCalendar.getActualMinimum(5));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(5, endCalendar.getActualMaximum(5));
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);

        int month = endCalendar.get(2) - startCalendar.get(2) + 1;
        TreeMap listMap = new TreeMap(new ExaminCellCompare());
        if (group.equals("dept")) {
            List dList = this.d_BO.FindEnabledDepartment();
            for (int j = 0; j < dList.size(); ++j) {
                for (int i = 0; i < month; ++i)
                    listMap.put("|" + (startCalendar.get(2) + i) + "|"
                            + ((Department) dList.get(j)).getId(), new TableCell(startCalendar
                            .get(2)
                            + i + 1 + " 月", ((Department) dList.get(j)).getDepartmentName()));
            }
        } else if (group.equals("location")) {
            List lList = this.l_BO.FindAllLocation();
            for (int j = 0; j < lList.size(); ++j)
                for (int i = 0; i < month; ++i)
                    listMap.put("|" + (startCalendar.get(2) + i) + "|"
                            + ((Location) lList.get(j)).getId(), new TableCell(startCalendar.get(2)
                            + i + 1 + " 月", ((Location) lList.get(j)).getLocationName()));
        } else {
            for (int i = 0; i < month; ++i) {
                listMap.put("|" + (startCalendar.get(2) + i), new TableCell(startCalendar.get(2)
                        + i + 1 + " 月"));
            }
        }
        try {
            if (group.equals("dept"))
                for (int i = 0; i < lrList.size(); ++i) {
                    Leaverequest tempLr = (Leaverequest) lrList.get(i);
                    Calendar currentC = Calendar.getInstance();
                    currentC.setTime(tempLr.getLrStartDate());
                    TableCell currentCell = (TableCell) listMap.get("|" + currentC.get(2) + "|"
                            + tempLr.getLrEmpDept().getId());
                    currentCell.setValue(currentCell.getValue()
                            + tempLr.getLrTotalDays().doubleValue());
                }
            else if (group.equals("location"))
                for (int i = 0; i < lrList.size(); ++i) {
                    Leaverequest tempLr = (Leaverequest) lrList.get(i);
                    Calendar currentC = Calendar.getInstance();
                    currentC.setTime(tempLr.getLrStartDate());
                    TableCell currentCell = (TableCell) listMap.get("|" + currentC.get(2) + "|"
                            + tempLr.getLrEmpLocationNo().getId());
                    currentCell.setValue(currentCell.getValue()
                            + tempLr.getLrTotalDays().doubleValue());
                }
            else
                for (int i = 0; i < lrList.size(); ++i) {
                    Leaverequest tempLr = (Leaverequest) lrList.get(i);
                    Calendar currentC = Calendar.getInstance();
                    currentC.setTime(tempLr.getLrStartDate());
                    TableCell currentCell = (TableCell) listMap.get("|" + currentC.get(2));
                    currentCell.setValue(currentCell.getValue()
                            + tempLr.getLrTotalDays().doubleValue());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List returnList = new ArrayList();
        Iterator celliterator = listMap.values().iterator();
        while (celliterator.hasNext()) {
            returnList.add(celliterator.next());
        }
        return returnList;
    }

    /** @deprecated */
    public List<TableCell> getEmpLOTHistory(String startTime, String endTime, String lrType,
            int lrStatusInclude, String group) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = df.parse(startTime);
            endDate = df.parse(endTime);
            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            c.set(5, c.getActualMinimum(5));
            startDate = c.getTime();
            c.setTime(endDate);
            c.set(5, c.getActualMaximum(5));
            c.set(11, c.getActualMaximum(11));
            endDate = c.getTime();
        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }
        DetachedCriteria detachedCrteria = DetachedCriteria.forClass(Overtimerequest.class);

        detachedCrteria.add(Restrictions.ge(Overtimerequest.PROP_OR_END_DATE, startDate));
        detachedCrteria.add(Restrictions.le(Overtimerequest.PROP_OR_START_DATE, endDate));

        if (!lrType.equals("all")) {
            detachedCrteria.add(Restrictions.eq(Overtimerequest.PROP_OR_OT_NO, new Overtimetype(
                    lrType)));
        }

        if (lrStatusInclude == 1) {
            detachedCrteria.add(Restrictions.or(Restrictions.eq(Overtimerequest.PROP_OR_STATUS,
                                                                new Integer(5)), Restrictions
                    .eq(Overtimerequest.PROP_OR_STATUS, new Integer(15))));
        } else {
            detachedCrteria.add(Restrictions.eq(Overtimerequest.PROP_OR_STATUS, new Integer(15)));
        }
        List otList = this.lr_DAO.findByCriteria(detachedCrteria);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(5, startCalendar.getActualMinimum(5));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(5, endCalendar.getActualMaximum(5));
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);

        int month = endCalendar.get(2) - startCalendar.get(2) + 1;
        TreeMap listMap = new TreeMap(new ExaminCellCompare());
        if (group.equals("dept")) {
            List dList = this.d_BO.FindEnabledDepartment();
            for (int j = 0; j < dList.size(); ++j) {
                for (int i = 0; i < month; ++i)
                    listMap.put("|" + (startCalendar.get(2) + i) + "|"
                            + ((Department) dList.get(j)).getId(), new TableCell(startCalendar
                            .get(2)
                            + i + 1 + " 月", ((Department) dList.get(j)).getDepartmentName()));
            }
        } else if (group.equals("location")) {
            List lList = this.l_BO.FindAllLocation();
            for (int j = 0; j < lList.size(); ++j)
                for (int i = 0; i < month; ++i)
                    listMap.put("|" + (startCalendar.get(2) + i) + "|"
                            + ((Location) lList.get(j)).getId(), new TableCell(startCalendar.get(2)
                            + i + 1 + " 月", ((Location) lList.get(j)).getLocationName()));
        } else {
            for (int i = 0; i < month; ++i) {
                listMap.put("|" + (startCalendar.get(2) + i), new TableCell(startCalendar.get(2)
                        + i + 1 + " 月"));
            }
        }
        try {
            if (group.equals("dept"))
                for (int i = 0; i < otList.size(); ++i) {
                    Overtimerequest tempOt = (Overtimerequest) otList.get(i);
                    Calendar currentC = Calendar.getInstance();
                    currentC.setTime(tempOt.getOrStartDate());
                    TableCell currentCell = (TableCell) listMap.get("|" + currentC.get(2) + "|"
                            + tempOt.getOrEmpDept().getId());
                    currentCell.setValue(currentCell.getValue()
                            + tempOt.getOrTotalHours().doubleValue());
                }
            else if (group.equals("location"))
                for (int i = 0; i < otList.size(); ++i) {
                    Overtimerequest tempOt = (Overtimerequest) otList.get(i);
                    Calendar currentC = Calendar.getInstance();
                    currentC.setTime(tempOt.getOrStartDate());
                    TableCell currentCell = (TableCell) listMap.get("|" + currentC.get(2) + "|"
                            + tempOt.getOrEmpLocationNo().getId());
                    currentCell.setValue(currentCell.getValue()
                            + tempOt.getOrTotalHours().doubleValue());
                }
            else
                for (int i = 0; i < otList.size(); ++i) {
                    Overtimerequest tempOt = (Overtimerequest) otList.get(i);
                    Calendar currentC = Calendar.getInstance();
                    currentC.setTime(tempOt.getOrStartDate());
                    TableCell currentCell = (TableCell) listMap.get("|" + currentC.get(2));
                    currentCell.setValue(currentCell.getValue()
                            + tempOt.getOrTotalHours().doubleValue());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List returnList = new ArrayList();
        Iterator celliterator = listMap.values().iterator();
        while (celliterator.hasNext()) {
            returnList.add(celliterator.next());
        }
        return returnList;
    }

    public List exeHqlList(String hqlStr) {
        return this.reportDAO.exeHqlList(hqlStr);
    }

    public IReportDAO getReportDAO() {
        return this.reportDAO;
    }

    public void setReportDAO(IReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    public ILeaverequestDAO getLr_DAO() {
        return this.lr_DAO;
    }

    public void setLr_DAO(ILeaverequestDAO lr_DAO) {
        this.lr_DAO = lr_DAO;
    }

    public ILeavecalendarBO getLc_BO() {
        return this.lc_BO;
    }

    public void setLc_BO(ILeavecalendarBO lc_BO) {
        this.lc_BO = lc_BO;
    }

    public IDepartmentBO getD_BO() {
        return this.d_BO;
    }

    public void setD_BO(IDepartmentBO d_bo) {
        this.d_BO = d_bo;
    }

    public ILocationBO getL_BO() {
        return this.l_BO;
    }

    public void setL_BO(ILocationBO l_bo) {
        this.l_BO = l_bo;
    }

    public IOvertimerequestDAO getOt_DAO() {
        return this.ot_DAO;
    }

    public void setOt_DAO(IOvertimerequestDAO ot_DAO) {
        this.ot_DAO = ot_DAO;
    }
}