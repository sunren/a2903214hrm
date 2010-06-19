package com.hr.io.extend;

import com.hr.examin.bo.interfaces.IWorkFlowBO;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.bo.AuthorityPosBo;
import com.hr.spring.SpringBeanFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public abstract class ICheckAndInsert {
    String noAuth;
    protected IEmployeeBo employeeBo;

    public ICheckAndInsert() {
        this.noAuth = "您对{0}无排班权限！";
        this.employeeBo = ((IEmployeeBo) SpringBeanFactory.getBean("empBo"));
    }

    public abstract int[] insertTransmit(List paramList, CommonParameters paramCommonParameters)
            throws Exception;

    public Map<String, String> getNameIdMap(String clazz, String id, String name, String[] condition) {
        List<Object[]> rs = this.employeeBo.exeHqlList(clazz, new String[] { id, name }, condition);
        Map result = new HashMap();
        for (Object[] objArr : rs) {
            result.put((String) objArr[1], (String) objArr[0]);
        }
        return result;
    }

    public List<Employee> getEmployeeList() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.addOrder(Order.asc(Employee.PROP_EMP_DISTINCT_NO));
        return this.employeeBo.findByCriteria(detachedCriteria);
    }

    public void chechDeptAuth(Employee currentEmp, String[] empIdArr, CommonParameters commonParas) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        String posId = (String) session.getAttribute("positionId");

        AuthorityPosBo authposBo = (AuthorityPosBo) SpringBeanFactory.getBean("authPosService");
        String[][] deptLocsInCharge = authposBo.getDeptAndLocInCharge(posId, Integer.valueOf(4));
        if (deptLocsInCharge == null)
            return;

        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List<Employee> empList = empBo.searchEmpArray(empIdArr);

        String empStr = "";
        IWorkFlowBO workflowBo = (IWorkFlowBO) SpringBeanFactory.getBean("workFlowBO");
        for (Employee emp : empList) {
            if ("NOCHARGE".equals(workflowBo.checkEmpCharge(deptLocsInCharge, emp))) {
                empStr = empStr + emp.getEmpName() +  "、";
            }

        }

        if (empStr.length() > 0) {
            empStr = empStr.substring(0, empStr.length() - 1);
            commonParas.addErrorMessage(this.noAuth, null, new String[] { empStr });
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.extend.ICheckAndInsert JD-Core Version: 0.5.4
 */