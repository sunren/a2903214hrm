package com.hr.homepage.bo;

import com.hr.compensation.domain.Empsalaryadj;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.examin.domain.Leavebalance;
import com.hr.examin.domain.Leavecalendar;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;
import com.hr.homepage.dao.IIFindTipDao;
import com.hr.profile.domain.Empaddconf;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emprelations;
import com.hr.recruitment.domain.Recruitchannel;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.security.domain.Userinfo;
import com.hr.training.domain.Tremployeeplan;
import com.hr.training.domain.Trtype;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class FindTipImpl implements IIFindTip {
    private IIFindTipDao findTipDao;
    private DateFormat format;
    private int currentYear;
    private int currentMonth;
    private Date currendDate;

    public FindTipImpl() {
        this.format = new SimpleDateFormat("yyyy-MM-dd");
        this.currentYear = DateUtil.getYear(new Date());
        this.currentMonth = DateUtil.getMonth(new Date());
        this.currendDate = DateUtil.convDateTimeToDate(new Date());
    }

    public ArrayList<String> getTip101() {
        DatabaseSysConfigManager config = DatabaseSysConfigManager.getInstance();
        Map props = config.getProperties();

        DetachedCriteria dcEmp = DetachedCriteria.forClass(Employee.class);
        dcEmp.add(Restrictions
                .or(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer.valueOf(1)), Restrictions
                        .ge(Employee.PROP_EMP_TERMINATE_DATE, DateUtil
                                .getYearMonthFirstDay(this.currentYear, this.currentMonth))));

        dcEmp.addOrder(Order.asc(Employee.PROP_EMP_DISTINCT_NO));
        List<Employee> empList = this.findTipDao.findByCriteria(dcEmp);

        DetachedCriteria dcRelations = DetachedCriteria.forClass(Emprelations.class);
        dcRelations.setFetchMode(Emprelations.PROP_EREL_EMPLOYEE, FetchMode.DEFAULT);
        dcRelations.createAlias("employee", "employee");
        dcRelations.add(Restrictions.eq("employee.empStatus", Integer.valueOf(1)));
        dcRelations.add(Restrictions.eq(Emprelations.PROP_EREL_ERELBIRTHDAYREMIND, Integer
                .valueOf(1)));
        List<Emprelations> erelList = this.findTipDao.findByCriteria(dcRelations);

        StringBuffer birth = new StringBuffer();
        int birtydayTip = 30;
        String birtydayTipDays = (String) props.get("sys.birthday.remind");
        if (StringUtils.isNotEmpty(birtydayTipDays)) {
            birtydayTip = Integer.parseInt(birtydayTipDays);
        }
        for (Employee emp : empList) {
            if ((emp.getEmpStatus().intValue() == 1)
                    && (DateUtil.isDateRemind(emp.getEmpBirthDate(), this.currendDate, birtydayTip,
                                              1))) {
                birth.append(getJSString(new StringBuilder()
                        .append("../profile/myInfo.action?empNo=").append(emp.getId())
                        .append("&empName=").append(emp.getEmpName()).append("&tab=1").toString(),
                                         emp.getEmpName())
                        + "〄1�7");
            }
        }

        for (Emprelations erel : erelList) {
            if ((erel.getErelBirthday() != null)
                    && (DateUtil.isDateRemind(erel.getErelBirthday(), this.currendDate,
                                              birtydayTip, 1))) {
                birth.append(getJSString(new StringBuilder()
                        .append("../profile/myInfo.action?empNo=").append(
                                                                          erel.getEmployee()
                                                                                  .getId())
                        .append("&empName=").append(erel.getEmployee().getEmpName())
                        .append("&tab=2").toString(), new StringBuilder()
                        .append(erel.getEmployee().getEmpName()).append("(")
                        .append(erel.getErelRelationship()).append(")").toString())
                        + "〄1�7");
            }

        }

        StringBuffer join = new StringBuffer();
        for (Employee emp : empList) {
            if (DateUtil.convDateToYM(emp.getEmpJoinDate())
                    .equals(DateUtil.convDateToYM(new Date()))) {
                join.append(getJSString(new StringBuilder()
                        .append("../profile/myInfo.action?empNo=").append(emp.getId())
                        .append("&empName=").append(emp.getEmpName()).append("&tab=1").toString(),
                                        emp.getEmpName())
                        + "〄1�7");
            }

        }

        StringBuffer confirm = new StringBuffer();
        int confirmTip = 30;
        String confirmTipDays = (String) props.get("sys.trial.expire.remind");
        if (StringUtils.isNotEmpty(confirmTipDays)) {
            confirmTip = Integer.parseInt(confirmTipDays);
        }
        for (Employee emp : empList) {
            if ((emp.getEmpStatus().intValue() == 1)
                    && (emp.getEmpConfirmDate() != null)
                    && (DateUtil.isDateRemind(emp.getEmpConfirmDate(), this.currendDate,
                                              confirmTip, 0))) {
                confirm.append(getJSString(new StringBuilder()
                        .append("../profile/myInfo.action?empNo=").append(emp.getId())
                        .append("&empName=").append(emp.getEmpName()).append("&tab=1").toString(),
                                           emp.getEmpName())
                        + "〄1�7");
            }

        }

        StringBuffer out = new StringBuffer();
        for (Employee emp : empList) {
            if ((emp.getEmpTerminateDate() != null)
                    && (DateUtil.convDateToYM(emp.getEmpTerminateDate()).equals(DateUtil
                            .convDateToYM(new Date())))) {
                out.append(getJSString(new StringBuilder()
                        .append("../profile/myInfo.action?empNo=").append(emp.getId())
                        .append("&empName=").append(emp.getEmpName()).append("&tab=1").toString(),
                                       emp.getEmpName())
                        + "〄1�7");
            }
        }

        ArrayList tipArrayList = new ArrayList();
        if (confirm.length() > 0)
            tipArrayList.add(confirmTip + "天内试用期结束的员工: <br>"
                    + confirm.substring(0, confirm.length() - 1));
        if (birth.length() > 0)
            tipArrayList.add(birtydayTip + "天内过生日的员工＄1�7br>"
                    + birth.substring(0, birth.length() - 1));
        if (join.length() > 0)
            tipArrayList.add("本月入职的员工：<br>" + join.substring(0, join.length() - 1));
        if (out.length() > 0)
            tipArrayList.add("本月离职的员工：<br>" + out.substring(0, out.length() - 1));
        return tipArrayList;
    }

    private boolean isTipDate(Date date, int days) {
        if (date == null)
            return false;
        boolean rs = false;
        Calendar now = Calendar.getInstance();
        Calendar cloneNow = (Calendar) now.clone();
        Calendar db = Calendar.getInstance();
        db.setTime(date);
        int dbday = db.get(6);
        int nowDay = now.get(6);
        int tmp = dbday - nowDay;
        rs = (0 <= tmp) && (tmp <= days);
        if (rs) {
            return true;
        }

        int oldyear = now.get(1);
        now.add(5, days);
        int nowYear = now.get(1);
        if (nowYear != oldyear) {
            int t = days - (365 - cloneNow.get(6));
            int tmp2 = now.get(6) - dbday;
            rs = (0 <= tmp2) && (tmp2 <= t);
        }
        return rs;
    }

    public ArrayList<String> getTip1012() {
        int tipDays = 30;
        String day = DatabaseSysConfigManager.getInstance()
                .getProperty("sys.contract.expire.remind");
        if (StringUtils.isNotEmpty(day)) {
            tipDays = Integer.valueOf(day).intValue();
        }
        ArrayList tipArrayList = new ArrayList();
        StringBuffer profile = new StringBuffer();
        String nowDate = DateUtil.formatTodayDate();
        String tipDate = DateUtil.dateTimeAdd(nowDate, tipDays - 1, 5);

        StringBuffer buffer = new StringBuffer();
        buffer.append("select e.id,e.empName from Employee e,Empcontract c ");
        buffer.append("where e.contract.ectId=c.ectId ");
        buffer.append("and e.empStatus=1 ");
        buffer.append("and c.etcExpire=0 ");
        buffer.append("and c.ectEndDate<='" + tipDate + "' ");
        buffer.append("and c.ectEndDate>='" + nowDate + "'");
        String hql = buffer.toString();
        List empNames = this.findTipDao.exeHqlList(hql);
        int size = empNames.size();
        int flag = 0;
        String id = "";
        String name = "";
        for (int i = 0; i < size; ++i) {
            Object[] obj = (Object[]) (Object[]) empNames.get(i);
            if (null != obj) {
                id = (String) obj[0];
                name = (String) obj[1];
                if ((flag > 0) && (flag % 4 == 0)) {
                    ++flag;
                    profile.append("<br>");
                }
                profile.append(getJSString(new StringBuilder()
                        .append("../profile/myInfo.action?empNo=").append(id).append("&empName=")
                        .append(name).append("&tab=3").toString(), name)
                        + "〄1�7");
            }
        }

        if (profile.length() > 0)
            tipArrayList.add(tipDays + "天内合同到期的员工：<br>"
                    + profile.substring(0, profile.length() - 1));

        ArrayList expList = getExpiredContractEmps();
        expList.addAll(tipArrayList);
        return expList;
    }

    private ArrayList<String> getExpiredContractEmps() {
        ArrayList tipArrayList = new ArrayList();
        StringBuffer profile = new StringBuffer();
        StringBuffer hqlBuffer = new StringBuffer();

        hqlBuffer.append("select id,empName from Employee ");
        hqlBuffer.append("where empStatus=1 and contract = null");
        List noContracts = this.findTipDao.exeHqlList(hqlBuffer.toString());

        String strDate = DateUtil.formatTodayDate();
        hqlBuffer = new StringBuffer();
        hqlBuffer.append("select e.id,e.empName from Employee e,Empcontract c ");
        hqlBuffer.append("where e.empStatus=1 ");
        hqlBuffer.append("and e.contract.ectId=c.ectId ");
        hqlBuffer.append("and c.etcExpire=0 ");
        hqlBuffer.append("and c.ectEndDate<'" + strDate + "'");
        List names = this.findTipDao.exeHqlList(hqlBuffer.toString());
        names.addAll(noContracts);
        int size = names.size();
        int flag = 0;
        String id = "";
        String name = "";
        for (int i = 0; i < size; ++i) {
            Object[] obj = (Object[]) (Object[]) names.get(i);
            if (null != obj) {
                id = (String) obj[0];
                name = (String) obj[1];
                if ((flag > 0) && (flag % 4 == 0)) {
                    ++flag;
                    profile.append("<br>");
                }
                profile.append(getJSString(new StringBuilder()
                        .append("../profile/myInfo.action?empNo=").append(id).append("&empName=")
                        .append(name).append("&tab=3").toString(), name)
                        + "〄1�7");
            }
        }

        if (profile.length() > 0)
            tipArrayList.add("合同过期及合同未签的员工＄1�7br>" + profile.substring(0, profile.length() - 1));
        return tipArrayList;
    }

    public ArrayList<String> getTip111(String id) {
        ArrayList tipArrayList = new ArrayList();
        StringBuffer infoS = new StringBuffer();

        StringBuffer salaryS = new StringBuffer();
        Date date = getLastLoginTime(id);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq("id", id));
        if (date != null)
            detachedCriteria.add(Restrictions.gt("empLastChangeTime", date));
        int info = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (info > 0) {
            infoS.append(getJSString("../profile/myInfo.action?tab=1", "查看"));
            tipArrayList.add("您的员工基本资料已被修改，请注意" + infoS + "〄1�7br>");
        }
        DetachedCriteria detachedCriteria3 = DetachedCriteria.forClass(Empsalaryconfig.class);
        detachedCriteria3.add(Restrictions.eq("id", id));
        if (date != null)
            detachedCriteria3.add(Restrictions.gt("escLastChangeTime", date));
        int salary = this.findTipDao.findRowCountByCriteria(detachedCriteria3);
        if (salary > 0) {
            salaryS.append(getJSString("../profile/myInfo.action?tab=4", "查看"));
            tipArrayList.add("您的薪资本月已调整，请注愄1�7" + salaryS + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip301() {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Trtype.class);
        int trtypeList = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (trtypeList == 0) {
            String list = getJSString("../training/trtConfig.action", "设置");
            tipArrayList.add("系统中尚未设置培训种类，请予仄1�7" + list + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip601() {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Recruitchannel.class);
        int recruitList = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (recruitList == 0) {
            String list = getJSString("../recruitment/recruitchannellist.action", "设置");
            tipArrayList.add("系统中尚无招聘渠道，请予仄1�7" + list + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip201() {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        detachedCriteria.add(Restrictions.eq("esaStatus", Integer.valueOf(31)));
        detachedCriteria
                .add(Restrictions.and(Restrictions.gt("esaCurEffDate", getLastMonthDate()),
                                      Restrictions.le("esaCurEffDate", getNextMonthDate())));

        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String tempS = getJSString(
                                       "../compensation/searchCompaplan.action?compaplan.ecpStatus=31",
                                       "调整");
            tipArrayList.add("本月朄1�7" + number + "个员工需覄1�7" + tempS + "薪资〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip401() {
        ArrayList tipArrayList = new ArrayList();
        StringBuffer balanceS = new StringBuffer();
        StringBuffer clanderS = new StringBuffer();

        Date yearStartDate = DateUtil.getYearFirstDay(this.currentYear);
        Date yearEndDate = DateUtil.getYearEndDay(this.currentYear);

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leavecalendar.class);
        detachedCriteria.add(Restrictions.and(Restrictions.ge("lcDate", yearStartDate),
                                              Restrictions.le("lcDate", yearEndDate)));

        if (this.findTipDao.findRowCountByCriteria(detachedCriteria) == 0) {
            clanderS.append(getJSString("../examin/leavecalendarManage.action", "设置"));
            tipArrayList.add("本年度公共假日未作调整，请予仄1�7" + clanderS + "〄1�7br>");
        }

        DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(Leavebalance.class);
        List balance = this.findTipDao.findByCriteria(detachedCriteria2);

        detachedCriteria.add(Restrictions.eq("lbYear", Integer.toString(this.currentYear)));
        DetachedCriteria detachedCriteria3 = DetachedCriteria.forClass(Employee.class);
        detachedCriteria3.add(Restrictions.eq("empStatus", Integer.valueOf(1)));
        List empList = this.findTipDao.findByCriteria(detachedCriteria3);
        int listSize = empList.size();
        int balanceCount = 0;
        boolean flag = false;
        int balanceSize = balance.size();
        for (int i = 0; i < listSize; ++i) {
            flag = false;
            Employee emp = (Employee) empList.get(i);
            for (int j = 0; j < balanceSize; ++j) {
                if (emp.getId().equals(
                                       ((Leavebalance) balance.get(j)).getLbEmpNo().getId()
                                               .toString())) {
                    flag = true;
                    break;
                }
            }
            if (flag)
                continue;
            ++balanceCount;
        }
        if (balanceCount > 0) {
            balanceS.append(getJSString("../examin/leavebalanceManage.action?tab=1", "设置"));
            tipArrayList.add("有员工未设置本年度年假额度，请予仄1�7" + balanceS + "〄1�7br>");
        }
        tipArrayList.addAll(getTip4012());
        return tipArrayList;
    }

    public ArrayList<String> getTip4012() {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria dc = DetachedCriteria.forClass(Leaverequest.class);
        dc.add(Restrictions.lt(Leaverequest.PROP_LR_STATUS, Integer.valueOf(15)));
        dc.add(Restrictions.isNull(Leaverequest.PROP_LR_NEXT_APPROVER));
        int number = this.findTipDao.findRowCountByCriteria(dc);
        if (number != 0) {
            String tempS = getJSString("../examin/hrExaminSearch.action?tab=1", "备案");
            tipArrayList.add("朄1�7" + number + "个请假申请等待HR" + tempS + "〄1�7br>");
        }

        DetachedCriteria dc2 = DetachedCriteria.forClass(Overtimerequest.class);
        dc2.add(Restrictions.lt(Overtimerequest.PROP_OR_STATUS, Integer.valueOf(15)));
        dc2.add(Restrictions.isNull(Overtimerequest.PROP_OR_NEXT_APPROVER));
        int number2 = this.findTipDao.findRowCountByCriteria(dc2);
        if (number2 != 0) {
            String tempS = getJSString("../examin/hrExaminSearch.action?tab=2", "备案");
            tipArrayList.add("朄1�7" + number2 + "个加班申请等待HR" + tempS + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip4112(String posNo) {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leaverequest.class);
        detachedCriteria.add(Restrictions.eq(Leaverequest.PROP_LR_NEXT_APPROVER, posNo));
        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String tempS = getJSString("../examin/deptExaminSearch.action?tab=1", "批准");
            tipArrayList.add("朄1�7" + number + "个请假申请等待您的1�7" + tempS + "〄1�7br>");
        }

        DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(Overtimerequest.class);
        detachedCriteria2.add(Restrictions.eq(Overtimerequest.PROP_OR_NEXT_APPROVER, posNo));
        int number2 = this.findTipDao.findRowCountByCriteria(detachedCriteria2);
        if (number2 != 0) {
            String tempS = getJSString("../examin/deptExaminSearch.action?tab=2", "批准");
            tipArrayList.add("朄1�7" + number2 + "个加班申请等待您的1�7" + tempS + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip411(String id) {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Leaverequest.class);
        detachedCriteria.add(Restrictions.eq("lrStatus", Integer.valueOf(21)));
        detachedCriteria.add(Restrictions.eq("lrEmpNo.id", id));
        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String temp = getJSString("../examin/myExamins.action?tab=1", "查看");
            tipArrayList.add("您有请假申请被拒绝，请注愄1�7" + temp + "〄1�7br>");
        }
        DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(Overtimerequest.class);
        detachedCriteria2.add(Restrictions.eq("orStatus", Integer.valueOf(21)));
        detachedCriteria2.add(Restrictions.eq("orEmpNo.id", id));
        int number2 = this.findTipDao.findRowCountByCriteria(detachedCriteria2);
        if (number2 != 0) {
            String temp = getJSString("../examin/myExamins.action?tab=2", "查看");
            tipArrayList.add("您有加班申请被拒绝，请注愄1�7" + temp + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip3012() {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Tremployeeplan.class);
        detachedCriteria.add(Restrictions.eq("trpStatus", Integer.valueOf(11)));
        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String tempS = getJSString("../training/trepHrApprove.action", "审批");
            tipArrayList.add("朄1�7" + number + "个部门已审的员工培训计划等待您的" + tempS + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip3112(String[] depNo) {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Tremployeeplan.class);
        detachedCriteria.add(Restrictions.eq("trpStatus", Integer.valueOf(2)));
        detachedCriteria.add(Restrictions.in("trpTraineeDept.id", depNo));
        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String tempS = getJSString("../training/trepDeptApprove.action", "审批");
            tipArrayList.add("朄1�7" + number + "个已提交的员工培训计划等待您的1�7" + tempS + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip311(String id) {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Tremployeeplan.class);
        detachedCriteria.add(Restrictions.eq("trpTraineeNo.id", id));
        detachedCriteria.add(Restrictions.eq("trpStatus", Integer.valueOf(21)));
        Date date = getLastLoginTime(id);
        if (date != null)
            detachedCriteria.add(Restrictions.gt("trpLastChangeTime", date));
        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String temp = getJSString("../training/myTrainingList.action", "查看");
            tipArrayList.add("您有培训计划被拒绝，请注愄1�7" + temp + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip6012() {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Recruitplan.class);
        detachedCriteria.add(Restrictions.eq("recpStatus", Integer.valueOf(11)));
        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String tempS = getJSString("../recruitment/ApproverRecruitplanHR.action", "审批");
            tipArrayList.add("朄1�7" + number + "个部门已审的员工招聘计划等待您的" + tempS + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip6112(String[] depNo) {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Recruitplan.class);
        detachedCriteria.add(Restrictions.eq("recpStatus", Integer.valueOf(2)));
        detachedCriteria.add(Restrictions.in("recpDepartmentNo.id", depNo));
        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String tempS = getJSString("../recruitment/ApproverRecruitplanDept.action", "审批");
            tipArrayList.add("朄1�7" + number + "个已提交的员工招聘计划等待您的1�7" + tempS + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip611(String id) {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Recruitplan.class);
        detachedCriteria.add(Restrictions.eq("recpCreateBy.id", id));
        detachedCriteria.add(Restrictions.eq("recpStatus", Integer.valueOf(21)));
        Date date = getLastLoginTime(id);
        if (date != null)
            detachedCriteria.add(Restrictions.gt("recpLastChangeTime", date));
        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String temp = getJSString("../recruitment/SearchRecruitplan.action", "查看");
            tipArrayList.add("您有招聘计划被拒绝，请注愄1�7" + temp + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip2012() {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        detachedCriteria.add(Restrictions.eq("esaStatus", Integer.valueOf(21)));
        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String tempS = getJSString("../compensation/searchApproveCompaplan.action?month=all",
                                       "审批");
            tipArrayList.add("朄1�7" + number + "个部门已审的调薪计划等待您的" + tempS + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip2112(String id) {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        detachedCriteria.add(Restrictions.eq("esaStatus", Integer.valueOf(11)));
        detachedCriteria.add(Restrictions.eq("esaNextApprover.id", id));
        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String tempS = getJSString("../compensation/searchBatchCompaplan.action", "审批");
            tipArrayList.add("有已提交的调薪计划等待您的1�7" + tempS + "〄1�7br>");
        }
        return tipArrayList;
    }

    public ArrayList<String> getTip211(String id) {
        ArrayList tipArrayList = new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
        detachedCriteria.add(Restrictions.eq("esaStatus", Integer.valueOf(2)));
        detachedCriteria.setFetchMode("esaCreateBy", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq("esaCreateBy.id", id));
        Date date = getLastLoginTime(id);
        if (date != null)
            detachedCriteria.add(Restrictions.gt("esaLastChangeTime", date));

        int number = this.findTipDao.findRowCountByCriteria(detachedCriteria);
        if (number != 0) {
            String temp = getJSString("../compensation/searchBatchCompaplan.action", "查看");
            tipArrayList.add("您有" + number + "调薪计划被拒绝，请注愄1�7" + temp + "〄1�7br>");
        }
        return tipArrayList;
    }

    public List getObjectList(DetachedCriteria detachedCriteria) {
        if (detachedCriteria == null)
            return null;
        return this.findTipDao.findByCriteria(detachedCriteria);
    }

    public IIFindTipDao getFindTipDao() {
        return this.findTipDao;
    }

    public void setFindTipDao(IIFindTipDao findTipDao) {
        this.findTipDao = findTipDao;
    }

    public Date getLastLoginTime(String userId) {
        DetachedCriteria userinfoDeta = DetachedCriteria.forClass(Userinfo.class);
        userinfoDeta.add(Restrictions.eq(Userinfo.PROP_ID, userId));
        List userList = this.findTipDao.findByCriteria(userinfoDeta);
        Userinfo userInfo = null;
        if ((userList != null) && (userList.size() == 1)) {
            userInfo = (Userinfo) userList.get(0);
        }
        if (userInfo == null)
            return null;
        return userInfo.getUiLastLoginTime();
    }

    public boolean checkDateInMonth(Date date) {
        if (date == null)
            return false;
        Calendar cal = Calendar.getInstance();
        cal.add(2, 0);
        int month = cal.get(2) + 1;
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        String mydate = sp.format(date);
        int monthInt = Integer.parseInt(mydate.substring(mydate.indexOf('-') + 1, mydate
                .lastIndexOf('-')));
        return month == monthInt;
    }

    public boolean checkDateInYearAndMonth(Date date) {
        if (date == null)
            return false;
        Calendar cal = Calendar.getInstance();
        cal.add(2, 0);
        int month = cal.get(2) + 1;
        int year = cal.get(1);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        String mydate = sp.format(date);
        int monthInt = Integer.parseInt(mydate.substring(mydate.indexOf('-') + 1, mydate
                .lastIndexOf('-')));
        int yearInt = Integer.parseInt(mydate.substring(0, mydate.indexOf('-')));
        return (month == monthInt) && (year == yearInt);
    }

    public boolean checkDateInDay(Date date) {
        if (date == null)
            return false;
        Calendar cal = Calendar.getInstance();
        cal.add(2, 0);
        int month = cal.get(2) + 1;
        int day = cal.get(5);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        String mydate = sp.format(date);
        int monthInt = Integer.parseInt(mydate.substring(mydate.indexOf('-') + 1, mydate
                .lastIndexOf('-')));
        int dayInt = Integer.parseInt(mydate.substring(mydate.lastIndexOf('-') + 1).trim());
        return (month == monthInt) && (day <= dayInt);
    }

    public Date getLastMonthDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(2, 0);
        cal.set(5, 0);
        return cal.getTime();
    }

    public Date getNextMonthDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(2, 1);
        cal.set(5, 1);
        return cal.getTime();
    }

    public Date getNextYearDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(1, 1);
        cal.set(5, 1);

        return cal.getTime();
    }

    public String getThisYear() {
        Calendar cal = Calendar.getInstance();
        cal.add(1, 0);
        int year = cal.get(1);
        return Integer.toString(year);
    }

    public String getJSString(String action, String tip) {
        return "<span onclick =\"toAction('" + action
                + "');\" style =\"cursor:pointer\"><font color =\"blue\">" + tip + "</font></span>";
    }

    public List<String> getEmployeeAdditionalTip() {
        DetachedCriteria additionalDateTip = DetachedCriteria.forClass(Empaddconf.class);
        additionalDateTip.add(Restrictions.eq("eadcFieldType", "date"));
        additionalDateTip.add(Restrictions.eq("eadcTableName", "empadditional"));
        additionalDateTip.add(Restrictions.not(Restrictions.isNull("eadcFieldValue")));
        additionalDateTip.add(Restrictions.ne("eadcFieldValue", ""));
        List additionalDateTipList = this.findTipDao.findByCriteria(additionalDateTip);

        DetachedCriteria additionalDetachedCriteria = DetachedCriteria.forClass(Employee.class);
        additionalDetachedCriteria.add(Restrictions.eq("empStatus", new Integer(1)));
        List<Employee> additionalList = this.findTipDao.findByCriteria(additionalDetachedCriteria);
        StringBuffer additionalTip = new StringBuffer();
        int additionalTipSize = 0;
        Map tipMap = new HashMap();
        for (Iterator i$ = additionalDateTipList.iterator(); i$.hasNext();) {
            Empaddconf conf = (Empaddconf) i$.next();
            Integer tipDays = getIntegerValue(conf.getEadcFieldValue());
            if (tipDays == null) {
                continue;
            }
            for (Employee add : additionalList) {
                if ((additionalTipSize > 0) && (additionalTipSize % 4 == 0)) {
                    ++additionalTipSize;
                    additionalTip.append("<br>");
                }
                Date date = getDateValue(add, conf.getEadcSeq().intValue());
                if (isTipDate(date, tipDays.intValue())) {
                    String tip = (String) tipMap.get(conf.getEadcFieldName());
                    String tmp = getJSString(new StringBuilder()
                            .append("../profile/myInfo.action?empNo=").append(add.getId())
                            .append("&empName=").append(add.getEmpName()).append("&tab=2")
                            .toString(), add.getEmpName())
                            + "〄1�7";

                    if (tip == null)
                        tipMap.put(conf.getEadcFieldName(), tipDays + "天内"
                                + conf.getEadcFieldName() + "到期的员工：<br/>" + tmp);
                    else
                        tipMap.put(conf.getEadcFieldName(), (String) tipMap.get(conf
                                .getEadcFieldName())
                                + tmp);
                }
            }
        }

        Empaddconf conf;
        Integer tipDays;
        List resultSet = new ArrayList();
        for (Object key : tipMap.keySet()) {
            String tmpValue = ((String) tipMap.get(key)).substring(0, ((String) tipMap.get(key))
                    .length() - 1);
            resultSet.add(tmpValue);
        }
        return resultSet;
    }

    private Date getDateValue(Employee additional, int index) {
        String method = "empAdditional" + index;
        try {
            Object rs = PropertyUtils.getProperty(additional, method);
            if (rs == null) {
                return null;
            }
            String result = (String) rs;
            return this.format.parse(result);
        } catch (Exception e) {
        }
        return null;
    }

    private Integer getIntegerValue(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
        }
        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.homepage.bo.FindTipImpl JD-Core Version: 0.5.4
 */