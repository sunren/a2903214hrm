package com.hr.base.wf;

import com.hr.base.Status;
import com.hr.configuration.bo.IEmailtemplateBO;
import com.hr.configuration.bo.TemplateService;
import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.examin.action.beans.OrgBean;
import com.hr.examin.bo.ExaminBoImpl;
import com.hr.examin.bo.interfaces.IWorkFlowBO;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.ObjectUtil;
import com.hr.util.SysConfigManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Workflow {
    private static final Log log = LogFactory.getLog(ExaminBoImpl.class);
    private static final long serialVersionUID = 1L;
    private SysConfigManager dbConfigManager = DatabaseSysConfigManager.getInstance();
    private Integer currStatus;
    private String currApprover;
    private String flowType;
    private Integer status;
    private String nextApprover;
    private String nextApproverDesc;
    private Integer docNo;
    private String docDesc;
    private BigDecimal amount;
    private Employee applier;
    private Employee creator;
    private Employee operator;
    private List<Emailsend> sendList;
    private String operate;
    private String comment;
    private Date opTime;
    private Object flowObj;

    public Workflow(String operate, String comment, Object obj) {
        this.operate = operate;
        this.comment = comment;

        if (obj instanceof Leaverequest)
            WorkflowInit(operate, comment, (Leaverequest) obj);
        else if (obj instanceof Overtimerequest)
            WorkflowInit(operate, comment, (Overtimerequest) obj);
    }

    private void WorkflowInit(String operate, String comment, Leaverequest lr) {
        this.currStatus = lr.getLrStatus();
        this.currApprover = lr.getLrNextApprover();

        this.flowType = "leaverequest";

        this.docNo = lr.getLrNo();
        this.docDesc = "请假单";
        this.applier = lr.getLrEmpNo();
        this.creator = lr.getLrCreateBy();
        this.amount = lr.getLrTotalDays();

        this.operator = lr.getLrLastChangeBy();
        this.opTime = lr.getLrLastChangeTime();

        this.operate = operate;
        this.comment = comment;

        this.flowObj = lr;
        lr.setWorkflow(this);
    }

    private void WorkflowInit(String operate, String comment, Overtimerequest or) {
        this.currStatus = or.getOrStatus();
        this.currApprover = or.getOrNextApprover();

        this.flowType = "overtimerequest";

        this.docNo = or.getOrNo();
        this.docDesc = "加班单";
        this.applier = or.getOrEmpNo();
        this.creator = or.getOrCreateBy();
        this.amount = or.getOrTotalHours();

        this.operator = or.getOrLastChangeBy();
        this.opTime = or.getOrLastChangeTime();

        this.operate = operate;
        this.comment = comment;

        this.flowObj = or;
        or.setWorkflow(this);
    }

    public static boolean wfRelationCheck(Workflow wf) {
        Employee operEmp = wf.getOperator();
        Employee emp = wf.getApplier();
        String operate = wf.getOperate();

        if ((!"hr-create".equals(operate)) && (!"hr-update".equals(operate))
                && (!operate.contains("mgr-"))) {
            return true;
        }

        boolean canApprove = false;
        if ((ObjectUtil.contains(operEmp.getEmpInCharge(), emp.getId()))
                && (operEmp.isHasApproveAuth())) {
            canApprove = true;
        }

        if (((("mgr-approve".equals(operate)) || ("mgr-reject".equals(operate)) || ("mgr-modify"
                .equals(operate))))
                && (!canApprove)) {
            return false;
        }

        if (("hr-create".equals(operate)) || ("hr-update".equals(operate))) {
            if (canApprove)
                operate = operate.replace("hr-", "mgr-");
            else {
                operate = operate.replace("hr-", "");
            }

        } else if (!canApprove) {
            operate = operate.replace("mgr", "");
        }

        wf.setOperate(operate);
        return true;
    }

    public static boolean wfStatusCheck(Workflow wf) {
        String operate = wf.getOperate();
        Integer status = wf.getCurrStatus();

        if (operate.contains("create")) {
            return true;
        }
        if (operate.contains("update")) {
            if (21 == status.intValue())
                return true;
        } else if ((operate.contains("mgr-")) || (operate.contains("gm-"))) {
            if ((Status.MGR_UPD_SET.contains(status)) && (wf.getCurrApprover() != null))
                return true;
        } else if ((operate.equals("hr-approve")) || (operate.equals("hr-reject"))) {
            if ((Status.HR_UPD_SET.contains(status)) && (wf.getCurrApprover() == null))
                return true;
        } else if (operate.equals("hr-confirm")) {
            if (15 == status.intValue())
                return true;
        } else if ((operate.equals("hr-cancel"))
                && (((15 == status.intValue()) || (16 == status.intValue())))) {
            return true;
        }

        return false;
    }

    public static boolean setWorkflowFields(Workflow wf, Map<String, Emailtemplate> templateMap,
            Map<String, StringBuffer> msgMap) {
        String operate = wf.getOperate();

        String hrEmails = getHREmials();

        if (operate.contains("hr-")) {
            wf.setNextApprover(null);
            wf.setStatus(calcStatus(operate, new Integer[0]));
            setObjFields(wf);

            wf.setSendList(emailNotify(wf, templateMap, hrEmails));
        } else if (operate.contains("gm-")) {
            wf.setNextApprover(null);
            wf.setStatus(calcStatus(operate, new Integer[0]));
            setObjFields(wf);

            if ((operate.equals("gm-reject"))
                    || (operate.equals("gm-approve"))
                    || (operate.equals("gm-modify"))
                    || ((operate.equals("gm-create")) && (!wf.getApplier().getId()
                            .equals(wf.getCreator().getId())))) {
                wf.setSendList(emailNotify(wf, templateMap, hrEmails));
            }

            if ((operate.equals("gm-create")) || (operate.equals("gm-update"))
                    || (operate.equals("gm-approve")) || (operate.equals("gm-modify"))) {
                if ((wf.getSendList() == null) || (wf.getSendList().size() == 0))
                    wf.setSendList(emailNotifyMGROrHR(wf, templateMap, hrEmails));
                else {
                    wf.getSendList().addAll(emailNotifyMGROrHR(wf, templateMap, hrEmails));
                }
            }
        } else if (operate.contains("mgr-")) {
            IWorkFlowBO workFlowBo = (IWorkFlowBO) SpringBeanFactory.getBean("workFlowBO");
            OrgBean orgBean = workFlowBo.getNextApprover(wf);

            if (orgBean == null) {
                msgMap.clear();
                msgAdd(msgMap, wf.getDocNo(), "编号为{0}的" + wf.getDocDesc() + "操作失败，请刷新后重试！");
                return false;
            }

            wf.setNextApprover(orgBean.getNextApprover());
            wf.setNextApproverDesc(orgBean.getNextApproverDesc());
            wf
                    .setStatus(calcStatus(operate, new Integer[] { Integer.valueOf(orgBean
                            .getLevel()) }));
            setObjFields(wf);

            if ((operate.equals("mgr-reject")) || (operate.equals("mgr-create"))
                    || (operate.equals("mgr-approve")) || (operate.equals("mgr-modify"))) {
                wf.setSendList(emailNotify(wf, templateMap, hrEmails));
            }

            if ((operate.equals("mgr-create")) || (operate.equals("mgr-update"))
                    || (operate.equals("mgr-approve")) || (operate.equals("mgr-modify"))) {
                if ((wf.getSendList() == null) || (wf.getSendList().size() == 0))
                    wf.setSendList(emailNotifyMGROrHR(wf, templateMap, hrEmails));
                else {
                    wf.getSendList().addAll(emailNotifyMGROrHR(wf, templateMap, hrEmails));
                }
            }
        } else if ((operate.contains("create")) || (operate.contains("update"))) {
            IWorkFlowBO workFlowBo = (IWorkFlowBO) SpringBeanFactory.getBean("workFlowBO");
            OrgBean orgBean = workFlowBo.getNextApproverOfOwn(wf);

            wf.setNextApprover(orgBean.getNextApprover());
            wf.setNextApproverDesc(orgBean.getNextApproverDesc());
            wf
                    .setStatus(calcStatus(operate, new Integer[] { Integer.valueOf(orgBean
                            .getLevel()) }));
            setObjFields(wf);

            wf.setSendList(emailNotifyMGROrHR(wf, templateMap, hrEmails));

            if ((wf.getApplier() != null)
                    && (!wf.getApplier().getId().equals(wf.getCreator().getId())))
                wf.getSendList().addAll(emailNotify(wf, templateMap, hrEmails));
        } else {
            msgMap.clear();
            msgAdd(msgMap, wf.getDocNo(), "编号为{0}的" + wf.getDocDesc() + "操作失败，请刷新后重试！");
            return false;
        }
        return true;
    }

    public static String getHREmials() {
        UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
        List<Userinfo> userList = userBo.getEmpByAuth("H4");
        String hrEmails = "";
        for (Userinfo user : userList) {
            hrEmails = hrEmails + user.getEmployee().getEmpEmail() + ",";
        }

        return hrEmails;
    }

    public static void msgMapAdd(Workflow wf, Map<String, StringBuffer> msgMap)
    {
      String operate = wf.getOperate();
      Integer docNo = wf.getDocNo();
      String docDesc = wf.getDocDesc();

      if (operate.contains("hr-")) {
        if (operate.equals("hr-approve"))
          msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "备案完毕。");
        else if (operate.equals("hr-reject"))
          msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "已被拒绝。");
        else if (operate.equals("hr-confirm"))
          msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "调整完毕。");
        else if (operate.equals("hr-cancel"))
          msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "已被作废。");
      }
      else if (operate.contains("gm-")) {
        if (operate.contains("reject"))
          msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "已被拒绝。");
        else msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "已报送HR审批。");
      }
      else if (operate.contains("mgr-")) {
        if (operate.contains("reject"))
          msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "已被拒绝。");
        else msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "已报送" + wf.getNextApproverDesc() + "审批。");
      }
      else if (operate.contains("create")) {
        msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "创建成功，并报送" + wf.getNextApproverDesc() + "审批。");
      }
      else if (operate.contains("update")) {
        msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "重新提交成功，并报送" + wf.getNextApproverDesc() + "审批。");
      }
      else
        msgAdd(msgMap, docNo, "编号为{0}的" + docDesc + "操作失败，请刷新后重试！");
    }


    private static Integer calcStatus(String operate, Integer[] level) {
        if (operate.contains("reject"))
            return Integer.valueOf(21);
        if ((level.length > 0) && (level[0] != null))
            return level[0];

        if (operate.equals("hr-approve"))
            return Integer.valueOf(15);
        if (operate.equals("hr-confirm"))
            return Integer.valueOf(16);
        if (operate.equals("hr-cancel"))
            return Integer.valueOf(22);
        if (operate.contains("gm-"))
            return Integer.valueOf(14);

        return null;
    }

    private static void setObjFields(Workflow wf) {
        String operate = wf.getOperate();
        Object obj = wf.getFlowObj();

        if (obj instanceof Leaverequest) {
            Leaverequest lr = (Leaverequest) obj;
            lr.setLrStatus(wf.getStatus());
            lr.setLrNextApprover(wf.getNextApprover());
            lr.setLrSecurityNo(UUID.randomUUID().toString());

            if (operate.contains("create")) {
                lr.setLrNo(wf.getDocNo());
                lr.setLrId(UUID.randomUUID().toString());
                lr.setLrEmpDept(lr.getLrEmpNo().getEmpDeptNo());
                lr.setLrEmpLocationNo(lr.getLrEmpNo().getEmpLocationNo());
            }

        } else if (obj instanceof Overtimerequest) {
            Overtimerequest or = (Overtimerequest) obj;
            or.setOrStatus(wf.getStatus());
            or.setOrNextApprover(wf.getNextApprover());
            or.setOrSecurityNo(UUID.randomUUID().toString());

            if (!operate.contains("create"))
                return;
            or.setOrNo(wf.getDocNo());
            or.setOrId(UUID.randomUUID().toString());
            or.setOrEmpDept(or.getOrEmpNo().getEmpDeptNo());
            or.setOrEmpLocationNo(or.getOrEmpNo().getEmpLocationNo());
        }
    }

    public static List<Emailsend> emailNotify(Workflow wf, Map<String, Emailtemplate> templateMap,
            String hrEmails) {
        List esList = new ArrayList();
        Emailsend emailsend = new Emailsend();

        String etTitleNo = getTemplateByNotify(wf);
        Emailtemplate template = (Emailtemplate) templateMap.get(etTitleNo);

        if (template == null) {
            log.warn("模板" + etTitleNo + "不存在或已停用！");
            return esList;
        }

        emailsend.setEsStatus(getEsStatusByTemplate(template.getEtStatus()));

        if (!wf.getApplier().getId().equals(wf.getCreator().getId())) {
            if (StringUtils.isNotEmpty(wf.getApplier().getEmpEmail())) {
                emailsend.setEsTo(wf.getApplier().getEmpEmail());
                emailsend.setEsCc(wf.getCreator().getEmpEmail());
            } else if ((StringUtils.isEmpty(wf.getApplier().getEmpEmail()))
                    && (StringUtils.isEmpty(wf.getCreator().getEmpEmail()))) {
                emailsend.setEsTo(wf.getCreator().getEmpEmail());
            }

        } else if (StringUtils.isNotEmpty(wf.getApplier().getEmpEmail())) {
            emailsend.setEsTo(wf.getApplier().getEmpEmail());
        }

        initMailParams(emailsend, wf, etTitleNo, null, null);

        esList.add(emailsend);
        return esList;
    }

    public static List<Emailsend> emailNotifyMGROrHR(Workflow wf,
            Map<String, Emailtemplate> templateMap, String hrEmails) {
        String etTitleNo = getTemplateByOperate(wf);
        Emailtemplate template = (Emailtemplate) templateMap.get(etTitleNo);

        if (template == null) {
            log.warn("模板" + etTitleNo + "不存在或已停用！");
            return null;
        }
        Integer esStatus = getEsStatusByTemplate(template.getEtStatus());

        String actionName = getApproveActionByOperate(wf);

        List esList = new ArrayList();
        if (wf.getNextApprover() == null) {
            List<Userinfo> userList = null;
            UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
            userList = userBo.getEmpByAuth("H4");

            for (Userinfo manager : userList) {
                Emailsend emailsend = new Emailsend();
                emailsend.setEsStatus(esStatus);
                emailsend.setEsTo(manager.getEmployee().getEmpEmail());
                initMailParams(emailsend, wf, etTitleNo, manager.getEmployee(), actionName);
                esList.add(emailsend);
            }

            return esList;
        }

        IPositionBo positionBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position position = positionBo.getPosById(wf.getNextApprover(),
                                                  new String[] { Position.PROP_POSITION_EMP_NO });
        Employee nextApprover = position.getPositionEmpNo();
        if (nextApprover != null) {
            Emailsend emailsend = new Emailsend();
            emailsend.setEsStatus(esStatus);

            if (StringUtils.isNotEmpty(nextApprover.getEmpEmail())) {
                emailsend.setEsTo(nextApprover.getEmpEmail());
            }

            initMailParams(emailsend, wf, etTitleNo, nextApprover, actionName);
            esList.add(emailsend);
        }
        return esList;
    }

    public static Integer getEsStatusByTemplate(Integer etStatus) {
        if (etStatus.intValue() == 0) {
            return null;
        }
        if (etStatus.intValue() == 1) {
            return new Integer(1);
        }
        if (etStatus.intValue() == 2) {
            return new Integer(0);
        }
        return null;
    }

    public static String getTemplateByNotify(Workflow wf) {
        String operate = wf.getOperate();
        Object data = wf.getFlowObj();
        String etTitleNo = null;

        if ((operate.contains("create")) || (operate.contains("update"))) {
            if (data instanceof Leaverequest)
                etTitleNo = "LeaveSubmitNotify";
            else if (data instanceof Overtimerequest)
                etTitleNo = "OTSubmitNotify";
        }
        if ((operate.contains("mgr-approve")) || (operate.contains("mgr-modify"))
                || (operate.contains("gm-approve")) || (operate.contains("gm-modify"))) {
            if (data instanceof Leaverequest)
                etTitleNo = "LeaveApproveNotify";
            else if (data instanceof Overtimerequest)
                etTitleNo = "OTApproveNotify";
        } else if (operate.equals("hr-approve")) {
            if (data instanceof Leaverequest)
                etTitleNo = "LeaveBackup";
            else if (data instanceof Overtimerequest)
                etTitleNo = "OTBackup";
        } else if (operate.equals("hr-confirm")) {
            if (data instanceof Leaverequest)
                etTitleNo = "LeaveResumption";
            else if (data instanceof Overtimerequest)
                etTitleNo = "OTConfirm";
        } else if (operate.equals("hr-cancel")) {
            if (data instanceof Leaverequest)
                etTitleNo = "LeaveCancel";
            else if (data instanceof Overtimerequest)
                etTitleNo = "OTCancel";
        } else if (operate.contains("reject")) {
            if (data instanceof Leaverequest)
                etTitleNo = "LeaveReject";
            else if (data instanceof Overtimerequest)
                etTitleNo = "OTReject";
        }

        return etTitleNo;
    }

    public static String getTemplateByOperate(Workflow wf) {
        String operate = wf.getOperate();
        Object data = wf.getFlowObj();
        String etTitleNo = null;

        if ((operate.contains("create")) || (operate.contains("update"))) {
            if (data instanceof Leaverequest)
                etTitleNo = "LeaveSubmit";
            else if (data instanceof Overtimerequest)
                etTitleNo = "OTSubmit";
        } else if ((operate.contains("mgr-approve")) || (operate.contains("mgr-modify"))
                || (operate.contains("gm-approve")) || (operate.contains("gm-modify"))) {
            if (data instanceof Leaverequest)
                etTitleNo = "LeaveApprove";
            else if (data instanceof Overtimerequest) {
                etTitleNo = "OTApprove";
            }
        }

        return etTitleNo;
    }

    public static String getApproveActionByOperate(Workflow wf) {
        Object data = wf.getFlowObj();
        String actionName = null;

        if (wf.getNextApprover() == null) {
            if (data instanceof Leaverequest)
                actionName = "emailHrLeaveApproveOrReject";
            else if (data instanceof Overtimerequest) {
                actionName = "emailHrOtApproveOrReject";
            }
        } else if (data instanceof Leaverequest)
            actionName = "emailDeptApproveOrReject";
        else if (data instanceof Overtimerequest) {
            actionName = "emailDeptOtApproveOrReject";
        }

        return actionName;
    }

    public static void initMailParams(Emailsend emailsend, Workflow wf, String templateNo,
            Employee nextApprover, String actionName) {
        Map params = new HashMap();
        String urlApprove = null;
        params.put("SENDER", wf.getOperator());
        params.put("APPLIER", wf.getApplier());
        if (wf.getFlowObj() instanceof Leaverequest) {
            Leaverequest lr = (Leaverequest) wf.getFlowObj();
            params.put("LEAVE", lr);
            params.put("URL", "examin/myExamins.action?tab=1");
            if (nextApprover != null)
                urlApprove = getConfirmUrl(nextApprover.getId(), lr.getLrId(),
                                           lr.getLrSecurityNo(), "leaverequest");
        } else if (wf.getFlowObj() instanceof Overtimerequest) {
            Overtimerequest or = (Overtimerequest) wf.getFlowObj();
            params.put("OR", or);
            params.put("URL", "examin/myExamins.action?tab=2");
            if (nextApprover != null)
                urlApprove = getConfirmUrl(nextApprover.getId(), or.getOrId(),
                                           or.getOrSecurityNo(), "overtimerequest");
        }
        params.put("MANAGER", nextApprover);
        params.put("APPROVEURL", urlApprove);

        TemplateService emailTemplateService = (TemplateService) SpringBeanFactory
                .getBean("emailTemplateService");
        String content = emailTemplateService.getContent(templateNo + "_body", params);
        String title = emailTemplateService.getContent(templateNo + "_title", params);
        emailsend.setEsTitle(title);
        emailsend.setEsContent(content);
    }

    public static String getConfirmUrl(String operatorId, String recordId, String securityNo,
            String flowType) {
        StringBuffer urlBuffer = new StringBuffer("emailApprove");
        urlBuffer.append("?").append("objId=").append(recordId);
        urlBuffer.append("&").append("employeeId=").append(operatorId);
        urlBuffer.append("&").append("securityNo=").append(securityNo);
        urlBuffer.append("&").append("flowType=").append(flowType);
        return urlBuffer.toString();
    }

    private static void msgAdd(Map<String, StringBuffer> msgMap, Integer docNo, String key) {
        StringBuffer msgValue = (StringBuffer) msgMap.get(key);
        if (msgValue == null) {
            msgValue = new StringBuffer(docNo.toString());
            msgMap.put(key, msgValue);
        } else {
            msgValue.append(", ").append(docNo);
            msgMap.put(key, msgValue);
        }
    }

    private Map<String, Emailtemplate> getTemplateMap() {
        IEmailtemplateBO templateBo = (IEmailtemplateBO) SpringBeanFactory
                .getBean("emailtemplateBO");
        Map rs = new HashMap();
        List<Emailtemplate> templateList = templateBo.getList();
        for (Emailtemplate tmp : templateList) {
            if (tmp.getEtStatus().intValue() == 1)
                rs.put(tmp.getEtTitleNo(), tmp);
        }
        return rs;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Employee getApplier() {
        return this.applier;
    }

    public void setApplier(Employee applier) {
        this.applier = applier;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Employee getCreator() {
        return this.creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }

    public String getCurrApprover() {
        return this.currApprover;
    }

    public void setCurrApprover(String currApprover) {
        this.currApprover = currApprover;
    }

    public Integer getCurrStatus() {
        return this.currStatus;
    }

    public void setCurrStatus(Integer currStatus) {
        this.currStatus = currStatus;
    }

    public String getDocDesc() {
        return this.docDesc;
    }

    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc;
    }

    public Integer getDocNo() {
        return this.docNo;
    }

    public void setDocNo(Integer docNo) {
        this.docNo = docNo;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNextApprover() {
        return this.nextApprover;
    }

    public void setNextApprover(String nextApprover) {
        this.nextApprover = nextApprover;
    }

    public List<Emailsend> getSendList() {
        return this.sendList;
    }

    public void setSendList(List<Emailsend> sendList) {
        this.sendList = sendList;
    }

    public String getOperate() {
        return this.operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public Employee getOperator() {
        return this.operator;
    }

    public void setOperator(Employee operator) {
        this.operator = operator;
    }

    public Object getFlowObj() {
        return this.flowObj;
    }

    public void setFlowObj(Object flowObj) {
        this.flowObj = flowObj;
    }

    public Date getOpTime() {
        return this.opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getNextApproverDesc() {
        return this.nextApproverDesc;
    }

    public void setNextApproverDesc(String nextApproverDesc) {
        this.nextApproverDesc = nextApproverDesc;
    }

    public String getFlowType() {
        return this.flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.base.wf.Workflow JD-Core Version: 0.5.4
 */