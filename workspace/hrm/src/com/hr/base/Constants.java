package com.hr.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract interface Constants {
    public static final String URL_COMP_DECODE = "UTF-8";
    public static final String PROP_DECODE = "GBK";
    public static final String CURR_OPER_NO = "curr_oper_no";
    public static final String CURR_OPER_NAME = "curr_oper_name";
    public static final String CURR_OPER_POS = "positionId";
    public static final String USER_INFO = "userinfo";
    public static final String USER_AUTHORITYS = "authList";
    public static final String CURR_OPER_PROFILE_ID = "curr_oper_proifle_id";
    public static final String IS_CREATE = "create";
    public static final String IS_UPDATE = "update";
    public static final String IS_DELETE = "delete";
    public static final int CONDITION_ALL = 3;
    public static final int CONDITION_DEPT = 2;
    public static final int CONDITION_OWN = 1;
    public static final String CONDITION_D = "DEPT";
    public static final String CONDITION_ADM = "ADM";
    public static final String CONDITION_A = "ALL";
    public static final String CONDITION_S = "SUB";
    public static final String CONDITION_O = "OWN";
    public static final String CONDITION_M = "HRM";
    public static final String CONDITION_H = "HR";
    public static final String CONDITION_HS = "HRSUB";
    public static final String CONDITION_NULL = "";
    public static final String ACTION_SEARCH = "0";
    public static final String ACTION_ADD = "1";
    public static final String ACTION_UPDATE = "2";
    public static final String ACTION_DELETE = "3";
    public static final String ACTION_PRINT = "6";
    public static final String ACTION_EXPORT = "7";
    public static final String ACTION_IMPORT = "8";
    public static final String EMAIL_TITLE = "email_title";
    public static final String EMAIL_CONTENT = "email_content";
    public static final String AUTH_LIMIT = "authLimit";
    public static final String USER_LIMIT = "USER";
    public static final String USER_LIMIT_ADM = "USERADM";
    public static final String USER_LIMIT_MGR = "USERMGR";
    public static final String USER_LIMIT_EMP = "USEREMP";
    public static final String EMP_LIMIT = "EMP";
    public static final String DATE_LIMIT = "dateLimit";
    public static final String COUNT_LIMIT = "countLimit";
    public static final String DEMO_AUTH_LIMIT = "1,2,3,4,5,11,12,13,14,15,31,32,33,34,35,61,71,72,73,81,82,83,84,85,86,87,88,89,91";
    public static final String DEMO_EMP_NUMBER = "100";
    public static final String DEMO_USER_TOTAL = "5";
    public static final String DEMO_USER_HR = "2";
    public static final String DEMO_USER_MGR = "2";
    public static final String DEMO_USER_EMP = "1";
    public static final String DEMO_DATE_LIMIT = "";
    public static final String DEMO_COUNT_LIMIT = "";
    public static final String MUT_DEMO = "EMP:100#USER:25#AUTH:1,2,3,4,5,11,12,13,14,15,16,71,72,73,81,82,85,86";
    public static final String MUT_DEMO_ROLE = "5";
    public static final String MUT_DEMO_AUTH = "2,4,12,14,16,22,24,32,34,42,44,52,54,61,62,63,64,65,71,81,82,83,84,85,86,87,88,89,91,92";
    public static final String MUT_AL_ROLE = "1";
    public static final String MUT_AL_AUTH = "3,16,23,33,72";
    public static final Set<Integer> ADM_AUTHS = new HashSet(Arrays.asList(new Integer[] {
            Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(11), Integer.valueOf(12),
            Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(21), Integer.valueOf(22),
            Integer.valueOf(31), Integer.valueOf(32), Integer.valueOf(38), Integer.valueOf(41),
            Integer.valueOf(42), Integer.valueOf(51), Integer.valueOf(52), Integer.valueOf(61),
            Integer.valueOf(62), Integer.valueOf(63), Integer.valueOf(71), Integer.valueOf(81),
            Integer.valueOf(82), Integer.valueOf(83), Integer.valueOf(84), Integer.valueOf(85),
            Integer.valueOf(86), Integer.valueOf(87), Integer.valueOf(88), Integer.valueOf(89),
            Integer.valueOf(91), Integer.valueOf(92) }));

    public static final Set<Integer> MGR_AUTHS = new HashSet(Arrays.asList(new Integer[] {
            Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(14), Integer.valueOf(15),
            Integer.valueOf(24), Integer.valueOf(25), Integer.valueOf(34), Integer.valueOf(35),
            Integer.valueOf(44), Integer.valueOf(45), Integer.valueOf(54), Integer.valueOf(55) }));
    public static final int EMPSALARY_ACCT_LIMIT = 48;
    public static final int DEPT_MAX_HEADS = 10;
    public static final int AOD_INITIAL_STATUS = 0;
    public static final int AOD_ADD_STATUS = 1;
    public static final int OWN_EXCLUDE = 0;
    public static final int OWN_INCLUDE = 1;
    public static final String SUB_EXCLUDE = "0";
    public static final String SUB_INCLUDE = "1";
    public static final String INA_EXCLUDE = "0";
    public static final String INA_INCLUDE = "1";
    public static final int ORG_NODE_GLOBAL = 0;
    public static final int ORG_NODE_BRANCH = 1;
    public static final int ORG_NODE_DEPT = 2;
    public static final int ORG_NODE_PB = 3;
    public static final int ORG_NODE_PB_VAC = 4;
    public static final int ORG_NODE_POS_M = 5;
    public static final int ORG_NODE_POS_F = 6;
    public static final int ORG_NODE_POS_VAC = 7;
    public static final int MOD_PROFILE = 1;
    public static final int MOD_COMPENSATION = 2;
    public static final int MOD_ATTENDANCE = 4;
    public static final int MOD_TRAINING = 3;
    public static final int MOD_EVALUATION = 5;
    public static final int MOD_RECRUITMENT = 6;
    public static final int MOD_REPORT = 7;
    public static final int MOD_INFORMATION = 8;
    public static final int PB_NO_MAXEXCEED = 0;
    public static final int PB_CANNOT_EXCEED = 1;
    public static final int PB_CAN_EXCEED = 2;
    public static final int PB_IN_CHARGE = 1;
    public static final int PB_NOT_IN_CHARGE = 0;
    public static final Integer[] DEFAULT_USER_AUTHORITY = { Integer.valueOf(1) };
    public static final int MAX_SQL_RECORDS_IN = 500;
    public static final String WF_TYPE_LEAVE = "leaverequest";
    public static final String WF_TYPE_OVERTIME = "overtimerequest";
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.base.Constants JD-Core Version: 0.5.4
 */