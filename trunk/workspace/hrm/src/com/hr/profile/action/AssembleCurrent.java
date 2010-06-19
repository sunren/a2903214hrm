package com.hr.profile.action;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssembleCurrent extends AssembleDeptList {
    protected void getDeptList() {
        IDepartmentBO departmentBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        this.allResultList = departmentBo.FindEnabledDepartment();
        prepareInitialDeptList();
    }

    protected void setActualNum() {
        IDepartmentBO departmentBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        List deptEmpNosList = departmentBo.getAllActiveDeptEmpNumList();
        for (int i = 0; i < this.deptList.size(); ++i) {
            Department dept = (Department) this.deptList.get(i);
            String deptId = dept.getId();
            for (int j = 0; j < deptEmpNosList.size(); ++j) {
                Object[] deptActualNum = (Object[]) (Object[]) deptEmpNosList.get(j);
                String turnId = (String) deptActualNum[1];
                if (deptId.equals(turnId)) {
                    dept.setActualNum((Integer) deptActualNum[0]);
                    break;
                }
            }
        }
    }

    protected void setOrderNum() {
        IPositionBaseBo positionBaseBo = (IPositionBaseBo) SpringBeanFactory
                .getBean("positionBaseBo");

        List activePbList = positionBaseBo.findAllActivePb();
        Map deptPbMap = new HashMap();
        for (int i = 0; i < activePbList.size(); ++i) {
            PositionBase pb = (PositionBase) activePbList.get(i);
            String deptKey = pb.getPbDeptId().getId();
            if (deptPbMap.containsKey(deptKey)) {
                List deptPbList = (List) deptPbMap.get(deptKey);
                deptPbList.add(pb);
            } else {
                List deptPbList = new ArrayList();
                deptPbList.add(pb);
                deptPbMap.put(deptKey, deptPbList);
            }
        }

        for (int i = 0; i < this.deptList.size(); ++i) {
            Department dept = (Department) this.deptList.get(i);
            List deptPbList = (List) deptPbMap.get(dept.getId());
            int sumOrderNum = 0;
            if (deptPbList != null) {
                for (int j = 0; j < deptPbList.size(); ++j) {
                    PositionBase pb = (PositionBase) deptPbList.get(j);
                    if (pb.getPbMaxEmployee() == null) {
                        continue;
                    }
                    sumOrderNum += pb.getPbMaxEmployee().intValue();
                }
            }
            dept.setOrderNum(Integer.valueOf(sumOrderNum));
        }
    }

    protected void setDeptHead() {
        IPositionBaseBo positionBaseBo = (IPositionBaseBo) SpringBeanFactory
                .getBean("positionBaseBo");

        List respPbList = positionBaseBo.findAllRespPb();
        Map pbEmpListMap = getPbEmpListMap();
        Map deptEmpListMap = new HashMap();
        for (int i = 0; i < respPbList.size(); ++i) {
            PositionBase pb = (PositionBase) respPbList.get(i);
            String deptKey = pb.getPbDeptId().getId();
            List transferEmpList = (List) pbEmpListMap.get(pb.getId());
            deptEmpListMap.put(deptKey, transferEmpList);
        }

        for (int i = 0; i < this.deptList.size(); ++i) {
            Department dept = (Department) this.deptList.get(i);
            List deptHeadEmpList = (List) deptEmpListMap.get(dept.getId());
            if ((deptHeadEmpList != null) && (deptHeadEmpList.size() == 1))
                dept.setDeptHead((Employee) deptHeadEmpList.get(0));
        }
    }

    private Map<String, List<Employee>> getPbEmpListMap() {
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List empList = empBo.loadAll();
        Map pbEmpListMap = new HashMap();

        for (int j = 0; j < empList.size(); ++j) {
            Employee emp = (Employee) empList.get(j);
            if (emp.getEmpPbNo() == null) {
                continue;
            }
            String pbKey = emp.getEmpPbNo().getId();
            if (pbEmpListMap.containsKey(pbKey)) {
                List mapEmpList = (List) pbEmpListMap.get(pbKey);
                mapEmpList.add(emp);
            } else {
                List mapEmpList = new ArrayList();
                mapEmpList.add(emp);
                pbEmpListMap.put(pbKey, mapEmpList);
            }
        }
        return pbEmpListMap;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.AssembleCurrent JD-Core Version: 0.5.4
 */