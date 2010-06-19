package com.hr.profile.bo;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.Department;
import com.hr.profile.dao.IPositionBaseDao;
import com.hr.profile.domain.Ouperfcriteria;
import com.hr.profile.domain.Ouqualify;
import com.hr.profile.domain.Ouresponse;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.PositionBaseHist;
import com.hr.profile.domain.base.TreeNode;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.StringUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class PositionBaseBoImpl implements IPositionBaseBo {
    private OrgDeptOperateHelper helper;
    private IPositionBaseDao positionBaseDao;

    public PositionBaseBoImpl() {
        this.helper = new OrgDeptOperateHelper();
    }

    public IPositionBaseDao getPositionBaseDao() {
        return this.positionBaseDao;
    }

    public void setPositionBaseDao(IPositionBaseDao positionBaseDao) {
        this.positionBaseDao = positionBaseDao;
    }

    public List<PositionBase> findAllActivePb() {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.add(Restrictions.eq(PositionBase.PROP_PB_STATUS, Integer.valueOf(1)));
        dc.addOrder(Order.asc(PositionBase.PROP_PB_SORT_ID));
        return this.positionBaseDao.findByCriteria(dc);
    }

    public List<PositionBase> findAllPbs() {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.addOrder(Order.asc(PositionBase.PROP_PB_SORT_ID));
        return this.positionBaseDao.findByCriteria(dc);
    }

    public List<PositionBase> findAllRespPb() {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.add(Restrictions.eq(PositionBase.PROP_PB_IN_CHARGE, Integer.valueOf(1)));
        dc.add(Restrictions.eq(PositionBase.PROP_PB_STATUS, Integer.valueOf(1)));
        return this.positionBaseDao.findByCriteria(dc);
    }

    public List<PositionBase> getActivePbsByDept(String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.setFetchMode(PositionBase.PROP_PB_DEPT_ID, FetchMode.JOIN);
        dc.setFetchMode(PositionBase.PROP_PB_SUP_ID, FetchMode.JOIN);
        dc.add(Restrictions.eq(PositionBase.PROP_PB_STATUS, Integer.valueOf(1)));
        dc.add(Restrictions.eq(PositionBase.PROP_PB_DEPT_ID, new Department(deptId)));
        dc.addOrder(Order.asc(PositionBase.PROP_PB_SORT_ID));
        return this.positionBaseDao.findByCriteria(dc);
    }

    public List<PositionBase> getSupActivePbsByDept(String deptId) {
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        Department parentDept = deptBo.getDeptById(deptId).getDepartmentParentId();
        if (parentDept == null) {
            return null;
        }
        return getActivePbsByDept(parentDept.getId());
    }

    public PositionBase loadPb(Object id, String[] fetch) {
        return (PositionBase) this.positionBaseDao.loadObject(PositionBase.class, id, fetch,
                                                              new boolean[] { false });
    }

    public List<PositionBase> getAllPbsOfDept(String[] deptArr) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.setFetchMode(PositionBase.PROP_PB_DEPT_ID, FetchMode.JOIN);
        dc.setFetchMode(PositionBase.PROP_PB_SUP_ID, FetchMode.JOIN);
        dc.add(Restrictions.in(PositionBase.PROP_PB_DEPT_ID + ".id", deptArr));
        dc.addOrder(Order.asc(PositionBase.PROP_PB_SORT_ID));
        List pbList = this.positionBaseDao.findByCriteria(dc);

        return pbList;
    }

    public PositionBase getRespPBofDept(String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.createAlias(PositionBase.PROP_PB_SUP_ID, "pbSupId", 1);
        dc.add(Restrictions.eq(PositionBase.PROP_PB_DEPT_ID + ".id", deptId));
        dc.add(Restrictions.eq(PositionBase.PROP_PB_IN_CHARGE, new Integer(1)));
        List pbList = this.positionBaseDao.findByCriteria(dc);

        if (pbList.size() > 0)
            return (PositionBase) pbList.get(0);
        return null;
    }

    public PositionBase getPBById(String pbId) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.setFetchMode(PositionBase.PROP_PB_DEPT_ID, FetchMode.JOIN);
        dc.add(Restrictions.eq(PositionBase.PROP_ID, pbId));
        List pbList = this.positionBaseDao.findByCriteria(dc);

        if (pbList.size() > 0)
            return (PositionBase) pbList.get(0);
        return null;
    }

    public List<PositionBase> getPbsOfDepts(List<Department> depts) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.add(Restrictions.in(PositionBase.PROP_PB_DEPT_ID, depts));
        dc.addOrder(Order.asc(PositionBase.PROP_PB_SORT_ID));
        List pbList = this.positionBaseDao.findByCriteria(dc);
        return pbList;
    }

    public synchronized Integer getMaxSortId() {
        String hql = "select max(pbSortId) from PositionBase";
        List maxNoList = this.positionBaseDao.exeHqlList(hql);
        Integer maxID;
        if ((maxNoList == null) || (maxNoList.size() < 1) || (maxNoList.get(0) == null))
            maxID = Integer.valueOf(1);
        else {
            maxID = Integer.valueOf(1 + ((Integer) (Integer) maxNoList.get(0)).intValue());
        }
        return maxID;
    }

    public synchronized Integer getNextPbSortIdOfDept(String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.add(Restrictions.eq(PositionBase.PROP_PB_DEPT_ID, new Department(deptId)));
        int count = this.positionBaseDao.findRowCountByCriteria(dc);
        count += 1;
        return Integer.valueOf(count);
    }

    public void saveOrupdatePb(PositionBase pb, String operatorNo) {
        String msgNewLog = "创建({0})职位，编制为({1})人！";
        String msgMaxEmpLog = "原编制({0})人，新编制({1})人。";
        PositionBaseHist pbHist = null;
        PositionBaseHist latestPbHist = null;
        boolean isNew = false;

        if (StringUtils.isEmpty(pb.getId())) {
            isNew = true;
            initPbHist(pb, pbHist, latestPbHist);
        } else if (pb.getMaxEmpChanged().booleanValue()) {
            initPbHist(pb, pbHist, latestPbHist);
        }

        this.positionBaseDao.saveOrupdate(pb);
        if (latestPbHist != null) {
            this.positionBaseDao.saveOrupdate(latestPbHist);
        }
        if (pbHist != null) {
            this.positionBaseDao.saveOrupdate(pbHist);
        }

        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        if (isNew) {
            msgNewLog = StringUtil.message(msgNewLog, new Object[] { pb.getPbName(),
                    pb.getPbMaxEmployee() });
            logBO.addToSyslog("positionbase", operatorNo, null, pb.getId(), 0, "新建", msgNewLog);
        } else if (pb.getMaxEmpChanged().booleanValue()) {
            msgMaxEmpLog = StringUtil.message(msgMaxEmpLog, new Object[] { pb.getHistMaxEmp(),
                    pb.getPbMaxEmployee() });
            logBO
                    .addToSyslog("positionbase", operatorNo, null, pb.getId(), 0, "更改编制",
                                 msgMaxEmpLog);
        }
    }

    private void initPbHist(PositionBase pb, PositionBaseHist pbHist, PositionBaseHist latestPbHist) {
        if (pb.getId() != null) {
            IPositionBaseHistBo pbHistBo = (IPositionBaseHistBo) SpringBeanFactory
                    .getBean("positionBaseHistBo");

            latestPbHist = pbHistBo.getLatestPBHist(pb);
            if (latestPbHist != null) {
                latestPbHist.setPbhiValidTo(new Date());
            }

        }

        pbHist = new PositionBaseHist();
        pbHist.setPbhiDeptId(pb.getPbDeptId());
        pbHist.setPbhiInCharge(pb.getPbInCharge());
        pbHist.setPbhiMaxEmployee(pb.getPbMaxEmployee());
        pbHist.setPbhiPbId(pb);
        pbHist.setPbhiPbStatus(pb.getPbStatus());
        pbHist.setPbhiValidFrom(new Date());
    }

    public String startPb(String id) {
        PositionBase targetPb = (PositionBase) this.positionBaseDao.loadObject(PositionBase.class,
                                                                               id, null,
                                                                               new boolean[0]);
        if ((targetPb == null) || (targetPb.getPbStatus().intValue() == 1)) {
            return "状态错误，请刷新页面后重试";
        }

        String info = this.helper.operateEnablePB(targetPb);
        return info;
    }

    public String disablePb(String id) {
        PositionBase targetPb = (PositionBase) this.positionBaseDao.loadObject(PositionBase.class,
                                                                               id, null,
                                                                               new boolean[0]);
        if ((targetPb == null) || (targetPb.getPbStatus().intValue() == 0)) {
            return "状态错误，请刷新页面后重试";
        }

        String info = this.helper.operateDisablePB(targetPb);
        return info;
    }

    public boolean duplicateName(String id, String pbName, String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.add(Restrictions.eq(PositionBase.PROP_PB_DEPT_ID, new Department(deptId.trim())));
        dc.add(Restrictions.eq(PositionBase.PROP_PB_NAME, pbName.trim()));

        if (StringUtils.isNotEmpty(id)) {
            dc.add(Restrictions.not(Restrictions.eq(PositionBase.PROP_ID, id.trim())));
        }
        List resultList = this.positionBaseDao.findByCriteria(dc);

        return (resultList != null) && (resultList.size() != 0);
    }

    public boolean savePbOrder(String[] ids) {
        if (ids == null)
            return false;
        int sort = 1;
        int len = ids.length;
        for (int i = 0; i < len; ++i) {
            this.positionBaseDao.exeHql("update PositionBase set pbSortId=" + sort + " where id ='"
                    + ids[i] + "'");

            ++sort;
        }
        return true;
    }

    public String delPb(String id) {
        PositionBase pb = loadPb(id, null);
        if (pb == null)
            return "数据错误，请刷新页面后重试！";

        String info = this.helper.operateDeletePB(pb);
        return info;
    }

    public List<PositionBase> getSelectSubList(String posId) {
        Position pos = (Position) this.positionBaseDao
                .loadObject(Position.class, posId, new String[] { Position.PROP_POSITION_PB_ID },
                            new boolean[0]);

        PositionBase pb = pos.getPositionPbId();
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        dc.setFetchMode(PositionBase.PROP_PB_DEPT_ID, FetchMode.JOIN);
        dc.add(Restrictions.eq(PositionBase.PROP_PB_IN_CHARGE, Integer.valueOf(0)));
        dc.add(Restrictions.eq(PositionBase.PROP_PB_DEPT_ID, pb.getPbDeptId()));
        dc.add(Restrictions.eq(PositionBase.PROP_PB_STATUS, Integer.valueOf(1)));
        dc.add(Restrictions.not(Restrictions.eq(PositionBase.PROP_ID, pb.getId())));

        List pbList = this.positionBaseDao.findByCriteria(dc);

        List supPbIds = getSupPBsOfPB(posId);
        Iterator pbItr = pbList.iterator();

        while (pbItr.hasNext()) {
            PositionBase pbIn = (PositionBase) pbItr.next();
            if (supPbIds.contains(pbIn.getId())) {
                pbItr.remove();
            }
        }
        return pbList;
    }

    public List<String> getSupPBsOfPB(String posId) {
        Position pos = (Position) this.positionBaseDao
                .loadObject(Position.class, posId, new String[] { Position.PROP_POSITION_PB_ID },
                            new boolean[0]);

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        List deptPosList = posBo.getPosListOfDept(pos.getPositionPbId().getPbDeptId().getId());

        List parentPBIdsList = new ArrayList();
        Position currentPos = pos;
        while (currentPos != null) {
            parentPBIdsList.add(currentPos.getPositionPbId().getId());
            String currentPosId;
            if (currentPos.getPositionParentId() == null)
                currentPosId = null;
            else {
                currentPosId = currentPos.getPositionParentId().getId();
            }
            boolean gotParent = false;
            for (int i = 0; (deptPosList != null) && (i < deptPosList.size()); ++i) {
                Position tempPos = (Position) deptPosList.get(i);
                if ((currentPosId != null) && (currentPosId.equals(tempPos.getId()))) {
                    currentPos = tempPos;
                    gotParent = true;
                    break;
                }
            }

            if (!gotParent) {
                break;
            }
        }
        return parentPBIdsList;
    }

    public boolean checkPBExceed(String pbId, int count) {
        PositionBase pb = loadPb(pbId, null);
        if ((pb.getPbMaxExceed().intValue() == 0) || (pb.getPbMaxExceed().intValue() == 2)
                || (pb.getPbMaxEmployee() == null))
            return false;

        Long nowCount = new Long(0L);
        String hql = "select count(*) from Position where positionPbId.id='" + pbId
                + "' and positionEmpNo.id is not null";
        List list = this.positionBaseDao.exeHqlList(hql);
        if ((list != null) && (list.get(0) != null))
            nowCount = (Long) list.get(0);

        return nowCount.intValue() + count > pb.getPbMaxEmployee().intValue();
    }

    public Map<String, List<PositionBase>> getDeptPBMap(String[] deptIds) {
        DetachedCriteria dc = DetachedCriteria.forClass(PositionBase.class);
        if ((deptIds != null) && (deptIds.length > 0)) {
            dc.add(Restrictions.in(PositionBase.PROP_PB_DEPT_ID + ".id", deptIds));
        }
        List pbList = this.positionBaseDao.findByCriteria(dc);

        Map deptPBMap = new HashMap();
        for (int i = 0; i < pbList.size(); ++i) {
            PositionBase pb = (PositionBase) pbList.get(i);
            List tempPBList = (List) deptPBMap.get(pb.getPbDeptId().getId());
            if (tempPBList == null) {
                tempPBList = new ArrayList();
                deptPBMap.put(pb.getPbDeptId().getId(), tempPBList);
            }
            tempPBList.add(pb);
        }

        return deptPBMap;
    }

    public void setPbOfNodeList(List<Department> depts, List<TreeNode> nodeList, int status) {
        List pbList = getPbsOfDepts(depts);

        for (int i = 0; (pbList != null) && (i < pbList.size()); ++i) {
            PositionBase pb = (PositionBase) pbList.get(i);
            pb.setPbSupId(new PositionBase(pb.getPbDeptId().getId()));
            TreeNode node;
            if (pb.getPbStatus().intValue() == 1) {
                node = new TreeNode(pb.getId(), pb.getPbName(), pb.getPbSupId().getId(), 3);
            } else {
                if (status == 0) {
                    continue;
                }
                node = new TreeNode(pb.getId(), pb.getPbName(), pb.getPbSupId().getId(), 4);
            }

            nodeList.add(i, node);
        }
    }

    public PositionBase copyPbToAnotherDept(String sourcePbId, String toDeptId, String operator) {
        PositionBase sourcePb = getPBById(sourcePbId);
        PositionBase newPb = new PositionBase();
        try {
            BeanUtils.copyProperties(newPb, sourcePb);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
        newPb.setId(null);
        newPb.setPbDeptId(new Department(toDeptId));
        newPb.setPbSupId(null);
        newPb.setPbInCharge(new Integer(0));

        if (duplicateName(null, newPb.getPbName(), toDeptId)) {
            newPb.setPbName(sourcePb.getPbName() + "(" + sourcePb.getPbDeptId().getDepartmentName()
                    + ")");
        }
        saveOrupdatePb(newPb, operator);

        IOuresponseBo pbRespBo = (IOuresponseBo) SpringBeanFactory.getBean("ouresponseBo");
        List<Ouresponse> pbRespList = pbRespBo.getPbRespByPbId(sourcePbId);
        for (Ouresponse pbResp : pbRespList) {
            Ouresponse newResp = new Ouresponse();
            newResp.setOurName(pbResp.getOurName());
            newResp.setOurRate(pbResp.getOurRate());
            newResp.setOurPb(newPb);
            newResp.setOurSortId(pbResp.getOurSortId());
            this.positionBaseDao.saveObject(newResp);
        }

        IOuperfcriteriaBo perfcrBo = (IOuperfcriteriaBo) SpringBeanFactory
                .getBean("ouperfcriteriaBo");
        List<Ouperfcriteria> pbPerfcrList = perfcrBo.getPbPerfcriteria(sourcePbId);
        for (Ouperfcriteria pbPerfcr : pbPerfcrList) {
            Ouperfcriteria newPbPerfcr = new Ouperfcriteria();
            newPbPerfcr.setOupName(pbPerfcr.getOupName());
            newPbPerfcr.setOupRate(pbPerfcr.getOupRate());
            newPbPerfcr.setOupSortId(pbPerfcr.getOupSortId());
            newPbPerfcr.setOupPb(newPb);
            this.positionBaseDao.saveObject(newPbPerfcr);
        }

        IOuqualifyBo pbQualifyBo = (IOuqualifyBo) SpringBeanFactory.getBean("ouqualifyBo");
        List<Ouqualify> pbQualifyList = pbQualifyBo.getpbQualifyByPbId(sourcePbId);
        for (Ouqualify pbQualify : pbQualifyList) {
            Ouqualify newPbQualify = new Ouqualify();
            newPbQualify.setOuqName(pbQualify.getOuqName());
            newPbQualify.setOuqDesc(pbQualify.getOuqDesc());
            newPbQualify.setOuqPbId(newPb);
            newPbQualify.setOuqSortId(pbQualify.getOuqSortId());
            this.positionBaseDao.saveObject(newPbQualify);
        }

        return newPb;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.PositionBaseBoImpl JD-Core Version: 0.5.4
 */