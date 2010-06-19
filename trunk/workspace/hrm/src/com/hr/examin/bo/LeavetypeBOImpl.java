package com.hr.examin.bo;

import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.dao.interfaces.ILeavetypeDAO;
import com.hr.examin.domain.Leavetype;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class LeavetypeBOImpl implements ILeavetypeBO {
    private ILeavetypeDAO lt_DAO;

    public Leavetype getLeavetype(String inputLtNo) {
        String[] fetch = null;
        return (Leavetype) this.lt_DAO
                .loadObject(Leavetype.class, inputLtNo, fetch, new boolean[0]);
    }

    public boolean isTypeExist(String inputLtNo) {
        DetachedCriteria detachedcriteria = DetachedCriteria.forClass(Leavetype.class);
        detachedcriteria.add(Restrictions.eq(Leavetype.PROP_LT_NO, inputLtNo));
        return this.lt_DAO.findRowCountByCriteria(detachedcriteria) > 0;
    }

    public Leavetype getAnnualLeave() {
        DetachedCriteria detachedcriteria = DetachedCriteria.forClass(Leavetype.class);
        detachedcriteria.add(Restrictions.eq(Leavetype.PROP_LT_SPECIAL_CAT, new Integer(1)));
        List re = this.lt_DAO.findByCriteria(detachedcriteria);
        if ((re != null) && (re.size() == 1)) {
            return (Leavetype) re.get(0);
        }
        return null;
    }

    public Leavetype getTiaoxiuLeavetype(Integer ltSpecialCat) {
        DetachedCriteria detachedcriteria = DetachedCriteria.forClass(Leavetype.class);
        detachedcriteria.add(Restrictions.eq(Leavetype.PROP_LT_SPECIAL_CAT, ltSpecialCat));
        List re = this.lt_DAO.findByCriteria(detachedcriteria);
        if ((re != null) && (re.size() == 1)) {
            return (Leavetype) re.get(0);
        }

        return null;
    }

    public List FindAllLeaveType() {
        DetachedCriteria dc = DetachedCriteria.forClass(Leavetype.class);
        dc.addOrder(Order.asc(Leavetype.PROP_LT_SORT_ID));
        return this.lt_DAO.findByCriteria(dc);
    }

    public String addLeaveType(Leavetype leaveType) {
        try {
            String trimmedLeavetypeName = leaveType.getLtName().trim();

            List oldLeavetype = this.lt_DAO.exeHqlList(" from Leavetype where ltName ='"
                    + trimmedLeavetypeName + "'");
            if (!oldLeavetype.isEmpty()) {
                return "EXISTED";
            }

            if (leaveType.getLtSpecialCat().intValue() != 20) {
                List leaveTypeSp = this.lt_DAO.exeHqlList(" from Leavetype where ltSpecialCat="
                        + leaveType.getLtSpecialCat());
                if (!leaveTypeSp.isEmpty()) {
                    return "LEAVE_TYPE_EXISTED";
                }

            }

            if ((leaveType.getLtSpecialCat().intValue() == 11)
                    || (leaveType.getLtSpecialCat().intValue() == 10)) {
                int sp = (leaveType.getLtSpecialCat().intValue() == 11) ? 10 : 11;
                List leaveTypeSp = this.lt_DAO.exeHqlList(" from Leavetype where ltSpecialCat="
                        + sp);
                if (!leaveTypeSp.isEmpty()) {
                    return "DUPLICATE_TIAOXIU";
                }
            }
            leaveType.setLtName(trimmedLeavetypeName);
            leaveType.setLtSortId(Integer.valueOf(0));
            this.lt_DAO.saveObject(leaveType);
            return "SUCC";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAIL";
    }

    public String delLeaveType(Class<Leavetype> name, String id) {
        StringBuffer buffer = new StringBuffer();
        try {
            List check = this.lt_DAO
                    .exeHqlList("select count(*) from Leaverequest where lrLtNo = '" + id + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0)
                buffer.append("有请假记录关联到该请假类型！");
            else
                this.lt_DAO.deleteObject(Leavetype.class, id);
        } catch (Exception e) {
            buffer.append("系统异常" + e.getMessage());
        }
        return buffer.toString();
    }

    public String updateLeaveType(Leavetype leaveType) {
        List temp = findLeaveTypeList(leaveType);
        if (!temp.isEmpty()) {
            return "EXISTED";
        }

        if (leaveType.getLtSpecialCat().intValue() != 20) {
            List leaveTypeSp = this.lt_DAO.exeHqlList(" from Leavetype where ltSpecialCat="
                    + leaveType.getLtSpecialCat() + " and ltNo<>'" + leaveType.getLtNo() + "'");

            if (!leaveTypeSp.isEmpty()) {
                return "LEAVE_TYPE_EXISTED";
            }

        }

        if ((leaveType.getLtSpecialCat().intValue() == 11)
                || (leaveType.getLtSpecialCat().intValue() == 10)) {
            int sp = (leaveType.getLtSpecialCat().intValue() == 11) ? 10 : 11;
            List leaveTypeSp = this.lt_DAO.exeHqlList(" from Leavetype where ltSpecialCat=" + sp
                    + " and ltNo<>'" + leaveType.getLtNo() + "'");

            if (!leaveTypeSp.isEmpty()) {
                return "DUPLICATE_TIAOXIU";
            }
        }

        Leavetype oldleaveType = (Leavetype) this.lt_DAO.loadObject(Leavetype.class, leaveType
                .getLtNo(), null, new boolean[0]);
        if (oldleaveType != null) {
            String trimmedLeavetypeName = leaveType.getLtName().trim();
            oldleaveType.setLtName(trimmedLeavetypeName);
            oldleaveType.setLtInclHoliday(leaveType.getLtInclHoliday());
            oldleaveType.setLtDesc(leaveType.getLtDesc());
            oldleaveType.setLtBalForward(leaveType.getLtBalForward());
            oldleaveType.setLtBalForwardDayLimit(leaveType.getLtBalForwardDayLimit());
            oldleaveType.setLtBalForwardHourLimit(leaveType.getLtBalForwardHourLimit());
            oldleaveType.setLtBalForwardPerLimit(leaveType.getLtBalForwardPerLimit());
            oldleaveType.setLtBalForwardRounding(leaveType.getLtBalForwardRounding());
            oldleaveType.setLtDaysForRelease(leaveType.getLtDaysForRelease());
            oldleaveType.setLtDaysOfYear(leaveType.getLtDaysOfYear());
            oldleaveType.setLtHoursForRelease(leaveType.getLtHoursForRelease());
            oldleaveType.setLtHoursOfYear(leaveType.getLtHoursOfYear());
            oldleaveType.setLtMaxApplyDays(leaveType.getLtMaxApplyDays());
            oldleaveType.setLtMaxApplyHours(leaveType.getLtMaxApplyHours());
            oldleaveType.setLtMinApplyDays(leaveType.getLtMinApplyDays());
            oldleaveType.setLtMinApplyHours(leaveType.getLtMinApplyHours());
            oldleaveType.setLtOtherParameter(leaveType.getLtOtherParameter());
            oldleaveType.setLtReleaseMethod(leaveType.getLtReleaseMethod());
            oldleaveType.setLtReleaseRounding(leaveType.getLtReleaseRounding());
            oldleaveType.setLtSpecialCat(leaveType.getLtSpecialCat());
            oldleaveType.setLtNeedComments(leaveType.getLtNeedComments());
            this.lt_DAO.updateObject(oldleaveType);
            return "SUCC";
        }
        return "ERROR";
    }

    public void saveLeavetypeByBatch(String[] ids) {
        if (ids == null)
            return;
        int sort = 1;
        int len = ids.length;
        for (int i = 0; i < len; ++i) {
            this.lt_DAO.exeHql("update Leavetype set ltSortId=" + sort + " where id ='" + ids[i]
                    + "'");

            ++sort;
        }
    }

    public List findLeaveTypeList(Leavetype leaveType) {
        DetachedCriteria detachedcriteria = DetachedCriteria.forClass(Leavetype.class);
        detachedcriteria.add(Restrictions.eq("ltName", leaveType.getLtName().trim()));
        detachedcriteria.add(Restrictions.ne("ltNo", leaveType.getLtNo()));
        List re = this.lt_DAO.findByCriteria(detachedcriteria);
        return re;
    }

    public List findLeaveTypeByName(String name) {
        DetachedCriteria detachedcriteria = DetachedCriteria.forClass(Leavetype.class);
        detachedcriteria.add(Restrictions.eq("ltName", name.trim()));
        List re = this.lt_DAO.findByCriteria(detachedcriteria);
        return re;
    }

    public ILeavetypeDAO getLt_DAO() {
        return this.lt_DAO;
    }

    public void setLt_DAO(ILeavetypeDAO lt_DAO) {
        this.lt_DAO = lt_DAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.LeavetypeBOImpl JD-Core Version: 0.5.4
 */