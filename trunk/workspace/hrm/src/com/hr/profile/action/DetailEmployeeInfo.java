package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.spring.SpringBeanFactory;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class DetailEmployeeInfo extends BaseAction {
    private static final long serialVersionUID = 472876731L;
    private Employee employee;
    private String connectionType;
    private String connectionNo;
    private List empList;
    private String id;
    private int directEmpCount;
    private int allEmpCount;

    public String execute() throws Exception {
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");

        String[] fetch = { Employee.PROP_EMP_TYPE, Employee.PROP_EMP_DEPT_NO,
                Employee.PROP_EMP_LOCATION_NO, Employee.PROP_EMP_PB_NO };

        if (StringUtils.isNotEmpty(this.id)) {
            this.employee = empBo.loadEmp(this.id, fetch);
        }

        if (this.employee == null) {
            this.employee = empBo.loadEmp(getCurrentEmpNo(), fetch);
        }

        String empMsn = this.employee.getEmpMsn();
        if ((empMsn != null) && (!"".equals(empMsn))) {
            if (empMsn.startsWith("QQ:")) {
                this.connectionType = "1";
                this.connectionNo = empMsn.replace("QQ:", "");
            } else if (empMsn.startsWith("WW:")) {
                this.connectionType = "2";
                this.connectionNo = empMsn.replace("WW:", "");
            } else {
                this.connectionType = "0";
                this.connectionNo = empMsn;
            }

        }

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position currPos = posBo.getPosByEmpNo(this.employee.getId(), new String[0]);
        if (currPos != null) {
            List allSubPosList = posBo.getAllSubPositions(null, new String[] { currPos.getId() });
            int allSub = 0;
            for (int i = 0; (allSubPosList != null) && (i < allSubPosList.size()); ++i) {
                Position pos = (Position) allSubPosList.get(i);
                if ((pos.getPositionEmpNo() == null)
                        || (!StringUtils.isNotEmpty(pos.getPositionEmpNo().getId())))
                    continue;
                ++allSub;
            }
            setAllEmpCount(allSub - 1);

            if (currPos.getPositionParentId() != null) {
                Position parentPos = posBo
                        .getPosById(currPos.getPositionParentId().getId(),
                                    new String[] { Position.PROP_POSITION_EMP_NO });
                if( parentPos != null ){
                    this.employee.setEmpSupNo(parentPos.getPositionEmpNo());
                }
            }

            this.empList = empBo.findDirectEmps(this.employee.getId());

            this.directEmpCount = ((this.empList == null) ? 0 : this.empList.size());
            setDirectEmpCount(this.directEmpCount);
        }

        return "success";
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List getEmpList() {
        return this.empList;
    }

    public void setEmpList(List empList) {
        this.empList = empList;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAllEmpCount() {
        return this.allEmpCount;
    }

    public void setAllEmpCount(int allEmpCount) {
        this.allEmpCount = allEmpCount;
    }

    public int getDirectEmpCount() {
        return this.directEmpCount;
    }

    public void setDirectEmpCount(int directEmpCount) {
        this.directEmpCount = directEmpCount;
    }

    public String getConnectionType() {
        return this.connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getConnectionNo() {
        return this.connectionNo;
    }

    public void setConnectionNo(String connectionNo) {
        this.connectionNo = connectionNo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.DetailEmployeeInfo JD-Core Version: 0.5.4
 */