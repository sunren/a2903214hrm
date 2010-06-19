package com.hr.profile.action;

import com.hr.base.BaseAction;
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
import com.hr.util.MyTools;
import com.hr.util.SysConfigManager;
import java.util.List;
import java.util.Map;

public class PbManualAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String pbId;
    private PositionBase pb;
    private String empNameListString;
    private List<Ouresponse> pbRespList;
    private List<Ouqualify> pbQualifyList;
    private List<Ouperfcriteria> pbPerfcrList;
    private String exceedConfig;
    private SysConfigManager dbConfigManager;

    public PbManualAction() {
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String showPbManual() {
    Map<?, ?> dbMap = this.dbConfigManager.getProperties();
    this.exceedConfig = ((String)dbMap.get("sys.position.max.exceed"));
    if (this.exceedConfig == null) {
      this.exceedConfig = "1";
    }
    IPositionBaseBo pbBo = (IPositionBaseBo)SpringBeanFactory.getBean("positionBaseBo");
    String[] fetch = { PositionBase.PROP_PB_DEPT_ID, PositionBase.PROP_PB_SUP_ID };
    this.pb = pbBo.loadPb(this.pbId, fetch);

    IEmployeeBo empBo = (IEmployeeBo)SpringBeanFactory.getBean("empBo");
    List<?> empNameList = empBo.getPbActiveEmpNos(this.pb.getId());
    this.empNameListString = MyTools.arrayToStr(empNameList.toArray(), '\u3001');

    IOuresponseBo pbRespBo = (IOuresponseBo)SpringBeanFactory.getBean("ouresponseBo");
    this.pbRespList = pbRespBo.getPbRespByPbId(this.pbId);

    IOuqualifyBo pbQualifyBo = (IOuqualifyBo)SpringBeanFactory.getBean("ouqualifyBo");
    this.pbQualifyList = pbQualifyBo.getpbQualifyByPbId(this.pbId);

    IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo)SpringBeanFactory.getBean("ouperfcriteriaBo");
    this.pbPerfcrList = perfcrBo.getPbPerfcriteria(this.pbId);
    return "success";
  }

    public String getPbId() {
        return this.pbId;
    }

    public void setPbId(String pbId) {
        this.pbId = pbId;
    }

    public PositionBase getPb() {
        return this.pb;
    }

    public void setPb(PositionBase pb) {
        this.pb = pb;
    }

    public String getExceedConfig() {
        return this.exceedConfig;
    }

    public void setExceedConfig(String exceedConfig) {
        this.exceedConfig = exceedConfig;
    }

    public String getEmpNameListString() {
        return this.empNameListString;
    }

    public void setEmpNameListString(String empNameListString) {
        this.empNameListString = empNameListString;
    }

    public List<Ouresponse> getPbRespList() {
        return this.pbRespList;
    }

    public void setPbRespList(List<Ouresponse> pbRespList) {
        this.pbRespList = pbRespList;
    }

    public List<Ouqualify> getPbQualifyList() {
        return this.pbQualifyList;
    }

    public void setPbQualifyList(List<Ouqualify> pbQualifyList) {
        this.pbQualifyList = pbQualifyList;
    }

    public List<Ouperfcriteria> getPbPerfcrList() {
        return this.pbPerfcrList;
    }

    public void setPbPerfcrList(List<Ouperfcriteria> pbPerfcrList) {
        this.pbPerfcrList = pbPerfcrList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.PbManualAction JD-Core Version: 0.5.4
 */