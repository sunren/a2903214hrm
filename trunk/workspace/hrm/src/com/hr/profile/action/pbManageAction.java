package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.ComonBeans;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Syslog;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IOuperfcriteriaBo;
import com.hr.profile.bo.IOuqualifyBo;
import com.hr.profile.bo.IOuresponseBo;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.domain.Ouperfcriteria;
import com.hr.profile.domain.Ouqualify;
import com.hr.profile.domain.Ouresponse;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.SysConfigManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class pbManageAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String pbId;
    private List<JobTitle> jobtitleList;
    private String deptId;
    private String parentPbId;
    private String exceedConfig;
    private String lastOperate;
    private Map<String, String> pcTypeMap;
    private PositionBase pb;
    private List<Ouresponse> pbRespList;
    private List<Ouqualify> pbQualifyList;
    private List<Ouperfcriteria> pbPerfcrList;
    private List<Syslog> pbHistActionList;
    private List<PositionBase> pbSelectList;
    private SysConfigManager dbConfigManager;

    public pbManageAction() {
        this.jobtitleList = null;

        this.lastOperate = null;

        this.pcTypeMap = ComonBeans.getValuesToMap("pcType", new boolean[] { false });

        this.pb = new PositionBase();

        this.pbSelectList = new ArrayList();
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String execute() throws Exception {
        return "success";
    }

    public String pbBasicInfo() {
        Map dbMap = this.dbConfigManager.getProperties();
        this.exceedConfig = ((String) dbMap.get("sys.position.max.exceed"));
        if (this.exceedConfig == null) {
            this.exceedConfig = "1";
        }

        IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
        this.jobtitleList = jobTitleBo.FindAllJobTitle();
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        String[] fetch = { PositionBase.PROP_PB_DEPT_ID, PositionBase.PROP_PB_SUP_ID };

        if (this.pbId == null) {
            if ((StringUtils.isEmpty(this.deptId)) && (StringUtils.isNotEmpty(this.parentPbId))) {
                PositionBase parentPb = pbBo.loadPb(this.parentPbId, fetch);
                this.deptId = parentPb.getPbDeptId().getId();
                this.pb.setPbDeptId(parentPb.getPbDeptId());
                this.pb.setPbSupId(parentPb);
            } else if ((StringUtils.isEmpty(this.parentPbId))
                    && (StringUtils.isNotEmpty(this.deptId))) {
                IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
                Department dept = deptBo.getDeptById(this.deptId);
                this.pb.setPbDeptId(dept);
            }
            this.lastOperate = null;
            initSelectPbList();
            return "success";
        }

        this.pb = pbBo.loadPb(this.pbId, fetch);
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List empListByPb = empBo.getPbActiveEmpNos(this.pb.getId());
        int activeEmpNum = empListByPb.size();

        if (this.pb.getPbMaxEmployee() == null) {
            this.pb.setLackEmpNum(null);
        } else {
            int lackNum = this.pb.getPbMaxEmployee().intValue() - activeEmpNum;
            if (lackNum >= 0)
                this.pb.setLackEmpNum(Integer.valueOf(lackNum));
            else {
                this.pb.setLackEmpNum(Integer.valueOf(0));
            }
        }
        this.pb.setActiveEmpNum(Integer.valueOf(activeEmpNum));
        initSelectPbList();
        return "success";
    }

    private void initSelectPbList() {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");

        if (this.pb.getId() == null) {
            this.pbSelectList = pbBo.getActivePbsByDept(this.pb.getPbDeptId().getId());
        } else if (this.pb.getPbInCharge().intValue() == 1) {
            this.pbSelectList = pbBo.getSupActivePbsByDept(this.pb.getPbDeptId().getId());
        } else {
            this.pbSelectList = pbBo.getActivePbsByDept(this.pb.getPbDeptId().getId());
            Iterator pbIt = this.pbSelectList.iterator();
            while (pbIt.hasNext()) {
                PositionBase pbIn = (PositionBase) pbIt.next();
                if (pbIn.getId().equals(this.pb.getId()))
                    pbIt.remove();
            }
        }
    }

    public String checkPbName(String id, String pbName, String deptId) {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        if (pbBo.duplicateName(id, pbName, deptId)) {
            return "FAIL";
        }
        return "SUCC";
    }

    public String savePbBasicInfo() {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        initPb(this.pb);

        String operatorNo = getCurrentEmpNo();
        if ((this.pb.getPbInCharge().intValue() == 1)
                && (this.pb.getPbMaxEmployee().intValue() > 1)) {
            this.pbId = this.pb.getId();
            return "error";
        }

        pbBo.saveOrupdatePb(this.pb, operatorNo);
        this.pbId = this.pb.getId();
        return "success";
    }

    private void initPb(PositionBase pb) {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        SysConfigManager dbConfigManager = DatabaseSysConfigManager.getInstance();
        Map dbMap = dbConfigManager.getProperties();

        String exceed = (String) dbMap.get("sys.position.max.exceed");
        if (exceed == null) {
            exceed = "1";
        }

        if (StringUtils.isEmpty(pb.getId())) {
            this.lastOperate = "new";
            pb.setId(null);
            pb.setPbMaxExceed(Integer.valueOf(Integer.parseInt(exceed)));
            pb.setPbStatus(Integer.valueOf(1));
            pb.setPbInCharge(Integer.valueOf(0));
            pb.setPbSortId(pbBo.getNextPbSortIdOfDept(pb.getPbDeptId().getId()));
        } else {
            this.lastOperate = "modify";
            PositionBase oldPb = pbBo.loadPb(pb.getId(), null);
            if (oldPb.getPbMaxEmployee() == null) {
                if (pb.getPbMaxEmployee() != null) {
                    pb.setMaxEmpChanged(Boolean.valueOf(true));
                    pb.setHistMaxEmp(oldPb.getPbMaxEmployee());
                }
            } else if (!oldPb.getPbMaxEmployee().equals(pb.getPbMaxEmployee())) {
                pb.setMaxEmpChanged(Boolean.valueOf(true));
                pb.setHistMaxEmp(oldPb.getPbMaxEmployee());
            }

            pb.setPbMaxExceed(oldPb.getPbMaxExceed());
            pb.setPbStatus(oldPb.getPbStatus());
            pb.setPbInCharge(oldPb.getPbInCharge());
            pb.setPbSortId(oldPb.getPbSortId());
        }

        if ((pb.getPbSupId() == null) || (StringUtils.isEmpty(pb.getPbSupId().getId())))
            pb.setPbSupId(null);
    }

    public String showPbTable() {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        this.pbSelectList = pbBo.getAllPbsOfDept(new String[] { this.deptId });
        return "success";
    }

    public String savePbOrder(String[] ids) {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        if (pbBo.savePbOrder(ids)) {
            return "SUCC";
        }
        return "FAIL";
    }

    public String showPbHistAction() {
        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        this.pbHistActionList = logBO.getLogs("positionbase", this.pbId.trim());
        return "success";
    }

    public String pbResponse() {
        IOuresponseBo pbRespBo = (IOuresponseBo) SpringBeanFactory.getBean("ouresponseBo");
        this.pbRespList = pbRespBo.getPbRespByPbId(this.pbId);
        return "success";
    }

    public String addPbResp(Ouresponse pbResp) {
        IOuresponseBo pbRespBo = (IOuresponseBo) SpringBeanFactory.getBean("ouresponseBo");
        if (pbRespBo.addPbResp(pbResp)) {
            return pbResp.getId();
        }
        return "EXISTED";
    }

    public String updatePbResp(Ouresponse pbResp) {
        IOuresponseBo pbRespBo = (IOuresponseBo) SpringBeanFactory.getBean("ouresponseBo");
        if (pbRespBo.updatePbResp(pbResp)) {
            return "SUCC";
        }
        return "EXISTED";
    }

    public String delPbResp(String respId) {
        IOuresponseBo pbRespBo = (IOuresponseBo) SpringBeanFactory.getBean("ouresponseBo");
        if (pbRespBo.delPbResp(respId)) {
            return "SUCC";
        }
        return "FAIL";
    }

    public String savePbRespOrder(String[] ids) {
        IOuresponseBo pbRespBo = (IOuresponseBo) SpringBeanFactory.getBean("ouresponseBo");
        pbRespBo.savePbRespOrder(ids);
        return "SUCC";
    }

    public String pbPerfcr() {
        IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo) SpringBeanFactory
                .getBean("ouperfcriteriaBo");
        this.pbPerfcrList = perfcrBo.getPbPerfcriteria(this.pbId);
        return "success";
    }

    public String addPbPerfcr(Ouperfcriteria pbPerfcr) {
        IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo) SpringBeanFactory
                .getBean("ouperfcriteriaBo");
        if (perfcrBo.addPbPerfcr(pbPerfcr)) {
            return pbPerfcr.getId();
        }
        return "EXISTED";
    }

    public String updatePbPerfcr(Ouperfcriteria pbPerfcr) {
        IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo) SpringBeanFactory
                .getBean("ouperfcriteriaBo");
        if (perfcrBo.updatePbPerfcr(pbPerfcr)) {
            return "SUCC";
        }
        return "EXISTED";
    }

    public String delPbPerfcr(String id) {
        IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo) SpringBeanFactory
                .getBean("ouperfcriteriaBo");
        if (perfcrBo.delPbPerfcr(id)) {
            return "SUCC";
        }
        return "FAIL";
    }

    public String savePbPerfcrOrder(String[] ids) {
        IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo) SpringBeanFactory
                .getBean("ouperfcriteriaBo");
        perfcrBo.savePbPerfcrOrder(ids);
        return "SUCC";
    }

    public String pbQualify() {
        IOuqualifyBo pbQualifyBo = (IOuqualifyBo) SpringBeanFactory.getBean("ouqualifyBo");
        this.pbQualifyList = pbQualifyBo.getpbQualifyByPbId(this.pbId);
        return "success";
    }

    public String addPbQualify(Ouqualify pbQualify) {
        IOuqualifyBo pbQualifyBo = (IOuqualifyBo) SpringBeanFactory.getBean("ouqualifyBo");
        if (pbQualifyBo.addPbQualify(pbQualify)) {
            return pbQualify.getId();
        }
        return "EXISTED";
    }

    public String updatePbQualify(Ouqualify pbQualify) {
        IOuqualifyBo pbQualifyBo = (IOuqualifyBo) SpringBeanFactory.getBean("ouqualifyBo");
        if (pbQualifyBo.updatePbQualify(pbQualify)) {
            return "SUCC";
        }
        return "EXISTED";
    }

    public String delPbQualify(String id) {
        IOuqualifyBo pbQualifyBo = (IOuqualifyBo) SpringBeanFactory.getBean("ouqualifyBo");
        if (pbQualifyBo.delPbQualify(id)) {
            return "SUCC";
        }
        return "FAIL";
    }

    public String savePbQualifyOrder(String[] ids) {
        IOuqualifyBo pbQualifyBo = (IOuqualifyBo) SpringBeanFactory.getBean("ouqualifyBo");
        pbQualifyBo.savePbQualifyOrder(ids);
        return "SUCC";
    }

    public String convertPbStatus(String id) {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");

        String operatorNo = getCurrentEmpNo();

        PositionBase pb = pbBo.loadPb(id, null);
        Integer status = pb.getPbStatus();
        if (status.intValue() == 1) {
            if (pbBo.disablePb(id).equals("SUCC")) {
                return "0";
            }
            return "FAIL";
        }

        if (pbBo.startPb(id).equals("SUCC")) {
            return "1";
        }
        return "FAIL";
    }

    public String delPb(String id) {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");

        PositionBase pb = pbBo.loadPb(id, null);
        if (pb == null) {
            return "职位不存在！";
        }
        if (pb.getPbInCharge().intValue() == 1) {
            return "负责职位不能删除";
        }

        String errorMsg = pbBo.delPb(id);
        return errorMsg;
    }

    public String getPbId() {
        return this.pbId;
    }

    public void setPbId(String pbId) {
        this.pbId = pbId;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public PositionBase getPb() {
        return this.pb;
    }

    public void setPb(PositionBase pb) {
        this.pb = pb;
    }

    public String getParentPbId() {
        return this.parentPbId;
    }

    public void setParentPbId(String parentPbId) {
        this.parentPbId = parentPbId;
    }

    public List<PositionBase> getPbSelectList() {
        return this.pbSelectList;
    }

    public void setPbSelectList(List<PositionBase> pbSelectList) {
        this.pbSelectList = pbSelectList;
    }

    public String getExceedConfig() {
        return this.exceedConfig;
    }

    public void setExceedConfig(String exceedConfig) {
        this.exceedConfig = exceedConfig;
    }

    public List<Ouresponse> getPbRespList() {
        return this.pbRespList;
    }

    public void setPbRespList(List<Ouresponse> pbRespList) {
        this.pbRespList = pbRespList;
    }

    public Map<String, String> getPcTypeMap() {
        return this.pcTypeMap;
    }

    public void setPcTypeMap(Map<String, String> pcTypeMap) {
        this.pcTypeMap = pcTypeMap;
    }

    public List<Ouqualify> getPbQualifyList() {
        return this.pbQualifyList;
    }

    public void setPbQualifyList(List<Ouqualify> pbQualifyList) {
        this.pbQualifyList = pbQualifyList;
    }

    public List<Syslog> getPbHistActionList() {
        return this.pbHistActionList;
    }

    public void setPbHistActionList(List<Syslog> pbHistActionList) {
        this.pbHistActionList = pbHistActionList;
    }

    public String getLastOperate() {
        return this.lastOperate;
    }

    public void setLastOperate(String lastOperate) {
        this.lastOperate = lastOperate;
    }

    public List<JobTitle> getJobtitleList() {
        return this.jobtitleList;
    }

    public void setJobtitleList(List<JobTitle> jobtitleList) {
        this.jobtitleList = jobtitleList;
    }

    public List<Ouperfcriteria> getPbPerfcrList() {
        return this.pbPerfcrList;
    }

    public void setPbPerfcrList(List<Ouperfcriteria> pbPerfcrList) {
        this.pbPerfcrList = pbPerfcrList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.pbManageAction JD-Core Version: 0.5.4
 */