package com.hr.profile.bo;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.Department;
import com.hr.profile.dao.IPositionDao;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.base.TreeNode;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.ObjectUtil;
import com.hr.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class PositionBoImpl implements IPositionBo {
    private IPositionDao positionDao;

    public IPositionDao getPositionDao() {
        return this.positionDao;
    }

    public void setPositionDao(IPositionDao positionDao) {
        this.positionDao = positionDao;
    }

    public Position getPosById(String posId, String[] fetch) {
        return (Position) this.positionDao.loadObject(Position.class, posId, fetch,
                                                      new boolean[] { true });
    }

    public Position getPositionById(String posId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);

        dc.add(Restrictions.eq(Position.PROP_ID, posId));
        dc.createAlias(Position.PROP_POSITION_EMP_NO, Position.PROP_POSITION_EMP_NO, 1);
        dc.createAlias(Position.PROP_POSITION_PB_ID, Position.PROP_POSITION_PB_ID, 1);
        dc.createAlias(Position.PROP_POSITION_PB_ID + "." + PositionBase.PROP_PB_DEPT_ID,
                       Position.PROP_POSITION_PB_ID + "." + PositionBase.PROP_PB_DEPT_ID, 1);
        List list = this.positionDao.findByCriteria(dc);
        if ((list == null) || (list.isEmpty())) {
            return null;
        }
        return (Position) list.get(0);
    }

    public boolean savePos(Position pos) {
        this.positionDao.saveOrupdate(pos);
        return true;
    }

    public boolean savePosList(List<Position> newList) {
        try {
            this.positionDao.saveOrupdate(newList);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Position> findAllActivePosition() {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.setFetchMode(Position.PROP_POSITION_EMP_NO, FetchMode.JOIN);
        dc.createAlias(Position.PROP_POSITION_PB_ID, "pb", 1);
        dc.createAlias("pb." + PositionBase.PROP_PB_DEPT_ID, "pbDept", 1);
        dc.add(Restrictions.eq("pbDept." + Department.PROP_DEPARTMENT_STATUS, new Integer(1)));
        dc.addOrder(Order.asc("pbDept." + Department.PROP_DEPARTMENT_SORT_ID));
        dc.addOrder(Order.asc("pb.pbSortId"));
        return this.positionDao.findByCriteria(dc);
    }

    public Position getRespPosOfDept(String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.createAlias(Position.PROP_POSITION_PB_ID, "positionPbId", 1);
        dc.createAlias(Position.PROP_POSITION_EMP_NO, "positionEmpNo", 1);
        dc.add(Restrictions.eq("positionPbId." + PositionBase.PROP_PB_DEPT_ID, new Department(
                deptId)));
        dc.add(Restrictions.eq("positionPbId." + PositionBase.PROP_PB_IN_CHARGE, new Integer(1)));
        List posList = this.positionDao.findByCriteria(dc);

        if (posList.size() == 0) {
            return null;
        }
        return (Position) posList.get(0);
    }

    public List<Position> getPosListOfDept(String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.createAlias(Position.PROP_POSITION_PB_ID, "positionPbId", 1);
        dc.createAlias(Position.PROP_POSITION_EMP_NO, "positionEmpNo", 1);
        dc.add(Restrictions.eq("positionPbId." + PositionBase.PROP_PB_DEPT_ID, new Department(
                deptId)));
        dc.addOrder(Order.asc("positionPbId.pbSortId"));
        List posList = this.positionDao.findByCriteria(dc);
        return posList;
    }

    public List<Position> getRespPosListOfDept(String[] deptIds) {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.createAlias(Position.PROP_POSITION_PB_ID, "positionPbId", 1);
        dc.add(Restrictions.in("positionPbId." + PositionBase.PROP_PB_DEPT_ID + ".id", deptIds));
        dc.add(Restrictions.eq("positionPbId." + PositionBase.PROP_PB_IN_CHARGE, new Integer(1)));
        List posList = this.positionDao.findByCriteria(dc);

        return posList;
    }

    public List<Position> getPosListOfEmps(List<Employee> empList) {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.createAlias(Position.PROP_POSITION_PB_ID, "positionPbId", 1);
        dc.add(Restrictions.in(Position.PROP_POSITION_EMP_NO, empList));
        List posList = this.positionDao.findByCriteria(dc);

        return posList;
    }

    public List<String> getAllParentPos() {
        String hql = "select distinct positionParentId.id from Position pos where pos.positionParentId is not null";
        List parentPosList = this.positionDao.exeHqlList(hql);

        return parentPosList;
    }

    public boolean isSupPos(String supPosNo, String subPosNo) {
        return ObjectUtil.contains(getAllSubPosIds(0, supPosNo), subPosNo);
    }

    public Position[] getAllSupPosIds(int sign, Employee emp) {
        Position pos = getPosByEmpNo(emp.getId(), new String[0]);
        return getAllSupPosIds(sign, pos.getId());
    }

    public Position[] getAllSupPosIds(int sign, String posId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.createAlias(Position.PROP_POSITION_EMP_NO, "positionEmpNo", 1);
        dc.createAlias(Position.PROP_POSITION_PB_ID, "positionPbId", 1);
        List<Position> allPosList = this.positionDao.findByCriteria(dc);

        Map allPosMap = new HashMap();
        for (Position pos : allPosList) {
            allPosMap.put(pos.getId(), pos);
        }

        Position currPos = (Position) allPosMap.get(posId);
        if (currPos == null)
            return null;

        List supPosQueue = new LinkedList();
        supPosQueue.add(currPos);

        while (currPos.getPositionParentId() != null) {
            currPos = (Position) allPosMap.get(currPos.getPositionParentId().getId());
            if (currPos == null)
                break;
            supPosQueue.add(currPos);
        }

        if (sign == 0)
            supPosQueue.remove(0);

        return (Position[]) supPosQueue.toArray(new Position[supPosQueue.size()]);
    }

    public String[] getAllSubPosIds(int sign, String posNo) {
        List<Position> subPosList = getAllSubPositions(null, new String[] { posNo });

        if (subPosList == null)
            return null;

        if (sign == 0)
            subPosList.remove(0);

        List subPosNoList = new ArrayList();
        for (Position tempDept : subPosList) {
            subPosNoList.add(tempDept.getId());
        }

        if (subPosNoList.size() == 0)
            return null;

        return (String[]) subPosNoList.toArray(new String[subPosNoList.size()]);
    }

    public String[] getAllSubEmpIds(int sign, String posNo) {
        List<Position> subPosList = getAllSubPositions(new String[] { Position.PROP_POSITION_EMP_NO },
                                             new String[] { posNo });

        if (subPosList == null)
            return null;

        if (sign == 0)
            subPosList.remove(0);

        List subEmpNoList = new ArrayList();
        for (Position tempDept : subPosList) {
            if (tempDept.getPositionEmpNo() != null) {
                subEmpNoList.add(tempDept.getPositionEmpNo().getId());
            }
        }
        if (subEmpNoList.size() == 0)
            return null;

        return (String[]) subEmpNoList.toArray(new String[subEmpNoList.size()]);
    }

    public List<Position> getAllSubPositions(String[] fetch, String[] posNos) {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        if (fetch != null) {
            for (int i = 0; i < fetch.length; ++i)
                dc.setFetchMode(fetch[i], FetchMode.JOIN);
        }
        List allPosList = this.positionDao.findByCriteria(dc);

        Iterator iter = allPosList.iterator();

        List subPosQueue = new LinkedList();
        while (iter.hasNext()) {
            Position tempPos = (Position) iter.next();
            if (ObjectUtil.contains(posNos, tempPos.getId())) {
                subPosQueue.add(tempPos);
                iter.remove();
            }
        }

        if (subPosQueue.size() == 0)
            return null;

        Position parentPos = null;
        for (int i = 0; i < subPosQueue.size(); ++i) {
            parentPos = (Position) subPosQueue.get(i);

            iter = allPosList.iterator();
            while (iter.hasNext()) {
                Position tempPos = (Position) iter.next();
                if ((tempPos.getPositionParentId() != null)
                        && (tempPos.getPositionParentId().getId().equals(parentPos.getId()))) {
                    subPosQueue.add(tempPos);
                    iter.remove();
                }
            }
        }

        return subPosQueue;
    }

    public Position getPosByEmpNo(String empNo, String[] fetch) {
        Employee emp = new Employee(empNo);
        if ((fetch != null) && (fetch.length == 0))
            fetch = null;

        Position pos = (Position) this.positionDao.loadObjectByName(Position.class,
                                                                    Position.PROP_POSITION_EMP_NO,
                                                                    emp, fetch, new boolean[0]);
        if (pos == null)
            return null;
        return pos;
    }

    public Employee getEmpWithPos(String empNo, String[] fetch) {
        Position pos = getPosByEmpNo(empNo, fetch);
        if (pos == null)
            return null;

        Employee emp = pos.getPositionEmpNo();
        if (emp == null)
            return null;

        emp.setPosition(pos);
        return emp;
    }

    public List<Position> getDirectSubPos(String posId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.createAlias(Position.PROP_POSITION_EMP_NO, "positionEmpNo", 1);
        dc.add(Restrictions.eq(Position.PROP_POSITION_PARENT_ID + ".id", posId));
        List posList = this.positionDao.findByCriteria(dc);

        return posList;
    }

    public List<Position> getPosListByPB(PositionBase pb) {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.createAlias(Position.PROP_POSITION_EMP_NO, "positionEmpNo", 1);
        dc.add(Restrictions.eq(Position.PROP_POSITION_PB_ID, pb));
        List posList = this.positionDao.findByCriteria(dc);

        return posList;
    }

    public String delPosition(String posId) {
        String msgPosNoExist = "({0})职位不存在，请刷新页面后重试";
        String msgEmpExist = "({0})职位有员工，不能删除";
        String msgPosIsResp = "({0})职位为负责职位，不能删除";

        Position position = getPosById(posId, new String[] { Position.PROP_POSITION_PB_ID });
        if (position == null)
            return StringUtil.message(msgPosNoExist, new Object[] { position.getPositionPbId()
                    .getPbName() });
        if (position.getPositionEmpNo() != null)
            return StringUtil.message(msgEmpExist, new Object[] { position.getPositionPbId()
                    .getPbName() });
        if (position.getPositionPbId().getPbInCharge().intValue() == 1) {
            return StringUtil.message(msgPosIsResp, new Object[] { position.getPositionPbId()
                    .getPbName() });
        }

        List<Position> subPosition = getDirectSubPos(posId);
        if ((subPosition != null) && (subPosition.size() > 0)) {
            String subMsg = null;
            for (Position posIn : subPosition) {
                subMsg = delPosition(posIn.getId());
                if (subMsg != null) {
                    break;
                }
            }

            if (subMsg != null) {
                return subMsg;
            }
        }
        try {
            this.positionDao.deleteObject(Position.class, posId);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "删除失败!";
        }
        return null;
    }

    public List<TreeNode> getPositionTreeListNode() {
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        IPositionBo positionBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");

        List nodeList = new ArrayList();
        List<Department> deptList = deptbo.FindAllDepartment();
        for (Department dept : deptList) {
            if (dept.getDepartmentParentId() == null) {
                dept.setDepartmentParentId(new Department("0"));
            }
            TreeNode node = null;
            if (dept.getDepartmentStatus().intValue() == 1) {
                node = new TreeNode(dept.getId(), dept.getDepartmentName(), dept
                        .getDepartmentParentId().getId(), dept.getDepartmentCate().intValue());
                nodeList.add(node);
            }
        }

        List positionList = positionBo.findAllActivePosition();
        String nodeName = null;
        for (int i = 0; (positionList != null) && (i < positionList.size()); ++i) {
            Position pos = (Position) positionList.get(i);
            int isResp = 0;

            if (pos.getPositionPbId().getPbInCharge().intValue() == 1) {
                pos.setPositionParentId(new Position(pos.getPositionPbId().getPbDeptId().getId()));
                isResp = 1;
            }
            int nodeType;
            if (pos.getPositionEmpNo() != null) {
                nodeName = pos.getPositionPbId().getPbName() + "("
                        + pos.getPositionEmpNo().getEmpName() + ")";
                
                if (pos.getPositionEmpNo().getEmpGender().intValue() == 1)
                    nodeType = 5;
                else
                    nodeType = 6;
            } else {
                nodeName = pos.getPositionPbId().getPbName() + "(空缺)";
                nodeType = 7;
            }

            TreeNode positionNode = new TreeNode(pos.getId(), nodeName, pos.getPositionParentId()
                    .getId(), nodeType, isResp);

            nodeList.add(i, positionNode);
        }

        return nodeList;
    }

    public List<TreeNode> getChangeSupTreeListNode(String posId) {
        String[] fetch = { Position.PROP_POSITION_PB_ID };
        Position position = getPosById(posId, fetch);
        List<Position> posList = getPosListOfDept(position.getPositionPbId().getPbDeptId().getId());

        Position rootPos = null;
        for (Position pos : posList) {
            if (pos.getPositionPbId().getPbInCharge().intValue() == 1)
                rootPos = pos;

        }

        List nodeList = convertPosToNodeList(posList, rootPos);
        return nodeList;
    }

    private List<TreeNode> convertPosToNodeList(List<Position> posList, Position rootPos) {
        List nodeList = new ArrayList();
        String nodeName = null;
        for (Position pos : posList) {
            if (pos.getPositionEmpNo() != null)
                nodeName = pos.getPositionPbId().getPbName() + "("
                        + pos.getPositionEmpNo().getEmpName() + ")";
            else {
                nodeName = pos.getPositionPbId().getPbName() + "(空缺)";
            }
            if (rootPos.getId().equals(pos.getId())) {
                pos.setPositionParentId(new Position("0"));
            }

            TreeNode positionNode = new TreeNode(pos.getId(), nodeName, pos.getPositionParentId()
                    .getId(), 3);
            nodeList.add(positionNode);
        }

        return nodeList;
    }

    public Position getPosInCharge(String posId) {
        Position pos = getPosById(posId, new String[] { Position.PROP_POSITION_PB_ID });
        List<Position> deptPosList = getPosListOfDept(pos.getPositionPbId().getPbDeptId().getId());
        for (Position posInDept : deptPosList) {
            if (posInDept.getPositionPbId().getPbInCharge().intValue() == 1) {
                return posInDept;
            }
        }
        return null;
    }

    public String batchSetEmpPos(String parentPosId, String pbId, String[] ids) {
        if ((ids == null) || (ids.length == 0))
            return "SUCC";
        IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");

        if (pbBo.checkPBExceed(pbId, ids.length))
            return "该职位超编！";

        List empList = empBo.searchEmpArray(ids);
        if (empList.size() == 0)
            return "数据状态错误，请刷新页面后重试";

        List posList = getPosListOfEmps(empList);
        for (int i = 0; i < posList.size(); ++i) {
            Position pos = (Position) posList.get(i);
            pos.setPositionEmpNo(null);
        }

        this.positionDao.saveOrupdate(posList);

        PositionBase currPB = pbBo.getPBById(pbId);
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.add(Restrictions.eq(Position.PROP_POSITION_PB_ID + ".id", pbId));
        dc.add(Restrictions.isNull(Position.PROP_POSITION_EMP_NO));
        List nullPosList = this.positionDao.findByCriteria(dc);
        List newPosList = new ArrayList();
        if (empList.size() <= nullPosList.size()) {
            for (int i = 0; i < empList.size(); ++i) {
                Employee emp = (Employee) empList.get(i);
                Position pos = (Position) nullPosList.get(i);
                if ((parentPosId != null) && (!parentPosId.equals("")))
                    pos.setPositionParentId(new Position(parentPosId));
                else {
                    pos.setPositionParentId(null);
                }
                pos.setPositionEmpNo(emp);
            }
        } else {
            for (int i = 0; i < nullPosList.size(); ++i) {
                Position pos = (Position) nullPosList.get(i);
                Employee emp = (Employee) empList.get(i);
                pos.setPositionEmpNo(emp);
            }

            for (int i = nullPosList.size(); i < empList.size(); ++i) {
                Employee emp = (Employee) empList.get(i);

                Position pos = new Position();
                pos.setPositionPbId(currPB);
                pos.setPositionParentId(new Position(parentPosId));
                pos.setPositionTakeNum(new Integer(1));
                pos.setPositionEmpNo(emp);
                newPosList.add(pos);
            }
        }

        this.positionDao.saveOrupdate(nullPosList);
        this.positionDao.saveOrupdate(newPosList);

        return "SUCC";
    }

    public Map<String, String> getPosDBMap() {
        String hql = "select emp.empDistinctNo, pos.id from Position pos left join pos.positionEmpNo emp";
        List list = this.positionDao.exeHqlList(hql);

        Map empPosMap = new HashMap();
        for (int i = 0; (list != null) && (i < list.size()); ++i) {
            String[] obj = (String[]) (String[]) list.get(i);
            empPosMap.put(obj[0], obj[1]);
        }

        return empPosMap;
    }

    public List<Position> getDBPosList() {
        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.createAlias(Position.PROP_POSITION_PB_ID, "positionPbId", 1);
        dc.createAlias(Position.PROP_POSITION_EMP_NO, "positionEmpNo", 1);
        List dbPosList = this.positionDao.findByCriteria(dc);

        return dbPosList;
    }

    public Map<String, Position> getAllDeptRespPosMap() {
        Map respPosMap = new HashMap();

        List dbPosList = getDBPosList();

        for (int i = 0; (dbPosList != null) && (i < dbPosList.size()); ++i) {
            Position pos = (Position) dbPosList.get(i);
            if (pos.getPositionPbId().getPbInCharge().intValue() == 1) {
                respPosMap.put(pos.getPositionPbId().getPbDeptId().getId(), pos);
            }
        }

        return respPosMap;
    }

    public boolean parentPosInList(List<Position> posList, Position pos) {
        for (int i = 0; (posList != null) && (i < posList.size()); ++i) {
            Position tempPos = (Position) posList.get(i);
            if (tempPos == pos.getPositionParentId())
                return true;
        }

        return false;
    }

    public Object putEmpToPB(List<Position> dbPosList, List<Position> filePosList, Employee emp,
            PositionBase currPB) {
        int count = 0;
        Position emptyPos = null;
        for (int i = 0; i < dbPosList.size(); ++i) {
            Position pos = (Position) dbPosList.get(i);
            if (pos.getPositionPbId().getId().equals(currPB.getId())) {
                ++count;
                if (pos.getPositionEmpNo() != null)
                    continue;
                emptyPos = pos;
            }

        }

        if (emptyPos == null) {
            for (int i = 0; i < filePosList.size(); ++i) {
                Position pos = (Position) filePosList.get(i);
                if (!pos.getPositionPbId().getId().equals(currPB.getId()))
                    continue;
                ++count;
            }

            if ((currPB.getPbMaxExceed().intValue() == 1) && (currPB.getPbMaxEmployee() != null)
                    && (count + 1 > currPB.getPbMaxEmployee().intValue()))
                return "Exceed";

            emptyPos = new Position();
            emptyPos.setPositionPbId(currPB);
            emptyPos.setPositionParentId(null);
            emptyPos.setPositionTakeNum(new Integer(1));
        }

        emptyPos.setPositionEmpNo(emp);

        return emptyPos;
    }

    public List<TreeNode> generateEmpConnTreeNodes() {
        List posList = findAllActivePosition();
        List nodeList = new ArrayList();
        for (int i = 0; i < posList.size(); ++i) {
            Position pos = (Position) posList.get(i);
            TreeNode node = new TreeNode();
            node.setNodeId(pos.getId());
            node.setNodeParentId((pos.getPositionParentId() != null) ? pos.getPositionParentId()
                    .getId() : "0");

            Employee emp = pos.getPositionEmpNo();
            if (emp != null) {
                node.setNodeName(emp.getEmpName());
                node.setEmpId(emp.getId());
                if (emp.getEmpGender().intValue() == 1)
                    node.setNodeType(5);
                else
                    node.setNodeType(6);
            } else {
                node.setNodeType(7);
                node.setNodeName("(空缺)");
            }

            nodeList.add(node);
        }

        return nodeList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.PositionBoImpl JD-Core Version: 0.5.4
 */