package com.hr.examin.action;

import com.hr.base.BaseAction;
import com.hr.base.ComonBeans;
import com.hr.base.DWRUtil;
import com.hr.base.Status;
import com.hr.base.UsersAuthority;
import com.hr.base.wf.Workflow;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Statusconf;
import com.hr.examin.action.beans.APM;
import com.hr.examin.action.beans.ExaminSearchBean;
import com.hr.examin.action.beans.LeaveFormBean;
import com.hr.examin.action.beans.OvertimeFormBean;
import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.bo.LrDataCheckImpl;
import com.hr.examin.bo.interfaces.IAttendshiftBO;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.bo.interfaces.IOvertimerequestBo;
import com.hr.examin.bo.interfaces.IOvertimetypeBo;
import com.hr.examin.bo.interfaces.IWorkFlowBO;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Leavebalance;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimerequest;
import com.hr.examin.domain.Overtimetype;
import com.hr.examin.domain.WorkFlowApprover;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.security.bo.IHasAuthBO;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.InterpreterException;
import com.hr.util.InterpreterExecuteContext;
import com.hr.util.StringUtil;
import com.hr.util.SysConfigManager;
import com.hr.util.SystemPropertiesReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class EmpExaminAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    public static final String SUCCESSByHourMode = "successByHourMode";
    SysConfigManager dbConfigManager = DatabaseSysConfigManager.getInstance();
    private List<Department> deptList;
    private List<Overtimetype> otTypeList;
    private List<Leavetype> lrTypeList;
    private List<Statusconf> lrStatusList;
    private List<Statusconf> otStatusList;
    private static final String AUTHMODULE = "411";
    private static final String AUTHCONDITION = "3";
    private List<Statusconf> statusList;
    private Map<String, String> statusMap;
    protected String infoMeg;
    protected String errorMsg;
    private String leaveType;

    public String getSuccessPath() {
        String mode = this.dbConfigManager.getProperty("sys.examin.shift.enable");
        if (mode.startsWith("1")) {
            return "successByHourMode";
        }
        return "success";
    }

    public void initLRLists() {
        this.deptList = getDrillDown("Department", new String[] { "1" });
        this.lrTypeList = getDrillDown("LeaveType", new String[0]);

        this.statusMap = ComonBeans.getValuesToMap(Leaverequest.PROP_LR_STATUS, Status.PROCESS_MAP,
                                                   new boolean[] { false });
    }

    public void initOTLists() {
        this.deptList = getDrillDown("Department", new String[] { "1" });
        this.otTypeList = getDrillDown("OvertimeType", new String[0]);

        this.statusMap = ComonBeans.getValuesToMap(Overtimerequest.PROP_OR_STATUS,
                                                   Status.PROCESS_MAP, new boolean[] { false });
    }

    public Leaverequest getLRContext(String lrID, HttpSession session) {
        if ((session == null) || (WebContextFactory.get() == null)
                || (WebContextFactory.get().getSession() == null)) {
            return null;
        }
        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        Leaverequest retlr = null;
        if (DWRUtil.checkAuth("myLeaveSearch", "execute").equals("OWN")) {
            retlr = lr_BO.loadLeaverequest(lrID);
        }
        if ((DWRUtil.checkAuth("deptLeaveSearch", "execute").equals("ALL"))
                || (DWRUtil.checkAuth("allLeaveSearch", "execute").equals("ALL"))) {
            retlr = lr_BO.loadLeaverequest(lrID);
        }
        if ((DWRUtil.checkAuth("deptLeaveSearch", "execute").equals("SUB"))
                || (DWRUtil.checkAuth("allLeaveSearch", "execute").equals("SUB"))) {
            retlr = lr_BO.loadLeaverequest(lrID);
        }
        if (DWRUtil.checkAuth("hrLeaveSearch", "execute").equals("HR")) {
            retlr = lr_BO.loadLeaverequest(lrID);
        }
        if (DWRUtil.checkAuth("allLeaveSearch", "execute").equals("HR")) {
            retlr = lr_BO.loadLeaverequest(lrID);
        }
        if ((retlr != null) && (((retlr.getLrStartApm() != null) || (retlr.getLrEndApm() != null)))) {
            retlr.setLrStartDate(DateUtil.convDateTimeToDate(retlr.getLrStartDate()));
            retlr.setLrEndDate(DateUtil.convDateTimeToDate(retlr.getLrEndDate()));
        }
        return retlr;
    }

    public Overtimerequest getOTContext(String otID, HttpSession session) {
        if ((session == null) || (WebContextFactory.get() == null)
                || (WebContextFactory.get().getSession() == null)) {
            return null;
        }
        IOvertimerequestBo or_Bo = (IOvertimerequestBo) getBean("overtimerequestBO");
        Overtimerequest relt = null;
        if (DWRUtil.checkAuth("myOvertimeSearch", "execute").equals("OWN")) {
            relt = or_Bo.loadOvertimerequest(otID);
            if (relt != null)
                return relt;
        }
        if ((DWRUtil.checkAuth("deptOvertimeSearch", "execute").equals("ALL"))
                || (DWRUtil.checkAuth("allOvertimeSearch", "execute").equals("ALL"))) {
            relt = or_Bo.loadOvertimerequest(otID);
            if (relt != null)
                return relt;
        }
        if ((DWRUtil.checkAuth("deptOvertimeSearch", "execute").equals("SUB"))
                || (DWRUtil.checkAuth("allOvertimeSearch", "execute").equals("SUB"))) {
            relt = or_Bo.loadOvertimerequest(otID);
            if (relt != null)
                return relt;
        }
        if (DWRUtil.checkAuth("hrOvertimeSearch", "execute").equals("HR")) {
            relt = or_Bo.loadOvertimerequest(otID);
            if (relt != null)
                return relt;
        }
        if (DWRUtil.checkAuth("allOvertimeSearch", "execute").equals("HR")) {
            relt = or_Bo.loadOvertimerequest(otID);
            if (relt != null)
                return relt;
        }
        return null;
    }

    /** @deprecated */
    public String approveOrReject(String actionName, String operate, String id, String logMeg,
            HttpSession session) {
        if ("allLeaveSearch.action".equals(actionName)) {
            if ("cancel".equals(operate)) {
                return new AllLeaveSearchAction().cancelled(id, logMeg, session);
            }
        } else if (("allOvertimeSearch.action".equals(actionName)) && ("cancel".equals(operate))) {
            return new AllOvertimeSearchAction().cancelled(id, logMeg, session);
        }

        return "错误＄1�7";
    }

    /** @deprecated */
    public String getAPMDecription(Date inpuDate) {
        return APM.getDescription(inpuDate);
    }

    /** @deprecated */
    public void validateLRStatus(ExaminSearchBean es_Bean) {
        if ((es_Bean == null) || (es_Bean.getStatu() == null)
                || (es_Bean.getStatu().intValue() == 0) || (es_Bean.getStatu().intValue() == 22)
                || (es_Bean.getStatu().intValue() == 14) || (es_Bean.getStatu().intValue() == 15)
                || (es_Bean.getStatu().intValue() == 21) || (es_Bean.getStatu().intValue() == 2)
                || (es_Bean.getStatu().intValue() == 16) || (es_Bean.getStatu().intValue() == 5)) {
            return;
        }

        addErrorInfo("Invalidate Status");
    }

    /** @deprecated */
    public void validateOTStatus(ExaminSearchBean es_Bean) {
        if ((es_Bean == null) || (es_Bean.getStatu() == null)
                || (es_Bean.getStatu().intValue() == 0) || (es_Bean.getStatu().intValue() == 22)
                || (es_Bean.getStatu().intValue() == 5) || (es_Bean.getStatu().intValue() == 14)
                || (es_Bean.getStatu().intValue() == 15) || (es_Bean.getStatu().intValue() == 21)
                || (es_Bean.getStatu().intValue() == 2) || (es_Bean.getStatu().intValue() == 16)) {
            return;
        }

        addErrorInfo("Invalidate Status");
    }

    public void validateDate(ExaminSearchBean es_Bean) {
        if (es_Bean != null) {
            if ((es_Bean.getEndDate() != null) && (es_Bean.getStartDate() != null)
                    && (es_Bean.getStartDate().getTime() > es_Bean.getEndDate().getTime())) {
                addErrorInfo("弄1�7始时间应小于结束时间＄1�7");
            }

            if ((((es_Bean.getEndDate() != null) || (es_Bean.getStartDate() == null)))
                    && (((es_Bean.getEndDate() == null) || (es_Bean.getStartDate() != null))))
                return;
            addErrorInfo("必须同时填写弄1�7始和结束日期＄1�7");
        }
    }

    public boolean hasAuthDWR(int moduleId) {
        IHasAuthBO bo = (IHasAuthBO) SpringBeanFactory.getBean("hasService");
        UsersAuthority userAuthority = new UsersAuthority(String.valueOf(moduleId));
        boolean has = false;
        try {
            has = bo.hasDWRAuth(userAuthority);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return has;
    }

    public boolean hasAuthModuleConditionDWR(int moduleId, int condition) {
        IHasAuthBO bo = (IHasAuthBO) SpringBeanFactory.getBean("hasService");
        UsersAuthority userAuthority = new UsersAuthority(String.valueOf(moduleId), String
                .valueOf(condition));

        boolean has = false;
        try {
            has = bo.hasDWRAuth(userAuthority);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return has;
    }

    public Leaverequest initLfBean(String operate, LeaveFormBean lf_Bean, String lrId) {
        String msgIdError = "输入请假单参数有误，请刷新后重试＄1�7";

        Leaverequest lr = new Leaverequest();
        if (!operate.contains("create")) {
            if (lrId != null) {
                ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
                lr = lr_BO.loadOneLeaverequest(lrId, new String[] { "lrEmpDept", "lrLtNo",
                        "lrEmpNo" });
            }
            if ((lrId == null) || (lr == null) || (lr.getLrId() == null)) {
                addErrorInfo(msgIdError);
                return null;
            }

        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        if (!operate.contains("create")) {
            lf_Bean.setLrEmpNo(lr.getLrEmpNo());
        } else if (lf_Bean.getLrEmpNo() != null)
            lf_Bean.setLrEmpNo(empBo.loadEmp(lf_Bean.getLrEmpNo().getId(), null));
        else
            lf_Bean.setLrEmpNo(empBo.loadEmp(getCurrentEmpNo(), null));

        if ((((operate.contains("create")) || (operate.contains("update"))))
                && (lf_Bean.getAllLtType() == null)) {
            ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
            lf_Bean.setAllLtType(lt_BO.FindAllLeaveType());

            for (Leavetype lt : lf_Bean.getAllLtType()) {
                if ((lr.getLrLtNo() != null) && (lr.getLrLtNo().getLtNo().equals(lt.getLtNo()))) {
                    lf_Bean.setLrLtNo(lt);
                    break;
                }

            }

        }

        if (lf_Bean.getLrReason() == null)
            lf_Bean.setLrReason(lr.getLrReason());

        if (lf_Bean.getBeginDate() == null) {
            if (!operate.contains("create")) {
                lf_Bean.setBeginDate(lr.getLrStartDate());
                lf_Bean.setEndDate(lr.getLrEndDate());
            } else {
                IAttendshiftBO attendBo = (IAttendshiftBO) getBean("attendshiftBO");
                Attendshift defaultAs = attendBo.getDefaultAttendshiftByEmp(lr.getLrEmpNo());
                Date[] dates = ExaminDateUtil.getShiftArr(new Date(), defaultAs.getAttsSession());
                lf_Bean.setBeginDate(dates[0]);
                lf_Bean.setEndDate(dates[1]);
            }

        }

        String leaveType = DatabaseSysConfigManager.getInstance()
                .getProperty("sys.examin.leave.type");
        lf_Bean.setApplyLRByDay(ExaminDateUtil.applyLRByDay(leaveType, lf_Bean.getLrEmpNo()
                .getEmpShiftType()));

        if (lf_Bean.isApplyLRByDay()) {
            if (lf_Bean.getBeginApm() == null) {
                lf_Bean.setBeginApm(Integer.valueOf((lr.getLrStartApm() == null) ? 0 : lr
                        .getLrStartApm().intValue()));
                lf_Bean.setEndApm(Integer.valueOf((lr.getLrEndApm() == null) ? 1 : lr.getLrEndApm()
                        .intValue()));
            }

            if (lr.getLrTotalDays() != null)
                lf_Bean.setTotalTime(StringUtil.formatBDToS(lr.getLrTotalDays(), new String[0])
                        + "处1�7");
        } else {
            if (lf_Bean.getBeginHour() == null) {
                lf_Bean.setBeginHour(Integer.valueOf(DateUtil.getHour(lf_Bean.getBeginDate())));
                lf_Bean.setBeginMinute(Integer.valueOf(DateUtil.getMinute(lf_Bean.getBeginDate())));
                lf_Bean.setEndHour(Integer.valueOf(DateUtil.getHour(lf_Bean.getEndDate())));
                lf_Bean.setEndMinute(Integer.valueOf(DateUtil.getMinute(lf_Bean.getEndDate())));
            }

            if (lr.getLrTotalHours() != null)
                lf_Bean.setTotalTime(lr.getLrTotalHours().toString() + "小时");

            if (lf_Bean.getBeginHour().intValue() == 24)
                lf_Bean.setBeginDate(DateUtil.dateAdd(lf_Bean.getBeginDate(), -1));
            if (lf_Bean.getEndHour().intValue() == 24) {
                lf_Bean.setEndDate(DateUtil.dateAdd(lf_Bean.getEndDate(), -1));
            }
        }

        if (operate.contains("modify")) {
            LrDataCheckImpl lrAddCheckData = (LrDataCheckImpl) getBean("lrDataCheck");
            String info = lrAddCheckData.validateLRDate(lr);

            String info2 = lrAddCheckData.validateLRConflict(lr);
            if ((!info.equals("SUCC")) || (!info2.equals("SUCC"))) {
                addSuccessInfo(info + " " + info2);
            }

            if (lf_Bean.isApplyLRByDay()) {
                lf_Bean
                        .setStartTime(DateUtil
                                .formatDateByApm(lr.getLrStartDate(), new Integer[] { lr
                                        .getLrStartApm() }));
                lf_Bean.setEndTime(DateUtil.formatDateByApm(lr.getLrEndDate(), new Integer[] { lr
                        .getLrEndApm() }));

                if (lr.getUseableDays() != null)
                    lf_Bean.setUseableTime(StringUtil.formatBDToS(lr.getUseableDays(),
                                                                  new String[0])
                            + "处1�7");
                if (lr.getUsedDays() != null)
                    lf_Bean.setUsedTime(StringUtil.formatBDToS(lr.getUsedDays(), new String[0])
                            + "处1�7");
                if (lr.getRemainDays() != null)
                    lf_Bean.setRemainTime(StringUtil.formatBDToS(lr.getRemainDays(), new String[0])
                            + "处1�7");
            } else {
                lf_Bean.setStartTime(DateUtil.formatDateByApm(lr.getLrStartDate(), new Integer[0]));
                lf_Bean.setEndTime(DateUtil.formatDateByApm(lr.getLrEndDate(), new Integer[0]));

                if (lr.getUseableHours() != null)
                    lf_Bean.setUseableTime(lr.getUseableHours().toString() + "小时");
                if (lr.getUsedHours() != null)
                    lf_Bean.setUsedTime(lr.getUsedHours().toString() + "小时");
                if (lr.getRemainHours() != null)
                    lf_Bean.setRemainTime(lr.getRemainHours().toString() + "小时");

            }

            String statusDesc = ComonBeans.getParameterValue("lrStatus", new String[] { lr
                    .getLrStatus().toString() });
            lf_Bean.setLrStatus(statusDesc);

            ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
            lf_Bean.setLogList(logBO.getLogs("leaverequest", lr.getLrId()));
        }

        return lr;
    }

    public String wfObjOpSingle(String operate, LeaveFormBean lf_Bean, String lrUpdateId) {
        String msgIEError = "浏览器参数异常，请刷新后重试＄1�7";

        if (lf_Bean == null) {
            addErrorInfo(msgIEError);
            return "input";
        }

        if (lf_Bean.getLrEmpNo().equals(getCurrentEmp())) {
            if (("hr-create".equals(operate)) || ("mgr-create".equals(operate)))
                operate = "create";
            if (("hr-update".equals(operate)) || ("mgr-update".equals(operate))) {
                operate = "update";
            }
        }

        String info = beforeInit(operate, lf_Bean);
        if (!info.equals("SUCC")) {
            addErrorInfo(info);
            return "input";
        }

        Leaverequest lr = objInit(operate, lf_Bean, lrUpdateId);
        if (lr == null) {
            return "input";
        }

        info = afterInit(lr);
        if (!info.equals("SUCC")) {
            addErrorInfo(info);
            return "input";
        }

        return wfObjOperate(new Object[] { lr });
    }

    public String beforeInit(String operate, LeaveFormBean lf_Bean) {
        String msgRejComment = "拒绝申请时，必须填写备注＄1�7";
        String msgModComment = "修改后审批，必须填写备注＄1�7";
        String msgCanComment = "申请作废时，必须填写备注＄1�7";
        String msgSameYear = "请假弄1�7始日朄1�7({0})和结束日朄1�7({1})必须为同丄1�7年！";
        String msgBeginTooBig = "请假弄1�7始日朄1�7({0})必须早于结束日期({1})＄1�7";
        String msgOwnSubmit = "系统不允许他人代填请假申请！";
        String msgEarlyDays = "必须提前{0}天提出请假申请！";

        if (StringUtils.isEmpty(lf_Bean.getLrAppComment())) {
            if (operate.contains("reject"))
                return msgRejComment;
            if (operate.contains("modify"))
                return msgModComment;
            if (operate.contains("cancel"))
                return msgCanComment;

        }

        if (DateUtil.getYear(lf_Bean.getBeginDate()) != DateUtil.getYear(lf_Bean.getBeginDate())) {
            return StringUtil.message(msgSameYear, new Object[] { lf_Bean.getBeginDate(),
                    lf_Bean.getEndDate() });
        }

        if (lf_Bean.getBeginHour() == null) {
            if (lf_Bean.getBeginApm().intValue() == 0)
                lf_Bean.setBeginDate(DateUtil.parseDateTimeToD(lf_Bean.getBeginDate(), 8, 0, 0));
            else
                lf_Bean.setBeginDate(DateUtil.parseDateTimeToD(lf_Bean.getBeginDate(), 13, 0, 0));
            if (lf_Bean.getEndApm().intValue() == 1)
                lf_Bean.setEndDate(DateUtil.parseDateTimeToD(lf_Bean.getEndDate(), 17, 0, 0));
            else
                lf_Bean.setEndDate(DateUtil.parseDateTimeToD(lf_Bean.getEndDate(), 12, 0, 0));
        } else {
            lf_Bean.setBeginDate(DateUtil.parseDateTimeToD(lf_Bean.getBeginDate(), lf_Bean
                    .getBeginHour().intValue(), lf_Bean.getBeginMinute().intValue(), 0));
            lf_Bean.setEndDate(DateUtil.parseDateTimeToD(lf_Bean.getEndDate(), lf_Bean.getEndHour()
                    .intValue(), lf_Bean.getEndMinute().intValue(), 0));
        }

        if (lf_Bean.getBeginDate().compareTo(lf_Bean.getEndDate()) >= 0) {
            return StringUtil.message(msgBeginTooBig, new Object[] { lf_Bean.getBeginDate(),
                    lf_Bean.getEndDate() });
        }

        if (!lf_Bean.getLrEmpNo().equals(getCurrentEmp())) {
            if ((((operate.contains("create")) || (operate.contains("update"))))
                    && ("0".equals(DatabaseSysConfigManager.getInstance()
                            .getProperty("sys.examin.create.level")))) {
                return msgOwnSubmit;
            }
        } else if (operate.contains("create")) {
            SystemPropertiesReader properties = SystemPropertiesReader.getInstance();
            String remind = properties.getValue("LR.Remind.Error");
            Integer days = Integer.valueOf(Integer.parseInt(properties
                    .getValue("LR.Remind.Error_own_days")));

            if ("true".equals(remind)) {
                Date allowStartDate = DateUtil.dateAdd(new Date(), days.intValue());
                if (DateUtil.compareTwoDay(allowStartDate, lf_Bean.getBeginDate()) == -1)
                    return StringUtil.message(msgEarlyDays, new Object[] { days });
            }
        }
        return "SUCC";
    }

    public Leaverequest objInit(String operate, LeaveFormBean lf_Bean, String lrUpdateId) {
        String msgIdError = "输入请假单参数有误，请刷新后重试＄1�7";

        Leaverequest lr = new Leaverequest();

        if (!operate.contains("create")) {
            if (!StringUtils.isEmpty(lrUpdateId)) {
                ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
                lr = lr_BO.loadLeaverequest(lrUpdateId);
            }
            if ((lr == null) || (StringUtils.isEmpty(lr.getLrId()))) {
                addErrorInfo(msgIdError);
                return null;
            }

        }

        String[] fetch = { Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_LOCATION_NO };

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee emp = empBo.loadEmp(lf_Bean.getLrEmpNo().getId(), fetch);
        lr.setLrEmpNo(emp);

        String leaveType = DatabaseSysConfigManager.getInstance()
                .getProperty("sys.examin.leave.type");
        lr.setApplyLRByDay(ExaminDateUtil
                .applyLRByDay(leaveType, lr.getLrEmpNo().getEmpShiftType()));

        if (lr.isApplyLRByDay()) {
            lr.setLrStartApm(lf_Bean.getBeginApm());
            lr.setLrEndApm(lf_Bean.getEndApm());
        }

        lr.setLrStartDate(lf_Bean.getBeginDate());
        lr.setLrEndDate(lf_Bean.getEndDate());

        if ((operate.contains("create")) || (operate.contains("update"))) {
            if (!lf_Bean.getLrLtNo().equals(lr.getLrLtNo())) {
                ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
                Leavetype lrtype = lt_BO.getLeavetype(lf_Bean.getLrLtNo().getLtNo());
                lr.setLrLtNo(lrtype);
            }

            lr.setLrReason(lf_Bean.getLrReason());
        }

        if (!wfInit(operate, lf_Bean.getLrAppComment(), new Object[] { lr }))
            return null;

        return lr;
    }

    public String afterInit(Leaverequest lr) {
        String msgNoAPM = "按天请假必须填写上下午！";
        String msgNotCreator = "您不是编号为{0}的请假单的创建�1�7�！";
        String msgNoReason = "{0}必须填写请假理由＄1�7";
        String msgCommentDayC = "创建：{0}天的{1}";
        String msgCommentHourC = "创建：{0}小时的{1}";
        String msgCommentDayU = "改为：{0}天的{1}";
        String msgCommentHourU = "改为：{0}小时的{1}";

        String operate = lr.getWorkflow().getOperate();

        if ((((operate.contains("create")) || (operate.contains("update"))
                || (operate.equals("hr-confirm")) || (operate.contains("modify"))))
                && (lr.isApplyLRByDay()) && (lr.getLrStartApm() == null)) {
            return msgNoAPM;
        }

        if ((operate.contains("update"))
                && (!getCurrentEmp().getId().equals(lr.getLrEmpNo().getId()))
                && (!getCurrentEmp().getId().equals(lr.getLrCreateBy().getId()))) {
            return StringUtil.message(msgNotCreator, new Object[] { lr.getLrNo() });
        }

        if ((((operate.contains("create")) || (operate.contains("update"))))
                && (lr.getLrLtNo().getLtNeedComments().intValue() == 1)
                && (((lr.getLrReason() == null) || (lr.getLrReason().trim().equals(""))))) {
            return StringUtil.message(msgNoReason, new Object[] { lr.getLrLtNo().getLtDesc() });
        }

        LrDataCheckImpl lrAddCheckData = (LrDataCheckImpl) getBean("lrDataCheck");
        String info = lrAddCheckData.validateLRDate(lr);
        if (info != "SUCC")
            return info;

        lr.getWorkflow().setAmount(lr.getLrTotalDays());

        info = lrAddCheckData.validateLRConflict(lr);
        if (info != "SUCC")
            return info;

        if (operate.contains("create")) {
            if (lr.isApplyLRByDay())
                lr.getWorkflow().setComment(
                                            StringUtil
                                                    .message(msgCommentDayC, new Object[] {
                                                            lr.getLrTotalDays(),
                                                            lr.getLrLtNo().getLtName() }));
            else
                lr.getWorkflow().setComment(
                                            StringUtil.message(msgCommentHourC, new Object[] {
                                                    lr.getLrTotalHours(),
                                                    lr.getLrLtNo().getLtName() }));
        } else if (operate.contains("update")) {
            if (lr.isApplyLRByDay())
                lr.getWorkflow().setComment(
                                            StringUtil
                                                    .message(msgCommentDayU, new Object[] {
                                                            lr.getLrTotalDays(),
                                                            lr.getLrLtNo().getLtName() }));
            else {
                lr.getWorkflow().setComment(
                                            StringUtil.message(msgCommentHourU, new Object[] {
                                                    lr.getLrTotalHours(),
                                                    lr.getLrLtNo().getLtName() }));
            }
        }
        return "SUCC";
    }

    public String wfObjOpBatch(String operate, LeaveFormBean lf_Bean, String[] lrUpdateId) {
        String msgIEError = "浏览器参数异常，请刷新后重试＄1�7";
        String msgNoComment = "拒绝申请时，必须填写备注＄1�7";
        String msgIdError = "输入请假单参数有误，请刷新后重试＄1�7";
        String msgNoData = "未�1�7�中任何有效请假单，请刷新后重试＄1�7";

        if (lf_Bean == null) {
            addErrorInfo(msgIEError);
            return "input";
        }

        if ((lrUpdateId == null) || (lrUpdateId.length == 0)
                || (StringUtils.isEmpty(lrUpdateId[0]))) {
            addErrorInfo(msgIdError);
            return "input";
        }

        if ((operate.contains("reject")) && (StringUtils.isEmpty(lf_Bean.getLrAppComment()))) {
            addErrorInfo(msgNoComment);
            return "input";
        }

        String[] fetch = { Leaverequest.PROP_LR_EMP_NO, Leaverequest.PROP_LR_EMP_DEPT,
                Leaverequest.PROP_LR_CREATE_BY, Leaverequest.PROP_LR_LT_NO };

        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        List lrList = lr_BO.loadLeaveRequests(lrUpdateId, fetch);
        if ((lrList == null) || (lrList.size() == 0)) {
            addErrorInfo(msgNoData);
            return "input";
        }

        if (!wfInit(operate, lf_Bean.getLrAppComment(), (Object[]) lrList
                .toArray(new Leaverequest[lrList.size()]))) {
            return "input";
        }
        return wfObjOperate((Object[]) lrList.toArray(new Leaverequest[lrList.size()]));
    }

    public Overtimerequest initOfBean(String operate, OvertimeFormBean of_Bean, String orId) {
        String msgIdError = "输入加班单参数有误，请刷新后重试＄1�7";

        Overtimerequest or = new Overtimerequest();
        IOvertimerequestBo or_BO = (IOvertimerequestBo) getBean("overtimerequestBO");
        if (!operate.contains("create")) {
            if (orId != null) {
                or = or_BO.loadOvertimerequest(orId, new String[] { "orEmpDept", "orOtNo",
                        "orEmpNo" });
            }
            if ((orId == null) || (or == null) || (or.getOrId() == null)) {
                addErrorInfo(msgIdError);
                return null;
            }

        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        if (!operate.contains("create")) {
            of_Bean.setOrEmpNo(or.getOrEmpNo());
        } else if (of_Bean.getOrEmpNo() != null)
            of_Bean.setOrEmpNo(empBo.loadEmp(of_Bean.getOrEmpNo().getId(), null));
        else
            of_Bean.setOrEmpNo(empBo.loadEmp(getCurrentEmpNo(), null));

        if ((((operate.contains("create")) || (operate.contains("update"))))
                && (of_Bean.getAllOtType() == null)) {
            IOvertimetypeBo ot_BO = (IOvertimetypeBo) getBean("overtimetypeBO");
            of_Bean.setAllOtType(ot_BO.FindAllOtType());

            for (Overtimetype ot : of_Bean.getAllOtType()) {
                if ((or.getOrOtNo() != null) && (or.getOrOtNo().getOtNo().equals(ot.getOtNo()))) {
                    of_Bean.setOrOtNo(ot);
                    break;
                }

            }

        }

        if (of_Bean.getOrReason() == null)
            of_Bean.setOrReason(or.getOrReason());

        if (of_Bean.getStartDate() == null) {
            Date orStartDate = or.getOrStartDate();
            Date orEndDate = or.getOrEndDate();

            if (operate.contains("create")) {
                IAttendshiftBO attendBo = (IAttendshiftBO) getBean("attendshiftBO");
                Attendshift defaultAs = attendBo.getDefaultAttendshiftByEmp(or.getOrEmpNo());

                Date[] dates = ExaminDateUtil.getSplitDateByAttendShift(new Date(), defaultAs
                        .getAttsSession());
                orStartDate = dates[(dates.length - 1)];
                orEndDate = DateUtil.dateTimeAdd(orStartDate, 1, 10);
                if (DateUtil.convDateTimeToDate(orEndDate).after(orStartDate)) {
                    orEndDate = DateUtil.convDateTimeToDate(orEndDate);
                }
            }
            of_Bean.setStartDate(orStartDate);
            of_Bean.setStartTime(Integer.valueOf(DateUtil.getHour(orStartDate)));
            of_Bean.setStartTimeMinute(Integer.valueOf(DateUtil.getMinute(orStartDate)));

            of_Bean.setEndTime(Integer.valueOf(DateUtil.getHour(orEndDate)));
            of_Bean.setEndTimeMinute(Integer.valueOf(DateUtil.getMinute(orEndDate)));

            if ((of_Bean.getEndTime().intValue() == 0)
                    && (of_Bean.getEndTimeMinute().intValue() == 0)) {
                of_Bean.setEndTime(Integer.valueOf(24));
            }
        }

        if (operate.contains("modify")) {
            of_Bean.setUsedTime(new BigDecimal(or_BO.getMonthTotal(or, Status.PROCESSED)));

            String statusDesc = ComonBeans.getParameterValue("orStatus", new String[] { or
                    .getOrStatus().toString() });
            of_Bean.setOtStatus(statusDesc);

            of_Bean.setIsTiaoxiu(Boolean.valueOf(false));
            of_Bean.setOrTiaoxiuHours(or.getOrTiaoxiuHours());
            of_Bean.setOrTiaoxiuExpire(or.getOrTiaoxiuExpire());

            if (of_Bean.getOrTiaoxiuHours() != null) {
                of_Bean.setIsTiaoxiu(Boolean.valueOf(true));
                if (of_Bean.getOrTiaoxiuExpire() == null) {
                    ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
                    Leavetype lt = lt_BO.getTiaoxiuLeavetype(Integer.valueOf(11));
                    if (lt != null) {
                        int expireDays = getOrTiaoxiuExpire(lt, or.getOrEmpNo());
                        of_Bean.setOrTiaoxiuExpire(DateUtil
                                .dateAdd(or.getOrStartDate(), expireDays));
                    }
                }
            }

            ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
            of_Bean.setLogList(logBO.getLogs("overtimerequest", or.getOrId()));
        }
        return or;
    }

    public String wfObjOpSingle(String operate, OvertimeFormBean of_Bean, String orUpdateId) {
        String msgIEError = "浏览器参数异常，请刷新后重试＄1�7";

        if (of_Bean == null) {
            addErrorInfo(msgIEError);
            return "input";
        }

        if (of_Bean.getOrEmpNo().equals(getCurrentEmp())) {
            if (("hr-create".equals(operate)) || ("mgr-create".equals(operate)))
                operate = "create";
            if (("hr-update".equals(operate)) || ("mgr-update".equals(operate))) {
                operate = "update";
            }
        }

        String info = beforeInit(operate, of_Bean);
        if (!info.equals("SUCC")) {
            addErrorInfo(info);
            return "input";
        }

        Overtimerequest or = objInit(operate, of_Bean, orUpdateId);
        if (or == null) {
            return "input";
        }

        info = afterInit(or);
        if (!info.equals("SUCC")) {
            addErrorInfo(info);
            return "input";
        }

        return wfObjOperate(new Object[] { or });
    }

    public String beforeInit(String operate, OvertimeFormBean of_Bean) {
        String msgRejComment = "拒绝申请时，必须填写备注＄1�7";
        String msgModComment = "修改后审批，必须填写备注＄1�7";
        String msgCanComment = "申请作废时，必须填写备注＄1�7";
        String msgBeginTooBig = "加班弄1�7始时闄1�7({0})必须早于结束时间({1})＄1�7";
        String msgEndTooBig = "加班结束时间不能跨天＄1�7";
        String msgOwnSubmit = "系统不允许他人代填加班申请！";
        String msgEarlyDays = "必须提前{0}天提出加班申请！";
        String msgTiaoxiu = "请设置调休小时数＄1�7";

        if (StringUtils.isEmpty(of_Bean.getOrAppComment())) {
            if (operate.contains("reject"))
                return msgRejComment;
            if (operate.contains("modify"))
                return msgModComment;
            if (operate.contains("cancel"))
                return msgCanComment;

        }

        Date startDate = DateUtil.parseDateTimeToD(of_Bean.getStartDate(), of_Bean.getStartTime()
                .intValue(), of_Bean.getStartTimeMinute().intValue(), 0);
        of_Bean.setStartDate(startDate);

        Date endDate = DateUtil.parseDateTimeToD(of_Bean.getStartDate(), of_Bean.getEndTime()
                .intValue(), of_Bean.getEndTimeMinute().intValue(), 0);
        of_Bean.setEndDate(endDate);

        if (startDate.compareTo(endDate) >= 0) {
            return StringUtil.message(msgBeginTooBig, new Object[] { startDate, endDate });
        }

        if ((DateUtil.getDay(startDate) != DateUtil.getDay(endDate))
                && (DateUtil.getMinute(endDate) > 0)) {
            return msgEndTooBig;
        }

        if (!of_Bean.getOrEmpNo().equals(getCurrentEmp())) {
            if ((((operate.contains("create")) || (operate.contains("update"))))
                    && ("0".equals(DatabaseSysConfigManager.getInstance()
                            .getProperty("sys.examin.create.level")))) {
                return msgOwnSubmit;
            }
        } else if (operate.contains("create")) {
            SystemPropertiesReader properties = SystemPropertiesReader.getInstance();
            String remind = properties.getValue("OR.Remind.Error");
            Integer days = Integer.valueOf(Integer.parseInt(properties
                    .getValue("OR.Remind.Error_own_days")));

            if ("true".equals(remind)) {
                Date allowStartDate = DateUtil.dateAdd(new Date(), days.intValue());
                if (DateUtil.compareTwoDay(allowStartDate, of_Bean.getStartDate()) == -1) {
                    return StringUtil.message(msgEarlyDays, new Object[] { days });
                }
            }
        }

        if (operate.contains("modify")) {
            if (!of_Bean.getIsTiaoxiu().booleanValue()) {
                of_Bean.setOrTiaoxiuHours(null);
                of_Bean.setOrTiaoxiuExpire(null);
            } else if ((of_Bean.getOrTiaoxiuHours() == null)
                    || (of_Bean.getOrTiaoxiuHours().equals(Integer.valueOf(0)))) {
                return msgTiaoxiu;
            }
        }

        return "SUCC";
    }

    public Overtimerequest objInit(String operate, OvertimeFormBean of_Bean, String orUpdateId) {
        String msgIdError = "输入加班单参数有误，请刷新后重试＄1�7";

        Overtimerequest or = new Overtimerequest();

        if (!operate.contains("create")) {
            if (!StringUtils.isEmpty(orUpdateId)) {
                IOvertimerequestBo or_BO = (IOvertimerequestBo) getBean("overtimerequestBO");
                or = or_BO.loadOvertimerequest(orUpdateId);
            }
            if ((or == null) || (StringUtils.isEmpty(or.getOrId()))) {
                addErrorInfo(msgIdError);
                return null;
            }

        }

        String[] fetch = { Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_LOCATION_NO };

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee emp = empBo.loadEmp(of_Bean.getOrEmpNo().getId(), fetch);
        or.setOrEmpNo(emp);

        or.setOrStartDate(of_Bean.getStartDate());
        or.setOrEndDate(of_Bean.getEndDate());
        or.setOrTotalHours(BigDecimal.valueOf(DateUtil.dateDiff(of_Bean.getStartDate(), of_Bean
                .getEndDate(), 12) / 60.0D));

        if ((operate.contains("create")) || (operate.contains("update"))) {
            if (!of_Bean.getOrOtNo().equals(or.getOrOtNo())) {
                IOvertimetypeBo ot_BO = (IOvertimetypeBo) getBean("overtimetypeBO");
                Overtimetype ortype = ot_BO.getOvertimetype(of_Bean.getOrOtNo().getOtNo());
                or.setOrOtNo(ortype);
            }

            or.setOrReason(of_Bean.getOrReason());
        }

        if (!wfInit(operate, of_Bean.getOrAppComment(), new Object[] { or }))
            return null;

        return or;
    }

    public String afterInit(Overtimerequest or) {
        String msgNotCreator = "您不是编号为{0}的加班单的创建�1�7�！";
        String msgNoReason = "{0}必须填写加班理由＄1�7";
        String msgSysError = "系统异常，请刷新后重试！";
        String msgCommentsC = "创建：{0}小时的{1}";
        String msgCommentsU = "改为：{0}小时的{1}";

        String operate = or.getWorkflow().getOperate();

        if ((operate.contains("update"))
                && (!getCurrentEmp().getId().equals(or.getOrEmpNo().getId()))
                && (!getCurrentEmp().getId().equals(or.getOrCreateBy().getId()))) {
            return StringUtil.message(msgNotCreator, new Object[] { or.getOrNo() });
        }

        if ((((operate.contains("create")) || (operate.contains("update"))))
                && (((or.getOrReason() == null) || (or.getOrReason().trim().equals(""))))) {
            return StringUtil.message(msgNoReason, new Object[] { or.getOrOtNo().getOtName() });
        }

        IOvertimerequestBo overtimerequestBo = (IOvertimerequestBo) getBean("overtimerequestBO");

        String info = null;
        try {
            info = overtimerequestBo.orCheckShifts(or);
            if (info != "SUCC")
                return info;
        } catch (Exception e) {
            e.printStackTrace();
            return msgSysError;
        }

        info = overtimerequestBo.checkMonthLimit(or);
        if (info != "SUCC")
            return info;

        info = overtimerequestBo.orCheckTime(or);
        if (info != "SUCC")
            return info;

        if (operate.contains("create")) {
            or.getWorkflow()
                    .setComment(
                                StringUtil.message(msgCommentsC, new Object[] {
                                        or.getOrTotalHours(), or.getOrOtNo().getOtName() }));
        } else if (operate.contains("update")) {
            or.getWorkflow()
                    .setComment(
                                StringUtil.message(msgCommentsU, new Object[] {
                                        or.getOrTotalHours(), or.getOrOtNo().getOtName() }));
        }

        return "SUCC";
    }

    public String wfObjOpBatch(String operate, OvertimeFormBean of_Bean, String[] orUpdateId) {
        String msgIEError = "浏览器参数异常，请刷新后重试＄1�7";
        String msgIdError = "输入加班单参数有误，请刷新后重试＄1�7";
        String msgNoComment = "拒绝申请时，必须填写备注＄1�7";
        String msgNoData = "未�1�7�中任何有效加班单，请刷新后重试＄1�7";

        if (of_Bean == null) {
            addErrorInfo(msgIEError);
            return "input";
        }

        if ((orUpdateId == null) || (orUpdateId.length == 0)
                || (StringUtils.isEmpty(orUpdateId[0]))) {
            addErrorInfo(msgIdError);
            return "input";
        }

        if ((operate.contains("reject")) && (StringUtils.isEmpty(of_Bean.getOrAppComment()))) {
            addErrorInfo(msgNoComment);
            return "input";
        }

        String[] fetch = { Overtimerequest.PROP_OR_EMP_NO, Overtimerequest.PROP_OR_EMP_DEPT,
                Overtimerequest.PROP_OR_CREATE_BY, Overtimerequest.PROP_OR_OT_NO };

        IOvertimerequestBo or_BO = (IOvertimerequestBo) getBean("overtimerequestBO");
        List orList = or_BO.loadOvertimeRequests(orUpdateId, fetch);
        if ((orList == null) || (orList.size() == 0)) {
            addErrorInfo(msgNoData);
            return "input";
        }

        if (operate.equals("tiaoxiu")) {
            if (of_Bean.getIsTiaoxiu().booleanValue()) {
                for (int i = 0; i < orList.size(); ++i) {
                    Overtimerequest temp = (Overtimerequest) orList.get(i);
                    temp.setOrTiaoxiuHours(of_Bean.getOrTiaoxiuHours());
                    temp.setOrTiaoxiuExpire(of_Bean.getOrTiaoxiuExpire());
                }
            }
            operate = "hr-approve";
        }

        if (!wfInit(operate, of_Bean.getOrAppComment(), (Object[]) orList
                .toArray(new Overtimerequest[orList.size()]))) {
            return "input";
        }
        return wfObjOperate((Object[]) orList.toArray(new Overtimerequest[orList.size()]));
    }

    protected boolean wfInit(String operate, String comment, Object[] objList) {
        String msgStatusErr = "编号为{0}的{1}状�1�7�错误，请刷新后重试＄1�7";
        String msgNoCharge = "您无权处理{0}的{1}＄1�7";

        if ((objList == null) || (objList.length == 0))
            return false;

        Employee currentEmp = operatorInit(operate, objList[0], getCurrentEmp(), new Position(
                getCurrentPosNo()));

        Date lastChangeTime = new Date();
        for (Object obj : objList) {
            if (obj instanceof Leaverequest) {
                Leaverequest lr = (Leaverequest) obj;
                lr.setLrLastChangeBy(currentEmp);
                lr.setLrLastChangeTime(lastChangeTime);
                if (operate.contains("create")) {
                    lr.setLrCreateBy(currentEmp);
                    lr.setLrCreateTime(lastChangeTime);
                }
            } else if (obj instanceof Overtimerequest) {
                Overtimerequest or = (Overtimerequest) obj;
                or.setOrLastChangeBy(currentEmp);
                or.setOrLastChangeTime(lastChangeTime);
                if (operate.contains("create")) {
                    or.setOrCreateBy(currentEmp);
                    or.setOrCreateTime(lastChangeTime);
                }

            }

            Workflow wf = new Workflow(operate, comment, obj);

            if (!Workflow.wfRelationCheck(wf)) {
                addErrorInfo(msgNoCharge, new Object[] { wf.getApplier().getEmpName(),
                        wf.getDocDesc() });
                return false;
            }

            if (!Workflow.wfStatusCheck(wf)) {
                addErrorInfo(msgStatusErr, new Object[] { wf.getDocNo(), wf.getDocDesc() });
                return false;
            }
        }
        return true;
    }

    public String wfObjOperate(Object[] objList) {
        if (objList.length == 0)
            return "input";

        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        Map msgMap = lr_BO.saveOrUpdateExaminData(objList);
        msgMapToInfo(msgMap);
        return "success";
    }

    private Employee operatorInit(String operate, Object obj, Employee currentEmp, Position pos) {
        currentEmp.setPosition(pos);

        String flowType = null;
        if (obj instanceof Leaverequest)
            flowType = "leaverequest";
        else if (obj instanceof Overtimerequest) {
            flowType = "overtimerequest";
        }

        if (("hr-create".equals(operate)) || ("hr-update".equals(operate))
                || (operate.contains("mgr-"))) {
            IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
            String[] empInCharge = posBo.getAllSubEmpIds(0, pos.getId());
            currentEmp.setEmpInCharge(empInCharge);

            IWorkFlowBO workflowBo = (IWorkFlowBO) SpringBeanFactory.getBean("workFlowBO");
            WorkFlowApprover wfapprover = workflowBo.getWfApproverById(currentEmp.getPosition()
                    .getId(), flowType);
            if (wfapprover != null)
                currentEmp.setHasApproveAuth(true);
            else
                currentEmp.setHasApproveAuth(false);
        }
        return currentEmp;
    }

    private void msgMapToInfo(Map<String, StringBuffer> msgMap) {
        Iterator iterator = msgMap.keySet().iterator();
        while (iterator.hasNext()) {
            String msg = (String) iterator.next();
            String var = ((StringBuffer) msgMap.get(msg)).toString();
            if (msg.contains("操作失败"))
                addErrorInfo(msg, new Object[] { var });
            else
                addSuccessInfo(msg, new Object[] { var });
        }
    }

    public int getOrTiaoxiuExpire(Leavetype lt, Employee emp) {
        String yearMonth = DateUtil.formatDateToS(new Date(), "yyyyMM").substring(0, 4) + "01";
        InterpreterExecuteContext context = new InterpreterExecuteContext(yearMonth);
        Leavebalance lb = null;
        try {
            lb = context.execute(lt, emp);
        } catch (InterpreterException e) {
            e.printStackTrace();
        }
        return lb.getLbOtherDays().intValue();
    }

    public String DWRwfObjOpSingle(String operate, String comment, String employeeId, String objId,
            String securityNo, String flowType) {
        String msgNoParam = "NOPARAM:{0}参数错误＄1�7";
        String msgNoComment = "NOCOMMENT:拒绝{0}必须填写备注＄1�7";
        String msgSecNoErr = "SECNOERR:验证码错误！请检查是否已经执行过该操作！";
        String msgStatusErr = "STATUSERR:{0}状�1�7�错误！请检查是否已经执行过该操作！";
        String msgNoCharge = "NOAUTH:您无权处理{0}的{1}＄1�7";
        String msgFail = "FAIL:{0}";
        String msgSUCC = "SUCC:{0}";

        if ((StringUtils.isEmpty(operate))
                || ((!"approve".equals(operate)) && (!"reject".equals(operate)))) {
            return StringUtil.message(msgNoParam, new Object[] { "操作砄1�7" });
        }

        if (StringUtils.isEmpty(employeeId)) {
            return StringUtil.message(msgNoParam, new Object[] { "操作耄1�7" });
        }

        if ((StringUtils.isEmpty(objId)) || (StringUtils.isEmpty(securityNo))) {
            return StringUtil.message(msgNoParam, new Object[] { "请假卄1�7" });
        }

        if ((StringUtils.isEmpty(objId)) || (StringUtils.isEmpty(securityNo))) {
            return StringUtil.message(msgNoParam, new Object[] { "验证砄1�7" });
        }

        if (("reject".equals(operate)) && (StringUtils.isEmpty(comment))) {
            return StringUtil.message(msgNoComment, new Object[] { "请假卄1�7" });
        }

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Employee currentEmp = posBo.getEmpWithPos(employeeId, new String[0]);
        if (currentEmp == null) {
            return StringUtil.message(msgNoParam, new Object[] { "操作耄1�7" });
        }

        Object obj = null;
        String nextApprover = null;
        if ("leaverequest".equals(flowType)) {
            ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
            Leaverequest lr = lr_BO.loadLeaverequest(objId);
            if (lr == null)
                return StringUtil.message(msgNoParam, new Object[] { "请假卄1�7" });
            if (!lr.getLrSecurityNo().equals(securityNo))
                return StringUtil.message(msgSecNoErr, new Object[0]);

            lr.setLrLastChangeBy(currentEmp);
            lr.setLrLastChangeTime(new Date());

            obj = lr;
            nextApprover = lr.getLrNextApprover();

            if (!Status.MGR_UPD_SET.contains(lr.getLrStatus()))
                return StringUtil.message(msgStatusErr, new Object[] { "请假卄1�7" });
        } else if ("overtimerequest".equals(flowType)) {
            IOvertimerequestBo or_Bo = (IOvertimerequestBo) getBean("overtimerequestBO");
            Overtimerequest or = or_Bo.loadOvertimerequest(objId);
            if (or == null)
                return StringUtil.message(msgNoParam, new Object[] { "加班卄1�7" });
            if (!or.getOrSecurityNo().equals(securityNo))
                return StringUtil.message(msgSecNoErr, new Object[0]);

            or.setOrLastChangeBy(currentEmp);
            or.setOrLastChangeTime(new Date());

            obj = or;
            nextApprover = or.getOrNextApprover();

            if (!Status.MGR_UPD_SET.contains(or.getOrStatus())) {
                return StringUtil.message(msgStatusErr, new Object[] { "加班卄1�7" });
            }
        }

        if (nextApprover == null) {
            operate = "hr-" + operate;
        } else {
            UserBo userBo = (UserBo) getBean("userService");
            Userinfo userinfo = userBo.getUserById(employeeId);
            if (userBo.checkAuthModule(userinfo, "411", "3"))
                operate = "gm-" + operate;
            else {
                operate = "mgr-" + operate;
            }

        }

        currentEmp = operatorInit(operate, obj, currentEmp, currentEmp.getPosition());

        Workflow wf = new Workflow(operate, comment, obj);

        if (!Workflow.wfRelationCheck(wf)) {
            return StringUtil.message(msgNoCharge, new Object[] { wf.getApplier().getEmpName(),
                    wf.getDocDesc() });
        }

        ILeaverequestBO lr_BO = (ILeaverequestBO) getBean("leaverequestBO");
        Map msgMap = lr_BO.saveOrUpdateExaminData(new Object[] { obj });

        Iterator iterator = msgMap.keySet().iterator();
        String failMsg = "";
        String succMsg = "";
        while (iterator.hasNext()) {
            String msg = (String) iterator.next();
            String var = ((StringBuffer) msgMap.get(msg)).toString();
            if (msg.contains("操作失败"))
                failMsg = failMsg + var;
            else {
                succMsg = succMsg + var;
            }
        }
        if (failMsg.length() > 0)
            return StringUtil.message(msgFail, new Object[] { failMsg });
        return StringUtil.message(msgSUCC, new Object[] { succMsg });
    }

    protected BigDecimal zeroIfNull(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return new BigDecimal(0);
        }
        return bigDecimal;
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public List<Overtimetype> getOtTypeList() {
        return this.otTypeList;
    }

    public void setOtTypeList(List<Overtimetype> otTypeList) {
        this.otTypeList = otTypeList;
    }

    public List<Leavetype> getLrTypeList() {
        return this.lrTypeList;
    }

    public void setLrTypeList(List<Leavetype> lrTypeList) {
        this.lrTypeList = lrTypeList;
    }

    public List<Statusconf> getLrStatusList() {
        return this.lrStatusList;
    }

    public void setLrStatusList(List<Statusconf> lrStatusList) {
        this.lrStatusList = lrStatusList;
    }

    public Map<String, String> getStatusMap() {
        return this.statusMap;
    }

    public void setStatusMap(Map<String, String> statusMap) {
        this.statusMap = statusMap;
    }

    public List<Statusconf> getOtStatusList() {
        return this.otStatusList;
    }

    public void setOtStatusList(List<Statusconf> otStatusList) {
        this.otStatusList = otStatusList;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getLeaveType() {
        return this.leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public List<Statusconf> getStatusList() {
        return this.statusList;
    }

    public void setStatusList(List<Statusconf> statusList) {
        this.statusList = statusList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.EmpExaminAction JD-Core Version: 0.5.4
 */