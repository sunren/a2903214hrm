package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.bo.IPositionBaseHistBo;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.PositionBaseHist;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.SysConfigManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrgMapAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Date queryDate;
    private String queryDeptId;
    private Integer queryDepth;
    private String haveHist;
    private List<Department> deptList;
    private List<Integer> selectDepthList;
    private int treeDepth;
    private SysConfigManager dbConfigManager;

    public OrgMapAction() {
        this.deptList = new ArrayList();

        this.selectDepthList = new ArrayList();

        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String showOrgMap() {
        Map dbMap = this.dbConfigManager.getProperties();

        this.haveHist = ((String) dbMap.get("sys.profile.save.history"));
        AssembleDeptList assembler = AssebleDeptListFactory.creator(this.queryDate,
                                                                    this.queryDeptId,
                                                                    this.queryDepth);

        this.deptList = assembler.getList();

        this.queryDeptId = assembler.getQueryDeptId();
        this.treeDepth = assembler.getTreeDepth().intValue();
        setSelectDepthList();

        return "success";
    }

    private void setSelectDepthList() {
        for (int i = 2; i <= this.treeDepth; ++i)
            this.selectDepthList.add(Integer.valueOf(i));
    }

    public List<PositionBase> getPbsByDept(String deptId, String queryDate) {
        List pbList = new ArrayList();
        IPositionBaseBo positionBaseBo = (IPositionBaseBo) SpringBeanFactory
                .getBean("positionBaseBo");

        if (queryDate == null) {
            pbList = positionBaseBo.getActivePbsByDept(deptId);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date queryDateForPb;
            try {
                queryDateForPb = dateFormat.parse(queryDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
            IPositionBaseHistBo pbhBo = (IPositionBaseHistBo) SpringBeanFactory
                    .getBean("positionBaseHistBo");

            List pbhList = pbhBo.getRequirePbhList(queryDateForPb, deptId);
            for (int i = 0; i < pbhList.size(); ++i) {
                PositionBaseHist pbh = (PositionBaseHist) pbhList.get(i);
                pbList.add(positionBaseBo.loadPb(pbh.getPbhiPbId().getId(),
                                                 new String[] { PositionBase.PROP_PB_DEPT_ID }));
            }
        }

        return pbList;
    }

    public Date getQueryDate() {
        return this.queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public String getQueryDeptId() {
        return this.queryDeptId;
    }

    public void setQueryDeptId(String queryDeptId) {
        this.queryDeptId = queryDeptId;
    }

    public Integer getQueryDepth() {
        return this.queryDepth;
    }

    public void setQueryDepth(Integer queryDepth) {
        this.queryDepth = queryDepth;
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public List<Integer> getSelectDepthList() {
        return this.selectDepthList;
    }

    public void setSelectDepthList(List<Integer> selectDepthList) {
        this.selectDepthList = selectDepthList;
    }

    public String getHaveHist() {
        return this.haveHist;
    }

    public void setHaveHist(String haveHist) {
        this.haveHist = haveHist;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.OrgMapAction JD-Core Version: 0.5.4
 */