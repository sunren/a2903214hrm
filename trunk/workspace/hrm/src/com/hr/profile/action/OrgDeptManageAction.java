package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Syslog;
import com.hr.profile.bo.IOuperfcriteriaBo;
import com.hr.profile.bo.IOuresponseBo;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Depthist;
import com.hr.profile.domain.Ouperfcriteria;
import com.hr.profile.domain.Ouresponse;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.PositionBaseHist;
import com.hr.profile.domain.base.TreeNode;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Message;
import com.hr.util.StringUtil;
import com.hr.util.SysConfigManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.axis.utils.StringUtils;

public class OrgDeptManageAction extends BaseAction {
    private String msgNoAuth;
    private String msgSaveSucc;
    private String msgDataStatuserror;
    private String msgRenameNameNotChange;
    private String msgRenameRepeatName;
    private String msgDeptNotExist;
    private String msgDeptRepeatName;
    private String msgMoveRootError;
    private String msgMoveSameNodeError;
    private String msgMoveAlreadyError;
    private String msgMoveToSubError;
    private String msgStartNoParent;
    private String msgStartParentStop;
    private String msgMergeToSubError;
    private static String DISABLE_ROOT_ID = "disabled";
    private List<TreeNode> nodeList;
    private Department dept;
    private String nodeId;
    private String nodeType;
    private List<JobTitle> jobtitleList;
    private String operateOver;
    private String rootId;
    private String disableRootId;
    private List<Ouresponse> deptResList;
    private List<PositionBase> deptPBList;
    private List<Ouperfcriteria> deptPerfcrList;
    private List<Depthist> deptHistList;
    private List<Syslog> deptLogList;
    private List<Department> directSubDeptList;
    SysConfigManager dbConfigManager;

