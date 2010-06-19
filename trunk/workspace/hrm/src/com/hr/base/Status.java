package com.hr.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract interface Status {
    public static final int ES_STATUS_CREATE = 0;
    public static final int ES_STATUS_SEND = 1;
    public static final int ES_STATUS_ERROR = 2;
    public static final int ECP_STATUS_DRAFT = 1;
    public static final int ECP_STATUS_HR_REJECT = 2;
    public static final String ECP_STATUS_HR_REJECT_LOG = "HR拒绝";
    public static final int ECP_STATUS_SUBMIT = 11;
    public static final String ECP_STATUS_SUBMIT_LOG = "提交计划";
    public static final String ECP_STATUS_RE_SUBMIT_LOG = "重新提交";
    public static final int ECP_STATUS_DEPT_APPROVE = 21;
    public static final String ECP_STATUS_DEPT_APPROVE_LOG = "部门批准";
    public static final int ECP_STATUS_GM_APPROVE = 22;
    public static final String ECP_STATUS_GM_APPROVE_LOG = "总经理批凄1�7";
    public static final int ECP_STATUS_HR_APPROVE = 31;
    public static final String ECP_STATUS_HR_APPROVE_LOG = "HR备案";
    public static final int ECP_STATUS_ADJUSTED = 41;
    public static final String ECP_STATUS_QUICK_ADJUSTED_LOG = "快�1�7�调敄1�7";
    public static final String ECP_STATUS_MAN_ADJUSTED_LOG = "手工调整";
    public static final String ECP_STATUS_SYS_ADJUSTED_LOG = "系统调整";
    public static final int ECP_STATUS_CANCELLED = 42;
    public static final String ECP_STATUS_CANCELLED_LOG = "作废";
    public static final int EMP_STATUS_QUIT = 0;
    public static final String EMP_STATUS_QUIT_LOG = "离职";
    public static final int EMP_STATUS_EMPLOYMENT = 1;
    public static final String EMP_STATUS_EMPLOYMENT_LOG = "在职";
    public static final Integer[] IN_PROCESS = { Integer.valueOf(2), Integer.valueOf(5),
            Integer.valueOf(14) };
    public static final Integer[] IN_MGR_PROCESS = { Integer.valueOf(2), Integer.valueOf(5) };
    public static final Integer[] PROCESSED = { Integer.valueOf(15), Integer.valueOf(16) };
    public static final Integer[] PROCESS_VALID = { Integer.valueOf(2), Integer.valueOf(5),
            Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16) };
    public static final Integer[] PROCESS_ERROR = { Integer.valueOf(21), Integer.valueOf(22) };
    public static final Integer[] PROCESS_MAP = { Integer.valueOf(2), Integer.valueOf(5),
            Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(21),
            Integer.valueOf(22) };

    public static final Set<Integer> MGR_UPD_SET = new HashSet(Arrays.asList(new Integer[] {
            Integer.valueOf(2), Integer.valueOf(5) }));

    public static final Set<Integer> HR_UPD_SET = new HashSet(Arrays.asList(new Integer[] {
            Integer.valueOf(5), Integer.valueOf(14) }));
    public static final int LR_STATUS_SUBMIT = 2;
    public static final String LR_STATUS_SUBMIT_LOG = "提交申请";
    public static final String LR_STATUS_RE_SUBMIT_LOG = "重新提交";
    public static final int LR_STATUS_IN_PROCESS = 5;
    public static final String LR_STATUS_IN_PROCESS_LOG = "正在审批";
    public static final int LR_STATUS_GM_APPROVE = 14;
    public static final String LR_STATUS_GM_APPROVE_LOG = "总经理批凄1�7";
    public static final int LR_STATUS_HR_APPROVE = 15;
    public static final String LR_STATUS_HR_APPROVE_LOG = "HR备案";
    public static final int LR_STATUS_CONFIRM = 16;
    public static final String LR_STATUS_CONFIRM_LOG = "锄1�7偄1�7";
    public static final int LR_STATUS_REJECT = 21;
    public static final String LR_STATUS_DEPT_REJECT_LOG = "部门拒绝";
    public static final String LR_STATUS_HR_REJECT_LOG = "HR拒绝";
    public static final int LR_STATUS_CANCELLED = 22;
    public static final String LR_STATUS_CANCELLED_LOG = "作废";
    public static final int OTR_STATUS_SUBMIT = 2;
    public static final String OTR_STATUS_SUBMIT_LOG = "提交申请";
    public static final String OTR_STATUS_RE_SUBMIT_LOG = "重新提交";
    public static final int OTR_STATUS_IN_PROCESS = 5;
    public static final String OTR_STATUS_IN_PROCESS_LOG = "正在审批";
    public static final int OTR_STATUS_GM_APPROVE = 14;
    public static final String OTR_STATUS_GM_APPROVE_LOG = "总经理批凄1�7";
    public static final int OTR_STATUS_HR_APPROVE = 15;
    public static final String OTR_STATUS_HR_APPROVE_LOG = "HR备案";
    public static final int OTR_STATUS_CONFIRM = 16;
    public static final String OTR_STATUS_CONFIRM_LOG = "加班确认";
    public static final int OTR_STATUS_REJECT = 21;
    public static final String OTR_STATUS_DEPT_REJECT_LOG = "部门拒绝";
    public static final String OTR_STATUS_HR_REJECT_LOG = "HR拒绝";
    public static final int OTR_STATUS_CANCELLED = 22;
    public static final String OTR_STATUS_CANCELLED_LOG = "作废";
    public static final int RA_STATUS_PROPOSE = 1;
    public static final String RA_STATUS_PROPOSE_LOG = "初�1�7��1�7�过";
    public static final int RA_STATUS_PAPERTEST = 2;
    public static final String RA_STATUS_PAPERTEST_LOG = "笔试";
    public static final int RA_STATUS_INTERVIEW = 3;
    public static final String RA_STATUS_INTERVIEW_LOG = "面试";
    public static final int RA_STATUS_PHONE_INTERVIEW = 4;
    public static final String RA_STATUS_PHONE_INTERVIEW_LOG = "电话面试";
    public static final int RA_STATUS_REJECT = 9;
    public static final String RA_STATUS_REJECT_LOG = "不合栄1�7";
    public static final int RA_STATUS_FINAL = 11;
    public static final String RA_STATUS_FINAL_LOG = "待定候�1�7�人";
    public static final int RA_STATUS_OFFER_SEND = 12;
    public static final String RA_STATUS_OFFER_SEND_LOG = "已发录取通知";
    public static final int RA_STATUS_OFFER_ACCEPT = 13;
    public static final String RA_STATUS_OFFER_ACCEPT_LOG = "接受录取通知";
    public static final int RA_STATUS_OFFER_REJECT = 19;
    public static final String RA_STATUS_OFFER_REJECT_LOG = "拒绝录取通知";
    public static final int RA_STATUS_BLACK_LIST = 21;
    public static final String RA_STATUS_BLACK_LIST_LOG = "黑名卄1�7";
    public static final int RP_STATUS_DRAFT = 1;
    public static final int RP_STATUS_SUBMIT = 2;
    public static final String RP_STATUS_SUBMIT_LOG = "提交计划";
    public static final String RP_STATUS_RE_SUBMIT_LOG = "重新提交";
    public static final int RP_STATUS_DEPT_APPROVE = 11;
    public static final String RP_STATUS_DEPT_APPROVE_LOG = "部门批准";
    public static final int RP_STATUS_HR_APPROVE = 12;
    public static final String RP_STATUS_HR_APPROVE_LOG = "HR备案";
    public static final int RP_STATUS_REJECT = 21;
    public static final String RP_STATUS_DEPT_REJECT_LOG = "部门拒绝";
    public static final String RP_STATUS_HR_REJECT_LOG = "HR拒绝";
    public static final int RP_STATUS_CANCELLED = 22;
    public static final String RP_STATUS_CANCELLED_LOG = "作废";
    public static final int RP_STATUS_TERMINATED = 23;
    public static final String RP_STATUS_TERMINATED_LOG = "中止";
    public static final int RP_STATUS_CLOSED = 31;
    public static final String RP_STATUS_CLOSED_LOG = "关闭";
    public static final int TREP_STATUS_SUBMIT = 2;
    public static final String TREP_STATUS_SUBMIT_LOG = "提交申请";
    public static final String TREP_STATUS_RE_SUBMIT_LOG = "重新提交";
    public static final int TREP_STATUS_DEPT_APPROVE = 11;
    public static final String TREP_STATUS_DEPT_APPROVE_LOG = "部门批准";
    public static final int TREP_STATUS_HR_APPROVE = 12;
    public static final String TREP_STATUS_HR_APPROVE_LOG = "HR备案";
    public static final int TREP_STATUS_REJECT = 21;
    public static final String TREP_STATUS_DEPT_REJECT_LOG = "部门拒绝";
    public static final String TREP_STATUS_HR_REJECT_LOG = "HR拒绝";
    public static final int TREP_STATUS_CANCELLED = 22;
    public static final String TREP_STATUS_CANCELLED_LOG = "作废";
    public static final int TREP_STATUS_FEEDBACKED = 31;
    public static final String TREP_STATUS_FEEDBACKED_LOG = "反馈";
    public static final int MAIL_SEND = 0;
    public static final int MAIL_SEND_SAVE = 1;
    public static final int MAIL_SAVE = 2;
    public static final int DEPT_COMPANY = 0;
    public static final int DEPT_BRANCH = 1;
    public static final int DEPT_DEPT = 2;
    public static final int DEPT_POSITION = 3;
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name: com.hr.base.Status
 * JD-Core Version: 0.5.4
 */