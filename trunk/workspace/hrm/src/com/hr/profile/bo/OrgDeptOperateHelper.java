package com.hr.profile.bo;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.dao.IDepartmentDAO;
import com.hr.configuration.domain.Department;
import com.hr.profile.domain.Depthist;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.PositionBaseHist;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class OrgDeptOperateHelper {
    String msgDisablePB;
    String msgDisableDept;
    String msgEnablePB;
    String msgEnableDept;
    String msgRenameDept;
    String msgMoveDept;
    String msgMovePB;
    String msgMovePBDept;
    String msgPBExistInEmpOrgHist;
    String msgDeptExistInEmpOrgHist;
    String msgDeptExistInDeptHist;
    String msgDeptExistInPBHist;
    String msgPBIsUsed;
    String msgDeletePBIsUsed;
    String msgDepIsUsed;
    String msgDeleteDepIsUsed;
    String msgPBExceed;

    public OrgDeptOperateHelper() {
        this.msgDisablePB = "{0}被停用�1�7�1�7";
        this.msgDisableDept = "{0}被停用�1�7�1�7";
        this.msgEnablePB = "{0}被启用�1�7�1�7";
        this.msgEnableDept = "{0}被启用�1�7�1�7";
        this.msgRenameDept = "部门名称由{0}改为{1}〄1�7";
        this.msgMoveDept = "部门{0}从{1}移动到{2}〄1�7";
        this.msgMovePB = "职位{0}从{1}移动到{2}";
        this.msgMovePBDept = "职位{0}从部门{1}移动到部门{2}〄1�7";
        this.msgPBExistInEmpOrgHist = "{0}在员工组织结构历史中存在，不能删除！";
        this.msgDeptExistInEmpOrgHist = "{0}在员工组织结构历史中存在，不能删除！";
        this.msgDeptExistInDeptHist = "{0}在组织结构历史中为{1}的父节点，不能删除！";
        this.msgDeptExistInPBHist = "{0}在职位{1}历史中存在，不能删除＄1�7";
        this.msgPBIsUsed = "该部闄1�7(及下属部闄1�7)有{0}职位有人在职，不能停用！";
        this.msgDeletePBIsUsed = "该部门有{0}职位有人在职，不能删除！";
        this.msgDepIsUsed = "该部闄1�7(及下属部闄1�7)有{0}有人在职，不能停用！";
        this.msgDeleteDepIsUsed = "该部闄1�7(及下属部闄1�7)有{0}部门有人在职，不能删除！";
        this.msgPBExceed = "部门({0})下的({1})职位超编＄1�7";
    }

    public String operateDeleteDept(String deptId) {
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");

        List allSubDeptList = deptBo.getAllSubDepts(null, new String[] { deptId });

        Department currentDept = null;
        for (int i = 0; (allSubDeptList != null) && (i < allSubDeptList.size()); ++i) {
            currentDept = (Department) allSubDeptList.get(i);
            if (deptId.equals(currentDept.getId())) {
                break;
            }
        }
        List directSubDeptList = new ArrayList();
        for (int i = 0; (allSubDeptList != null) && (i < allSubDeptList.size()); ++i) {
            Department tempDept = (Department) allSubDeptList.get(i);
            if (!currentDept.getId().equals(tempDept.getDepartmentParentId().getId()))
                continue;
            directSubDeptList.add(tempDept);
        }

        for (int i = 0; i < directSubDeptList.size(); ++i) {
            Department directSubDept = (Department) directSubDeptList.get(i);
            String tempInfo = operateDeleteDept(directSubDept.getId());
            if (!"SUCC".equals(tempInfo))
                return tempInfo;

        }

        List pbList = pbBo.getAllPbsOfDept(new String[] { deptId });
        for (int i = 0; i < pbList.size(); ++i) {
            PositionBase pb = (PositionBase) pbList.get(i);
            String tempInfo = operateDeletePB(pb);
            if (!tempInfo.equals("SUCC"))
                return tempInfo;

        }

        String tempInfo = operateDeleteCurrentDept(currentDept);
        if (!tempInfo.equals("SUCC"))
            return tempInfo;

        return "SUCC";
    }

    public String operateDeletePB(PositionBase pb) {
        IDepartmentDAO dao = (IDepartmentDAO) SpringBeanFactory.getBean("departmentDAO");

        String info = "SUCC";

        String hql = "select count(*) from Employee where empPbNo.id='" + pb.getId() + "'";
        List list = dao.exeHqlList(hql);
        if ((list != null) && (list.get(0) != null)
                && (((Long) (Long) list.get(0)).longValue() > new Long(0L).longValue()))
            info = info
                    + StringUtil.message(this.msgDeletePBIsUsed, new Object[] { pb.getPbName() });

        hql = "select count(*) from EmpHistOrg where ehoPbNo.id='" + pb.getId() + "'";
        list = dao.exeHqlList(hql);
        if ((list != null) && (list.get(0) != null)
                && (((Long) (Long) list.get(0)).longValue() > new Long(0L).longValue()))
            info = info
                    + StringUtil.message(this.msgPBExistInEmpOrgHist,
                                         new Object[] { pb.getPbName() });

        if (!info.equals("SUCC"))
            return info.replace("SUCC", "");

        hql = "update PositionBase set pbSupId.id=null where pbSupId='" + pb.getId() + "'";
        dao.exeHql(hql);

        hql = "delete from Position where positionPbId.id='" + pb.getId() + "'";
        dao.exeHql(hql);

        hql = "delete from Ouresponse where ourPb.id='" + pb.getId() + "'";
        dao.exeHql(hql);

        hql = "delete from Ouperfcriteria where oupPb.id='" + pb.getId() + "'";
        dao.exeHql(hql);

        hql = "delete from Ouqualify where ouqPbId.id='" + pb.getId() + "'";
        dao.exeHql(hql);

        operateDeleteLogsById(pb.getId());

        hql = "delete from PositionBaseHist where pbhiPbId.id='" + pb.getId() + "'";
        dao.exeHql(hql);
        hql = "delete from PositionBase where id='" + pb.getId() + "'";
        dao.exeHql(hql);

        return info;
    }

    public String operateDeleteCurrentDept(Department currentDept) {
        IDepartmentDAO dao = (IDepartmentDAO) SpringBeanFactory.getBean("departmentDAO");

        String info = "SUCC";

        String hql = "select count(*) from Employee where empDeptNo.id='" + currentDept.getId()
                + "'";
        List list = dao.exeHqlList(hql);
        if ((list != null) && (list.get(0) != null)
                && (((Long) (Long) list.get(0)).longValue() > new Long(0L).longValue()))
            info = info
                    + StringUtil.message(this.msgDeleteDepIsUsed, new Object[] { currentDept
                            .getDepartmentName() });

        hql = "select count(*) from EmpHistOrg where ehoDeptNo.id = '" + currentDept.getId() + "'";
        list = dao.exeHqlList(hql);
        if ((list != null) && (list.get(0) != null)
                && (((Long) (Long) list.get(0)).longValue() > new Long(0L).longValue()))
            info = info
                    + StringUtil.message(this.msgDeptExistInEmpOrgHist, new Object[] { currentDept
                            .getDepartmentName() });

        hql = "select distinct(hist.dhiDeptName) from Depthist hist left join hist.dhiDeptSupId deptSup where deptSup.id='"
                + currentDept.getId() + "'";
        list = dao.exeHqlList(hql);
        String subDeptNameStr = "";
        for (int i = 0; (list != null) && (i < list.size()); ++i) {
            subDeptNameStr = subDeptNameStr + (String) list.get(i) + ",";
        }
        if (subDeptNameStr.trim().length() > 0)
            info = info
                    + StringUtil.message(this.msgDeptExistInDeptHist, new Object[] {
                            currentDept.getDepartmentName(),
                            subDeptNameStr.substring(0, subDeptNameStr.length() - 1) });

        hql = "select distinct(pb.pbName) from PositionBaseHist hist left join hist.pbhiPbId pb where hist.pbhiDeptId.id='"
                + currentDept.getId() + "'";
        list = dao.exeHqlList(hql);
        String pbNameStr = "";
        for (int i = 0; (list != null) && (i < list.size()); ++i) {
            pbNameStr = pbNameStr + (String) list.get(i) + ",";
        }
        if (pbNameStr.trim().length() > 0)
            info = info
                    + StringUtil.message(this.msgDeptExistInPBHist, new Object[] {
                            currentDept.getDepartmentName(),
                            pbNameStr.substring(0, pbNameStr.length() - 1) });

        if (!info.trim().equals("SUCC")) {
            return info.replaceAll("SUCC", "");
        }

        hql = "delete from Ouresponse where ourDept.id='" + currentDept.getId() + "'";
        dao.exeHql(hql);

        hql = "delete from Depthist where dhiDeptNo.id='" + currentDept.getId() + "'";
        dao.exeHql(hql);
        hql = "delete from Department where id='" + currentDept.getId() + "'";
        dao.exeHql(hql);

        operateDeleteLogsById(currentDept.getId());

        return info;
    }

    public String operateDisableDept(String deptId) {
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");

        List allSubDeptList = deptBo.getAllSubDepts(new Integer(1), new String[] { deptId });

        Department currentDept = null;
        for (int i = 0; (allSubDeptList != null) && (i < allSubDeptList.size()); ++i) {
            currentDept = (Department) allSubDeptList.get(i);
            if (deptId.equals(currentDept.getId())) {
                break;
            }
        }
        List directSubDeptList = new ArrayList();
        for (int i = 0; (allSubDeptList != null) && (i < allSubDeptList.size()); ++i) {
            Department tempDept = (Department) allSubDeptList.get(i);
            if (tempDept.getDepartmentStatus().intValue() != 0) {
                if (!currentDept.getId().equals(tempDept.getDepartmentParentId().getId()))
                    continue;
                directSubDeptList.add(tempDept);
            }
        }

        for (int i = 0; i < directSubDeptList.size(); ++i) {
            Department directSubDept = (Department) directSubDeptList.get(i);
            String tempInfo = operateDisableDept(directSubDept.getId());
            if (!"SUCC".equals(tempInfo))
                return tempInfo;

        }

        List pbList = pbBo.getActivePbsByDept(deptId);
        for (int i = 0; i < pbList.size(); ++i) {
            PositionBase pb = (PositionBase) pbList.get(i);
            String tempInfo = operateDisablePB(pb);
            if (!tempInfo.equals("SUCC"))
                return tempInfo;

        }

        String tempInfo = operateDisableCurrentDept(currentDept);
        if (!tempInfo.equals("SUCC"))
            return tempInfo;

        return "SUCC";
    }

    public String operateDisablePB(PositionBase pb) {
        IDepartmentDAO dao = (IDepartmentDAO) SpringBeanFactory.getBean("departmentDAO");

        String info = "SUCC";

        String hql = "select count(*) from Employee where empPbNo.id='" + pb.getId()
                + "' and empStatus = 1";
        List list = dao.exeHqlList(hql);
        if ((list != null) && (list.get(0) != null)
                && (((Long) (Long) list.get(0)).longValue() > new Long(0L).longValue()))
            info = info + StringUtil.message(this.msgPBIsUsed, new Object[] { pb.getPbName() });

        if (!info.equals("SUCC"))
            return info.replace("SUCC", "");

        hql = "update PositionBase set pbSupId.id=null where pbSupId='" + pb.getId() + "'";
        dao.exeHql(hql);

        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.createAlias(Position.PROP_POSITION_PB_ID, "positionPbId", 1);
        dc.add(Restrictions.ne("positionPbId." + PositionBase.PROP_PB_IN_CHARGE, new Integer(1)));
        dc.add(Restrictions.eq("positionPbId." + PositionBase.PROP_ID, pb.getId()));
        List nonRespPosList = dao.findByCriteria(dc);
        dao.deleteCollection(nonRespPosList);

        pb.setPbStatus(new Integer(0));

        operateSavePBOwnAndHist(pb);

        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        String msg = StringUtil.message(this.msgDisablePB, new Object[] { pb.getPbName() });
        logBO.addToSyslog("positionbase", getCurrentEmp().getId(), null, pb.getId(), 0, "停用", msg);

        return "SUCC";
    }

    public String operateEnablePB(PositionBase pb) {
        String info = "SUCC";

        pb.setPbStatus(new Integer(1));

        operateSavePBOwnAndHist(pb);

        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        String msg = StringUtil.message(this.msgEnablePB, new Object[] { pb.getPbName() });
        logBO.addToSyslog("positionbase", getCurrentEmp().getId(), null, pb.getId(), 0, "启用", msg);

        return info;
    }

    public String operateDisableCurrentDept(Department dept) {
        IDepartmentDAO dao = (IDepartmentDAO) SpringBeanFactory.getBean("departmentDAO");
        IDeptHistBo deptHistBo = (IDeptHistBo) SpringBeanFactory.getBean("deptHistBo");

        String info = "SUCC";

        String hql = "select count(*) from Employee where empDeptNo.id='" + dept.getId()
                + "' and empStatus = 1";
        List list = dao.exeHqlList(hql);
        if ((list != null) && (list.get(0) != null)
                && (((Long) (Long) list.get(0)).longValue() > new Long(0L).longValue()))
            info = info
                    + StringUtil.message(this.msgDepIsUsed,
                                         new Object[] { dept.getDepartmentName() });

        if (!info.equals("SUCC"))
            return info.replace("SUCC", "");

        dept.setDepartmentStatus(new Integer(0));

        operateSaveDeptOwnAndHist(dept);

        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        String msg = StringUtil.message(this.msgDisableDept, new Object[] { dept
                .getDepartmentName() });
        logBO.addToSyslog("department", getCurrentEmp().getId(), null, dept.getId(), 0, "停用", msg);

        return info;
    }

    public String operateEnableCurrentDept(Department dept) {
        String info = "SUCC";

        dept.setDepartmentStatus(new Integer(1));

        operateSaveDeptOwnAndHist(dept);

        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        String msg = StringUtil.message(this.msgEnableDept,
                                        new Object[] { dept.getDepartmentName() });
        logBO.addToSyslog("department", getCurrentEmp().getId(), null, dept.getId(), 0, "启用", msg);

        return info;
    }

    public String operateDeptRename(Department dept) {
        IDepartmentDAO dao = (IDepartmentDAO) SpringBeanFactory.getBean("departmentDAO");
        IDeptHistBo deptHistBo = (IDeptHistBo) SpringBeanFactory.getBean("deptHistBo");

        String info = "SUCC";

        String hql = "select departmentName from Department where id='" + dept.getId() + "'";
        List list = dao.exeHqlList(hql);
        String lastName = ((list != null) && (list.get(0) != null)) ? (String) list.get(0) : "";

        operateSaveDeptOwnAndHist(dept);

        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        String msg = StringUtil.message(this.msgRenameDept, new Object[] { lastName,
                dept.getDepartmentName() });
        logBO.addToSyslog("department", getCurrentEmp().getId(), null, dept.getId(), 0, "更名", msg);

        return info;
    }

    public String operateDeptMove(Department fromDept, Department toDept) {
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        IDepartmentDAO dao = (IDepartmentDAO) SpringBeanFactory.getBean("departmentDAO");

        String lastParentName = fromDept.getDepartmentParentId().getDepartmentName();
        fromDept.setDepartmentParentId(toDept);

        operateSaveDeptOwnAndHist(fromDept);

        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        String msg = StringUtil.message(this.msgMoveDept, new Object[] {
                fromDept.getDepartmentName(), lastParentName, toDept.getDepartmentName() });
        logBO.addToSyslog("department", getCurrentEmp().getId(), null, fromDept.getId(), 0, "移动",
                          msg);

        PositionBase fromRespPB = pbBo.getRespPBofDept(fromDept.getId());
        String lastPBSupName = (fromRespPB.getPbSupId() != null) ? fromRespPB.getPbSupId()
                .getPbName() : "";
        PositionBase toRespPB = pbBo.getRespPBofDept(toDept.getId());
        if (fromRespPB != null)
            fromRespPB.setPbSupId(toRespPB);

        operateSavePBOwnAndHist(fromRespPB);

        msg = StringUtil.message(this.msgMovePB, new Object[] { fromRespPB.getPbName(),
                lastPBSupName, toRespPB.getPbName() });
        logBO.addToSyslog("positionbase", getCurrentEmp().getId(), null, fromRespPB.getId(), 0,
                          "移动", msg);

        Position respPos = posBo.getRespPosOfDept(fromDept.getId());
        Position respPosParent = posBo.getRespPosOfDept(fromDept.getDepartmentParentId().getId());
        if (respPos != null)
            respPos.setPositionParentId(respPosParent);
        dao.saveOrupdate(respPos);

        return "SUCC";
    }

    public boolean operateSaveDeptOwnAndHist(Department dept) {
        IDepartmentDAO dao = (IDepartmentDAO) SpringBeanFactory.getBean("departmentDAO");
        IDeptHistBo deptHistBo = (IDeptHistBo) SpringBeanFactory.getBean("deptHistBo");

        Depthist oldDeptHist = deptHistBo.getLatestHistOfDept(dept);
        if (oldDeptHist != null)
            oldDeptHist.setDhiValidTo(new Date());
        Depthist newDeptHist = new Depthist(null, dept, dept.getDepartmentParentId(), dept
                .getDepartmentName(), dept.getDepartmentStatus(), new Date());

        if (dept != null)
            dao.saveOrupdate(dept);
        if (oldDeptHist != null)
            dao.saveOrupdate(oldDeptHist);
        if (newDeptHist != null)
            dao.saveOrupdate(newDeptHist);

        return true;
    }

    public boolean operateSavePBOwnAndHist(PositionBase pb) {
        IPositionBaseHistBo positionBaseHistBo = (IPositionBaseHistBo) SpringBeanFactory
                .getBean("positionBaseHistBo");
        IDepartmentDAO dao = (IDepartmentDAO) SpringBeanFactory.getBean("departmentDAO");

        PositionBaseHist oldPBHist = positionBaseHistBo.getLatestPBHist(pb);
        PositionBaseHist newPBHist = null;
        if (oldPBHist != null)
            oldPBHist.setPbhiValidTo(new Date());
        newPBHist = new PositionBaseHist(null, pb, pb.getPbDeptId(), pb.getPbStatus(), pb
                .getPbMaxEmployee(), pb.getPbInCharge(), new Date(), null);

        if (pb != null)
            dao.saveOrupdate(pb);
        if (oldPBHist != null)
            dao.saveOrupdate(oldPBHist);
        if (newPBHist != null)
            dao.saveOrupdate(newPBHist);

        return true;
    }

    public Employee getCurrentEmp() {
        HttpSession session = null;
        if (WebContextFactory.get() != null)
            session = WebContextFactory.get().getSession();
        else if (ServletActionContext.getRequest() != null) {
            session = ServletActionContext.getRequest().getSession();
        }
        Employee currentEmp = ((Userinfo) session.getAttribute("userinfo")).getEmployee();

        return currentEmp;
    }

    public boolean operateDeleteLogsById(String id) {
        IDepartmentDAO dao = (IDepartmentDAO) SpringBeanFactory.getBean("departmentDAO");

        String hql = "delete from Syslog where slRecordId='" + id + "'";
        dao.exeHql(hql);

        return true;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.OrgDeptOperateHelper JD-Core Version: 0.5.4
 */