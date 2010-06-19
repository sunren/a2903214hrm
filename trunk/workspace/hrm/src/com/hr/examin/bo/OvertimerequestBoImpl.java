package com.hr.examin.bo;

import com.hr.base.Status;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IStatBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Department;
import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.OvertimeFormBean;
import com.hr.examin.bo.interfaces.IAttendshiftBO;
import com.hr.examin.bo.interfaces.IEmpshiftBo;
import com.hr.examin.bo.interfaces.ILeavecalendarBO;
import com.hr.examin.bo.interfaces.IOvertimerequestBo;
import com.hr.examin.bo.interfaces.IOvertimetypeBo;
import com.hr.examin.bo.interfaces.IWorkFlowBO;
import com.hr.examin.dao.interfaces.IOvertimerequestDAO;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Empshift;
import com.hr.examin.domain.Overtimerequest;
import com.hr.examin.domain.Overtimetype;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import com.hr.util.StringUtil;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class OvertimerequestBoImpl extends ExaminBoImpl implements IOvertimerequestBo {
    private IOvertimetypeBo or_Bo;
    private IStatBO st_BO;
    private IDepartmentBO dp_BO;
    private IWorkFlowBO workFlowBO;
    private ILeavecalendarBO lcBO;
    private static final String table_name = "overtimerequest";

    public String orInitTime(OvertimeFormBean of_Bean) throws Exception {
        return "SUCC";
    }

    public String orCheckShifts(Overtimerequest or) throws Exception {
        IEmpshiftBo empShiftBo = (IEmpshiftBo) SpringBeanFactory.getBean("empshiftBO");

        Date otDate = DateUtil.convDateTimeToDate(or.getOrStartDate());

        if (or.getOrEmpNo().getEmpShiftType().intValue() == 3) {
            List list = empShiftBo.getEmpShiftList(DateUtil.dateAdd(otDate, -1), otDate, or
                    .getOrEmpNo().getId());
            if (list == null) {
                return "SUCC";
            }

            Iterator empShiftIterator = list.iterator();
            while (empShiftIterator.hasNext()) {
                Empshift es = (Empshift) empShiftIterator.next();

                if ((es.getEmpshiftDate().before(otDate))
                        && (es.getEmpshiftShiftNo().getAttsNightShift().intValue() != 1)) {
                    continue;
                }
                String isNightShift = "";
                if (es.getEmpshiftShiftNo().getAttsNightShift().intValue() == 1) {
                    isNightShift = "夜班";
                }

                int isInShift = ExaminDateUtil.isTimeInShift(or.getOrStartDate(), es
                        .getEmpshiftDate(), es.getEmpshiftShiftNo().getAttsSession());
                if ((isInShift == 1) || (isInShift == 2) || (isInShift == 4)) {
                    return "弄1�7始时间与您的" + isNightShift + "班次 "
                            + es.getEmpshiftShiftNo().getAttsSession() + " 有冲突！";
                }

                isInShift = ExaminDateUtil.isTimeInShift(or.getOrEndDate(), es.getEmpshiftDate(),
                                                         es.getEmpshiftShiftNo().getAttsSession());
                if ((isInShift == 2) || (isInShift == 3) || (isInShift == 4)) {
                    return "结束时间与您的1�7" + isNightShift + "班次 "
                            + es.getEmpshiftShiftNo().getAttsSession() + " 有冲突！";
                }

                int timeInShift = ExaminDateUtil.minutesInShift(or.getOrStartDate(), or
                        .getOrEndDate(), es.getEmpshiftDate(), es.getEmpshiftShiftNo()
                        .getAttsSession());
                if (timeInShift > 0)
                    return "加班时间与您的1�7" + isNightShift + "班次 "
                            + es.getEmpshiftShiftNo().getAttsSession() + " 有冲突！";
            }
        } else {
            List lcList = this.lcBO.getCalendarListByDay(or.getOrStartDate(), or.getOrStartDate());
            int holidayFlag = this.lcBO.isACalendarDay(or.getOrStartDate(), lcList, or.getOrEmpNo()
                    .getEmpLocationNo());
            if ((holidayFlag == 0) || (holidayFlag == 2)) {
                return "SUCC";
            }

            IAttendshiftBO attendshiftBo = (IAttendshiftBO) SpringBeanFactory
                    .getBean("attendshiftBO");
            Attendshift as = attendshiftBo.getDefaultAttendshiftByEmp(or.getOrEmpNo());

            int isInShift = ExaminDateUtil.isTimeInShift(or.getOrStartDate(), otDate, as
                    .getAttsSession());
            if ((isInShift == 1) || (isInShift == 2)) {
                return "弄1�7始时间与您的班次 " + as.getAttsSession() + " 有冲突！";
            }

            isInShift = ExaminDateUtil
                    .isTimeInShift(or.getOrEndDate(), otDate, as.getAttsSession());
            if ((isInShift == 2) || (isInShift == 3)) {
                return "加班时间与您的班欄1�7 " + as.getAttsSession() + " 有冲突！";
            }

            int timeInShift = ExaminDateUtil.minutesInShift(or.getOrStartDate(), or.getOrEndDate(),
                                                            otDate, as.getAttsSession());
            if (timeInShift > 0) {
                return "加班时间与您的班欄1�7 " + as.getAttsSession() + " 有冲突！";
            }
        }
        return "SUCC";
    }

    public String orCheckTime(Overtimerequest or) {
        String msgConflict = "与编号为{0}的加班单存在时间冲突＄1�7";

        DetachedCriteria dc = DetachedCriteria.forClass(Overtimerequest.class);

        dc.add(Restrictions.eq(Overtimerequest.PROP_OR_EMP_NO, or.getOrEmpNo()));
        dc.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, Status.PROCESS_VALID));

        Date startTime = or.getOrStartDate();
        Date endTime = or.getOrEndDate();
        dc.add(Restrictions.lt(Overtimerequest.PROP_OR_START_DATE, endTime));
        dc.add(Restrictions.gt(Overtimerequest.PROP_OR_END_DATE, startTime));

        List resultList = this.or_DAO.findByCriteria(dc);
        if ((resultList == null) || (resultList.size() == 0))
            return "SUCC";
        if ((resultList.size() == 1)
                && (((Overtimerequest) resultList.get(0)).getOrId().equals(or.getOrId()))) {
            return "SUCC";
        }
        return StringUtil.message(msgConflict, new Object[] { ((Overtimerequest) resultList.get(0))
                .getOrNo() });
    }

    public double getTotalTime(Overtimerequest or, Date startDate, Date endDate, Integer[] statusSet) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Overtimerequest.class);

        Date startD = DateUtil.convDateTimeToDate(startDate);
        Date endD = DateUtil.convDateTimeToDate(DateUtil.dateAdd(endDate, 1));

        detachedCriteria
                .add(Restrictions.between(Overtimerequest.PROP_OR_START_DATE, startD, endD));

        detachedCriteria.add(Restrictions.eq(Overtimerequest.PROP_OR_OT_NO, or.getOrOtNo()));
        detachedCriteria.add(Restrictions.eq(Overtimerequest.PROP_OR_EMP_NO, or.getOrEmpNo()));
        detachedCriteria.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, statusSet));

        detachedCriteria.setProjection(Projections.projectionList()
                .add(Projections.sum(Overtimerequest.PROP_OR_TOTAL_HOURS)));
        List orList = this.or_DAO.findByCriteria(detachedCriteria);
        if ((orList == null) || (orList.size() == 0) || (orList.get(0) == null))
            return 0.0D;

        BigDecimal total = (BigDecimal) orList.get(0);
        return total.doubleValue();
    }

    public double getTotalTimeRecal(Overtimerequest or, Date startDate, Date endDate,
            Integer[] statusSet) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Overtimerequest.class);

        Date startD = DateUtil.convDateTimeToDate(startDate);
        Date endD = DateUtil.convDateTimeToDate(DateUtil.dateAdd(endDate, 1));

        detachedCriteria
                .add(Restrictions.between(Overtimerequest.PROP_OR_START_DATE, startD, endD));

        detachedCriteria.add(Restrictions.eq(Overtimerequest.PROP_OR_OT_NO, or.getOrOtNo()));
        detachedCriteria.add(Restrictions.eq(Overtimerequest.PROP_OR_EMP_NO, or.getOrEmpNo()));
        detachedCriteria.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, statusSet));

        List<Overtimerequest> orList = this.or_DAO.findByCriteria(detachedCriteria);
        if ((orList == null) || (orList.size() == 0))
            return 0.0D;

        double totalTime = 0.0D;
        boolean recalOr = false;
        for (Overtimerequest orTemp : orList)
            if (orTemp.getOrId().equals(or.getOrId())) {
                recalOr = true;
                totalTime += or.getOrTotalHours().doubleValue();
            } else {
                totalTime += orTemp.getOrTotalHours().doubleValue();
            }
        if (recalOr) {
            return totalTime;
        }
        return totalTime + or.getOrTotalHours().doubleValue();
    }

    public double getTotalOTTiaoxiuTime(Overtimerequest ot, Date startDate, Date endDate,
            int[] statusSet) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Overtimerequest.class);
        detachedCriteria.add(Restrictions.ge(Overtimerequest.PROP_OR_START_DATE, startDate));
        detachedCriteria.add(Restrictions.le(Overtimerequest.PROP_OR_END_DATE, endDate));
        detachedCriteria.add(Restrictions.eq(Overtimerequest.PROP_OR_EMP_NO, ot.getOrEmpNo()));
        if (ot.getOrOtNo() != null) {
            detachedCriteria.add(Restrictions.eq(Overtimerequest.PROP_OR_OT_NO, ot.getOrOtNo()));
        }
        Integer[] status = new Integer[statusSet.length];
        for (int i = 0; i < status.length; ++i) {
            status[i] = new Integer(statusSet[i]);
        }
        detachedCriteria.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, status));
        if ((ot.getOrId() != null) && (!ot.getOrId().equals(""))) {
            detachedCriteria.add(Restrictions.ne(Overtimerequest.PROP_OR_ID, ot.getOrId()));
        }
        detachedCriteria.setProjection(Projections.projectionList()
                .add(Projections.sum(Overtimerequest.PROP_OR_TIAOXIU_HOURS)));
        List list = this.or_DAO.findByCriteria(detachedCriteria);
        if ((list != null) && (list.size() > 0)) {
            BigDecimal b = (BigDecimal) list.get(0);
            if (b != null) {
                return b.doubleValue();
            }
        }
        return 0.0D;
    }

    public List<Overtimerequest> getEmpAllTiaoxiuOrList(Overtimerequest ot, int[] statusSet) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Overtimerequest.class);
        detachedCriteria.add(Restrictions.eq(Overtimerequest.PROP_OR_EMP_NO, ot.getOrEmpNo()));
        if (ot.getOrOtNo() != null) {
            detachedCriteria.add(Restrictions.eq(Overtimerequest.PROP_OR_OT_NO, ot.getOrOtNo()));
        }
        Integer[] status = new Integer[statusSet.length];
        for (int i = 0; i < status.length; ++i) {
            status[i] = new Integer(statusSet[i]);
        }
        detachedCriteria.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, status));
        if ((ot.getOrId() != null) && (!ot.getOrId().equals(""))) {
            detachedCriteria.add(Restrictions.ne(Overtimerequest.PROP_OR_ID, ot.getOrId()));
        }
        detachedCriteria.add(Restrictions.gt(Overtimerequest.PROP_OR_TIAOXIU_HOURS, new BigDecimal(
                0.0D)));
        detachedCriteria.addOrder(Order.asc(Overtimerequest.PROP_OR_START_DATE));

        List orList = this.or_DAO.findByCriteria(detachedCriteria);
        return orList;
    }

    /** @deprecated */
    public Overtimerequest searchByOrId(String orId) {
        String[] fetch = { Overtimerequest.PROP_OR_OT_NO, Overtimerequest.PROP_OR_EMP_NO,
                Overtimerequest.PROP_OR_EMP_DEPT, Overtimerequest.PROP_OR_CREATE_BY,
                Overtimerequest.PROP_OR_LAST_CHANGE_BY };

        Object results = this.or_DAO.loadObject(Overtimerequest.class, orId, fetch, new boolean[0]);
        if (results == null)
            return null;
        Overtimerequest result = (Overtimerequest) results;
        return result;
    }

    /** @deprecated */
    public List<String> deleteOvertimereuqest(String orId, Employee currentEmployee) {
        List errors = new ArrayList();
        try {
            Overtimerequest or = searchByOrId(orId);
            if (or == null) {
                throw new Exception("操作不成功，请重试！");
            }

            ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
            logBO.deleteLogs("overtimerequest", orId + "");

            this.or_DAO.deleteObject(or);
            return errors;
        } catch (Exception e) {
            e.printStackTrace();
            errors.add(e.getMessage());
        }
        return errors;
    }

    public String checkMonthLimit(Overtimerequest or) {
        String msgConflict = "{0}当月加班朄1�7多为{1}小时，您的申请已超过上限＄1�7";

        Overtimetype overtimetype = this.or_Bo.searchByID(or.getOrOtNo().getOtNo());
        if (overtimetype.getOtOverLimit() == null)
            return "SUCC";

        double limitTime = overtimetype.getOtOverLimit().intValue();

        String config = DatabaseSysConfigManager.getInstance().getProperty("sys.examin.month.sum");
        Date[] dateArr = ExaminDateUtil.getDateArray(or.getOrStartDate(), config);
        double actualTime = getTotalTimeRecal(or, dateArr[0], dateArr[1], Status.PROCESS_VALID);

        if (actualTime <= limitTime) {
            return "SUCC";
        }
        return StringUtil.message(msgConflict, new Object[] { or.getOrOtNo().getOtName(),
                Double.valueOf(limitTime) });
    }

    public double getMonthTotal(Overtimerequest or, Integer[] status) {
        String config = DatabaseSysConfigManager.getInstance().getProperty("sys.examin.month.sum");
        Date[] dateArr = ExaminDateUtil.getDateArray(or.getOrStartDate(), config);
        double usedTime = getTotalTime(or, dateArr[0], dateArr[1], status);

        return usedTime;
    }

    public List<Overtimerequest> loadOvertimeRequests(String[] orIDs, String[] fetch) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Overtimerequest.class);
            for (int i = 0; i < fetch.length; ++i) {
                dc.setFetchMode(fetch[i], FetchMode.JOIN);
            }
            BaseCrit.addDC(dc, Overtimerequest.PROP_OR_ID, "in", orIDs);

            dc.addOrder(Order.asc(Overtimerequest.PROP_OR_NO));

            List result = this.or_DAO.findByCriteria(dc);
            if (result.size() == 0) {
                return null;
            }
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    public Overtimerequest loadOvertimerequest(String orID) {
        String[] fetch = { Overtimerequest.PROP_OR_EMP_NO,
                Overtimerequest.PROP_OR_EMP_NO + "." + Employee.PROP_EMP_LOCATION_NO,
                Overtimerequest.PROP_OR_EMP_NO + "." + Employee.PROP_EMP_DEPT_NO,
                Overtimerequest.PROP_OR_CREATE_BY,
                Overtimerequest.PROP_OR_CREATE_BY + "." + Employee.PROP_EMP_LOCATION_NO,
                Overtimerequest.PROP_OR_CREATE_BY + "." + Employee.PROP_EMP_DEPT_NO,
                Overtimerequest.PROP_OR_LAST_CHANGE_BY,
                Overtimerequest.PROP_OR_LAST_CHANGE_BY + "." + Employee.PROP_EMP_LOCATION_NO,
                Overtimerequest.PROP_OR_LAST_CHANGE_BY + "." + Employee.PROP_EMP_DEPT_NO,
                Overtimerequest.PROP_OR_EMP_DEPT, Overtimerequest.PROP_OR_OT_NO };

        Overtimerequest resultOR = (Overtimerequest) this.or_DAO.loadObject(Overtimerequest.class,
                                                                            orID, fetch,
                                                                            new boolean[0]);

        return resultOR;
    }

    public Overtimerequest loadOvertimerequest(String orID, String[] fetch) {
        Overtimerequest resultOR = (Overtimerequest) this.or_DAO.loadObject(Overtimerequest.class,
                                                                            orID, fetch,
                                                                            new boolean[0]);

        return resultOR;
    }

    public void examinSearch(ExaminSearchBean searchBean, DetachedCriteria dc, Pager page) {
        dc.setFetchMode(Overtimerequest.PROP_OR_CREATE_BY, FetchMode.JOIN);
        dc.setFetchMode(Overtimerequest.PROP_OR_LAST_CHANGE_BY, FetchMode.JOIN);
        dc.createAlias(Overtimerequest.PROP_OR_EMP_NO, "emp", 1);
        dc.createAlias(Overtimerequest.PROP_OR_CREATE_BY, "creator");
        dc.createAlias(Overtimerequest.PROP_OR_EMP_DEPT, "dept");
        dc.createAlias(Overtimerequest.PROP_OR_OT_NO, "orOtNo", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "empOrgDept", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_LOCATION_NO, "empOrgLoc", 1);
        try {
            if (!StringUtils.isEmpty(searchBean.getNo()))
                BaseCrit.addDC(dc, Overtimerequest.PROP_OR_NO, "eq", new Integer[] { Integer
                        .valueOf(Integer.parseInt(searchBean.getNo())) });
        } catch (Exception ex) {
            System.out.println("==========error: cannot calc No" + searchBean.getNo());
        }

        BaseCrit.addDC(dc, Overtimerequest.PROP_OR_REASON, "like", new String[] { searchBean
                .getReason() });

        BaseCrit.addDCDate(dc, Overtimerequest.PROP_OR_START_DATE, "ge", new Date[] { searchBean
                .getStartDate() });

        if (searchBean.getEndDate() != null) {
            BaseCrit.addDCDate(dc, Overtimerequest.PROP_OR_END_DATE, "le", new Date[] { DateUtil
                    .dateAdd(searchBean.getEndDate(), 1) });
        }

        if (!StringUtils.isEmpty(searchBean.getTypeNo())) {
            BaseCrit.addDC(dc, Overtimerequest.PROP_OR_OT_NO, Overtimetype.PROP_OT_NO,
                           new Object[] { new Overtimetype(searchBean.getTypeNo()) });
        }

        BaseCrit.addDC(dc, Overtimerequest.PROP_OR_STATUS, "eq", new Integer[] { searchBean
                .getStatu() });

        BaseCrit.addEmpDC(dc, "emp", searchBean.getEmp());

        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, Integer.valueOf(1), new Department(
                                   searchBean.getDepNo()));

        page.splitPage(dc);
        setOrder(dc, page.getOrder());

        List result = this.or_DAO.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());

        searchBean.setResult(result);
    }

    public void approverMgrSearch(ExaminSearchBean searchBean, Employee currentEmp, Pager page) {
        DetachedCriteria dc = DetachedCriteria.forClass(Overtimerequest.class);

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position pos = posBo.getPosByEmpNo(currentEmp.getId(), new String[0]);
        BaseCrit.addDC(dc, Overtimerequest.PROP_OR_NEXT_APPROVER, "eq",
                       new String[] { pos.getId() });

        BaseCrit.addDC(dc, Overtimerequest.PROP_OR_STATUS, "in", Status.IN_MGR_PROCESS);

        examinSearch(searchBean, dc, page);
    }

    public void approverHRSearch(ExaminSearchBean searchBean, Pager page) {
        DetachedCriteria dc = DetachedCriteria.forClass(Overtimerequest.class);

        dc.add(Restrictions.isNull(Overtimerequest.PROP_OR_NEXT_APPROVER));
        dc.add(Restrictions.isNull(Overtimerequest.PROP_OR_NEXT_APPROVER));

        dc.add(Restrictions.in(Overtimerequest.PROP_OR_STATUS, Status.IN_PROCESS));

        examinSearch(searchBean, dc, page);
    }

    public void ExaminOwnSearch(ExaminSearchBean searchBean, Pager page, String empNo) {
        DetachedCriteria dc = DetachedCriteria.forClass(Overtimerequest.class);

        dc.add(Restrictions.eq("orEmpNo.id", empNo));

        examinSearch(searchBean, dc, page);
    }

    public void orAllSearch(ExaminSearchBean searchBean, Pager page) {
        DetachedCriteria dc = DetachedCriteria.forClass(Overtimerequest.class);

        examinSearch(searchBean, dc, page);
    }

    private void setOrder(DetachedCriteria detachedCriteria, String order) {
        if ((order != null) && (order.trim().length() > 1) && (order.indexOf("-") > -1)) {
            String ordeName = null;
            String operate = null;
            String[] params = order.split("-");
            if (params.length > 0)
                ordeName = params[0];
            if (params.length > 1) {
                operate = params[1];
            }

            if (ordeName.equals("id")) {
                if (operate.equals("up"))
                    detachedCriteria.addOrder(Order.asc(Overtimerequest.PROP_OR_NO));
                else {
                    detachedCriteria.addOrder(Order.desc(Overtimerequest.PROP_OR_NO));
                }
            }

            if (ordeName.equals("dep")) {
                if (operate.equals("up")) {
                    detachedCriteria.addOrder(Order.asc("empOrgDept."
                            + Department.PROP_DEPARTMENT_NAME));
                } else {
                    detachedCriteria.addOrder(Order.desc("empOrgDept."
                            + Department.PROP_DEPARTMENT_NAME));
                }

            }

            if (ordeName.equals("emp")) {
                if (operate.equals("up")) {
                    detachedCriteria.addOrder(Order.asc("emp." + Employee.PROP_EMP_NAME));
                } else {
                    detachedCriteria.addOrder(Order.desc("emp." + Employee.PROP_EMP_NAME));
                }

            }

            if (ordeName.equals("type")) {
                if (operate.equals("up")) {
                    detachedCriteria.addOrder(Order.asc(Overtimerequest.PROP_OR_OT_NO + "."
                            + Overtimetype.PROP_OT_NAME));
                } else {
                    detachedCriteria.addOrder(Order.desc(Overtimerequest.PROP_OR_OT_NO + "."
                            + Overtimetype.PROP_OT_NAME));
                }

            }

            if (ordeName.equals("reason")) {
                if (operate.equals("up"))
                    detachedCriteria.addOrder(Order.asc(Overtimerequest.PROP_OR_REASON));
                else {
                    detachedCriteria.addOrder(Order.desc(Overtimerequest.PROP_OR_REASON));
                }
            }

            if (ordeName.equals("startT")) {
                if (operate.equals("up"))
                    detachedCriteria.addOrder(Order.asc(Overtimerequest.PROP_OR_START_DATE));
                else {
                    detachedCriteria.addOrder(Order.desc(Overtimerequest.PROP_OR_START_DATE));
                }
            }

            if (ordeName.equals("total")) {
                if (operate.equals("up"))
                    detachedCriteria.addOrder(Order.asc(Overtimerequest.PROP_OR_TOTAL_HOURS));
                else {
                    detachedCriteria.addOrder(Order.desc(Overtimerequest.PROP_OR_TOTAL_HOURS));
                }
            }

            if (ordeName.equals("statu")) {
                if (operate.equals("up"))
                    detachedCriteria.addOrder(Order.asc(Overtimerequest.PROP_OR_STATUS));
                else
                    detachedCriteria.addOrder(Order.desc(Overtimerequest.PROP_OR_STATUS));
            }
        } else {
            detachedCriteria.addOrder(Order.desc(Overtimerequest.PROP_OR_NO));
        }
    }

    public String[][] getOvertimeTotal(String empId) {
        Calendar calendar = Calendar.getInstance();

        String hql = "select orOtNo.otName,sum(orTotalHours) from Overtimerequest where orStatus in (2,6,10,11,12,13,14,15,16) and orEmpNo.id='"
                + empId
                + "' and YEAR(orStartDate)="
                + calendar.get(1)
                + " group by orOtNo.otName"
                + " order by orOtNo.otName";

        List list = this.or_DAO.exeHqlList(hql);
        if ((list == null) || (list.size() == 0)) {
            return (String[][]) null;
        }

        hql = "select orOtNo.otName,sum(orTotalHours) from Overtimerequest where orStatus in (2,6,10,11,12,13,14) and orEmpNo.id='"
                + empId
                + "' and YEAR(orStartDate)="
                + calendar.get(1)
                + " group by orOtNo.otName"
                + " order by orOtNo.otName";

        List toApproveList = this.or_DAO.exeHqlList(hql);

        String[][] result = new String[14][list.size() + 1];
        for (int i = 0; i < result.length; ++i) {
            for (int j = 0; j < result[i].length; ++j)
                result[i][j] = "0.0";
        }
        int toApproveIndex = 0;
        for (int i = 0; i < list.size(); ++i) {
            result[13][i] = ((Object[]) (Object[]) list.get(i))[0].toString();

            result[12][i] = ((Object[]) (Object[]) list.get(i))[1].toString();

            if ((toApproveList.size() <= 0)
                    || (toApproveIndex >= toApproveList.size())
                    || (!result[13][i].equals(((Object[]) (Object[]) toApproveList
                            .get(toApproveIndex))[0].toString()))) {
                continue;
            }

            double toApproveSum = Double.parseDouble(((Object[]) (Object[]) toApproveList
                    .get(toApproveIndex))[1].toString());

            result[12][i] = (result[12][i] + "(" + toApproveSum + "小时待审)");
            ++toApproveIndex;
        }

        hql = "select MONTH(orStartDate),orOtNo.otName,sum(orTotalHours) from Overtimerequest where orStatus in (2,6,10,11,12,13,14,15,16) and orEmpNo.id='"
                + empId
                + "' and YEAR(orStartDate)="
                + calendar.get(1)
                + " group by MONTH(orStartDate),orOtNo.otName"
                + " order by MONTH(orStartDate),orOtNo.otName";

        List totalList = this.or_DAO.exeHqlList(hql);

        hql = "select MONTH(orStartDate),orOtNo.otName,sum(orTotalHours)  from Overtimerequest where orStatus in (2,6,10,11,12,13,14) and orEmpNo.id='"
                + empId
                + "' and YEAR(orStartDate)="
                + calendar.get(1)
                + " group by MONTH(orStartDate),orOtNo.otName"
                + " order by MONTH(orStartDate),orOtNo.otName";

        List totalToApproveList = this.or_DAO.exeHqlList(hql);

        int index = 0;
        toApproveIndex = 0;
        int currentMonth = 1;
        double monthTotal = 0.0D;
        double totalOvertime = 0.0D;

        double monthToAppTotal = 0.0D;
        double totalToAppOvertime = 0.0D;

        for (int i = 0; i < totalList.size();) {
            Object[] item = (Object[]) (Object[]) totalList.get(i);

            int month = Integer.parseInt(item[0].toString());

            if (month == currentMonth) {
                if (((Object[]) (Object[]) list.get(index))[0].toString()
                        .equals(item[1].toString())) {
                    result[(currentMonth - 1)][index] = item[2].toString();

                    monthTotal += Double.parseDouble(item[2].toString());
                    ++i;
                    if ((totalToApproveList != null)
                            && (toApproveIndex < totalToApproveList.size())
                            && (((Object[]) (Object[]) totalToApproveList.get(toApproveIndex))[0]
                                    .toString().equals(item[0].toString()))
                            && (((Object[]) (Object[]) totalToApproveList.get(toApproveIndex))[1]
                                    .toString().equals(item[1].toString()))) {
                        double toApprove = Double
                                .parseDouble(((Object[]) (Object[]) totalToApproveList
                                        .get(toApproveIndex))[2].toString());

                        monthToAppTotal += toApprove;
                        result[(currentMonth - 1)][index] = (result[(currentMonth - 1)][index]
                                + "(" + toApprove + "小时待审)");

                        ++toApproveIndex;
                    }
                } else {
                    ++index;
                }
            } else {
                String monthToAppTotalTmp = (monthToAppTotal > 0.0D) ? "(" + monthToAppTotal
                        + "小时待审)" : "";

                result[(currentMonth - 1)][list.size()] = (monthTotal + monthToAppTotalTmp);

                totalOvertime += monthTotal;

                totalToAppOvertime += monthToAppTotal;

                monthTotal = 0.0D;
                monthToAppTotal = 0.0D;
                currentMonth = month;
                index = 0;
            }
        }
        String monthToAppTotalTmp = (monthToAppTotal > 0.0D) ? "(" + monthToAppTotal + "小时待审)" : "";
        result[(currentMonth - 1)][list.size()] = (monthTotal + monthToAppTotalTmp);
        totalOvertime += monthTotal;
        totalToAppOvertime += monthToAppTotal;

        result[13][list.size()] = "月度汇�1�7�1�7";
        String totalToAppTotalTmp = (totalToAppOvertime > 0.0D) ? "(" + totalToAppOvertime
                + "小时待审)" : "";

        result[12][list.size()] = (totalOvertime + totalToAppTotalTmp);
        return result;
    }

    public void deleteExtraOvertimerequest() {
        String deleteHql = "delete from Overtimerequest where orNextApprover is not null and orNextApprover!=''";
        this.or_DAO.exeHql(deleteHql);
    }

    public IOvertimetypeBo getOt_BO() {
        return this.or_Bo;
    }

    public void setOt_BO(IOvertimetypeBo or_Bo) {
        this.or_Bo = or_Bo;
    }

    public IStatBO getSt_BO() {
        return this.st_BO;
    }

    public void setSt_BO(IStatBO sc_BO) {
        this.st_BO = sc_BO;
    }

    public IDepartmentBO getDp_BO() {
        return this.dp_BO;
    }

    public void setDp_BO(IDepartmentBO dp_BO) {
        this.dp_BO = dp_BO;
    }

    public ILeavecalendarBO getLcBO() {
        return this.lcBO;
    }

    public void setLcBO(ILeavecalendarBO lcBO) {
        this.lcBO = lcBO;
    }

    public IWorkFlowBO getWorkFlowBO() {
        return this.workFlowBO;
    }

    public void setWorkFlowBO(IWorkFlowBO workFlowBO) {
        this.workFlowBO = workFlowBO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.OvertimerequestBoImpl JD-Core Version: 0.5.4
 */