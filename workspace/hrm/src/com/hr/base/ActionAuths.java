package com.hr.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActionAuths implements Constants {
    private static HashMap<String, List<UsersAuthority>> actionAuthMap = new HashMap();

    public static List<UsersAuthority> getActionAuths(String actionName, String methodName) {
        return (List) actionAuthMap.get(actionName.trim() + "-" + methodName.trim());
    }

    public static List<UsersAuthority> getActionAuths(String actionName) {
        return (List) actionAuthMap.get(actionName);
    }

    static {
        List userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("101", "2", "ALL"));
        userAuthorityList.add(new UsersAuthority("111", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("101", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("111", "2", "SUB"));
        actionAuthMap.put("searchEmp-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("101", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("101", "1", "HRSUB"));
        actionAuthMap.put("batchQuit-batchQuit", userAuthorityList);
        actionAuthMap.put("batchTransfer-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, ""));
        actionAuthMap.put("delEmp-delEmp", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, "ADM"));
        userAuthorityList.add(new UsersAuthority("111", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("111", "2", "SUB"));
        userAuthorityList.add(new UsersAuthority("111", "1", "OWN"));
        actionAuthMap.put("infoCard-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("101", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("111", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("101", "1", "HRSUB"));
        userAuthorityList.add(new UsersAuthority("111", "2", "SUB"));
        userAuthorityList.add(new UsersAuthority("111", "1", "OWN"));
        actionAuthMap.put("updateEmpInit-updateInit", userAuthorityList);
        actionAuthMap.put("empAdditional-listAll", userAuthorityList);
        actionAuthMap.put("empProfile-execute", userAuthorityList);

        actionAuthMap.put("listContract-list", userAuthorityList);
        actionAuthMap.put("listTransfer-list", userAuthorityList);
        actionAuthMap.put("listEval-list", userAuthorityList);
        actionAuthMap.put("listReward-list", userAuthorityList);
        actionAuthMap.put("listQuit-list", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, "ADM"));
        actionAuthMap.put("empCreateInit-createInit", userAuthorityList);
        actionAuthMap.put("empCreate-createEmp", userAuthorityList);
        actionAuthMap.put("updateEmp-updateEmp", userAuthorityList);
        actionAuthMap.put("updateBasicInfo-execute", userAuthorityList);
        actionAuthMap.put("createProfile-createProfile", userAuthorityList);
        actionAuthMap.put("ajaxSelfInit-selfInit", userAuthorityList);
        actionAuthMap.put("ajaxSelf-updateEmp", userAuthorityList);
        actionAuthMap.put("delProfile-delProfile", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("101", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("101", "1", "SUB"));
        actionAuthMap.put("eduAdd-eduHisAdd", userAuthorityList);
        actionAuthMap.put("eduUpdate-eduHisUpdate", userAuthorityList);
        actionAuthMap.put("deleteEduAttach-attachDelete", userAuthorityList);

        actionAuthMap.put("trainAdd-trainHisAdd", userAuthorityList);
        actionAuthMap.put("trainUpdate-trainHisUpdate", userAuthorityList);
        actionAuthMap.put("deleteTrainAttach-attachDelete", userAuthorityList);

        actionAuthMap.put("relationsAdd-relationsAdd", userAuthorityList);
        actionAuthMap.put("relationsUpdate-relationsUpdate", userAuthorityList);

        actionAuthMap.put("createResumeInit-resume", userAuthorityList);
        actionAuthMap.put("createResume-createProfile", userAuthorityList);
        actionAuthMap.put("resumeUpload-resumeUpload", userAuthorityList);
        actionAuthMap.put("resumeDelete-resumeDelete", userAuthorityList);

        actionAuthMap.put("empeduhisdwr-", userAuthorityList);
        actionAuthMap.put("empjobhisdwr-", userAuthorityList);
        actionAuthMap.put("emptrainhisdwr-", userAuthorityList);
        actionAuthMap.put("emprelationsdwr-", userAuthorityList);

        actionAuthMap.put("empAdditionaldwr-", userAuthorityList);

        actionAuthMap.put("empContractAdd-addContract", userAuthorityList);
        actionAuthMap.put("deleteContract-deleteContract", userAuthorityList);
        actionAuthMap.put("empContractUpdate-updateContract", userAuthorityList);
        actionAuthMap.put("deleteContractAttach-attachDelete", userAuthorityList);

        actionAuthMap.put("empTransferAdd-addTransfer", userAuthorityList);
        actionAuthMap.put("EmpTransfer-deleteTransfer", userAuthorityList);
        actionAuthMap.put("empTransferUpdate-updateTransfer", userAuthorityList);

        actionAuthMap.put("empEvalAdd-addEval", userAuthorityList);
        actionAuthMap.put("EmpEval-deleteEval", userAuthorityList);
        actionAuthMap.put("empEvalUpdate-updateEval", userAuthorityList);
        actionAuthMap.put("deleteEvalAttach-attachDelete", userAuthorityList);

        actionAuthMap.put("empRewardAdd-addReward", userAuthorityList);
        actionAuthMap.put("EmpReward-deleteReward", userAuthorityList);
        actionAuthMap.put("empRewardUpdate-updateReward", userAuthorityList);

        actionAuthMap.put("empQuitAdd-addQuit", userAuthorityList);
        actionAuthMap.put("empQuitDelete-deleteQuit", userAuthorityList);
        actionAuthMap.put("EmpQuit-deleteQuit", userAuthorityList);
        actionAuthMap.put("empQuitUpdate-updateQuit", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, "ADM"));
        actionAuthMap.put("createContractInit-contract", userAuthorityList);
        actionAuthMap.put("createContract-createProfile", userAuthorityList);
        actionAuthMap.put("batchCreate-batchCreate", userAuthorityList);
        actionAuthMap.put("updateManContract-updateContract", userAuthorityList);
        actionAuthMap.put("batchContinuous-batchContinuous", userAuthorityList);
        actionAuthMap.put("batchRepeal-batchRepeal", userAuthorityList);
        actionAuthMap.put("deleteEmpContract-deleteContract", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("101", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("101", "1", "SUB"));
        actionAuthMap.put("manageContract-execute", userAuthorityList);
        actionAuthMap.put("empQuitManagement-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, "ADM"));
        actionAuthMap.put("batchRehabEmp-execute", userAuthorityList);
        actionAuthMap.put("batchDeleteEmp-batchDelete", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, "ADM"));
        actionAuthMap.put("searchProfile-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, ""));
        userAuthorityList.add(new UsersAuthority("111", null, ""));
        actionAuthMap.put("empTreeInit-execute", userAuthorityList);
        actionAuthMap.put("myInfo-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", "3", "ADM"));
        actionAuthMap.put("profileConfig-execute", userAuthorityList);
        actionAuthMap.put("branchConfig-execute", userAuthorityList);
        actionAuthMap.put("departmentConfig-execute", userAuthorityList);
        actionAuthMap.put("unitmanagerConfig-execute", userAuthorityList);
        actionAuthMap.put("groupConfig-execute", userAuthorityList);
        actionAuthMap.put("jobtitleConfig-execute", userAuthorityList);
        actionAuthMap.put("emptypeConfig-execute", userAuthorityList);
        actionAuthMap.put("locationConfig-execute", userAuthorityList);
        actionAuthMap.put("orgHeads-execute", userAuthorityList);
        actionAuthMap.put("branchmanagerConfig-execute", userAuthorityList);
        actionAuthMap.put("departmanagerConfig-execute", userAuthorityList);
        actionAuthMap.put("unitmanagerConfig-execute", userAuthorityList);
        actionAuthMap.put("groupmanagerConfig-execute", userAuthorityList);
        actionAuthMap.put("locationmanagerConfig-execute", userAuthorityList);

        actionAuthMap.put("OrgDeptManage-execute", userAuthorityList);
        actionAuthMap.put("OrgDeptManage-goBranchDeptTabs", userAuthorityList);
        actionAuthMap.put("OrgDeptManage-editBranchDeptInit", userAuthorityList);
        actionAuthMap.put("OrgDeptManage-saveBranchDept", userAuthorityList);
        actionAuthMap.put("OrgDeptManage-goDeptResTab", userAuthorityList);
        actionAuthMap.put("OrgDeptManage-goDeptPBsTab", userAuthorityList);
        actionAuthMap.put("OrgDeptManage-goDeptHistTab", userAuthorityList);

        actionAuthMap.put("OrgMapAction-showOrgMap", userAuthorityList);
        actionAuthMap.put("ShowPbManagePageAction-showPbManagePage", userAuthorityList);
        actionAuthMap.put("pbManageAction-execute", userAuthorityList);
        actionAuthMap.put("pbManageAction-pbBasicInfo", userAuthorityList);
        actionAuthMap.put("pbManageAction-savePbBasicInfo", userAuthorityList);
        actionAuthMap.put("pbManageAction-showPbTable", userAuthorityList);
        actionAuthMap.put("pbManageAction-pbResponse", userAuthorityList);
        actionAuthMap.put("pbManageAction-pbPosPerfcr", userAuthorityList);
        actionAuthMap.put("pbManageAction-pbQualify", userAuthorityList);
        actionAuthMap.put("pbManageAction-showPbHistAction", userAuthorityList);
        actionAuthMap.put("PbManualAction-showPbManual", userAuthorityList);
        actionAuthMap.put("ShowPositionStructureAction-showPositionStructure", userAuthorityList);
        actionAuthMap.put("PositionManageAction-positionInfo", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, "ADM"));
        actionAuthMap.put("empReport-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, "ADM"));
        actionAuthMap.put("employeeReport-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, ""));
        actionAuthMap.put("deleteEmp-delEmp", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, "ALL"));
        userAuthorityList.add(new UsersAuthority("111", null, "SUB"));
        actionAuthMap.put("ajaxMySalary-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("111", null, "OWN"));
        actionAuthMap.put("passwordOwn-own", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("201", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("201", "1", "SUB"));
        actionAuthMap.put("DWRforAcctItemDef-execute", userAuthorityList);
        actionAuthMap.put("searchSalary-execute", userAuthorityList);
        actionAuthMap.put("addSalaryConfigInit-addSalaryConfigInit", userAuthorityList);
        actionAuthMap.put("updateSalaryConfigInit-updateSalaryConfigInit", userAuthorityList);
        actionAuthMap.put("addSalaryConfig-addSalaryConfig", userAuthorityList);
        actionAuthMap.put("updateSalaryConfig-updateSalaryConfig", userAuthorityList);
        actionAuthMap.put("deleteSalaryConfig-deleteSalaryConfig", userAuthorityList);
        actionAuthMap.put("viewdetail-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", "3", "HR"));
        actionAuthMap.put("searchSalaryPaid-approve", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("201", "2", "HR"));
        actionAuthMap.put("searchSalaryPaid-confirmSubmit", userAuthorityList);
        actionAuthMap.put("initPaid-initPaid", userAuthorityList);
        actionAuthMap.put("postsalary-execute", userAuthorityList);
        actionAuthMap.put("viewAllSalarypaid-viewAllSalarypaid", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("201", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("211", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("201", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("211", "2", "SUB"));
        actionAuthMap.put("searchSalaryPaid-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("201", "2", "HR"));
        actionAuthMap.put("searchBatchCompaplan-execute", userAuthorityList);
        actionAuthMap.put("addEmpsalaryadj-addEmpsalaryadj", userAuthorityList);
        actionAuthMap.put("deleteEmpsalaryadj-deleteCompaplan", userAuthorityList);
        actionAuthMap.put("searchCompaplan-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("201", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("201", "1", "SUB"));
        actionAuthMap.put("searchEmpbenefit-execute", userAuthorityList);
        actionAuthMap.put("addBenefitInit-addBenefitInit", userAuthorityList);
        actionAuthMap.put("addBenefit-execute", userAuthorityList);
        actionAuthMap.put("updateBenefitInit-updateBenefitInit", userAuthorityList);
        actionAuthMap.put("updateBenefit-execute", userAuthorityList);
        actionAuthMap.put("delEmpbenefit-delEmpbenefit", userAuthorityList);
        actionAuthMap.put("searchEbp-execute", userAuthorityList);
        actionAuthMap.put("saveBeneAddData-saveBeneAddData", userAuthorityList);
        actionAuthMap.put("deleteOneEbp-deleteEbpById", userAuthorityList);
        actionAuthMap.put("modifyEbp-modifyEbp", userAuthorityList);
        actionAuthMap.put("checkAddData-checkAddData", userAuthorityList);
        actionAuthMap.put("getEbpById-getEbpById", userAuthorityList);
        actionAuthMap.put("getEbpByEmpMonth-getEbpByEmpMonth", userAuthorityList);
        actionAuthMap.put("getEbpByEmpMonths-getEbpByEmpMonths", userAuthorityList);
        actionAuthMap.put("searchBeneHistory-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("231", null, "ALL"));
        actionAuthMap.put("salarypayEmailSend-sendEmail", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("211", null, "OWN"));
        actionAuthMap.put("mySalaryConf-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("201", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("211", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("201", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("211", "2", "SUB"));
        userAuthorityList.add(new UsersAuthority("211", "1", "OWN"));
        actionAuthMap.put("mySalaryPaid-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", "3", "HR"));
        actionAuthMap.put("salaryRpInit-execute", userAuthorityList);
        actionAuthMap.put("salaryRp-execute", userAuthorityList);
        actionAuthMap.put("salaryHistoryReport-execute", userAuthorityList);
        actionAuthMap.put("salaryCostReport-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("201", "2", "HR"));
        actionAuthMap.put("compensationConfig-execute", userAuthorityList);
        actionAuthMap.put("salaryRatingConfig-execute", userAuthorityList);
        actionAuthMap.put("benefitTypeList-execute", userAuthorityList);
        actionAuthMap.put("acctItemDef-execute", userAuthorityList);
        actionAuthMap.put("searchesa-execute", userAuthorityList);

        actionAuthMap.put("addesa-execute", userAuthorityList);
        actionAuthMap.put("addAcctVersion-geneNewVersion", userAuthorityList);
        actionAuthMap.put("delesa-execute", userAuthorityList);
        actionAuthMap.put("updateesainit-execute", userAuthorityList);
        actionAuthMap.put("copyAcct-execute", userAuthorityList);
        actionAuthMap.put("updateesa-execute", userAuthorityList);
        actionAuthMap.put("viewSalaryConfig-viewSalaryConfig", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("411", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("401", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("401", "2", "ALL"));
        userAuthorityList.add(new UsersAuthority("401", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "2", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "1", "OWN"));
        actionAuthMap.put("empListAction-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("411", null, "OWN"));
        actionAuthMap.put("myExamins-execute", userAuthorityList);
        actionAuthMap.put("myLeaveSearch-execute", userAuthorityList);
        actionAuthMap.put("myOvertimeSearch-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("411", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "2", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "1", "OWN"));
        actionAuthMap.put("empLRAddInit-executeInit", userAuthorityList);
        actionAuthMap.put("empOTAddInit-executeInit", userAuthorityList);
        actionAuthMap.put("empLRAddDo-execute", userAuthorityList);
        actionAuthMap.put("empOTAddDo-execute", userAuthorityList);
        actionAuthMap.put("LRUpdateInit-executeInit", userAuthorityList);
        actionAuthMap.put("myOTUpdateInit-executeInit", userAuthorityList);
        actionAuthMap.put("LRUpdateDo-execute", userAuthorityList);
        actionAuthMap.put("myOTUpdateDo-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("411", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("411", "2", "SUB"));
        actionAuthMap.put("deptExaminSearch-execute", userAuthorityList);
        actionAuthMap.put("deptLeaveSearch-execute", userAuthorityList);
        actionAuthMap.put("deptOvertimeSearch-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        actionAuthMap.put("hrExaminSearch-execute", userAuthorityList);
        actionAuthMap.put("hrLeaveSearch-execute", userAuthorityList);
        actionAuthMap.put("hrOvertimeSearch-execute", userAuthorityList);
        actionAuthMap.put("emailHrLeaveApproveOrReject-hrLeaveApproveOrReject", userAuthorityList);
        actionAuthMap.put("emailHrOtApproveOrReject-hrOtApproveOrReject", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("411", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("401", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "2", "SUB"));
        actionAuthMap.put("allExaminSearch-execute", userAuthorityList);
        actionAuthMap.put("allLeaveSearch-execute", userAuthorityList);
        actionAuthMap.put("allOvertimeSearch-execute", userAuthorityList);
        actionAuthMap.put("allOtModifyAndConfirm-hrOtConfirmWithModify", userAuthorityList);
        actionAuthMap.put("emailDeptApproveOrReject-deptApproveOrReject", userAuthorityList);
        actionAuthMap.put("emailDeptOtApproveOrReject-deptOtApproveOrReject", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("411", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("401", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "2", "SUB"));
        actionAuthMap.put("examinScheduleSearch-execute", userAuthorityList);
        actionAuthMap.put("examinScheduleInsert-insertSchedule", userAuthorityList);
        actionAuthMap.put("examinScheduleInsert-updateOneSchedule", userAuthorityList);
        actionAuthMap.put("examinScheduleInsert-batchUpdateSchedule", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("411", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("401", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "2", "SUB"));
        actionAuthMap.put("originalDataImportShow-execute", userAuthorityList);
        actionAuthMap.put("deleteAndToImport-deleteAndToImport", userAuthorityList);
        actionAuthMap.put("deleteOriginalData-deleteOriginalData", userAuthorityList);
        actionAuthMap.put("deleteOneOriginalData-deleteOneOriginalData", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        actionAuthMap.put("attdSyncRecordShow-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("411", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("401", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "2", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "1", "OWN"));
        actionAuthMap.put("searchAttenddaily-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        actionAuthMap.put("deleteAttendmonthly-deleteAttendmonthly", userAuthorityList);
        actionAuthMap.put("batchDelAttendmonthly-batchDelAttendmonthly", userAuthorityList);
        actionAuthMap.put("calDailyToAttendmonthly-calDailyToAttendmonthly", userAuthorityList);
        actionAuthMap.put("DwrForAttend-getEmpAttendmonthly", userAuthorityList);
        actionAuthMap.put("DwrForAttend-saveEmpAttendmonthly", userAuthorityList);

        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        actionAuthMap.put("applyAttendperiod-applyAttendperiod", userAuthorityList);

        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        actionAuthMap.put("closeAttendperiod-closeAttendperiod", userAuthorityList);
        actionAuthMap.put("reOpenAttendperiod-reOpenAttendperiod", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("411", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("401", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "2", "SUB"));
        actionAuthMap.put("searchAttendmonthly-execute", userAuthorityList);
        actionAuthMap.put("DwrForAttend-getAttendDailyMemory", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        actionAuthMap.put("examinReport-execute", userAuthorityList);
        actionAuthMap.put("lrReprotByTime-executeEmpLr", userAuthorityList);
        actionAuthMap.put("otReprotByTime-executeEmpOt", userAuthorityList);
        actionAuthMap.put("examinReportInit-execute", userAuthorityList);
        actionAuthMap.put("leavecalendarManage-execute", userAuthorityList);
        actionAuthMap.put("leavebalanceManage-execute", userAuthorityList);
        actionAuthMap.put("leavebalanceCurYear-execute", userAuthorityList);
        actionAuthMap.put("leavebalanceNextYear-execute", userAuthorityList);
        actionAuthMap.put("leavebalancePrevious-execute", userAuthorityList);
        actionAuthMap.put("examinShiftSearch-shiftSearch", userAuthorityList);
        actionAuthMap.put("examinShiftInsert-insertShift", userAuthorityList);
        actionAuthMap.put("examinShiftInsert-delShift", userAuthorityList);
        actionAuthMap.put("initLeaveBalance-initLeaveBalance", userAuthorityList);
        actionAuthMap.put("doSubmitLeaveBalance-doSubmitLeaveBalance", userAuthorityList);
        actionAuthMap.put("doDeleteLeaveBalance-doDeleteLeaveBalance", userAuthorityList);
        actionAuthMap.put("workFlowApprove-workFlowConfig", userAuthorityList);
        actionAuthMap.put("workFlowApproveShow-workFlowShow", userAuthorityList);
        actionAuthMap.put("workFlowApproveManage-workFlowList", userAuthorityList);
        actionAuthMap.put("authorityPosManage-authorityPos", userAuthorityList);
        actionAuthMap.put("workFlowApproveManageUpdate-update", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("311", null, "OWN"));
        actionAuthMap.put("myTrainingList-execute", userAuthorityList);
        actionAuthMap.put("trepAdd-execute", userAuthorityList);
        actionAuthMap.put("trepAddInit-trepAddInit", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("311", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("311", "2", "DEPT"));
        actionAuthMap.put("trepDeptApprove-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("301", "2", "HR"));
        actionAuthMap.put("trepHrApprove-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("301", null, "ADM"));
        actionAuthMap.put("trReport-execute", userAuthorityList);
        actionAuthMap.put("trepCancel-cancelTrep", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("301", null, "ADM"));
        userAuthorityList.add(new UsersAuthority("311", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("311", "2", "DEPT"));
        actionAuthMap.put("trepList-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("301", null, "ADM"));
        actionAuthMap.put("trcourseConfig-execute", userAuthorityList);
        actionAuthMap.put("trcourseAdd-trcourseAdd", userAuthorityList);
        actionAuthMap.put("updateTrcInit-updateTrcInit", userAuthorityList);
        actionAuthMap.put("updateTrc-updateTrc", userAuthorityList);
        actionAuthMap.put("deleteTrc-deleteTrc", userAuthorityList);
        actionAuthMap.put("trcOpen-openTrc", userAuthorityList);
        actionAuthMap.put("closeTrc-closeTrc", userAuthorityList);
        actionAuthMap.put("trtConfig-execute", userAuthorityList);

        actionAuthMap.put("trcpAdd-addTrcp", userAuthorityList);
        actionAuthMap.put("trcpUpdate-updateTrcp", userAuthorityList);
        actionAuthMap.put("trcpDelete-deleteTrcp", userAuthorityList);
        actionAuthMap.put("trcpOpen-openTrcp", userAuthorityList);
        actionAuthMap.put("closeTrcp-closeTrcp", userAuthorityList);

        actionAuthMap.put("TrtConfig-trtAdd", userAuthorityList);
        actionAuthMap.put("TrtConfig-trtDelete", userAuthorityList);
        actionAuthMap.put("TrtConfig-trtUpdate", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("611", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("611", "2", "DEPT"));
        userAuthorityList.add(new UsersAuthority("611", "1", "OWN"));
        actionAuthMap.put("SearchRecruitplan-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("UpdateRecruitplanInitADM-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("DeleteRecruitplanADM-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("611", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("611", "2", "DEPT"));
        userAuthorityList.add(new UsersAuthority("611", "1", "OWN"));
        actionAuthMap.put("AddRecruitplan-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("611", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("611", "2", "DEPT"));
        userAuthorityList.add(new UsersAuthority("611", "1", "OWN"));
        actionAuthMap.put("AddRecruitplanInit-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("recruitapplierCreate-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("deleteapplier-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        userAuthorityList.add(new UsersAuthority("601", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("611", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("611", "2", "DEPT"));
        actionAuthMap.put("recruitapplierSearch-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("recruitapplierUpdate-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("recruitapplierUpdateRemark-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("recruitapplierUpdateRemarkBishi-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        userAuthorityList.add(new UsersAuthority("611", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("611", "2", "DEPT"));
        actionAuthMap.put("ViewRecruitplanDetailForHR-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        userAuthorityList.add(new UsersAuthority("611", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("611", "2", "DEPT"));
        actionAuthMap.put("SearchRecruitplanForHR-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("recruitchannellist-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("611", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("611", "2", "DEPT"));
        actionAuthMap.put("ApproverRecruitplanDept-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", "2", "HR"));
        actionAuthMap.put("ApproverRecruitplanHR-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("UpdateRecruitplanInitADM-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("DownFile-recruitapplierDownFile", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("601", null, "ADM"));
        actionAuthMap.put("recruitmentReport-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("911", null, ""));
        actionAuthMap.put("userList-execute", userAuthorityList);
        actionAuthMap.put("delUser-del", userAuthorityList);
        actionAuthMap.put("addUserInit-execute", userAuthorityList);
        actionAuthMap.put("addUser-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("921", null, ""));
        actionAuthMap.put("getRoleList-execute", userAuthorityList);
        actionAuthMap.put("addRoleInit-execute", userAuthorityList);
        actionAuthMap.put("addRoleInit-addinit", userAuthorityList);
        actionAuthMap.put("addRole-execute", userAuthorityList);
        actionAuthMap.put("roleView-view", userAuthorityList);
        actionAuthMap.put("roleEdit-execute", userAuthorityList);
        actionAuthMap.put("roleUpdate-update", userAuthorityList);
        actionAuthMap.put("roleDel-del", userAuthorityList);
        actionAuthMap.put("getAuthList-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("963", null, ""));
        actionAuthMap.put("backup-saveBackup", userAuthorityList);
        actionAuthMap.put("delete-delete", userAuthorityList);
        actionAuthMap.put("resume-resume", userAuthorityList);
        actionAuthMap.put("sqlbin-sqlbin", userAuthorityList);
        actionAuthMap.put("backupResume-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("941", null, ""));
        actionAuthMap.put("logclean-deleteAllLogs", userAuthorityList);
        actionAuthMap.put("logscan-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("962", null, ""));
        actionAuthMap.put("datadelete-deleteData", userAuthorityList);
        actionAuthMap.put("datascan-scanData", userAuthorityList);
        actionAuthMap.put("dataclean-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("951", null, ""));
        actionAuthMap.put("emailSearch-execute", userAuthorityList);
        actionAuthMap.put("EmailsendBatch-execute", userAuthorityList);
        actionAuthMap.put("etSearch-execute", userAuthorityList);
        actionAuthMap.put("modifyTemplate-modifyTemplate", userAuthorityList);
        actionAuthMap.put("modifyTemplateInit-modifyTemplateInit", userAuthorityList);
        actionAuthMap.put("openEmailsend-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("952", null, ""));
        actionAuthMap.put("config-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("961", null, ""));
        actionAuthMap.put("systemconfiginit-execute", userAuthorityList);
        actionAuthMap.put("showConfigProfile-execute", userAuthorityList);
        actionAuthMap.put("profileUpdateConfig-executeUpdate", userAuthorityList);
        actionAuthMap.put("examinUpdateConfig-executeExamin", userAuthorityList);
        actionAuthMap.put("showConfigExamin-showConfigExamin", userAuthorityList);
        actionAuthMap.put("showConfigSalary-showConfigSalary", userAuthorityList);
        actionAuthMap.put("salaryUpdateConfig-executeSalary", userAuthorityList);
        actionAuthMap.put("showExaminShift-showExaminShift", userAuthorityList);
        actionAuthMap.put("examinShiftUpdateConfig-updateExaminShift", userAuthorityList);
        actionAuthMap.put("showConfigMail-showConfigMail", userAuthorityList);
        actionAuthMap.put("mailUpdateConfig-executeMail", userAuthorityList);
        actionAuthMap.put("showConfigOthers-showConfigOthers", userAuthorityList);
        actionAuthMap.put("otherUpdateConfig-executeOthers", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("931", null, ""));
        actionAuthMap.put("clientInit-executeInit", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("801", null, ""));
        actionAuthMap.put("searchInfo-execute", userAuthorityList);
        actionAuthMap.put("createInfoInit-execute", userAuthorityList);
        actionAuthMap.put("createInfo-execute", userAuthorityList);
        actionAuthMap.put("updateInfo-execute", userAuthorityList);
        actionAuthMap.put("downloadFile-execute", userAuthorityList);
        actionAuthMap.put("NewsInfo-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("801", null, ""));
        userAuthorityList.add(new UsersAuthority("802", "3", ""));
        userAuthorityList.add(new UsersAuthority("802", "2", ""));
        actionAuthMap.put("openInfo-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, ""));
        actionAuthMap.put("importinfo-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, ""));
        userAuthorityList.add(new UsersAuthority("201", null, ""));
        actionAuthMap.put("infomatch-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", null, ""));
        actionAuthMap.put("postsalary-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("701", null, ""));
        actionAuthMap.put("listAll-execute", userAuthorityList);
        actionAuthMap.put("uploadInit-execute", userAuthorityList);
        actionAuthMap.put("uploadReport-execute", userAuthorityList);
        actionAuthMap.put("updateUploadReportInit-execute", userAuthorityList);
        actionAuthMap.put("updateUploadReport-execute", userAuthorityList);
        actionAuthMap.put("updateReport-execute", userAuthorityList);
        actionAuthMap.put("viewReport-execute", userAuthorityList);
        actionAuthMap.put("runReport-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("702", null, ""));
        actionAuthMap.put("createInit-execute", userAuthorityList);
        actionAuthMap.put("editReport-execute", userAuthorityList);
        actionAuthMap.put("updateReportInit-execute", userAuthorityList);
        actionAuthMap.put("deleteReport-execute", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("702", null, ""));
        actionAuthMap.put("iodefList-execute", userAuthorityList);
        actionAuthMap.put("ommList-execute", userAuthorityList);
        actionAuthMap.put("immList-execute", userAuthorityList);
        actionAuthMap.put("iodef-view", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("702", null, ""));
        actionAuthMap.put("iodefList-execute", userAuthorityList);
        actionAuthMap.put("ommList-execute", userAuthorityList);
        actionAuthMap.put("immList-execute", userAuthorityList);
        actionAuthMap.put("iodef-view", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("702", "2", ""));
        actionAuthMap.put("iodef-edit", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("101", null, "ADM"));
        actionAuthMap.put("import-IEmpreward", userAuthorityList);
        actionAuthMap.put("import-IEmptransfer", userAuthorityList);
        actionAuthMap.put("import-IEmpeval", userAuthorityList);
        actionAuthMap.put("import-IEmpContract", userAuthorityList);
        actionAuthMap.put("import-IEmployeeBasic", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("201", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("201", "2", "HR"));
        actionAuthMap.put("import-IEmpSalaryConfig", userAuthorityList);
        actionAuthMap.put("import-IEmpSalaryPay", userAuthorityList);
        actionAuthMap.put("import-IEmpSalaryBenefit", userAuthorityList);
        actionAuthMap.put("import-IEmpSalaryBenefitUpd", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        userAuthorityList.add(new UsersAuthority("411", "3", "ALL"));
        userAuthorityList.add(new UsersAuthority("401", "1", "SUB"));
        userAuthorityList.add(new UsersAuthority("411", "2", "SUB"));
        actionAuthMap.put("import-IExaminShift", userAuthorityList);
        actionAuthMap.put("import-IExaminCardData", userAuthorityList);

        userAuthorityList = new ArrayList();
        userAuthorityList.add(new UsersAuthority("401", "3", "HR"));
        userAuthorityList.add(new UsersAuthority("401", "2", "HR"));
        actionAuthMap.put("import-IExamMonthly", userAuthorityList);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.base.ActionAuths JD-Core Version: 0.5.4
 */