    public OrgDeptManageAction() {
        this.msgNoAuth = "没有权限";
        this.msgSaveSucc = "保存成功";
        this.msgDataStatuserror = "数据错误，请刷新页面后重试！";

        this.msgRenameNameNotChange = "名称未发生变化！";
        this.msgRenameRepeatName = "当前节点下已经存在名称为{0}的节点！";

        this.msgDeptNotExist = "{0}已不存在，请刷新后重试！";
        this.msgDeptRepeatName = "当前节点下已经存在名称为{0}的节点！";

        this.msgMoveRootError = "不能移动根节点！";
        this.msgMoveSameNodeError = "两节点为同一节点";
        this.msgMoveAlreadyError = "节点{0}已经在节点{1}下！";
        this.msgMoveToSubError = "父节点不能移动到其子级节点！";

        this.msgStartNoParent = "{0}无父节点，不能启用！";
        this.msgStartParentStop = "{0}的父节点已停用，不能启用";

        this.msgMergeToSubError = "不能将父部门合并到子部门";

        this.nodeList = null;

        this.dept = null;

        this.nodeId = null;

        this.nodeType = null;

        this.jobtitleList = null;

        this.operateOver = null;

        this.rootId = null;

        this.disableRootId = null;

        this.deptResList = null;

        this.deptPBList = null;

        this.deptHistList = null;

        this.deptLogList = null;

        this.directSubDeptList = null;

        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String execute() throws Exception {
        this.nodeList = getTreeNodesList();
        this.disableRootId = DISABLE_ROOT_ID;

        return "success";
    }

    public String goBranchDeptTabs() {
        return "branchDeptTabs";
    }

    public String editBranchDeptInit() throws Exception {
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        IJobTitleBo titleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        setJobtitleList(titleBo.FindEnabledJobTitle());

        if (this.dept == null) {
            addErrorInfo(this.msgDataStatuserror);
            return null;
        }

        Department tempDept = null;
        if (!StringUtils.isEmpty(this.dept.getId())) {
            tempDept = deptBo.getDeptById(this.dept.getId());
            if (tempDept == null)
                return "error";

            getOtherInfoOfDept(tempDept);
            this.dept = tempDept;
        } else {
            this.dept.setDepartmentParentId(new Department(this.nodeId));
            this.dept.setDepartmentCate(Integer.valueOf(Integer.parseInt(this.nodeType)));
        }

        if ((this.dept.getDepartmentCate().intValue() == 1)
                || (this.dept.getDepartmentCate().intValue() == 0)) {
            this.nodeType = "1";
            return "editBranchInit";
        }
        if (this.dept.getDepartmentCate().intValue() == 2) {
            this.nodeType = "2";
            return "editDeptInit";
        }

        return null;
    }

    public String saveBranchDept() throws Exception {
        Map dbMap = this.dbConfigManager.getProperties();
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");

        if (this.dept == null) {
            addErrorInfo(this.msgDataStatuserror);
            return "success";
        }

        Department tempDept = null;

        if (!StringUtils.isEmpty(this.dept.getId())) {
            tempDept = deptBo.loadDepartmentByNo(this.dept.getId(), new String[0]);
            if (tempDept == null) {
                addErrorInfo(StringUtil.message(this.msgDeptNotExist, new Object[] { this.dept
                        .getDepartmentName() }));
                return "success";
            }

            if (StringUtils.isEmpty(this.dept.getDepartmentParentId().getId()))
                this.dept.setDepartmentParentId(null);
            deptBo.saveorUpdateDepartment(this.dept);
            this.operateOver = "editOver";
        } else {
            this.dept.setDepartmentName(this.dept.getDepartmentName().trim());
            Department sameNameDept = deptBo.getDeptByName(this.dept.getDepartmentName(),
                                                           this.nodeId);
            if (sameNameDept != null) {
                addErrorInfo(StringUtil.message(this.msgDeptRepeatName, new Object[] { this.dept
                        .getDepartmentName() }));
                this.operateOver = "nameRepeat";
                return "success";
            }

            this.dept.setId(getUUID());

            this.dept.setDepartmentStatus(new Integer(1));
            this.dept.setDepartmentSortId(deptBo.getMaxSortId());
            this.dept.setDepartmentCreateDate(new Date());

            Position parentRespPos = posBo.getRespPosOfDept(this.dept.getDepartmentParentId()
                    .getId());

            PositionBase respPB = this.dept.getRespPb();
            respPB.setId(getUUID());
            respPB.setPbDeptId(this.dept);
            respPB.setPbJobTitle(this.dept.getRespPb().getPbJobTitle());
            respPB.setPbInCharge(new Integer(1));
            respPB.setPbMaxEmployee(new Integer(1));
            respPB.setPbMaxExceed(Integer.valueOf(Integer.parseInt((dbMap
                    .get("sys.position.max.exceed") != null) ? (String) dbMap
                    .get("sys.position.max.exceed") : "1")));
            respPB.setPbSortId(pbBo.getMaxSortId());
            respPB.setPbStatus(new Integer(1));
            respPB.setPbSupId(parentRespPos.getPositionPbId());

            Position pos = new Position(getUUID());
            pos.setPositionPbId(respPB);
            pos.setPositionParentId(parentRespPos);
            pos.setPositionTakeNum(new Integer(1));
            pos.setPositionEmpNo(this.dept.getDeptHead());

            Depthist deptHist = new Depthist(getUUID(), this.dept, this.dept
                    .getDepartmentParentId(), this.dept.getDepartmentName(), new Integer(1),
                    new Date());
            PositionBaseHist pbHist = new PositionBaseHist(getUUID(), respPB, this.dept, respPB
                    .getPbStatus(), respPB.getPbMaxEmployee(), respPB.getPbInCharge(), new Date(),
                    null);

            boolean save = deptBo.addDeptSave(this.dept, respPB, pos, deptHist, pbHist);
            if (save)
                addSuccessInfo(StringUtil.message(this.msgSaveSucc, new Object[0]));
            this.operateOver = "newOver";
        }

        return "success";
    }

    public String deptRename(String deptId, String deptName) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }

        deptName = deptName.trim();

        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        Department dept = deptBo.getDeptById(deptId);
        if ((deptName != null) && (deptName.equals(dept.getDepartmentName())))
            return StringUtil.message(this.msgRenameNameNotChange, new Object[0]);

