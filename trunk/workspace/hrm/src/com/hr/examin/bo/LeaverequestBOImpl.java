package com.hr.examin.bo;

import com.hr.base.Status;
import com.hr.configuration.bo.IStatBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Department;
import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.LeaveTotalBean;
import com.hr.examin.bo.interfaces.IAttendshiftBO;
import com.hr.examin.bo.interfaces.IEmpshiftBo;
import com.hr.examin.bo.interfaces.ILeavecalendarBO;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.bo.interfaces.IWorkFlowBO;
import com.hr.examin.dao.interfaces.ILeaverequestDAO;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class LeaverequestBOImpl extends ExaminBoImpl implements ILeaverequestBO {
    private IEmpshiftBo empShiftBo;
    private IAttendshiftBO attendBo;
    public static final String table_name = "leaverequest";
    private ILeavetypeBO lt_BO;
    private IWorkFlowBO workFlowBo;
    private IStatBO st_BO;
    private ILeavecalendarBO lc_BO;
    private LrDataCheckImpl lrDataCheckBO;

    public List<Leaverequest> getTotalStatusSetList(Employee currentEmp, Date startDate,
            Date endDate, Leavetype lrType, int[] statusSet) {
        String[] fetch = { Leaverequest.PROP_LR_EMP_NO,
                Leaverequest.PROP_LR_EMP_NO + "." + Employee.PROP_EMP_LOCATION_NO,
                Leaverequest.PROP_LR_EMP_NO + "." + Employee.PROP_EMP_DEPT_NO,
                Leaverequest.PROP_LR_CREATE_BY,
                Leaverequest.PROP_LR_CREATE_BY + "." + Employee.PROP_EMP_LOCATION_NO,
                Leaverequest.PROP_LR_CREATE_BY + "." + Employee.PROP_EMP_DEPT_NO,
                Leaverequest.PROP_LR_LAST_CHANGE_BY,
                Leaverequest.PROP_LR_LAST_CHANGE_BY + "." + Employee.PROP_EMP_LOCATION_NO,
                Leaverequest.PROP_LR_LAST_CHANGE_BY + "." + Employee.PROP_EMP_DEPT_NO,
                Leaverequest.PROP_LR_EMP_DEPT, Leaverequest.PROP_LR_LT_NO };

        DetachedCriteria dc = DetachedCriteria.forClass(Leaverequest.class);
        dc.add(Restrictions.eq(Leaverequest.PROP_LR_EMP_NO, currentEmp));
        dc.add(Restrictions.eq(Leaverequest.PROP_LR_LT_NO, lrType));

        for (int i = 0; i < fetch.length; ++i) {
            dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }

        dc.add(Restrictions.or(Restrictions.and(Restrictions.ge(Leaverequest.PROP_LR_START_DATE,
                                                                startDate), Restrictions
                .lt(Leaverequest.PROP_LR_START_DATE, endDate)), Restrictions.and(Restrictions
                .le(Leaverequest.PROP_LR_END_DATE, endDate), Restrictions
                .gt(Leaverequest.PROP_LR_END_DATE, startDate))));

        if (statusSet != null) {
            Integer[] statusSet1 = new Integer[statusSet.length];
            for (int i = 0; i < statusSet.length; ++i) {
                statusSet1[i] = Integer.valueOf(statusSet[i]);
            }
            dc.add(Restrictions.in(Leaverequest.PROP_LR_STATUS, statusSet1));
        }

        List result = this.lr_DAO.findByCriteria(dc);

        return result;
    }

    public List<String> delLeaveRequest(String lrID, Employee currentEmp) {
        List result = new ArrayList();
        try {
            this.lr_DAO.deleteObject(Leaverequest.class, lrID);
            this.sl_BO.deleteLogs("leaverequest", "" + lrID);
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public void examinSearch(ExaminSearchBean searchBean, DetachedCriteria dc, Pager page) {
        String[] fetch = { Leaverequest.PROP_LR_CREATE_BY, Leaverequest.PROP_LR_EMP_DEPT,
                Leaverequest.PROP_LR_LAST_CHANGE_BY };

        dc.createAlias(Leaverequest.PROP_LR_EMP_NO, "emp", 1);
        dc.createAlias(Leaverequest.PROP_LR_CREATE_BY, "creator");

        dc.createAlias(Leaverequest.PROP_LR_EMP_NO + "." + Employee.PROP_EMP_DEPT_NO, "empOrgDept",
                       1);
        dc.createAlias(Leaverequest.PROP_LR_EMP_NO + "." + Employee.PROP_EMP_PB_NO, "empPbNo", 1);

        dc.createAlias(Leaverequest.PROP_LR_EMP_NO + "." + Employee.PROP_EMP_LOCATION_NO,
                       "empOrgLoc", 1);
        dc.createAlias(Leaverequest.PROP_LR_LT_NO, "lrLtNo", 1);

        for (int i = 0; i < fetch.length; ++i) {
            dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }

        try {
            if (!StringUtils.isEmpty(searchBean.getNo()))
                BaseCrit.addDC(dc, Leaverequest.PROP_LR_NO, "eq", new Integer[] { Integer
                        .valueOf(Integer.parseInt(searchBean.getNo())) });
        } catch (Exception ex) {
            System.out.println("==========error: cannot calc No" + searchBean.getNo());
        }

        BaseCrit.addDC(dc, Leaverequest.PROP_LR_REASON, "like", new String[] { searchBean
                .getReason() });

        BaseCrit.addDCDate(dc, Leaverequest.PROP_LR_START_DATE, "ge", new Date[] { searchBean
                .getStartDate() });

        if (searchBean.getEndDate() != null) {
            BaseCrit.addDCDate(dc, Leaverequest.PROP_LR_END_DATE, "le", new Date[] { DateUtil
                    .dateAdd(searchBean.getEndDate(), 1) });
        }

        if (!StringUtils.isEmpty(searchBean.getTypeNo())) {
            BaseCrit.addDC(dc, Leaverequest.PROP_LR_LT_NO, Leavetype.PROP_LT_NO,
                           new Object[] { new Leavetype(searchBean.getTypeNo()) });
        }

        BaseCrit.addDC(dc, Leaverequest.PROP_LR_STATUS, "eq",
                       new Integer[] { searchBean.getStatu() });

        BaseCrit.addEmpDC(dc, "emp", searchBean.getEmp());

        BaseCrit.addDeptDC(dc, Leaverequest.PROP_LR_EMP_NO + "." + Employee.PROP_EMP_DEPT_NO,
                           Leaverequest.PROP_LR_EMP_NO + "." + Employee.PROP_EMP_PB_NO, Integer
                                   .valueOf(1), new Department(searchBean.getDepNo()));

        List result = null;
        if (page != null) {
            setOrder(dc, page.getOrder());
            page.splitPage(dc);
            result = this.lr_DAO.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
        } else {
            result = this.lr_DAO.findByCriteria(dc);
        }

        if (result.size() > 0) {
            String leaveType = DatabaseSysConfigManager.getInstance()
                    .getProperty("sys.examin.leave.type");
            for (int i = 0; i < result.size(); ++i) {
                Leaverequest lr = (Leaverequest) result.get(i);
                lr.setApplyLRByDay(ExaminDateUtil.applyLRByDay(leaveType, lr.getLrEmpNo()
                        .getEmpShiftType()));
            }
        }

        searchBean.setResult(result);
    }

    public void approverMgrSearch(ExaminSearchBean searchBean, Employee currentEmp, Pager page) {
        DetachedCriteria dc = DetachedCriteria.forClass(Leaverequest.class);

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position pos = posBo.getPosByEmpNo(currentEmp.getId(), new String[0]);
        BaseCrit.addDC(dc, Leaverequest.PROP_LR_NEXT_APPROVER, "eq", new String[] { pos.getId() });

        BaseCrit.addDC(dc, Leaverequest.PROP_LR_STATUS, "in", Status.IN_MGR_PROCESS);

        examinSearch(searchBean, dc, page);
    }

    public void approverHRSearch(ExaminSearchBean searchBean, Pager page) {
        DetachedCriteria dc = DetachedCriteria.forClass(Leaverequest.class);

        dc.add(Restrictions.isNull(Leaverequest.PROP_LR_NEXT_APPROVER));

        dc.add(Restrictions.in(Leaverequest.PROP_LR_STATUS, Status.IN_PROCESS));

        examinSearch(searchBean, dc, page);
    }

    public void lrOwnSearch(ExaminSearchBean searchBean, String empNo, Pager page) {
        DetachedCriteria dc = DetachedCriteria.forClass(Leaverequest.class);

        dc.add(Restrictions.eq(Leaverequest.PROP_LR_EMP_NO + "." + Employee.PROP_ID, empNo));

        examinSearch(searchBean, dc, page);
    }

    public void lrAllSearch(ExaminSearchBean searchBean, Pager page) {
        DetachedCriteria dc = DetachedCriteria.forClass(Leaverequest.class);

        examinSearch(searchBean, dc, page);
    }

    public List<LeaveTotalBean> getLeaveTotal(String empId) {
        if ((empId == null) || (empId.length() == 0)) {
            return null;
        }
        List result = new ArrayList();

        Calendar calendar = Calendar.getInstance();

        String hql = "select lrLtNo.ltName, sum(lrTotalHours), count(*) from Leaverequest where (lrStatus in (2,5,15,16)) and lrEmpNo.id='"
                + empId + "' and YEAR(lrStartDate)=" + calendar.get(1) + "group by lrLtNo.ltName ";

        List totalList = this.lr_DAO.exeHqlList(hql);
        if (totalList == null) {
            return null;
        }

        hql = "select lrLtNo.ltName, sum(lrTotalHours), count(*) from Leaverequest where lrStatus in (15,16) and lrEmpNo.id='"
                + empId + "' and YEAR(lrStartDate)=" + calendar.get(1) + " group by lrLtNo.ltName";

        List useList = this.lr_DAO.exeHqlList(hql);
        double total = 0.0D;
        double toApproval = 0.0D;

        double hoursPerDay = ExaminDateUtil.getDefaultHoursPerDay();

        int useIndex = 0;
        for (int i = 0; i < totalList.size(); ++i) {
            Object[] items = (Object[]) (Object[]) totalList.get(i);
            Object[] useItems;
            if ((useList == null) || (useIndex >= useList.size()))
                useItems = new Object[] { "0", "0.0", "0" };
            else {
                useItems = (Object[]) (Object[]) useList.get(useIndex);
            }

            LeaveTotalBean leaveTotal = new LeaveTotalBean();
            leaveTotal.setType(items[0].toString());

            String totalDays = "0";
            String totalTimes = "0";

            double toApproveDays = Double.parseDouble(items[1].toString());

            int toApproveTimes = Integer.parseInt(items[2].toString());
            if ((items[0].equals(useItems[0])) || ("0".equals(useItems))) {
                totalDays = useItems[1].toString();

                totalTimes = useItems[2].toString();

                toApproveDays -= Double.parseDouble(useItems[1].toString());

                toApproveTimes -= Integer.parseInt(useItems[2].toString());
                ++useIndex;

                total += Double.parseDouble(useItems[1].toString());
            }
            toApproval += toApproveDays;
            totalDays = String.valueOf(MyTools
                    .round(Double.parseDouble(totalDays) / hoursPerDay, 2));
            leaveTotal.setTotaldays(totalDays);
            leaveTotal.setTotalTimes(totalTimes);
            toApproveDays = MyTools.round(toApproveDays / hoursPerDay, 2);
            leaveTotal.setToApproveDays(new Double(toApproveDays).toString());
            leaveTotal.setToApproveTimes(new Integer(toApproveTimes).toString());
            result.add(leaveTotal);
        }
        LeaveTotalBean leaveTotal = new LeaveTotalBean();

        leaveTotal.setType("合计");
        toApproval = MyTools.round(toApproval / hoursPerDay, 2);
        leaveTotal.setToApproveDays(new Double(toApproval).toString());
        total = MyTools.round(total / hoursPerDay, 2);
        leaveTotal.setTotaldays(new Double(total).toString());
        result.add(leaveTotal);
        return result;
    }

    public BigDecimal[] getTotalUsedDay(Employee currentEmp, int year, Leavetype lrType,
            int[] statusSet) {
        Date startDate = DateUtil.getYearFirstDay(year);
        Date endDate = DateUtil.getYearFirstDay(year + 1);
        String hql = "";
        hql = "select sum(lr.lrTotalHours),sum(lr.lrTotalDays) from Leaverequest lr where lr.lrEmpNo.id='"
                + currentEmp.getId()
                + "' and lr.lrStartDate >=? and lr.lrStartDate <? and lr.lrLtNo = '"
                + lrType.getLtNo() + "'";

        if (statusSet.length > 0) {
            hql = hql + " and ( ";
            for (int i = 0; i < statusSet.length; ++i) {
                hql = hql + " (lr.lrStatus = " + statusSet[i] + " ) ";
                if (i + 1 < statusSet.length) {
                    hql = hql + " or ";
                }
            }
            hql = hql + ")";
        }
        Object rs = this.lr_DAO.exeHqlObject(hql, new Object[] { startDate, endDate });
        Object[] result = (Object[]) (Object[]) rs;
        BigDecimal rs0 = (BigDecimal) result[0];
        BigDecimal rs1 = (BigDecimal) result[1];
        return new BigDecimal[] { rs0, rs1 };
    }

    public double[] getLeaveDaysBeforAfter(List<Leaverequest> inputlist, Date checkDate) {
        return this.lrDataCheckBO.getLeaveHoursBeforAfter(inputlist, checkDate);
    }

    public List<Leaverequest> loadLeaveRequests(String[] lrIDs, String[] fetch) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Leaverequest.class);
            for (int i = 0; i < fetch.length; ++i) {
                dc.setFetchMode(fetch[i], FetchMode.JOIN);
            }
            BaseCrit.addDC(dc, Leaverequest.PROP_LR_ID, "in", lrIDs);

            dc.addOrder(Order.asc(Leaverequest.PROP_LR_NO));

            List result = this.lr_DAO.findByCriteria(dc);
            if (result.size() == 0) {
                return null;
            }
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    public Leaverequest loadLeaverequest(String lrID) {
        String[] fetch = { Leaverequest.PROP_LR_EMP_NO,
                Leaverequest.PROP_LR_EMP_NO + "." + Employee.PROP_EMP_LOCATION_NO,
                Leaverequest.PROP_LR_EMP_NO + "." + Employee.PROP_EMP_DEPT_NO,
                Leaverequest.PROP_LR_CREATE_BY,
                Leaverequest.PROP_LR_CREATE_BY + "." + Employee.PROP_EMP_LOCATION_NO,
                Leaverequest.PROP_LR_CREATE_BY + "." + Employee.PROP_EMP_DEPT_NO,
                Leaverequest.PROP_LR_LAST_CHANGE_BY,
                Leaverequest.PROP_LR_LAST_CHANGE_BY + "." + Employee.PROP_EMP_LOCATION_NO,
                Leaverequest.PROP_LR_LAST_CHANGE_BY + "." + Employee.PROP_EMP_DEPT_NO,
                Leaverequest.PROP_LR_EMP_DEPT, Leaverequest.PROP_LR_LT_NO };

        Leaverequest resultLR = (Leaverequest) this.lr_DAO.loadObject(Leaverequest.class, lrID,
                                                                      fetch, new boolean[0]);
        return resultLR;
    }

    public Leaverequest loadOneLeaverequest(String lrId, String[] fetch) {
        Object obj = this.lr_DAO.loadObject(Leaverequest.class, lrId, fetch, new boolean[0]);
        if (obj != null)
            return (Leaverequest) obj;
        return null;
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
                    detachedCriteria.addOrder(Order.asc(Leaverequest.PROP_LR_NO));
                else {
                    detachedCriteria.addOrder(Order.desc(Leaverequest.PROP_LR_NO));
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
                    detachedCriteria.addOrder(Order.asc(Leaverequest.PROP_LR_LT_NO + "."
                            + Leavetype.PROP_LT_NAME));
                } else {
                    detachedCriteria.addOrder(Order.desc(Leaverequest.PROP_LR_LT_NO + "."
                            + Leavetype.PROP_LT_NAME));
                }

            }

            if (ordeName.equals("reason")) {
                if (operate.equals("up"))
                    detachedCriteria.addOrder(Order.asc(Leaverequest.PROP_LR_REASON));
                else {
                    detachedCriteria.addOrder(Order.desc(Leaverequest.PROP_LR_REASON));
                }
            }

            if (ordeName.equals("startT")) {
                if (operate.equals("up"))
                    detachedCriteria.addOrder(Order.asc(Leaverequest.PROP_LR_START_DATE));
                else {
                    detachedCriteria.addOrder(Order.desc(Leaverequest.PROP_LR_START_DATE));
                }
            }

            if (ordeName.equals("endT")) {
                if (operate.equals("up"))
                    detachedCriteria.addOrder(Order.asc(Leaverequest.PROP_LR_END_DATE));
                else {
                    detachedCriteria.addOrder(Order.desc(Leaverequest.PROP_LR_END_DATE));
                }
            }

            if (ordeName.equals("total")) {
                if (operate.equals("up"))
                    detachedCriteria.addOrder(Order.asc(Leaverequest.PROP_LR_TOTAL_HOURS));
                else {
                    detachedCriteria.addOrder(Order.desc(Leaverequest.PROP_LR_TOTAL_HOURS));
                }
            }

            if (ordeName.equals("statu")) {
                if (operate.equals("up"))
                    detachedCriteria.addOrder(Order.asc(Leaverequest.PROP_LR_STATUS));
                else
                    detachedCriteria.addOrder(Order.desc(Leaverequest.PROP_LR_STATUS));
            }
        } else {
            detachedCriteria.addOrder(Order.desc(Leaverequest.PROP_LR_NO));
        }
    }

    public ILeavetypeBO getLt_BO() {
        return this.lt_BO;
    }

    public void setLt_BO(ILeavetypeBO lt_BO) {
        this.lt_BO = lt_BO;
    }

    public IStatBO getSt_BO() {
        return this.st_BO;
    }

    public void setSt_BO(IStatBO st_BO) {
        this.st_BO = st_BO;
    }

    public ILeavecalendarBO getLc_BO() {
        return this.lc_BO;
    }

    public void setLc_BO(ILeavecalendarBO lc_BO) {
        this.lc_BO = lc_BO;
    }

    public IAttendshiftBO getAttendBo() {
        return this.attendBo;
    }

    public void setAttendBo(IAttendshiftBO attendBo) {
        this.attendBo = attendBo;
    }

    public LrDataCheckImpl getLrDataCheckBO() {
        return this.lrDataCheckBO;
    }

    public void setLrDataCheckBO(LrDataCheckImpl lrDataCheckBO) {
        this.lrDataCheckBO = lrDataCheckBO;
    }

    public IEmpshiftBo getEmpShiftBo() {
        return this.empShiftBo;
    }

    public void setEmpShiftBo(IEmpshiftBo empShiftBo) {
        this.empShiftBo = empShiftBo;
    }

    public IWorkFlowBO getWorkFlowBo() {
        return this.workFlowBo;
    }

    public void setWorkFlowBo(IWorkFlowBO workFlowBo) {
        this.workFlowBo = workFlowBo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.LeaverequestBOImpl JD-Core Version: 0.5.4
 */