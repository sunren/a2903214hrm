package com.hr.configuration.bo;

import com.hr.configuration.dao.IDepartmentDAO;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IDeptHistBo;
import com.hr.profile.bo.IEmpHistOrgBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IOrgheadsBo;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.bo.OrgDeptMergeHelper;
import com.hr.profile.bo.OrgDeptOperateHelper;
import com.hr.profile.domain.Depthist;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emptransfer;
import com.hr.profile.domain.Orgheads;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.PositionBaseHist;
import com.hr.profile.domain.base.TreeNode;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import com.hr.util.Message;
import com.hr.util.ObjectUtil;
import com.hr.util.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DepartmentBOImpl implements IDepartmentBO {
    private IDepartmentDAO dao;
    private OrgDeptOperateHelper helper;

    public DepartmentBOImpl() {
        this.dao = null;

        this.helper = new OrgDeptOperateHelper();
    }

    public List<Department> FindAllDepartment() {
        DetachedCriteria dc = DetachedCriteria.forClass(Department.class);
        dc.addOrder(Order.asc("departmentSortId"));
        List deptList = this.dao.findByCriteria(dc);

        return deptList;
    }

    public List<Department> FindEnabledDepartment() {
        DetachedCriteria dc = DetachedCriteria.forClass(Department.class);
        dc.add(Restrictions.eq(Department.PROP_DEPARTMENT_STATUS, new Integer(1)));
        dc.addOrder(Order.asc("departmentSortId"));

        return this.dao.findByCriteria(dc);
    }

    public List<Department> getSubDeptsByParentId(String parentId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Department.class);
        dc.createAlias(Department.PROP_DEPARTMENT_PARENT_ID, "departmentParentId", 1);
        dc.add(Restrictions.eq(Department.PROP_DEPARTMENT_STATUS, new Integer(1)));
        dc.add(Restrictions.eq(Department.PROP_DEPARTMENT_PARENT_ID, new Department(parentId)));
        dc.addOrder(Order.asc("departmentSortId"));
        return this.dao.findByCriteria(dc);
    }

    public synchronized Integer getMaxSortId() {
        String hql = "select max(departmentSortId) as maxSortId from Department dept";
        List maxNoList = this.dao.exeHqlList(hql);
        Integer maxID;
        if ((maxNoList == null) || (maxNoList.size() < 1) || (maxNoList.get(0) == null))
            maxID = Integer.valueOf(1);
        else {
            maxID = Integer.valueOf(1 + ((Integer) (Integer) maxNoList.get(0)).intValue());
        }
        return maxID;
    }

    public boolean addDeptSave(Department dept, PositionBase respPB, Position pos,
            Depthist deptHist, PositionBaseHist respPBHist) {
        String msgNewDept = "新建部门{0}成功";
        String msgNewPB = "新建职位{0}成功";

        HttpSession session = ServletActionContext.getRequest().getSession();
        Employee currentEmp = ((Userinfo) session.getAttribute("userinfo")).getEmployee();

        this.dao.saveObject(dept);
        this.dao.saveObject(deptHist);

        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        String msg = StringUtil.message(msgNewDept, new Object[] { dept.getDepartmentName() });
        logBO.addToSyslog("department", currentEmp.getId(), null, dept.getId(), 0, "新建", msg);

        this.dao.saveObject(respPB);
        this.dao.saveObject(respPBHist);
        this.dao.saveObject(pos);

        msg = StringUtil.message(msgNewPB, new Object[] { respPB.getPbName() });
        logBO.addToSyslog("positionbase", currentEmp.getId(), null, respPB.getId(), 0, "新建", msg);

        return true;
    }

    public String saveProcessDeptRename(Department dept) {
        this.helper.operateDeptRename(dept);
        return "SUCC";
    }

    public String saveDeptMove(String fromId, String toId) {
        Department fromDept = getDeptById(fromId);
        Department toDept = getDeptById(toId);

        this.helper.operateDeptMove(fromDept, toDept);

        return "SUCC";
    }

    public void saveMergeDept(String fromIds, String toId, String[][] fromPBInfo, Message msg) {
        String info = msg.getMsgContent();

        List<Department> fromDeptList = new ArrayList<Department>();
        String[] fromIdArray = fromIds.split(",");
        for (int i = 0; i < fromIdArray.length; ++i) {
            Department dept = getDeptById(fromIdArray[i]);
            fromDeptList.add(dept);
        }

        Department toDept = getDeptById(toId);

        for (Department fromDept : fromDeptList) {
            OrgDeptMergeHelper operateHelper = new OrgDeptMergeHelper(fromDept, toDept);
            IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");

            String respPosId = posBo.getRespPosOfDept(fromDept.getId()).getId();

            for (int i = 0; (fromPBInfo != null) && (i < fromPBInfo.length); ++i) {
                if (fromPBInfo[i][5].equals(fromDept.getId())) {
                    String pbId = fromPBInfo[i][0];
                    String pbInCharge = fromPBInfo[i][1];
                    String operate = fromPBInfo[i][2];
                    String toPBId = fromPBInfo[i][3];
                    String canelResp = fromPBInfo[i][4];

                    if (("1".equals(pbInCharge)) && ("1".equals(operate))) {
                        String tempInfo = operateHelper.respPBCopy(pbId);
                        if (!"SUCC".equals(tempInfo)) {
                            info = info + tempInfo;
                            break;
                        }
                    }

                    if (("1".equals(pbInCharge)) && ("2".equals(operate))) {
                        String tempInfo = operateHelper.respPBMoveTo(pbId, toPBId);
                        if (!"SUCC".equals(tempInfo)) {
                            info = info + tempInfo;
                            break;
                        }
                    }

                    if (("0".equals(pbInCharge)) && ("1".equals(operate))) {
                        String tempInfo = operateHelper.pbCopy(pbId);
                        if (!"SUCC".equals(tempInfo)) {
                            info = info + tempInfo;
                            break;
                        }
                    }

                    if (("0".equals(pbInCharge)) && ("2".equals(operate))) {
                        String tempInfo = operateHelper.pbMoveTo(pbId, toPBId);
                        if (!"SUCC".equals(tempInfo)) {
                            info = info + tempInfo;
                            break;
                        }

                    }

                    if (("1".equals(pbInCharge)) && ("1".equals(canelResp))) {
                        operateHelper.cancelResp(respPosId);
                    }

                }

            }

            if ("SUCC".equals(info)) {
                String tempInfo = operateHelper.deptOperate();
                if (!"SUCC".equals(tempInfo))
                    info = info + tempInfo;
            }
            msg.setMsgContent(info);

            if (!"SUCC".equals(info)) {
                msg.setMsgContent(info.replace("SUCC", ""));
                throw new RuntimeException();
            }
        }
    }

    /** @deprecated */
    public List<Department> FindAllDepartmentConHead() {
        DetachedCriteria dc = DetachedCriteria.forClass(Department.class);
        dc.addOrder(Order.asc("departmentSortId"));
        List result = this.dao.findByCriteria(dc);
        Department temp = new Department();
        Boolean flag = Boolean.valueOf(false);
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        IOrgheadsBo headsBo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");
        List<Orgheads> orgheadlist = headsBo.listOrgheadNo("deptheader");
        List orgheadempIdlist = new ArrayList();
        for (Orgheads orgheadObj : orgheadlist) {
            orgheadempIdlist.add(orgheadObj.getOrgheadsEmpNo());
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        List emplist;
        if (orgheadempIdlist.size() == 0) {
            emplist = null;
        } else {
            detachedCriteria.add(Restrictions.in(Employee.PROP_ID, orgheadempIdlist));
            emplist = empBo.findByCriteria(detachedCriteria);
        }
        for (int i = 0; i < ((result == null) ? 0 : result.size()); ++i) {
            flag = Boolean.valueOf(false);
            for (int j = 0; j < ((orgheadlist == null) ? 0 : orgheadlist.size()); ++j) {
                if (((Department) result.get(i)).getId().equals(
                                                                ((Orgheads) orgheadlist.get(j))
                                                                        .getOrgheadsOrgNo())) {
                    for (int k = 0; k < ((emplist == null) ? 0 : emplist.size()); ++k) {
                        if (((Orgheads) orgheadlist.get(j)).getOrgheadsEmpNo()
                                .equals(((Employee) emplist.get(k)).getId())) {
                            temp = (Department) result.get(i);
                            temp.setHeadNo(((Employee) emplist.get(k)).getId());
                            temp.setHeadName(((Employee) emplist.get(k)).getEmpName());
                            result.set(i, temp);
                            flag = Boolean.valueOf(true);
                            break;
                        }
                    }
                }
                if (flag.booleanValue()) {
                    break;
                }
            }
        }
        return result;
    }

    public boolean saveorUpdateDepartment(Department dept) {
        try {
            this.dao.saveOrupdate(dept);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /** @deprecated */
    public boolean addDepartment(Department dept) {
        try {
            String trimmedDepartmentName = dept.getDepartmentName().trim();
            List old = this.dao.exeHqlList(" from Department where departmentName ='"
                    + trimmedDepartmentName + "'");

            if ((old == null) || (old.size() == 0)) {
                dept.setDepartmentName(trimmedDepartmentName);
                this.dao.saveObject(dept);

                if (StringUtils.isNotEmpty(dept.getDeptHeader())) {
                    Orgheads head = new Orgheads();
                    head.setOrgheadsEmpNo(dept.getDeptHeader());
                    head.setOrgheadsOrgNo(dept.getId());
                    head.setOrgheadsResponsibility("deptheader");
                    this.dao.saveObject(head);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    /** @deprecated */
    public String delDepartment(Class<Department> name, String id) {
        StringBuffer message = new StringBuffer();
        try {
            List check = null;
            check = this.dao.exeHqlList("select count(*) from Empsalarypay where espDept = '" + id
                    + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                message.append("有每月薪资发放数据关联到该部门！");
            }
            check = this.dao.exeHqlList("select count(*) from Employee where empDeptNo = '" + id
                    + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                message.append("该部门下有员工存在！");
            }
            check = this.dao
                    .exeHqlList("select count(*) from Businessunit where businessunitDeptNo = '"
                            + id + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                message.append("该部门下有业务单元存在！");
            }
            Department department = (Department) this.dao
                    .loadObject(name, id, null, new boolean[0]);
            if (message.toString().length() == 0) {
                this.dao.deleteObject(department);
                this.dao.exeHql("delete from Orgheads where orgheads_org_no='" + id + "'");
            }
        } catch (Exception e) {
            message.append("系统异常：" + e.getMessage());
        }
        return message.toString();
    }

    /** @deprecated */
    public String updateDepartment(Department dept) {
        String trimmedDepartmentName = dept.getDepartmentName().trim();

        if (dept.getDepartmentStatus().intValue() == 0) {
            List check = new ArrayList();
            check = this.dao.exeHqlList("select count(*) from Employee where empDeptNo = '"
                    + dept.getId() + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return "error1";
            }
            check = this.dao
                    .exeHqlList("select count(*) from Businessunit where businessunitDeptNo = '"
                            + dept.getId() + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return "error2";
            }
        }

        List old = this.dao.exeHqlList(" from Department where  id != '" + dept.getId()
                + "'  and departmentName  ='" + trimmedDepartmentName + "'");

        if ((old == null) || (old.size() == 0)) {
            Department oldDepartment = (Department) this.dao.loadObject(Department.class, dept
                    .getId(), null, new boolean[0]);
            oldDepartment.setDepartmentDesc(dept.getDepartmentDesc());
            oldDepartment.setDepartmentName(trimmedDepartmentName);
            oldDepartment.setDepartmentStatus(dept.getDepartmentStatus());
            oldDepartment.setDepartmentSortId(dept.getDepartmentSortId());

            this.dao.updateObject(oldDepartment);

            Orgheads head = new Orgheads();
            IOrgheadsBo headsbo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");

            if (StringUtils.isNotEmpty(dept.getDeptHeader())) {
                if ((oldDepartment.getDeptHeader() == null)
                        || (oldDepartment.getDeptHeader().trim().equals(""))) {
                    head.setOrgheadsEmpNo(dept.getDeptHeader());
                    head.setOrgheadsOrgNo(oldDepartment.getId());
                    head.setOrgheadsResponsibility("deptheader");
                    headsbo.insert(head);
                } else if (!dept.getDeptHeader().trim().equalsIgnoreCase(
                                                                         oldDepartment
                                                                                 .getDeptHeader()
                                                                                 .trim())) {
                    String hql = "delete from Orgheads where orgheadsEmpNo='"
                            + dept.getDeptHeader() + "' and orgheadsOrgNo='" + dept.getId() + "'";
                    this.dao.exeHql(hql);

                    String hql2 = "delete from Orgheads where orgheadsOrgNo='" + dept.getId()
                            + "' and orgheadsResponsibility='deptheader'";
                    this.dao.equals(hql2);
                    head.setOrgheadsEmpNo(dept.getDeptHeader());
                    head.setOrgheadsOrgNo(oldDepartment.getId());
                    head.setOrgheadsResponsibility("deptheader");
                    headsbo.insert(head);
                }

            }

            return "success";
        }
        return "exist";
    }

    /** @deprecated */
    public List getDepartmentsByNos(String[] deptNos) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Department.class);
        detachedCriteria.add(Restrictions.in(Department.PROP_ID, deptNos));
        return this.dao.findByCriteria(detachedCriteria);
    }

    public Department getDeptById(String deptId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Department.class);
        detachedCriteria.createAlias(Department.PROP_DEPARTMENT_PARENT_ID,
                                     Department.PROP_DEPARTMENT_PARENT_ID, 1);
        detachedCriteria.add(Restrictions.eq(Department.PROP_ID, deptId));
        List deptList = this.dao.findByCriteria(detachedCriteria);

        if (deptList.size() > 0)
            return (Department) deptList.get(0);
        return null;
    }

    public Department getDeptByName(String deptName, String parentId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Department.class);
        detachedCriteria.createAlias(Department.PROP_DEPARTMENT_PARENT_ID,
                                     Department.PROP_DEPARTMENT_PARENT_ID, 1);
        detachedCriteria.add(Restrictions.eq(Department.PROP_DEPARTMENT_NAME, deptName));
        detachedCriteria.add(Restrictions
                .eq(Department.PROP_DEPARTMENT_PARENT_ID + ".id", parentId));
        List deptList = this.dao.findByCriteria(detachedCriteria);

        if (deptList.size() > 0)
            return (Department) deptList.get(0);
        return null;
    }

    public Department loadDepartmentByNo(String deptNo, String[] fetch) {
        return (Department) this.dao.loadObject(Department.class, deptNo, fetch, new boolean[0]);
    }

    /** @deprecated */
    public void saveDepartmentSortIdByBatch(String[] ids) {
        int length = ids.length;
        int sortId = 1;
        for (int i = 0; i < length; ++i) {
            this.dao.exeHql("update Department set departmentSortId=" + sortId + " where id ='"
                    + ids[i] + "'");
            ++sortId;
        }
    }

    public int getActiveEmpNos(String deptId) {
        List subDepts = getAllSubDeptsOfDept(new Department(deptId));
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        dc.add(Restrictions.eq(Employee.PROP_EMP_STATUS, new Integer(1)));
        dc.add(Restrictions.in(Employee.PROP_EMP_DEPT_NO, subDepts));

        return this.dao.findRowCountByCriteria(dc);
    }

    public int getOrderEmpNos(String deptId) {
        List deptIdList = getAllSubDepts(new Integer(1), new String[] { deptId });
        String deptIdStr = "";
        for (int i = 0; (deptIdList != null) && (i < deptIdList.size()); ++i) {
            deptIdStr = deptIdStr + "'" + ((Department) deptIdList.get(i)).getId() + "',";
        }

        String hql = "select sum(pbMaxEmployee) from PositionBase where pbDeptId.id in ("
                + deptIdStr.substring(0, deptIdStr.length() - 1) + ") and pbStatus=1";
        List list = this.dao.exeHqlList(hql);
        if ((list != null) && (list.size() > 0) && (list.get(0) != null)
                && (!"".equals(list.get(0)))) {
            Long num = (Long) (Long) list.get(0);
            return num.intValue();
        }

        return 0;
    }

    public void update_disableDept(String deptId, Message msg) {
        String info = this.helper.operateDisableDept(deptId);

        if (!info.equals("SUCC")) {
            msg.setMsgContent(info);
            throw new RuntimeException();
        }
    }

    public String update_enableDept(String deptId) {
        String info = "SUCC";

        Department currentDept = getDeptById(deptId);

        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        List pbList = pbBo.getAllPbsOfDept(new String[] { deptId });
        for (int i = 0; i < pbList.size(); ++i) {
            PositionBase pb = (PositionBase) pbList.get(i);
            String tempInfo = this.helper.operateEnablePB(pb);
            if (tempInfo.equals("SUCC"))
                continue;
            info = info + tempInfo;
        }

        this.helper.operateEnableCurrentDept(currentDept);

        return info;
    }

    public void deleteDept(String deptId, Message msg) {
        String info = this.helper.operateDeleteDept(deptId);

        if (!info.equals("SUCC")) {
            msg.setMsgContent(info);
            throw new RuntimeException();
        }
    }

    public String getDeptInCharge(String posId) {
        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position position = posBo.getPosById(posId, new String[] { Position.PROP_POSITION_PB_ID });

        if (position.getPositionPbId().getPbInCharge().intValue() == 1) {
            return position.getPositionPbId().getPbDeptId().getId();
        }
        return null;
    }

    public String[] getAllSubDeptIds(int sign, String deptNo) {
        List<Department> subDeptList = getAllSubDepts(new Integer(1), new String[] { deptNo });

        if (subDeptList == null)
            return null;

        if (sign == 0)
            subDeptList.remove(0);

        List subDeptNoList = new ArrayList();
        for (Department tempDept : subDeptList) {
            subDeptNoList.add(tempDept.getId());
        }

        if (subDeptNoList.size() == 0)
            return null;

        return (String[]) subDeptNoList.toArray(new String[subDeptNoList.size()]);
    }

    public String[] getAllSubDeptIds(String[] deptNos) {
        List<Department> subDeptList = getAllSubDepts(new Integer(1), deptNos);

        if (subDeptList == null)
            return null;

        List subDeptNoList = new ArrayList();
        for (Department tempDept : subDeptList) {
            subDeptNoList.add(tempDept.getId());
        }

        return (String[]) subDeptNoList.toArray(new String[subDeptNoList.size()]);
    }

    public List<Department> getAllSubDepts(Integer status, String[] deptNos) {
        DetachedCriteria dc = DetachedCriteria.forClass(Department.class);
        if (status != null)
            dc.add(Restrictions.eq(Department.PROP_DEPARTMENT_STATUS, status));
        List allDeptList = this.dao.findByCriteria(dc);

        Iterator iter = allDeptList.iterator();

        List subDeptQueue = new ArrayList();
        while (iter.hasNext()) {
            Department tempDept = (Department) iter.next();
            if (ObjectUtil.contains(deptNos, tempDept.getId())) {
                subDeptQueue.add(tempDept);
                iter.remove();
            }
        }

        if (subDeptQueue.size() == 0)
            return null;

        Department parentDept = null;
        for (int i = 0; i < subDeptQueue.size(); ++i) {
            parentDept = (Department) subDeptQueue.get(i);

            Iterator iterTemp = allDeptList.iterator();
            while (iterTemp.hasNext()) {
                Department tempDept = (Department) iterTemp.next();
                if ((tempDept.getDepartmentParentId() != null)
                        && (tempDept.getDepartmentParentId().getId().equals(parentDept.getId()))) {
                    subDeptQueue.add(tempDept);
                    iterTemp.remove();
                }
            }
        }

        return subDeptQueue;
    }

    public List<Department> getAllSubDeptsOfDept(Department dept) {
        DetachedCriteria dc = DetachedCriteria.forClass(Department.class);
        List allDeptList = this.dao.findByCriteria(dc);

        List subDeptList = new ArrayList();

        for (int i = 0; (allDeptList != null) && (i < allDeptList.size()); ++i) {
            Department tempDept = (Department) allDeptList.get(i);
            if (tempDept.getId().equals(dept.getId())) {
                subDeptList.add(tempDept);
                break;
            }

        }

        Iterator iter = allDeptList.iterator();
        for (int i = 0; (subDeptList != null) && (i < subDeptList.size()); ++i) {
            Department parentDept = (Department) subDeptList.get(i);
            while (iter.hasNext()) {
                Department tempDept = (Department) iter.next();
                if ((tempDept != null) && (tempDept.getDepartmentParentId() != null)
                        && (parentDept != null)
                        && (tempDept.getDepartmentParentId().getId().equals(parentDept.getId()))) {
                    subDeptList.add(tempDept);
                    iter.remove();
                }
            }
        }

        return subDeptList;
    }

    public boolean saveEmpTransferData(Employee emp, Department newDept, PositionBase newPB) {
        if ((newDept == null) && (newPB == null))
            return true;
        IEmpHistOrgBo emphistorgBo = (IEmpHistOrgBo) SpringBeanFactory.getBean("empHistOrgBo");

        Emptransfer transfer = null;
        transfer = gernerateTransfer(emp, newDept, newPB);

        if (newDept != null)
            emp.setEmpDeptNo(newDept);
        if (newPB != null)
            emp.setEmpPbNo(newPB);

        emphistorgBo.saveEmpHistOrg(emp, newDept, newPB);

        this.dao.saveOrupdate(emp);
        this.dao.saveOrupdate(transfer);

        return true;
    }

    private Emptransfer gernerateTransfer(Employee emp, Department newDept, PositionBase newPB) {
        OrgDeptOperateHelper helper = new OrgDeptOperateHelper();
        Employee currentEmployee = helper.getCurrentEmp();

        Emptransfer obj = new Emptransfer();
        obj.setEmployee(emp);
        obj.setEftTransferDate(new Date());
        obj.setEftTransferType("0");
        if (newDept != null) {
            obj.setEftOldDeptNo(emp.getEmpDeptNo());
            obj.setEftNewDeptNo(newDept);
        }
        if (newPB != null) {
            obj.setEftOldPbId(emp.getEmpPbNo());
            obj.setEftNewPbId(newPB);
        }
        obj.setEftReason("部门合并");
        obj.setEftCreateDate(new Date());
        obj.setEftCreateBy(currentEmployee.getId());
        obj.setEftLastChangeBy(currentEmployee.getId());
        obj.setEftLastChangeTime(new Date());
        obj.setEftComments("部门合并");

        return obj;
    }

    public List<TreeNode> FindEnabledDepartmentNode() {
        List nodeList = new ArrayList();
        List<Department> deptList = FindEnabledDepartment();

        for (Department dept : deptList) {
            if (dept.getDepartmentParentId() == null) {
                dept.setDepartmentParentId(new Department("0"));
            }
            nodeList.add(new TreeNode(dept.getId(), dept.getDepartmentName(), dept
                    .getDepartmentParentId().getId(), dept.getDepartmentCate().intValue()));
        }

        return nodeList;
    }

    public List getAllActiveDeptEmpNumList() {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        dc.createAlias(Employee.PROP_EMP_DEPT_NO, "dept");
        dc.add(Restrictions.eq(Employee.PROP_EMP_STATUS, new Integer(1)));
        dc.setProjection(Projections.projectionList().add(Projections.rowCount())
                .add(Projections.groupProperty("dept." + Department.PROP_ID)));

        return this.dao.findByProjectionDetachedCriteria(dc);
    }

    public Map<String, Department[]> getDeptLevels(Department[] deptArray, Date date) {
        if ((deptArray == null) || (deptArray.length < 1)) {
            return null;
        }

        List allActiveDepts = new ArrayList();
        if (date == null) {
            allActiveDepts = FindEnabledDepartment();
        } else {
            IDeptHistBo deptHistBo = (IDeptHistBo) SpringBeanFactory.getBean("deptHistBo");
            List dhList = deptHistBo.getDhsBeforeQueryDate(date);
            for (int i = 0; i < dhList.size(); ++i) {
                Depthist dh = (Depthist) dhList.get(i);
                allActiveDepts.add(dh.assembleDeptByDh());
            }

        }

        Map deptLevelsMap = new HashMap();
        for (int i = 0; i < deptArray.length; ++i) {
            Department dept = deptArray[i];
            if (dept == null) {
                continue;
            }
            String deptKey = dept.getId();
            if (deptLevelsMap.containsKey(deptKey)) {
                continue;
            }
            List superList = superDeptListOfDept(dept, allActiveDepts);
            Department[] superLevels = new Department[4];
            for (int j = 0; j < superList.size(); ++j) {
                if (j == 0) {
                    Department superDept = (Department) superList.get(j);
                    if ((superDept.getDepartmentCate() != null)
                            && (superDept.getDepartmentCate().equals(Integer.valueOf(1)))) {
                        superLevels[0] = superDept;
                    }
                    superLevels[1] = superDept;
                } else {
                    Department superDept = (Department) superList.get(j);
                    superLevels[(j + 1)] = superDept;
                }
            }
            deptLevelsMap.put(deptKey, superLevels);
        }

        return deptLevelsMap;
    }

    private List<Department> superDeptListOfDept(Department dept, List<Department> allActiveDepts) {
        // TODO 测试修改可能有问题，蒋旭
        List superList = new ArrayList();

        superList.add(dept);

        if ((dept.getDepartmentParentId() == null)
                || (dept.getDepartmentParentId().getId().equals("rootid")))
            return superList;
        String turnDeptId = dept.getDepartmentParentId().getId();
        boolean turnIdExist;
        label137: do {
            turnIdExist = false;
            Iterator i$ = allActiveDepts.iterator();
            Department deptInWhile;
            do {
                if (!i$.hasNext())
                    break label137;
                deptInWhile = (Department) i$.next();
            } while (!deptInWhile.getId().equals(turnDeptId));

            turnIdExist = true;
            superList.add(deptInWhile);
            if (deptInWhile.getDepartmentParentId() == null)
                turnDeptId = null;
            else {
                turnDeptId = deptInWhile.getDepartmentParentId().getId();
            }
        }

        while ((!turnDeptId.equals("rootid")) && (turnIdExist));

        Collections.reverse(superList);

        if (superList.size() > 3) {
            superList = superList.subList(0, 3);
        }
        return superList;
    }

    public <T> void setListDeptLevels(List<T> results, String deptField, String branchField,
            String deptLevelPrefix, String[] dateField) {
        // TODO 测试修改可能有问题，蒋旭
        if ((results == null) || (results.size() < 1)) {
            return;
        }

        String dateString = null;
        if ((dateField != null) && (dateField.length == 1))
            dateString = dateField[0];
        if (!StringUtils.isEmpty(dateString)) {
            for (Iterator i$ = results.iterator(); i$.hasNext();) {
                Object obj = i$.next();
                try {
                    Department dept = (Department) PropertyUtils.getNestedProperty(obj, deptField);
                    Date date = null;
                    Object dateObj = PropertyUtils.getNestedProperty(obj, dateString);

                    if (dateObj instanceof String) {
                        String dateSourceString = (String) dateObj;

                        if (dateSourceString.trim().length() == 6) {
                            dateSourceString = dateSourceString.substring(0, 4) + "-"
                                    + dateSourceString.substring(4);
                            date = DateUtil.parseDateByFormat(dateSourceString, "yyyy-MM");
                        }
                    } else {
                        date = (Date) dateObj;
                    }

                    Map deptLevelsMap = getDeptLevels(new Department[] { dept }, date);
                    Department[] deptLevels = (Department[]) deptLevelsMap.get(dept.getId());
                    if (deptLevels != null) {
                        PropertyUtils.setNestedProperty(obj, branchField, deptLevels[0]);
                        int i = 1;
                        for (i = 1; i < deptLevels.length; i++) {
                            PropertyUtils
                                    .setNestedProperty(obj, deptLevelPrefix + i, deptLevels[i]);
                        }
                    }
                } catch (Exception e) {
                }
            }

        } else {
            Department[] deptArray = new Department[results.size()];
            for (int i = 0; i < results.size(); ++i) {
                Department dept;
                try {
                    dept = (Department) PropertyUtils.getNestedProperty(results.get(i), deptField);
                } catch (Exception e) {
                    return;
                }
                deptArray[i] = dept;
            }

            Map deptLevelsMap = getDeptLevels(deptArray, null);
            for (Iterator i$ = results.iterator(); i$.hasNext();) {
                Object obj = i$.next();
                try {
                    Department dept = (Department) PropertyUtils.getNestedProperty(obj, deptField);
                    Department[] deptLevels = (Department[]) deptLevelsMap.get(dept.getId());
                    if (deptLevels != null) {
                        PropertyUtils.setNestedProperty(obj, branchField, deptLevels[0]);
                        int i = 1;
                        for (i = 1; i < deptLevels.length; i++) {
                            PropertyUtils
                                    .setNestedProperty(obj, deptLevelPrefix + i, deptLevels[i]);
                        }
                    }

                } catch (Exception e) {
                }
            }

        }
    }

    public List<Department> setDeptOfNodeList(List<TreeNode> nodeList, int status) {
        List<Department> depts;
        if (status == 0)
            depts = FindEnabledDepartment();
        else {
            depts = FindAllDepartment();
        }

        for (Department dept : depts) {
            if (dept.getDepartmentParentId() == null) {
                dept.setDepartmentParentId(new Department("0"));
            }
            TreeNode node = null;
            if (status == 0) {
                node = new TreeNode(dept.getId(), dept.getDepartmentName(), dept
                        .getDepartmentParentId().getId(), dept.getDepartmentCate().intValue());
            } else if (dept.getDepartmentStatus().intValue() == 1)
                node = new TreeNode(dept.getId(), dept.getDepartmentName(), dept
                        .getDepartmentParentId().getId(), dept.getDepartmentCate().intValue());
            else {
                node = new TreeNode(dept.getId(), dept.getDepartmentName(), "disable", dept
                        .getDepartmentCate().intValue());
            }

            nodeList.add(node);
        }

        if (status != 0) {
            nodeList.add(new TreeNode("disabled", "停用的组织结构", "0", 11));
        }

        return depts;
    }

    public Map<String, String> getDeptLevelNameMap() {
        Map levelNameMap = new HashMap();

        List deptList = FindEnabledDepartment();

        for (int i = 0; (deptList != null) && (i < deptList.size()); ++i) {
            Department dept = (Department) deptList.get(i);
            String levelName = dept.getDepartmentName();

            Department parentDept = dept;
            while ((parentDept.getDepartmentParentId() != null)
                    && (parentDept.getDepartmentParentId().getId() != null)) {
                parentDept = getDeptById(deptList, parentDept.getDepartmentParentId().getId());
                levelName = parentDept.getDepartmentName() + "@@" + levelName;
            }

            levelNameMap.put(dept.getId() + "@@" + dept.getDepartmentName(), levelName);
        }

        return levelNameMap;
    }

    public String checkInputDeptLevel(Map<String, String> levelNameMap, String[] inputNameArr) {
        Set keySet = levelNameMap.keySet();
        Iterator iter = keySet.iterator();
        int count = 0;

        String lastDeptName = inputNameArr[5];
        String fitKeyValue = null;
        while (iter.hasNext()) {
            String keyValue = (String) iter.next();
            if (keyValue.indexOf(lastDeptName) >= 0)
                ;
            String levelName = (String) levelNameMap.get(keyValue);
            String[] levelNameArr = levelName.split("@@");

            if (!lastDeptName.equals(levelNameArr[(levelNameArr.length - 1)])) {
                continue;
            }

            boolean fit = true;
            for (int i = 0; i < inputNameArr.length - 1; ++i) {
                String inputNameTemp = inputNameArr[i];
                String levelNameTemp = null;
                if (i < levelNameArr.length - 1)
                    levelNameTemp = levelNameArr[(i + 1)];

                if ((inputNameTemp != null) && (levelNameTemp != null)
                        && (!inputNameTemp.equals(levelNameTemp)))
                    fit = false;
                if ((inputNameTemp == null) || (levelNameTemp != null))
                    continue;
                fit = false;
            }

            if (fit) {
                fitKeyValue = keyValue;
                ++count;
            }

        }

        if ((count == 0) || (count > 1))
            return null;

        return fitKeyValue.split("@@")[0];
    }

    private Department getDeptById(List<Department> deptList, String deptId) {
        for (int i = 0; (deptList != null) && (i < deptList.size()); ++i) {
            Department dept = (Department) deptList.get(i);
            if ((deptId != null) && (dept != null) && (deptId.equals(dept.getId())))
                return dept;
        }

        return null;
    }

    public boolean saveDeptOrder(String[] ids) {
        if ((ids == null) || (ids.length == 0))
            return true;
        for (int i = 0; i < ids.length; ++i) {
            int order = i + 1;
            String hql = "update Department set departmentSortId=" + order + " where id='" + ids[i]
                    + "'";
            this.dao.exeHql(hql);
        }

        return true;
    }

    public IDepartmentDAO getDao() {
        return this.dao;
    }

    public void setDao(IDepartmentDAO dao) {
        this.dao = dao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.DepartmentBOImpl JD-Core Version: 0.5.4
 */