        if (dept.getDepartmentParentId() != null) {
            Department sameNameDept = deptBo.getDeptByName(deptName, dept.getDepartmentParentId()
                    .getId());
            if (sameNameDept != null)
                return StringUtil.message(this.msgRenameRepeatName, new Object[] { deptName });
        }

        dept.setDepartmentName(deptName);
        if (dept.getDepartmentCreateDate() == null)
            dept.setDepartmentCreateDate(new Date());

        deptBo.saveProcessDeptRename(dept);
        return "SUCC";
    }

    public String moveNode(String fromId, String toId) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }

        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        List nodeList = getTreeNodesList();
        TreeNode fromNode = getNodeById(nodeList, fromId);
        TreeNode toNode = getNodeById(nodeList, toId);

        String info = moveNodeCheck(nodeList, fromNode, toNode);
        if (!"SUCC".equals(info))
            return info;

        deptBo.saveDeptMove(fromId, toId);

        return "SUCC";
    }

    public String moveNodeCheck(List<TreeNode> nodeList, TreeNode fromNode, TreeNode toNode) {
        String info = "SUCC";

        if (fromNode.getNodeType() == 0)
            return StringUtil.message(this.msgMoveRootError, new Object[0]);
        if (fromNode.getNodeId().equals(toNode.getNodeId()))
            return StringUtil.message(this.msgMoveSameNodeError, new Object[0]);
        if (fromNode.getNodeParentId().equals(toNode.getNodeId())) {
            return StringUtil.message(this.msgMoveAlreadyError, new Object[] {
                    fromNode.getNodeName(), toNode.getNodeName() });
        }

        TreeNode tempToNode = toNode;
        while (!"0".equals(tempToNode.getNodeParentId())) {
            tempToNode = getNodeById(nodeList, tempToNode.getNodeParentId());
            if (tempToNode.getNodeId().equals(fromNode.getNodeId()))
                ;
            info = StringUtil.message(this.msgMoveToSubError, new Object[0]);
        }

        return info;
    }

    public String disableDept(String deptId) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }

        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");

        Department dept = deptBo.getDeptById(deptId);
        if ((dept == null) || (dept.getDepartmentStatus().intValue() == 0))
            return StringUtil.message(this.msgDataStatuserror, new Object[0]);

        Message msg = new Message(null, "SUCC");
        try {
            deptBo.update_disableDept(deptId, msg);
        } catch (RuntimeException e) {
            if (!"SUCC".equals(msg.getMsgContent())) {
                return msg.getMsgContent();
            }

            e.printStackTrace();
            return e.getMessage();
        }

        return "SUCC";
    }

    public String enableDept(String deptId) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }

        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");

        Department dept = deptBo.getDeptById(deptId);
        if ((dept == null) || (dept.getDepartmentStatus().intValue() == 1))
            return StringUtil.message(this.msgDataStatuserror, new Object[0]);

        String tempInfo = enableDeptCheck(dept);
        if (!"SUCC".equals(tempInfo))
            return tempInfo;

        tempInfo = deptBo.update_enableDept(deptId);
        if (!"SUCC".equals(tempInfo))
            return tempInfo;

        return "SUCC," + dept.getDepartmentParentId().getId();
    }

    public String enableDeptCheck(Department dept) {
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        List deptList = deptBo.FindAllDepartment();

        Department tempParentDept = getDeptById(dept.getDepartmentParentId().getId(), deptList);
        if (tempParentDept == null)
            return StringUtil.message(this.msgStartNoParent, new Object[] { dept
                    .getDepartmentName() });
        while ((tempParentDept.getDepartmentParentId() != null)
                && (!StringUtils.isEmpty(tempParentDept.getDepartmentParentId().getId()))) {
            if (tempParentDept.getDepartmentStatus().intValue() == 0)
                return StringUtil.message(this.msgStartParentStop, new Object[] { dept
                        .getDepartmentName() });
            tempParentDept = getDeptById(tempParentDept.getDepartmentParentId().getId(), deptList);
        }

        return "SUCC";
    }

    public Department getDeptById(String deptId, List<Department> deptList) {
        for (int i = 0; (deptList != null) && (i < deptList.size()); ++i) {
            Department dept = (Department) deptList.get(i);
            if (deptId.equals(dept.getId()))
                return dept;
        }

        return null;
    }

    public String deleteDept(String deptId) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");

        Department dept = deptBo.getDeptById(deptId);
        if (dept == null)
            return StringUtil.message(this.msgDataStatuserror, new Object[0]);

        Message msg = new Message(null, "SUCC");
        try {
            deptBo.deleteDept(deptId, msg);
        } catch (RuntimeException e) {
            if (!"SUCC".equals(msg.getMsgContent())) {
                return msg.getMsgContent();
            }

            e.printStackTrace();
            return e.getMessage();
        }

        return "SUCC";
    }

    public Object mergeNode(String fromId, String toId, String fromPBInfo) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }

        if (fromPBInfo.indexOf("#") < 0)
            return StringUtil.message(this.msgDataStatuserror, new Object[0]);
        String[] infoArr = fromPBInfo.split("#");
        String[][] infoTwoArr = new String[infoArr.length][6];
        for (int i = 0; (infoArr != null) && (i < infoArr.length); ++i) {
            String[] tempArr = infoArr[i].split(",");
            if ((tempArr[0] == null) || ("".equals(tempArr[0].trim()))) {
                return StringUtil.message(this.msgDataStatuserror, new Object[0]);
            }
            infoTwoArr[i] = tempArr;
        }

        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        Message msg = new Message(null, "SUCC");
        try {
            deptBo.saveMergeDept(fromId, toId, infoTwoArr, msg);
        } catch (RuntimeException e) {
            if (!"SUCC".equals(msg.getMsgContent())) {
                return msg.getMsgContent();
            }

            e.printStackTrace();
            return e.getMessage();
        }

        return getTreeNodesList();
    }

    private List<TreeNode> getTreeNodesList() {
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");

        if ((this.nodeList == null) || (this.nodeList.size() == 0))
            this.nodeList = new ArrayList();
        List<Department> deptList = deptbo.FindAllDepartment();
        for (Department dept : deptList) {
            if (dept.getDepartmentParentId() == null) {
                dept.setDepartmentParentId(new Department("0"));
                this.rootId = dept.getId();
            }

            TreeNode node = null;
            if (dept.getDepartmentStatus().intValue() == 1)
                node = new TreeNode(dept.getId(), dept.getDepartmentName(), dept
                        .getDepartmentParentId().getId(), dept.getDepartmentCate().intValue(), dept
                        .getDepartmentStatus().intValue());
            else if (dept.getDepartmentStatus().intValue() == 0) {
                node = new TreeNode(dept.getId(), dept.getDepartmentName(), DISABLE_ROOT_ID, dept
                        .getDepartmentCate().intValue(), dept.getDepartmentStatus().intValue());
            }
            this.nodeList.add(node);
        }
        this.nodeList.add(new TreeNode("disabled", "停用的组织结构", "0", 11));

        return this.nodeList;
    }

    public List<TreeNode> getAllEnabledDept(String fromType, String nodeId) {
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        List enabledDeptNodeList = deptBo.FindEnabledDepartmentNode();

        return enabledDeptNodeList;
    }

    public Object getAllPBofDept(String fromDeptIds, String toDeptId) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");

        String info = "SUCC";
        String[] fromDeptArray = fromDeptIds.split(",");
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        String[] subDepts = deptBo.getAllSubDeptIds(fromDeptArray);
        for (int i = 0; (subDepts != null) && (i < subDepts.length); ++i) {
            String dept = subDepts[i];
            if (dept.equals(toDeptId)) {
                info = info + StringUtil.message(this.msgMergeToSubError, new Object[0]);
                break;
            }
        }
        if (!"SUCC".equals(info))
            return info.replace("SUCC", "");

        Map toPBMap = new HashMap();

        Map pbOperateMap = new HashMap();
        setMapValue(toPBMap, pbOperateMap, toDeptId);

        List pbList = pbBo.getAllPbsOfDept(fromDeptArray);
        for (int i = 0; (pbList != null) && (i < pbList.size()); ++i) {
            PositionBase pb = (PositionBase) pbList.get(i);

            pb.setPbOperateMap(pbOperateMap);

            pb.setPbMoveToMap(toPBMap);
        }

        return pbList;
    }

    private void setMapValue(Map<String, String> toPBMap, Map<String, String> pbOperateMap,
            String toDeptId) {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        List toPBList = pbBo.getAllPbsOfDept(new String[] { toDeptId });
        for (int i = 0; (toPBList != null) && (i < toPBList.size()); ++i) {
            PositionBase pb = (PositionBase) toPBList.get(i);
            toPBMap.put(pb.getId(), pb.getPbName());
        }

        pbOperateMap.put("0_1", "请选择");
        pbOperateMap.put("1", "拷贝");
        pbOperateMap.put("2", "并入");
    }

    public void getOtherInfoOfDept(Department tempDept) {
        if (tempDept == null)
            return;
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");

        PositionBase respPB = pbBo.getRespPBofDept(tempDept.getId());
        tempDept.setRespPb(respPB);

        Position respPos = posBo.getRespPosOfDept(tempDept.getId());
        tempDept.setDeptHead((respPos != null) ? respPos.getPositionEmpNo() : null);

        if (tempDept.getDepartmentStatus().intValue() == 1) {
            tempDept.setOrderNum(Integer.valueOf(deptBo.getOrderEmpNos(tempDept.getId())));
            tempDept.setActualNum(Integer.valueOf(deptBo.getActiveEmpNos(tempDept.getId())));
        }

        List subDeptList = deptBo.getSubDeptsByParentId(this.dept.getId());
        tempDept.setSubDeptList(subDeptList);
    }

    public String goDeptResTab() {
        IOuresponseBo deptResBo = (IOuresponseBo) SpringBeanFactory.getBean("ouresponseBo");
        if ((this.dept != null) && (!StringUtils.isEmpty(this.dept.getId()))) {
            this.deptResList = deptResBo.getDeptRespLisByDept(this.dept.getId());
        }

        return "success";
    }

    public String goDeptPerfcrTab() {
        IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo) SpringBeanFactory
                .getBean("ouperfcriteriaBo");
        this.deptPerfcrList = perfcrBo.getDeptPerfcriteria(this.dept.getId());
        return "success";
    }

    public Ouresponse saveDeptRes(Ouresponse deptRes) {
        IOuresponseBo deptResBo = (IOuresponseBo) SpringBeanFactory.getBean("ouresponseBo");
        deptResBo.saveDeptRes(deptRes);

        return deptRes;
    }

    public Ouperfcriteria saveDeptPerfcr(Ouperfcriteria deptPerfcr) {
        IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo) SpringBeanFactory
                .getBean("ouperfcriteriaBo");
        if (StringUtils.isEmpty(deptPerfcr.getId()))
            perfcrBo.addPbPerfcr(deptPerfcr);
        else {
            perfcrBo.updatePbPerfcr(deptPerfcr);
        }

        return deptPerfcr;
    }

    public String deleteDeptRes(String deptResId) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }

        IOuresponseBo deptResBo = (IOuresponseBo) SpringBeanFactory.getBean("ouresponseBo");
        deptResBo.deleteDeptResById(deptResId);

        return "SUCC";
    }

    public String deleteDeptPerfcr(String id) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }

        IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo) SpringBeanFactory
                .getBean("ouperfcriteriaBo");
        perfcrBo.delPbPerfcr(id);

        return "SUCC";
    }

    public String saveDeptResSortBatch(String[] ids) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }

        IOuresponseBo deptResBo = (IOuresponseBo) SpringBeanFactory.getBean("ouresponseBo");
        deptResBo.saveSortIdBatch(ids);

        return "SUCC";
    }

    public String saveDeptPerfcrSortBatch(String[] ids) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }

        IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo) SpringBeanFactory
                .getBean("ouperfcriteriaBo");
        perfcrBo.savePbPerfcrOrder(ids);

        return "SUCC";
    }

    public String goDeptPBsTab() {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        if ((this.dept != null) && (!StringUtils.isEmpty(this.dept.getId()))) {
            this.deptPBList = pbBo.getAllPbsOfDept(new String[] { this.dept.getId() });
        }

        return "success";
    }

    public String goDeptHistTab() {
        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");

        if ((this.dept != null) && (!StringUtils.isEmpty(this.dept.getId()))) {
            this.deptLogList = logBO.getLogs("department", this.dept.getId());
        }

        return "success";
    }

    public String goDirectSubDeptTab() {
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        if ((this.dept != null) && (!StringUtils.isEmpty(this.dept.getId()))) {
            this.directSubDeptList = deptBo.getSubDeptsByParentId(this.dept.getId());
            for (int i = 0; (this.directSubDeptList != null) && (i < this.directSubDeptList.size()); ++i) {
                Department dept = (Department) this.directSubDeptList.get(i);
                getOtherInfoOfDept(dept);
            }
        }

        return "success";
    }

    public String saveDeptOrder(String[] ids) {
        String auth = DWRUtil.checkAuth("OrgDeptManage", "execute");
        if (!"ADM".equals(auth)) {
            return StringUtil.message(this.msgNoAuth, new Object[0]);
        }

        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        deptBo.saveDeptOrder(ids);

        return "SUCC";
    }

    private String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public TreeNode getNodeById(List<TreeNode> nodeList, String nodeId) {
        TreeNode node = null;
        for (TreeNode temp : nodeList) {
            if (temp.getNodeId().equals(nodeId)) {
                node = temp;
                break;
            }
        }

        return node;
    }

    public Department getDept() {
        return this.dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public List<JobTitle> getJobtitleList() {
        return this.jobtitleList;
    }

    public void setJobtitleList(List<JobTitle> jobtitleList) {
        this.jobtitleList = jobtitleList;
    }

    public List<TreeNode> getNodeList() {
        return this.nodeList;
    }

    public void setNodeList(List<TreeNode> nodeList) {
        this.nodeList = nodeList;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getOperateOver() {
        return this.operateOver;
    }

    public void setOperateOver(String operateOver) {
        this.operateOver = operateOver;
    }

    public String getRootId() {
        return this.rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getDisableRootId() {
        return this.disableRootId;
    }

    public void setDisableRootId(String disableRootId) {
        this.disableRootId = disableRootId;
    }

    public List<Ouresponse> getDeptResList() {
        return this.deptResList;
    }

    public void setDeptResList(List<Ouresponse> deptResList) {
        this.deptResList = deptResList;
    }

    public List<PositionBase> getDeptPBList() {
        return this.deptPBList;
    }

    public void setDeptPBList(List<PositionBase> deptPBList) {
        this.deptPBList = deptPBList;
    }

    public List<Depthist> getDeptHistList() {
        return this.deptHistList;
    }

    public void setDeptHistList(List<Depthist> deptHistList) {
        this.deptHistList = deptHistList;
    }

    public List<Syslog> getDeptLogList() {
        return this.deptLogList;
    }

    public void setDeptLogList(List<Syslog> deptLogList) {
        this.deptLogList = deptLogList;
    }

    public List<Ouperfcriteria> getDeptPerfcrList() {
        return this.deptPerfcrList;
    }

    public void setDeptPerfcrList(List<Ouperfcriteria> deptPerfcrList) {
        this.deptPerfcrList = deptPerfcrList;
    }

    public List<Department> getDirectSubDeptList() {
        return this.directSubDeptList;
    }

    public void setDirectSubDeptList(List<Department> directSubDeptList) {
        this.directSubDeptList = directSubDeptList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.OrgDeptManageAction JD-Core Version: 0.5.4
 */